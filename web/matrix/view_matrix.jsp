<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.matrix.MatrixInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="matrix" class="com.web.jxp.matrix.Matrix" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 7, submtp = 64;
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
        MatrixInfo info = null;
        if(session.getAttribute("MATRIX_DETAIL") != null)
            info = (MatrixInfo) session.getAttribute("MATRIX_DETAIL");
        ArrayList list = new ArrayList();
        if(session.getAttribute("MATRIX_POS_LIST") != null)
        {
            list = (ArrayList) session.getAttribute("MATRIX_POS_LIST");
            session.removeAttribute("MATRIX_POS_LIST");
        }
        int size = list.size();
%>  
<head>
    <meta charset="utf-8">
    <title><%= matrix.getMainPath("title") != null ? matrix.getMainPath("title") : "" %></title>
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
    <script src="../jsnew/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="../jsnew/matrix.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/matrix/MatrixAction.do" onsubmit="return false;" styleClass="form-horizontal">
<html:hidden property="doCancel"/>
<html:hidden property="search"/>
<html:hidden property="matrixId" />
<html:hidden property="matrixpositionId" />
<html:hidden property="doAssign" />
<html:hidden property="doModify" />
<html:hidden property="positionId" />
<html:hidden property="doDeleteDetail" />
<html:hidden property="status" />
    <!-- Begin page -->
    <div id="layout-wrapper">
        <%@include file="../header.jsp" %>
        <%@include file="../sidemenu.jsp" %>
        <!-- Start right Content -->
        <div class="main-content">
            <div class="page-content">
                <div class="row head_title_area head_fixed">
                    <div class="col-md-12 col-xl-12">
                        <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> View Configure Matrix</a></div>
                        <div class="float-end">                            
                            <div class="toggled-off usefool_tool">
                                <div class="toggle-title">
                                    <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                    <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>

                                </div>
                                <!-- end toggle-title --->
                                <div class="toggle-content">
                                    <h4>Useful Tools</h4>
                                    <%@include file ="../shortcutmenu_edit.jsp"%>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 metrix_top_right">
                        <div class="row d-flex align-items-center">
                            <% if(info != null) {%>
                            <div class="col-md-2 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-6"><label>Matrix Code</label></div>
                                    <div class="col-md-6"><span><%= matrix.changeNum(info.getMatrixId(), 3) %></span></div>
                                </div>
                            </div>
                            <div class="col-md-2 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-6"><label>Matrix Name</label></div>
                                    <div class="col-md-6"><span><%= info.getName() != null ? info.getName() : "" %></span></div>
                                </div>
                            </div>
                            <div class="col-md-3 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-5"><label>Asset Type</label></div>
                                    <div class="col-md-7"><span><%= info.getAssettypeName() != null ? info.getAssettypeName() : "" %></span></div>
                                </div>
                            </div>
                            <div class="col-md-4 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-3"><label>Description</label></div>
                                    <div class="col-md-9"><span><%= info.getDescription() != null ? info.getDescription() : "" %></span></div>
                                </div>
                            </div>
                            <% if(editper.equals("Y")) {%>
                            <div class="col-md-1">
                                <div class="ref_vie_ope edit_metrix">
                                    <ul><li><a href="javascript: modifyForm('<%=info.getMatrixId()%>');"><img src="../assets/images/edit.png"/></a></li></ul>
                                </div>
                            </div>
                            <% } %>
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
                                        <div class="main-heading m_30">
                                            <div class="add-btn">
                                                <h4>POSITION LIST </h4>
                                            </div>

                                        </div>
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="table-rep-plugin sort_table">
                                                    <div class="table-responsive mb-0">
                                                        <table id="tech-companies-1" class="table table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <th width="%">
                                                                        <span><b>Position Rank</b> </span>
                                                                    </th>
                                                                    <th width="%" class="text-center">
                                                                        <span><b>Category</b></span>
                                                                    </th>
                                                                    <th width="%" class="text-center">
                                                                        <span><b>Sub-category</b></span>
                                                                    </th>
                                                                    <th width="%" class="text-center">
                                                                        <span><b>Courses</b></span>
                                                                    </th>
                                                                    <th width="%" class="text-right">
                                                                        <span><b>Action</b></span>
                                                                    </th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
<%
                                                                if(size > 0)
                                                                {
                                                                    for(int i = 0; i < size; i++)
                                                                    {
                                                                        MatrixInfo minfo = (MatrixInfo) list.get(i);
                                                                        if(minfo != null)
                                                                        {
                                                                            int status = minfo.getStatus();
%>
                                                                            <tr>
                                                                                <td><%= minfo.getName() != null ? minfo.getName() : ""%></td>
                                                                                <td class="assets_list text-center"><% if(minfo.getCount1() > 0) {%><a href='javascript:;'><%= matrix.changeNum(minfo.getCount1(), 2) %><% } else {%>&nbsp;<% } %></a></td>
                                                                                <td class="assets_list text-center"><% if(minfo.getCount2() > 0) {%><a href='javascript:;'><%= matrix.changeNum(minfo.getCount2(), 2) %><% } else {%>&nbsp;<% } %></a></td>
                                                                                <td class="assets_list text-center"><% if(minfo.getCount3() > 0) {%><a href='javascript:;'><%= matrix.changeNum(minfo.getCount3(), 2) %><% } else {%>&nbsp;<% } %></a></td>
                                                                                <td class="action_column">
                                                                                    <% if(status == 1 || status == 0) {%><a class="mr_15" href="javascript: assigncourse('<%=minfo.getMatrixpositionId()%>', '<%=minfo.getPositionId()%>');"><% if(minfo.getCount1() <= 0) {%><img src="../assets/images/link.png" /><% } else { %><img src="../assets/images/pencil.png" /><% } %></a><% } else { %>&nbsp;<% } %>
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
                            </div>
                        </div>
                    </div> 
                </div>
                <!-- End Page-content -->
            </div>
            <!-- end main content-->
        </div>
    </div>
    <!-- END layout-wrapper -->
    <%@include file="../footer.jsp" %>
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
