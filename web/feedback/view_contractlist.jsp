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
            int mtp = 2, submtp = 7, ctp = 13;

            if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%
        }
        String file_path = feedback.getMainPath("view_candidate_file");
        ArrayList contract_list = new ArrayList();
        if (session.getAttribute("CONTRACTLIST") != null) {
            contract_list = (ArrayList) session.getAttribute("CONTRACTLIST");
        }
        
        FeedbackInfo ainfo = null;       
        int count = 0;
        int recordsperpage = feedback.getCount();
        if (session.getAttribute("COUNT_CONTRACT") != null) {
            count = Integer.parseInt((String) session.getAttribute("COUNT_CONTRACT"));
        }
        int pageSize = count / recordsperpage;
        if (count % recordsperpage > 0) {
            pageSize = pageSize + 1;
        }

        int total = contract_list.size();
        int showsizelist = feedback.getCountList("show_size_list");
        int CurrPageNo = 1;
        
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
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/feedback.js"></script>        
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed crew_user bg_image">
        <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;" styleClass="form-horizontal"  enctype="multipart/form-data">
        <html:hidden property="candidateId" />
        <html:hidden property="contractApprove" />
        <html:hidden property="type" />
        <html:hidden property="searchContract" />
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div id="layout-wrapper">
                <script src="../assets/js/user-after-login-header.js"></script> 
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content">

                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-12 col-xl-12 heading_title"><h1><a href="javascript:;"><i class="ion ion-ios-arrow-round-back"></i></a>  Crew Contracts</h1></div>
                                <div class="col-md-12 col-xl-12">
                                    <div class="body-background index_list">
                                        <div class="row">
                                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 well_feed_page">
                                                <div class="row">
                                                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 feed_list_1">
                                                        <div class="row">
                                                            <div class="col-xl-3 col-lg-3 col-md-4 col-sm-5 col-12 search_section">
                                                                <div class="row">
                                                                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 field_ic">
                                                                        <html:text property ="search1" styleClass="form-control" styleId="example-text-input" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                               this.removeAttribute('readonly');
                                                                               this.blur();
                                                                               this.focus();
                                                                               }"/>
                                                                    <a href="javascript:;" onclick="searchContract('s', '-1');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-2 col-lg-2 col-md-3 col-sm-3 col-5 field_ic select_dd_section">
                                                               <html:select property="crewClientId" styleId="crewClientId" styleClass="form-select" onchange="javascript: getCrewAsset();" >
                                                                <html:optionsCollection filter="false" property="clients" label="ddlLabel" value="ddlValue">
                                                                </html:optionsCollection>
                                                            </html:select>
                                                            </div>
                                                            <div class="col-xl-2 col-lg-2 col-md-3 col-sm-3 col-5 field_ic">
                                                                <html:select property="crewAssetId" styleId="crewAssetId" styleClass="form-select">
                                                                <html:optionsCollection filter="false" property="assets" label="ddlLabel" value="ddlValue">
                                                                </html:optionsCollection>
                                                            </html:select>
                                                            </div>
                                                            <div class="col-xl-3 col-lg-3 col-md-2 col-sm-4 col-2 date_go_sec">
                                                                <div class="row">
                                                                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                                        <div class="input-daterange input-group input-addon">
                                                                        <html:text property="fromDate1" styleId="wesl_from_dt" styleClass="form-control wesl_from_dt" onfocus="if (this.value == 'DD-MMM-YYYY') {
                                                                                   this.value = '';
                                                                                   }" onblur="if (this.value == '') {
                                                                                   this.value = 'DD-MMM-YYYY';
                                                                                   }"/>
                                                                        <script>
                                                                            document.getElementById("wesl_from_dt").setAttribute("placeholder", "DD-MMM-YYYY");
                                                                            document.getElementById("wesl_from_dt").setAttribute("autocomplete", "off");
                                                                        </script>
                                                                        <div class="input-group-add">
                                                                            <span class="input-group-text">To</span>
                                                                        </div>
                                                                        <html:text property="toDate1" styleId="wesl_to_dt" styleClass="form-control wesl_to_dt" onfocus="if (this.value == 'DD-MMM-YYYY') {
                                                                                   this.value = '';
                                                                                   }" onblur="if (this.value == '') {
                                                                                   this.value = 'DD-MMM-YYYY';
                                                                                   }"/>
                                                                        <script>
                                                                            document.getElementById("wesl_to_dt").setAttribute("placeholder", "DD-MMM-YYYY");
                                                                            document.getElementById("wesl_to_dt").setAttribute("autocomplete", "off");
                                                                        </script>
                                                                    </div>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="col-xl-1 col-lg-2 col-md-2 col-sm-12 col-12 date_go_sec"><a href="javascript: searchContract('s', '-1');" class="go_btn">Go</a></div>
                                                            <div class="col-xl-1 col-lg-1 col-md-1 col-sm-1 col-2 user_filter">
                                                                <div class="row">
                                                                    <div class="col-sm-12 field_ic filter_div">
                                                                        <div class="dropdown">
                                                                            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                                                                <i class="mdi mdi-filter-outline"></i>
                                                                            </button>
                                                                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                                                            <li>
                                                                                <html:text property="fromDateMob" styleId="wesl_from_dt1" styleClass="form-control wesl_from_dt" onfocus="if (this.value == 'DD-MMM-YYYY') {
                                                                                           this.value = '';
                                                                                           }" onblur="if (this.value == '') {
                                                                                           this.value = 'DD-MMM-YYYY';
                                                                                           }"/>
                                                                                <script>
                                                                                    document.getElementById("wesl_from_dt1").setAttribute("placeholder", "DD-MMM-YYYY");
                                                                                    document.getElementById("wesl_from_dt1").setAttribute("autocomplete", "off");
                                                                                </script>
                                                                                <div class="input-group-add">
                                                                                    <span class="input-group-text">To</span>
                                                                                </div>
                                                                                <html:text property="toDateMob" styleId="wesl_to_dt1" styleClass="form-control wesl_to_dt" onfocus="if (this.value == 'DD-MMM-YYYY') {
                                                                                           this.value = '';
                                                                                           }" onblur="if (this.value == '') {
                                                                                           this.value = 'DD-MMM-YYYY';
                                                                                           }"/>
                                                                                <script>
                                                                                    document.getElementById("wesl_to_dt1").setAttribute("placeholder", "DD-MMM-YYYY");
                                                                                    document.getElementById("wesl_to_dt1").setAttribute("autocomplete", "off");
                                                                                </script>
                                                                            </li>
                                                                            <li>
                                                                                <div class="col-xl-12 col-lg-2 col-md-3 col-sm-3 col-12 float-end"><a href="javascript: searchContract('s', '-1');" class="go_btn">Go</a></div>
                                                                            </li>
                                                                        </ul>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>

                                        <div class="" id='ajax_contract'>
                                            <input type="hidden" name="nextCDel" value="<%= total%>" />
                                            <%
                                                int feedbackCount = count;
                                                int nextCount = 0;
                                                String prev = "";
                                                String prevclose = "";
                                                String next = "";
                                                String nextclose = "";
                                                int last = 0;
                                                if (session.getAttribute("NEXTCONTRACT") != null) {
                                                    nextCount = Integer.parseInt((String) session.getAttribute("NEXTCONTRACT"));
                                                    CurrPageNo = Integer.parseInt((String) session.getAttribute("NEXTCONTRACTVALUE"));
                                            %>
                                            <input type='hidden' name='totalpage' value='<%= pageSize%>'>
                                            <input type="hidden" name="nextContractValue" value="<%=CurrPageNo%>">
                                            <%
                                                    if (nextCount == 0 && feedbackCount > recordsperpage) {
                                                        next = ("<li class='next'><a href=\"javascript: searchContract('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                                                        nextclose = ("</i></a></li>");
                                                    } else if (nextCount > 0 && feedbackCount
                                                            > ((nextCount * recordsperpage) + recordsperpage)) {
                                                        next = ("<li class='next'><a href=\"javascript: searchContract('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                                                        nextclose = ("</i></a></li>");
                                                        prev = ("<li class='prev'><a href=\"javascript: searchContract('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                        prevclose = ("</i></a></li>");
                                                    } else if (nextCount > 0 && feedbackCount
                                                            <= ((nextCount * recordsperpage) + recordsperpage)) {
                                                        prev = ("<li class='prev'><a href=\"javascript: searchContract('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                        prevclose = ("</i></a></li>");
                                                        last = feedbackCount;
                                                    } else {
                                                        recordsperpage = feedbackCount;
                                                    }
                                                } else {
                                                    recordsperpage = feedbackCount;
                                                }
                                                int test = nextCount;
                                                int noOfPages = 1;
                                                if (recordsperpage > 0) {
                                                    noOfPages = feedbackCount / recordsperpage;
                                                    if (feedbackCount % recordsperpage > 0) {
                                                        noOfPages += 1;
                                                    }
                                                }
                                                if (total > 0) {
                                            %>

                                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                <div class="wesl_pagination pagination-mg mt_15">
                                                    <div class="wesl_pg_rcds">
                                                        Page&nbsp;<%=CurrPageNo%> of <%= noOfPages%>, &nbsp;(<%= nextCount * recordsperpage + 1%> - <%= last > 0 ? last : nextCount * recordsperpage + recordsperpage%> record(s) of <%=feedbackCount%>)                                                
                                                    </div>
                                                    <div class="wesl_No_pages">
                                                        <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                            <ul class="wesl_pagination">
                                                                <%
                                                                    if (noOfPages > 1 && CurrPageNo != 1) {
                                                                %>
                                                                <li class="wesl_pg_navi"><a href="javascript: searchContract('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
                                                                        <%
                                                                            }
                                                                        %>
                                                                        <%= prev%><%= prevclose%> &nbsp;
                                                                <%
                                                                    int a1 = test - showsizelist;
                                                                    int a2 = test + showsizelist;
                                                                    for (int ii = a1; ii <= a2; ii++) {
                                                                        if (ii >= 0 && ii < noOfPages) {
                                                                            if (ii == test) {
                                                                %>
                                                                <li class="wesl_active"><a href="javascript:;"><%=ii + 1%></a></li>
                                                                    <%
                                                                    } else {
                                                                    %>
                                                                <li><a href="javascript: searchContract('n', '<%=ii%>');"><%= ii + 1%></a></li>
                                                                    <%
                                                                                }
                                                                            }
                                                                        }
                                                                    %>
                                                                &nbsp;<%= next%><%= nextclose%>
                                                                <%
                                                                    if (noOfPages > 1 && CurrPageNo != noOfPages) {
                                                                %>
                                                                <li class='wesl_pg_navi'><a href="javascript: searchContract('n','<%=(noOfPages - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
                                                                        <%
                                                                            }
                                                                        %>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                            
                                                    
                                                </div>
                                                <ul class="comp_list">
                                                    <li class="contracts_name_label thead_font">Contract Name</li>
                                                    <li class="contracts_client_label thead_font">Client</li>
                                                    <li class="contracts_asset_label thead_font">Asset</li>
                                                    <li class="contracts_val_label text-center thead_font">Validity
                                                        <div class="contracts_from_to_label thead_font">
                                                            <span>From</span>
                                                            <span>To</span>
                                                        </div> 
                                                    </li>
                                                    <li class="contracts_acc_date_label text-center thead_font">Acceptance Date</li>
                                                    <li class="contracts_actions_label text-center thead_font">Actions</li>
                                                </ul>
                                                <ul class="comp_list_detail ">
                                                <%
                                                        if (total > 0) {
                                                                
                                                            for (int i = 0; i < total; i++) {
                                                                ainfo = (FeedbackInfo) contract_list.get(i);
                                                                if (ainfo != null) {
                                                %>
                                                <li>
                                                    <div class="comp_list_main">

                                                        <ul>
                                                            <li class="contracts_name_label mob_mb_15 contr_ord_list_1">
                                                                <span class="value_record"><%= ainfo.getContract() != null ? ainfo.getContract() : "" %></span>
                                                            </li>
                                                            <li class="contracts_client_label mob_mb_15 contr_ord_list_2">
                                                                <span class="mob_show label_title">Client</span> 
                                                                <span class="value_record"><%= ainfo.getClientName() != null ? ainfo.getClientName() : "" %></span>
                                                            </li>
                                                            <li class="contracts_client_label mob_mb_15 contr_ord_list_3">
                                                                <span class="mob_show label_title">Asset</span> 
                                                                <span class="value_record"><%= ainfo.getAssetName() != null ? ainfo.getAssetName() : "" %></span>
                                                            </li>
                                                            <li class="contracts_val_label mob_mb_15 val_from_to text-center contr_ord_list_4">
                                                                <span class="mob_show label_title">Validity From</span> 
                                                                <span class="value_record "><%= ainfo.getFromDate() != null ? ainfo.getFromDate() : "" %></span>
                                                            </li>
                                                            <li class="contracts_val_label mob_mb_15 val_from_to text-center contr_ord_list_5">
                                                                <span class="mob_show label_title">Validity To</span> 
                                                                <span class="value_record"><%= ainfo.getToDate() != null ? ainfo.getToDate() : "" %></span>
                                                            </li>
                                                            <li class="contracts_acc_date_label mob_mb_15 text-center contr_ord_list_6">
                                                                <span class="mob_show label_title">Acceptance Date</span> 
                                                                <span class="value_record"><%= ainfo.getAcceptanceDate() != null ? ainfo.getAcceptanceDate() : "" %></span>
                                                            </li>

                                                            <li class="contracts_actions_label action_list text-center contr_ord_list_7">
                                                                <a href="javascript:;" onclick="javascript: getModalOf('<%=ainfo.getContractdetailId()%>');" class="mr_15" data-bs-toggle="modal" data-bs-target="#user_comp_pending_modal"><img src="../assets/images/view.png"/></a>	
                                                            </li>
                                                        </ul>
                                                    </div>

                                                </li>
                                                <%
                                                       }
                                                       }
                                                       }
                                                %>
                                            </ul>
                                                
                                                <div class="wesl_pagination pagination-mg mt_15">
                                                    <div class="wesl_pg_rcds">
                                                        Page&nbsp;<%=CurrPageNo%> of <%= noOfPages%>, &nbsp;(<%= nextCount * recordsperpage + 1%> - <%= last > 0 ? last : nextCount * recordsperpage + recordsperpage%> record(s) of <%=feedbackCount%>)                                                
                                                    </div>
                                                    <div class="wesl_No_pages">
                                                        <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                            <ul class="wesl_pagination">
                                                                <%
                                                                    if (noOfPages > 1 && CurrPageNo != 1) {
                                                                %>
                                                                <li class="wesl_pg_navi"><a href="javascript: searchContract('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
                                                                        <%
                                                                            }
                                                                        %>
                                                                        <%= prev%><%= prevclose%> &nbsp;
                                                                <%
                                                                    for (int ii = a1; ii <= a2; ii++) {
                                                                        if (ii >= 0 && ii < noOfPages) {
                                                                            if (ii == test) {
                                                                %>
                                                                <li class="wesl_active"><a href="javascript:;"><%=ii + 1%></a></li>
                                                                    <%
                                                                    } else {
                                                                    %>
                                                                <li><a href="javascript: searchContract('n', '<%=ii%>');"><%= ii + 1%></a></li>
                                                                    <%
                                                                                }
                                                                            }
                                                                        }
                                                                    %>
                                                                &nbsp;<%= next%><%= nextclose%>
                                                                <%
                                                                    if (noOfPages > 1 && CurrPageNo != noOfPages) {
                                                                %>
                                                                <li class='wesl_pg_navi'><a href="javascript: searchContract('n','<%=(noOfPages - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
                                                                        <%
                                                                            }
                                                                        %>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                         </div>
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

        <div id="view_resume_list" class="modal fade view_resume_list" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div><a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen r_f_screen">Full Screen</a></div>
                        <div class="row" id="viewexpdiv">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="crew_contract_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body" id='crew_contractdata'>

                    </div>
                </div>
            </div>
        </div>


        <div id="user_comp_pending_modal" class="modal fade parameter_modal large_modal">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body " id="modal_contract">
                        
                                            
                    </div>
                </div>
            </div>
        </div>
        <!-- JAVASCRIPT -->
        <script src="../assets/libs/jquery/jquery.min.js"></script>		
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
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
        <script type="text/javascript">
        jQuery(document).ready(function () {
                $(".kt-selectpicker").selectpicker();
                 $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "yyyy",
                        viewMode: "years", 
                        minViewMode: "years",
                        autoclose: "true",
                        orientation: "bottom"
                });
        });
        </script>
        <script type="text/javascript">
        jQuery(document).ready(function () {
                var date_pick = ".wesl_from_dt, .wesl_to_dt";
                $(date_pick).datepicker({
                        todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                });
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
