package com.web.jxp.crewdb;
import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class CrewdbForm extends ActionForm
{
    private int crewdbId;
    private String search;
    private String doCancel;
    private String doModify;
    private String doView;
    private int statusIndex;
    private int positionIndex;
    private Collection positions;
    private int type;
    private Collection clients;
    private Collection countries;
    private Collection assets;
    private int clientIdIndex;
    private int countryIdIndex;
    private int assetIdIndex;
    private int ctp;

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    public Collection getClients() {
        return clients;
    }

    public void setClients(Collection clients) {
        this.clients = clients;
    }

    public Collection getCountries() {
        return countries;
    }

    public void setCountries(Collection countries) {
        this.countries = countries;
    }

    public Collection getAssets() {
        return assets;
    }

    public void setAssets(Collection assets) {
        this.assets = assets;
    }

    public int getClientIdIndex() {
        return clientIdIndex;
    }

    public void setClientIdIndex(int clientIdIndex) {
        this.clientIdIndex = clientIdIndex;
    }

    public int getCountryIdIndex() {
        return countryIdIndex;
    }

    public void setCountryIdIndex(int countryIdIndex) {
        this.countryIdIndex = countryIdIndex;
    }

    public int getAssetIdIndex() {
        return assetIdIndex;
    }

    public void setAssetIdIndex(int assetIdIndex) {
        this.assetIdIndex = assetIdIndex;
    }

    public int getCrewdbId() {
        return crewdbId;
    }

    /**
     * @param crewdbId the crewdbId to set
     */
    public void setCrewdbId(int crewdbId) {
        this.crewdbId = crewdbId;
    }

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
     * @return the doModify
     */
    public String getDoModify() {
        return doModify;
    }

    /**
     * @param doModify the doModify to set
     */
    public void setDoModify(String doModify) {
        this.doModify = doModify;
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
     * @return the positionIndex
     */
    public int getPositionIndex() {
        return positionIndex;
    }

    /**
     * @param positionIndex the positionIndex to set
     */
    public void setPositionIndex(int positionIndex) {
        this.positionIndex = positionIndex;
    }

    /**
     * @return the positions
     */
    public Collection getPositions() {
        return positions;
    }

    /**
     * @param positions the positions to set
     */
    public void setPositions(Collection positions) {
        this.positions = positions;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }
    
}