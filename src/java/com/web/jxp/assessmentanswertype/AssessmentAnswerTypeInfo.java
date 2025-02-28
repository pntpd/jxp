package com.web.jxp.assessmentanswertype;

public class AssessmentAnswerTypeInfo
{
    private int assessmentAnswerTypeId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public AssessmentAnswerTypeInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public AssessmentAnswerTypeInfo(int assessmentAnswerTypeId, String name, int status, int userId)
    {
        this.assessmentAnswerTypeId = assessmentAnswerTypeId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the assessmentAnswerTypeId
     */
    public int getAssessmentAnswerTypeId() {
        return assessmentAnswerTypeId;
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
}
