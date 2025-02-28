package com.web.jxp.doccheck;

import java.util.Collection;

public class DoccheckInfo {

    private int doccheckId;
    private String doccheckName;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;

    private int clientId;
    private Collection clients;
    private String clientName;

    private int assetId;
    private Collection assets;
    private String assetName;

    //for ddl
    public DoccheckInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;

    }

    public DoccheckInfo(int doccheckId, String name, int status, int userId) {
        this.doccheckId = doccheckId;
        this.doccheckName = name;
        this.status = status;
        this.userId = userId;
    }

    public DoccheckInfo(int doccheckId, String doccheckname, int Status, String clientname) {

        this.doccheckId = doccheckId;
        this.doccheckName = doccheckname;
        this.status = Status;
        this.clientName = clientname;

    }

    public DoccheckInfo(int doccheckId, String name, int status, String clientName, int userId) {
        this.doccheckId = doccheckId;
        this.doccheckName = name;
        this.status = status;
        this.clientName = clientName;
        this.userId = userId;

    }

    public DoccheckInfo(int doccheckId, String name, int status, String clientName, String assetName, int userId) {
        this.doccheckId = doccheckId;
        this.doccheckName = name;
        this.status = status;
        this.clientName = clientName;
        this.assetName = assetName;
        this.userId = userId;

    }

    public DoccheckInfo(int doccheckId, String clientName, String assetName, String name, int status, int userId) {
        this.doccheckId = doccheckId;
        this.clientName = clientName;
        this.assetName = assetName;
        this.doccheckName = name;
        this.status = status;
        this.userId = userId;

    }

    public DoccheckInfo(int doccheckId, String clientName, String assetName, String issuesAuthority, int status) {
        this.doccheckId = doccheckId;
        this.clientName = clientName;
        this.assetName = assetName;
        this.doccheckName = issuesAuthority;
        this.status = status;
    }

    public DoccheckInfo(int doccheckId, String name, int clientId, int status, int userId) {
        this.doccheckId = doccheckId;
        this.doccheckName = name;
        this.clientId = clientId;
        this.status = status;
        this.userId = userId;

    }

    public DoccheckInfo(int doccheckId, String name, int clientId, int assetId, int status, int userId) {
        this.doccheckId = doccheckId;
        this.doccheckName = name;
        this.clientId = clientId;
        this.status = status;
        this.userId = userId;
        this.assetId = assetId;

    }

    public int getAssetId() {
        return assetId;
    }

    public Collection getAssets() {
        return assets;
    }

    public String getAssetName() {
        return assetName;
    }

    public int getClientId() {
        return clientId;
    }

    public Collection getClients() {
        return clients;
    }

    public String getClientName() {
        return clientName;
    }

    /**
     * @return the doccheckId
     */
    public int getDoccheckId() {
        return doccheckId;
    }

    /**
     * @return the name
     */
    public String getDoccheckName() {
        return doccheckName;
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
