<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 2, submtp = 4, ctp = 7;
            String per = "N", addper = "N", editper = "N", deleteper = "N";
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
                }
            }
            ArrayList trainingcert_list = new ArrayList();
            int count = 0;
            int recordsperpage = talentpool.getCount();
            if(session.getAttribute("COUNT_CERTLIST") != null)
                count = Integer.parseInt((String) session.getAttribute("COUNT_CERTLIST"));
            if(session.getAttribute("CANDTRAININGCERTLIST") != null)
                trainingcert_list  = (ArrayList) session.getAttribute("CANDTRAININGCERTLIST");
            int pageSize = count / recordsperpage;
            if(count % recordsperpage > 0)
                pageSize = pageSize + 1;

            int trainingcert_list_size  = trainingcert_list.size();
            int showsizelist = talentpool.getCountList("show_size_list");
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
                
                String file_path = talentpool.getMainPath("view_candidate_file");
                
            TalentpoolInfo dinfo = null;
            int dstatus = 0;
            if (session.getAttribute("CANDIDATE_DETAIL") != null) {
                dinfo = (TalentpoolInfo) session.getAttribute("CANDIDATE_DETAIL");
                dstatus = dinfo.getStatus();
            }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= talentpool.getMainPath("title") != null ? talentpool.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/talentpool.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="doaddtrainingcertdetail"/>
        <html:hidden property="doSavetrainingcertdetail"/>
        <html:hidden property="doDeletetrainingcertdetail"/>
        <html:hidden property="trainingandcertId"/>
        <div id="layout-wrapper">
            <%@include file ="../header.jsp"%>
            <%@include file ="../sidemenu.jsp"%>
            <div class="main-content">
                <div class="page-content tab_panel">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start">
                                <a href="javascript:goback();" class="back_arrow">
                                    <img  src="../assets/images/back-arrow.png"/> 
                                    <%@include file ="../talentpool_title.jsp"%>
                                </a>
                            </div>
                            <div class="float-end">
                                <div class="toggled-off usefool_tool">
                                    <div class="toggle-title">
                                        <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                        <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>
                                    </div>
                                    <div class="toggle-content">
                                        <h4>Useful Tools</h4>
                                        <ul>
                                            <li><a href="javascript: openinnewtab();"><img src="../assets/images/open-in-new-tab.png"/> Open in New Tab</a></li>
                                            <li><a href="javascript: printPage('printArea');"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                            <li><a href="javascript: exporttoexcelnew('7');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12 col-xl-12 tab_head_area">
                            <%@include file ="../talentpooltab.jsp"%>
                        </div>
                    </div>
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background">
                                    <div class="row d-none1">
                                        <div class="col-lg-12">
                                            <% if(!message.equals("")) {%><div class="alert <%=clsmessage%> alert-dismissible fade show">
                                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button><%=message%>
                                            </div><% } %>
                                            <div class="tab-content">
                                                <div class="tab-pane active">
                                                    <div class="m_30 mt_30">
                                                        <div class="row">
                                                            <div class="main-heading mb_10">
                                                                <div class="add-btn">
                                                                    <h4>TRAINING, CERTIFICATION & SAFETY COURSES</h4>
                                                                </div>
                                                               <div class="row">
                                                                <div class="col-sm-9 field_ic">
                                                                    <html:select property="courseIndex" styleId="courseIndex" styleClass="form-select" onchange="javascript: searchCertificate('s','-1');">
                                                                        <html:optionsCollection filter="false" property="coursenames" label="ddlLabel" value="ddlValue">
                                                                        </html:optionsCollection>
                                                                    </html:select>
                                                                </div>
                                                            </div>
                                                                <div class="pull_right float-end">
                                                                    <% if(addper.equals("Y") && dstatus != 4) {%> <a href="javascript: modifytrainingcertdetailForm(-1);" class="add_btn"><i class="mdi mdi-plus"></i></a> <% } %>
                                                                </div>
                                                            </div>
                                                            <div class='row' id='ajax_cat'>
                                                                                    <%
                                                                                        int talentpoolCount = count;
                                                                                        int nextCount = 0;
                                                                                        String prev = "";
                                                                                        String prevclose = "";
                                                                                        String next = "";
                                                                                        String nextclose = "";
                                                                                        int last = 0;
                                                                                        if (session.getAttribute("NEXTCERT") != null)
                                                                                        {
                                                                                            nextCount = Integer.parseInt((String)session.getAttribute("NEXTCERT"));
                                                                                            CurrPageNo = Integer.parseInt((String)session.getAttribute("NEXTCERTVALUE"));
                                                                                    %>
                                                                                    <input type='hidden' name='totalpage' value='<%= pageSize %>'>
                                                                                    <input type="hidden" name="nextCertificateValue" value="<%=CurrPageNo%>">
                                                                                    <%
                                                                                                if (nextCount == 0 && talentpoolCount > recordsperpage)
                                                                                                {
                                                                                                    next = ("<li class='next'><a href=\"javascript: searchCertificate('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                                                                                                    nextclose = ("</i></a></li>");
                                                                                                }
                                                                                                else if (nextCount > 0 && talentpoolCount >
                                                                                                    ((nextCount*recordsperpage) + recordsperpage))
                                                                                                {
                                                                                                    next = ("<li class='next'><a href=\"javascript: searchCertificate('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                                                                                                    nextclose = ("</i></a></li>");
                                                                                                    prev = ("<li class='prev'><a href=\"javascript: searchCertificate('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                                                                    prevclose = ("</i></a></li>");
                                                                                                }
                                                                                                else if (nextCount > 0 && talentpoolCount <=
                                                                                                    ((nextCount*recordsperpage) + recordsperpage))
                                                                                                {
                                                                                                    prev = ("<li class='prev'><a href=\"javascript: searchCertificate('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                                                                    prevclose = ("</i></a></li>");
                                                                                                    last = talentpoolCount;
                                                                                                }
                                                                                                else
                                                                                                {
                                                                                                    recordsperpage = talentpoolCount;
                                                                                                }
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                                recordsperpage = talentpoolCount;
                                                                                            }
                                                                                            int test = nextCount;
                                                                                            int noOfPages = 1;
                                                                                            if (recordsperpage > 0)
                                                                                            {
                                                                                                noOfPages = talentpoolCount / recordsperpage;
                                                                                                if (talentpoolCount % recordsperpage > 0)
                                                                                                    noOfPages += 1;
                                                                                            }
                                                                                            if(trainingcert_list_size > 0)
                                                                                            {
                                                                                    %>
                                                    <div class="wesl_pagination pagination-mg m_15">
                                                                <div class="wesl_pg_rcds">
                                                                    Page&nbsp;<%=CurrPageNo%> of <%= noOfPages %>, &nbsp;(<%= nextCount*recordsperpage + 1 %> - <%= last > 0?last:nextCount*recordsperpage + recordsperpage %> record(s) of <%=talentpoolCount%>)                                                
                                                                </div>
                                                                <div class="wesl_No_pages">
                                                                    <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                                        <ul class="wesl_pagination">
                                                                            <%
                                                                                                                                if(noOfPages > 1 && CurrPageNo != 1)
                                                                                                                                {
                                                                            %>
                                                                            <li class="wesl_pg_navi"><a href="javascript: searchCertificate('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                                            <li><a href="javascript: searchCertificate('n', '<%=ii%>');"><%= ii+1 %></a></li>
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
                                                                            <li class='wesl_pg_navi'><a href="javascript: searchCertificate('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
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
                                                                                <th width="20%"><b>Course Name</b></th>
                                                                                <th width="15%"><b>Type</b></th>
                                                                                <th width="20%"><b>Institution, Location</b></th>
                                                                                <th width="10%"><b>Approved By</b></th>
                                                                                <th width="10%"><b>Issue Date</b></th>
                                                                                <th width="10%"><b>Expiry Date</b></th>
                                                                                <th width="15%" class="text-right"><b>Actions</b></th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
    <%
                                                                            if(trainingcert_list_size > 0)
                                                                            {
                                                                                TalentpoolInfo ainfo;
                                                                                String filep, url;
                                                                                for(int i = 0; i < trainingcert_list_size; i++)
                                                                                {
                                                                                    ainfo = (TalentpoolInfo) trainingcert_list.get(i);
                                                                                    if(ainfo != null)
                                                                                    {
                                                                                        filep = ""; url = "";
                                                                                        if(ainfo.getCertifilename() != null && !ainfo.getCertifilename().equals(""))
                                                                                            filep = file_path+ainfo.getCertifilename();
                                                                                        else if(ainfo.getUrl() != null && !ainfo.getUrl().equals(""))
                                                                                            url = ainfo.getUrl();

    %>
                                                                            <tr>
                                                                                <td><%= ainfo.getCoursename() != null ? ainfo.getCoursename() : "" %></td>
                                                                                <td><%= ainfo.getCoursetype() != null ? ainfo.getCoursetype() : "" %></td>
                                                                                <td><%= ainfo.getEduinstitute() != null ? ainfo.getEduinstitute() : "" %></td>
                                                                                <td><%= ainfo.getApprovedby() != null ? ainfo.getApprovedby() : "" %></td>
                                                                                <td><%= ainfo.getDateofissue() != null ? ainfo.getDateofissue() : "" %></td>
                                                                                <td><%= ainfo.getExpirydate() != null ? ainfo.getExpirydate() : "" %></td>
                                                                                <td class="action_column">                                                                                    
                                                                                    <% if(!filep.equals("")) {%><a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=filep %>');"><img src="../assets/images/attachment.png"/> </a><% } else if(!url.equals("")) {%><a href="<%=url%>" class="mr_15" target="_blank"><img src="../assets/images/attachment.png"/> </a><% } else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>
                                                                                    <% if (editper.equals("Y") && ainfo.getStatus() == 1 && dstatus != 4) {%><a href="javascript: modifytrainingcertdetailForm('<%= ainfo.getTrainingandcertId()%>');" class="edit_mode mr_15"><img src="../assets/images/pencil.png"/></a><% }else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>

                                                                                    <span class="switch_bth">
                                                                                        <% if (deleteper.equals("Y") && dstatus != 4) {%>
                                                                                        <div class="form-check form-switch">
                                                                                            <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault_<%=(i)%>" <% if( ainfo.getStatus() == 1){%> checked <% }%> <% if(!editper.equals("Y")) {%> disabled="true" <% } %>  onclick="javascript: deletetrainingcertForm('<%= ainfo.getTrainingandcertId()%>', '<%= ainfo.getStatus()%>');"/>
                                                                                        </div>
                                                                                        <%}%>
                                                                                    </span>
                                                                                </td>
                                                                            </tr>    
<%
                                                                                }
                                                                            }
                                                                        }
                                                                        else
                                                                        {
%>
                                                                            <tr><td colspan='7'>No Training and Certification details available.</td></tr>
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
                                                                        Page&nbsp;<%=CurrPageNo%> of <%= noOfPages %>, &nbsp;(<%= nextCount*recordsperpage + 1 %> - <%= last > 0?last:nextCount*recordsperpage + recordsperpage %> record(s) of <%=talentpoolCount%>)                                                
                                                                    </div>
                                                                    <div class="wesl_No_pages">
                                                                        <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                                            <ul class="wesl_pagination">
                                                                                <%
                                                                                                                        if(noOfPages > 1 && CurrPageNo != 1)
                                                                                                                        {
                                                                                %>
                                                                                <li class="wesl_pg_navi"><a href="javascript: searchCertificate('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                                                <li><a href="javascript: searchCertificate('n', '<%=ii%>');"><%= ii+1 %></a></li>
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
                                                                                <li class='wesl_pg_navi'><a href="javascript: searchCertificate('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
                                                                                        <%
                                                                                                                                            }
                                                                                        %>
                                                                            </ul>
                                                                        </div>
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
                        <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                                        <span class="resume_title">File</span>
                                        <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                                        <a id='diframe' href="" class="down_btn" download=""><img src="../assets/images/download.png"/></a>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <iframe id='iframe' class="doc" src=""></iframe>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <script src="../assets/libs/jquery/jquery.min.js"></script>		
                        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
                        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
                        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
                        <script src="../assets/js/app.js"></script>	
                        <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>
                        <script src="../assets/js/bootstrap-select.min.js"></script>
                        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
                        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
                        <script>
                                                                                                                    $("#iframe").on("load", function () {
                                                                                                                        let head = $("#iframe").contents().find("head");
                                                                                                                        let css = '<style>img{margin: 0px auto;}</style>';
                                                                                                                        $(head).append(css);
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
                            $(function () {
                                $("#upload_link1").on('click', function (e) {
                                    e.preventDefault();
                                    $("#upload1:hidden").trigger('click');
                                });
                            });
                        </script>
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
                            addLoadEvent(setsidemenu('7'));
                            ;
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
