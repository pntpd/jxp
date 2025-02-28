package com.web.jxp.jobpost;

import java.util.Collection;

public class JobPostInfo {

    private int jobpostId;

    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;

    private String refno;
    private String clientname;
    private String assetname;
    private String positionname;
    private String grade;
    private String countryname;
    private String cityname;
    private String educationlevel;
    private String opening;
    private String poston;

    private String nationality;
    private String language;
    private String gender;
    private String description;
    private String targetmobdate;
    private String vacancypostedby;
    private String additionalnote;
    private String currency;

    private double experiencemin;
    private double experiencemax;
    private double dayratevalue;
    private double remunerationmin;
    private double remunerationmax;
    private double workhour;

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
    private int benefitquestionId;
    private int jobpostbenefitId;

    private String positionbenefitinfo;
    private String benifitname;
    private String codenum;

    //    ==========================================ASSESSMENT===============================================================
    private Collection assessments;

    private int assessmentId;
    private int minScore, passingFlag, assessmentDetailId;

    private String assessmentName;
    private String parameter;

    //for ddl
    public JobPostInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public JobPostInfo(int ddlValue, int benefittypeId, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.benefittypeId = benefittypeId;
        this.ddlLabel = ddlLabel;
    }

    public JobPostInfo(int jobpostId, String refno, String clientname, String positionname, String grade, String targetmobdate,
            String opening, String poston, int status, int userId) {
        this.jobpostId = jobpostId;
        this.refno = refno;
        this.clientname = clientname;
        this.positionname = positionname;
        this.grade = grade;
        this.targetmobdate = targetmobdate;
        this.opening = opening;
        this.poston = poston;
        this.status = status;
        this.userId = userId;
    }

    public JobPostInfo(int jobpostId, int clientId, int clientassetId, int positionId, int gradeId, int countryId, int cityId,
            double experiencemin, double experiencemax, String nationality, String language, String gender, int educationtypeId,
            String description, int noofopening, String targetmobdate, int tenure, int currencyId, double dayratevalue,
            double remunerationmin, double remunerationmax, double workhour, String vacancypostedby, String additionalnote,
            int status, int userId, String positionname, String cityname) {
        this.jobpostId = jobpostId;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.positionId = positionId;
        this.gradeId = gradeId;
        this.countryId = countryId;
        this.cityId = cityId;
        this.experiencemin = experiencemin;
        this.experiencemax = experiencemax;
        this.nationality = nationality;
        this.language = language;
        this.gender = gender;
        this.educationtypeId = educationtypeId;
        this.description = description;
        this.noofopening = noofopening;
        this.targetmobdate = targetmobdate;
        this.tenure = tenure;
        this.currencyId = currencyId;
        this.dayratevalue = dayratevalue;
        this.remunerationmin = remunerationmin;
        this.remunerationmax = remunerationmax;
        this.workhour = workhour;
        this.vacancypostedby = vacancypostedby;
        this.additionalnote = additionalnote;
        this.status = status;
        this.userId = userId;
        this.positionname = positionname;
        this.cityname = cityname;
    }

    public JobPostInfo(int jobpostId, String clientname, String assetname, String positionname, String grade, String countryname,
            String cityname, double experiencemin, double experiencemax, String nationality, String language, String gender,
            String educationlevel, String description, int noofopening, String targetmobdate, int tenure, String currency,
            double dayratevalue, double remunerationmin, double remunerationmax, double workhour, String vacancypostedby,
            String additionalnote, int status, int userId) {
        this.jobpostId = jobpostId;
        this.clientname = clientname;
        this.assetname = assetname;
        this.positionname = positionname;
        this.grade = grade;
        this.countryname = countryname;
        this.cityname = cityname;
        this.experiencemin = experiencemin;
        this.experiencemax = experiencemax;
        this.nationality = nationality;
        this.language = language;
        this.gender = gender;
        this.educationlevel = educationlevel;
        this.description = description;
        this.noofopening = noofopening;
        this.targetmobdate = targetmobdate;
        this.tenure = tenure;
        this.currency = currency;
        this.dayratevalue = dayratevalue;
        this.remunerationmin = remunerationmin;
        this.remunerationmax = remunerationmax;
        this.workhour = workhour;
        this.vacancypostedby = vacancypostedby;
        this.additionalnote = additionalnote;
        this.status = status;
        this.userId = userId;
    }

    //    ==========================================BENEFITS===============================================================
    public JobPostInfo(int jobpostId, int benefitId, String positionbenefitinfo, String benifitname, String codenum, int status, int userId, int jobpostbenefitId) {
        this.jobpostId = jobpostId;
        this.benefitId = benefitId;
        this.positionbenefitinfo = positionbenefitinfo;
        this.benifitname = benifitname;
        this.codenum = codenum;
        this.status = status;
        this.userId = userId;
        this.jobpostbenefitId = jobpostbenefitId;
    }

    public JobPostInfo(int benefitId, String benifitname, int benefittypeId, int status, int userId) {
        this.benefitId = benefitId;
        this.benifitname = benifitname;
        this.benefittypeId = benefittypeId;
        this.status = status;
        this.userId = userId;
    }

    public JobPostInfo(int benefitId, String benifitname, int benefittypeId, int benefitquestionId, String positionbenefitinfo, String codenum) {
        this.benefitId = benefitId;
        this.benifitname = benifitname;
        this.benefittypeId = benefittypeId;
        this.benefitquestionId = benefitquestionId;
        this.positionbenefitinfo = positionbenefitinfo;
        this.codenum = codenum;
    }
    //    ==========================================ASSESSMENT===============================================================

    public JobPostInfo(int assessmentDetailId, int assessmentId, String assessmentName, int minScore, int passingFlag, String parameter, int status) {
        this.assessmentDetailId = assessmentDetailId;
        this.assessmentId = assessmentId;
        this.assessmentName = assessmentName;
        this.minScore = minScore;
        this.passingFlag = passingFlag;
        this.parameter = parameter;
        this.status = status;
    }

    public JobPostInfo(int assessmentDetailId, int assessmentId, int minScore, int passingFlag) {
        this.assessmentDetailId = assessmentDetailId;
        this.assessmentId = assessmentId;
        this.minScore = minScore;
        this.passingFlag = passingFlag;
    }

    public JobPostInfo(int assessmentId, int minScore, int passingFlag) {
        this.assessmentId = assessmentId;
        this.minScore = minScore;
        this.passingFlag = passingFlag;
    }

    public JobPostInfo(int assessmentDetailId, int assessmentId, int minScore, int passingFlag, int status) {
        this.assessmentDetailId = assessmentDetailId;
        this.assessmentId = assessmentId;
        this.minScore = minScore;
        this.passingFlag = passingFlag;
        this.status = status;
    }

    /**
     * @return the jobpostId
     */
    public int getJobpostId() {
        return jobpostId;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the ddlValue
     */
    public int getDdlValue() {
        return ddlValue;
    }

    /**
     * @return the ddlLabel
     */
    public String getDdlLabel() {
        return ddlLabel;
    }

    /**
     * @return the refno
     */
    public String getRefno() {
        return refno;
    }

    /**
     * @return the clientname
     */
    public String getClientname() {
        return clientname;
    }

    /**
     * @return the grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * @return the opening
     */
    public String getOpening() {
        return opening;
    }

    /**
     * @return the poston
     */
    public String getPoston() {
        return poston;
    }

    /**
     * @return the positionname
     */
    public String getPositionname() {
        return positionname;
    }

    /**
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the targetmobdate
     */
    public String getTargetmobdate() {
        return targetmobdate;
    }

    /**
     * @return the vacancypostedby
     */
    public String getVacancypostedby() {
        return vacancypostedby;
    }

    /**
     * @return the additionalnote
     */
    public String getAdditionalnote() {
        return additionalnote;
    }

    /**
     * @return the experiencemin
     */
    public double getExperiencemin() {
        return experiencemin;
    }

    /**
     * @return the experiencemax
     */
    public double getExperiencemax() {
        return experiencemax;
    }

    /**
     * @return the dayratevalue
     */
    public double getDayratevalue() {
        return dayratevalue;
    }

    /**
     * @return the remunerationmin
     */
    public double getRemunerationmin() {
        return remunerationmin;
    }

    /**
     * @return the remunerationmax
     */
    public double getRemunerationmax() {
        return remunerationmax;
    }

    /**
     * @return the workhour
     */
    public double getWorkhour() {
        return workhour;
    }

    /**
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @return the clientassetId
     */
    public int getClientassetId() {
        return clientassetId;
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
    }

    /**
     * @return the gradeId
     */
    public int getGradeId() {
        return gradeId;
    }

    /**
     * @return the countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @return the cityId
     */
    public int getCityId() {
        return cityId;
    }

    /**
     * @return the noofopening
     */
    public int getNoofopening() {
        return noofopening;
    }

    /**
     * @return the tenure
     */
    public int getTenure() {
        return tenure;
    }
    
    /**
     * @return the educationtypeId
     */
    public int getEducationtypeId() {
        return educationtypeId;
    }

    /**
     * @return the assetname
     */
    public String getAssetname() {
        return assetname;
    }

    /**
     * @return the countryname
     */
    public String getCountryname() {
        return countryname;
    }

    /**
     * @return the cityname
     */
    public String getCityname() {
        return cityname;
    }

    /**
     * @return the educationlevel
     */
    public String getEducationlevel() {
        return educationlevel;
    }

    /**
     * @return the benefitId
     */
    public int getBenefitId() {
        return benefitId;
    }

    /**
     * @return the benefittypeId
     */
    public int getBenefittypeId() {
        return benefittypeId;
    }

    /**
     * @return the benefitquestionId
     */
    public int getBenefitquestionId() {
        return benefitquestionId;
    }

    /**
     * @return the positionbenefitinfo
     */
    public String getPositionbenefitinfo() {
        return positionbenefitinfo;
    }

    /**
     * @return the benifitname
     */
    public String getBenifitname() {
        return benifitname;
    }

    /**
     * @return the codenum
     */
    public String getCodenum() {
        return codenum;
    }

    /**
     * @return the assessments
     */
    public Collection getAssessments() {
        return assessments;
    }

    /**
     * @return the assessmentId
     */
    public int getAssessmentId() {
        return assessmentId;
    }

    /**
     * @return the minScore
     */
    public int getMinScore() {
        return minScore;
    }

    /**
     * @return the passingFlag
     */
    public int getPassingFlag() {
        return passingFlag;
    }

    /**
     * @return the assessmentDetailId
     */
    public int getAssessmentDetailId() {
        return assessmentDetailId;
    }

    /**
     * @return the assessmentName
     */
    public String getAssessmentName() {
        return assessmentName;
    }

    /**
     * @return the parameter
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * @return the jobpostbenefitId
     */
    public int getJobpostbenefitId() {
        return jobpostbenefitId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

}
