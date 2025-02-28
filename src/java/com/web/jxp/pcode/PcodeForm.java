package com.web.jxp.pcode;
import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class PcodeForm extends ActionForm
{
    private int pcodeId;
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
    private String code;
    private int type;
    private String doCategory;
    private String doSaveQuestion;
    private int categoryIdHidden;
    private Collection categories;
    private int categoryId;
    private String categoryName;
    private int questionId[];

    /**
     * @return the pcodeId
     */
    public int getPcodeId() {
        return pcodeId;
    }

    /**
     * @param pcodeId the pcodeId to set
     */
    public void setPcodeId(int pcodeId) {
        this.pcodeId = pcodeId;
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
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
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
     * @return the doCategory
     */
    public String getDoCategory() {
        return doCategory;
    }

    /**
     * @param doCategory the doCategory to set
     */
    public void setDoCategory(String doCategory) {
        this.doCategory = doCategory;
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
     * @return the categoryIdHidden
     */
    public int getCategoryIdHidden() {
        return categoryIdHidden;
    }

    /**
     * @param categoryIdHidden the categoryIdHidden to set
     */
    public void setCategoryIdHidden(int categoryIdHidden) {
        this.categoryIdHidden = categoryIdHidden;
    }

    /**
     * @return the categories
     */
    public Collection getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(Collection categories) {
        this.categories = categories;
    }

    /**
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return the questionId
     */
    public int[] getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId the questionId to set
     */
    public void setQuestionId(int[] questionId) {
        this.questionId = questionId;
    }
}