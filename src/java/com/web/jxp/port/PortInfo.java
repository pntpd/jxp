package com.web.jxp.port;

import java.util.Collection;

public class PortInfo {

    private int portId;
    private String name;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;
    private int countryId;
    private Collection countrys;
    private String countryName;
    private int ddlValue1;

    //for ddl
    public PortInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    
    public PortInfo(int ddlValue, String ddlLabel, int ddlValue1) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
        this.ddlValue1 = ddlValue1;
    }

    public PortInfo(int portId, String name, int countryId, int status, int userId) {
        this.portId = portId;
        this.name = name;
        this.countryId = countryId;
        this.status = status;
        this.userId = userId;
    }

    public PortInfo(int portId, String name, String countryName, int status, int i) {
        this.portId = portId;
        this.name = name;
        this.countryName = countryName;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the portId
     */
    public int getPortId() {
        return portId;
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

    public int getCountryId() {
        return countryId;
    }

    public Collection getCountrys() {
        return countrys;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getDdlValue1() {
        return ddlValue1;
    } 
    
}
