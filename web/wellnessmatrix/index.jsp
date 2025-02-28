<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.wellnessmatrix.WellnessmatrixInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="wellnessmatrix" class="com.web.jxp.wellnessmatrix.Wellnessmatrix" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 72;
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
            ArrayList wellnessmatrix_list = new ArrayList();
            int count = 0;
            int recordsperpage = wellnessmatrix.getCount();
            if(session.getAttribute("COUNT_LIST") != null)
                count = Integer.parseInt((String) session.getAttribute("COUNT_LIST"));
            if(session.getAttribute("WELLMATRIX_LIST") != null)
                wellnessmatrix_list = (ArrayList) session.getAttribute("WELLMATRIX_LIST");
            int pageSize = count / recordsperpage;
            if(count % recordsperpage > 0)
                pageSize = pageSize + 1;

            int total = wellnessmatrix_list.size();
            int showsizelist = wellnessmatrix.getCountList("show_size_list");
            int CurrPageNo = 1;

            String message = "", clsmessage = "deleted-msg";
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }
            if(message.toLowerCase().contains("success"))
            {
                    message = "";
            }
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";
    %>
    <head>
        <meta charset="utf-8">
        <title><%= wellnessmatrix.getMainPath("title") != null ? wellnessmatrix.getMainPath("title") : "" %></title>
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
        <script type="text/javascript" src="../jsnew/wellnessmatrix.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/wellnessmatrix/WellnessmatrixAction.do" onsubmit="return false;">
            <html:hidden property="doAdd"/>
            <html:hidden property="doModify"/>
            <html:hidden property="doView"/>    
            <html:hidden property="wellnessmatrixId"/>
            <html:hidden property="clientId"/>
            <html:hidden property="assetId"/>
            <html:hidden property="type"/>
            
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
                                    <a href="javascript: backtoold();"><img src="../assets/images/back-arrow.png"/></a>
                                    <span>Wellness Matrix</span>
                                </div>
                                
                                <div class="float-end">
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
                                                <li><a href="javascript: exporttoexcel('1');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
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
                                    <div class="col-lg-9">
                                        <div class="row mb-3">
                                            
                                            <div class="col-lg-4">
                                                <div class="row">
                                                    <label for="example-text-input" class="col-sm-3 col-form-label text-right">Client:</label>
                                                    <div class="col-sm-9 field_ic">
                                                        <html:select property="clientIdIndex" styleId="clientIdIndex" styleClass="form-select" onchange="javascript: setAssetDDL();" >
                                                            <html:optionsCollection filter="false" property="clients" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-lg-4">
                                                <div class="row">
                                                    <label for="example-text-input" class="col-sm-3 col-form-label text-right">Asset:</label>
                                                    <div class="col-sm-9 field_ic">
                                                        <html:select property="assetIdIndex" styleId="assetIdIndex" styleClass="form-select" onchange="javascript: searchFormAjax('s', '-1');">
                                                            <html:optionsCollection filter="false" property="assets" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>
                                                
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                                    <div class="float-end">
                                                    <a href="javascript: resetFilter();" class="reset_export mr_15"><img src="../assets/images/refresh.png"/> Reset Filters</a>
                                                </div></div>
                                        
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
                                                                                    int wellnessmatrixCount = count;
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
                                                                                        if (nextCount == 0 && wellnessmatrixCount > recordsperpage)
                                                                                        {
                                                                                            next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                                                                                            nextclose = ("</i></a></li>");
                                                                                        }
                                                                                        else if (nextCount > 0 && wellnessmatrixCount >
                                                                                            ((nextCount*recordsperpage) + recordsperpage))
                                                                                        {
                                                                                            next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                                                                                            nextclose = ("</i></a></li>");
                                                                                            prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                                                            prevclose = ("</i></a></li>");
                                                                                        }
                                                                                        else if (nextCount > 0 && wellnessmatrixCount <=
                                                                                            ((nextCount*recordsperpage) + recordsperpage))
                                                                                        {
                                                                                            prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                                                            prevclose = ("</i></a></li>");
                                                                                            last = wellnessmatrixCount;
                                                                                        }
                                                                                        else
                                                                                        {
                                                                                            recordsperpage = wellnessmatrixCount;
                                                                                        }
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        recordsperpage = wellnessmatrixCount;
                                                                                    }
                                                                                    int test = nextCount;
                                                                                    int noOfPages = 1;
                                                                                    if (recordsperpage > 0)
                                                                                    {
                                                                                        noOfPages = wellnessmatrixCount / recordsperpage;
                                                                                        if (wellnessmatrixCount % recordsperpage > 0)
                                                                                            noOfPages += 1;
                                                                                    }
                                                                                    if(total > 0)
                                                                                    {
                                            %>
                                            <div class="wesl_pagination pagination-mg m_15">
                                                <div class="wesl_pg_rcds">
                                                    Page&nbsp;<%=CurrPageNo%> of <%= noOfPages %>, &nbsp;(<%= nextCount*recordsperpage + 1 %> - <%= last > 0?last:nextCount*recordsperpage + recordsperpage %> record(s) of <%=wellnessmatrixCount%>)                                                
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
                                                    <div class="table-responsive mb-0" data-bs-pattern="priority-columns">        
                                                        <table id="tech-companies-1" class="table table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <th width="35%">
                                                                        <span><b>Client</b></span>
                                                                        <a href="javascript: sortForm('1', '2');" id="img_1_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                        <a href="javascript: sortForm('1', '1');" id="img_1_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                    </th>

                                                                    <th width="35%">
                                                                        <span><b>Asset</b></span>
                                                                        <a href="javascript: sortForm('2', '2');" id="img_2_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                        <a href="javascript: sortForm('2', '1');" id="img_2_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                    </th>
                                                                    <th width="13%" class="text-center">
                                                                        <span><b>Positions</b></span>
                                                                        <a href="javascript: sortForm('3', '2');" id="img_3_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                        <a href="javascript: sortForm('3', '1');" id="img_3_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                    </th>
                                                                    <th width="10%" class="text-right">
                                                                        <span><b>Actions</b></span>
                                                                    </th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="sort_id">
<%
                                                                int status;
                                                                WellnessmatrixInfo info;
                                                                for (int i = 0; i < total; i++)
                                                                {
                                                                    info = (WellnessmatrixInfo) wellnessmatrix_list.get(i);
                                                                    if (info != null) 
                                                                    {
                                                                        status = info.getStatus();
%>
                                                                <tr class='hand_cursor'>
                                                                    <td class="hand_cursor" onclick="javascript: showDetail('<%= info.getClientId()%>','<%= info.getAssetId()%>');"><%= info.getClientName() != null ? info.getClientName() : ""%></td>
                                                                    <td class="hand_cursor" onclick="javascript: showDetail('<%= info.getClientId()%>','<%= info.getAssetId()%>');"><%= info.getClientAssetName() != null ? info.getClientAssetName() : ""%></td>
                                                                    <td class="hand_cursor assets_list text-center"><a href='javascript: void(0);' onclick="javascript: showDetail('<%= info.getClientId()%>','<%= info.getAssetId()%>');"><%= info.getPcount() %></a></td>
                                                                    <td>
                                                                        <% if (editper.equals("Y")) {%><a href="javascript: showDetail('<%= info.getClientId()%>','<%= info.getAssetId()%>');" class="edit_mode float-end mr_15"><img src="../assets/images/pencil.png"/><% } %>
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
                                                    Page&nbsp;<%=CurrPageNo%> of <%= noOfPages %>, &nbsp;(<%= nextCount*recordsperpage + 1 %> - <%= last > 0?last:nextCount*recordsperpage + recordsperpage %> record(s) of <%=wellnessmatrixCount%>)                                                
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