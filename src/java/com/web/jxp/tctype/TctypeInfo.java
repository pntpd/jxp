package com.web.jxp.tctype;

import java.util.Collection;

public class TctypeInfo
{
    private int tctypeId;
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
    public TctypeInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public TctypeInfo(int tctypeId, String name, int status, int userId)
    {
        this.tctypeId = tctypeId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }
    //view
    public TctypeInfo(int tctypeId, String name, int status, int userId, String clientname , String assetname)
    {
        this.tctypeId = tctypeId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientName = clientname;
        this.assetName = assetname;
    }
    //save
    public TctypeInfo(int tctypeId, String name, int status, int userId,int clientId, int assetId)
    {
        this.tctypeId = tctypeId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientId = clientId;
        this.assetId = assetId;
    }

    /**
     * @return the tctypeId
     */
    public int getTctypeId() {
        return tctypeId;
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
