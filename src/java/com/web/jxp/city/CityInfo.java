package com.web.jxp.city; 
public class CityInfo 
{
    private int cityId;
    private String cityName;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;    
    private int countryId;
    private String countryName;
    
    //for ddl
    public CityInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public CityInfo(int cityId, String name, int status, int userId)
    {
        this.cityId = cityId;
        this.cityName = name;
        this.status = status;
        this.userId = userId;
    }
    
    public CityInfo(int cityId, String cityname, int Status , String countryname)
    {   
        
        this.cityId = cityId;
        this.cityName = cityname;
        this.status = Status;
        this.countryName = countryname;
        
    }
     public CityInfo(int cityId, String name, int status, String  countryName, int userId)
    {
        this.cityId = cityId;
        this.cityName = name;
        this.status = status;
        this.countryName = countryName;
        this.userId = userId;
        
    }
    
     public CityInfo(int cityId, String name, int countryId, int status, int userId)
    {
        this.cityId = cityId;
        this.cityName = name;
        this.countryId = countryId;
        this.status = status;
        this.userId = userId;
       
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountryName() {
        return countryName;
    }
    /**
     * @return the cityId
     */
    public int getCityId() {
        return cityId;
    }

    /**
     * @return the name
     */
    public String getCityName() {
        return cityName;
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
