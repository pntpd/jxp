package com.web.jxp.level;

public class LevelInfo
{
    private int levelId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public LevelInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public LevelInfo(int levelId, String name, int status, int userId)
    {
        this.levelId = levelId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the levelId
     */
    public int getLevelId() {
        return levelId;
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
