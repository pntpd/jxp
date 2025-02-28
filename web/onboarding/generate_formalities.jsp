<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.onboarding.OnboardingInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="onboarding" class="com.web.jxp.onboarding.Onboarding" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 2, submtp = 56;
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

                OnboardingInfo info = null;
                if (request.getAttribute("ONBOARDING_DETAIL") != null) {
                    info = (OnboardingInfo) request.getAttribute("ONBOARDING_DETAIL");
                }

                OnboardingInfo cinfo = null;
                if (request.getAttribute("SHORTLISTEDCANDIDATE_DETAILS") != null) {
                    cinfo = (OnboardingInfo) request.getAttribute("SHORTLISTEDCANDIDATE_DETAILS");
                }

                ArrayList formalitylist = null;
                if (request.getAttribute("FORMALITYLIST_DETAILS") != null) {
                    formalitylist = (ArrayList) request.getAttribute("FORMALITYLIST_DETAILS");
                }
                
                int total = formalitylist.size();
                String file_path = onboarding.getMainPath("view_onboarding");
                String photo_file = onboarding.getMainPath("view_candidate_file");
                String cphoto = "";
                 if(!cinfo.getPhoto().equals(""))
                {
                    cphoto = photo_file+cinfo.getPhoto();
                }
           
    %>
    <head>
        <meta charset="utf-8">
        <title><%= onboarding.getMainPath("title") != null ? onboarding.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/onboarding.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/onboarding/OnboardingAction.do" onsubmit="return false;" enctype="multipart/form-data">
        <html:hidden property="doGenerate"/>
        <html:hidden property="doSave"/>
        <html:hidden property="doView"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="doMail"/>
        <html:hidden property="candidateId"/>
        <html:hidden property="shortlistId"/>
        <html:hidden property="jobpostId"/>
        <html:hidden property="clientId"/>
        <html:hidden property="clientassetId"/>
        <html:hidden property="assetcountryId"/>
        <html:hidden property="zipfilename"/>
        <html:hidden property="generatedlistsize"/>
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <div class="main-content">
                <div class="page-content tab_panel1 no_tab1">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow"><a href="javascript: gobackview();"><img  src="../assets/images/back-arrow.png"/></a> <span>Onboarding</span></div>
                            <div class="float-end">
                                <div class="toggled-off usefool_tool">
                                    <div class="toggle-title">
                                        <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                        <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>
                                    </div>
                                    <div class="toggle-content">
                                        <h4>Useful Tools</h4>
                                        <%@include file ="../shortcutmenu.jsp"%>
                                    </div>
                                </div>
                            </div>
                        </div>	
                    </div>

                    <div class="container-fluid pd_0">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 pd_0">


                                <div class="body-background com_checks">

                                    <div class="row com_checks_main">

                                        <div class="col-md-12 com_top_right">
                                            <div class="row d-flex align-items-center">
                                                <div class="col-md-5 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Client Asset</label></div>
                                                        <div class="col-md-9"><span><%=  info.getClientName()+"-"+ info.getClientAsset() %></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Location</label></div>
                                                        <div class="col-md-9"><span><%= info.getCountry() %></span></div>
                                                    </div>

                                                </div>
                                                <div class="col-md-5 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Total Positions</label></div>
                                                        <div class="col-md-9"><span><%= info.getPositionCount() %></span></div>
                                                    </div>
                                                    <div class="row mb_0">&nbsp;</div>
                                                </div>

                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="mob_value">Onboarded <span><%=  onboarding.changeNum( info.getOnboardCount(),2)%>&nbsp;/&nbsp;<%=  onboarding.changeNum( info.getTotalnoofopenings(),2)%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_view_job"><a href="javascript: ;"><img src="../assets/images/view.png"/><br/> View Asset</a></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                    <div class="row onboarding_form_main">

                                        <div class="col-lg-4 col-md-4 col-sm-12 col-12">
                                            <div class="row">
                                                <div class="col-md-12  ">
                                                    <div class="search_short_main gen_prof_list">
                                                        <ul>
                                                            <li class="odd_list_1">
                                                                <div class="search_box">
                                                                    <div class="row">
                                                                        <div class="col-md-12 comp_view">
                                                                            <div class="row">
                                                                                <div class="col-md-12 client_prof_status">
                                                                                    <div class="row d-flex align-items-center">
                                                                                        <div class="col-md-3 com_view_prof cand_box_img">
                                                                                            <div class="user_photo pic_photo">
                                                                                                <div class="upload_file">
                                                                                                     <img src="<%=cphoto%>">
                                                                                                    <a href="javascript:;" onclick="viewCandidate('<%=cinfo.getCandidateId()%>')"><img src="../assets/images/view.png"></a>
                                                                                                </div>
                                                                                            </div>	
                                                                                        </div>

                                                                                        <div class="full_name_ic col-md-9 mb_0">
                                                                                            <div class="row">
                                                                                                <div class="col-md-12">
                                                                                                    <b><%=  cinfo.getName()%> </b>
                                                                                                </div>
                                                                                                <div class="col-md-12">
                                                                                                    <b><%= cinfo.getPosition()%> </b>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>


                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row gen_list_area">
                                                <div class="col-md-12 ">
                                                    <div class="main-heading mb_20">
                                                        <div class="add-btn"><h4>GENERATE FORMS</h4></div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Select Forms</label>
                                                    <html:select property="formalityId" styleId="formalityId" styleClass="form-select" onchange="javascript: clearvalue();">
                                                        <html:optionsCollection filter="false" property="formalitiestemplate" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>                                                
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <div class="table-responsive mb-0">
                                                        <table id="tech-companies-1" class="table table-striped">
                                                            <tbody id="ajax_cat">
<%
                                                            for(int i = 0 ; i < total; i++)
                                                            {
                                                                OnboardingInfo pinfo = (OnboardingInfo)formalitylist.get(i);
                                                                if (pinfo != null)
                                                                {
                                                                    String pdffile = "";
                                                                    if(!pinfo.getPdffilename().equals(""))
                                                                    {

                                                                        pdffile = file_path+pinfo.getPdffilename();
                                                                    }                                                               
%>
                                                                <tr>
                                                                    <td width="80%" class="hand_cursor" onclick="javascript: showformality('<%= pdffile%>');"><%=  pinfo.getFormalityname() != null ? pinfo.getFormalityname() : "" %></td>
                                                                    <td width="20%" class="text-right">
                                                                        <a href="javascript: delfromlist('<%= pinfo.getOnboarddocId() %> ');" class="close_ic"><span id="dellistid<%=(i+1)%>"><i class="ion ion-md-close"></span></i></a>&nbsp;

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
                                                        document.getElementById("cval3").setAttribute('placeholder', '');
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
                                                    
                                            <div class="row ">
                                                <div class="col-md-12 text-center" id = "downloadzipId1" <%if(total <= 0){ %>style="display:none;"<%} %>>                                                        
                                                    <a onclick=" javascript: gobackonclick();" href="/jxp/onboarding/download.jsp?shortlistId=<%=onboarding.cipher(cinfo.getShortlistId()+"")%>" class="down_only mr_15"> Download Only</a>
                                                    <a  href="javascript:;" data-bs-toggle="modal" data-bs-target="#email_det_candidate_modal" class="down_email" onclick=" javscript : getModel('<%= cinfo.getOnboardflag() %>')"> Download & Email</a>
                                                    <a href="javascript: addtolist();" class="down_email gen_btn1"> Generate</a>
                                                </div>
                                                <div class="col-md-12 text-center" >    
                                                    <span  id = "downloadzipId2" <%if(total <= 0){ %>style="display:none;"<%} %>>
                                                        <a href="javascript:;" onclick=" javascript: dvalidation();" class="down_only mr_15"> Download Only</a>
                                                        <a  href="javascript:;" class="down_email" onclick=" javascript : dvalidation()"> Download & Email</a>
                                                    </span>
                                                    <span  id = "downloadzipId3" <%if(total <= 0){ %>style="display:none;"<%} %>>
                                                        <a href="javascript: addtolist();" class="down_email gen_btn1"> Generate</a>
                                                    </span>
                                                </div>
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
                </div>
            </div>
        </div>
        <%@include file ="../footer.jsp"%>
        <div id="email_det_candidate_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="mailmodal">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="../assets/libs/jquery/jquery.min.js"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>
        <script src="../assets/js/table-responsive.init.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script> 
    </html:form>
        <script type="text/javascript">
    function addLoadEvent(func) {
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
        addLoadEvent(getformalitieslist());
        addLoadEvent(function() {
        })
    </script>
</body>
<%
}
catch(Exception e)
{
    e.printStackTrace();
}
%>
</html>