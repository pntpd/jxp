<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.clientinvoicing.ClientinvoicingInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="clientinvoicing" class="com.web.jxp.clientinvoicing.Clientinvoicing" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null)
        {
            int uid = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            if (uinfo != null)
            {
                uid = uinfo.getUserId();
            }
            String invoicetempIds = request.getParameter("invoicetempId") != null && !request.getParameter("invoicetempId").equals("") ? vobj.replaceint(request.getParameter("invoicetempId")) : "0";
            String timesheetIds = request.getParameter("timesheetId") != null && !request.getParameter("timesheetId").equals("") ? vobj.replaceint(request.getParameter("timesheetId")) : "0";
            String bankIds = request.getParameter("bankId") != null && !request.getParameter("bankId").equals("") ? vobj.replaceint(request.getParameter("bankId")) : "0";
            String invoicenos = request.getParameter("invoiceno") != null && !request.getParameter("invoiceno").equals("") ? vobj.replacedesc(request.getParameter("invoiceno")) : "";
            String invoicedates = request.getParameter("invoicedate") != null && !request.getParameter("invoicedate").equals("") ? vobj.replacedesc(request.getParameter("invoicedate")) : "";
            String amount1s = request.getParameter("amount1") != null && !request.getParameter("amount1").equals("") ? vobj.replacedouble(request.getParameter("amount1")) : "";
            String amount2s = request.getParameter("amount2") != null && !request.getParameter("amount2").equals("") ? vobj.replacedouble(request.getParameter("amount2")) : "";
            String amount3s = request.getParameter("amount3") != null && !request.getParameter("amount3").equals("") ? vobj.replacedouble(request.getParameter("amount3")) : "";
            String percentage_s = request.getParameter("percentage") != null && !request.getParameter("percentage").equals("") ? vobj.replacedouble(request.getParameter("percentage")) : "";
            String taxname = request.getParameter("taxName") != null && !request.getParameter("taxName").equals("") ? vobj.replacedesc(request.getParameter("taxName")) : "";
            String percentage_s1 = request.getParameter("percentage1") != null && !request.getParameter("percentage1").equals("") ? vobj.replacedouble(request.getParameter("percentage1")) : "";
            String taxname1 = request.getParameter("taxName1") != null && !request.getParameter("taxName1").equals("") ? vobj.replacedesc(request.getParameter("taxName1")) : "";
            String percentage_s2 = request.getParameter("percentage2") != null && !request.getParameter("percentage2").equals("") ? vobj.replacedouble(request.getParameter("percentage2")) : "";
            String taxname2 = request.getParameter("taxName2") != null && !request.getParameter("taxName2").equals("") ? vobj.replacedesc(request.getParameter("taxName2")) : "";
            String percentage_s3 = request.getParameter("percentage3") != null && !request.getParameter("percentage3").equals("") ? vobj.replacedouble(request.getParameter("percentage3")) : "";
            String taxname3 = request.getParameter("taxName3") != null && !request.getParameter("taxName3").equals("") ? vobj.replacedesc(request.getParameter("taxName3")) : "";
            String percentage_s4 = request.getParameter("percentage4") != null && !request.getParameter("percentage4").equals("") ? vobj.replacedouble(request.getParameter("percentage4")) : "";
            String taxname4 = request.getParameter("taxName4") != null && !request.getParameter("taxName4").equals("") ? vobj.replacedesc(request.getParameter("taxName4")) : "";
            int bankId = Integer.parseInt(bankIds);
            int invoicetempId = Integer.parseInt(invoicetempIds);
            int timesheetId = Integer.parseInt(timesheetIds);
            double amount1 = Double.parseDouble(amount1s);
            double amount2 = Double.parseDouble(amount2s);
            double amount3 = Double.parseDouble(amount3s);
            double percentage = Double.parseDouble(percentage_s);
            double percentage1 = Double.parseDouble(percentage_s1);
            double percentage2 = Double.parseDouble(percentage_s2);
            double percentage3 = Double.parseDouble(percentage_s3);
            double percentage4 = Double.parseDouble(percentage_s4);
            String filepath = clientinvoicing.getMainPath("view_invoice_pdf");

            clientinvoicing.updateGeneratedtemplatedata(timesheetId, invoicetempId,bankId,invoicenos,
                    invoicedates,amount1,amount2,amount3, uid, taxname, percentage, taxname1, percentage1, 
                    taxname2, percentage2, taxname3, percentage3, taxname4, percentage4);
            String pdffilename = clientinvoicing.generateInvoice(timesheetId);
            
            
            response.getWriter().write(filepath + pdffilename);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>