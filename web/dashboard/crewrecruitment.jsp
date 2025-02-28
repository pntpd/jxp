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
    int unchecked = 0, minchecked = 0, checked = 0, offeraccepted = 0, totalpositions = 0,
            total_candidate = 0, open_job = 0, sortlisted = 0, selected = 0, onboarded = 0, available = 0, sortlisted_total = 0, selected_total = 0;
    int tarr[] = new int[12];
    if(request.getAttribute("CRCOUNT") != null)
    {
        available = 1;
        tarr = (int[]) request.getAttribute("CRCOUNT");
        request.removeAttribute("CRCOUNT");
        if(tarr != null && tarr.length == 12)
        {
            unchecked = tarr[0];
            minchecked = tarr[1];
            checked = tarr[2];
            offeraccepted = tarr[3];
            totalpositions = tarr[4];
            total_candidate = tarr[5];
            open_job = tarr[6];
            sortlisted = tarr[7]; 
            selected = tarr[8]; 
            onboarded = tarr[9]; 
            sortlisted_total = tarr[10];
            selected_total = tarr[11];
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
        <html:hidden property="doCrecruitmentSearch" />
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
                                    <span class="back_arrow">Crew Recruitment</span>
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
                                                            <html:select property="assetIdIndex" styleId="assetIdIndex" styleClass="form-select" onchange="javascript: searchcrecruitcount();">
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
                                                                <option value="3" selected>Crew Recruitment</option>
                                                                <option value="1">Crew Rotation</option>                                                                
                                                                <option value="4">Training &amp; Development</option>
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
                                                                <div class="float-start"><h2>Placement Rate</h2></div>
                                                            </div>
                                                            <div class="col-md-12 position-relative">
                                                                <div id="echarts_pie" style="height:160px;"></div>                                                                
                                                                <span class="total_percentage placement_div"><%if(totalpositions > 0) {%><%=Math.round((double)((double)offeraccepted/(totalpositions))*100.0)%>%<% } %></span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-9 m_15">
                                                    <div class="row">
                                                        <div class="col-md-12 rec_no_label">
                                                            <ul>
                                                                <li><span class="rec_no_value orange_text"><%=total_candidate%></span><span class="rec_label">Total Candidates</span></li>
                                                                <li><span class="rec_no_value orange_text"><%=open_job%></span><span class="rec_label">Open Job Postings</span></li>
                                                                <li><span class="rec_no_value orange_text"><%=sortlisted%></span><span class="rec_label">Shortlisted</span></li>
                                                                <li><span class="rec_no_value orange_text"><%=selected%></span><span class="rec_label">Selected</span></li>
                                                                <li><span class="rec_no_value orange_text"><%=onboarded%></span><span class="rec_label">Onboarded</span></li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>                                                
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6 d-none1">
                                                    <div class="graph_box">
                                                        <div class="row flex-end align-items-center">
                                                            <div class="col-md-12 m_5">
                                                                <div class="float-start"><h2>Recruitment Funnel</h2></div>
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
                                                            <div class="col-md-12 m_5">
                                                                <div class="float-start"><h2>Compliance Check</h2></div>
                                                            </div>
                                                            <div class="col-md-9 position-relative">
                                                                <div id="echarts_pie_03" style="height:230px;"></div>
                                                                <span class="total_percentage compliance_div"><%if(checked + unchecked + minchecked > 0) {%><%=Math.round((double)((double)checked/(checked + unchecked + minchecked))*100.0)%>%<% } %></span>
                                                            </div>
                                                            <div class="col-md-3 vertical_list">
                                                                <div class="shadow_div offshore_normal graph_record m_15">
                                                                    <span><%=checked + unchecked + minchecked%></span>
                                                                    <label class="">Total</label>
                                                                </div>
                                                                <div class="shadow_div pass graph_record m_15">
                                                                    <span><%=checked%></span>
                                                                    <label class="">Checked</label>
                                                                </div>
                                                                <div class="shadow_div scheduled graph_record m_15">
                                                                    <span><%=unchecked%></span>
                                                                    <label class="">Unchecked</label>
                                                                </div>
                                                                <div class="shadow_div failed graph_record">
                                                                    <span><%=minchecked%></span>
                                                                    <label class="">Minimum Checked</label>
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
                jQuery(document).ready(function() {
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
                                                        {value: <%=totalpositions%>,itemStyle: {normal: {color: '#f1f1f1', label :{show : false},labelLine :{show : false}}},name: "Total Vacancies"}, 
                                                        {value: <%=offeraccepted%>,itemStyle: {normal: {color: '#3c71cd', label :{show : false},labelLine :{show : false}}},name: "Offer Accepted"}
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
                                                        {value: <%=checked%>,itemStyle: {normal: {color: '#3c71cd', label :{show : false},labelLine :{show : false}}},name: "Checked"}, 
                                                        {value: <%=unchecked%>,itemStyle: {normal: {color: '#f78233', label :{show : false},labelLine :{show : false}}},name: "Unchecked"},
                                                        {value: <%=minchecked%>,itemStyle: {normal: {color: '#a7a7a7', label :{show : false},labelLine :{show : false}}},name: "Minimum Checked"}
                                                ]
                                        }, 
                                        ]
                                })
					
                        })
                });
        </script>
        <script>			
                // Create root and chart
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
                  paddingRight: -45
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
                {country: "Total Vacancies",value: <%=totalpositions%>}, 
                {country: "Candidate Shortlisted",value: <%=sortlisted_total%>}, 
                {country: "Compliance Checks",value: <%=checked + minchecked + unchecked %>}, 
                {country: "Client Selection",value: <%=selected_total%>},
                {country: "Offer Accepted",value: <%=offeraccepted%>}
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