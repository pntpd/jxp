<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.onboarding.OnboardingInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="onboarding" class="com.web.jxp.onboarding.Onboarding" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 2, submtp = 56;
            String per = "N", addper = "N", editper = "N", deleteper = "N",approveper="N";
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
                    approveper = uinfo.getApproveper() != null ? uinfo.getApproveper() : "N";
                }
            }

                OnboardingInfo info = null;
                if (request.getAttribute("ONBOARDING_DETAIL") != null) {
                    info = (OnboardingInfo) request.getAttribute("ONBOARDING_DETAIL");
                }

                OnboardingInfo cinfo = null;
                if (request.getAttribute("SHORTLISTEDCANDIDATE_DETAILS") != null) {
                    cinfo = (OnboardingInfo) request.getAttribute("SHORTLISTEDCANDIDATE_DETAILS");
                }

                ArrayList formalitylist = null;
                if (request.getAttribute("UPLOADFORMALITYLIST_DETAILS") != null) {
                    formalitylist = (ArrayList) request.getAttribute("UPLOADFORMALITYLIST_DETAILS");
                }
                ArrayList kitlist = null;
                if (request.getAttribute("KITLIST_DETAILS") != null) {
                    kitlist = (ArrayList) request.getAttribute("KITLIST_DETAILS");
                }
                
                int total = formalitylist.size();
                String file_path = onboarding.getMainPath("view_onboarding");
                
                String viw_path = onboarding.getMainPath("view_candidate_file");
                String cphoto = "../assets/images/empty_user.png";
                if (!cinfo.getPhoto().equals("")) {
                    cphoto = viw_path + cinfo.getPhoto();
                }
            System.out.println("cphoto :: "+cphoto);
    %>
    <head>
        <meta charset="utf-8">
        <title><%= onboarding.getMainPath("title") != null ? onboarding.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/onboarding.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/onboarding/OnboardingAction.do" onsubmit="return false;" enctype="multipart/form-data">
        <html:hidden property="doGenerate"/>
        <html:hidden property="doSave"/>
        <html:hidden property="doView"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="doMail"/>
        <html:hidden property="doUpload"/>
        <html:hidden property="doDeleteExt"/>
        <html:hidden property="candidateId"/>
        <html:hidden property="shortlistId"/>
        <html:hidden property="jobpostId"/>
        <html:hidden property="clientId"/>
        <html:hidden property="clientassetId"/>
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <div class="main-content">
                <div class="page-content tab_panel1 no_tab1">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow"><a href="javascript: gobackview();"><img  src="../assets/images/back-arrow.png"/></a> <span>Onboarding</span></div>
                            <div class="float-end">
                                <div class="toggled-off usefool_tool">
                                    <div class="toggle-title">
                                        <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                        <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>
                                    </div>
                                    <div class="toggle-content">
                                        <h4>Useful Tools</h4>
                                        <%@include file ="../shortcutmenu.jsp"%>
                                    </div>
                                </div>
                            </div>
                        </div>	
                    </div>

                    <div class="container-fluid pd_0">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 pd_0">


                                <div class="body-background com_checks">

                                    <div class="row com_checks_main">

                                        <div class="col-md-12 com_top_right">
                                            <div class="row d-flex align-items-center">
                                                <div class="col-md-5 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Client Asset</label></div>
                                                        <div class="col-md-9"><span><%=  info.getClientName()+"-"+ info.getClientAsset() %></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Location</label></div>
                                                        <div class="col-md-9"><span><%= info.getCountry() %></span></div>
                                                    </div>

                                                </div>
                                                <div class="col-md-5 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Total Positions</label></div>
                                                        <div class="col-md-9"><span><%= info.getPositionCount() %></span></div>
                                                    </div>
                                                    <div class="row mb_0">&nbsp;</div>
                                                </div>

                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="mob_value">Onboarded <span><%=  onboarding.changeNum( info.getOnboardCount(),2)%></a>&nbsp;/&nbsp;<%=  onboarding.changeNum( info.getTotalnoofopenings(),2)%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_view_job"><a href="javascript:;"><img src="../assets/images/view.png"/><br/> View Asset</a></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                    <div class="row onboarding_form_main">

                                        <div class="col-lg-4 col-md-4 col-sm-12 col-12">
                                            <div class="row">
                                                <div class="col-md-12  ">
                                                    <div class="search_short_main gen_prof_list">
                                                        <ul>
                                                            <li class="odd_list_1">
                                                                <div class="search_box">
                                                                    <div class="row">
                                                                        <div class="col-md-12 comp_view">
                                                                            <div class="row">
                                                                                <div class="col-md-12 client_prof_status">
                                                                                    <div class="row d-flex align-items-center">
                                                                                        <div class="col-md-3 com_view_prof cand_box_img">
                                                                                            <div class="user_photo pic_photo">
                                                                                                <div class="upload_file">
                                                                                                    
                                                                                                    <img src="<%=cphoto%>">
                                                                                                    <a href="javascript:;" onclick="viewCandidate('<%=cinfo.getCandidateId()%>');"><img src="../assets/images/view.png"></a>
                                                                                                    
                                                                                                </div>
                                                                                            </div>	
                                                                                        </div>

                                                                                        <div class="full_name_ic col-md-9 mb_0">
                                                                                            <div class="row">
                                                                                                <div class="col-md-12">
                                                                                                    <b><%=  cinfo.getName()%> </b>
                                                                                                </div>
                                                                                                <div class="col-md-12">
                                                                                                    <b><%= cinfo.getPosition()%> </b>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>


                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row gen_list_area">
                                                <div class="col-lg-6 col-md-6 col-sm-6 col-6 form_group">                                                        
                                                        <label class="form_label">Onboarding Date</label>
                                                        <div class="input-daterange input-group">
                                                            <input type="text" name="date2" value="<%=cinfo.getDate()%>" id="date2" class="form-control add-style wesl_dt date-add" placeholder="DD-MMM-YYYY">
                                                        </div>
                                                    </div>
                                                <div class="col-md-12 ">
                                                    <div class="main-heading mb_20">
                                                        <div class="add-btn"><h4>COLLECT FORMS</h4></div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <div class="table-responsive mb-0">
                                                        <table id="tech-companies-1" class="table table-striped">
                                                            <tbody id="ajax_cat">

                                                                <%
                                                                    if(cinfo.getOnboardflag() == 7)
                                                                    {
                                                                %>
                                                                <tr>
                                                                    <td width="80%">External joining formalities(.zip 20mb)</td>
                                                                    <td width="20%" class="text-center">
                                                                        <span class="mr_15">
                                                                            <a data-bs-toggle="modal" data-bs-target="#upload_collect_modal" id="" class="coll_form_ic" ><img src="../assets/images/upload.png"/></a>
                                                                        </span>
                                                                    </td>
                                                                </tr>
                                                                <%} else if(cinfo.getOnboardflag() <= 8 && cinfo.getUpflag() == 0){
                                                                            for(int i = 0 ;i < total;i++){
                                                                            OnboardingInfo pinfo = (OnboardingInfo)formalitylist.get(i);
                                                                            if (pinfo != null) {
                                                                
                                                                            String pdffile = "";
                                                                       
                                                                            String tickclass = "";
                                                                            if(!pinfo.getPdffilename().equals(""))
                                                                            {
                                                                                tickclass = "green_tick";
                                                                                pdffile = file_path+pinfo.getPdffilename();
                                                                            }
                                                                            else
                                                                            {
                                                                                tickclass = "cross";
                                                                            }
                                                                %>
                                                                <tr>
                                                                    <td width="67%"><%=  pinfo.getFormalityname() != null ? pinfo.getFormalityname() : "" %></td>
                                                                    <td width="33%" class="text-right">
                                                                        <% if(!pinfo.getPdffilename().equals("")) {%>
                                                                        <a href="javascript:;" class="coll_form_ic mr_8"><img src="../assets/images/green_tick_uploaded.png"/></a>
                                                                        <a href="javascript:;" class="coll_form_ic mr_8" onclick="javascript: showformality('<%= pdffile%>');"><img src="../assets/images/view.png"/></a>
                                                                        <a href="javascript: deluploadfromlist('<%= pinfo.getOnboarddocId() %>');" class="coll_form_ic"><img src="../assets/images/cross.png"/></a>
                                                                            <%} else {%>
                                                                        <span class="mr_15">
                                                                            <a data-bs-toggle="modal" data-bs-target="#upload_collect_modal" id="" class="coll_form_ic" onclick="javascript : setOnboarddocId('<%= pinfo.getOnboarddocId() %>');"><img src="../assets/images/upload.png"/></a> 
                                                                        </span>
                                                                        <%}%>
                                                                    </td>
                                                                </tr>
                                                                <%
                                                                }
                                                                }
                                                        }
                                                                   if(cinfo.getOnboardflag() == 8 && cinfo.getUpflag() == 1)
                                                                   {
                                                                %>
                                                                <tr>
                                                                    <td width="67%">External joining formalities(.zip 20mb)</td>
                                                                    <td width="33%" class="text-right">
                                                                        <a href="javascript:;" class="coll_form_ic mr_15"><img src="../assets/images/green_tick_uploaded.png"/></a>
                                                                        <a href="<%=file_path+cinfo.getExtfilename()%>" class="coll_form_ic mr_15"><img src="../assets/images/view.png"/></a>
                                                                        <input type="hidden" id="hdnExternalfile" name="hdnExternalfile" value="<%=cinfo.getExtfilename()%>"/>
                                                                        <a href="javascript:;" onclick="deleteExternalfile();" class="coll_form_ic "><img src="../assets/images/cross.png"/></a>

                                                                    </td>
                                                                </tr>
                                                                <%}%>
                                                            </tbody>	
                                                        </table>
                                                    </div>	
                                                </div>
                                                <%
                                                       if((cinfo.getOnboardflag() == 7 || cinfo.getOnboardflag() == 8) && cinfo.getUpflag() == 1)
                                                       {
                                                %>
                                                <div class="row ">
                                                    <div class="col-md-12 text-center">
                                                        <a href="javascript:;" <% if(cinfo.getOnboardflag() == 8 && cinfo.getUpflag() == 1){%> data-bs-toggle="modal"  data-bs-target="#onboard_candidate_list_modal"<%}%> class="down_email long_arrow <%= cinfo.getOnboardflag() == 8 && cinfo.getUpflag() == 1 ? "" : "disable_text proceed_btn" %>">Proceed <i class="ion ion-ios-arrow-round-forward"></i></a>
                                                    </div>
                                                </div>
                                                <%
                                                    }
                                                %>

                                            </div>

                                        </div>
                                        <div class="col-lg-8 col-md-8 col-sm-12 col-12">
                                            <div class="row">
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <iframe class="cv_file" src="" id="iframe"></iframe>
                                                </div>
                                            </div>	
                                        </div>	

                                    </div>
                                    <%
                                    if(cinfo.getOnboardflag() <= 8 && cinfo.getUpflag() == 0)
                                    {
                                    %>
                                    <div class="row">
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                            <a href="javascript:;" <% if(cinfo.getOnboardflag() == 8){%>data-bs-toggle="modal" data-bs-target="#onboard_candidate_list_modal"<%}%> class="save_btn long_arrow <%= (cinfo.getOnboardflag() == 5 || cinfo.getOnboardflag() == 6) ? "disable_text proceed_btn" : "" %>" >Proceed <i class="ion ion-ios-arrow-round-forward"></i></a>
                                        </div>
                                    </div> 
                                    <%
                                    }
                                    %>
                                </div>

                            </div>
                        </div> 
                    </div>
                </div>
            </div>
        </div>
        <%@include file ="../footer.jsp"%>

        <div id="onboard_candidate_list_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <h2>ONBOARD CANDIDATE</h2>
                                <div class="row client_position_table req_doc_check">
                                    <ul>

                                        <%   for(int i = 0 ; i < kitlist.size(); i++){
                                                            OnboardingInfo kinfo = (OnboardingInfo)kitlist.get(i);
                                            if (kinfo != null) {
                                        %>
                                        <li>
                                            <div class="mt-checkbox-list">
                                                <label class="mt-checkbox mt-checkbox-outline"> 
                                                    <input type="checkbox" value="<%= kinfo.getDdlValue()%>" id = "onboardingkitIds<%=i%>" name="onboardingkitIds">
                                                    <span></span> 
                                                </label>
                                            </div>
                                            <span class="check_list_text"><%= kinfo.getDdlLabel()%> </span>
                                        </li>

                                        <%
                                                }
                                                    }
                                        %>
                                    </ul>
                                </div>	
                                <div class="row">	
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center">
                                        <a href="javascript:;" class="save_page" onclick="javscript : save();"><img src="../assets/images/save.png"> Save</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="upload_collect_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <h2>UPLOAD COLLECT FORM </h2>
                                <div class="row">

                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4">
                                        <div class="row">
                                            <div class="col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                <label class="form_label">FILE</label>
                                                <input id="upload1" name="attachfile" type="file" onchange="javascript: setClass('1');">
                                                <a href="javascript:;" id="upload_link_1" class="attache_btn attache_btn_white uploaded_img1">
                                                    <i class="fas fa-paperclip"></i> Attach
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>	
                                <div class="row">
                                    <input type='hidden' name='onboarddocId' id='onboarddocId' value=''/>

                                    <%
                                        if(cinfo.getOnboardflag() == 7)
                                        {
                                    %>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center"><a href="javascript:;" class="save_page" onclick = "javascript: uploadedformalitieszip('<%=cinfo.getShortlistId()%>');"><img src="../assets/images/save.png"> Save</a></div>
                                            <%} else {%>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center"><a href="javascript:;" class="save_page" onclick = "javascript: uploadedformalities('<%=cinfo.getShortlistId()%>');"><img src="../assets/images/save.png"> Save</a></div>

                                    <%}%>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="../assets/libs/jquery/jquery.min.js"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>
        <script src="../assets/js/table-responsive.init.js"></script>
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script> 
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        
        <script>
            $(function () {
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });
            });
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