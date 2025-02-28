<%@page import="java.util.Stack"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.dashboard.DashboardInfo" %>
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
    int c1 = 0, c2 = 0, c3 = 0, c4 = 0; 
    if(request.getAttribute("VINFO") != null)
    {
        arr = (int[]) request.getAttribute("VINFO");
        request.removeAttribute("VINFO");
        if(arr != null && arr.length == 4)
        {
            c1 = arr[0];
            c2 = arr[1];
            c3 = arr[2];
            c4 = arr[3];
        }
    }    
    int c5 = 0, c6 = 0, c7 = 0, c8 = 0;
    int tarr[] = new int[4];
    if(request.getAttribute("AINFO") != null)
    {
        tarr = (int[]) request.getAttribute("AINFO");
        request.removeAttribute("AINFO");
        if(tarr != null && tarr.length == 4)
        {
            c5 = tarr[0];
            c6 = tarr[1];
            c7 = tarr[2];
            c8 = tarr[3];
        }
    }
    int garr[] = new int[10];
    if(request.getAttribute("G4INFO") != null)
    {
        garr = (int[]) request.getAttribute("G4INFO");
        request.removeAttribute("G4INFO");
    }
    ArrayList list = new ArrayList();
    if(request.getAttribute("G5LIST") != null)
    {
        list = (ArrayList) request.getAttribute("G5LIST");
        request.removeAttribute("G5LIST");   
    }
    int list_size = list.size();
    int employed = 0, available = 0;
    String str1 = "", str2 = "", legend = "";
    for(int t = 0; t < list_size; t++)
    {
        DashboardInfo dinfo = (DashboardInfo) list.get(t);
        if(dinfo != null)
        {
            if(dinfo.getDdlLabel() != null)
            {
                if(dinfo.getDdlLabel().equals("Available"))
                    available = dinfo.getDdlValue();
                else
                    employed += dinfo.getDdlValue();
                legend += ("<li><span class='box_bg' style='background-color: #"+dinfo.getColorCode()+";'></span> "+dinfo.getDdlLabel()+"</li>");
                if(t == list_size - 1)
                {
                    str1 += "{country: \""+dinfo.getDdlLabel()+"\",value: "+dinfo.getDdlValue()+"}";
                    str2 += "am5.color(0x"+(dinfo.getColorCode() != null && !dinfo.getColorCode().equals("") ? dinfo.getColorCode() : "CBCCCB") +")";
                }
                else
                {
                    str1 += "{country: \""+dinfo.getDdlLabel()+"\",value: "+dinfo.getDdlValue()+"},";
                    str2 += "am5.color(0x"+(dinfo.getColorCode() != null && !dinfo.getColorCode().equals("") ? dinfo.getColorCode() : "CBCCCB") +"),";
                }
            }
        }       
    }
    int farr[] = new int[3];
    if(request.getAttribute("G1LIST") != null)
    {
        farr = (int[]) request.getAttribute("G1LIST");
        request.removeAttribute("G1LIST");
    }
    int g1 = farr[0], g2 = farr[1], g3 = farr[2]; 
    
    int arr5[] = new int[3];  
    int totalcandidate = 0, availablecandidate = 0, employedcandidate = 0; 
    if(request.getAttribute("CINFO") != null)
    {
        arr5 = (int[]) request.getAttribute("CINFO");
        request.removeAttribute("CINFO");
        if(arr5 != null && arr5.length == 3)
        {
            totalcandidate = arr5[0];
            employedcandidate = arr5[1];
            availablecandidate = arr5[2];
        }
    }
    availablecandidate = totalcandidate-employedcandidate;
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
                                    <span class="back_arrow">Crew Enrollment</span>
                                </div>
                                <div class="col col-lg-4">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="row d-flex align-items-center">
                                                <label for="example-text-input" class="col-sm-5 com_label_value col-form-label text-right pd_0"><span>Select Dashboard:</span></label>
                                                <div class="col-sm-7">
                                                    <div class="row d-flex align-items-center">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                            <select class="form-select" id="stype" name="stype" onchange="javascript: directdashboard();">
                                                                <option value="2" selected>Crew Enrollment</option>
                                                                <option value="3">Crew Recruitment</option>
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
                    
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background dashboard_work_area mt_15">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="row">
                                                <div class="col-md-4 m_15">
                                                    <div class="graph_box ">
                                                        <div class="row  flex-end align-items-center">
                                                           
                                                            <div class="col-md-8 position-relative">
                                                                       <div class="row">
                                                                         <div class="col-md-12 m_5">
                                                                            <div class="float-start"><h2>Enrollment</h2></div>
                                                                        </div>
                                                                        <div id="echarts_pie_04" style="height:160px;"></div>
                                                                            <span class="total_percentage"><%if(g1 > 0) {%><%=Math.round((double)((double)g2/(g1))*100.0)%>%<% } %></span>
                                                                       </div>
                                                                </div>
                                                                
                                                            <div class="col-md-4 vertical_list">
                                                                <div class="shadow_div offshore_normal graph_record m_15">
                                                                    <span><%=g1%></span>
                                                                    <label class="">Total</label>
                                                                </div>
                                                                <div class="shadow_div pass graph_record m_15">
                                                                    <span><%=g2%></span>
                                                                    <label class="">Completed</label>
                                                                </div>
                                                                <div class="shadow_div scheduled graph_record m_15">
                                                                    <span><%=g3%></span>
                                                                    <label class="">Pending</label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-4 m_15">
                                                    <div class="graph_box">
                                                        <div class="row flex-end align-items-center">
                                                            
                                                            <div class="col-md-8 position-relative">
                                                                <div class="row">
                                                                       <div class="col-md-12 m_5">
                                                                        <div class="float-start"><h2>Verification</h2></div>
                                                                    </div>
                                                                <div id="echarts_pie" style="height:160px;"></div>
                                                                <span class="total_percentage"><%if(c4 > 0) {%><%=Math.round((double)((double)c1/c4)*100.0)%>%<% } %></span>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-4 vertical_list">

                                                                <div class="shadow_div offshore_normal graph_record m_15">
                                                                    <span><%=c4%></span>
                                                                    <label class="">Total</label>
                                                                </div>
                                                                <div class="shadow_div verified graph_record m_15">
                                                                    <span><%=c1%></span>
                                                                    <label class="">Verified</label>
                                                                </div>
                                                                <div class="shadow_div unverified graph_record m_15">
                                                                    <span><%=c3%></span>
                                                                    <label class="">Unverified</label>
                                                                </div>
                                                                <div class="shadow_div min_verified graph_record">
                                                                    <span><%=c2%></span>
                                                                    <label class="">Minimum Verified </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                
                                                <div class="col-md-4 m_15">
                                                    <div class="graph_box ">
                                                        <div class="row  flex-end align-items-center">
                                                            
                                                            <div class="col-md-8 position-relative">
                                                                <div class="row">
                                                                        <div class="col-md-12 m_5">
                                                                            <div class="float-start"><h2>Assessment</h2></div>
                                                                        </div>
                                                                        <div id="echarts_pie_03" style="height:160px;"></div>
                                                                        <span class="total_percentage"><%if(c5+c6+c7 > 0) {%><%=Math.round((double)((double)c5/(c5+c6+c7)*100.0))%>%<% } %></span>
                                                                </div> 
                                                            </div>
                                                            <div class="col-md-4 vertical_list">
                                                                <div class="shadow_div offshore_normal graph_record m_15">
                                                                    <span><%=c5+c6+c7%></span>
                                                                    <label class="">Total</label>
                                                                </div>
                                                                <div class="shadow_div pass graph_record m_15">
                                                                    <span><%=c5%></span>
                                                                    <label class="">Pass</label>
                                                                </div>
                                                                <div class="shadow_div scheduled graph_record m_15">
                                                                    <span><%=c7%></span>
                                                                    <label class="">Scheduled</label>
                                                                </div>
                                                                <div class="shadow_div failed graph_record">
                                                                    <span><%=c6%></span>
                                                                    <label class="">Failed</label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-6 d-none1 m_15">
                                                    <div class="graph_box">
                                                        <div class="row flex-end align-items-center">
                                                            <div class="col-md-12 m_15">
                                                                <div class="float-start"><h2>Verification Details</h2></div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div id="chartdiv_1" style="height:400px;"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                                    
                                                <div class="col-md-6 d-none1">
                                                    <div class="graph_box">
                                                        <div class="row flex-end align-items-center">
                                                            <div class="col-md-12 m_15">
                                                                <div class="float-start"><h2>Talent Pool</h2></div>
                                                            </div>                                                            
                                                            <div class="col-md-10">
                                                                <div id="chartdiv_2" style="height:315px;"></div>
                                                            </div>
                                                            <div class="col-md-2 vertical_list">
                                                                <div class="shadow_div offshore_normal graph_record m_15">
                                                                    <span><%=totalcandidate%></span>
                                                                    <label class="">Total</label>
                                                                </div>
                                                                <div class="shadow_div employed graph_record m_15">
                                                                    <span><%=employedcandidate%></span>
                                                                    <label class="">Employed</label>
                                                                </div>
                                                                <div class="shadow_div available graph_record">
                                                                    <span><a href="../talentpool/TalentpoolAction.do?&employementstatus=1" target="_blank"><%=availablecandidate%></a></span>
                                                                    <label>Available</label>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="full_width talent_pool_color talent_pool_legend">
                                                                    <ul>
                                                                        <%=legend%>
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
                                data: [
                                    {value: <%=c1%>,itemStyle: {normal: {color: '#3c71cd', label :{show : false},labelLine :{show : false}}},name: "Verified"}, 
                                    {value: <%=c3%>,itemStyle: {normal: {color: '#f78233', label :{show : false},labelLine :{show : false}}},name: "Unverified"}, 
                                    {value: <%=c2%>,itemStyle: {normal: {color: '#a7a7a7', label :{show : false},labelLine :{show : false}}},name: "Minimum Verified"}
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
                                center: ["50%", 80],
                                radius: [80, 70],
                                data: [
                                    {value: <%=c5%>,itemStyle: {normal: {color: '#3c71cd', label :{show : false},labelLine :{show : false}}},name: "Pass"}, 
                                    {value: <%=c7%>,itemStyle: {normal: {color: '#f78233', label :{show : false},labelLine :{show : false}}},name: "Scheduled"},
                                    {value: <%=c6%>,itemStyle: {normal: {color: '#a7a7a7', label :{show : false},labelLine :{show : false}}},name: "Failed"}
                                ]
                            }, 
                        ]
                    });
                    
                    var c = e.init(document.getElementById("echarts_pie_04")); 
                    c.setOption({
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
                                data: [
                                    {value: <%=g2%>,itemStyle: {normal: {color: '#3c71cd', label :{show : false},labelLine :{show : false}}},name: "Completed"}, 
                                    {value: <%=g3%>,itemStyle: {normal: {color: '#f78233', label :{show : false},labelLine :{show : false}}},name: "Pending"}
                                ]
                            }, 
                            ]
                        })

                    })
                });
        </script>
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
              pinchZoomX: true
            }));	
            var cursor = chart.set("cursor", am5xy.XYCursor.new(root, {}));
            cursor.lineY.set("visible", false);
            var xRenderer = am5xy.AxisRendererX.new(root, { minGridDistance: 30 });
            xRenderer.labels.template.setAll({
              rotation: -75,
              centerY: am5.p50,
              centerX: am5.p100,
              paddingRight: 15
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


            // Set data
            var data = [
                    {country: "Personal",value: <%=garr[0]%>}, 
                    {country: "Languages",value: <%=garr[1]%>}, 
                    {country: "Health",value: <%=garr[2]%>}, 
                    {country: "Vaccination",value: <%=garr[3]%>}, 
                    {country: "Experience",value: <%=garr[4]%>},
                    {country: "Education",value: <%=garr[5]%>}, 
                    {country: "Certifications",value: <%=garr[6]%>}, 
                    {country: "Bank Details",value: <%=garr[8]%>},
                    {country: "Documents",value: <%=garr[9]%>}
            ];

            chart.get("colors").set("colors", [
              am5.color(0x2478A6),
              am5.color(0x2478A6),
              am5.color(0x2478A6),
              am5.color(0x2478A6),
              am5.color(0x2478A6),
              am5.color(0x2478A6),
              am5.color(0x2478A6),
              am5.color(0x2478A6),
              am5.color(0x2478A6),
              am5.color(0x2478A6)
            ]);			
            series.columns.template.setAll({width:25});
            xAxis.data.setAll(data);
            series.data.setAll(data);
            series.appear(1000);
            chart.appear(1000, 100);
            }); // end am5.ready()
    </script>
    <script>
        am5.ready(function()
        {
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
                rotation: -75,
                centerY: am5.p50,
                centerX: am5.p100,
                paddingRight: 15
            });
            
            xRenderer.grid.template.setAll({
                location: 1
            })

            var xAxis = chart.xAxes.push(am5xy.CategoryAxis.new(root, {
                maxDeviation: 0.3,
                categoryField: "country",
                renderer: xRenderer,
                disabled: false,
                tooltip: am5.Tooltip.new(root, {})
            }));
            xAxis.hide();
            
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

            var data = [
                <%=str1%>
            ]; 
            chart.get("colors").set("colors", [
              <%=str2%>
            ]);
            
            series.columns.template.setAll({width:25});
            xAxis.data.setAll(data);
            series.data.setAll(data);

            legend.data.setAll(series.dataItems);
            series.appear(1000);
            chart.appear(1000, 100);
        }); // end am5.ready()
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