<%@page import="com.web.jxp.clientlogin.ClientloginInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%
    String userName = "";
    if (session.getAttribute("MLOGININFO") != null) 
    {
        ClientloginInfo uInfo = (ClientloginInfo) session.getAttribute("MLOGININFO");
        userName = uInfo.getName() != null ? uInfo.getName(): "";
    }
%>

<html:hidden property="doCancel"/>
<html:hidden property="doView"/>
<html:hidden property="doLogout"/>
<header id="page-topbar">
    <div class="navbar-header">
        <div class="d-flex">
            <div class="navbar-brand-box">
                <a href="javascript:;" class="logo logo-light">
                    <span class="logo-lg"><img src="../assets/images/ocs-logo.png" alt="logo" onclick="javascript: backtohome();"></span>
                    <!--<button type="button" class="btn btn-sm px-3 font-size-24 header-item waves-effect menu-icon-right" id="vertical-menu-btn"><i class="mdi mdi-menu"></i></button>-->
                </a>
            </div>
        </div>

        <div class="d-flex flex-center align-items-center">
            <div class="cleint_asset_name">
                <ul>
                    <li><label>User Name</label><span><%=userName%></span></li>
                </ul>
            </div>
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
                
                <li class="menu2<%=(ctp == 1) ? " mm-active active" : ""%>">
                    <a href="javascript:;" onclick="javascript: ;" class="waves-effect<%=(ctp == 1) ? " active" : ""%>"><strong>Selection</strong></a>
                </li>
<!--                <li class="menu4 log_out_button">
                    <a  href="/jxp/feedback/ClientloginAction.do?doLogout=yes" class="waves-effect">
                        <img src="../assets/images/log_out.png"/> <strong>Log Out</strong>
                    </a>
                </li>
                <li class="menu5 privacy_button mm-active">
                    <a href="/jxp/feedback/crewprivacy_policy.jsp" class="waves-effect active">Privacy Policy</a>                    
                </li>-->
            </ul>
        </div>
    </div>
</div>

<script src="../assets/js/sweetalert2.min.js"></script>
<link href="../assets/css/minimal.css" rel="stylesheet"/>