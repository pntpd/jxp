package com.web.jxp.position;

import java.util.Collection;

public class PositionInfo {

    private int positionId;

    private int status;
    private int userId;
    private int ddlValue;

    private String ddlLabel;

    private String positiontitle;
    private String grade;
    private String asset;
    private String qualification;
    private String gender;

    private double renumerationmin;
    private double renumerationmax;
    private double genworkexprangemin;
    private double genworkexprangemax;
    private double posworkexprangemin;
    private double posworkexprangemax;

    private String rotation;
    private String hoursofwork;
    private String language;
    private String nationality;
    private String minqualification;
    private String qualificationtype;
    private String description;
    private String position1;
    private String position2;
    private String position3;
    private String currency;

    private int assettypeId;
    private int gradeId;
    private int minqualificationId;
    private int qualificationtypeId;
    private int rotationId;
    private int hoursofworkId;
    private int languageId;
    private int nationalityId;
    private int currencyId;
    private int overstaystart;

//    ==========================================BENEFITS===============================================================
    private int benefitId;
    private int benefittypeId;
    private int benefitquestionId;
    private int positionbenefitId;

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
    public PositionInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public PositionInfo(int ddlValue, int benefittypeId, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.benefittypeId = benefittypeId;
        this.ddlLabel = ddlLabel;
    }

    public PositionInfo(int positionId, String positiontitle, String grade, String asset, String minqualification, String rotation, int status, int userId) {
        this.positionId = positionId;
        this.positiontitle = positiontitle;
        this.grade = grade;
        this.asset = asset;
        this.minqualification = minqualification;
        this.rotation = rotation;
        this.status = status;
        this.userId = userId;
    }

    public PositionInfo(int positionId, String positiontitle, String gender, double renumerationmin, double renumerationmax, double genworkexprangemin,
            double genworkexprangemax, double posworkexprangemin, double posworkexprangemax, String description, String position1, String position2, String position3,
            int assettypeId, int gradeId, int minqualificationId, int qualificationtypeId, int rotationId, int hoursofworkId, int languageId, int nationalityId, int status,
            int userId, int currencyId, int overstaystart) {
        this.positionId = positionId;
        this.positiontitle = positiontitle;
        this.gender = gender;
        this.renumerationmin = renumerationmin;
        this.renumerationmax = renumerationmax;
        this.genworkexprangemin = genworkexprangemin;
        this.genworkexprangemax = genworkexprangemax;
        this.posworkexprangemin = posworkexprangemin;
        this.posworkexprangemax = posworkexprangemax;
        this.description = description;
        this.position1 = position1;
        this.position2 = position2;
        this.position3 = position3;
        this.assettypeId = assettypeId;
        this.gradeId = gradeId;
        this.minqualificationId = minqualificationId;
        this.qualificationtypeId = qualificationtypeId;
        this.rotationId = rotationId;
        this.hoursofworkId = hoursofworkId;
        this.languageId = languageId;
        this.nationalityId = nationalityId;
        this.status = status;
        this.userId = userId;
        this.currencyId = currencyId;
        this.overstaystart = overstaystart;
    }

    public PositionInfo(int positionId, String positiontitle, String gender, double renumerationmin, double renumerationmax, double genworkexprangemin,
            double genworkexprangemax, double posworkexprangemin, double posworkexprangemax, String description, String position1, String position2, String position3,
            int assettypeId, int gradeId, int minqualificationId, int qualificationtypeId, int rotationId, int hoursofworkId, int languageId, int nationalityId, int status,
            int userId, int currencyId, int overstay, int a) {
        this.positionId = positionId;
        this.positiontitle = positiontitle;
        this.gender = gender;
        this.renumerationmin = renumerationmin;
        this.renumerationmax = renumerationmax;
        this.genworkexprangemin = genworkexprangemin;
        this.genworkexprangemax = genworkexprangemax;
        this.posworkexprangemin = posworkexprangemin;
        this.posworkexprangemax = posworkexprangemax;
        this.description = description;
        this.position1 = position1;
        this.position2 = position2;
        this.position3 = position3;
        this.assettypeId = assettypeId;
        this.gradeId = gradeId;
        this.minqualificationId = minqualificationId;
        this.qualificationtypeId = qualificationtypeId;
        this.rotationId = rotationId;
        this.hoursofworkId = hoursofworkId;
        this.languageId = languageId;
        this.nationalityId = nationalityId;
        this.status = status;
        this.userId = userId;
        this.currencyId = currencyId;
        this.overstaystart = overstay;
    }

    public PositionInfo(int positionId, String positiontitle, String grade, String assettype, String rotation, String hoursofwork, String language, String gender,
            String nationality, double renumerationmin, double renumerationmax, String minqualification, String qualificationtype, double genworkexprangemin,
            double genworkexprangemax, double posworkexprangemin, double posworkexprangemax, String description, String position1, String position2,
            String position3, int status, int userId, String currency, int rotationoverstay) {
        this.positionId = positionId;
        this.positiontitle = positiontitle;
        this.grade = grade;
        this.asset = assettype;
        this.rotation = rotation;
        this.hoursofwork = hoursofwork;
        this.language = language;
        this.gender = gender;
        this.nationality = nationality;
        this.renumerationmin = renumerationmin;
        this.renumerationmax = renumerationmax;
        this.minqualification = minqualification;
        this.qualificationtype = qualificationtype;
        this.genworkexprangemin = genworkexprangemin;
        this.genworkexprangemax = genworkexprangemax;
        this.posworkexprangemin = posworkexprangemin;
        this.posworkexprangemax = posworkexprangemax;
        this.description = description;
        this.position1 = position1;
        this.position2 = position2;
        this.position3 = position3;
        this.status = status;
        this.userId = userId;
        this.currency = currency;
        this.overstaystart = rotationoverstay;
    }

    //    ==========================================BENEFITS===============================================================
    public PositionInfo(int positionbenefitId, int positionId, int benefitId, String positionbenefitinfo, String benifitname, String codenum, int status, int userId) {
        this.positionbenefitId = positionbenefitId;
        this.positionId = positionId;
        this.benefitId = benefitId;
        this.positionbenefitinfo = positionbenefitinfo;
        this.benifitname = benifitname;
        this.codenum = codenum;
        this.status = status;
        this.userId = userId;
    }

    public PositionInfo(int benefitId, String benifitname, int benefittypeId, int status, int userId) {
        this.benefitId = benefitId;
        this.benifitname = benifitname;
        this.benefittypeId = benefittypeId;
        this.status = status;
        this.userId = userId;
    }

    public PositionInfo(int benefitId, String benifitname, int benefittypeId, int benefitquestionId, String positionbenefitinfo, String codenum, int status) {
        this.benefitId = benefitId;
        this.benifitname = benifitname;
        this.benefittypeId = benefittypeId;
        this.benefitquestionId = benefitquestionId;
        this.positionbenefitinfo = positionbenefitinfo;
        this.codenum = codenum;
        this.status = status;
    }

    public PositionInfo(int assessmentDetailId, int assessmentId, String assessmentName, int minScore, int passingFlag, String parameter, int status) {
        this.assessmentDetailId = assessmentDetailId;
        this.assessmentId = assessmentId;
        this.assessmentName = assessmentName;
        this.minScore = minScore;
        this.passingFlag = passingFlag;
        this.parameter = parameter;
        this.status = status;
    }

    public PositionInfo(int assessmentDetailId, int assessmentId, int minScore, int passingFlag) {
        this.assessmentDetailId = assessmentDetailId;
        this.assessmentId = assessmentId;
        this.minScore = minScore;
        this.passingFlag = passingFlag;
    }

    public PositionInfo(int assessmentId, int minScore, int passingFlag) {
        this.assessmentId = assessmentId;
        this.minScore = minScore;
        this.passingFlag = passingFlag;
    }

    public PositionInfo(int assessmentDetailId, int assessmentId, int minScore, int passingFlag, int status) {
        this.assessmentDetailId = assessmentDetailId;
        this.assessmentId = assessmentId;
        this.minScore = minScore;
        this.passingFlag = passingFlag;
        this.status = status;
    }

    public int getOverstaystart() {
        return overstaystart;
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
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

    public String getGrade() {
        return grade;
    }

    /**
     * @return the asset
     */
    public String getAsset() {
        return asset;
    }

    /**
     * @return the qualification
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * @return the positiontitle
     */
    public String getPositiontitle() {
        return positiontitle;
    }

    /**
     * @return the assettypeId
     */
    public int getAssettypeId() {
        return assettypeId;
    }

    /**
     * @return the gradeId
     */
    public int getGradeId() {
        return gradeId;
    }

    /**
     * @return the minqualificationId
     */
    public int getMinqualificationId() {
        return minqualificationId;
    }

    /**
     * @return the qualificationtypeId
     */
    public int getQualificationtypeId() {
        return qualificationtypeId;
    }

    /**
     * @return the rotationId
     */
    public int getRotationId() {
        return rotationId;
    }

    /**
     * @return the hoursofworkId
     */
    public int getHoursofworkId() {
        return hoursofworkId;
    }

    /**
     * @return the languageId
     */
    public int getLanguageId() {
        return languageId;
    }

    /**
     * @return the nationalityId
     */
    public int getNationalityId() {
        return nationalityId;
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
     * @return the renumerationmin
     */
    public double getRenumerationmin() {
        return renumerationmin;
    }

    /**
     * @return the renumerationmax
     */
    public double getRenumerationmax() {
        return renumerationmax;
    }

    /**
     * @return the genworkexprangemin
     */
    public double getGenworkexprangemin() {
        return genworkexprangemin;
    }

    /**
     * @return the genworkexprangemax
     */
    public double getGenworkexprangemax() {
        return genworkexprangemax;
    }

    /**
     * @return the posworkexprangemin
     */
    public double getPosworkexprangemin() {
        return posworkexprangemin;
    }

    /**
     * @return the posworkexprangemax
     */
    public double getPosworkexprangemax() {
        return posworkexprangemax;
    }

    /**
     * @return the position1
     */
    public String getPosition1() {
        return position1;
    }

    /**
     * @return the position2
     */
    public String getPosition2() {
        return position2;
    }

    /**
     * @return the position3
     */
    public String getPosition3() {
        return position3;
    }

    /**
     * @return the rotation
     */
    public String getRotation() {
        return rotation;
    }

    /**
     * @return the hoursofwork
     */
    public String getHoursofwork() {
        return hoursofwork;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @return the minqualification
     */
    public String getMinqualification() {
        return minqualification;
    }

    /**
     * @return the qualificationtype
     */
    public String getQualificationtype() {
        return qualificationtype;
    }

    /**
     * @return the benefitId
     */
    public int getBenefitId() {
        return benefitId;
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
     * @return the positionbenefitId
     */
    public int getPositionbenefitId() {
        return positionbenefitId;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return the currencyId
     */
    public int getCurrencyId() {
        return currencyId;
    }
}
