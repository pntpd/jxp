<%@page language="java" contentType="text/html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo"%>
<%@page import="com.web.jxp.onboarding.OnboardingInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<!doctype html>
<html lang="en">
    <%
         request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        try
        {
            int mtp = 2, submtp = 4;
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
            
    %>
    <head>
        <%@page pageEncoding="UTF-8"%>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><%= talentpool.getMainPath("title") != null ? talentpool.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/talentpoolgenerateof.js"></script>
        <script type="text/javascript" src="../jsnew/talentpool.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" >
            <html:hidden property="doView"/>
            <html:hidden property="search"/>
            <html:hidden property="candidateId"/>
            <html:hidden property="clientIndex"/>
            <html:hidden property="assetIndex"/>
            <html:hidden property="positionIndexId"/>
            <html:hidden property="locationIndex"/>
            <html:hidden property="employementstatus"/>
            <html:hidden property="generatedlistsize"/>
            <html:hidden property="hiddenFile"/>
            
             <div id="layout-wrapper">
                <%@include file="../header.jsp" %>
                <%@include file="../sidemenu.jsp" %>

                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content tab_panel no_tab1">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start back_arrow">
                                    <a href="javascript: viewback();" class="back_arrow">
                                        <img  src="../assets/images/back-arrow.png"/>
                                    </a>
                                    <span>Talent Pool</span></div>
                                <div class="float-end">
                                    <!-- <a href="javascript:;" class="add_btn mr_25"><i class="mdi mdi-plus"></i></a> -->
                                    <div class="toggled-off usefool_tool">
                                        <div class="toggle-title">
                                            <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                            <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>

                                        </div>
                                        <!-- end toggle-title --->
                                        <div class="toggle-content">
                                            <h4>Useful Tools</h4>
                                            <ul>
                                                <li><a href="javascript:;"><img src="../assets/images/open-in-new-tab.png"/> Open in New Tab</a></li>
                                                <li><a href="javascript:;"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                                <li><a href="javascript:;"><img src="../assets/images/share-as-email.png"/> Share as Email</a></li>
                                                <li><a href="javascript:;"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>	
                        </div>
                        <div class="container-fluid pd_0">
                            <div class="row">
                                <div class="col-md-12 col-xl-12 pd_0">
                                    <div class="body-background com_checks">
                                        <div class="row">
                                            <div class="col-md-12  ">
                                                <div class="main-heading mt_30 mb_20">
                                                    <div class="add-btn"><h4>Generate Document</h4></div>
                                                    <div class="clear_btn pull_right float-end"><a href="javascript: resetofForm();">Reset </a></div>
                                                </div>
                                            </div>

                                            <div class="col-lg-4 col-md-4 col-sm-12 col-12" id="tempdata">
                                                <div class="row gen_doc_list_area">
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                        <label class="form_label">Client</label>
                                                        <html:select property="clientId" styleId="clientId" styleClass="form-select" onchange="javascript: setformalitytemplate();">
                                                            <html:optionsCollection filter="false" property="clients" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                    
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                        <label class="form_label">Asset</label>
                                                        <html:select property="clientassetId" styleId="clientassetId" styleClass="form-select"> 
                                                            <html:optionsCollection filter="false" property="assets" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                   
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                        <label class="form_label">Select Forms</label>
                                                        <html:select property="formalityId" styleId="formalityId" styleClass="form-select" onchange="javascript: clearvalue();">
                                                            <html:optionsCollection filter="false" property="formalitytemplates" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <div class="table-responsive mb-0">
                                                        <table id="tech-companies-1" class="table table-striped">
                                                            <tbody id="ajax_cat">
                                                            </tbody>	
                                                        </table>
                                                    </div>	
                                                </div>
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                        <label class="form_label">Custom Value 1</label>
                                                        <html:text property="cval1" styleId="cval1" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("cval1").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                        <label class="form_label">Custom Value 2</label>
                                                        <html:text property="cval2" styleId="cval2" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("cval2").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                        <label class="form_label">Custom Value 3</label>
                                                        <html:text property="cval3" styleId="cval3" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("cval1").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                        
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 4</label>
                                                    <html:text property="cval4" styleId="cval4" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval4").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 5</label>
                                                    <html:text property="cval5" styleId="cval5" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval5").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 6</label>
                                                    <html:text property="cval6" styleId="cval6" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval6").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 7</label>
                                                    <html:text property="cval7" styleId="cval7" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval7").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 8</label>
                                                    <html:text property="cval8" styleId="cval8" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval8").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 9</label>
                                                    <html:text property="cval9" styleId="cval9" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval9").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Custom Value 10</label>
                                                    <html:text property="cval10" styleId="cval10" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cval10").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                </div>
                                                    
                                                <div class="col-md-12 text-center">    
                                                    <span id="downloadzipId2" style="display: none;">
                                                        <a class="down_btn down_only mr_15"  href="/jxp/talentpool/downloadof.jsp" target="_blank"> Download</a>
                                                    </span>
                                                    <a href="javascript: generatepdf();" id="generatepdfdiv" class="down_email gen_btn1"> Generate</a>
                                                </div>   
                                                
                                            </div>
                                            <div class="col-lg-8 col-md-8 col-sm-12 col-12">
                                                <div class="row">
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                        <iframe class="cv_file" src="" id="iframe"></iframe>
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
            <%@include file ="../footer.jsp"%>            
            <script src="../assets/libs/jquery/jquery.min.js"></script>
            <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
            <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
            <script src="../assets/js/app.js"></script>
            <script src="../assets/js/table-responsive.init.js"></script>
            <script src="/jxp/assets/js/sweetalert2.min.js"></script> 
            
            <script type="text/javascript">
            var candidateId = document.forms[0].candidateId.value;
            function addLoadEvent(func) 
            {
                  var oldonload = window.onload;
                  if (typeof window.onload != 'function') {
                    window.onload = func;
                  } else {
                    window.onload = function() {
                      if (oldonload) {
                        oldonload();
                      }
                    }
                  }
                } 
                addLoadEvent(getformalitieslist(candidateId));
                addLoadEvent(function() {
                })
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