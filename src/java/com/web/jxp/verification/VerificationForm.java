package com.web.jxp.verification;
import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

public class VerificationForm extends ActionForm
{
    private int verificationId;
    private String search;
    private String doCancel;
    private String doModify;
    private String doView;
    private String doVerify;
    private String doVerifyOther;
    
    private int statusIndex;
    private int positionIndex;
    private int tabIndex;
    
    private Collection position;
    
    private int type;
    private int tabId;
    private int childId;
    private int rbverification;
    private int candidateId;
    private String verificatonauthority;
    private FormFile verifiedfile;
    private int ctp;
    private int tabIdOther;
    private int alertId;
    private String documentName;
    private String coursename;

    public String getDoVerifyOther() {
        return doVerifyOther;
    }

    public void setDoVerifyOther(String doVerifyOther) {
        this.doVerifyOther = doVerifyOther;
    }

    public int getTabIdOther() {
        return tabIdOther;
    }

    public void setTabIdOther(int tabIdOther) {
        this.tabIdOther = tabIdOther;
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

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    public int getVerificationId() {
        return verificationId;
    }

    /**
     * @param verificationId the verificationId to set
     */
    public void setVerificationId(int verificationId) {
        this.verificationId = verificationId;
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

    public Collection getPosition() {
        return position;
    }

    public void setPosition(Collection position) {
        this.position = position;
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
     * @return the tabId
     */
    public int getTabId() {
        return tabId;
    }

    /**
     * @param tabId the tabId to set
     */
    public void setTabId(int tabId) {
        this.tabId = tabId;
    }

    /**
     * @return the childId
     */
    public int getChildId() {
        return childId;
    }

    /**
     * @param childId the childId to set
     */
    public void setChildId(int childId) {
        this.childId = childId;
    }

    /**
     * @return the doVerify
     */
    public String getDoVerify() {
        return doVerify;
    }

    /**
     * @param doVerify the doVerify to set
     */
    public void setDoVerify(String doVerify) {
        this.doVerify = doVerify;
    }

    /**
     * @return the rbverification
     */
    public int getRbverification() {
        return rbverification;
    }

    /**
     * @param rbverification the rbverification to set
     */
    public void setRbverification(int rbverification) {
        this.rbverification = rbverification;
    }

    /**
     * @return the verificatonauthority
     */
    public String getVerificatonauthority() {
        return verificatonauthority;
    }

    /**
     * @param verificatonauthority the verificatonauthority to set
     */
    public void setVerificatonauthority(String verificatonauthority) {
        this.verificatonauthority = verificatonauthority;
    }

    /**
     * @return the verifiedfile
     */
    public FormFile getVerifiedfile() {
        return verifiedfile;
    }

    /**
     * @param verifiedfile the verifiedfile to set
     */
    public void setVerifiedfile(FormFile verifiedfile) {
        this.verifiedfile = verifiedfile;
    }

    /**
     * @return the tabIndex
     */
    public int getTabIndex() {
        return tabIndex;
    }

    /**
     * @param tabIndex the tabIndex to set
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
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
     * @return the documentName
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * @param documentName the documentName to set
     */
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    /**
     * @return the coursename
     */
    public String getCoursename() {
        return coursename;
    }

    /**
     * @param coursename the coursename to set
     */
    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    /**
     * @return the alertId
     */
    public int getAlertId() {
        return alertId;
    }

    /**
     * @param alertId the alertId to set
     */
    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }
    
}