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
            int mtp = 2, submtp = 4, ctp = 26;
            if (session.getAttribute("LOGININFO") == null)
            {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
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

            String pdffilename = "";
            if(request.getAttribute("PDFFILENAME") != null)
            {
                pdffilename = (String) request.getAttribute("PDFFILENAME");   
            }
            String file_path = talentpool.getMainPath("view_candidate_file");
            String pdffile = "";
            if(!pdffilename.equals(""))
            {
                pdffile = file_path + pdffilename;
            }
            TalentpoolInfo info = null;
            String candidateName = "", clientName = "";
            if (session.getAttribute("CANDIDATE_DETAIL") != null) {
                info = (TalentpoolInfo) session.getAttribute("CANDIDATE_DETAIL");
                candidateName = info.getName() != null ? info.getName(): "";
                clientName = info.getClientName() != null ? info.getClientName(): "";
            }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= talentpool.getMainPath("title") != null ? talentpool.getMainPath("title") : "" %></title>
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
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/talentpool.js"></script>
        <script type="text/javascript" src="../jsnew/talentpoolgeneratecv.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="contractdetailId"/>
        <html:hidden property="clientassetId"/>
        <html:hidden property="doGeneratedContract"/>
        <html:hidden property="clientIdContract"/>
        <html:hidden property="assetIdContract"/>
        <html:hidden property="contractStatus"/>
        <html:hidden property="fromDate"/>
        <html:hidden property="toDate"/>
        <html:hidden property="currentDate"/>
        <html:hidden property="cfilehidden"/>
        <html:hidden property="type"/>
        <html:hidden property="cancelContract"/>
        <div id="layout-wrapper">
            <%@include file ="../header.jsp"%>
            <%@include file ="../sidemenu.jsp"%>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content tab_panel tab_panel_level_2">
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
                    <div class="container-fluid pd_0">
                            <div class="row">
                                <div class="col-md-12 col-xl-12 pd_0">
                                    <div class="body-background com_checks1">                                       
                                        <div class="row">
                                            <div class="col-md-12  ">
                                                <div class="main-heading mt_30 mb_20">
                                                    <div class="add-btn"><h4>GENERATE CONTRACT</h4></div>
                                                    <div class="clear_btn pull_right float-end"><a href="javascript: resetContractForm();">Reset </a></div>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-12 col-12">
                                                <div class="row">

                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                       <label class="form_label">Contract</label>
                                                        <html:select property="contractId" styleId="contractId" styleClass="form-select">
                                                            <html:optionsCollection filter="false" property="contracts" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                       <label class="form_label">Select Valid From</label>
                                                        <html:text property="dateofissue" styleId="dateofissue" styleClass="form-control add-style wesl_dt date-add" />
                                                       <script type="text/javascript">
                                                           document.getElementById("dateofissue").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                       </script>
                                                    </div>
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                       <label class="form_label">Select Valid To</label>
                                                        <html:text property="dateofexpiry" styleId="dateofexpiry" styleClass="form-control add-style wesl_dt date-add" />
                                                       <script type="text/javascript">
                                                           document.getElementById("dateofexpiry").setAttribute('placeholder', 'DD-MMM-YYYY');
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
                                                            document.getElementById("cval1").setAttribute('placeholder', '');
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
                                                       
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="pdfdiv2">
                                                        <a href="javascript: gotoContractlist() ;" class="cl_btn"> Close</a>
                                                    </div>
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="pdfdiv">
                                                        <a href="javascript: generateContractpdf();" class="gen_btn" style="left: 590px;"> Generate</a> 
                                                    </div>
                                                </div>	
                                            </div>                                                    
                                            <div class="col-lg-8 col-md-8 col-sm-12 col-12">
                                                <div class="row">
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                        <iframe class="cv_file" src="<%= pdffile%>" id="iframe" ></iframe>
                                                    </div>
                                                </div>	
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="dmail" style="display: none;">
                                                <!--<a href="javascript:;" onclick=" javascript: getContarctModel();" data-bs-toggle="modal" data-bs-target="#mail_modal" class="save_btn">Email Crew </a>-->
                                                <!--<a href="javascript:;" onclick=" javascript: generateContractpdf();"  class="save_btn">Submit </a>-->
                                                <a href="javascript:;" onclick=" javascript: submitContractmailForm('<%=candidateName%>', '<%=clientName%>');"  class="save_btn">Submit </a>
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
                            <a id='diframe' href="" class="down_btn"><img src="../assets/images/download.png"/></a>
                            <a id='diframedel' href="javascript: delfileedit('1');" class="trash_icon"><img src="../assets/images/trash.png"/></a>
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
        function setIframeedit(uval)
        {
            var url_v = "", classname = "";
            if (uval.includes(".doc") || uval.includes(".docx") || uval.includes("wordprocessingml.document"))
            {
                url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
                classname = "doc_mode";
            } else if (uval.includes(".pdf"))
            {
                url_v = uval+"#toolbar=0&page=1&view=fitH,100";
                classname = "pdf_mode";
            } else
            {
                url_v = uval;
                classname = "img_mode";
            }
            window.top.$('#iframe').attr('src', 'about:blank');
            setTimeout(function () {
                window.top.$('#iframe').attr('src', url_v);
                document.getElementById("iframe").className = classname;
                document.getElementById("diframe").href = uval;
            }, 1000);

            $("#iframe").on("load", function () {
                let head = $("#iframe").contents().find("head");
                let css = '<style>img{margin: 0px auto;}</style>';
                $(head).append(css);
            });
        }
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
