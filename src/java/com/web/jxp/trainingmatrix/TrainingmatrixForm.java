package com.web.jxp.trainingmatrix;
import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class TrainingmatrixForm extends ActionForm
{
    private String search;
    private String doCancel;
    private String doSave;
    private String doView;
    private Collection categories;
    private Collection clients;
    private Collection assets;
    private int clientIdIndex;    
    private int assetIdIndex;
    private int categoryId;
    private String doCategory;
    private String doSaveCourse;
    private String categoryName;
    private int categoryIdHidden;
    private int positionIdHidden;
    
    private int[] subcategoryId;
    private int[] courseId;
    private int[] tctypeId;
    private int[] trainingId;
    private int[] levelId;
    private int[] courseIdrel;
    private int positionId;
    private int clientId;
    private int assetId;
    private int ctp;
    private Collection positions;

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
     * @return the clients
     */
    public Collection getClients() {
        return clients;
    }

    /**
     * @param clients the clients to set
     */
    public void setClients(Collection clients) {
        this.clients = clients;
    }

    /**
     * @return the assets
     */
    public Collection getAssets() {
        return assets;
    }

    /**
     * @param assets the assets to set
     */
    public void setAssets(Collection assets) {
        this.assets = assets;
    }

    /**
     * @return the clientIdIndex
     */
    public int getClientIdIndex() {
        return clientIdIndex;
    }

    /**
     * @param clientIdIndex the clientIdIndex to set
     */
    public void setClientIdIndex(int clientIdIndex) {
        this.clientIdIndex = clientIdIndex;
    }

    /**
     * @return the assetIdIndex
     */
    public int getAssetIdIndex() {
        return assetIdIndex;
    }

    /**
     * @param assetIdIndex the assetIdIndex to set
     */
    public void setAssetIdIndex(int assetIdIndex) {
        this.assetIdIndex = assetIdIndex;
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
     * @return the positionIdHidden
     */
    public int getPositionIdHidden() {
        return positionIdHidden;
    }

    /**
     * @param positionIdHidden the positionIdHidden to set
     */
    public void setPositionIdHidden(int positionIdHidden) {
        this.positionIdHidden = positionIdHidden;
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

    /**
     * @return the courseId
     */
    public int[] getCourseId() {
        return courseId;
    }

    /**
     * @param courseId the courseId to set
     */
    public void setCourseId(int[] courseId) {
        this.courseId = courseId;
    }

    /**
     * @return the tctypeId
     */
    public int[] getTctypeId() {
        return tctypeId;
    }

    /**
     * @param tctypeId the tctypeId to set
     */
    public void setTctypeId(int[] tctypeId) {
        this.tctypeId = tctypeId;
    }

    /**
     * @return the trainingId
     */
    public int[] getTrainingId() {
        return trainingId;
    }

    /**
     * @param trainingId the trainingId to set
     */
    public void setTrainingId(int[] trainingId) {
        this.trainingId = trainingId;
    }

    /**
     * @return the levelId
     */
    public int[] getLevelId() {
        return levelId;
    }

    /**
     * @param levelId the levelId to set
     */
    public void setLevelId(int[] levelId) {
        this.levelId = levelId;
    }

    /**
     * @return the courseIdrel
     */
    public int[] getCourseIdrel() {
        return courseIdrel;
    }

    /**
     * @param courseIdrel the courseIdrel to set
     */
    public void setCourseIdrel(int[] courseIdrel) {
        this.courseIdrel = courseIdrel;
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
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * @return the assetId
     */
    public int getAssetId() {
        return assetId;
    }

    /**
     * @param assetId the assetId to set
     */
    public void setAssetId(int assetId) {
        this.assetId = assetId;
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
     * @return the positions
     */
    public Collection getPositions() {
        return positions;
    }

    /**
     * @param positions the positions to set
     */
    public void setPositions(Collection positions) {
        this.positions = positions;
    }
}