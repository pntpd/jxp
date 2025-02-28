package com.web.jxp.timezone;

import java.util.Collection;

public class TimezoneInfo {

    private int timezoneId;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;

    private int countryId;

    private Collection countrys;

    private String countryName;
    private String timezoneName;
    private String timezoneCode;

    //for ddl
    public TimezoneInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public TimezoneInfo(int timezoneId, String name, int status, int userId) {
        this.timezoneId = timezoneId;
        this.timezoneName = name;
        this.status = status;
        this.userId = userId;
    }

    public TimezoneInfo(int timezoneId, String timezonename, String code, int Status, String countryname) {

        this.timezoneId = timezoneId;
        this.timezoneName = timezonename;
        this.status = Status;
        this.countryName = countryname;
        this.timezoneCode = code;
    }

    public TimezoneInfo(int timezoneId, String name, String code, int status, String countryName, int userId) {
        this.timezoneId = timezoneId;
        this.timezoneName = name;
        this.timezoneCode = code;
        this.status = status;
        this.countryName = countryName;
        this.userId = userId;

    }

    public TimezoneInfo(int timezoneId, String name, String code, int countryId, int status, int userId) {
        this.timezoneId = timezoneId;
        this.timezoneName = name;
        this.timezoneCode = code;
        this.countryId = countryId;
        this.status = status;
        this.userId = userId;

    }

    public int getCountryId() {
        return countryId;
    }

    public Collection getCountrys() {
        return countrys;
    }

    public String getCountryName() {
        return countryName;
    }

    /**
     * @return the timezoneId
     */
    public int getTimezoneId() {
        return timezoneId;
    }

    /**
     * @return the name
     */
    public String getTimezoneName() {
        return timezoneName;
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

    public String getTimezoneCode() {
        return timezoneCode;
    }
}
