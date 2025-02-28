<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.contract.ContractInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="contract" class="com.web.jxp.contract.Contract" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 98;
            if (session.getAttribute("LOGININFO") == null)
            {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
            }
            String message = "", clsmessage = "red_font", ids = "", eids = "", dids = "", tids = "", lids ="", vids ="", nids ="";
           
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }        
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";
            
            String thankyou = "";
            if(request.getAttribute("VCSAVEMODEL") != null)
            {
                thankyou = (String)request.getAttribute("VCSAVEMODEL");
                request.removeAttribute("VCSAVEMODEL");
            }
                   
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
            if (session.getAttribute("LANGUAGEDETAIL_IDs") != null) 
            {
                lids = (String) session.getAttribute("LANGUAGEDETAIL_IDs");            
                request.getSession().removeAttribute("LANGUAGEDETAIL_IDs");
            }   
            if (session.getAttribute("VACCINEDETAIL_IDs") != null) 
            {
                vids = (String) session.getAttribute("VACCINEDETAIL_IDs");            
                request.getSession().removeAttribute("VACCINEDETAIL_IDs");
            }
            if (session.getAttribute("NOMINEEDETAIL_IDs") != null) 
            {
                nids = (String) session.getAttribute("NOMINEEDETAIL_IDs");            
                request.getSession().removeAttribute("NOMINEEDETAIL_IDs");
            }
            String filename = "";
            if (session.getAttribute("FILENAME") != null)
            {
                filename = (String)session.getAttribute("FILENAME");
                if(filename != null && !filename.equals(""))
                    filename = contract.getMainPath("view_contractfile")+filename;
            }
            
    %>
    <head>
        <meta charset="utf-8">
        <title><%= contract.getMainPath("title") != null ? contract.getMainPath("title") : "" %></title>
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
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/contract.js"></script>        
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/contract/ContractAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="contractId"/>
        <html:hidden property="doSave"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="search"/>  
        <html:hidden property="deschidden"/>
        <html:hidden property="contractfilehidden"/>
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
                                        <c:when test="${contractForm.contractId <= 0}">
                                            Add Contract
                                        </c:when>
                                        <c:otherwise>
                                            Edit Contract
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
                                                <label class="form_label">Client<span class="required">*</span></label>
                                                <html:select property="clientId" styleClass="form-select" onchange="javascript: setAssetsDDL();">
                                                    <html:optionsCollection filter="false" property="clients" label="ddlLabel" value="ddlValue">
                                                    </html:optionsCollection>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                <label class="form_label">Asset<span class="required">*</span></label>
                                                <html:select property="assetId" styleId="assetId" styleClass="form-select" onchange="javascript: setContractDDL();">
                                                    <html:optionsCollection filter="false" property="assets" label="ddlLabel" value="ddlValue">
                                                    </html:optionsCollection>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                <label class="form_label">Name<span class="required">*</span></label>
                                                <html:text property="name" styleId="name" styleClass="form-control" maxlength="100"/>
                                                <script type="text/javascript">
                                                    document.getElementById("name").setAttribute('placeholder', '');
                                                </script>
                                            </div> 
                                                
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Work Experience Column</label>
                                                <select name="expColumn" id="expColumnmultiselect_dd" multiple="multiple" class="form-select">
                                                    <option value="1" <% if (contract.checkToStr(ids, "1")) {%>selected<%}%>>Position</option>
                                                    <option value="2" <% if (contract.checkToStr(ids, "2")) {%>selected<%}%>>Department</option>
                                                    <option value="3" <% if (contract.checkToStr(ids, "3")) {%>selected<%}%>>Company Name</option>
                                                    <option value="4" <% if (contract.checkToStr(ids, "4")) {%>selected<%}%>>Asset Name</option>
                                                    <option value="5" <% if (contract.checkToStr(ids, "5")) {%>selected<%}%>>Start Date</option>
                                                    <option value="6" <% if (contract.checkToStr(ids, "6")) {%>selected<%}%>>End Date</option>
                                                    <option value="7" <% if (contract.checkToStr(ids, "7")) {%>selected<%}%>>Experience</option>
                                                </Select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Education Column</label>
                                                <select name="eduColumn" id="eduColumnmultiselect_dd" multiple="multiple" class="form-select">
                                                    <option value="1" <% if (contract.checkToStr(eids, "1")) {%>selected<%}%>>Degree</option>
                                                    <option value="2" <% if (contract.checkToStr(eids, "2")) {%>selected<%}%>>End Date</option>
                                                    <option value="3" <% if (contract.checkToStr(eids, "3")) {%>selected<%}%>>Institution/Location</option>
                                                </Select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Document Column</label>
                                                <select name="docColumn" id="docColumnmultiselect_dd" multiple="multiple" class="form-select">
                                                    <option value="1" <% if (contract.checkToStr(dids, "1")) {%>selected<%}%>>Document Name</option>
                                                    <option value="2" <% if (contract.checkToStr(dids, "2")) {%>selected<%}%>>Number</option>
                                                    <option value="3" <% if (contract.checkToStr(dids, "3")) {%>selected<%}%>>Place of Issue</option>
                                                    <option value="4" <% if (contract.checkToStr(dids, "4")) {%>selected<%}%>>Issued By</option>
                                                    <option value="5" <% if (contract.checkToStr(dids, "5")) {%>selected<%}%>>Issue Date</option>
                                                    <option value="6" <% if (contract.checkToStr(dids, "6")) {%>selected<%}%>>Expiry Date</option>
                                                </Select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Training and Certificate Column</label>
                                                <select name="trainingColumn" id="trainingColumnmultiselect_dd" multiple="multiple" class="form-select">
                                                    <option value="5" <% if (contract.checkToStr(tids, "5")) {%>selected<%}%>>Issue Date</option>
                                                    <option value="6" <% if (contract.checkToStr(tids, "6")) {%>selected<%}%>>Expiry Date</option>
                                                    <option value="1" <% if (contract.checkToStr(tids, "1")) {%>selected<%}%>>Course Name</option>
                                                    <option value="2" <% if (contract.checkToStr(tids, "2")) {%>selected<%}%>>Type</option>
                                                    <option value="3" <% if (contract.checkToStr(tids, "3")) {%>selected<%}%>>Institution/Location</option>
                                                    <option value="4" <% if (contract.checkToStr(tids, "4")) {%>selected<%}%>>Approved By</option>
                                                    <option value="7" <% if (contract.checkToStr(tids, "7")) {%>selected<%}%>>Certification No</option>
                                                </Select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Language Column</label>
                                                <select name="languageColumn" id="languageColumnmultiselect_dd" multiple="multiple" class="form-select">
                                                    <option value="1" <% if (contract.checkToStr(lids, "1")) {%>selected<%}%>>Name</option>
                                                    <option value="2" <% if (contract.checkToStr(lids, "2")) {%>selected<%}%>>Proficiency</option>
                                                </Select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Vaccine Column</label>
                                                <select name="vaccineColumn" id="vaccineColumnmultiselect_dd" multiple="multiple" class="form-select">
                                                    <option value="1" <% if (contract.checkToStr(vids, "1")) {%>selected<%}%>>Name</option>
                                                    <option value="2" <% if (contract.checkToStr(vids, "2")) {%>selected<%}%>>Issue Date</option>
                                                    <option value="3" <% if (contract.checkToStr(vids, "3")) {%>selected<%}%>>Expiry Date</option>
                                                    <option value="4" <% if (contract.checkToStr(vids, "4")) {%>selected<%}%>>Place Of Issue</option>
                                                </Select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Nominee Column</label>
                                                <select name="nomineeColumn" id="nomineeColumnmultiselect_dd" multiple="multiple" class="form-select">
                                                    <option value="1" <% if (contract.checkToStr(nids, "1")) {%>selected<%}%>>Name</option>
                                                    <option value="2" <% if (contract.checkToStr(nids, "2")) {%>selected<%}%>>Contact Number</option>
                                                    <option value="3" <% if (contract.checkToStr(nids, "3")) {%>selected<%}%>>Relation</option>
                                                    <option value="4" <% if (contract.checkToStr(nids, "4")) {%>selected<%}%>>Age Of Nominee</option>
                                                    <option value="5" <% if (contract.checkToStr(nids, "5")) {%>selected<%}%>>Address</option>
                                                    <option value="6" <% if (contract.checkToStr(nids, "6")) {%>selected<%}%>>Percentage Of Distribution</option>
                                                </Select>
                                            </div>
                                                
                                            <div class="mb_20">
                                                <button type="button" class="mr_15"><a href="javascript:;" class="button" data-bs-toggle="modal" data-bs-target="#thank_you_modal"><i class="button"></i>Information For Contract</a></button>
                                            </div>

                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group" onchange="javascript: showhide();">
                                                <label class="control-label col-md-3">Contract Type:<span class="required" aria-required="true"></span></label>
                                                <div class="mt-radio-inline radio_list">
                                                    <label class="mt-radio">
                                                        <html:radio property="type" value="1"/> Main Contract
                                                        <span></span>
                                                    </label>
                                                    <label class="mt-radio">
                                                        <html:radio property="type" value="2"/> Sub Contract
                                                        <span></span>
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group" id="subDiv" style="display: none">
                                                <label class="form_label">Contract<span class="required">*</span></label>
                                                <html:select property="refId" styleId="refId" styleClass="form-select">
                                                    <html:optionsCollection filter="false" property="contracts" label="ddlLabel" value="ddlValue">
                                                    </html:optionsCollection>
                                                </html:select>
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
                                                <html:file property="contractfile" styleId="upload1" onchange="javascript: setClass('1');"/>
                                                <a href="javascript:;" id="upload_link_1" class="attache_btn uploaded_img1"><i class="fas fa-paperclip"></i> Attach</a>
                                                <% if(!filename.equals("")) {%>
                                                <div class="down_prev"  id='preview_1'>
                                                    <a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript: setIframe('<%=filename%>');">Preview</a>
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
            <div class="modal-dialog modal-dialog-centered modal-contract copy_text" >
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body" >
                        <div class="row client_position_table" >
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
                                                    <td>Email Id</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Email');" ><a href="javascript:;" id="Email" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__EMAIL__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>DOB</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('DateofBirth');" ><a href="javascript:;" id="DateofBirth" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__DOB__</a></td>  
                                                </tr>                                                
                                                <tr>
                                                <td>Age</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Age');" ><a href="javascript:;" id="Age" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__AGE__</a></td>  
                                                </tr>                                                
                                                <tr>
                                                    <td>Gender</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Gender');"><a href="javascript:;" id="Gender" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__GENDER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Employee Number</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('empno');"><a href="javascript:;" id="empno" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__EMPLOYEENUMBER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Permanent Address</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PermanentAddress');"><a href="javascript:;" id="PermanentAddress" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PERMANENTADDRESS__</a></td>  
                                                </tr>
                                                
                                                <tr>
                                                    <td>Communication Address</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CommunicationAddress');"><a href="javascript:;" id="PermanentAddress" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__COMMUNICATIONADDRESS__</a></td>  
                                                </tr>                                                
                                                 <tr>
                                                    <td>Primary Contact</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PrimaryContact');"><a href="javascript:;" id="PrimaryContact" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CONTACT1__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Primary Contact 2</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PrimaryContact2');"><a href="javascript:;" id="PrimaryContact2" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CONTACT2__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Primary Contact 3</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PrimaryContact3');"><a href="javascript:;" id="PrimaryContact3" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CONTACT3__</a></td>  
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
                                                    <td>Nationality</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Nationality');"><a href="javascript:;" id="Nationality" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__NATIONALITY__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Next to Kin</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('NexttoKin');"><a href="javascript:;" id="NexttoKin" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__NEXTOFKIN__</a></td>  
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
                                                    <td>Asset Name</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('AssetName');"><a href="javascript:;" id="AssetName" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ASSETNAME__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Company Name</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Company');"><a href="javascript:;" id="Company" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__COMPANY__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Work Experience</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('WorkExperience');"><a href="javascript:;" id="WorkExperience" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__WORKEXPERIENCE__</a></td>  
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
                                                    <td>Nominee Details</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('NomineeDetails');"><a href="javascript:;" id="NomineeDetails" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__NOMINEE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Highest Qualification</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('HighestQualification');"><a href="javascript:;" id="HighestQualification" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__HIGHESTQUALIFICATION__</a></td>  
                                                </tr> 
                                                <tr>
                                                    <td>CDC Number</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CDCNumber');"><a href="javascript:;" id="CDCNumber" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CDCNUMBER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>PAN Card Number</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PanCardNumber');"><a href="javascript:;" id="PanCardNumber" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PANCARDNUMBER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Aadhar Number</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('AadharNumber');"><a href="javascript:;" id="AadharNumber" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__AADHARNUMBER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Passport Number</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PassportNumber');"><a href="javascript:;" id="PassportNumber" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PASSPORTNUMBER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Passport place Of Issue</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('PassportplaceofIssue');"><a href="javascript:;" id="PassportplaceofIssue" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PASSPORTISSUEPLACE__</a></td>  
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
                                                    <td>Language Details</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Language');"><a href="javascript:;" id="Language" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__LANGUAGEDETAILS__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Vaccine Details</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('Language');"><a href="javascript:;" id="Language" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__VACCINEDEATILS__</a></td>  
                                                </tr>      
                                                <tr>
                                                    <td>Seaman Specific Medical Fitness</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ssmf');"><a href="javascript:;" id="ssmf" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__SSMF__</a></td>  
                                                </tr>      
                                                <tr>
                                                    <td>OGUK Medical FTW</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ogukmedicalftw');"><a href="javascript:;" id="ogukmedicalftw" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__OGUKMEDICAL__</a></td>  
                                                </tr>      
                                                <tr>
                                                    <td>OGUK Expiry</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ogukexp');"><a href="javascript:;" id="ogukexp" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__OGUKMEDICALEXPIRY__</a></td>  
                                                </tr>      
                                                <tr>
                                                    <td>Medical Fitness Certificate</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('medifitcert');"><a href="javascript:;" id="medifitcert" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__MEDICALCERTIFICATE__</a></td>  
                                                </tr>      
                                                <tr>
                                                    <td>Medical Fitness Certificate Expiry</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('medifitcertexp');"><a href="javascript:;" id="medifitcertexp" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__MEDICALCERTIFICATEEXPIRY__</a></td>  
                                                </tr>      
                                                <tr>
                                                    <td>Blood Group</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('bloodgroup');"><a href="javascript:;" id="bloodgroup" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__BLOODGROUP__</a></td>  
                                                </tr>                                                
                                                <tr>
                                                    <td>Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CurrDate');"><a href="javascript:;" id="CurrDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CURRENTDATE__</a></td>
                                                </tr>
                                                <tr>
                                                    <td>Profile Photo</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ProfilePhoto');"><a href="javascript:;" id="ProfilePhoto" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__PHOTO__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Marital Status</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('MaritalStatus');"><a href="javascript:;" id="MaritalStatus" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__MARITALSTATUS__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Bank Account No</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('BankAccountNo');"><a href="javascript:;" id="BankAccountNo" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__ACCOUNTNUMBER__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>IFSC Code</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('IFSCCode');"><a href="javascript:;" id="IFSCCode" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__IFSCCODE__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Valid From Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ValidFromDate');"><a href="javascript:;" id="ValidFromDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__VALIDFROM__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Valid To Date</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('ValidToDate');"><a href="javascript:;" id="ValidToDate" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__VALIDTO__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Current Year</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('CurrentYear');"><a href="javascript:;" id="CurrentYear" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__CURRENTYEAR__</a></td>  
                                                </tr>
                                                <tr>
                                                    <td>Serial Number</td>
                                                    <td class='hand_cursor' onclick = "javascript: copyTemplate('SerialNumber');"><a href="javascript:;" id="SerialNumber" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Copy Text" aria-label="Copy Text">__SERIALNUMBER__</a></td>  
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
            showhide();
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
                $('#languageColumnmultiselect_dd').multiselect({
                    includeSelectAllOption: true,
                    //maxHeight: 400,
                    dropUp: true,
                    nonSelectedText: '- Select -',
                    maxHeight: 200,
                    enableFiltering: false,
                    enableCaseInsensitiveFiltering: false,
                    buttonWidth: '100%'
                });
                $('#vaccineColumnmultiselect_dd').multiselect({
                    includeSelectAllOption: true,
                    //maxHeight: 400,
                    dropUp: true,
                    nonSelectedText: '- Select -',
                    maxHeight: 200,
                    enableFiltering: false,
                    enableCaseInsensitiveFiltering: false,
                    buttonWidth: '100%'
                });
                $('#nomineeColumnmultiselect_dd').multiselect({
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
        <script>
            $(function () {
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
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
