package com.web.jxp.grade;

public class GradeInfo
{
    private int gradeId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public GradeInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public GradeInfo(int gradeId, String name, int status, int userId)
    {
        this.gradeId = gradeId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the gradeId
     */
    public int getGradeId() {
        return gradeId;
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
