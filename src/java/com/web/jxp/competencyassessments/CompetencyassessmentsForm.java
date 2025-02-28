package com.web.jxp.competencyassessments;
import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class CompetencyassessmentsForm extends ActionForm
{
    private int competencyassessmentsId;
    private int status;
    private String search;
    private String doCancel;
    private String doModify;
    private String name;
    private String doAdd;
    private String doSave;
    private String doView;
    private int assettypeId;
    private int departmentId;
    private Collection assettypes;
    private Collection departments;
    private int assettypeIdIndex;
    private int departmentIdIndex;
    private int ctp;    
    private int schedule;    
    private String description;

    /**
     * @return the competencyassessmentsId
     */
    public int getCompetencyassessmentsId() {
        return competencyassessmentsId;
    }

    /**
     * @param competencyassessmentsId the competencyassessmentsId to set
     */
    public void setCompetencyassessmentsId(int competencyassessmentsId) {
        this.competencyassessmentsId = competencyassessmentsId;
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
     * @return the departmentId
     */
    public int getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the departments
     */
    public Collection getDepartments() {
        return departments;
    }

    /**
     * @param departments the departments to set
     */
    public void setDepartments(Collection departments) {
        this.departments = departments;
    }

    /**
     * @return the departmentIdIndex
     */
    public int getDepartmentIdIndex() {
        return departmentIdIndex;
    }

    /**
     * @param departmentIdIndex the departmentIdIndex to set
     */
    public void setDepartmentIdIndex(int departmentIdIndex) {
        this.departmentIdIndex = departmentIdIndex;
    }

    /**
     * @return the schedule
     */
    public int getSchedule() {
        return schedule;
    }

    /**
     * @param schedule the schedule to set
     */
    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }

    /**
     * @return the departmentColl
     */
    

    /**
     * @param departmentColl the departmentColl to set
     */
   
}