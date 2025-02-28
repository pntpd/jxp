<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.feedback.FeedbackInfo"%>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%
        }
        int crewrotationId = 0, ctp = 16;
        if (session.getAttribute("CREWLOGIN") != null) {
            CrewloginInfo info = (CrewloginInfo) session.getAttribute("CREWLOGIN");
            if (info != null) {
                crewrotationId = info.getCrewrotationId();
            }
        }
        ArrayList list = new ArrayList();

        if (session.getAttribute("CLIENTOFFER_LIST") != null) {
            list = (ArrayList) session.getAttribute("CLIENTOFFER_LIST");
        }
        int total = list.size();
        String filepath = feedback.getMainPath("view_resumetemplate_pdf");

        int tpId =0, sId = 0;
        if(session.getAttribute("TP_ID") != null)
        {
            tpId = Integer.parseInt((String) session.getAttribute("TP_ID"));
        }
        session.removeAttribute("TP_ID");

        if(session.getAttribute("S_ID") != null)
        {
            sId = Integer.parseInt((String) session.getAttribute("S_ID"));
        }
        session.removeAttribute("S_ID");
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

        <link href="../assets/filter/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/filter/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/feedback.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed crew_user bg_image">
    <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="doSaveOffer"/>
        <html:hidden property="doSendmail"/>
        
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>

            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">

                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 heading_title"><h1>Offer Details</h1></div>
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background index_list">
                                    <div class="row">
                                        
                                        <div class="" id='ajax_cat'>
                                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                
                                                <ul class="comp_list">
                                                    <li class="client_pos_rank_label">Position | Rank</li>
                                                    <li class="client_mail_label">Mail By</li>
                                                    <li class="client_mail_date_label">Mail Date</li>
                                                    <li class="client_action_label">Action</li>
                                                </ul>
                                                <ul class="comp_list_detail" id="sort_id">
<%
                                            FeedbackInfo info;     
                                            String file = "";
                                            for (int i = 0; i < total; i++) 
                                            {
                                                info = (FeedbackInfo) list.get(i);
                                                if (info != null) 
                                                {
                                                            file =  info.getFilename() != null ? info.getFilename() : "";  
%>
                                                    <li>
                                                        <div class="comp_list_main">
                                                            <ul>                                                                
                                                                <li class="client_pos_rank_label" onclick="javascript: ;">
                                                                    <span class="mob_show label_title">Position | Rank</span>
                                                                    <span class="value_record"><%= info.getPositionname() != null ? info.getPositionname() : ""%></span>
                                                                </li>
                                                                <li class="client_mail_label" onclick="javascript: ;">
                                                                    <span class="mob_show label_title">Mail By</span>
                                                                    <span class="value_record"><%= info.getUsername() != null ? info.getUsername() : ""%></span>
                                                                </li>
                                                                <li class="client_mail_date_label">
                                                                    <span class="mob_show label_title">Mail Date</span>
                                                                    <span class="value_record"><%= info.getDate() != null ? info.getDate() : ""%></span>
                                                                </li>
                                                                <li class="client_action_label action_list_column">
                                                                    <span class="mob_show label_title">Action</span>
                                                                    <%if(!file.equals("")) {%>
                                                                        <a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript: setIframe('<%=(filepath+file)%>');"><img src="../assets/images/attachment.png"/></a>
                                                                        <%if(tpId > 0) {%>
                                                                        <a href="javascript:;" class="mr_15" onclick=" javascript: getEmailModal('<%= info.getShortlistId()%>', '<%=tpId%>');" data-bs-toggle="modal" data-bs-target="#mail_modal">Send Mail</a>
                                                                        <%}else{%>
                                                                        <a class="pending_label" href="javascript:;" class="mr_15" onclick=" javascript: replayToOffer('<%=info.getShortlistId()%>') ;" data-bs-toggle="modal" data-bs-target="#user_pending_modal">Pending</a>
                                                                        <%}%>
                                                                    <%}%>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </li>
<%
                                                }
                                            }
%>
                                                </ul>
                                                
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
        <!-- END layout-wrapper -->
        <div id="user_pending_modal" class="modal fade parameter_modal">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body ">
                        <div class="row">
                            <div class="col-lg-12" id="offer_pending_div">

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
                                <center> <iframe id='iframe' class="doc" src=""></iframe></center>
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
                        <div class="col-lg-12" id='emailmodal'>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

        <!-- JAVASCRIPT -->
        <script src="../assets/filter/jquery.min.js"></script>	
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/js/bootstrap-multiselect.js"></script>
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/time/bootstrap-timepicker.min.js" type="text/javascript"></script>
        <script src="../assets/filter/select2.full.min.js" type="text/javascript"></script>
        <script src="../assets/filter/menu-app.js"></script>
        <script src="../assets/filter/app.min.js"></script> 
        <script src="../assets/filter/components-select2.min.js" type="text/javascript"></script>
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="../assets/js/sweetalert2.min.js"></script>
        <% if(tpId > 0){%>
        <script type="text/javascript">
            $(window).on('load', function () {
                getEmailModal('<%=sId%>','<%=tpId%>');
            });
        </script>
        <%}%>    
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
