package com.web.jxp.assessmentparameter;

public class AssessmentParameterInfo
{
    private int assessmentParameterId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    private String description;
    
    //for ddl
    public AssessmentParameterInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public AssessmentParameterInfo(int assessmentParameterId, String name, int status, int userId)
    {
        this.assessmentParameterId = assessmentParameterId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }
    public AssessmentParameterInfo(int assessmentParameterId, String name, int status, int userId, String description)
    {
        this.assessmentParameterId = assessmentParameterId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.description = description;
    }
    
    public AssessmentParameterInfo( String name, String description)
    {
        this.name = name;
        this.status = status;
        this.description = description;
    }

   public  AssessmentParameterInfo(int assessmentParameterId, String name, String description, int status) {
        this.assessmentParameterId = assessmentParameterId;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public AssessmentParameterInfo(int assessmentParameterId, String name, String description, int status, int uId) {
        this.assessmentParameterId = assessmentParameterId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.userId = uId;
    }

    public String getDescription() {
        return description;
    }
    
    public int getAssessmentParameterId() {
        return assessmentParameterId;
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
