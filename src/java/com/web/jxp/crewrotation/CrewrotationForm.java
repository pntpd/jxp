package com.web.jxp.crewrotation;

import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.MultipartRequestHandler;

public class CrewrotationForm extends ActionForm {

    private int jobpostId;
    private int ctp;
    private int statusIndex;
    private int ddlValue;
    private String ddlLabel;
    private String search;
    private String doCancel;
    private String doView;
    private String doSave;
    private String doDetails;
    private String doSummary;
    private String doDelete;

    private Collection positions;
    private Collection locations;
    private Collection clients;
    private Collection assets;
    private Collection resumetemplates;

    private int clientIdIndex;
    private int assetIdIndex;
    private int status;
    private int candidateId;
    private int resumetempId;
    private int clientId;
    private int shortlistId;
    private int clientassetId;
    private int crewrotationId;
    private int activityId;
    private String astartdate;
    private String aenddate;
    private String remarks;
    private String doSaveActivity;
    private String doSaveSignoff;
    private String doSaveSignon;
    private String doSavereqdoc;
    private int countryId;

    private int rdateId;
    private int noofdays;
    private String sdate;
    private String stime;
    private String edate;
    private String etime;
    private int[] ddlhidden;
    private int[] docIds;

    private String cval1;
    private String cval2;
    private String cval3;
    private String rejectReason[];
    private String rejectRemark;
    private String comments;
    private String designation;
    private String dmname;
    private String contractor;
    private String validto;
    private String validfrom;
    private String txtAcceptremark;
    private String declineReason[];
    private String txtDeclineremark;
    private String hdnclientasset;

    private String searchcr;
    private String crdate;
    private int statusindex;

    private String fromdateindex;
    private String todateindex;
    private int activityIdindex;

    private int cractivityId;

    private String reasons;
    private String reasons1;
    private String sdate1;
    private String stime1;

    private String edate1;
    private String etime1;
    private int rdateid1;
    private String remarks1;
    private String reasons2;
    private String sdate2;
    private String stime2;
    private String reasons3;
    private String sdate3;
    private String stime3;
    private String siremarks;
    private int signonoffId;

    private int dynamicId;
    private int activityDropdown;
    private int positionIdIndex;
    private int positionIndex;
    private String reasonsDDL;

    private String doPlanning;
    private String searchp;
    private String fromDate;
    private String toDate;
    private int positionIdPlan;
    private int statusPlan;
    private int positionId;

    private String reasonsDDL1;

    private String reasonsDDL2;
    private String reasonsDDL3;

    private String doSearchCR;
    private String positionView;
    private int hdnCrewRota;
    private int p2positionId;
    private int positionId2;
    private int positionIdEdit;
    private int positionIdActivity;
    private int positionIdSignOff;
    private int crewId;
    private int rota;
    

    public String getDoPlanning() {
        return doPlanning;
    }

    public void setDoPlanning(String doPlanning) {
        this.doPlanning = doPlanning;
    }

    public String getSearchp() {
        return searchp;
    }

    public void setSearchp(String searchp) {
        this.searchp = searchp;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public int getPositionIdPlan() {
        return positionIdPlan;
    }

    public void setPositionIdPlan(int positionIdPlan) {
        this.positionIdPlan = positionIdPlan;
    }

    public int getStatusPlan() {
        return statusPlan;
    }

    public void setStatusPlan(int statusPlan) {
        this.statusPlan = statusPlan;
    }

    public String getSiremarks() {
        return siremarks;
    }

    public void setSiremarks(String siremarks) {
        this.siremarks = siremarks;
    }

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public int getActivityDropdown() {
        return activityDropdown;
    }

    public void setActivityDropdown(int activityDropdown) {
        this.activityDropdown = activityDropdown;
    }

    public int getPositionIdIndex() {
        return positionIdIndex;
    }

    public void setPositionIdIndex(int positionIdIndex) {
        this.positionIdIndex = positionIdIndex;
    }

    public int getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(int positionIndex) {
        this.positionIndex = positionIndex;
    }

    public int getSignonoffId() {
        return signonoffId;
    }

    public void setSignonoffId(int signonoffId) {
        this.signonoffId = signonoffId;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getSdate1() {
        return sdate1;
    }

    public void setSdate1(String sdate1) {
        this.sdate1 = sdate1;
    }

    public String getStime1() {
        return stime1;
    }

    public void setStime1(String stime1) {
        this.stime1 = stime1;
    }

    public String getEdate1() {
        return edate1;
    }

    public void setEdate1(String edate1) {
        this.edate1 = edate1;
    }

    public String getEtime1() {
        return etime1;
    }

    public void setEtime1(String etime1) {
        this.etime1 = etime1;
    }

    public int getRdateid1() {
        return rdateid1;
    }

    public void setRdateid1(int rdateid1) {
        this.rdateid1 = rdateid1;
    }

    public String getRemarks1() {
        return remarks1;
    }

    public void setRemarks1(String remarks1) {
        this.remarks1 = remarks1;
    }

    public String getSdate2() {
        return sdate2;
    }

    public void setSdate2(String sdate2) {
        this.sdate2 = sdate2;
    }

    public String getStime2() {
        return stime2;
    }

    public void setStime2(String stime2) {
        this.stime2 = stime2;
    }

    public String getReasons1() {
        return reasons1;
    }

    public void setReasons1(String reasons1) {
        this.reasons1 = reasons1;
    }

    public String getReasons2() {
        return reasons2;
    }

    public void setReasons2(String reasons2) {
        this.reasons2 = reasons2;
    }

    public String getReasons3() {
        return reasons3;
    }

    public void setReasons3(String reasons3) {
        this.reasons3 = reasons3;
    }

    public String getSdate3() {
        return sdate3;
    }

    public void setSdate3(String sdate3) {
        this.sdate3 = sdate3;
    }

    public String getStime3() {
        return stime3;
    }

    public void setStime3(String stime3) {
        this.stime3 = stime3;
    }

    public int getCractivityId() {
        return cractivityId;
    }

    public void setCractivityId(int cractivityId) {
        this.cractivityId = cractivityId;
    }

    public String getFromdateindex() {
        return fromdateindex;
    }

    public void setFromdateindex(String fromdateindex) {
        this.fromdateindex = fromdateindex;
    }

    public String getTodateindex() {
        return todateindex;
    }

    public void setTodateindex(String todateindex) {
        this.todateindex = todateindex;
    }

    public int getActivityIdindex() {
        return activityIdindex;
    }

    public void setActivityIdindex(int activityIdindex) {
        this.activityIdindex = activityIdindex;
    }

    public String getSearchcr() {
        return searchcr;
    }

    public void setSearchcr(String searchcr) {
        this.searchcr = searchcr;
    }

    public String getCrdate() {
        return crdate;
    }

    public void setCrdate(String crdate) {
        this.crdate = crdate;
    }

    public int getStatusindex() {
        return statusindex;
    }

    public void setStatusindex(int statusindex) {
        this.statusindex = statusindex;
    }

    public int[] getDocIds() {
        return docIds;
    }

    public void setDocIds(int[] docIds) {
        this.docIds = docIds;
    }

    public int[] getDdlhidden() {
        return ddlhidden;
    }

    public void setDdlhidden(int[] ddlhidden) {
        this.ddlhidden = ddlhidden;
    }

    public String getDoSavereqdoc() {
        return doSavereqdoc;
    }

    public void setDoSavereqdoc(String doSavereqdoc) {
        this.doSavereqdoc = doSavereqdoc;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public int getNoofdays() {
        return noofdays;
    }

    public void setNoofdays(int noofdays) {
        this.noofdays = noofdays;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getDoSaveSignoff() {
        return doSaveSignoff;
    }

    public void setDoSaveSignoff(String doSaveSignoff) {
        this.doSaveSignoff = doSaveSignoff;
    }

    public String getDoSaveSignon() {
        return doSaveSignon;
    }

    public void setDoSaveSignon(String doSaveSignon) {
        this.doSaveSignon = doSaveSignon;
    }

    public int getRdateId() {
        return rdateId;
    }

    public void setRdateId(int rdateId) {
        this.rdateId = rdateId;
    }

    public String getDoSaveActivity() {
        return doSaveActivity;
    }

    public void setDoSaveActivity(String doSaveActivity) {
        this.doSaveActivity = doSaveActivity;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getAstartdate() {
        return astartdate;
    }

    public void setAstartdate(String astartdate) {
        this.astartdate = astartdate;
    }

    public String getAenddate() {
        return aenddate;
    }

    public void setAenddate(String aenddate) {
        this.aenddate = aenddate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getCrewrotationId() {
        return crewrotationId;
    }

    public void setCrewrotationId(int crewrotationId) {
        this.crewrotationId = crewrotationId;
    }

    public Collection getLocations() {
        return locations;
    }

    public void setLocations(Collection locations) {
        this.locations = locations;
    }

    public String getDoDetails() {
        return doDetails;
    }

    public void setDoDetails(String doDetails) {
        this.doDetails = doDetails;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public ActionServlet getServlet() {
        return servlet;
    }

    public void setServlet(ActionServlet servlet) {
        this.servlet = servlet;
    }

    public MultipartRequestHandler getMultipartRequestHandler() {
        return multipartRequestHandler;
    }

    public void setMultipartRequestHandler(MultipartRequestHandler multipartRequestHandler) {
        this.multipartRequestHandler = multipartRequestHandler;
    }

    public int getClientassetId() {
        return clientassetId;
    }

    public void setClientassetId(int clientassetId) {
        this.clientassetId = clientassetId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDmname() {
        return dmname;
    }

    public void setDmname(String dmname) {
        this.dmname = dmname;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getValidto() {
        return validto;
    }

    public void setValidto(String validto) {
        this.validto = validto;
    }

    public String getValidfrom() {
        return validfrom;
    }

    public void setValidfrom(String validfrom) {
        this.validfrom = validfrom;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

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

    public int getDdlValue() {
        return ddlValue;
    }

    public void setDdlValue(int ddlValue) {
        this.ddlValue = ddlValue;
    }

    public String getDdlLabel() {
        return ddlLabel;
    }

    public void setDdlLabel(String ddlLabel) {
        this.ddlLabel = ddlLabel;
    }

    public Collection getResumetemplates() {
        return resumetemplates;
    }

    public void setResumetemplates(Collection resumetemplates) {
        this.resumetemplates = resumetemplates;
    }

    public int getResumetempId() {
        return resumetempId;
    }

    public void setResumetempId(int resumetempId) {
        this.resumetempId = resumetempId;
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
     * @return the shortlistId
     */
    public int getShortlistId() {
        return shortlistId;
    }

    /**
     * @param shortlistId the shortlistId to set
     */
    public void setShortlistId(int shortlistId) {
        this.shortlistId = shortlistId;
    }

    public String getCval1() {
        return cval1;
    }

    public void setCval1(String cval1) {
        this.cval1 = cval1;
    }

    public String getCval2() {
        return cval2;
    }

    public void setCval2(String cval2) {
        this.cval2 = cval2;
    }

    public String getCval3() {
        return cval3;
    }

    public void setCval3(String cval3) {
        this.cval3 = cval3;
    }

    /**
     * @return the rejectReason
     */
    public String[] getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String[] rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getRejectRemark() {
        return rejectRemark;
    }

    public void setRejectRemark(String rejectRemark) {
        this.rejectRemark = rejectRemark;
    }

    public String getTxtAcceptremark() {
        return txtAcceptremark;
    }

    public void setTxtAcceptremark(String txtAcceptremark) {
        this.txtAcceptremark = txtAcceptremark;
    }

    public String[] getDeclineReason() {
        return declineReason;
    }

    public void setDeclineReason(String[] declineReason) {
        this.declineReason = declineReason;
    }

    public String getTxtDeclineremark() {
        return txtDeclineremark;
    }

    public void setTxtDeclineremark(String txtDeclineremark) {
        this.txtDeclineremark = txtDeclineremark;
    }

    public String getDoSummary() {
        return doSummary;
    }

    public void setDoSummary(String doSummary) {
        this.doSummary = doSummary;
    }

    public String getHdnclientasset() {
        return hdnclientasset;
    }

    public void setHdnclientasset(String hdnclientasset) {
        this.hdnclientasset = hdnclientasset;
    }

    public String getDoDelete() {
        return doDelete;
    }

    public void setDoDelete(String doDelete) {
        this.doDelete = doDelete;
    }

    /**
     * @return the reasonsDDL
     */
    public String getReasonsDDL() {
        return reasonsDDL;
    }

    /**
     * @param reasonsDDL the reasonsDDL to set
     */
    public void setReasonsDDL(String reasonsDDL) {
        this.reasonsDDL = reasonsDDL;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getReasonsDDL1() {
        return reasonsDDL1;
    }

    public void setReasonsDDL1(String reasonsDDL1) {
        this.reasonsDDL1 = reasonsDDL1;
    }

    public String getReasonsDDL2() {
        return reasonsDDL2;
    }

    public void setReasonsDDL2(String reasonsDDL2) {
        this.reasonsDDL2 = reasonsDDL2;
    }

    public String getReasonsDDL3() {
        return reasonsDDL3;
    }

    public void setReasonsDDL3(String reasonsDDL3) {
        this.reasonsDDL3 = reasonsDDL3;
    }

    public String getDoSearchCR() {
        return doSearchCR;
    }

    public void setDoSearchCR(String doSearchCR) {
        this.doSearchCR = doSearchCR;
    }

    public int getHdnCrewRota() {
        return hdnCrewRota;
    }

    public void setHdnCrewRota(int hdnCrewRota) {
        this.hdnCrewRota = hdnCrewRota;
    }

    /**
     * @return the p2positionId
     */
    public int getP2positionId() {
        return p2positionId;
    }

    /**
     * @param p2positionId the p2positionId to set
     */
    public void setP2positionId(int p2positionId) {
        this.p2positionId = p2positionId;
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
     * @return the positionView
     */
    public String getPositionView() {
        return positionView;
    }

    /**
     * @param positionView the positionView to set
     */
    public void setPositionView(String positionView) {
        this.positionView = positionView;
    }

    /**
     * @return the positionIdEdit
     */
    public int getPositionIdEdit() {
        return positionIdEdit;
    }

    /**
     * @param positionIdEdit the positionIdEdit to set
     */
    public void setPositionIdEdit(int positionIdEdit) {
        this.positionIdEdit = positionIdEdit;
    }

    /**
     * @return the positionIdActivity
     */
    public int getPositionIdActivity() {
        return positionIdActivity;
    }

    /**
     * @param positionIdActivity the positionIdActivity to set
     */
    public void setPositionIdActivity(int positionIdActivity) {
        this.positionIdActivity = positionIdActivity;
    }

    /**
     * @return the positionIdSignOff
     */
    public int getPositionIdSignOff() {
        return positionIdSignOff;
    }

    /**
     * @param positionIdSignOff the positionIdSignOff to set
     */
    public void setPositionIdSignOff(int positionIdSignOff) {
        this.positionIdSignOff = positionIdSignOff;
    }

    /**
     * @return the crewId
     */
    public int getCrewId() {
        return crewId;
    }

    /**
     * @param crewId the crewId to set
     */
    public void setCrewId(int crewId) {
        this.crewId = crewId;
    }

    /**
     * @return the rota
     */
    public int getRota() {
        return rota;
    }

    /**
     * @param rota the rota to set
     */
    public void setRota(int rota) {
        this.rota = rota;
    }

}
