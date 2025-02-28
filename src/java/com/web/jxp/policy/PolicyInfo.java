package com.web.jxp.policy;

public class PolicyInfo {

    private int policyId;
    private String policyName;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;

    private int clientId;
    private String clientName;

    private int assetId;
    private String assetName;
    private String fileName;

    //for ddl
    public PolicyInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;

    }

    public PolicyInfo(int policyId, String name, int status, int userId) {
        this.policyId = policyId;
        this.policyName = name;
        this.status = status;
        this.userId = userId;
    }

    public PolicyInfo(int policyId, String policyname, int Status, String clientname) {

        this.policyId = policyId;
        this.policyName = policyname;
        this.status = Status;
        this.clientName = clientname;

    }

    public PolicyInfo(int policyId, String name, int status, String clientName, int userId) {
        this.policyId = policyId;
        this.policyName = name;
        this.status = status;
        this.clientName = clientName;
        this.userId = userId;

    }

    public PolicyInfo(int policyId, String name, int status, String clientName, String assetName, String fileName) {
        this.policyId = policyId;
        this.policyName = name;
        this.status = status;
        this.clientName = clientName;
        this.assetName = assetName;
        this.fileName = fileName;
    }

    public PolicyInfo(int policyId, String clientName, String assetName, String name, int status, int userId) {
        this.policyId = policyId;
        this.clientName = clientName;
        this.assetName = assetName;
        this.policyName = name;
        this.status = status;
        this.userId = userId;

    }

    public PolicyInfo(int policyId, String clientName, String assetName, String issuesAuthority, int status) {
        this.policyId = policyId;
        this.clientName = clientName;
        this.assetName = assetName;
        this.policyName = issuesAuthority;
        this.status = status;
    }

    public PolicyInfo(int policyId, String name, int clientId, int status, int userId) {
        this.policyId = policyId;
        this.policyName = name;
        this.clientId = clientId;
        this.status = status;
        this.userId = userId;

    }

    //Add edit
    public PolicyInfo(int policyId, String name, int clientId, int assetId, int status, int userId, String fileName) {
        this.policyId = policyId;
        this.policyName = name;
        this.clientId = clientId;
        this.status = status;
        this.userId = userId;
        this.assetId = assetId;
        this.fileName = fileName;
    }

    public int getAssetId() {
        return assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public int getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    /**
     * @return the policyId
     */
    public int getPolicyId() {
        return policyId;
    }

    /**
     * @return the name
     */
    public String getPolicyName() {
        return policyName;
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
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }
}
