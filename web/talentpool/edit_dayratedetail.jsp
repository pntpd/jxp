<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 2, submtp = 4, ctp = 28;
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
            String message = "", clsmessage = "red_font";
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }        
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";
            
            ArrayList list = new ArrayList();
            if(request.getSession().getAttribute("MODULEPER_LIST") != null)
                list = (ArrayList)request.getSession().getAttribute("MODULEPER_LIST");

            TalentpoolInfo dinfo = null;
            int clientAssetId = 0;
            if (session.getAttribute("CANDIDATE_DETAIL") != null) {
                dinfo = (TalentpoolInfo) session.getAttribute("CANDIDATE_DETAIL");
                clientAssetId = dinfo.getToAssetId();
            }

            TalentpoolInfo pinfo = null;
            int pid1 = 0, pid2 = 0;
            String pname1 = "", pname2 = "";
            if (session.getAttribute("PINFO") != null) 
            {
                pinfo = (TalentpoolInfo) session.getAttribute("PINFO");
                if(pinfo != null)
                {
                    pid1 = pinfo.getPositionId();
                    pid2 = pinfo.getPositionId2();
                    pname1 = pinfo.getPosition() != null ? pinfo.getPosition(): "";
                    pname2 = pinfo.getPosition2() != null ? pinfo.getPosition2(): "";
                }
            }
            TalentpoolInfo pinfo2 = null;
            int positionId=0;
            if (session.getAttribute("PID_INFO") != null) 
            {
                pinfo2 = (TalentpoolInfo) session.getAttribute("PID_INFO");
                if(pinfo2 != null)
                {
                    positionId = pinfo2.getPositionId2();
                }
            }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= talentpool.getMainPath("title") != null ? talentpool.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/talentpool.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="doSaveDayratedetail"/>
        <html:hidden property="dayrateId"/>
        <input type="hidden" name="clientAssetId" value="<%=clientAssetId%>"/>
        <div id="layout-wrapper">
            <%@include file ="../header.jsp"%>
            <%@include file ="../sidemenu.jsp"%>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content tab_panel">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start">
                                <a href="javascript:goback();" class="back_arrow">
                                    <img  src="../assets/images/back-arrow.png"/> 
                                    <%@include file ="../talentpool_title.jsp"%>
                                </a>
                            </div>
                        </div>
                        <div class="col-md-12 col-xl-12 tab_head_area">
                            <%@include file ="../talentpooltab.jsp"%>
                        </div>
                    </div>
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background">
                                    <div class="row d-none1">
                                        <div class="col-lg-12">
                                            <% if(!message.equals("")) {%><div class="sbold <%=clsmessage%>">
                                                <%=message%>
                                            </div><% } %>
                                            <div class="main-heading m_30">
                                                <div class="add-btn">
                                                    <h4>DAY RATE</h4>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Current Position<span class="required">*</span></label>
                                                <select name="positionId" Id="positiondiv" class="form-select" onchange="javascript: ;">
                                                        <option value="-1">Select Position</option>
                                                        <option value="<%=pid1%>"><%=pname1%></option>
                                                        <%if(pid2> 0){%>
                                                        <option value="<%=pid2%>"><%=pname2%></option>
                                                        <%}%>
                                                </select>                                                
                                            </div>                                                
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">From Date<span class="required">*</span></label>
                                                <div class="input-daterange input-group">
                                                    <html:text property="fromDate" styleId="fromDate" styleClass="form-control add-style wesl_dt date-add" />
                                                    <script type="text/javascript">
                                                        document.getElementById("fromDate").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                    </script>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">To Date<span class="required"></span></label>
                                                <div class="input-daterange input-group">
                                                    <html:text property="toDate" styleId="toDate" styleClass="form-control add-style wesl_dt2 date-add" />
                                                    <script type="text/javascript">
                                                        document.getElementById("toDate").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                    </script>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Day Rate<span class="required">*</span></label>
                                                <div class="row">
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                        <html:text property="rate1" styleId="rate1" styleClass="form-control text-right" maxlength="10" onkeypress="return allowPositiveNumber(event);"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("rate1").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Overtime</label>
                                                <div class="row">
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                        <html:text property="rate2" styleId="rate2" styleClass="form-control text-right" maxlength="10" onkeypress="return allowPositiveNumber(event);"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("rate2").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Allowance</label>
                                                <div class="row">
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                        <html:text property="rate3" styleId="rate3" styleClass="form-control text-right" maxlength="10" onkeypress="return allowPositiveNumber(event);"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("rate3").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                        <a href="javascript: submitDayrateForm();" class="save_btn"><img src="../assets/images/save.png"/> Save</a>
                                        <a href="javascript: openTab('28');" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>	
        <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
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
        <script type="text/javascript">
            jQuery(document).ready(function () {
                $(".kt-selectpicker").selectpicker();
                $(".wesl_dt2").datepicker({
                    todayHighlight: !0,
                    format: "dd-M-yyyy",
                    autoclose: "true",
                    orientation: "bottom"
                });
            });
        </script>        
        <script type="text/javascript">
            function setval()
            {
                if ((document.forms[0].rate1.value) <= 0)
                    document.forms[0].rate1.value = "";
                if ((document.forms[0].rate2.value) <= 0)
                    document.forms[0].rate2.value = "";
                if ((document.forms[0].rate3.value) <= 0)
                    document.forms[0].rate3.value = "";
            }
            
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
            addLoadEvent(setval());
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