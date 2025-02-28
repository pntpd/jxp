package com.web.jxp.hoursofwork;

public class HoursOfWorkInfo
{
    private int hoursofworkId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    private int noOfHours;
    private String ddlLabel1;
    
    //for ddl
    public HoursOfWorkInfo(int ddlValue, String ddlLabel, String ddlLabel1)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
        this.ddlLabel1 = ddlLabel1;
    }

    public HoursOfWorkInfo(int hoursofworkId, int noOfHours, String name, int status, int userId)
    {
        this.hoursofworkId = hoursofworkId;
        this.noOfHours = noOfHours;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the hoursofworkId
     */
    public int getHoursofworkId() {
        return hoursofworkId;
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

    public int getNoOfHours() {
        return noOfHours;
    }

    public String getDdlLabel1() {
        return ddlLabel1;
    }   
}
