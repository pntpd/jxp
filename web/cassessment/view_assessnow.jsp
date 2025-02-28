<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.cassessment.CassessmentInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="cassessment" class="com.web.jxp.cassessment.Cassessment" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 2, submtp = 14, coordinator = 0;
            String per = "N", addper = "N", editper = "N", deleteper = "N", approveper = "N" ;
            if (session.getAttribute("LOGININFO") == null) {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
        } else {
            UserInfo uinfo = (UserInfo) session.getAttribute("LOGININFO");
            if (uinfo != null) {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
                approveper = uinfo.getApproveper() != null ? uinfo.getApproveper() : "N";
                coordinator = uinfo.getCoordinator();

            }
        }

        CassessmentInfo info = null;
        if (session.getAttribute("CANDIDATEPOSITION_INFO") != null) {
            info = (CassessmentInfo) session.getAttribute("CANDIDATEPOSITION_INFO");
        }
        String pass_status = "";
        if (request.getAttribute("PASS_STATUS") != null) {
            pass_status = (String) request.getAttribute("PASS_STATUS");
        }

           ArrayList list = new ArrayList();
            if(session.getAttribute("ASSESSMENTASSESSOR_LIST") != null)
                list = (ArrayList)session.getAttribute("ASSESSMENTASSESSOR_LIST");
            int list_size = list.size();
    %>
    <head>
        <meta charset="utf-8">
        <title><%= cassessment.getMainPath("title") != null ? cassessment.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/time/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/cassessment.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/cassessment/CassessmentAction.do" onsubmit="return false;">
            <html:hidden property="doView"/>
            <html:hidden property="doSave"/>
            <html:hidden property="doCancel"/>
            <html:hidden property="doChange"/>
            <html:hidden property="doAssessorView"/>
            <html:hidden property="doAssessorSave"/>
            <html:hidden property="doCancelAssessor"/>
            <html:hidden property="cassessmentId"/>
            <html:hidden property="pAssessmentId"/>
            <html:hidden property="candidateId"/>
            <html:hidden property="assettypeIndex"/>
            <html:hidden property="doSaveAssessNow"/>
            <html:hidden property="radio18"/>
            <input type='hidden' name="positionId" value="<%=info.getPositionId()%>"/>
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file="../header.jsp" %>
                <%@include file="../sidemenu.jsp" %>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content tab_panel">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> Assess Now</a></div>
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
                                                <li><a href="javascript:;"><img src="../assets/images/open-in-new-tab.png"/> Open in New Tab</a></li>
                                                <li><a href="javascript:;"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                                <li><a href="javascript:;"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>	
                        </div>

                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-12 col-xl-12">
                                    <div class="body-background mt_15">
                                        <div class="row d-none1">
                                            <div class="row col-lg-12 all_client_sec" id="">
                                                <div class="">
                                                    <div class="main-heading">
                                                        <div class="add-btn">
                                                            <h4><%= info != null ? info.getName() : "" %> | <%= info != null ? info.getPosition() : "" %> | <%= info != null ? info.getRankName() : "" %> </h4>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 ass_now">
                                                    <div class="table-responsive table-detail">
                                                        <table class="table table-striped1 table-bordered1">
                                                            <thead>
                                                                <tr>
                                                                    <th width="69%" class="text-left head_bg" rowspan="4" colspan="2">Observations</th>
                                                                </tr>
                                                                <tr><th colspan="5" class="text-center">Rating</th></tr>
                                                                <tr>
                                                                    <th class="text-center">Low</th>
                                                                    <th colspan="3" class="text-center both_arrow"><img src="../assets/images/both_arrow.png"></th>
                                                                    <th class="text-center">High</th>
                                                                </tr>
                                                                <tr>
                                                                    <th width="6.20%" class="text-center">1</th>
                                                                    <th width="6.20%" class="text-center">2</th>
                                                                    <th width="6.20%" class="text-center">3</th>
                                                                    <th width="6.20%" class="text-center">4</th>
                                                                    <th width="6.20%" class="text-center">5</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <tr>
                                                                    <td colspan="7" class="pd_null">
                                                                        <div class="assess_now_scroll">
                                                                            <table width="100%" class="table table-striped">
                                                                                <tbody>
                                                                                    <tr class="title_head_bg">
                                                                                        <td colspan="7">Appearance:</td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center" width="20%">&nbsp;</th>
                                                                                        <td width="50%">Dress up / presentation</td>
                                                                                        <td width="6%" class="text-center"><label class="mt-radio light_radio"><html:radio property="radio1" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td width="6%" class="text-center"><label class="mt-radio light_radio"><html:radio property="radio1" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td width="6%" class="text-center"><label class="mt-radio light_radio"><html:radio property="radio1" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td width="6%" class="text-center"><label class="mt-radio light_radio"><html:radio property="radio1" value="4" />&nbsp;<span></span></label></td>
                                                                                        <td width="6%" class="text-center"><label class="mt-radio light_radio"><html:radio property="radio1" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center"">&nbsp;</th>
                                                                                        <td>Composure</td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio2" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio2" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio2" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio2" value="4" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio2" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>


                                                                                    <tr class="title_head_bg">
                                                                                        <td colspan="7">Observable Traits:</td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center"">&nbsp;</th>
                                                                                        <td>Attitude</td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio3" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio3" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio3" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio3" value="4" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio3" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center"">&nbsp;</th>
                                                                                        <td>Motivation</td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio4" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio4" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio4" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio4" value="4" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio4" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center"">&nbsp;</th>
                                                                                        <td>Communication</td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio5" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio5" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio5" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio5" value="4" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio5" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center"">&nbsp;</th>
                                                                                        <td>Assertiveness</td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio6" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio6" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio6" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio6" value="4" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio6" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center"">&nbsp;</th>
                                                                                        <td>Verbal / Persuasiveness</td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio7" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio7" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio7" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio7" value="4" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio7" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>

                                                                                    <tr class="title_head_bg">
                                                                                        <td colspan="7">Skills:</td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center"">&nbsp;</th>
                                                                                        <td>Education/Professional</td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio8" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio8" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio8" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio8" value="4" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio8" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center"">&nbsp;</th>
                                                                                        <td>Relevant Experience</td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio9" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio9" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio9" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio9" value="4" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio9" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center"">&nbsp;</th>
                                                                                        <td>Digital Proficiency and the ability to work with multiple technologies</td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio10" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio10" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio10" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio10" value="4" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio10" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center"">&nbsp;</th>
                                                                                        <td>Technical Skills (if required)</td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio11" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio11" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio11" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio11" value="4" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio11" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>

                                                                                    <tr class="title_head_bg">
                                                                                        <td colspan="7">Competencies:</td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center"">&nbsp;</th>
                                                                                        <td>Business Knowledge / knowledge of field</td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio12" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio12" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio12" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio12" value="4" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio12" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center"">&nbsp;</th>
                                                                                        <td>Job Knowledge</td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio13" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio13" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio13" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio13" value="4" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio13" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center"">&nbsp;</th>
                                                                                        <td>Clarity of thought</td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio14" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio14" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio14" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio14" value="4" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio14" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center"">&nbsp;</th>
                                                                                        <td>Understands questions and answers to the point</td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio15" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio15" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio15" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio15" value="4" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio15" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <th class="text-center"">&nbsp;</th>
                                                                                        <td>Logic</td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio16" value="1" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio16" value="2" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio16" value="3" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio16" value="4" /> &nbsp;<span></span></label></td>
                                                                                        <td class="text-center"><label class="mt-radio light_radio"><html:radio property="radio16" value="5" /> &nbsp;<span></span></label></td>
                                                                                    </tr>

                                                                                    <tr>
                                                                                        <td colspan="7" class="title_head_bg">Overall Rating:</td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td colspan="7" class="pd_0 over_final">
                                                                                            <table width="100%">
                                                                                                <tbody>
                                                                                                    <tr>
                                                                                                        <td width="20%"><label class="mt-radio light_radio"><html:radio property="radio17" value="1" /> Poor<span></span></label></td>
                                                                                                        <td>
                                                                                                            <label class="mt-radio light_radio"><html:radio property="radio17" value="2" /> Satisfactory<span></span></label>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <label class="mt-radio light_radio"><html:radio property="radio17" value="3" /> Good <span></span></label>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <label class="mt-radio light_radio"><html:radio property="radio17" value="4" /> Very Good<span></span></label>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <label class="mt-radio light_radio"><html:radio property="radio17" value="5" /> Excellent<span></span></label>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </tbody>
                                                                                            </table>
                                                                                        </td>
                                                                                    </tr>
                                                                                </tbody></table>

                                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                                                <label class="form_label">Additional Comments</label>

                                                                                <html:textarea property="cdescription" rows="5" styleId="cdescription" styleClass="form-control"></html:textarea>
                                                                                    <script type="text/javascript">
                                                                                        document.getElementById("cdescription").setAttribute('placeholder', '');
                                                                                        document.getElementById("cdescription").setAttribute('maxlength', '1000');
                                                                                    </script>
                                                                                </div>
                                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                                                <%if(!pass_status.equals("2") && (((addper.equals("Y") || editper.equals("Y")) && coordinator == 1 ) || per.equals("Y") || addper.equals("Y") || editper.equals("Y"))){%>
                                                                                <label class="form_label">Final Action</label>
                                                                                <div class="row">

                                                                                    <div class="col-lg-6 col-md-6"><a href="javascript: saveAssessNow('2');"  class="trans_btn">Approve</a></div>
                                                                                    <div class="col-lg-6 col-md-6"><a href="javascript: saveAssessNow('3');"  class="termi_btn">Reject</a></div>

                                                                                </div>
                                                                                <%}%>
                                                                            </div>


                                                                        </div>
                                                                    </td>
                                                                </tr>

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
                <!-- END layout-wrapper -->

                <%@include file ="../footer.jsp"%>

                <div id="client_position" class="modal fade client_position" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                            </div>
                            <div class="modal-body" id="camodel">
                            </div>
                        </div>
                    </div>
                </div>

                <!-- JAVASCRIPT -->
                <script src="../assets/libs/jquery/jquery.min.js"></script>		
                <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
                <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
                <script src="../assets/libs/metismenu/metisMenu.min.js"></script>

                <script src="../assets/js/sweetalert2.min.js"></script>
                <script src="../assets/js/app.min.js"></script>
                <script src="../assets/js/bootstrap-select.min.js"></script>

                <script>
                                                                                        // toggle class show hide text section
                                                                                        $(document).on('click', '.toggle-title', function () {
                                                                                            $(this).parent()
                                                                                                    .toggleClass('toggled-on')
                                                                                                    .toggleClass('toggled-off');
                                                                                        });
                </script>
                <script>
                    getAssessorScore();
                </script>

            </html:form>
    </body>
    <%
        } catch (Exception e) {
            e.printStackTrace();
        }
    %>
</html>
