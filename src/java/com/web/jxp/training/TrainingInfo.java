package com.web.jxp.training;

import java.util.Collection;

public class TrainingInfo
{
    private int trainingId;
    private String name;
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

    public int getClientId() {
        return clientId;
    }

    public Collection getClients() {
        return clients;
    }

    public String getClientName() {
        return clientName;
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
    
    
    //for ddl
    public TrainingInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public TrainingInfo(int trainingId, String name, int status, int userId)
    {
        this.trainingId = trainingId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }
    public TrainingInfo(int trainingId, String name, int status, int userId, String client, String asset)
    {
        this.trainingId = trainingId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientName = client;
        this.assetName = asset;
    }
    public TrainingInfo(int trainingId, String name, int status, String clientname,String assetname)
    {
        this.trainingId = trainingId;
        this.name = name;
        this.status = status;
        this.clientName = clientname;
        this.assetName = assetname;
    }
    public TrainingInfo(int trainingId, String name, int status, int userId, int clientId, int assetId)
    {
        this.trainingId = trainingId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientId = clientId;
        this.assetId = assetId;
    }

    /**
     * @return the trainingId
     */
    public int getTrainingId() {
        return trainingId;
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
