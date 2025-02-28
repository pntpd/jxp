<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.assessment.AssessmentInfo" %>
<%@page import="com.web.jxp.assessmentparameter.AssessmentParameterInfo" %>

<%@page import="java.util.ArrayList" %>
<jsp:useBean id="assessment" class="com.web.jxp.assessment.Assessment" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 13;
            if (session.getAttribute("LOGININFO") == null)
            {
    %>
    <jsp:forward page="/index1.jsp"/>
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

             ArrayList aparameter_list = new ArrayList();
            if(request.getAttribute("APARAMETERLIST") != null)
                aparameter_list = (ArrayList) request.getAttribute("APARAMETERLIST");
            int aparameter_list_size = aparameter_list.size();
            String pids = "";
            if(request.getAttribute("PIDS") != null)
            {
                pids = (String) request.getAttribute("PIDS");
                request.removeAttribute("PIDS");
            }
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
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/assessment.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/assessment/AssessmentAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
            <html:hidden property="assessmentId"/>
            <html:hidden property="doSave"/>
            <html:hidden property="doCancel"/>
            <html:hidden property="search"/>
            <html:hidden property="assessmentparameterIndex"/>
            <html:hidden property="questiontypeIndex"/>
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file ="../header.jsp"%>
                <%@include file ="../sidemenu.jsp"%>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start">
                                    <a href="javascript:goback();" class="back_arrow">
                                        <img  src="../assets/images/back-arrow.png"/>
                                        Assessment
                                    </a>
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
                                                
                                            </div>
                                        </div>
                                        <div class="row d-none1">
                                            <div class="col-lg-12">
                                                <% if(!message.equals("")) {%><div class="sbold <%=clsmessage%>">
                                                    <%=message%>
                                                </div><% } %>
                                                <div class="main-heading">
                                                    <div class="add-btn">
                                                        <h4>&nbsp;</h4>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row m_30">
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Assessment Code</label>
                                                    <html:text property="assessmentcode" styleId="assessmentcode" styleClass="form-control" maxlength="100" readonly="true" />
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Assessment Name</label>
                                                    <html:text property="name" styleId="name" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("name").setAttribute('placeholder', '');
                                                    </script>
                                                </div>

                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Mode</label>
                                                    <html:select styleClass="form-select" property="mode">
                                                        <html:option value="">- Select -</html:option>
                                                        <html:option value="Online">Online</html:option>
                                                        <html:option value="Offline">Offline</html:option>
                                                    </html:select>
                                                </div>  
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
                                                            <label class="form_label para_title">Select Parameters</label>
                                                            <a href="javascript: addparameter();" class="add_btn para_btn" data-bs-toggle="modal" data-bs-target="#par_add_modal" class="attache_btn"><i class="mdi mdi-plus"></i></a>
                                                        </div>
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 select_parameter">
                                                            <ul id="parameterul">
<%
                                                        if(aparameter_list_size > 0)
                                                        {
                                                        AssessmentParameterInfo info;
                                                        for (int i = 0; i < aparameter_list_size; i++)
                                                        {
                                                            info = (AssessmentParameterInfo) aparameter_list.get(i);
                                                            if (info != null) 
                                                            {
%>
                                                                <li>
                                                                    <div class="form-check permission-check">
                                                                        <input class="form-check-input" type="checkbox" name="assparaameter"  id="assparaameter" onchange="javascript: setCheckedassessmentparameters();" value="<%=info.getDdlValue()%>" <%if(assessment.checkToStr(pids, ""+info.getDdlValue())) {%>checked<% } %>/>
                                                                        <span class="ml_10"><%= assessment.changeNum(info.getDdlValue(),2)%> | <%= info.getDdlLabel() != null ? info.getDdlLabel() : "" %></span>
                                                                    </div>
                                                                </li>
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
                                                            <label class="form_label para_title">Select Parameter Questions</label>
                                                            <a href="javascript:;" class="add_btn para_btn" data-bs-toggle="modal" data-bs-target="#par_que_modal"><i class="mdi mdi-plus"></i></a>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-12 col-12 form_group">
                                                            <html:select property="checkedassessmentparameterId" styleId="checkedassessmentparameterId" styleClass="form-select" onchange="javascript: setCheckedquestionparameters('-1');">
                                                                <html:optionsCollection filter="false" property="checkedassessmentparameters" label="ddlLabel" value="ddlValue">
                                                                </html:optionsCollection>
                                                            </html:select>
                                                        </div>
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                            <div class="table-responsive mb-0">
                                                                <table class="table table-striped">
                                                                    <tbody id="checkedassessmentquestions" >

                                                                    </tbody>
                                                                </table>
                                                            </div>		
                                                        </div>


                                                    </div>
                                                </div>
                                                
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group" id="submitdiv">
                                                    <a href="javascript:submitForm();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
                                                    <a href="javascript:goback();" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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

                <div id="par_add_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <h2>ADD PARAMETER</h2>
                                        <div class="row">
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                                <label class="form_label">Name</label>
                                                <input class="form-control" name ="name_modal" placeholder="" maxlength ="100"/>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                                <label class="form_label">Description</label>
                                                <textarea class="form-control" rows="6" name ="description_modal" placeholder="" maxlength ="1000" /></textarea>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center"><a href="javascript: addparameter();" class="save_page"><img src="../assets/images/save.png"> Save</a></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="par_que_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <h2>ADD QUESTIONS</h2>
                                        <div class="row">
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                                <label class="form_label">Question Text</label>
                                                <input class="form-control" name ="question_modal" placeholder="" maxlength ="200">
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                                <label class="form_label">Answer Type</label>
                                                <html:select property="assessmentanswerId" styleId="assessmentanswerId" styleClass="form-select">
                                                    <html:optionsCollection filter="false" property="assessmentanswers" label="ddlLabel" value="ddlValue">
                                                    </html:optionsCollection>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                                <label class="form_label">Parameter</label>
                                                <html:select property="checkedassessmentparameterId_model" styleId="checkedassessmentparameterId_model" styleClass="form-select">
                                                    <html:optionsCollection filter="false" property="checkedassessmentparameters" label="ddlLabel" value="ddlValue">
                                                    </html:optionsCollection>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                                <label class="form_label">Attach External Link</label>
                                                <input class="form-control" name ="externallink_modal" placeholder="" maxlength ="200">
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center"><a href="javascript: addquestions();" class="save_page"><img src="../assets/images/save.png"> Save</a></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <div id="view_par_que_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <h2>PARAMETER QUESTION</h2>
                                        <div class="row"  id = "questviewmodal">
                                            
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
