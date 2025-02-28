package com.web.jxp.benefitinformationtype;
import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.MultipartRequestHandler;

public class BenefitinformationtypeForm extends ActionForm
{
    private int benefitinformationtypeId;
    private int status;
    private String search;
    private String doCancel;
    private String doModify;
    private String benefitinformationtypeName;
    private String doAdd;
    private String doSave;
    private String doView;    
    private int benefittypeId;
    private Collection benefittypes;
    private String BenefittypeName;
    private int ctp;

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    public int getBenefittypeId() {
        return benefittypeId;
    }

    public void setBenefittypeId(int benefittypeId) {
        this.benefittypeId = benefittypeId;
    }

    public Collection getBenefittypes() {
        return benefittypes;
    }

    public void setBenefittypes(Collection benefittypes) {
        this.benefittypes = benefittypes;
    }

    public String getBenefittypeName() {
        return BenefittypeName;
    }

    public void setBenefittypeName(String BenefittypeName) {
        this.BenefittypeName = BenefittypeName;
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
     * @return the benefitinformationtypeId
     */
    public int getBenefitinformationtypeId() {
        return benefitinformationtypeId;
    }

    /**
     * @param benefitinformationtypeId the benefitinformationtypeId to set
     */
    public void setBenefitinformationtypeId(int benefitinformationtypeId) {
        this.benefitinformationtypeId = benefitinformationtypeId;
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
    public String getBenefitinformationtypeName() {
        return benefitinformationtypeName;
    }

    /**
     * @param name the name to set
     */
    public void setBenefitinformationtypeName(String benefitinformationtypeName) {
        this.benefitinformationtypeName = benefitinformationtypeName;
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
}