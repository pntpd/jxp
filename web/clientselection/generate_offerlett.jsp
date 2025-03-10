<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.clientselection.ClientselectionInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="clientselection" class="com.web.jxp.clientselection.Clientselection" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 2, submtp = 51;
            String per = "N", addper = "N", editper = "N", deleteper = "N",approveper="N";
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
                    approveper = uinfo.getApproveper() != null ? uinfo.getApproveper() : "N";
                }
            }

                String pdffilename = "", clientasset="";
                int sflag = 0, shortlistId = 0, jobpostId = 0 , oflag = 0, typeId = 0;
                if(request.getAttribute("PDFFILENAME") != null)
                {
                    pdffilename = (String) request.getAttribute("PDFFILENAME");   
                }
                if(request.getAttribute("SFLAG") != null)
                {
                    sflag = (int) request.getAttribute("SFLAG");   
                }
                if(request.getAttribute("OFLAG") != null)
                {
                    oflag = (int) request.getAttribute("OFLAG");   
                }
                if(request.getAttribute("SHORTLISTID") != null)
                {
                    shortlistId = (int) request.getAttribute("SHORTLISTID");   
                }
                if(request.getAttribute("JOBPOSTID") != null)
                {
                    jobpostId = (int) request.getAttribute("JOBPOSTID");   
                }
                if(request.getAttribute("OFF_TP") != null)
                {
                    typeId = (int) request.getAttribute("OFF_TP");   
                }
                if(request.getAttribute("CLIENTASSET") != null)
                {
                    clientasset = (String) request.getAttribute("CLIENTASSET");   
                }
                String file_path = clientselection.getMainPath("view_resumetemplate_pdf");
                String pdffile = "";
                if(!pdffilename.equals(""))
                {
                    pdffile = file_path+pdffilename;
                }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= clientselection.getMainPath("title") != null ? clientselection.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">

        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/clientselection.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/clientselection/ClientselectionAction.do" onsubmit="return false;">
        <html:hidden property="doSave"/>
        <html:hidden property="doView"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="candidateId"/>
        <html:hidden property="shortlistId"/>
        <html:hidden property="sflag"/>
        <html:hidden property="jobpostId"/>
        <html:hidden property="clientId"/>
        <html:hidden property="from"/>
        <html:hidden property="type"/>
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <div class="main-content">
                <div class="page-content tab_panel1 no_tab1">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow"><a href="javascript: gobackview();"><img  src="../assets/images/back-arrow.png"/></a> <span>Client Selection</span></div>
                            <div class="float-end">
                                <div class="toggled-off usefool_tool">
                                    <div class="toggle-title">
                                        <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                        <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>
                                    </div>
                                    <div class="toggle-content">
                                        <h4>Useful Tools</h4>
                                        <%@include file ="../shortcutmenu.jsp"%>
                                    </div>
                                </div>
                            </div>
                        </div>	
                    </div>

                    <div class="container-fluid pd_0">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 pd_0">
                                <div class="body-background com_checks">

                                    <div class="row">
                                        <div class="col-md-12  ">
                                            <div class="main-heading mt_30 mb_20">
                                                <div class="add-btn"><h4>GENERATE OFFER LETTER</h4></div>
                                                <div class="clear_btn pull_right float-end"><a href="javascript: resetForm();">Reset </a></div>
                                            </div>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-12 col-12">
                                            <div class="row">

                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Select Offer Letter Format</label>
                                                    <html:select property="resumetempId" styleId="resumetempId" styleClass="form-select">
                                                        <html:option value="-2">OCS Template</html:option>
                                                        <html:optionsCollection filter="false" property="resumetemplates" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>

                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Variable Pay Element (%)</label>
                                                    <html:text property="variablepay" styleId="variablepay" styleClass="form-control" maxlength="12"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("variablepay").setAttribute('placeholder', '');
                                                    </script>
                                                </div>

                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <div class="row">
                                                        <label for="example-text-input" class="form_label">Date</label>
                                                        <div class="full_width field_ic">
                                                            <div class="input-daterange input-group input-addon display_flex">
                                                                <html:text property="validfrom" styleId="wesl_from_dt" styleClass="form-control wesl_from_dt"/>
                                                                <script type="text/javascript">
                                                                    document.getElementById("wesl_from_dt").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                                </script>
                                                                <div class="input-group-add">
                                                                    <span class="input-group-text">To</span>
                                                                </div>
                                                                <html:text property="validto" styleId="wesl_to_dt" styleClass="form-control wesl_from_dt"/>
                                                                <script type="text/javascript">
                                                                    document.getElementById("wesl_to_dt").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                                </script>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Contractor</label>
                                                    <html:text property="contractor" styleId="contractor" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("contractor").setAttribute('placeholder', '');
                                                    </script>
                                                </div>


                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Effective Date of Official Call off Order</label>
                                                    <div class="input-daterange input-group">
                                                        <html:text property="edate" styleId="edate" styleClass="form-control add-style wesl_dt date-add" />
                                                        <script type="text/javascript">
                                                            document.getElementById("edate").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                        </script>

                                                    </div>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Demand Manager Name</label>
                                                    <html:text property="dmname" styleId="dmname" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("dmname").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Designation</label>
                                                    <html:text property="designation" styleId="designation" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("designation").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Comments</label>
                                                    <html:textarea property="comments" styleId="comments" styleClass="form-control" rows="4" />
                                                    <script type="text/javascript">
                                                        document.getElementById("comments").setAttribute('placeholder', '');
                                                        document.getElementById("comments").setAttribute('maxlength', '1000');
                                                    </script>
                                                </div>


                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 1</label>
                                                    <html:text property="cval1" styleId="cval1" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval1").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 2</label>
                                                    <html:text property="cval2" styleId="cval2" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval2").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 3</label>
                                                    <html:text property="cval3" styleId="cval3" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval3").setAttribute('placeholder', '');
                                                    </script>
                                                </div>

                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 4</label>
                                                    <html:text property="cval4" styleId="cval4" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval4").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 5</label>
                                                    <html:text property="cval5" styleId="cval5" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval5").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 6</label>
                                                    <html:text property="cval6" styleId="cval6" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval6").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 7</label>
                                                    <html:text property="cval7" styleId="cval7" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval7").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 8</label>
                                                    <html:text property="cval8" styleId="cval8" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval8").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 9</label>
                                                    <html:text property="cval9" styleId="cval9" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval9").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 10</label>
                                                    <html:text property="cval10" styleId="cval10" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval10").setAttribute('placeholder', '');
                                                    </script>
                                                </div>

                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="generatepdfdiv">
                                                    <a href="javascript: generateofferpdf('<%=clientasset%>');" class="gen_btn"> Generate</a>
                                                </div>
                                            </div>	
                                        </div>
                                        <div class="col-lg-8 col-md-8 col-sm-12 col-12">
                                            <div class="row">
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <iframe class="cv_file" src="<%=pdffile%>" id="iframe" ></iframe>
                                                </div>
                                            </div>	
                                        </div>	

                                    </div>
                                    <div class="row">
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                            <a href="javascript:;" onclick=" javascript: getModelforoffer('<%= shortlistId%>', '<%= sflag%>', '<%= oflag%>');" data-bs-toggle="modal" data-bs-target="#mail_modal" class="save_btn"><%if(oflag == 3){%>Resend Email Candidate<%} else {%>Email Candidate <%}%></a>
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
        <div id="mail_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id = 'mailmodal'>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="thank_you_modalmail" class="modal fade thank_you_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="thankyou">
                                <h2>Thank You!</h2>
                                <center><img src="../assets/images/thank-you.png"></center>
                                <h3>Email Sent</h3>
                                <!--<p>Candidate CV has been successfully sent to the client.</p>-->
                                <p>Offer letter has been successfully sent to candidate.</p>
                                <a href="javascript: view('<%= jobpostId%>', '');" class="msg_button" style="text-decoration: underline;">Client Selection</a>
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
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="../assets/js/table-responsive.init.js"></script>
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
        <script type="text/javascript">
            jQuery(document).ready(function () {
                var date_pick = ".wesl_from_dt";
                $(date_pick).datepicker({
                    todayHighlight: !0,
                    format: "dd-M-yyyy",
                    autoclose: "true",
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