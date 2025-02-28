package com.web.jxp.assessmentquestion;

import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class AssessmentQuestionForm extends ActionForm {

    private int assessmentquestionId;
    private int status;
    private String search;
    private String doCancel;
    private String doModify;
    private String name;
    private String doAdd;
    private String doSave;
    private String doView;
    private Collection assessmentAnswerTypes;
    private Collection assessmentParameters;
    private int assessmentAnswerTypeIdIndex;
    private int assessmentParameterIdIndex;
    private int assessmentAnswerTypeId;
    private int assessmentParameterId;
    private String link;
    private int ctp;

    
    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }
    /**
     * @return the assessmentquestionId
     */
    public int getAssessmentquestionId() {
        return assessmentquestionId;
    }

    /**
     * @param assessmentquestionId the assessmentquestionId to set
     */
    public void setAssessmentquestionId(int assessmentquestionId) {
        this.assessmentquestionId = assessmentquestionId;
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

    public Collection getAssessmentAnswerTypes() {
        return assessmentAnswerTypes;
    }

    public void setAssessmentAnswerTypes(Collection assessmentAnswerTypes) {
        this.assessmentAnswerTypes = assessmentAnswerTypes;
    }

    public Collection getAssessmentParameters() {
        return assessmentParameters;
    }

    public void setAssessmentParameters(Collection assessmentParameters) {
        this.assessmentParameters = assessmentParameters;
    }

    public int getAssessmentAnswerTypeIdIndex() {
        return assessmentAnswerTypeIdIndex;
    }

    public void setAssessmentAnswerTypeIdIndex(int assessmentAnswerTypeIdIndex) {
        this.assessmentAnswerTypeIdIndex = assessmentAnswerTypeIdIndex;
    }

    public int getAssessmentParameterIdIndex() {
        return assessmentParameterIdIndex;
    }

    public void setAssessmentParameterIdIndex(int assessmentParameterIdIndex) {
        this.assessmentParameterIdIndex = assessmentParameterIdIndex;
    }

    public int getAssessmentAnswerTypeId() {
        return assessmentAnswerTypeId;
    }

    public void setAssessmentAnswerTypeId(int assessmentAnswerTypeId) {
        this.assessmentAnswerTypeId = assessmentAnswerTypeId;
    }

    public int getAssessmentParameterId() {
        return assessmentParameterId;
    }

    public void setAssessmentParameterId(int assessmentParameterId) {
        this.assessmentParameterId = assessmentParameterId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }  
    
}
