package com.web.jxp.assessment;
import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class AssessmentForm extends ActionForm
{
    private int assessmentId;
    private int status;
    private String search;
    private String doCancel;
    private String doModify;
    private String name;
    private String quesType;
    private String paraName;
    private String doAdd;
    private String doSave;
    private String doView;
    private int assessmentparameterId;
    private Collection assessmentparameters;
    private int assessmentparameterIndex;
    private Collection questiontypes;
    private int questiontypeIndex;
    private int questiontypeId;
    private String mode;
    private String assessmentcode;
    private String []  assparaameter;
    private String parameterids;
    
    private int checkedassessmentparameterId;
    private Collection checkedassessmentparameters;
    
    private int checkedassessmentparameterId_model;
    
    private Collection assessmentanswers;
    private int assessmentanswerId;
    private int ctp;

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    public Collection getAssessmentanswers() {
        return assessmentanswers;
    }

    public void setAssessmentanswers(Collection assessmentanswers) {
        this.assessmentanswers = assessmentanswers;
    }

    public int getAssessmentanswerId() {
        return assessmentanswerId;
    }

    public void setAssessmentanswerId(int assessmentanswerId) {
        this.assessmentanswerId = assessmentanswerId;
    }

    public int getCheckedassessmentparameterId_model() {
        return checkedassessmentparameterId_model;
    }

    public void setCheckedassessmentparameterId_model(int checkedassessmentparameterId_model) {
        this.checkedassessmentparameterId_model = checkedassessmentparameterId_model;
    }

    public int getCheckedassessmentparameterId() {
        return checkedassessmentparameterId;
    }

    public void setCheckedassessmentparameterId(int checkedassessmentparameterId) {
        this.checkedassessmentparameterId = checkedassessmentparameterId;
    }

    public Collection getCheckedassessmentparameters() {
        return checkedassessmentparameters;
    }

    public void setCheckedassessmentparameters(Collection checkedassessmentparameters) {
        this.checkedassessmentparameters = checkedassessmentparameters;
    }
    
   public String getParameterids() {
        return parameterids;
    }

    public void setParameterids(String parameterids) {
        this.parameterids = parameterids;
    }
    
    

    public String[] getAssparaameter() {
        return assparaameter;
    }

    public void setAssparaameter(String[] assparaameter) {
        this.assparaameter = assparaameter;
    }

    public String getAssessmentcode() {
        return assessmentcode;
    }

    public void setAssessmentcode(String assessmentcode) {
        this.assessmentcode = assessmentcode;
    }
    
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    
    

    public Collection getQuestiontypes() {
        return questiontypes;
    }

    public void setQuestiontypes(Collection questiontypes) {
        this.questiontypes = questiontypes;
    }

    public int getQuestiontypeIndex() {
        return questiontypeIndex;
    }

    public void setQuestiontypeIndex(int questiontypeIndex) {
        this.questiontypeIndex = questiontypeIndex;
    }

    public int getQuestiontypeId() {
        return questiontypeId;
    }

    public void setQuestiontypeId(int questiontypeId) {
        this.questiontypeId = questiontypeId;
    }
    
            
            
            
    public int getAssessmentparameterIndex() {
        return assessmentparameterIndex;
    }

    public void setAssessmentparameterIndex(int assessmentparameterIndex) {
        this.assessmentparameterIndex = assessmentparameterIndex;
    }
    
    

    public int getAssessmentparameterId() {
        return assessmentparameterId;
    }

    public void setAssessmentparameterId(int assessmentparameterId) {
        this.assessmentparameterId = assessmentparameterId;
    }

    public Collection getAssessmentparameters() {
        return assessmentparameters;
    }

    public void setAssessmentparameters(Collection assessmentparameters) {
        this.assessmentparameters = assessmentparameters;
    }


       public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
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

    public String getQuesType() {
        return quesType;
    }

    public void setQuesType(String quesType) {
        this.quesType = quesType;
    }

    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
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
}