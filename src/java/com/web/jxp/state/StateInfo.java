package com.web.jxp.state; 

public class StateInfo 
{
    private int stateId;
    private String stateName;
    private int status;
    private int userId;  
    private int ddlValue;
    private int ddlValue2;
    private String ddlLabel;    
    private int countryId;
    private String countryName;
    
    //for ddl
    public StateInfo(int ddlValue,  String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    
    public StateInfo(int ddlValue, int ddlValue2, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlValue2 = ddlValue2;
        this.ddlLabel = ddlLabel;
    }

    public StateInfo(int stateId, String name, int status, int userId)
    {
        this.stateId = stateId;
        this.stateName = name;
        this.status = status;
        this.userId = userId;
    }
    
    public StateInfo(int stateId, String statename, int Status , String countryname)
    {   
        
        this.stateId = stateId;
        this.stateName = statename;
        this.status = Status;
        this.countryName = countryname;
        
    }
     public StateInfo(int stateId, String name, int status, String  countryName, int userId)
    {
        this.stateId = stateId;
        this.stateName = name;
        this.status = status;
        this.countryName = countryName;
        this.userId = userId;
        
    }
    
     public StateInfo(int stateId, String name, int countryId, int status, int userId)
    {
        this.stateId = stateId;
        this.stateName = name;
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
     * @return the stateId
     */
    public int getStateId() {
        return stateId;
    }

    /**
     * @return the name
     */
    public String getStateName() {
        return stateName;
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

    /**
     * @return the ddlValue2
     */
    public int getDdlValue2() {
        return ddlValue2;
    }
}
