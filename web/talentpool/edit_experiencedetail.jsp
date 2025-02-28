<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 2, submtp = 4, ctp = 5;
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
            String message = "", clsmessage = "red_font";
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }        
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";
            String filename = "", filename1 = "";
            if (session.getAttribute("FILENAME") != null)
            {
                filename = (String)session.getAttribute("FILENAME");
                if(filename != null && !filename.equals(""))
                    filename = talentpool.getMainPath("view_candidate_file")+filename;
            }
            if (session.getAttribute("FILENAME1") != null)
            {
                filename1 = (String)session.getAttribute("FILENAME1");
                if(filename1 != null && !filename1.equals(""))
                    filename1 = talentpool.getMainPath("view_candidate_file")+filename1;
            }
            ArrayList list = new ArrayList();
            if(request.getSession().getAttribute("MODULEPER_LIST") != null)
                list = (ArrayList)request.getSession().getAttribute("MODULEPER_LIST");
    %>
    <head>
        <meta charset="utf-8">
        <title><%= talentpool.getMainPath("title") != null ? talentpool.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="../autofill/jquery-ui.min.css" />  <!-- Autofill-->
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <script src="../assets/libs/jquery/jquery.min.js"></script>
        <script src="../autofill/jquery-ui.min.js" type="text/javascript"></script> <!-- autofill-->
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>	
        <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/talentpool.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="doaddexperiencedetail"/>
        <html:hidden property="doSaveexperiencedetail"/>
        <html:hidden property="doDeleteexperiencedetail"/>
        <html:hidden property="experiencedetailId"/>
        <html:hidden property="experiencehiddenfile"/>
        <html:hidden property="workinghiddenfile"/>
        <html:hidden property="rolehiddenfile"/>
        <html:hidden property="currentDate"/>
        <div id="layout-wrapper">
            <%@include file ="../header.jsp"%>
            <%@include file ="../sidemenu.jsp"%>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content tab_panel">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start">
                                <a href="javascript:goback();" class="back_arrow">
                                    <img  src="../assets/images/back-arrow.png"/> 
                                    <%@include file ="../talentpool_title.jsp"%>
                                </a>
                            </div>
                        </div>
                        <div class="col-md-12 col-xl-12 tab_head_area">
                            <%@include file ="../talentpooltab.jsp"%>
                        </div>
                    </div>
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background">
                                    <div class="row d-none1">
                                        <div class="col-lg-12">
                                            <% if(!message.equals("")) {%><div class="sbold <%=clsmessage%>">
                                                <%=message%>
                                            </div><% } %>
                                            <div class="main-heading m_30">
                                                <div class="add-btn">
                                                    <h4>WORK EXPERIENCE DETAILS</h4>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-8 col-md-8 col-sm-8 col-12 form_group">
                                                    <label class="form_label">Company Name<span class="required">*</span></label>
                                                    <html:text property="companyname" styleId="companyname" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("companyname").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Company Industry<span class="required">*</span></label>
                                                    <html:select property="companyindustryId" styleClass="form-select">
                                                        <html:optionsCollection filter="false" property="companyindustries" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Asset Type<span class="required">*</span></label>
                                                    <html:select property="assettypeId" styleId="assettypeddl" styleClass="form-select" onchange="javascript: setAssetPosition();">
                                                        <html:optionsCollection filter="false" property="assettypes" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Asset Name<span class="required">*</span></label>
                                                    <html:text property="assetname" styleId="assetname" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("assetname").setAttribute('placeholder', '');
                                                    </script>
                                                </div>

                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Position<span class="required">*</span></label>
                                                    <html:select property="positionId" styleId="positionddl" styleClass="form-select">
                                                        <html:optionsCollection filter="false" property="positions" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Department / Function</label>
                                                    <html:select property="departmentId" styleClass="form-select">
                                                        <html:optionsCollection filter="false" property="departments" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Country</label>
                                                    <html:select property="countryId" styleId="countryId" styleClass="form-select" onchange="javascript: clearcity();">
                                                        <html:optionsCollection filter="false" property="countries" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <div class="row flex-end align-items-end">
                                                            <div class="col-lg-10 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">City</label>
                                                    <html:hidden property="cityId" />
                                                    <html:text property="cityName" styleId="cityName" styleClass="form-control" maxlength="100" onblur="if (this.value == '') {
                                                                    document.forms[0].cityId.value = '0';
                                                                }"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cityName").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-2 col-md-12 col-sm-12 col-12 form_group pd_left_null">
                                                                <a href="javascript:;" onclick="javascript: addtomaster('3')" class="add_btn"><i class="mdi mdi-plus"></i></a>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Operator</label>
                                                    <html:text property="clientpartyname" styleId="clientpartyname" styleClass="form-control" maxlength="100"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("clientpartyname").setAttribute('placeholder', '');
                                                        document.getElementById("clientpartyname").setAttribute('autocomplete', 'off');
                                                    </script>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Water Depth</label>
                                                    <html:select property="waterdepthId" styleClass="form-select">
                                                        <html:optionsCollection filter="false" property="waterdepths" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Last drawn salary / Day rate</label>
                                                    <div class="row">
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4">
                                                            <html:select property="lastdrawnsalarycurrencyId" styleClass="form-select">
                                                                <html:optionsCollection filter="false" property="currencies" label="ddlLabel" value="ddlValue">
                                                                </html:optionsCollection>
                                                            </html:select>
                                                        </div>
                                                        <div class="col-lg-8 col-md-8 col-sm-8 col-4">
                                                            <html:text property="lastdrawnsalary" styleId="lastdrawnsalary" styleClass="form-control text-right" maxlength="100"/>
                                                            <script type="text/javascript">
                                                                document.getElementById("lastdrawnsalary").setAttribute('placeholder', '');
                                                            </script>
                                                        </div>
                                                    </div>
                                                </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                                <label class="form_label">Roles And Responsibilities</label>
                                                                <html:textarea property="role" rows="6" styleId="role" styleClass="form-control"></html:textarea>
                                                                <script type="text/javascript">
                                                                    document.getElementById("role").setAttribute('placeholder', '');
                                                                    document.getElementById("role").setAttribute('maxlength', '1000');
                                                                </script>
                                                            </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Work Start Date<span class="required">*</span></label>
                                                    <div class="input-daterange input-group">
                                                        <html:text property="workstartdate" styleId="workstartdate" styleClass="form-control add-style wesl_dt date-add" />
                                                        <script type="text/javascript">
                                                            document.getElementById("workstartdate").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                        </script>                                                      
                                                    </div>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <div class="row">
                                                        <div class="col-lg-6 col-md-6 col-sm-6 col-4 form_group">
                                                            <label class="form_label">&nbsp;</label>
                                                            <div class="full_width">
                                                                <div class="form-check permission-check">
                                                                    <html:checkbox property="currentworkingstatus" value = "1" styleClass="form-check-input"  styleId="switch1" onchange="javascript:showhideexperience();" />
                                                                    <span class="ml_10"><b>I currently work here.</b></span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-6 col-md-6 col-sm-6 col-4 form_group">
                                                            <label class="form_label">Experience Certificate (if any) (.pdf/.jpeg/.png)</label>
                                                            <html:file property="experiencefile" styleId="upload1" onchange="javascript: setClass('1');"/>
                                                            <a href="javascript:;" id="upload_link_1" class="attache_btn"><i class="fas fa-paperclip"></i> Attach</a>

                                                            <% if(!filename.equals("")) {%><div class="down_prev"  id='preview_2'><a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframeedit('<%=filename%>', '2');">Preview</a></div><% } %>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group" id="all_workenddate">
                                                    <label class="form_label">Work End Date<span class="required">*</span></label>
                                                    <div class="input-daterange input-group">
                                                        <html:text property="workenddate" styleId="workenddate" styleClass="form-control add-style wesl_dt date-add" />
                                                        <script type="text/javascript">
                                                            document.getElementById("workenddate").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                        </script>
                                                    </div>
                                                </div>
                                                <div id="all_currentworking" style='display: none;'>                                                        
                                                    <div class="row" >
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Skills</label>
                                                            <html:select property="skillsId" styleClass="form-select">
                                                                <html:optionsCollection filter="false" property="skills" label="ddlLabel" value="ddlValue">
                                                                </html:optionsCollection>
                                                            </html:select>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Rank</label>
                                                            <html:select property="gradeid" styleClass="form-select">
                                                                <html:optionsCollection filter="false" property="grades" label="ddlLabel" value="ddlValue">
                                                                </html:optionsCollection>
                                                            </html:select>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Owner Pool</label>
                                                            <html:text property="ownerpool" styleId="ownerpool" styleClass="form-control" maxlength="100"/>
                                                            <script type="text/javascript">
                                                                document.getElementById("ownerpool").setAttribute('placeholder', '');
                                                            </script>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Crew Type</label>
                                                            <html:select property="crewtypeid" styleClass="form-select">
                                                                <html:optionsCollection filter="false" property="crewtypes" label="ddlLabel" value="ddlValue">
                                                                </html:optionsCollection>
                                                            </html:select>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">OCS Employed</label>
                                                            <html:select styleClass="form-select" property="ocsemployed">
                                                                <html:option value="">- Select -</html:option>
                                                                <html:option value="Yes">Yes</html:option>
                                                                <html:option value="No">No</html:option>
                                                            </html:select>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Legal Rights</label>
                                                            <html:select styleClass="form-select" property="legalrights">
                                                                <html:option value="">- Select -</html:option>
                                                                <html:option value="Yes">Yes</html:option>
                                                                <html:option value="No">No</html:option>
                                                            </html:select>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Day Rate</label>
                                                            <div class="row">
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12">
                                                                    <html:select property="dayratecurrencyid" styleClass="form-select">
                                                                        <html:optionsCollection filter="false" property="currencies" label="ddlLabel" value="ddlValue">
                                                                        </html:optionsCollection>
                                                                    </html:select>
                                                                </div>
                                                                <div class="col-lg-8 col-md-8 col-sm-8 col-12">
                                                                    <html:text property="dayrate" styleId="dayrate" styleClass="form-control text-right" maxlength="100"/>
                                                                    <script type="text/javascript">
                                                                        document.getElementById("dayrate").setAttribute('placeholder', '');
                                                                    </script>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Monthly Salary</label>
                                                            <div class="row">
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12">
                                                                    <html:select property="monthlysalarycurrencyId" styleClass="form-select">
                                                                        <html:optionsCollection filter="false" property="currencies" label="ddlLabel" value="ddlValue">
                                                                        </html:optionsCollection>
                                                                    </html:select>
                                                                </div>
                                                                <div class="col-lg-8 col-md-8 col-sm-8 col-12">
                                                                    <html:text property="monthlysalary" styleId="monthlysalary" styleClass="form-control text-right" maxlength="100"/>
                                                                    <script type="text/javascript">
                                                                        document.getElementById("monthlysalary").setAttribute('placeholder', '');
                                                                    </script>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Attach Documents (5MB each) (.pdf/.jpeg/.png)</label>
                                                            <html:file property="workingfile" styleId="upload2" onchange="javascript: setClass('2');"/>
                                                            <a href="javascript:;" id="upload_link_2" class="attache_btn"><i class="fas fa-paperclip"></i> Attach</a>
                                                            <% if(!filename.equals("")) {%><div class="down_prev"  id='preview_3'><a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframeedit('<%=filename1%>', '3');">Preview</a></div><% } %>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                                <a href="javascript: submitexperienceform();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
                                                <a href="javascript: openTab('10');" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
        <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        <span class="resume_title"> File</span>
                        <div class="float-end">
                            <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                            <a id='diframe' href="" class="down_btn" download=""><img src="../assets/images/download.png"/></a>
                            <a id='diframedel' href="" class="trash_icon"><img src="../assets/images/trash.png"/></a>
                        </div>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <iframe id='iframe' class="doc" src=""></iframe>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="relation_modal" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <input type='hidden' name='mtype' />
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id='maddid'>Relation</h4> 
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="row">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <label class="form_label">Name</label>
                                        <input class="form-control" placeholder="" name='mname' maxlength="100"/>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center"><a href="javascript:addtomasterajax();" class="save_button mt_15"><img src="../assets/images/save.png"> Save</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        <script type="text/javascript" src="../js/tiny/tinymce.min.js"></script>
        <script type="text/javascript" src="../js/tiny/form-editor.init.js"></script>
        <script>
        function setIframeedit(uval, type)
        {
            if (type == 2)
            {
                document.getElementById("diframedel").style.display = "";
                document.getElementById("diframedel").href = "javascript: delfileedit('2')";
            } else
                document.getElementById("diframedel").style.display = "none";
            var url_v = "", classname = "";
            if (uval.includes(".doc") || uval.includes(".docx") || uval.includes("wordprocessingml.document"))
            {
                url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
                classname = "doc_mode";
            } else if (uval.includes(".pdf"))
            {
                url_v = uval+"#toolbar=0&page=1&view=fitH,100";
                classname = "pdf_mode";
            } else
            {
                url_v = uval;
                classname = "img_mode";
            }
            window.top.$('#iframe').attr('src', 'about:blank');
            setTimeout(function () {
                window.top.$('#iframe').attr('src', url_v);
                document.getElementById("iframe").className = classname;
                document.getElementById("diframe").href = uval;
            }, 1000);

            $("#iframe").on("load", function () {
                let head = $("#iframe").contents().find("head");
                let css = '<style>img{margin: 0px auto;}</style>';
                $(head).append(css);
            });
        }
        </script>
        <script>
            showhideexperience();
        </script>
        <script type="text/javascript">
            jQuery(document).ready(function () {
                $(".kt-selectpicker").selectpicker();
                $(".wesl_dt").datepicker({
                    todayHighlight: !0,
                    format: "dd-M-yyyy",
                    autoclose: "true",
                    orientation: "bottom"
                });
            });
        </script>
        <script>
            $(function () {
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });
                $("#upload_link_2").on('click', function (e) {
                    e.preventDefault();
                    $("#upload2:hidden").trigger('click');
                });
            });
        </script>
        <script>
            $(function ()
            {
                $("#cityName").autocomplete({
                    source: function (request, response) {
                        $.ajax({
                            url: "/jxp/ajax/client/autofillcity.jsp",
                            type: 'post',
                            dataType: "json",
                            data: JSON.stringify({"search": request.term, "countryId": $("#countryId").val()}),
                            success: function (data) {
                                response(data);
                            }
                        });
                    },
                    select: function (event, ui) {
                        $('#cityName').val(ui.item.label); // display the selected text
                        $('input[name="cityId"]').val(ui.item.value);
                        return false;
                    },
                    focus: function (event, ui)
                    {
                        $('#cityName').val(ui.item.label); // display the selected text
                        return false;
                    }
                });
            });
        </script>
        <script>
            $(function ()
            {
                $("#clientpartyname").autocomplete({
                    source: function (request, response) {
                        $.ajax({
                            url: "/jxp/ajax/talentpool/autofillparty.jsp",
                            type: 'post',
                            dataType: "json",
                            data: JSON.stringify({"search": request.term}),
                            success: function (data) {
                                response(data);
                            }
                        });
                    },
                    select: function (event, ui) {
                        $('#clientpartyname').val(ui.item.label); // display the selected text
                        return false;
                    },
                    focus: function (event, ui)
                    {
                        $('#clientpartyname').val(ui.item.label); // display the selected text
                        return false;
                    }
                });
            });
        </script>
        <script type="text/javascript">
            function setval()
            {
                if (eval(document.forms[0].lastdrawnsalary.value) <= 0 && eval(document.forms[0].experiencedetailId.value) <= 0)
                    document.forms[0].lastdrawnsalary.value = "";
                if (eval(document.forms[0].dayrate.value) <= 0)
                    document.forms[0].dayrate.value = "";
                if (eval(document.forms[0].monthlysalary.value) <= 0)
                    document.forms[0].monthlysalary.value = "";
            }
            function addLoadEvent(func)
            {
                var oldonload = window.onload;
                if (typeof window.onload != 'function') {
                    window.onload = func;
                } else {
                    window.onload = function () {
                        if (oldonload) {
                            oldonload();
                        }
                    }
                }
            }
            addLoadEvent(setval());
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
