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
        FrameworkInfo pinfo = null;
        ArrayList list = new ArrayList();
        if(request.getAttribute("PLIST") != null)
        {
            list = (ArrayList) request.getAttribute("PLIST");
        }
        int size = list.size();
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
<html:hidden property="doDeptChange" />
<html:hidden property="clientId"/>
<html:hidden property="assetId"/>
<html:hidden property="doAssign" />
<html:hidden property="positionIdHidden" />
<html:hidden property="pdeptIdHidden" />
<html:hidden property="doDeleteRole" />
<html:hidden property="status" />
<html:hidden property="doAssignAll" />
    <!-- Begin page -->
    <div id="layout-wrapper">
        <%@include file="../header.jsp" %>
        <%@include file="../sidemenu.jsp" %>
        <!-- Start right Content -->
        <div class="main-content">
            <div class="page-content">
                <div class="row head_title_area head_fixed">
                    <div class="col-xl-12">
                        <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> Framework Management</a></div>
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
                                        <li><a href="javascript: exportexcel('2');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 metrix_top_right">
                        <div class="row d-flex align-items-center">
                            <% if(info != null) {%>
                            <div class="col-md-3 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-3"><label>Client</label></div>
                                    <div class="col-md-9"><span><%= info.getClientName() != null ? info.getClientName() : "" %></span></div>
                                </div>
                            </div>
                            <div class="col-md-3 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-3"><label>Asset</label></div>
                                    <div class="col-md-9"><span><%= info.getClientAssetName() != null ? info.getClientAssetName() : "" %></span></div>
                                </div>
                            </div>
                            <div class="col-md-2 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-4"><label>Positions</label></div>
                                    <div class="col-md-8"><span><%= framework.changeNum(info.getPcount(), 3) %></span></div>
                                </div>
                            </div>
                            <div class="col-md-2 com_label_value">
                                &nbsp;
                            </div>                            
                            <% } %>
                        </div>
                    </div>
                </div>
                <div class="container-fluid pd_0">
                    <div class="row">
                        <div class="col-md-12 col-xl-12 pd_0">
                            <div class="body-background head_assign">
                                <div class="row d-none1">
                                    <div class="col-lg-12 pd_left_right_50">
                                        <div class="row d-flex align-items-center main-heading m_30">
                                            <div class="col-lg-7"><div class="add-btn"><h4>POSITION LIST</h4></div></div>
                                            <div class="col-lg-3">
                                                <div class="row">
                                                    <label class="col-sm-4 col-form-label text-right">Department:</label>
                                                    <div class="col-sm-8 field_ic">
                                                        <html:select property="pdeptId" styleId="pdeptId" styleClass="form-select" onchange="javascript: changeDept();">
                                                            <html:optionsCollection filter="false" property="pdepts" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-2 float-end text-right">
                                                <a id='assignhref' href="javascript:;" class="add_new_ques inactive_btn">Assign&nbsp;Role&nbsp;Competencies</a>
                                            </div>
                                        </div>
                                        <% if(size > 0) {%>
                                        <div class="row">
                                            <div class="col-lg-12" id="printArea">
                                                <div class="table-rep-plugin sort_table">
                                                    <div class="table-responsive mb-0 ellipse_code">
                                                        <table id="tech-companies-1" class="table table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <th width="3%" class="select_check_box">
                                                                        <label class="mt-checkbox mt-checkbox-outline"> 
                                                                            <input type="checkbox" name="cball" id="cball" value="1" onchange="javascript: setallcb();" />
                                                                            <span></span>
                                                                        </label>	
                                                                    </th>
                                                                    <th width="47%"><span><b>Position-Rank</b></span></th>
                                                                    <th width="20%"><span><b>Department</b></span></th>
                                                                    <th width="20%" class="text-center"><span><b>Role Competencies</b></span></th>
                                                                    <th width="10%" class="text-right"><span><b>Actions</b></span></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>                                                                
<%
                                                                String deptName;
                                                                for(int i = 0; i < size; i++)
                                                                {
                                                                    FrameworkInfo sinfo = (FrameworkInfo) list.get(i);
                                                                    if(sinfo != null)
                                                                    {     
                                                                        deptName = sinfo.getPdeptName() != null ? sinfo.getPdeptName() : "";
%>
                                                                        <tr>
                                                                            <td class="select_check_box">
                                                                                <% if(!deptName.equals("")){%>
                                                                                <label class="mt-checkbox mt-checkbox-outline"> 
                                                                                    <input type="checkbox" value="<%=sinfo.getPositionId()%>" name="positioncb" id="positioncb_<%=i%>" class="singlechkbox" onchange="javascript: setcb('<%=i%>');" />
                                                                                    <span></span>
                                                                                </label>
                                                                                <% } else {%>&nbsp;<% }%>
                                                                            </td>
                                                                            <td><%= (sinfo.getPositionName() != null ? sinfo.getPositionName() : "") %></td>
                                                                            <td><%= (sinfo.getPdeptName() != null ? sinfo.getPdeptName() : "") %></td>
                                                                            <td class="assets_list text-center"><% if(sinfo.getRolecount() > 0) {%><a href="javascript:;"><%= framework.changeNum(sinfo.getRolecount(), 2) %></a><% } else {%>&nbsp;<% } %></td>
                                                                            <td class="action_column">
                                                                                <% if(!deptName.equals("")){%>
                                                                                <a class="mr_15" href="javascript: assign('<%= sinfo.getPositionId()%>', '<%=sinfo.getPdeptId()%>');"><% if(sinfo.getRolecount() > 0) {%><img src="../assets/images/pencil.png" /><% } else { %><img src="../assets/images/link.png" /><% } %></a>
                                                                                <span class="switch_bth float-end">
                                                                                    <div class="form-check form-switch">
                                                                                        <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault_<%=(i)%>" <% if(sinfo.getRolecount() > 0){%>checked<% }%> <% if(!editper.equals("Y")) {%>disabled="true"<% } %> onclick="javascript: deleterole('<%= sinfo.getPositionId()%>', '<%=sinfo.getRolecount()%>', '<%=i%>');"/>
                                                                                    </div>
                                                                                </span>
                                                                                <% } else {%>&nbsp;<% } %>
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
    <!-- END layout-wrapper -->
    <%@include file="../footer.jsp" %>
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
                            <h3>Training(s) Assigned</h3>
                            <p>The matrx has been assigned to the Client Asset.</p>
                            <a href="javascript: goback();" class="msg_button" style="text-decoration: underline;">Training Matrix List</a>
                        </div>
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
