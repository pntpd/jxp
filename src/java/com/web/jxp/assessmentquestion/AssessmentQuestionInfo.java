package com.web.jxp.assessmentquestion;

public class AssessmentQuestionInfo {

    private int assessmentquestionId;
    private String name;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;
    private int assessmentParameterId;
    private String assessmentParameterName;
    private int assessmentAnswerTypeId;
    private String assessmentAnswerTypeName;
    private String link;

    public AssessmentQuestionInfo(int assessmentquestionId, String name, int assessmentAnswerTypeId, int assessmentParameterId, String fileName, int status, int uId) {
        this.assessmentquestionId = assessmentquestionId;
        this.name = name;
        this.assessmentAnswerTypeId = assessmentAnswerTypeId;
        this.assessmentParameterId = assessmentParameterId;
        this.link = fileName;
        this.status = status;
        this.userId = uId;
    }

    public AssessmentQuestionInfo(int assessmentquestionId, String name, String assessmentAnswerTypeName, String assessmentParameterName, int status) {
        this.assessmentquestionId = assessmentquestionId;
        this.name = name;
        this.assessmentAnswerTypeName = assessmentAnswerTypeName;
        this.assessmentParameterName = assessmentParameterName;
        this.status = status;
    }

    public AssessmentQuestionInfo(String name, String assessmentAnswerTypeName, String assessmentParameterName, String link, int status) {
        this.name = name;
        this.assessmentAnswerTypeName = assessmentAnswerTypeName;
        this.assessmentParameterName = assessmentParameterName;
        this.link = link;
        this.status = status;
    }

    public int getAssessmentParameterId() {
        return assessmentParameterId;
    }
    
    public String getAssessmentParameterName() {
        return assessmentParameterName;
    }

    //for ddl
    public AssessmentQuestionInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public AssessmentQuestionInfo(int assessmentQuestionId, String name, int status, int userId) {
        this.assessmentquestionId = assessmentQuestionId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    public AssessmentQuestionInfo(int verificationsubtypeId, String verificationsubtypename, int Status, String verificationtypename) {

        this.assessmentquestionId = verificationsubtypeId;
        this.name = verificationsubtypename;
        this.status = Status;
        this.assessmentParameterName = verificationtypename;

    }

    public AssessmentQuestionInfo(int verificationsubtypeId, String name, int status, String verificationtypeName, int userId) {
        this.assessmentquestionId = verificationsubtypeId;
        this.name = name;
        this.status = status;
        this.assessmentParameterName = verificationtypeName;
        this.userId = userId;

    }

    public AssessmentQuestionInfo(int verificationsubtypeId, String name, int verificationtypeId, int status, int userId) {
        this.assessmentquestionId = verificationsubtypeId;
        this.name = name;
        this.assessmentParameterId = verificationtypeId;
        this.status = status;
        this.userId = userId;
    }

    public AssessmentQuestionInfo(String name, int assessmentAnswerTypeId, int assessmentParameterId, String link, int status) {
        this.name = name;
        this.assessmentAnswerTypeId = assessmentAnswerTypeId;
        this.assessmentParameterId = assessmentParameterId;
        this.link = link;
        this.status = status;
    }
    
    public AssessmentQuestionInfo(String questionName,String questionlink,String answerName,String assessmentParametername)
    {
        this.assessmentAnswerTypeName = answerName;
        this.assessmentParameterName = assessmentParametername;
        this.name = questionName;
        this.link = questionlink;
    }

    /**
     * @return the assessmentquestionId
     */
    public int getAssessmentquestionId() {
        return assessmentquestionId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
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

    public int getAssessmentAnswerTypeId() {
        return assessmentAnswerTypeId;
    }

    public String getAssessmentAnswerTypeName() {
        return assessmentAnswerTypeName;
    }

    public String getLink() {
        return link;
    }
}
