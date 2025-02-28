package com.web.jxp.documentexpiry;

public class DocumentexpiryInfo
{
    private int candidateId;
    private int govdocId;
    private int courseId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    private String documentName;
    private String expiryDate;
    private int remind;
    private String clientName;
    private String assetName;
    
    private int notificationId;
    private String remarks;
    private int moduleId;
    private int mainId;
    private String date;
    private String stVal;
    private String moduleName;
    private String filename;
    private int clientId;
    private int clientassetId;
    
    //for ddl
    public DocumentexpiryInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    
    //for Document Listing
    public DocumentexpiryInfo(int candidateId, int govdocId, String name, String document, 
        String expirydate, int remind, String clientName, String assetName)
    {
        this.candidateId = candidateId;
        this.govdocId = govdocId;
        this.name = name;
        this.documentName = document;
        this.expiryDate = expirydate;
        this.remind = remind;
        this.clientName = clientName;
        this.assetName = assetName;
    }    
    
    //For alert listing
    public DocumentexpiryInfo(int notificationId, String name, String clientName, String assetName, 
            String stVal, String date, String moduleName, String remarks, String filename, 
            int mainId, int moduleId, int clientId, int assetId)
    {
        this.notificationId = notificationId;
        this.name = name;
        this.clientName = clientName;
        this.assetName = assetName;
        this.stVal = stVal;
        this.date = date;
        this.moduleName = moduleName;
        this.remarks = remarks;
        this.filename = filename;
        this.mainId = mainId;
        this.moduleId = moduleId;
        this.clientId = clientId;
        this.clientassetId = assetId;
    }
    /**
     * @return the candidateId
     */
    public int getCandidateId() {
        return candidateId;
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

    /**
     * @return the documentName
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * @return the expiryDate
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * @return the govdocId
     */
    public int getGovdocId() {
        return govdocId;
    }

    /**
     * @return the remind
     */
    public int getRemind() {
        return remind;
    }

    /**
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @return the assetName
     */
    public String getAssetName() {
        return assetName;
    }

    /**
     * @return the courseId
     */
    public int getCourseId() {
        return courseId;
    }
    /**
     * @return the moduleId
     */
    public int getModuleId() {
        return moduleId;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @return the stVal
     */
    public String getStVal() {
        return stVal;
    }

    /**
     * @return the moduleName
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @return the notificationId
     */
    public int getNotificationId() {
        return notificationId;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }
    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @return the mainId
     */
    public int getMainId() {
        return mainId;
    }

    /**
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @return the clientassetId
     */
    public int getClientassetId() {
        return clientassetId;
    }
}
