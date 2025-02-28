package com.web.jxp.matrix;
import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class MatrixForm extends ActionForm
{
    private int matrixId;
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
    private int matrixpositionId;
    private Collection categories;
    private int categoryId;
    private String doCategory;
    private String doSaveCourse;
    private String categoryName;
    private int categoryIdHidden;    
    private int[] subcategoryId;
    private int[] courseId;
    private int positionId;
    private String doDeleteDetail;

    /**
     * @return the matrixId
     */
    public int getMatrixId() {
        return matrixId;
    }

    /**
     * @param matrixId the matrixId to set
     */
    public void setMatrixId(int matrixId) {
        this.matrixId = matrixId;
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
     * @return the matrixpositionId
     */
    public int getMatrixpositionId() {
        return matrixpositionId;
    }

    /**
     * @param matrixpositionId the matrixpositionId to set
     */
    public void setMatrixpositionId(int matrixpositionId) {
        this.matrixpositionId = matrixpositionId;
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
}