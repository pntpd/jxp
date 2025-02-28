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
        try {
            int mtp = 2, submtp = 4, ctp = 1;
            String per = "N", addper = "N", editper = "N", deleteper = "N";
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
            }
        }
        String message = "", clsmessage = "deleted-msg";
        if (request.getAttribute("MESSAGE") != null) {
            message = (String) request.getAttribute("MESSAGE");
            request.removeAttribute("MESSAGE");
        }
        if (message.toLowerCase().contains("success")) {
            message = "";
        }
        if (message != null && (message.toLowerCase()).indexOf("success") != -1) {
            clsmessage = "updated-msg";
        }
        ArrayList list = new ArrayList();
        if (request.getSession().getAttribute("MODULEPER_LIST") != null) {
            list = (ArrayList) request.getSession().getAttribute("MODULEPER_LIST");
        }

        String file_path = talentpool.getMainPath("view_candidate_file");
        TalentpoolInfo info = null;
        int status = 0;
        String date2 = "";
        if (session.getAttribute("CANDIDATE_DETAIL") != null) {
            info = (TalentpoolInfo) session.getAttribute("CANDIDATE_DETAIL");
            status = info.getStatus();
            date2 = info.getDate2() != null ? info.getDate2(): "";
        }

        String thankyou = "no";
        if (request.getAttribute("CANDSAVEMODEL") != null) {
            thankyou = (String) request.getAttribute("CANDSAVEMODEL");
            request.removeAttribute("CANDSAVEMODEL");
        }
        String thankyou1 = "no";
        if (request.getAttribute("TRANSAVEMODEL") != null) {
            thankyou1 = (String) request.getAttribute("TRANSAVEMODEL");
            request.removeAttribute("TRANSAVEMODEL");
        }
        String thankyou2 = "no";
        if (request.getAttribute("TERMISAVEMODEL") != null) {
            thankyou2 = (String) request.getAttribute("TERMISAVEMODEL");
            request.removeAttribute("TERMISAVEMODEL");
        }

        String filename = "";
        if (session.getAttribute("TERMINATEFILENAME") != null) {
            filename = (String) session.getAttribute("TERMINATEFILENAME");
            if (filename != null && !filename.equals("")) {
                filename = talentpool.getMainPath("view_candidate_file") + filename;
            }
        }
        ArrayList offshore_list = new ArrayList();
        if(request.getAttribute("OFFSHORELIST") != null)
            offshore_list = (ArrayList) request.getAttribute("OFFSHORELIST");
        int offshore_list_size = offshore_list.size();

        TalentpoolInfo p1info = null;
        double p1rate = 0.0;
        if (session.getAttribute("P1_RATE") != null) 
        {
            p1info = (TalentpoolInfo) session.getAttribute("P1_RATE");
            p1rate = p1info.getRate1();
        }
        TalentpoolInfo p2info = null;
        double p2rate = 0.0;
        if (session.getAttribute("P2_RATE") != null) 
        {
            p2info = (TalentpoolInfo) session.getAttribute("P2_RATE");
            p2rate = p2info.getRate1();
        }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= talentpool.getMainPath("title") != null ? talentpool.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/talentpool.js"></script>
        <script type="text/javascript" src="../jsnew/modal_ddl.js"></script>
        <script type="text/javascript" src="../jsnew/talentpoolgeneratecv.js"></script>
        <script type="text/javascript" src="../jsnew/talentpoolgenerateof.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="doSaveTransferModal"/>
        <html:hidden property="doSaveTerminateModal"/>
        <html:hidden property="doGenerate"/>
        <html:hidden property="doGenerateOF"/>
        <html:hidden property="terminatefilehidden"/>
        <html:hidden property="doSaveOnboardModal"/>
        <div id="layout-wrapper">
            <%@include file ="../header.jsp"%>
            <%@include file ="../sidemenu.jsp"%>
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
                            <div class="float-end">
                                <div class="toggled-off usefool_tool">
                                    <div class="toggle-title">
                                        <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                        <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>
                                    </div>
                                    <div class="toggle-content">
                                        <h4>Useful Tools</h4>
                                        <ul>
                                            <li><a href="javascript: openinnewtab();"><img src="../assets/images/open-in-new-tab.png"/> Open in New Tab</a></li>
                                            <li><a href="javascript:;"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                            <li>
                                                <a href="javascript: ;"><img src="../assets/images/export-data.png"/> Export Data</a>
                                                <%--<%if((addper.equals("Y") || editper.equals("Y"))){%>
                                                    <a href="javascript: dataDupm();"><img src="../assets/images/export-data.png"/> Export Data</a>
                                                <%}else{%>
                                                <%}%>--%>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
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
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <% if (!message.equals("")) {%><div class="alert <%=clsmessage%> alert-dismissible fade show">
                                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button><%=message%>
                                            </div><% } %>
                                            <div class="tab-content">
                                                <div class="tab-pane active">
                                                    <div class="">
                                                        <div class="row m_30 flex-end align-items-end">
                                                            <%
                                                                if(info != null) 
                                                                    {
                                                                        String view_path = talentpool.getMainPath("view_candidate_file");
                                                                        String cphoto = info.getPhoto() != null && !info.getPhoto().equals("") ? view_path+info.getPhoto() : "../assets/images/empty_user.png";
                                                                        String resumefilename = info.getResumefilename() != null && !info.getResumefilename().equals("") ? view_path+info.getResumefilename() : "";
                                                                        String certificate = "";
                                                                        int vflag = 0;
                                                                        vflag = info.getVflag();
                                                            %>
                                                            <div class="col-lg-2 col-md-2 col-sm-2 col-12">
                                                                <div class="row mt_30">
                                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                                        <div class="user_photo pic_photo <%if(vflag == 4){%>ocs_certified<%}%>">
                                                                            <%if(vflag == 4){%><img src="../assets/images/ocs_certified.png" class="cer_icon"><%}%>
                                                                            <img src="../assets/images/user.png" />
                                                                            <div class="upload_file">
                                                                                <input id="upload1" type="file">
                                                                                <img src="<%=cphoto%>">                                                                                    
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-10 col-md-8 col-sm-8 col-12">
                                                                <div class="row">
                                                                    <div class="col-lg-5 col-md-5 col-sm-5 col-12 candi_info">
                                                                        <ul>
                                                                            <li><span><%= info.getName() != null ? info.getName() : "" %></span></li>
                                                                            <% if(info.getPosition() != null && !info.getPosition().equals("")) {%><li><span><%= info.getPosition() %></span></li><% } %>
                                                                            <% if((info.getContactno1() != null && !info.getContactno1().equals("")) || (info.getContactno2() != null && !info.getContactno2().equals(""))) {%><li><span><%= info.getCode1Id() != null ? info.getCode1Id() : "" %> <%= info.getContactno1() != null ? info.getContactno1() : "" %><% if(info.getContactno2() != null && !info.getContactno2().equals("")) {%> | <%= info.getCode2Id() != null ? info.getCode2Id() : "" %> <%= info.getContactno2() %><% } %></span></li><% } %>
                                                                            <li><span><%= info.getEmailId() != null ? info.getEmailId() : "" %></span></li>
                                                                        </ul>
                                                                    </div>

                                                                    <div class="col-lg-2 col-md-6 col-sm-8 col-12 generate_div">
                                                                        <%if((addper.equals("Y") || editper.equals("Y")) && status != 4){%>
                                                                        <div class="generate_btn">
                                                                            <a href="javascript:;" onclick="getGenerateOF('<%=info.getCandidateId()%>')">Generate Document</a>
                                                                        </div>
                                                                        <%}%>
                                                                    </div>

                                                                    <div class="col-lg-2 col-md-6 col-sm-8 col-12 generate_div">
                                                                        <%if((addper.equals("Y") || editper.equals("Y")) && status != 4){%>
                                                                        <div class="generate_btn">
                                                                            <a href="javascript:;" onclick="getGenerateCV('<%=info.getCandidateId()%>')">Generate CV</a>
                                                                        </div>
                                                                        <%}%>
                                                                    </div>
                                                                    <div class="col-lg-3 col-md-3 col-sm-6 col-12 text-left flex-end align-items-end edit_sec">
                                                                        <div class="edit_btn pull_right float-end">
                                                                            <% if(editper.equals("Y") && info.getStatus() != 4)  {%> <a href="javascript: modifyForm1();"><img src="../assets/images/edit.png"></a> <% } %>
                                                                        </div>
                                                                        <ul class="resume_attach">
                                                                            <li><label class="form_label">Resume (5MB)</label></li>
                                                                            <li><a href="javascript:;" class="attache_btn"><i class="fas fa-paperclip"></i> Attachment <span class="attach_number"> <%=talentpool.changeNum(info.getFilecount(),3)%></span></a></li>
                                                                            <li>
                                                                                <div class="down_prev">
                                                                                    <% if(info.getFilecount() > 0) {%>
                                                                                    <a href="/jxp/talentpool/download.jsp?candidateId=<%=talentpool.cipher(info.getCandidateId()+"")%>&fn=<%=info.getFirstname().replaceAll("['\"&\\s]", "")%>" class="" target="_blank">
                                                                                        Download All
                                                                                    </a><span class="dot_ic"></span>
                                                                                    <a href="javascript:;" onclick="javascript: viewimg('<%=info.getCandidateId()%>', '<%=info.getFirstname().replaceAll("['\"&\\s]", "")%>');" class="" data-bs-toggle="modal" data-bs-target="#view_resume_list">Preview All</a><%} else {%>&nbsp;<%}%>
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
                                                                <label class="form_label">Age</label>
                                                                <span class="form-control"><%= info.getAge() > 0 ? info.getAge() : "&nbsp;"%></span>
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
                                                                <label class="form_label">State</label>
                                                                <span class="form-control"><%= info.getStateName() != null && !info.getStateName().equals("") ? info.getStateName() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">City</label>
                                                                <span class="form-control"><%= info.getCity() != null && !info.getCity().equals("") ? info.getCity() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Pin Code</label>
                                                                <span class="form-control"><%= info.getPinCode() != null && !info.getPinCode().equals("") ? info.getPinCode() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Nationality</label>
                                                                <span class="form-control"><%= info.getNationality() != null && !info.getNationality().equals("") ? info.getNationality() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Religion</label>
                                                                <span class="form-control"><%= info.getReligion() != null && !info.getReligion().equals("") ? info.getReligion() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Additional Contact No.</label>
                                                                <div class="row">
                                                                    <div class="col-lg-3"> <span class="form-control"><%= info.getCode3Id() != null && !info.getCode3Id().equals("") ? info.getCode3Id() : "&nbsp;" %></span></div>
                                                                    <div class="col-lg-9"> <span class="form-control"><%= info.getContactno3() != null && !info.getContactno3().equals("") ? info.getContactno3() : "&nbsp;" %></span></div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Emergency Contact No. 1</label>
                                                                <div class="row">
                                                                    <div class="col-lg-3"><span class="form-control"><%= info.getEcode1Id() != null && !info.getEcode1Id().equals("") ? info.getEcode1Id() : "&nbsp;" %></span></div>
                                                                    <div class="col-lg-9"> <span class="form-control"><%= info.getEcontactno1() != null && !info.getEcontactno1().equals("") ? info.getEcontactno1() : "&nbsp;" %></span></div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Emergency Contact No. 2</label>
                                                                <div class="row">
                                                                    <div class="col-lg-3"> <span class="form-control"><%= info.getEcode2Id() != null && !info.getEcode2Id().equals("") ? info.getEcode2Id() : "&nbsp;" %></span></div>
                                                                    <div class="col-lg-9"><span class="form-control"><%= info.getEcontactno2() != null && !info.getEcontactno2().equals("") ? info.getEcontactno2() : "&nbsp;" %></span></div>
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
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4">
                                                                <div class="row">
                                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                                        <label class="form_label">Asset Type</label>
                                                                        <span class="form-control m_37"><%= info.getAssettype() != null && !info.getAssettype().equals("") ? info.getAssettype() : "&nbsp;" %></span>
                                                                        <div class="textfield_mg">
                                                                            <label class="form_label">Employee ID</label>
                                                                            <span class="form-control"><%= info.getEmployeeId() != null && !info.getEmployeeId().equals("") ? info.getEmployeeId() : "&nbsp;" %></span>                                                    
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">	
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                                <div class="row flex-end align-items-end">
                                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                                        <label class="form_label">Preferred Department</label>
                                                                        <span class="form-control"><%= info.getDepartment() != null && !info.getDepartment().equals("") ? info.getDepartment() : "&nbsp;" %></span>
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
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Applying Job For</label>
                                                                <span class="form-control"><%= info.getApplytypevalue() != null && !info.getApplytypevalue().equals("") ? info.getApplytypevalue() : "&nbsp;" %></span>
                                                            </div> 
                                                            
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Primary Position</label>
                                                                <span class="form-control"><%= info.getPosition() != null && !info.getPosition().equals("") ? info.getPosition() : "&nbsp;" %></span>
                                                            </div> 
                                                            <%if(info.getPositionname() != null && !info.getPositionname().equals("")) {%>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Secondary Position</label>
                                                                <span class="form-control"><%= info.getPositionname() != null && !info.getPositionname().equals("") ? info.getPositionname() : "&nbsp;" %></span>
                                                            </div> 
                                                            <%}%>
                                                            
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Travel Days</label>
                                                                <span class="form-control"><%= info.getTravelDays() > 0 ? info.getTravelDays(): "&nbsp;" %></span>
                                                            </div> 
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Nearest International Airport</label>
                                                                <span class="form-control"><%= info.getAirport1() != null && !info.getAirport1().equals("") ? info.getAirport1() : "&nbsp;" %></span>
                                                            </div> 
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Nearest Domestic Airport</label>
                                                                <span class="form-control"><%= info.getAirport2() != null && !info.getAirport2().equals("") ? info.getAirport2() : "&nbsp;" %></span>
                                                            </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Professional Profile</label>
                                                                <span class="form-control"><%= info.getProfile() != null && !info.getProfile().equals("") ? info.getProfile() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Strengths and Skills</label>
                                                                <span class="form-control"><%= info.getSkill1() != null && !info.getSkill1().equals("") ? info.getSkill1() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Additional Skills</label>
                                                                <span class="form-control"><%= info.getSkill2() != null && !info.getSkill2().equals("") ? info.getSkill2() : "&nbsp;" %></span>
                                                            </div>
<%--                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Onboarding Date</label>
                                                                <span class="form-control"><%= info.getDate() != null && !info.getDate().equals("") ? info.getDate() : "&nbsp;" %></span>
                                                            </div>                                                             --%>
                                                            <%
                                                                if(info.getClientId()> 0 && (addper.equals("Y") || editper.equals("Y"))){
                                                            %>
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12"><hr></div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 short_list_area tran_ter_area">
                                                                <div class="row client_head_search">
                                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12"><h2>CURRENT EMPLOYMENT <span> - <%= info.getClientName() != null && !info.getClientName().equals("") ? info.getClientName() : "&nbsp;" %> || <%=info.getAssetName() != null && !info.getAssetName().equals("") ? info.getAssetName() : "&nbsp;" %></span></h2></div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <div class="row">
                                                                    <div class="col-lg-6 col-md-6 hand_cursor"><a onclick="javascript: setClients();" data-bs-toggle="modal" data-bs-target="#transfer_modal" class="trans_btn">Transfer</a></div>
                                                                    <div class="col-lg-6 col-md-6 hand_cursor"><a onclick="javascript: setAssets();" data-bs-toggle="modal" data-bs-target="#terminate_modal" class="termi_btn">Close Assignment</a></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <%}%>
                                                    </div>                                                           
                                                <%
                                                    if (info.getClientId() <= 0 && info.getProgressId() <= 0 && (addper.equals("Y") || editper.equals("Y")) && status != 4) {
                                                %>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12"><hr></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <div class="row">
                                                        <div class="col-lg-6 col-md-6 hand_cursor"><a onclick="javascript: setClientDDLModal();" data-bs-toggle="modal" data-bs-target="#onboardRemark_modal" class="trans_btn">Onboard</a></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <%}%>
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
<%@include file ="../footer.jsp"%>
<div id="view_resume_list" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
            </div>
            <div class="modal-body">
                <div><a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen r_f_screen">Full Screen</a></div>
                <div class="row" id="viewfilesdiv">
                </div>
            </div>
        </div>
    </div>
</div>
<div id="thank_you_modal" class="modal fade thank_you_modal blur_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12">
                        <h2>Thank You!</h2>
                        <center><img src="../assets/images/thank-you.png"></center>
                        <h3>Basic Enrollment Completed</h3>
                        <p>You may now find this candidate in the</p>
                        <a href="javascript: goback();" class="msg_button" style="text-decoration: underline;">Enroll Candidate</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="thank_you_modal1" class="modal fade thank_you_modal blur_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12">
                        <h2>Thank You!</h2>
                        <center><img src="../assets/images/thank-you.png"></center>
                        <h3>Candidate Transfer Complete</h3>
                        <p>Find the candidate in Mobilization module to complete joining formalities</p>
                        <a href="../mobilization/MobilizationAction.do?clientId=<%=info.getClientId()%>&candidateId=<%=info.getCandidateId()%>" target="_blank"><b>Mobilization</b><br/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="thank_you_modal2" class="modal fade thank_you_modal blur_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12">
                        <h2>Thank You!</h2>
                        <center><img src="../assets/images/thank-you.png"></center>
                        <h3>Candidate Assignment Closed</h3>
                        <p>Find the candidate in Talent Pool as available to opt new jobs</p>
                        <a href="javascript: goback();" class="msg_button" style="text-decoration: underline;">Talent Pool</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%
    if (info.getClientId() > 0 && (addper.equals("Y") || editper.equals("Y"))) {
%>

<!---------------------------------------------------------------------------------------->
<div id="transfer_modal" class="modal fade parameter_modal large_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12">
                        <input type="hidden" name="clientassetId" value="<%=info.getToAssetId()%>"/>
                        <input type="hidden" name="clientId" value ="<%=info.getClientId()%>"/>
                        <h2>TRANSFER</h2>
                        <div class="row client_position_table">
                            <div class="col-lg-6 col-md-6 col-sm-6 col-12">
                                <div class="row">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">From Client</label>
                                        <span class="form-control"><%= info.getClientName() != null && !info.getClientName().equals("") ? info.getClientName() : "&nbsp;"%></span>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">From Asset</label>
                                        <span class="form-control"><%= info.getAssetName() != null && !info.getAssetName().equals("") ? info.getAssetName() : "&nbsp;"%></span>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">From Position</label>
                                        <span class="form-control"><%= info.getPosition() != null && !info.getPosition().equals("") ? info.getPosition() : "&nbsp;"%></span>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4">
                                        <div class="row">
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Current Day Rate</label>
                                                <span class="form-control"><%= info.getCurrency() != null && !info.getCurrency().equals("") ? info.getCurrency() : "&nbsp;"%></span>
                                            </div>
                                            <div class="col-lg-6 col-md-6 col-sm-6 col-4 form_group">
                                                <label class="form_label">&nbsp;</label>
                                                <span class="form-control"><%= talentpool.getDecimal(p1rate)%></span>
                                            </div>
                                        </div>
                                    </div>
                                     <% if(info.getPositionname() != null && !info.getPositionname().equals("")) {%>       
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                            <label class="form_label">Secondary Position</label>
                                            <span class="form-control"><%= info.getPositionname() != null && !info.getPositionname().equals("") ? info.getPositionname() : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-4">
                                            <div class="row">
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Current Day Rate</label>
                                                    <span class="form-control"><%= info.getCurrency() != null && !info.getCurrency().equals("") ? info.getCurrency() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-6 col-md-6 col-sm-6 col-4 form_group">
                                                    <label class="form_label">&nbsp;</label>
                                                    <span class="form-control"><%= talentpool.getDecimal(p2rate)%></span>
                                                </div>
                                            </div>
                                        </div>
                                    <%}%>  
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-6 col-12">
                                <div class="row">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">To Client<span class="required">*</span></label>
                                        <select class="form-select" name="toClientId" id="clientdiv" onchange="setAssets();">
                                            <option value="-1">Select Client</option>
                                        </select>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">To Asset<span class="required">*</span></label>
                                        <select class="form-select" name="toAssetId" id="assetdiv" onchange="setPositions();">
                                            <option value="-1">Select Asset</option>
                                        </select>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">To Position<span class="required">*</span></label>
                                        <select class="form-select" name="toPositionId" id="positiondiv" onchange="javascript : getPositions2();">
                                            <option value="-1">Select Position | Rank</option>
                                        </select>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4">
                                        <div class="row">
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Transfer Day Rate<span class="required">*</span></label>
                                                <input type="hidden" name="toCurrencyId" value=""/>
                                                <span class="form-control" id="currencydiv">&nbsp;</span>
                                            </div>
                                            <div class="col-lg-6 col-md-6 col-sm-6 col-4 form_group">
                                                <label class="form_label">&nbsp;</label>
                                                <input type="text" name="torate1" id="torate1" value="" class="form-control" placeholder="" onkeypress="return allowPositiveNumber(event);"/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Overtime/Hr</label>
                                                <input type="text" name="torate2" id="torate2" value="" class="form-control" placeholder="" onkeypress="return allowPositiveNumber(event);"/>
                                            </div>
                                            <div class="col-lg-6 col-md-6 col-sm-6 col-4 form_group">
                                                <label class="form_label">Allowances</label>
                                                <input type="text" name="torate3" id="torate3" value="" class="form-control" placeholder="" onkeypress="return allowPositiveNumber(event);"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-4 form_group">
                                        <label class="form_label">Joining Date<span class="required">*</span></label>
                                        <div class="input-daterange input-group">
                                            <input type="text" name="joiningDate1" value="" id="joiningDate1" class="form-control add-style wesl_dt date-add" placeholder="DD-MMM-YYYY">
                                        </div>
                                    </div>
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-4 form_group">
                                        <label class="form_label">End Date</label>
                                        <div class="input-daterange input-group">
                                            <input type="text" name="endDate1" value="" id="endDate1" class="form-control add-style wesl_dt date-add" placeholder="DD-MMM-YYYY">
                                        </div>
                                    </div>
                                    <div class="row">                                    
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">To Secondary Position</label>
                                        <select class="form-select" name="toPositionId2" id="positiondiv2" onchange="javascript: showratediv();">
                                            <option value="-1">Select Position | Rank</option>
                                        </select>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4" style="display: none" id="rateDiv">
                                        <div class="row">
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Transfer Day Rate</label>
                                                <input type="hidden" name="toCurrencyId2" value=""/>
                                                <span class="form-control" id="currencydiv2">&nbsp;</span>
                                            </div>
                                            <div class="col-lg-6 col-md-6 col-sm-6 col-4 form_group">
                                                <label class="form_label">&nbsp;</label>
                                                <input type="text" name="top2rate1" id="top2rate1" value="" class="form-control" placeholder="" onkeypress="return allowPositiveNumber(event);"/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Overtime/Hr</label>
                                                <input type="text" name="top2rate2" id="top2rate2" value="" class="form-control" placeholder="" onkeypress="return allowPositiveNumber(event);"/>
                                            </div>
                                            <div class="col-lg-6 col-md-6 col-sm-6 col-4 form_group">
                                                <label class="form_label">Allowances</label>
                                                <input type="text" name="top2rate3" id="top2rate3" value="" class="form-control" placeholder="" onkeypress="return allowPositiveNumber(event);"/>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 col-md-6 col-sm-12 col-4 form_group">                                    
                                            <label class="form_label">Joining Date<span class="required">*</span></label>
                                            <div class="input-daterange input-group">
                                                <input type="text" name="joiningDate2" value="" id="joiningDate2" class="form-control add-style wesl_dt date-add" placeholder="DD-MMM-YYYY">
                                            </div>
                                        </div>
                                        <div class="col-lg-6 col-md-6 col-sm-12 col-4 form_group">
                                            <label class="form_label">End Date</label>
                                            <div class="input-daterange input-group">
                                                <input type="text" name="endDate2" value="" id="endDate2" class="form-control add-style wesl_dt date-add" placeholder="DD-MMM-YYYY">
                                            </div>
                                        </div>
                                    </div>                                    
                                </div>
                                    
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">Remarks<span class="required">*</span></label>
                                        <html:textarea property="transferRemark" rows="6" styleId="transferRemark" styleClass="form-control"></html:textarea>
                                        <script type="text/javascript">
                                            document.getElementById("transferRemark").setAttribute('placeholder', '');
                                            document.getElementById("transferRemark").setAttribute('maxlength', '1000');
                                        </script>
                                    </div>
                                </div>
                                
                            </div>
                        </div>	
                        <div class="row">	
                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center">
                                <div class="row justify-content-end" id="submitdiv">
                                    <div class="col-lg-6 col-md-6">
                                        <a class="trans_btn hand_cursor" data-bs-dismiss="modal">Do Not Transfer</a>
                                    </div>
                                    <div class="col-lg-6 col-md-6">
                                        <a href="javascript: submitTransferForm('<%=info.getCandidateId()%>');" class="termi_btn">Transfer</a>
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
<!---------------------------------------------------------------------------------------->
<div id="terminate_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12">

                        <h2>CLOSE ASSIGNMENT</h2>
                        <div class="row client_position_table">
                            <div class="col-lg-6 col-md-6 col-sm-12 col-4 form_group">
                                <label class="form_label">Joining Date</label>
                                <div class="input-daterange input-group">
                                    <input type="text" name="joiningDate" value="" id="joiningDate" class="form-control add-style wesl_dt date-add" placeholder="DD-MMM-YYYY">
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-12 col-4 form_group">
                                <label class="form_label">End Date</label>
                                <div class="input-daterange input-group">
                                    <input type="text" name="endDate" value="" id="endDate" class="form-control add-style wesl_dt date-add" placeholder="DD-MMM-YYYY">
                                </div>
                            </div>
                            <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                <label class="form_label">Reasons</label>
                                <html:select styleClass="form-select" property="reason">
                                    <html:option value="">- Select -</html:option>
                                    <html:option value="Transfer">Transfer</html:option>
                                    <html:option value="Terminate">Terminate</html:option>
                                    <html:option value="Resign">Resign</html:option>
                                    <html:option value="Contract Over">Contract Over</html:option>
                                    <html:option value="Deceased">Deceased</html:option>
                                    <html:option value="Medical Unfit">Medical Unfit</html:option>
                                    <html:option value="Temporary Assignment">Temporary Assignment</html:option>
                                    <html:option value="Temporary Transfer">Temporary Transfer</html:option>
                                    <html:option value="Other">Other</html:option>
                                </html:select>
                            </div>
                            <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                <label class="form_label">Remarks</label>
                                <html:textarea property="terminateRemark" rows="5" styleId="terminateRemark" styleClass="form-control"></html:textarea>
                                <script type="text/javascript">
                                    document.getElementById("terminateRemark").setAttribute('placeholder', '');
                                    document.getElementById("terminateRemark").setAttribute('maxlength', '1000');
                                </script>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                <label class="form_label">Attach Document</label>                                                   
                                <html:file property="terminatefile" styleId="upload2" onchange="javascript: setClass('2');"/>
                                <a href="javascript:;" id="upload_link_2" class="attache_btn uploaded_img1"><i class="fas fa-paperclip"></i> Attach</a>
                                <% if (!filename.equals("")) {%><div class="down_prev"  id='preview_1'><a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript: setIframeedit('<%=filename%>');">Preview</a></div><% }%>
                            </div>
                        </div>	
                        <div class="row">	
                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center" id="submitdiv">
                                <div class="row">
                                    <div class="col-lg-6 col-md-6"><a class="trans_btn hand_cursor" data-bs-dismiss="modal">Cancel</a></div>
                                    <div class="col-lg-6 col-md-6"><a href="javascript: submitTerminateForm('<%=info.getCandidateId()%>');" class="termi_btn">Close Assignment</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="view_pdf" class="modal fade" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                <span class="resume_title"> File</span>
                <div class="float-end">
                    <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                    <a id='diframe' href="" class="down_btn" download=""><img src="../assets/images/download.png"/></a>
                    <a id='diframedel' href="javascript: delfileedit('1');" class="trash_icon"><img src="../assets/images/trash.png"/></a>
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
<%}%>
<!--For Onboarding remarks-->
<%
    if (info.getClientId() <= 0 && info.getProgressId() <= 0 && (addper.equals("Y") || editper.equals("Y"))) {
%>
<div id="onboardRemark_modal" class="modal fade parameter_modal large_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
            </div>
            <div class="modal-body" id="onboard_remark">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="row">
                            <div class="col-lg-7 col-md-7 col-sm-7 col-12">
                                <h2>REMARKS</h2>
                            </div>
                            <div class="col-lg-5 col-md-5 col-sm-5 col-12">
                                <h2>ONBOARD</h2>
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-lg-7 col-md-7 col-sm-7 col-12 client_position_table">
                                <div class="col-lg-12 all_client_sec" id="printArea">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                        <div class="table-responsive table-detail">
                                            <table class="table table-striped mb-0">
                                                <thead>
                                                    <tr>
                                                        <th width="15%">Date</th>
                                                        <th width="33%">Client/Asset</th>
                                                        <th width="19%">Remark Type</th>
                                                        <th width="33%">Remark</th>
                                                    </tr>
                                                </thead>
                                                <tbody class="vertical_top">
<%
                                            if(offshore_list_size > 0)
                                            {
                                                TalentpoolInfo ainfo;
                                                
                                                for(int i = 0; i < offshore_list_size; i++)
                                                {
                                                    ainfo = (TalentpoolInfo) offshore_list.get(i);
                                                    if(ainfo != null)
                                                    {   
                                                        String client = ainfo.getClientName() != null ? ainfo.getClientName() : "";
                                                        String asset = ainfo.getAssetName() != null ? ainfo.getAssetName() : "";
%>
                                                    <tr>
                                                        <td><%= ainfo.getDate() != null ? ainfo.getDate() : "" %></td>
                                                        <td><%= client+ " / "+ asset%></td>                                                   
                                                        <td><%= ainfo.getTypeName() != null ? ainfo.getTypeName() : "" %></td>
                                                        <td><%= ainfo.getRemarks() != null ? ainfo.getRemarks() : "" %></td>                                                        
                                                    </tr>                                                                
<%
                                                        }
                                                    }
                                                }
                                                else
                                                {
%>
                                                    <tr><td colspan='5'>No remarks available.</td></tr>
<%
                                                }
%>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-5 col-md-5 col-sm-5 col-12">
                                <div class="row">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">Client</label>
                                        <select name="clientIdModal" id="clientIdModal" class="form-select" onchange="javascript: setAssetDDLModal();">                                            
                                        </select>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">Asset</label>
                                        <select name="assetIdModal" id="assetIdModal" class="form-select" onchange="javascript: setPositionDDLModal();">                                            
                                        </select>
                                    </div>   
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">Position</label>
                                        <select name="positionIdModal" id="positionIdModal" class="form-select">                                            
                                        </select>
                                    </div>
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-4 form_group">
                                        <label class="form_label">Onboarding Date</label>
                                        <div class="input-daterange input-group">
                                            <input type="text" name="date" value="<%=date2%>" id="date" class="form-control add-style wesl_dt date-add" placeholder="DD-MMM-YYYY">
                                        </div>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">Remarks</label>
                                        <html:textarea property="onboardRemark" rows="5" styleId="onboardRemark" styleClass="form-control"></html:textarea>
                                            <script type="text/javascript">
                                                document.getElementById("onboardRemark").setAttribute('placeholder', '');
                                                document.getElementById("onboardRemark").setAttribute('maxlength', '500');
                                            </script>
                                        </div>
                                    </div>                                    
                                </div>
                            </div>
                        </div>
                    </div>	
                    <div class="row">	
                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center" id="submitdiv">
                        <div class="row">
                            <div class="col-lg-6 col-md-6">
                                <a class="trans_btn hand_cursor" data-bs-dismiss="modal">Do Not Onboard</a>
                            </div>
                            <div class="col-lg-6 col-md-6">
                                <a href="javascript: submitOnboard('<%=info.getCandidateId()%>');" class="termi_btn">Onboard</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%}%>
<script src="../assets/libs/jquery/jquery.min.js"></script>		
<script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
<script src="../assets/libs/metismenu/metisMenu.min.js"></script>
<script src="../assets/js/app.js"></script>	
<script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>
<script src="../assets/js/bootstrap-select.min.js"></script>
<script src="/jxp/assets/js/sweetalert2.min.js"></script>
<script src="../assets/js/bootstrap-datepicker.min.js"></script>
<% if (thankyou.equals("yes")) {%>
<script type="text/javascript">
    $(window).on('load', function () {
        $('#thank_you_modal').modal('show');
    });
</script>
<%}
if (thankyou1.equals("yes")) {%>
<script type="text/javascript">
    $(window).on('load', function () {
        $('#thank_you_modal1').modal('show');
    });
</script>
<%}
            if (thankyou2.equals("yes")) {%>
<script type="text/javascript">
    $(window).on('load', function () {
        $('#thank_you_modal2').modal('show');
    });
</script>
<%}%>
<script>
    // toggle class show hide text section
    $(document).on('click', '.toggle-title', function () {
        $(this).parent()
                .toggleClass('toggled-on')
                .toggleClass('toggled-off');
    });
</script>
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
    function setIframeedit(uval)
    {
        var url_v = "", classname = "";
        if (uval.includes(".doc") || uval.includes(".docx") || uval.includes("wordprocessingml.document"))
        {
            url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
            classname = "doc_mode";
        } else if (uval.includes(".pdf"))
        {
            url_v = uval + "#toolbar=0&page=1&view=fitH,100";
            classname = "pdf_mode";
        } else
        {
            url_v = uval;
            classname = "img_mode";
        }
        window.top.$('#iframe').attr('src', 'about:blank');
        setTimeout(function () {
            window.top.$('#iframe').attr('src', url_v);
            document.getElementById("iframe").className = classname;
            document.getElementById("diframe").href = uval;
        }, 1000);
        $("#iframe").on("load", function () {
            let head = $("#iframe").contents().find("head");
            let css = '<style>img{margin: 0px auto;}</style>';
            $(head).append(css);
        });
    }
</script>
<script>
    $(function () {
        $("#upload_link_2").on('click', function (e) {
            e.preventDefault();
            $("#upload2:hidden").trigger('click');
        });
    });
</script>
</html:form>

</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>