package com.web.jxp.skills;

public class SkillsInfo
{
    private int skillsId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public SkillsInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public SkillsInfo(int skillsId, String name, int status, int userId)
    {
        this.skillsId = skillsId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the skillsId
     */
    public int getSkillsId() {
        return skillsId;
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
