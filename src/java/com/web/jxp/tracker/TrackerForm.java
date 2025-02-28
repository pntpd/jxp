package com.web.jxp.tracker;
import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class TrackerForm extends ActionForm
{
    private String search;
    private String doCancel;
    private String doView;
    private Collection clients;
    private Collection assets;
    private Collection positions;    
    private int clientIdIndex;    
    private int assetIdIndex;
    private int positionIdIndex;
    private int positionId2Index;
    private int mode;
    private String doSearch;
    private String doSearchAsset;
    private int crewrotationId;
    private String doAssign1;
    private String doAssign2;
    private String doAssignSave1;
    private String searchdetail;
    private int ftype;
    private int positionId;
    private int typefrom;
    private String doSavepersonalassign;
    private String mcrids;
    private int ctp;
    private String fromval;
    private String description;
    private String remarks;
    private int typefrom1;
    private int candidateId;
    private String exptype;
    private int passessmenttypeId;
    private int priorityId;
    private Collection passessmenttypes;
    private Collection priorities;
    private int pcodeIdIndex;
    private String doSaveassign;
    private Collection pcodes;
    private int pcodeId;
    private String pcodeids;
    private int pcodeIdModal;
    private int crewrotationIdModal;
    private String completebydate1;
    private int auserId;
    private int resultId;
    private int resultId2;
    private int trackerId;
    private int status;
    private int statusModal;
    private int score;
    private Collection results;
    private FormFile trackerFile;
    private String trackerFilehidden;
    private FormFile trackerFile2;
    private String trackerFilehidden2;
    private String doUpdateTrack;    
    private int practicalscore;    
    private int averagescore;    
    private String practicalremarks;
    private String username1Hiden;
    private String username2Hiden;
    private int candidateIdModal;    
    private String completebydate2;
    private String outcomeremarks;
    private int outcomeId;    
    private String rejection;
    private int reasonId;    
    private int trainingId;   
    private int month;

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
     * @return the positionId2Index
     */
    public int getPositionId2Index() {
        return positionId2Index;
    }

    /**
     * @param positionId2Index the positionId2Index to set
     */
    public void setPositionId2Index(int positionId2Index) {
        this.positionId2Index = positionId2Index;
    }

    /**
     * @return the mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(int mode) {
        this.mode = mode;
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
     * @return the doSearchAsset
     */
    public String getDoSearchAsset() {
        return doSearchAsset;
    }

    /**
     * @param doSearchAsset the doSearchAsset to set
     */
    public void setDoSearchAsset(String doSearchAsset) {
        this.doSearchAsset = doSearchAsset;
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
     * @return the doAssign1
     */
    public String getDoAssign1() {
        return doAssign1;
    }

    /**
     * @param doAssign1 the doAssign1 to set
     */
    public void setDoAssign1(String doAssign1) {
        this.doAssign1 = doAssign1;
    }

    /**
     * @return the doAssign2
     */
    public String getDoAssign2() {
        return doAssign2;
    }

    /**
     * @param doAssign2 the doAssign2 to set
     */
    public void setDoAssign2(String doAssign2) {
        this.doAssign2 = doAssign2;
    }

    /**
     * @return the doAssignSave1
     */
    public String getDoAssignSave1() {
        return doAssignSave1;
    }

    /**
     * @param doAssignSave1 the doAssignSave1 to set
     */
    public void setDoAssignSave1(String doAssignSave1) {
        this.doAssignSave1 = doAssignSave1;
    }

    /**
     * @return the searchdetail
     */
    public String getSearchdetail() {
        return searchdetail;
    }

    /**
     * @param searchdetail the searchdetail to set
     */
    public void setSearchdetail(String searchdetail) {
        this.searchdetail = searchdetail;
    }

    /**
     * @return the ftype
     */
    public int getFtype() {
        return ftype;
    }

    /**
     * @param ftype the ftype to set
     */
    public void setFtype(int ftype) {
        this.ftype = ftype;
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
     * @return the typefrom
     */
    public int getTypefrom() {
        return typefrom;
    }

    /**
     * @param typefrom the typefrom to set
     */
    public void setTypefrom(int typefrom) {
        this.typefrom = typefrom;
    }

    /**
     * @return the doSavepersonalassign
     */
    public String getDoSavepersonalassign() {
        return doSavepersonalassign;
    }

    /**
     * @param doSavepersonalassign the doSavepersonalassign to set
     */
    public void setDoSavepersonalassign(String doSavepersonalassign) {
        this.doSavepersonalassign = doSavepersonalassign;
    }

    /**
     * @return the mcrids
     */
    public String getMcrids() {
        return mcrids;
    }

    /**
     * @param mcrids the mcrids to set
     */
    public void setMcrids(String mcrids) {
        this.mcrids = mcrids;
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
     * @return the fromval
     */
    public String getFromval() {
        return fromval;
    }

    /**
     * @param fromval the fromval to set
     */
    public void setFromval(String fromval) {
        this.fromval = fromval;
    }


    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the typefrom1
     */
    public int getTypefrom1() {
        return typefrom1;
    }

    /**
     * @param typefrom1 the typefrom1 to set
     */
    public void setTypefrom1(int typefrom1) {
        this.typefrom1 = typefrom1;
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
     * @return the exptype
     */
    public String getExptype() {
        return exptype;
    }

    /**
     * @param exptype the exptype to set
     */
    public void setExptype(String exptype) {
        this.exptype = exptype;
    }

    /**
     * @return the passessmenttypeId
     */
    public int getPassessmenttypeId() {
        return passessmenttypeId;
    }

    /**
     * @param passessmenttypeId the passessmenttypeId to set
     */
    public void setPassessmenttypeId(int passessmenttypeId) {
        this.passessmenttypeId = passessmenttypeId;
    }

    /**
     * @return the priorityId
     */
    public int getPriorityId() {
        return priorityId;
    }

    /**
     * @param priorityId the priorityId to set
     */
    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    /**
     * @return the passessmenttypes
     */
    public Collection getPassessmenttypes() {
        return passessmenttypes;
    }

    /**
     * @param passessmenttypes the passessmenttypes to set
     */
    public void setPassessmenttypes(Collection passessmenttypes) {
        this.passessmenttypes = passessmenttypes;
    }

    /**
     * @return the priorities
     */
    public Collection getPriorities() {
        return priorities;
    }

    /**
     * @param priorities the priorities to set
     */
    public void setPriorities(Collection priorities) {
        this.priorities = priorities;
    }

    /**
     * @return the pcodeIdIndex
     */
    public int getPcodeIdIndex() {
        return pcodeIdIndex;
    }

    /**
     * @param pcodeIdIndex the pcodeIdIndex to set
     */
    public void setPcodeIdIndex(int pcodeIdIndex) {
        this.pcodeIdIndex = pcodeIdIndex;
    }

    /**
     * @return the doSaveassign
     */
    public String getDoSaveassign() {
        return doSaveassign;
    }

    /**
     * @param doSaveassign the doSaveassign to set
     */
    public void setDoSaveassign(String doSaveassign) {
        this.doSaveassign = doSaveassign;
    }

    /**
     * @return the pcodes
     */
    public Collection getPcodes() {
        return pcodes;
    }

    /**
     * @param pcodes the pcodes to set
     */
    public void setPcodes(Collection pcodes) {
        this.pcodes = pcodes;
    }

    /**
     * @return the pcodeId
     */
    public int getPcodeId() {
        return pcodeId;
    }

    /**
     * @param pcodeId the pcodeId to set
     */
    public void setPcodeId(int pcodeId) {
        this.pcodeId = pcodeId;
    }

    /**
     * @return the pcodeids
     */
    public String getPcodeids() {
        return pcodeids;
    }

    /**
     * @param pcodeids the pcodeids to set
     */
    public void setPcodeids(String pcodeids) {
        this.pcodeids = pcodeids;
    }

    /**
     * @return the pcodeIdModal
     */
    public int getPcodeIdModal() {
        return pcodeIdModal;
    }

    /**
     * @param pcodeIdModal the pcodeIdModal to set
     */
    public void setPcodeIdModal(int pcodeIdModal) {
        this.pcodeIdModal = pcodeIdModal;
    }

    /**
     * @return the crewrotationIdModal
     */
    public int getCrewrotationIdModal() {
        return crewrotationIdModal;
    }

    /**
     * @param crewrotationIdModal the crewrotationIdModal to set
     */
    public void setCrewrotationIdModal(int crewrotationIdModal) {
        this.crewrotationIdModal = crewrotationIdModal;
    }

    /**
     * @return the completebydate1
     */
    public String getCompletebydate1() {
        return completebydate1;
    }

    /**
     * @param completebydate1 the completebydate1 to set
     */
    public void setCompletebydate1(String completebydate1) {
        this.completebydate1 = completebydate1;
    }

    /**
     * @return the auserId
     */
    public int getAuserId() {
        return auserId;
    }

    /**
     * @param auserId the auserId to set
     */
    public void setAuserId(int auserId) {
        this.auserId = auserId;
    }

    /**
     * @return the resultId
     */
    public int getResultId() {
        return resultId;
    }

    /**
     * @param resultId the resultId to set
     */
    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    /**
     * @return the results
     */
    public Collection getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(Collection results) {
        this.results = results;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the trackerFile
     */
    public FormFile getTrackerFile() {
        return trackerFile;
    }

    /**
     * @param trackerFile the trackerFile to set
     */
    public void setTrackerFile(FormFile trackerFile) {
        this.trackerFile = trackerFile;
    }

    /**
     * @return the trackerFilehidden
     */
    public String getTrackerFilehidden() {
        return trackerFilehidden;
    }

    /**
     * @param trackerFilehidden the trackerFilehidden to set
     */
    public void setTrackerFilehidden(String trackerFilehidden) {
        this.trackerFilehidden = trackerFilehidden;
    }

    /**
     * @return the doUpdateTrack
     */
    public String getDoUpdateTrack() {
        return doUpdateTrack;
    }

    /**
     * @param doUpdateTrack the doUpdateTrack to set
     */
    public void setDoUpdateTrack(String doUpdateTrack) {
        this.doUpdateTrack = doUpdateTrack;
    }

    /**
     * @return the trackerId
     */
    public int getTrackerId() {
        return trackerId;
    }

    /**
     * @param trackerId the trackerId to set
     */
    public void setTrackerId(int trackerId) {
        this.trackerId = trackerId;
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
     * @return the trackerFile2
     */
    public FormFile getTrackerFile2() {
        return trackerFile2;
    }

    /**
     * @param trackerFile2 the trackerFile2 to set
     */
    public void setTrackerFile2(FormFile trackerFile2) {
        this.trackerFile2 = trackerFile2;
    }

    /**
     * @return the trackerFilehidden2
     */
    public String getTrackerFilehidden2() {
        return trackerFilehidden2;
    }

    /**
     * @param trackerFilehidden2 the trackerFilehidden2 to set
     */
    public void setTrackerFilehidden2(String trackerFilehidden2) {
        this.trackerFilehidden2 = trackerFilehidden2;
    }

    /**
     * @return the practicalscore
     */
    public int getPracticalscore() {
        return practicalscore;
    }

    /**
     * @param practicalscore the practicalscore to set
     */
    public void setPracticalscore(int practicalscore) {
        this.practicalscore = practicalscore;
    }

    

    /**
     * @return the practicalremarks
     */
    public String getPracticalremarks() {
        return practicalremarks;
    }

    /**
     * @param practicalremarks the practicalremarks to set
     */
    public void setPracticalremarks(String practicalremarks) {
        this.practicalremarks = practicalremarks;
    }

    /**
     * @return the resultId2
     */
    public int getResultId2() {
        return resultId2;
    }

    /**
     * @param resultId2 the resultId2 to set
     */
    public void setResultId2(int resultId2) {
        this.resultId2 = resultId2;
    }

    /**
     * @return the statusModal
     */
    public int getStatusModal() {
        return statusModal;
    }

    /**
     * @param statusModal the statusModal to set
     */
    public void setStatusModal(int statusModal) {
        this.statusModal = statusModal;
    }

    /**
     * @return the username1Hiden
     */
    public String getUsername1Hiden() {
        return username1Hiden;
    }

    /**
     * @param username1Hiden the username1Hiden to set
     */
    public void setUsername1Hiden(String username1Hiden) {
        this.username1Hiden = username1Hiden;
    }

    /**
     * @return the username2Hiden
     */
    public String getUsername2Hiden() {
        return username2Hiden;
    }

    /**
     * @param username2Hiden the username2Hiden to set
     */
    public void setUsername2Hiden(String username2Hiden) {
        this.username2Hiden = username2Hiden;
    }

    /**
     * @return the averagescore
     */
    public int getAveragescore() {
        return averagescore;
    }

    /**
     * @param averagescore the averagescore to set
     */
    public void setAveragescore(int averagescore) {
        this.averagescore = averagescore;
    }

    /**
     * @return the candidateIdModal
     */
    public int getCandidateIdModal() {
        return candidateIdModal;
    }

    /**
     * @param candidateIdModal the candidateIdModal to set
     */
    public void setCandidateIdModal(int candidateIdModal) {
        this.candidateIdModal = candidateIdModal;
    }

    /**
     * @return the completebydate2
     */
    public String getCompletebydate2() {
        return completebydate2;
    }

    /**
     * @param completebydate2 the completebydate2 to set
     */
    public void setCompletebydate2(String completebydate2) {
        this.completebydate2 = completebydate2;
    }

    /**
     * @return the outcomeremarks
     */
    public String getOutcomeremarks() {
        return outcomeremarks;
    }

    /**
     * @param outcomeremarks the outcomeremarks to set
     */
    public void setOutcomeremarks(String outcomeremarks) {
        this.outcomeremarks = outcomeremarks;
    }

    /**
     * @return the outcomeId
     */
    public int getOutcomeId() {
        return outcomeId;
    }

    /**
     * @param outcomeId the outcomeId to set
     */
    public void setOutcomeId(int outcomeId) {
        this.outcomeId = outcomeId;
    }

    /**
     * @return the trainingId
     */
    public int getTrainingId() {
        return trainingId;
    }

    /**
     * @param trainingId the trainingId to set
     */
    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }

    /**
     * @return the rejection
     */
    public String getRejection() {
        return rejection;
    }

    /**
     * @param rejection the rejection to set
     */
    public void setRejection(String rejection) {
        this.rejection = rejection;
    }

    /**
     * @return the reasonId
     */
    public int getReasonId() {
        return reasonId;
    }

    /**
     * @param reasonId the reasonId to set
     */
    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

  
}