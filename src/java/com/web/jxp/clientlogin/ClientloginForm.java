package com.web.jxp.clientlogin;

import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class ClientloginForm extends ActionForm 
{
    private int ddlValue;
    private String ddlLabel;
    private int status;    
    private String search;    
    private String search2;    
    private String doCancel;
    private String name;
    private String doView;    
    private int positionIndex;
    private int clientIdIndex;
    private int assetIdIndex;
    private Collection clients;
    private Collection assets;
    private Collection positions;
    private String doLogout;
    private int jobpostId;
    private String from;
    private int shortlistId;
    private int candidateId;
    private int clientId;
    private int assetId;
    private int ctp;
    private int selCandTimeZone;
    private int selInterviewerTimeZone;
    private int selInterviewer;
    private int selDuration;
    private int interviewId;
    
    private String txtstartdate;
    private String txttime;
    private String selMode;
    private String txtDate;
    private String doSaveInterview;
    
    private String txtLocLink;
    private String txtremarks;
    private String remark;
    private int type;
    private int score;
    private int positionId;
    private int resumetempId;
    private String doEvaluation;
    private String doGenerate;
    private Collection resumetemplates;
    private String cval1;
    private String cval2;
    private String cval3;
    private String cval4;
    private String cval5;
    private String cval6;
    private String cval7;
    private String cval8;
    private String cval9;
    private String cval10;
    
    private String validfrom;
    private String validto;
    private String edate;
    private String contractor;
    private String dmname;
    private String designation;
    private String comments;
    private String variablepay;
    private String doGenerateoffer;
    private String hdnclientasset;
    private String doReject;
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
     * @return the doLogout
     */
    public String getDoLogout() {
        return doLogout;
    }

    /**
     * @param doLogout the doLogout to set
     */
    public void setDoLogout(String doLogout) {
        this.doLogout = doLogout;
    }

    /**
     * @return the ddlValue
     */
    public int getDdlValue() {
        return ddlValue;
    }

    /**
     * @param ddlValue the ddlValue to set
     */
    public void setDdlValue(int ddlValue) {
        this.ddlValue = ddlValue;
    }

    /**
     * @return the ddlLabel
     */
    public String getDdlLabel() {
        return ddlLabel;
    }

    /**
     * @param ddlLabel the ddlLabel to set
     */
    public void setDdlLabel(String ddlLabel) {
        this.ddlLabel = ddlLabel;
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
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
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
     * @return the selCandTimeZone
     */
    public int getSelCandTimeZone() {
        return selCandTimeZone;
    }

    /**
     * @param selCandTimeZone the selCandTimeZone to set
     */
    public void setSelCandTimeZone(int selCandTimeZone) {
        this.selCandTimeZone = selCandTimeZone;
    }

    /**
     * @return the selInterviewerTimeZone
     */
    public int getSelInterviewerTimeZone() {
        return selInterviewerTimeZone;
    }

    /**
     * @param selInterviewerTimeZone the selInterviewerTimeZone to set
     */
    public void setSelInterviewerTimeZone(int selInterviewerTimeZone) {
        this.selInterviewerTimeZone = selInterviewerTimeZone;
    }

    /**
     * @return the selInterviewer
     */
    public int getSelInterviewer() {
        return selInterviewer;
    }

    /**
     * @param selInterviewer the selInterviewer to set
     */
    public void setSelInterviewer(int selInterviewer) {
        this.selInterviewer = selInterviewer;
    }

    /**
     * @return the selDuration
     */
    public int getSelDuration() {
        return selDuration;
    }

    /**
     * @param selDuration the selDuration to set
     */
    public void setSelDuration(int selDuration) {
        this.selDuration = selDuration;
    }

    /**
     * @return the txtstartdate
     */
    public String getTxtstartdate() {
        return txtstartdate;
    }

    /**
     * @param txtstartdate the txtstartdate to set
     */
    public void setTxtstartdate(String txtstartdate) {
        this.txtstartdate = txtstartdate;
    }

    /**
     * @return the txttime
     */
    public String getTxttime() {
        return txttime;
    }

    /**
     * @param txttime the txttime to set
     */
    public void setTxttime(String txttime) {
        this.txttime = txttime;
    }

    /**
     * @return the selMode
     */
    public String getSelMode() {
        return selMode;
    }

    /**
     * @param selMode the selMode to set
     */
    public void setSelMode(String selMode) {
        this.selMode = selMode;
    }

    /**
     * @return the txtDate
     */
    public String getTxtDate() {
        return txtDate;
    }

    /**
     * @param txtDate the txtDate to set
     */
    public void setTxtDate(String txtDate) {
        this.txtDate = txtDate;
    }

    /**
     * @return the doSaveInterview
     */
    public String getDoSaveInterview() {
        return doSaveInterview;
    }

    /**
     * @param doSaveInterview the doSaveInterview to set
     */
    public void setDoSaveInterview(String doSaveInterview) {
        this.doSaveInterview = doSaveInterview;
    }

    /**
     * @return the txtLocLink
     */
    public String getTxtLocLink() {
        return txtLocLink;
    }

    /**
     * @param txtLocLink the txtLocLink to set
     */
    public void setTxtLocLink(String txtLocLink) {
        this.txtLocLink = txtLocLink;
    }

    /**
     * @return the txtremarks
     */
    public String getTxtremarks() {
        return txtremarks;
    }

    /**
     * @param txtremarks the txtremarks to set
     */
    public void setTxtremarks(String txtremarks) {
        this.txtremarks = txtremarks;
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
     * @return the interviewId
     */
    public int getInterviewId() {
        return interviewId;
    }

    /**
     * @param interviewId the interviewId to set
     */
    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
     * @return the doEvaluation
     */
    public String getDoEvaluation() {
        return doEvaluation;
    }

    /**
     * @param doEvaluation the doEvaluation to set
     */
    public void setDoEvaluation(String doEvaluation) {
        this.doEvaluation = doEvaluation;
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
     * @return the doGenerate
     */
    public String getDoGenerate() {
        return doGenerate;
    }

    /**
     * @param doGenerate the doGenerate to set
     */
    public void setDoGenerate(String doGenerate) {
        this.doGenerate = doGenerate;
    }

    /**
     * @return the resumetemplates
     */
    public Collection getResumetemplates() {
        return resumetemplates;
    }

    /**
     * @param resumetemplates the resumetemplates to set
     */
    public void setResumetemplates(Collection resumetemplates) {
        this.resumetemplates = resumetemplates;
    }

    /**
     * @return the resumetempId
     */
    public int getResumetempId() {
        return resumetempId;
    }

    /**
     * @param resumetempId the resumetempId to set
     */
    public void setResumetempId(int resumetempId) {
        this.resumetempId = resumetempId;
    }

    /**
     * @return the cval1
     */
    public String getCval1() {
        return cval1;
    }

    /**
     * @param cval1 the cval1 to set
     */
    public void setCval1(String cval1) {
        this.cval1 = cval1;
    }

    /**
     * @return the cval2
     */
    public String getCval2() {
        return cval2;
    }

    /**
     * @param cval2 the cval2 to set
     */
    public void setCval2(String cval2) {
        this.cval2 = cval2;
    }

    /**
     * @return the cval3
     */
    public String getCval3() {
        return cval3;
    }

    /**
     * @param cval3 the cval3 to set
     */
    public void setCval3(String cval3) {
        this.cval3 = cval3;
    }

    /**
     * @return the cval4
     */
    public String getCval4() {
        return cval4;
    }

    /**
     * @param cval4 the cval4 to set
     */
    public void setCval4(String cval4) {
        this.cval4 = cval4;
    }

    /**
     * @return the cval5
     */
    public String getCval5() {
        return cval5;
    }

    /**
     * @param cval5 the cval5 to set
     */
    public void setCval5(String cval5) {
        this.cval5 = cval5;
    }

    /**
     * @return the cval6
     */
    public String getCval6() {
        return cval6;
    }

    /**
     * @param cval6 the cval6 to set
     */
    public void setCval6(String cval6) {
        this.cval6 = cval6;
    }

    /**
     * @return the cval7
     */
    public String getCval7() {
        return cval7;
    }

    /**
     * @param cval7 the cval7 to set
     */
    public void setCval7(String cval7) {
        this.cval7 = cval7;
    }

    /**
     * @return the cval8
     */
    public String getCval8() {
        return cval8;
    }

    /**
     * @param cval8 the cval8 to set
     */
    public void setCval8(String cval8) {
        this.cval8 = cval8;
    }

    /**
     * @return the cval9
     */
    public String getCval9() {
        return cval9;
    }

    /**
     * @param cval9 the cval9 to set
     */
    public void setCval9(String cval9) {
        this.cval9 = cval9;
    }

    /**
     * @return the cval10
     */
    public String getCval10() {
        return cval10;
    }

    /**
     * @param cval10 the cval10 to set
     */
    public void setCval10(String cval10) {
        this.cval10 = cval10;
    }

    /**
     * @return the validfrom
     */
    public String getValidfrom() {
        return validfrom;
    }

    /**
     * @param validfrom the validfrom to set
     */
    public void setValidfrom(String validfrom) {
        this.validfrom = validfrom;
    }

    /**
     * @return the validto
     */
    public String getValidto() {
        return validto;
    }

    /**
     * @param validto the validto to set
     */
    public void setValidto(String validto) {
        this.validto = validto;
    }

    /**
     * @return the edate
     */
    public String getEdate() {
        return edate;
    }

    /**
     * @param edate the edate to set
     */
    public void setEdate(String edate) {
        this.edate = edate;
    }

    /**
     * @return the contractor
     */
    public String getContractor() {
        return contractor;
    }

    /**
     * @param contractor the contractor to set
     */
    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    /**
     * @return the dmname
     */
    public String getDmname() {
        return dmname;
    }

    /**
     * @param dmname the dmname to set
     */
    public void setDmname(String dmname) {
        this.dmname = dmname;
    }

    /**
     * @return the designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * @param designation the designation to set
     */
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the variablepay
     */
    public String getVariablepay() {
        return variablepay;
    }

    /**
     * @param variablepay the variablepay to set
     */
    public void setVariablepay(String variablepay) {
        this.variablepay = variablepay;
    }

    /**
     * @return the doGenerateoffer
     */
    public String getDoGenerateoffer() {
        return doGenerateoffer;
    }

    /**
     * @param doGenerateoffer the doGenerateoffer to set
     */
    public void setDoGenerateoffer(String doGenerateoffer) {
        this.doGenerateoffer = doGenerateoffer;
    }

    /**
     * @return the hdnclientasset
     */
    public String getHdnclientasset() {
        return hdnclientasset;
    }

    /**
     * @param hdnclientasset the hdnclientasset to set
     */
    public void setHdnclientasset(String hdnclientasset) {
        this.hdnclientasset = hdnclientasset;
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
     * @return the doReject
     */
    public String getDoReject() {
        return doReject;
    }

    /**
     * @param doReject the doReject to set
     */
    public void setDoReject(String doReject) {
        this.doReject = doReject;
    }
    
}
