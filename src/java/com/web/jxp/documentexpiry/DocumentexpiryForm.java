package com.web.jxp.documentexpiry;
import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class DocumentexpiryForm extends ActionForm
{
    private int govdocId;
    private String doRemind;
    private Collection documents;
    private Collection clients;
    private Collection assets;
    private Collection coursenames;
    private int coursenameId;
    private int documentId;
    private int dropdownId;
    private int healthId;
    private int ctp;
    private int type;
    private int exp;
    private int clientIdIndex;
    private int assetIdIndex;
    private String[] govdoccb;
    private String doRemindAll;
    private String search;
    
    private String search2;
    private String viewAlerts;
    private String viewExpiry;
    private String deleteAlert;
    
    private String fromDate;
    private String toDate;
    private String fromTime;
    private String toTime;
    private int clientIdAlert;
    private int assetIdAlert;
    private int moduleId;
    private int notificationId;
    private int candidateId;
    private String doView;
    private int jobpostId;
    private int clientId;
    private int clientassetId;

    /**
     * @return the govdocId
     */
    public int getGovdocId() {
        return govdocId;
    }

    /**
     * @param govdocId the govdocId to set
     */
    public void setGovdocId(int govdocId) {
        this.govdocId = govdocId;
    }

    /**
     * @return the doRemind
     */
    public String getDoRemind() {
        return doRemind;
    }

    /**
     * @param doRemind the doRemind to set
     */
    public void setDoRemind(String doRemind) {
        this.doRemind = doRemind;
    }

    /**
     * @return the documents
     */
    public Collection getDocuments() {
        return documents;
    }

    /**
     * @param documents the documents to set
     */
    public void setDocuments(Collection documents) {
        this.documents = documents;
    }

    /**
     * @return the documentId
     */
    public int getDocumentId() {
        return documentId;
    }

    /**
     * @param documentId the documentId to set
     */
    public void setDocumentId(int documentId) {
        this.documentId = documentId;
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
     * @return the exp
     */
    public int getExp() {
        return exp;
    }

    /**
     * @param exp the exp to set
     */
    public void setExp(int exp) {
        this.exp = exp;
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
     * @return the govdoccb
     */
    public String[] getGovdoccb() {
        return govdoccb;
    }

    /**
     * @param govdoccb the govdoccb to set
     */
    public void setGovdoccb(String[] govdoccb) {
        this.govdoccb = govdoccb;
    }

    /**
     * @return the doRemindAll
     */
    public String getDoRemindAll() {
        return doRemindAll;
    }

    /**
     * @param doRemindAll the doRemindAll to set
     */
    public void setDoRemindAll(String doRemindAll) {
        this.doRemindAll = doRemindAll;
    }

    /**
     * @return the coursenames
     */
    public Collection getCoursenames() {
        return coursenames;
    }

    /**
     * @param coursenames the coursenames to set
     */
    public void setCoursenames(Collection coursenames) {
        this.coursenames = coursenames;
    }

    /**
     * @return the coursenameId
     */
    public int getCoursenameId() {
        return coursenameId;
    }

    /**
     * @param coursenameId the coursenameId to set
     */
    public void setCoursenameId(int coursenameId) {
        this.coursenameId = coursenameId;
    }

    /**
     * @return the dropdownId
     */
    public int getDropdownId() {
        return dropdownId;
    }

    /**
     * @param dropdownId the dropdownId to set
     */
    public void setDropdownId(int dropdownId) {
        this.dropdownId = dropdownId;
    }

    /**
     * @return the healthId
     */
    public int getHealthId() {
        return healthId;
    }

    /**
     * @param healthId the healthId to set
     */
    public void setHealthId(int healthId) {
        this.healthId = healthId;
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
     * @return the viewAlerts
     */
    public String getViewAlerts() {
        return viewAlerts;
    }

    /**
     * @param viewAlerts the viewAlerts to set
     */
    public void setViewAlerts(String viewAlerts) {
        this.viewAlerts = viewAlerts;
    }

    /**
     * @return the viewExpiry
     */
    public String getViewExpiry() {
        return viewExpiry;
    }

    /**
     * @param viewExpiry the viewExpiry to set
     */
    public void setViewExpiry(String viewExpiry) {
        this.viewExpiry = viewExpiry;
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
     * @return the fromTime
     */
    public String getFromTime() {
        return fromTime;
    }

    /**
     * @param fromTime the fromTime to set
     */
    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    /**
     * @return the toTime
     */
    public String getToTime() {
        return toTime;
    }

    /**
     * @param toTime the toTime to set
     */
    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    /**
     * @return the clientIdAlert
     */
    public int getClientIdAlert() {
        return clientIdAlert;
    }

    /**
     * @param clientIdAlert the clientIdAlert to set
     */
    public void setClientIdAlert(int clientIdAlert) {
        this.clientIdAlert = clientIdAlert;
    }

    /**
     * @return the assetIdAlert
     */
    public int getAssetIdAlert() {
        return assetIdAlert;
    }

    /**
     * @param assetIdAlert the assetIdAlert to set
     */
    public void setAssetIdAlert(int assetIdAlert) {
        this.assetIdAlert = assetIdAlert;
    }

    /**
     * @return the moduleId
     */
    public int getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId the moduleId to set
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
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

    /**
     * @return the deleteAlert
     */
    public String getDeleteAlert() {
        return deleteAlert;
    }

    /**
     * @param deleteAlert the deleteAlert to set
     */
    public void setDeleteAlert(String deleteAlert) {
        this.deleteAlert = deleteAlert;
    }

    /**
     * @return the notificationId
     */
    public int getNotificationId() {
        return notificationId;
    }

    /**
     * @param notificationId the notificationId to set
     */
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
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
     * @return the jobpostId
     */
    public int getJobpostId() {
        return jobpostId;
    }

    /**
     * @param jobpostId the jobpostId to set
     */
    public void setJobpostId(int jobpostId) {
        this.jobpostId = jobpostId;
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

}