<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.resumetemplate.ResumetemplateInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="resumetemplate" class="com.web.jxp.resumetemplate.Resumetemplate" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 7, submtp = 50;
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

        ResumetemplateInfo info = null;
        if(request.getAttribute("RESUMETEMPLATE_DETAIL") != null)
            info = (ResumetemplateInfo)request.getAttribute("RESUMETEMPLATE_DETAIL");
        
        String viewpath = resumetemplate.getMainPath("view_resumetemplate_pdf");       
%>  
<head>
    <meta charset="utf-8">
    <title><%= resumetemplate.getMainPath("title") != null ? resumetemplate.getMainPath("title") : "" %></title>
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
    <script type="text/javascript" src="../jsnew/resumetemplate.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/resumetemplate/ResumetemplateAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
<html:hidden property="doCancel"/>
<html:hidden property="search"/>
<html:hidden property="resumetemplateId"/>
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
                        <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> View Resume Template</a></div>
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
                                            <label class="form_label">Template</label>
                                            <span class="form-control"><%= (info.getName() != null && !info.getName().equals("")) ? info.getName() : "&nbsp;" %></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Work Experience Column</label>
                                            <span class="form-control"><%= resumetemplate.getNameFromId(info.getExpColumn()) != null && !resumetemplate.getNameFromId(info.getExpColumn()).equals("") ? resumetemplate.getNameFromId(info.getExpColumn()) : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Experience Type</label>
                                            <span class="form-control"><%= info.getExptype() == 1 ? "Table" : "Vertical"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Education Column</label>
                                            <span class="form-control"><%= resumetemplate.getEduFromId(info.getEduColumn()) != null && !resumetemplate.getEduFromId(info.getEduColumn()).equals("") ? resumetemplate.getEduFromId(info.getEduColumn()) : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Education Type</label>
                                            <span class="form-control"><%= info.getEdutype() == 1 ? "Table" : "Vertical"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Document Column</label>
                                            <span class="form-control"><%= resumetemplate.getDocFromId(info.getDocColumn()) != null && !resumetemplate.getDocFromId(info.getDocColumn()).equals("") ? resumetemplate.getDocFromId(info.getDocColumn()) : "&nbsp;"%></span>
                                        </div>
<%--                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Document Type</label>
                                            <span class="form-control"><%= info.getDoctype() == 1 ? "Table" : "Vertical"%></span>
                                        </div>--%>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Training and Certificate Column</label>
                                            <span class="form-control"><%= resumetemplate.getTrainingFromId(info.getTrainingColumn()) != null && !resumetemplate.getTrainingFromId(info.getTrainingColumn()).equals("") ? resumetemplate.getTrainingFromId(info.getTrainingColumn()) : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Training and Certificate Type</label>
                                            <span class="form-control"><%= info.getCerttype() == 1 ? "Table" : "Vertical"%></span>
                                        </div>
                                        <div><b>Alternate Labels For Experience</b></div>                                        
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Position</label>
                                            <span class="form-control"><%= info.getLabel1()!= null && !info.getLabel1().equals("") ? info.getLabel1() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Department</label>
                                            <span class="form-control"><%= info.getLabel2()!= null && !info.getLabel2().equals("") ? info.getLabel2() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Company</label>
                                            <span class="form-control"><%= info.getLabel3()!= null && !info.getLabel3().equals("") ? info.getLabel3() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Asset</label>
                                            <span class="form-control"><%= info.getLabel4()!= null && !info.getLabel4().equals("") ? info.getLabel4() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Asset Type</label>
                                            <span class="form-control"><%= info.getLabel5()!= null && !info.getLabel5().equals("") ? info.getLabel5() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Start Date</label>
                                            <span class="form-control"><%= info.getLabel6()!= null && !info.getLabel6().equals("") ? info.getLabel6() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">End Date</label>
                                            <span class="form-control"><%= info.getLabel7()!= null && !info.getLabel7().equals("") ? info.getLabel7() : "&nbsp;"%></span>
                                        </div>
                                        
                                        <div><b>Alternate Labels For Education</b></div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Kind</label>
                                            <span class="form-control"><%= info.getLabel8()!= null && !info.getLabel8().equals("") ? info.getLabel8() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Degree</label>
                                            <span class="form-control"><%= info.getLabel9()!= null && !info.getLabel9().equals("") ? info.getLabel9() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Institution / Location</label>
                                            <span class="form-control"><%= info.getLabel10()!= null && !info.getLabel10().equals("") ? info.getLabel10() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Field Of study</label>
                                            <span class="form-control"><%= info.getLabel11()!= null && !info.getLabel11().equals("") ? info.getLabel11() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Start Date</label>
                                            <span class="form-control"><%= info.getLabel12()!= null && !info.getLabel12().equals("") ? info.getLabel12() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">End Date</label>
                                            <span class="form-control"><%= info.getLabel13()!= null && !info.getLabel13().equals("") ? info.getLabel13() : "&nbsp;"%></span>
                                        </div>
                                        
                                        
                                        <div><b>Alternate Labels For Document</b></div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Document Name</label>
                                            <span class="form-control"><%= info.getLabel14()!= null && !info.getLabel14().equals("") ? info.getLabel14() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Number</label>
                                            <span class="form-control"><%= info.getLabel15()!= null && !info.getLabel15().equals("") ? info.getLabel15() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Place Of Issue</label>
                                            <span class="form-control"><%= info.getLabel16()!= null && !info.getLabel16().equals("") ? info.getLabel16() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Issued By</label>
                                            <span class="form-control"><%= info.getLabel17()!= null && !info.getLabel17().equals("") ? info.getLabel17() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Issue Date</label>
                                            <span class="form-control"><%= info.getLabel18()!= null && !info.getLabel18().equals("") ? info.getLabel18() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Expiry Date</label>
                                            <span class="form-control"><%= info.getLabel19()!= null && !info.getLabel19().equals("") ? info.getLabel19() : "&nbsp;"%></span>
                                        </div>
                                        
                                        <div><b>Alternate Labels For Certification</b></div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Issue Date</label>
                                            <span class="form-control"><%= info.getLabel20()!= null && !info.getLabel20().equals("") ? info.getLabel20() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Expiry Date</label>
                                            <span class="form-control"><%= info.getLabel21()!= null && !info.getLabel21().equals("") ? info.getLabel21() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Validity</label>
                                            <span class="form-control"><%= info.getLabel22()!= null && !info.getLabel22().equals("") ? info.getLabel22() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Course Name</label>
                                            <span class="form-control"><%= info.getLabel23()!= null && !info.getLabel23().equals("") ? info.getLabel23() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Type</label>
                                            <span class="form-control"><%= info.getLabel24()!= null && !info.getLabel24().equals("") ? info.getLabel24() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Institution/Location</label>
                                            <span class="form-control"><%= info.getLabel25()!= null && !info.getLabel25().equals("") ? info.getLabel25() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Approved By</label>
                                            <span class="form-control"><%= info.getLabel26()!= null && !info.getLabel26().equals("") ? info.getLabel26() : "&nbsp;"%></span>
                                        </div>                                        
                                        
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                <label class="control-label col-md-4 bold">Template Type:</label>
                                            <div class="col-md-8 control-label2">
                                                <span><%= info.getTemptype()== 1  ? "For CV" : "For Offer Letter" %></span>
                                            </div>
                                        </div>
                                        </div>
                                         <div class="row">
                                            <div class="col-md-12 form-horizontal">
                                            <div class="form-group">
                                                <label class="control-label col-md-2 bold">Description:</label>
                                                <div class="col-md-10 control-label2">
                                                    <span></span>
                                                    <span><%= info.getDescription() != null && !info.getDescription().equals("") ? info.getDescription() : "NA" %></span>
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
                                                        <a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript: setIframeedit('<%=viewpath+info.getFilename()%>');">Preview</a>
                                                    </span>
                                                </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group" id="submitdiv">
                                                    <label class="form_label">Delete Water Mark</label>
                                                    <span >
                                                        <a href="javascript: deleteFile('<%=info.getResumetemplateId()%>');"><img src="../assets/images/remove.png" /></a>
                                                    </span>
                                                </div>
                                            <%}%>
                                        </div>
                                    </div>
                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                        <label class="form_label">Status</label>
                                        <span class="form-control"><%= resumetemplate.getStatusById(info.getStatus()) %></span>
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
    <!-- END layout-wrapper -->
    <%@include file="../footer.jsp" %>
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
