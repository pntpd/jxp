<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.documentexpiry.DocumentexpiryInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="documentexpiry" class="com.web.jxp.documentexpiry.Documentexpiry" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 2, submtp = 70, ctp= 2;
        String addper = "N", editper = "N";
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
                addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }
        }
        ArrayList list = new ArrayList();
        if(session.getAttribute("ALERT_LIST") != null)
            list = (ArrayList) session.getAttribute("ALERT_LIST");
        int total = list.size();
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
        String viewpath = documentexpiry.getMainPath("view_maillog_file");

%>
    <head>
        <meta charset="utf-8">
        <title><%= documentexpiry.getMainPath("title") != null ? documentexpiry.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <!-- Responsive Table css -->
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        
        
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
	<link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
	<link href="../assets/time/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
        
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/documentexpiry.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/documentexpiry/DocumentexpiryAction.do" onsubmit="return false;">
            <html:hidden property="viewExpiry"/>
            <html:hidden property="deleteAlert"/>
            <html:hidden property="notificationId"/>
            <html:hidden property="candidateId"/>
            <html:hidden property="doView"/>
            <html:hidden property="jobpostId"/>
            <html:hidden property="clientId"/>
            <html:hidden property="clientassetId"/>
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file="../header.jsp" %>
                <%@include file="../sidemenu.jsp" %>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content tab_panel">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start back_arrow">
                                    <a href="javascript: backtoold();"><img src="../assets/images/back-arrow.png"/></a>
                                    <span>Notifications</span>
                                </div>
                                
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
                                                <li><a href="javascript: ;"><img src="../assets/images/open-in-new-tab.png"/> Open in New Tab</a></li>
                                                <li><a href="javascript:;"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                                <li><a href="javascript: exportAlert();"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>	
                            <div class="col-md-12 col-xl-12 tab_head_area">
                                <ul class='nav nav-tabs nav-tabs-custom' id='tab_menu'>
                                    <li id='list_menu1' class='nav-item'>
                                        <a class='nav-link <%=(ctp == 1) ? " active" : ""%>' href="javascript: viewExpiry();">
                                            <span class='d-none d-md-block'>Expiry</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                    <li id='list_menu1' class='nav-item'>
                                        <a class='nav-link <%=(ctp == 2) ? " active" : ""%>' href="javascript: viewAlerts();">
                                            <span class='d-none d-md-block'>Alerts</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                </ul> 
                            </div>                                    
                        </div>                                        
                        
                        <div class="container-fluid">                            
                            <div class="row">
                                <div class="col-md-12 col-xl-12">
                                    <div class="body-background">
                                        <div class="row">                                
                                            <div class="col-md-12 col-xl-12">
                                                <div class="">
                                                    <div class="row" >
                                                        <div class="row mb_20">
                                                            <div class="col-lg-9">
                                                                <div class="row">
                                                                    <div class="col-lg-3">
                                                                        <div class="row">
                                                                            <label for="example-text-input" class="col-sm-3 col-form-label">Search:</label>
                                                                            <div class="col-sm-9 field_ic">
                                                                                <html:text property ="search2" styleClass="form-control" styleId="example-text-input" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                                           this.removeAttribute('readonly');
                                                                                           this.blur();
                                                                                           this.focus();
                                                                                           }"/>
                                                                                <a href="javascript: searchFormAjax2('s','-1');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-3 text-right">
                                                                <a href="javascript: resetFilter2();" class="reset_export mr_8"><img src="../assets/images/refresh.png"> Reset Filters</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>	
                                        
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="row mb-3">                                                
                                                    <div class="col-lg-4">
                                                        <div class="row">
                                                            <label for="example-text-input" class="col-sm-2 col-form-label">Date</label>
                                                            <div class="col-sm-10 field_ic">
                                                                <div class="input-daterange input-group input-addon display_flex">
                                                                    <html:text property="fromDate" styleId="wesl_from_dt" styleClass="form-control wesl_from_dt"/>
                                                                    <script type="text/javascript">
                                                                        document.getElementById("wesl_from_dt").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                                    </script>
                                                                    <div class="input-group-add">
                                                                        <span class="input-group-text">To</span>
                                                                    </div>
                                                                    <html:text property="toDate" styleId="wesl_to_dt" styleClass="form-control wesl_from_dt"/>
                                                                    <script type="text/javascript">
                                                                        document.getElementById("wesl_to_dt").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                                    </script>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-1">
                                                        <a href="javascript: searchFormAjax2('s','-1');" class="go_btn">Go</a>
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <div class="row">
                                                            <div class="col-sm-4 field_ic">
                                                                <html:select property="clientIdAlert" styleId="clientIdAlert" styleClass="form-select" onchange="javascript: setAssetAlert();">
                                                                    <html:optionsCollection filter="false" property="clients" label="ddlLabel" value="ddlValue">
                                                                    </html:optionsCollection>
                                                                </html:select>
                                                            </div>
                                                            <div class="col-sm-4 field_ic">
                                                                <html:select property="assetIdAlert" styleId="assetIdAlert" styleClass="form-select" onchange="javascript: searchFormAjax2('s','-1');">
                                                                    <html:optionsCollection filter="false" property="assets" label="ddlLabel" value="ddlValue">
                                                                    </html:optionsCollection>
                                                                </html:select>
                                                            </div>
                                                            <div class="col-sm-4 field_ic">
                                                                <html:select styleClass="form-select" property="moduleId" onchange="javascript: searchFormAjax2('s','-1');">
                                                                    <html:option value="-1">Select Module</html:option>
                                                                    <html:option value="1">Candidate Selection</html:option>
                                                                    <html:option value="2">Open Form</html:option>
                                                                    <html:option value="3">Client Selection</html:option>
                                                                    <html:option value="4">Job Posting</html:option>
                                                                    <html:option value="5">Crew Rotation</html:option>
                                                                </html:select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="row" id='ajax_cat2'>                                                                               
                                            <div class="col-lg-12" id="printArea">
                                                <div class="table-rep-plugin sort_table">
                                                    <div class="table-responsive mb-0" data-bs-pattern="priority-columns">        
                                                        <table id="tech-companies-1" class="table table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <th width="20%">
                                                                        <span><b>Action By</b></span>
                                                                        <a href="javascript: sortForm2('1', '2');" id="img_1_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                        <a href="javascript: sortForm2('1', '1');" id="img_1_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                    </th>
                                                                    <th width="15%">
                                                                        <span><b>Module</b></span>
                                                                        <a href="javascript: sortForm2('2', '2');" id="img_2_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                        <a href="javascript: sortForm2('2', '1');" id="img_2_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                    </th>
                                                                    <th width="15%">
                                                                        <span><b>Action Type</b></span>
                                                                        <a href="javascript: sortForm2('3', '2');" id="img_3_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                        <a href="javascript: sortForm2('3', '1');" id="img_3_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                    </th>
                                                                    <th width="15%">
                                                                        <span><b>Client</b></span>
                                                                        <a href="javascript: sortForm2('4', '2');" id="img_4_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                        <a href="javascript: sortForm2('4', '1');" id="img_4_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                    </th>
                                                                    <th width="15%">
                                                                        <span><b>Asset</b></span>
                                                                        <a href="javascript: sortForm2('5', '2');" id="img_5_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                        <a href="javascript: sortForm2('5', '1');" id="img_5_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                    </th>
                                                                    <th width="10%">
                                                                        <span><b>Date-Time</b></span>
                                                                        <a href="javascript: sortForm2('6', '2');" id="img_6_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                        <a href="javascript: sortForm2('6', '1');" id="img_6_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                    </th>
                                                                    <th width="10%" class="text-right">
                                                                        <span><b>Action</b></span>
                                                                    </th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="sort_id2">
<%
                                                                DocumentexpiryInfo info;
                                                                for (int i = 0; i < total; i++)
                                                                {
                                                                    info = (DocumentexpiryInfo) list.get(i);
                                                                    if (info != null) 
                                                                    {
                                                                        int mainId = info.getMainId();
                                                                        int moduleId = info.getModuleId();
                                                                        int clientId = info.getClientId();
                                                                        int clientassetId = info.getClientassetId();
                                                                        int notificationId = info.getNotificationId();
%>
                                                                    <tr>
                                                                        <td class="hand_cursor"
                                                                            <%if(moduleId == 1){%> 
                                                                                onclick="javascript: goToCrew('<%=mainId%>');" 
                                                                            <%}else if(moduleId == 2){%> 
                                                                                onclick="javascript: goToClientSelectionByAssetId('<%= mainId%>');" 
                                                                            <%}else if(moduleId == 3){%> 
                                                                                onclick="javascript: showJobpostDetail('<%= mainId%>');" 
                                                                            <%} else if(moduleId==4){%> 
                                                                                onclick="javascript: goToClientSelectionById('<%= mainId%>');" 
                                                                            <%}else if(moduleId== 5){%> 
                                                                                onclick="javascript: goToCrewrotation('<%= clientId%>', '<%= clientassetId%>');" <%}%> >
                                                                            <%=info.getName() != null ? info.getName() : ""%>
                                                                        </td>
                                                                        <td class="hand_cursor"  
                                                                             <%if(moduleId == 1){%> 
                                                                                onclick="javascript: goToCrew('<%=mainId%>');" 
                                                                            <%}else if(moduleId == 2){%> 
                                                                                onclick="javascript: goToClientSelectionByAssetId('<%= mainId%>');" 
                                                                            <%}else if(moduleId == 3){%> 
                                                                                onclick="javascript: showJobpostDetail('<%= mainId%>');" 
                                                                            <%} else if(moduleId==4){%> 
                                                                                onclick="javascript: goToClientSelectionById('<%= mainId%>');" 
                                                                            <%}else if(moduleId== 5){%> 
                                                                                onclick="javascript: goToCrewrotation('<%= clientId%>', '<%= clientassetId%>');" <%}%> >
                                                                            <%= info.getModuleName() != null ? info.getModuleName() : ""%>
                                                                        </td>
                                                                        <td class="hand_cursor"
                                                                             <%if(moduleId == 1){%> 
                                                                                onclick="javascript: goToCrew('<%=mainId%>');" 
                                                                            <%}else if(moduleId == 2){%> 
                                                                                onclick="javascript: goToClientSelectionByAssetId('<%= mainId%>');" 
                                                                            <%}else if(moduleId == 3){%> 
                                                                                onclick="javascript: showJobpostDetail('<%= mainId%>');" 
                                                                            <%} else if(moduleId==4){%> 
                                                                                onclick="javascript: goToClientSelectionById('<%= mainId%>');" 
                                                                            <%}else if(moduleId== 5){%> 
                                                                                onclick="javascript: goToCrewrotation('<%= clientId%>', '<%= clientassetId%>');" <%}%> >
                                                                            <%= info.getStVal()  != null ? info.getStVal() : ""%>
                                                                        </td>
                                                                        <td class="hand_cursor"
                                                                            <%if(moduleId == 1){%> 
                                                                                onclick="javascript: goToCrew('<%=mainId%>');" 
                                                                            <%}else if(moduleId == 2){%> 
                                                                                onclick="javascript: goToClientSelectionByAssetId('<%= mainId%>');" 
                                                                            <%}else if(moduleId == 3){%> 
                                                                                onclick="javascript: showJobpostDetail('<%= mainId%>');" 
                                                                            <%} else if(moduleId==4){%> 
                                                                                onclick="javascript: goToClientSelectionById('<%= mainId%>');" 
                                                                            <%}else if(moduleId== 5){%> 
                                                                                onclick="javascript: goToCrewrotation('<%= clientId%>', '<%= clientassetId%>');" <%}%> >
                                                                            <%= info.getClientName() != null ? info.getClientName() : ""%>
                                                                        </td>
                                                                        <td class="hand_cursor"
                                                                            <%if(moduleId == 1){%> 
                                                                                onclick="javascript: goToCrew('<%=mainId%>');" 
                                                                            <%}else if(moduleId == 2){%> 
                                                                                onclick="javascript: goToClientSelectionByAssetId('<%= mainId%>');" 
                                                                            <%}else if(moduleId == 3){%> 
                                                                                onclick="javascript: showJobpostDetail('<%= mainId%>');" 
                                                                            <%} else if(moduleId==4){%> 
                                                                                onclick="javascript: goToClientSelectionById('<%= mainId%>');" 
                                                                            <%}else if(moduleId== 5){%> 
                                                                                onclick="javascript: goToCrewrotation('<%= clientId%>', '<%= clientassetId%>');" <%}%> >
                                                                            <%= info.getAssetName() != null ? info.getAssetName() : ""%>
                                                                        </td>
                                                                        <td class="hand_cursor"
                                                                            <%if(moduleId == 1){%> 
                                                                                onclick="javascript: goToCrew('<%=mainId%>');" 
                                                                            <%}else if(moduleId == 2){%> 
                                                                                onclick="javascript: goToClientSelectionByAssetId('<%= mainId%>');" 
                                                                            <%}else if(moduleId == 3){%> 
                                                                                onclick="javascript: showJobpostDetail('<%= mainId%>');" 
                                                                            <%} else if(moduleId==4){%> 
                                                                                onclick="javascript: goToClientSelectionById('<%= mainId%>');" 
                                                                            <%}else if(moduleId== 5){%> 
                                                                                onclick="javascript: goToCrewrotation('<%= clientId%>', '<%= clientassetId%>');" <%}%> >
                                                                            <%= info.getDate() != null ? info.getDate() : ""%>
                                                                        </td>
                                                                        <td class="action_column text-right">
                                                                            <% if(info.getFilename() != null && !info.getFilename().equals("")) {%>
                                                                            <a href="<%=viewpath+info.getFilename()%>" class="view_mode mr_15" target="_blank">
                                                                                <img src="../assets/images/view.png"/></a>
                                                                            <% } else {%>&nbsp;<% } %>  
                                                                            <%--<a class="coll_form_ic" href="javascript: deleteAlert('<%=notificationId%>');"><img src="../assets/images/cross.png" /></a>--%>
                                                                        </td>                                                                        
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
                    <!-- End Page-content -->
                </div>
                <!-- end main content-->
            </div>
            </div>
            <!-- END layout-wrapper -->
            <%@include file ="../footer.jsp"%>
            <!-- JAVASCRIPT -->
            <script src="../assets/libs/jquery/jquery.min.js"></script>
            <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
            <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
            <script src="../assets/js/app.js"></script>
            <!-- Responsive Table js -->
            <script src="../assets/js/rwd-table.min.js"></script>
            <script src="../assets/js/table-responsive.init.js"></script>
            <script src="../assets/time/bootstrap-timepicker.min.js" type="text/javascript"></script>
            <script src="../assets/js/bootstrap-select.min.js"></script> 
            <script src="../assets/js/bootstrap-datepicker.min.js"></script>
            <script src="../assets/time/components-date-time-pickers.min.js" type="text/javascript"></script>
            
            <script src="/jxp/assets/js/sweetalert2.min.js"></script>
            <script>
            // toggle class show hide text section
            $(document).on('click', '.toggle-title', function () {
                $(this).parent()
                        .toggleClass('toggled-on')
                        .toggleClass('toggled-off');
            });
            </script>
            <script>
            $(document).ready(function () {
                $('input').attr('autocomplete', 'off');
            });
        </script>
        
        <script>
			//Timepicker
			$(".timepicker-24").timepicker({
				format: 'mm:mm',
				autoclose: !0,
                minuteStep: 5,
                showSeconds: !1,
                showMeridian: !1
			});
		</script>
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
        
        <script type="text/javascript">
            function addLoadEvent(func) {
                  var oldonload = window.onload;
                  if (typeof window.onload != 'function') {
                    window.onload = func;
                  } else {
                    window.onload = function() {
                      if (oldonload) {
                        oldonload();
                      }
                    }
                  }
                }
                addLoadEvent(showhide());
                addLoadEvent(function() {
                })
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