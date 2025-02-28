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
           int mtp = 2, submtp = 4, ctp = 26;
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
            ArrayList contract_list = new ArrayList();
            int count = 0;
            int recordsperpage = talentpool.getCount();
            if(session.getAttribute("COUNT_CONTRACT") != null)
                count = Integer.parseInt((String) session.getAttribute("COUNT_CONTRACT"));
            if(session.getAttribute("CONTRACTLIST") != null)
                contract_list  = (ArrayList) session.getAttribute("CONTRACTLIST");
            int pageSize = count / recordsperpage;
            if(count % recordsperpage > 0)
                pageSize = pageSize + 1;

            int contract_list_size  = contract_list.size();
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
        <script type="text/javascript" src="../jsnew/talentpoolgeneratecv.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="doManageContractdetail"/>
        <html:hidden property="doSaveContractdetail"/>
        <html:hidden property="contractdetailId"/>
        <html:hidden property="clientassetId"/>
        <html:hidden property="refId"/>
        <html:hidden property="type"/>
        <html:hidden property="doGeneratedContract"/>
        <html:hidden property="repeatContract"/>
        <html:hidden property="contractApprove"/>
        <html:hidden property="deleteContract"/>
        <html:hidden property="deleteContractFile"/>
        <html:hidden property="fileId"/>        
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
                                            <li><a href="javascript: exporttoexcelnew('27');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
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
                                                                    <h4>CONTRACT DETAILS</h4>
                                                                </div>
                                                               <div class="row">
                                                                
                                                            </div>
                                                                <div class="pull_right float-end">
                                                                    <% if (addper.equals("Y") && dstatus != 4) {%><a href="javascript: addContractdetailForm(-1, -1, 1);" class="add_btn mr_25"><i class="mdi mdi-plus"></i></a><% } %>
                                                                </div>
                                                            </div>
                                                                <div class="col-lg-2 col-md-2 col-sm-3 col-12 float-start">
                                                    <html:select property="clientIdContract" styleId="clientIdContract" styleClass="form-select" onchange="javascript: setAssetContract();" >
                                                        <html:optionsCollection filter="false" property="clients" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-2 col-md-2 col-sm-3 col-12 float-start">
                                                    <html:select property="assetIdContract" styleId="assetIdContract" styleClass="form-select" >
                                                        <html:optionsCollection filter="false" property="clientassets" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-2 col-md-2 col-sm-3 col-12 float-start">
                                                    <html:select property="contractStatus" styleId="contractStatus" styleClass="form-select">
                                                        <html:option value="-1">Select Status</html:option>
                                                        <html:option value="1">New</html:option>
                                                        <html:option value="2">Ongoing</html:option>
                                                        <html:option value="3">Expired</html:option>
                                                        <html:option value="4">In-Active</html:option>
                                                    </html:select>
                                                </div>


                                                <div class="col-lg-2 col-md-2 col-sm-3 col-12 float-start">

                                                    <div class="input-daterange input-group">
                                                        <html:text property="fromDate" styleId="fromDate" styleClass="form-control add-style wesl_dt date-add" />
                                                        <script type="text/javascript">
                                                            document.getElementById("fromDate").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                        </script>
                                                    </div>
                                                </div>
                                                <div class="col-lg-2 col-md-2 col-sm-3 col-12 float-start">

                                                    <div class="input-daterange input-group">
                                                        <html:text property="toDate" styleId="toDate" styleClass="form-control add-style wesl_dt date-add" />
                                                        <script type="text/javascript">
                                                            document.getElementById("toDate").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                        </script>
                                                    </div>
                                                </div>
                                                <div class="col-lg-2 col-md-2 col-sm-3 col-12 float-start">
                                                    <a href="javascript: searchContract('s','-1');" class="go_btn">Go</a>
                                                </div>
                                                <div class='row' id='ajax_contract'>
                                                    <%
                                                        int talentpoolCount = count;
                                                        int nextCount = 0;
                                                        String prev = "";
                                                        String prevclose = "";
                                                        String next = "";
                                                        String nextclose = "";
                                                        int last = 0;
                                                        if (session.getAttribute("NEXTCONTRACT") != null)
                                                        {
                                                            nextCount = Integer.parseInt((String)session.getAttribute("NEXTCONTRACT"));
                                                            CurrPageNo = Integer.parseInt((String)session.getAttribute("NEXTCONTRACTVALUE"));
                                                    %>
                                                    <input type='hidden' name='totalpage' value='<%= pageSize %>'>
                                                    <input type="hidden" name="nextContractValue" value="<%=CurrPageNo%>">
                                                    <%
                                                                if (nextCount == 0 && talentpoolCount > recordsperpage)
                                                                {
                                                                    next = ("<li class='next'><a href=\"javascript: searchContract('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                                                                    nextclose = ("</i></a></li>");
                                                                }
                                                                else if (nextCount > 0 && talentpoolCount >
                                                                    ((nextCount*recordsperpage) + recordsperpage))
                                                                {
                                                                    next = ("<li class='next'><a href=\"javascript: searchContract('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                                                                    nextclose = ("</i></a></li>");
                                                                    prev = ("<li class='prev'><a href=\"javascript: searchContract('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                                    prevclose = ("</i></a></li>");
                                                                }
                                                                else if (nextCount > 0 && talentpoolCount <=
                                                                    ((nextCount*recordsperpage) + recordsperpage))
                                                                {
                                                                    prev = ("<li class='prev'><a href=\"javascript: searchContract('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
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
                                                            if(contract_list_size > 0)
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
                                                                            <li class="wesl_pg_navi"><a href="javascript: searchContract('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                                            <li><a href="javascript: searchContract('n', '<%=ii%>');"><%= ii+1 %></a></li>
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
                                                                            <li class='wesl_pg_navi'><a href="javascript: searchContract('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
                                                                                    <%
                                                                                                                                        }
                                                                                    %>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-12" id="printArea">
                                                                <div class="table-rep-plugin sort_table">
                                                                    <div class="table-responsive mb-0" >        
                                                                        <table id="tech-companies-1" class="table table-striped">
                                                                            <thead>
                                                                                <tr class="first_row">
                                                                                    <th colspan="5" width="%">&nbsp;</th> 
                                                                                    <th colspan="2" width="%" class="text-center"><span><b>Validity</b></span></th>
                                                                                    <th colspan="2" width="%" class="text-center"><span><b>Approval</b></span></th>
                                                                                    <th width="%">&nbsp;</th>
                                                                                    <th width="%">&nbsp;</th>
                                                                                </tr>    
                                                                                <tr>
                                                                                    <th rowspan="2" width="%"><span><b>Position</b></span></th>
                                                                                    <th rowspan="2" width="%"><span><b>Client</b></span></th>
                                                                                    <th rowspan="2" width="%"><span><b>Asset</b></span></th>
                                                                                    <th rowspan="2" width="%"><span><b>Type</b></span></th>
                                                                                    <th rowspan="2" width="%"><span><b>Status</b></span></th>
                                                                                    <th width="%" class="text-center"><span><b>From</b></span></th>                                                                
                                                                                    <th width="%" class="text-center"><span><b>To</b></span></th>  
                                                                                    <th width="%" class="text-center"><span><b>Company</b></span></th>
                                                                                    <th width="%" class="text-center"><span><b>Crew</b></span></th>  
                                                                                    <th width="%" class="text-center">&nbsp;</th> 
                                                                                    <th rowspan="2" width="%" class="text-right"><span><b>Actions</b></span></th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%
                                                                                    if (contract_list_size > 0) {
                                                                                        TalentpoolInfo ainfo;
                                                                                        for (int i = 0; i < contract_list_size; i++) {
                                                                                            ainfo = (TalentpoolInfo) contract_list.get(i);
                                                                                            if (ainfo != null) {
                                                                                %>
                                                                                <tr>
                                                                                    <td><%= ainfo.getPositionname() != null ? ainfo.getPositionname() : ""%></td>
                                                                                    <td><%= ainfo.getClientName() != null ? ainfo.getClientName() : ""%></td>
                                                                                    <td><%= ainfo.getAssetName() != null ? ainfo.getAssetName() : ""%></td>
                                                                                    <td><%= ainfo.getType() == 1 ? "Main" : "Repeated"%></td>
                                                                                    <td><%= ainfo.getStatusValue() != null ? ainfo.getStatusValue() : ""%></td>
                                                                                    <td class="text-center"><%= ainfo.getFromDate() != null ? ainfo.getFromDate() : ""%></td>
                                                                                    <td class="text-center"><%= ainfo.getToDate() != null ? ainfo.getToDate() : ""%></td>
                                                                                    <td class="text-center sel_uns_check">
                                                                                        <% if (approveper.equals("Y") && ainfo.getApproval2() == 0 && ainfo.getStatus() == 1 && dstatus != 4) {%>
                                                                                        
                                                                                        <a href="javascript:;" data-bs-toggle="modal" data-bs-target="#crew_contract_modal" onclick="javascript: getCrewDetails('<%= ainfo.getContractdetailId()%>', '2', '<%=ainfo.getFile1()%>');">
                                                                                            <img src="../assets/images/unselected_check.png"/>
                                                                                        </a>
                                                                                        <%} else if (ainfo.getApproval2() == 1) {%>
                                                                                        <a>
                                                                                            <img src="../assets/images/selected_check.png"/>
                                                                                        </a>
                                                                                        <%}%>        
                                                                                    </td>
                                                                                    <td class="text-center sel_uns_check">
                                                                                        <% if (approveper.equals("Y") && ainfo.getApproval1() == 0 && ainfo.getApproval2() == 1 && ainfo.getStatus() == 1 && dstatus != 4) {%>
                                                                                        <a href="javascript:;" data-bs-toggle="modal" data-bs-target="#crew_contract_modal" onclick="javascript: getCrewDetails('<%= ainfo.getContractdetailId()%>', '1','<%=ainfo.getFile1()%>');">
                                                                                            <img src="../assets/images/unselected_check.png"/>
                                                                                        </a>
                                                                                        <%} else if (ainfo.getApproval1() == 1) {%>
                                                                                        <a>
                                                                                            <img src="../assets/images/selected_check.png"/>
                                                                                        </a>
                                                                                        <%}%>        
                                                                                    </td>                                                                                      
                                                                                    <td class="text-center repeat_btn">
                                                                                        <% if (addper.equals("Y") && ainfo.getType() == 1 && ainfo.getStatus() == 1 && ainfo.getApproval1() == 1 && ainfo.getApproval2() == 1 && dstatus != 4) {%>
                                                                                        <a href="javascript: repeatContract('2','<%=ainfo.getContractId()%>', '-1');" class=" mr_15">Repeat</a>
                                                                                        <%}%>
                                                                                    </td>
                                                                                    <td class="action_column">
                                                                                        <%if(ainfo.getApproval1() == 1 || ainfo.getApproval2() == 1){%>
                                                                                        <a class="mr_15" href="javascript:;" data-bs-toggle="modal" data-bs-target="#crew_contractremarks" onclick="javascript: getCrewRemarkDetails('<%= ainfo.getContractdetailId()%>');">
                                                                                            <img src="../assets/images/info.png"/>
                                                                                        </a>
                                                                                        <%}%>
                                                                                        <a href="javascript:;" onclick="javascript: viewContractDoc('<%= ainfo.getContractdetailId()%>', '<%=ainfo.getFile1()%>', '<%=ainfo.getFile2()%>', '<%=ainfo.getFile3()%>');" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_resume_list"><img src="../assets/images/attachment.png"/></a>
                                                                                        <% if (editper.equals("Y") && ainfo.getStatus() == 1 && ainfo.getApproval1() <= 0 && ainfo.getApproval2() <= 0 && dstatus != 4) {%><a href="javascript: modifyContract('<%=ainfo.getType()%>','<%= ainfo.getContractdetailId()%>');" class=" mr_15"><img src="../assets/images/pencil.png"/></a><% } else {%><%}%>

                                                                                        <span class="switch_bth">  
                                                                                            <% if (deleteper.equals("Y") && dstatus != 4) {%>
                                                                                            <div class="form-check form-switch">
                                                                                                <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault_<%=(i)%>" <% if(ainfo.getStatus() == 1 && dstatus != 4){%> checked onclick="javascript: deleteContractForm('<%= ainfo.getContractdetailId()%>', '<%= ainfo.getStatus()%>', '<%=i%>');" <% }%> <% if(ainfo.getStatus() ==2) {%> disabled="true" <% } %>/>
                                                                                            </div>
                                                                                            <%}%>
                                                                                        </span>
                                                                                    </td>
                                                                                </tr>    
                                                                                <%
                                                                                        }
                                                                                    }
                                                                                } else {
                                                                                %>
                                                                                <tr><td colspan='13'>No contract details available.</td></tr>
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
                                                                            <li class="wesl_pg_navi"><a href="javascript: searchContract('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                                            <li><a href="javascript: searchContract('n', '<%=ii%>');"><%= ii+1 %></a></li>
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
                                                                            <li class='wesl_pg_navi'><a href="javascript: searchContract('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
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
                    </div>
                </div>
        <%@include file ="../footer.jsp"%>
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

        <div id="crew_contractremarks" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body" id='crew_contractremarksdata'>

                    </div>
                </div>
            </div>
        </div>
        <div id="mail_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id = 'mailmodal'>

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
                    orientation: "bottom",
                });
            });
        </script>
        <script>
            $(function () {
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });
            });
        </script>
        <script>
            $(function () {
                $("#upload_link_2").on('click', function (e) {
                    e.preventDefault();
                    $("#upload2:hidden").trigger('click');
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
