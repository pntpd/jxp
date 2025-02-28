package com.web.jxp.access;

public class AccessInfo 
{
    private int accessId;
    private String name;
    private String ipaddress;
    private String desc;
    private String regDate;
    private String localip;
    private int ddlValue;
    private String ddlLable;
    
    public AccessInfo ()
    {
    }

     public AccessInfo(int ddlValue,String ddlLable)
    {
        this.ddlValue = ddlValue;
        this.ddlLable = ddlLable;
    }
    
    //for index
    public AccessInfo (int accessId, String name, String ipaddress, String desc, String regDate, String localip)
    {
        this.accessId = accessId;
        this.name = name;
        this.ipaddress = ipaddress;
        this.desc = desc;
        this.regDate = regDate;
        this.localip = localip;
    }

    public String getDdlLable() {
        return ddlLable;
    }

    public int getDdlValue() {
        return ddlValue;
    }
    public int getAccessId ()
    {
        return accessId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getIpaddress()
    {
        return ipaddress;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    public String getRegDate()
    {
        return regDate;
    }

    /**
     * @return the localip
     */
    public String getLocalip() {
        return localip;
    }
}
