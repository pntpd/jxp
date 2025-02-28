<%@page import="java.util.Stack"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.web.jxp.competency.CompetencyInfo"%>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<jsp:useBean id="competency" class="com.web.jxp.competency.Competency" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 8, submtp = 92;
            if (session.getAttribute("LOGININFO") == null) {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
        }
            //position list
            ArrayList positionlist = new ArrayList();
            if (session.getAttribute("COMP_POSITIONLIST") != null) {
                positionlist = (ArrayList) session.getAttribute("COMP_POSITIONLIST");
            }
            int positiontotal = positionlist.size();
           
            // personlist
            ArrayList personlist = new ArrayList();
            if (session.getAttribute("COMP_PERSONALLIST") != null) {
                personlist = (ArrayList) session.getAttribute("COMP_PERSONALLIST");
            }
            int personlisttotal = personlist.size();
            
            // DeptList
            ArrayList deptlist = new ArrayList();
            if (session.getAttribute("COMP_DEPTLIST") != null) {
                deptlist = (ArrayList) session.getAttribute("COMP_DEPTLIST");
            }
            int deptlisttotal = deptlist.size();
            
            // Comp role
            ArrayList comprolelist = new ArrayList();
            if (session.getAttribute("COMP_COMPROLELIST") != null) {
                comprolelist = (ArrayList) session.getAttribute("COMP_COMPROLELIST");
            }
            int comprolelisttotal = comprolelist.size();

            // Comp role-table 
            ArrayList rolelist = new ArrayList();
            if (session.getAttribute("ROLE_TABLE") != null) {
                rolelist = (ArrayList) session.getAttribute("ROLE_TABLE");
            }
            int rolelist_size = rolelist.size();
            
            // Comp position count
            ArrayList positioncountlist = new ArrayList();
            if (session.getAttribute("COMP_POSITIONCOUNTLIST") != null) {
                positioncountlist = (ArrayList) session.getAttribute("COMP_POSITIONCOUNTLIST");
            }
            int positioncountlisttotal = positioncountlist.size();

            String position = "", getstr = "";
            int count1 = 0, count2 = 0, count3 = 0;
            StringBuilder sb = new StringBuilder();
            if(positioncountlisttotal > 0)
            {
                for(int  i = 0; i < positioncountlisttotal; i++)
                {
                    CompetencyInfo ginfo = (CompetencyInfo) positioncountlist.get(i);                    
                    {
                        position = ginfo.getDdlLabel() != null ? ginfo.getDdlLabel(): "";
                        count1 = ginfo.getCount1();
                        count2 = ginfo.getCount2();
                        count3 = ginfo.getCount3();
                        if(i == positioncountlisttotal - 1)
                             sb.append("{\"year\":\""+position+" \",\"competent\": "+count1+",\"not_competent\":"+count2+",\"pending\":"+count3+"}");
                        else
                             sb.append("{\"year\":\""+position+" \",\"competent\": "+count1+",\"not_competent\":"+count2+",\"pending\":"+count3+"},");
                    }
                }
            }
            getstr = sb.toString();
            sb.setLength(0);

             //checkbox list
            String positioncb = "", crewrotationcb = "", comprolecb = "" , departmentcb = "";
            if (session.getAttribute("POSITIONCB") != null) {
                positioncb = (String) session.getAttribute("POSITIONCB");
            }
            if (session.getAttribute("CREWROTATIONCB") != null) {
                crewrotationcb = (String) session.getAttribute("CREWROTATIONCB");
            }
            if (session.getAttribute("ROLECB") != null) {
                comprolecb = (String) session.getAttribute("ROLECB");
            }
            if (session.getAttribute("DEPTCB") != null) {
                departmentcb = (String) session.getAttribute("DEPTCB");
            }

            //Personal-position            
            ArrayList perposlist = new ArrayList();
            if (session.getAttribute("PER_POSITION") != null) {
                perposlist = (ArrayList) session.getAttribute("PER_POSITION");
            }
            int perposlisttotal = perposlist.size();

            //Trackerlist
            ArrayList trackerlist = new ArrayList();
            if (session.getAttribute("TRACKER_LIST") != null) {
                trackerlist = (ArrayList) session.getAttribute("TRACKER_LIST");
            }
            int trackerlisttotal = trackerlist.size();

            ArrayList alist = new ArrayList();
            if (session.getAttribute("ALIST") != null) {
                alist = (ArrayList) session.getAttribute("ALIST");
            }

            //DEPT_TABLELIST
            ArrayList dept_tablelist = new ArrayList();
            if (session.getAttribute("DEPT_TABLELIST") != null) {
                dept_tablelist = (ArrayList) session.getAttribute("DEPT_TABLELIST");
            }
            int dept_tablesize = dept_tablelist.size();

            int arr[] = new int[17];
            int onshore = 0, offshore = 0, totalcrew = 0, total_positions = 0, assigned_positions = 0,
            assigned_crew = 0, unassigned_crew = 0, pass = 0, fail = 0, appeal = 0, totaltracker = 0,
            pendingtracker = 0, expired1 = 0, expired2 = 0, expired3 = 0, expired = 0, total_competencies =0;

            if(session.getAttribute("COUNTCOMPETENCY") != null)
            {               
                arr = (int[]) session.getAttribute("COUNTCOMPETENCY");
                session.removeAttribute("COUNTCOMPETENCY");
                if(arr != null && arr.length == 17)
                {
                    onshore = arr[0];
                    offshore = arr[1];
                    totalcrew = arr[2];
                    total_positions = arr[3];
                    assigned_positions = arr[4];
                    assigned_crew = arr[5];
                    unassigned_crew = arr[6];
                    pass = arr[7]; 
                    fail = arr[8]; 
                    appeal = arr[9]; 
                    pendingtracker = arr[10];
                    expired1 = arr[11];
                    expired2 = arr[12];
                    expired3 = arr[13];
                    expired = arr[14];           
                    totaltracker = arr[15];    
                    total_competencies = arr[16];    

                }
            }
            double pass_per  = 0;
            if(totaltracker > 0)
            {
                pass_per = pass / (double) totaltracker * 100.0;
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
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/competency.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/competency/CompetencyAction.do" onsubmit="return false;" enctype="multipart/form-data">
            <html:hidden property = "competencyId"/>
            <html:hidden property = "crewrotationId"/>
            <html:hidden property = "pcodeId"/>
            <html:hidden property = "doSearch"/>
            <html:hidden property = "searchDetails"/>
            <html:hidden property = "positioncb"/>
            <html:hidden property = "comprolecb"/>
            <html:hidden property = "departmentcb"/>
            <html:hidden property = "crewrotationcb"/>
            <html:hidden property = "type"/>
            <html:hidden property = "expType"/>
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
                                    <span class="back_arrow">Competency</span>
                                </div>
                                <div class="col col-lg-10">
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
                                                            <html:select property="assetIdIndex" styleId="assetIdIndex" styleClass="form-select" onchange="javascript: getCompetency();">
                                                                <html:optionsCollection filter="false" property="assets" label="ddlLabel" value="ddlValue">
                                                                </html:optionsCollection>
                                                            </html:select>
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
                                        <div class="col-md-2 pd_0 left_filter_area">
                                           <div class="row d-flex align-items-center justify-content-center m_15">
                                                    <div class="col float-start text-left posi_perso">
                                                            <h3>Filter</h3>
                                                    </div>
                                                    <div class="col float-end text-right">
                                                            <a href="javascript: getCompetency();" class="go_btn shadow_div">Go</a>
                                                    </div>
                                            </div>
                                            
                                            
                                            
                                            
                                             <%if(deptlisttotal > 0){%>
                                            <div class="posi_perso shadow_div m_15">
                                                <h3>Department</h3>
                                                <div class="field_ic m_15">
                                                    <html:text property ="searchDept" styleId="example-text-input" styleClass="form-control" maxlength="200"  readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                    this.removeAttribute('readonly');
                                                                    this.blur();
                                                                    this.focus();
                                                                }"/>
                                                    <a href="javascript: setList('1');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                </div>
                                                <div class="pos_per_list" id="sDept">
                                                    <ul>
<%
                                                        if(deptlisttotal > 0){
                                                        for(int i = 0 ;i < deptlisttotal; i++){
                                                        CompetencyInfo deptinfo = (CompetencyInfo)deptlist.get(i);
                                                        if (deptinfo != null) {
%> 
                                                        <li>
                                                             <label class="mt-checkbox mt-checkbox-outline"> <%= deptinfo.getDdlLabel() != null && !deptinfo.getDdlLabel().equals("") ? deptinfo.getDdlLabel() : ""%>
                                                                    <input type="checkbox" value="<%= deptinfo.getDdlValue()%>" name="deptcb" onchange =" javascript: setVal('1');" <% if(competency.checkToStr(departmentcb,""+deptinfo.getDdlValue()))
                            {%>
                                 checked 
                         <%  }%>/>
                                                                <span></span>
                                                            </label>	
                                                        </li>                                                       
<%                                                              }
                                                            }
                                                    }
%>
                                                        
                                                    </ul>
                                                </div>
                                            </div>
                                             <%}%>
                                            
                                            <%if(positiontotal > 0){%>
                                            <div class="posi_perso shadow_div m_15">
                                                <h3>Position</h3>
                                                <div class="field_ic m_15">
                                                    <html:text property ="searchPosition" styleId="example-text-input" styleClass="form-control" maxlength="200" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                    this.removeAttribute('readonly');
                                                                    this.blur();
                                                                    this.focus();
                                                                }"/>
                                                    <a href="javascript: setList('2');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                </div>
                                                <div class="pos_per_list" id="sPosition">
                                                    <ul>
<%
                                                        if(positiontotal > 0){
                                                        for(int i = 0 ;i < positiontotal; i++){
                                                        CompetencyInfo positioninfo = (CompetencyInfo)positionlist.get(i);
                                                        if (positioninfo != null) {
%> 
                                                        <li>
                                                            <label class="mt-checkbox mt-checkbox-outline"> <%= positioninfo.getDdlLabel() != null && !positioninfo.getDdlLabel().equals("") ? positioninfo.getDdlLabel() : ""%>
                                                                    <input type="checkbox" value="<%= positioninfo.getDdlValue()%>" name="pcb" onchange =" javascript: setVal('2');" <% if(competency.checkToStr(positioncb,""+positioninfo.getDdlValue()))
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
                                                    <html:text property ="searchName" styleId="example-text-input" styleClass="form-control" maxlength="200" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                    this.removeAttribute('readonly');
                                                                    this.blur();
                                                                    this.focus();
                                                                }"/>
                                                    <a href="javascript: setList('3');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                </div>
                                                <div class="pos_per_list" id="sPersonnel">
                                                    <ul>
<%
                                                            if(personlisttotal > 0){
                                                            for(int i = 0 ;i < personlisttotal; i++){
                                                            CompetencyInfo personinfo = (CompetencyInfo)personlist.get(i);
                                                            if (personinfo != null) {
%> 
                                                        <li>
                                                            <label class="mt-checkbox mt-checkbox-outline"> <%= personinfo.getDdlLabel() != null && !personinfo.getDdlLabel().equals("") ? personinfo.getDdlLabel() : ""%>
                                                            <input type="checkbox" value="<%= personinfo.getDdlValue()%>" name="crewcb" onchange =" javascript: setVal('3');" <% if(competency.checkToStr(crewrotationcb,""+personinfo.getDdlValue()))
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
                                            
                                            <%if(comprolelisttotal > 0){%>
                                            <div class="posi_perso shadow_div m_15">
                                                <h3>Role Competencies</h3>
                                                <div class="field_ic m_15">
                                                    <html:text property ="searchRole" styleId="example-text-input" styleClass="form-control" maxlength="200" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                    this.removeAttribute('readonly');
                                                                    this.blur();
                                                                    this.focus();
                                                                }"/>
                                                    <a href="javascript: setList('4');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                </div>
                                                <div class="pos_per_list" id="sRole">
                                                    <ul>
<%
                                                                    if(comprolelisttotal > 0){
                                                                    for(int i = 0 ;i < comprolelisttotal; i++){
                                                                    CompetencyInfo comproleinfo = (CompetencyInfo)comprolelist.get(i);
                                                                    if (comproleinfo != null) {
%> 
                                                        <li>
                                                             <label class="mt-checkbox mt-checkbox-outline"> <%= comproleinfo.getDdlLabel() != null && !comproleinfo.getDdlLabel().equals("") ? comproleinfo.getDdlLabel() : ""%>
                                                                     <input type="checkbox" value="<%= comproleinfo.getDdlValue()%>" name="rolecb" onchange =" javascript: setVal('4');" <% if(competency.checkToStr(comprolecb,""+comproleinfo.getDdlValue()))
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
                                            <div class="row d-flex align-items-center justify-content-center m_15">
                                                    <div class="col float-start text-left posi_perso">
                                                            <h3>Filter</h3>
                                                    </div>
                                                    <div class="col float-end text-right">
                                                            <a href="javascript: getCompetency();" class="go_btn shadow_div">Go</a>
                                                    </div>
                                            </div>
                                            <div class="posi_perso shadow_div m_15">
                                                <h3>Legend</h3>
                                                <div class="legend_list">
                                                    <ul>
                                                        <li><span class="round_circle circle_unassigned"></span> Unassigned</li>
                                                        <li><span class="round_circle circle_pending"></span> In Progress</li>
                                                        <li><span class="round_circle circle_complete"></span> Competent</li>
                                                        <li><span class="round_circle circle_exipred"></span> Not Yet Competent</li>
                                                        <li><span class="round_circle circle_exipry"></span> Appealed</li>
                                                        <li><span class="round_circle circle_expired_bg"></span> Expired</li>
                                                    </ul>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="col-md-10">
                                            <div class="row">
                                                <div class="col-md-12 m_15">
                                                    <div class="float-start">&nbsp;</div>
                                                    <div class="float-end com_pen_exp_clear">
                                                        <a href="javascript:;" onclick="javascript: gototracker();" class="view_det_bg fw-bold">View Details</a>
                                                        <a href="javascript:;" onclick="javascript: showDetail('5');" class="pen_exp_bg fw-bold">Pending: <%=pendingtracker%></a>
                                                        <a href="javascript:;" onclick="javascript: showDetail('1');" class="pen_exp_bg fw-bold">Expired: <%=expired%></a>
                                                        <a href="javascript:;" onclick="javascript: showDetail('2');" class="pen_exp_bg">Expiry in 45 Days: <%=expired1%></a>
                                                        <a href="javascript:;" onclick="javascript: showDetail('3');" class="pen_exp_bg"> Expiry in 45-65 Days: <%=expired2%></a>
                                                        <a href="javascript:;" onclick="javascript: showDetail('4');" class="pen_exp_bg"> Expiry in 90 Days: <%=expired3%></a>
                                                        <a href="javascript: clear('1');" class="home_export"> Clear Selection</a>
                                                    </div>
                                                </div>                                                       
                                                
                                                <div class="col-md-12 m_15">
                                                    <div class="row">
                                                        <div class="col-md-3">
                                                            <div class="row">
                                                                <div class="col-md-6 pd_right_0">
                                                                    <div class="shadow_div graph_record bg_white total_records">
                                                                        <label class="">Total Position</label>
                                                                        <span><%=total_positions%></span>
                                                                    </div>	
                                                                </div>
                                                                <div class="col-md-6 pd_right_0">
                                                                    <div class="shadow_div graph_record m_15 bg_white total_records">
                                                                         <label class="">Total Personnel</label>
                                                                            <span><%=offshore%></span>
                                                                    </div>	
                                                                </div>
                                                                <div class="col-md-6 pd_right_0">
                                                                    <div class="shadow_div graph_record m_15 bg_white total_records">
                                                                        <label class="">Position Assigned</label>
                                                                       <span><%=assigned_positions%></span>
                                                                    </div>	
                                                                </div>
                                                                <div class="col-md-6 pd_right_0">
                                                                    <div class="shadow_div graph_record m_15 bg_white total_records">
                                                                        <label class="">Personnel Assigned</label>
                                                                        <span><%=assigned_crew%></span>
                                                                    </div>	
                                                                </div>
                                                                <div class="col-md-6 pd_right_0">
                                                                    <div class="shadow_div graph_record bg_white total_records">
                                                                        <label class="">Onboarded</label>
                                                                        <span><%=totalcrew%></span>
                                                                    </div>	
                                                                </div>
                                                                <div class="col-md-6 pd_right_0">
                                                                    <div class="shadow_div graph_record bg_white total_records">
                                                                        <label class="">To Be Added</label>
                                                                        <span><%=unassigned_crew%></span>
                                                                    </div>	
                                                                </div>
                                                            </div>
                                                        </div>
                                                                    
                                                        <div class="col-md-4 ">
                                                            <div class="graph_box graph_title">
                                                                <div class="row">
                                                                    <div class="col-md-12 m_15">
                                                                        <div class="float-start"><h2>Competency Assessments %</h2></div>
                                                                    </div>
                                                                    <div class="col-md-12 position-relative">
                                                                        <div id="echarts_pie_01" style="height:140px;"></div>
                                                                        <span class="total_percentage comp_per grey_text"><%= competency.getDecimal(pass_per) %></span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-5">
                                                            <div class="graph_box stack_graph">
                                                                <div class="row">
                                                                    <div class="col-md-12 position-relative">
                                                                        <div id="chartdiv_1" style="height:205px;"></div>
                                                                        <div class="full_width talent_pool_color com_dash_graph">
                                                                            <ul>
                                                                                <li><span class="box_bg" style="background-color:#4477aa;"></span> Competent</li>
                                                                                <li><span class="box_bg" style="background-color:#f93f17;"></span> Not Yet Competent</li>
                                                                                <li><span class="box_bg" style="background-color:#b6d7ea;"></span> Pending</li>
                                                                            </ul>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>	
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-12 m_15">
                                                    <div class="row">
                                                        <div class="col-md-4">
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <div class="shadow_div graph_record bg_white total_records">
                                                                        <label class="">Total Role Competencies</label>
                                                                        <span><%=total_competencies%></span>
                                                                    </div>	
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-8">
                                                            <div class="row">
                                                                <div class="col-md-3">
                                                                    <div class="shadow_div graph_record bg_white total_records">
                                                                        <label class="">Competent</label>
                                                                        <span class="comp_text"><%=pass%></span>
                                                                    </div>	
                                                                </div>
                                                                <div class="col-md-3">
                                                                    <div class="shadow_div graph_record bg_white total_records">
                                                                        <label class="">Not Yet Competent</label>
                                                                        <span class="not_yet_comp_text"><%=fail%></span>
                                                                    </div>	
                                                                </div>
                                                                <div class="col-md-3">
                                                                    <div class="shadow_div graph_record bg_white total_records">
                                                                        <label class="">Pending</label>
                                                                        <span class="pen_text"><%=pendingtracker%></span>
                                                                    </div>	
                                                                </div>
                                                                <div class="col-md-3">
                                                                    <div class="shadow_div graph_record bg_white total_records">
                                                                        <label class="">Appeals</label>
                                                                        <span class="app_text"><%=appeal%></span>
                                                                    </div>	
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                                    
                                                <div class="col-md-12 m_15 mt_15">
                                                    <div class="float-end">
                                                        <a href="javascript: exporttoexcel();" class="home_export"><i class="fas fa-file-export"></i> Export to Excel</a>
                                                    </div>
                                                </div>
                                                                    
                                                <div class="col-lg-12 total_role_table">
                                                    <div class="table-rep-plugin sort_table">
                                                        <div class="table-responsive1 mb-0">
                                                            <table class="table table-striped">
                                                                <thead>
                                                                    <tr>
                                                                        <th class="text-center" width="%"><span><b>Department</b></span></th>
                                                                        <th class="text-center" width="%"><span><b>Total <br/> Positions</b></span></th>
                                                                        <th class="text-center" width="%"><span><b>Total <br/> Personnel</b></span></th>
                                                                        <th class="text-center" width="%"><span><b>Total Role <br/> Competencies</b></span></th>
                                                                        <th class="text-center" width="%"><span><b>Total Role <br/> Competencies <br/>Assigned</b></span></th>
                                                                        <th class="text-center" width="%"><span><b>Total Role <br/> Competencies <br/>Submitted</b></span></th>
                                                                        <th class="text-center" width="%"><span><b>Total Role <br/> Competencies <br/>checked by Assessor</b></span></th>
                                                                        <th class="text-center" width="%"><span><b>Total Role <br/> Competencies with <br/> satisfactory result</b></span></th>
                                                                        <th class="text-center" width="%"><span><b>Total Role <br/> Competencies <br/>needs further action</b></span></th>
                                                                        <th class="text-center" width="%"><span><b>% Completion of <br/> Role Competency</b></span></th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <tr>
<%  
                                                                    double completion_per  = 0;
                                                                    for(int i = 0 ;i < dept_tablesize;i++){
                                                                    CompetencyInfo info = (CompetencyInfo) dept_tablelist.get(i);
                                                                    if (info != null) {
                                                                       
                                                                        completion_per = (info.getCount5() /(double)info.getCount4()) * 100.0;
%>   
                                                                        <td class="text-center"><%=( info.getDepartment() != null && !info.getDepartment().equals("") ? info.getDepartment() : "")%></td>
                                                                        <td class="text-center"><%=info.getCount1()%></td>
                                                                        <td class="text-center"><%=info.getCount2()%></td>
                                                                        <td class="text-center"><%=info.getCount3()%></td>
                                                                        <td class="text-center"><%=info.getCount4()%></td>
                                                                        <td class="text-center"><%=info.getCount5()%></td>
                                                                        <td class="text-center"><%=info.getCount6()%></td>
                                                                        <td class="text-center"><%=info.getCount7()%></td>
                                                                        <td class="text-center"><%=info.getCount8()%></td>
                                                                        <%if(completion_per > 0){%>
                                                                            <td class="text-center"><%=competency.getDecimal(completion_per) %> </td>
                                                                        <%}else{%>
                                                                            <td class="text-center">-</td>
                                                                        <%}%>
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

                                                <div class="col-md-12 m_15 name_role_comp_table">
                                                    
                                                    <div class="view">
                                                        <div class="table_wrapper_01">
                                                            <table class="table_01">
                                                                <thead>
                                                                    <tr class="tr_01">
                                                                        <th class="sticky-col first_col" colspan="2">&nbsp;</th>
                                                                        <th class="text-center" colspan="30">Name of Role Competency</th>
                                                                    </tr>
                                                                    <tr class="tr_02">
                                                                        <th class="sticky-col first_col">Position</th>
                                                                        <th class="sticky-col second_col">Personnel</th>
 <%
                                                                        for(int i = 0 ; i< rolelist_size; i++)
                                                                        {
                                                                                CompetencyInfo info = (CompetencyInfo) rolelist.get(i);
                                                                                if (info != null) 
                                                                                { 
 %>                                                                       
                                                                        
                                                                                    <th class="text-center"><span class="vert_text" title="<%= (info.getDdlLabel() != null && !info.getDdlLabel().equals("") ? info.getDdlLabel() : "") %>"><%= (info.getDdlLabel() != null && !info.getDdlLabel().equals("") ? info.getDdlLabel() : "") %></span></th>
<%
                                                                                }
                                                                        }
%>
                                                                    </tr>
                                                                </thead>                                                                
                                                                <tbody>
<%
                                                            for(int i = 0 ; i< perposlisttotal; i++)
                                                            {
                                                                CompetencyInfo info = (CompetencyInfo)perposlist.get(i);
                                                                if (info != null) 
                                                                { 
                                                                    int crewrotationId = info.getCrewrotationId();
                                                                    int positionId = info.getPositionId();
                                                                    
%>     
                                                                    <tr>
                                                                        <td class="sticky-col td_bg_white first_col"><%=( info.getPosition() != null && !info.getPosition().equals("") ? info.getPosition() : "") %></td>
                                                                        <td class="sticky-col td_bg_white second_col"><%= (info.getName() != null && !info.getName().equals("") ? info.getName() : "") %></td>
<%
                                                                        String stname = "" , classsName ="";
                                                                        int trackerId = 0;
                                                                        for(int j = 0; j < rolelist_size; j++)
                                                                        {
                                                                            CompetencyInfo rinfo = (CompetencyInfo)  rolelist.get(j);
                                                                            if (rinfo != null) 
                                                                            { 
                                                                                int pcodeId = rinfo.getDdlValue();  
                                                                                CompetencyInfo innerinfo = competency.getfromList(trackerlist, alist, positionId, crewrotationId, pcodeId);
                                                                                trackerId = 0;
                                                                                stname = ""; classsName = "";
                                                                                if(innerinfo != null)
                                                                                {
                                                                                    trackerId = innerinfo.getCrewrotationId();
                                                                                    stname = innerinfo.getName() != null ? innerinfo.getName() : "";
                                                                                    classsName = innerinfo.getPosition() != null ? innerinfo.getPosition() : "";
                                                                                }
%>                                                                            
                                                                                
                                                                                <%if(stname.equals("UA")){%>
                                                                                <td class="<%=classsName%>"><%=stname%></td>
                                                                                <%}else if(!stname.equals("")){%>
                                                                                    <td class="text-center text_white hand_cursor <%=classsName%>"  data-bs-toggle="modal" data-bs-target="#unassigned_details_modal_01" onclick="javascript: getTrackerDetails('<%=trackerId%>');"><%=stname%></td><%}else{%>
                                                                                <td class="text-center">-</td><%}%>
<%
                                                                            }
                                                                        }
%>                                                                        
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

        <div id="unassigned_details_modal_01" class="modal fade parameter_modal large_modal">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="personalmodal">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>        
        <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button data-bs-toggle="modal" data-bs-target="#unassigned_details_modal_01" type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        <span class="resume_title">View Attachment</span>
                        <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                        <a href="" class="down_btn" id="diframe" download target="_blank"><img src="../assets/images/download.png"/></a>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <iframe class="pdf_mode" src="" id="iframe"></iframe>                                        
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="history_modal" class="modal fade multiple_position parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" data-keyboard="false" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button data-bs-toggle="modal" data-bs-target="#unassigned_details_modal_01" type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <h2>Tracker History</h2>
                                <div class="full_width client_position_table">
                                    <div class="table-rep-plugin sort_table">
                                        <div class="table-responsive mb-0">
                                            <table id="tech-companies-1" class="table table-striped">
                                                <thead>
                                                    <tr>
                                                        <th width="%"><span><b>Date / Time</b> </span></th>
                                                        <th width="%"><span><b>User Name</b></span></th>
                                                        <th width="%"><span><b>Action</b></span></th>
                                                    </tr>
                                                </thead>
                                                <tbody id="historydiv">
                                                    
                                                </tbody>
                                            </table>
                                        </div>		
                                    </div>
                                </div>                            
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="online_submission_modal" class="modal fade parameter_modal define_modal large_modal">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header"> 
                        <button type="button" data-bs-toggle="modal" data-bs-target="#know_ass_details_modal_02" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="row flex-center align-items-center mb_10" id="questiondiv">
                                    
                                </div>
                                <div class="row client_position_table">
                                    <div class="col-md-12 table-rep-plugin sort_table">
                                        <div class="table-responsive mb-0">
                                            <table id="tech-companies-1" class="table table-striped">
                                                <tbody>
                                                </tbody>
                                            </table>
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

            <%@include file ="../footer.jsp"%>
            
            <!-- JAVASCRIPT -->
            <script src="../assets/libs/jquery/jquery.min.js"></script>
            <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
            <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script>
            <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
            <script src="../assets/js/app.js"></script>
            
             <script src="../assets/js/echarts.js" type="text/javascript"></script>
            <script src="../assets/js/stack/index.js"></script>
            <script src="../assets/js/stack/xy.js"></script>
            <script src="../assets/js/stack/Animated.js"></script>

            <!-- Responsive Table js -->
            <script src="../assets/js/rwd-table.min.js"></script>
            <script src="../assets/js/table-responsive.init.js"></script>
            <script src="/jxp/assets/js/sweetalert2.min.js"></script>
            
            <script>
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
//                addLoadEvent(setAssetDDL());             
            </script>
           <script>
            jQuery(document).ready(function() {
            require.config({
            paths: {echarts: "../assets/global/plugins/echarts/"}}),
                    require(["echarts", "echarts/chart/pie"], function(e) {


                    var b = e.init(document.getElementById("echarts_pie_01"));
                    b.setOption({
                    tooltip: {
                    show: !0,
                            formatter: "{b} : {c} ({d}%)"
                    },
                            legend: {
                            orient: "vertical",
                                    x: "left",
                                    data: [""]
                            },
                            calculable: !0,
                            series: [
                            {
                            name: "",
                                    type: "pie",
                                    center: ["50%", 70],
                                    radius: [70, 60],
                                    data: [
                                    {value: <%=pass%>, itemStyle: {normal: {color: '#b6d7ea', label :{show : false}, labelLine :{show : false}}}, name: "Competent"},
                                    {value: <%=totaltracker%>, itemStyle: {normal: {color: '#4477aa', label :{show : false}, labelLine :{show : false}}}, name: "Total"}
                                    ]
                            },
                            ]
                    })

                    })
            });
        </script>
        <!-- Chart code -->

       <script>
        am5.ready(function() {

                var root = am5.Root.new("chartdiv_1");

                root.setThemes([
                  am5themes_Animated.new(root)
                ]);


                var chart = root.container.children.push(am5xy.XYChart.new(root, {
                        panX: true,
                        panY: true,
                        wheelX: "panX",
                        wheelY: "zoomX",
                        layout: root.verticalLayout
                }));

                var cursor = chart.set("cursor", am5xy.XYCursor.new(root, {}));
                cursor.lineY.set("visible", false);


                var data = [<%=getstr%>]
                chart.get("colors").set("colors", [
                  am5.color(0x4477aa),
                  am5.color(0xf93f17),
                  am5.color(0xb6d7ea)
                ]);


                var xRenderer = am5xy.AxisRendererX.new(root, { minGridDistance: 10 });
                xRenderer.labels.template.setAll({
                        oversizedBehavior: "truncate",
                        maxWidth: 70,
                        ellipsis: "...",
                        fontSize: 9,
                        rotation: -70,
                        centerY: am5.p50,
                        centerX: am5.p100,
                        paddingRight: 0
                });

                xRenderer.grid.template.setAll({
                  location: 1
                })

                var xAxis = chart.xAxes.push(am5xy.CategoryAxis.new(root, {
                        maxDeviation: 0.3,
                        categoryField: "year",
                        renderer: xRenderer,
                        tooltip: am5.Tooltip.new(root, {})
                }));

                var yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {

                  maxDeviation: 0.3,
                  renderer: am5xy.AxisRendererY.new(root, {
                        strokeOpacity: 0.1
                  })
                }));



                xAxis.data.setAll(data);

                // Add series
                function makeSeries(name, fieldName) {
                  var series = chart.series.push(am5xy.ColumnSeries.new(root, {
                        //maskBullets: false,
                        name: name,
                        stacked: true,
                        xAxis: xAxis,
                        yAxis: yAxis,
                        valueYField: fieldName,
                        categoryXField: "year"
                  }));

                  series.columns.template.setAll({
                        tooltipText: "{name}, {categoryX}: {valueY}",
                        width: am5.percent(50),
                        tooltipY: am5.percent(10)
                  });
                  series.data.setAll(data);

                  // Make stuff animate on load
                  series.appear();

                  series.bullets.push(function() {
                        return am5.Bullet.new(root, {
                          sprite: am5.Label.new(root, {
                                text: "{valueY}",
                                //fill: root.interfaceColors.get("alternativeText"),
                                fontSize: 0.1,
                                centerY: am5.p50,
                                centerX: am5.p50,
                                populateText: true
                          })
                        });
                  });

                }

                makeSeries("Competent", "competent");
                makeSeries("Not Competent", "not_competent");
                makeSeries("Pending", "pending");



                series.columns.template.setAll({width:5});
                series.data.setAll(data);

                // Make stuff animate on load
                series.appear(1000);
                chart.appear(1000, 100);

                }); 
        </script>

        <script>
            $("#myiFrame").on("load", function() {
            let head = $("#myiFrame").contents().find("head");
            let css = '<style>img{margin: 0px auto;max-width:-webkit-fill-available;}</style>';
            $(head).append(css);
            });
        </script>

        <script>
            // requires jquery library
            jQuery(document).ready(function() {
            jQuery(".main-table").clone(true).appendTo('#table-scroll').addClass('clone');
            });
        </script>
        <script type="text/javascript">
            jQuery(function () {
            jQuery("#selectbox").change(function () {
            location.href = jQuery(this).val();
            })
            })
        </script>
            
            </html:form>
    </body>
    <%
        } catch (Exception e) {
            e.printStackTrace();
        }
    %>
</html>