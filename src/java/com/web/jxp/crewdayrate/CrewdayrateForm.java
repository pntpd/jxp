package com.web.jxp.crewdayrate;
import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class CrewdayrateForm extends ActionForm
{
    private String search;
    private String search2;
    private String doCancel;
    private String doSave;
    private String doView;
    private String doView2;
    private String doChange;
    private Collection clients;
    private Collection assets;
    private Collection positions;
    private int clientIdIndex;    
    private int clientId;    
    private int assetIdIndex;
    private int assetId;
    private int ctp;
    private int status;
    private int crewrotationId;
    private int type;
    private int typeId;
    private double rate1;
    private double rate2;
    private double rate3;
    private double prate;
    private int positionId;
    private int positionId2;
    private int candidateId;
    private int positionIdCandidate;
    private int clientassetId;
    private String doModifyAsset;
    private String fromDate;
    private String toDate;
    private String doSavePosition;
    private int positionIdRate;
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
     * @return the rate1
     */
    public double getRate1() {
        return rate1;
    }

    /**
     * @param rate1 the rate1 to set
     */
    public void setRate1(double rate1) {
        this.rate1 = rate1;
    }

    /**
     * @return the rate2
     */
    public double getRate2() {
        return rate2;
    }

    /**
     * @param rate2 the rate2 to set
     */
    public void setRate2(double rate2) {
        this.rate2 = rate2;
    }

    /**
     * @return the rate3
     */
    public double getRate3() {
        return rate3;
    }

    /**
     * @param rate3 the rate3 to set
     */
    public void setRate3(double rate3) {
        this.rate3 = rate3;
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
     * @return the doChange
     */
    public String getDoChange() {
        return doChange;
    }

    /**
     * @param doChange the doChange to set
     */
    public void setDoChange(String doChange) {
        this.doChange = doChange;
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
     * @return the clientassetId
     */
    public int getClientassetId() {
        return clientassetId;
    }

    /**
     * @param clientassetId the clientassetId to set
     */
    public void setClientassetId(int clientassetId) {
        this.clientassetId = clientassetId;
    }

    /**
     * @return the doModifyAsset
     */
    public String getDoModifyAsset() {
        return doModifyAsset;
    }

    /**
     * @param doModifyAsset the doModifyAsset to set
     */
    public void setDoModifyAsset(String doModifyAsset) {
        this.doModifyAsset = doModifyAsset;
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    /**
     * @return the prate
     */
    public double getPrate() {
        return prate;
    }

    /**
     * @param prate the prate to set
     */
    public void setPrate(double prate) {
        this.prate = prate;
    }

    /**
     * @return the candidateId
     */
    public int getCandidateId() {
        return candidateId;
    }

    /**
     * @param candidateId the candidateId to set
     */
    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }
    /**
     * @return the positionIdCandidate
     */
    public int getPositionIdCandidate() {
        return positionIdCandidate;
    }

    /**
     * @param positionIdCandidate the positionIdCandidate to set
     */
    public void setPositionIdCandidate(int positionIdCandidate) {
        this.positionIdCandidate = positionIdCandidate;
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
     * @return the typeId
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    /**
     * @return the doSavePosition
     */
    public String getDoSavePosition() {
        return doSavePosition;
    }

    /**
     * @param doSavePosition the doSavePosition to set
     */
    public void setDoSavePosition(String doSavePosition) {
        this.doSavePosition = doSavePosition;
    }

    /**
     * @return the positionIdRate
     */
    public int getPositionIdRate() {
        return positionIdRate;
    }

    /**
     * @param positionIdRate the positionIdRate to set
     */
    public void setPositionIdRate(int positionIdRate) {
        this.positionIdRate = positionIdRate;
    }

    /**
     * @return the positionId2
     */
    public int getPositionId2() {
        return positionId2;
    }

    /**
     * @param positionId2 the positionId2 to set
     */
    public void setPositionId2(int positionId2) {
        this.positionId2 = positionId2;
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
     * @return the doView2
     */
    public String getDoView2() {
        return doView2;
    }

    /**
     * @param doView2 the doView2 to set
     */
    public void setDoView2(String doView2) {
        this.doView2 = doView2;
    }

    /**
     * @return the search2
     */
    public String getSearch2() {
        return search2;
    }

    /**
     * @param search2 the search2 to set
     */
    public void setSearch2(String search2) {
        this.search2 = search2;
    }

}