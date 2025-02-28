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
        MatrixInfo pinfo = null;
        String positionName = "";
        if(session.getAttribute("PINFO") != null)
        {
            pinfo = (MatrixInfo) session.getAttribute("PINFO");
            if(pinfo != null)
                positionName = pinfo.getDdlLabel() != null ? pinfo.getDdlLabel() : "";
        }
        int categoryId = 0; 
        String categoryName = "";
        if(request.getAttribute("CATNAME") != null)
        {
            MatrixInfo minfo = (MatrixInfo) request.getAttribute("CATNAME");
            if(minfo != null)
            {
                categoryId = minfo.getDdlValue();
                categoryName = minfo.getDdlLabel() != null ? minfo.getDdlLabel() : "";
            }
            request.removeAttribute("CATNAME");
        }
        ArrayList list = new ArrayList();
        if(request.getAttribute("SUBCATLIST") != null)
        {
            list = (ArrayList) request.getAttribute("SUBCATLIST");
            request.removeAttribute("SUBCATLIST");
        }
        int size = list.size();
        ArrayList course_list = new ArrayList();
        if(request.getAttribute("COURSE_LIST") != null)
        {
            course_list = (ArrayList) request.getAttribute("COURSE_LIST");
            request.removeAttribute("COURSE_LIST");
        }
        int course_list_size = course_list.size();
        
        String show_modal = "no";
        if(request.getAttribute("SAVED") != null)
        {
            show_modal = (String) request.getAttribute("SAVED");
            request.removeAttribute("SAVED");
        }
%>  
<head>
    <meta charset="utf-8">
    <title><%= matrix.getMainPath("title") != null ? matrix.getMainPath("title") : "" %></title>
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
    <script type="text/javascript" src="../jsnew/matrix.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/matrix/MatrixAction.do" onsubmit="return false;" styleClass="form-horizontal">
<html:hidden property="doCancel"/>
<html:hidden property="search"/>
<html:hidden property="matrixId" />
<html:hidden property="matrixpositionId" />
<html:hidden property="doModify" />
<html:hidden property="doCategory" />
<html:hidden property="doSaveCourse" />
<html:hidden property="categoryName" />
<html:hidden property="categoryIdHidden" />
<html:hidden property="doView"/>
    <!-- Begin page -->
    <div id="layout-wrapper">
        <%@include file="../header.jsp" %>
        <%@include file="../sidemenu.jsp" %>
        <!-- Start right Content -->
        <div class="main-content">
            <div class="page-content">
                <div class="row head_title_area head_fixed">
                    <div class="col-xl-12">
                        <div class="float-start"><a href="javascript: gobackassign();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> View Configure Matrix</a></div>
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
                                        <div class="main-heading just_cont_inherit m_30">
                                            <div class="add-btn mr_15">
                                                <h4><%=positionName%></h4>
                                            </div>
                                            <div class="col-lg-2 col-md-2 col-sm-3 col-12">
                                                <html:select property="categoryId" styleId="categorId" styleClass="form-select" onchange="javascript: changeCategory();">
                                                    <html:optionsCollection filter="false" property="categories" label="ddlLabel" value="ddlValue">
                                                    </html:optionsCollection>
                                                </html:select>
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
                                                                    <th width="15%"><span><b>Category</b></span></th>
                                                                    <th width="15%"><span><b>Sub Category</b></span></th>
                                                                    <th width="36%"><span><b>Topics</b></span></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <tr>
                                                                    <td>
                                                                        <label class="mt-checkbox mt-checkbox-outline"> <%=categoryName %>
                                                                            <input name="catcb" id="catcb" type="checkbox" value="1" <% if(matrix.checkinlistcat(course_list, categoryId)) {%>checked<% } %> onchange="javascript: setSubcat('<%=size%>');"/>
                                                                            <span></span>
                                                                        </label>
                                                                    </td>
                                                                    <td class="assets_list">&nbsp;</td>
                                                                    <td class="assets_list">&nbsp;</td>															
                                                                </tr>
<%
                                                                for(int i = 0; i < size; i++)
                                                                {
                                                                    MatrixInfo sinfo = (MatrixInfo) list.get(i);
                                                                    if(sinfo != null)
                                                                    {
                                                                        int subcategoryId = sinfo.getDdlValue();
                                                                        String subcategoryName = sinfo.getDdlLabel() != null ? sinfo.getDdlLabel() : ""; 
                                                                        ArrayList clist = matrix.getListFromList(course_list, subcategoryId);
                                                                        int clist_size = clist.size();
                                                                        if(clist_size > 0)
                                                                        {
                                                                            boolean b = matrix.checkinlist(course_list, subcategoryId);
%>
                                                                            <tr>
                                                                                <input type="hidden" id="coursesize_<%=subcategoryId%>" value="<%=clist_size%>" />
                                                                                <td class="assets_list">&nbsp;</td>		
                                                                                <td>
                                                                                    <label class="mt-checkbox mt-checkbox-outline"> <%=subcategoryName%>
                                                                                        <input type="checkbox" value="<%=subcategoryId%>" name="subcategorycb" id='subcategorycb_<%=(i+1)%>' <% if(b == true) {%>checked<% } %> onchange="javascript: setcourse('<%=subcategoryId%>', '<%=(i+1)%>', '<%=clist_size%>');" />
                                                                                        <span></span>
                                                                                    </label>                                                                                    
                                                                                </td>
                                                                                <td class="assets_list">&nbsp;</td>	
                                                                            </tr>
<%
                                                                            for(int j = 0; j < clist_size; j++)
                                                                            {
                                                                                MatrixInfo cinfo = (MatrixInfo) clist.get(j);
                                                                                if(cinfo != null)
                                                                                {
%>
                                                                                <tr>
                                                                                    <input type="hidden" name="subcategoryId" id="subcategoryId_<%=subcategoryId%>_<%=(j+1)%>" value="<% if(b == true) {%><%=subcategoryId%><% } else{ %>-1<% } %>" />
                                                                                    <input type="hidden" name="courseId" id="courseId_<%=subcategoryId%>_<%=(j+1)%>" value="<% if(cinfo.getMatrixdetailId() > 0) {%><%=cinfo.getCourseId()%><% } else{ %>-1<% } %>" /> 
                                                                                    <td class="assets_list">&nbsp;</td>	
                                                                                    <td class="assets_list">&nbsp;</td>	
                                                                                    <td>
                                                                                        <label class="mt-checkbox mt-checkbox-outline"> <%=cinfo.getName() != null ? cinfo.getName() : "" %>
                                                                                            <input name="coursecb" type="checkbox" value="<%=cinfo.getCourseId()%>" id="coursecb_<%=subcategoryId%>_<%=(j+1)%>" <% if(cinfo.getMatrixdetailId() > 0){%>checked<% } %> onchange="javascript: setcoursehidden('<%=subcategoryId%>', '<%=(j+1)%>');" />
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
                                        <% } %>
                                        <% if(addper.equals("Y") || editper.equals("Y")) {%>
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
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
                            <p>Selected courses have been assigned to the position.</p>
                            <a href="javascript: showDetailBack();" class="msg_button" style="text-decoration: underline;">Assign Training List</a>
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
