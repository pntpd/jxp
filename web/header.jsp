<%
    String userName = "";
    if (session.getAttribute("WELCOME") != null)
    {
        userName = (String) request.getSession().getAttribute("WELCOME");
    }
    String photo = "";
    if (session.getAttribute("USERPHOTO") != null)
    {
        photo = (String) request.getSession().getAttribute("USERPHOTO");
    }

    int totalReminder = 0;
    if(session.getAttribute("TOTALRCOUNT") != null)
    {
        totalReminder = Integer.parseInt((String) session.getAttribute("TOTALRCOUNT"));
    }

    if(photo.equals(""))
        photo = "/jxp/assets/images/empty_profile.png";
%>
<input type="hidden" name="candidateIdHeader" value="0"/>
<input type="hidden" name="doViewHeader" value=""/>
<header id="page-topbar">	
    <div class="navbar-header">
        <div class="d-flex">
            <div class="navbar-brand-box">
                <a href="javascript:;" class="logo logo-light">
                    <span class="logo-lg"><img src="/jxp/assets/images/header-logo.png" alt="logo"></span>
                </a>
                <span class="lg_round_bg">&nbsp;</span>
            </div>
        </div>
        
        <div class="d-flex">              
            <div class="top-textfield" id="headersearchdiv" style="display: none;">
                <input type="text" name="searchheader" id="searchheader" tabindex="998" class="form-control" maxlength="200" placeholder="Find Your Candidate" onchange="javascript: autoheader();" onkeyup="javascript: autoheader();" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                    this.removeAttribute('readonly');
                                    this.blur();
                                    this.focus();
                                }"/>
                <div tabindex="999" style="margin-top: 0px; margin-left: 0px;overflow:auto; height:auto;position:inherit;width: 250px;display:none;" id="headersdiv"></div>
            </div>
            <div class="me-2 find_candidate">
                <button type="button" onclick="javascript: opensearchbox();" class="btn header-item waves-effect">Find Your Candidate </button>
            </div>
            
            <div class="dropdown d-inline-block me-2 noti_user notifi_div">
                <a href='/jxp/documentexpiry/DocumentexpiryAction.do' type="button" class="btn header-item noti-icon waves-effect" id="page-header-notifications-dropdown">
                    <img src="/jxp/assets/images/notification.png" class="user_noti"/> <span><%=totalReminder%></span>
                </a>
                <div class="dropdown-menu dropdown-menu-lg dropdown-menu-end p-0" aria-labelledby="page-header-notifications-dropdown" style="">
                    <div class="p-3">
                        <div class="row align-items-center">
                            <div class="col">
                                <a href="javascript:void(0);" class="right-bar-toggle float-end">
                                    <i class="mdi mdi-close noti-icon"></i>
                                </a>
                                <h5 class="m-0"> Notification </h5>
                            </div>
                        </div>
                    </div>

                    <div class="p-3 noti_list">
                        <ul>
                           
                        </ul>
                    </div>

                </div>
            </div>
            <div class="dropdown d-inline-block noti_user">
                <button type="button" class="btn header-item waves-effect" id="page-header-user-dropdown" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <img class="header-profile-user user_noti" src="/jxp/assets/images/user-profile.png" alt="User">
                </button>
                <div class="dropdown-menu dropdown-menu-lg dropdown-menu-end" style="">
                    <div class="rightbar-title">
                        <a href="javascript:void(0);" class="right-bar-toggle float-end">
                            <i class="mdi mdi-close noti-icon"></i>
                        </a>
                        <h5 class="m-0">Your Profile <%=userName != null ? userName : ""%></h5>
                    </div>

                    <div class="row rightbar-title">
                        <div class="profile_user_bg">
                            <img class="profile_bg" src="/jxp/assets/images/profile_bg.jpg"/>
                            <img class="profile_img" src="<%=photo%>"/>
                        </div>
                    </div>
                    <div class="prof_work_area">
                        <a class="dropdown-item" href="javascript: openCPForm();"><i class="ion ion-md-create"></i> Change Password</a>
                        <a class="dropdown-item about-drop-item" href="javascript:;" data-bs-toggle="modal" data-bs-target="#thank_you_modal_header" ><i class="ion ion-ios-information-circle-outline noti-icon"></i> About</a>
                        <a class="dropdown-item" href="/jxp/user/privacy_policy.jsp"><svg xmlns="http://www.w3.org/2000/svg" width="16.148" height="30.187" viewBox="0 0 34.148 33.187"><defs><style>.cls-1{fill:#707070}</style></defs><g id="Privacy_Policy" data-name="Privacy Policy" transform="translate(-0.212 -0.325)"><g id="Group_7746" data-name="Group 7746" transform="translate(0.212 0.325)"><path id="Path_98363" data-name="Path 98363" class="cls-1" d="M24.334,26.134a15.967,15.967,0,0,1-5.708-5.85,18.533,18.533,0,0,1-2.517-8.806.9.9,0,0,1,.709-.893A26.2,26.2,0,0,0,20.926,9.35a26.021,26.021,0,0,0,3.408-1.632V3.511a1.334,1.334,0,0,0-.423-.965,1.557,1.557,0,0,0-1.077-.422H3.51a1.557,1.557,0,0,0-1.077.422,1.333,1.333,0,0,0-.424.965V30.326a1.333,1.333,0,0,0,.424.965,1.557,1.557,0,0,0,1.077.422H22.834a1.557,1.557,0,0,0,1.077-.422,1.334,1.334,0,0,0,.423-.965Zm-2.466-8.343a.9.9,0,0,1,1.355-1.179l1.692,1.945,3.16-5.765a.9.9,0,0,1,1.573.864l-3.754,6.851a.9.9,0,0,1-1.488.2l-2.538-2.916ZM5.928,7.209a.9.9,0,0,1,0-1.8H20.415a.9.9,0,0,1,0,1.8H5.928Zm0,21.215a.9.9,0,1,1,0-1.8H20.415a.9.9,0,1,1,0,1.8H5.928Zm0-5.3a.9.9,0,0,1,0-1.8H16.206a.9.9,0,0,1,0,1.8Zm0-5.3a.9.9,0,0,1,0-1.8h7.827a.9.9,0,0,1,0,1.8H5.928Zm0-5.3a.9.9,0,1,1,0-1.8H13.1a.9.9,0,1,1,0,1.8H5.928Zm20.2-4.8A26.133,26.133,0,0,0,29.541,9.35a25.991,25.991,0,0,0,4.108,1.233.9.9,0,0,1,.707.958,18.547,18.547,0,0,1-2.516,8.744,15.962,15.962,0,0,1-5.708,5.849v4.192a3.124,3.124,0,0,1-.985,2.265,3.35,3.35,0,0,1-2.312.921H3.51A3.349,3.349,0,0,1,1.2,32.591a3.12,3.12,0,0,1-.986-2.265V3.511A3.12,3.12,0,0,1,1.2,1.246,3.349,3.349,0,0,1,3.51.325H22.834a3.35,3.35,0,0,1,2.312.921,3.124,3.124,0,0,1,.985,2.265V7.717ZM20.177,19.386a14.164,14.164,0,0,0,5.056,5.2,14.166,14.166,0,0,0,5.056-5.2,16.862,16.862,0,0,0,2.246-7.216,27.772,27.772,0,0,1-3.647-1.149,27.852,27.852,0,0,1-3.655-1.753,27.77,27.77,0,0,1-3.654,1.753,27.731,27.731,0,0,1-3.647,1.149,16.855,16.855,0,0,0,2.246,7.216Z" transform="translate(-0.212 -0.325)"></path></g></g></svg> Privacy Policy</a>
                        <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="/jxp/user/LogoutAction.do"><i class="ion ion-ios-log-out noti-icon"></i> Logout</a>                        
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
                        
<div id="thank_you_modal_header" class="modal fade thank_you_modal blur_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
            </div>
            <div class="modal-body modal-data">
                <div class="row">
                    <div class="col-lg-12">
                        <h3>About</h3>
                        <center><span class="logo-lg"><img src="/jxp/assets/images/header-logo.png" alt="logo"></span></center>
                        &nbsp;
                        <p><b>Version 01.2023</b></p>
                        <p>Last updated: 27-09-2023</p>
                        <p>Powered by  <a href="https://planetngtech.com/" target="_blank" class="anchor_underline">Planet NEXTgen Technologies India private Limited</a></p>
                        <p>Unauthorised distribution and reproduction of this software is prohibited.</p>
                        <p class="copyright-para">&copy; 2023 JourneyXpro. All rights reserved.</p>
                        <div class="modal-button"><a href="javascript: ;" class="button" data-bs-dismiss="modal" >Okay</a></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>