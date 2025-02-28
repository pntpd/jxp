package com.web.jxp.vaccine;

public class VaccineInfo
{
    private int vaccineId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public VaccineInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public VaccineInfo(int vaccineId, String name, int status, int userId)
    {
        this.vaccineId = vaccineId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the vaccineId
     */
    public int getVaccineId() {
        return vaccineId;
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
