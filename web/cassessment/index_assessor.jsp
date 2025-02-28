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

        ArrayList assessor_list = new ArrayList();
        
        
        if (session.getAttribute("ASSESSOR_LIST") != null) {
            assessor_list = (ArrayList) session.getAttribute("ASSESSOR_LIST");
        }
        int count = 0;
        int recordsperpage = cassessment.getCount();
        if (session.getAttribute("COUNT_LISTA") != null) {
            count = Integer.parseInt((String) session.getAttribute("COUNT_LISTA"));
        }
        int pageSize = count / recordsperpage;
        if (count % recordsperpage > 0) {
            pageSize = pageSize + 1;
        }
        int showsizelist = cassessment.getCountList("show_size_list");
        int CurrPageNo = 1;

        int total = assessor_list.size();

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
        <html:hidden property="doAssessorView"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="doChange"/>
        <html:hidden property="doCancelAssessor"/>
        <html:hidden property="cassessmentId"/>
        <html:hidden property="candidateId"/>
        <html:hidden property="pAssessmentId"/>
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
                                        <ul>
                                            <li><a href="javascript:openinnewtab();"><img src="../assets/images/open-in-new-tab.png"/> Open in New Tab</a></li>
                                            <li><a href="javascript:printPage('printArea');"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                            <li><a href="javascript: exporttoexcelassessor();"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                        </ul>
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
                                                        <html:text property ="search" styleId="example-text-input" styleClass="form-control" maxlength="200" onkeypress="javascript: handleKeySearchAssesor(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {this.removeAttribute('readonly'); this.blur(); this.focus();}"/>
                                                        <a href="javascript: searchFormAjaxAssessor('s','-1');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-2">
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select styleClass="form-select" styleId="assettypeIndex" property="assettypeIndex" onchange="javascript: searchFormAjaxAssessor('s', '-1');" >
                                                            <html:optionsCollection filter="false" property="assettypes" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-2">
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select property="passessmentIndex" styleId="passessmentIndex" styleClass="form-select" onchange="javascript: searchFormAjaxAssessor('s', '-1');" >
                                                            <html:optionsCollection filter="false" property="passessments" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-2">
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select property="aPositionIndex" styleId="aPositionIndex" styleClass="form-select" onchange="javascript: searchFormAjaxAssessor('s', '-1');" >
                                                            <html:optionsCollection filter="false" property="aPositions" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-lg-2" <%if(!per.equals("Y")){%> style="display: none"  <%}%> >
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select property="assessorIndex" styleId="assessorIndex" styleClass="form-select" onchange="javascript: searchFormAjaxAssessor('s', '-1');" >
                                                            <html:optionsCollection filter="false" property="assessors" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-2 text-right">
                                        <a href="javascript: resetFilterAssessor();" class="reset_export mr_15"><img src="../assets/images/refresh.png"> Reset Filters</a>                                       
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
                                            if (session.getAttribute("NEXTA") != null) {
                                                nextCount = Integer.parseInt((String) session.getAttribute("NEXTA"));
                                                CurrPageNo = Integer.parseInt((String) session.getAttribute("NEXTVALUEA"));
                                        %>
                                        <input type='hidden' name='totalpage' value='<%= pageSize%>'>
                                        <input type="hidden" name="nextValue" value="<%=CurrPageNo%>">
                                        <%
                                                if (nextCount == 0 && cassessmentCount > recordsperpage) {
                                                    next = ("<li class='next'><a href=\"javascript: searchFormAjaxAssessor('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                                                    nextclose = ("</i></a></li>");
                                                } else if (nextCount > 0 && cassessmentCount
                                                        > ((nextCount * recordsperpage) + recordsperpage)) {
                                                    next = ("<li class='next'><a href=\"javascript: searchFormAjaxAssessor('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                                                    nextclose = ("</i></a></li>");
                                                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjaxAssessor('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                    prevclose = ("</i></a></li>");
                                                } else if (nextCount > 0 && cassessmentCount
                                                        <= ((nextCount * recordsperpage) + recordsperpage)) {
                                                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjaxAssessor('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
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
                                                        <li class="wesl_pg_navi"><a href="javascript: searchFormAjaxAssessor('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                        <li><a href="javascript: searchFormAjaxAssessor('n', '<%=ii%>');"><%= ii + 1%></a></li>
                                                            <%
                                                                        }
                                                                    }
                                                                }
                                                            %>
                                                        &nbsp;<%= next%><%= nextclose%>
                                                        <%
                                                            if (noOfPages > 1 && CurrPageNo != noOfPages) {
                                                        %>
                                                        <li class='wesl_pg_navi'><a href="javascript: searchFormAjaxAssessor('n','<%=(noOfPages - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
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
                                                                <th class="text-left" width="9%">
                                                                    <span><b>Date</b> </span>
                                                                    <a href="javascript: sortFormAssessor('1', '2');" id="img_1_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortFormAssessor('1', '1');" id="img_1_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th class="text-left" width="9%">
                                                                    <span><b>Time</b></span>
                                                                    <a href="javascript: sortFormAssessor('2', '2');" id="img_2_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortFormAssessor('2', '1');" id="img_2_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th class="text-left" width="13%">
                                                                    <span><b>Name</b></span>
                                                                    <a href="javascript: sortFormAssessor('3', '2');" id="img_3_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortFormAssessor('3', '1');" id="img_3_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th class="text-left" width="13%">
                                                                    <span><b>Assessment</b></span>
                                                                    <a href="javascript: sortFormAssessor('4', '2');" id="img_4_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortFormAssessor('4', '1');" id="img_4_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th class="text-left" width="13%">
                                                                    <span><b>Asset Type</b></span>
                                                                    <a href="javascript: sortFormAssessor('6', '2');" id="img_4_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortFormAssessor('6', '1');" id="img_4_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th class="text-left" width="14%">
                                                                    <span><b>Position-Rank</b></span>
                                                                    <a href="javascript: sortFormAssessor('5', '2');" id="img_5_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortFormAssessor('5', '1');" id="img_5_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th class="text-left" width="6%"><span><b>Status</b></span></th>
                                                                <th width="7%"  class="text-center" ><span><b>Min Score</b></span></th>
                                                                <th width="8%"><span><b>Assessor</b></span></th>
                                                                <th width="8%" class="text-center"><span><b>Score Obtained</b></span></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id="sort_id">
                                                            <%
                                                                if(total >0){
                                                                CassessmentInfo info;
                                                                for (int i = 0; i < total; i++) {
                                                                    info = (CassessmentInfo) assessor_list.get(i);
                                                                    if (info != null) {
                                                                    int status = info.getStatus();
                                                                    String link = ";";
                                                                    if(status == 1)
                                                                        link = "showAssessorDetail('"+info.getCandidateId()+"','"+info.getCassessmentId()+"', '"+info.getpAssessmentId()+"');";
                                                            %>
                                                            <tr>
                                                                <td class="hand_cursor" href='javascript: void(0);' onclick="javascript:<%=link%>"><%=  info.getDate() != null && !"".equals(info.getDate()) ? info.getDate() : ""%></td>
                                                                <td class="hand_cursor" href='javascript: void(0);' onclick="javascript: <%=link%>"><%= info.getTime() != null && !"".equals(info.getTime()) ? info.getTime() : ""%></td>
                                                                <td class="hand_cursor" href='javascript: void(0);' onclick="javascript: <%=link%>"><%= info.getName() != null && !"".equals(info.getName()) ? info.getName() : ""%></td>
                                                                <td class="hand_cursor" href='javascript: void(0);' onclick="javascript: <%=link%>"><%= info.getAssessmentName() != null && !"".equals(info.getAssessmentName()) ? info.getAssessmentName() : ""%></td>
                                                                <td class="hand_cursor" href='javascript: void(0);' onclick="javascript: <%=link%>"><%= info.getAssettypeName() != null && !"".equals(info.getAssettypeName()) ? info.getAssettypeName() : ""%></td>
                                                                <td class="hand_cursor" href='javascript: void(0);' onclick="javascript: <%=link%>"><%= info.getPosition() != null && !"".equals(info.getPosition()) ? info.getPosition() : ""%></td>
                                                                <td class="hand_cursor" href='javascript: void(0);' onclick="javascript: <%=link%>"><%= info.getAssStatus() != null && !"".equals(info.getAssStatus()) ? info.getAssStatus() : ""%></td>
                                                                <td class="hand_cursor text-center" href='javascript: void(0);' onclick="javascript: <%=link%>"><%= info.getMinScore() %></td>
                                                                <td class="hand_cursor" href='javascript: void(0);' onclick="javascript: <%=link%>"><%= info.getAssessorName() != null && !"".equals(info.getAssessorName()) ? info.getAssessorName() : ""%></td>
                                                                <td class="hand_cursor text-center" href='javascript: void(0);' onclick="javascript: <%=link%>"><% if(status == 1)  { %> - <%} else { %> <span <%if(status == 3) { %> class='red_mark' <%}%> ><%= info.getMarks() %></span> <% }%></td>
                                                            </tr>
                                                            <%
                                                                    }
                                                                }  }
                                                           else
                                                                            {
                                                            %>
                                                            <tr>
                                                                <td colspan="8">No information available.</td>
                                                            </tr>
                                                            <%
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
                                                        <li class="wesl_pg_navi"><a href="javascript: searchFormAjaxAssessor('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                        <li><a href="javascript: searchFormAjaxAssessor('n', '<%=ii%>');"><%= ii + 1%></a></li>
                                                            <%
                                                                        }
                                                                    }
                                                                }
                                                            %>
                                                        &nbsp;<%= next%><%= nextclose%>
                                                        <%
                                                            if (noOfPages > 1 && CurrPageNo != noOfPages) {
                                                        %>
                                                        <li class='wesl_pg_navi'><a href="javascript: searchFormAjaxAssessor('n','<%=(noOfPages - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
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

        <!-- JAVASCRIPT -->
        <script src="../assets/libs/jquery/jquery.min.js"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>
        <!-- Responsive Table js -->
        <script src="../assets/js/rwd-table.min.js"></script>
        <script src="../assets/js/table-responsive.init.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
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
