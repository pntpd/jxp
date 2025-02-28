package com.web.jxp.contract;

public class ContractInfo {

    private int contractId;
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
    private int editor;
    private int type;
    private int refId;
    private int assetId;
    private int attachment;
    private String expColumn;
    private String eduColumn;
    private String docColumn;
    private String trainingColumn;
    private String languageColumn;
    private String vaccineColumn;
    private String nomineeColumn;
    private String assetName;
    private String refName;
    private String filename;
    private String description2;
    private String description3;

    //for ddl
    public ContractInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    //For listing
    public ContractInfo(int contractId, String name, int status, String clientName, String assetName, int type, String refName)
    {
        this.contractId = contractId;
        this.name = name;
        this.status = status;
        this.clientname = clientName;
        this.assetName = assetName;
        this.type = type;
        this.refName = refName;
    }

    public ContractInfo(int contractId, String name, int status, String clientname) {
        this.contractId = contractId;
        this.name = name;
        this.status = status;
        this.clientname = clientname;
    }

    public ContractInfo(int contractId, String name, int status, int userId, String clientname, int a) {
        this.contractId = contractId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientname = clientname;

    }

    public ContractInfo(int contractId, String name, int status, int userId, String clientname) {
        this.contractId = contractId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientname = clientname;
    }

    //For view
    public ContractInfo(int contractId, String name, int status, String clientname, String description, 
            int type, String assetname, String workexp, String edudetail, String docdetail, String certdetail,
            String language, String vaccine, int refId, String refname, String description2, String description3, 
            String filename, String nomineedetail ) 
    {
        this.contractId = contractId;
        this.name = name;
        this.status = status;
        this.clientname = clientname;
        this.description = description;
        this.type = type;
        this.assetName = assetname;
        this.expColumn = workexp;
        this.eduColumn = edudetail;
        this.docColumn = docdetail;
        this.trainingColumn = certdetail;
        this.languageColumn = language;
        this.vaccineColumn = vaccine;
        this.refId = refId;
        this.refName = refname;
        this.description2 = description2;
        this.description3 = description3;
        this.filename = filename;
        this.nomineeColumn = nomineedetail;
    }

    public ContractInfo(int contractId, String name, int status, int userId, int clientId, String description, int b) {
        this.contractId = contractId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientId = clientId;
        this.description = description;
    }

    //Edit
    public ContractInfo(int contractId, String name, int clientid, String description, 
            int status, int type,int refId, String workexp, String edudetail,
            String docdetail, String certificate, String langauge, String vaccine, int assetId,
            String description2, String description3, String filename, String nominee) 
    {
        this.contractId = contractId;
        this.name = name;
        this.clientId = clientid;
        this.description = description;
        this.status = status;
        this.type = type;
        this.refId = refId;
        this.expColumn = workexp;
        this.eduColumn = edudetail;
        this.docColumn = docdetail;
        this.trainingColumn = certificate;
        this.languageColumn = langauge;
        this.vaccineColumn = vaccine;
        this.assetId = assetId;
        this.description2 = description2;
        this.description3 = description3;
        this.filename = filename;
        this.nomineeColumn = nominee;
    }

    //Add page
    public ContractInfo(int contractId, String name, int status, int userId, int clientId, String description) {
        this.contractId = contractId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientId = clientId;
        this.description = description;

    }

    //For add
    public ContractInfo(int contractId, String name, int status, int userId, int clientId,
            String description, int assetId, int type, int refId, String expcolumn, String candeducolumn, 
            String canddoccolumn, String ctrainingcolumn, String clangcolumn, 
            String cvaccinecolumn, String description2, String description3, String fileName, String nomcolumn) 
    {
        this.contractId = contractId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientId = clientId;
        this.description = description;
        this.assetId = assetId;
        this.type = type;
        this.refId = refId;
        this.expColumn = expcolumn;
        this.eduColumn = candeducolumn;
        this.docColumn = canddoccolumn;
        this.trainingColumn = ctrainingcolumn;
        this.languageColumn = clangcolumn;
        this.vaccineColumn = cvaccinecolumn;
        this.description2 = description2;
        this.description3 = description3;
        this.filename = fileName;
        this.nomineeColumn = nomcolumn;
    }

    public int getType() {
        return type;
    }
    
    public int getEditor() {
        return editor;
    }

    public int getAttachment() {
        return attachment;
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
     * @return the contractId
     */
    public int getContractId() {
        return contractId;
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
     * @return the expColumn
     */
    public String getExpColumn() {
        return expColumn;
    }

    /**
     * @return the eduColumn
     */
    public String getEduColumn() {
        return eduColumn;
    }

    /**
     * @return the docColumn
     */
    public String getDocColumn() {
        return docColumn;
    }

    /**
     * @return the trainingColumn
     */
    public String getTrainingColumn() {
        return trainingColumn;
    }

    /**
     * @return the languageColumn
     */
    public String getLanguageColumn() {
        return languageColumn;
    }

    /**
     * @return the vaccineColumn
     */
    public String getVaccineColumn() {
        return vaccineColumn;
    }
    /**
     * @return the refId
     */
    public int getRefId() {
        return refId;
    }

    /**
     * @return the assetId
     */
    public int getAssetId() {
        return assetId;
    }

    /**
     * @return the assetName
     */
    public String getAssetName() {
        return assetName;
    }

    /**
     * @return the refName
     */
    public String getRefName() {
        return refName;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @return the description2
     */
    public String getDescription2() {
        return description2;
    }

    /**
     * @return the description3
     */
    public String getDescription3() {
        return description3;
    }

    /**
     * @return the nomineeColumn
     */
    public String getNomineeColumn() {
        return nomineeColumn;
    }
}
