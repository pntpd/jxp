<%@page import="com.web.jxp.candidateregistration.CandidateRegistrationInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<jsp:useBean id="cr" class="com.web.jxp.candidateregistration.CandidateRegistration" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            if (session.getAttribute("REG_EMAILID") == null) {
    %>
    <jsp:forward page="/candidateregistration/regindex1.jsp"/>
    <%
        }
        String path = cr.getMainPath("view_candidate_file");
        String thankyou = "no";
        if (request.getAttribute("CANDSAVEMODEL") != null) {
            thankyou = (String) request.getAttribute("CANDSAVEMODEL");
            request.removeAttribute("CANDSAVEMODEL");
        }
        int sameaspaddress = 0;
        if (request.getAttribute("SAMEASADDRESS") != null) {
            sameaspaddress = (int) request.getAttribute("SAMEASADDRESS");
            request.removeAttribute("SAMEASADDRESS");
        }
        int isfresher = 0;
        if (request.getAttribute("ISFRESHER") != null) {
            isfresher = (int) request.getAttribute("ISFRESHER");
            request.removeAttribute("ISFRESHER");
        }

        String resumeFile = "", adhaarFile = "", panFile = "", langproftype = "";
        if (request.getAttribute("RESUMEFILE") != null) {
            resumeFile = (String) request.getAttribute("RESUMEFILE");
            request.removeAttribute("RESUMEFILE");
            if (resumeFile != null && !resumeFile.equals("")) {
                resumeFile = path + resumeFile;
            }
        }
        if (request.getAttribute("ADHAARFILE") != null) {
            adhaarFile = (String) request.getAttribute("ADHAARFILE");
            request.removeAttribute("ADHAARFILE");
            if (adhaarFile != null && !adhaarFile.equals("")) {
                adhaarFile = path + adhaarFile;
            }
        }
        if (request.getAttribute("PANFILE") != null) {
            panFile = (String) request.getAttribute("PANFILE");
            request.removeAttribute("PANFILE");
            if (panFile != null && !panFile.equals("")) {
                panFile = path + panFile;
            }
        }

        ArrayList exp_list = new ArrayList();
        if (session.getAttribute("CANDREGWORKEXP") != null) {
            exp_list = (ArrayList) request.getSession().getAttribute("CANDREGWORKEXP");
        }
        int exp_size = exp_list.size();

        ArrayList cert_list = new ArrayList();
        if (session.getAttribute("CANDREGCERT") != null) {
            cert_list = (ArrayList) request.getSession().getAttribute("CANDREGCERT");
        }
        int cert_size = cert_list.size();

        if (request.getAttribute("LANGPROFTYPE") != null) {
            langproftype = (String) request.getAttribute("LANGPROFTYPE");
            request.removeAttribute("LANGPROFTYPE");
        }
    %>
    <head>
        <meta charset="utf-8">
        <title>OCS - Candidate Registration Form</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/pnt-images/ocsfavicon.png">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../icons/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="../simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/drop/dropzone.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/pnt_style.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="../autofill/jquery-ui.min.css" />  <!-- Autofill-->
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script src="../jsnew/candidateregistration.js" type="text/javascript"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed form-body">
    <html:form action="/candidateregistration/CandidateRegistrationAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="candidateId"/>
        <html:hidden property="doSave"/>
        <html:hidden property="doSaveDraft"/>
        <html:hidden property="fname"/>
        <html:hidden property="localFile"/>
        <html:hidden property="tempfname"/>
        <html:hidden property="templocalFile"/>
        <html:hidden property="currentDate"/>
        <html:hidden property="hdnadhaarfile"/>
        <html:hidden property="hdnpanfile"/>
        <html:hidden property="expCount"/>
        <html:hidden property="certCount"/>
        <div class="container main-boder">
            <div class="header">
                <div class="headerLinetxt d-flex align-items-center gap-2">
                    <div class="verticalRedLine h-30 ">

                    </div>
                    <div>
                        <h2 class="sbold mt-2">Candidate Registration Form</h2>
                    </div>
                </div>
                <div class="action-buttons d-flex gap-2" id="submitdiv">                  
                    <button type="button" class="newbtn newbtn-draft h-35" onclick="getDraftSave();">Save Draft</button>
                    <button type="button" class="newbtn newbtn-submit h-35" onclick="getSubmit();">Submit</button>
                    <button type="button" class="newbtn newbtn-cancel h-35" onclick="getCloseRegistration();">Close</button>
                </div>
            </div>
            <div class="section-container" id="main_section">
                <div class="section">
                    <div class="form-group">
                        <div class="mt-radio-inline">
                            <label>Are you applying for onshore position?</label>
                            <label class="mt-radio">
                                <html:radio property="onshore" styleId="onshore1" value="1"/> Yes<span></span>
                            </label>
                            <label class="mt-radio">
                                <html:radio property="onshore" value="2"/> No<span></span>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="section" id="mySection1">
                    <label class="section-label" onclick="getshowhide('1');">
                        Resume
                        <img id="img1" src="../assets/images/white-up-arrow.png" alt="Up Arrow"/>
                    </label>
                    <div id="div1">
                        <div class="row p-2">
                            <div class="col-lg-3 col-md-3 col-sm-6 col-12">
                                <label class="form_label">Resume (5MB) (.pdf/.docx/ .jpeg/ .jpg /.png)<span class="required">*</span></label>
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-6 text-center">
                                <a href="javascript:;" data-bs-toggle="modal" id="upload_link_resume" data-bs-target="#drag_drop_modal" class="attache_btn"><i class="fas fa-paperclip"></i> Attachment <span class="attach_number"> </span></a>
                                <% if (!resumeFile.equals("")) {%><div class="down_prev"><a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=resumeFile%>');">Preview</a></div><% } %>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="section" id="mySection2">
                    <label class="section-label" onclick="getshowhide('2');">
                        Personal Information
                        <img id="img2" src="../assets/images/white-down-arrow.png" alt="Down Arrow"/>
                    </label>
                    <div id="div2" style="display: none">
                        <div class="row">
                            <div class="col-md-6 col-sm-12">
                                <div class="row">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <label class="form_label">First Name<span class="required">*</span></label>
                                        <html:text property="firstname" styleId="firstname" styleClass="form-control" maxlength="100"/>
                                        <script type="text/javascript">
                                            document.getElementById("firstname").setAttribute('placeholder', 'Enter First Name');
                                        </script>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <label class="form_label">Middle Name </label>
                                        <html:text property="middlename" styleId="middlename" styleClass="form-control" maxlength="100"/>
                                        <script type="text/javascript">
                                            document.getElementById("middlename").setAttribute('placeholder', 'Enter Middle Name');
                                        </script>
                                    </div>

                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <label class="form_label">Last Name<span class="required">*</span></label>
                                        <html:text property="lastname" styleId="lastname" styleClass="form-control" maxlength="100"/>
                                        <script type="text/javascript">
                                            document.getElementById("lastname").setAttribute('placeholder', 'Enter Last Name');
                                        </script>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                        <label class="form_label">Date of Birth<span class="required">*</span></label>
                                        <div class="input-daterange input-group">
                                            <html:text property="dob" styleId="dob" onchange="getCurrentAge(this);" styleClass="form-control add-style wesl_dt date-add" />
                                            <script type="text/javascript">
                                                document.getElementById("dob").setAttribute('placeholder', 'DD-MMM-YYYY');
                                            </script>
                                        </div>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <div class="row">
                                            <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                                <label class="form_label">Adhaar<span class="required">*</span></label>
                                                <html:text property="adhaar" styleId="adhaar" styleClass="form-control" maxlength="12"/>
                                                <script type="text/javascript">
                                                    document.getElementById("adhaar").setAttribute('placeholder', 'Enter Adhaar No.');
                                                </script>
                                            </div>
                                            <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                                <label class="form_label">Adhaar File</label>
                                                <html:file property="adhaarfile" styleId="upload1" styleClass="form-control" onchange="javascript: setClass('1');"/>
                                                <div class="w-100 w-sm-50">
                                                    <a href="javascript:;" id="upload_link_1" class="attache_btn uploaded_img1">
                                                        <i class="fas fa-paperclip"></i> Attach
                                                    </a>
                                                </div>
                                                <% if (!adhaarFile.equals("")) {%><div class="down_prev"><a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=adhaarFile%>');">Preview</a></div><% } %>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-12">
                                <div class="row">
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                        <label class="form_label">Gender<span class="required">*</span></label>
                                        <html:select styleClass="form-select" property="gender">
                                            <html:option value="Gender">- Select -</html:option>
                                            <html:option value="Male">Male</html:option>
                                            <html:option value="Female">Female</html:option>
                                            <html:option value="Others">Others</html:option>
                                        </html:select>
                                    </div>
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                        <label class="form_label">Marital Status<span class="required">*</span></label>
                                        <html:select property="maritalstatusId" styleClass="form-select">
                                            <html:optionsCollection filter="false" property="maritalstatuses" label="ddlLabel" value="ddlValue">
                                            </html:optionsCollection>
                                        </html:select>
                                    </div>
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                        <label class="form_label">Nationality<span class="required">*</span></label>
                                        <html:select property="nationalityId" styleClass="form-select">
                                            <html:optionsCollection filter="false" property="countries" label="ddlLabel1" value="ddlValue">
                                            </html:optionsCollection>
                                        </html:select>
                                    </div>
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                        <label class="form_label">Religion</label>
                                        <html:text property="religion" styleId="religion" styleClass="form-control" maxlength="200"/>
                                        <script type="text/javascript">
                                            document.getElementById("religion").setAttribute('placeholder', 'Enter Religion');
                                        </script>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <label class="form_label">Place of Birth</label>
                                        <html:text property="placeofbirth" styleId="placeofbirth" styleClass="form-control" maxlength="100"/>
                                        <script type="text/javascript">
                                            document.getElementById("placeofbirth").setAttribute('placeholder', 'Enter Place of Birth');
                                        </script>
                                    </div>
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-12">   
                                        <label class="form_label">Age</label>
                                        <html:text property="age" styleId="age" styleClass="form-control" onkeypress="return allowPositiveNumber1(event);"/>
                                        <script type="text/javascript">
                                            document.getElementById("age").setAttribute('readonly', 'true');
                                        </script>
                                    </div>
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                        <label class="form_label">Passport<span class="required">*</span></label>
                                        <html:select styleClass="form-select" property="passport">
                                            <html:option value="0">- Select -</html:option>
                                            <html:option value="1">Yes</html:option>
                                            <html:option value="2">No</html:option>
                                        </html:select>
                                    </div>
                                </div>
                                <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                    <div class="row">
                                        <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                            <label class="form_label">Pan Card<span class="required">*</span></label>
                                            <html:text property="pancard" styleId="pancard" styleClass="form-control text-uppercase" maxlength="10"/>
                                            <script type="text/javascript">
                                                document.getElementById("pancard").setAttribute('placeholder', 'Enter Pan Card');
                                            </script>
                                        </div>
                                        <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                            <label class="form_label">Pan File</label>
                                            <html:file property="panfile" styleId="upload2" styleClass="form-control" onchange="javascript: setClass('2');"/>
                                            <div class="w-100 w-sm-50">
                                                <a href="javascript:;" id="upload_link_2" class="attache_btn uploaded_img1">
                                                    <i class="fas fa-paperclip"></i> Attach
                                                </a>
                                            </div>
                                            <% if (!panFile.equals("")) {%><div class="down_prev"><a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=panFile%>');">Preview</a></div><% } %>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="section" id="mySection3">
                    <label class="section-label"  onclick="getshowhide('3');">
                        Contact Information 
                        <img id="img3" src="../assets/images/white-down-arrow.png" alt="Down Arrow"/>
                    </label>
                    <div id="div3" style="display: none">
                        <div class="row">
                            <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                <label class="form_label">Primary Contact No.<span class="required">*</span></label>
                                <div class="row">
                                    <div class="col-lg-3">
                                        <html:select property="code1Id" styleClass="form-select">
                                            <html:optionsCollection filter="false" property="countries" label="ddlLabel3" value="ddlLabel2">
                                            </html:optionsCollection>
                                        </html:select>
                                    </div>
                                    <div class="col-lg-9">
                                        <html:text property="contactno1" styleId="contactno1" styleClass="form-control" maxlength="20" onkeypress="return allowPositiveNumber1(event);"/>
                                        <script type="text/javascript">
                                            document.getElementById("contactno1").setAttribute('placeholder', 'Enter Contact No.');
                                        </script>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                <label class="form_label">Email ID</label>
                                <html:text property="emailId" styleId="emailId" styleClass="form-control" maxlength="100" onchange="javascript: checkEmailAjaxCandidate();"/>
                                <script type="text/javascript">
                                    document.getElementById("emailId").setAttribute('placeholder', 'Enter Email ID');
                                    document.getElementById("emailId").setAttribute('readonly', '');
                                </script>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="section" id="mySection4">
                    <label class="section-label" onclick="getshowhide('4');">
                        Address <img id="img4" src="../assets/images/white-down-arrow.png" alt="Down Arrow"/>
                    </label>
                    <div id="div4" style="display: none">
                        <div class="row">
                            <!-- Permanent Address -->
                            <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                <label class="bg-light p-2 add-label">Permanent Address<span class="required">*</span></label>
                                <html:text property="address1line1" styleId="address1line1" styleClass="form-control" maxlength="100"/>  
                                <script type="text/javascript">
                                    document.getElementById("address1line1").setAttribute('placeholder', 'Enter Address Line 1');
                                </script>
                                <html:text property="address1line2" styleId="address1line2" styleClass="form-control" maxlength="100"/>
                                <script type="text/javascript">
                                    document.getElementById("address1line2").setAttribute('placeholder', 'Enter Address Line 2');
                                </script>
                                <html:text property="address1line3" styleId="address1line3" styleClass="form-control" maxlength="100"/>
                                <script type="text/javascript">
                                    document.getElementById("address1line3").setAttribute('placeholder', 'Enter Address Line 3');
                                </script>
                                <html:text property="pinCode" styleId="pinCode" styleClass="form-control" maxlength="10"/>
                                <script type="text/javascript">
                                    document.getElementById("pinCode").setAttribute('placeholder', 'Enter Pincode');
                                </script>
                                <html:select property="countryId" styleId="countryId" styleClass="form-select" onchange="javascript: clearcity('1');" >
                                    <html:optionsCollection filter="false" property="countries" label="ddlLabel" value="ddlValue">
                                    </html:optionsCollection>
                                </html:select>
                                <html:select property="stateId" styleId="stateId" styleClass="form-select" >
                                    <html:optionsCollection filter="false" property="states" label="ddlLabel" value="ddlValue">
                                    </html:optionsCollection>
                                </html:select>
                                <html:hidden property="cityId" />
                                <html:text property="cityName" styleId="cityName" styleClass="form-control" maxlength="100" onblur="if (this.value == '') {
                                            document.forms[0].cityId.value = '0';
                                        }"/>
                                <script type="text/javascript">
                                    document.getElementById("cityName").setAttribute('placeholder', 'Enter City Name');
                                    document.getElementById("cityName").setAttribute('autocomplete', 'off');
                                </script>
                            </div>

                            <!-- Communication Address -->
                            <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                <label class="bg-light p-2 smDevice-column d-flex justify-content-between align-items-center add-label">Communication Address
                                    <span class="com-form-check">
                                        <html:checkbox property="sameAsPermanent" styleId="sameAsPermanent" value = "1" styleClass="form-check-input" onclick="getCommunicationAddress(this)"/>
                                        &nbsp;<label for="sameAsPermanent" class="form-check-label">Same as Permanent Address</label>
                                    </span>
                                </label>
                                <div id="dcommunication" <%if (sameaspaddress == 1) {%>style="display: none"<%}%>>
                                    <html:text property="address2line1" styleId="address2line1" styleClass="form-control" maxlength="100"/>  
                                    <script type="text/javascript">
                                        document.getElementById("address2line1").setAttribute('placeholder', 'Enter Address Line 1');
                                    </script>
                                    <html:text property="address2line2" styleId="address2line2" styleClass="form-control" maxlength="100"/> 
                                    <script type="text/javascript">
                                        document.getElementById("address2line2").setAttribute('placeholder', 'Enter Address Line 2');
                                    </script>
                                    <html:text property="address2line3" styleId="address2line3" styleClass="form-control" maxlength="100"/>  
                                    <script type="text/javascript">
                                        document.getElementById("address2line3").setAttribute('placeholder', 'Enter Address Line 3');
                                    </script>
                                    <html:text property="pinCode2" styleId="pinCode2" styleClass="form-control" maxlength="10"/>
                                    <script type="text/javascript">
                                        document.getElementById("pinCode2").setAttribute('placeholder', 'Enter Pincode');
                                    </script>
                                    <html:select property="countryId2" styleId="countryId2" styleClass="form-select" onchange="javascript: clearcity('2');" >
                                        <html:optionsCollection filter="false" property="countries" label="ddlLabel" value="ddlValue">
                                        </html:optionsCollection>
                                    </html:select>
                                    <html:select property="stateId2" styleId="stateId2" styleClass="form-select" >
                                        <html:optionsCollection filter="false" property="states2" label="ddlLabel" value="ddlValue">
                                        </html:optionsCollection>
                                    </html:select>
                                    <html:hidden property="cityId2" />
                                    <html:text property="cityName2" styleId="cityName2" styleClass="form-control" maxlength="100" onblur="if (this.value == '') {
                                                document.forms[0].cityId2.value = '0';
                                            }"/>
                                    <script type="text/javascript">
                                        document.getElementById("cityName2").setAttribute('placeholder', 'Enter City Name');
                                        document.getElementById("cityName2").setAttribute('autocomplete', 'off');
                                    </script>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="section" id="mySection5">
                    <label class="section-label" onclick="getshowhide('5');">
                        Position Applied For <img id="img5" src="../assets/images/white-down-arrow.png" alt="Down Arrow"/>
                    </label>
                    <div id="div5" style="display: none">
                        <div class="row">
                            <div class="col-md-6 col-sm-12">
                                <div class="row">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <label class="form_label">Asset Type<span class="required">*</span></label>
                                        <html:select property="assettypeId" styleId="assettypeddl" styleClass="form-select" onchange="javascript: setAssetPosition()">
                                            <html:optionsCollection filter="false" property="assettypes" label="ddlLabel" value="ddlValue">
                                            </html:optionsCollection>
                                        </html:select> 
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <label class="form_label">Position</label>
                                        <html:select property="positionId" styleId="positionddl" styleClass="form-select">
                                            <html:optionsCollection filter="false" property="positions" label="ddlLabel" value="ddlValue">
                                            </html:optionsCollection>
                                        </html:select> 
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 col-12">
                                <div class="row">
                                    <div class="col-12">
                                        <label class="form_label">Expected Salary</label>
                                    </div>
                                    <div class="col-lg-4 col-md-4 col-sm-6 col-6">

                                        <html:select property="ecurrencyId" styleClass="form-select">
                                            <html:optionsCollection filter="false" property="currencies" label="ddlLabel" value="ddlValue">
                                            </html:optionsCollection>
                                        </html:select>
                                    </div>
                                    <div class="col-lg-8 col-md-8 col-sm-6 col-6">

                                        <html:text property="expectedsalary" styleId="expectedsalary" styleClass="form-control text-left" maxlength="12" onkeypress="return allowPositiveNumber(event);" onfocus="if (this.value == '0') {
                                                    this.value = '';
                                                }" onblur="if (this.value == '') {
                                                            this.value = '0';
                                                        }"/>  
                                        <script type="text/javascript">
                                            document.getElementById("expectedsalary").setAttribute('placeholder', 'Enter Expected Salary');
                                        </script>
                                    </div>
                                    <div class="col-12">
                                        <label class="form_label">Last Drawn Salary</label>
                                    </div>
                                    <div class="col-lg-4 col-md-4 col-sm-6 col-6">

                                        <html:select property="lcurrencyId" styleClass="form-select">
                                            <html:optionsCollection filter="false" property="currencies" label="ddlLabel" value="ddlValue">
                                            </html:optionsCollection>
                                        </html:select>
                                    </div>
                                    <div class="col-lg-8 col-md-8 col-sm-6 col-6">

                                        <html:text property="lastdrawnsalary" styleId="lastdrawnsalary" styleClass="form-control text-left" maxlength="12" onkeypress="return allowPositiveNumber(event);" onfocus="if (this.value == '0') {
                                                    this.value = '';
                                                }" onblur="if (this.value == '') {
                                                            this.value = '0';
                                                        }"/>  
                                        <script type="text/javascript">
                                            document.getElementById("lastdrawnsalary").setAttribute('placeholder', 'Enter Last Drawn Salary');
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Education Section -->
                <div class="section" id="mySection6">
                    <label class="section-label" onclick="getshowhide('6');">
                        Education 
                        <img id="img6" src="../assets/images/white-down-arrow.png" alt="Down Arrow">
                    </label>
                    <div id="div6" style="display: none">
                        <div class="row">
                            <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                <label class="form_label">Highest Qualification<span class="required">*</span></label>
                                <html:select property="kindId" styleClass="form-select">
                                    <html:optionsCollection filter="false" property="qualificationtypes" label="ddlLabel" value="ddlValue">
                                    </html:optionsCollection>
                                </html:select>
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                <label class="form_label">Field of Study<span class="required">*</span></label>
                                <html:select property="degreeId" styleClass="form-select">
                                    <html:optionsCollection filter="false" property="degrees" label="ddlLabel" value="ddlValue">
                                    </html:optionsCollection>
                                </html:select>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Work Experience Section -->
                <div class="section" id="mySection7">
                    <label class="section-label" onclick="getshowhide('7');">
                        Work Experience 
                        <img id="img7" src="../assets/images/white-down-arrow.png" alt="Down Arrow">
                    </label>
                    <div id="div7" style="display: none">
                        <div class="row">
                            <div class="col-lg-6 col-md-6 col-sm-12 col-12 mt_10">
                                <div class="full_width">
                                    <div class="form-check permission-check" id="dFresher">
                                        <html:checkbox property="isFresher" styleClass="form-check-input" styleId="isFresher" value="1" onclick="checkFresher(this);"/>
                                        <span class="ml_10"><b>Is Fresher</b></span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-12 col-12 mt_10" id="subdiv7_1" <%if (isfresher == 1) {%>style="display: none"<%}%>>
                                <div class="full_width">
                                    <div class="form-check permission-check">
                                        <html:checkbox property="pastOCSemp" styleClass="form-check-input" styleId="pastOCSemp"/>
                                        <span class="ml_10"><b>Past OCS Employee</b></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="subdiv7_2" <%if (isfresher == 1) {%>style="display: none"<%}%>>
                            <div class="col-md-6 col-sm-12">
                                <div class="row">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <label class="form_label">Company Name<span class="required">*</span></label>
                                        <html:text property="companyname" styleId="companyname" styleClass="form-control" maxlength="100"/>
                                        <script type="text/javascript">
                                            document.getElementById("companyname").setAttribute('placeholder', 'Enter Company Name');
                                        </script>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <label class="form_label">Asset Name<span class="required">*</span></label>
                                        <html:text property="assetname" styleId="assetname" styleClass="form-control" maxlength="100"/>
                                        <script type="text/javascript">
                                            document.getElementById("assetname").setAttribute('placeholder', 'Enter Asset Name');
                                        </script>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <label class="form_label">Start Date<span class="required">*</span></label>
                                        <div class="input-daterange input-group">
                                            <html:text property="workstartdate" styleId="workstartdate" styleClass="form-control add-style wesl_dt date-add" />
                                            <script type="text/javascript">
                                                document.getElementById("workstartdate").setAttribute('placeholder', 'DD-MMM-YYYY');
                                            </script>                                                      
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-12">
                                <div class="row">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <label class="form_label">Asset Type<span class="required">*</span></label>
                                        <html:select property="expassettypeId" styleClass="form-select" onchange="javascript: setAssetPosition2();">
                                            <html:optionsCollection filter="false" property="assettypes" label="ddlLabel" value="ddlValue">
                                            </html:optionsCollection>
                                        </html:select>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <label class="form_label">Position<span class="required">*</span></label>
                                        <html:select property="exppositionId" styleId="positionId2" styleClass="form-select">
                                            <html:optionsCollection filter="false" property="positions" label="ddlLabel" value="ddlValue">
                                            </html:optionsCollection>
                                        </html:select>
                                    </div>
                                    <div class="col-lg-5 col-md-5 col-sm-12 col-12">
                                        <label class="form_label smDeviceD-none">&nbsp;</label>
                                        <div class="full_width">
                                            <div class="form-check permission-check">
                                                <html:checkbox property="currentworkingstatus" styleClass="form-check-input" styleId="currentworkingstatus" onclick="checkCurrent(this, 1);" />
                                                <span class="ml_10"><b>Is Current Employer</b></span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-7 col-md-7 col-sm-12 col-12">
                                        <label class="form_label">End Date<span class="required">*</span></label>
                                        <div class="input-daterange input-group">
                                            <html:text property="workenddate" styleId="workenddate" styleClass="form-control add-style wesl_dt date-add" />
                                            <script type="text/javascript">
                                                document.getElementById("workenddate").setAttribute('placeholder', 'DD-MMM-YYYY');
                                            </script>
                                        </div>
                                    </div>
                                </div>
                                <div class="action-buttons text-right mt_10"> 
                                    <button class="newbtn newbtn-save" onclick="addExperience();">Add Experience</button>
                                </div>
                            </div>

                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 web-device mt-4">
                                <div style="overflow-x: auto; margin-bottom: 20px;">
                                    <table class="table mb-0">
                                        <thead class="form-thead">
                                        <th width="18%" class="text-nowrap form-th">Company Name</th>
                                        <th width="17%" class="text-nowrap form-th">Asset Type</th>
                                        <th width="18%" class="text-nowrap form-th">Asset Name</th>
                                        <th width="8%" class="text-nowrap form-th">Position</th>
                                        <th width="9%" class="text-nowrap form-th">Start Date</th>
                                        <th width="9%" class="text-nowrap form-th">End Date</th>
                                        <th width="7%" class="text-nowrap form-th">Past OCS</th>
                                        <th width="7%" class="text-nowrap form-th">Is Current</th>
                                        <th width="7%" class="text-nowrap form-th">Action</th>
                                        </thead>
                                        <tbody id="exptbody">
                                            <%
                                                if (exp_size > 0) {
                                                    CandidateRegistrationInfo info = null;
                                                    String compName = "";
                                                    for (int i = 0; i < exp_size; i++) {
                                                        info = (CandidateRegistrationInfo) exp_list.get(i);
                                                        if (info != null && info.getExppositionId() > 0) {
                                                            compName = info.getCompanyname() != null ? info.getCompanyname() : "";
                                            %>
                                            <tr>
                                                <td><%=compName%></td>
                                                <td><%=(info.getExpassettype() != null ? info.getExpassettype() : "")%></td>
                                                <td><%=(info.getAssetname() != null ? info.getAssetname() : "")%></td>
                                                <td><%=(info.getExpposition() != null ? info.getExpposition() : "")%></td>
                                                <td><%=(info.getWorkstartdate() != null ? info.getWorkstartdate() : "")%></td>
                                                <td><%=(info.getWorkenddate() != null ? info.getWorkenddate() : "")%></td>
                                                <td><%=(info.getPastOCSemp() == 1 ? "Yes" : "No")%></td>
                                                <td><%=(info.getCurrentworkingstatus() == 1 ? "Yes" : "No")%></td>
                                                <td>
                                                    <div class='d-flex  mr-15'>
                                                        <a href="javascript:;" onclick="getEditExp('<%=compName%>', '<%=info.getExppositionId()%>');" ><img src="../assets/pnt-images/edit-icon.svg"/></a>&nbsp;
                                                        <a href="javascript:;" onclick="getRemoveExp('<%=compName%>', '<%=info.getExppositionId()%>');" ><img src="../assets/pnt-images/cancel-icon.svg"/></a>
                                                    </div>
                                                </td>
                                            </tr>
                                            <%}
                                                }
                                            } else {
                                            %>
                                            <tr><td colspan="9"><%= cr.getMainPath("record_not_found")%></td></tr>
                                                <%}%>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!--Mobile device-->           
                            <div class="mobile-device mt-2" id="expmobdiv">
                                <%
                                    if (exp_size > 0) {
                                        CandidateRegistrationInfo info = null;
                                        String compName = "";
                                        for (int i = 0; i < exp_size; i++) {
                                            info = (CandidateRegistrationInfo) exp_list.get(i);
                                            if (info != null && info.getExppositionId() > 0) {
                                                compName = info.getCompanyname() != null ? info.getCompanyname() : "";
                                %>
                                <div class="mob-Experience mb-2">
                                    <div class="row">
                                        <div class="d-flex justify-content-end">
                                            <div class="d-flex gap-2">
                                                <div><a href="javascript:;" onclick="getEditExp('<%=compName%>', '<%=info.getExppositionId()%>');" class="mr_15"><img src="../assets/pnt-images/edit-icon.svg"/></a></div>
                                                <div> <a href="javascript:;" onclick="getRemoveExp('<%=compName%>', '<%=info.getExppositionId()%>');" class="mr_15"><img src="../assets/pnt-images/cancel-icon.svg"/></a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12 mb-2">
                                            <div class="d-flex flex-column">
                                                <div>
                                                    <span class="gray"> Company Name</span>
                                                </div>
                                                <div>
                                                    <span class="black"><%=compName%></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12 mb-2">
                                            <div class="d-flex gap-2">
                                                <div class="col-6">
                                                    <div class="d-flex flex-column">
                                                        <div>
                                                            <span class="gray"> Asset Type</span>
                                                        </div>
                                                        <div>
                                                            <span class="black"><%=(info.getExpassettype() != null ? info.getExpassettype() : "")%></span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-6">
                                                    <div class="d-flex flex-column">
                                                        <div>
                                                            <span class="gray"> Asset Name</span>
                                                        </div>
                                                        <div>
                                                            <span class="black"><%=(info.getAssetname() != null ? info.getAssetname() : "")%></span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12 mb-2">
                                            <div class="d-flex flex-column">
                                                <div>
                                                    <span class="gray"> Position</span>
                                                </div>
                                                <div>
                                                    <span class="black"><%=info.getExpposition() != null ? info.getExpposition() : ""%></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12 mb-2">
                                            <div class="d-flex gap-2">
                                                <div class="col-6">
                                                    <div class="d-flex flex-column">
                                                        <div>
                                                            <span class="gray"> Start Date</span>
                                                        </div>
                                                        <div>
                                                            <span class="black"><%=(info.getWorkstartdate() != null ? info.getWorkstartdate() : "")%></span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-6">
                                                    <div class="d-flex flex-column">
                                                        <div>
                                                            <span class="gray"> End Date</span>
                                                        </div>
                                                        <div>
                                                            <span class="black"><%=(info.getWorkenddate() != null ? info.getWorkenddate() : "")%></span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12 mb-2">
                                            <div class="d-flex gap-2">
                                                <div class="col-6">
                                                    <div class="d-flex flex-column">
                                                        <div>
                                                            <span class="gray"> Past OCS</span>
                                                        </div>
                                                        <div>
                                                            <span class="black"><%=(info.getPastOCSemp() == 1 ? "Yes" : "No")%></span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-6">
                                                    <div class="d-flex flex-column">
                                                        <div>
                                                            <span class="gray"> Is Current</span>
                                                        </div>
                                                        <div>
                                                            <span class="black"><%=(info.getCurrentworkingstatus() == 1 ? "Yes" : "No")%></span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%}
                                    }
                                } else {
                                %>
                                <div class="mob-Experience mb-2">
                                    <div class="row">
                                        <div class="col-12 mb-2">
                                            <div class="d-flex flex-column">
                                                <div>
                                                    <span class="black"><%= cr.getMainPath("record_not_found")%></span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%}%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="section" id="mySection8">
                <label class="section-label" onclick="getshowhide('8');">Language
                    <img id="img8" src="../assets/images/white-down-arrow.png" alt="Down Arrow"/>
                </label>
                <div id="div8" style="display: none">
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                            <label class="form_label">English<span class="required">*</span></label>
                            <input type="hidden" name="languageId1" value="1" />
                            <span class="com-form-check permission-check">
                                <html:checkbox property="languageProf1" value="1" styleClass="form-check-input" styleId="speak1" onclick="checkLanguage1();"/>
                                &nbsp;<label for="speak1" class="form-check-label">Speak</label>
                            </span>
                            <%
                                if (langproftype.contains("1")) {
                            %>
                            <script>
                                document.getElementById("speak1").checked = true;
                            </script>
                            <%
                                }
                            %>
                            <span class="com-form-check permission-check">
                                <html:checkbox property="languageProf1" value="2" styleClass="form-check-input" styleId="read1" onclick="checkLanguage1();" />
                                &nbsp;<label for="read1" class="form-check-label">Read</label>
                            </span>
                            <%
                                if (langproftype.contains("2")) {
                            %>
                            <script>
                                document.getElementById("read1").checked = true;
                            </script>
                            <%
                                }
                            %>
                            <span class="com-form-check permission-check">
                                <html:checkbox property="languageProf1" value="3" styleClass="form-check-input" styleId="write1" onclick="checkLanguage1();" />
                                &nbsp;<label for="write1" class="form-check-label">Write</label>
                            </span>
                            <%
                                if (langproftype.contains("3")) {
                            %>
                            <script>
                                document.getElementById("write1").checked = true;
                            </script>
                            <%
                                }
                            %>
                            <span class="com-form-check permission-check">
                                <html:checkbox property="languageProf1" value="4" styleClass="form-check-input" styleId="dontKnow1" onclick="checkLanguage2();" />
                                &nbsp;<label for="dontKnow1" class="form-check-label">Don't Know</label>
                            </span>
                            <%
                                if (langproftype.contains("4")) {
                            %>
                            <script>
                                document.getElementById("dontKnow1").checked = true;
                            </script>
                            <%
                                }
                            %>
                        </div>

                        <label class="form_label">Language Choice 1</label>
                        <div class="col-lg-3 col-md-3 col-sm-12 col-12">
                            <html:select property="languageId2" styleClass="form-select">
                                <html:optionsCollection filter="false" property="languages" label="ddlLabel" value="ddlValue">
                                </html:optionsCollection>
                            </html:select>
                        </div>

                        <label class="form_label">Language Choice 2</label>
                        <div class="col-lg-3 col-md-3 col-sm-12 col-12">
                            <html:select property="languageId3" styleClass="form-select">
                                <html:optionsCollection filter="false" property="languages" label="ddlLabel" value="ddlValue">
                                </html:optionsCollection>
                            </html:select>
                        </div>

                        <label class="form_label">Language Choice 3</label>
                        <div class="col-lg-3 col-md-3 col-sm-12 col-12">
                            <html:select property="languageId4" styleClass="form-select">
                                <html:optionsCollection filter="false" property="languages" label="ddlLabel" value="ddlValue">
                                </html:optionsCollection>
                            </html:select>
                        </div>
                    </div>
                </div>
            </div>

            <div class="section" id="mySection9">
                <label class="section-label" onclick="getshowhide('9');">Offshore Mandatory Certifications
                    <img id="img9" src="../assets/images/white-down-arrow.png" alt="Down Arrow"/>
                </label>
                <div id="div9" style="display: none">
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                            <label class="form_label">Certificates<span class="required">*</span></label>
                            <html:select property="coursenameId" styleClass="form-select">
                                <html:optionsCollection filter="false" property="coursenames" label="ddlLabel" value="ddlValue">
                                </html:optionsCollection>
                            </html:select>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                            <label class="form_label">Date of Issue<span class="required">*</span></label>
                            <div class="input-daterange input-group">
                                <html:text property="dateofissue" styleId="dateofissue" styleClass="form-control add-style wesl_dt date-add" />
                                <script type="text/javascript">
                                    document.getElementById("dateofissue").setAttribute('placeholder', 'DD-MMM-YYYY');
                                </script>                 
                            </div>
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col2">
                            <div class="full_width align-center">
                                <div class="form-check permission-check">
                                    <html:checkbox property="isexpiry" value = "1" styleClass="form-check-input" styleId="isexpiry" onclick="checkCurrent(this, 2);" />
                                    <span class="ml_10"><b>No Expiry</b></span>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-12 col-12">
                            <label class="form_label">Expiry Date</label>
                            <div class="input-daterange input-group">
                                <html:text property="dateofexpiry" styleId="dateofexpiry" styleClass="form-control add-style wesl_dt1 date-add" />
                                <script type="text/javascript">
                                    document.getElementById("dateofexpiry").setAttribute('placeholder', 'DD-MMM-YYYY');
                                </script>
                            </div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                            <label class="form_label">Approved By</label>
                            <html:select property="approvedbyId" styleClass="form-select">
                                <html:optionsCollection filter="false" property="approvedbys" label="ddlLabel" value="ddlValue">
                                </html:optionsCollection>
                            </html:select>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                            <label class="form_label">Certificate Upload <span class="required">*</span></label>
                            <div class="col-lg-6 col-md-6 col-sm-6 col-6  ">
                                <input type="file" name="trainingcertfile" id="upload3" onchange="javascript: setClass('3');"/>
                                <a href="javascript:;" id="upload_link_3" class="attache_btn uploaded_img1"><i class="fas fa-paperclip"></i> Attach</a>
                            </div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                            <div class="action-buttons text-right mt_10"> 
                                <button class="newbtn newbtn-save" onclick="getAddCertificate();">Add Certificates</button>
                            </div>
                        </div>
                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 mt-4 web-device">
                            <div style="overflow-x: auto; margin-bottom: 20px;">
                                <table class="table mb-0">
                                    <thead class="form-thead">
                                    <th width="40%" class="text-nowrap form-th">Certificate Name</th>
                                    <th width="9%" class="text-nowrap form-th">Date of Issue</th>
                                    <th width="9%" class="text-nowrap form-th">Date of Expiry</th>
                                    <th width="7%" class="text-nowrap form-th">No Expiry</th>
                                    <th width="35%" class="text-nowrap form-th">Approved By</th>
                                    <th width="7%" class="text-nowrap form-th">Action</th>
                                    </thead>
                                    <tbody id="certtbody">
                                        <%
                                            if (cert_size > 0) {
                                                CandidateRegistrationInfo info = null;
                                                for (int i = 0; i < cert_size; i++) {
                                                    info = (CandidateRegistrationInfo) cert_list.get(i);
                                                    if (info != null) {
                                        %>
                                        <tr>
                                            <td><%=info.getCoursename() != null ? info.getCoursename() : ""%></td>
                                            <td><%=(info.getDateofissue() != null ? info.getDateofissue() : "")%></td>
                                            <td><%=(info.getDateofexpiry() != null && info.getIsexpiry() == 0 ? info.getDateofexpiry() : "")%></td>
                                            <td><%=(info.getIsexpiry() == 1 ? "Yes" : "No")%></td>
                                            <td><%=(info.getApprovedby() != null && !info.getApprovedby().equals("- Select -") ? info.getApprovedby() : "")%></td>
                                            <td>
                                                <div class='d-flex  mr-15'>
                                                    <a href="javascript:;" onclick="getEditCert('<%=info.getCoursenameId()%>');" ><img src="../assets/pnt-images/edit-icon.svg"/></a>&nbsp;
                                                    <a href="javascript:;" onclick="getRemoveCert('<%=info.getCoursenameId()%>');" ><img src="../assets/pnt-images/cancel-icon.svg"/></a>
                                                </div>
                                            </td>
                                        </tr>
                                        <%}
                                            }
                                        } else {
                                        %>
                                        <tr>
                                            <td colspan=6'><%=(cr.getMainPath("record_not_found"))%></td>
                                        </tr>
                                        <%}
                                        %>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="mobile-device mt-2" id="certmobdiv">
                            <%
                                if (cert_size > 0) {
                                    CandidateRegistrationInfo info = null;
                                    for (int i = 0; i < cert_size; i++) {
                                        info = (CandidateRegistrationInfo) cert_list.get(i);
                                        if (info != null) {
                            %>
                            <div class="mob-Experience mb-2">
                                <div class="row">
                                    <div class="d-flex justify-content-end">
                                        <div class="d-flex gap-2">
                                            <div>
                                                <a href="javascript:;" onclick="getEditCert('<%=info.getCoursenameId()%>');" class="mr_15"><img src="../assets/pnt-images/edit-icon.svg"/></a></div>
                                            <div> <a href="javascript:;" onclick="getRemoveCert('<%=info.getCoursenameId()%>');" class="mr_15"><img src="../assets/pnt-images/cancel-icon.svg"/></a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12 mb-2">
                                        <div class="d-flex flex-column">
                                            <div>
                                                <span class="gray"> Name Of certificate</span>
                                            </div>
                                            <div>
                                                <span class="black"><%=info.getCoursename() != null ? info.getCoursename() : ""%></span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12 mb-2">
                                        <div class="d-flex gap-2">
                                            <div class="col-6">
                                                <div class="d-flex flex-column">
                                                    <div>
                                                        <span class="gray">  Date Of Issue</span>
                                                    </div>
                                                    <div>
                                                        <span class="black"><%=(info.getDateofissue() != null ? info.getDateofissue() : "")%></span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-6">
                                                <div class="d-flex flex-column">
                                                    <div>
                                                        <span class="gray">Date of Expiry</span>
                                                    </div>
                                                    <div>
                                                        <span class="black"><%=(info.getDateofexpiry() != null && info.getIsexpiry() == 0 ? info.getDateofexpiry() : "")%></span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12 mb-2">
                                        <div class="d-flex gap-2">
                                            <div class="col-6">
                                                <div class="d-flex flex-column">
                                                    <div>
                                                        <span class="gray">No Expiry</span>
                                                    </div>
                                                    <div>
                                                        <span class="black"><%=(info.getIsexpiry() == 1 ? "Yes" : "No")%></span>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="col-6">
                                                <div class="d-flex flex-column">
                                                    <div>
                                                        <span class="gray">Approved By</span>
                                                    </div>
                                                    <div>
                                                        <span class="black"><%=(info.getApprovedby() != null && !info.getApprovedby().equals("- Select -") ? info.getApprovedby() : "")%></span>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <%}
                                }
                            } else {
                            %>
                            <div class="mob-Experience mb-2">
                                <div class="row">
                                    <div class="col-12 mb-2">
                                        <div class="d-flex flex-column">
                                            <div>
                                                <span class="black"><%= cr.getMainPath("record_not_found")%></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <%}
                            %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <footer class="form-footer">
            <div class="container-fluid">
                <div class="row">            
                    <div class="col-sm-6 text-left">
                        <img src="../assets/images/copy_right.png"/> HubC3
                    </div>
                    <div class="col-sm-6 text-right">
                        Powered by Planet NEXTgen Technologies
                    </div>
                </div>
            </div>
        </footer>
    </div>
    <!-- END layout-wrapper -->
    <div id="thank_you_modal" class="modal fade thank_you_modal blur_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close_modal_btn pull-right" onclick="getCloseRegistration();"><i class="ion ion-md-close"></i></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <h2>Thank You!</h2>
                            <center><img src="../assets/pnt-images/thank-you.png"></center>
                            <!--<p>You may now find this candidate in the</p>-->
                            <a href="javascript:;" onclick="getCloseRegistration();" class="msg_button" style="text-decoration: underline;">Close</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="drag_drop_modal" class="modal fade drag_drop_div" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="dropzone dropzone-file-area" id="my-dropzone" action="##">
                                <h3 class="sbold">
                                    <img src="../assets/images/drag_drop_upload.png"/>
                                    Drag and Drop <br/> <span>or browse your files</span>
                                </h3>
                                <div class="drop_list_save" id="dSave">
                                    <a href="javascript:void(0);" onclick="javascript: setClassRes();" data-bs-dismiss="modal">
                                        <img src="../assets/images/save.png"> Save</a>
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
    <script src="../assets/drop/jquery.min.js"></script>	
    <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
    <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
    <script src="../autofill/jquery-ui.min.js" type="text/javascript"></script> <!-- autofill-->
    <script src="../assets/drop/dropzone.min.js" type="text/javascript"></script>  
    <script src="../assets/drop/app.min.js" type="text/javascript"></script>
    <script src="../assets/js/bootstrap-select.min.js"></script>
    <script src="../assets/js/bootstrap-datepicker.min.js"></script>
    <script src="../assets/drop/form-dropzone.min.js" type="text/javascript"></script>
    <script src="/jxp/assets/js/sweetalert2.min.js"></script>
    <script>
                                        $(document).ready(function () {
                                            $('input[type="text"]').attr('autocomplete', 'off');
                                        });
    </script>
    <% if (thankyou.equals("yes")) {%>
    <script type="text/javascript">
        $(window).on('load', function () {
            $('#thank_you_modal').modal('show');
        });
    </script>
    <%}%>
    <script>
        $(function ()
        {
            $("#cityName").autocomplete({
                source: function (request, response) {
                    $.ajax({
                        url: "/jxp/ajax/candidateregistration/autofillcity.jsp",
                        type: 'post',
                        dataType: "json",
                        data: JSON.stringify({"search": request.term, "countryId": $("#countryId").val()}),
                        success: function (data) {
                            response(data);
                        }
                    });
                },
                select: function (event, ui) {
                    //console.log('onHover :: '+JSON.stringify(ui,null,2));					
                    $('#cityName').val(ui.item.label); // display the selected text
                    $('input[name="cityId"]').val(ui.item.value);
                    return false;
                },
                focus: function (event, ui)
                {
                    //console.log('onFocus :: '+JSON.stringify(ui,null,2));					
                    $('#cityName').val(ui.item.label); // display the selected text
                    return false;
                }
            });
            $("#cityName2").autocomplete({
                source: function (request, response) {
                    $.ajax({
                        url: "/jxp/ajax/candidateregistration/autofillcity.jsp",
                        type: 'post',
                        dataType: "json",
                        data: JSON.stringify({"search": request.term, "countryId": $("#countryId2").val()}),
                        success: function (data) {
                            response(data);
                        }
                    });
                },
                select: function (event, ui) {
                    $('#cityName2').val(ui.item.label);
                    $('input[name="cityId2"]').val(ui.item.value);
                    return false;
                },
                focus: function (event, ui)
                {
                    $('#cityName2').val(ui.item.label);
                    return false;
                }
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
        $(function () {
            $("#upload_link_2").on('click', function (e) {
                e.preventDefault();
                $("#upload2:hidden").trigger('click');
            });

        });
        $(function () {
            $("#upload_link_3").on('click', function (e) {
                e.preventDefault();
                $("#upload3:hidden").trigger('click');
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
                orientation: "bottom",
                endDate: "-1d"
            });

            $(".kt-selectpicker").selectpicker();
            $(".wesl_dt1").datepicker({
                todayHighlight: !0,
                format: "dd-M-yyyy",
                autoclose: "true",
                orientation: "bottom"
            });
        });
    </script>
    <script>
        Dropzone.autoDiscover = false;
        var val = "", localfname = "";
        $("#my-dropzone").dropzone({
            addRemoveLinks: true,
            maxFilesize: 5,
            maxFiles: 50,
            parallelUploads: 50,
            createImageThumbnails: !0,
            acceptedFiles: ".doc,.docx,.pdf,.png,.jpg,.jpeg",
            init: function () {
                this.on("addedfile", function (file) {
                    if (file.name != "")
                    {
                        if (!(file.name).match(/(\.(docx)|(doc)|(pdf)|(jpeg)|(jpg)|(png))$/i))
                        {
                            alert("Only .pdf, .doc, .docx, .jpeg, .jpg, .png are allowed.");
                            return false;
                        }
                    }
                    var n = Dropzone.createElement("<a href='javascript:' class='trash_drop_list'><img src='../assets/images/trash.png'/></a>"),
                            t = this;
                    n.addEventListener("click", function (n) {
                        n.preventDefault(),
                                n.stopPropagation(),
                                t.removeFile(file)
                    }),
                            file.previewElement.appendChild(n)

                    var reader = new FileReader();
                    reader.onload = function (event) {
                        var base64String = event.target.result;
                        if (val == "")
                        {
                            val += base64String;
                            localfname += file.name;
                        } else
                        {
                            val += ("@#@" + base64String);
                            localfname += "@#@" + file.name;
                        }
                        document.candidateRegistrationForm.fname.value = val;
                        document.candidateRegistrationForm.localFile.value = localfname;
                    };
                    reader.readAsDataURL(file);

                });
                this.on("error", function (file, message) {
                    swal.fire(message);
                    this.removeFile(file);
                    var reader = new FileReader();
                    reader.onload = function (event) {
                        var base64String = event.target.result;
                        var val1 = document.candidateRegistrationForm.fname.value;
                        var fval = val1.replace("@#@" + base64String + "@#@", "");
                        fval = fval.replace("@#@" + base64String, "");
                        fval = fval.replace(base64String + "@#@", "");
                        fval = fval.replace(base64String, "");
                        val = fval;
                        document.candidateRegistrationForm.fname.value = fval;

                        var localFileval = document.candidateRegistrationForm.localFile.value;
                        var localFile = localFileval.replace("@#@" + file.name + "@#@", "");
                        localFile = localFile.replace("@#@" + file.name, "");
                        localFile = localFile.replace(file.name + "@#@", "");
                        localFile = localFile.replace(file.name, "");
                        localfname = localFile;
                        document.candidateRegistrationForm.localFile.value = localfname;
                    };
                    reader.readAsDataURL(e);
                });
                this.on("removedfile", function (file)
                {
                    var reader = new FileReader();
                    reader.onload = function (event) {
                        var base64String = event.target.result;
                        var val1 = document.candidateRegistrationForm.fname.value;
                        var fval = val1.replace("@#@" + base64String + "@#@", "");
                        fval = fval.replace("@#@" + base64String, "");
                        fval = fval.replace(base64String + "@#@", "");
                        fval = fval.replace(base64String, "");
                        val = fval;
                        document.candidateRegistrationForm.fname.value = fval;

                        var localFileval = document.candidateRegistrationForm.localFile.value;
                        var localFile = localFileval.replace("@#@" + file.name + "@#@", "");
                        localFile = localFile.replace("@#@" + file.name, "");
                        localFile = localFile.replace(file.name + "@#@", "");
                        localFile = localFile.replace(file.name, "");
                        localfname = localFile;
                        document.candidateRegistrationForm.localFile.value = localfname;
                    };
                    reader.readAsDataURL(file);
                }
                );
            },
            dictDefaultMessage: '<span class="text-center"><span class="font-lg visible-xs-block visible-sm-block visible-lg-block"><span class="font-lg"><i class="fa fa-caret-right text-danger"></i> Drop files <span class="font-xs">to upload</span></span><span>&nbsp&nbsp<h4 class="display-inline"> (Or Click)</h4></span>',
            dictResponseError: 'Error uploading file!'
        });
    </script>
    <script type="text/javascript">
        document.addEventListener("DOMContentLoaded", function () {
            document.getElementById("upload3").addEventListener("change", function (event) {
                var fileInput = event.target;
                if (fileInput.files.length === 0)
                    return; // No file selected

                var file = fileInput.files[0];
                var reader = new FileReader();

                reader.onload = function (e) {
                    let base64Data = e.target.result;
                    let form = document.forms["candidateRegistrationForm"];

                    if (form) {
                        form.tempfname.value = base64Data;
                        form.templocalFile.value = file.name;
                    } else {
                        console.error("Form not found: candidateRegistrationForm");
                    }
                };

                reader.onerror = function () {
                    console.error("Error reading file");
                };

                reader.readAsDataURL(file);
            });
        });
    </script>
    <script type="text/javascript">
        function setClassRes()
        {
            if (trim(document.forms[0].fname.value) != "")
                document.getElementById("upload_link_resume").className = "attache_btn uploaded_ocs_img";
            else
                document.getElementById("upload_link_resume").className = "attache_btn";
        }
        function setval()
        {
            if (eval(document.forms[0].expectedsalary.value) <= 0)
                document.forms[0].expectedsalary.value = "";
            if (eval(document.forms[0].age.value) <= 0)
                document.forms[0].age.value = "";
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
    </script>
</html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>