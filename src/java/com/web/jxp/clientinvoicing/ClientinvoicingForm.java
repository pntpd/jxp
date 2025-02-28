package com.web.jxp.clientinvoicing;

import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ClientinvoicingForm extends ActionForm {

    private int clientinvoicingId;
    private int status;
    private String search;
    private String doCancel;
    private String doModify;
    private String name;
    private String doAdd;
    private String doSave;
    private String doView;
    private int ctp;

    private Collection clients;
    private Collection assets;
    private Collection years;
    private int clientIdIndex;
    private int assetIdIndex;
    private int typeIndex;
    private int type;
    private int clientassetId;
    private int invoicestatusIndex;
    private int month;
    private int yearId;
    private int timesheetId;
    private String doGenerate;
    private String doGenerateInvoice;
    private String doMailInvoice;
    private String doPayment;
    private String doViewPayment;
    private int invoicetempId;
    private Collection invoicetemplates;
    private Collection banks;
    private int bankId;
    private String invoiceno;
    private String invoicedate;
    private int clientId;
    private double amount1;
    private double amount2;
    private double amount3;
    private FormFile attachfile;
    private String fromval;
    private String toval;
    private String ccval;
    private String bccval;
    private String subject;
    private String description;
    private int checkcb;
    private Collection paymentmode;
    private int paymentmodeId;
    private String paymentremark;
    private FormFile paymentadvice;
    private String doSavePayment;
    private String doSavePaySlip;
    private FormFile upload2;
    private String taxName;
    private String taxName1;
    private String taxName2;
    private String taxName3;
    private String taxName4;
    private double percentage;
    private double percentage1;
    private double percentage2;
    private double percentage3;
    private double percentage4;

    public String getDoSavePayment() {
        return doSavePayment;
    }

    public void setDoSavePayment(String doSavePayment) {
        this.doSavePayment = doSavePayment;
    }

    public String getPaymentremark() {
        return paymentremark;
    }

    public void setPaymentremark(String paymentremark) {
        this.paymentremark = paymentremark;
    }

    public FormFile getPaymentadvice() {
        return paymentadvice;
    }

    public void setPaymentadvice(FormFile paymentadvice) {
        this.paymentadvice = paymentadvice;
    }

    public Collection getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(Collection paymentmode) {
        this.paymentmode = paymentmode;
    }

    public int getPaymentmodeId() {
        return paymentmodeId;
    }

    public void setPaymentmodeId(int paymentmodeId) {
        this.paymentmodeId = paymentmodeId;
    }

    public String getFromval() {
        return fromval;
    }

    public void setFromval(String fromval) {
        this.fromval = fromval;
    }

    public String getToval() {
        return toval;
    }

    public void setToval(String toval) {
        this.toval = toval;
    }

    public String getCcval() {
        return ccval;
    }

    public void setCcval(String ccval) {
        this.ccval = ccval;
    }

    public String getBccval() {
        return bccval;
    }

    public void setBccval(String bccval) {
        this.bccval = bccval;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCheckcb() {
        return checkcb;
    }

    public void setCheckcb(int checkcb) {
        this.checkcb = checkcb;
    }

    public FormFile getAttachfile() {
        return attachfile;
    }

    public void setAttachfile(FormFile attachfile) {
        this.attachfile = attachfile;
    }

    public Collection getBanks() {
        return banks;
    }

    public void setBanks(Collection banks) {
        this.banks = banks;
    }

    public int getInvoicetempId() {
        return invoicetempId;
    }

    public void setInvoicetempId(int invoicetempId) {
        this.invoicetempId = invoicetempId;
    }

    public Collection getInvoicetemplates() {
        return invoicetemplates;
    }

    public void setInvoicetemplates(Collection invoicetemplates) {
        this.invoicetemplates = invoicetemplates;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(String invoicedate) {
        this.invoicedate = invoicedate;
    }

    public double getAmount1() {
        return amount1;
    }

    public void setAmount1(double amount1) {
        this.amount1 = amount1;
    }

    public double getAmount2() {
        return amount2;
    }

    public void setAmount2(double amount2) {
        this.amount2 = amount2;
    }

    public double getAmount3() {
        return amount3;
    }

    public void setAmount3(double amount3) {
        this.amount3 = amount3;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getDoGenerateInvoice() {
        return doGenerateInvoice;
    }

    public void setDoGenerateInvoice(String doGenerateInvoice) {
        this.doGenerateInvoice = doGenerateInvoice;
    }

    public String getDoMailInvoice() {
        return doMailInvoice;
    }

    public void setDoMailInvoice(String doMailInvoice) {
        this.doMailInvoice = doMailInvoice;
    }

    public String getDoViewPayment() {
        return doViewPayment;
    }

    public void setDoViewPayment(String doViewPayment) {
        this.doViewPayment = doViewPayment;
    }

    public int getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(int timesheetId) {
        this.timesheetId = timesheetId;
    }

    public String getDoGenerate() {
        return doGenerate;
    }

    public void setDoGenerate(String doGenerate) {
        this.doGenerate = doGenerate;
    }

    public String getDoPayment() {
        return doPayment;
    }

    public void setDoPayment(String doPayment) {
        this.doPayment = doPayment;
    }

    public Collection getYears() {
        return years;
    }

    public void setYears(Collection years) {
        this.years = years;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYearId() {
        return yearId;
    }

    public void setYearId(int yearId) {
        this.yearId = yearId;
    }

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    /**
     * @return the clientinvoicingId
     */
    public int getClientinvoicingId() {
        return clientinvoicingId;
    }

    /**
     * @param clientinvoicingId the clientinvoicingId to set
     */
    public void setClientinvoicingId(int clientinvoicingId) {
        this.clientinvoicingId = clientinvoicingId;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the search
     */
    public String getSearch() {
        return search;
    }

    /**
     * @param search the search to set
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * @return the doCancel
     */
    public String getDoCancel() {
        return doCancel;
    }

    /**
     * @param doCancel the doCancel to set
     */
    public void setDoCancel(String doCancel) {
        this.doCancel = doCancel;
    }

    /**
     * @return the doModify
     */
    public String getDoModify() {
        return doModify;
    }

    /**
     * @param doModify the doModify to set
     */
    public void setDoModify(String doModify) {
        this.doModify = doModify;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the doAdd
     */
    public String getDoAdd() {
        return doAdd;
    }

    /**
     * @param doAdd the doAdd to set
     */
    public void setDoAdd(String doAdd) {
        this.doAdd = doAdd;
    }

    /**
     * @return the doSave
     */
    public String getDoSave() {
        return doSave;
    }

    /**
     * @param doSave the doSave to set
     */
    public void setDoSave(String doSave) {
        this.doSave = doSave;
    }
    /**
     * @return the doView
     */
    public String getDoView() {
        return doView;
    }

    /**
     * @param doView the doView to set
     */
    public void setDoView(String doView) {
        this.doView = doView;
    }
    public Collection getClients() {
        return clients;
    }

    public void setClients(Collection clients) {
        this.clients = clients;
    }

    public Collection getAssets() {
        return assets;
    }

    public void setAssets(Collection assets) {
        this.assets = assets;
    }

    public int getClientIdIndex() {
        return clientIdIndex;
    }

    public void setClientIdIndex(int clientIdIndex) {
        this.clientIdIndex = clientIdIndex;
    }

    public int getAssetIdIndex() {
        return assetIdIndex;
    }

    public void setAssetIdIndex(int assetIdIndex) {
        this.assetIdIndex = assetIdIndex;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(int typeIndex) {
        this.typeIndex = typeIndex;
    }

    public int getClientassetId() {
        return clientassetId;
    }

    public void setClientassetId(int clientassetId) {
        this.clientassetId = clientassetId;
    }

    public int getInvoicestatusIndex() {
        return invoicestatusIndex;
    }

    public void setInvoicestatusIndex(int invoicestatusIndex) {
        this.invoicestatusIndex = invoicestatusIndex;
    }

    public String getDoSavePaySlip() {
        return doSavePaySlip;
    }

    public void setDoSavePaySlip(String doSavePaySlip) {
        this.doSavePaySlip = doSavePaySlip;
    }

    public FormFile getUpload2() {
        return upload2;
    }

    public void setUpload2(FormFile upload2) {
        this.upload2 = upload2;
    }

    /**
     * @return the taxName
     */
    public String getTaxName() {
        return taxName;
    }

    /**
     * @param taxName the taxName to set
     */
    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    /**
     * @return the percentage
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * @param percentage the percentage to set
     */
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    /**
     * @return the taxName1
     */
    public String getTaxName1() {
        return taxName1;
    }

    /**
     * @param taxName1 the taxName1 to set
     */
    public void setTaxName1(String taxName1) {
        this.taxName1 = taxName1;
    }

    /**
     * @return the taxName2
     */
    public String getTaxName2() {
        return taxName2;
    }

    /**
     * @param taxName2 the taxName2 to set
     */
    public void setTaxName2(String taxName2) {
        this.taxName2 = taxName2;
    }

    /**
     * @return the taxName3
     */
    public String getTaxName3() {
        return taxName3;
    }

    /**
     * @param taxName3 the taxName3 to set
     */
    public void setTaxName3(String taxName3) {
        this.taxName3 = taxName3;
    }

    /**
     * @return the percentage1
     */
    public double getPercentage1() {
        return percentage1;
    }

    /**
     * @param percentage1 the percentage1 to set
     */
    public void setPercentage1(double percentage1) {
        this.percentage1 = percentage1;
    }

    /**
     * @return the percentage2
     */
    public double getPercentage2() {
        return percentage2;
    }

    /**
     * @param percentage2 the percentage2 to set
     */
    public void setPercentage2(double percentage2) {
        this.percentage2 = percentage2;
    }

    /**
     * @return the percentage3
     */
    public double getPercentage3() {
        return percentage3;
    }

    /**
     * @param percentage3 the percentage3 to set
     */
    public void setPercentage3(double percentage3) {
        this.percentage3 = percentage3;
    }

    /**
     * @return the taxName4
     */
    public String getTaxName4() {
        return taxName4;
    }

    /**
     * @param taxName4 the taxName4 to set
     */
    public void setTaxName4(String taxName4) {
        this.taxName4 = taxName4;
    }

    /**
     * @return the percentage4
     */
    public double getPercentage4() {
        return percentage4;
    }

    /**
     * @param percentage4 the percentage4 to set
     */
    public void setPercentage4(double percentage4) {
        this.percentage4 = percentage4;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

   

}
