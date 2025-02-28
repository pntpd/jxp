<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.pcode.PcodeInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="pcode" class="com.web.jxp.pcode.Pcode" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 7, submtp = 86;
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
        PcodeInfo info = null;
        if(session.getAttribute("PCODE_DETAIL") != null)
            info = (PcodeInfo)session.getAttribute("PCODE_DETAIL");
        String assettype = "", department = "", code = "", role = "";
        if(info != null)
        {
            assettype = info.getAssettypeName() != null ? info.getAssettypeName() : "";
            department = info.getDeptName() != null ? info.getDeptName() : "";
            code = info.getCode() != null ? info.getCode() : "";
            role = info.getName() != null ? info.getName() : "";
        }
        ArrayList list = new ArrayList();
        if(request.getAttribute("LIST") != null)
            list = (ArrayList)request.getAttribute("LIST");
        int list_size = list.size();
        String message = "", clsmessage = "red_font";
        if (request.getAttribute("MESSAGE") != null)
        {
            message = (String)request.getAttribute("MESSAGE");
            request.removeAttribute("MESSAGE");
        }        
        if(message != null && (message.toLowerCase()).indexOf("success") != -1)
            clsmessage = "updated-msg";
        int categoryId = 0; 
        String categoryName = "";
        if(request.getAttribute("CATNAME") != null)
        {
            PcodeInfo minfo = (PcodeInfo) request.getAttribute("CATNAME");
            if(minfo != null)
            {
                categoryId = minfo.getDdlValue();
                categoryName = minfo.getDdlLabel() != null ? minfo.getDdlLabel() : "";
            }
            request.removeAttribute("CATNAME");
        }
        boolean checked = pcode.checkinlistcat(list, categoryId);
        boolean ftime = false;
        if(checked == false)
            ftime = true;
        String show_modal = "no";
        if(request.getAttribute("SAVED") != null)
        {
            show_modal = (String) request.getAttribute("SAVED");
            request.removeAttribute("SAVED");
        }
%>  
<head>
    <meta charset="utf-8">
    <title><%= pcode.getMainPath("title") != null ? pcode.getMainPath("title") : "" %></title>
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
    <script type="text/javascript" src="../jsnew/pcode.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/pcode/PcodeAction.do" onsubmit="return false;" styleClass="form-horizontal">
<html:hidden property="pcodeId"/>
<html:hidden property="doCancel"/>
<html:hidden property="search"/>
<html:hidden property="assettypeIdIndex"/> 
<html:hidden property="pdeptIdIndex"/>
<html:hidden property="doCategory"/>
<html:hidden property="doSaveQuestion"/>
<html:hidden property="categoryIdHidden" />
<html:hidden property="categoryName" />
    <!-- Begin page -->
    <div id="layout-wrapper">
        <%@include file="../header.jsp" %>
        <%@include file="../sidemenu.jsp" %>
        <!-- Start right Content -->
        <div class="main-content">
            <div class="page-content tab_panel no_tab1">
                <div class="row head_title_area head_title_edit">
                    <div class="col-md-12 col-xl-12">
                        <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> Competency Framework</a></div>
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
                                        <li><a href="javascript: exportexcel();"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>	
                
                <div class="col-md-12 metrix_top_right">
                    <div class="row d-flex align-items-center position-relative">
                        <div class="col-md-4 com_label_value">
                            <div class="row mb_0">
                                <div class="col-md-3 pd_right_0 "><label>Asset Type</label></div>
                                <div class="col-md-9 pd_0 single_line"><span><%=assettype%></span></div>
                            </div>
                        </div>
                        <div class="col-md-3 com_label_value">
                            <div class="row mb_0">
                                <div class="col-md-4"><label>Department</label></div>
                                <div class="col-md-8 pd_0"><span><%=department%></span></div>
                            </div>
                        </div>                        
                        <div class="col-md-1 com_label_value">
                            <div class="row mb_0">
                                <div class="col-md-5 pd_0"><label>Code</label></div>
                                <div class="col-md-7 pd_left_null single_line"><span><%=code%></span></div>
                            </div>
                        </div>
                        <div class="col-md-4 com_label_value">
                            <div class="row mb_0">
                                <div class="col-md-3 pd_0"><label>Role Competency</label></div>
                                <div class="col-md-9 pd_left_null single_line"><span><%=role%></span></div>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
                <div class="container-fluid pd_0">
                    <div class="row">
                        <div class="col-md-12 col-xl-12 pd_0">
                            <div class="body-background head_assign">
                                <div class="row d-none1">
                                    
                                    <div class="col-lg-12 pd_left_right_50">
                                        <div class="main-heading just_cont_inherit m_30">          
                                            
                                            
                                        
                                        
                                        <div class="col-lg-4">
                                                <div class="row">
                                                    <label for="example-text-input" class="col-sm-2 col-form-label ">Category:</label>
                                                    <div class="col-sm-8 field_ic">
                                                       <html:select property="categoryId" styleId="categorId" styleClass="form-select" onchange="javascript: changeCategory();">
                                                    <html:optionsCollection filter="false" property="categories" label="ddlLabel" value="ddlValue">
                                                    </html:optionsCollection>
                                                </html:select>
                                                    </div>
                                                </div>
                                            </div>
                                            </div>
                                        
                                        
                                        <% if(!categoryName.equals("")) {%>
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="table-rep-plugin sort_table">
                                                    <div class="table-responsive mb-0">
                                                        <table id="tech-companies-1" class="table table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <th width="20%"><span><b>Category</b></span></th>
                                                                    <th width="80%"><span><b>Questions</b></span></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <tr>
                                                                    <td>
                                                                        <label class="mt-checkbox mt-checkbox-outline"> <%=categoryName %>
                                                                            <input name="catcb" id="catcb" type="checkbox" value="1" <% if(checked || ftime == true) {%>checked<% } %> onchange="javascript: setQuestion('<%=list_size%>');"/>
                                                                            <span></span>
                                                                        </label>
                                                                    </td>
                                                                    <td class="assets_list">&nbsp;</td>															
                                                                </tr>
<%
                                                                for(int i = 0; i < list_size; i++)
                                                                {
                                                                    PcodeInfo sinfo = (PcodeInfo) list.get(i);
                                                                    if(sinfo != null)
                                                                    {                                                                        
%>                                                                                
                                                                        <tr>
                                                                            <input type="hidden" name="questionId" id="questionId_<%=(i+1)%>" value="<% if(sinfo.getPcodequestionId() > 0 || ftime == true) {%><%=sinfo.getPquestionId()%><% } else{ %>-1<% } %>" /> 
                                                                            <td class="assets_list">&nbsp;</td>	
                                                                            <td>
                                                                                <label class="mt-checkbox mt-checkbox-outline"> <%=sinfo.getName() != null ? sinfo.getName() : "" %>
                                                                                    <input name="questioncb" type="checkbox" value="<%=sinfo.getPquestionId()%>" id="questioncb_<%=(i+1)%>" <% if(sinfo.getPcodequestionId() > 0 || ftime == true){%>checked<% } %> onchange="javascript: setquestionhidden('<%=(i+1)%>');" />
                                                                                    <span></span>
                                                                                </label>                                                                                           
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
                                        <% } %>
                                        <% if(addper.equals("Y") || editper.equals("Y")) {%>
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="savediv">
                                            <a href="javascript: save();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
                                        </div>
                                        <%}%>
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
                            <h3>Assessments Assigned</h3>
                            <p>Go ahead and assign assessments under other role competencies.</p>
                            <a href="javascript: goback();" class="msg_button" style="text-decoration: underline;">Competency Framework</a>
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
    <% if(show_modal.equals("yes")){%>
        <script type="text/javascript">
            $(window).on('load', function () {
                $('#thank_you_modal').modal('show');
            });
        </script>
    <%}%>
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
