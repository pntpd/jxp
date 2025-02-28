package com.web.jxp.appealreason;

public class AppealreasonInfo
{
    private int appealreasonId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public AppealreasonInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public AppealreasonInfo(int appealreasonId, String name, int status, int userId)
    {
        this.appealreasonId = appealreasonId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the appealreasonId
     */
    public int getAppealreasonId() {
        return appealreasonId;
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
