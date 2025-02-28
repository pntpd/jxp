<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.assessment.AssessmentInfo"%>
<%@page import="com.web.jxp.assessmentparameter.AssessmentParameterInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="assessment" class="com.web.jxp.assessment.Assessment" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 13;
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
            AssessmentInfo info = null;
            if(request.getAttribute("ASSESSMENT_DETAIL") != null)
                info = (AssessmentInfo)request.getAttribute("ASSESSMENT_DETAIL");
             int assessmentId = 0;
             if(info != null){
                    assessmentId = info.getAssessmentId();
                    }
            Collection plist = null;
            if(request.getAttribute("APLIST") != null)
                    plist = (Collection)request.getAttribute("APLIST");
            ArrayList list = new ArrayList();
            if(plist != null)
                list = new ArrayList<>(plist);
            int list_size = list.size();
    %>  
    <head>
        <meta charset="utf-8">
        <title><%= assessment.getMainPath("title") != null ? assessment.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/assessment.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/assessment/AssessmentAction.do" onsubmit="return false;" styleClass="form-horizontal">
            <html:hidden property="doCancel"/>
            <html:hidden property="search"/>
            <html:hidden property="doModify"/>
            <html:hidden property="assessmentId"/>
            <html:hidden property="assessmentparameterIndex"/>
            <html:hidden property="questiontypeIndex"/>
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file="../header.jsp" %>
                <%@include file="../sidemenu.jsp" %>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/>  Assessment</a></div>
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
                        </div>
                        <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background">
                                    <div class="row m_30">
                                        <div class="main-heading">
                                            <div class="add-btn">
                                                <h4>ASSESSMENT DETAILS</h4>
                                            </div>
                                            
                                            <div class="edit_btn pull_right float-end">
                                                <% if (editper.equals("Y")) {%><a href="javascript: modifyFormview('<%=assessmentId%>')"><img src="../assets/images/edit.png"></a><% } %>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row m_30">
<%
                                        if(info != null)
                                        {
%>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Assessment Code</label>
                                            <span class="form-control"><%= (info.getAssessmentId())%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Assessment Name</label>
                                            <span class="form-control"><%= (info.getName() != null && !info.getName().equals("")) ? info.getName() : "&nbsp;" %></span>
                                        </div>

                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Mode</label>
                                            <span class="form-control"><%= (info.getMode()!= null && !info.getMode().equals("")) ? info.getMode() : "&nbsp;" %></span>
                                        </div>
<%
                                            }
%>
                                    </div>	
                                    <div class="row m_30">
                                        <div class="main-heading">
                                            <div class="add-btn">
                                                <h4>PARAMETERS</h4>
                                            </div>
                                        </div>
                                    </div>	
                                    <div class="row">	
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4">
                                            <div class="row">
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label para_title">Selected Parameters</label>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 select_parameter">
                                                    <ul>
<%
                                                if(list_size > 0)
                                                {
                                                AssessmentParameterInfo apinfo;
                                                for (int i = 0; i < list_size; i++)
                                                {
                                                    apinfo = (AssessmentParameterInfo) list.get(i);
                                                    if (apinfo != null) 
                                                    {
%>
                                                        <li><span class="ml_10"> <%= assessment.changeNum(apinfo.getDdlValue(),2)%> | <%= apinfo.getDdlLabel() != null ? apinfo.getDdlLabel() : "" %></span></li>
<%
                                                   }                                                                   
                                                   }
                                                           }

%>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-8 col-md-8 col-sm-8 col-4">
                                            <div class="row">
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label para_title">Selected Questions</label>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-12 col-12 form_group">
                                                    <html:select property="checkedassessmentparameterId" styleId="checkedassessmentparameterId" styleClass="form-select" onchange="javascript: setcheckedquestionparametersView();">
                                                        <html:optionsCollection filter="false" property="checkedassessmentparameters" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <div class="table-responsive mb-0">
                                                        <table class="table table-striped">
                                                            <tbody id="checkedassessmentview" >


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
                    </div
                    <!-- end main content-->
                </div>
            </div>
            <!-- END layout-wrapper -->
            <%@include file="../footer.jsp" %>

            <div id="par_que_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body" >
                            <div class="row">
                                <div class="col-lg-12">
                                    <h2>PARAMETER QUESTION</h2>
                                    <div class="row" id = "questviewmodal">
                                    </div>
                                </div>
                            </div>
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
            <script>
                                                                            // toggle class show hide text section
                                                                            $(document).on('click', '.toggle-title', function () {
                                                                                $(this).parent()
                                                                                        .toggleClass('toggled-on')
                                                                                        .toggleClass('toggled-off');
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
