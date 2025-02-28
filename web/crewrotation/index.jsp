<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.crewrotation.CrewrotationInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="crewrotation" class="com.web.jxp.crewrotation.Crewrotation" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 2, submtp = 57, allclient = 0, companytype = 0;
            String per = "N", addper = "N", editper = "N", deleteper = "N",approveper="N", assetids = "";
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
                    allclient = uinfo.getAllclient();
                    companytype = uinfo.getCompanytype();
                }
            }
                ArrayList candidate_list = new ArrayList();
                int count = 0;
                int recordsperpage = crewrotation.getCount();
                if(session.getAttribute("COUNT_LIST") != null)
                    count = Integer.parseInt((String) session.getAttribute("COUNT_LIST"));
                if(session.getAttribute("CROTATION_LIST") != null)
                    candidate_list = (ArrayList) session.getAttribute("CROTATION_LIST");
                String file_path = crewrotation.getMainPath("view_candidate_file");
                int pageSize = count / recordsperpage;
                if(count % recordsperpage > 0)
                    pageSize = pageSize + 1;
                int total = candidate_list.size();
                int showsizelist = crewrotation.getCountList("show_size_list");
                int CurrPageNo = 1;
                String message = "", clsmessage = "deleted-msg";
                if (request.getAttribute("MESSAGE") != null)
                {
                    message = (String)request.getAttribute("MESSAGE");
                    request.removeAttribute("MESSAGE");
                }    
                if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                    clsmessage = "updated-msg";
                      
    %>
    <head>
        <meta charset="utf-8">
        <title><%= crewrotation.getMainPath("title") != null ? crewrotation.getMainPath("title") : "" %></title>
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
    <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/crewrotation.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/crewrotation/CrewrotationAction.do" onsubmit="return false;">
        <html:hidden property="doView"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="jobpostId"/>
        <html:hidden property="clientId"/>
        <html:hidden property="clientassetId"/>
        <html:hidden property="statusindex"/>
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <div class="main-content">
                <div class="page-content">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow">
                                <a href="javascript: backtoold();"><img src="../assets/images/back-arrow.png"/></a>
                                <span>Crew Rotation</span>
                            </div>
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
                    <div class="row">
                        <div class="col-md-12 col-xl-12">
                            <div class="search_export_area">
                                <div class="row">
                                    <div class="col-lg-9">
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
                                            <div class="col-lg-3">
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select property="clientIdIndex" styleId="clientIdIndex" styleClass="form-select" onchange="javascript: setAssetDDL();" >
                                                            <html:optionsCollection filter="false" property="clients" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-lg-3">
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select property="assetIdIndex" styleId="assetIdIndex" styleClass="form-select">
                                                            <html:optionsCollection filter="false" property="assets" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-lg-2">
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select property="countryId" styleId="countryId" styleClass="form-select" onchange="javascript: searchFormAjax('s', '-1');" >
                                                            <html:optionsCollection filter="false" property="locations" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-lg-1">
                                                <a href="javascript:searchFormAjax('s','-1');" class="go_btn">Go</a>
                                            </div> 

                                        </div>
                                    </div>
                                    <div class="col-lg-3 text-right">
                                        <a href="javascript: resetFilter();" class="reset_export mr_15"><img src="../assets/images/refresh.png"> Reset Filters</a>
                                        <a href="javascript:;" class="add_user">Advance Filter</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>	
                    <div class="container-fluid">
                        <div class="row">
                            <% if(!message.equals("")) {%><div class="alert <%=clsmessage%> alert-dismissible fade show">
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button><%=message%>
                            </div><% } %>
                        </div>
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background mt_0">
                                    <div class="row" id='ajax_cat'>
                                        <input type="hidden" name="nextDel" value="<%= total %>" />
                                        <%
                                            int candidateCount = count;
                                            int nextCount = 0;
                                            String prev = "";
                                            String prevclose = "";
                                            String next = "";
                                            String nextclose = "";
                                            int last = 0;
                                            if (session.getAttribute("NEXT") != null)
                                            {
                                                nextCount = Integer.parseInt((String)session.getAttribute("NEXT"));
                                                CurrPageNo = Integer.parseInt((String)session.getAttribute("NEXTVALUE"));
                                        %>
                                        <input type='hidden' name='totalpage' value='<%= pageSize %>'>
                                        <input type="hidden" name="nextValue" value="<%=CurrPageNo%>">
                                        <%
                                                if (nextCount == 0 && candidateCount > recordsperpage)
                                                {
                                                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                                                    nextclose = ("</i></a></li>");
                                                }
                                                else if (nextCount > 0 && candidateCount >
                                                    ((nextCount*recordsperpage) + recordsperpage))
                                                {
                                                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                                                    nextclose = ("</i></a></li>");
                                                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                    prevclose = ("</i></a></li>");
                                                }
                                                else if (nextCount > 0 && candidateCount <=
                                                    ((nextCount*recordsperpage) + recordsperpage))
                                                {
                                                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                    prevclose = ("</i></a></li>");
                                                    last = candidateCount;
                                                }
                                                else
                                                {
                                                    recordsperpage = candidateCount;
                                                }
                                            }
                                            else
                                            {
                                                recordsperpage = candidateCount;
                                            }
                                            int test = nextCount;
                                            int noOfPages = 1;
                                            if (recordsperpage > 0)
                                            {
                                                noOfPages = candidateCount / recordsperpage;
                                                if (candidateCount % recordsperpage > 0)
                                                    noOfPages += 1;
                                            }
                                            if(total > 0)
                                            {
                                        %>
                                        <div class="wesl_pagination pagination-mg m_15">
                                            <div class="wesl_pg_rcds">
                                                Page&nbsp;<%=CurrPageNo%> of <%= noOfPages %>, &nbsp;(<%= nextCount*recordsperpage + 1 %> - <%= last > 0?last:nextCount*recordsperpage + recordsperpage %> record(s) of <%=candidateCount%>)                                                
                                            </div>
                                            <div class="wesl_No_pages">
                                                <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                    <ul class="wesl_pagination">
                                                        <%
                                                        if(noOfPages > 1 && CurrPageNo != 1)
                                                        {
                                                        %>
                                                        <li class="wesl_pg_navi"><a href="javascript: searchFormAjax('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
                                                                <%
                                                                }
                                                                %>
                                                                <%= prev %><%= prevclose %> &nbsp;
                                                        <%
                                                        int a1 = test - showsizelist;
                                                        int a2 = test + showsizelist;
                                                        for(int ii = a1; ii <= a2; ii++)
                                                        {
                                                            if(ii >= 0 && ii < noOfPages)
                                                            {
                                                                if(ii == test)
                                                                {
                                                        %>
                                                        <li class="wesl_active"><a href="javascript:;"><%=ii+1 %></a></li>
                                                            <%
                                                                }
                                                                else
                                                                {
                                                            %>
                                                        <li><a href="javascript: searchFormAjax('n', '<%=ii%>');"><%= ii+1 %></a></li>
                                                            <%
                                                                    }
                                                                }
                                                            }
                                                            %>
                                                        &nbsp;<%= next %><%= nextclose %>
                                                        <%
                                                            if (noOfPages > 1 && CurrPageNo != noOfPages)
                                                            {
                                                        %>
                                                        <li class='wesl_pg_navi'><a href="javascript: searchFormAjax('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
                                                                <%
                                                                }
                                                                %>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>                                        
                                        <div class="col-lg-12" id="printArea">
                                            <div class="table-rep-plugin sort_table">
                                                <div class="table-responsive mb-0 ellipse_code">        
                                                    <table id="tech-companies-1" class="table table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th rowspan="2" width="22%">
                                                                    <span><b>Client</b></span>
                                                                    <a href="javascript: sortForm('1', '2');" id="img_1_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortForm('1', '1');" id="img_1_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th rowspan="2" width="22%">
                                                                    <span><b>Asset</b></span>
                                                                    <a href="javascript: sortForm('2', '2');" id="img_2_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortForm('2', '1');" id="img_2_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th rowspan="2" width="11%">
                                                                    <span><b>Country</b></span>
                                                                    <a href="javascript: sortForm('3', '2');" id="img_3_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortForm('3', '1');" id="img_3_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th rowspan="2" width="10%" class="text-center">
                                                                    <span><b>Onshore</b></span>
                                                                </th>
                                                                <th colspan="4" class="text-center"><span><b>Offshore</b></span></th>
                                                                <th rowspan="2" width="5%" class="text-center"><span><b>Total</b></span></th>
                                                                <th rowspan="2" width="10%" class="text-center"><span><b>Action</b></span></th>
                                                            </tr>
                                                            <tr>
                                                                <th width="5%" class="text-center">Total</th>
                                                                <th width="5%" class="text-center">Normal</th>
                                                                <th width="5%" class="text-center">Extended</th>
                                                                <th width="5%" class="text-center">Overstay</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id="sort_id">
                                                            <%
                                                                int status;
                                                                CrewrotationInfo info;
                                                                for (int i = 0; i < total; i++)
                                                                {
                                                                    info = (CrewrotationInfo) candidate_list.get(i);
                                                                    if (info != null) 
                                                                    {

                                                            %>
                                                            <tr  href='javascript: void(0);'  >
                                                                <td class="hand_cursor" href='javascript: void(0);' onclick="javascript: view('<%= info.getClientId()%>', '<%= info.getClientassetId()%>');"><%= info.getClientName() != null ? info.getClientName() : ""%></td>
                                                                <td class="hand_cursor" href='javascript: void(0);' onclick="javascript: view('<%= info.getClientId()%>', '<%= info.getClientassetId()%>');"><%= info.getClientAsset() != null ? info.getClientAsset() : ""%></td>
                                                                <td class="hand_cursor" href='javascript: void(0);' onclick="javascript: view('<%= info.getClientId()%>', '<%= info.getClientassetId()%>');"><%= info.getCountry() != null ? info.getCountry() : ""%></td>
                                                                <td class="assets_list text-center"><a href="javascript: viewStatusIndex('<%= info.getClientId()%>', '<%= info.getClientassetId()%>');" class="onshore_value"><%= info.getStatus1()%></a></td>
                                                                <td class="assets_list text-center"><a href="javascript:;" class="off_total_value"><%= info.getNormal()+info.getDelayed()+info.getExtended()%></a></td>
                                                                <td class="assets_list text-center"><a href="javascript:;" class="off_normal_value"><%= info.getNormal()%></a></td>
                                                                <td class="assets_list text-center"><a href="javascript:;" class="off_extended_value"><%= info.getDelayed()%></a></td>
                                                                <td class="assets_list text-center"><a href="javascript:;" class="off_overstay_value"><%= info.getExtended()%></a></td>
                                                                <td class="assets_list text-center"><a href="javascript:;" class="total_value"><%= info.getStatus1()+info.getNormal()+info.getDelayed()+info.getExtended()%></a></td>
                                                                <td class="text-center">
                                                                    <div class="main-nav">
                                                                        <ul>
                                                                            <li class="drop_down">
                                                                                <a href="javascript:;" class="toggle"><i class="fas fa-ellipsis-v"></i></a>
                                                                                <div class="dropdown_menu">
                                                                                    <div class="dropdown-wrapper">
                                                                                        <div class="category-menu">
                                                                                            <a href="javascript: ViewClient('<%=info.getClientId()%>');">View Client</a>
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
                                                Page&nbsp;<%=CurrPageNo%> of <%= noOfPages %>, &nbsp;(<%= nextCount*recordsperpage + 1 %> - <%= last > 0?last:nextCount*recordsperpage + recordsperpage %> record(s) of <%=candidateCount%>)                                                
                                            </div>
                                            <div class="wesl_No_pages">
                                                <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                    <ul class="wesl_pagination">
                                                        <%
                                                                                                            if(noOfPages > 1 && CurrPageNo != 1)
                                                                                                            {
                                                        %>
                                                        <li class="wesl_pg_navi"><a href="javascript: searchFormAjax('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
                                                                <%
                                                                                                                    }
                                                                %>
                                                                <%= prev %><%= prevclose %> &nbsp;
                                                        <%
                                                                                                            for(int ii = a1; ii <= a2; ii++)
                                                                                                            {
                                                                                                                if(ii >= 0 && ii < noOfPages)
                                                                                                                {
                                                                                                                    if(ii == test)
                                                                                                                    {
                                                        %>
                                                        <li class="wesl_active"><a href="javascript:;"><%=ii+1 %></a></li>
                                                            <%
                                                                                                                        }
                                                                                                                        else
                                                                                                                        {
                                                            %>
                                                        <li><a href="javascript: searchFormAjax('n', '<%=ii%>');"><%= ii+1 %></a></li>
                                                            <%
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                            %>
                                                        &nbsp;<%= next %><%= nextclose %>
                                                        <%
                                                                                                            if (noOfPages > 1 && CurrPageNo != noOfPages)
                                                                                                            {
                                                        %>
                                                        <li class='wesl_pg_navi'><a href="javascript: searchFormAjax('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
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
            </div>
        </div>
        <%@include file ="../footer.jsp"%>

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
        <script>
            $(document).ready(function () {
                $('input').attr('autocomplete', 'off');
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