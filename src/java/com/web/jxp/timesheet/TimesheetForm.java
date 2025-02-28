package com.web.jxp.timesheet;
import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class TimesheetForm extends ActionForm
{
    private String search;
    private String doCancel;
    private String doSave;
    private String doApproveInvoice;
    private String doSentApproval;
    private String doSaveDraft;
    private String doDelete;
    private String doView;
    private String doSearch;
    private String doGetDetails;
    private String doCreateSheet;
    private Collection clients;
    private Collection assets;
    private Collection years;
    private int yearId;    
    private int clientIdIndex;    
    private int clientId;    
    private int assetIdIndex;
    private int revisionId;
    private int assetId;
    private int ctp;
    private int typevalue;
    private int month;
    private int status;
    private int currencyId;
    private int statusIndex;
    private int timesheetId;
    private int checktype;
    private String fromDate;
    private String toDate;
    private String email;
    private String ccaddress;
    private String bccaddress;
    private String message;
    private String subject;
    private String excelfile;
    private String currentDate;
    private String remarks;
    private String approveRemarks;
    private FormFile invoiceFile;
    private String invoicefilehidden;
    private FormFile sendApproveFile;
    private String sendApprovefilehidden;
    private double[] amount;
    private double[] prate;
    private double[] srate;
    private double[] gtotal;
    private double[] rate;
    private int[] flag;
    private int[] totalcrew;
    private int[] positionId;
    private int[] crewrotationId;
    private int[] timesheetdetailId;
    private int[] type;
    private String[] fromDate2;
    private String[] toDate2;
    private int[] days;
    private int[] appPositionId;
    private String doSummary;
    private int crewId;
    private int clientassetId;
    private int timesheetIdEdit;
    private int typeId;
    private String regenerate;
    private int repeatId;
    private String fromDateIndex;
    private String toDateIndex;
    
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
     * @return the doDelete
     */
    public String getDoDelete() {
        return doDelete;
    }

    /**
     * @param doDelete the doDelete to set
     */
    public void setDoDelete(String doDelete) {
        this.doDelete = doDelete;
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

    /**
     * @return the clients
     */
    public Collection getClients() {
        return clients;
    }

    /**
     * @param clients the clients to set
     */
    public void setClients(Collection clients) {
        this.clients = clients;
    }

    /**
     * @return the assets
     */
    public Collection getAssets() {
        return assets;
    }

    /**
     * @param assets the assets to set
     */
    public void setAssets(Collection assets) {
        this.assets = assets;
    }

    /**
     * @return the clientIdIndex
     */
    public int getClientIdIndex() {
        return clientIdIndex;
    }

    /**
     * @param clientIdIndex the clientIdIndex to set
     */
    public void setClientIdIndex(int clientIdIndex) {
        this.clientIdIndex = clientIdIndex;
    }

    /**
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * @return the assetIdIndex
     */
    public int getAssetIdIndex() {
        return assetIdIndex;
    }

    /**
     * @param assetIdIndex the assetIdIndex to set
     */
    public void setAssetIdIndex(int assetIdIndex) {
        this.assetIdIndex = assetIdIndex;
    }

    /**
     * @return the assetId
     */
    public int getAssetId() {
        return assetId;
    }

    /**
     * @param assetId the assetId to set
     */
    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    /**
     * @return the ctp
     */
    public int getCtp() {
        return ctp;
    }

    /**
     * @param ctp the ctp to set
     */
    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    /**
     * @return the typevalue
     */
    public int getTypevalue() {
        return typevalue;
    }

    /**
     * @param typevalue the typevalue to set
     */
    public void setTypevalue(int typevalue) {
        this.typevalue = typevalue;
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
     * @return the timesheetId
     */
    public int getTimesheetId() {
        return timesheetId;
    }

    /**
     * @param timesheetId the timesheetId to set
     */
    public void setTimesheetId(int timesheetId) {
        this.timesheetId = timesheetId;
    }

    /**
     * @return the fromDate
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the years
     */
    public Collection getYears() {
        return years;
    }

    /**
     * @param years the years to set
     */
    public void setYears(Collection years) {
        this.years = years;
    }

    /**
     * @return the yearId
     */
    public int getYearId() {
        return yearId;
    }

    /**
     * @param yearId the yearId to set
     */
    public void setYearId(int yearId) {
        this.yearId = yearId;
    }

    /**
     * @return the doSearch
     */
    public String getDoSearch() {
        return doSearch;
    }

    /**
     * @param doSearch the doSearch to set
     */
    public void setDoSearch(String doSearch) {
        this.doSearch = doSearch;
    }

    /**
     * @return the statusIndex
     */
    public int getStatusIndex() {
        return statusIndex;
    }

    /**
     * @param statusIndex the statusIndex to set
     */
    public void setStatusIndex(int statusIndex) {
        this.statusIndex = statusIndex;
    }

    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @return the doGetDetails
     */
    public String getDoGetDetails() {
        return doGetDetails;
    }

    /**
     * @param doGetDetails the doGetDetails to set
     */
    public void setDoGetDetails(String doGetDetails) {
        this.doGetDetails = doGetDetails;
    }

    /**
     * @return the doCreateSheet
     */
    public String getDoCreateSheet() {
        return doCreateSheet;
    }

    /**
     * @param doCreateSheet the doCreateSheet to set
     */
    public void setDoCreateSheet(String doCreateSheet) {
        this.doCreateSheet = doCreateSheet;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * @param currencyId the currencyId to set
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * @return the doSaveDraft
     */
    public String getDoSaveDraft() {
        return doSaveDraft;
    }

    /**
     * @param doSaveDraft the doSaveDraft to set
     */
    public void setDoSaveDraft(String doSaveDraft) {
        this.doSaveDraft = doSaveDraft;
    }

    /**
     * @return the currentDate
     */
    public String getCurrentDate() {
        return currentDate;
    }

    /**
     * @param currentDate the currentDate to set
     */
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    /**
     * @return the revisionId
     */
    public int getRevisionId() {
        return revisionId;
    }

    /**
     * @param revisionId the revisionId to set
     */
    public void setRevisionId(int revisionId) {
        this.revisionId = revisionId;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the invoiceFile
     */
    public FormFile getInvoiceFile() {
        return invoiceFile;
    }

    /**
     * @param invoiceFile the invoiceFile to set
     */
    public void setInvoiceFile(FormFile invoiceFile) {
        this.invoiceFile = invoiceFile;
    }

    /**
     * @return the invoicefilehidden
     */
    public String getInvoicefilehidden() {
        return invoicefilehidden;
    }

    /**
     * @param invoicefilehidden the invoicefilehidden to set
     */
    public void setInvoicefilehidden(String invoicefilehidden) {
        this.invoicefilehidden = invoicefilehidden;
    }

    /**
     * @return the doApproveInvoice
     */
    public String getDoApproveInvoice() {
        return doApproveInvoice;
    }

    /**
     * @param doApproveInvoice the doApproveInvoice to set
     */
    public void setDoApproveInvoice(String doApproveInvoice) {
        this.doApproveInvoice = doApproveInvoice;
    }

    /**
     * @return the approveRemarks
     */
    public String getApproveRemarks() {
        return approveRemarks;
    }

    /**
     * @param approveRemarks the approveRemarks to set
     */
    public void setApproveRemarks(String approveRemarks) {
        this.approveRemarks = approveRemarks;
    }

    /**
     * @return the doSentApproval
     */
    public String getDoSentApproval() {
        return doSentApproval;
    }

    /**
     * @param doSentApproval the doSentApproval to set
     */
    public void setDoSentApproval(String doSentApproval) {
        this.doSentApproval = doSentApproval;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the sendApproveFile
     */
    public FormFile getSendApproveFile() {
        return sendApproveFile;
    }

    /**
     * @param sendApproveFile the sendApproveFile to set
     */
    public void setSendApproveFile(FormFile sendApproveFile) {
        this.sendApproveFile = sendApproveFile;
    }

    /**
     * @return the sendApprovefilehidden
     */
    public String getSendApprovefilehidden() {
        return sendApprovefilehidden;
    }

    /**
     * @param sendApprovefilehidden the sendApprovefilehidden to set
     */
    public void setSendApprovefilehidden(String sendApprovefilehidden) {
        this.sendApprovefilehidden = sendApprovefilehidden;
    }

    /**
     * @return the ccaddress
     */
    public String getCcaddress() {
        return ccaddress;
    }

    /**
     * @param ccaddress the ccaddress to set
     */
    public void setCcaddress(String ccaddress) {
        this.ccaddress = ccaddress;
    }

    /**
     * @return the bccaddress
     */
    public String getBccaddress() {
        return bccaddress;
    }

    /**
     * @param bccaddress the bccaddress to set
     */
    public void setBccaddress(String bccaddress) {
        this.bccaddress = bccaddress;
    }

   

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the checktype
     */
    public int getChecktype() {
        return checktype;
    }

    /**
     * @param checktype the checktype to set
     */
    public void setChecktype(int checktype) {
        this.checktype = checktype;
    }

    /**
     * @return the excelfile
     */
    public String getExcelfile() {
        return excelfile;
    }

    /**
     * @param excelfile the excelfile to set
     */
    public void setExcelfile(String excelfile) {
        this.excelfile = excelfile;
    }

    /**
     * @return the amount
     */
    public double[] getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double[] amount) {
        this.amount = amount;
    }

    /**
     * @return the prate
     */
    public double[] getPrate() {
        return prate;
    }

    /**
     * @param prate the prate to set
     */
    public void setPrate(double[] prate) {
        this.prate = prate;
    }

    /**
     * @return the srate
     */
    public double[] getSrate() {
        return srate;
    }

    /**
     * @param srate the srate to set
     */
    public void setSrate(double[] srate) {
        this.srate = srate;
    }

    /**
     * @return the gtotal
     */
    public double[] getGtotal() {
        return gtotal;
    }

    /**
     * @param gtotal the gtotal to set
     */
    public void setGtotal(double[] gtotal) {
        this.gtotal = gtotal;
    }


    /**
     * @return the rate
     */
    public double[] getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(double[] rate) {
        this.rate = rate;
    }

   

    /**
     * @return the positionId
     */
    public int[] getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(int[] positionId) {
        this.positionId = positionId;
    }

    /**
     * @return the crewrotationId
     */
    public int[] getCrewrotationId() {
        return crewrotationId;
    }

    /**
     * @param crewrotationId the crewrotationId to set
     */
    public void setCrewrotationId(int[] crewrotationId) {
        this.crewrotationId = crewrotationId;
    }

    /**
     * @return the timesheetdetailId
     */
    public int[] getTimesheetdetailId() {
        return timesheetdetailId;
    }

    /**
     * @param timesheetdetailId the timesheetdetailId to set
     */
    public void setTimesheetdetailId(int[] timesheetdetailId) {
        this.timesheetdetailId = timesheetdetailId;
    }

    /**
     * @return the flag
     */
    public int[] getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(int[] flag) {
        this.flag = flag;
    }

    /**
     * @return the type
     */
    public int[] getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int[] type) {
        this.type = type;
    }

    /**
     * @return the fromDate2
     */
    public String[] getFromDate2() {
        return fromDate2;
    }

    /**
     * @param fromDate2 the fromDate2 to set
     */
    public void setFromDate2(String[] fromDate2) {
        this.fromDate2 = fromDate2;
    }

    /**
     * @return the toDate2
     */
    public String[] getToDate2() {
        return toDate2;
    }

    /**
     * @param toDate2 the toDate2 to set
     */
    public void setToDate2(String[] toDate2) {
        this.toDate2 = toDate2;
    }

    /**
     * @return the days
     */
    public int[] getDays() {
        return days;
    }

    /**
     * @param days the days to set
     */
    public void setDays(int[] days) {
        this.days = days;
    }

    /**
     * @return the totalcrew
     */
    public int[] getTotalcrew() {
        return totalcrew;
    }

    /**
     * @param totalcrew the totalcrew to set
     */
    public void setTotalcrew(int[] totalcrew) {
        this.totalcrew = totalcrew;
    }

    /**
     * @return the appPositionId
     */
    public int[] getAppPositionId() {
        return appPositionId;
    }

    /**
     * @param appPositionId the appPositionId to set
     */
    public void setAppPositionId(int[] appPositionId) {
        this.appPositionId = appPositionId;
    }

    /**
     * @return the doSummary
     */
    public String getDoSummary() {
        return doSummary;
    }

    /**
     * @param doSummary the doSummary to set
     */
    public void setDoSummary(String doSummary) {
        this.doSummary = doSummary;
    }

    /**
     * @return the crewId
     */
    public int getCrewId() {
        return crewId;
    }

    /**
     * @param crewId the crewId to set
     */
    public void setCrewId(int crewId) {
        this.crewId = crewId;
    }

    /**
     * @return the regenerate
     */
    public String getRegenerate() {
        return regenerate;
    }

    /**
     * @param regenerate the regenerate to set
     */
    public void setRegenerate(String regenerate) {
        this.regenerate = regenerate;
    }

    /**
     * @return the clientassetId
     */
    public int getClientassetId() {
        return clientassetId;
    }

    /**
     * @param clientassetId the clientassetId to set
     */
    public void setClientassetId(int clientassetId) {
        this.clientassetId = clientassetId;
    }

    /**
     * @return the timesheetIdEdit
     */
    public int getTimesheetIdEdit() {
        return timesheetIdEdit;
    }

    /**
     * @param timesheetIdEdit the timesheetIdEdit to set
     */
    public void setTimesheetIdEdit(int timesheetIdEdit) {
        this.timesheetIdEdit = timesheetIdEdit;
    }

    /**
     * @return the typeId
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    /**
     * @return the repeatId
     */
    public int getRepeatId() {
        return repeatId;
    }

    /**
     * @param repeatId the repeatId to set
     */
    public void setRepeatId(int repeatId) {
        this.repeatId = repeatId;
    }

    /**
     * @return the fromDateIndex
     */
    public String getFromDateIndex() {
        return fromDateIndex;
    }

    /**
     * @param fromDateIndex the fromDateIndex to set
     */
    public void setFromDateIndex(String fromDateIndex) {
        this.fromDateIndex = fromDateIndex;
    }

    /**
     * @return the toDateIndex
     */
    public String getToDateIndex() {
        return toDateIndex;
    }

    /**
     * @param toDateIndex the toDateIndex to set
     */
    public void setToDateIndex(String toDateIndex) {
        this.toDateIndex = toDateIndex;
    }
}