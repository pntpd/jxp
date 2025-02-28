package com.web.jxp.rotation;

public class RotationInfo
{
    private int rotationId;
    private int noOfDays;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    private String ddlLabel1;
    
    //for ddl
    public RotationInfo(int ddlValue, String ddlLabel, String ddlLabel1)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
        this.ddlLabel1 = ddlLabel1;
    }

    public RotationInfo(int rotationId, int noOfDays, String name, int status, int userId)
    {
        this.rotationId = rotationId;
        this.noOfDays = noOfDays;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the rotationId
     */
    public int getRotationId() {
        return rotationId;
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

    public int getNoOfDays() {
        return noOfDays;
    }   

    public String getDdlLabel1() {
        return ddlLabel1;
    }   
}
