
<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.client.ClientInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="client" class="com.web.jxp.client.Client" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 2,ctp = 1;
            if (session.getAttribute("LOGININFO") == null)
            {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
            }

            String message = "", clsmessage = "red_font" , ocsuserIds = "", mids = "", rids = "";
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }        
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";

            if (session.getAttribute("OCSUSERS_IDs") != null) {
                ocsuserIds = (String) session.getAttribute("OCSUSERS_IDs");
            }
            ArrayList ocsuserlist = client.getUsers();

            if (session.getAttribute("M_IDs") != null) {
                mids = (String) session.getAttribute("M_IDs");
            }
            ArrayList mlist = client.getManagerIds();

            if (session.getAttribute("R_IDs") != null) {
                rids = (String) session.getAttribute("R_IDs");
            }
            ArrayList rlist = client.getRecruiterIds();
    %>
    <head>
        <meta charset="utf-8">
        <title><%= client.getMainPath("title") != null ? client.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/client.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/client/ClientAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
            <html:hidden property="clientId"/>
            <html:hidden property="doSave"/>
            <html:hidden property="doCancel"/>
            <html:hidden property="doView"/>
            <html:hidden property="doAdd"/>
            <html:hidden property="doModify"/>
            <html:hidden property="search"/>
            <html:hidden property="countryIndexId"/>
            <html:hidden property="assettypeIndexId"/>
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file ="../header.jsp"%>
                <%@include file ="../sidemenu.jsp"%>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content tab_panel">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start">
                                    <span  class="back_arrow">
                                        <a href="javascript:goback();"><img  src="../assets/images/back-arrow.png"/> </a>
                                        Client Master
                                    </span>
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
                                            <%@include file ="../shortcutmenu_edit.jsp"%>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="col-md-12 col-xl-12 tab_head_area">

                                <c:choose>
                                    <c:when test="${clientForm.clientId <= 0}">
                                        <ul class='nav nav-tabs nav-tabs-custom disable_menu' id='tab_menu'>

                                            <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 1) ? " active" : ""%>'>
                                                <a class='nav-link' href="javascript: ;">
                                                    <span class='d-none d-md-block'>Client</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                                </a>
                                            </li>

                                            <li id='list_menu2' class='list_menu2 nav-item <%=(ctp == 2) ? " active" : ""%>'>
                                                <a class='nav-link' href="javascript: ;">
                                                    <span class='d-none d-md-block'>Asset</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
                                                </a>
                                            </li>

                                        </ul> 
                                    </c:when>
                                    <c:otherwise>
                                        <ul class='nav nav-tabs nav-tabs-custom' id='tab_menu'>

                                            <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 1) ? " active" : ""%>'>
                                                <a class='nav-link' href="javascript: showDetail('-1');">
                                                    <span class='d-none d-md-block'>Client</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                                </a>
                                            </li>

                                            <li id='list_menu2' class='list_menu2 nav-item <%=(ctp == 2) ? " active" : ""%>'>
                                                <a class='nav-link' href="javascript: viewasset('-1');">
                                                    <span class='d-none d-md-block'>Asset</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
                                                </a>
                                            </li>

                                        </ul> 
                                    </c:otherwise>
                                </c:choose>
                            </div>



                        </div>
                        <div class="container-fluid" id="printArea">
                            <div class="row">
                                <div class="col-md-12 col-xl-12">
                                    <div class="body-background">
                                        <div class="row d-none1">
                                            <div class="main-heading m_30">
                                                <div class="add-btn">
                                                    <h4>ADD / EDIT CLIENT</h4>
                                                </div>
                                            </div>
                                            <div class="col-lg-12">
                                                <% if(!message.equals("")) {%><div class="sbold <%=clsmessage%>">
                                                    <%=message%>
                                                </div><% } %>
                                                
                                            </div>
                                            <div class="row col-lg-12">
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">ID</label>
                                                    <html:text property="clientcode" styleId="clientcode" styleClass="form-control" maxlength="100" readonly="true" />
                                                </div>
                                                <div class="col-lg-8 col-md-8 col-sm-8 col-12 form_group">
                                                    <label class="form_label">Name<span class="required">*</span></label>
                                                    <html:text property="name" styleId="name" styleClass="form-control" maxlength="200"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("name").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-8 col-md-8 col-sm-8 col-12 form_group">
                                                    <label class="form_label">Head Office Address<span class="required">*</span></label>
                                                    <html:text property="headofficeaddress" styleId="headofficeaddress" styleClass="form-control" maxlength="500"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("headofficeaddress").setAttribute('placeholder', '');
                                                    </script>
                                                </div>

                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Country<span class="required">*</span></label>
                                                    <html:select property="countryId" styleClass="form-select">
                                                        <html:option value="-1">- Select -</html:option>
                                                        <html:optionsCollection filter="false" property="countries" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Incharge Name (point of contact)<span class="required">*</span></label>
                                                    <html:text property="inchargename" styleId="inchargename" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("inchargename").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Position of Incharge (point of contact)</label>
                                                    <html:text property="position" styleId="position" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("position").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Incharge Mail<span class="required">*</span></label>
                                                    <html:text property="email" styleId="email" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("email").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Contact Number<span class="required">*</span></label>
                                                    <div class="row">
                                                        <div class="col-lg-4">
                                                            <html:select property="ISDcode" styleClass="form-select">
                                                                <html:option value="-1">- Select -</html:option>
                                                                <html:optionsCollection filter="false" property="countries" label="ddlLabel3" value="ddlLabel2">
                                                                </html:optionsCollection>
                                                            </html:select>
                                                        </div>
                                                        <div class="col-lg-8">
                                                            <html:text property="contact" styleId="contact" styleClass="form-control" maxlength="20"/>
                                                            <script type="text/javascript">
                                                                document.getElementById("contact").setAttribute('placeholder', '');
                                                            </script>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Enrollment Date</label>
                                                    <div class="input-daterange input-group">
                                                        <html:text property="date" styleId="date" styleClass="form-control add-style wesl_dt date-add" maxlength="11"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("date").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                        </script>
                                                    </div>
                                                </div>

                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group media_links">
                                                    <label class="form_label">Media Links</label>
                                                    <div class="media_link">
                                                        <ul>
                                                            <li><a href="javascript:showlink('1');"><i class="ion ion-ios-globe"></i></a></li>
                                                            <li><a href="javascript:showlink('2');"><i class="ion ion-md-locate"></i></a></li>
                                                            <li><a href="javascript:showlink('3');;"><i class="ion ion-logo-instagram"></i></a></li>
                                                            <li><a href="javascript:showlink('4');"><i class="ion ion-logo-linkedin"></i></a></li>
                                                            <li><a href="javascript:showlink('5');"><i class="ion ion-md-link"></i></a></li>
                                                            <li class="pull_right plus_icon text_right"><a href="javascript:showlink('6');"><i class="ion ion-md-add"></i></a></li>
                                                        </ul>
                                                    </div>
                                                    <div id='linkid1' class="media_view_2" style="display:none;">
                                                        <html:text property="link1" styleId="link1" styleClass="form-control" maxlength="200"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("link1").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div id='linkid2' class="media_view_2" style="display:none;">
                                                        <html:text property="link2" styleId="link2" styleClass="form-control" maxlength="200"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("link2").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div id='linkid3' class="media_view_2" style="display:none;">   
                                                        <html:text property="link3" styleId="link3" styleClass="form-control" maxlength="200"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("link3").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div id='linkid4' class="media_view_2" style="display:none;">
                                                        <html:text property="link4" styleId="link4" styleClass="form-control" maxlength="200"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("link4").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div id='linkid5' class="media_view_2" style="display:none;">
                                                        <html:text property="link5" styleId="link5" styleClass="form-control" maxlength="200"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("link5").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                        <div id='linkid6' class="media_view_2" style="display:none;">
                                                        <html:textarea property="link6" rows="3" styleId="link6" styleClass="form-control" />
                                                        <script type="text/javascript">
                                                            document.getElementById("link6").setAttribute('placeholder', '');
                                                            document.getElementById("link6").setAttribute('maxlength', '1000');
                                                        </script>
                                                    </div>
                                                </div>
                                                <%
                                                    int nsize = 0;
                                                    nsize = ocsuserlist.size();
                                                %>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                    <div class="row flex-end align-items-end">
                                                        <div class="col-lg-10 col-md-12 col-sm-12 col-12 form_group">
                                                            <label class="form_label">Co-ordinator from OCS<span class="required">*</span></label>
                                                            <div class="row">
                                                                <div class="col-lg-10 col-md-10 col-sm-10 col-10 noti_div">
                                                                    <div class="input-group flex_div">
                                                                        <a href="javascript: setUsers();" class="input-group-text refresh_btn"><i class="ion ion-md-refresh"></i></a>
                                                                        <select name="ocsuserIds" id="ocsusermultiselect_dd" multiple="multiple" class="form-select">
                                                                            <%
                                                                                if (nsize > 0) {
                                                                                    for (int i = 0; i < nsize; i++) {
                                                                                        ClientInfo info = (ClientInfo) ocsuserlist.get(i);
                                                                            %>
                                                                            <option value="<%=info.getDdlValue()%>" <% if (client.checkToStr(ocsuserIds, info.getDdlValue() + "")) {%>selected<%}%>><%= (info.getDdlLabel())%> </option>
                                                                            <%
                                                                                    }
                                                                                }
                                                                            %>
                                                                        </Select>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-2 col-md-12 col-sm-12 col-12 form_group">
                                                                    <a href="../user/UserAction.do?doAdd=yes" target="_blank" class="add_btn"><i class="mdi mdi-plus"></i></a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <%
                                                                                            int msize = 0;
                                                                                            msize = mlist.size();
                                                %>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                    <div class="row flex-end align-items-end">
                                                        <div class="col-lg-10 col-md-12 col-sm-12 col-12 form_group">
                                                            <label class="form_label">Client Manager<span class="required">*</span></label>
                                                            <div class="row">
                                                                <div class="col-lg-10 col-md-10 col-sm-10 col-10 noti_div">
                                                                    <div class="input-group flex_div">
                                                                        <select name="mids" id="managermultiselect_dd" multiple="multiple" class="form-select">
                                                                            <%
                                                                                if (msize > 0) {
                                                                                    for (int i = 0; i < msize; i++) {
                                                                                        ClientInfo info = (ClientInfo) mlist.get(i);
                                                                            %>
                                                                            <option value="<%=info.getDdlValue()%>" <% if (client.checkToStr(mids, info.getDdlValue() + "")) {%>selected<%}%>><%= (info.getDdlLabel())%> </option>
                                                                            <%
                                                                                    }
                                                                                }
                                                                            %>
                                                                        </Select>


                                                                    </div>
                                                                </div>

                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                                        
<%
                                                int rsize = 0;
                                                rsize = rlist.size();
%>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                    <div class="row flex-end align-items-end">
                                                        <div class="col-lg-10 col-md-12 col-sm-12 col-12 form_group">
                                                            <label class="form_label">Recruiters from OCS<span class="required">*</span></label>
                                                            <div class="row">
                                                                <div class="col-lg-10 col-md-10 col-sm-10 col-10 noti_div">
                                                                    <div class="input-group flex_div">
                                                                        <select name="rids" id="recruitermultiselect_dd" multiple="multiple" class="form-select">
                                                                            <%
                                                                                if (rsize > 0) 
                                                                                {
                                                                                    for (int i = 0; i < rsize; i++) 
                                                                                    {
                                                                                        ClientInfo info = (ClientInfo) rlist.get(i);
                                                                            %>
                                                                                <option value="<%=info.getDdlValue()%>" <% if (client.checkToStr(rids, info.getDdlValue() + "")) {%>selected<%}%>><%= (info.getDdlLabel())%> </option>
                                                                            <%
                                                                                    }
                                                                                }
                                                                            %>
                                                                        </Select>


                                                                    </div>
                                                                </div>

                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                        
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Short Name<span class="required">*</span></label>
                                                    <div class="input-daterange input-group">
                                                        <html:text property="shortName" styleId="shortName" styleClass="form-control" maxlength="20"/>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Color Code<span class="required">*</span></label>
                                                    <div class="input-daterange input-group">
                                                        <html:text property="colorCode" styleId="colorCode" styleClass="form-control" maxlength="20"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                                    <a href="javascript:submitForm()" class="save_btn"><img src="../assets/images/save.png"> Save</a>
                                                    <c:choose>
                                                        <c:when test="${clientForm.clientId <= 0}">
                                                            <a href="javascript: goback();" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="javascript: showDetail('-1');" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
                                                        </c:otherwise>
                                                    </c:choose>
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
            <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
            <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
            <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
            <script src="../assets/js/app.js"></script>	
            <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>
            <script src="../assets/js/bootstrap-select.min.js"></script>
            <script src="../assets/js/bootstrap-datepicker.min.js"></script>
            <script src="/jxp/assets/js/sweetalert2.min.js"></script>
            <script>
                    // toggle class show hide text section
                    $(document).on('click', '.toggle-title', function () {
                        $(this).parent()
                                .toggleClass('toggled-on')
                                .toggleClass('toggled-off');
                    });
            </script>
            <script type="text/javascript">
                    $(document).ready(function () {
                        $('#ocsusermultiselect_dd').multiselect({
                            includeSelectAllOption: true,
                            //maxHeight: 400,
                            dropUp: true,
                            nonSelectedText: '- Select -',
                            maxHeight: 200,
                            enableFiltering: false,
                            enableCaseInsensitiveFiltering: false,
                            buttonWidth: '100%'
                        });
                    });
                </script>
                <script type="text/javascript">
                        $(document).ready(function () {
                            $('#managermultiselect_dd').multiselect({
                                includeSelectAllOption: true,
                                //maxHeight: 400,
                                dropUp: true,
                                nonSelectedText: '- Select -',
                                maxHeight: 200,
                                enableFiltering: false,
                                enableCaseInsensitiveFiltering: false,
                                buttonWidth: '100%'
                            });
                        });
                </script>
                <script type="text/javascript">
                        $(document).ready(function () {
                            $('#recruitermultiselect_dd').multiselect({
                                includeSelectAllOption: true,
                                //maxHeight: 400,
                                dropUp: true,
                                nonSelectedText: '- Select -',
                                maxHeight: 200,
                                enableFiltering: false,
                                enableCaseInsensitiveFiltering: false,
                                buttonWidth: '100%'
                            });
                        });
                </script>
            <script type="text/javascript">
                jQuery(document).ready(function () {
                    $(".kt-selectpicker").selectpicker();
                    $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "top"
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
