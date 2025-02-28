package com.web.jxp.shortlisting;

import org.apache.struts.action.ActionForm;

public class ShortlistingForm extends ActionForm {

    private int ctp;
    private int status;

    private String search;
    private String doCandSearch;
    private String doSave;
    private String doCancel;
    private String fm;

    private int jobpostId;
    private int candidateId;
    private int clientId;
    private int clientAssetId;
    private int sortedcount;
    private int clientIdIndex;

    private String jobpostsearch;

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDoCandSearch() {
        return doCandSearch;
    }

    public void setDoCandSearch(String doCandSearch) {
        this.doCandSearch = doCandSearch;
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
     * @return the jobpostsearch
     */
    public String getJobpostsearch() {
        return jobpostsearch;
    }

    /**
     * @param jobpostsearch the jobpostsearch to set
     */
    public void setJobpostsearch(String jobpostsearch) {
        this.jobpostsearch = jobpostsearch;
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientAssetId() {
        return clientAssetId;
    }

    public void setClientAssetId(int clientAssetId) {
        this.clientAssetId = clientAssetId;
    }

    public int getSortedcount() {
        return sortedcount;
    }

    public void setSortedcount(int sortedcount) {
        this.sortedcount = sortedcount;
    }

    public String getDoCancel() {
        return doCancel;
    }

    public void setDoCancel(String doCancel) {
        this.doCancel = doCancel;
    }

    public int getClientIdIndex() {
        return clientIdIndex;
    }

    public void setClientIdIndex(int clientIdIndex) {
        this.clientIdIndex = clientIdIndex;
    }

    /**
     * @return the fm
     */
    public String getFm() {
        return fm;
    }

    /**
     * @param fm the fm to set
     */
    public void setFm(String fm) {
        this.fm = fm;
    }

}
