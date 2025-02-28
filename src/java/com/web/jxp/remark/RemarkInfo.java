package com.web.jxp.remark;

public class RemarkInfo
{
    private int remarkId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public RemarkInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public RemarkInfo(int remarkId, String name, int status, int userId)
    {
        this.remarkId = remarkId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the remarkId
     */
    public int getRemarkId() {
        return remarkId;
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
