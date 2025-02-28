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
            int mtp = 2, submtp = 4, ctp = 7;
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

            String filename = "";
            if (session.getAttribute("FILENAME") != null)
            {
                filename = (String)session.getAttribute("FILENAME");
                if(filename != null && !filename.equals(""))
                    filename = talentpool.getMainPath("view_candidate_file")+filename;
            }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= talentpool.getMainPath("title") != null ? talentpool.getMainPath("title") : "" %></title>
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
        <link rel="stylesheet" type="text/css" href="../autofill/jquery-ui.min.css" />  <!-- Autofill-->
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="doaddtrainingcertdetail"/>
        <html:hidden property="doSavetrainingcertdetail"/>
        <html:hidden property="doDeletetrainingcertdetail"/>
        <html:hidden property="trainingcerthiddenfile"/>
        <html:hidden property="trainingandcertId"/>
        <html:hidden property="currentDate"/>
        <html:hidden property="courseverification"/>
        <html:hidden property="courseIndex"/>
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
                                                    <h4>TRAINING, CERTIFICATION & SAFETY COURSES</h4>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Course Type</label>
                                                    <html:select property="coursetypeId" styleClass="form-select">
                                                        <html:optionsCollection filter="false" property="coursetypes" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-8 col-md-8 col-sm-8 col-12 form_group">
                                                    <label class="form_label">Course Name<span class="required">*</span></label>
                                                    <html:select property="coursenameId" styleClass="form-select">
                                                        <html:optionsCollection filter="false" property="coursenames" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Educational Institute</label>
                                                    <html:text property="educationInstitute" styleId="educationInstitute" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("educationInstitute").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <div class="row flex-end align-items-end">
                                                        <div class="col-lg-10 col-md-12 col-sm-12 col-12 form_group">
                                                            <label class="form_label">Location of Institute (City)</label>
                                                            <html:hidden property="locationofInstituteId" />
                                                            <html:text property="cityName" styleId="cityName" styleClass="form-control" maxlength="100" onblur="if (this.value == '') {
                                                                        document.forms[0].locationofInstituteId.value = '0';
                                                                    }"/>
                                                            <script type="text/javascript">
                                                                document.getElementById("cityName").setAttribute('placeholder', '');
                                                            </script>
                                                        </div>
                                                        <div class="col-lg-2 col-md-12 col-sm-12 col-12 form_group pd_left_null">
                                                            <a href="javascript:;" class="add_btn" data-bs-toggle="modal" data-bs-target="#city_modal"><i class="mdi mdi-plus"></i></a>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Field of Study</label>
                                                    <html:text property="fieldofstudy" styleId="fieldofstudy" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("fieldofstudy").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Course Started</label>
                                                    <div class="input-daterange input-group">
                                                        <html:text property="coursestarted" styleId="coursestarted" styleClass="form-control add-style wesl_dt date-add" />
                                                        <script type="text/javascript">
                                                            document.getElementById("coursestarted").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                        </script>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Passing Date</label>
                                                    <div class="input-daterange input-group">
                                                        <html:text property="passingdate" styleId="passingdate" styleClass="form-control add-style wesl_dt date-add" />
                                                        <script type="text/javascript">
                                                            document.getElementById("passingdate").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                        </script>
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
                                                    <label class="form_label">Certification No.</label>
                                                    <html:text property="certificationno" styleId="certificationno" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("certificationno").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Date of Expiry</label>
                                                    <div class="input-daterange input-group">
                                                        <html:text property="dateofexpiry" styleId="dateofexpiry" styleClass="form-control add-style wesl_dt date-add" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                this.removeAttribute('readonly');
                                                                this.blur();
                                                                this.focus();
                                                            }"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("dateofexpiry").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                        </script>
                                                    </div>
                                                </div>
<!--                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Course Verification<span class="required">*</span></label>
                                                    <html:select styleClass="form-select" property="courseverification">
                                                        <html:option value="">- Select -</html:option>
                                                        <html:option value="Yes">Yes</html:option>
                                                        <html:option value="No">No</html:option>
                                                    </html:select>
                                                </div>-->
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Approved By</label>
                                                    <html:select property="approvedbyId" styleClass="form-select">
                                                        <html:optionsCollection filter="false" property="approvedbys" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Attach Documents (5MB each) (.pdf/.jpeg/.png)</label>
                                                    <html:file property="trainingcertfile" styleId="upload1" onchange="javascript: setClass('1');"/>
                                                    <a href="javascript:;" id="upload_link_1" class="attache_btn uploaded_img1"><i class="fas fa-paperclip"></i> Attach</a>
                                                    <% if(!filename.equals("")) {%><div class="down_prev"><a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=filename%>');">Preview</a></div><% } %>
                                                </div>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                                <a href="javascript:submittrainingcertform();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
                                                <a href="javascript: openTab('8');" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
        <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        <span class="resume_title"> File</span>
                        <div class="float-end">
                            <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                            <a id='diframe' href="" class="down_btn" download=""><img src="../assets/images/download.png"/></a>
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
        <script src="../assets/libs/jquery/jquery.min.js"></script>
        <script src="../autofill/jquery-ui.min.js" type="text/javascript"></script> <!-- autofill-->
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>	
        <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/talentpool.js"></script>
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
            $(function () {
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
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
                            data: JSON.stringify({"search": request.term}),
                            success: function (data) {
                                response(data);
                            }
                        });
                    },
                    select: function (event, ui) {
                        $('#cityName').val(ui.item.label); // display the selected text
                        $('input[name="locationofInstituteId"]').val(ui.item.value);
                        return false;
                    },
                    focus: function (event, ui)
                    {
                        $('#cityName').val(ui.item.label); // display the selected text
                        return false;
                    }
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
