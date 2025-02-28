<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.clientlogin.ClientloginInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="clientlogin" class="com.web.jxp.clientlogin.Clientlogin" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            if (session.getAttribute("MLOGININFO") == null) 
            {
    %>
    <jsp:forward page="/managerindex.jsp"/>
    <%
            }
        int ctp = 1;
        ArrayList list = new ArrayList();
        if (session.getAttribute("CLIENTLOGIN_LIST") != null) {
            list = (ArrayList) session.getAttribute("CLIENTLOGIN_LIST");
        }

        int count = 0;
        int recordsperpage = clientlogin.getCount();
        if(session.getAttribute("COUNT_LIST") != null)
            count = Integer.parseInt((String) session.getAttribute("COUNT_LIST"));
        if (session.getAttribute("CLIENTLOGIN_LIST") != null) {
            list = (ArrayList) session.getAttribute("CLIENTLOGIN_LIST");
        }
        int pageSize = count / recordsperpage;
        if(count % recordsperpage > 0)
            pageSize = pageSize + 1;
        int total = list.size();
        int showsizelist = clientlogin.getCountList("show_size_list");
        int CurrPageNo = 1;

        ClientloginInfo info = null;
    %>
    <head>
        <meta charset="utf-8">
        <title><%= clientlogin.getMainPath("title") != null ? clientlogin.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/time/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/drop/dropzone.min.css" rel="stylesheet" type="text/css" />

        <link href="../assets/filter/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/filter/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/clientlogin.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback ocs_clientlogin vertical-collpsed crew_user bg_image">
    <html:form action="/clientlogin/ClientloginAction.do" onsubmit="return false;">
        
        <html:hidden property="jobpostId"/>
        <div id="layout-wrapper">
            <%@include file="../header_clientlogin.jsp" %>
            <div class="main-content">
                <div class="page-content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 heading_title"><h1><a href="javascript:;"><i class="ion ion-ios-arrow-round-back"></i></a>  Client Login</h1></div>
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background index_list">
                                    <div class="row">
                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 well_feed_page">
                                            <div class="row">
                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 feed_list_1 ">
                                                    <div class="row">
                                                        <div class="col-xl-3 col-lg-3 col-md-4 col-sm-4 col-12">
                                                            <div class="row">
                                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 field_ic mb_field_15">
                                                                    <html:text property ="search" styleId="example-text-input" styleClass="form-control" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {this.removeAttribute('readonly'); this.blur(); this.focus();}"/>
                                                                        <a href="javascript: searchFormAjax('s','-1');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-lg-2 col-md-3 col-sm-4 col-12">
                                                            <div class="row">
                                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 mb_field_15">
                                                                    <html:select property="clientIdIndex" styleId="clientIdIndex" styleClass="form-select" onchange="javascript: setAssetDDL();" >
                                                                        <html:optionsCollection filter="false" property="clients" label="ddlLabel" value="ddlValue">
                                                                        </html:optionsCollection>
                                                                    </html:select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-lg-2 col-md-3 col-sm-4 col-12">
                                                            <div class="row">
                                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 mb_field_15">
                                                                    <html:select property="assetIdIndex" styleId="assetIdIndex" styleClass="form-select" onchange="javascript: setPositionDDL();">
                                                                        <html:optionsCollection filter="false" property="assets" label="ddlLabel" value="ddlValue">
                                                                        </html:optionsCollection>
                                                                    </html:select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-3 col-lg-3 col-md-3 col-sm-4 col-12">
                                                            <div class="row">
                                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 mb_field_15">
                                                                    <html:select property="positionIndex" styleId="positionIndex" styleClass="form-select" onchange="javascript: searchFormAjax('s', '-1');" >
                                                                        <html:optionsCollection filter="false" property="positions" label="ddlLabel" value="ddlValue">
                                                                        </html:optionsCollection>
                                                                    </html:select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-2 col-lg-2 col-md-3 col-sm-4 col-12 mb_field_15">
                                                            <a href="javascript: resetFilter();" class="reset_btn mr_5"><img src="../assets/images/refresh.png"> Reset Filters</a>
                                                        </div>

                                                    </div>
                                                </div>

                                            </div>
                                        </div>

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
                                        <div class="col-lg-12">
                                            <div class="table-rep-plugin sort_table">
                                                <div class="table-responsive mb-0 ellipse_code" data-bs-pattern="priority-columns">            
                                            <ul class="comp_list">
                                                <li class="ref_no_label">Ref. No.</li>
                                                <li class="posted_on_label">Posted On</li>
                                                <li class="client_asset_label">Client - Asset</li>
                                                <li class="position_rank_label">Position - Rank</li>
                                                <li class="client_status_label">Status</li>
                                                <li class="feedback_filter_label">
                                                    <div class="toggled-off_02 usefool_tool">
                                                        <div class="toggle-title_02">
<!--                                                            <img src="../assets/images/filter.png" class="fa-angle-left">
                                                            <img src="../assets/images/filter_up.png" class="fa-angle-right">-->
                                                        </div>
                                                        <div class="toggle-content">
                                                            <ul>
                                                                <li><a href="javascript:;"><span class="round_circle circle_exipry"></span> Pending</a></li>
                                                                <li><a href="javascript:;"><span class="round_circle circle_complete"></span> Complete</a></li>
                                                            </ul>
                                                        </div>
                                                    </div>	
                                                </li>

                                                <li class="accepted_label text-center">Accepted</li>

                                            </ul>
                                            
                                            <ul class="comp_list_detail">
<%
                                            for (int i = 0; i < list.size(); i++) 
                                            {
                                                info = (ClientloginInfo) list.get(i);
                                                if(info != null)
                                                {
%>
                                                        <li class="hand_cursor">
                                                            <div class="comp_list_main">
                                                                <ul>

                                                                    <li class="ref_no_label" onclick="javascript: view('<%= info.getJobpostId()%>');">
                                                                        <span class="mob_show label_title">Ref. No.</span> 
                                                                        <span class="value_record" ><%= clientlogin.changeNum( info.getJobpostId(),6) %></span>
                                                                    </li>
                                                                    <li class="posted_on_label" onclick="javascript: view('<%= info.getJobpostId()%>');">
                                                                        <span class="mob_show label_title">Posted On</span> 
                                                                        <span class="value_record" ><%= info.getDate() != null ? info.getDate() : "" %></span>
                                                                    </li>
                                                                    <li class="client_asset_label" onclick="javascript: view('<%= info.getJobpostId()%>');">
                                                                        <span class="mob_show label_title">Client - Asset</span> 
                                                                        <span class="value_record" ><%= info.getClientName() != null ? info.getClientName() : ""%></span>
                                                                    </li>
                                                                    <li class="position_rank_label" onclick="javascript: view('<%= info.getJobpostId()%>');">
                                                                        <span class="mob_show label_title">Position - Rank</span> 
                                                                        <span class="value_record" ><%= info.getPositionname() != null ? info.getPositionname() : ""%></span>
                                                                    </li>
                                                                    <li class="client_status_label" onclick="javascript: view('<%= info.getJobpostId()%>');">
                                                                        <span class="mob_show label_title">Status</span> 
                                                                        <span class="round_circle circle_exipry"></span> 
                                                                        <span class="value_record" ><%= info.getStVal() != null ? info.getStVal() : ""%></span>
                                                                    </li>

                                                                    <li class="feedback_filter_label mob_colum_hide">&nbsp;</li>
                                                                    <li class="accepted_label text-center" onclick="javascript: view('<%= info.getJobpostId()%>');">
                                                                        <span class="mob_show label_title">Accepted</span> 
                                                                        <a href="javascript:;" class="value_record que_number"><%= info.getSelcount()%></a> / <a href="javascript:;" class="value_record que_number"><%= info.getOpening()%></a>
                                                                    </li>
                                                                </ul>
                                                            </div>
                                                        </li>
<%
                                                }
                                            }
%>


                                            </ul>

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
                    </div>
                    
                    
                    
                                    
                </div>
            </div>
        </div>
        <!-- END layout-wrapper -->
        

        <!-- JAVASCRIPT -->
        <script src="../assets/filter/jquery.min.js"></script>	
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/js/bootstrap-multiselect.js"></script>
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/time/bootstrap-timepicker.min.js" type="text/javascript"></script>
        <script src="../assets/filter/select2.full.min.js" type="text/javascript"></script>
        <script src="../assets/filter/menu-app.js"></script>
        <script src="../assets/filter/app.min.js"></script> 
        <script src="../assets/filter/components-select2.min.js" type="text/javascript"></script>
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>

        <script src="../assets/js/sweetalert2.min.js"></script>
        
        <script>
            // toggle class show hide text section
            $(document).on('click', '.toggle-title', function () {
                $(this).parent()
                        .toggleClass('toggled-on')
                        .toggleClass('toggled-off');
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

        <script>
            //Timepicker
            $(".timepicker-24").timepicker({
                format: 'mm:mm',
                autoclose: !0,
                minuteStep: 5,
                showSeconds: !1,
                showMeridian: !1
            });
        </script>
        
        <script type="text/javascript">
            
            function addLoadEvent(func)
            {
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
            //addLoadEvent(setPositionDDL());
        </script>
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
