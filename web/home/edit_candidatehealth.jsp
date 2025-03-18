<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.candidate.CandidateInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 2, submtp = 7, ctp = 3;
            if (session.getAttribute("HOME_EMAILID") == null)
            {
    %>
    <jsp:forward page="/homeindex1.jsp"/>
    <%
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
    %>
    <head>
        <meta charset="utf-8">
        <title><%= candidate.getMainPath("title") != null ? candidate.getMainPath("title") : "" %></title>
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
        <link href="https://fonts.googleapis.com/css2?family=Rubik:wght@300;400;500;600&display=swap" rel="stylesheet">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/home.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed registration_page">
    <html:form action="/home/HomeAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="candidateId"/>
        <html:hidden property="doSave"/>
        <html:hidden property="doModify"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="doView"/>
        <html:hidden property="doViewBanklist"/>
        <html:hidden property="doViewlangdetail"/>
        <html:hidden property="doViewvaccinationlist"/>
        <html:hidden property="doViewgovdocumentlist"/>
        <html:hidden property="doViewhitchlist"/>
        <html:hidden property="doViewtrainingcertlist"/>
        <html:hidden property="doVieweducationlist"/>
        <html:hidden property="doViewexperiencelist"/>
        <html:hidden property="doViewhealthdetail"/>
        <html:hidden property="doSavehealthdetail"/>
        <html:hidden property="doaddhealthdetail"/>
        <html:hidden property="candidatehealthId"/>
        <div id="layout-wrapper">
            <%@include file ="../homeheader.jsp"%>
            <div class="main-content">
                <div class="page-content tab_panel">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12 tab_head_area">
                            <%@include file ="../homecandidatetab.jsp"%>
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
                                            <div class="main-heading m_30 mt_30">
                                                <div class="add-btn">
                                                    <h4>HEALTH DETAILS</h4>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Seaman Specific Medical Fitness<span class="required" >*</span></label>
                                                <html:select styleClass="form-select" property="ssmf" onchange="javascript: getSpanId();">
                                                    <html:option value="">- Select -</html:option>
                                                    <html:option value="Yes">Yes</html:option>
                                                    <html:option value="No">No</html:option>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-8 col-md-8 col-sm-8 col-4 form_group">
                                                <label class="form_label">OGUK Medical FTW<span class="required" id="spanId1"> *</span></label>
                                                <html:text property="ogukmedicalftw" styleId="ogukmedicalftw" styleClass="form-control" maxlength="100"/>
                                                <script type="text/javascript">
                                                    document.getElementById("ogukmedicalftw").setAttribute('placeholder', '');
                                                </script>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">OGUK Expiry<span class="required" id="spanId2"> *</span></label>
                                                <div class="input-daterange input-group">
                                                    <html:text property="ogukexp" styleId="ogukexp" styleClass="form-control add-style wesl_dt date-add" />
                                                    <script type="text/javascript">
                                                        document.getElementById("ogukexp").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                    </script>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Medical Fitness Certificate<span class="required" id="spanId3"> *</span></label>
                                                <html:select styleClass="form-select" property="medifitcert">
                                                    <html:option value="">- Select -</html:option>
                                                    <html:option value="Yes">Yes</html:option>
                                                    <html:option value="No">No</html:option>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Medical Fitness Certificate Expiry<span class="required" id="spanId4"> *</span></label>
                                                <div class="input-daterange input-group">
                                                    <html:text property="medifitcertexp" styleId="medifitcertexp" styleClass="form-control add-style wesl_dt date-add" />
                                                    <script type="text/javascript">
                                                        document.getElementById("medifitcertexp").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                    </script>
                                                </div>
                                            </div>

                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Blood Group</label>
                                                <html:select styleClass="form-select" property="bloodgroup">
                                                    <html:option value="">- Select -</html:option>
                                                    <html:option value="O-">O-</html:option>
                                                    <html:option value="O+">O+</html:option>
                                                    <html:option value="A-">A-</html:option>
                                                    <html:option value="A+">A+</html:option>
                                                    <html:option value="B-">B-</html:option>
                                                    <html:option value="B+">B+</html:option>
                                                    <html:option value="AB-">AB-</html:option>
                                                    <html:option value="AB+">AB+</html:option>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Blood Pressure</label>
                                                <html:select property="bloodpressureid" styleClass="form-select">
                                                    <html:optionsCollection filter="false" property="bloodpressures" label="ddlLabel" value="ddlValue">
                                                    </html:optionsCollection>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Hypertension</label>
                                                <html:select styleClass="form-select" property="hypertension">
                                                    <html:option value="">- Select -</html:option>
                                                    <html:option value="Yes">Yes</html:option>
                                                    <html:option value="No">No</html:option>
                                                </html:select>
                                            </div>
                                        </div>
                                        <div class="row">	
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Diabetes</label>
                                                <html:select styleClass="form-select" property="diabetes">
                                                    <html:option value="">- Select -</html:option>
                                                    <html:option value="Yes">Yes</html:option>
                                                    <html:option value="No">No</html:option>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Smoking</label>
                                                <html:select styleClass="form-select" property="smoking">
                                                    <html:option value="">- Select -</html:option>
                                                    <html:option value="Yes">Yes</html:option>
                                                    <html:option value="No">No</html:option>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Covid-19 2 Doses<span class="required">*</span></label>
                                                <html:select styleClass="form-select" property="cov192doses">
                                                    <html:option value="">- Select -</html:option>
                                                    <html:option value="Yes">Yes</html:option>
                                                    <html:option value="No">No</html:option>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Height (in cm)</label>
                                                <html:text property="height" styleId="height" styleClass="form-control" maxlength="8" onkeypress="return allowPositiveNumber(event);" onfocus="if (this.value == '0.0') {
                                                            this.value = '';
                                                        }" onblur="if (this.value == '') {
                                                                    this.value = '0.0';
                                                                }"/>
                                                <script type="text/javascript">
                                                    document.getElementById("height").setAttribute('placeholder', '');
                                                </script>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Weight (in Kg)</label>
                                                <html:text property="weight" styleId="weight" styleClass="form-control" maxlength="8" onkeypress="return allowPositiveNumber(event);" onfocus="if (this.value == '0.0') {
                                                            this.value = '';
                                                        }" onblur="if (this.value == '') {
                                                                    this.value = '0.0';
                                                                }"/>
                                                <script type="text/javascript">
                                                    document.getElementById("weight").setAttribute('placeholder', '');
                                                </script>
                                            </div>
                                            <div class="col-lg-3 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Attach File (5MB) (.pdf/.jpeg/.png)</label>
                                                <html:file property="healthfile" styleId="upload1" onchange="javascript: setClass('1');"/>
                                                <a href="javascript:;" id="upload_link_1" class="attache_btn uploaded_img1"><i class="fas fa-paperclip"></i> Attach</a>
                                            </div>
                                        </div>
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                            <a href="javascript:submithealthform();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
                                            <a href="javascript: openTab('4');" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
        <script>
            $(function () {
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });
            });
        </script> 
        <script type="text/javascript">
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
            addLoadEvent(getSpanId());
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
