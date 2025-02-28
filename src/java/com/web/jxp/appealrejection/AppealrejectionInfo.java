package com.web.jxp.appealrejection;

public class AppealrejectionInfo
{
    private int appealrejectionId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public AppealrejectionInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public AppealrejectionInfo(int appealrejectionId, String name, int status, int userId)
    {
        this.appealrejectionId = appealrejectionId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the appealrejectionId
     */
    public int getAppealrejectionId() {
        return appealrejectionId;
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
