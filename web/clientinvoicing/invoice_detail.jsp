<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.clientinvoicing.ClientinvoicingInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="clientinvoicing" class="com.web.jxp.clientinvoicing.Clientinvoicing" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 2, submtp = 77;
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
                

                ClientinvoicingInfo cinfo = null;
            if(request.getAttribute("CLIENTDOPAYGEN") != null)
            {
                cinfo = (ClientinvoicingInfo)request.getAttribute("CLIENTDOPAYGEN");
            }
                ArrayList list = new ArrayList();
            if(request.getAttribute("CLIENTDOPAYLIST") != null)
            {
                list = (ArrayList)request.getAttribute("CLIENTDOPAYLIST");
            }
                int list_size = list.size();
                String pdffilename = "";
                if(request.getAttribute("PDFFILENAME") != null)
                {
                    pdffilename = (String) request.getAttribute("PDFFILENAME");   
                }

                String file_path = clientinvoicing.getMainPath("view_invoice_pdf");
                String pdffile = "";
                if(!pdffilename.equals(""))
                {
                    pdffile = file_path + pdffilename;
                }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= clientinvoicing.getMainPath("title") != null ? clientinvoicing.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/clientinvoicing.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/clientinvoicing/ClientinvoicingAction.do" onsubmit="return false;" enctype="multipart/form-data">
        <html:hidden property="doSave"/>
        <html:hidden property="doView"/>
        <html:hidden property="doGenerate"/>
        <html:hidden property="doGenerateInvoice"/>
        <html:hidden property="doSavePayment"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="timesheetId"/>
        <html:hidden property="clientId"/>
        <html:hidden property="clientassetId"/>
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <div class="main-content">
                <div class="page-content tab_panel1 no_tab1">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow"><a href="javascript: showDetail('<%= cinfo.getClientassetId()%>');"><img  src="../assets/images/back-arrow.png"/></a> <span>Invoicing</span></div>
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
                                    <div class="col-md-12 metrix_top_right">
                                        <div class="row d-flex align-items-center">
                                            <div class="col-md-3 com_label_value">
                                                <div class="row mb_0">
                                                    <div class="col-md-3"><label>Client</label></div>
                                                    <div class="col-md-9"><span><%= cinfo != null && !cinfo.getClientName().equals("") ? cinfo.getClientName() : ""%></span></div>
                                                </div>
                                            </div>
                                            <div class="col-md-3 com_label_value">
                                                <div class="row mb_0">
                                                    <div class="col-md-3 pd_0"><label>Asset</label></div>
                                                    <div class="col-md-9 pd_0"><span><%= cinfo != null && !cinfo.getClientassetName().equals("") ? cinfo.getClientassetName() : ""%></span></div>
                                                </div>
                                            </div>

                                            <div class="col-lg-6">
                                                <div class="row d-flex align-items-center">
                                                    <div class="col-md-2 com_label_value">
                                                        <div class="row mb_0">
                                                            <div class="col-md-9 pd_0"><label>Invoice</label></div>
                                                            <div class="col-md-3 pd_0"><span><%= clientinvoicing.changeNum( cinfo.getInvoiceId(),6) %></span></div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                    </div>

                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background com_checks1">

                                    <div class="row">
                                        <div class="col-md-12  ">
                                            <div class="main-heading mt_30 mb_20">
                                                <div class="add-btn"><h4>INVOICE DETAILS</h4></div>
                                            </div>
                                        </div>
                                        <div class="col-lg-8 col-md-8 col-sm-12 col-12">
                                            <div class="row">
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 re_generate">
                                                    <iframe class="cv_file" src="<%= pdffile%>" id="iframe" ></iframe>
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12"><a href="javascript: doGenerateinvoice('<%= cinfo.getTimesheetId()%>');" class="re_gen_btn"> Re-generate</a></div>
                                                </div>
                                            </div>	
                                        </div>	
                                        
                                                    <div class="col-lg-4 col-md-4 col-sm-12 col-12">
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                            <div class="row">
                                                                <table width="100%" class="table">
                                                                    
<%
                                                        ClientinvoicingInfo info;
                                                        for (int i = 0; i < list_size; i++)
                                                        {
                                                            info = (ClientinvoicingInfo) list.get(i);
                                                            if (info != null) 
                                                            {
%>
                                                                    <tr>
                                                                        <%if(i == 0){%>
                                                                        <td width="30%" rowspan="<%=i%>"><label class="form_label">Email Sent on</label></td>
                                                                        <%}%>
                                                                        <td width="40%"><label class="form_label"><%=info.getDate()%></label></td>
                                                                        <td width="30%" align="right"><a href="javascript:;" onclick =" javascript: getviewmailmodal(<%=info.getMaillogId()%>);"  data-bs-toggle="modal" data-bs-target="#mail_modal" class="generate_action">View Email</a></td>
                                                                   </tr>
                                                                        <%}}%>  
                                                                   
                                                                    
                                                                </table>
                                                            </div>
                                                        </div>                                                        
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                            <label class="form_label">Remarks</label>
                                                            <html:textarea property="paymentremark" rows="5" styleId="paymentremark" styleClass="form-control"></html:textarea>
                                                                <script type="text/javascript">
                                                                    document.getElementById("paymentremark").setAttribute('placeholder', '');
                                                                    document.getElementById("paymentremark").setAttribute('maxlength', '500');
                                                                </script>
                                                        </div>
                                                        <div class="col-lg-6 col-md-6 col-sm-6 col-4 form_group">
                                                            <label class="form_label">Attach Payment Advice</label>
                                                            <html:file property="paymentadvice" styleId="upload1" onchange="javascript: setClass('1');"/>
                                                            <a href="javascript:;" id="upload_link_1" class="attache_btn uploaded_img1">
                                                                <i class="fas fa-paperclip"></i> Attach
                                                            </a>
                                                        </div>

                                                    </div>	
                                    </div>
                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                            <% if (editper.equals("Y") || addper.equals("Y")){ %> 
                                                            <a href="javascript: ;" onclick ="javascript: submitPaymentadviceForm()" class="save_btn">Payment Received</a>
                                                            <%}%>
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
        <div id="mail_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id = 'mailmodal'>

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
        <script>
                        $(function(){
                                $("#upload_link_1").on('click', function(e){
                                        e.preventDefault();
                                        $("#upload1:hidden").trigger('click');
                                });
				
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