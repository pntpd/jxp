<%@page import="java.util.Stack"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<jsp:useBean id="dashboard" class="com.web.jxp.dashboard.Ticket" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try 
    {
        int mtp = -1, submtp = -1;
        if (session.getAttribute("LOGININFO") == null) {
%>
    <jsp:forward page="/index1.jsp"/>
<%
    }
    int arr[] = new int[4];  
    int c1 = 0, c2 = 0, c3 = 0, c4 = 0, available = 0; 
    if(request.getAttribute("DBINFO") != null)
    {
        available = 1;
        arr = (int[]) request.getAttribute("DBINFO");
        request.removeAttribute("DBINFO");
        if(arr != null && arr.length == 4)
        {
            c1 = arr[0];
            c2 = arr[1];
            c3 = arr[2];
            c4 = arr[3];
        }
    }    
    int completed_count = 0, pending_count = 0, unassigned = 0, expired1 = 0, expired2 = 0, expired3 = 0,
        expired = 0, total_count = 0;
    int tarr[] = new int[11];
    if(request.getAttribute("DBINFOTRAINING") != null)
    {
        available = 1;
        tarr = (int[]) request.getAttribute("DBINFOTRAINING");
        request.removeAttribute("DBINFOTRAINING");
        if(tarr != null && tarr.length == 11)
        {
            completed_count = tarr[0];
            pending_count = tarr[1];
            unassigned = tarr[2];
            expired1 = tarr[3];
            expired2 = tarr[4];
            expired3 = tarr[5];
            expired = tarr[6];
            total_count = tarr[7]; 
        }
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
    <link href="/jxp/assets/css/minimal.css" rel="stylesheet" />
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="../jsnew/common.js"></script>
    <script type="text/javascript" src="../jsnew/dashboard.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/dashboard/DashboardAction.do" onsubmit="return false;" enctype="multipart/form-data">
        <html:hidden property="doDashboardSearch" />
        <html:hidden property="doCrecruitment" />
        <div id="layout-wrapper">
            <%@include file ="../header.jsp"%>
            <%@include file ="../sidemenu.jsp"%>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start">
                                <span class="back_arrow">Dashboard</span>
                            </div>
                            <div class="float-end">
                                <div class="toggled-off usefool_tool">                                    
                                </div>
                            </div>
                        </div>	
                    </div>
                    <div class="row">
                        <div class="col-md-12 col-xl-12">
                            <div class="search_export_area">
                                <div class="row">
                                    <div class="col-lg-8">
                                        <div class="row mb-3">
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
                                            <div class="col-lg-1">
                                                <a href="javascript: searchcount1();" class="go_btn">Go</a>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background dashboard_work_area">
                                    <div class="row">                                        
<%
                                        if(available > 0)
                                        {
%>
                                        <div class="col-md-12">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="graph_box">
                                                        <div class="row flex-end align-items-center">
                                                            <div class="col-md-12 m_15">
                                                                <div class="float-start"><h2>Crew Rotation</h2></div>
                                                                <div class="float-end"><a href="javascript: gotod('1');" class="clear_sec"><img src="../assets/images/white_view.png"/> View Detail</a></div>
                                                            </div>
                                                            <div class="col-md-9">
                                                                <div id="echarts_pie" style="height:300px;"></div>
                                                            </div>
                                                            <div class="col-md-3 vertical_list">
                                                                <div class="shadow_div offshore_normal graph_record m_15">
                                                                    <span><%=c2%></span>
                                                                    <label class="">Offshore - Normal</label>
                                                                </div>
                                                                <div class="shadow_div offshore_extended graph_record m_15">
                                                                    <span><%=c3%></span>
                                                                    <label class="">Offshore - Extended</label>
                                                                </div>
                                                                <div class="shadow_div offshore_overstay graph_record m_15">
                                                                    <span><%=c4%></span>
                                                                    <label class="">Offshore - Overstay</label>
                                                                </div>
                                                                <div class="shadow_div offshore_onshore graph_record">
                                                                    <span><%=c1%></span>
                                                                    <label class="">Onshore</label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="graph_box ">
                                                        <div class="row  flex-end align-items-center">
                                                            <div class="col-md-12">
                                                                <div class="float-start"><h2>Training</h2></div>
                                                            </div>
                                                            <div class="col-md-9">
                                                                <div id="echarts_pie_02" style="height:300px;"></div>
                                                            </div>
                                                            <div class="col-md-3 vertical_list">
                                                                <div class="shadow_div training_courses graph_record m_15">                                                                     
                                                                    <span><%=total_count%></span>
                                                                    <label class="">Courses</label>
                                                                </div>
                                                                <div class="shadow_div training_unassigned graph_record m_15">
                                                                    <span><%=unassigned%></span>
                                                                    <label class="">Unassigned</label>
                                                                </div>
                                                                <div class="shadow_div training_pending graph_record m_15">
                                                                    <span><%=pending_count%></span>
                                                                    <label class="">Pending</label>
                                                                </div>
                                                                <div class="shadow_div training_complete graph_record">
                                                                    <span><%=completed_count%></span>
                                                                    <label class="">Complete</label>
                                                                </div>
                                                                <div class="shadow_div training_exipry graph_record">
                                                                    <span><%=expired1%></span>
                                                                    <label class="">Expiry - 45 days</label>
                                                                </div>
                                                                <div class="shadow_div training_exipry graph_record">
                                                                    <span><%=expired2%></span>
                                                                    <label class="">Expiry - 45 - 65 days</label>
                                                                </div>
                                                                <div class="shadow_div training_exipry graph_record">
                                                                    <span><%=expired3%></span>
                                                                    <label class="">Expiry - 65 - 90 days</label>
                                                                </div>
                                                                <div class="shadow_div training_expired graph_record">
                                                                    <span><%=expired%></span>
                                                                    <label class="">Expired</label>
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
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        <script src="../assets/js/echarts.js" type="text/javascript"></script>
        <script src="../assets/js/app.js"></script>  
        <script>			
            $(document).on('click', '.toggle-title', function() {
                $(this).parent()
                .toggleClass('toggled-on')
                .toggleClass('toggled-off');
            });
        </script>
        <script>
            jQuery(document).ready(function() 
            {
                require.config({
                    paths: {echarts: "../assets/global/plugins/echarts/"}}), 
                    require(["echarts", "echarts/chart/bar", "echarts/chart/chord", "echarts/chart/eventRiver", "echarts/chart/force", "echarts/chart/funnel", "echarts/chart/gauge", "echarts/chart/heatmap", "echarts/chart/k", "echarts/chart/line", "echarts/chart/map", "echarts/chart/pie", "echarts/chart/radar", "echarts/chart/scatter", "echarts/chart/tree", "echarts/chart/treemap", "echarts/chart/venn", "echarts/chart/wordCloud"], function(e) {
						
                    var l = e.init(document.getElementById("echarts_pie"));
                    l.setOption({
                        tooltip: {
                            show: !0,
                            formatter: "{a} <br/>{b} : {c}"
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
                            center: ["50%", 150],
                            radius: [90, 100],
                            data: [
                                {value: <%=c2%>,itemStyle: {normal: {color: '#2ecf37',label :{show : false},labelLine :{show : false}}},name: "Offshore - Normal"}, 
                                {value: <%=c3%>,itemStyle: {normal: {color: '#eaec19',label :{show : false},labelLine :{show : false}}},name: "Offshore - Extended"}, 
                                {value: <%=c4%>,itemStyle: {normal: {color: '#e69c23',label :{show : false},labelLine :{show : false}}},name: "Offshore - Overstay"},
                                {value: <%=c1%>,itemStyle: {normal: {color: '#2478A6',label :{show : false},labelLine :{show : false}}},name: "Onshore"}
                            ]
                        }, 
                        ]
                    });
                    
                    var a = e.init(document.getElementById("echarts_pie_02"));
                    a.setOption({
                        tooltip: {
                            show: !0,
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
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
                            center: ["50%", 150],
                            radius: [90, 100],                            
                            data: [
                                {value: <%=unassigned%>,itemStyle: {normal: {color: '#DBA426',label :{show : false},labelLine :{show : false}}},name: "Unassigned"}, 
                                {value: <%=pending_count%>,itemStyle: {normal: {color: '#39A7C0',label :{show : false},labelLine :{show : false}}},name: "Pending"},
                                {value: <%=completed_count%>,itemStyle: {normal: {color: '#6D9F29',label :{show : false},labelLine :{show : false}}},name: "Complete"},
                                {value: <%=expired1%>,itemStyle: {normal: {color: '#FF5A0A',label :{show : false},labelLine :{show : false}}},name: "Expiry - 45 days"},
                                {value: <%=expired2%>,itemStyle: {normal: {color: '#e04e08',label :{show : false},labelLine :{show : false}}},name: "Expiry - 45 - 65 days"},
                                {value: <%=expired3%>,itemStyle: {normal: {color: '#bf4307',label :{show : false},labelLine :{show : false}}},name: "Expiry - 65 - 90 days"},
                                {value: <%=expired%>,itemStyle: {normal: {color: '#B71616',label :{show : false},labelLine :{show : false}}},name: "Expired"}
                            ]
                        }, 
                        ]
                    })
                })
            });
        </script>
    </html:form>
</body>
<%
    } 
    catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>