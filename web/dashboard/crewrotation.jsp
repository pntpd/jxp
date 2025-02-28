<%@page import="java.util.Stack"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.web.jxp.dashboard.DashboardInfo"%>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<jsp:useBean id="dashboard" class="com.web.jxp.dashboard.Dashboard" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = -1, submtp = -1;
            if (session.getAttribute("LOGININFO") == null) {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
        }
            //summary list
            ArrayList dlist = new ArrayList();
            if (session.getAttribute("DASH_CREWROTATIONDLIST") != null) {
                dlist = (ArrayList) session.getAttribute("DASH_CREWROTATIONDLIST");
            }
            int dtotal = dlist.size();
            //position list
            ArrayList positionlist = new ArrayList();
            if (session.getAttribute("DASH_CREWROTATIONPLIST") != null) {
                positionlist = (ArrayList) session.getAttribute("DASH_CREWROTATIONPLIST");
            }
            int positiontotal = positionlist.size();
            // candidatelist
            ArrayList candlist = new ArrayList();
            if (session.getAttribute("DASH_CREWROTATIONCLIST") != null) {
                candlist = (ArrayList) session.getAttribute("DASH_CREWROTATIONCLIST");
            }
            int candlisttotal = candlist.size();
            // personlist
            ArrayList personlist = new ArrayList();
            if (session.getAttribute("DASH_CREWROTATIONPERSONLIST") != null) {
                personlist = (ArrayList) session.getAttribute("DASH_CREWROTATIONPERSONLIST");
            }
            int personlisttotal = personlist.size();
            // info of 3 lists
            DashboardInfo listinfo = null;
            if (session.getAttribute("DASH_CREWROTATIONULIST") != null) 
            {
                listinfo = (DashboardInfo) session.getAttribute("DASH_CREWROTATIONULIST");
            } 
            // datelist
            ArrayList datelist = new ArrayList();
            if (session.getAttribute("DASH_DATELIST") != null) {
                datelist = (ArrayList) session.getAttribute("DASH_DATELIST");
            }
            int datetotal = datelist.size();
            // datelistday
            ArrayList datelistday = new ArrayList();
            if (session.getAttribute("DASH_DATELISTDAY") != null) {
                datelistday = (ArrayList) session.getAttribute("DASH_DATELISTDAY");
            }
            int datelistdaytotal = datelistday.size();
            String month = "";
            if (session.getAttribute("MONTH") != null)
            {
                month = (String)session.getAttribute("MONTH");
            }

            //checkbox list
            String positioncb = "", crewrotationcb = "";
            if (session.getAttribute("POSITIONCB") != null) {
                positioncb = (String) session.getAttribute("POSITIONCB");
            }
            if (session.getAttribute("CREWROTATIONCB") != null) {
                crewrotationcb = (String) session.getAttribute("CREWROTATIONCB");
            }
            int normal = 0, extended= 0, overstay = 0, onshore = 0;
            
            String crId = "";
            if(request.getAttribute("DASHCRID") != null)
            {
                crId = (String)request.getAttribute("DASHCRID");
                request.removeAttribute("DASHCRID");
            }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= user.getMainPath("title") != null ? user.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <!-- Responsive Table css -->
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/dashboard.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/dashboard/DashboardAction.do" onsubmit="return false;" enctype="multipart/form-data">
            <html:hidden property = "doCR"/>
            <html:hidden property = "doSearchCR"/>
            <html:hidden property = "doSearchCRpp"/>
            <html:hidden property="crewrotationcb"/>
             <html:hidden property="positioncb"/>
             <html:hidden property="ttype"/>
             <html:hidden property="clientId"/>
             <html:hidden property="clientassetId"/>
             <html:hidden property="doView"/>
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file ="../header.jsp"%>
                <%@include file ="../sidemenu.jsp"%>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content">
                        <div class="row head_title_area dash_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="row">
                                    <div class="col">
                                        <span class="back_arrow">Dashboard</span>
                                    </div>
                                    <div class="col col-lg-9">
                                        <div class="row">
                                            <div class="col-lg-4">
                                                <div class="row d-flex align-items-center">
                                                    <label for="example-text-input" class="col-sm-5 com_label_value col-form-label text-right pd_0"><span>Select Client:</span></label>
                                                    <div class="col-sm-7">
                                                        <div class="row d-flex align-items-center">
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                                <html:select property="clientIdIndex" styleId="clientIdIndex" styleClass="form-select" onchange="javascript: setAssetDDL();" >
                                                                    <html:optionsCollection filter="false" property="clients" label="ddlLabel" value="ddlValue">
                                                                    </html:optionsCollection>
                                                                </html:select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div class="row d-flex align-items-center">
                                                    <label for="example-text-input" class="col-sm-5 com_label_value col-form-label text-right pd_0"><span>Select Asset:</span></label>
                                                    <div class="col-sm-7">
                                                        <div class="row d-flex align-items-center">
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                                <html:select property="assetIdIndex" styleId="assetIdIndex" styleClass="form-select" onchange="javascript: dashboard1();">
                                                                    <html:optionsCollection filter="false" property="assets" label="ddlLabel" value="ddlValue">
                                                                    </html:optionsCollection>
                                                                </html:select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div class="row d-flex align-items-center">
                                                    <label for="example-text-input" class="col-sm-5 com_label_value col-form-label text-right pd_0"><span>Select Dashboard:</span></label>
                                                    <div class="col-sm-7">
                                                        <div class="row d-flex align-items-center">
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                                <select class="form-select" id="stype" name="stype" onchange="javascript: directdashboard();">
                                                                    <option value="2">Crew Enrollment</option>
                                                                    <option value="3">Crew Recruitment</option>
                                                                    <option value="1" selected>Crew Rotation</option>
                                                                    <option value="4">Training & Development</option>
                                                                </select>
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
<%
                        if(personlisttotal > 0)
                        {
%>
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-12 col-xl-12">
                                    <div class="body-background dashboard_work_area mt_15">
                                        <div class="row">
                                            <div class="col-md-2 pd_0">
                                                <div class="full_width m_15">
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <div class="full_width shadow_div">
                                                            <html:select property="month" styleId="month" styleClass="form-select">
                                                                <html:option value="-1">- Month -</html:option>
                                                                <html:option value="1">Jan</html:option>
                                                                <html:option value="2">Feb</html:option>
                                                                <html:option value="3">Mar</html:option>
                                                                <html:option value="4">Apr</html:option>
                                                                <html:option value="5">May</html:option>
                                                                <html:option value="6">Jun</html:option>
                                                                <html:option value="7">Jul</html:option>
                                                                <html:option value="8">Aug</html:option>
                                                                <html:option value="9">Sep</html:option>
                                                                <html:option value="10">Oct</html:option>
                                                                <html:option value="11">Nov</html:option>
                                                                <html:option value="12">Dec</html:option>
                                                                </html:select>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <div class="full_width shadow_div">
                                                            <html:select property="year" styleId="year" styleClass="form-select" >
                                                                <html:option value="-1">- Year -</html:option>
                                                                <html:option value="2023">2023</html:option>
                                                                <html:option value="2024">2024</html:option>
                                                                <html:option value="2025">2025</html:option>
                                                                <html:option value="2026">2026</html:option>
                                                                <html:option value="2027">2027</html:option>
                                                                <html:option value="2028">2028</html:option>
                                                                <html:option value="2029">2029</html:option>
                                                                <html:option value="2030">2030</html:option>
                                                                <html:option value="2031">2031</html:option>
                                                                <html:option value="2032">2032</html:option>
                                                                <html:option value="2033">2033</html:option>
                                                                <html:option value="2034">2034</html:option>
                                                                <html:option value="2035">2035</html:option>
                                                                <html:option value="2036">2036</html:option>
                                                                <html:option value="2037">2037</html:option>
                                                                <html:option value="2038">2038</html:option>
                                                                <html:option value="2039">2039</html:option>
                                                                <html:option value="2040">2040</html:option>
                                                            </html:select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="shadow_div m_15 float-end text-right">
                                                    <a href="javascript: searchcr();" class="go_btn">Go</a>
                                                </div>
                                                <%if(positiontotal > 0){%>
                                                <div class="posi_perso shadow_div m_15">
                                                    <h3>Position</h3>
                                                    <div class="field_ic m_15">
                                                        <html:text property ="searchPosition" styleId="example-text-input" styleClass="form-control" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                    this.removeAttribute('readonly');
                                                                    this.blur();
                                                                    this.focus();
                                                                }"/>
                                                        <a href="javascript: setPositionlist();" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                    </div>
                                                        <div class="pos_per_list" id="sPosition">
                                                        <ul>
<%
                                                                    if(positiontotal > 0){
                                                                    for(int i = 0 ;i < positiontotal; i++){
                                                                    DashboardInfo positioninfo = (DashboardInfo)positionlist.get(i);
                                                                    if (positioninfo != null) {
%> 
                                                            <li>
                                                                <label class="mt-checkbox mt-checkbox-outline"> <%= positioninfo.getPosition() != null && !positioninfo.getPosition().equals("") ? positioninfo.getPosition() : ""%>
                                                                    <input type="checkbox" value="<%= positioninfo.getPositionId()%>" name="pcb" onchange =" javascript :setValposition();" <% if(dashboard.checkToStr(positioncb,""+positioninfo.getPositionId()))
                            {%>
                                 checked 
                         <%  }%>/>
                                                                    <span></span>
                                                                </label>	
                                                            </li>
                                                            <%}}}%>
                                                        </ul>
                                                    </div>
                                                </div>
                                                        <%}%>
                                                <%if(personlisttotal > 0){%>     
                                                <div class="posi_perso shadow_div m_15">
                                                    <h3>Personnel</h3>
                                                    <div class="field_ic m_15">
                                                        <html:text property ="searchPersonnel" styleId="example-text-input" styleClass="form-control" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                    this.removeAttribute('readonly');
                                                                    this.blur();
                                                                    this.focus();
                                                                }"/>
                                                        <a href="javascript: setPersonnellist();" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                    </div>
                                                        <div class="pos_per_list" id="sPersonnel">
                                                        <ul>
<%
                                                                    if(personlisttotal > 0){
                                                                    for(int i = 0 ;i < personlisttotal; i++){
                                                                    DashboardInfo personinfo = (DashboardInfo)personlist.get(i);
                                                                    if (personinfo != null) {
%> 
                                                            <li>
                                                                <label class="mt-checkbox mt-checkbox-outline"> <%= personinfo.getCandidateName() != null && !personinfo.getCandidateName().equals("") ? personinfo.getCandidateName() : ""%>
                                                                    <input type="checkbox" value="<%= personinfo.getCrewrotationId()%>" name="crewcb" onchange =" javascript :setValperson();" <% if(dashboard.checkToStr(crewrotationcb,""+personinfo.getCrewrotationId()))
                            {%>
                                 checked 
                         <%  }%>/>
                                                                    <span></span>
                                                                </label>	
                                                            </li>
                                                            <%}}}%>
                                                        </ul>
                                                    </div>
                                                </div>
                                                      
                                                <div class="posi_perso shadow_div m_15">
                                                    <h3>Legend</h3>
                                                    <div class="legend_list">
                                                        <ul>
                                                            <li><span class="round_circle normal_circle"></span> Normal</li>
                                                            <li><span class="round_circle early_circle"></span> Early Sign On</li>
                                                            <li><span class="round_circle delayed_sign_on"></span> Delayed Sign On</li>
                                                            <li><span class="round_circle signoff_circle"></span> Sign Off</li>
                                                            <li><span class="round_circle extended_circle"></span> Extended Sign Off</li>
                                                            <li><span class="round_circle overstay_circle"></span> Overstay Sign Off</li>
                                                            <li><span class="round_circle planning_circle"></span> Plan</li>
                                                            <li><span class="round_circle training_circle"></span> Training</li>
                                                            <li><span class="round_circle temp_pro_circle"></span> Temp. Promotion</li>
                                                            <li><span class="round_circle standby_circle"></span> S/BY</li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                          <%}%>
                                            </div>
                                                
                                            <div class="col-md-10">
                                                <div class="row">
                                                    <div class="col-md-12 m_15 div_1">
                                                        <div class="float-start">&nbsp;</div>
                                                        <div class="float-end">
                                                            <a href="javascript: view();" class="clear_sec mr_15"><img src="../assets/images/white_view.png" target = "_blank"> View Rotation</a>
                                                            <a href="javascript: resetsearchcr();" class="clear_sec mr_15">Clear Selection</a>
                                                            <a href="javascript: exporttoexcel('2');" class="home_export mr_15"> Export Excel</a>
                                                        </div>
                                                    </div>
                                                    
                                                    <div class="col-md-12 m_15 div_2">
                                                        <%if(candlisttotal > 0){%>
                                                        <div class="view">
                                                            <div class="table_wrapper_01">
                                                                <table class="table_01">
                                                                    <thead>
                                                                        <tr class="tr_01">
                                                                            <th class="ticky-col first_col" scope="col" colspan="4">&nbsp;</th>
                                                                            <th class="text-center" scope="col" colspan="<%=datelistdaytotal%>"><%=month%></th>
                                                                        </tr>
                                                                        <tr class="tr_02">                                                                            
                                                                            <th class="sticky-col first_col" scope="col">Position</th>
                                                                            <th class="sticky-col second_col" scope="col">Personnel</th>
                                                                            <th class="sticky-col third_col text-center" scope="col">Rotations</th>
                                                                            <th class="sticky-col fourth_col text-center" scope="col">Status</th>
                                                                <%
                                                                    if(datelistdaytotal > 0){
                                                                    for(int i = 0 ;i < datelistdaytotal;i++){
                                                                    String date = (String)datelistday.get(i);
                                                                    if (date != null) {
                                                                                    %> 
                                                                        <th scope="col"><%= date %></th>

                                                                        <%
                                                        }
                                                        }
}
                                                                        %> 
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody id="listbycasebody">
                                                                        
<%                                                                String lastdate = "",datestatus = "";
                                                                    if(datetotal > 0){
                                                                    lastdate = (String) datelist.get(datetotal - 1);
                                                                    String currentdate = dashboard.currDate();
                                                                    if(dashboard.getDiffTwoDateInt(currentdate, lastdate, "yyyy-MM-dd") > 0)
                                                                        datestatus = currentdate;
                                                                    else
                                                                        datestatus = lastdate;
                                                                    }
                                                                    if(candlisttotal > 0){
                                                                    for(int i = 0 ;i < candlisttotal;i++){
                                                                    DashboardInfo caninfo = (DashboardInfo)candlist.get(i);
                                                                    if (caninfo != null) {
                                                                        
                                                                    int countstatus = 0;
                                                                    if (listinfo != null)
                                                                    {                                                                
                                                                        countstatus = dashboard.checkwork((ArrayList)listinfo.getList4(), (ArrayList)listinfo.getList3(),(ArrayList)listinfo.getList2(), (ArrayList)listinfo.getList1(), 
                                                                        datestatus, (ArrayList)listinfo.getList5(), (ArrayList)listinfo.getList6(), (ArrayList)listinfo.getList7(), (ArrayList)listinfo.getList8(), (ArrayList)listinfo.getList9(), caninfo.getCandidateId(), caninfo.getPositionId()) ;
                                                                    }
                                                                    if(countstatus == 0 || countstatus == 4 || countstatus == 7){ onshore++; }
                                                                    else if(countstatus == 1 || countstatus == 5){normal++;}
                                                                    else if(countstatus == 2){extended++;}
                                                                    else if(countstatus == 3){overstay++;}
%> 
                                                                    <tr> 
                                                                    <td class="td_bg_white sticky-col first_col"><%= caninfo.getPosition() != null && !caninfo.getPosition().equals("") ? caninfo.getPosition() : ""%></td>
                                                                    <td class="td_bg_white sticky-col second_col"><%= caninfo.getCandidateName() != null && !caninfo.getCandidateName().equals("") ? caninfo.getCandidateName() : ""%></td>
                                                                    <td class="td_bg_white sticky-col third_col text-center"><%= caninfo.getRotations() %></td>
                                                                    <td class="td_bg_white sticky-col fourth_col text-center"><span class="round_circle <% if(caninfo.getStatus() == 1){%>signoff_circle<%}else{%>normal_circle<%}%>"></span></td>
<%
                                                                    if(datetotal > 0){
                                                                        int daystatus = 0;
                                                                    for(int j = 0 ;j < datetotal;j++){
                                                                    String date = (String)datelist.get(j);                                                                    
                                                                    if (date != null && listinfo != null)
                                                                    {
                                                                        daystatus = dashboard.checkwork((ArrayList)listinfo.getList4(), (ArrayList)listinfo.getList3(),(ArrayList)listinfo.getList2(), (ArrayList)listinfo.getList1(), date, (ArrayList)listinfo.getList5(), (ArrayList)listinfo.getList6(), (ArrayList)listinfo.getList7(), (ArrayList)listinfo.getList8(), (ArrayList)listinfo.getList9(), caninfo.getCandidateId(), caninfo.getPositionId()) ;
%>                                                                      
                                                                        <td class="<%if( daystatus == 8 ){ %>training_circle <%} else if(daystatus == 9){ %>temp_pro_circle <%} else if(daystatus == 10){%>standby_label<%} else if(daystatus == 1){ %>w_label <%} else if(daystatus == 2){%>yellow_sf_label<%} else if(daystatus == 3){%>orange_sf_label<%} else if(daystatus == 4){%>sf_label hand_cursor<%}else if(daystatus == 7){%>w_delayed_label<%}else if(daystatus == 5){%>w_early_label<%}else if(daystatus == 6){%>planning_label<%}%>" <%if(daystatus == 4){%>data-bs-toggle="modal" data-bs-target="#sf_modal" onclick = "javascript: setsfmodal('<%=caninfo.getCrewrotationId()%>','<%= date%>');"<%}else if(daystatus == 5){%>data-bs-toggle="modal" data-bs-target="#sf_modal" onclick = "javascript: setearlymodal('<%=caninfo.getCrewrotationId()%>','<%= date%>');"<%}else if(daystatus == 7){%>data-bs-toggle="modal" data-bs-target="#sf_modal" onclick = "javascript: setearlymodal('<%=caninfo.getCrewrotationId()%>','<%= date%>');"<%}else if(daystatus == 8){%>data-bs-toggle="modal" data-bs-target="#sf_modal" onclick = "javascript: settrainingmodal('<%=caninfo.getCrewrotationId()%>','<%= date%>');"<%}else if(daystatus == 9){%>data-bs-toggle="modal" data-bs-target="#sf_modal" onclick = "javascript: setTpmodal('<%=caninfo.getCrewrotationId()%>','<%= date%>');"<%}else if(daystatus == 10){%>data-bs-toggle="modal" data-bs-target="#sf_modal" onclick = "javascript: setSbymodal('<%=caninfo.getCrewrotationId()%>','<%= date%>');"<%}%> ><%if(daystatus == 1 || daystatus == 2 || daystatus == 3){ %>W<%} else if(daystatus == 7) {%>D<%}  else if(daystatus == 5) {%>R<%} else if(daystatus == 4){%>SO<%} else if(daystatus == 6){%>P<% } else if(daystatus == 8){%>TR<% } else if(daystatus == 9){%>TP<% } else if(daystatus == 10) {%>SB<%} else{%>&nbsp;<%}%></td>
                                                                    
<%                                                                  }
                                                                    
                                                            }
                                                        }
%> 
                                                                            </tr>
                                                                            <%
                                                        }
%>
<%
                                                        }
                                                        }
%> 
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                  <%}%>
                                                  
                                                    </div>

                                                    <div class="col-md-12 m_15 div_3">
                                                       <% if(candlisttotal > 0){ %>
                                                        <div class="all_record">
                                                            
                                                            <table class="">
                                                                <tbody>
                                                                    
                                                                    <tr>
                                                                        <td width="14%" class="text-center bg_white">
                                                                            <table width="100%" class="">
                                                                                <tr><td><span>All</span></td></tr>
                                                                                <tr><td>&nbsp;</td></tr>
                                                                                <tr><td><span class="rocord_value"><a href=" javascript: setlistbycase('-1');"><%= normal+extended+ overstay+onshore%></a></span></td></tr>
                                                                            </table>
                                                                        </td>
                                                                        <td width="6%">&nbsp;</td>
                                                                        <td width="60%" class="bg_white nor_ext_over">
                                                                            <table width="100%">
                                                                                <thead>
                                                                                    <tr>
                                                                                        <th width="%" colspan="3" class="text-center border_bottom"><span>Offshore: <%= normal+extended+ overstay%></span></th>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th width="33.33%" class="text-center"><span>Normal</span></th>
                                                                                        <th width="33.33%" class="text-center"><span>Extended</span></th>
                                                                                        <th width="33.33%" class="text-center right_shadow"><span>Overstay</span></th>
                                                                                    </tr>
                                                                                </thead>
                                                                                <tbody>
                                                                                    <tr>
                                                                                        <td class="text-center"><span class="rocord_value"><a href=" javascript: setlistbycase('1');"><%= normal %></a></span></td>
                                                                                        <td class="text-center"><span class="rocord_value"><a href=" javascript: setlistbycase('2');"><%= extended %></a></span></td>
                                                                                        <td class="text-center"><span class="rocord_value"><a href=" javascript: setlistbycase('3');"><%= overstay %></a></span></td>
                                                                                    </tr>
                                                                                </tbody>
                                                                            </table>	
                                                                        </td>
                                                                        <td width="6%">&nbsp;</td>
                                                                        <td width="14%" class="text-center bg_white">
                                                                            <table width="100%" class="">
                                                                                <tr><td><span>Onshore</span></td></tr>
                                                                                <tr><td>&nbsp;</td></tr>
                                                                                <tr><td><span class="rocord_value"><a href=" javascript: setlistbycase('4');"><%=  dashboard.changeNum(onshore,2) %></a></span></td></tr>
                                                                            </table>	
                                                                        </td>
                                                                    </tr>
                                                      
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <%}%>
                                                    </div>
                                                             
                                                    <div class="col-md-12 m_15 mt_50 div_4">
                                                        <%if(candlisttotal > 0){%>
                                                        <div class="float-start">
                                                            <div class="shadow_div float-start mr_15">
                                                                <div class="input-daterange input-group input-addon">
                                                                    <html:text property="fromdate" styleId="wesl_from_dt" styleClass="form-control wesl_from_dt"/>
                                                                    <script type="text/javascript">
                                                                        document.getElementById("wesl_from_dt").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                                    </script>
                                                                    <div class="input-group-add">
                                                                        <span class="input-group-text">To</span>
                                                                    </div>
                                                                    <html:text property="todate" styleId="wesl_to_dt" styleClass="form-control wesl_from_dt"/>
                                                                    <script type="text/javascript">
                                                                        document.getElementById("wesl_to_dt").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                                    </script>
                                                                </div>
                                                            </div>
                                                            <div class="float-start">
                                                                <a href="javascript: searchcrpp();" class="go_btn">Go</a>
                                                            </div>
                                                        </div>
                                                        <%}%>          
                                                        <%if(dtotal > 0){%>
                                                        <div class="float-end">
                                                            <a href="javascript: exporttoexcel('1');" class="home_export"><i class="fas fa-file-export"></i> Export to Excel</a>
                                                        </div>
                                                        <%}%>          
                                                    </div>
                                                               
                                                               
                                                    <div class="col-lg-12 dash_list_table div_5" id="counttableid">
                                                        <%if(dtotal > 0){%>
                                                        <div class="table-rep-plugin sort_table">
                                                            <div class="table-responsive mb-0">
                                                                <table id="tech-companies-1" class="table table-striped">
                                                                    <thead>
                                                                        <tr>
                                                                            <th width="15%"><span><b>Position</b> </span></th>
                                                                            <th width="15%"><span><b>Personnel</b></span></th>
                                                                            <th width="6%" class="text-center"><span><b>Rotations</b></span></th>
                                                                            <th width="6%" class="text-center"><span><b>Rotation Days</b></span></th>
                                                                            <th width="6%" class="text-center"><span><b>&#60;Rotation Days</b></span></th>
                                                                            <th width="6%" class="text-center"><span><b>Normal</b></span></th>
                                                                            <th width="6%" class="text-center"><span><b>&#62;Rotation Days Extended</b></span></th>
                                                                            <th width="5%" class="text-center"><span><b>Over<br/> Stay</b></span></th>
                                                                            <th width="6%" class="text-center"><span><b>Total<br/> Offshore<br/> Days</b></span></th>
                                                                            <th width="6%" class="text-center"><span><b>Total <br/>Onshore<br/> Days</b></span></th>
                                                                            <th width="5%" class="text-center"><span><b>Office<br/> Work</b></span></th>
                                                                            <th width="6%" class="text-center"><span><b>Training</b></span></th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                        <%
                                                        for(int i = 0 ;i < dtotal;i++){
                                                        DashboardInfo oinfo = (DashboardInfo)dlist.get(i);
                                                        if (oinfo != null) {

                                                        %>                                                                        
                                                                        <tr>
                                                                            <td><%= oinfo.getPosition() != null && !oinfo.getPosition().equals("") ? oinfo.getPosition() : ""%></td>
                                                                            <td><%= oinfo.getCandidateName() != null && !oinfo.getCandidateName().equals("") ? oinfo.getCandidateName() : ""%></td>
                                                                            <td class="text-center"><%= oinfo.getRotations() %></td>
                                                                            <td class="text-center"><%= oinfo.getNoofdays() %></td>
                                                                            <td class="text-center"><%= oinfo.getEarly() %></td>
                                                                            <td class="text-center"><%= oinfo.getNormal() %></td>
                                                                            <td class="text-center"><%= oinfo.getExtended() %></td>
                                                                            <td class="text-center"><%= oinfo.getOverstay() %></td>
                                                                            <td class="text-center"><%= oinfo.getNormal()+oinfo.getExtended()+oinfo.getOverstay() %></td>
                                                                            <td class="text-center"><%= oinfo.getOfficework()+oinfo.getTraining()+oinfo.getAvailable() %></td>
                                                                            <td class="text-center"><%= oinfo.getOfficework() %></td>
                                                                            <td class="text-center"><%= oinfo.getTraining() %></td>
                                                                        </tr>
                                                        <%
                                                        }
                                                        }
                                                        %>                                                                        
                                                                    </tbody>
                                                                </table>
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
<%
                    }
%>
                    </div>
                    <!-- End Page-content -->
                </div>
                <!-- end main content-->
            </div>
            <!-- END layout-wrapper -->

            <%@include file ="../footer.jsp"%>
            <div id="sf_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row" id="sfmodal">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- JAVASCRIPT -->
            <script src="../assets/libs/jquery/jquery.min.js"></script>
            <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script>
            <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
            <script src="../assets/js/bootstrap-datepicker.min.js"></script>
            <script src="../assets/js/app.js"></script>
            <!-- Responsive Table js -->
            <script src="../assets/js/rwd-table.min.js"></script>
            <script src="../assets/js/table-responsive.init.js"></script>
            <script src="/jxp/assets/js/sweetalert2.min.js"></script>
            <% if(!crId.equals("")){%>
            <script type="text/javascript">
                    $(window).on('load', function () {                
                        window.location.hash="<%=crId%>";
                    });
            </script>
            <%}%>
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