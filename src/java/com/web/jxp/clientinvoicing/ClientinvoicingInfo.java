package com.web.jxp.clientinvoicing;

public class ClientinvoicingInfo {

    private int clientinvoicingId;
    private String name;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;

    private int clientassetId;
    private int clientId;
    private String clientName;
    private String clientassetName;
    private int type;
    private int invoiceCount;

    private int timesheetId;
    private int invoiceId;
    private int invoicestatus;
    private String generatedate;
    private String sentdate;
    private String fromdate;
    private String todate;
    private String sendappfile;

    private String generatedfile;
    private String mailattachedfile;
    private String attachedfile;
    private String approvaldate;
    private String clientemail;

    private String to;
    private String cc;
    private String bcc;
    private String from;
    private String subject;
    private String mailbody;
    private String username;
    private String date;
    private int maillogId;
    private String paymentdate;
    private int paymentmodeId;
    private String paymentremark;
    private String paymentmode;
    private double amount1;
    private int counrtyId;
    private String foldername;

    //for ddl
    public ClientinvoicingInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public ClientinvoicingInfo(int clientinvoicingId, String name, int status, int userId) {
        this.clientinvoicingId = clientinvoicingId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    ClientinvoicingInfo(int clientassetId, int clientId, String clientName, String clientassetName, int type, int invoiceCount) {
        this.clientassetId = clientassetId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientassetName = clientassetName;
        this.type = type;
        this.invoiceCount = invoiceCount;
    }

    public ClientinvoicingInfo(int clientassetId, String clientassetName, String clientName, int counrtyId) {
        this.clientassetId = clientassetId;
        this.clientName = clientName;
        this.clientassetName = clientassetName;
        this.counrtyId = counrtyId;
    }

    public ClientinvoicingInfo(int timesheetId, int invoiceId, int clientId, int clientassetId, String clientassetName, String clientName,
            String generatedfile, String mailattachedfile, String attachedfile, String paymentdate,
            int paymentmodeId, String paymentremark,  int invoicestatus, double amount1) {
        this.timesheetId = timesheetId;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.invoiceId = invoiceId;
        this.clientName = clientName;
        this.clientassetName = clientassetName;
        this.generatedfile = generatedfile;
        this.mailattachedfile = mailattachedfile;
        this.attachedfile = attachedfile;
        this.paymentdate = paymentdate;
        this.paymentmodeId = paymentmodeId;
        this.paymentremark = paymentremark;
        this.invoicestatus = invoicestatus;
        this.amount1 = amount1;

    }

    public ClientinvoicingInfo(int timesheetId, int invoiceId, int clientId, int clientassetId, String clientassetName, String clientName,
            String generatedfile, String mailattachedfile, String attachedfile, String approvaldate, int invoicestatus, String clientemail, String fromdate, String todate) {
        this.clientemail = clientemail;
        this.timesheetId = timesheetId;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.invoiceId = invoiceId;
        this.clientName = clientName;
        this.clientassetName = clientassetName;
        this.generatedfile = generatedfile;
        this.mailattachedfile = mailattachedfile;
        this.attachedfile = attachedfile;
        this.approvaldate = approvaldate;
        this.invoicestatus = invoicestatus;
        this.todate = todate;
        this.fromdate = fromdate;

    }

    public ClientinvoicingInfo(int timesheetId, int invoiceId, int invoicestatus, String generatedate,
            String sentdate, String fromdate, String todate, String sendappfile) {
        this.timesheetId = timesheetId;
        this.invoiceId = invoiceId;
        this.invoicestatus = invoicestatus;
        this.generatedate = generatedate;
        this.sentdate = sentdate;
        this.fromdate = fromdate;
        this.todate = todate;
        this.sendappfile = sendappfile;
    }

    public ClientinvoicingInfo(int timesheetId, String to, String cc, String bcc, String from, String name, String subject, String mailbody,
            String generatedfile, String mailattachedfile, String username) {
        this.timesheetId = timesheetId;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.from = from;
        this.subject = subject;
        this.mailbody = mailbody;
        this.clientName = name;
        this.generatedfile = generatedfile;
        this.mailattachedfile = mailattachedfile;
        this.username = username;
    }

    public ClientinvoicingInfo(int timesheetId, String to, String cc, String bcc, String from, String name, String subject, String mailbody,
            String generatedfile, String mailattachedfile, String username, String date, int maillogId) {

        this.maillogId = maillogId;
        this.date = date;
        this.timesheetId = timesheetId;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.from = from;
        this.subject = subject;
        this.mailbody = mailbody;
        this.clientName = name;
        this.generatedfile = generatedfile;
        this.mailattachedfile = mailattachedfile;
        this.username = username;
    }

    public ClientinvoicingInfo(String foldername, String date, String name) {
        this.foldername = foldername;
        this.date = date;
        this.username = name;
    }

    /**
     * @return the clientinvoicingId
     */
    public int getClientinvoicingId() {
        return clientinvoicingId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the ddlValue
     */
    public int getDdlValue() {
        return ddlValue;
    }

    /**
     * @return the ddlLabel
     */
    public String getDdlLabel() {
        return ddlLabel;
    }

    public int getClientassetId() {
        return clientassetId;
    }

    public int getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientassetName() {
        return clientassetName;
    }

    public int getType() {
        return type;
    }

    public int getInvoiceCount() {
        return invoiceCount;
    }

    public int getTimesheetId() {
        return timesheetId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public int getInvoicestatus() {
        return invoicestatus;
    }

    public String getGeneratedate() {
        return generatedate;
    }

    public String getSentdate() {
        return sentdate;
    }

    public String getFromdate() {
        return fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public String getGeneratedfile() {
        return generatedfile;
    }

    public void setGeneratedfile(String generatedfile) {
        this.generatedfile = generatedfile;
    }

    public String getMailattachedfile() {
        return mailattachedfile;
    }

    public void setMailattachedfile(String mailattachedfile) {
        this.mailattachedfile = mailattachedfile;
    }

    public String getAttachedfile() {
        return attachedfile;
    }

    public void setAttachedfile(String attachedfile) {
        this.attachedfile = attachedfile;
    }

    public String getApprovaldate() {
        return approvaldate;
    }

    public String getClientemail() {
        return clientemail;
    }

    public String getTo() {
        return to;
    }

    public String getCc() {
        return cc;
    }

    public String getBcc() {
        return bcc;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getMailbody() {
        return mailbody;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public int getMaillogId() {
        return maillogId;
    }

    public String getPaymentdate() {
        return paymentdate;
    }

    public int getPaymentmodeId() {
        return paymentmodeId;
    }

    public String getPaymentremark() {
        return paymentremark;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public String getSendappfile() {
        return sendappfile;
    }

    public double getAmount1() {
        return amount1;
    }

    public int getCounrtyId() {
        return counrtyId;
    }

    public void setCounrtyId(int counrtyId) {
        this.counrtyId = counrtyId;
    }

    public String getFoldername() {
        return foldername;
    }

}
