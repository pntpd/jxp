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
            int mtp = 2, submtp = 4, ctp = 14;
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
            String file_path = talentpool.getMainPath("view_candidate_file");
                ArrayList shortlisting_list = new ArrayList();
            if(request.getAttribute("SHORTLISTINGLIST") != null)
                shortlisting_list = (ArrayList) request.getAttribute("SHORTLISTINGLIST");
            int shortlisting_list_size = shortlisting_list.size();
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
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/talentpool.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="cassessmentId"/>
        <html:hidden property="pAssessmentId"/>
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
                                        <li><a href="javascript: printPage('printArea');"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                        <li><a href="javascript: exporttoexcelnew('13');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
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
                                <div class="body-background tab2_body">
                                    <div class="row d-none1">
                                        <div class="col-lg-12">
                                            <div class="tab-content">
                                                <div class="tab-pane active">
                                                    <div class="m_30">
                                                        <div class="row">
                                                            <div class="main-heading">
                                                                <div class="add-btn">
                                                                    <h4>SHORTLISTING HISTORY</h4>
                                                                </div>
                                                                <div class="edit_btn pull_right float-end ver_shiled_area">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-12 all_client_sec" id="all_client">
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group" id="printArea">
                                                                <div class="table-responsive table-detail">
                                                                    <table class="table table-striped mb-0">
                                                                        <thead>
                                                                            <tr>
                                                                                <th width="10%">Ref</th>
                                                                                <th width="10%">Posted On</th>
                                                                                <th width="25%">Client - Asset</th>
                                                                                <th width="25%">Position - Rank</th>
                                                                                <th width="10%">Shortlisted On</th>
                                                                                <th width="10%">Shortlisted By</th>
                                                                                <th width="10%">Post Status</th>

                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <%
                                                                            if(shortlisting_list_size > 0)
                                                                            {
                                                                                TalentpoolInfo ainfo;
                                                                                for(int i = 0; i < shortlisting_list_size; i++)
                                                                                {
                                                                                    ainfo = (TalentpoolInfo) shortlisting_list.get(i);
                                                                                    if(ainfo != null)
                                                                                    {
                                                                            %>
                                                                            <tr>
                                                                                <!--ainfo.getJobpostId()-->
                                                                                <td><%= ainfo.getCode() != null && !ainfo.getCode().equals("") ? ainfo.getCode() : "&nbsp;" %></td>
                                                                                <td><%= ainfo.getPoston() != null && !ainfo.getPoston().equals("") ? ainfo.getPoston() : "&nbsp;" %></td>
                                                                                <td><%= ainfo.getClientName() != null && !ainfo.getClientName().equals("") ? ainfo.getClientName() : "" %> - <%= ainfo.getClientAsset() != null && !ainfo.getClientAsset().equals("") ? ainfo.getClientAsset() : "" %></td>
                                                                                <td><%= ainfo.getPosition() != null && !ainfo.getPosition().equals("") ? ainfo.getPosition() : "" %> - <%= ainfo.getGradename() != null && !ainfo.getGradename().equals("") ? ainfo.getGradename() : "" %></td>
                                                                                <td><%= ainfo.getShortliston() != null && !ainfo.getShortliston().equals("") ? ainfo.getShortliston() : "&nbsp;" %></td>
                                                                                <td><%= ainfo.getName() != null && !ainfo.getName().equals("") ? ainfo.getName() : "&nbsp;" %></td>
                                                                                <td><%= ainfo.getStatusvalue() != null && !ainfo.getStatusvalue().equals("") ? ainfo.getStatusvalue() : "&nbsp;" %></td>
                                                                            </tr>  
                                                                            <%
                                                                                    }
                                                                                }
                                                                            }
                                                                            else
                                                                            {
                                                                            %>
                                                                            <tr><td colspan='7'>No shortlisting details available.</td></tr>
                                                                            <%
                                                                            }
                                                                            %>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>	
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                                <a href="javascript:;" class="filter_btn"><i class="mdi mdi-filter-outline"></i> Filter</a>
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