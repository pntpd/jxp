package com.web.jxp.position;

import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.MultipartRequestHandler;

public class PositionForm extends ActionForm {

    private int positionId;
    private int ctp;
    private int status;

    private String search;
    private String doCancel;
    private String doModify;
    private String doAdd;
    private String doSave;
    private String doView;
    private String doBenefitsList;
    private String doAddBenefit;
    private String doSaveBenefit;
    private String doModifyBenefit;
    private String doSavePosBenefit;
    private String doAssessment;
    private String doDeleteBenefit;

    private String grade;
    private String asset;
    private String qualification;
    private String positiontitle;
    private String gender;

    private double renumerationmin;
    private double renumerationmax;
    private double genworkexprangemin;
    private double genworkexprangemax;
    private double posworkexprangemin;
    private double posworkexprangemax;

    private String description;
    private String position1;
    private String position2;
    private String position3;

    private Collection assettype;
    private Collection grades;
    private Collection minqualification;
    private Collection qualificationtype;
    private Collection crewrotation;
    private Collection hoursofwork;
    private Collection language;
    private Collection nationality;
    private Collection currency;
    private int overstaystart;
    //    ==========================================
    private Collection benefittype;
    private int assettypeId;
    private int gradeId;
    private int minqualificationId;
    private int qualificationtypeId;
    private int rotationId;
    private int hoursofworkId;
    private int languageId;
    private int nationalityId;
    private int currencyId;

    //    ==========================================BENEFITS===============================================================
    private int benefitId;
    private int benefittypeId;
    private int positionBenefitId;
    private int statusBenifit;

    private String benefitname;
    private String benefitcode;
    private String positionbenefitinfo;

    private int hiddenbenefittype[];
    private int posbenefitId[];
    private int statusbenefit[];
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

    public int getOverstaystart() {
        return overstaystart;
    }

    public void setOverstaystart(int overstaystart) {
        this.overstaystart = overstaystart;
    }

    public ActionServlet getServlet() {
        return servlet;
    }

    public void setServlet(ActionServlet servlet) {
        this.servlet = servlet;
    }

    public MultipartRequestHandler getMultipartRequestHandler() {
        return multipartRequestHandler;
    }

    public void setMultipartRequestHandler(MultipartRequestHandler multipartRequestHandler) {
        this.multipartRequestHandler = multipartRequestHandler;
    }

    
    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

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
     * @return the positiontitle
     */
    public String getPositiontitle() {
        return positiontitle;
    }

    /**
     * @param positiontitle the positiontitle to set
     */
    public void setPositiontitle(String positiontitle) {
        this.positiontitle = positiontitle;
    }

    /**
     * @return the assettype
     */
    public Collection getAssettype() {
        return assettype;
    }

    /**
     * @param assettype the assettype to set
     */
    public void setAssettype(Collection assettype) {
        this.assettype = assettype;
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
     * @return the minqualificationId
     */
    public int getMinqualificationId() {
        return minqualificationId;
    }

    /**
     * @param minqualificationId the minqualificationId to set
     */
    public void setMinqualificationId(int minqualificationId) {
        this.minqualificationId = minqualificationId;
    }

    /**
     * @return the grades
     */
    public Collection getGrades() {
        return grades;
    }

    /**
     * @param grades the grades to set
     */
    public void setGrades(Collection grades) {
        this.grades = grades;
    }

    /**
     * @return the minqualification
     */
    public Collection getMinqualification() {
        return minqualification;
    }

    /**
     * @param minqualification the minqualification to set
     */
    public void setMinqualification(Collection minqualification) {
        this.minqualification = minqualification;
    }

    /**
     * @return the qualificationtypeId
     */
    public int getQualificationtypeId() {
        return qualificationtypeId;
    }

    /**
     * @param qualificationtypeId the qualificationtypeId to set
     */
    public void setQualificationtypeId(int qualificationtypeId) {
        this.qualificationtypeId = qualificationtypeId;
    }

    /**
     * @return the qualificationtype
     */
    public Collection getQualificationtype() {
        return qualificationtype;
    }

    /**
     * @param qualificationtype the qualificationtype to set
     */
    public void setQualificationtype(Collection qualificationtype) {
        this.qualificationtype = qualificationtype;
    }

    /**
     * @return the rotationId
     */
    public int getRotationId() {
        return rotationId;
    }

    /**
     * @param rotationId the rotationId to set
     */
    public void setRotationId(int rotationId) {
        this.rotationId = rotationId;
    }

    /**
     * @return the hoursofworkId
     */
    public int getHoursofworkId() {
        return hoursofworkId;
    }

    /**
     * @param hoursofworkId the hoursofworkId to set
     */
    public void setHoursofworkId(int hoursofworkId) {
        this.hoursofworkId = hoursofworkId;
    }

    /**
     * @return the crewrotation
     */
    public Collection getCrewrotation() {
        return crewrotation;
    }

    /**
     * @param crewrotation the crewrotation to set
     */
    public void setCrewrotation(Collection crewrotation) {
        this.crewrotation = crewrotation;
    }

    /**
     * @return the hoursofwork
     */
    public Collection getHoursofwork() {
        return hoursofwork;
    }

    /**
     * @param hoursofwork the hoursofwork to set
     */
    public void setHoursofwork(Collection hoursofwork) {
        this.hoursofwork = hoursofwork;
    }

    /**
     * @return the language
     */
    public Collection getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(Collection language) {
        this.language = language;
    }

    /**
     * @return the nationality
     */
    public Collection getNationality() {
        return nationality;
    }

    /**
     * @param nationality the nationality to set
     */
    public void setNationality(Collection nationality) {
        this.nationality = nationality;
    }

    /**
     * @return the languageId
     */
    public int getLanguageId() {
        return languageId;
    }

    /**
     * @param languageId the languageId to set
     */
    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    /**
     * @return the nationalityId
     */
    public int getNationalityId() {
        return nationalityId;
    }

    /**
     * @param nationalityId the nationalityId to set
     */
    public void setNationalityId(int nationalityId) {
        this.nationalityId = nationalityId;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
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
     * @return the renumerationmin
     */
    public double getRenumerationmin() {
        return renumerationmin;
    }

    /**
     * @param renumerationmin the renumerationmin to set
     */
    public void setRenumerationmin(double renumerationmin) {
        this.renumerationmin = renumerationmin;
    }

    /**
     * @return the renumerationmax
     */
    public double getRenumerationmax() {
        return renumerationmax;
    }

    /**
     * @param renumerationmax the renumerationmax to set
     */
    public void setRenumerationmax(double renumerationmax) {
        this.renumerationmax = renumerationmax;
    }

    /**
     * @return the genworkexprangemin
     */
    public double getGenworkexprangemin() {
        return genworkexprangemin;
    }

    /**
     * @param genworkexprangemin the genworkexprangemin to set
     */
    public void setGenworkexprangemin(double genworkexprangemin) {
        this.genworkexprangemin = genworkexprangemin;
    }

    /**
     * @return the genworkexprangemax
     */
    public double getGenworkexprangemax() {
        return genworkexprangemax;
    }

    /**
     * @param genworkexprangemax the genworkexprangemax to set
     */
    public void setGenworkexprangemax(double genworkexprangemax) {
        this.genworkexprangemax = genworkexprangemax;
    }

    /**
     * @return the posworkexprangemin
     */
    public double getPosworkexprangemin() {
        return posworkexprangemin;
    }

    /**
     * @param posworkexprangemin the posworkexprangemin to set
     */
    public void setPosworkexprangemin(double posworkexprangemin) {
        this.posworkexprangemin = posworkexprangemin;
    }

    /**
     * @return the posworkexprangemax
     */
    public double getPosworkexprangemax() {
        return posworkexprangemax;
    }

    /**
     * @param posworkexprangemax the posworkexprangemax to set
     */
    public void setPosworkexprangemax(double posworkexprangemax) {
        this.posworkexprangemax = posworkexprangemax;
    }

    /**
     * @return the position1
     */
    public String getPosition1() {
        return position1;
    }

    /**
     * @param position1 the position1 to set
     */
    public void setPosition1(String position1) {
        this.position1 = position1;
    }

    /**
     * @return the position2
     */
    public String getPosition2() {
        return position2;
    }

    /**
     * @param position2 the position2 to set
     */
    public void setPosition2(String position2) {
        this.position2 = position2;
    }

    /**
     * @return the position3
     */
    public String getPosition3() {
        return position3;
    }

    /**
     * @param position3 the position3 to set
     */
    public void setPosition3(String position3) {
        this.position3 = position3;
    }

    /**
     * @return the doAssessment
     */
    public String getDoAssessment() {
        return doAssessment;
    }

    /**
     * @param doAssessment the doAssessment to set
     */
    public void setDoAssessment(String doAssessment) {
        this.doAssessment = doAssessment;
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
     * @return the doDeleteBenefit
     */
    public String getDoDeleteBenefit() {
        return doDeleteBenefit;
    }

    /**
     * @param doDeleteBenefit the doDeleteBenefit to set
     */
    public void setDoDeleteBenefit(String doDeleteBenefit) {
        this.doDeleteBenefit = doDeleteBenefit;
    }

    /**
     * @return the statusbenefit
     */
    public int[] getStatusbenefit() {
        return statusbenefit;
    }

    /**
     * @param statusbenefit the statusbenefit to set
     */
    public void setStatusbenefit(int[] statusbenefit) {
        this.statusbenefit = statusbenefit;
    }

    /**
     * @return the positionBenefitId
     */
    public int getPositionBenefitId() {
        return positionBenefitId;
    }

    /**
     * @param positionBenefitId the positionBenefitId to set
     */
    public void setPositionBenefitId(int positionBenefitId) {
        this.positionBenefitId = positionBenefitId;
    }

    /**
     * @return the statusBenifit
     */
    public int getStatusBenifit() {
        return statusBenifit;
    }

    /**
     * @param statusBenifit the statusBenifit to set
     */
    public void setStatusBenifit(int statusBenifit) {
        this.statusBenifit = statusBenifit;
    }

    /**
     * @return the currency
     */
    public Collection getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(Collection currency) {
        this.currency = currency;
    }

    /**
     * @return the currencyId
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * @param currencyId the currencyId to set
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

}
