<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.cassessment.CassessmentInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="cassessment" class="com.web.jxp.cassessment.Cassessment" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 2, submtp = 14;
            String per = "N", addper = "N", editper = "N", deleteper = "N", approveper = "N";
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
                approveper = uinfo.getApproveper() != null ? uinfo.getApproveper() : "N";
            }
        }

        ArrayList cassessment_list = new ArrayList();
        int count = 0;
        int recordsperpage = cassessment.getCount();
        if (session.getAttribute("COUNT_LIST") != null) {
            count = Integer.parseInt((String) session.getAttribute("COUNT_LIST"));
        }
        if (session.getAttribute("CASSESSMENT_LIST") != null) {
            cassessment_list = (ArrayList) session.getAttribute("CASSESSMENT_LIST");
        }
        int pageSize = count / recordsperpage;
        if (count % recordsperpage > 0) {
            pageSize = pageSize + 1;
        }

        int total = cassessment_list.size();
        int showsizelist = cassessment.getCountList("show_size_list");
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

        String thankyou = "no";
            if(request.getAttribute("ASSESSTPP") != null)
            {
                thankyou = (String)request.getAttribute("ASSESSTPP");
                request.removeAttribute("ASSESSTPP");
            }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= cassessment.getMainPath("title") != null ? cassessment.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <!-- Responsive Table css -->
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">

        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/cassessment.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/cassessment/CassessmentAction.do" onsubmit="return false;">
        <html:hidden property="doView"/>
        <html:hidden property="doSave"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="doChange"/>
        <html:hidden property="cassessmentId"/>
        <html:hidden property="candidateId"/>
        <html:hidden property="pAssessmentId"/>
        <html:hidden property="doViewAssessNow"/>
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow">
                                <a href="javascript: backtoold();" ><img src="../assets/images/back-arrow.png"/></a>
                                <span>Assessments</span>
                            </div>
                            <div class="float-end">
                                <%if(per.equals("Y")) {%>
                                <div class="role mr_25">
                                    <label>Select Role:</label>
                                    <html:select property="selectRole" styleId="selectRole" styleClass="form-select select_role" onchange="javascript: setRole();">           
                                        <html:option value="1">Default</html:option>
                                        <html:option value="2">Assessor</html:option>
                                    </html:select>
                                </div>
                                <%}%>
                                <div class="toggled-off usefool_tool">
                                    <div class="toggle-title">
                                        <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                        <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>
                                    </div>
                                    <!-- end toggle-title --->
                                    <div class="toggle-content">
                                        <h4>Useful Tools</h4>
                                        <%@include file ="../shortcutmenu.jsp"%>
                                    </div>
                                </div>
                            </div>
                        </div>	
                    </div>
                    <div class="row">
                        <div class="col-md-12 col-xl-12">
                            <div class="search_export_area">
                                <div class="row">
                                    <div class="col-lg-10">
                                        <div class="row mb-3">
                                            <div class="col-lg-3">
                                                <div class="row">
                                                    <label for="example-text-input" class="col-sm-3 col-form-label">Search:</label>
                                                    <div class="col-sm-9 field_ic">
                                                        <html:text property ="search" styleId="example-text-input" styleClass="form-control" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {this.removeAttribute('readonly'); this.blur(); this.focus();}"/>
                                                        <a href="javascript: searchFormAjax('s','-1');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-2">
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select styleClass="form-select" styleId="assettypeIndex" property="assettypeIndex" onchange="javascript: searchFormAjax('s', '-1');" >
                                                            <html:optionsCollection filter="false" property="assettypes" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-2">
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select property="positionIndex" styleId="positionddl" styleClass="form-select" onchange="javascript: searchFormAjax('s', '-1');" >
                                                            <html:optionsCollection filter="false" property="position" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-2">
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select property="astatusIndex" styleId="assessmentddl" styleClass="form-select" onchange="javascript: searchFormAjax('s', '-1');" >
                                                            <html:option value="-1">Select Assessment Status</html:option>
                                                            <html:option value="1">Unscheduled</html:option>
                                                            <html:option value="2">Scheduled</html:option>
                                                            <html:option value="3">Passed</html:option>
                                                            <html:option value="4">Failed</html:option>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-2">
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select styleClass="form-select" styleId="verifiedIndex" property="vstatusIndex" onchange="javascript: searchFormAjax('s', '-1');" >
                                                            <html:option value="-1">Select Verified</html:option>
                                                            <html:option value="3">Minimum</html:option>
                                                            <html:option value="4">Verified</html:option>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>                                            
                                        </div>
                                    </div>
                                    <div class="col-lg-2 text-right">
                                        <a href="javascript: resetFilter();;" class="reset_export mr_15"><img src="../assets/images/refresh.png"> Reset Filters</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>	
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background mt_0">
                                    <div class="row" id='ajax_cat'>
                                        <input type="hidden" name="nextDel" value="<%= total%>" />
                                        <%
                                            int cassessmentCount = count;
                                            int nextCount = 0;
                                            String prev = "";
                                            String prevclose = "";
                                            String next = "";
                                            String nextclose = "";
                                            int last = 0;
                                            if (session.getAttribute("NEXT") != null) {
                                                nextCount = Integer.parseInt((String) session.getAttribute("NEXT"));
                                                CurrPageNo = Integer.parseInt((String) session.getAttribute("NEXTVALUE"));
                                        %>
                                        <input type='hidden' name='totalpage' value='<%= pageSize%>'>
                                        <input type="hidden" name="nextValue" value="<%=CurrPageNo%>">
                                        <%
                                                if (nextCount == 0 && cassessmentCount > recordsperpage) {
                                                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                                                    nextclose = ("</i></a></li>");
                                                } else if (nextCount > 0 && cassessmentCount
                                                        > ((nextCount * recordsperpage) + recordsperpage)) {
                                                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                                                    nextclose = ("</i></a></li>");
                                                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                    prevclose = ("</i></a></li>");
                                                } else if (nextCount > 0 && cassessmentCount
                                                        <= ((nextCount * recordsperpage) + recordsperpage)) {
                                                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                    prevclose = ("</i></a></li>");
                                                    last = cassessmentCount;
                                                } else {
                                                    recordsperpage = cassessmentCount;
                                                }
                                            } else {
                                                recordsperpage = cassessmentCount;
                                            }
                                            int test = nextCount;
                                            int noOfPages = 1;
                                            if (recordsperpage > 0) {
                                                noOfPages = cassessmentCount / recordsperpage;
                                                if (cassessmentCount % recordsperpage > 0) {
                                                    noOfPages += 1;
                                                }
                                            }
                                            if (total > 0) {
                                        %>
                                        <div class="wesl_pagination pagination-mg m_15">
                                            <div class="wesl_pg_rcds">
                                                Page&nbsp;<%=CurrPageNo%> of <%= noOfPages%>, &nbsp;(<%= nextCount * recordsperpage + 1%> - <%= last > 0 ? last : nextCount * recordsperpage + recordsperpage%> record(s) of <%=cassessmentCount%>)
                                            </div>
                                            <div class="wesl_No_pages">
                                                <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                    <ul class="wesl_pagination">
                                                        <%
                                                            if (noOfPages > 1 && CurrPageNo != 1) {
                                                        %>
                                                        <li class="wesl_pg_navi"><a href="javascript: searchFormAjax('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                        <li><a href="javascript: searchFormAjax('n', '<%=ii%>');"><%= ii + 1%></a></li>
                                                            <%
                                                                        }
                                                                    }
                                                                }
                                                            %>
                                                        &nbsp;<%= next%><%= nextclose%>
                                                        <%
                                                            if (noOfPages > 1 && CurrPageNo != noOfPages) {
                                                        %>
                                                        <li class='wesl_pg_navi'><a href="javascript: searchFormAjax('n','<%=(noOfPages - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
                                                                <%
                                                                    }
                                                                %>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12" id="printArea">
                                            <div class="table-rep-plugin sort_table">
                                                <div class="table-responsive mb-0" data-bs-pattern="priority-columns">
                                                    <table id="tech-companies-1" class="table table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th width="7%">
                                                                    <span><b>ID</b> </span>
                                                                    <a href="javascript: sortForm('1', '2');" id="img_1_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortForm('1', '1');" id="img_1_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th width="18%">
                                                                    <span><b>Name</b></span>
                                                                    <a href="javascript: sortForm('2', '2');" id="img_2_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortForm('2', '1');" id="img_2_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th width="17%">
                                                                    <span><b>Asset Type</b></span>
                                                                    <a href="javascript: sortForm('5', '2');" id="img_5_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortForm('5', '1');" id="img_5_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th width="18%">
                                                                    <span><b>Position-Rank</b></span>
                                                                    <a href="javascript: sortForm('3', '2');" id="img_3_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortForm('3', '1');" id="img_3_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th width="10%">
                                                                    <span><b>Verified On</b></span>
                                                                    <a href="javascript: sortForm('4', '2');" id="img_4_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortForm('4', '1');" id="img_4_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th width="10%"><span><b>Verified</b></span></th>
                                                                <th width="10%"><span><b>Assessment Status</b></span></th>
                                                                <th width="10%" class="text-center"><span><b>Scheduled</b></span></th>
                                                                <th width="6%" class="text-center" id="tech-companies-1-col-8"><span><b>Action</b></span></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id="sort_id">
                                                            <%
                                                                int status;
                                                                CassessmentInfo info;
                                                                for (int i = 0; i < total; i++) {
                                                                    info = (CassessmentInfo) cassessment_list.get(i);
                                                                    if (info != null) {
                                                                        status = info.getStatus();
                                                            %>
                                                            <tr>
                                                                <td><%=  info.getIds() != null && !"".equals(info.getIds()) ? info.getIds() : ""%></td>
                                                                <td><%= info.getName() != null && !"".equals(info.getName()) ? info.getName() : ""%></td>
                                                                <td><%= info.getAssettypeName() != null && !"".equals(info.getAssettypeName()) ? info.getAssettypeName() : ""%></td>
                                                                <td><%= info.getPosition() != null && !"".equals(info.getPosition()) ? info.getPosition() : ""%></td>
                                                                <td><%= info.getVdate() != null && !"".equals(info.getVdate()) ? info.getVdate() : ""%></td>
                                                                <td><%= info.getVflagstatus() != null && !"".equals(info.getVflagstatus()) ? info.getVflagstatus() : ""%></td>
                                                                <td><%= info.getAflagstatus() != null && !"".equals(info.getAflagstatus()) ? info.getAflagstatus() : ""%></td>
                                                                <td class="assets_list text-center"><%if (info.getScheduled() != null && !"".equals(info.getScheduled())) {%><a href='javascript: void(0);'><%=info.getScheduled()%></a><%} else {%>&nbsp;<%}%></td>
                                                                <td class="text-center" data-org-colspan="1" data-columns="tech-companies-1-col-8">
                                                                    <div class="main-nav ass_list">
                                                                        <ul>
                                                                            <li class="drop_down">
                                                                                <a href="javascript:;" class="toggle"><i class="fas fa-ellipsis-v"></i></a>
                                                                                <div class="dropdown_menu">
                                                                                    <div class="dropdown-wrapper">

                                                                                        <div class="category-menu">
                                                                                            <a href="javascript: showDetailAssessNow('<%= info.getCassessmentId()%>');">Assess Now</a>
                                                                                            <a href="javascript: showDetail('<%= info.getCassessmentId()%>');">Schedule Assessments</a>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </li>
                                                                        </ul>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                            <%
                                                                    }
                                                                }
                                                            %>
                                                        </tbody>
                                                    </table>
                                                </div>	
                                            </div>			


                                        </div>
                                        <div class="wesl_pagination pagination-mg mt_15">
                                            <div class="wesl_pg_rcds">
                                                Page&nbsp;<%=CurrPageNo%> of <%= noOfPages%>, &nbsp;(<%= nextCount * recordsperpage + 1%> - <%= last > 0 ? last : nextCount * recordsperpage + recordsperpage%> record(s) of <%=cassessmentCount%>)
                                            </div>
                                            <div class="wesl_No_pages">
                                                <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                    <ul class="wesl_pagination">
                                                        <%
                                                            if (noOfPages > 1 && CurrPageNo != 1) {
                                                        %>
                                                        <li class="wesl_pg_navi"><a href="javascript: searchFormAjax('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                        <li><a href="javascript: searchFormAjax('n', '<%=ii%>');"><%= ii + 1%></a></li>
                                                            <%
                                                                        }
                                                                    }
                                                                }
                                                            %>
                                                        &nbsp;<%= next%><%= nextclose%>
                                                        <%
                                                            if (noOfPages > 1 && CurrPageNo != noOfPages) {
                                                        %>
                                                        <li class='wesl_pg_navi'><a href="javascript: searchFormAjax('n','<%=(noOfPages - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
                                                                <%
                                                                    }
                                                                %>
                                                    </ul>
                                                </div>
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
                <!-- End Page-content -->
            </div>
            <!-- end main content-->
        </div>
        <!-- END layout-wrapper -->
        <%@include file ="../footer.jsp"%>
        <div id="thank_you_modal" class="modal fade thank_you_modal blur_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <h2>Thank You!</h2>
                                    <center><img src="../assets/images/thank-you.png"></center>
                                    <h3>Quick Assessment Complete</h3>
                                    <p>This candidate profile will now be visible and OCS Certified in Talent Pool Profile</p>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            

        <!-- JAVASCRIPT -->
        <script src="../assets/libs/jquery/jquery.min.js"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>
        <!-- Responsive Table js -->
        <script src="../assets/js/rwd-table.min.js"></script>
        <script src="../assets/js/table-responsive.init.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        <% if(thankyou.equals("yes")){%>
            <script type="text/javascript">
                $(window).on('load', function() {
                    $('#thank_you_modal').modal('show');
                });
            </script>
            <%}%>
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
