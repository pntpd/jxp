package com.web.jxp.enginetype;

public class EnginetypeInfo
{
    private int enginetypeId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public EnginetypeInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public EnginetypeInfo(int enginetypeId, String name, int status, int userId)
    {
        this.enginetypeId = enginetypeId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the enginetypeId
     */
    public int getEnginetypeId() {
        return enginetypeId;
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
