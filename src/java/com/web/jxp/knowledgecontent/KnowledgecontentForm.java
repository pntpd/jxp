package com.web.jxp.knowledgecontent;

import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class KnowledgecontentForm extends ActionForm {

    private int categoryId;
    private int status;
    private String search;
    private String doCancel;
    private String doModify;
    private String doModifysubcategory;
    private String doModifycourse;
    private String name;
    private String doAdd;
    private String doSave;
    private String doView;
    private int ctp;

    private String categorycode;
    private String cdescription;
    private String attachmenthidden;
    private FormFile attachment;
    private FormFile upload1;

    private int subcategoryId;
    private int subcategoryIdIndex;
    private int esubcategoryId;
    private int courseId;
    private int substatus;
    private String doIndexSubcategory;
    private String doAddSubcategory;
    private String doSaveSubcategory;
    private String subcategorycode;
    private String doIndexCourse;
    private String doAddCourse;
    private String doSaveCourse;
    private String coursecode;
    private String fname;
    private String categoryname;
    private String subcategoryname;
    private String dodeleteCourse;
    private String dodeletesubcategory;
    private String topicname;
    private Collection subcategorys;
    private String assettype[];
    private String doAddFileList;
    private String doDeleteFileList;
    private String dispname;
    private int type;
    private int tempcount;

    public int getSubstatus() {
        return substatus;
    }

    public void setSubstatus(int substatus) {
        this.substatus = substatus;
    }

    public String getDodeleteCourse() {
        return dodeleteCourse;
    }

    public void setDodeleteCourse(String dodeleteCourse) {
        this.dodeleteCourse = dodeleteCourse;
    }

    public String getDodeletesubcategory() {
        return dodeletesubcategory;
    }

    public void setDodeletesubcategory(String dodeletesubcategory) {
        this.dodeletesubcategory = dodeletesubcategory;
    }

    public String getSubcategoryname() {
        return subcategoryname;
    }

    public void setSubcategoryname(String subcategoryname) {
        this.subcategoryname = subcategoryname;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getDoModifysubcategory() {
        return doModifysubcategory;
    }

    public void setDoModifysubcategory(String doModifysubcategory) {
        this.doModifysubcategory = doModifysubcategory;
    }

    public String getDoModifycourse() {
        return doModifycourse;
    }

    public void setDoModifycourse(String doModifycourse) {
        this.doModifycourse = doModifycourse;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getEsubcategoryId() {
        return esubcategoryId;
    }

    public void setEsubcategoryId(int esubcategoryId) {
        this.esubcategoryId = esubcategoryId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public Collection getSubcategorys() {
        return subcategorys;
    }

    public void setSubcategorys(Collection subcategorys) {
        this.subcategorys = subcategorys;
    }

    public int getSubcategoryIdIndex() {
        return subcategoryIdIndex;
    }

    public void setSubcategoryIdIndex(int subcategoryIdIndex) {
        this.subcategoryIdIndex = subcategoryIdIndex;
    }

    public String getDoAddCourse() {
        return doAddCourse;
    }

    public void setDoAddCourse(String doAddCourse) {
        this.doAddCourse = doAddCourse;
    }

    public String getDoIndexCourse() {
        return doIndexCourse;
    }

    public void setDoIndexCourse(String doIndexCourse) {
        this.doIndexCourse = doIndexCourse;
    }

    public String getDoSaveCourse() {
        return doSaveCourse;
    }

    public void setDoSaveCourse(String doSaveCourse) {
        this.doSaveCourse = doSaveCourse;
    }

    public String getSubcategorycode() {
        return subcategorycode;
    }

    public void setSubcategorycode(String subcategorycode) {
        this.subcategorycode = subcategorycode;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getDoSaveSubcategory() {
        return doSaveSubcategory;
    }

    public void setDoSaveSubcategory(String doSaveSubcategory) {
        this.doSaveSubcategory = doSaveSubcategory;
    }

    public String getDoIndexSubcategory() {
        return doIndexSubcategory;
    }

    public void setDoIndexSubcategory(String doIndexSubcategory) {
        this.doIndexSubcategory = doIndexSubcategory;
    }

    public String getDoAddSubcategory() {
        return doAddSubcategory;
    }

    public void setDoAddSubcategory(String doAddSubcategory) {
        this.doAddSubcategory = doAddSubcategory;
    }

    public FormFile getAttachment() {
        return attachment;
    }

    public void setAttachment(FormFile attachment) {
        this.attachment = attachment;
    }

    public String getAttachmenthidden() {
        return attachmenthidden;
    }

    public void setAttachmenthidden(String attachmenthidden) {
        this.attachmenthidden = attachmenthidden;
    }

    public String getCdescription() {
        return cdescription;
    }

    public void setCdescription(String cdescription) {
        this.cdescription = cdescription;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategorycode() {
        return categorycode;
    }

    public void setCategorycode(String categorycode) {
        this.categorycode = categorycode;
    }

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

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

    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }

    public String[] getAssettype() {
        return assettype;
    }

    public void setAssettype(String[] assettype) {
        this.assettype = assettype;
    }

    public String getDoAddFileList() {
        return doAddFileList;
    }

    public void setDoAddFileList(String doAddFileList) {
        this.doAddFileList = doAddFileList;
    }

    public FormFile getUpload1() {
        return upload1;
    }

    public void setUpload1(FormFile upload1) {
        this.upload1 = upload1;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDispname() {
        return dispname;
    }

    public void setDispname(String dispname) {
        this.dispname = dispname;
    }

    public String getDoDeleteFileList() {
        return doDeleteFileList;
    }

    public void setDoDeleteFileList(String doDeleteFileList) {
        this.doDeleteFileList = doDeleteFileList;
    }

    public int getTempcount() {
        return tempcount;
    }

    public void setTempcount(int tempcount) {
        this.tempcount = tempcount;
    }

}
