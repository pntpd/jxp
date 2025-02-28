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
    int completed_count = 0, pending_count = 0, unassigned = 0, expired1 = 0, expired2 = 0, expired3 = 0,
        expired = 0, total_count = 0, candidate_count = 0, position_count = 0, available = 0;
    int tarr[] = new int[11];
    if(request.getAttribute("TRAINGCOUNT") != null)
    {
        available = 1;
        tarr = (int[]) request.getAttribute("TRAINGCOUNT");
        request.removeAttribute("TRAINGCOUNT");
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
            position_count = tarr[8]; 
            candidate_count = tarr[9]; 
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
        <html:hidden property="doTrainingSearch" />
        <html:hidden property="doCrecruitment" />
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
                                    <span class="back_arrow">Training & Development</span>
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
                                                            <html:select property="assetIdIndex" styleId="assetIdIndex" styleClass="form-select" onchange="javascript: searchtraingcount();"> 
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
                                                                <option value="1">Crew Rotation</option>                                                                
                                                                <option value="4" selected>Training &amp; Development</option>
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
                    if(available > 0)
                    {
%>
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background dashboard_work_area mt_15">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="row d-flex align-items-center">
                                                <div class="col-md-3 m_15">
                                                    <div class="graph_box">
                                                        <div class="row flex-end align-items-center">
                                                            <div class="col-md-12 m_15">
                                                                <div class="float-start"><h2>Progress</h2></div>
                                                            </div>
                                                            <div class="col-md-12 position-relative">
                                                                <div id="echarts_pie" style="height:160px;"></div>
                                                                <span class="total_percentage placement_div"><%if(completed_count+pending_count > 0) {%><%=Math.round((double)((double)completed_count/(completed_count+pending_count))*100.0)%>%<% } %></span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-9 m_15">
                                                    <div class="row">
                                                        <div class="col-md-12 rec_no_label">
                                                            <ul>
                                                                <li><span class="rec_no_value blue_text"><%=total_count%></span><span class="rec_label">Course</span></li>
                                                                <li><span class="rec_no_value blue_text"><%=candidate_count%></span><span class="rec_label">Personnel</span></li>
                                                                <li><span class="rec_no_value blue_text"><%=position_count%></span><span class="rec_label">Position</span></li>
                                                                <li><span class="rec_no_value orange_text"><%=pending_count%></span><span class="rec_label">Pending</span></li>
                                                                <li><span class="rec_no_value green_text"><%=completed_count%></span><span class="rec_label">Complete</span></li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>                                                
                                            </div>
                                            <div class="row">

                                                <div class="col-md-6 d-none">
                                                    <div class="graph_box">
                                                        <div class="row flex-end align-items-center">
                                                            <div class="col-md-12 m_15">
                                                                <div class="float-start"><h2>Verification Details</h2></div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div id="chartdiv_1" style="height:370px;"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-6 d-none1">
                                                    <div class="graph_box">
                                                        <div class="row flex-end align-items-center">
                                                            <div class="col-md-12 m_15">
                                                                <div class="float-start"><h2>Training Status</h2></div>
                                                            </div>
                                                            <div class="col-md-12">

                                                                <div class="full_width" id="chartdiv_2" style="height:230px;"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-6 m_15">
                                                    <div class="graph_box ">
                                                        <div class="row  flex-end align-items-center">
                                                            <div class="col-md-12 m_15">
                                                                <div class="float-start"><h2>Training Expiry</h2></div>
                                                            </div>
                                                            <div class="col-md-9 position-relative">
                                                                <div id="echarts_pie_03" style="height:230px;"></div>
                                                                <span class="total_percentage training_div"><%if(expired > 0) {%><%=Math.round((double)((double)expired/(completed_count)*100.0))%>%<% } %></span>
                                                            </div>
                                                            <div class="col-md-3">
                                                                <div class="full_width talent_pool_color circle_legend">
                                                                    <ul>
                                                                        <li><span class="box_bg" style="background-color:#2478A6;"></span> Expired</li>
                                                                        <li><span class="box_bg" style="background-color:#f78233;"></span> < 45</li>
                                                                        <li><span class="box_bg" style="background-color:#a7a7a7;"></span> 45-60</li>
                                                                        <li><span class="box_bg" style="background-color:#eaec19;"></span> 65-90</li>
                                                                    </ul>
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
<%
                    }
%>
                </div>
            </div>
        </div>       
        <%@include file ="../footer.jsp"%>
        <script src="../assets/libs/jquery/jquery.min.js"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        <script src="../assets/js/echarts.js" type="text/javascript"></script>
        <script src="../assets/js/stack/index.js"></script>
        <script src="../assets/js/stack/xy.js"></script>
        <script src="../assets/js/stack/Animated.js"></script>
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
                    require(["echarts", "echarts/chart/pie"], function(e) {					
                        var l = e.init(document.getElementById("echarts_pie"));
                            l.setOption({
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
                                        center: ["50%", 80],
                                        radius: [80, 70],
                                        data:  [
                                            {value: <%=completed_count%>,itemStyle: {normal: {color: '#3c71cd', label :{show : false},labelLine :{show : false}}},name: "Complete"}, 
                                            {value: <%=pending_count%>,itemStyle: {normal: {color: '#f1f1f1', label :{show : false},labelLine :{show : false}}},name: "Pending"}
                                        ]
                                    }, 
                                ]
                            });
                            var b = e.init(document.getElementById("echarts_pie_03"));
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
                                        center: ["50%", 105],
                                        radius: [80, 70],
                                        data: [
                                            {value: <%=expired%>,itemStyle: {normal: {color: '#2478A6', label :{show : false},labelLine :{show : false}}},name: "Expired"}, 
                                            {value: <%=expired1%>,itemStyle: {normal: {color: '#f78233', label :{show : false},labelLine :{show : false}}},name: "< 45"},
                                            {value: <%=expired2%>,itemStyle: {normal: {color: '#a7a7a7', label :{show : false},labelLine :{show : false}}},name: "45-60"},
                                            {value: <%=expired3%>,itemStyle: {normal: {color: '#eaec19', label :{show : false},labelLine :{show : false}}},name: "65-90"}
                                        ]
                                    }, 
                                ]
                            })					
                        })
                    });
            </script>
            <script>
                var root = am5.Root.new("chartdiv_2"); 
                    root.setThemes([
                      am5themes_Animated.new(root)
                    ]);
                    var chart = root.container.children.push(am5xy.XYChart.new(root, {
                      panX: true,
                      panY: true,
                      wheelX: "panX",
                      wheelY: "zoomX",
                      pinchZoomX: true
                    }));
                    var cursor = chart.set("cursor", am5xy.XYCursor.new(root, {}));
                    cursor.lineY.set("visible", false);
                    var xRenderer = am5xy.AxisRendererX.new(root, { minGridDistance: 30 });
                    xRenderer.labels.template.setAll({
                      fontSize: 11,
                      rotation: -20,
                      centerY: am5.p50,
                      centerX: am5.p100,
                      paddingTop: 20,
                      paddingRight: -20
                    });

                    xRenderer.grid.template.setAll({
                      location: 1
                    })

                    var xAxis = chart.xAxes.push(am5xy.CategoryAxis.new(root, {
                      maxDeviation: 0.3,
                      categoryField: "country",
                      renderer: xRenderer,
                      tooltip: am5.Tooltip.new(root, {})
                    }));
                    //xAxis.hide();

                    var yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {
                      maxDeviation: 0.3,
                      renderer: am5xy.AxisRendererY.new(root, {
                            strokeOpacity: 0.1
                      })
                    }));	
                    var series = chart.series.push(am5xy.ColumnSeries.new(root, {
                      name: "Series 1",
                            xAxis: xAxis,
                      yAxis: yAxis,
                      valueYField: "value",
                      sequencedInterpolation: true,
                      categoryXField: "country",
                      tooltip: am5.Tooltip.new(root, {
                            labelText: "{value}"
                      })
                    }));

                    var columnTemplate = series.columns.template;
                    columnTemplate.strokeWidth = 2;
                    columnTemplate.strokeOpacity = 1;

                    series.columns.template.setAll({ cornerRadiusTL: 5, cornerRadiusTR: 5, strokeOpacity: 0 });
                    series.columns.template.adapters.add("fill", function(fill, target) {
                      return chart.get("colors").getIndex(series.columns.indexOf(target));
                    });

                    series.columns.template.adapters.add("stroke", function(stroke, target) {
                      return chart.get("colors").getIndex(series.columns.indexOf(target));
                    });

                    // Define data
                    var data = [
                    {country: "Courses",value: <%=total_count%>}, 
                    {country: "Unassigned",value: <%=unassigned%>}, 
                    {country: "Pending",value: <%=pending_count%>}, 
                    {country: "Complete",value: <%=completed_count%>}, 
                    {country: "Expired",value: <%=expired%>}
                    ];

                    chart.get("colors").set("colors", [
                      am5.color(0x2478A6),
                      am5.color(0x2478A6),
                      am5.color(0x2478A6),
                      am5.color(0x2478A6),
                      am5.color(0x2478A6)
                    ]);
                    
                    series.columns.template.setAll({width:25});
                    xAxis.data.setAll(data);
                    series.appear(1000);
                    chart.appear(1000, 100);
                    series.data.setAll(data);
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