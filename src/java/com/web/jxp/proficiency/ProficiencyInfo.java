package com.web.jxp.proficiency;

public class ProficiencyInfo
{
    private int proficiencyId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public ProficiencyInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public ProficiencyInfo(int proficiencyId, String name, int status, int userId)
    {
        this.proficiencyId = proficiencyId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the proficiencyId
     */
    public int getProficiencyId() {
        return proficiencyId;
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
