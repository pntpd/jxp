package com.web.jxp.clientinvoicing;

import com.web.jxp.base.Validate;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import static com.web.jxp.common.Common.*;
import com.web.jxp.user.UserInfo;
import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.Stack;
import org.apache.struts.upload.FormFile;

public class ClientinvoicingAction extends Action 
{

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        ClientinvoicingForm frm = (ClientinvoicingForm) form;
        Clientinvoicing clientinvoicing = new Clientinvoicing();
        int count = clientinvoicing.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "", username = "";
        if (request.getSession().getAttribute("LOGININFO") != null) 
        {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null)
            {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
                cids = uInfo.getCids();
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids();
                username = uInfo.getName();
            }
        }
        int check_user = clientinvoicing.checkUserSession(request, 77, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = clientinvoicing.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Client Invoicing Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }

        Collection clients = clientinvoicing.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        Collection assets = clientinvoicing.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);

        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);

        int typeIndex = frm.getTypeIndex();
        frm.setTypeIndex(typeIndex);

        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setClientinvoicingId(-1);
            saveToken(request);
            return mapping.findForward("add_clientinvoicing");
        } else if (frm.getDoGenerate() != null && frm.getDoGenerate().equals("yes")) {
            frm.setDoGenerate("no");
            int timesheetId = frm.getTimesheetId();
            frm.setTimesheetId(timesheetId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            ClientinvoicingInfo info = clientinvoicing.getTimesheetDetailByIdforDetail(timesheetId);
            request.setAttribute("CLIENTTIMESHEETGEN", info);
            frm.setAmount1(info.getAmount1());
            Collection invoicetemplates = clientinvoicing.getinvoicetemplatesforclientindex(info.getClientId());
            frm.setInvoicetemplates(invoicetemplates);

            Collection banks = clientinvoicing.getBank();
            frm.setBanks(banks);
            frm.setClientId(info.getClientId());

            request.setAttribute("PDFFILENAME", info.getGeneratedfile());
            return mapping.findForward("generate_invoice");
        } else if (frm.getDoMailInvoice() != null && frm.getDoMailInvoice().equals("yes")) {
            print(this, " DoMailInvoice block :: ");
            frm.setDoMailInvoice("no");
            int timesheetId = frm.getTimesheetId();
            frm.setTimesheetId(timesheetId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            String fromval = frm.getFromval();
            String toval = frm.getToval();
            String ccval = frm.getCcval();
            String bccval = frm.getBccval();
            String subject = frm.getSubject();
            String description = frm.getDescription();
            FormFile attachfile = frm.getAttachfile();

            ClientinvoicingInfo info1 = clientinvoicing.getTimesheetDetailByIdforDetailMail(timesheetId);

            String add_invoice_pdf = clientinvoicing.getMainPath("add_invoice_pdf");
            String foldername = clientinvoicing.createFolder(add_invoice_pdf);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "";
            try {
                if (attachfile != null && attachfile.getFileSize() > 0) {
                    fileName1 = clientinvoicing.uploadFile(0, "", attachfile, fn + "_1", add_invoice_pdf, foldername);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            clientinvoicing.sendInvoiceMail(fromval, toval, ccval, bccval, subject, description, info1.getGeneratedfile(), fileName1, info1.getClientName(), timesheetId, username);

            frm.setClientassetId(clientassetId);
            int invoicestatusIndex = frm.getInvoicestatusIndex();
            frm.setInvoicestatusIndex(invoicestatusIndex);
            int month = frm.getMonth();
            frm.setMonth(month);
            Collection years = clientinvoicing.getYears();
            frm.setYears(years);
            int yearId = frm.getYearId();
            frm.setYearId(yearId);

            ClientinvoicingInfo info = clientinvoicing.getClientDetailById(clientassetId);
            request.setAttribute("CLIENTTIMESHEET_DETAIL", info);
            ArrayList timesheetList = clientinvoicing.getClientassetdetailByName(search, invoicestatusIndex, 0, count, month, yearId, clientassetId);
            int cnt = 0;
            if (timesheetList.size() > 0) {
                ClientinvoicingInfo cinfo = (ClientinvoicingInfo) timesheetList.get(timesheetList.size() - 1);
                cnt = cinfo.getTimesheetId();
                timesheetList.remove(timesheetList.size() - 1);
            }
            request.getSession().setAttribute("CLIENTTIMESHEET_LIST", timesheetList);
            request.getSession().setAttribute("COUNT_LISTTS", cnt + "");
            request.getSession().setAttribute("NEXTTS", "0");
            request.getSession().setAttribute("NEXTVALUETS", "1");
            return mapping.findForward("view_indextimesheet");
        } else if (frm.getDoPayment() != null && frm.getDoPayment().equals("yes")) {
            frm.setDoPayment("no");
            int timesheetId = frm.getTimesheetId();
            frm.setTimesheetId(timesheetId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            ClientinvoicingInfo info = clientinvoicing.getTimesheetDetailByIdforDetail(timesheetId);
            request.setAttribute("CLIENTDOPAYGEN", info);
            ArrayList list = clientinvoicing.getTimesheetDetailfromMaillogtablelist(timesheetId);
            request.setAttribute("CLIENTDOPAYLIST", list);           

            request.setAttribute("PDFFILENAME", info.getGeneratedfile());
            return mapping.findForward("invoice_detail");
        } else if (frm.getDoSavePayment() != null && frm.getDoSavePayment().equals("yes")) {
            frm.setDoSavePayment("no");
            int timesheetId = frm.getTimesheetId();

            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);

            int paymentmodeId = frm.getPaymentmodeId();
            String paymentremark = frm.getPaymentremark();
            FormFile paymentadvice = frm.getPaymentadvice();
            String add_invoice_pdf = clientinvoicing.getMainPath("add_invoice_pdf");
            String foldername = clientinvoicing.createFolder(add_invoice_pdf);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "";
            try {
                if (paymentadvice != null && paymentadvice.getFileSize() > 0) {
                    fileName1 = clientinvoicing.uploadFile(0, "", paymentadvice, fn + "_1", add_invoice_pdf, foldername);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            clientinvoicing.updatePaymentdata(timesheetId, paymentmodeId, paymentremark, fileName1, uId);

            frm.setClientassetId(clientassetId);
            int invoicestatusIndex = frm.getInvoicestatusIndex();
            frm.setInvoicestatusIndex(invoicestatusIndex);
            int month = frm.getMonth();
            frm.setMonth(month);
            Collection years = clientinvoicing.getYears();
            frm.setYears(years);
            int yearId = frm.getYearId();
            frm.setYearId(yearId);

            ClientinvoicingInfo info = clientinvoicing.getClientDetailById(clientassetId);
            request.setAttribute("CLIENTTIMESHEET_DETAIL", info);
            ArrayList timesheetList = clientinvoicing.getClientassetdetailByName(search, invoicestatusIndex, 0, count, month, yearId, clientassetId);
            int cnt = 0;
            if (timesheetList.size() > 0) {
                ClientinvoicingInfo cinfo = (ClientinvoicingInfo) timesheetList.get(timesheetList.size() - 1);
                cnt = cinfo.getTimesheetId();
                timesheetList.remove(timesheetList.size() - 1);
            }
            request.getSession().setAttribute("CLIENTTIMESHEET_LIST", timesheetList);
            request.getSession().setAttribute("COUNT_LISTTS", cnt + "");
            request.getSession().setAttribute("NEXTTS", "0");
            request.getSession().setAttribute("NEXTVALUETS", "1");
            return mapping.findForward("view_indextimesheet");
        } else if (frm.getDoViewPayment() != null && frm.getDoViewPayment().equals("yes")) {
            frm.setDoViewPayment("no");
            int timesheetId = frm.getTimesheetId();
            frm.setTimesheetId(timesheetId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            ClientinvoicingInfo info = clientinvoicing.getTimesheetDetailByIdforDetail(timesheetId);
            request.setAttribute("CLIENTDOPAYGEN", info);
            ArrayList list = clientinvoicing.getTimesheetDetailfromMaillogtablelist(timesheetId);
            request.setAttribute("CLIENTDOPAYLIST", list);

            request.setAttribute("PDFFILENAME", info.getGeneratedfile());
            return mapping.findForward("payment_detail");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            int invoicestatusIndex = frm.getInvoicestatusIndex();
            frm.setInvoicestatusIndex(invoicestatusIndex);
            int month = frm.getMonth();
            frm.setMonth(month);
            Collection years = clientinvoicing.getYears();
            frm.setYears(years);
            int yearId = frm.getYearId();
            frm.setYearId(yearId);

            ClientinvoicingInfo info = clientinvoicing.getClientDetailById(clientassetId);
            request.setAttribute("CLIENTTIMESHEET_DETAIL", info);
            ArrayList timesheetList = clientinvoicing.getClientassetdetailByName(search, invoicestatusIndex, 0, count, month, yearId, clientassetId);
            int cnt = 0;
            if (timesheetList.size() > 0) {
                ClientinvoicingInfo cinfo = (ClientinvoicingInfo) timesheetList.get(timesheetList.size() - 1);
                cnt = cinfo.getTimesheetId();
                timesheetList.remove(timesheetList.size() - 1);
            }
            request.getSession().setAttribute("CLIENTTIMESHEET_LIST", timesheetList);
            request.getSession().setAttribute("COUNT_LISTTS", cnt + "");
            request.getSession().setAttribute("NEXTTS", "0");
            request.getSession().setAttribute("NEXTVALUETS", "1");
            return mapping.findForward("view_indextimesheet");

        } else if (frm.getDoSavePaySlip() != null && frm.getDoSavePaySlip().equals("yes")) {
            frm.setDoSavePaySlip("no");
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            int invoicestatusIndex = frm.getInvoicestatusIndex();
            frm.setInvoicestatusIndex(invoicestatusIndex);
            int month = frm.getMonth();
            frm.setMonth(month);
            Collection years = clientinvoicing.getYears();
            frm.setYears(years);
            int yearId = frm.getYearId();
            frm.setYearId(yearId);
            FormFile filename = frm.getUpload2();

            String add_candidate_file = clientinvoicing.getMainPath("add_candidate_file");
            String foldername = clientinvoicing.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName = "";

            if (filename != null && filename.getFileSize() > 0) {
                fileName = clientinvoicing.uploadFile(0, "", filename, fn, add_candidate_file, foldername);
            }
            File dir = new File(add_candidate_file + foldername + "/" + fn + "_uz/");
            if (!dir.exists()) {
                dir.mkdir();
            }
            clientinvoicing.unzip(add_candidate_file + fileName, add_candidate_file + foldername + "/" + fn + "_uz/");
            fileName = foldername + "/" + fn + "_uz/";

            ClientinvoicingInfo info = clientinvoicing.getClientDetailById(clientassetId);
            int cc = clientinvoicing.savesalaryslip((info != null ? info.getCounrtyId() : 0), clientassetId, add_candidate_file + foldername + "/" + fn + "_uz/", fileName);
            if (cc > 0) {
                clientinvoicing.createPaySlipHistory(fileName, uId, clientassetId);
            }
            request.setAttribute("CLIENTTIMESHEET_DETAIL", info);
            ArrayList timesheetList = clientinvoicing.getClientassetdetailByName(search, invoicestatusIndex, 0, count, month, yearId, clientassetId);
            int cnt = 0;
            if (timesheetList.size() > 0) {
                ClientinvoicingInfo cinfo = (ClientinvoicingInfo) timesheetList.get(timesheetList.size() - 1);
                cnt = cinfo.getTimesheetId();
                timesheetList.remove(timesheetList.size() - 1);
            }
            request.setAttribute("CANDSAVEMODEL", "yes");
            request.getSession().setAttribute("CLIENTTIMESHEET_LIST", timesheetList);
            request.getSession().setAttribute("COUNT_LISTTS", cnt + "");
            request.getSession().setAttribute("NEXTTS", "0");
            request.getSession().setAttribute("NEXTVALUETS", "1");           
            return mapping.findForward("view_indextimesheet");
        }  else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            print(this, "doCancel block");
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList clientinvoicingList = clientinvoicing.getClientinvoicingByName(search, next, count, allclient, permission, cids, assetids, clientIdIndex, assetIdIndex, typeIndex);
            int cnt = 0;
            if (clientinvoicingList.size() > 0) {
                ClientinvoicingInfo cinfo = (ClientinvoicingInfo) clientinvoicingList.get(clientinvoicingList.size() - 1);
                cnt = cinfo.getClientassetId();
                clientinvoicingList.remove(clientinvoicingList.size() - 1);
            }
            request.getSession().setAttribute("CLIENTINVOICING_LIST", clientinvoicingList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        } else {
            print(this, "else block.");
            if (frm.getCtp() == 0) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            ArrayList clientinvoicingList = clientinvoicing.getClientinvoicingByName(search, 0, count, allclient, permission, cids, assetids, clientIdIndex, assetIdIndex, typeIndex);
            int cnt = 0;
            if (clientinvoicingList.size() > 0) {
                ClientinvoicingInfo cinfo = (ClientinvoicingInfo) clientinvoicingList.get(clientinvoicingList.size() - 1);
                cnt = cinfo.getClientassetId();
                clientinvoicingList.remove(clientinvoicingList.size() - 1);
            }
            request.getSession().setAttribute("CLIENTINVOICING_LIST", clientinvoicingList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
