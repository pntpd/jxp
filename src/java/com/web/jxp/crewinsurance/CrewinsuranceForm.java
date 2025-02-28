package com.web.jxp.crewinsurance;
import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class CrewinsuranceForm extends ActionForm
{
    private int crewinsuranceId;
    private int crewrotationId;
    private int status;
    private String search;
    private String doSearch;
    private String doCancel;
    private String doModify;
    private String name;
    private String doAdd;
    private String doSave;
    private Collection statuses;
    private Collection clients;
    private Collection assets;
    private Collection positions;
    private int positionIdIndex;
    private int clientIdIndex;
    private int assetIdIndex;
    private String doView;
    private String fromDate;
    private String toDate;
    private FormFile certificatefile;
    private String certificatefilehidden;
    private int statusIndex;
    private int ctp;
    private int dependent;
    private int nomineeId;
    
    private String currentDate;
    private String[] cball;
    private String[] cb1;
    
    /**
     * @return the crewinsuranceId
     */
    public int getCrewinsuranceId() {
        return crewinsuranceId;
    }

    /**
     * @param crewinsuranceId the crewinsuranceId to set
     */
    public void setCrewinsuranceId(int crewinsuranceId) {
        this.crewinsuranceId = crewinsuranceId;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the doAdd
     */
    public String getDoAdd() {
        return doAdd;
    }

    /**
     * @param doAdd the doAdd to set
     */
    public void setDoAdd(String doAdd) {
        this.doAdd = doAdd;
    }

    /**
     * @return the doSave
     */
    public String getDoSave() {
        return doSave;
    }

    /**
     * @param doSave the doSave to set
     */
    public void setDoSave(String doSave) {
        this.doSave = doSave;
    }

    /**
     * @return the statuses
     */
    public Collection getStatuses() {
        return statuses;
    }

    /**
     * @param statuses the statuses to set
     */
    public void setStatuses(Collection statuses) {
        this.statuses = statuses;
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
     * @return the positiondIndex
     */
    

    /**
     * @return the fromDate
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the certificatefile
     */
    public FormFile getCertificatefile() {
        return certificatefile;
    }

    /**
     * @param certificatefile the certificatefile to set
     */
    public void setCertificatefile(FormFile certificatefile) {
        this.certificatefile = certificatefile;
    }

    /**
     * @return the certificatefilehidden
     */
    public String getCertificatefilehidden() {
        return certificatefilehidden;
    }

    /**
     * @param certificatefilehidden the certificatefilehidden to set
     */
    public void setCertificatefilehidden(String certificatefilehidden) {
        this.certificatefilehidden = certificatefilehidden;
    }

    /**
     * @return the dependent
     */
    public int getDependent() {
        return dependent;
    }

    /**
     * @param dependent the dependent to set
     */
    public void setDependent(int dependent) {
        this.dependent = dependent;
    }

    /**
     * @return the crewrotationId
     */
    public int getCrewrotationId() {
        return crewrotationId;
    }

    /**
     * @param crewrotationId the crewrotationId to set
     */
    public void setCrewrotationId(int crewrotationId) {
        this.crewrotationId = crewrotationId;
    }

    /**
     * @return the positionIdIndex
     */
    public int getPositionIdIndex() {
        return positionIdIndex;
    }

    /**
     * @param positionIdIndex the positionIdIndex to set
     */
    public void setPositionIdIndex(int positionIdIndex) {
        this.positionIdIndex = positionIdIndex;
    }

    /**
     * @return the doSearch
     */
    public String getDoSearch() {
        return doSearch;
    }

    /**
     * @param doSearch the doSearch to set
     */
    public void setDoSearch(String doSearch) {
        this.doSearch = doSearch;
    }

    /**
     * @return the currentDate
     */
    public String getCurrentDate() {
        return currentDate;
    }

    /**
     * @param currentDate the currentDate to set
     */
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    /**
     * @return the nomineeId
     */
    public int getNomineeId() {
        return nomineeId;
    }

    /**
     * @param nomineeId the nomineeId to set
     */
    public void setNomineeId(int nomineeId) {
        this.nomineeId = nomineeId;
    }    
    /**
     * @return the cb1
     */
    public String[] getCb1() {
        return cb1;
    }

    /**
     * @param cb1 the cb1 to set
     */
    public void setCb1(String[] cb1) {
        this.cb1 = cb1;
    }

    /**
     * @return the cball
     */
    public String[] getCball() {
        return cball;
    }

    /**
     * @param cball the cball to set
     */
    public void setCball(String[] cball) {
        this.cball = cball;
    }
}