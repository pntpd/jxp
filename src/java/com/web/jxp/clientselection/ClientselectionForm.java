package com.web.jxp.clientselection;

import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class ClientselectionForm extends ActionForm {

    private int jobpostId;
    private int ctp;
    private int statusIndex;
    private int ddlValue;
    private double variablepay;
    private String ddlLabel;
    private String search;
    private String search2;
    private String doCancel;
    private String doView;
    private String doSave;
    private String doGenerate;
    private String doGenerateoffer;
    private String doSelect;
    private String doReject;
    private String doAccept;
    private String doDecline;
    private String doSummary;
    private String doRelease;

    private Collection positions;
    private Collection clients;
    private Collection assets;
    private Collection resumetemplates;

    private int clientIdIndex;
    private int assetIdIndex;
    private int status;
    private int candidateId;
    private int resumetempId;
    private int clientId;
    private int assetId;
    private int shortlistId;
    private int sflag;
    private int oflag;
    private int type;

    private String from;
    private String pgvalue;
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
    private String rejectReason[];
    private String rejectRemark;
    private String comments;
    private String designation;
    private String dmname;
    private String contractor;
    private String validto;
    private String validfrom;
    private String edate;
    private String txtAcceptremark;
    private String declineReason[];
    private String txtDeclineremark;
    private String hdnclientasset;

    public double getVariablepay() {
        return variablepay;
    }

    public void setVariablepay(double variablepay) {
        this.variablepay = variablepay;
    }

    public int getOflag() {
        return oflag;
    }

    public void setOflag(int oflag) {
        this.oflag = oflag;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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
     * @return the pgvalue
     */
    public String getPgvalue() {
        return pgvalue;
    }

    /**
     * @param pgvalue the pgvalue to set
     */
    public void setPgvalue(String pgvalue) {
        this.pgvalue = pgvalue;
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

    public String getDoGenerate() {
        return doGenerate;
    }

    public void setDoGenerate(String doGenerate) {
        this.doGenerate = doGenerate;
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

    /**
     * @return the sflag
     */
    public int getSflag() {
        return sflag;
    }

    /**
     * @param sflag the sflag to set
     */
    public void setSflag(int sflag) {
        this.sflag = sflag;
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

    public String getDoSelect() {
        return doSelect;
    }

    public void setDoSelect(String doSelect) {
        this.doSelect = doSelect;
    }

    public String getDoReject() {
        return doReject;
    }

    public void setDoReject(String doReject) {
        this.doReject = doReject;
    }

    /**
     * @return the rejectReason
     */
    public String[] getRejectReason() {
        return rejectReason;
    }

    /**
     * @param rejectReason the rejectReason to set
     */
    public void setRejectReason(String[] rejectReason) {
        this.rejectReason = rejectReason;
    }

    /**
     * @return the rejectRemark
     */
    public String getRejectRemark() {
        return rejectRemark;
    }

    /**
     * @param rejectRemark the rejectRemark to set
     */
    public void setRejectRemark(String rejectRemark) {
        this.rejectRemark = rejectRemark;
    }

    public String getDoGenerateoffer() {
        return doGenerateoffer;
    }

    public void setDoGenerateoffer(String doGenerateoffer) {
        this.doGenerateoffer = doGenerateoffer;
    }

    public String getDoAccept() {
        return doAccept;
    }

    public void setDoAccept(String doAccept) {
        this.doAccept = doAccept;
    }

    public String getDoDecline() {
        return doDecline;
    }

    public void setDoDecline(String doDecline) {
        this.doDecline = doDecline;
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

    /**
     * @return the doSummary
     */
    public String getDoSummary() {
        return doSummary;
    }

    /**
     * @param doSummary the doSummary to set
     */
    public void setDoSummary(String doSummary) {
        this.doSummary = doSummary;
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
     * @return the doRelease
     */
    public String getDoRelease() {
        return doRelease;
    }

    /**
     * @param doRelease the doRelease to set
     */
    public void setDoRelease(String doRelease) {
        this.doRelease = doRelease;
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
