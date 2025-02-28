package com.web.jxp.invoicetemplate; 
public class InvoicetemplateInfo
{
    private int invoicetemplateId;
    private String name;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;
    private int clientId;
    private int clientIdIndex;
    private String clientname;
    private String description;
    private String deschidden;

    //for ddl
    public InvoicetemplateInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    //for index
    public InvoicetemplateInfo(int invoicetemplateId, String name, int status, String clientname) {
        this.invoicetemplateId = invoicetemplateId;
        this.name = name;
        this.status = status;
        this.clientname = clientname;
    }

    //Edit
    public InvoicetemplateInfo(int invoicetemplateId, String name, int clientId, String clientname, String description, int status, int userId) 
    {
        this.invoicetemplateId = invoicetemplateId;
        this.name = name;
        this.clientId = clientId;
        this.clientname = clientname;
        this.description = description;
        this.status = status;
        this.userId = userId;
    }

    public String getDeschidden() {
        return deschidden;
    }

    public String getDescription() {
        return description;
    }

    public String getClientname() {
        return clientname;
    }

    public int getClientId() {
        return clientId;
    }

    public int getClientIdIndex() {
        return clientIdIndex;
    }

    /**
     * @return the invoicetemplateId
     */
    public int getInvoicetemplateId() {
        return invoicetemplateId;
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
}
