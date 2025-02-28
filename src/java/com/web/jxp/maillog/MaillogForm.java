package com.web.jxp.maillog;

import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class MaillogForm extends ActionForm
{ 
    private int maillogId;
    private String search; 
    private String page;
    private int moduleId;
    private String fromDate;
    private String toDate;
    
    private String doMaillog;
    private String searchmaillog;
    private int typeIndex;
    private String doView;
    private String doCancel;

    private Collection moduleCat;
    private int ctp;

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }
    
    /**
     * Getter for property maillogId.
     * @return Value of property maillogId.
     */
    public int getMaillogId() {
        return maillogId;
    }
    
    /**
     * Setter for property maillogId.
     * @param maillogId New value of property maillogId.
     */
    public void setMaillogId(int maillogId) {
        this.maillogId = maillogId;
    }

    public int getModuleId() {
        return moduleId;
    }

   

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }
    
    /**
     * Getter for property page.
     * @return Value of property page.
     */
    public java.lang.String getPage() {
        return page;
    }
    
    /**
     * Setter for property page.
     * @param page New value of property page.
     */
    public void setPage(java.lang.String page) {
        this.page = page;
    }
    
    /**
     * Getter for property search.
     * @return Value of property search.
     */
    public java.lang.String getSearch() {
        return search;
    }
    
    /**
     * Setter for property search.
     * @param search New value of property search.
     */
    public void setSearch(java.lang.String search) {
        this.search = search;
    }
    
    /**
     * Getter for property fromDate.
     * @return Value of property fromDate.
     */
    public java.lang.String getFromDate() {
        return fromDate;
    }
    
    /**
     * Setter for property fromDate.
     * @param fromDate New value of property fromDate.
     */
    public void setFromDate(java.lang.String fromDate) {
        this.fromDate = fromDate;
    }
    
    /**
     * Getter for property toDate.
     * @return Value of property toDate.
     */
    public java.lang.String getToDate() {
        return toDate;
    }
    
    /**
     * Setter for property toDate.
     * @param toDate New value of property toDate.
     */
    public void setToDate(java.lang.String toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the moduleCat
     */
    public Collection getModuleCat() {
        return moduleCat;
    }

    /**
     * @param moduleCat the moduleCat to set
     */
    public void setModuleCat(Collection moduleCat) {
        this.moduleCat = moduleCat;
    }

    /**
     * @return the doMaillog
     */
    public String getDoMaillog() {
        return doMaillog;
    }

    /**
     * @param doMaillog the doMaillog to set
     */
    public void setDoMaillog(String doMaillog) {
        this.doMaillog = doMaillog;
    }

    /**
     * @return the typeIndex
     */
    public int getTypeIndex() {
        return typeIndex;
    }

    /**
     * @param typeIndex the typeIndex to set
     */
    public void setTypeIndex(int typeIndex) {
        this.typeIndex = typeIndex;
    }

    /**
     * @return the searchmaillog
     */
    public String getSearchmaillog() {
        return searchmaillog;
    }

    /**
     * @param searchmaillog the searchmaillog to set
     */
    public void setSearchmaillog(String searchmaillog) {
        this.searchmaillog = searchmaillog;
    }

    public String getDoView() {
        return doView;
    }

    public void setDoView(String doView) {
        this.doView = doView;
    }    

    public String getDoCancel() {
        return doCancel;
    }

    public void setDoCancel(String doCancel) {
        this.doCancel = doCancel;
    }
    
}
