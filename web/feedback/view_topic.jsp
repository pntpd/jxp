<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.feedback.FeedbackInfo" %>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int ctp = 10;
            if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%
        }
        ArrayList list = new ArrayList();
        if (request.getAttribute("TOPICLIST") != null) {
            list = (ArrayList) request.getAttribute("TOPICLIST");
        }
        int list_size = list.size();
    %>
    <head>
        <meta charset="utf-8">
        <title><%= feedback.getMainPath("title") != null ? feedback.getMainPath("title") : ""%></title>
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
        <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;">
        <html:hidden property="doCancelCompetency"/>
        
        <div id="layout-wrapper"> 
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 heading_title"><h1><a href="javascript: gobackcomp();"><i class="ion ion-ios-arrow-round-back"></i></a>  Competency</h1></div>

                            <div class="col-md-12 col-xl-12">
                                <div class="body-background online_assessment topic_list">
                                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 user_table_list">
                                        <div class="table-rep-plugin sort_table">
                                            <div class="table-responsive mb-0">
                                                <table class="table table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th width="80%"><b>Topic</b></th>
                                                            <th width="10%" class="text-center"><b>Actions</b></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
<%
                                                    int topicId = 0;
                                                    if (list_size > 0) 
                                                    {
                                                        FeedbackInfo info;
                                                        for (int i = 0; i < list_size; i++) 
                                                        {
                                                            info = (FeedbackInfo) list.get(i);
                                                            if (info != null) 
                                                            {
                                                                topicId = info.getDdlValue();
%>
                                                        <tr>
                                                            <td><b><%=(info.getDdlLabel() != null ? info.getDdlLabel() : "")%></b></td>
                                                            <td class="action_column text-center">
                                                                <a href="javascript:;" onclick="javascript: viewTopicFiles('<%=topicId%>');" data-bs-toggle="modal" data-bs-target="#view_resume_list"><img src="../assets/images/view.png"> </a>
                                                            </td>
                                                        </tr>
<%                                                          }
                                                        }
                                                    }else{
                                                    %>
                                                        <tr >
                                                            <td colspan="2"><%=feedback.getMainPath("record_not_found") %></td>                                                            
                                                        </tr>
<%                                                         
                                                                }
%>
                                                    </tbody>				
                                                </table>
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
        <script>
            // toggle class show hide text section
            $(document).on('click', '.toggle-title', function () {
                $(this).parent()
                        .toggleClass('toggled-on')
                        .toggleClass('toggled-off');
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
