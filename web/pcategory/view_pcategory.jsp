<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.pcategory.PcategoryInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="pcategory" class="com.web.jxp.pcategory.Pcategory" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 7, submtp = 83;
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
        PcategoryInfo info = null;
        if(session.getAttribute("PCATEGORY_DETAIL") != null)
            info = (PcategoryInfo)session.getAttribute("PCATEGORY_DETAIL");
        String assettype = "", department = "", category = "";
        int qcount = 0;
        if(info != null)
        {
            assettype = info.getAssettypeName() != null ? info.getAssettypeName() : "";
            department = info.getDeptName() != null ? info.getDeptName() : "";
            category = info.getName() != null ? info.getName() : "";
            qcount = info.getQcount(); 
        }
        ArrayList list1 = new ArrayList();
        ArrayList list2 = new ArrayList();
        if(request.getAttribute("QLIST1") != null)
            list1 = (ArrayList)request.getAttribute("QLIST1");
        if(request.getAttribute("QLIST2") != null)
            list2 = (ArrayList)request.getAttribute("QLIST2");
        int list1_size = list1.size();
        int list2_size = list2.size();
        String message = "", clsmessage = "red_font";
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
    <title><%= pcategory.getMainPath("title") != null ? pcategory.getMainPath("title") : "" %></title>
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
    <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="../jsnew/validation.js"></script>
    <script src="../jsnew/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="../jsnew/pcategory.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/pcategory/PcategoryAction.do" onsubmit="return false;" styleClass="form-horizontal">
<html:hidden property="doCancel"/>
<html:hidden property="search"/>
<html:hidden property="assettypeIdIndex"/> 
<html:hidden property="pdeptIdIndex"/>
<html:hidden property="doSearchQuestion"/>
<html:hidden property="doSaveQuestion"/>
<html:hidden property="doDeleteQuestion"/>
<html:hidden property="pcategoryId"/>
<html:hidden property="delId"/>
<html:hidden property="qnamesugg"/>
    <!-- Begin page -->
    <div id="layout-wrapper">
        <%@include file="../header.jsp" %>
        <%@include file="../sidemenu.jsp" %>
        <!-- Start right Content -->
        <div class="main-content">
            <div class="page-content tab_panel no_tab1">
                <div class="row head_title_area head_title_edit">
                    <div class="col-md-12 col-xl-12">
                        <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> Competency Assessments</a></div>
                        <div class="float-end">                            
                            <div class="toggled-off usefool_tool">
                                <div class="toggle-title">
                                    <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                    <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>

                                </div>
                                <!-- end toggle-title --->
                                <div class="toggle-content">
                                    <h4>Useful Tools</h4>
                                    <ul>
                                        <li><a href="javascript:openinnewtab();"><img src="../assets/images/open-in-new-tab.png"/> Open in New Tab</a></li>
                                        <li><a href="javascript:printPage('printArea');"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                        <li><a href="javascript: exporttoexcelq();"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>	
                
                <div class="col-md-12 metrix_top_right">
                    <div class="row d-flex align-items-center position-relative">
                        <div class="col-md-3 com_label_value">
                            <div class="row mb_0">
                                <div class="col-md-4 pd_right_0 "><label>Asset Type</label></div>
                                <div class="col-md-8 pd_0 single_line"><span><%=assettype%></span></div>
                            </div>
                        </div>
                        <div class="col-md-3 com_label_value">
                            <div class="row mb_0">
                                <div class="col-md-4"><label>Department</label></div>
                                <div class="col-md-8 pd_0"><span><%=department%></span></div>
                            </div>
                        </div>
                        <div class="col-md-4 com_label_value">
                            <div class="row mb_0">
                                <div class="col-md-2 pd_0"><label>Category</label></div>
                                <div class="col-md-10 pd_left_null single_line"><span><%=category%></span></div>
                            </div>
                        </div>
                        <div class="col-md-2 float-end">
                            <div class="row">
                                <div class="col-md-8 com_label_value">
                                    <div class="row mb_0">
                                        <div class="col-md-9 pd_0"><label>Added questions</label></div>
                                        <div class="col-md-3 ques_value"><span><%=pcategory.changeNum(qcount, 2)%></span></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
                <div class="container-fluid pd_0">
                    <div class="row">
                        <div class="col-md-12 col-xl-12 pd_0">
                            <div class="body-background">
                                <div class="row d-none1">                                    
                                    <div class="col-lg-12 pd_left_right_50">
                                        <% if(!message.equals("")) {%><div class="sbold <%=clsmessage%>">
                                            <%=message%>
                                        </div><% } %>
                                        <div class="row d-flex align-items-center main-heading m_30">
                                            <div class="col-lg-4"><div class="add-btn"><h4>QUESTION LIST</h4></div></div>
                                            <div class="col-lg-3">
                                                <div class="row">
                                                    <label for="example-text-input" class="col-sm-3 col-form-label">Search:</label>
                                                    <div class="col-sm-9 field_ic">
                                                        <html:text property ="searchq" styleClass="form-control" styleId="example-text-input" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {this.removeAttribute('readonly');this.blur();this.focus();}" />
                                                        <a href="javascript: searchq();" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-5 float-end text-right">
                                                <% if(addper.equals("Y")) {%><a href="javascript:;" data-bs-toggle="modal" data-bs-target="#add_new_ques_modal" class="add_new_ques">Add New Questions</a><% } %>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="row">
                                                    <div class="col-lg-8 right_border">
                                                        <div class="row">
                                                            <div class="col-lg-12">
                                                                <div class="row">
                                                                    <div class="col-lg-6"><b>Question</b></div>
                                                                    <div class="col-lg-6 text-right"><b>Edit/Remove Question</b></div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-12 sort_table quest_table">
                                                                <table class="table table-striped">
                                                                    <tbody>
<%
                                                                        if(list1_size > 0)
                                                                        {
                                                                            for(int i = 0; i < list1_size; i++)
                                                                            {
                                                                                PcategoryInfo pinfo = (PcategoryInfo) list1.get(i);
                                                                                if(pinfo != null)
                                                                                {
%>
                                                                                    <tr>
                                                                                        <td width="82%">
                                                                                            <%= pinfo.getName() != null ? pinfo.getName() : "" %>
                                                                                        </td>
                                                                                        <td width="18%" class="action_column">
                                                                                            <% if(pinfo.getStatus() == 1) {%>
                                                                                                <% if(editper.equals("Y")) {%><a href="javascript:;" data-bs-toggle="modal" data-bs-target="#add_new_ques_modal" class="mr_15" onclick="javascript: editquestion('<%=pinfo.getPquestionId()%>', '<%=pinfo.getName()%>');"><img src="../assets/images/pencil.png" /></a><% } %>
                                                                                                <% if(deleteper.equals("Y")) {%><a class="" href="javascript: deletequestion('<%=pinfo.getPquestionId()%>');"><img src="../assets/images/cross.png" /></a><% } %>
                                                                                            <% } %>
                                                                                        </td>																
                                                                                    </tr>
<%
                                                                                }
                                                                            }
                                                                        }
%>                                                                        
                                                                    </tbody>
                                                                </table>	
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <div class="row">
                                                            <div class="col-lg-12">
                                                                <div class="row">
                                                                    <div class="col-lg-7"><b>Suggested Questions</b></div>
                                                                    <div class="col-lg-5 text-right"><b>Add Question</b></div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-12 sort_table quest_table vertical_top sugg_que_table">
                                                                <table class="table table-striped">
                                                                    <tbody>
<%
                                                                        if(list2_size > 0)
                                                                        {
                                                                            for(int i = 0; i < list2_size; i++)
                                                                            {
                                                                                PcategoryInfo pinfo = (PcategoryInfo) list2.get(i);
                                                                                if(pinfo != null)
                                                                                {
%>
                                                                                    <tr>
                                                                                        <td width="80%">
                                                                                            <%= pinfo.getDdlLabel() != null ? pinfo.getDdlLabel() : "" %>
                                                                                        </td>
                                                                                        <td width="20%" class="action_column">
                                                                                            <% if(addper.equals("Y")) {%><a class="" href="javascript: addquestion('<%=pinfo.getDdlLabel()%>');"><img src="../assets/images/plus.png" /></a><% } %>
                                                                                        </td>																
                                                                                    </tr>
<%
                                                                                }
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
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
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
        <div id="add_new_ques_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <input type='hidden' name='pquestionId' value='-1' />
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <h2>ADD NEW QUESTION</h2>
                                <div class="row client_position_table1">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                        <label class="form_label">Question Text</label>
                                        <textarea class="form-control" rows="6" name='qname' maxlength="1000"></textarea>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center" id="saveid">
                                        <a href="javascript: savequestion();" class="save_page"><img src="../assets/images/save.png" /> Save</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="../footer.jsp" %>
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
