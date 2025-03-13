<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.feedback.FeedbackInfo"%>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<jsp:useBean id="crewlogin" class="com.web.jxp.crewlogin.Crewlogin" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int ctp = 0;
            if (session.getAttribute("CREWLOGIN") == null && request.getParameter("mobLogin") != null) {
                String stremail = request.getParameter("mobLogin");
                CrewloginInfo info = crewlogin.getCandidateInfo(stremail);
                session.setAttribute("CREWLOGIN", info);
                if (info != null) {
                    session.setAttribute("WELCOMECRL", (info.getName() != null ? info.getName() : ""));
                }
            }
            if (session.getAttribute("CREWLOGIN") == null) {

    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%        }
        String assetname = "", position = "";
        int crewrotationId = 0;
        if (session.getAttribute("CREWLOGIN") != null) {
            CrewloginInfo info = (CrewloginInfo) session.getAttribute("CREWLOGIN");
            if (info != null) {
                crewrotationId = info.getCrewrotationId();
                assetname = info.getAssetname() != null ? info.getAssetname() : "";
                position = info.getPosition() != null ? info.getPosition() : "";
            }
        }
        ArrayList feedback_list = new ArrayList();
        int count = 0;
        int recordsperpage = feedback.getCount();
        if (session.getAttribute("COUNT_LIST") != null) {
            count = Integer.parseInt((String) session.getAttribute("COUNT_LIST"));
        }
        if (session.getAttribute("FEEDBACK_LIST") != null) {
            feedback_list = (ArrayList) session.getAttribute("FEEDBACK_LIST");
        }
        int pageSize = count / recordsperpage;
        if (count % recordsperpage > 0) {
            pageSize = pageSize + 1;
        }

        int total = feedback_list.size();
        int showsizelist = feedback.getCountList("show_size_list");
        int CurrPageNo = 1;

        String message = "", clsmessage = "deleted-msg";
        if (request.getAttribute("MESSAGE") != null) {
            message = (String) request.getAttribute("MESSAGE");
            request.removeAttribute("MESSAGE");
        }
        if (message.toLowerCase().contains("success")) {
            message = "";
        }
        if (message != null && (message.toLowerCase()).indexOf("success") != -1) {
            clsmessage = "updated-msg";
        }
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
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/feedback.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed crew_user dash_page bg_image">
    <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;">
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 heading_title"><h1>Welcome,  <%=userName != null ? userName : ""%>!<br><span><%=(!assetname.equals("") ? assetname + " | " + position : position)%></span></h1></div>

                            <div class="col-md-12 col-xl-12">
                                <div class="body-background bg_null dash_bg">
                                    <div class="row row-flex justify-content-between">
                                        <div class="col-xl-3 col-lg-4 col-md-4 col-sm-6 col-12">
                                            <div class="dash_list">
                                                <a href="javascript:;" onclick="showProfile();">
                                                    <img src="../assets/images/training-and-dev.png"/></br><span>Profile</span>	
                                                </a>
                                                <div class="dash_list_desc">
                                                    <p>
                                                        Profile will carry all your personal, educational, and professional details, including the important documents, certifications, and health files. 
                                                    </p>
                                                    <p>
                                                        Your shared data will be saved in our secured software. All the information you share will be kept confidential by OCS.
                                                    </p>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="col-xl-3 col-lg-4 col-md-4 col-sm-6 col-12">
                                            <div class="dash_list">
                                                <a href="javascript:;" onclick="getFeedbacklist();">
                                                    <img src="../assets/images/wellness-mgmt.png"/></br><span>Wellness Feedback</span>	
                                                </a>
                                                <div class="dash_list_desc">
                                                    <p>
                                                        Our work culture encourages employees to be open and have frank dialogues with their colleagues and mentors across hierarchy and function, so they can deliver excellence. We are determined to have a learning work atmosphere conducive to teamwork and co-operation at core. These surveys help us attain a clear picture of our trajectory and help us get better each day.
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xl-3 col-lg-4 col-md-4 col-sm-6 col-12">
                                            <div class="dash_list">
                                                <a href="javascript:;" onclick="getCompetencylist();">
                                                    <img src="../assets/images/competency-mgmt.png"/></br><span>Competency</span>	
                                                </a>
                                                <div class="dash_list_desc">
                                                    <p>
                                                        Our frontline is empowered to execute, excel and establish with a highly refined attention to detail. Every employee has a clear progression plan and a well-defined career path. The Competency Assessments at regular intervals helps you to grow at all stages and phases of their professional journey.
                                                    </p>
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
                                                    addLoadEvent(setmenu('11'))
        </script>
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
