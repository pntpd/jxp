<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.knowledgebasematrix.KnowledgebasematrixInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="knowledgebasematrix" class="com.web.jxp.knowledgebasematrix.Knowledgebasematrix" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 10, submtp = 94;
            String per = "N", addper = "N", editper = "N", deleteper = "N";
            if (session.getAttribute("LOGININFO") == null) {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
        } else {
            UserInfo uinfo = (UserInfo) session.getAttribute("LOGININFO");
            if (uinfo != null) {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
            }
        }
        KnowledgebasematrixInfo info = null;
        if (session.getAttribute("WELLMATRIX_DETAIL") != null) {
            info = (KnowledgebasematrixInfo) session.getAttribute("WELLMATRIX_DETAIL");
        }
        ArrayList list = new ArrayList();
        if (session.getAttribute("WELLMATRIX_POS_LIST") != null) {
            list = (ArrayList) session.getAttribute("WELLMATRIX_POS_LIST");
        }
        int size = list.size();
    %>  
    <head>
        <meta charset="utf-8">
        <title><%= knowledgebasematrix.getMainPath("title") != null ? knowledgebasematrix.getMainPath("title") : ""%></title>
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
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/knowledgebasematrix.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/knowledgebasematrix/KnowledgebasematrixAction.do" onsubmit="return false;" styleClass="form-horizontal">
        <html:hidden property="doCancel"/>
        <html:hidden property="type"/>
        <html:hidden property="knowledgebasematrixId" />
        <html:hidden property="wellnessmatrixpositionId" />
        <html:hidden property="doAssign" />
        <html:hidden property="doModify" />
        <html:hidden property="positionId" />
        <html:hidden property="clientId" />
        <html:hidden property="assetId" />
        <html:hidden property="doDeleteDetail" />
        <html:hidden property="status" />
        <html:hidden property="doView" />
        <html:hidden property="assetIdIndex" />
        <html:hidden property="clientIdIndex" />
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="row head_title_area head_fixed">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/>Knowledge Base Matrix</a></div>
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
                                            <li><a href="javascript: exporttoexcel('2');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12 metrix_top_right">
                            <div class="row d-flex align-items-center position-relative">
                                <% if (info != null) {%>
                                <div class="col-md-3 com_label_value">
                                    <div class="row mb_0">
                                        <div class="col-md-3"><label>Client</label></div>
                                        <div class="col-md-9 pd_left_null"><span><%= info.getClientName() != null ? info.getClientName() : ""%></span></div>
                                    </div>
                                </div>
                                <div class="col-md-2 com_label_value">
                                    <div class="row mb_0">
                                        <div class="col-md-4"><label>Asset</label></div>
                                        <div class="col-md-8 pd_left_null"><span><%= info.getClientAssetName() != null ? info.getClientAssetName() : ""%></span></div>
                                    </div>
                                </div>
                                <div class="col-md-2 com_label_value">
                                    <div class="row mb_0">
                                        <div class="col-md-6"><label>Positions</label></div>
                                        <div class="col-md-6"><span><%= info.getPcount()%></span></div>
                                    </div>
                                </div>

                                <% if (editper.equals("Y")) {%>
                                <div class="col-md-1 position-absolute end-0">
                                    <div class="ref_vie_ope edit_metrix">
                                        <ul><li><a href="javascript: viewasset('<%=info.getClientId()%>');"><img src="../assets/images/edit.png"/></a></li></ul>
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
                                                                            <a href="javascript: sortFormPosition('1', '2');" id="img_1_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                            <a href="javascript: sortFormPosition('1', '1');" id="img_1_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                        </th>
                                                                        <th width="%" class="text-center">
                                                                            <span><b>Category</b></span>
                                                                            <a href="javascript: sortFormPosition('2', '2');" id="img_2_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                            <a href="javascript: sortFormPosition('2', '1');" id="img_2_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                        </th>

                                                                        <th width="%" class="text-center">
                                                                            <span><b>Module</b></span>
                                                                            <a href="javascript: sortFormPosition('3', '2');" id="img_3_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                            <a href="javascript: sortFormPosition('3', '1');" id="img_3_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                        </th>
                                                                        <th width="%" class="text-center">
                                                                            <span><b>Topic</b></span>
                                                                            <a href="javascript: sortFormPosition('4', '2');" id="img_4_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                            <a href="javascript: sortFormPosition('4', '1');" id="img_4_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                        </th>
                                                                        <th width="%" class="text-right">
                                                                            <span><b>Actions</b></span>
                                                                        </th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody  id="sort_idp">
                                                                    <%
                                                                        if (size > 0) {
                                                                            for (int i = 0; i < size; i++) {
                                                                                KnowledgebasematrixInfo minfo = (KnowledgebasematrixInfo) list.get(i);
                                                                                if (minfo != null) {
                                                                                    int status = 0;
                                                                                    if (minfo.getStatusCount() > 0) {
                                                                                        status = 1;
                                                                                    }
                                                                    %>
                                                                    <tr>
                                                                        <td><%= minfo.getName() != null ? minfo.getName() : ""%></td>
                                                                        <td class="assets_list text-center"><% if (minfo.getCount1() > 0) {%><a href='javascript:;'><%= knowledgebasematrix.changeNum(minfo.getCount1(), 2)%><% } else {%>&nbsp;<% } %></a></td>
                                                                        <td class="assets_list text-center"><% if (minfo.getCount2() > 0) {%><a href='javascript:;'><%= knowledgebasematrix.changeNum(minfo.getCount2(), 2)%><% } else {%>&nbsp;<% } %></a></td>
                                                                        <td class="assets_list text-center"><% if (minfo.getCount3() > 0) {%><a href='javascript:;'><%= knowledgebasematrix.changeNum(minfo.getCount3(), 2)%><% } else {%>&nbsp;<% }%></a></td>
                                                                        <td class="action_column">
                                                                            <a class="mr_15" href="javascript: assignquestion('<%=minfo.getPositionId()%>');"><% if (minfo.getCount1() <= 0) {%><img src="../assets/images/link.png" /><% } else if (status == 1) { %><img src="../assets/images/pencil.png" /><% } %><a/>

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
        <script src="///assets/js/sweetalert2.min.js"></script>
        <script>
        // toggle class show hide text section
        $(document).on('click', '.toggle-title', function () {
            $(this).parent()
                    .toggleClass('toggled-on')
                    .toggleClass('toggled-off');
        });
        </script>
        <script>
            $(window).on('scroll', function () {
                if ($(this).scrollTop() > 150) {
                    $('.head_fixed').addClass("is-sticky");
                } else {
                    $('.head_fixed').removeClass("is-sticky");
                }
            });
        </script>
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
