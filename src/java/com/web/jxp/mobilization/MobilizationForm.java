package com.web.jxp.mobilization;

import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

public class MobilizationForm extends ActionForm {

    private int jobpostId;
    private int ctp;
    private int statusIndex;
    private int ddlValue;
    private String ddlLabel;
    private String search;
    private String txtsearch;
    private String doCancel;
    private String doView;
    private String doSave;
    private String doMobTravel;
    private String doMobAccomm;
    private String doSaveTravel;
    private String doSaveAccomm;
    private String doViewCancel;
    private String doMobMail;
    private String doMobhistMail;
    private String doSaveMob;

    private Collection positions;
    private Collection locations;
    private Collection clients;
    private Collection assets;

    private int clientIdIndex;
    private int assetIdIndex;
    private int status;
    private int candidateId;
    private int clientId;
    private int clientassetId;
    private int crewrotationId;
    private int countryId;
    private int type;
    private int mobilizationId;
    private int positionId;

    private String val1;
    private String val2;
    private String val3;
    private String val4;
    private String val5;
    private String val6;
    private String val7;
    private String val8;
    private String val9;
    private String vald9;
    private String valt9;
    private String val10;
    private String vald10;
    private String valt10;
    private String hdnfilename;

    private FormFile upload1;

    //mail
    private int chktravelnotreq;
    private int chkaccommnotreq;
    private String mailfrom;
    private String mailto;
    private String mailcc;
    private String mailbcc;
    private String mailsubject;
    private String maildescription;

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

    public String getDoMobTravel() {
        return doMobTravel;
    }

    public void setDoMobTravel(String doMobTravel) {
        this.doMobTravel = doMobTravel;
    }

    public String getDoMobAccomm() {
        return doMobAccomm;
    }

    public void setDoMobAccomm(String doMobAccomm) {
        this.doMobAccomm = doMobAccomm;
    }

    public String getVal1() {
        return val1;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public String getVal2() {
        return val2;
    }

    public void setVal2(String val2) {
        this.val2 = val2;
    }

    public String getVal3() {
        return val3;
    }

    public void setVal3(String val3) {
        this.val3 = val3;
    }

    public String getVal4() {
        return val4;
    }

    public void setVal4(String val4) {
        this.val4 = val4;
    }

    public String getVal5() {
        return val5;
    }

    public void setVal5(String val5) {
        this.val5 = val5;
    }

    public String getVal6() {
        return val6;
    }

    public void setVal6(String val6) {
        this.val6 = val6;
    }

    public String getVal7() {
        return val7;
    }

    public void setVal7(String val7) {
        this.val7 = val7;
    }

    public String getVal8() {
        return val8;
    }

    public void setVal8(String val8) {
        this.val8 = val8;
    }

    public String getVal9() {
        return val9;
    }

    public void setVal9(String val9) {
        this.val9 = val9;
    }

    public String getVal10() {
        return val10;
    }

    public void setVal10(String val10) {
        this.val10 = val10;
    }

    public String getDoViewCancel() {
        return doViewCancel;
    }

    public void setDoViewCancel(String doViewCancel) {
        this.doViewCancel = doViewCancel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHdnfilename() {
        return hdnfilename;
    }

    public void setHdnfilename(String hdnfilename) {
        this.hdnfilename = hdnfilename;
    }

    public FormFile getUpload1() {
        return upload1;
    }

    public void setUpload1(FormFile upload1) {
        this.upload1 = upload1;
    }

    public String getDoSaveTravel() {
        return doSaveTravel;
    }

    public void setDoSaveTravel(String doSaveTravel) {
        this.doSaveTravel = doSaveTravel;
    }

    public String getDoSaveAccomm() {
        return doSaveAccomm;
    }

    public void setDoSaveAccomm(String doSaveAccomm) {
        this.doSaveAccomm = doSaveAccomm;
    }

    public String getDoMobMail() {
        return doMobMail;
    }

    public void setDoMobMail(String doMobMail) {
        this.doMobMail = doMobMail;
    }

    public String getMailfrom() {
        return mailfrom;
    }

    public void setMailfrom(String mailfrom) {
        this.mailfrom = mailfrom;
    }

    public String getMailto() {
        return mailto;
    }

    public void setMailto(String mailto) {
        this.mailto = mailto;
    }

    public String getMailcc() {
        return mailcc;
    }

    public void setMailcc(String mailcc) {
        this.mailcc = mailcc;
    }

    public String getMailbcc() {
        return mailbcc;
    }

    public void setMailbcc(String mailbcc) {
        this.mailbcc = mailbcc;
    }

    public String getMailsubject() {
        return mailsubject;
    }

    public void setMailsubject(String mailsubject) {
        this.mailsubject = mailsubject;
    }

    public String getMaildescription() {
        return maildescription;
    }

    public void setMaildescription(String maildescription) {
        this.maildescription = maildescription;
    }

    public int getChktravelnotreq() {
        return chktravelnotreq;
    }

    public void setChktravelnotreq(int chktravelnotreq) {
        this.chktravelnotreq = chktravelnotreq;
    }

    public int getChkaccommnotreq() {
        return chkaccommnotreq;
    }

    public void setChkaccommnotreq(int chkaccommnotreq) {
        this.chkaccommnotreq = chkaccommnotreq;
    }

    public String getDoSaveMob() {
        return doSaveMob;
    }

    public void setDoSaveMob(String doSaveMob) {
        this.doSaveMob = doSaveMob;
    }

    public int getMobilizationId() {
        return mobilizationId;
    }

    public void setMobilizationId(int mobilizationId) {
        this.mobilizationId = mobilizationId;
    }

    public String getVald9() {
        return vald9;
    }

    public void setVald9(String vald9) {
        this.vald9 = vald9;
    }

    public String getValt9() {
        return valt9;
    }

    public void setValt9(String valt9) {
        this.valt9 = valt9;
    }

    public String getVald10() {
        return vald10;
    }

    public void setVald10(String vald10) {
        this.vald10 = vald10;
    }

    public String getValt10() {
        return valt10;
    }

    public void setValt10(String valt10) {
        this.valt10 = valt10;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getTxtsearch() {
        return txtsearch;
    }

    public void setTxtsearch(String txtsearch) {
        this.txtsearch = txtsearch;
    }

    public String getDoMobhistMail() {
        return doMobhistMail;
    }

    public void setDoMobhistMail(String doMobhistMail) {
        this.doMobhistMail = doMobhistMail;
    }

}
