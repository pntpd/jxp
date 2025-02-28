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
        try 
        {
            int mtp = 8, submtp = 92;
            if (session.getAttribute("LOGININFO") == null) 
            {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
            }
            // Comp List By Name
            ArrayList list = new ArrayList();
            if (session.getAttribute("COMP_NAMELIST") != null) {
                list = (ArrayList) session.getAttribute("COMP_NAMELIST");
            }
            int listtotal = list.size();
            // Comp graph list
            ArrayList graphlist = new ArrayList();
            if (request.getAttribute("GRAPH_COUNT") != null) {
                graphlist = (ArrayList) request.getAttribute("GRAPH_COUNT");
            }
            int graph_size = graphlist.size();
            String gstr = "";
            StringBuilder sb = new StringBuilder();
            if(graph_size > 0)
            {
                for(int  g = 0; g < graph_size; g++)
                {
                    CompetencyInfo ginfo = (CompetencyInfo) graphlist.get(g);
                    if(ginfo != null && ginfo.getDdlLabel() != null && !ginfo.getDdlLabel().equals(""))
                    {
                        if(g == graph_size - 1)
                            sb.append("{country: \""+(ginfo.getDdlLabel())+" \", value: "+ginfo.getDdlValue()+"}");
                        else
                            sb.append("{country: \""+(ginfo.getDdlLabel())+" \", value: "+ginfo.getDdlValue()+"},");
                    }
                }
            }
            gstr = sb.toString();
            sb.setLength(0);
            int exptype = 0;
            if(request.getAttribute("EXPTYPE") != null)
                exptype = Integer.parseInt((String) request.getAttribute("EXPTYPE")); 
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
        <html:form action="/competency/CompetencyAction.do" onsubmit="return false;">
            <html:hidden property = "expType"/>
            <html:hidden property = "searchGraph"/>
            <html:hidden property = "clientIdIndex"/>
            <html:hidden property = "assetIdIndex"/>
            <html:hidden property = "doSearchPosition"/>
            <html:hidden property = "doCancel"/>
            <html:hidden property = "departmentcb"/>
            <html:hidden property = "crewrotationcb"/>
            <html:hidden property = "positioncb"/>
            <html:hidden property = "comprolecb"/>
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
                                    <span class="back_arrow"><a href="javascript: goback();" ><img src="../assets/images/back-arrow.png"></a> Role Competencies (Expiry, Near Expiry and Pending)</span>
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
                                                <div class="col-md-12 m_15">
                                                    <div class="float-start com_pen_exp_clear">
                                                        <a href="javascript:;"  onclick="javascript: getDetail('1');" class="pen_exp_bg <%if(exptype == 1){%>active<%}%>">Expired</a>
                                                        <a href="javascript:;"  onclick="javascript: getDetail('2');" class="pen_exp_bg <%if(exptype == 2){%>active<%}%>">Expiry in 45 Days</a>
                                                        <a href="javascript:;"  onclick="javascript: getDetail('3');" class="pen_exp_bg <%if(exptype == 3){%>active<%}%>"> Expiry in 45-65 Days</a>
                                                        <a href="javascript:;"  onclick="javascript: getDetail('4');" class="pen_exp_bg <%if(exptype == 4){%>active<%}%>"> Expiry in 90 Days</a>
                                                        <a href="javascript:;"  onclick="javascript: getDetail('5');" class="pen_exp_bg <%if(exptype == 5){%>active<%}%>">Pending</a>
                                                    </div>
                                                    <div class="float-end export_clear">
                                                        <a href="javascript: exporttoexcel2();" class="home_export"><i class="fas fa-file-export"></i> Export to Excel</a>
                                                        <a href="javascript: clear('2');;" class="home_export"> Clear Selection</a>
                                                    </div>
                                                </div>

                                                <div class="col-md-12 m_15">
                                                    <div class="row">
                                                        <div class="col-md-4">
                                                            <div class="graph_box">
                                                                <div class="row">
                                                                    <div class="col-md-12 m_15">
                                                                        <div class="float-start">
                                                                            <%if(exptype == 1){%><h2>Expired</h2><%}%>
                                                                            <%if(exptype == 2){%><h2>Expiry in 45 Days</h2><%}%>
                                                                            <%if(exptype == 3){%><h2>Expiry in 45-65 Days</h2><%}%>
                                                                            <%if(exptype == 4){%><h2> Expiry in 90 Days</h2><%}%>
                                                                            <%if(exptype == 5){%><h2>Pending</h2><%}%>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row full_width">
                                                                        <div id="chartdiv_1" style="height:350px;"></div>
                                                                    </div>
                                                                </div>
                                                            </div>	
                                                        </div>

                                                        <div class="col-md-8">
                                                            <div class="row">
                                                                <div class="col-lg-12">
                                                                    <div class="row mb-3">
                                                                        <div class="col-lg-5">
                                                                            <div class="row">
                                                                                <label for="example-text-input" class="col-sm-3 col-form-label">Search</label>
                                                                                <div class="col-sm-9 field_ic">
                                                                                    <html:text property ="search" styleClass="form-control" styleId="example-text-input" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                                            this.removeAttribute('readonly');
                                                                                            this.blur();
                                                                                            this.focus();
                                                                                        }"/>
                                                                                    <a href="javascript: searchPosition();" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-lg-3">
                                                                            <div class="row">
                                                                                <div class="col-sm-12 field_ic">
                                                                                    <html:select property="positionId" styleId="positionId" styleClass="form-select">
                                                                                        <html:optionsCollection filter="false" property="positions" label="ddlLabel" value="ddlValue">
                                                                                        </html:optionsCollection>
                                                                                    </html:select>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-lg-1">
                                                                            <a href="javascript: searchPosition();" class="go_btn">Go</a>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="col-lg-12 total_role_table total_role_font">
                                                                    <div class="table-rep-plugin sort_table">
                                                                        <div class="table-responsive1 mb-0">
                                                                            <table class="table table-striped">
                                                                                <thead>
                                                                                    <tr>
                                                                                        <th width="%"><span><b>Department</b></span></th>
                                                                                        <th width="%"><span><b>Positions</b></span></th>
                                                                                        <th width="%"><span><b>Personnel</b></span></th>
                                                                                        <th width="%"><span><b>Role Competency</b></span></th>
                                                                                        <th width="%" class="text-center"><span><b>Expiry in Days</b></span></th>
                                                                                        <th width="%" class="text-center"><span><b>Priority</b></span></th>
                                                                                    </tr>
                                                                                </thead>
                                                                                <tbody>
                                                                                   
<%
                                                                    int status = 0;
                                                                    for(int i = 0 ;i < listtotal;i++)
                                                                    {
                                                                        CompetencyInfo info = (CompetencyInfo)list.get(i);
                                                                        if (info != null)
                                                                        {
                                                                            status = info.getStatus();
%>   
                                                                            <tr>
                                                                                <td><%=info.getDepartment() != null ? info.getDepartment(): ""%></td>
                                                                                <td><%=info.getPosition() != null ? info.getPosition(): ""%></td>
                                                                                <td><%=info.getName() != null ? info.getName(): ""%></td>
                                                                                <td><%=info.getRole() != null ? info.getRole(): ""%></td>
                                                                                <td class="text-center"><%if(status == 4){%><%=info.getDateDiff()%><%}else{%>-<%}%></td> 
                                                                                <td class="text-center"><%=info.getPriority()!= null ? info.getPriority(): ""%></td>
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


        <script src="../assets/js/footer.js"></script> 


        <!-- JAVASCRIPT -->
        <script src="../assets/libs/jquery/jquery.min.js"></script>

        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>

        <script src="../assets/js/echarts.js" type="text/javascript"></script>
        <script src="../assets/js/stack/index.js"></script>
        <script src="../assets/js/stack/xy.js"></script>
        <script src="../assets/js/stack/Animated.js"></script>
        <script src="../assets/js/app.js"></script>

        <!-- Responsive Table js -->
        <script src="../assets/js/rwd-table.min.js"></script>
        <script src="../assets/js/table-responsive.init.js"></script>

        <script>
            am5.ready(function() {

            // Create root element
            // https://www.amcharts.com/docs/v5/getting-started/#Root_element
            var root = am5.Root.new("chartdiv_1");
            // Set themes
            // https://www.amcharts.com/docs/v5/concepts/themes/
            root.setThemes([
                    am5themes_Animated.new(root)
            ]);
            // Create chart
            // https://www.amcharts.com/docs/v5/charts/xy-chart/
            var chart = root.container.children.push(am5xy.XYChart.new(root, {
            panX: true,
                    panY: true,
                    wheelX: "panX",
                    wheelY: "zoomX",
                    pinchZoomX: true
            }));
            // Add cursor
            // https://www.amcharts.com/docs/v5/charts/xy-chart/cursor/
            var cursor = chart.set("cursor", am5xy.XYCursor.new(root, {}));
            cursor.lineY.set("visible", false);
            // Create axes
            // https://www.amcharts.com/docs/v5/charts/xy-chart/axes/
            var xRenderer = am5xy.AxisRendererX.new(root, { minGridDistance: 10 });
            xRenderer.labels.template.setAll({
            fontSize: 12,
                    rotation: - 75,
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
            // Create series
            // https://www.amcharts.com/docs/v5/charts/xy-chart/series/
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
            series.columns.template.setAll({ cornerRadiusTL: 0, cornerRadiusTR: 0, strokeOpacity: 0 });
            series.columns.template.adapters.add("fill", function(fill, target) {
            return chart.get("colors").getIndex(series.columns.indexOf(target));
            });
            series.columns.template.adapters.add("stroke", function(stroke, target) {
            return chart.get("colors").getIndex(series.columns.indexOf(target));
            });
            // Set data
            var data = [
            <%=gstr%>
            ];
            chart.get("colors").set("colors", [
                    am5.color(0x2478A6),
                    am5.color(0x2478A6),
                    am5.color(0x2478A6)
            ]);
            series.columns.template.setAll({width:15});
            xAxis.data.setAll(data);
            series.data.setAll(data);
            // Make stuff animate on load
            // https://www.amcharts.com/docs/v5/concepts/animations/
            series.appear(1000);
            chart.appear(1000, 100);
            }); // end am5.ready()
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