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
            int mtp = 2, submtp = 4, ctp = 24;
            if (session.getAttribute("LOGININFO") == null)
            {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
            }
            String message = "", clsmessage = "red_font";
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }        
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";
            ArrayList list = new ArrayList();
            if(request.getSession().getAttribute("MODULEPER_LIST") != null)
                list = (ArrayList)request.getSession().getAttribute("MODULEPER_LIST");            
    %>
    <head>
        <meta charset="utf-8">
        <title><%= talentpool.getMainPath("title") != null ? talentpool.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/talentpool.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="doManageNomineedetail"/>
        <html:hidden property="doSaveNomineedetail"/>
        <html:hidden property="doDeleteNomineedetail"/>
        <html:hidden property="nomineedetailId"/>
        <div id="layout-wrapper">
            <%@include file ="../header.jsp"%>
            <%@include file ="../sidemenu.jsp"%>
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
                                            <div class="main-heading">                                           
                                                <div class="add-btn">
                                                    <h4>NOMINEE</h4>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Nominee Name<span class="required">*</span></label>
                                                <html:text property="nomineeName" styleId="nomineeName" styleClass="form-control" maxlength="200"/>
                                                <script type="text/javascript">
                                                    document.getElementById("nomineeName").setAttribute('placeholder', '');
                                                </script>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Nominee Contact No<span class="required">*</span></label>
                                                <div class="row">
                                                    <div class="col-lg-4">
                                                        <html:select property="code1Id" styleClass="form-select">
                                                            <html:optionsCollection filter="false" property="countries" label="ddlLabel3" value="ddlLabel2">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                    <div class="col-lg-8">
                                                        <html:text property="nomineeContactno" styleId="nomineeContactno" styleClass="form-control" maxlength="20" onkeypress="return allowPositiveNumber1(event);"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("nomineeContactno").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>                                                        
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Relation<span class="required">*</span></label>
                                                <html:select property="relationId" styleClass="form-select">
                                                    <html:optionsCollection filter="false" property="relations" label="ddlLabel" value="ddlValue">
                                                    </html:optionsCollection>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Age Of Nominee</label>
                                                <html:text property="age" styleId="age" styleClass="form-control" maxlength="3" onkeypress="return allowPositiveNumber1(event);"/>
                                                <script type="text/javascript">
                                                    document.getElementById("age").setAttribute('placeholder', '');
                                                </script>
                                            </div>                                            
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4">
                                                <div class="row">
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                        <label class="form_label">Percentage Of Distribution<span class="required"></span></label>
                                                        <html:text property="percentage" styleId="percentage" styleClass="form-control m_15" maxlength="6" onkeypress="return allowPositiveNumber(event);"/>                              
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4">
                                                <div class="row">
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                        <label class="form_label">Address<span class="required"></span></label> 
                                                            <html:textarea property="address" styleId="address" styleClass="form-control" rows="4"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("address").setAttribute('placeholder', '');
                                                            document.getElementById("address").setAttribute('maxlength', '1000');
                                                        </script>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                        <a href="javascript: submitnomineeform();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
                                        <a href="javascript:openTab('24');" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> 
                </div>
            </div>
        </div>
        <%@include file ="../footer.jsp"%>

        <script src="../assets/libs/jquery/jquery.min.js"></script>		
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>	
        <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        <script type="text/javascript">            
            function setval()
            {
                if (eval(document.forms[0].age.value) <= 0)
                    document.forms[0].age.value = "";
                if (eval(document.forms[0].percentage.value) <= 0)
                    document.forms[0].percentage.value = "";
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
