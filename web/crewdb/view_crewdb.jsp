<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.crewdb.CrewdbInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="crewdb" class="com.web.jxp.crewdb.Crewdb" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 2, submtp = 4;
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
     
        String file_path = crewdb.getMainPath("view_candidate_file");
        CrewdbInfo info = null;
        if(session.getAttribute("CREWDB_DETAIL") != null)
            info = (CrewdbInfo)session.getAttribute("CREWDB_DETAIL");       
        ArrayList list = new ArrayList();

        if(request.getAttribute("LIST1") != null)
        {
            list = (ArrayList)request.getAttribute("LIST1"); 
            request.removeAttribute("LIST1");
        }
        int list_size = list.size();

        CrewdbInfo healthinfo = null;
        if(session.getAttribute("HEALTH_DETAIL") != null)
        {
            healthinfo = (CrewdbInfo)session.getAttribute("HEALTH_DETAIL");   
        }

        ArrayList getvaccinationList = new ArrayList();
        if(request.getAttribute("VACCINATIONLIST") != null)
        {
            getvaccinationList = (ArrayList)request.getAttribute("VACCINATIONLIST"); 
            request.removeAttribute("VACCINATIONLIST");
        }
        int listvac_size = getvaccinationList.size();

        
        ArrayList workexplist = new ArrayList();
      
        if(request.getAttribute("WORKEXPLIST") != null)
        {
            workexplist = (ArrayList)request.getAttribute("WORKEXPLIST"); 
            request.removeAttribute("WORKEXPLIST");
        }
        int listworkexp_size = workexplist.size();

        ArrayList educlist = new ArrayList();
        if(request.getAttribute("LISTEDUC") != null)
        {
            educlist = (ArrayList)request.getAttribute("LISTEDUC"); 
            request.removeAttribute("LISTEDUC");
        }
        int listeduc_size = educlist.size();


        ArrayList certilist = new ArrayList();

        if(request.getAttribute("LISTCERTI") != null)
        {
            certilist = (ArrayList)request.getAttribute("LISTCERTI"); 
            request.removeAttribute("LISTCERTI");
        }
        int listcerti_size = certilist.size();

        ArrayList hitchlist = new ArrayList();
        if(request.getAttribute("LISTHITCH") != null)
        {
            hitchlist = (ArrayList)request.getAttribute("LISTHITCH"); 
            request.removeAttribute("LISTHITCH");
        }
        int listhitch_size = hitchlist.size();

        ArrayList banklist = new ArrayList();
        if(request.getAttribute("LISTBANK") != null)
        {
            banklist = (ArrayList)request.getAttribute("LISTBANK"); 
            request.removeAttribute("LISTBANK");
        }
        int banklist_size = banklist.size();

      
        ArrayList govdoclist = new ArrayList();
        if(request.getAttribute("LISTGOVDOC") != null)
        {
            govdoclist = (ArrayList)request.getAttribute("LISTGOVDOC"); 
            request.removeAttribute("LISTGOVDOC");
        }
        int govdoclist_size = govdoclist.size();

        ArrayList cassessmenthistorylist = new ArrayList();
        if(request.getAttribute("LISTCASSHIST") != null)
        {
            cassessmenthistorylist = (ArrayList)request.getAttribute("LISTCASSHIST"); 
            request.removeAttribute("LISTCASSHIST");
        }
        int cassessmenthistorylist_size = 0;
            cassessmenthistorylist_size = cassessmenthistorylist.size();
        
%>
<head>
    <meta charset="utf-8">
    <title><%= crewdb.getMainPath("title") != null ? crewdb.getMainPath("title") : "" %></title>
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
    <script type="text/javascript" src="../jsnew/validation.js"></script>
    <script type="text/javascript" src="../jsnew/crewdb.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/crewdb/CrewdbAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
<html:hidden property="doCancel"/>
<html:hidden property="search"/>
<html:hidden property="positionIndex"/>
<html:hidden property="statusIndex"/>
<html:hidden property="clientIdIndex"/>
<html:hidden property="countryIdIndex"/>
<html:hidden property="assetIdIndex"/>
<html:hidden property="crewdbId"/>
<html:hidden property="doView"/>
<html:hidden property="type"/>
    <!-- Begin page -->
    <div id="layout-wrapper">
        <%@include file="../header.jsp" %>
        <%@include file="../sidemenu.jsp" %>
        <!-- Start right Content -->
        <div class="main-content">
            <div class="page-content tab_panel">
                <div class="row head_title_area">
                    <div class="col-md-12 col-xl-12">
                        <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> View Talent Pool</a></div>
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
                    <div class="col-md-12 col-xl-12 tab_head_area">
                        <ul class="nav nav-tabs nav-tabs-custom" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" data-bs-toggle="tab" href="#tab1" role="tab">
                                    <span class="d-none d-md-block">Personal</span><span class="d-block d-md-none"><i class="mdi mdi-home-variant h5"></i></span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link " data-bs-toggle="tab" href="#tab2" role="tab">
                                    <span class="d-none d-md-block">Language</span><span class="d-block d-md-none"><i class="mdi mdi-account h5"></i></span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link " data-bs-toggle="tab" href="#tab3" role="tab">
                                    <span class="d-none d-md-block">Health</span><span class="d-block d-md-none"><i class="mdi mdi-account h5"></i></span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link " data-bs-toggle="tab" href="#tab5" role="tab">
                                    <span class="d-none d-md-block">Vaccination Details</span><span class="d-block d-md-none"><i class="mdi mdi-account h5"></i></span>
                                </a>
                            </li>
                            
                            <li class="nav-item">
                                <a class="nav-link " data-bs-toggle="tab" href="#tab4" role="tab">
                                    <span class="d-none d-md-block">Experience</span><span class="d-block d-md-none"><i class="mdi mdi-account h5"></i></span>
                                </a>
                            </li>
                            
                            <li class="nav-item">
                                <a class="nav-link " data-bs-toggle="tab" href="#tab6" role="tab">
                                    <span class="d-none d-md-block">Education</span><span class="d-block d-md-none"><i class="mdi mdi-account h5"></i></span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link " data-bs-toggle="tab" href="#tab7" role="tab">
                                    <span class="d-none d-md-block">Certifications</span><span class="d-block d-md-none"><i class="mdi mdi-account h5"></i></span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link " data-bs-toggle="tab" href="#tab8" role="tab">
                                    <span class="d-none d-md-block">Hitch Details</span><span class="d-block d-md-none"><i class="mdi mdi-account h5"></i></span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link " data-bs-toggle="tab" href="#tab9" role="tab">
                                    <span class="d-none d-md-block">Bank Details</span><span class="d-block d-md-none"><i class="mdi mdi-account h5"></i></span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link " data-bs-toggle="tab" href="#tab10" role="tab">
                                    <span class="d-none d-md-block">Documents</span><span class="d-block d-md-none"><i class="mdi mdi-account h5"></i></span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link " data-bs-toggle="tab" href="#tab11" role="tab">
                                    <span class="d-none d-md-block">Assessment History</span><span class="d-block d-md-none"><i class="mdi mdi-account h5"></i></span>
                                </a>
                            </li>
                        </ul>
                    </div>		
                </div>
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12 col-xl-12">
                            <div class="body-background">
                                <div class="row d-none1">
                                    <div class="col-lg-12">
                                        <div class="tab-content">
                                            <div class="tab-pane  active" id="tab1" role="tabpanel">
                                                <div class="row ">
                                                    <div class="row m_30">
<%
                                                        if(info != null) 
                                                        {
                                                            String view_path = crewdb.getMainPath("view_candidate_file");
                                                            String cphoto = info.getPhoto() != null && !info.getPhoto().equals("") ? view_path+info.getPhoto() : "../assets/images/empty_profile.png";
                                                            String resumefilename = info.getResumefilename() != null && !info.getResumefilename().equals("") ? view_path+info.getResumefilename() : "";
                                                            String certificate = "";
%>
                                                                <div class="col-lg-2 col-md-2 col-sm-2 col-12">
                                                                    <div class="row mt_30">
                                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                                            <div class="user_photo pic_photo">
                                                                                <img src="../assets/images/user.png" />
                                                                                <div class="upload_file">
                                                                                    <input id="upload1" type="file">
                                                                                    <img src="<%=cphoto%>">                                                                                    
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-10 col-md-8 col-sm-8 col-12 flex-end align-items-end">
                                                                    <div class="row">
                                                                        <div class="col-lg-8 col-md-8 col-sm-8 col-12 candi_info">
                                                                            <ul>
                                                                                <li><span><%= info.getName() != null ? info.getName() : "" %></span></li>
                                                                            <% if(info.getPosition() != null && !info.getPosition().equals("")) {%><li><span><%= info.getPosition() %></span></li><% } %>
                                                                            <% if((info.getContact1() != null && !info.getContact1().equals("")) || (info.getContact2() != null && !info.getContact2().equals(""))) {%><li><span><%= info.getContact1_code() != null ? info.getContact1_code() : "" %> <%= info.getContact1() != null ? info.getContact1() : "" %><% if(info.getContact2() != null && !info.getContact2().equals("")) {%> | <%= info.getContact2_code() != null ? info.getContact2_code() : "" %> <%= info.getContact2() %><% } %></span></li><% } %>
                                                                            <li><span><%= info.getEmail() != null ? info.getEmail() : "" %></span></li>
                                                                            </ul>
                                                                        </div>

                                                                        <div class="col-lg-3 col-md-3 col-sm-6 col-12 text-left flex-end align-items-end edit_sec">
                                                                            
                                                                            <ul class="resume_attach">
                                                                                <li><label class="form_label">Resume</label></li>
                                                                                <li><a href="javascript:;" class="attache_btn"><i class="fas fa-paperclip"></i> Attachment <span class="attach_number"> <%=crewdb.changeNum(info.getFilecount(),3)%></span></a></li>
                                                                                <li>
                                                                                    <div class="down_prev">

                                                                                        <% if(info.getFilecount() > 0) {%><a href="/jxp/candidate/download.jsp?candidateId=<%=crewdb.cipher(info.getCrewdbId()+"")%>&fn=<%=info.getFirstname().replaceAll("['\"&\\s]", "")%>" class="" target="_blank">Download All</a><span class="dot_ic"></span>
                                                                                        <a href="javascript:;" onclick="javascript: viewimg('<%=info.getCrewdbId()%>', '<%=info.getFirstname().replaceAll("['\"&\\s]", "")%>');" class="" data-bs-toggle="modal" data-bs-target="#view_resume_list">Preview All</a><%}%>

                                                                                    </div>
                                                                                </li>
                                                                            </ul>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                       <div class="row">
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Date of Birth</label>
                                                                    <span class="form-control"><%= info.getDob() != null && !info.getDob().equals("") ? info.getDob() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Place of Birth</label>
                                                                    <span class="form-control"><%= info.getPlace() != null && !info.getPlace().equals("") ? info.getPlace() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Gender</label>
                                                                    <span class="form-control"><%= info.getGender() != null && !info.getGender().equals("") ? info.getGender() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Marital Status</label>
                                                                    <span class="form-control"><%= info.getMaritialstatus() != null && !info.getMaritialstatus().equals("") ? info.getMaritialstatus() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Country</label>
                                                                    <span class="form-control"><%= info.getCountryName() != null && !info.getCountryName().equals("") ? info.getCountryName() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">City</label>
                                                                    <span class="form-control"><%= info.getCity() != null && !info.getCity().equals("") ? info.getCity() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Nationality</label>
                                                                    <span class="form-control"><%= info.getNationality() != null && !info.getNationality().equals("") ? info.getNationality() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Additional Contact No.</label>
                                                                <div class="row">
                                                                    <div class="col-lg-3"><span class="form-control"><%= info.getContact3_code() != null && !info.getContact3_code().equals("") ? info.getContact3_code() : "&nbsp;" %></span></div>
                                                                    <div class="col-lg-9"> <span class="form-control"><%= info.getContact3() != null && !info.getContact3().equals("") ? info.getContact3() : "&nbsp;" %></span></div>
                                                               </div>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Emergency Contact No. 1</label>
                                                                <div class="row">
                                                                        <div class="col-lg-3"><span class="form-control"><%= info.getEcontact1_code() != null && !info.getEcontact1_code().equals("") ? info.getEcontact1_code() : "&nbsp;" %></span></div>
                                                                        <div class="col-lg-9"> <span class="form-control"><%= info.getEcontact1() != null && !info.getEcontact1().equals("") ? info.getEcontact1() : "&nbsp;" %></span></div>
                                                                       </div>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Emergency Contact No. 2</label>
                                                                <div class="row">
                                                                        <div class="col-lg-3"><span class="form-control"><%= info.getEcontact2_code() != null && !info.getEcontact2_code().equals("") ? info.getEcontact2_code() : "&nbsp;" %></span></div>
                                                                        <div class="col-lg-9"> <span class="form-control"><%= info.getEcontact2() != null && !info.getEcontact2().equals("") ? info.getEcontact2() : "&nbsp;" %></span></div>
                                                                </div>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Next of Kin</label>
                                                                    <span class="form-control"><%= info.getNextofkin() != null && !info.getNextofkin().equals("") ? info.getNextofkin() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Relation</label>
                                                                    <span class="form-control"><%= info.getRelation() != null && !info.getRelation().equals("") ? info.getRelation() : "&nbsp;" %></span>
                                                                </div>
                                                            </div>
                                                                <div class="row">	
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4">
                                                                    <div class="row">
                                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                                            <label class="form_label">Permanent Address</label>
                                                                            <span class="form-control m_15"><%= info.getAddress1line1() != null && !info.getAddress1line1().equals("") ? info.getAddress1line1() : "&nbsp;" %></span>
                                                                             <span class="form-control m_15"><%= info.getAddress1line2() != null && !info.getAddress1line2().equals("") ? info.getAddress1line2() : "&nbsp;" %></span>
                                                                             <span class="form-control"><%= info.getAddress1line3() != null && !info.getAddress1line3().equals("") ? info.getAddress1line3() : "&nbsp;" %></span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4">
                                                                    <div class="row">
                                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                                            <label class="form_label">Communication Address</label>
                                                                            <span class="form-control m_15"><%= info.getAddress2line1() != null && !info.getAddress2line1().equals("") ? info.getAddress2line1() : "&nbsp;" %></span>
                                                                            <span class="form-control m_15"><%= info.getAddress2line2() != null && !info.getAddress2line2().equals("") ? info.getAddress2line2() : "&nbsp;" %></span>
                                                                            <span class="form-control"><%= info.getAddress2line3() != null && !info.getAddress2line3().equals("") ? info.getAddress2line3() : "&nbsp;" %></span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">	
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                                    <div class="row flex-end align-items-end">
                                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                                            <label class="form_label">Preferred Department</label>
                                                                            <span class="form-control"><%= info.getPreferreddept() != null && !info.getPreferreddept().equals("") ? info.getPreferreddept() : "&nbsp;" %></span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Expected Salary</label>
                                                                    <div class="row">
                                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4">
                                                                            <span class="form-control"><%= info.getCurrency() != null && !info.getCurrency().equals("") ? info.getCurrency() : "&nbsp;" %></span>
                                                                        </div>
                                                                        <div class="col-lg-8 col-md-8 col-sm-8 col-4">
                                                                            <span class="form-control"><%= info.getExpectedsalary() > 0 ? info.getExpectedsalary() : "&nbsp;" %></span>
                                                                        </div>
                                                                    </div> 
                                                                    <% } %>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                            <div class="tab-pane " id="tab2" role="tabpanel">
                                                 <div class="row m_30">
                                                     <div class="main-heading">
                                                         <div class="add-btn">
                                                             <h4>LANGUAGE DETAILS</h4>
                                                         </div>
                                                     </div>
                                                     <div class="row">
                                                         <div class="col-lg-12 all_client_sec" id="all_client">
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                                <div class="table-responsive table-detail">
                                                                    <table class="table table-striped mb-0">
                                                                         <thead>
                                                                             <tr>
                                                                                 <th width="20%"><span><b>Language</b> </span></th>
                                                                                 <th width="71%"><span><b>Proficiency</b></span> </th>
                                                                                 <th width="9%" class="text-center"><span><b>Attachment</b> </span></th>
                                                                             </tr>
                                                                         </thead>
                                                                         <tbody>
<%  
                                                                            if(list_size > 0)
                                                                            {
                                                                                for(int i = 0; i < list_size; i++)
                                                                                {
                                                                                    CrewdbInfo langinfo = (CrewdbInfo) list.get(i);
                                                                                    if(langinfo != null)
                                                                                    {
%>
                                                                                        <tr>
                                                                                            <td><%= langinfo.getLanguageName() != null && !langinfo.getLanguageName().equals("") ? langinfo.getLanguageName() :  "&nbsp;"  %></td>
                                                                                            <td><%= langinfo.getProficiencyName() != null && !langinfo.getProficiencyName().equals("") ? langinfo.getProficiencyName() :  "&nbsp;"  %></td>
                                                                                            <td class="action_column text-center">
                                                                                            <% if(!langinfo.getCandlangfilename().equals("")) {%><a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=file_path+langinfo.getCandlangfilename() %>');"><img src="../assets/images/attachment.png"/> </a><% } else { %><% } %>
                                                                                            </td>
                                                                                        </tr>
<%
                                                                                    }
                                                                                }
                                                                            }
                                                                            else
                                                                            {
%>
                                                                                    <tr>
                                                                                        <td colspan="2">No information available.</td>
                                                                                        </tr>
<%
                                                                            }
%>
                                                                         </tbody>
                                                                     </table>
                                                                 </div>
                                                             </div>		
                                                         </div>
                                                     </div>
                                                 </div>
                                             </div>
                                            <div class="tab-pane " id="tab3" role="tabpanel">
                                                <div class="row ">
                                                    
                                                    <div class="main-heading m_30">
                                                        <div class="add-btn"><h4>HEALTH DETAILS</h4></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Seaman Specific Medical Fitness</label>
                                                            <span class="form-control"><%= healthinfo != null && healthinfo.getSsmf() != null && !healthinfo.getSsmf().equals("")  ? healthinfo.getSsmf() :  "&nbsp;" %></span>
                                                        </div>
                                                        <div class="col-lg-8 col-md-8 col-sm-8 col-12 form_group">
                                                            <label class="form_label">OGUK Medical FTW</label>
                                                            <span class="form-control"><%= healthinfo != null &&  healthinfo.getOgukmedicalftw() != null && !healthinfo.getOgukmedicalftw().equals("")  ? healthinfo.getOgukmedicalftw() : "&nbsp;" %></span>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">OGUK Expiry</label>
                                                            <span class="form-control"><%=  healthinfo != null &&  healthinfo.getOgukexp() != null && !healthinfo.getOgukexp().equals("")  ? healthinfo.getOgukexp() : "&nbsp;" %></span>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Medical Fitness Certificate</label>
                                                            <span class="form-control"><%=  healthinfo != null &&  healthinfo.getMedifitcertexp() != null && !healthinfo.getMedifitcertexp().equals("")  ? healthinfo.getMedifitcertexp() : "&nbsp;" %></span>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Medical Fitness Certificate Expiry</label>
                                                            <span class="form-control"><%=  healthinfo != null &&  healthinfo.getMedifitcertexp() != null && !healthinfo.getMedifitcertexp().equals("")  ? healthinfo.getMedifitcertexp() : " &nbsp;" %></span>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Blood Group</label>
                                                            <span class="form-control"><%=  healthinfo != null &&  healthinfo.getBloodgroup() != null && !healthinfo.getBloodgroup().equals("")  ? healthinfo.getBloodgroup() : " &nbsp;" %></span>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Blood Pressure</label>
                                                            <span class="form-control"><%=  healthinfo != null &&  healthinfo.getBloodpressure() != null && !healthinfo.getBloodpressure().equals("")  ? healthinfo.getBloodpressure() :"&nbsp;" %></span>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Hypertension</label>
                                                            <span class="form-control"><%= healthinfo != null && healthinfo.getHypertension() != null && !healthinfo.getHypertension().equals("")  ? healthinfo.getHypertension() :  "&nbsp;" %></span>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Diabetes</label>
                                                            <span class="form-control"><%= healthinfo != null && healthinfo.getDiabetes() != null && !healthinfo.getDiabetes().equals("")  ? healthinfo.getDiabetes() :  "&nbsp;" %></span>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Smoking</label>
                                                            <span class="form-control"><%= healthinfo != null && healthinfo.getSmoking() != null && !healthinfo.getSmoking().equals("")  ? healthinfo.getSmoking() :  "&nbsp;" %></span>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Covid-19 2 Doses</label>
                                                            <span class="form-control"><%= healthinfo != null && healthinfo.getCov192doses() != null && !healthinfo.getCov192doses().equals("")  ? healthinfo.getCov192doses() :  "&nbsp;" %></span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane " id="tab4" role="tabpanel">
                                                <div class="row ">
                                                    <div class="main-heading m_30">
                                                        <div class="add-btn"><h4>WORK EXPERIENCE DETAILS</h4></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                            <div class="table-rep-plugin">
                                                                <div class="table-responsive mb-0">
                                                                    <table class="table table-striped">
                                                                        <thead>
                                                                            <tr>
                                                                                <th width="15%"><span><b>Position</b> </span></th>
                                                                                <th width="15%"><span><b>Department</b> </span></th>
                                                                                <th width="20%"><span><b>Company Name</b> </span></th>
                                                                                <th width="21%"><span><b>Asset Name</b> </span></th>
                                                                                <th width="10%"><span><b>Start Date</b> </span></th>
                                                                                <th width="10%"><span><b>End Date</b> </span></th>
                                                                                <th width="9%" class="text-center"><span><b>Attachment</b> </span></th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
<%  
                                                                            if(listworkexp_size > 0)
                                                                            {
                                                                                for(int i = 0; i < listworkexp_size; i++)
                                                                                {
                                                                                    CrewdbInfo workexpinfo = (CrewdbInfo) workexplist.get(i);
                                                                                    if(workexpinfo != null)
                                                                                    {
%>
                                                                                        <tr>
                                                                                            <td><%= workexpinfo.getJobtitle() != null && !workexpinfo.getJobtitle().equals("")  ? workexpinfo.getJobtitle() :  "&nbsp;" %></td>
                                                                                            <td><%= workexpinfo.getDepartmentfunction() != null && !workexpinfo.getDepartmentfunction().equals("")  ? workexpinfo.getDepartmentfunction() :  "&nbsp;" %></td>
                                                                                            <td><%= workexpinfo.getCompany() != null && !workexpinfo.getCompany().equals("")  ? workexpinfo.getCompany() :  "&nbsp;" %></td>
                                                                                            <td><%= workexpinfo.getAssetName() != null && !workexpinfo.getAssetName().equals("")  ? workexpinfo.getAssetName() :  "&nbsp;" %></td>
                                                                                            <td><%= workexpinfo.getWorkstartdate() != null && !workexpinfo.getWorkstartdate().equals("")  ? workexpinfo.getWorkstartdate() :  "&nbsp;" %></td>
                                                                                            <td><%= workexpinfo.getWorkenddate() != null && !workexpinfo.getWorkenddate().equals("") ? workexpinfo.getWorkenddate() : "Present" %></td>
                                                                                            <td class="action_column text-center">                                                                                    
                                                                                            <% if(!workexpinfo.getWorkexpfilename().equals("") ||!workexpinfo.getWorkexpfilename().equals("") ) {%><a href="javascript:;" onclick="javascript: viewworkexpfiles('<%=workexpinfo.getWorkexpfilename()%>','<%=workexpinfo.getWorkexpfilename()%>');" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_exp_list"><img src="../assets/images/attachment.png"/></a><% } else { %><% } %>
                                                                                            </td>
                                                                                        </tr>
<%
                                                                                    }
                                                                                }
                                                                            }
                                                                            else
                                                                            {
%>
                                                                                    <tr>
                                                                                        <td colspan="7">No information available.</td>
                                                                                        </tr>
<%
                                                                            }
%>
                                                                         </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>		
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane " id="tab5" role="tabpanel">
                                                <div class="row">
                                                    <div class="main-heading m_30">
                                                        <div class="add-btn"><h4>VACCINATION DETAILS</h4></div>
                                                    </div>
                                                    <div class="row m_30">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                            <div class="table-rep-plugin">
                                                                <div class="table-responsive mb-0">
                                                                    <table class="table table-striped">
                                                                        <thead>
                                                                            <tr>
                                                                                <th width="25%"><span><b>Name</b> </span></th>
                                                                                <th width="13%"><span><b>Type</b> </span></th>
                                                                                <th width="25%"><span><b>Place</b> </span></th>
                                                                                <th width="14%"><span><b>Application Date</b> </span></th>
                                                                                <th width="14%"><span><b>Expiration Date</b> </span></th>
                                                                                <th width="9%" class="text-center"><span><b>Attachment</b> </span></th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
<%  
                                                                            if(listvac_size > 0)
                                                                            {
                                                                                for(int i = 0; i < listvac_size; i++)
                                                                                {
                                                                                    CrewdbInfo vacinfo = (CrewdbInfo) getvaccinationList.get(i);
                                                                                    if(vacinfo != null)
                                                                                    {
%>
                                                                                            <tr>
                                                                                                <td><%= vacinfo.getVaccinename() != null && !vacinfo.getVaccinename().equals("")  ? vacinfo.getVaccinename() :  "&nbsp;" %></td>
                                                                                                <td><%= vacinfo.getVaccinetype() != null && !vacinfo.getVaccinetype().equals("")  ? vacinfo.getVaccinetype() :  "&nbsp;" %></td>
                                                                                                <td><%= vacinfo.getPlaceofapplication() != null && !vacinfo.getPlaceofapplication().equals("")  ? vacinfo.getPlaceofapplication() :  "&nbsp;" %></td>
                                                                                                <td><%= vacinfo.getDateofapplication() != null && !vacinfo.getDateofapplication().equals("")  ? vacinfo.getDateofapplication() :  "&nbsp;" %></td>
                                                                                                <td><%= vacinfo.getDateofexpiry() != null && !vacinfo.getDateofexpiry().equals("")  ? vacinfo.getDateofexpiry() :  "Life" %></td>
                                                                                               <td class="action_column text-center">
                                                                                            <% if(!vacinfo.getVacfilename().equals("")) {%><a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=file_path+vacinfo.getVacfilename() %>');"><img src="../assets/images/attachment.png"/> </a><% } else { %><% } %>
                                                                                            </td>
                                                                                               
                                                                                            </tr>                                                                                           
<%
                                                                                    }
                                                                                }
                                                                            }
                                                                            else
                                                                            {
%>
                                                                                    <tr>
                                                                                        <td colspan="6">No information available.</td>
                                                                                        </tr>
<%
                                                                            }
%>
                                                                         </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>		
                                                        </div>
                                                    </div>
                                                    
                                                </div>
                                            </div>
                                            <div class="tab-pane " id="tab6" role="tabpanel">
                                                <div class="row ">
                                                    <div class="main-heading m_30">
                                                        <div class="add-btn"><h4>EDUCATIONAL DETAILS</h4></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                            <div class="table-rep-plugin">
                                                                <div class="table-responsive mb-0">
                                                                    <table class="table table-striped">
                                                                        <thead>
                                                                            <tr>
                                                                                <th width="20%"><span><b>Kind</b> </span></th>
                                                                                <th width="14%"><span><b>Degree</b> </span></th>
                                                                                <th width="17%"><span><b>Institution, Location</b> </span></th>
                                                                                <th width="20%"><span><b>Field of Study</b> </span></th>
                                                                                <th width="10%"><span><b>Start Date</b> </span></th>
                                                                                <th width="10%"><span><b>End Date</b> </span></th>
                                                                                <th width="9%" class="text-center"><span><b>Attachment</b> </span></th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
<%  
                                                                            if(listeduc_size > 0)
                                                                            {
                                                                                for(int i = 0; i < listeduc_size; i++)
                                                                                {
                                                                                    CrewdbInfo educdetailinfo = (CrewdbInfo) educlist.get(i);
                                                                                    if(educdetailinfo != null)
                                                                                    {
%>
                                                                                        <tr>
                                                                                            <td><%= educdetailinfo.getBackgroundofstudy() != null  && !educdetailinfo.getBackgroundofstudy().equals("")  ? educdetailinfo.getBackgroundofstudy() : "&nbsp;" %></td>
                                                                                            <td><%= educdetailinfo.getDegree() != null && !educdetailinfo.getDegree().equals("") ? educdetailinfo.getDegree() : "&nbsp;" %></td>
                                                                                            <td><%= educdetailinfo.getEduinstitute() != null  && !educdetailinfo.getEduinstitute().equals("")  ? educdetailinfo.getEduinstitute() : "&nbsp;" %>,<%= educdetailinfo.getLocationofinstitute() != null  && !educdetailinfo.getLocationofinstitute().equals("")  ? educdetailinfo.getLocationofinstitute() : "&nbsp;" %></td>
                                                                                             <td><%= educdetailinfo.getFieldofstudy() != null  && !educdetailinfo.getFieldofstudy().equals("") ? educdetailinfo.getFieldofstudy() : "&nbsp;" %></td>
                                                                                            <td><%= educdetailinfo.getCoursestart() != null  && !educdetailinfo.getCoursestart().equals("")  ? educdetailinfo.getCoursestart() : "&nbsp;" %></td>
                                                                                            <td><%= educdetailinfo.getPassingyear() != null  && !educdetailinfo.getPassingyear().equals("")  ? educdetailinfo.getPassingyear() : "&nbsp;" %></td>
                                                                                            
                                                                                            <td class="action_column text-center">                                                                                        
                                                                                            <% if(!educdetailinfo.getEducfilename().equals("")) {%><a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript: setIframe('<%=file_path+educdetailinfo.getEducfilename() %>');"><img src="../assets/images/attachment.png"/> </a><% } else { %><% } %>
                                                                                            </td>
                                                                                        </tr>
<%
                                                                                    }
                                                                                }
                                                                            }
                                                                            else
                                                                            {
%>
                                                                                    <tr>
                                                                                        <td colspan="7">No information available.</td>
                                                                                        </tr>
<%
                                                                            }
%>
                                                                         </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>		
                                                </div>
                                            </div>
                                            <div class="tab-pane " id="tab7" role="tabpanel">
                                                <div class="row ">
                                                    <div class="main-heading m_30">
                                                        <div class="add-btn"><h4>TRAINING, CERTIFICATION & SAFETY COURSES</h4></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                            <div class="table-rep-plugin">
                                                                <div class="table-responsive mb-0">
                                                                    <table class="table table-striped">
                                                                        <thead>
                                                                            <tr>
                                                                                <th width="21%"><span><b>Course Name</b> </span></th>
                                                                                <th width="15%"><span><b>Type</b> </span></th>
                                                                                <th width="20%"><span><b>Institution, Location</b> </span></th>
                                                                                <th width="15%"><span><b>Approved By</b> </span></th>
                                                                                <th width="10%"><span><b>Issue Date</b> </span></th>
                                                                                <th width="10%"><span><b>Expiry Date</b> </span></th>
                                                                                <th width="9%" class="text-center"><span><b>Attachment</b> </span></th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
<%  
                                                                            if(listcerti_size > 0)
                                                                            {
                                                                                for(int i = 0; i < listcerti_size; i++)
                                                                                {
                                                                                    CrewdbInfo certidetailinfo = (CrewdbInfo) certilist.get(i);
                                                                                    if(certidetailinfo != null)
                                                                                    {
%>
                                                                                        <tr>
                                                                                            <td><%= certidetailinfo.getCoursename() != null && !certidetailinfo.getCoursename().equals("")  ? certidetailinfo.getCoursename() : "&nbsp;" %></td>
                                                                                            <td><%= certidetailinfo.getCoursetype() != null && !certidetailinfo.getCoursetype().equals("") ? certidetailinfo.getCoursetype() : "&nbsp;" %></td>
                                                                                            <td><%= certidetailinfo.getEducinst() != null && !certidetailinfo.getEducinst().equals("")  ? certidetailinfo.getEducinst() : "&nbsp;" %></td>
                                                                                            <td><%= certidetailinfo.getApprovedby() != null && !certidetailinfo.getApprovedby().equals("")  ? certidetailinfo.getApprovedby() : "&nbsp;" %></td>
                                                                                            <td><%= certidetailinfo.getDateofissue() != null && !certidetailinfo.getDateofissue().equals("") ? certidetailinfo.getDateofissue() : "&nbsp;" %></td>
                                                                                            <td><%= certidetailinfo.getExpirydate() != null && !certidetailinfo.getExpirydate().equals("") ? certidetailinfo.getExpirydate() : "&nbsp;" %></td>
                                                                                            <td class="action_column text-center">                                                                                    
                                                                                            <% if(!certidetailinfo.getCertifilename().equals("")) {%><a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript: setIframe('<%=file_path+certidetailinfo.getCertifilename() %>');"><img src="../assets/images/attachment.png"/> </a><% } else { %><% } %>
                                                                                            </td>
                                                                                        </tr>
<%
                                                                                    }
                                                                                }
                                                                            }
                                                                            else
                                                                            {
%>
                                                                                    <tr>
                                                                                        <td colspan="7">No information available.</td>
                                                                                        </tr>
<%
                                                                            }
%>
                                                                         </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane " id="tab8" role="tabpanel">
                                                <div class="row ">
                                                    <div class="main-heading m_30">
                                                        <div class="add-btn"><h4>HITCH HISTORY</h4></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                            <div class="table-rep-plugin">
                                                                <div class="table-responsive mb-0">
                                                                    <table class="table table-striped">
                                                                        <thead>
                                                                            <tr>
                                                                                <th width="15%"><span><b>Position</b> </span></th>
                                                                                <th width="15%"><span><b>Company Ship</b> </span></th>
                                                                                <th width="10%"><span><b>Region</b> </span></th>
                                                                                <th width="12%"><span><b>Country</b> </span></th>
                                                                                <th width="20%"><span><b>Engine Type</b> </span></th>
                                                                                <th width="8%%"><span><b>Tonnage</b> </span></th>
                                                                                <th width="10%"><span><b>Sign In</b> </span></th>
                                                                                <th width="10%"><span><b>Sign Off</b> </span></th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
<%  
                                                                            if(listhitch_size > 0)
                                                                            {
                                                                                for(int i = 0; i < listhitch_size; i++)
                                                                                {
                                                                                    CrewdbInfo hitchdetailinfo = (CrewdbInfo) hitchlist.get(i);
                                                                                    if(hitchdetailinfo != null)
                                                                                    {
%>
                                                                                        <tr>
                                                                                            <td><%= hitchdetailinfo.getPosition() != null && !hitchdetailinfo.getPosition().equals("") ? hitchdetailinfo.getPosition() : "&nbsp;" %></td>
                                                                                            <td><%= hitchdetailinfo.getCompanyshipname() != null && !hitchdetailinfo.getCompanyshipname().equals("") ? hitchdetailinfo.getCompanyshipname() : "&nbsp;" %></td>
                                                                                            <td><%= hitchdetailinfo.getRegion() != null && !hitchdetailinfo.getRegion().equals("") ? hitchdetailinfo.getRegion() : "" %></td>
                                                                                            <td><%= hitchdetailinfo.getCountryname() != null && !hitchdetailinfo.getCountryname().equals("") ? hitchdetailinfo.getCountryname() : "&nbsp;" %></td>
                                                                                            <td><%= hitchdetailinfo.getEnginetype() != null && !hitchdetailinfo.getEnginetype().equals("") ? hitchdetailinfo.getEnginetype() : "&nbsp;" %></td>
                                                                                            <td><% if( hitchdetailinfo.getTonnage() > 0) {%><%= hitchdetailinfo.getTonnage()%><% } else {%>&nbsp;<% }%></td>
                                                                                            <td><%= hitchdetailinfo.getSignin() != null && !hitchdetailinfo.getSignin().equals("")  ? hitchdetailinfo.getSignin() : "&nbsp;" %></td>
                                                                                            <td><%= hitchdetailinfo.getSignoff() != null && !hitchdetailinfo.getSignoff().equals("")  ? hitchdetailinfo.getSignoff() : "&nbsp;" %></td>
                                                                                            
                                                                                        </tr>
<%
                                                                                    }
                                                                                }
                                                                            }
                                                                            else
                                                                            {
%>
                                                                                    <tr>
                                                                                        <td colspan="8">No information available.</td>
                                                                                        </tr>
<%
                                                                            }
%>
                                                                         </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="tab-pane " id="tab9" role="tabpanel">
                                                <div class="row ">
                                                    <div class="main-heading m_30">
                                                        <div class="add-btn"><h4>BANK DETAILS</h4></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                            <div class="table-rep-plugin">
                                                                <div class="table-responsive mb-0">
                                                                    <table class="table table-striped">
                                                                        <thead>
                                                                            <tr>
                                                                                <th width="20%"><span><b>Bank Name</b> </span></th>
                                                                                <th width="30%"><span><b>Account Type</b> </span></th>
                                                                                <th width="16%"><span><b>Branch</b> </span></th>
                                                                                <th width="16%"><span><b>IFSC</b> </span></th>
                                                                                <th width="9%"><span><b>Primary</b> </span></th>
                                                                                <th width="9%" class="text-center"><span><b>Attachment</b> </span></th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
<%  
                                                                            if(banklist_size > 0)
                                                                            {
                                                                                for(int i = 0; i < banklist_size; i++)
                                                                                    
                                                                                {
                                                                                    CrewdbInfo bkinfo = (CrewdbInfo) banklist.get(i);
                                                                                    if(bkinfo != null)
                                                                                    {
%>
                                                                                        <tr>
                                                                                            <td><%= bkinfo.getBankName() != null && !bkinfo.getBankName().equals("") ? bkinfo.getBankName() : "&nbsp;" %></td>
                                                                                            <td><%= bkinfo.getSavingAccountNo() != null && !bkinfo.getSavingAccountNo().equals("") ? bkinfo.getSavingAccountNo() : "&nbsp;" %></td>
                                                                                            <td><%= bkinfo.getBranch() != null && !bkinfo.getBranch().equals("") ? bkinfo.getBranch() : "" %></td>
                                                                                            <td><%= bkinfo.getIFSCCode() != null && !bkinfo.getIFSCCode().equals("") ? bkinfo.getIFSCCode() : "&nbsp;" %></td>
                                                                                            <% if( bkinfo.getPrimarybankId() == 1) {%><td class="primary_active"><i class="fa fa-check" aria-hidden="true"></i></td><% } else {%><td></td><% } %>
                                                                                            <td class="action_column text-center">                                                                    
                                                                                            <% if(!bkinfo.getBkFilename().equals("")) {%><a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=file_path+bkinfo.getBkFilename() %>');"><img src="../assets/images/attachment.png"/> </a><% } else { %>&nbsp;<% } %>
                                                                                            </td>
                                                                                        </tr>
<%
                                                                                    }
                                                                                }
                                                                            }
                                                                            else
                                                                            {
%>
                                                                                    <tr>
                                                                                        <td colspan="8">No information available.</td>
                                                                                        </tr>
<%
                                                                            }
%>
                                                                         </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="tab-pane " id="tab10" role="tabpanel">
                                                <div class="row ">
                                                    <div class="main-heading m_30">
                                                        <div class="add-btn"><h4>DOCUMENTS</h4></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                            <div class="table-rep-plugin">
                                                                <div class="table-responsive mb-0">
                                                                    <table class="table table-striped">
                                                                        <thead>
                                                                            <tr>
                                                                                <th width="17%"><b>Document Name</b></th>
                                                                                <th width="10%"><b>Number</b></th>
                                                                                <th width="22%"><b>Place of Issue</b></th>
                                                                                <th width="22%"><b>Issued By</b></th>
                                                                                <th width="10%"><b>Issue Date</b></th>
                                                                                <th width="10%"><b>Expiry Date</b></th>
                                                                                <th width="9%" class="text-center"><b>Attachment</b></th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
<%  
                                                                            if(govdoclist_size > 0)
                                                                            {
                                                                                for(int i = 0; i < govdoclist_size; i++)
                                                                                {
                                                                                    CrewdbInfo govdocinfo = (CrewdbInfo) govdoclist.get(i);
                                                                                    if(govdocinfo != null)
                                                                                    {
%>
                                                                                        <tr>
                                                                                            <td><%= govdocinfo.getDocumentName() != null && !govdocinfo.getDocumentName().equals("") ? govdocinfo.getDocumentName() : "&nbsp;" %></td>
                                                                                            <td><%= govdocinfo.getDocumentNo() != null && !govdocinfo.getDocumentNo().equals("") ? govdocinfo.getDocumentNo() : "&nbsp;" %></td>
                                                                                            <td><%= govdocinfo.getPlaceOfissue() != null && !govdocinfo.getPlaceOfissue().equals("") ? govdocinfo.getPlaceOfissue() : "" %></td>
                                                                                            <td><%= govdocinfo.getDocIssuedby() != null && !govdocinfo.getDocIssuedby().equals("")  ? govdocinfo.getDocIssuedby() : "&nbsp;" %></td>
                                                                                            <td><%= govdocinfo.getDateOfissue() != null && !govdocinfo.getDateOfissue().equals("") ? govdocinfo.getDateOfissue() : "&nbsp;" %></td>
                                                                                            <td><%= govdocinfo.getDateOfexpiry() != null && !govdocinfo.getDateOfexpiry().equals("") ? govdocinfo.getDateOfexpiry() : "&nbsp;" %></td>
                                                                                            <td class="action_column text-center">                                                                       
                                                                                            <% if(!govdocinfo.getDocFilename().equals("")) {%><a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript: setIframe('<%=file_path+govdocinfo.getDocFilename() %>');"><img src="../assets/images/attachment.png"/> </a><% } else { %>&nbsp;<% } %>
                                                                                            </td>
                                                                                        </tr>
<%
                                                                                    }
                                                                                }
                                                                            }
                                                                            else
                                                                            {
%>
                                                                                    <tr>
                                                                                        <td colspan="8">No information available.</td>
                                                                                        </tr>
<%
                                                                            }
%>
                                                                         </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                                                         
                                            <div class="tab-pane " id="tab11" role="tabpanel">
                                                <div class="row ">
                                                    <div class="main-heading m_30">
                                                        <div class="add-btn"><h4>ASSESSMENT HISTORY</h4></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                            <div class="table-rep-plugin">
                                                                <div class="table-responsive mb-0">
                                                                    <table class="table table-striped">
                                                                        <thead>
                                                                            <tr>
                                                                                <th width="10%"><b>Date</b></th>
                                                                                <th width="10%"><b>Time</b></th>
                                                                                <th width="22%"><b>Coordinator</b></th>
                                                                                <th width="15%"><b>Assessment</b></th>
                                                                                <th width="22%"><b>Assessor</b></th>
                                                                                <th width="9%"><b>Score</b></th>
                                                                                <th width="10%"><b>Status</b></th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
<%  
                                                                            if(cassessmenthistorylist_size > 0)
                                                                            {
                                                                                
                                                                                for(int i = 0; i < cassessmenthistorylist_size; i++)
                                                                                {
                                                                                    CrewdbInfo ahistInfo = (CrewdbInfo) cassessmenthistorylist.get(i);
                                                                                    int status = ahistInfo.getStatus();
                                                                                    if(ahistInfo != null)
                                                                                    {
%>
                                                                                        <tr>
                                                                                            <td><%= ahistInfo.getDate() != null && !ahistInfo.getDate().equals("") ? ahistInfo.getDate() : "&nbsp;" %></td>
                                                                                            <td><%= ahistInfo.getTime() != null && !ahistInfo.getTime().equals("") ? ahistInfo.getTime() : "&nbsp;" %></td>
                                                                                            <td><%= ahistInfo.getCoordinatorName() != null && !ahistInfo.getCoordinatorName().equals("") ? ahistInfo.getCoordinatorName() : "" %></td>
                                                                                            <td><%= ahistInfo.getAssessment() != null && !ahistInfo.getAssessment().equals("")  ? ahistInfo.getAssessment() : "&nbsp;" %></td>
                                                                                            <td><%= ahistInfo.getAssessorName() != null && !ahistInfo.getAssessorName().equals("") ? ahistInfo.getAssessorName() : "&nbsp;" %></td>
                                                                                            <td><% if(ahistInfo.getMarks() > 0)  { %><span <%if(status == 2){ %> class='green_mark' <%} else if(status == 3) { %> class='red_mark' <%}%> ><%= ahistInfo.getMarks() %></span> <%} else { %> - <% }%></td>
                                                                                            <td><%= ahistInfo.getStatusValue() != null && !ahistInfo.getStatusValue().equals("") ? ahistInfo.getStatusValue() : "&nbsp;" %></td>
                                                                                        </tr>
<%
                                                                                    }
                                                                                }
                                                                            }
                                                                            else
                                                                            {
%>
                                                                                    <tr>
                                                                                        <td colspan="8">No information available.</td>
                                                                                        </tr>
<%
                                                                            }
%>
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
                                </div>
                            </div> 
                        </div>
                        <!-- End Page-content -->
                    </div>
                    <!-- end main content-->
                </div>
            </div>
        </div>
    </div>
    <!-- END layout-wrapper -->
    <%@include file="../footer.jsp" %>    
    <div id="view_resume_list" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row" id="viewfilesdiv">

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
                            <span class="resume_title"></span>
                            <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                            <a id='diframe' href="" class="down_btn" download=""><img src="../assets/images/download.png"/></a>
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
    
    <div id="view_exp_list" class="modal fade view_resume_list" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                                </div>
                                <div class="modal-body">
                                    <div class="row" id="viewexpdiv">
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
