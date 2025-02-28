<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.shortlisting.ShortlistingInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="shortlisting" class="com.web.jxp.shortlisting.Shortlisting" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 2, submtp = 49, ctp = 2;
            String per = "N", addper = "N", editper = "N", deleteper = "N";
            if (session.getAttribute("LOGININFO") == null) {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
        } else {
            UserInfo uinfo = (UserInfo) session.getAttribute("LOGININFO");
            if (uinfo != null) {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
            }
        }

        ShortlistingInfo info = null;
        if (request.getAttribute("SHORTLISTINGINFO") != null) {
            info = (ShortlistingInfo) request.getAttribute("SHORTLISTINGINFO");
        }

        ArrayList search_list = new ArrayList();
        if (session.getAttribute("SEARCHPARAMETER") != null) {
            search_list = (ArrayList) session.getAttribute("SEARCHPARAMETER");
        }
        int total = search_list.size();

        ArrayList candidate_list = new ArrayList();
        if (session.getAttribute("CANDIDATELIST") != null) {
            candidate_list = (ArrayList) session.getAttribute("CANDIDATELIST");
        }
        int total1 = candidate_list.size();

        String thankyou = "no";
        if(request.getAttribute("SHORTLISTINGSAVEMODEL") != null)
        {
            thankyou = (String)request.getAttribute("SHORTLISTINGSAVEMODEL");
            request.removeAttribute("SHORTLISTINGSAVEMODEL");
        }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= shortlisting.getMainPath("title") != null ? shortlisting.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="cache-control" content="no-cache"/>
        <meta http-equiv="pragma" content="no-cache"/>
        <meta http-equiv="expires" content="0"/>
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">

        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">	
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="../autofill/jquery-ui.min.css" />  <!-- Autofill-->

        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/shortlisting.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/shortlisting/ShortlistingAction.do" onsubmit="return false;" styleClass="form-horizontal">
        <html:hidden property="search"/>
        <html:hidden property="doCandSearch"/>
        <html:hidden property="doSave"/>
        <html:hidden property="candidateId"/>
        <html:hidden property="jobpostId"/>
        <html:hidden property="clientId"/>
        <html:hidden property="clientAssetId" />
        <html:hidden property="clientIdIndex" />

        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file ="../header.jsp"%>
            <%@include file ="../sidemenu.jsp"%> 
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow">
                                <a href="javascript:backtoold();"><img src="../assets/images/back-arrow.png"/></a>
                                <span>Shortlisting</span>
                            </div>
                            <div class="float-end">
                                <div class="toggled-off usefool_tool">
                                    <div class="toggle-title">
                                        <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                        <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>
                                    </div>
                                    <div class="toggle-content">
                                        <h4>Useful Tools</h4>
                                        <%@include file ="../shortcutmenu_edit.jsp"%>
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
                                        <div class="col-md-6 com_top_left">
                                            <div class="row d-flex align-items-center">

                                                <div class="col-md-12">
                                                    <div class="input-group bootstrap-touchspin bootstrap-touchspin-injected short_list_search">
                                                        <span class="input-group-btn input-group-prepend search_label">Search Job Post:</span>
                                                        <html:text property="jobpostsearch" styleId="jobpostsearch" styleClass="form-control" maxlength="200" onblur="if (this.value == '') {
                                                                    document.forms[0].jobpostId.value = '0';
                                                                }" readonly="true" onfocus="if (this.hasAttribute('readonly')) {this.removeAttribute('readonly'); this.blur(); this.focus();}"/>
                                                        <a class="search_view" href="javascript: searchFormShortlist();"><img src="../assets/images/view_icon.png"/></a>
                                                        <script type="text/javascript">
                                                            document.getElementById("jobpostsearch").setAttribute('placeholder', 'Search by Reference No / Position-Rank / Client Name / Asset Name');
                                                            document.getElementById("jobpostsearch").setAttribute('autocomplete', 'off');
                                                        </script>
                                                        <span class="input-group-btn input-group-append search_filter">Filter</span>
                                                    </div>
                                                </div>
                                                <div class="toggle-content">
                                                        &nbsp;
                                                    <h6><b>Applied filters</b></h6>
                                                </div>
                                                <div class="col-md-12 pill_box_area">
                                                    <ul id="dsearchparameter">
                                                        <%
                                                            if (total > 0) {
                                                                ShortlistingInfo sinfo;
                                                                for (int i = 0; i < total; i++) {
                                                                    sinfo = (ShortlistingInfo) search_list.get(i);
                                                                    if (sinfo != null) {
                                                                        if (i == 0 || i == (total-1)) {}else{
                                                        %>
                                                        <li><span><%=sinfo.getVal2()%></span> <a href="javascript:getpills('<%=i%>','<%=sinfo.getShowflag()%>');"><i class="ion ion-md-close"></i></a></li>
                                                                <%
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                %>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6 com_top_right">
                                            <div class="row d-flex align-items-center">
                                                <div class="col-md-8 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Posting Date</label></div>
                                                        <div class="col-md-9"><span><%= info != null && (info.getPoston() != null || info.getPoston().equals("")) ? info.getPoston() : "&nbsp;"%></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Position-Rank</label></div>
                                                        <div class="col-md-9"><span><%= info != null && (info.getPosition() != null || info.getPosition().equals("")) ? info.getPosition() : "&nbsp;"%><%= info != null && (info.getGrade() != null || info.getGrade().equals("")) ? " - " + info.getGrade() : "&nbsp;"%></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Client - Asset</label></div>
                                                        <div class="col-md-9"><span><%= info != null && (info.getClient() != null || info.getClient().equals("")) ? info.getClient() : "&nbsp;"%><%= info != null && (info.getClientasset() != null || info.getClientasset().equals("")) ? " - " + info.getClientasset() : "&nbsp;"%></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Education</label></div>
                                                        <div class="col-md-9"><span><%= info != null && (info.getQualification() != null || info.getQualification().equals("")) ? info.getQualification() : "&nbsp;"%></span></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-3"><label>Experience</label></div>
                                                        <div class="col-md-9"><%if (info != null) {%><span><%=info.getMinexp()%>-<%=info.getMaxexp()%> Yrs</span><%}%></div>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <%if (info != null) {%>
                                                    <div class="ref_vie_ope status_area">
                                                        <ul>
                                                            <li class="com_ope_job">Status <span><%=info.getStatusvalue() != null || info.getStatusvalue().equals("") ? info.getStatusvalue() : "&nbsp;"%></span></li>
                                                        </ul>
                                                    </div>
                                                    <%} else {%>&nbsp;<%}%>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_ref_no"><span>Ref. <strong><%if (info != null) {%><%=info.getCode() != null || info.getCode().equals("") ? info.getCode() : "&nbsp;"%><%} else {%>&nbsp;<%}%></strong></span></li>
                                                            <li class="com_view_job"><%if (info != null) {%><a href="javascript: ViewJobpost();"><img src="../assets/images/view.png"/><br/> View Job Post</a><%} else {%>&nbsp;<%}%></li>
                                                            <li class="com_ope_job">Opening <span><%if (info != null) {%><%= info != null ? info.getNoofopening() : "0"%><%} else {%>&nbsp;<%}%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">

                                        <div class="col-lg-6 col-md-6 col-sm-6 col-12">
                                            <div class="row">
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 short_list_area">
                                                    <h2>SEARCH RESULTS</h2>
                                                    <div class="search_short_main">
                                                        <ul id="dcandidate">
                                                            <%
                                                                if (total1 > 0) {
                                                                    ShortlistingInfo cinfo;
                                                                    String strclass="";
                                                                    for (int i = 0; i < total1; i++) {
                                                                        cinfo = (ShortlistingInfo) candidate_list.get(i);
                                                                        if (cinfo != null) {
                                                                            if((i+1)%2==0){
                                                                                strclass="odd_list_2";
                                                                            }else{
                                                                                strclass="odd_list_1";
                                                                            }
                                                            %>
                                                            <li class="<%=strclass%>">
                                                                <div class="search_box">
                                                                    <div class="row">

                                                                        <div class="col-md-10 comp_view">
                                                                            <div class="row">
                                                                                <div class="full_name_ic col-md-12 mb_0">
                                                                                    <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="Full Name"><i class="mdi mdi-account"></i></a>
                                                                                    <span><%= cinfo.getName() != null || cinfo.getName().equals("") ? cinfo.getName() : "&nbsp;"%></span>
                                                                                </div>

                                                                                <div class="posi_rank_ic col-md-12 mb_0">
                                                                                    <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="Position-Rank"><i class="mdi mdi-star-circle"></i></a>
                                                                                    <span><%= cinfo.getPosition() != null || cinfo.getPosition().equals("") ? cinfo.getPosition() : "&nbsp;"%><%= cinfo.getGrade() != null || cinfo.getGrade().equals("") ? " - " + cinfo.getGrade() : "&nbsp;"%></span>
                                                                                </div>

                                                                                <div class="expe_ic col-md-12 mb_0">
                                                                                    <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="Experience"><i class="mdi mdi-lightbulb"></i></a>
                                                                                    <span><%= cinfo.getExp()%> Yrs</span>
                                                                                </div>

                                                                                <div class="gradu_ic col-md-12 mb_0">
                                                                                    <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="Education"><i class="fas fa-graduation-cap"></i></a>
                                                                                    <span><%= cinfo.getDegree() != null || cinfo.getDegree().equals("") ? cinfo.getDegree() : "&nbsp;"%><%= cinfo.getQualification() != null || cinfo.getQualification().equals("") ? " - " + cinfo.getQualification() : "&nbsp;"%></span>
                                                                                </div>

                                                                                <div class="brief_ic col-md-12">
                                                                                    <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="Last Job"><i class="ion ion-ios-briefcase"></i></a>
                                                                                    <span><%= cinfo.getCompany() != null || cinfo.getCompany().equals("") ? cinfo.getCompany() : "&nbsp;"%></span>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-md-2 add_view_area">
                                                                            <div class="row">
                                                                                <div class="search_add_btn"><a href="javascript:;" id="btnadd_<%=cinfo.getCandidateId()%>" onclick="addtoSortedSet('<%=cinfo.getCandidateId()%>', 'ADD')">Add <i class="ion ion-ios-arrow-round-forward"></i></a></div>
                                                                                <div class="search_view_prof com_view_job">
                                                                                    <a href="javascript: viewTalentPool('<%=cinfo.getCandidateId()%>');"><img src="../assets/images/view.png"/><br/> View Profile</a>	
                                                                                </div>
                                                                            </div>
                                                                        </div>

                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <%
                                                                        }
                                                                    }
                                                                }
                                                            %>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>	
                                        </div>
                                        <div class="col-lg-6 col-md-6 col-sm-6 col-12">
                                            <div class="row">
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 short_list_area">
                                                    <h2>SHORTLIST FOR COMPLIANCE CHECKS</h2>
                                                    <div class="search_short_main">
                                                        <ul  id="dsortedcandidate">
                                                            <input type="hidden" name="sortedcount" id="sortedcount" value="0"/>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>	
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                            <a href="javascript:submitShortlistForm();" class="save_btn short_save_btn">Shortlist <i class="ion ion-ios-arrow-round-forward"></i></a>
                                        </div>
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
        <!-- END layout-wrapper -->

        <%@include file ="../footer.jsp"%>
        <div id="thank_you_modal" class="modal fade thank_you_modal blur_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true" onclick="closemodal();"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <h2>Thank You!</h2>
                                <center><img src="../assets/images/thank-you.png"></center>
                                <h3>Candidate(s) Shortlisted</h3>
                                <p>Proceed to check the compliance of candidates </p>
                                <a href="javascript: gotocompliancecheck();" class="msg_button" style="text-decoration: underline;">Compliance Checks</a>
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
        <script src="../autofill/jquery-ui.min.js" type="text/javascript"></script> <!-- autofill-->
        <script src="../assets/js/app.min.js"></script>


        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        <% if(thankyou.equals("yes")){%>
        <script type="text/javascript">
                            $(window).on('load', function () {
                                $('#thank_you_modal').modal('show');
                            });
        </script>
        <%}%>
        <script>
            $(function ()
            {
                $("#jobpostsearch").autocomplete({
                    source: function (request, response) {
                        $.ajax({
                            url: "/jxp/ajax/shortlisting/autofilljobpost.jsp",
                            type: 'post',
                            dataType: "json",
                            data: JSON.stringify({"search": request.term}),
                            success: function (data) {
                                response(data);
                            }
                        });
                    },
                    select: function (event, ui) {
                        //console.log('onHover :: '+JSON.stringify(ui,null,2));					
                        $('#jobpostsearch').val(ui.item.label); // display the selected text
                        $('input[name="jobpostId"]').val(ui.item.value);
                        searchFormShortlist();
                        return false;
                    },
                    focus: function (event, ui)
                    {
                        //console.log('onFocus :: '+JSON.stringify(ui,null,2));					
                        $('#jobpostsearch').val(ui.item.label); // display the selected text
                        $('input[name="jobpostId"]').val(ui.item.value);
                        return false;
                    }
                });
            });
        </script>
        <script>
            // toggle class show hide text section
            $(document).on('click', '.toggle-title', function () {
                $(this).parent()
                        .toggleClass('toggled-on')
                        .toggleClass('toggled-off');
            });
        </script>
        <script>
            $(document).ready(function () {
                $('input').attr('autocomplete', 'off');
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
