package com.web.jxp.base;

public class StatsInfo
{
    private String id;
    private String stats;
    private String year;
    private int ddlValue;
    private String ddlLabel;
    private String dateTime;
    private String userName;
    private String action;
    private String subdeptName;


    public StatsInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    public StatsInfo(String dateTime,String userName,String action)
    {
        this.dateTime = dateTime;
        this.userName = userName;
        this.action = action;
    }
    public StatsInfo(String dateTime, String userName, String action, String subdeptName)
    {
        this.dateTime = dateTime;
        this.userName = userName;
        this.action = action;
        this.subdeptName = subdeptName;
    }

    /**
     * @return the ddlValue
     */
    public int getDdlValue()
    {
        return ddlValue;
    }

    /**
     * @return the ddlLabel
     */

    public String getDdlLabel()
    {
        return ddlLabel;
    }
    public StatsInfo(String year)
    {
        this.year = year;
    }

    public StatsInfo(String id, String stats) 
    {
        this.id = id;
        this.stats = stats;
    }
    
    public String getId() 
    {
        return id;
    }

    public String getStats() 
    {
        return stats;
    } 
    
    public String getYear()
    {
        return year;
    }

    /**
     * @return the dateTime
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * @return the usreName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @return the subdeptName
     */
    public String getSubdeptName() {
        return subdeptName;
    }
}
