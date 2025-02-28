package com.web.jxp.ppetype;

public class PpetypeInfo
{
    private int ppetypeId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    private String addvalue;
    private int typeId;
    
    //for ddl
    public PpetypeInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public PpetypeInfo(int ppetypeId, String name, int status, int userId, int typeId,String addvalue)
    {
        this.ppetypeId = ppetypeId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.typeId = typeId;
        this.addvalue = addvalue;
    }

    /**
     * @return the ppetypeId
     */
    public int getPpetypeId() {
        return ppetypeId;
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

    /**
     * @return the addvalue
     */
    public String getAddvalue() {
        return addvalue;
    }

    /**
     * @return the typeId
     */
    public int getTypeId() {
        return typeId;
    }
}
