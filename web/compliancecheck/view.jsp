<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.compliancecheck.CompliancecheckInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="compliancecheck" class="com.web.jxp.compliancecheck.Compliancecheck" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 2, submtp = 47;
            String per = "N", addper = "N", editper = "N", deleteper = "N",approveper="N";
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
                    approveper = uinfo.getApproveper() != null ? uinfo.getApproveper() : "N";
                }
            }
                ArrayList candidate_list = new ArrayList();
                int count = 0;
                int recordsperpage = compliancecheck.getCount();
                if(session.getAttribute("COUNT_LIST") != null)
                    count = Integer.parseInt((String) session.getAttribute("COUNT_LIST"));
                if(session.getAttribute("CC_LIST") != null)
                    candidate_list = (ArrayList) session.getAttribute("CC_LIST");
                    
                CompliancecheckInfo cinfo = null;
                if(session.getAttribute("CANDIDATEC_DETAIL") != null)
                {
                    cinfo = (CompliancecheckInfo)session.getAttribute("CANDIDATEC_DETAIL");   
                }

                CompliancecheckInfo jpinfo = null;
                if(session.getAttribute("JOBPOST_DETAIL") != null)
                {
                    jpinfo = (CompliancecheckInfo)session.getAttribute("JOBPOST_DETAIL");   
                }


                String file_path = compliancecheck.getMainPath("view_candidate_file");

                String photoname = cinfo.getPhotoName() != null && !cinfo.getPhotoName().equals("") ? file_path+cinfo.getPhotoName() : "../assets/images/empty_user_100x100.png";

                int pageSize = count / recordsperpage;
                if(count % recordsperpage > 0)
                    pageSize = pageSize + 1;
                int total = candidate_list.size();
                int showsizelist = compliancecheck.getCountList("show_size_list");
                int CurrPageNo = 1;
                String message = "", clsmessage = "deleted-msg";
                if (request.getAttribute("MESSAGE") != null)
                {
                    message = (String)request.getAttribute("MESSAGE");
                    request.removeAttribute("MESSAGE");
                }    
                if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                    clsmessage = "updated-msg";
    %>
    <head>
        <meta charset="utf-8">
        <title><%= compliancecheck.getMainPath("title") != null ? compliancecheck.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/compliancecheck.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/compliancecheck/CompliancecheckAction.do" onsubmit="return false;">
        <html:hidden property="doSave"/>
        <html:hidden property="doView"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="candidateId"/>
        <html:hidden property="jobpostId"/>
        <html:hidden property="shortlistId"/>
        <html:hidden property="status"/>
        <html:hidden property="listsize"/>
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <div class="main-content">
                <div class="page-content tab_panel1 no_tab1">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow">
                                <a href="javascript: goback();"><img src="../assets/images/back-arrow.png"/></a>
                                <span>Compliance Checks</span>
                            </div>
                            <div class="float-end">
                                <div class="toggled-off usefool_tool">
                                    <div class="toggle-title">
                                        <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                        <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>
                                    </div>
                                    <div class="toggle-content">
                                        <h4>Useful Tools</h4>
                                        <%@include file ="../shortcutmenu.jsp"%>
                                    </div>
                                </div>
                            </div>
                        </div>	
                    </div>

                    <div class="container-fluid pd_0">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 pd_0">
                                <div class="body-background com_checks">
                                    <div class="row com_checks_main">
                                        <div class="col-md-6 com_top_left">
                                            <div class="row d-flex align-items-center">
                                                <div class="col-md-3 com_view_prof">
                                                    <div class="user_photo pic_photo">
                                                        <img src="../assets/images/user.png">
                                                        <div class="upload_file">
                                                            <input id="upload1" type="file">
                                                            <!-- <img src="../assets/images/empty_user.png"> -->
                                                            <img src="<%=photoname%>">
                                                            <a href="../talentpool/TalentpoolAction.do?doView=yes&candidateId=<%=cinfo.getCandidateId()%>" target="_blank"><img src="../assets/images/view.png"></a>
                                                        </div>
                                                    </div>	
                                                </div>
                                                <div class="col-md-9 comp_view">
                                                    <div class="row">
                                                        <div class="col-md-12 mb_0">
                                                            <img src="../assets/images/full_name.png"/>
                                                            <span><%= cinfo.getName() != null ? cinfo.getName() : ""%></span>
                                                        </div>

                                                        <div class="col-md-12 mb_0">
                                                            <img src="../assets/images/star.png"/>
                                                            <span><%= cinfo.getPosition() != null ? cinfo.getPosition() : ""%></span>
                                                        </div>

                                                        <div class="col-md-12 mb_0">
                                                            <img src="../assets/images/experience.png"/>
                                                            <span><%= cinfo.getExperience()%> Yrs</span>
                                                        </div>

                                                        <div class="col-md-12 mb_0">
                                                            <img src="../assets/images/education.png"/>
                                                            <span><%= cinfo.getDegree() != null ? cinfo.getDegree()+" " : ""%><%= cinfo.getQualificationType() != null ? cinfo.getQualificationType() : ""%></span>
                                                        </div>

                                                        <div class="col-md-12">
                                                            <img src="../assets/images/bag.png"/>
                                                            <span><%= cinfo.getCompanyName() != null ? cinfo.getCompanyName() : ""%></span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6 com_top_right">
                                            <div class="row d-flex align-items-center">
                                                <div class="col-md-10 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Posting Date</label></div>
                                                        <div class="col-md-9"><span><%= jpinfo.getDate() != null ? jpinfo.getDate() : ""%></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Position-Rank</label></div>
                                                        <div class="col-md-9"><span><%= jpinfo.getPosition() != null ? jpinfo.getPosition() : ""%></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Client - Asset</label></div>
                                                        <div class="col-md-9"><span><%= jpinfo.getClientName() != null ? jpinfo.getClientName() : ""%> - <%= jpinfo.getAssetName() != null ? jpinfo.getAssetName() : ""%></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Education</label></div>
                                                        <div class="col-md-9"><span><%= jpinfo.getQualificationType() != null ? jpinfo.getQualificationType() : ""%></span></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-3"><label>Experience</label></div>
                                                        <div class="col-md-9"><span><%= jpinfo.getExpMin()%> Yrs</span></div>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_ref_no"><span>Ref. <strong><%= compliancecheck.changeNum( jpinfo.getJobpostId(),5) %></strong></span></li>
                                                            <li class="com_view_job"><a href="../jobpost/JobPostAction.do?doView=yes&jobpostId=<%=jpinfo.getJobpostId()%>" target="_blank"><img src="../assets/images/view.png"/><br/> View Job Post</a></li>
                                                            <li class="com_ope_job">Opening <span><%= compliancecheck.changeNum( jpinfo.getOpenings(),2)%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="comliance_list">
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                <div class="table-responsive table-detail">
                                                    <table class="table table-striped mb-0">
                                                        <thead>
                                                            <tr>
                                                                <th width="65%">Compliance</th>
                                                                <th width="20%">Checked By</th>
                                                                <th width="15%" class="text-right">Actions</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <%
                                                                                                                    int status;
                                                                                                                    CompliancecheckInfo info;
                                                                                                                    for (int i = 0; i < total; i++)
                                                                                                                    {
                                                                                                                        info = (CompliancecheckInfo) candidate_list.get(i);
                                                                                                                        if (info != null) 
                                                                                                                        {
                                                                                                                            status = info.getStatus();
                                                            %>
                                                            <tr>
                                                                <td><%= info.getCheckpointName() != null ? info.getCheckpointName() : ""%></td>
                                                                <td><%= info.getUserName() != null ? info.getUserName() : ""%></td>
                                                                <td class="action_column">
                                                                    <a class="mr_15" href="javascript:;" data-bs-toggle="modal" data-bs-target="#verify_modal" onclick=" javascript: getModel('<%=info.getNshortlistccId()%>', '<%=info.getCheckpointId()%>');"><img src="../assets/images/<%= compliancecheck.getVerifiedImage(info.getStatus())%>"></a>
                                                                </td>
                                                            </tr>
                                                            <%
                                                                                                                        }
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
        <%@include file ="../footer.jsp"%>
        <div id="verify_modal" class="modal fade verify_modal com_pdf1 blur_modal 1" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row d-none1" id="dverify">
                            <div class="col-lg-12">
                                <h2>COMPLIANCE CHECKS</h2>
                                <div class="row ver_work_area">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <div class="mt-radio-list">
                                            <div class="form-check ">
                                                <input class="form-check-input" type="checkbox" value="1" id="invalidCheck_1" name="test">
                                                <label class="form-check-label" for="invalidCheck_1">
                                                    Checked
                                                </label>
                                            </div>
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="1" id="invalidCheck_2" name="test">
                                                <label class="form-check-label" for="invalidCheck_2">
                                                    Minimum Checked
                                                </label>
                                            </div>
                                        </div>
                                    </div>


                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">Instruction Checked</label>
                                        <span class="form-control">
                                            <b>Minimum Verification:</b><br/> 
                                            First name, last name. Position Applied for matches the resume experience/profile. Resume is updated with latest exploits and experience. <br/> <br/> 
                                            <b>Checked:</b> Candidate Address is valid. Department preferred is valid.

                                        </span>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">Remarks</label>
                                        <textarea class="form-control" rows="3"></textarea>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">Compliance Details</label>
                                        <div class="full_width veri_details">
                                            <ul>
                                                <li>11-Nov-2018 15:55</li>
                                                <li>Yash Hendulkar</li>
                                                <li>Lorem Ipsum is simply dummy text.</li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center">
                                        <a href="javascript:;" class="view_prof mr_15">View Profile</a>
                                        <a href="javascript:;" class="save_page"><img src="../assets/images/shield.png"> Checked</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="../assets/libs/jquery/jquery.min.js"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>
        <script src="../assets/js/table-responsive.init.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script> 
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