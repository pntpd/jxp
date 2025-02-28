<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.candidate.CandidateInfo" %>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 2, submtp = 7, ctp = 3;
            if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%
        }
        ArrayList list = new ArrayList();
        if (request.getSession().getAttribute("MODULEPER_LIST") != null) {
            list = (ArrayList) request.getSession().getAttribute("MODULEPER_LIST");
        }
        String file_path = candidate.getMainPath("view_candidate_file");
        CandidateInfo healthinfo = null;
        if (session.getAttribute("CANDHEALTHINFO") != null) {
            healthinfo = (CandidateInfo) session.getAttribute("CANDHEALTHINFO");
        }
        int ipass = 0;
        if (session.getAttribute("PASS") != null) {
            ipass = Integer.parseInt((String) session.getAttribute("PASS"));
        }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= candidate.getMainPath("title") != null ? candidate.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/feedback.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed crew_user bg_image">
    <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;" styleClass="form-horizontal">
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">

                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 heading_title"><h1>Profile > Health Details</h1></div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="body-background">
                                    <div class="row">
                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 mt_30">
                                            <div class="row">
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Seaman Specific Medical Fitness</label>
                                                    <span class="form-control"><%= healthinfo != null && healthinfo.getSsmf() != null && !healthinfo.getSsmf().equals("")  ? healthinfo.getSsmf() :  "&nbsp;" %></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">OGUK Medical FTW</label>
                                                    <span class="form-control"><%= healthinfo != null &&  healthinfo.getOgukmedicalftw() != null && !healthinfo.getOgukmedicalftw().equals("")  ? healthinfo.getOgukmedicalftw() : "&nbsp;" %></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">OGUK Expiry</label>
                                                    <span class="form-control"><%=  healthinfo != null &&  healthinfo.getOgukexp() != null && !healthinfo.getOgukexp().equals("")  ? healthinfo.getOgukexp() : "&nbsp;" %></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Medical Fitness Certificate</label>
                                                    <span class="form-control"><%=  healthinfo != null &&  healthinfo.getMedifitcert() != null && !healthinfo.getMedifitcert().equals("")  ? healthinfo.getMedifitcert() : "&nbsp;" %></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Medical Fitness Certificate Expiry</label>
                                                    <span class="form-control"><%=  healthinfo != null &&  healthinfo.getMedifitcertexp() != null && !healthinfo.getMedifitcertexp().equals("")  ? healthinfo.getMedifitcertexp() : " &nbsp;" %></span>
                                                </div>

                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Blood Group</label>
                                                    <span class="form-control"><%=  healthinfo != null &&  healthinfo.getBloodgroup() != null && !healthinfo.getBloodgroup().equals("")  ? healthinfo.getBloodgroup() : " &nbsp;" %></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Blood Pressure</label>
                                                    <span class="form-control"><%=  healthinfo != null &&  healthinfo.getBloodpressure() != null && !healthinfo.getBloodpressure().equals("")  ? healthinfo.getBloodpressure() :"&nbsp;" %></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Hypertension</label>
                                                    <span class="form-control"><%= healthinfo != null &&  healthinfo.getHypertension() != null && !healthinfo.getHypertension().equals("")  ? healthinfo.getHypertension() : "&nbsp;" %></span>
                                                </div>
                                            </div>
                                            <div class="row">	
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Diabetes</label>
                                                    <span class="form-control"><%= healthinfo != null &&  healthinfo.getDiabetes() != null && !healthinfo.getDiabetes().equals("")  ? healthinfo.getDiabetes() : "&nbsp;" %></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Smoking</label>
                                                    <span class="form-control"><%= healthinfo != null && healthinfo.getSmoking() != null && !healthinfo.getSmoking().equals("")  ? healthinfo.getSmoking() : "&nbsp;" %></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Covid-19 2 Doses</label>
                                                    <span class="form-control"><%= healthinfo != null && healthinfo.getCov192doses() != null && !healthinfo.getCov192doses().equals("")  ? healthinfo.getCov192doses() : "&nbsp;" %></span>
                                                </div>
                                                <% if(healthinfo != null && healthinfo.getMfFilename() !=null && !healthinfo.getMfFilename().equals("")) {%>
                                            <div class="col-lg-3 col-md-3 col-sm-6 col-12 text-left flex-end align-items-end edit_sec">
                                                <ul class="resume_attach">
                                                    <li><label class="form_label">File</label></li>
                                                    <li><a href="javascript:;" class="attache_btn"><i class="fas fa-paperclip"></i> Attachment <span class="attach_number"> <%=candidate.changeNum(1,3)%></span></a></li>
                                                    <li>
                                                        <div class="down_prev text-end">
                                                            <a href="javascript:;" class="" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=file_path+healthinfo.getMfFilename() %>');">Preview</a>
                                                        </div>
                                                    </li>
                                                </ul>
                                            </div>
                                            <%}%>
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
        <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        <span class="resume_title"> File</span>
                            <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                            <a id='diframe' href="" class="down_btn" download=""><img src="../assets/images/download.png"/></a>
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
        <script src="../assets/libs/jquery/jquery.min.js"></script>		
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>
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
        </script>
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
