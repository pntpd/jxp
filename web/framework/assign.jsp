<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.framework.FrameworkInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="framework" class="com.web.jxp.framework.Framework" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 5, submtp = 87;
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
        FrameworkInfo info = null;
        if(session.getAttribute("BASICINFO") != null)
            info = (FrameworkInfo) session.getAttribute("BASICINFO");
        String clientName = "", assetName = "", positionName = "", pdeptName = "";
        if(info != null)
        {
            clientName = info.getClientName() != null ? info.getClientName() : "";
            assetName = info.getClientAssetName() != null ? info.getClientAssetName() : "";
        }
        FrameworkInfo finfo = null;
        if(session.getAttribute("BASICINFOASSIGN") != null)
            finfo = (FrameworkInfo) session.getAttribute("BASICINFOASSIGN");
        int count1 = 0, count2 = 0, count3 = 0; 
        if(finfo != null)
        {
            positionName = finfo.getPositionName() != null ? finfo.getPositionName() : "";
            pdeptName = finfo.getPdeptName() != null ? finfo.getPdeptName() : "";
            count1 = finfo.getCount1();
            count2 = finfo.getCount2();
            count3 = finfo.getCount3();
        }
        ArrayList list = new ArrayList();
        if(request.getAttribute("CLIST") != null)
        {
            list = (ArrayList) request.getAttribute("CLIST");
            request.removeAttribute("CLIST");
        }
        int size = list.size();
        ArrayList question_list = new ArrayList();
        if(request.getAttribute("QLIST") != null)
        {
            question_list = (ArrayList) request.getAttribute("QLIST");
            request.removeAttribute("QLIST");
        }
        int question_list_size = question_list.size();
        String pcodeName = "";
        if(request.getAttribute("PCODENAME") != null)
        {
            pcodeName = (String) request.getAttribute("PCODENAME");
            request.removeAttribute("PCODENAME");
        }
        String show_modal = "no";
        if(request.getAttribute("SAVED") != null)
        {
            show_modal = (String) request.getAttribute("SAVED");
            request.removeAttribute("SAVED");
        }
        boolean multiple = false;
        if(positionName.contains("SHOW SELECTED POSITIONS"))
            multiple = true;
%>  
<head>
    <meta charset="utf-8">
    <title><%= framework.getMainPath("title") != null ? framework.getMainPath("title") : "" %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="shortcut icon" href="../assets/images/favicon.png" />
    <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css" />
    <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
    <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css" />
    <link href="/jxp/assets/css/minimal.css" rel="stylesheet" />
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
    <script src="../jsnew/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="../jsnew/framework.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/framework/FrameworkAction.do" onsubmit="return false;" styleClass="form-horizontal">
<html:hidden property="doCancel"/>
<html:hidden property="search"/>
<html:hidden property="clientIdIndex"/>
<html:hidden property="assetIdIndex"/>
<html:hidden property="doView" />
<html:hidden property="clientId"/>
<html:hidden property="assetId"/>
<html:hidden property="positionIdHidden" />
<html:hidden property="pdeptIdHidden" />
<html:hidden property="pcodeIdHidden" />
<html:hidden property="pcodeName" />
<html:hidden property="doSave" />
<html:hidden property="doChangePcode" />
<html:hidden property="pids" />
    <!-- Begin page -->
    <div id="layout-wrapper">
        <%@include file="../header.jsp" %>
        <%@include file="../sidemenu.jsp" %>
        <!-- Start right Content -->
        <div class="main-content">
            <div class="page-content">
                <div class="row head_title_area head_fixed">
                    <div class="col-xl-12">
                        <div class="float-start"><a href="javascript: gobackassign();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> Framework Management</a></div>
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
                                        <li><a href="javascript: exportexcel('3');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 metrix_top_right">
                        <div class="row d-flex align-items-center position-relative">
                            <div class="col-md-2 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-3 pd_right_0"><label>Client</label></div>
                                    <div class="col-md-9 pd_0 single_line"><span><%=clientName%></span></div>
                                </div>
                            </div>
                            <div class="col-md-2 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-4"><label>Asset</label></div>
                                    <div class="col-md-8 pd_0 single_line"><span><%=assetName%></span></div>
                                </div>
                            </div>
                            <div class="col-md-2 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-5 pd_0"><label>Department</label></div>
                                    <div class="col-md-7 pd_left_null single_line"><span><%=pdeptName%></span></div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="row d-flex align-items-center">
                                    <div class="col-md-4 com_label_value pd_left_null">
                                        <div class="row d-flex align-items-center mb_0">
                                            <div class="col-md-10 text-right pd_left_null"><label>Selected Role Competencies</label></div>
                                            <div class="col-md-2 ques_value"><span><%=framework.changeNum(count1, 2)%></span></div>
                                        </div>
                                    </div>
                                    <div class="col-md-4 com_label_value">
                                        <div class="row d-flex align-items-center mb_0">
                                            <div class="col-md-10 text-right pd_left_null"><label>Selected Categories</label></div>
                                            <div class="col-md-2 ques_value"><span><%=framework.changeNum(count2, 2)%></span></div>
                                        </div>
                                    </div>
                                    <div class="col-md-4 com_label_value pd_left_null">
                                        <div class="row d-flex align-items-center mb_0">
                                            <div class="col-md-8 text-right pd_left_null"><label>Selected Questions</label></div>
                                            <div class="col-md-2 ques_value"><span><%=framework.changeNum(count3, 2)%></span></div>
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
                                        <div class="row d-flex align-items-center main-heading m_30">
                                            <div class="col-lg-4"><div class="add-btn mult_pos_rank"><h4 onclick="javascript: setposition();" data-bs-toggle="modal" data-bs-target="#client_position"><% if(multiple == true) {%><span><%=positionName%></span><% } else {%><%=positionName%><% } %></h4></div></div>
                                            <div class="col-lg-8">
                                                    <div class="row">
                                                            <div class="col-lg-4">
                                                                <html:select property="pcodeId" styleId="pcodeId" styleClass="form-select" onchange="javascript: changepcode();">
                                                                    <html:optionsCollection filter="false" property="pcodes" label="ddlLabel" value="ddlValue">
                                                                    </html:optionsCollection>
                                                                </html:select>
                                                            </div>                                            
                                                            <div class="col-lg-3">
                                                                <html:select property="monthId" styleId="monthId" styleClass="form-select">
                                                                    <html:option value="-1">Set Validity(in months)</html:option>
                                                                    <html:option value="1">1</html:option>
                                                                    <html:option value="2">2</html:option>
                                                                    <html:option value="3">3</html:option>
                                                                    <html:option value="4">4</html:option>
                                                                    <html:option value="5">5</html:option>
                                                                    <html:option value="6">6</html:option>
                                                                    <html:option value="7">7</html:option>
                                                                    <html:option value="8">8</html:option>
                                                                    <html:option value="9">9</html:option>
                                                                    <html:option value="10">10</html:option>
                                                                    <html:option value="11">11</html:option>
                                                                    <html:option value="12">12</html:option>
                                                                </html:select>
                                                            </div>
                                                            <div class="col-lg-3">
                                                                <html:select property="passessmenttypeId" styleId="passessmenttypeId" styleClass="form-select">
                                                                    <html:optionsCollection filter="false" property="assessmenttypes" label="ddlLabel" value="ddlValue">
                                                                    </html:optionsCollection>
                                                                </html:select>
                                                            </div>
                                                            <div class="col-lg-2">
                                                                <html:select property="priorityId" styleId="priorityId" styleClass="form-select">
                                                                    <html:optionsCollection filter="false" property="priorities" label="ddlLabel" value="ddlValue">
                                                                    </html:optionsCollection>
                                                                </html:select>
                                                            </div>
                                                     </div>
                                            </div>
                                        </div>
                                        <% if(!pcodeName.equals("")) {%>
                                        <div class="row">
                                            <div class="col-lg-12" id="printArea">
                                                <div class="table-rep-plugin sort_table">
                                                    <div class="table-responsive mb-0">
                                                        <table id="tech-companies-1" class="table table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <th width="20%"><span><b>Role Competencies</b></span></th>
                                                                    <th width="20%"><span><b>Categories</b></span></th>
                                                                    <th width="60%"><span><b>Questions</b></span></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <tr>
                                                                    <td colspan="3">
                                                                        <label class="mt-checkbox mt-checkbox-outline"> <b><%=pcodeName%></b>
                                                                            <input type="checkbox" value="1" name="test" name='pcodecb' checked="true" disabled="true" />
                                                                            <span></span>
                                                                        </label>
                                                                    </td>
                                                                </tr>
<%
                                                                for(int i = 0; i < size; i++)
                                                                {
                                                                    FrameworkInfo sinfo = (FrameworkInfo) list.get(i);
                                                                    if(sinfo != null)
                                                                    {
                                                                        int categoryId = sinfo.getDdlValue();
                                                                        String catName = sinfo.getDdlLabel() != null ? sinfo.getDdlLabel() : ""; 
                                                                        ArrayList clist = framework.getListFromList(question_list, categoryId);
                                                                        int clist_size = clist.size();
                                                                        if(clist_size > 0)
                                                                        {
                                                                            boolean b = framework.checkinlist(question_list, categoryId);
%>
                                                                            <tr>
                                                                                <input type="hidden" id="questionsize_<%=categoryId%>" value="<%=clist_size%>" />
                                                                                <td class="assets_list">&nbsp;</td>		
                                                                                <td>
                                                                                    <label class="mt-checkbox mt-checkbox-outline"> <%=catName%>
                                                                                        <input type="checkbox" value="<%=categoryId%>" name="categorycb" id='categorycb_<%=(i+1)%>' <% if(b == true) {%>checked<% } %> onchange="javascript: setquestion('<%=categoryId%>', '<%=(i+1)%>', '<%=clist_size%>');" />
                                                                                        <span></span>
                                                                                    </label>                                                                                    
                                                                                </td>
                                                                                <td class="assets_list">&nbsp;</td>	
                                                                            </tr>
<%
                                                                            for(int j = 0; j < clist_size; j++)
                                                                            {
                                                                                FrameworkInfo cinfo = (FrameworkInfo) clist.get(j);
                                                                                if(cinfo != null)
                                                                                {
%>
                                                                                <tr>
                                                                                    <input type="hidden" name="categoryId" id="categoryId_<%=categoryId%>_<%=(j+1)%>" value="<%=categoryId%>" />
                                                                                    <input type="hidden" name="questionId" id="questionId_<%=categoryId%>_<%=(j+1)%>" value="<% if(cinfo.getFcroledetailId() > 0) {%><%=cinfo.getPquestionId()%><% } else{ %>-1<% } %>" /> 
                                                                                    <td class="assets_list">&nbsp;</td>	
                                                                                    <td class="assets_list">&nbsp;</td>	
                                                                                    <td>
                                                                                        <label class="mt-checkbox mt-checkbox-outline"> <%=cinfo.getName() != null ? cinfo.getName() : "" %>
                                                                                            <input name="questioncb" type="checkbox" value="<%=cinfo.getPquestionId()%>" id="questioncb_<%=categoryId%>_<%=(j+1)%>" <% if(cinfo.getFcroledetailId() > 0){%>checked<% } %> onchange="javascript: setquestionhidden('<%=categoryId%>', '<%=(j+1)%>');" />
                                                                                            <span></span>
                                                                                        </label>                                                                                           
                                                                                    </td>                                                                                    	
                                                                                </tr>
<%
                                                                                }
                                                                            }
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
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12" id='savediv'>
                                            <% if(addper.equals("Y") || editper.equals("Y")) {%><a href="javascript: save();" class="save_btn"><img src="../assets/images/save.png"> Save</a><% } %> 
                                        </div>
                                        <% } %>
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
    <%@include file="../footer.jsp" %>
    <% if(show_modal.equals("yes")){%>
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
                            <h3>Role Competencies Configured</h3>
                            <p>Selected Role Competencies have been assigned to the Position(s)</p>
                            <a href="javascript: gobackassign();" class="msg_button" style="text-decoration: underline;">Framework Management</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <% } %>
    <% if(multiple == true) {%>
    <div id="client_position" class="modal fade multiple_position parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <h2>SELECTED POSITIONS - FRAMEWORK MANAGEMENT</h2>
                            <div class="full_width client_position_table">
                                <div class="table-rep-plugin sort_table">
                                    <div class="table-responsive mb-0">
                                        <table id="tech-companies-1" class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th width="%"><span><b>Position</b> </span></th>
                                                    <th width="%"><span><b>Rank</b></span></th>
                                                </tr>
                                            </thead>
                                            <tbody id="pdivlist">                               
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
    <% } %>
    <!-- JAVASCRIPT -->
    <script src="../assets/libs/jquery/jquery.min.js"></script>		
    <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
    <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
    <script src="../assets/js/app.js"></script>	
    <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>   
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
        $(window).on('scroll', function() {
            if ($(this).scrollTop() >150){  
                $('.head_fixed').addClass("is-sticky");
            }
            else{
                $('.head_fixed').removeClass("is-sticky");
            }
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
