package com.web.jxp.access;

import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class AccessForm extends ActionForm {

    private int accessId;
    private String search;
    private String page;
    private int moduleId;
    private String fromDate;
    private String toDate;
    private int ctp;

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    private Collection moduleCat;

    /**
     * Getter for property accessId.
     *
     * @return Value of property accessId.
     */
    public int getAccessId() {
        return accessId;
    }

    /**
     * Setter for property accessId.
     *
     * @param accessId New value of property accessId.
     */
    public void setAccessId(int accessId) {
        this.accessId = accessId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * Getter for property page.
     *
     * @return Value of property page.
     */
    public java.lang.String getPage() {
        return page;
    }

    /**
     * Setter for property page.
     *
     * @param page New value of property page.
     */
    public void setPage(java.lang.String page) {
        this.page = page;
    }

    /**
     * Getter for property search.
     *
     * @return Value of property search.
     */
    public java.lang.String getSearch() {
        return search;
    }

    /**
     * Setter for property search.
     *
     * @param search New value of property search.
     */
    public void setSearch(java.lang.String search) {
        this.search = search;
    }

    /**
     * Getter for property fromDate.
     *
     * @return Value of property fromDate.
     */
    public java.lang.String getFromDate() {
        return fromDate;
    }

    /**
     * Setter for property fromDate.
     *
     * @param fromDate New value of property fromDate.
     */
    public void setFromDate(java.lang.String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Getter for property toDate.
     *
     * @return Value of property toDate.
     */
    public java.lang.String getToDate() {
        return toDate;
    }

    /**
     * Setter for property toDate.
     *
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

}
