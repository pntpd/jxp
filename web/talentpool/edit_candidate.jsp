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
            int mtp = 2, submtp = 4, ctp = 1;
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
            ArrayList list = new ArrayList();
            if(request.getSession().getAttribute("MODULEPER_LIST") != null)
                list = (ArrayList)request.getSession().getAttribute("MODULEPER_LIST");
        String photoc = "";
        if(request.getAttribute("PHOTO") != null)
        {
            photoc = (String)request.getAttribute("PHOTO"); 
            request.removeAttribute("PHOTO");
        }
        if(photoc != null && !photoc.equals(""))
                photoc = talentpool.getMainPath("view_candidate_file") + photoc;
        else
            photoc = "../assets/images/empty_user.png";
        String filecount = "";
         if(request.getAttribute("FILECOUNT") != null)
        {
            filecount = (String)request.getAttribute("FILECOUNT"); 
            request.removeAttribute("FILECOUNT");
        }
        String ratetype = "";
        if(session.getAttribute("RATETYPE") != null)
        {
            ratetype = (String) session.getAttribute("RATETYPE"); 
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
        <link href="../assets/drop/dropzone.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="../autofill/jquery-ui.min.css" />  <!-- Autofill-->
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/talentpool.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="currentDate"/>
        <html:hidden property="fname"/>
        <html:hidden property="localFile" />
        <html:hidden property="positionIdhidden"/>
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
                        <div class="col-md-12 col-xl-12 tab_head_area ">
                            <%@include file ="../talentpooltab.jsp"%>
                        </div>
                    </div>
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background tab2_body">
                                    <div class="row">
                                        <html:hidden property="photofilehidden" />
                                        <html:hidden property="resumefilehidden" />                                        
                                        <div class="col-lg-12">
                                            <% if(!message.equals("")) {%><div class="sbold <%=clsmessage%>">
                                                <%=message%>
                                            </div><% } %>
                                            <div class="tab-content">
                                                <div class="tab-pane active">
                                                    <div class="">
                                                        <div class="row m_30">
                                                            <div class="col-lg-2 col-md-2 col-sm-2 col-12">
                                                                <div class="row  mt_30">
                                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                                        <div class="user_photo pic_photo">
                                                                            <img src="../assets/images/user.png">
                                                                            <div class="upload_file">
                                                                                <html:file property="photofile" styleId="upload1" onchange="javascript: setCandidateClass('1');"/>
                                                                                <img id="photo_profile_id" src="<%=photoc%>" />
                                                                                <% if(photoc.contains("empty_user")) {%><span>Display Picture <br/>(optional)</span><% } %>
                                                                                <a id="upload_link_1" class="uploadphotoced_img1 d-none1" href="javascript:;" >
                                                                                    <img src="../assets/images/upload.png"> 
                                                                                </a>
                                                                                <a href="javascript:;" id="upload_link_edit" class="profile_edit d-none"><i class="ion ion-md-create"></i></a>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-10 col-md-8 col-sm-8 col-12 flex-end align-items-end">
                                                                <div class="row">
                                                                    <div class="col-lg-3 col-md-8 col-sm-8 col-12">
                                                                        <label class="form_label">First Name<span class="required">*</span></label>
                                                                        <html:text property="firstname" styleId="firstname" styleClass="form-control" maxlength="100"/>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("firstname").setAttribute('placeholder', '');
                                                                        </script>
                                                                    </div>
                                                                    <div class="col-lg-3 col-md-4 col-sm-4 col-4">
                                                                        <label class="form_label">Middle Name</label>
                                                                        <html:text property="middlename" styleId="middlename" styleClass="form-control" maxlength="100"/>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("middlename").setAttribute('placeholder', '');
                                                                        </script> 
                                                                    </div>
                                                                    <div class="col-lg-3 col-md-4 col-sm-4 col-4">
                                                                        <label class="form_label">Last Name<span class="required">*</span></label>
                                                                        <html:text property="lastname" styleId="lastname" styleClass="form-control" maxlength="100"/>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("lastname").setAttribute('placeholder', '');
                                                                        </script>
                                                                    </div>
                                                                    <div class="col-lg-3 col-md-3 col-sm-6 col-12 text-right">
                                                                        <label class="form_label">Resume (5MB) (.pdf/.docx/ .jpeg/ .jpg /.png)<span class="required">*</span></label>
                                                                        <a href="javascript:;" data-bs-toggle="modal" id="upload_link_resume" data-bs-target="#drag_drop_modal" class="attache_btn"><i class="fas fa-paperclip"></i> Attachment <span class="attach_number"> <%=filecount%></span></a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Date of Birth<span class="required">*</span></label>
                                                                <div class="input-daterange input-group">
                                                                    <html:text property="dob" styleId="dob" styleClass="form-control add-style wesl_dt date-add" />
                                                                    <script type="text/javascript">
                                                                        document.getElementById("dob").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                                    </script>
                                                                </div>
                                                            </div>
                                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                        <label class="form_label">Age</label>
                                                                        <html:text property="age" styleId="age" styleClass="form-control" maxlength="3" onkeypress="return allowPositiveNumber1(event);"/>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("age").setAttribute('placeholder', '');
                                                                        </script>
                                                                    </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Place of Birth</label>
                                                                <html:text property="placeofbirth" styleId="placeofbirth" styleClass="form-control" maxlength="100"/>
                                                                <script type="text/javascript">
                                                                    document.getElementById("placeofbirth").setAttribute('placeholder', '');
                                                                </script>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Gender<span class="required">*</span></label>
                                                                <html:select styleClass="form-select" property="gender">
                                                                    <html:option value="Gender">- Select -</html:option>
                                                                    <html:option value="Male">Male</html:option>
                                                                    <html:option value="Female">Female</html:option>
                                                                    <html:option value="Others">Others</html:option>
                                                                </html:select>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Marital Status<span class="required">*</span></label>
                                                                <html:select property="maritalstatusId" styleClass="form-select">
                                                                    <html:optionsCollection filter="false" property="maritalstatuses" label="ddlLabel" value="ddlValue">
                                                                    </html:optionsCollection>
                                                                </html:select>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Country<span class="required">*</span></label>
                                                                <html:select property="countryId" styleId="countryId" styleClass="form-select" onchange="javascript: clearcity();" >
                                                                    <html:optionsCollection filter="false" property="countries" label="ddlLabel" value="ddlValue">
                                                                    </html:optionsCollection>
                                                                </html:select>
                                                            </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <div class="row flex-end align-items-end">
                                                                        <div class="col-lg-10 col-md-12 col-sm-12 col-12 form_group">
                                                                            <label class="form_label">State</label>
                                                                            <html:select property="stateId" styleId="stateId" styleClass="form-select" >
                                                                                <html:optionsCollection filter="false" property="states" label="ddlLabel" value="ddlValue">
                                                                                </html:optionsCollection>
                                                                            </html:select>
                                                                        </div>
                                                                        <div class="col-lg-2 col-md-12 col-sm-12 col-12 form_group pd_left_null">
                                                                            <a href="javascript:;" onclick="javascript: addtomaster('4')" class="add_btn" data-bs-toggle="modal" data-bs-target="#relation_modal"><i class="mdi mdi-plus"></i></a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <div class="row flex-end align-items-end">
                                                                    <div class="col-lg-10 col-md-12 col-sm-12 col-12 form_group">
                                                                        <label class="form_label">City<span class="required">*</span></label>
                                                                        <html:hidden property="cityId" />
                                                                        <html:text property="cityName" styleId="cityName" styleClass="form-control" maxlength="100" onblur="if (this.value == '') {
                                                                                    document.forms[0].cityId.value = '0';
                                                                                }"/>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("cityName").setAttribute('placeholder', '');
                                                                            document.getElementById("cityName").setAttribute('autocomplete', 'off');
                                                                        </script>
                                                                    </div>
                                                                    <div class="col-lg-2 col-md-12 col-sm-12 col-12 form_group pd_left_null">
                                                                        <a href="javascript:;" onclick="javascript: addtomaster('3')" class="add_btn" data-bs-toggle="modal" data-bs-target="#relation_modal"><i class="mdi mdi-plus"></i></a>
                                                                    </div>
                                                                </div>                                                                       
                                                            </div>                                                                        
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                               <label class="form_label">Pin Code</label>
                                                               <html:text property="pinCode" styleId="pinCode" styleClass="form-control" maxlength="10" onkeypress="return allowPositiveNumber1(event);"/>
                                                               <script type="text/javascript">
                                                                   document.getElementById("pinCode").setAttribute('placeholder', '');
                                                               </script>
                                                           </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Nationality<span class="required">*</span></label>
                                                                <html:select property="nationalityId" styleClass="form-select">
                                                                    <html:optionsCollection filter="false" property="countries" label="ddlLabel1" value="ddlValue">
                                                                    </html:optionsCollection>
                                                                </html:select>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Religion</label>
                                                                <html:text property="religion" styleId="religion" styleClass="form-control" maxlength="200"/>
                                                                <script type="text/javascript">
                                                                    document.getElementById("religion").setAttribute('placeholder', '');
                                                                </script>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Email ID<span class="required">*</span></label>
                                                                <html:text property="emailId" styleId="emailId" styleClass="form-control" maxlength="100" onchange="javascript: checkEmailAjaxCandidate();"/>
                                                                <script type="text/javascript">
                                                                    document.getElementById("emailId").setAttribute('placeholder', '');
                                                                </script>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Primary Contact No.<span class="required">*</span></label>
                                                                <div class="row">
                                                                    <div class="col-lg-4">
                                                                        <html:select property="code1Id" styleClass="form-select">
                                                                            <html:optionsCollection filter="false" property="countries" label="ddlLabel3" value="ddlLabel2">
                                                                            </html:optionsCollection>
                                                                        </html:select>
                                                                    </div>
                                                                    <div class="col-lg-8">
                                                                        <html:text property="contactno1" styleId="contactno1" styleClass="form-control text-center" maxlength="20" onkeypress="return allowPositiveNumber1(event);"/>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("contactno1").setAttribute('placeholder', '');
                                                                        </script>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Secondary Contact No.</label>
                                                                <div class="row">
                                                                    <div class="col-lg-4">
                                                                        <html:select property="code2Id" styleClass="form-select">
                                                                            <html:optionsCollection filter="false" property="countries" label="ddlLabel3" value="ddlLabel2">
                                                                            </html:optionsCollection>
                                                                        </html:select>
                                                                    </div>
                                                                    <div class="col-lg-8">
                                                                        <html:text property="contactno2" styleId="contactno2" styleClass="form-control text-center" maxlength="20" onkeypress="return allowPositiveNumber1(event);"/>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("contactno2").setAttribute('placeholder', '');
                                                                        </script>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Additional Contact No.</label>
                                                                <div class="row">
                                                                    <div class="col-lg-4">
                                                                        <html:select property="code3Id" styleClass="form-select">
                                                                            <html:optionsCollection filter="false" property="countries" label="ddlLabel3" value="ddlLabel2">
                                                                            </html:optionsCollection>
                                                                        </html:select>
                                                                    </div>
                                                                    <div class="col-lg-8">
                                                                        <html:text property="contactno3" styleId="contactno3" styleClass="form-control text-center" maxlength="20" onkeypress="return allowPositiveNumber1(event);"/>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("contactno3").setAttribute('placeholder', '');
                                                                        </script>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Emergency Contact No. 1<span class="required">*</span></label>
                                                                <div class="row">
                                                                    <div class="col-lg-4">
                                                                        <html:select property="ecode1Id" styleClass="form-select">
                                                                            <html:optionsCollection filter="false" property="countries" label="ddlLabel3" value="ddlLabel2">
                                                                            </html:optionsCollection>
                                                                        </html:select>
                                                                    </div>
                                                                    <div class="col-lg-8">
                                                                        <html:text property="econtactno1" styleId="econtactno1" styleClass="form-control text-center" maxlength="20" onkeypress="return allowPositiveNumber1(event);"/>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("econtactno1").setAttribute('placeholder', '');
                                                                        </script>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Emergency Contact No. 2</label>
                                                                <div class="row">
                                                                    <div class="col-lg-4">
                                                                        <html:select property="ecode2Id" styleClass="form-select">
                                                                            <html:optionsCollection filter="false" property="countries" label="ddlLabel3" value="ddlLabel2">
                                                                            </html:optionsCollection>
                                                                        </html:select>
                                                                    </div>
                                                                    <div class="col-lg-8">
                                                                        <html:text property="econtactno2" styleId="econtactno2" styleClass="form-control text-center" maxlength="20" onkeypress="return allowPositiveNumber1(event);"/>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("econtactno2").setAttribute('placeholder', '');
                                                                        </script>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Next of Kin</label>
                                                                <html:text property="nextofkin" styleId="nextofkin" styleClass="form-control" maxlength="100"/>
                                                                <script type="text/javascript">
                                                                    document.getElementById("nextofkin").setAttribute('placeholder', '');
                                                                </script>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <div class="row flex-end align-items-end">
                                                                    <div class="col-lg-10 col-md-12 col-sm-12 col-12 form_group">
                                                                        <label class="form_label">Relation</label>
                                                                        <html:select property="relationId" styleClass="form-select" styleId="relationdiv">
                                                                            <html:optionsCollection filter="false" property="relations" label="ddlLabel" value="ddlValue">
                                                                            </html:optionsCollection>
                                                                        </html:select>
                                                                    </div>
                                                                    <div class="col-lg-2 col-md-12 col-sm-12 col-12 form_group pd_left_null">
                                                                        <a href="javascript:;" onclick="javascript: addtomaster('1')" class="add_btn" data-bs-toggle="modal" data-bs-target="#relation_modal"><i class="mdi mdi-plus"></i></a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>	
                                                        <div class="row">	
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4">
                                                                <div class="row">
                                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                                        <label class="form_label">Permanent Address<span class="required">*</span></label>
                                                                        <html:text property="address1line1" styleId="address1line1" styleClass="form-control m_15" maxlength="100"/>                              
                                                                        <html:text property="address1line2" styleId="address1line2" styleClass="form-control m_15" maxlength="100"/>
                                                                        <html:text property="address1line3" styleId="address1line3" styleClass="form-control m_15" maxlength="100"/>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4">
                                                                <div class="row">
                                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                                        <label class="form_label">Communication Address</label>
                                                                        <html:text property="address2line1" styleId="address2line1" styleClass="form-control m_15" maxlength="100"/>                                                      
                                                                        <html:text property="address2line2" styleId="address2line2" styleClass="form-control m_15" maxlength="100"/>                               
                                                                        <html:text property="address2line3" styleId="address2line3" styleClass="form-control m_15" maxlength="100"/>                                                        
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4">
                                                                <div class="row">
                                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                                        <label class="form_label">Asset Type<span class="required">*</span></label>
                                                                        <html:select property="assettypeId" styleId="assettypeddl" styleClass="form-select" onchange="javascript: setAssetPosition();">
                                                                            <html:optionsCollection filter="false" property="assettypes" label="ddlLabel" value="ddlValue">
                                                                            </html:optionsCollection>
                                                                        </html:select>
                                                                        <div class="textfield_mg">
                                                                            <label class="form_label">Employee ID</label>
                                                                            <html:text property="employeeid" styleId="employeeid" styleClass="form-control m_15" maxlength="100"/>                                                        
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">	
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                                <label class="form_label">Primary Position<span class="required">*</span></label>
                                                                <div class="row">
                                                                    <div class="col-lg-10 col-md-10 col-sm-10 col-10">
                                                                        <div class="input-group flex_div">
                                                                            <a href="javascript: setPosition();" class="input-group-text refresh_btn"><i class="ion ion-md-refresh"></i></a>
                                                                            <html:select property="positionId" styleId="positionddl" styleClass="form-select" onchange="javascript: setAssetPosition2();">
                                                                                <html:optionsCollection filter="false" property="positions" label="ddlLabel" value="ddlValue">
                                                                                </html:optionsCollection>
                                                                            </html:select>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-lg-2 col-md-12 col-sm-12 col-12 form_group pd_left_null">
                                                                        <a href="../position/PositionAction.do?doAdd=yes" target="_blank" class="add_btn"><i class="mdi mdi-plus"></i></a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                                <div class="row flex-end align-items-end">
                                                                    <div class="col-lg-10 col-md-12 col-sm-12 col-12 form_group">
                                                                        <label class="form_label">Preferred Department</label>
                                                                        <html:select property="departmentId" styleClass="form-select" styleId='departmentdiv'>
                                                                            <html:optionsCollection filter="false" property="departments" label="ddlLabel" value="ddlValue">
                                                                            </html:optionsCollection>
                                                                        </html:select>
                                                                    </div>
                                                                    <div class="col-lg-2 col-md-12 col-sm-12 col-12 form_group pd_left_null">
                                                                        <a href="javascript:;" onclick="javascript: addtomaster('2')" class="add_btn" data-bs-toggle="modal" data-bs-target="#relation_modal"><i class="mdi mdi-plus"></i></a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Expected Salary</label>
                                                                <div class="row">
                                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4">
                                                                        <html:select property="currencyId" styleClass="form-select">
                                                                            <html:optionsCollection filter="false" property="currencies" label="ddlLabel" value="ddlValue">
                                                                            </html:optionsCollection>
                                                                        </html:select>
                                                                    </div>
                                                                    <div class="col-lg-8 col-md-8 col-sm-8 col-4">
                                                                        <html:text property="expectedsalary" styleId="expectedsalary" styleClass="form-control text-right" maxlength="12"/>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("expectedsalary").setAttribute('placeholder', '');
                                                                        </script>
                                                                    </div>                                                                                                                                        
                                                                </div>
                                                            </div>
                                                            <% if(ratetype != null && !ratetype.equals("")) {%>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Applying Job For</label>
                                                                <html:select styleClass="form-select" property="applytype">
                                                                    <html:option value="-1">- Select -</html:option>
                                                                    <html:option value="1">Marine</html:option>
                                                                    <html:option value="2">Oil and Gas</html:option>
                                                                </html:select>
                                                            </div>
                                                            <% } %>                                                                    
                                                            </div> 
                                                            <div class="row">	
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                                    <label class="form_label">Secondary Position</label>
                                                                    <div class="row">
                                                                        <div class="col-lg-10 col-md-10 col-sm-10 col-10">
                                                                            <div class="input-group flex_div">
                                                                                <a href="javascript: setPosition();" class="input-group-text refresh_btn"><i class="ion ion-md-refresh"></i></a>
                                                                                <html:select property="positionId2" styleId="positionId2" styleClass="form-select" >
                                                                                    <html:optionsCollection filter="false" property="positions2" label="ddlLabel" value="ddlValue">
                                                                                    </html:optionsCollection>
                                                                                </html:select>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-lg-2 col-md-12 col-sm-12 col-12 form_group pd_left_null">
                                                                            <a href="../position/PositionAction.do?doAdd=yes" target="_blank" class="add_btn"><i class="mdi mdi-plus"></i></a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                                    <label class="form_label">Travel Days</label>
                                                                    <html:text property="travelDays" styleId="travelDays" styleClass="form-control" maxlength="2" onkeypress="return allowPositiveNumber1(event);"/>                                                        
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                                    <label class="form_label">Nearest International Airport</label>
                                                                    <html:text property="airport1" styleId="airport1" styleClass="form-control text-left" maxlength="100"/>
                                                                    <script type="text/javascript">
                                                                        document.getElementById("airport1").setAttribute('placeholder', '');
                                                                    </script>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                                    <label class="form_label">Nearest Domestic Airport</label>
                                                                    <html:text property="airport2" styleId="airport2" styleClass="form-control text-left" maxlength="100"/>
                                                                    <script type="text/javascript">
                                                                        document.getElementById("airport2").setAttribute('placeholder', '');
                                                                    </script>
                                                                </div>                                                                
                                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                                        <label class="form_label">Professional Profile</label>
                                                                        <html:textarea property="profile" rows="6" styleId="profile" styleClass="form-control"></html:textarea>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("profile").setAttribute('placeholder', '');
                                                                            document.getElementById("profile").setAttribute('maxlength', '1000');
                                                                        </script>
                                                                    </div>                                                                
                                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                                        <label class="form_label">Strengths and Skills</label>
                                                                        <html:textarea property="skill1" rows="6" styleId="skill1" styleClass="form-control"></html:textarea>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("skill1").setAttribute('placeholder', '');
                                                                            document.getElementById("skill1").setAttribute('maxlength', '2000');
                                                                        </script>
                                                                    </div>                                                                
                                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                                        <label class="form_label">Additional Skills</label>
                                                                        <html:textarea property="skill2" rows="6" styleId="skill2" styleClass="form-control"></html:textarea>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("skill2").setAttribute('placeholder', '');
                                                                            document.getElementById("skill2").setAttribute('maxlength', '2000');
                                                                        </script>
                                                                    </div>                                                                
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                                            <a href="javascript:submitForm();" class="save_btn"><img src="../assets/images/save.png"/> Save</a>                                                            
                                                            <a href="javascript: view1();" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>                                                                
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div> 
                    </div> 
                </div>
            </div>
        <!-- END layout-wrapper -->
        <%@include file ="../footer.jsp"%>
        <div id="relation_modal" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <input type='hidden' name='mtype' />
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id='maddid'>Relation</h4> 
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="row">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <label class="form_label">Name</label>
                                        <input class="form-control" placeholder="" name='mname' maxlength="100"/>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center"><a href="javascript:addtomasterajax();" class="save_button mt_15"><img src="../assets/images/save.png"> Save</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="view_resume" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-5">
                                <form action="../assets/drop/upload.php" class="dropzone dropzone-file-area" id="my-dropzone">
                                    <h3 class="sbold">Drop files here or click to upload</h3>
                                </form>
                            </div>
                            <div class="col-lg-7">
                                <iframe class="doc" src="../assets/pdf/1.pdf"></iframe>                                 
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
                                    <div class="drop_list_save" id="dSave"><a href="javascript:void(0);" onclick="javascript: setClassRes();" data-bs-dismiss="modal"><img src="../assets/images/save.png"> Save</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- JAVASCRIPT -->
        <script src="../assets/drop/jquery.min.js"></script>	
        <script src="../autofill/jquery-ui.min.js" type="text/javascript"></script> <!-- autofill-->
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/drop/dropzone.min.js" type="text/javascript"></script>  
        <script src="../assets/drop/app.min.js" type="text/javascript"></script>
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="../assets/drop/form-dropzone.min.js" type="text/javascript"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>

        <script>
            $(function ()
            {
                $("#cityName").autocomplete({
                    source: function (request, response) {
                        $.ajax({
                            url: "/jxp/ajax/client/autofillcity.jsp",
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
            jQuery(document).ready(function () {
                $(".kt-selectpicker").selectpicker();
                $(".wesl_dt").datepicker({
                    todayHighlight: !0,
                    format: "dd-M-yyyy",
                    autoclose: "true",
                    orientation: "bottom",
                    endDate: "-1d"
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
                               Swal.fire("Only .pdf, .doc, .docx, .jpeg, .jpg, .png are allowed.");
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
                            }
                            else
                            {
                                val += ("@#@" + base64String);
                                localfname += ("@#@" +file.name);
                            }
                            document.talentpoolForm.fname.value = val;
                            document.talentpoolForm.localFile.value = localfname;
                        };
                        reader.readAsDataURL(file);

                    });
                    this.on("error", function (file, message) {
                        swal.fire(message);
                        this.removeFile(file);
                        var reader = new FileReader();
                        reader.onload = function (event) {
                            var base64String = event.target.result;
                            var val1 = document.talentpoolForm.fname.value;
                            var fval = val1.replace("@#@" + base64String + "@#@", "");
                            fval = fval.replace("@#@" + base64String, "");
                            fval = fval.replace(base64String + "@#@", "");
                            fval = fval.replace(base64String, "");
                            val = fval;                            
                            document.talentpoolForm.fname.value = fval;
                            
                            var localFileval = document.talentpoolForm.localFile.value;
                            var localFile = localFileval.replace("@#@" + file.name + "@#@", "");
                            localFile = localFile.replace("@#@" + file.name, "");
                            localFile = localFile.replace(file.name + "@#@", "");
                            localFile = localFile.replace(file.name, "");
                            localfname = localFile;
                            document.talentpoolForm.localFile.value = localfname;
                        };
                        reader.readAsDataURL(e);
                    });
                    this.on("removedfile", function (file)
                    {
                        var reader = new FileReader();
                        reader.onload = function (event) {
                            var base64String = event.target.result;
                            var val1 = document.talentpoolForm.fname.value;
                            var fval = val1.replace("@#@" + base64String + "@#@", "");
                            fval = fval.replace("@#@" + base64String, "");
                            fval = fval.replace(base64String + "@#@", "");
                            fval = fval.replace(base64String, "");
                            val = fval;
                            document.talentpoolForm.fname.value = fval;
                            
                            var localFileval = document.talentpoolForm.localFile.value;
                            var localFile = localFileval.replace("@#@" + file.name + "@#@", "");
                            localFile = localFile.replace("@#@" + file.name, "");
                            localFile = localFile.replace(file.name + "@#@", "");
                            localFile = localFile.replace(file.name, "");
                            localfname = localFile;
                            document.talentpoolForm.localFile.value = localfname;
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
            function setClassRes()
            {
                if (trim(document.forms[0].fname.value) != "")
                    document.getElementById("upload_link_resume").className = "attache_btn uploaded_img";
                else
                    document.getElementById("upload_link_resume").className = "attache_btn";
            }
            function setval()
            {
                if (eval(document.forms[0].expectedsalary.value) <= 0)
                    document.forms[0].expectedsalary.value = "";
                if (eval(document.forms[0].age.value) <= 0)
                    document.forms[0].age.value = "";
                if (eval(document.forms[0].travelDays.value) <= 0)
                    document.forms[0].travelDays.value = "";
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