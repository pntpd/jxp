package com.web.jxp.analytics;
import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class AnalyticsForm extends ActionForm 
{
    private int ctp;
    private String doCrew;
    private String doTraining;
    private Collection clients;
    private Collection assets;
    private int clientIdIndex;
    private int assetIdIndex;
    private int clientId;
    private int assetId;
    private String doAttendance;

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
     * @return the doCrew
     */
    public String getDoCrew() {
        return doCrew;
    }

    /**
     * @param doCrew the doCrew to set
     */
    public void setDoCrew(String doCrew) {
        this.doCrew = doCrew;
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
     * @return the doTraining
     */
    public String getDoTraining() {
        return doTraining;
    }

    /**
     * @param doTraining the doTraining to set
     */
    public void setDoTraining(String doTraining) {
        this.doTraining = doTraining;
    }

    /**
     * @return the doAttendance
     */
    public String getDoAttendance() {
        return doAttendance;
    }

    /**
     * @param doAttendance the doAttendance to set
     */
    public void setDoAttendance(String doAttendance) {
        this.doAttendance = doAttendance;
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
}
