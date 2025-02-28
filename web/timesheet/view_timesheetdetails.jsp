<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.timesheet.TimesheetInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="timesheet" class="com.web.jxp.timesheet.Timesheet" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 9, submtp = 76;
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
            ArrayList data_list = new ArrayList();
            TimesheetInfo cinfo = null;
            if(session.getAttribute("BASICINFO") != null)
            {
                cinfo = (TimesheetInfo) session.getAttribute("BASICINFO");            
            }
            TimesheetInfo tsinfo = null;
            int tcount = 0;
            double grandtotal_top = 0;
            if(session.getAttribute("TSINFO") != null)
            {
                tsinfo = (TimesheetInfo) session.getAttribute("TSINFO"); 
                if(tsinfo != null)
                {
                    tcount = tsinfo.getTcount();
                    grandtotal_top = tsinfo.getGrandtotal();
                }
            }
            int activity_size = 0, crewlist_size = 0, pendinglist_size = 0, allowancelist_size = 0, ratelist_size =0, 
            apid = 0, assetId =0 , crewId = 0, datasize = 0, repId =0;
            
            TimesheetInfo info = null;
            ArrayList activitylist = new ArrayList();
            ArrayList crewlist = new ArrayList();
            ArrayList pendinglist = new ArrayList();
            ArrayList allowancelist = new ArrayList();
            ArrayList already_list = new ArrayList();
            ArrayList modify_list = new ArrayList();           
            ArrayList ratelist = new ArrayList();     
            ArrayList spro_list = new ArrayList();
            int modify_list_size = 0;
            String date1= "", date2 = "", aposition = "", adate= "";            
            if(session.getAttribute("DUPT_LIST") != null){
                    data_list = (ArrayList) session.getAttribute("DUPT_LIST");
            }
            datasize = data_list.size();            
            if(tcount <= 0)
            {
                if(session.getAttribute("ACTIVITY_LIST") != null)
                    activitylist = (ArrayList) session.getAttribute("ACTIVITY_LIST");        
                activity_size = activitylist.size();
                if(activity_size > 0)
                {
                    for(int i = 0; i < activity_size; i++)
                    {
                        TimesheetInfo ainfo = (TimesheetInfo)  activitylist.get(i);
                        if (ainfo != null) 
                        {
                            date1 = ainfo.getFromDate() != null ? ainfo.getFromDate() : "";
                            date2 = ainfo.getToDate() != null ? ainfo.getToDate() : "";
                        }
                    }                                                            
                }               
                        
                if(session.getAttribute("CREW_LIST") != null)
                    crewlist = (ArrayList) session.getAttribute("CREW_LIST");        
                crewlist_size = crewlist.size();
                if(crewlist_size > 0)
                {
                    for(int i = 0; i < crewlist_size; i++)
                    {
                        TimesheetInfo crinfo = (TimesheetInfo)  crewlist.get(i);
                        if (crinfo != null) 
                        {
                            assetId = crinfo.getAssetId();                            
                            crewId = crinfo.getCrewrotationId();                            
                        }
                    }                                                            
                }

                //Ratelist
                if(session.getAttribute("CREWRATE_LIST") != null)
                    ratelist = (ArrayList) session.getAttribute("CREWRATE_LIST");
                ratelist_size = ratelist.size();
                
                if(session.getAttribute("PENDING_LIST") != null)
                    pendinglist = (ArrayList) session.getAttribute("PENDING_LIST");        
                pendinglist_size = pendinglist.size();

                 if(session.getAttribute("ALLOWANCE_LIST") != null)
                    allowancelist = (ArrayList) session.getAttribute("ALLOWANCE_LIST");        
                allowancelist_size = allowancelist.size();
                
                if(session.getAttribute("SBYPRO_LIST") != null)
                    spro_list = (ArrayList) session.getAttribute("SBYPRO_LIST");
            }
            else
            {
                if(session.getAttribute("MODIFY_LIST") != null)
                {
                    modify_list = (ArrayList) session.getAttribute("MODIFY_LIST");   
                    modify_list_size = modify_list.size();
                }
            }
        
            String thankyou = "no";
            if (request.getAttribute("THANKYOU") != null) {
                thankyou = (String) request.getAttribute("THANKYOU");
                request.removeAttribute("THANKYOU");
            }        
    %>  
    <head>
        <meta charset="utf-8">
        <title><%= timesheet.getMainPath("title") != null ? timesheet.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="shortcut icon" href="../assets/images/favicon.png" />
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css" />
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet" />
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/timesheet.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/timesheet/TimesheetAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
            <html:hidden property="doCancel"/>
            <html:hidden property="clientIdIndex"/>
            <html:hidden property="assetIdIndex"/>
            <html:hidden property="assetId"/>
            <html:hidden property="typevalue" />
            <html:hidden property="doView" />
            <html:hidden property="search" />
            <html:hidden property="timesheetId" />
            <html:hidden property="statusIndex" />
            <html:hidden property="month" />
            <html:hidden property="yearId" />
            <html:hidden property="doSaveDraft" />
            <html:hidden property="doSummary" />
            <html:hidden property="clientId" />
            <html:hidden property="crewId" />
            <html:hidden property="regenerate" />
            <html:hidden property="doSentApproval" />
            <html:hidden property="typeId" />
            <html:hidden property="fromDateIndex" />
            <html:hidden property="toDateIndex" />
            <input type="hidden" name="clientassetId" value='<%=assetId%>'/> 
            
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file="../header.jsp" %>
                <%@include file="../sidemenu.jsp" %>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content tab_panel no_tab1">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start"><span class="back_arrow"><a href="javascript: gobackpage();"><img src="../assets/images/back-arrow.png"/></a> Timesheet Management</span></div>
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
                                                <% if(tcount > 0) {%>
                                                    <li><a href="javascript: exportDetails('2');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                                <% }else {%>
                                                    <li><a href="javascript: exportDetails('1');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                                <% } %>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 metrix_top_right">
                                <div class="row d-flex align-items-center">
                                    <div class="col-md-3 com_label_value">
                                        <div class="row mb_0">
                                            <div class="col-md-3"><label>Client</label></div>
                                            <div class="col-md-9"><span><%= cinfo != null && cinfo.getClientName() != null ? cinfo.getClientName() : "" %></span></div>
                                        </div>
                                    </div>
                                    <div class="col-md-3 com_label_value">
                                        <div class="row mb_0">
                                            <div class="col-md-3 pd_0"><label>Asset</label></div>
                                            <div class="col-md-9 pd_0"><span><%= cinfo != null && cinfo.getClientAssetName() != null ? cinfo.getClientAssetName() : "" %></span></div>
                                        </div>
                                    </div>
                                    <div class="col-lg-6">
                                        <div class="row d-flex align-items-center">
                                            <div class="col-md-5 com_label_value">
                                                <div class="row mb_0">
                                                    <div class="col-md-4 pd_0"><label>Date Range</label></div>
                                                    <div class="col-md-8 pd_0"><span><%= tsinfo != null && tsinfo.getFromDate() != null ? tsinfo.getFromDate() : "" %> to <%= tsinfo != null && tsinfo.getToDate() != null ? tsinfo.getToDate() : "" %></span></div>
                                                </div>
                                            </div>
                                            <div class="col-md-2 com_label_value">
                                                <div class="row mb_0">
                                                    <div class="col-md-9 text-right"><label>Currency</label></div>
                                                    <div class="col-md-3 pd_0"><span><%= cinfo != null && cinfo.getCurrencyName() != null ? cinfo.getCurrencyName() : "" %></span></div>
                                                </div>
                                            </div>
                                            <div class="col-lg-5 col-md-5 col-sm-12 col-12">&nbsp;</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="container-fluid pd_0" id="printArea">
                            <div class="row">
                                <div class="col-md-12 col-xl-12 pd_0">
                                    <div class="body-background send_approval">
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="table-rep-plugin sort_table">
                                                    <div class="table-responsive mb-0">
                                                        <table id="tech-companies-1" class="table table-striped table-border">
                                                            <thead>
                                                                <tr class="blue_thead">
                                                                    <th colspan="3"><span>Timesheet: <b><%= timesheet.changeNum(tsinfo.getTimesheetId(),3)%></b></span></th>
                                                                    <th colspan="8" class="text-center"><b>EXPENSE DETAILS</b></th>
                                                                    <th class="text-center"><span><%= cinfo != null && cinfo.getCurrencyName() != null ? cinfo.getCurrencyName() : "" %> <% if(grandtotal_top > 0) {%><%=timesheet.getDecimal(grandtotal_top)%><% } %></span></th>
                                                                </tr>
                                                                <tr>                                                                    
                                                                    <th width="15%"><span><b>Personnel Name</b> </span></th>
                                                                    <th width="15%"><span><b>Position</b></span></th>
                                                                    <th width="13%"><span><b>Type of Expense</b></span></th>
                                                                    <th width="8%" class="text-center"><span><b>Sign On</b></span></th>
                                                                    <th width="8%" class="text-center"><span><b>Sign Off</b></span></th>
                                                                    <th width="3%" class="select_check_box"><b>SEND</b></th>
                                                                    <th width="9%" colspan="2" class="text-center"><span><b>Days / Hours</b></span></th>
                                                                    <th width="6%" class="text-center"><span><b>Rate</b></span></th>
                                                                    <th width="5%" class="text-center"><span><b>%</b></span></th>
                                                                    <th width="9%" class="text-right"><span><b>Amount</b></span></th>
                                                                    <th width="9%" class="text-right"><span><b>Chargeable</b></span></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
<%    
                                                            if(tcount <= 0 && datasize<= 0)
                                                            {
                                                                int crewrotationId =0, activityId = 0, noofdays = 0, size1 = 0, size2 = 0, size3 = 0, 
                                                                size4= 0, size5 = 0 , positionId = 0, candidateId = 0, travelDays=0, dateDiff1= 0, dateDiff2=0, 
                                                                apositionId1 = 0, apositionId2 = 0;
                                                                String fromDate = "", toDate ="", expensetype = "", name = "", position="", fromDate2 = "", toDate2 ="", apdate ="", newdate="",
                                                                aposition1 = "", aposition2 ="";                                                        
                                                                TimesheetInfo tinfo;
                                                                TimesheetInfo info5 = null;
                                                                double rate1 = 0, rate2 = 0, rate3= 0, allowance = 0, aprate1 = 0, aprate2 = 0, aprate3 = 0, oldrate1 = 0, oldrate2 = 0, oldrate3 = 0, allowancePer= 0;
                                                                for (int i = 0; i < crewlist_size; i++)
                                                                {
                                                                    tinfo = (TimesheetInfo) crewlist.get(i);
                                                                    if(tinfo != null)
                                                                    {
                                                                        name = tinfo.getName() != null ? tinfo.getName(): "" ;
                                                                        position = tinfo.getPosition() != null ? tinfo.getPosition(): "" ;
                                                                        crewrotationId = tinfo.getCrewrotationId();
                                                                        positionId = tinfo.getPositionId();
                                                                        candidateId = tinfo.getCandidateId();
                                                                    }
                                                                    
                                                                    ArrayList list1 = timesheet.getListFromList1(activitylist, candidateId, positionId);
                                                                    ArrayList list2 = timesheet.getListFromList2(spro_list, candidateId, positionId);
                                                                    ArrayList list3 = timesheet.getListFromListOld(pendinglist, crewrotationId, positionId);   
                                                                    ArrayList list4 = timesheet.getListFromList3(allowancelist, candidateId, positionId);    
                                                                    
                                                                    size1 = list1.size();
                                                                    size2 = list2.size();
                                                                    size3 = list3.size();
                                                                    size4 = list4.size();
                                                                    
                                                                    int crew_total  = size1 + size2 + size3 + size4;
                                                                    if(size1 > 0){
                                                                        crew_total = crew_total  + 1;
                                                                    }
                                                                    if(size1 > 0 || size2 > 0 || size3 > 0 || size4 > 0)
                                                                    {
                                                                        int pdays = timesheet.getTPNoofdays(list2, crewrotationId);
                                                                        int sbdays = timesheet.getSBYNoofdays(list2, crewrotationId);
                                                                        int sbdays2 = timesheet.getSBYdays(list2, crewrotationId);
                                                                        int cc = 0;
                                                                        if(size1 > 0)
                                                                        {
                                                                            for(int j = 0; j < size1; j++)
                                                                            {
                                                                                
                                                                                TimesheetInfo crinfo = (TimesheetInfo)  list1.get(j);
                                                                                if (crinfo != null) 
                                                                                {
                                                                                    ++cc;                                                                                      
                                                                                    activityId = crinfo.getActivityId();
                                                                                    fromDate = crinfo.getFromDate() != null ? crinfo.getFromDate() : "";
                                                                                    toDate = crinfo.getToDate() != null ? crinfo.getToDate() : "";
                                                                                    fromDate2 = crinfo.getFromDate() != null ? crinfo.getFromDate() : "";
                                                                                    toDate2 = crinfo.getToDate() != null ? crinfo.getToDate() : "";
                                                                                    expensetype = crinfo.getStatusValue() != null ? crinfo.getStatusValue() : "";
                                                                                    noofdays = crinfo.getDateDiff();                                                                                    
                                                                                    aposition = crinfo.getPosition() != null ? crinfo.getPosition(): "" ;
                                                                                    if(noofdays < 0)
                                                                                        noofdays = 0; 
                                                                                    double rate1_apply = rate1;
                                                                                    if(crinfo.getOldrate() == 1)
                                                                                    {
                                                                                        rate1_apply = oldrate1;
                                                                                    } 
                                                                                    info5 = timesheet.getRateFromList(ratelist, candidateId, positionId, toDate);  
                                                                                    rate1 =0; rate2 = 0; rate3 = 0;
                                                                                    if(info5 != null)
                                                                                    {
                                                                                        rate1 = info5.getRate1();
                                                                                        rate2 = info5.getRate2();
                                                                                        rate3 = info5.getRate3();
                                                                                    }
                                                                                    
%>                                                                                              
                                                                                    <tr>	
                                                                                        <input type="hidden" name="timesheetdetailId"  value="0" />
                                                                                        <input type="hidden" name="crewrotationId"  value="<%=crewrotationId%>" />
                                                                                        <input type="hidden" name="positionId"  value="<%=positionId%>" />
                                                                                        <input type="hidden" name="appPositionId"  value="0" />
                                                                                        <input type="hidden" name="type"  value="<%=activityId%>" />
                                                                                        <input type="hidden" name="fromDate2"  value="<%=fromDate%>" />
                                                                                        <input type="hidden" name="toDate2"  value="<%=toDate%>" />
                                                                                        <td><%=name %></td>
                                                                                        <td>
                                                                                            <%=aposition%>
                                                                                        </td>
                                                                                        <td><span class="mr_15"><%=expensetype%></span><a class="edit_mode" href="javascript: viewRotation('<%=crewrotationId%>', '<%=cinfo.getClientId()%>','<%=assetId%>');"><img src="../assets/images/pencil.png"></a></td>                                                                    
                                                                                        <td class="text-center"><%=fromDate%></td>
                                                                                        <td class="text-center"><%=toDate%></td>
                                                                                        <td class="select_check_box">
                                                                                            <label class="mt-checkbox mt-checkbox-outline"> 
                                                                                                <input type="checkbox" value="1" name="cb" id="cb_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" class="singlechkbox"  onchange="javascript: setGrandTotal('<%=crewrotationId%>', '<%=cc%>', '<%=positionId%>');" />
                                                                                                <input type="hidden" name="flag"  id="flag_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="0" />
                                                                                                <span></span>
                                                                                            </label>	
                                                                                        </td>
                                                                                        <td class="text-center">
                                                                                            <%if(j == size1-1){%>
                                                                                                <%=noofdays-sbdays2-pdays%>
                                                                                            <%}else{%>
                                                                                                <%=noofdays%>
                                                                                            <%}%>
                                                                                            <input type="hidden" name="days"  id="days_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=noofdays%>"  /></td>
                                                                                        <td>Days</td>
                                                                                        <input type="hidden" name="prate"  id="prate_<%=crewrotationId%>_<%=cc%>" value="0" />
                                                                                        <input type="hidden" name="rate"  id="rate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=rate1%>" />
                                                                                        <td class="text-center">
                                                                                            <%=rate1%>
                                                                                        </td>
                                                                                        <td class="assets_list text-center">
                                                                                            <input type="hidden" name="srate"  id="srate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="0" maxlength="5" />
                                                                                        </td>          
                                                                                        <input type="hidden" name="amount"  id="amount_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=noofdays*rate1%>" />
                                                                                        <td class="text-right"><%=noofdays*rate1%></td>
                                                                                        <input type="hidden" name="gtotal"  value="0" />
                                                                                        <input type="hidden" name="totalcrew"  value="0" />
                                                                                        <td class="text-center" >&nbsp;</td>
                                                                                    </tr>                                                                
<%
                                                                                }                                                                                
                                                                            }                                                                       
%>

<% 
                                                                        if(size2 >0)
                                                                        {
                                                                            for(int k = 0; k < size2; k++)
                                                                            {
                                                                                ++cc;
                                                                                TimesheetInfo kinfo = (TimesheetInfo)  list2.get(k);
                                                                                if (kinfo != null) 
                                                                                {
                                                                                    activityId = kinfo.getActivityId();
                                                                                    fromDate = kinfo.getFromDate() != null ? kinfo.getFromDate() : "";
                                                                                    toDate = kinfo.getToDate() != null ? kinfo.getToDate() : "";
                                                                                    expensetype = kinfo.getStatusValue() != null ? kinfo.getStatusValue() : "";
                                                                                    noofdays = kinfo.getDateDiff();
                                                                                    double prate = kinfo.getRate();
                                                                                    double amount = 0;          
                                                                                    int positionId_pro = 0;
                                                                                    String position_pro = "";
                                                                                    if(activityId == 3)
                                                                                    {
                                                                                        position_pro = kinfo.getPosition() != null ? kinfo.getPosition() : "";
                                                                                        positionId_pro = kinfo.getPositionId();
                                                                                        amount = noofdays * prate;
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        position_pro = position;
                                                                                        positionId_pro = positionId;
                                                                                    }
%>
                                                                                    <tr>
                                                                                        <input type="hidden" name="timesheetdetailId"  value="0" />
                                                                                        <input type="hidden" name="crewrotationId"  value="<%=crewrotationId%>" />
                                                                                        <input type="hidden" name="positionId"  value="<%=positionId_pro%>" />
                                                                                        <input type="hidden" name="appPositionId"  value="0" />
                                                                                        <input type="hidden" name="type"  value="<%=activityId%>" />
                                                                                        <input type="hidden" name="fromDate2"  value="<%=fromDate%>" />
                                                                                        <input type="hidden" name="toDate2"  value="<%=toDate%>" />
                                                                                            <td><%=name %></td>
                                                                                            <td><%=position_pro%></td>
                                                                                            <td><%=expensetype%></td>                                                                    
                                                                                            <td class="text-center"><%=fromDate%></td>
                                                                                            <td class="text-center"><%=toDate%></td>
                                                                                            <td class="select_check_box">
                                                                                                <label class="mt-checkbox mt-checkbox-outline"> 
                                                                                                    <input type="checkbox" value="1" name="cb" id="cb_<%=crewrotationId%>_<%=cc%>_<%=positionId_pro%>" class="singlechkbox"  onchange="javascript: setGrandTotal('<%=crewrotationId%>', '<%=cc%>', '<%=positionId_pro%>');" />
                                                                                                    <input type="hidden" name="flag"  id="flag_<%=crewrotationId%>_<%=cc%>_<%=positionId_pro%>" value="0" />
                                                                                                    <span></span>
                                                                                                </label>	
                                                                                            </td>
                                                                                            <td class="text-center">
                                                                                                <%=noofdays%>
                                                                                                <input type="hidden" name="days"  id="days_<%=crewrotationId%>_<%=cc%>_<%=positionId_pro%>" value=<%=noofdays%>  /></td>
                                                                                            <td>Days</td>
                                                                                            <input type="hidden" name="prate"  id="prate_<%=crewrotationId%>_<%=cc%>" value="<%=prate%>" />
                                                                                            <input type="hidden" name="rate"  id="rate_<%=crewrotationId%>_<%=cc%>_<%=positionId_pro%>" value="<%=rate1%>" />
                                                                                            <td class="text-center"  id="ratetd_<%=crewrotationId%>_<%=cc%>_<%=positionId_pro%>"><%=prate%></td>
                                                                                            <td class="text-center">
                                                                                            <% if(activityId == 4){ %>
                                                                                                <input type="text" name="srate"  id="srate_<%=crewrotationId%>_<%=cc%>_<%=positionId_pro%>" value="0" maxlength="5" class="form-control text-center" onchange="javascript: setStandby('<%=crewrotationId%>', '<%=cc%>','<%=positionId_pro%>');"  />
                                                                                            <% } else {%>
                                                                                                 <input type="hidden" name="srate"  id="srate_<%=crewrotationId%>_<%=cc%>_<%=positionId_pro%>" value="0" />
                                                                                            <% } %>
                                                                                            </td>
                                                                                            <input type="hidden" name="amount"  id="amount_<%=crewrotationId%>_<%=cc%>_<%=positionId_pro%>" value="<%=amount%>" />
                                                                                            <td class="text-right"  id="amounttd_<%=crewrotationId%>_<%=cc%>_<%=positionId_pro%>" ><%=amount%></td>
                                                                                            <% if(size4 == 0 ) {%>
                                                                                                 <input type='hidden' name='totalcrew' id='totalcrew_<%=crewrotationId%>' value='<%=crew_total%>' />
                                                                                                <input type="hidden" name="gtotal"  id="gtotal_<%=crewrotationId%>_<%=positionId_pro%>" value="0" />
                                                                                                <td class="text-center"  id="gtotaltd_<%=crewrotationId%>_<%=positionId_pro%>">0</td>
                                                                                                <% } else { %>
                                                                                                    <input type="hidden" name="gtotal"  value="0" />
                                                                                                    <input type='hidden' name='totalcrew' value='0' />
                                                                                                    <td class="text-center" >&nbsp;</td>
                                                                                                <% } %>
                                                                                        </tr> 
<%
                                                                                    }
                                                                                }
                                                                            }
                                                                        ++cc;
%>


                                                                        <tr>
                                                                            <input type="hidden" name="timesheetdetailId"  value="0" />
                                                                            <input type="hidden" name="crewrotationId"  value="<%=crewrotationId%>" />
                                                                            <input type="hidden" name="positionId"  value="<%=positionId%>" />
                                                                            <input type="hidden" name="appPositionId"  value="0" />
                                                                            <input type="hidden" name="type"  value="2" />
                                                                            <input type="hidden" name="fromDate2"  value="<%=fromDate%>" />
                                                                            <input type="hidden" name="toDate2"  value="<%=toDate%>" />
                                                                            <td><%=name%></td>
                                                                            <td><%=position%></td>
                                                                            <td>Overtime</td>                                                                    
                                                                            <td class="text-center"><%=fromDate2%></td>
                                                                            <td class="text-center"><%=toDate2%></td>
                                                                            <td class="select_check_box">
                                                                                <label class="mt-checkbox mt-checkbox-outline"> 
                                                                                    <input type="checkbox" value="1" name="cb" id="cb_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" class="singlechkbox"  onchange="javascript: setGrandTotal('<%=crewrotationId%>', '<%=cc%>', '<%=positionId%>');" />
                                                                                    <input type="hidden" name="flag"  id="flag_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="0" />
                                                                                    <span></span>
                                                                                </label>	
                                                                            </td>
                                                                            <td class="text-center">
                                                                                <input type="text" name="days"  id="days_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="0" class="form-control text-center" onchange="javascript: setTotal('<%=crewrotationId%>', '<%=cc%>','<%=positionId%>');"  />
                                                                            </td>
                                                                            <td>Hours</td>
                                                                            <input type="hidden" name="prate"  id="prate_<%=crewrotationId%>_<%=cc%>" value="0" />
                                                                            <input type="hidden" name="rate"  id="rate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=rate2%>" />
                                                                            <td class="text-center" id="ratetd_<%=crewrotationId%>_<%=cc%>_<%=positionId%>"><%=rate2%></td>
                                                                            <td class="assets_list text-center">
                                                                                <input type="hidden" name="srate"  id="srate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="0" maxlength="5" />
                                                                            </td>                                                                                    
                                                                            <input type="hidden" name="amount"  id="amount_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="0" />
                                                                            <td class="text-right"  id="amounttd_<%=crewrotationId%>_<%=cc%>_<%=positionId%>">&nbsp;</td>
                                                                            <% if(size4 == 0) {%>
                                                                            <input type='hidden' name='totalcrew' id='totalcrew_<%=crewrotationId%>' value='<%=crew_total%>' />
                                                                            <input type="hidden" name="gtotal"  id="gtotal_<%=crewrotationId%>_<%=positionId%>" value="0" />
                                                                            <td class="text-center"  id="gtotaltd_<%=crewrotationId%>_<%=positionId%>">0</td>
                                                                            <% } else { %>
                                                                                <input type="hidden" name="gtotal"  value="0" />
                                                                                <input type='hidden' name='totalcrew' value='0' />
                                                                                <td class="text-center">&nbsp;</td>
                                                                            <% } %>                                                                                    
                                                                        </tr>    


<%
                                                                                
                                                                                for(int l = 0; l < size3; l++)
                                                                                {
                                                                                    ++cc;
                                                                                    TimesheetInfo pinfo = (TimesheetInfo)  list3.get(l);
                                                                                    if (pinfo != null) 
                                                                                    {
                                                                                        activityId = pinfo.getType();
                                                                                        fromDate = pinfo.getFromDate() != null ? pinfo.getFromDate() : "";
                                                                                        toDate = pinfo.getToDate() != null ? pinfo.getToDate() : "";
                                                                                        expensetype = pinfo.getStatusValue() != null ? pinfo.getStatusValue() : "";
                                                                                        noofdays = pinfo.getNoofdays();
                                                                                        position = pinfo.getPosition() != null ? pinfo.getPosition() : "";
                                                                                        positionId = pinfo.getPositionId();
                                                                                        rate1 = pinfo.getRate();
                                                                                        double srate = pinfo.getPercent();
                                                                                        double prate = pinfo.getPrate();                                                                                        
                                                                                        String dtype = "Days"; 
                                                                                        if(activityId == 2)
                                                                                            dtype = "Hours";
%>
                                                                                            <tr style="background-color: #e59866 ">
                                                                                                <input type="hidden" name="timesheetdetailId"  value="0" />
                                                                                                <input type="hidden" name="crewrotationId"  value="<%=crewrotationId%>" />
                                                                                                <input type="hidden" name="positionId"  value="<%=positionId%>" />
                                                                                                <input type="hidden" name="appPositionId"  value="0" />
                                                                                                <input type="hidden" name="type"  value="<%=activityId%>" />                                                                                                
                                                                                                <input type="hidden" name="fromDate2"  value="<%=fromDate%>" />
                                                                                                <input type="hidden" name="toDate2"  value="<%=toDate%>" />
                                                                                                <td ><%=name%></td>
                                                                                                <td><%=position%></td>
                                                                                                <td><%=expensetype%></td>       
                                                                                                <% if(activityId == 5) {%>
                                                                                                    <td class="text-center"></td>
                                                                                                    <td class="text-center"></td>
                                                                                                <%}else {%>
                                                                                                    <td class="text-center"><%=fromDate%></td>
                                                                                                    <td class="text-center"><%=toDate%></td>
                                                                                                <%}%>
                                                                                                <td class="select_check_box">
                                                                                                    <label class="mt-checkbox mt-checkbox-outline"> 
                                                                                                        <input type="checkbox" value="1" name="cb" id="cb_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" class="singlechkbox"  onchange="javascript: setGrandTotal('<%=crewrotationId%>', '<%=cc%>', '<%=positionId%>');"/>
                                                                                                        <input type="hidden" name="flag"  id="flag_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="0" />
                                                                                                        <span></span>
                                                                                                    </label>	
                                                                                                </td>
                                                                                                <td class="text-center">
                                                                                                    <% if(activityId == 2) {%>
                                                                                                        <input type="text" name="days"  id="days_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=noofdays%>" class="form-control text-center" onchange="javascript: setTotal('<%=crewrotationId%>', '<%=cc%>','<%=positionId%>');"  />
                                                                                                        
                                                                                                    <% } else if(activityId == 5) {%>
                                                                                                        <input type="text" name="days"  id="days_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=noofdays%>" class="form-control text-center" onchange="javascript: setTotal('<%=crewrotationId%>', '<%=cc%>','<%=positionId%>');"  />
                                                                                                    <% } else {%>
                                                                                                    <%=noofdays%>
                                                                                                        <input type="hidden" name="days"  id="days_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=noofdays%>" class="form-control text-center" />
                                                                                                    <% } %>
                                                                                                </td>
                                                                                                <td><%=dtype%></td>                                                                                
                                                                                                <input type="hidden" name="rate"  id="rate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=rate1%>" />
                                                                                                <td class="text-center" id="ratetd_<%=crewrotationId%>_<%=cc%>_<%=positionId%>">
                                                                                                    <% if(activityId == 2) {%>
                                                                                                        <%=timesheet.getDecimal((rate1))%>
                                                                                                    <% } else if(activityId == 4) {%>
                                                                                                        <%=timesheet.getDecimal((rate1 * srate) / 100.0)%>                                                                                                        
                                                                                                    <% } else if(activityId == 3) {%>
                                                                                                        <%=prate%>
                                                                                                    <% } else { %>
                                                                                                        <%=rate1%>
                                                                                                    <% } %>
                                                                                                </td> 
                                                                                                <input type="hidden" name="prate"  id="prate_<%=crewrotationId%>_<%=cc%>" value="<%=prate%>" />
                                                                                                <td>
                                                                                                    <% if(activityId == 4){ %>
                                                                                                       <input type="text" name="srate"  id="srate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=srate%>" maxlength="5" class="form-control text-center" onchange="javascript: setStandby('<%=crewrotationId%>', '<%=cc%>','<%=positionId%>');" />
                                                                                                    <% } else {%>
                                                                                                        <input type="hidden" name="srate"  id="srate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="0" maxlength="5" class="form-control text-center" />
                                                                                                    <% } %>
                                                                                                </td>
                                                                                                <input type="hidden" name="amount"  id="amount_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=timesheet.getDecimal(pinfo.getAmount())%>" />
                                                                                                <td class="text-right"  id="amounttd_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" ><%=timesheet.getDecimal(pinfo.getAmount())%></td>
                                                                                                <% if(size4 == 0) {%>
                                                                                                    <input type='hidden' name='totalcrew' id='totalcrew_<%=crewrotationId%>' value='<%=crew_total%>' />
                                                                                                    <input type="hidden" name="gtotal"  id="gtotal_<%=crewrotationId%>_<%=positionId%>" value="0" />
                                                                                                    <td class="text-center"  id="gtotaltd_<%=crewrotationId%>_<%=positionId%>">0</td>
                                                                                                <% } else { %>
                                                                                                    <input type="hidden" name="gtotal"  value="0" />
                                                                                                    <input type='hidden' name='totalcrew' value='0' />
                                                                                                    <td class="text-center" >&nbsp;</td>
                                                                                                <% } %>
                                                                                            </tr> 
<%                                                                                                  //++cc;
                                                                                        }
                                                                                    }
%>
                                                                                
<%
                                                                            for(int k = 0; k < size4; k++)
                                                                            {
                                                                                TimesheetInfo allinfo = (TimesheetInfo)  list4.get(k);
                                                                                if (allinfo != null) 
                                                                                {
                                                                                    ++cc;
                                                                                    allowance = allinfo.getRate1();
                                                                                    allowancePer = allinfo.getAllowance();
                                                                                    positionId = allinfo.getPositionId();
                                                                                    travelDays = allinfo.getTravelDays();
%>
                                                                                <tr>
                                                                                    <input type="hidden" name="timesheetdetailId"  value="0" />
                                                                                    <input type="hidden" name="crewrotationId"  value="<%=crewrotationId%>" />
                                                                                    <input type="hidden" name="positionId"  value="<%=positionId%>" />
                                                                                    <input type="hidden" name="appPositionId"  value="0" />
                                                                                    <input type="hidden" name="type"  value="5" />
                                                                                    <input type="hidden" name="fromDate2"  value="<%=fromDate%>" />
                                                                                    <input type="hidden" name="toDate2"  value="<%=toDate%>" />
                                                                                    <td><%=name %></td>
                                                                                    <td><%=position%></td>
                                                                                    <td>Travel</td>                                                                    
                                                                                    <td class="text-center"></td>
                                                                                    <td class="text-center"></td>
                                                                                    <td class="select_check_box">
                                                                                        <label class="mt-checkbox mt-checkbox-outline"> 
                                                                                            <input type="checkbox" value="1" name="cb" id="cb_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" class="singlechkbox"  onchange="javascript: setGrandTotal('<%=crewrotationId%>', '<%=cc%>', '<%=positionId%>');" />
                                                                                            <input type="hidden" name="flag"  id="flag_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="0" />
                                                                                            <span></span>
                                                                                        </label>	
                                                                                    </td>
                                                                                    <td class="text-center">
                                                                                        <input type="text" name="days"  id="days_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=travelDays%>" class="form-control text-center" onchange="javascript: setTotal('<%=crewrotationId%>', '<%=cc%>','<%=positionId%>');"  />
                                                                                    </td>
                                                                                    <td>Days</td>
                                                                                    <input type="hidden" name="prate"  id="prate_<%=crewrotationId%>_<%=cc%>" value="0" />
                                                                                    <input type="hidden" name="rate"  id="rate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=timesheet.getDecimal(allowance)%>" />
                                                                                    <td class="text-center" id="ratetd_<%=crewrotationId%>_<%=cc%>_<%=positionId%>">
                                                                                        <%=timesheet.getDecimal(allowance)%>
                                                                                    </td>
                                                                                    <td class="assets_list text-center">
                                                                                        <input type="hidden" name="srate"  id="srate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=allowancePer%>" maxlength="5" />
                                                                                        <%=allowancePer%>
                                                                                    </td>                                                                                    
                                                                                    <input type="hidden" name="amount"  id="amount_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=timesheet.getDecimal(allowance*travelDays)%>" />
                                                                                    <td class="text-right"  id="amounttd_<%=crewrotationId%>_<%=cc%>_<%=positionId%>">
                                                                                        <%=timesheet.getDecimal(allowance*travelDays)%>
                                                                                    </td>
                                                                                    <% if(k == size4 - 1) {%>
                                                                                    <input type='hidden' name='totalcrew' id='totalcrew_<%=crewrotationId%>' value='<%=crew_total%>' />
                                                                                    <input type="hidden" name="gtotal" id="gtotal_<%=crewrotationId%>_<%=positionId%>" value="0" />
                                                                                    <td class="text-center" id="gtotaltd_<%=crewrotationId%>_<%=positionId%>">
                                                                                        0
                                                                                    </td>
                                                                                    <% } else { %>
                                                                                        <input type="hidden" name="gtotal"  value="0" />
                                                                                        <input type="hidden" name="totalcrew"  value="0" />
                                                                                        <td class="text-center">&nbsp;</td>
                                                                                    <% } %>
                                                                                    
                                                                                </tr>                                                                                
<%
                                                                                    }
                                                                                }                                                                                
%>
                                                                                
                                                                                
<%
                                                                            }
                                                                            
                                                                                
                                                                                }
                                                                            }
                                                                        }
                                                                        else if(modify_list_size > 0) 
                                                                        {
                                                                            int cc = 0;
                                                                            int tempcrewId = 0;
                                                                            for(int l = 0; l < modify_list_size; l++)
                                                                            {
                                                                                TimesheetInfo pinfo = (TimesheetInfo)  modify_list.get(l);
                                                                                if (pinfo != null) 
                                                                                {
                                                                                    int crewrotationId  = pinfo.getCrewrotationId();
                                                                                    if(tempcrewId != crewrotationId)
                                                                                        cc = 0;
                                                                                    ++cc;

                                                                                    int activityId = pinfo.getType();
                                                                                    repId = pinfo.getRepeatId();
                                                                                    String name = pinfo.getName() != null ? pinfo.getName() : "";
                                                                                    String fromDate = pinfo.getFromDate() != null ? pinfo.getFromDate() : "";
                                                                                    String toDate = pinfo.getToDate() != null ? pinfo.getToDate() : "";
                                                                                    String expensetype = pinfo.getStatusValue() != null ? pinfo.getStatusValue() : "";
                                                                                    int noofdays = pinfo.getNoofdays();
                                                                                    String position = pinfo.getPosition() != null ? pinfo.getPosition() : "";
                                                                                    int positionId = pinfo.getPositionId();
                                                                                    double rate1 = pinfo.getRate();
                                                                                    double srate = pinfo.getPercent();
                                                                                    double prate = pinfo.getPrate();
                                                                                    int crew_total = pinfo.getTotalcrew(); 
                                                                                    int flag = pinfo.getFlag();
                                                                                    int assetIdEdit = pinfo.getAssetId();
                                                                                    String dtype = "Days"; 
                                                                                    if(activityId == 2)
                                                                                        dtype = "Hours";                                                                                      
%>
                                                                                        <tr>
                                                                                            <input type="hidden" name="timesheetdetailId"  value="<%=pinfo.getTimesheetdetailId()%>" />
                                                                                            <input type="hidden" name="repeatId"  value="<%=repId%>" />
                                                                                            <input type="hidden" name="crewrotationId"  value="<%=crewrotationId%>" />
                                                                                            <input type="hidden" name="positionId"  value="<%=positionId%>" />
                                                                                            <input type="hidden" name="appPositionId"  value="0" />
                                                                                            <input type="hidden" name="type"  value="<%=activityId%>" />
                                                                                            <input type="hidden" name="fromDate2"  value="<%=fromDate%>" />
                                                                                            <input type="hidden" name="toDate2"  value="<%=toDate%>" />
                                                                                            <td><%=name %></td>
                                                                                            <td><%=position%></td>
                                                                                            <td>
                                                                                                <span class="mr_15"><%=expensetype%></span>
                                                                                                <% if(cc == 1) {%>
                                                                                                <a class="edit_mode" href="javascript: viewRotation('<%=crewrotationId%>','<%=cinfo.getClientId()%>','<%=assetIdEdit%>');"><img src="../assets/images/pencil.png"></a>
                                                                                                <%}%>
                                                                                            </td>                                                                   
                                                                                             <% if(activityId == 5) {%>
                                                                                                <td class="text-center"></td>
                                                                                                <td class="text-center"></td>
                                                                                            <%}else{%>
                                                                                                <td class="text-center"><%=fromDate%></td>
                                                                                                <td class="text-center"><%=toDate%></td>
                                                                                            <%}%>
                                                                                            <td class="select_check_box">
                                                                                                <label class="mt-checkbox mt-checkbox-outline"> 
                                                                                                    <input type="checkbox" value="1" name="cb" id="cb_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" class="singlechkbox"  onchange="javascript: setGrandTotal('<%=crewrotationId%>', '<%=cc%>', '<%=positionId%>');" <% if(flag == 1) {%>checked<% } %>/>
                                                                                                    <input type="hidden" name="flag"  id="flag_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=flag%>" />
                                                                                                    <span></span>
                                                                                                </label>	
                                                                                            </td>
                                                                                            <td class="text-center">
                                                                                                <% if(activityId == 2) {%>
                                                                                                    <input type="text" name="days"  id="days_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=noofdays%>" class="form-control text-center" onchange="javascript: setTotal('<%=crewrotationId%>', '<%=cc%>','<%=positionId%>');"  />
                                                                                                <% } else if(activityId == 5) {%>
                                                                                                    <input type="text" name="days"  id="days_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=noofdays%>" class="form-control text-center" onchange="javascript: setTotal('<%=crewrotationId%>', '<%=cc%>','<%=positionId%>');"  />
                                                                                                <% } else {%>
                                                                                                <%=noofdays%>
                                                                                                    <input type="hidden" name="days"  id="days_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=noofdays%>" class="form-control text-center" />
                                                                                                <% } %>
                                                                                            </td>
                                                                                            <td><%=dtype%></td>                                                                                
                                                                                            <input type="hidden" name="rate"  id="rate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=rate1%>" />
                                                                                            <td class="text-center" id="ratetd_<%=crewrotationId%>_<%=cc%>_<%=positionId%>"><% if(activityId == 4) {%><%=timesheet.getDecimal((rate1 * srate) / 100.0)%><% } else if(activityId == 3) {%><%=prate%><% } else { %><%=rate1%><% } %></td>                                                                                                
                                                                                            <input type="hidden" name="prate"  id="prate_<%=crewrotationId%>_<%=cc%>" value="<%=prate%>" />
                                                                                            <td>
                                                                                             <% if(activityId == 4){ %>
                                                                                                <input type="text" name="srate"  id="srate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=srate%>" maxlength="5" class="form-control text-center"  onchange="javascript: setStandby('<%=crewrotationId%>', '<%=cc%>','<%=positionId%>');"/>
                                                                                            <% } else if(activityId == 5){ %>
                                                                                                <input type="hidden" name="srate"  id="srate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=srate%>"/>
                                                                                                <%=srate%>
                                                                                            <% } else {%>
                                                                                                 <input type="hidden" name="srate"  id="srate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="0" maxlength="5" class="form-control text-center" />
                                                                                            <% } %>
                                                                                            </td>
                                                                                            <input type="hidden" name="amount"  id="amount_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=pinfo.getAmount()%>" />
                                                                                            <td class="text-right"  id="amounttd_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" ><%=pinfo.getAmount()%></td>
                                                                                            <% if(crew_total > 0) {%>
                                                                                                <input type='hidden' name='totalcrew' id='totalcrew_<%=crewrotationId%>' value='<%=crew_total%>' />
                                                                                                <input type="hidden" name="gtotal"  id="gtotal_<%=crewrotationId%>_<%=positionId%>" value="<%=pinfo.getGrandtotal() %>" />
                                                                                                <td class="text-center"  id="gtotaltd_<%=crewrotationId%>_<%=positionId%>"><%=timesheet.getDecimal(pinfo.getGrandtotal()) %></td>
                                                                                                <% } else { %>
                                                                                                    <input type="hidden" name="gtotal"  value="0" />
                                                                                                    <input type='hidden' name='totalcrew' value='0' />
                                                                                                    <td class="text-center" >&nbsp;</td>
                                                                                                <% } %>
                                                                                        </tr> 
<%
                                                                                        tempcrewId = crewrotationId;
                                                                                    }
                                                                                }
                                                                        
%>



<%
                                                                            }else 
                                                                            {
                                                                            if(datasize > 0)
                                                                            {
                                                                                int cc=0;
                                                                                for(int d = 0; d < datasize; d++)
                                                                                {
                                                                                    ++cc;
                                                                                    TimesheetInfo pinfo = (TimesheetInfo)  data_list.get(d);
                                                                                    if (pinfo != null) 
                                                                                    {
                                                                                        int crewrotationId = pinfo.getCrewrotationId();
                                                                                        int tid = pinfo.getTimesheetId();
                                                                                        int activityId = pinfo.getType();
                                                                                        int crew_total = pinfo.getTotalcrew();
                                                                                        String fromDate = pinfo.getFromDate() != null ? pinfo.getFromDate() : "";
                                                                                        String toDate = pinfo.getToDate() != null ? pinfo.getToDate() : "";
                                                                                        String expensetype = pinfo.getStatusValue() != null ? pinfo.getStatusValue() : "";
                                                                                        int noofdays = pinfo.getNoofdays();
                                                                                        String position = pinfo.getPosition() != null ? pinfo.getPosition() : "";
                                                                                        String name = pinfo.getName() != null ? pinfo.getName() : "";
                                                                                        int positionId = pinfo.getPositionId();
                                                                                        double rate1 = pinfo.getRate();
                                                                                        double srate = pinfo.getPercent();
                                                                                        double prate = pinfo.getPrate();                                                                                        
                                                                                        String dtype = "Days"; 
                                                                                        if(activityId == 2)
                                                                                            dtype = "Hours";
%>
                                                                                            <tr>
                                                                                                
                                                                                                <input type="hidden" name="timesheetdetailId"  value="0" />
                                                                                                <input type="hidden" name="repeatId"  value="0" />
                                                                                                <input type="hidden" name="crewrotationId"  value="<%=crewrotationId%>" />
                                                                                                <input type="hidden" name="positionId"  value="<%=positionId%>" />
                                                                                                <input type="hidden" name="appPositionId"  value="0" />
                                                                                                <input type="hidden" name="type"  value="<%=activityId%>" />                                                                                                
                                                                                                <input type="hidden" name="fromDate2"  value="<%=fromDate%>" />
                                                                                                <input type="hidden" name="toDate2"  value="<%=toDate%>" />
                                                                                                <td ><%=name%></td>
                                                                                                <td><%=position%></td>
                                                                                                <td><%=expensetype%></td>       
                                                                                                <% if(activityId == 5) {%>
                                                                                                    <td class="text-center"></td>
                                                                                                    <td class="text-center"></td>
                                                                                                <%}else {%>
                                                                                                    <td class="text-center"><%=fromDate%></td>
                                                                                                    <td class="text-center"><%=toDate%></td>
                                                                                                <%}%>
                                                                                                <td class="select_check_box">
                                                                                                    <label class="mt-checkbox mt-checkbox-outline"> 
                                                                                                        <input type="checkbox" value="1" name="cb" id="cb_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" class="singlechkbox"  onchange="javascript: setGrandTotal('<%=crewrotationId%>', '<%=cc%>', '<%=positionId%>');"/>
                                                                                                        <input type="hidden" name="flag"  id="flag_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="0" />
                                                                                                        <span></span>
                                                                                                    </label>	
                                                                                                </td>
                                                                                                <td class="text-center">
                                                                                                    <% if(activityId == 2) {%>
                                                                                                        <input type="text" name="days"  id="days_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=noofdays%>" class="form-control text-center" onchange="javascript: setTotal('<%=crewrotationId%>', '<%=cc%>','<%=positionId%>');"  />
                                                                                                        
                                                                                                    <% } else if(activityId == 5) {%>
                                                                                                        <input type="text" name="days"  id="days_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=noofdays%>" class="form-control text-center" onchange="javascript: setTotal('<%=crewrotationId%>', '<%=cc%>','<%=positionId%>');"  />
                                                                                                    <% } else {%>
                                                                                                    <%=noofdays%>
                                                                                                        <input type="hidden" name="days"  id="days_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=noofdays%>" class="form-control text-center" />
                                                                                                    <% } %>
                                                                                                </td>
                                                                                                <td><%=dtype%></td>                                                                                
                                                                                                <input type="hidden" name="rate"  id="rate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=rate1%>" />
                                                                                                <td class="text-center" id="ratetd_<%=crewrotationId%>_<%=cc%>_<%=positionId%>">
                                                                                                    <% if(activityId == 2) {%>
                                                                                                        <%=timesheet.getDecimal((rate1))%>
                                                                                                    <% } else if(activityId == 4) {%>
                                                                                                        <%=timesheet.getDecimal((rate1 * srate) / 100.0)%>                                                                                                        
                                                                                                    <% } else if(activityId == 3) {%>
                                                                                                        <%=prate%>
                                                                                                    <% } else { %>
                                                                                                        <%=rate1%>
                                                                                                    <% } %>
                                                                                                </td> 
                                                                                                <input type="hidden" name="prate"  id="prate_<%=crewrotationId%>_<%=cc%>" value="<%=prate%>" />
                                                                                                <td>
                                                                                                    <% if(activityId == 4){ %>
                                                                                                       <input type="text" name="srate"  id="srate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=srate%>" maxlength="5" class="form-control text-center" onchange="javascript: setStandby('<%=crewrotationId%>', '<%=cc%>','<%=positionId%>');" />
                                                                                                    <% } else {%>
                                                                                                        <input type="hidden" name="srate"  id="srate_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="0" maxlength="5" class="form-control text-center" />
                                                                                                    <% } %>
                                                                                                </td>
                                                                                                <input type="hidden" name="amount"  id="amount_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" value="<%=timesheet.getDecimal(pinfo.getAmount())%>" />
                                                                                                <td class="text-right"  id="amounttd_<%=crewrotationId%>_<%=cc%>_<%=positionId%>" ><%=timesheet.getDecimal(pinfo.getAmount())%></td>
                                                                                                <% if(d == datasize- 1) {%>
                                                                                                    <input type='hidden' name='totalcrew' id='totalcrew_<%=crewrotationId%>' value='<%=crew_total%>' />
                                                                                                    <input type="hidden" name="gtotal"  id="gtotal_<%=crewrotationId%>_<%=positionId%>" value="0" />
                                                                                                    <td class="text-center"  id="gtotaltd_<%=crewrotationId%>_<%=positionId%>">0</td>
                                                                                                <% } else { %>
                                                                                                    <input type="hidden" name="gtotal"  value="0" />
                                                                                                    <input type='hidden' name='totalcrew' value='0' />
                                                                                                    <td class="text-center" >&nbsp;</td>
                                                                                                <% } %>
                                                                                            </tr> 
<%                                                                                                  //++cc;
                                                                                        }
                                                                                    }
                                                                                    }
                                                                                    }
%>
                                                            </tbody>
                                                        </table>	
                                                    </div>
                                                </div>	
                                            </div>
                                             <%      
                                                if((addper.equals("Y") || editper.equals("Y")) && ((tsinfo.getStatus() == 1) || (tsinfo.getStatus() == 2) || tsinfo.getStatus() == 4)){
                                            %>          
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <a class="regenerate_btn" href="javascript: regenerateTimesheet('<%=tsinfo.getTimesheetId()%>');" ><img src="../assets/images/regenerate.png" ></a>
                                                    <%if(tsinfo.getStatus() == 2){%>
                                                        <a class="send_apporval_btn" onclick="javascript: SentforApproval('<%= tsinfo.getTimesheetId()%>', '2', '<%= repId%>');" data-bs-toggle="modal" data-bs-target="#email_timesheet_modal"><img src="../assets/images/email.png" title="Send for Approval"></a>
                                                    <%}%>
                                                    <a href="javascript: saveDraft();" class="save_btn save_draf">Save Draft</a>    
                                                </div>
                                            <%}%>
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
                </div>
                
                <%@include file="../footer.jsp" %>
                <div id="email_timesheet_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="row client_position_table" id="approveDiv">                                            
                                        </div>                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
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
                                        <h3>Timesheet Saved</h3>                                        
                                        <a class="trans_btn hand_cursor" data-bs-dismiss="modal">Timesheet</a>
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
                <script src="../assets/js/app.js"></script>	
                <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>   
                <script src="/jxp/assets/js/sweetalert2.min.js"></script>
                
                <% if (thankyou.equals("yes")) {%>
                <script type="text/javascript">
                    $(window).on('load', function () {
                        $('#thank_you_modal').modal('show');
                    });
                </script>
                <%}%>
                <script>
                        $(window).on('scroll', function () {
                            if ($(this).scrollTop() > 150) {
                                $('.head_fixed').addClass("is-sticky");
                            } else {
                                $('.head_fixed').removeClass("is-sticky");
                            }
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
                //addLoadEvent();
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
