package com.web.jxp.companyindustry;

public class CompanyindustryInfo
{
    private int companyindustryId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public CompanyindustryInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public CompanyindustryInfo(int companyindustryId, String name, int status, int userId)
    {
        this.companyindustryId = companyindustryId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }
     public CompanyindustryInfo(int companyindustryId, String companyindustryname, int status )
    {   
        this.companyindustryId = companyindustryId;
        this.name = companyindustryname;
        this.status = status;
    }

    /**
     * @return the companyindustryId
     */
    public int getCompanyindustryId() {
        return companyindustryId;
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
