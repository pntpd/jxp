package com.web.jxp.airport;

public class AirportInfo
{
    private int airportId;
    private String airportName;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    private int countryId;
    private int cityId;
    private String countryName;
    private String cityName;
    private String shortname;

    public String getCityName() {
        return cityName;
    }
    public String getShortname() {
        return shortname;
    }

    public int getCityId() {
        return cityId;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountryName() {
        return countryName;
    }
    //for ddl
    public AirportInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public AirportInfo(int airportId, String name, int status, int userId)
    {
        this.airportId = airportId;
        this.airportName = name;
        this.status = status;
        this.userId = userId;
    }
    
    public AirportInfo(int airportId, String airportname, int Status , String countryname)
    {   
        
        this.airportId = airportId;
        this.airportName = airportname;
        this.status = Status;
        this.countryName = countryname;
        
    }
     public AirportInfo(int airportId, String name, int status, String  countryName, int userId,String cityName, String shortname)
    {
        this.airportId = airportId;
        this.airportName = name;
        this.status = status;
        this.countryName = countryName;
        this.userId = userId;
        this.cityName = cityName;
        this.shortname = shortname;
        
    }
    
     public AirportInfo(int airportId, String name,  int status, int countryId, int userId, int cityId,String shortname)
    {
        this.airportId = airportId;
        this.airportName = name;
        this.countryId = countryId;
        this.status = status;
        this.userId = userId;
        this.cityId = cityId;
        this.shortname = shortname;
       
    }
     
     public AirportInfo(int airportId, String name,String shortname,int status,String countryName,String cityName)
     {
         this.airportId = airportId;
         this.airportName = name;
         this.shortname = shortname;
         this.status = status;
         this.countryName = countryName;
         this.cityName = cityName;
     }

    /**
     * @return the airportId
     */
    public int getAirportId() {
        return airportId;
    }

    /**
     * @return the name
     */
    public String getAirportName() {
        return airportName;
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
