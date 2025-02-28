package com.web.jxp.vaccinetype;

public class VaccinetypeInfo
{
    private int vaccinetypeId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public VaccinetypeInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public VaccinetypeInfo(int vaccinetypeId, String name, int status, int userId)
    {
        this.vaccinetypeId = vaccinetypeId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the vaccinetypeId
     */
    public int getVaccinetypeId() {
        return vaccinetypeId;
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
