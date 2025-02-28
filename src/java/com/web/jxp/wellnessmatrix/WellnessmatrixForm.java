package com.web.jxp.wellnessmatrix;
import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class WellnessmatrixForm extends ActionForm
{
    private int wellnessmatrixId;
    private int status;
    private String search;
    private String doCancel;
    private String doModify;
    private String name;
    private String doAdd;
    private String doSave;
    private Collection statuses;
    private String doView;
    private int statusIndex;
    private int ctp;
    private int assettypeId;
    private Collection assettypes;
    private String mid;
    private String description;
    private String doAssign;
    private int wellnessmatrixpositionId;
    private Collection categories;
    private int categoryId;
    private String doCategory;
    private String doSaveCourse;
    private String categoryName;
    private int categoryIdHidden;
    
    private int[] subcategoryId;
    private int[] questionId;
    private int positionId;
    private String doDeleteDetail;
    
    private Collection clients;
    private Collection assets;
    private int clientIdIndex;    
    private int assetIdIndex;
    private int clientId;    
    private int assetId;
    private int type;

    /**
     * @return the wellnessmatrixId
     */
    public int getWellnessmatrixId() {
        return wellnessmatrixId;
    }

    /**
     * @param wellnessmatrixId the wellnessmatrixId to set
     */
    public void setWellnessmatrixId(int wellnessmatrixId) {
        this.wellnessmatrixId = wellnessmatrixId;
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
     * @return the mid
     */
    public String getMid() {
        return mid;
    }

    /**
     * @param mid the mid to set
     */
    public void setMid(String mid) {
        this.mid = mid;
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
     * @return the doAssign
     */
    public String getDoAssign() {
        return doAssign;
    }

    /**
     * @param doAssign the doAssign to set
     */
    public void setDoAssign(String doAssign) {
        this.doAssign = doAssign;
    }

    /**
     * @return the wellnessmatrixpositionId
     */
    public int getWellnessmatrixpositionId() {
        return wellnessmatrixpositionId;
    }

    /**
     * @param wellnessmatrixpositionId the wellnessmatrixpositionId to set
     */
    public void setWellnessmatrixpositionId(int wellnessmatrixpositionId) {
        this.wellnessmatrixpositionId = wellnessmatrixpositionId;
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
     * @return the doSaveCourse
     */
    public String getDoSaveCourse() {
        return doSaveCourse;
    }

    /**
     * @param doSaveCourse the doSaveCourse to set
     */
    public void setDoSaveCourse(String doSaveCourse) {
        this.doSaveCourse = doSaveCourse;
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
     * @return the subcategoryId
     */
    public int[] getSubcategoryId() {
        return subcategoryId;
    }

    /**
     * @param subcategoryId the subcategoryId to set
     */
    public void setSubcategoryId(int[] subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public int[] getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int[] questionId) {
        this.questionId = questionId;
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
     * @return the doDeleteDetail
     */
    public String getDoDeleteDetail() {
        return doDeleteDetail;
    }

    /**
     * @param doDeleteDetail the doDeleteDetail to set
     */
    public void setDoDeleteDetail(String doDeleteDetail) {
        this.doDeleteDetail = doDeleteDetail;
    }

    public Collection getClients() {
        return clients;
    }

    public void setClients(Collection clients) {
        this.clients = clients;
    }

    public Collection getAssets() {
        return assets;
    }

    public void setAssets(Collection assets) {
        this.assets = assets;
    }

    public int getClientIdIndex() {
        return clientIdIndex;
    }

    public void setClientIdIndex(int clientIdIndex) {
        this.clientIdIndex = clientIdIndex;
    }

    public int getAssetIdIndex() {
        return assetIdIndex;
    }

    public void setAssetIdIndex(int assetIdIndex) {
        this.assetIdIndex = assetIdIndex;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getAssetId() {
        return assetId;
    }

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
    
    
}