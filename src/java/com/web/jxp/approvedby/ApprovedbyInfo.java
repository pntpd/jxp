package com.web.jxp.approvedby;

public class ApprovedbyInfo
{
    private int approvedbyId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public ApprovedbyInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public ApprovedbyInfo(int approvedbyId, String name, int status, int userId)
    {
        this.approvedbyId = approvedbyId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the approvedbyId
     */
    public int getApprovedbyId() {
        return approvedbyId;
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
