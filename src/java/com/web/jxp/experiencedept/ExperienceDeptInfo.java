package com.web.jxp.experiencedept;

public class ExperienceDeptInfo
{
    private int experiencedeptId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public ExperienceDeptInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public ExperienceDeptInfo(int experiencedeptId, String name, int status, int userId)
    {
        this.experiencedeptId = experiencedeptId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the experiencedeptId
     */
    public int getExperiencedeptId() {
        return experiencedeptId;
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
