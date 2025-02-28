package com.web.jxp.crewtype;

public class CrewtypeInfo
{
    private int crewtypeId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public CrewtypeInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public CrewtypeInfo(int crewtypeId, String name, int status, int userId)
    {
        this.crewtypeId = crewtypeId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the crewtypeId
     */
    public int getCrewtypeId() {
        return crewtypeId;
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
