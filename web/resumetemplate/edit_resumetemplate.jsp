<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.resumetemplate.ResumetemplateInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="resumetemplate" class="com.web.jxp.resumetemplate.Resumetemplate" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 50;
            if (session.getAttribute("LOGININFO") == null)
            {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
            }
            String message = "", clsmessage = "red_font", ids = "", eids = "", dids = "", tids = "";
           
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }        
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";
            if (session.getAttribute("WORKEXP_IDs") != null) 
            {
                ids = (String) session.getAttribute("WORKEXP_IDs");                
                request.getSession().removeAttribute("WORKEXP_IDs");
            }
            if (session.getAttribute("EDUDETAIL_IDs") != null) 
            {
                eids = (String) session.getAttribute("EDUDETAIL_IDs");                
                request.getSession().removeAttribute("EDUDETAIL_IDs");
            }
            if (session.getAttribute("DOCDETAIL_IDs") != null) 
            {
                dids = (String) session.getAttribute("DOCDETAIL_IDs");            
                request.getSession().removeAttribute("DOCDETAIL_IDs");
            } 
            if (session.getAttribute("TRAININGDETAIL_IDs") != null) 
            {
                tids = (String) session.getAttribute("TRAININGDETAIL_IDs");            
                request.getSession().removeAttribute("TRAININGDETAIL_IDs");
            }    
            String filename = "";
            if (session.getAttribute("FILENAME") != null)
            {
                filename = (String)session.getAttribute("FILENAME");
                if(filename != null && !filename.equals(""))
                    filename = resumetemplate.getMainPath("view_resumetemplate_pdf")+filename;
            }            
    %>
    <head>
        <meta charset="utf-8">
        <title><%= resumetemplate.getMainPath("title") != null ? resumetemplate.getMainPath("title") : "" %></title>
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
        <script type="text/javascript" src="../jsnew/resumetemplate.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/resumetemplate/ResumetemplateAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="resumetemplateId"/>
        <html:hidden property="doSave"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="search"/>  
        <html:hidden property="deschidden"/> 
        <html:hidden property="resumefilehidden"/> 
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
                                    <c:choose>
                                        <c:when test="${resumetemplateForm.resumetemplateId <= 0}">
                                            Add Resume Template
                                        </c:when>
                                        <c:otherwise>
                                            Edit Resume Template
                                        </c:otherwise>
                                    </c:choose>
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
                                        <div class="row col-lg-12">	
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                <label class="form_label">Client Name </label>
                                                <html:select styleClass="form-select" property="clientId">
                                                    <html:optionsCollection property="clients" value="ddlValue" label="ddlLabel"></html:optionsCollection>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                <label class="form_label">Template</label>
                                                <html:text property="name" styleId="name" styleClass="form-control" maxlength="100"/>
                                                <script type="text/javascript">
                                                    document.getElementById("name").setAttribute('placeholder', '');
                                                </script>
                                            </div> 
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Work Experience Column</label>
                                                <select name="expColumn" id="expColumnmultiselect_dd" multiple="multiple" class="form-select">
                                                    <option value="1" <% if (resumetemplate.checkToStr(ids, "1")) {%>selected<%}%>>Position</option>
                                                    <option value="2" <% if (resumetemplate.checkToStr(ids, "2")) {%>selected<%}%>>Department</option>
                                                    <option value="3" <% if (resumetemplate.checkToStr(ids, "3")) {%>selected<%}%>>Company Name</option>
                                                    <option value="4" <% if (resumetemplate.checkToStr(ids, "4")) {%>selected<%}%>>Asset Name</option>
                                                    <option value="5" <% if (resumetemplate.checkToStr(ids, "5")) {%>selected<%}%>>Asset Type</option>
                                                    <option value="6" <% if (resumetemplate.checkToStr(ids, "6")) {%>selected<%}%>>Start Date</option>
                                                    <option value="7" <% if (resumetemplate.checkToStr(ids, "7")) {%>selected<%}%>>End Date</option>
                                                </Select>
                                            </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Experience Type</label>
                                                        <html:select property="exptype" styleId="exptype" styleClass="form-select">
                                                            <html:option value="1">Table</html:option>
                                                            <html:option value="2">Vertical</html:option>
                                                        </html:select>
                                                    </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Education Column</label>
                                                <select name="eduColumn" id="eduColumnmultiselect_dd" multiple="multiple" class="form-select">
                                                    <option value="1" <% if (resumetemplate.checkToStr(eids, "1")) {%>selected<%}%>>Kind</option>
                                                    <option value="2" <% if (resumetemplate.checkToStr(eids, "2")) {%>selected<%}%>>Degree</option>
                                                    <option value="3" <% if (resumetemplate.checkToStr(eids, "3")) {%>selected<%}%>>Institution/Location</option>
                                                    <option value="4" <% if (resumetemplate.checkToStr(eids, "4")) {%>selected<%}%>>Field of Study</option>
                                                    <option value="5" <% if (resumetemplate.checkToStr(eids, "5")) {%>selected<%}%>>Start Date</option>
                                                    <option value="6" <% if (resumetemplate.checkToStr(eids, "6")) {%>selected<%}%>>End Date</option>
                                                </Select>
                                            </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Education Type</label>
                                                        <html:select property="edutype" styleId="edutype" styleClass="form-select">
                                                            <html:option value="1">Table</html:option>
                                                            <html:option value="2">Vertical</html:option>
                                                        </html:select>
                                                    </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Document Column</label>
                                                <select name="docColumn" id="docColumnmultiselect_dd" multiple="multiple" class="form-select">
                                                    <option value="1" <% if (resumetemplate.checkToStr(dids, "1")) {%>selected<%}%>>Document Name</option>
                                                    <option value="2" <% if (resumetemplate.checkToStr(dids, "2")) {%>selected<%}%>>Number</option>
                                                    <option value="3" <% if (resumetemplate.checkToStr(dids, "3")) {%>selected<%}%>>Place of Issue</option>
                                                    <option value="4" <% if (resumetemplate.checkToStr(dids, "4")) {%>selected<%}%>>Issued By</option>
                                                    <option value="5" <% if (resumetemplate.checkToStr(dids, "5")) {%>selected<%}%>>Issue Date</option>
                                                    <option value="6" <% if (resumetemplate.checkToStr(dids, "6")) {%>selected<%}%>>Expiry Date</option>
                                                </Select>
                                            </div>
<%--                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Document Type</label>
                                                        <html:select property="doctype" styleId="doctype" styleClass="form-select">
                                                            <html:option value="1">Table</html:option>
                                                            <html:option value="2">Vertical</html:option>
                                                        </html:select>
                                                    </div>--%>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Training and Certificate Column</label>
                                                <select name="trainingColumn" id="trainingColumnmultiselect_dd" multiple="multiple" class="form-select">
                                                    <option value="5" <% if (resumetemplate.checkToStr(tids, "5")) {%>selected<%}%>>Issue Date</option>
                                                    <option value="6" <% if (resumetemplate.checkToStr(tids, "6")) {%>selected<%}%>>Expiry Date</option>
                                                    <option value="7" <% if (resumetemplate.checkToStr(tids, "7")) {%>selected<%}%>>Validity</option>
                                                    <option value="1" <% if (resumetemplate.checkToStr(tids, "1")) {%>selected<%}%>>Course Name</option>
                                                    <option value="2" <% if (resumetemplate.checkToStr(tids, "2")) {%>selected<%}%>>Type</option>
                                                    <option value="3" <% if (resumetemplate.checkToStr(tids, "3")) {%>selected<%}%>>Institution/Location</option>
                                                    <option value="4" <% if (resumetemplate.checkToStr(tids, "4")) {%>selected<%}%>>Approved By</option>
                                                </Select>
                                            </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Training and Certificate Type</label>
                                                        <html:select property="certtype" styleId="certtype" styleClass="form-select">
                                                            <html:option value="1">Table</html:option>
                                                            <html:option value="2">Vertical</html:option>
                                                        </html:select>
                                                    </div>
                                                <div><div><b>Alternate Labels For Experience</b></div></div>
                                                
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Position</label>
                                                        <html:text property="label1" styleId="label1" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label1").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Department</label>
                                                        <html:text property="label2" styleId="label2" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label2").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Company Name</label>
                                                        <html:text property="label3" styleId="label3" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label3").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Asset Name</label>
                                                        <html:text property="label4" styleId="label4" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label4").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Asset Type</label>
                                                        <html:text property="label5" styleId="label5" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label5").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Start Date</label>
                                                        <html:text property="label6" styleId="label6" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label6").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">End Date</label>
                                                        <html:text property="label7" styleId="label7" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label7").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                        
                                                     <div><div><b>Alternate Labels For Education</b></div></div>   
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Kind</label>
                                                        <html:text property="label8" styleId="label8" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label8").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Degree</label>
                                                        <html:text property="label9" styleId="label9" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label9").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Institute</label>
                                                        <html:text property="label10" styleId="label10" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label10").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Field Of Study</label>
                                                        <html:text property="label11" styleId="label11" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label11").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                        
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Start Date</label>
                                                        <html:text property="label12" styleId="label12" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label12").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">End Date</label>
                                                        <html:text property="label13" styleId="label13" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label13").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                        <div><div><b>Alternate Labels For Document</b></div></div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Document Name</label>
                                                        <html:text property="label14" styleId="label14" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label14").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Number</label>
                                                        <html:text property="label15" styleId="label15" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label15").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Place Of Issue</label>
                                                        <html:text property="label16" styleId="label16" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label16").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Issued By</label>
                                                        <html:text property="label17" styleId="label17" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label17").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Issue Date</label>
                                                        <html:text property="label18" styleId="label18" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label18").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Expiry Date</label>
                                                        <html:text property="label19" styleId="label19" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label19").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                        
                                                        <div><div><b>Alternate Labels For Certification</b></div></div>                                                        
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Issue Date</label>
                                                        <html:text property="label20" styleId="label20" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label20").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Expiry Date</label>
                                                        <html:text property="label21" styleId="label21" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label21").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Validity</label>
                                                        <html:text property="label22" styleId="label22" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label22").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Course Name</label>
                                                        <html:text property="label23" styleId="label23" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label23").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Type</label>
                                                        <html:text property="label24" styleId="label24" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label24").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Institution / Location</label>
                                                        <html:text property="label25" styleId="label25" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label25").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                        <label class="form_label">Approved By</label>
                                                        <html:text property="label26" styleId="label26" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("label26").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group" >
                                                <label class="control-label col-md-3">Template Type:<span class="required" aria-required="true"></span></label>
                                                <div class="mt-radio-inline radio_list">
                                                    <label class="mt-radio">
                                                        <html:radio property="temptype" value="1" /> For CV
                                                        <span></span>
                                                    </label>
                                                    <label class="mt-radio">
                                                        <html:radio property="temptype" value="2" /> For Offer Letter
                                                        <span></span>
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="mb_20">
                                                <button type="button" class="mr_15"><a href="javascript:;" class="button" data-bs-toggle="modal" data-bs-target="#thank_you_modal"><i class="button"></i>Information For CV</a></button>
                                                <button type="button"><a href="javascript:;" class="button" data-bs-toggle="modal" data-bs-target="#thank_you_modal1"><i class="button"></i>Information For Offer Letter</a></button>
                                            </div>
                                            <div class="form-group form-horizontal">
                                                <label class="control-label col-md-3">Description <span class="required" aria-required="true"></span></label>
                                                <div class="col-md-6 control-label3">
                                                    <html:textarea property="description" styleId="elm1" style="width:1000px; height: 400px;" />
                                                </div>
                                            </div>
                                            <div class="form-group form-horizontal">
                                                <label class="control-label col-md-3">Header Description <span class="required" aria-required="true"></span></label>
                                                <div class="col-md-6 control-label3">
                                                    <html:textarea property="description2" styleId="elm2" style="width:1000px; height: 400px;" />
                                                </div>
                                            </div>
                                            <div class="form-group form-horizontal">
                                                <label class="control-label col-md-3">Footer Description <span class="required" aria-required="true"></span></label>
                                                <div class="col-md-6 control-label3">
                                                    <html:textarea property="description3" styleId="elm3" style="width:1000px; height: 400px;" />
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Attach Water Mark (5MB)(.jpeg/ .jpg /.png Only)</label>
                                                <html:file property="resumefile" styleId="upload1" onchange="javascript: setClass('1');"/>
                                                <a href="javascript:;" id="upload_link_1" class="attache_btn uploaded_img1"><i class="fas fa-paperclip"></i> Attach</a>
                                                <% if(!filename.equals("")) {%>
                                                <div class="down_prev"  id='preview_1'>
                                                    <a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript: setIframeedit('<%=filename%>');">Preview</a>
                                                </div>
                                                <% } %>
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

        <!-- END layout-wrapper -->
        <%@include file ="../footer.jsp"%>
        <div id="thank_you_modal" class="modal fade thank_you_modal blur_modal text-left" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-resumetemplate copy_text" >
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="mandatory-col info_msg">
                                    <p>You can copy the text by clicking on it.</p>
                                </div>
                                <div class="table-rep-plugin sort_table">
                                    <div class="table-responsive mb-0" data-bs-pattern="priority-columns">  
                                        <table id="tech-companies-1" class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th><span><b>Label</b> </span></th>
                                                    <th><span><b>Value</b></span></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td >First Name</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('FirstName');"><a href="javascript:;" id="FirstName" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text"> __FIRSTNAME__ </a></td>
                                                </tr>
                                                <tr>
                                                    <td>Middle Name</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('MiddleName');" ><a href="javascript:;" id="MiddleName" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__MIDDLENAME__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Last Name</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('LastName');" ><a href="javascript:;" id="LastName" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__LASTNAME__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Date of Birth</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('DateofBirth');" ><a href="javascript:;" id="DateofBirth" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__DOB__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Age</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Age');" ><a href="javascript:;" id="Age" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__AGE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Place of Birth</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PlaceofBirth');" ><a href="javascript:;" id="PlaceofBirth" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PLACEOFBIRTH__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Blood Group</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('BloodGroup');" ><a href="javascript:;" id="BloodGroup" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__BLOODGROUP__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Current Date Time</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CurrentDateTime');" ><a href="javascript:;" id="CurrentDateTime" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CURRENTDATETIME__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Email Id</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('EmailId');" ><a href="javascript:;" id="EmailId" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__EMAIL__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>City</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('City');"><a href="javascript:;" id="City" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CITY__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Primary Contact</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PrimaryContact');"><a href="javascript:;" id="PrimaryContact" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CONTACT1__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Primary Contact2</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PrimaryContact2');"><a href="javascript:;" id="PrimaryContact2" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CONTACT2__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Primary Contact3</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PrimaryContact3');"><a href="javascript:;" id="PrimaryContact3" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CONTACT3__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Gender</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Gender');"><a href="javascript:;" id="Gender" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__GENDER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Country Name</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CountryName');"><a href="javascript:;" id="CountryName" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__COUNTRYNAME__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Nationality</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Nationality');"><a href="javascript:;" id="Nationality" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__NATIONALITY__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Permanent Address line 1</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PermanentAddress1line1');"><a href="javascript:;" id="PermanentAddress1line1" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ADDRESS1LINE1__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Permanent Address line 2</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PermanentAddress1line2');"><a href="javascript:;" id="PermanentAddress1line2" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ADDRESS1LINE2__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Permanent Address line 3</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PermanentAddress1line3');"><a href="javascript:;" id="PermanentAddress1line3" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ADDRESS1LINE3__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Communication Address line 1</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CommunicationAddress1line1');"><a href="javascript:;" id="PermanentAddress2line1" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ADDRESS2LINE1__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Communication Address line 2</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CommunicationAddress1line2');"><a href="javascript:;" id="PermanentAddress2line2" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ADDRESS2LINE2__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Communication Address line 3</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CommunicationAddress1line3');"><a href="javascript:;" id="PermanentAddress2line3" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ADDRESS2LINE3__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Next to Kin</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('NexttoKin');"><a href="javascript:;" id="NexttoKin" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__NEXTOFKIN__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Relation</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Relation');"><a href="javascript:;" id="Relation" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__RELATION__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Emergency Contact 1</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('EmergencyContact1');"><a href="javascript:;" id="EmergencyContact1" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ECONTACT1__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Emergency Contact 2</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('EmergencyContact2');"><a href="javascript:;" id="EmergencyContact2" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ECONTACT2__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Marital Status</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('MaritalStatus');"><a href="javascript:;" id="MaritalStatus" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__MARITALSTATUS__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Profile Photo</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ProfilePhoto');"><a href="javascript:;" id="ProfilePhoto" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PHOTO__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Experience Department</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ExperienceDepartment');"><a href="javascript:;" id="ExperienceDepartment" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__EXPERIENCEDEPT__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Asset Type</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Assettype');"><a href="javascript:;" id="Assettype" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ASSETTYPE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Expected Salary</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ExpectedSalary');"><a href="javascript:;" id="ExpectedSalary" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__EXPECTEDSALARY__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Primary Position Name</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PositionName');"><a href="javascript:;" id="PositionName" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PRIMARYPOSITIONNAME__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Primary Position Rate</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PrimaryPositionRate');"><a href="javascript:;" id="PrimaryPositionRate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PRIMARYPOSITIONRATE__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Primary Position Overtime Rate</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PrimaryPositionOvertimeRate');"><a href="javascript:;" id="PrimaryPositionOvertimeRate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PRIMARYPOSITIONOVERTIMERATE__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Secondary Position Name</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PositionName2');"><a href="javascript:;" id="PositionName2" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__SECONDARYPOSITIONNAME__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Secondary Position Rate</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('SecondaryPositionRate');"><a href="javascript:;" id="SecondaryPositionRate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__SECONDARYPOSITIONRATE__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Secondary Position Overtime Rate</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('SecondaryPositionOvertimeRate');"><a href="javascript:;" id="SecondaryPositionOvertimeRate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__SECONDARYPOSITIONOVERTIMERATE__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Currency</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Currency');"><a href="javascript:;" id="Currency" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CURRENCY__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Work Experience</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('WorkExperience');"><a href="javascript:;" id="WorkExperience" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__WORKEXPERIENCE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Professional Profile</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ProfessionalProfile');"><a href="javascript:;" id="ProfessionalProfile" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PROFESSIONALPROFILE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Strengths and Skills</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('StrengthsandSkills');"><a href="javascript:;" id="StrengthsandSkills" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__STRENGTHSANDSKILLS__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Additional Skills</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('AdditionalSkills');"><a href="javascript:;" id="AdditionalSkills" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ADDITIONALSKILLS__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Roles and Responsibilities</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('RolesandResponsibilities');"><a href="javascript:;" id="RolesandResponsibilities" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ROLESANDRESPONSIBILITIES__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Total Experience</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('TotalExperience');"><a href="javascript:;" id="TotalExperience" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__TOTALWORKEXPERIENCE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Total Primary Position Experience</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('TotalPrimaryPositionExperience');"><a href="javascript:;" id="TotalPrimaryPositionExperience" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__TOTALPRIMARYPOSITIONWORKEXPERIENCE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Total Offshore Experience</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('TotalRotationExperience');"><a href="javascript:;" id="TotalRotationExperience" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__TOTALROTATIONEXPERIENCE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Total Primary Position Offshore Experience</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PrimaryPositionExperience');"><a href="javascript:;" id="PrimaryPositionExperience" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PRIMARYPOSITIONEXPERIENCE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Total Secondary Position Offshore Experience</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('SecondaryPositionExperience');"><a href="javascript:;" id="SecondaryPositionExperience" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__SECONDARYPOSITIONEXPERIENCE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Current Positions Offshore Experience</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PrimarySecondaryPositionExperience');"><a href="javascript:;" id="PrimarySecondaryPositionExperience" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PRIMARYSECONDARYPOSITIONEXPERIENCE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Educational Details</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('EducationalDetails');"><a href="javascript:;" id="EducationalDetails" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__EDUCATION__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Document Details</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('DocumentDetails');"><a href="javascript:;" id="DocumentDetails" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__GOVDOC__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Certifications Details</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CertificationsDetails');"><a href="javascript:;" id="CertificationsDetails" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CERTIFICATIONS__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Highest Qualification</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('HighestQualification');"><a href="javascript:;" id="HighestQualification" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__HIGHESTQUALIFICATION__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Aadhar Number</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('AadharNumber');"><a href="javascript:;" id="AadharNumber" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__AADHARNUMBER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Aadhar Issue Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('AadharIssueDate');"><a href="javascript:;" id="AadharIssueDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__AADHARISSUEDATE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Aadhar Card verification done</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('AadharVerification');"><a href="javascript:;" id="AadharVerification" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__AADHARVERIFICATION__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Birth Certificate Number</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('BirthCertificateNumber');"><a href="javascript:;" id="BirthCertificateNumber" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__BIRTHCERTIFICATENUMBER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Birth Certificate Issue Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('BirthCertificateIssueDate');"><a href="javascript:;" id="BirthCertificateIssueDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__BIRTHCERTIFICATEISSUEDATE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>CDC Number</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CDCNumber');"><a href="javascript:;" id="CDCNumber" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CDCNUMBER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>CDC Issue Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CDCIssueDate');"><a href="javascript:;" id="CDCIssueDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CDCISSUEDATE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>CDC Expiry Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CDCExpiryDate');"><a href="javascript:;" id="CDCExpiryDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CDCEXPIRYDATE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Medical Certificate Number</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('MedicalCertificateNumber');"><a href="javascript:;" id="MedicalCertificateNumber" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__MEDICALCERTIFICATENUMBER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Medical Certificate Issue Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('MedicalCertificateIssueDate');"><a href="javascript:;" id="MedicalCertificateIssueDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__MEDICALCERTIFICATEISSUEDATE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Medical Certificate Expiry Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('MedicalCertificateExpiryDate');"><a href="javascript:;" id="MedicalCertificateExpiryDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__MEDICALCERTIFICATEEXPIRYDATE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>ONGC NED Pass Number</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ONGCNEDPassNumber');"><a href="javascript:;" id="ONGCNEDPassNumber" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ONGCNEDPASSNUMBER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>ONGC NED Pass Issue Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ONGCNEDPassIssueDate');"><a href="javascript:;" id="ONGCNEDPassIssueDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ONGCNEDPASSNUMBERISSUEDATE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>ONGC NED Pass Expiry Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ONGCNEDPassExpiryDate');"><a href="javascript:;" id="ONGCNEDPassExpiryDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ONGCNEDPASSNUMBEREXPIRYDATE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>PAN Card Number</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PanCardNumber');"><a href="javascript:;" id="PanCardNumber" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PANCARDNUMBER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>PAN Card Issue Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PanCardIssueDate');"><a href="javascript:;" id="PanCardIssueDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PANCARDISSUEDATE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Passport Availability</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PassportAvailability');"><a href="javascript:;" id="PassportAvailability" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PASSPORTAVAILABILITY__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Passport Number</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PassportNumber');"><a href="javascript:;" id="PassportNumber" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PASSPORTNUMBER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Passport Issue Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PassportIssueDate');"><a href="javascript:;" id="PassportIssueDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PASSPORTISSUEDATE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Passport Expiry Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PassportExpiryDate');"><a href="javascript:;" id="PassportExpiryDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PASSPORTEXPIRYDATE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Passport Validity</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PassportValidity');"><a href="javascript:;" id="PassportValidity" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PASSPORTVALIDITY__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>PCC Availability</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PCCAvailability');"><a href="javascript:;" id="PCCAvailability" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PCCAVAILABILITY__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>PCC Number</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PCCNumber');"><a href="javascript:;" id="PCCNumber" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PCCNUMBER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>PCC Issue Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PCCIssueDate');"><a href="javascript:;" id="PCCIssueDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PCCISSUEDATE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>PCC Expiry Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PCCExpiryDate');"><a href="javascript:;" id="PCCExpiryDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PCCEXPIRYDATE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>PCC Validity</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PCCValidity');"><a href="javascript:;" id="PCCValidity" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PCCVALIDITY__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>O-BOSIET Availability </td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('BOSIETAvailability');"><a href="javascript:;" id="BOSIETAvailability" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__BOSIETAVAILABILITY__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>O-BOSIET Validity </td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('BOSIETValidity');"><a href="javascript:;" id="BOSIETValidity" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__BOSIETVALIDITY__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Hydrogen Sulfide (H2S) Training Certification Availability</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('H2SAvailability');"><a href="javascript:;" id="H2SAvailability" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__H2SAVAILABILITY__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Hydrogen Sulfide (H2S) Training Certification Validity</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('H2SValidity');"><a href="javascript:;" id="H2SValidity" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__H2SVALIDITY__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Language List</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('LanguageList');"><a href="javascript:;" id="LanguageList" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__LANGUAGELIST__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Language Proficiency</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('LanguageProficiency');"><a href="javascript:;" id="LanguageProficiency" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__LANGUAGEPROFICIENCY__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 1</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue1');"><a href="javascript:;" id="CustomValue1" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE1__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 2</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue2');"><a href="javascript:;" id="CustomValue2" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE2__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 3</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue3');"><a href="javascript:;" id="CustomValue3" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE3__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 4</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue4');"><a href="javascript:;" id="CustomValue4" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE4__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 5</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue5');"><a href="javascript:;" id="CustomValue5" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE5__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 6</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue6');"><a href="javascript:;" id="CustomValue6" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE6__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 7</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue7');"><a href="javascript:;" id="CustomValue7" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE7__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 8</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue8');"><a href="javascript:;" id="CustomValue8" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE8__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 9</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue9');"><a href="javascript:;" id="CustomValue9" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE9__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 10</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue10');"><a href="javascript:;" id="CustomValue10" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE10__ </a></td>  
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

        <div id="thank_you_modal1" class="modal fade thank_you_modal blur_modal text-left" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-resumetemplate copy_text" >
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="mandatory-col info_msg">
                                    <p>You can copy the text by clicking on it.</p>
                                </div>
                                <div class="table-rep-plugin sort_table">
                                    <div class="table-responsive mb-0" data-bs-pattern="priority-columns">  
                                        <table id="tech-companies-1" class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th><span><b>Label</b> </span></th>
                                                    <th><span><b>Value</b></span></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>Valid From Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ValidFromDate');"><a href="javascript:;" id="ValidFromDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__VALIDFROM__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Valid To Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ValidToDate');"><a href="javascript:;" id="ValidToDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__VALIDTO__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Company</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Company');"><a href="javascript:;" id="Company" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__COMPANY__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Contractor</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Contractor');"><a href="javascript:;" id="Contractor" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CONTRACTOR__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Effective Date of Official Call Of Order</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CallOfOrder');"><a href="javascript:;" id="CallOfOrder" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CALLOFFORDER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Demand Manager Name</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ManagerName');"><a href="javascript:;" id="ManagerName" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__MANAGERNAME__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Designation</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Designation');"><a href="javascript:;" id="Designation" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__DESIGNATION__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Candidate Name</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PersonnelRequested');"><a href="javascript:;" id="PersonnelRequested" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__NAME__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Primary Position Name</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PositionName');"><a href="javascript:;" id="PositionName" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PRIMARYPOSITIONNAME__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Primary Position Rate</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PrimaryPositionRate');"><a href="javascript:;" id="PrimaryPositionRate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PRIMARYPOSITIONRATE__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Primary Position Overtime Rate</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PrimaryPositionOvertimeRate');"><a href="javascript:;" id="PrimaryPositionOvertimeRate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PRIMARYPOSITIONOVERTIMERATE__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Secondary Position Name</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PositionName2');"><a href="javascript:;" id="PositionName2" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__SECONDARYPOSITIONNAME__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Secondary Position Rate</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('SecondaryPositionRate');"><a href="javascript:;" id="SecondaryPositionRate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__SECONDARYPOSITIONRATE__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Secondary Position Overtime Rate</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('SecondaryPositionOvertimeRate');"><a href="javascript:;" id="SecondaryPositionOvertimeRate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__SECONDARYPOSITIONOVERTIMERATE__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Next to Kin</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('NexttoKin');"><a href="javascript:;" id="NexttoKin" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__NEXTOFKIN__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Asset Name</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('AssetName');"><a href="javascript:;" id="AssetName" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ASSETNAME__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Comments</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Comments');"><a href="javascript:;" id="Comments" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__COMMENTS__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Day Rate</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('DayRate');"><a href="javascript:;" id="DayRate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__DAYRATE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Day Rate Currency</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('DayRateCurrency');"><a href="javascript:;" id="DayRateCurrency" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__DAYRATECURRENCY__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Total Rate</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('TotalRate');"><a href="javascript:;" id="TotalRate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__TOTALRATE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Variable Pay Element(VPE)</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Vpe');"><a href="javascript:;" id="Vpe" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__VPE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Permanent Address line 1</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PermanentAddress1line1');"><a href="javascript:;" id="PermanentAddress1line1" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ADDRESS1LINE1__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Permanent Address line 2</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PermanentAddress1line2');"><a href="javascript:;" id="PermanentAddress1line2" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ADDRESS1LINE2__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Permanent Address line 3</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PermanentAddress1line3');"><a href="javascript:;" id="PermanentAddress1line3" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ADDRESS1LINE3__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Communication Address line 1</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CommunicationAddress1line1');"><a href="javascript:;" id="CommunicationAddress1line1" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ADDRESS2LINE1__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Communication Address line 2</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CommunicationAddress1line2');"><a href="javascript:;" id="CommunicationAddress1line2" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ADDRESS2LINE2__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Communication Address line 3</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CommunicationAddress1line3');"><a href="javascript:;" id="CommunicationAddress1line3" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ADDRESS2LINE3__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Relation</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Relation');"><a href="javascript:;" id="Relation" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__RELATION__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Emergency Contact 1</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('EmergencyContact1');"><a href="javascript:;" id="EmergencyContact1" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ECONTACT1__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Emergency Contact 2</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('EmergencyContact2');"><a href="javascript:;" id="EmergencyContact2" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ECONTACT2__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CurrDate');"><a href="javascript:;" id="CurrDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CURRENTDATE__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 1</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue1');"><a href="javascript:;" id="CustomValue1" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE1__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 2</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue2');"><a href="javascript:;" id="CustomValue2" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE2__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 3</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue3');"><a href="javascript:;" id="CustomValue3" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE3__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 4</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue4');"><a href="javascript:;" id="CustomValue4" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE4__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 5</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue5');"><a href="javascript:;" id="CustomValue5" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE5__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 6</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue6');"><a href="javascript:;" id="CustomValue6" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE6__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 7</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue7');"><a href="javascript:;" id="CustomValue7" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE7__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 8</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue8');"><a href="javascript:;" id="CustomValue8" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE8__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 9</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue9');"><a href="javascript:;" id="CustomValue9" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE9__ </a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Custom Value 10</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CustomValue10');"><a href="javascript:;" id="CustomValue10" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CUSTOMVALUE10__ </a></td>  
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
        <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                            <span class="resume_title"> File</span>
                            <div class="float-end">
                                <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                                <a id='diframe' href="" class="down_btn"><img src="../assets/images/download.png"/></a>
                            </div>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <iframe id='iframe' class="doc" src=""></iframe>
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
        <script type="text/javascript" src="../js/tiny/tinymce.min.js"></script>
        <script type="text/javascript" src="../js/tiny/form-editor.init.js"></script>
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
        <script>
            $(function () {
                $('[data-toggle="tooltip"]').tooltip()
            })
        </script>
        <script>
            $(function () {
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });
            });
        </script>
        <script>
                function setIframeedit(uval)
                {
                    var url_v = "", classname = "";
                    if (uval.includes(".doc") || uval.includes(".docx") || uval.includes("wordprocessingml.document")) 
                    {
                        url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
                        classname = "doc_mode";
                    }
                    else if (uval.includes(".pdf"))
                    {
                        url_v = uval+"#toolbar=0&page=1&view=fitH,100";
                        classname = "pdf_mode";
                    }
                    else
                    {
                        url_v = uval;
                        classname = "img_mode";
                    }
                    window.top.$('#iframe').attr('src', 'about:blank');
                    setTimeout(function () {
                        window.top.$('#iframe').attr('src', url_v);
                         document.getElementById("iframe").className=classname;
                         document.getElementById("diframe").href =uval;
                    }, 1000);

                    $("#iframe").on("load", function() {
                            let head = $("#iframe").contents().find("head");
                            let css = '<style>img{margin: 0px auto;}</style>';
                            $(head).append(css);
                        });
                }
            </script>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#expColumnmultiselect_dd').multiselect({
                    includeSelectAllOption: true,
                    //maxHeight: 400,
                    dropUp: true,
                    nonSelectedText: '- Select -',
                    maxHeight: 200,
                    enableFiltering: false,
                    enableCaseInsensitiveFiltering: false,
                    buttonWidth: '100%'
                });
                $('#eduColumnmultiselect_dd').multiselect({
                    includeSelectAllOption: true,
                    //maxHeight: 400,
                    dropUp: true,
                    nonSelectedText: '- Select -',
                    maxHeight: 200,
                    enableFiltering: false,
                    enableCaseInsensitiveFiltering: false,
                    buttonWidth: '100%'
                });
                $('#docColumnmultiselect_dd').multiselect({
                    includeSelectAllOption: true,
                    //maxHeight: 400,
                    dropUp: true,
                    nonSelectedText: '- Select -',
                    maxHeight: 200,
                    enableFiltering: false,
                    enableCaseInsensitiveFiltering: false,
                    buttonWidth: '100%'
                });
                $('#trainingColumnmultiselect_dd').multiselect({
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
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/resumetemplate.js"></script>
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
