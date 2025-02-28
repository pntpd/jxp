<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.candidate.CandidateInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 2, submtp = 7, ctp = 2;
            String per = "N", addper = "N", editper = "N", deleteper = "N";
        if (session.getAttribute("HOME_EMAILID") == null)
        {
%>
    <jsp:forward page="/homeindex1.jsp"/>
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
            String filename = "";
            if (session.getAttribute("FILENAME") != null)
            {
                filename = (String)session.getAttribute("FILENAME");
                if(filename != null && !filename.equals(""))
                    filename = candidate.getMainPath("view_candidate_file")+filename;
            }
            ArrayList list = new ArrayList();
            if(request.getSession().getAttribute("MODULEPER_LIST") != null)
                list = (ArrayList)request.getSession().getAttribute("MODULEPER_LIST");
    %>
    <head>
        <meta charset="utf-8">
        <title><%= candidate.getMainPath("title") != null ? candidate.getMainPath("title") : "" %></title>
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
        <link href="https://fonts.googleapis.com/css2?family=Rubik:wght@300;400;500;600&display=swap" rel="stylesheet">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/home.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed registration_page">
        <html:form action="/home/HomeAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
            <html:hidden property="candidateId"/>
            <html:hidden property="doSave"/>
            <html:hidden property="doModify"/>
            <html:hidden property="doCancel"/>
            <html:hidden property="doView"/>
            <html:hidden property="doViewBanklist"/>
            <html:hidden property="doViewlangdetail"/>
            <html:hidden property="doViewhealthdetail"/>
            <html:hidden property="doViewvaccinationlist"/>
            <html:hidden property="doViewgovdocumentlist"/>
            <html:hidden property="doViewhitchlist"/>
            <html:hidden property="doViewtrainingcertlist"/>
            <html:hidden property="doVieweducationlist"/>
            <html:hidden property="doViewexperiencelist"/>
            <html:hidden property="doaddlangdetail" />
            <html:hidden property="doModifylangdetail"/>
            <html:hidden property="doSavelangdetail"/>
            <html:hidden property="candidateLangId"/>
            <html:hidden property="langfilehidden" />
            <div id="layout-wrapper">
                <%@include file ="../homeheader.jsp"%>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content tab_panel">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12 tab_head_area">
                                <%@include file ="../homecandidatetab.jsp"%>
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
                                                <div class="main-heading m_30 mt_30">
                                                    <div class="add-btn">
                                                        <h4>LANGUAGE DETAILS</h4>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-6 form_group">
                                                    <label class="form_label">Select Language<span class="required">*</span></label>
                                                    <html:select property="languageId" styleClass="form-select">
                                                        <html:optionsCollection filter="false" property="languages" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-6 form_group">
                                                    <label class="form_label">Proficiency<span class="required">*</span></label>
                                                    <html:select property="proficiencyId" styleClass="form-select">
                                                        <html:optionsCollection filter="false" property="proficiencies" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-6 form_group">
                                                    <label class="form_label">Language Test Certificate (if any)(5MB)(.pdf/.jpeg/.png)</label>                                                   
                                                    <html:file property="langfile" styleId="upload1" onchange="javascript: setClass('1');"/>
                                                    <a href="javascript:;" id="upload_link_1" class="attache_btn uploaded_img1"><i class="fas fa-paperclip"></i> Attach</a>
                                                    <% if(!filename.equals("")) {%><div class="down_prev"  id='preview_1'><a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframeedit('<%=filename%>');">Preview</a></div><% } %>
                                                </div>
                                            </div>
                                        </div>	
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                            <a href="javascript: submitlangaugeform();" class="save_btn"><img src="../assets/images/save.png"/> Save</a>
                                            <a href="javascript:openTab('2');" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
                    }
                    else if (uval.includes(".pdf"))
                    {
                        url_v = uval+"#toolbar=0&page=1&view=fitH,100";
                        classname = "pdf_mode";
                    }
                    else
                    {
                        url_v = uval;
                        classname = "img_mode";
                    }
                    window.top.$('#iframe').attr('src', 'about:blank');
                    setTimeout(function () {
                        window.top.$('#iframe').attr('src', url_v);
                         document.getElementById("iframe").className=classname;
                         document.getElementById("diframe").href =uval;
                    }, 1000);

                    $("#iframe").on("load", function() {
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