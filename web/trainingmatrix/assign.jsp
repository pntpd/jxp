<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.trainingmatrix.TrainingmatrixInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="trainingmatrix" class="com.web.jxp.trainingmatrix.Trainingmatrix" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 4, submtp = 66;
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
        TrainingmatrixInfo info = null;
        if(session.getAttribute("BASICINFO") != null)
            info = (TrainingmatrixInfo) session.getAttribute("BASICINFO");
        TrainingmatrixInfo pinfo = null;
        ArrayList list = new ArrayList();
        if(session.getAttribute("SUBCATLIST") != null)
        {
            list = (ArrayList) session.getAttribute("SUBCATLIST");
        }
        int size = list.size();
        ArrayList course_list = new ArrayList();
        if(request.getAttribute("COURSE_LIST") != null)
        {
            course_list = (ArrayList) request.getAttribute("COURSE_LIST");
            request.removeAttribute("COURSE_LIST");
        }
        int course_list_size = course_list.size();
        ArrayList list1 = new ArrayList();
        if(session.getAttribute("LIST1") != null)
        {
            list1 = (ArrayList) session.getAttribute("LIST1");
        }
        ArrayList list2 = new ArrayList();
        if(session.getAttribute("LIST2") != null)
        {
            list2 = (ArrayList) session.getAttribute("LIST2");
        }
        ArrayList list3 = new ArrayList();
        if(session.getAttribute("LIST3") != null)
        {
            list3 = (ArrayList) session.getAttribute("LIST3");
        }
        String show_modal = "no";
        if(request.getAttribute("SAVED") != null)
        {
            show_modal = (String) request.getAttribute("SAVED");
            request.removeAttribute("SAVED");
        }
%>  
<head>
    <meta charset="utf-8">
    <title><%= trainingmatrix.getMainPath("title") != null ? trainingmatrix.getMainPath("title") : "" %></title>
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
    <script type="text/javascript" src="../jsnew/trainingmatrix.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/trainingmatrix/TrainingmatrixAction.do" onsubmit="return false;" styleClass="form-horizontal">
<html:hidden property="doCancel"/>
<html:hidden property="search"/>
<html:hidden property="clientIdIndex"/>
<html:hidden property="assetIdIndex"/>
<html:hidden property="clientId" />
<html:hidden property="assetId" />
<html:hidden property="doCategory" />
<html:hidden property="doSaveCourse" />
<html:hidden property="categoryIdHidden" />
<html:hidden property="positionIdHidden" />
<html:hidden property="doView" />
    <!-- Begin page -->
    <div id="layout-wrapper">
        <%@include file="../header.jsp" %>
        <%@include file="../sidemenu.jsp" %>
        <!-- Start right Content -->
        <div class="main-content">
            <div class="page-content">
                <div class="row head_title_area head_fixed">
                    <div class="col-xl-12">
                        <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> Training Matrix</a></div>
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
                            <div class="col-md-3 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-4"><label>Asset Type</label></div>
                                    <div class="col-md-8"><span><%= info.getAssettypeName() != null ? info.getAssettypeName() : "" %></span></div>
                                </div>
                            </div>
                            <div class="col-md-2 com_label_value">
                                &nbsp;
                            </div>
                            <div class="col-md-1">
                                <div class="ref_vie_ope">
                                    <ul>
                                        <li class="com_view_job"><a href="javascript: viewasset();"><img src="../assets/images/view.png"> View Asset</a></li>
                                    </ul>
                                </div>
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
                                        <div class="main-heading just_cont_inherit m_30">
                                            <div class="add-btn mr_15">
                                                <h4>TRAINING MATRIX</h4>
                                            </div>
                                            
                                            <div class="col-lg-4">
                                                <div class="row">
                                                    <label for="example-text-input" class="col-sm-3 col-form-label text-right">Position:</label>
                                                    <div class="col-sm-9 field_ic">
                                                        <html:select property="positionId" styleId="positionId" styleClass="form-select" onchange="javascript: changeCategorychange();">
                                                            <html:optionsCollection filter="false" property="positions" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select> 
                                                    </div>
                                                </div>
                                            </div>
                                             <div class="col-lg-4 mr_15">
                                                <div class="row">
                                                    <label for="example-text-input" class="col-sm-3 col-form-label text-right">Category:</label>
                                                    <div class="col-sm-9 field_ic">
                                                        <html:select property="categoryId" styleId="categorId" styleClass="form-select" onchange="javascript: changeCategorychange();">
                                                            <html:optionsCollection filter="false" property="categories" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>
                                             <a href="javascript: changeCategory();" class="go_btn">Go</a>
                                          
                                        </div>
                                        
                                        <% if(size > 0) {%>
                                        <div class="row" id="printArea">
                                            <div class="col-lg-12">
                                                <div class="table-rep-plugin sort_table">
                                                    <div class="table-responsive mb-0">
                                                        <table id="tech-companies-1" class="table table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <th width="17%"><span><b>Sub Category</b></span></th>
                                                                    <th width="23%"><span><b>Topics</b></span></th>
                                                                    <th width="15%"><span><b>Course Type</b></span></th>
                                                                    <th width="15%"><span><b>Priority</b></span></th>
                                                                    <th width="15%"><span><b>Level</b></span></th>
                                                                    <th width="15%"><span><b>Relative Training</b></span></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>                                                                
<%
                                                                for(int i = 0; i < size; i++)
                                                                {
                                                                    TrainingmatrixInfo sinfo = (TrainingmatrixInfo) list.get(i);
                                                                    if(sinfo != null)
                                                                    {
                                                                        int subcategoryId = sinfo.getDdlValue();
                                                                        String subcategoryName = sinfo.getDdlLabel() != null ? sinfo.getDdlLabel() : ""; 
                                                                        ArrayList clist = trainingmatrix.getListFromList(course_list, subcategoryId);
                                                                        int clist_size = clist.size();
                                                                        if(clist_size > 0)
                                                                        {
                                                                            boolean b = trainingmatrix.checkinlist(course_list, subcategoryId);                                                                            
%>
                                                                            <tr>
                                                                                <input type="hidden" id="coursesize_<%=subcategoryId%>" value="<%=clist_size%>" />
                                                                                <td>
                                                                                    <label class="mt-checkbox mt-checkbox-outline"> <%=subcategoryName%>
                                                                                        <input type="checkbox" value="<%=subcategoryId%>" name="subcategorycb" id='subcategorycb_<%=(i+1)%>' <% if(b == true) {%>checked<% } %> onchange="javascript: setcourse('<%=subcategoryId%>', '<%=(i+1)%>', '<%=clist_size%>');" />
                                                                                        <span></span>
                                                                                    </label>                                                                                    
                                                                                </td>
                                                                                <td colspan="5" class="assets_list">&nbsp;</td>		
                                                                            </tr>
<%
                                                                            for(int j = 0; j < clist_size; j++)
                                                                            {
                                                                                TrainingmatrixInfo cinfo = (TrainingmatrixInfo) clist.get(j);
                                                                                if(cinfo != null)
                                                                                {
                                                                                    ArrayList list4 = trainingmatrix.getListFromListCourse(clist, cinfo.getCourseId());
%>
                                                                                <tr>
                                                                                    <input type="hidden" name="subcategoryId" id="subcategoryId_<%=subcategoryId%>_<%=(j+1)%>" value="<%=subcategoryId%>" />
                                                                                    <input type="hidden" name="courseId" id="courseId_<%=subcategoryId%>_<%=(j+1)%>" value="<% if(cinfo.getClientmatrixdetailId() > 0) {%><%=cinfo.getCourseId()%><% } else{ %>-1<% } %>" /> 
                                                                                    <input type="hidden" name="tctypeId" id="tctypeId_<%=subcategoryId%>_<%=(j+1)%>" value="<%= cinfo.getTctypeId() %>" /> 
                                                                                    <input type="hidden" name="trainingId" id="trainingId_<%=subcategoryId%>_<%=(j+1)%>" value="<%= cinfo.getTraingId() %>" /> 
                                                                                    <input type="hidden" name="levelId" id="levelId_<%=subcategoryId%>_<%=(j+1)%>" value="<%= cinfo.getLevelId() %>" /> 
                                                                                    <input type="hidden" name="courseIdrel" id="courseIdrel_<%=subcategoryId%>_<%=(j+1)%>" value="<%= cinfo.getCourseIdrel() %>" /> 
                                                                                    <td class="assets_list">&nbsp;</td>	
                                                                                    <td>
                                                                                        <label class="mt-checkbox mt-checkbox-outline"> <%=cinfo.getName() != null ? cinfo.getName() : "" %>
                                                                                            <input name="coursecb" type="checkbox" value="<%=cinfo.getCourseId()%>" id="coursecb_<%=subcategoryId%>_<%=(j+1)%>" <% if(cinfo.getClientmatrixdetailId() > 0){%>checked<% } %> onchange="javascript: setcoursehidden('<%=subcategoryId%>', '<%=(j+1)%>');" />
                                                                                            <span></span>
                                                                                        </label>                                                                                           
                                                                                    </td>
                                                                                    <td class="assets_list">
                                                                                        <select class="form-select" name="tctypeIdddl" id="tctypeIdddl_<%=subcategoryId%>_<%=(j+1)%>" onchange="javascript: setTraingKind('<%=subcategoryId%>', '<%=(j+1)%>');">
                                                                                            <option value="-1">- Select Course Type -</option>
                                                                                            <%=trainingmatrix.getCollection1(list1, cinfo.getTctypeId())%>
                                                                                        </select>	
                                                                                    </td>		
                                                                                    <td class="assets_list">                                                                                        
                                                                                        <select class="form-select" name="trainingIdddl" id="trainingIdddl_<%=subcategoryId%>_<%=(j+1)%>" onchange="javascript: setTraingType('<%=subcategoryId%>', '<%=(j+1)%>');">
                                                                                            <option value="-1">- Select Priority -</option>
                                                                                            <%=trainingmatrix.getCollection1(list2, cinfo.getTraingId())%>
                                                                                        </select>	
                                                                                    </td>
                                                                                    <td class="assets_list">
                                                                                        <select class="form-select" name="levelIdddl" id="levelIdddl_<%=subcategoryId%>_<%=(j+1)%>" onchange="javascript: setlevel('<%=subcategoryId%>', '<%=(j+1)%>');">
                                                                                            <option value="-1">NA</option>
                                                                                            <%=trainingmatrix.getCollection1(list3, cinfo.getLevelId())%>
                                                                                        </select>	
                                                                                    </td>		
                                                                                    <td class="assets_list">
                                                                                        <select class="form-select" name="courseIdrelddl" id="courseIdrelddl_<%=subcategoryId%>_<%=(j+1)%>" onchange="javascript: setcourseIdrel('<%=subcategoryId%>', '<%=(j+1)%>');">
                                                                                            <option value="-1">NA</option>
                                                                                            <%=trainingmatrix.getCollectionRel(list4, cinfo.getCourseIdrel())%>
                                                                                        </select>	
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
