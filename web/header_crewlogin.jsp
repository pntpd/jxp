<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%
    String userName = "";
    if (session.getAttribute("WELCOMECRL") != null) {
        userName = (String) request.getSession().getAttribute("WELCOMECRL");
    }
    CrewloginInfo cl_info = null;
    String c_name = "", a_name = "";
    int crewId = 0;
    if (session.getAttribute("CREWLOGIN") != null) {
        cl_info = (CrewloginInfo) request.getSession().getAttribute("CREWLOGIN");
    }
    if (cl_info != null) {
        c_name = cl_info.getClientname() != null ? cl_info.getClientname() : "";
        a_name = cl_info.getAssetname() != null ? cl_info.getAssetname() : "";
        crewId = cl_info.getCrewrotationId();
    }
%>

<html:hidden property="doCancel"/>
<html:hidden property="doView"/>
<html:hidden property="doViewProfile"/>
<html:hidden property="doViewBanklist"/>
<html:hidden property="doViewlangdetail"/>
<html:hidden property="doViewvaccinationlist"/>
<html:hidden property="doViewgovdocumentlist"/>
<html:hidden property="doViewtrainingcertlist"/>
<html:hidden property="doVieweducationlist"/>
<html:hidden property="doViewexperiencelist"/>
<html:hidden property="doViewhealthdetail"/>
<html:hidden property="doGetFeedbackList"/>
<html:hidden property="doCompetencyList"/>
<html:hidden property="documentList"/>
<html:hidden property="trainingList"/>
<html:hidden property="viewContractList"/>
<html:hidden property="viewInterviewList"/>
<html:hidden property="viewClientOfferwList"/>
<html:hidden property="doLogout"/>
<header id="page-topbar">
    <!--<div class="container-fluid header_top_area">A BW Offshore & Planet Energy JW</div>-->
    <div class="navbar-header">
        <div class="d-flex">
            <div class="navbar-brand-box">
                <a href="javascript:;" class="logo logo-light">
                    <span class="logo-lg"><img src="../assets/images/ocs-logo.png" alt="logo" onclick="javascript: backtohome();"></span>
                    <button type="button" class="btn btn-sm px-3 font-size-24 header-item waves-effect menu-icon-right" id="vertical-menu-btn"><i class="mdi mdi-menu"></i></button>
                </a>
            </div>
        </div>

        <div class="d-flex flex-center align-items-center">
            <%if (crewId > 0) {%>
            <div class="cleint_asset_name">
                <ul>
                    <li><label>Client Name</label><span><%=c_name%></span></li>
                    <li><label>Asset Name</label><span><%=a_name%></span></li>
                </ul>
            </div>
            <%} else {%>
            <div class="cleint_asset_name">
                <ul>
                    <li><label></label><span><%=userName%></span></li> 
                </ul>
            </div>
            <%}%>
            <div class="dropdown noti_user">
                <a class="btn header-item waves-effect" href="javascript: ;" onclick="javascript: logout();">
                    <img class="header-profile-user user_noti" src="../assets/images/log_out.png" alt="User"></br>
                    <span>Log Out</span>
                </a>
            </div>
        </div>
    </div>
</header>

<div class="vertical-menu">
    <div data-simplebar class="h-100">
        <div id="sidebar-menu">
            <ul class="metismenu list-unstyled" id="side-menu">
                <li class="menu1<%=(ctp == 1 || ctp == 2 || ctp == 3 || ctp == 4 || ctp == 5 || ctp == 6 || ctp == 7 || ctp == 8 || ctp == 13 || ctp == 14) ? " mm-active active" : ""%>">
                    <a href="javascript:;" class="has-arrow waves-effect"><strong>Profile</strong></a>
                    <ul class="sub-menu mm-collapse" aria-expanded="false">
                        <li><a class="<%=(ctp == 1) ? " active" : ""%>" href="javascript:;" onclick="view1();">Personal</a></li>
                        <li><a class="<%=(ctp == 2) ? " active" : ""%>"  href="javascript:;" onclick="openTab('2');">Languages</a></li>
                        <li><a class="<%=(ctp == 3) ? " active" : ""%>"  href="javascript:;" onclick="openTab('4');">Health</a></li>
                        <li><a class="<%=(ctp == 4) ? " active" : ""%>"  href="javascript:;" onclick="openTab('5');">Vaccination</a></li>
                        <li><a class="<%=(ctp == 5) ? " active" : ""%>"  href="javascript:;" onclick="openTab('10');">Experience</a></li>
                        <li><a class="<%=(ctp == 6) ? " active" : ""%>"  href="javascript:;" onclick="openTab('9');">Education</a></li>
                        <li><a class="<%=(ctp == 7) ? " active" : ""%>"  href="javascript:;" onclick="openTab('8');">Certifications</a></li>
                        <li><a class="<%=(ctp == 8) ? " active" : ""%>"  href="javascript:;" onclick="openTab('6');">Documents</a></li>
                        <li><a class="<%=(ctp == 14) ? " active" : ""%>"  href="javascript:;" onclick="openTab('14');">Bank</a></li>
                        <%if (crewId > 0) {%><li><a class="<%=(ctp == 13) ? " active" : ""%>"  href="javascript:;" onclick="openTab('13');">Contract</a></li><%}%>
                    </ul>
                </li>
                <%if (crewId > 0) {%>
                <li class="menu2<%=(ctp == 9) ? " mm-active active" : ""%>">
                    <a href="javascript:;" onclick="getFeedbacklist();" class="waves-effect<%=(ctp == 9) ? " active" : ""%>"><strong>Wellness Feedback</strong></a>
                </li>
                <li class="menu3<%=(ctp == 10) ? " mm-active active" : ""%>">
                    <a href="javascript:;" onclick="javascript: getCompetencylist();" class="waves-effect">
                        <strong>Competency</strong>
                    </a>
                </li>
                <li class="menu3<%=(ctp == 11) ? " mm-active active" : ""%>">
                    <a href="javascript:;" onclick="javascript: getDocumentlist();" class="waves-effect">
                        <strong>Documents</strong>
                    </a>
                </li>
                <li class="menu3<%=(ctp == 12) ? " mm-active active" : ""%>">
                    <a href="javascript:;" onclick="javascript: getTraininglist();" class="waves-effect">
                        <strong>Training Course</strong>
                    </a>
                </li>
                <%}%>
                <li class="menu3<%=(ctp == 15) ? " mm-active active" : ""%>">
                    <a href="javascript:;" onclick="javascript: getInterviewlist();" class="waves-effect">
                        <strong>Interview</strong>
                    </a>
                </li>
                <li class="menu3<%=(ctp == 16) ? " mm-active active" : ""%>">
                    <a href="javascript:;" onclick="javascript: getOfferlist();" class="waves-effect">
                        <strong>Client Offer</strong>
                    </a>
                </li>
                <!--                <li class="menu4 log_out_button">
                                    <a  href="/jxp/feedback/FeedbackAction.do?doLogout=yes" class="waves-effect">
                                        <img src="../assets/images/log_out.png"/> <strong>Log Out</strong>
                                    </a>
                                </li>-->
                <li class="menu5 privacy_button mm-active">
                    <a href="/jxp/feedback/crewprivacy_policy.jsp" class="waves-effect active">Privacy Policy</a>                    
                </li>
            </ul>
        </div>
    </div>
</div>

<script src="../assets/js/sweetalert2.min.js"></script>
<link href="../assets/css/minimal.css" rel="stylesheet"/>