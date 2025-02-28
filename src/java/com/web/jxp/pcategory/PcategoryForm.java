package com.web.jxp.pcategory;
import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class PcategoryForm extends ActionForm
{
    private int pcategoryId;
    private int status;
    private String search;
    private String doCancel;
    private String doModify;
    private String name;
    private String doAdd;
    private String doSave;
    private String doView;
    private int assettypeId;
    private Collection assettypes;
    private int assettypeIdIndex;
    private int ctp;
    private Collection pdepts;
    private int pdeptId;
    private int pdeptIdIndex;
    private String description;
    private String searchq;
    private String doSaveQuestion;
    private String doSearchQuestion;
    private String qname;
    private int pquestionId;
    private String doDeleteQuestion;
    private int delId;
    private String qnamesugg;
    private int type;

    /**
     * @return the pcategoryId
     */
    public int getPcategoryId() {
        return pcategoryId;
    }

    /**
     * @param pcategoryId the pcategoryId to set
     */
    public void setPcategoryId(int pcategoryId) {
        this.pcategoryId = pcategoryId;
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

    /**
     * @return the assettypeId
     */
    public int getAssettypeId() {
        return assettypeId;
    }

    /**
     * @param assettypeId the assettypeId to set
     */
    public void setAssettypeId(int assettypeId) {
        this.assettypeId = assettypeId;
    }

    /**
     * @return the assettypes
     */
    public Collection getAssettypes() {
        return assettypes;
    }

    /**
     * @param assettypes the assettypes to set
     */
    public void setAssettypes(Collection assettypes) {
        this.assettypes = assettypes;
    }

    /**
     * @return the assettypeIdIndex
     */
    public int getAssettypeIdIndex() {
        return assettypeIdIndex;
    }

    /**
     * @param assettypeIdIndex the assettypeIdIndex to set
     */
    public void setAssettypeIdIndex(int assettypeIdIndex) {
        this.assettypeIdIndex = assettypeIdIndex;
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
     * @return the pdepts
     */
    public Collection getPdepts() {
        return pdepts;
    }

    /**
     * @param pdepts the pdepts to set
     */
    public void setPdepts(Collection pdepts) {
        this.pdepts = pdepts;
    }

    /**
     * @return the pdeptId
     */
    public int getPdeptId() {
        return pdeptId;
    }

    /**
     * @param pdeptId the pdeptId to set
     */
    public void setPdeptId(int pdeptId) {
        this.pdeptId = pdeptId;
    }

    /**
     * @return the pdeptIdIndex
     */
    public int getPdeptIdIndex() {
        return pdeptIdIndex;
    }

    /**
     * @param pdeptIdIndex the pdeptIdIndex to set
     */
    public void setPdeptIdIndex(int pdeptIdIndex) {
        this.pdeptIdIndex = pdeptIdIndex;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the searchq
     */
    public String getSearchq() {
        return searchq;
    }

    /**
     * @param searchq the searchq to set
     */
    public void setSearchq(String searchq) {
        this.searchq = searchq;
    }

    /**
     * @return the doSaveQuestion
     */
    public String getDoSaveQuestion() {
        return doSaveQuestion;
    }

    /**
     * @param doSaveQuestion the doSaveQuestion to set
     */
    public void setDoSaveQuestion(String doSaveQuestion) {
        this.doSaveQuestion = doSaveQuestion;
    }

    /**
     * @return the doSearchQuestion
     */
    public String getDoSearchQuestion() {
        return doSearchQuestion;
    }

    /**
     * @param doSearchQuestion the doSearchQuestion to set
     */
    public void setDoSearchQuestion(String doSearchQuestion) {
        this.doSearchQuestion = doSearchQuestion;
    }

    /**
     * @return the qname
     */
    public String getQname() {
        return qname;
    }

    /**
     * @param qname the qname to set
     */
    public void setQname(String qname) {
        this.qname = qname;
    }

    /**
     * @return the pquestionId
     */
    public int getPquestionId() {
        return pquestionId;
    }

    /**
     * @param pquestionId the pquestionId to set
     */
    public void setPquestionId(int pquestionId) {
        this.pquestionId = pquestionId;
    }

    /**
     * @return the doDeleteQuestion
     */
    public String getDoDeleteQuestion() {
        return doDeleteQuestion;
    }

    /**
     * @param doDeleteQuestion the doDeleteQuestion to set
     */
    public void setDoDeleteQuestion(String doDeleteQuestion) {
        this.doDeleteQuestion = doDeleteQuestion;
    }

    /**
     * @return the delId
     */
    public int getDelId() {
        return delId;
    }

    /**
     * @param delId the delId to set
     */
    public void setDelId(int delId) {
        this.delId = delId;
    }

    /**
     * @return the qnamesugg
     */
    public String getQnamesugg() {
        return qnamesugg;
    }

    /**
     * @param qnamesugg the qnamesugg to set
     */
    public void setQnamesugg(String qnamesugg) {
        this.qnamesugg = qnamesugg;
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
}