package com.web.jxp.jobpost;

import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class JobPostForm extends ActionForm {

    private int jobpostId;
    private int ctp;
    private int status;

    private String search;
    private String doCancel;
    private String doModify;
    private String doAdd;
    private String doSave;
    private String doView;
    private String doClose;
    private String doBenefitsList;
    private String doAddBenefit;
    private String doSaveBenefit;
    private String doModifyBenefit;
    private String doSavePosBenefit;
    private String doDeleteJobPostBenefitDetail;

    private Collection client;
    private Collection clientasset;
    private Collection position;
    private Collection grade;
    private Collection country;
    private Collection education;
    private Collection currency;
    //    ==========================================
    private Collection benefittype;

    private String nationality[];
    private String language[];
    private String gender[];
    private String positionname;
    private String description;
    private String targetmobdate;
    private String vacancypostedby;
    private String additionalnote;
    private String cityname;

    private double experiencemin;
    private double experiencemax;
    private double dayratevalue;
    private double remunerationmin;
    private double remunerationmax;
    private double workhour;

    private int statusIndex;
    private int clientId;
    private int clientassetId;
    private int positionId;
    private int gradeId;
    private int countryId;
    private int cityId;
    private int educationtypeId;
    private int noofopening;
    private int tenure;
    private int currencyId;

    //    ==========================================BENEFITS===============================================================
    private int benefitId;
    private int benefittypeId;
    private int jobpostBenefitId;

    private String benefitname;
    private String benefitcode;
    private String positionbenefitinfo;

    private int hiddenbenefittype[];
    private int posbenefitId[];
    private int benefetquestionIdHidden[];
    private String posdescription[];

    //    ==========================================ASSESSMENT===============================================================
    private Collection assessments;

    private int assessmentDetailId;
    private int assessmentId, passingFlag, minScore;

    private String doViewAssessmentList;
    private String doDeleteAssessmentDetail;
    private String doModifyAssessmentDetail;
    private String doSaveAssessmentDetail;

    
    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }
    /**
     * @return the jobpostId
     */
    public int getJobpostId() {
        return jobpostId;
    }

    /**
     * @param jobpostId the jobpostId to set
     */
    public void setJobpostId(int jobpostId) {
        this.jobpostId = jobpostId;
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
     * @return the doBenefitsList
     */
    public String getDoBenefitsList() {
        return doBenefitsList;
    }

    /**
     * @param doBenefitsList the doBenefitsList to set
     */
    public void setDoBenefitsList(String doBenefitsList) {
        this.doBenefitsList = doBenefitsList;
    }

    /**
     * @return the doAddBenefit
     */
    public String getDoAddBenefit() {
        return doAddBenefit;
    }

    /**
     * @param doAddBenefit the doAddBenefit to set
     */
    public void setDoAddBenefit(String doAddBenefit) {
        this.doAddBenefit = doAddBenefit;
    }

    /**
     * @return the doSaveBenefit
     */
    public String getDoSaveBenefit() {
        return doSaveBenefit;
    }

    /**
     * @param doSaveBenefit the doSaveBenefit to set
     */
    public void setDoSaveBenefit(String doSaveBenefit) {
        this.doSaveBenefit = doSaveBenefit;
    }

    /**
     * @return the doModifyBenefit
     */
    public String getDoModifyBenefit() {
        return doModifyBenefit;
    }

    /**
     * @param doModifyBenefit the doModifyBenefit to set
     */
    public void setDoModifyBenefit(String doModifyBenefit) {
        this.doModifyBenefit = doModifyBenefit;
    }

    /**
     * @return the doSavePosBenefit
     */
    public String getDoSavePosBenefit() {
        return doSavePosBenefit;
    }

    /**
     * @param doSavePosBenefit the doSavePosBenefit to set
     */
    public void setDoSavePosBenefit(String doSavePosBenefit) {
        this.doSavePosBenefit = doSavePosBenefit;
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
     * @return the targetmobdate
     */
    public String getTargetmobdate() {
        return targetmobdate;
    }

    /**
     * @param targetmobdate the targetmobdate to set
     */
    public void setTargetmobdate(String targetmobdate) {
        this.targetmobdate = targetmobdate;
    }

    /**
     * @return the vacancypostedby
     */
    public String getVacancypostedby() {
        return vacancypostedby;
    }

    /**
     * @param vacancypostedby the vacancypostedby to set
     */
    public void setVacancypostedby(String vacancypostedby) {
        this.vacancypostedby = vacancypostedby;
    }

    /**
     * @return the additionalnote
     */
    public String getAdditionalnote() {
        return additionalnote;
    }

    /**
     * @param additionalnote the additionalnote to set
     */
    public void setAdditionalnote(String additionalnote) {
        this.additionalnote = additionalnote;
    }

    /**
     * @return the experiencemin
     */
    public double getExperiencemin() {
        return experiencemin;
    }

    /**
     * @param experiencemin the experiencemin to set
     */
    public void setExperiencemin(double experiencemin) {
        this.experiencemin = experiencemin;
    }

    /**
     * @return the experiencemax
     */
    public double getExperiencemax() {
        return experiencemax;
    }

    /**
     * @param experiencemax the experiencemax to set
     */
    public void setExperiencemax(double experiencemax) {
        this.experiencemax = experiencemax;
    }

    /**
     * @return the dayratevalue
     */
    public double getDayratevalue() {
        return dayratevalue;
    }

    /**
     * @param dayratevalue the dayratevalue to set
     */
    public void setDayratevalue(double dayratevalue) {
        this.dayratevalue = dayratevalue;
    }

    /**
     * @return the remunerationmin
     */
    public double getRemunerationmin() {
        return remunerationmin;
    }

    /**
     * @param remunerationmin the remunerationmin to set
     */
    public void setRemunerationmin(double remunerationmin) {
        this.remunerationmin = remunerationmin;
    }

    /**
     * @return the remunerationmax
     */
    public double getRemunerationmax() {
        return remunerationmax;
    }

    /**
     * @param remunerationmax the remunerationmax to set
     */
    public void setRemunerationmax(double remunerationmax) {
        this.remunerationmax = remunerationmax;
    }

    /**
     * @return the workhour
     */
    public double getWorkhour() {
        return workhour;
    }

    /**
     * @param workhour the workhour to set
     */
    public void setWorkhour(double workhour) {
        this.workhour = workhour;
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
     * @return the clientassetId
     */
    public int getClientassetId() {
        return clientassetId;
    }

    /**
     * @param clientassetId the clientassetId to set
     */
    public void setClientassetId(int clientassetId) {
        this.clientassetId = clientassetId;
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
     * @return the gradeId
     */
    public int getGradeId() {
        return gradeId;
    }

    /**
     * @param gradeId the gradeId to set
     */
    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    /**
     * @return the countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the noofopening
     */
    public int getNoofopening() {
        return noofopening;
    }

    /**
     * @param noofopening the noofopening to set
     */
    public void setNoofopening(int noofopening) {
        this.noofopening = noofopening;
    }

    /**
     * @return the tenure
     */
    public int getTenure() {
        return tenure;
    }

    /**
     * @param tenure the tenure to set
     */
    public void setTenure(int tenure) {
        this.tenure = tenure;
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
     * @return the doViewAssessmentList
     */
    public String getDoViewAssessmentList() {
        return doViewAssessmentList;
    }

    /**
     * @param doViewAssessmentList the doViewAssessmentList to set
     */
    public void setDoViewAssessmentList(String doViewAssessmentList) {
        this.doViewAssessmentList = doViewAssessmentList;
    }

    /**
     * @return the client
     */
    public Collection getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Collection client) {
        this.client = client;
    }

    /**
     * @return the clientasset
     */
    public Collection getClientasset() {
        return clientasset;
    }

    /**
     * @param clientasset the clientasset to set
     */
    public void setClientasset(Collection clientasset) {
        this.clientasset = clientasset;
    }

    /**
     * @return the position
     */
    public Collection getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Collection position) {
        this.position = position;
    }

    /**
     * @return the grade
     */
    public Collection getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(Collection grade) {
        this.grade = grade;
    }

    /**
     * @return the educationtypeId
     */
    public int getEducationtypeId() {
        return educationtypeId;
    }

    /**
     * @param educationtypeId the educationtypeId to set
     */
    public void setEducationtypeId(int educationtypeId) {
        this.educationtypeId = educationtypeId;
    }

    /**
     * @return the country
     */
    public Collection getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(Collection country) {
        this.country = country;
    }

    /**
     * @return the education
     */
    public Collection getEducation() {
        return education;
    }

    /**
     * @param education the education to set
     */
    public void setEducation(Collection education) {
        this.education = education;
    }

    /**
     * @return the cityId
     */
    public int getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the cityname
     */
    public String getCityname() {
        return cityname;
    }

    /**
     * @param cityname the cityname to set
     */
    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    /**
     * @return the positionname
     */
    public String getPositionname() {
        return positionname;
    }

    /**
     * @param positionname the positionname to set
     */
    public void setPositionname(String positionname) {
        this.positionname = positionname;
    }

    /**
     * @return the nationality
     */
    public String[] getNationality() {
        return nationality;
    }

    /**
     * @param nationality the nationality to set
     */
    public void setNationality(String[] nationality) {
        this.nationality = nationality;
    }

    /**
     * @return the language
     */
    public String[] getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String[] language) {
        this.language = language;
    }

    /**
     * @return the gender
     */
    public String[] getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String[] gender) {
        this.gender = gender;
    }

    /**
     * @return the doDeleteAssessmentDetail
     */
    public String getDoDeleteAssessmentDetail() {
        return doDeleteAssessmentDetail;
    }

    /**
     * @param doDeleteAssessmentDetail the doDeleteAssessmentDetail to set
     */
    public void setDoDeleteAssessmentDetail(String doDeleteAssessmentDetail) {
        this.doDeleteAssessmentDetail = doDeleteAssessmentDetail;
    }

    /**
     * @return the doModifyAssessmentDetail
     */
    public String getDoModifyAssessmentDetail() {
        return doModifyAssessmentDetail;
    }

    /**
     * @param doModifyAssessmentDetail the doModifyAssessmentDetail to set
     */
    public void setDoModifyAssessmentDetail(String doModifyAssessmentDetail) {
        this.doModifyAssessmentDetail = doModifyAssessmentDetail;
    }

    /**
     * @return the doSaveAssessmentDetail
     */
    public String getDoSaveAssessmentDetail() {
        return doSaveAssessmentDetail;
    }

    /**
     * @param doSaveAssessmentDetail the doSaveAssessmentDetail to set
     */
    public void setDoSaveAssessmentDetail(String doSaveAssessmentDetail) {
        this.doSaveAssessmentDetail = doSaveAssessmentDetail;
    }

    /**
     * @return the benefitId
     */
    public int getBenefitId() {
        return benefitId;
    }

    /**
     * @param benefitId the benefitId to set
     */
    public void setBenefitId(int benefitId) {
        this.benefitId = benefitId;
    }

    /**
     * @return the benefittypeId
     */
    public int getBenefittypeId() {
        return benefittypeId;
    }

    /**
     * @param benefittypeId the benefittypeId to set
     */
    public void setBenefittypeId(int benefittypeId) {
        this.benefittypeId = benefittypeId;
    }

    /**
     * @return the benefitname
     */
    public String getBenefitname() {
        return benefitname;
    }

    /**
     * @param benefitname the benefitname to set
     */
    public void setBenefitname(String benefitname) {
        this.benefitname = benefitname;
    }

    /**
     * @return the benefitcode
     */
    public String getBenefitcode() {
        return benefitcode;
    }

    /**
     * @param benefitcode the benefitcode to set
     */
    public void setBenefitcode(String benefitcode) {
        this.benefitcode = benefitcode;
    }

    /**
     * @return the positionbenefitinfo
     */
    public String getPositionbenefitinfo() {
        return positionbenefitinfo;
    }

    /**
     * @param positionbenefitinfo the positionbenefitinfo to set
     */
    public void setPositionbenefitinfo(String positionbenefitinfo) {
        this.positionbenefitinfo = positionbenefitinfo;
    }

    /**
     * @return the hiddenbenefittype
     */
    public int[] getHiddenbenefittype() {
        return hiddenbenefittype;
    }

    /**
     * @param hiddenbenefittype the hiddenbenefittype to set
     */
    public void setHiddenbenefittype(int[] hiddenbenefittype) {
        this.hiddenbenefittype = hiddenbenefittype;
    }

    /**
     * @return the posbenefitId
     */
    public int[] getPosbenefitId() {
        return posbenefitId;
    }

    /**
     * @param posbenefitId the posbenefitId to set
     */
    public void setPosbenefitId(int[] posbenefitId) {
        this.posbenefitId = posbenefitId;
    }

    /**
     * @return the benefetquestionIdHidden
     */
    public int[] getBenefetquestionIdHidden() {
        return benefetquestionIdHidden;
    }

    /**
     * @param benefetquestionIdHidden the benefetquestionIdHidden to set
     */
    public void setBenefetquestionIdHidden(int[] benefetquestionIdHidden) {
        this.benefetquestionIdHidden = benefetquestionIdHidden;
    }

    /**
     * @return the posdescription
     */
    public String[] getPosdescription() {
        return posdescription;
    }

    /**
     * @param posdescription the posdescription to set
     */
    public void setPosdescription(String[] posdescription) {
        this.posdescription = posdescription;
    }

    /**
     * @return the assessments
     */
    public Collection getAssessments() {
        return assessments;
    }

    /**
     * @param assessments the assessments to set
     */
    public void setAssessments(Collection assessments) {
        this.assessments = assessments;
    }

    /**
     * @return the assessmentDetailId
     */
    public int getAssessmentDetailId() {
        return assessmentDetailId;
    }

    /**
     * @param assessmentDetailId the assessmentDetailId to set
     */
    public void setAssessmentDetailId(int assessmentDetailId) {
        this.assessmentDetailId = assessmentDetailId;
    }

    /**
     * @return the assessmentId
     */
    public int getAssessmentId() {
        return assessmentId;
    }

    /**
     * @param assessmentId the assessmentId to set
     */
    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    /**
     * @return the passingFlag
     */
    public int getPassingFlag() {
        return passingFlag;
    }

    /**
     * @param passingFlag the passingFlag to set
     */
    public void setPassingFlag(int passingFlag) {
        this.passingFlag = passingFlag;
    }

    /**
     * @return the minScore
     */
    public int getMinScore() {
        return minScore;
    }

    /**
     * @param minScore the minScore to set
     */
    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    /**
     * @return the benefittype
     */
    public Collection getBenefittype() {
        return benefittype;
    }

    /**
     * @param benefittype the benefittype to set
     */
    public void setBenefittype(Collection benefittype) {
        this.benefittype = benefittype;
    }

    /**
     * @return the doDeleteJobPostBenefitDetail
     */
    public String getDoDeleteJobPostBenefitDetail() {
        return doDeleteJobPostBenefitDetail;
    }

    /**
     * @param doDeleteJobPostBenefitDetail the doDeleteJobPostBenefitDetail to set
     */
    public void setDoDeleteJobPostBenefitDetail(String doDeleteJobPostBenefitDetail) {
        this.doDeleteJobPostBenefitDetail = doDeleteJobPostBenefitDetail;
    }
    
    /**
     * @return the jobpostBenefitId
     */
    public int getJobpostBenefitId() {
        return jobpostBenefitId;
    }

    /**
     * @param jobpostBenefitId the jobpostBenefitId to set
     */
    public void setJobpostBenefitId(int jobpostBenefitId) {
        this.jobpostBenefitId = jobpostBenefitId;
    }

    public Collection getCurrency() {
        return currency;
    }

    public void setCurrency(Collection currency) {
        this.currency = currency;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * @return the doClose
     */
    public String getDoClose() {
        return doClose;
    }

    /**
     * @param doClose the doClose to set
     */
    public void setDoClose(String doClose) {
        this.doClose = doClose;
    }

}
