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
            int mtp = 2, submtp = 70, ctp =1;
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
            ArrayList documentexpiry_list = new ArrayList();
            int count = 0;
            int recordsperpage = documentexpiry.getCount();
            if(session.getAttribute("COUNT_LIST") != null)
                count = Integer.parseInt((String) session.getAttribute("COUNT_LIST"));
            if(session.getAttribute("DOCUMENTEXPIRY_LIST") != null)
                documentexpiry_list = (ArrayList) session.getAttribute("DOCUMENTEXPIRY_LIST");
            int pageSize = count / recordsperpage;
            if(count % recordsperpage > 0)
                pageSize = pageSize + 1;

            int total = documentexpiry_list.size();
            int showsizelist = documentexpiry.getCountList("show_size_list");
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
                
            int reminderCount = 0;
            if(session.getAttribute("TOTALRCOUNT") != null)
            {
                reminderCount = Integer.parseInt((String) session.getAttribute("TOTALRCOUNT"));
                session.removeAttribute("TOTALRCOUNT");
            }
            String tabno = "1";
            if(session.getAttribute("TABNO") != null)
                tabno = (String)session.getAttribute("TABNO");
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
            <html:hidden property="doRemind"/>    
            <html:hidden property="govdocId"/>
            <html:hidden property="doRemindAll"/>
            <html:hidden property="viewAlerts"/>
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
                                            <%@include file ="../shortcutmenu.jsp"%>
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
                                                        <div class="col-lg-10"> 
                                                            <div class="row mb-3">
                                                                <div class="col-lg-2 mb_15">
                                                                    <div class="row">
                                                                        <div class="col-sm-12 field_ic">
                                                                            <html:text property ="search" styleClass="form-control" styleId="example-text-input" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                                       this.removeAttribute('readonly');
                                                                                       this.blur();
                                                                                       this.focus();
                                                                                       }"/>
                                                                            <a href="javascript: searchFormAjax('s','-1');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-2 mb_15">
                                                                    <div class="row">
                                                                        <div class="col-sm-12 field_ic">
                                                                            <html:select property="clientIdIndex" styleId="clientIdIndex" styleClass="form-select" onchange="javascript: setAssetDDL();">
                                                                                <html:optionsCollection filter="false" property="clients" label="ddlLabel" value="ddlValue">
                                                                                </html:optionsCollection>
                                                                            </html:select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-2 mb_15">
                                                                    <div class="row">
                                                                        <div class="col-sm-12 field_ic">
                                                                            <html:select property="assetIdIndex" styleId="assetIdIndex" styleClass="form-select" onchange="javascript: searchFormAjax('s','-1');">
                                                                                <html:optionsCollection filter="false" property="assets" label="ddlLabel" value="ddlValue">
                                                                                </html:optionsCollection>
                                                                            </html:select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-2 mb_15">
                                                                    <div class="row">
                                                                        <div class="col-sm-12 field_ic">
                                                                            <html:select styleClass="form-select" property="dropdownId" onchange="javascript: showhide();">
                                                                                <html:option value="2">Document</html:option>
                                                                                <html:option value="1">Certificates</html:option>
                                                                                <html:option value="3">Medical Certificate</html:option>
                                                                            </html:select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-2 mb_15" id="coursediv" style="display: none">
                                                                    <div class="row">
                                                                        <div class="col-sm-12 field_ic">
                                                                            <html:select property="coursenameId" styleId="coursenameId" styleClass="form-select" onchange="javascript: searchFormAjax('s','-1');">
                                                                                <html:optionsCollection filter="false" property="coursenames" label="ddlLabel" value="ddlValue">
                                                                                </html:optionsCollection>
                                                                            </html:select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-2 mb_15" id="documentdiv" style="display: none">
                                                                    <div class="row">
                                                                        <div class="col-sm-12 field_ic">
                                                                            <html:select property="documentId" styleId="documentId" styleClass="form-select" onchange="javascript: searchFormAjax('s','-1');">
                                                                                <html:optionsCollection filter="false" property="documents" label="ddlLabel" value="ddlValue">
                                                                                </html:optionsCollection>
                                                                            </html:select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-2 mb_15" id="healthdiv" style="display: none">
                                                                    <div class="row">
                                                                        <div class="col-sm-12 field_ic">
                                                                            <html:select styleClass="form-select" property="healthId" onchange="javascript: searchFormAjax('s','-1');">
                                                                                <html:option value="1">OGUK Medical FTW</html:option>
                                                                                <html:option value="2">Medical Fitness Certificate</html:option>
                                                                            </html:select>
                                                                        </div>
                                                                    </div>
                                                                </div>                                  

                                                                <div class="col-lg-2 mb_15">
                                                                    <div class="row">
                                                                        <div class="col-sm-12 field_ic">
                                                                            <html:select styleClass="form-select" property="exp" onchange="javascript: searchFormAjax('s','-1');">
                                                                                <html:option value="1">Expiring (10 days)</html:option>
                                                                                <html:option value="3">Expiring (90 days)</html:option>
                                                                                <html:option value="4">Expiring (180 days)</html:option>
                                                                                <html:option value="5">Expiring (365 days)</html:option>
                                                                                <html:option value="2">Expired </html:option>
                                                                            </html:select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-2 mb_15">
                                                                    <div class="row">
                                                                        <div class="col-sm-12 field_ic">
                                                                            <html:select styleClass="form-select" property="type" onchange="javascript: searchFormAjax('s','-1');">
                                                                                <html:option value="-1">Reminder Status</html:option>
                                                                                <html:option value="1">Reminder Pending</html:option>
                                                                                <html:option value="2">Reminder Sent</html:option>
                                                                            </html:select>
                                                                        </div>
                                                                    </div>
                                                                </div>  
                                                            </div>    
                                                        </div>
                                                        <div class="col-lg-2 text-right pd_0" id='remindall_div'>  
                                                            <% if(addper.equals("Y") || editper.equals("Y")) {%><a id="assignhref" href="javascript:;" class="add_user remind_selected mr_5" style="display:none;">Remind&nbsp;Selected</a><% } %> 
                                                            <a href="javascript:resetFilter();" class="reset_export"><img src="../assets/images/refresh.png"/> Reset Filters</a>
                                                        </div>                                        
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="row" id='ajax_cat'>
                                            <input type="hidden" name="nextDel" value="<%= total %>" />
                                                                               
                                            <div class="col-lg-12" id="printArea">
                                                <div class="table-rep-plugin sort_table">
                                                    <div class="table-responsive mb-0" data-bs-pattern="priority-columns">        
                                                        <table id="tech-companies-1" class="table table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <th width="3%" class="select_check_box">
                                                                        <label class="mt-checkbox mt-checkbox-outline"> 
                                                                            <input type="checkbox" class="" name="govdoccball" id="govdoccball" onchange="javascrip: setall();" />
                                                                            <span></span>
                                                                        </label>	
                                                                    </th>
                                                                    <th width="7%">
                                                                        <span><b>ID</b></span>
                                                                        <a href="javascript: sortForm('1', '2');" id="img_1_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                        <a href="javascript: sortForm('1', '1');" id="img_1_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                    </th>
                                                                    <th width="18%">
                                                                        <span><b>Name</b></span>
                                                                        <a href="javascript: sortForm('2', '2');" id="img_2_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                        <a href="javascript: sortForm('2', '1');" id="img_2_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                    </th>
                                                                    <th width="18%">
                                                                        <span><b>Client Name</b></span>
                                                                        <a href="javascript: sortForm('3', '2');" id="img_3_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                        <a href="javascript: sortForm('3', '1');" id="img_3_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                    </th>
                                                                    <th width="18%">
                                                                        <span><b>Asset Name</b></span>
                                                                        <a href="javascript: sortForm('4', '2');" id="img_4_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                        <a href="javascript: sortForm('4', '1');" id="img_4_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                    </th>
                                                                    <th width="17%">
                                                                        <span><b>Document Name</b></span>
                                                                        <a href="javascript: sortForm('5', '2');" id="img_5_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                        <a href="javascript: sortForm('5', '1');" id="img_5_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                    </th>                                                                    
                                                                    <th width="11%">
                                                                        <span><b>Expiry Date</b></span>
                                                                        <a href="javascript: sortForm('6', '2');" id="img_6_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                        <a href="javascript: sortForm('6', '1');" id="img_6_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                    </th>
                                                                    <th width="6%" class="text-right">
                                                                        <span><b>&nbsp;</b></span>
                                                                    </th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="sort_id">
<%
                                                                DocumentexpiryInfo info;
                                                                for (int i = 0; i < total; i++)
                                                                {
                                                                    info = (DocumentexpiryInfo) documentexpiry_list.get(i);
                                                                    if (info != null) 
                                                                    {
%>
                                                                    <tr>
                                                                        <td class="select_check_box">
                                                                            <label class="mt-checkbox mt-checkbox-outline"> 
                                                                                <input type="checkbox" value="<%=info.getGovdocId()%>" name="govdoccb" id="govdoccb" class="singlechkbox" onchange="javascript: setcb();" />
                                                                                <span></span>
                                                                            </label>	
                                                                        </td>
                                                                        <td><%= documentexpiry.changeNum(info.getCandidateId(), 6)%></td>
                                                                        <td><%= info.getName() != null ? info.getName() : ""%></td>
                                                                        <td><%= info.getClientName() != null ? info.getClientName() : ""%></td>
                                                                        <td><%= info.getAssetName() != null ? info.getAssetName() : ""%></td>
                                                                        <td><%= info.getDocumentName() != null ? info.getDocumentName() : ""%></td>
                                                                        <td><%= info.getExpiryDate() != null ? info.getExpiryDate() : ""%></td>
                                                                        <td class="text-center"><% if(addper.equals("Y") || editper.equals("Y")) {%><a class="remind_bell_btn" href="javascript: sendmail('<%=info.getGovdocId()%>');"><i class="far fa-bell"></i> <span><%=info.getRemind()%></span></a><% } else {%>&nbsp;<% } %></td>
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
            <script src="../assets/time/bootstrap-timepicker.min.js" type="text/javascript"></script>
            <script src="../assets/js/app.js"></script>
            <!-- Responsive Table js -->
            <script src="../assets/js/rwd-table.min.js"></script>
            <script src="../assets/js/table-responsive.init.js"></script>
            
            
            
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
					format: "dd-mm-yyyy",
					autoclose: "true",
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