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
            int mtp = 2, submtp = 7, ctp = 1;        
            String per = "N", addper = "N", editper = "N", deleteper = "N";
            if (session.getAttribute("HOME_EMAILID") == null)
            {
    %>
    <jsp:forward page="/homeindex1.jsp"/>
    <%
            }
   
            String message = "", clsmessage = "deleted-msg";
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }
            if(message.toLowerCase().contains("success"))
            {
                    message = "";
            }
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";
            ArrayList list = new ArrayList();
            if(request.getSession().getAttribute("MODULEPER_LIST") != null)
                list = (ArrayList)request.getSession().getAttribute("MODULEPER_LIST");

                String file_path = candidate.getMainPath("view_candidate_file");
                    CandidateInfo info = null;
            if(session.getAttribute("CANDIDATE_DETAIL") != null)
            {
                info = (CandidateInfo)session.getAttribute("CANDIDATE_DETAIL");   
            }

            String thankyou = "no";
            if(request.getAttribute("CANDSAVEMODEL") != null)
            {
                //thankyou = (String)request.getAttribute("CANDSAVEMODEL");
                //request.removeAttribute("CANDSAVEMODEL");
            }
            int ipass=0;
            if(session.getAttribute("PASS") != null)
                ipass = Integer.parseInt((String)session.getAttribute("PASS"));
    %>
    <head>
        <meta charset="utf-8">
        <title><%= candidate.getMainPath("title") != null ? candidate.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
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
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <% if(!message.equals("")) {%><div class="alert <%=clsmessage%> alert-dismissible fade show">
                                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button><%=message%>
                                            </div><% } %>
                                            <div class="tab-content">
                                                <div class="tab-pane active">
                                                    <div class="">
                                                        <div class="row m_30  flex-end align-items-end">
                                                            <%
                                                            if(info != null) 
                                                            {
                                                                String view_path = candidate.getMainPath("view_candidate_file");
                                                                String cphoto = info.getPhoto() != null && !info.getPhoto().equals("") ? view_path+info.getPhoto() : "../assets/images/empty_user.png";
                                                                String resumefilename = info.getResumefilename() != null && !info.getResumefilename().equals("") ? view_path+info.getResumefilename() : "";
                                                                String certificate = "";
                                                            %>
                                                            <div class="col-lg-2 col-md-2 col-sm-2 col-5">
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
                                                            <div class="col-lg-10 col-md-8 col-sm-8 col-7">
                                                                <div class="row">
                                                                    <div class="col-lg-9 col-md-8 col-sm-8 col-12 candi_info">
                                                                        <ul>
                                                                            <li><span><%= info.getName() != null ? info.getName() : "" %></span></li>
                                                                            <% if(info.getPosition() != null && !info.getPosition().equals("")) {%><li><span><%= info.getPosition() %></span></li><% } %>
                                                                            <% if((info.getContactno1() != null && !info.getContactno1().equals(""))) {%><li><span><%= info.getCode1Id() != null ? info.getCode1Id() : "" %> <%= info.getContactno1() != null ? info.getContactno1() : "" %></span></li><% } %>
                                                                            <li><span><%= info.getEmailId() != null ? info.getEmailId() : "" %></span></li>
                                                                        </ul>
                                                                    </div>
                                                                    <div class="col-lg-3 col-md-3 col-sm-6 col-12 text-left flex-end align-items-end edit_sec">
                                                                        <div class="edit_btn pull_right float-end">
                                                                            <%
                                                                            if(ipass ==2){}else{
                                                                            %>
                                                                             <a href="javascript: modifyForm1();"><img src="../assets/images/edit.png"></a> 
                                                                                <%
                                                                                }
                                                                                %>
                                                                        </div>
                                                                        <ul class="resume_attach">
                                                                            <li><label class="form_label">Resume (5MB)</label></li>
                                                                            <li><a href="javascript:;" class="attache_btn"><i class="fas fa-paperclip"></i> Attachment <span class="attach_number" id="rcount"> <%=candidate.changeNum(info.getFilecount(),3)%></span></a></li>
                                                                            <li>
                                                                                <div class="down_prev">
                                                                                    <% if(info.getFilecount() > 0) {%><a href="/jxp/candidate/download.jsp?candidateId=<%=candidate.cipher(info.getCandidateId()+"")%>&fn=<%=info.getFirstname().replaceAll("['\"&\\s]", "")%>" class="" target="_blank">Download All</a><span class="dot_ic"></span>
                                                                                    <a href="javascript:;" onclick="javascript: viewimg('<%=info.getCandidateId()%>', '<%=info.getFirstname().replaceAll("['\"&\\s]", "")%>');" class="" data-bs-toggle="modal" data-bs-target="#view_resume_list">Preview All</a><%} else {%>&nbsp;<%}%>
                                                                                </div>
                                                                            </li>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-6 form_group">
                                                                <label class="form_label">Date of Birth</label>
                                                                <span class="form-control"><%= info.getDob() != null && !info.getDob().equals("") ? info.getDob() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-6 form_group">
                                                                <label class="form_label">Age</label>
                                                                <span class="form-control"><%= (info.getAge() > 0 ? info.getAge() : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-6 form_group">
                                                                <label class="form_label">Gender</label>
                                                                <span class="form-control"><%= info.getGender() != null && !info.getGender().equals("") ? info.getGender() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-6 form_group">
                                                                <label class="form_label">Marital Status</label>
                                                                <span class="form-control"><%= info.getMaritialstatus() != null && !info.getMaritialstatus().equals("") ? info.getMaritialstatus() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-6 form_group">
                                                                <label class="form_label">Country (Current residence)</label>
                                                                <span class="form-control"><%= info.getCountryName() != null && !info.getCountryName().equals("") ? info.getCountryName() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-6 form_group">
                                                                <label class="form_label">State</label>
                                                                <span class="form-control"><%= info.getStateName() != null && !info.getStateName().equals("") ? info.getStateName() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-6 form_group">
                                                                <label class="form_label">City</label>
                                                                <span class="form-control"><%= info.getCity() != null && !info.getCity().equals("") ? info.getCity() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-6 form_group">
                                                                <label class="form_label">Pin Code</label>
                                                                <span class="form-control"><%= info.getPinCode() != null && !info.getPinCode().equals("") ? info.getPinCode() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-6 form_group">
                                                                <label class="form_label">Nationality</label>
                                                                <span class="form-control"><%= info.getNationality() != null && !info.getNationality().equals("") ? info.getNationality() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-6 form_group">
                                                                <label class="form_label">Applying Job For</label>
                                                                <span class="form-control"><%= info.getApplytypevalue() != null && !info.getApplytypevalue().equals("") ? info.getApplytypevalue() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-6">
                                                                <div class="row">
                                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                                        <label class="form_label">Asset Type</label>
                                                                        <span class="form-control"><%= info.getAssettype() != null && !info.getAssettype().equals("") ? info.getAssettype() : "&nbsp;" %></span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                                <label class="form_label">Expected Salary(Per annum)</label>
                                                                <div class="row">
                                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-6">
                                                                        <span class="form-control"><%= info.getCurrency() != null && !info.getCurrency().equals("") ? info.getCurrency() : "&nbsp;" %></span>
                                                                    </div>
                                                                    <div class="col-lg-8 col-md-8 col-sm-8 col-6">
                                                                        <span class="form-control"><%= info.getExpectedsalary() > 0 ? info.getExpectedsalary() : "&nbsp;" %></span>
                                                                    </div>
                                                                </div> 
                                                                <% } %>
                                                            </div>
                                                        </div>	
                                                        <div class="row">	
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12">
                                                                <div class="row">
                                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                                        <label class="form_label">Permanent Address</label>
                                                                        <span class="form-control m_15"><%= info.getAddress1line1() != null && !info.getAddress1line1().equals("") ? info.getAddress1line1() : "&nbsp;" %></span>
                                                                        <span class="form-control m_15"><%= info.getAddress1line2() != null && !info.getAddress1line2().equals("") ? info.getAddress1line2() : "&nbsp;" %></span>
                                                                        <span class="form-control"><%= info.getAddress1line3() != null && !info.getAddress1line3().equals("") ? info.getAddress1line3() : "&nbsp;" %></span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-6">
                                                                <label class="form_label">Nearest International Airport</label>
                                                                <span class="form-control"><%= info.getAirport1() != null && !info.getAirport1().equals("") ? info.getAirport1() : "&nbsp;" %></span>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-6">
                                                                <label class="form_label">Nearest Domestic Airport</label>
                                                                <span class="form-control"><%= info.getAirport2() != null && !info.getAirport2().equals("") ? info.getAirport2() : "&nbsp;" %></span>
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
        <script src="../assets/libs/jquery/jquery.min.js"></script>		
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>	
        <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        <% if(thankyou.equals("yes")){%>
        <script type="text/javascript">
            $(window).on('load', function () {
                $('#thank_you_modal').modal('show');
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