package com.web.jxp.verificationcheckpoint;

import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.MultipartRequestHandler;

public class VerificationcheckpointForm extends ActionForm {

    private int verificationcheckpointId;
    private int status;
    private String search;
    private String doCancel;
    private String doModify;
    private String verificationcheckpointName;
    private String doAdd;
    private String doSave;
    private Collection statuses;
    private String doView;
    private int statusIndex;
    private String verificationcheckpointcode;
    private int tabid;
    private String displaynote;
    private int minverification;
    private int ctp;

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    public int getMinverification() {
        return minverification;
    }

    public void setMinverification(int minverification) {
        this.minverification = minverification;
    }

    public String getDisplaynote() {
        return displaynote;
    }

    public void setDisplaynote(String displaynote) {
        this.displaynote = displaynote;
    }

    public int getTabid() {
        return tabid;
    }

    public void setTabid(int tabid) {
        this.tabid = tabid;
    }

    public String getVerificationcheckpointcode() {
        return verificationcheckpointcode;
    }

    public void setVerificationcheckpointcode(String verificationcheckpointcode) {
        this.verificationcheckpointcode = verificationcheckpointcode;
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

    /**
     * @return the verificationcheckpointId
     */
    public int getVerificationcheckpointId() {
        return verificationcheckpointId;
    }

    /**
     * @param verificationcheckpointId the verificationcheckpointId to set
     */
    public void setVerificationcheckpointId(int verificationcheckpointId) {
        this.verificationcheckpointId = verificationcheckpointId;
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
    public String getVerificationcheckpointName() {
        return verificationcheckpointName;
    }

    /**
     * @param name the name to set
     */
    public void setVerificationcheckpointName(String verificationcheckpointName) {
        this.verificationcheckpointName = verificationcheckpointName;
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
}
