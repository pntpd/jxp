package com.web.jxp.country;

public class CountryInfo
{
    private int countryId;
    private String name;
    private String nationality;
    private String code;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    private String ddlLabel1;
    private String ddlLabel2;
    private String ddlLabel3;
    
    //for ddl
    public CountryInfo(int ddlValue, String ddlLabel,String ddlLabel1,String ddlLabel2,String ddlLabel3)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
        this.ddlLabel1 = ddlLabel1;
        this.ddlLabel2 = ddlLabel2;
        this.ddlLabel3 = ddlLabel3;
        
    }

    public CountryInfo(int countryId, String name, String nationality, String code, int status, int userId)
    {
        this.countryId = countryId;
        this.name = name;
        this.nationality = nationality;
        this.code = code;
        this.status = status;
        this.userId = userId;
    }

    public String getDdlLabel3() {
        return ddlLabel3;
    }

    public String getDdlLabel1() {
        return ddlLabel1;
    }

    public String getDdlLabel2() {
        return ddlLabel2;
    }
    /**
     * @return the countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
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
