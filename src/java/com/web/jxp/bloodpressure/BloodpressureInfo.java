package com.web.jxp.bloodpressure;

public class BloodpressureInfo
{
    private int bloodpressureId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public BloodpressureInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public BloodpressureInfo(int bloodpressureId, String name, int status, int userId)
    {
        this.bloodpressureId = bloodpressureId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the bloodpressureId
     */
    public int getBloodpressureId() {
        return bloodpressureId;
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
