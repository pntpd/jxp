package com.web.jxp.appeal;

public class AppealInfo
{
    private int appealId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public AppealInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public AppealInfo(int appealId, String name, int status, int userId)
    {
        this.appealId = appealId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the appealId
     */
    public int getAppealId() {
        return appealId;
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
