<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.crewdayrate.CrewdayrateInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="crewdayrate" class="com.web.jxp.crewdayrate.Crewdayrate" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 74;
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
            CrewdayrateInfo info = null;
            int clientId = 0, positionId= 0;
            if(session.getAttribute("BASICINFO") != null)
            {
                info = (CrewdayrateInfo) session.getAttribute("BASICINFO");
                if(info != null)
                    clientId = info.getClientId();
            }
            CrewdayrateInfo pinfo = null;

            ArrayList plist = new ArrayList();
            if(session.getAttribute("POSITION_LIST") != null)
            {
                plist = (ArrayList) session.getAttribute("POSITION_LIST");
            }
            int plist_size = plist.size();
            ArrayList clist = new ArrayList();
            if(session.getAttribute("C_LIST") != null)
            {
                clist = (ArrayList) session.getAttribute("C_LIST");
            }
            String thankyou = "no";
            if(request.getAttribute("SAVERATE") != null)
            {
                thankyou = (String)request.getAttribute("SAVERATE");
                request.removeAttribute("SAVERATE");
            }
            String message = "", clsmessage = "red_font";
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }        
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";
    %>  
    <head>
        <meta charset="utf-8">
        <title><%= crewdayrate.getMainPath("title") != null ? crewdayrate.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="shortcut icon" href="../assets/images/favicon.png" />
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css" />
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet" />
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/crewdayrate.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/crewdayrate/CrewdayrateAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
            <html:hidden property="doCancel"/>
            <html:hidden property="search"/>
            <html:hidden property="clientIdIndex"/>
            <html:hidden property="assetIdIndex"/>
            <html:hidden property="assetId" />
            <html:hidden property="doSave" />
            <html:hidden property="doView" />
            <html:hidden property="doView2" />
            <html:hidden property="doModifyAsset"/>
            <html:hidden property="clientassetId"/>
            <html:hidden property="typeId"/>
            <input type="hidden" name="clientId" value="<%=clientId%>" />
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file="../header.jsp" %>
                <%@include file="../sidemenu.jsp" %>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content tab_panel no_tab1">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start"><span class="back_arrow"><a href="javascript: goback();"><img src="../assets/images/back-arrow.png"></a> Crew Day Rate</span></div>
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
                                                <li><a href="javascript: exportDetails();"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>	
                            <div class="col-md-12 metrix_top_right">
                                <div class="row d-flex align-items-center">
                                    <div class="col-md-4 com_label_value">
                                        <div class="row mb_0">
                                            <div class="col-md-7 pd_right_0">
                                                <div class="row mb_0">
                                                    <div class="col-md-3"><label>Client</label></div>
                                                    <div class="col-md-9 text-left pd_0 single_line"><span><%= info != null && info.getClientName() != null ? info.getClientName() : "" %></span></div>
                                                </div>
                                            </div>
                                            <div class="col-md-5">
                                                <div class="row mb_0">
                                                    <div class="col-md-4"><label>Asset</label></div>
                                                    <div class="col-md-8 text-left pd_0 single_line"><span><%= info != null && info.getClientAssetName() != null ? info.getClientAssetName() : "" %></span></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-3 com_label_value">
                                        <div class="row mb_0">
                                            <div class="col-md-4">
                                                <div class="row mb_0">
                                                    <div class="col-md-5"><label>Positions</label></div>
                                                    <div class="col-md-7 text-right"><span><%=plist_size%></span></div>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="row mb_0">
                                                    <div class="col-md-5"><label>Personnel</label></div>
                                                    <div class="col-md-7 text-right"><span><%=clist.size()%></span></div>
                                                </div>
                                            </div>
                                            <div class="col-md-4 com_label_value">
                                                <div class="row mb_0">
                                                    <div class="col-md-5"><label>Currency</label></div>
                                                    <div class="col-md-7 text-right"><span><%= info != null && info.getCurrencyName() != null ? info.getCurrencyName() : "" %></span></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4 com_label_value">
                                        <div class="row mb_0">
                                            <div class="col-md-12 single_line"><span></span></div>
                                        </div>
                                    </div>
                                    <div class="col-md-1">
                                        <div class="ref_vie_ope edit_metrix">
                                            <%if(addper.equals("Y") || (editper.equals("Y"))){%>
                                            <ul><li><a href="javascript: modifyClientAsset();"><img src="../assets/images/edit.png"/></a></li></ul>
                                                        <%}%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="container-fluid pd_0" id="printArea">
                            <div class="row">
                                <div class="col-md-12 col-xl-12 pd_0">
                                    <div class="body-background">
                                        <div class="row d-none1">
                                            <div class="col-lg-12 pd_left_right_50">
                                                <div class="main-heading m_30">
                                                    <div class="add-btn">
                                                        <h4>POSITION LIST </h4>
                                                    </div>
                                                </div>
                                                <div class="col-lg-9">
                                                    <div class="row mb-3">
                                                        <div class="col-lg-3">
                                                            <div class="row">
                                                                <label for="example-text-input" class="col-sm-3 col-form-label">Search:</label>
                                                                <div class="col-sm-9 field_ic">
                                                                    <html:text property ="search2" styleId="example-text-input" styleClass="form-control" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {this.removeAttribute('readonly'); this.blur(); this.focus();}"/>
                                                                    <a href="javascript: searchDetails();" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-6">
                                                            <div class="row">
                                                                <label for="example-text-input" class="col-sm-2 col-form-label">Positions:</label>
                                                                <div class="col-sm-9 field_ic">
                                                                    <html:select property="positionId2" styleId="positionId2" styleClass="form-select" onchange="javascript: searchDetails();" >
                                                                        <html:optionsCollection filter="false" property="positions" label="ddlLabel" value="ddlValue">
                                                                        </html:optionsCollection>
                                                                    </html:select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                                                                     
                                                    </div>
                                                </div>
                                                
                                                <%
                                                    if(plist_size > 0)
                                                    {
                                                %>
                                                <div class="row">
                                                    <div class="col-lg-12">
                                                        <div class="table-rep-plugin sort_table">
                                                            <div class="table-responsive mb-0">
                                                                <table id="tech-companies-1" class="table table-striped">
                                                                    <thead>
                                                                        <tr>
                                                                            <th width="20%">
                                                                                <span><b>Position Rank</b> </span>
                                                                            </th>
                                                                            <th width="20%" class="text-left">
                                                                                <span><b>Personnel</b></span>
                                                                            </th>
                                                                            <th width="15%" class="text-left">
                                                                                <span><b>Position Day Rate</b></span>
                                                                            </th>
                                                                            <th width="5%" class="text-left">
                                                                                <span><b>Day Rate</b></span>
                                                                            </th>
                                                                            <th width="5%" class="text-left">
                                                                                <span><b>Overtime / hr</b></span>
                                                                            </th>
                                                                            <th width="5%" class="text-left">
                                                                                <span><b>Allowances</b></span>
                                                                            </th>
                                                                            <th width="10%" class="text-left">
                                                                                <span><b>From Date</b></span>
                                                                            </th>
                                                                            <th width="10%" class="text-left">
                                                                                <span><b>To Date</b></span>
                                                                            </th>
                                                                            <th width="5%" class="text-left">
                                                                                <span><b>History</b></span>
                                                                            </th>
                                                                            <th width="5%" class="text-left">
                                                                                <span><b>Action</b></span>
                                                                            </th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
<%                                                              int cc = 0;
                                                                for(int i = 0; i < plist_size; i++)
                                                                {
                                                                    CrewdayrateInfo cinfo = (CrewdayrateInfo) plist.get(i);
                                                                    if(cinfo != null)
                                                                    {
                                                                        positionId = cinfo.getDdlValue();                                                                        
                                                                        ArrayList list = crewdayrate.getListFromList(clist, positionId);
                                                                        int list_size = list.size();
                                                                        if(list_size > 0)
                                                                        {
                                                                            cc++;
%>      
                                                                        <tr>
                                                                            <td><%= cinfo.getDdlLabel() != null ? cinfo.getDdlLabel() : "" %></td>
                                                                            <td>&nbsp;</td>
                                                                            <td>
                                                                                <input type="text" id="prate_<%=cc%>_<%=positionId%>" name="prate" class="form-control" onkeypress="return allowPositiveNumber(event);" placeholder="Enter Day Rate" <% if(cinfo.getRate1() > 0) {%>value="<%=cinfo.getRate1()%>"<% } %>/>
                                                                            </td>
                                                                            <td>
                                                                                <a href="javascript:;" class="blue_btn" onclick="javascript: updateRate('<%=positionId%>', '<%=cc%>');" id="submitdiv">Update</a>
                                                                            </td>
                                                                            <td>&nbsp;</td>
                                                                            <td>&nbsp;</td>
                                                                            <td>&nbsp;</td>
                                                                            <td>&nbsp;</td>
                                                                            <td>&nbsp;</td>
                                                                            <td>&nbsp;</td>
                                                                        </tr>
<%
                                                            for(int j = 0; j < list_size; j++)
                                                            {
                                                                CrewdayrateInfo cwinfo = (CrewdayrateInfo) list.get(j);
                                                                if(cwinfo != null)
                                                                {
%>
                                                                    <tr>
                                                                        <td>&nbsp;</td>
                                                                        <input type="hidden" name="type" value="<%=cwinfo.getType()%>"/>  
                                                                        <td><%=cwinfo.getName() != null ? cwinfo.getName() : ""%></td>  
                                                                        <td>&nbsp;</td>
                                                                        <td><%=cwinfo.getRate1()%></td>
                                                                        <td><%=cwinfo.getRate2()%></td>
                                                                        <td><%=cwinfo.getRate3()%></td>
                                                                        <td><%=cwinfo.getFromDate() != null ? cwinfo.getFromDate() : ""%></td>
                                                                        <td><%=cwinfo.getToDate() != null ? cwinfo.getToDate() : ""%></td>
                                                                        <td class="text-right">
                                                                            <a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#client_position" onclick="javascript: getHistory('<%=cwinfo.getCandidateId() %>', '<%=positionId%>');" ><img src="../assets/images/reload-time-blue.png" style="width:24px;"/> </a>
                                                                        </td>
                                                                        <td class="crew_add_btn text-right">
                                                                            <a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#client_position2" onclick="javascript: editModal('<%=cwinfo.getCandidateId() %>', '<%=positionId%>');" ><img src="../assets/images/add_list.png"/> </a>
                                                                        </td>
                                                                    </tr>
<%
                                                                }
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
                                                </div>                                        
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12">                                           
                                                    <a href="javascript: goback();" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
                        <!-- End Page-content -->
                    </div>
                    <!-- end main content-->
                </div>
                <!-- END layout-wrapper -->
            </div>
            <%@include file="../footer.jsp" %>
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
                                    <h3>Day Rates Configured</h3>
                                    <p>These day rates have been assigned to Client Asset</p>
                                    <a href="javascript: goback();" class="msg_button" style="text-decoration: underline;">Crew Day Rate</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="client_position" class="modal fade client_position appr_history" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div><br>
                            <h2>&nbsp;Crew Rate History</h2>
                        </div>                
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12" id="rateHistory">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="client_position2" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body" id="edit_div">

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
            <script src="../assets/js/bootstrap-select.min.js"></script>
            <script src="../assets/js/bootstrap-datepicker.min.js"></script>
            <script src="/jxp/assets/js/sweetalert2.min.js"></script>
            <%if(thankyou.equals("yes")){%>
            <script type="text/javascript">
                                                                            $(window).on('load', function () {
                                                                                $('#thank_you_modal').modal('show');
                                                                            });
            </script>
            <%}%>    
            <%if(!message.equals("")){%>
            <script type="text/javascript">
                $(window).on('load', function () {
                    Swal.fire("date already exists.");
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

            <script type="text/javascript">
                jQuery(document).ready(function () {
                    $(".kt-selectpicker").selectpicker();
                    $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "bottom"
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
