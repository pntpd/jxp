package com.web.jxp.knowledgebasematrix;

import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class KnowledgebasematrixForm extends ActionForm {

    private int knowledgebasematrixId;
    private int status;
    private String search;
    private String doCancel;
    private String doModify;
    private String name;
    private String doAdd;
    private String doSave;
    private String doView;
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

    public int getKnowledgebasematrixId() {
        return knowledgebasematrixId;
    }

    public void setKnowledgebasematrixId(int knowledgebasematrixId) {
        this.knowledgebasematrixId = knowledgebasematrixId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getDoCancel() {
        return doCancel;
    }

    public void setDoCancel(String doCancel) {
        this.doCancel = doCancel;
    }

    public String getDoModify() {
        return doModify;
    }

    public void setDoModify(String doModify) {
        this.doModify = doModify;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoAdd() {
        return doAdd;
    }

    public void setDoAdd(String doAdd) {
        this.doAdd = doAdd;
    }

    public String getDoSave() {
        return doSave;
    }

    public void setDoSave(String doSave) {
        this.doSave = doSave;
    }

    public String getDoView() {
        return doView;
    }

    public void setDoView(String doView) {
        this.doView = doView;
    }

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    public int getAssettypeId() {
        return assettypeId;
    }

    public void setAssettypeId(int assettypeId) {
        this.assettypeId = assettypeId;
    }

    public Collection getAssettypes() {
        return assettypes;
    }

    public void setAssettypes(Collection assettypes) {
        this.assettypes = assettypes;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoAssign() {
        return doAssign;
    }

    public void setDoAssign(String doAssign) {
        this.doAssign = doAssign;
    }

    public int getWellnessmatrixpositionId() {
        return wellnessmatrixpositionId;
    }

    public void setWellnessmatrixpositionId(int wellnessmatrixpositionId) {
        this.wellnessmatrixpositionId = wellnessmatrixpositionId;
    }

    public Collection getCategories() {
        return categories;
    }

    public void setCategories(Collection categories) {
        this.categories = categories;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDoCategory() {
        return doCategory;
    }

    public void setDoCategory(String doCategory) {
        this.doCategory = doCategory;
    }

    public String getDoSaveCourse() {
        return doSaveCourse;
    }

    public void setDoSaveCourse(String doSaveCourse) {
        this.doSaveCourse = doSaveCourse;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryIdHidden() {
        return categoryIdHidden;
    }

    public void setCategoryIdHidden(int categoryIdHidden) {
        this.categoryIdHidden = categoryIdHidden;
    }

    public int[] getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int[] subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public int[] getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int[] questionId) {
        this.questionId = questionId;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getDoDeleteDetail() {
        return doDeleteDetail;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
