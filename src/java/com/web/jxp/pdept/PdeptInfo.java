package com.web.jxp.pdept;
public class PdeptInfo
{
    private int pdeptId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;    
    private int assettypeId;
    private String assettypeName;
    private String description;
    
    //for ddl
    public PdeptInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    //for list
    public PdeptInfo(int pdeptId, String name, String assettypeName, int status)
    {
        this.pdeptId = pdeptId;
        this.name = name;
        this.assettypeName = assettypeName;
        this.status = status;
    }
    //for add
    public PdeptInfo(int pdeptId, String name, int assettypeId, String description, int status, int userId)
    {   
        
        this.pdeptId = pdeptId;
        this.name = name;
        this.assettypeId = assettypeId;
        this.description = description;
        this.status = status;
        this.userId = userId;        
    }
    //for view
    public PdeptInfo(int pdeptId, String name, String assettypeName, int status, String description)
    {
        this.pdeptId = pdeptId;
        this.name = name;
        this.assettypeName = assettypeName;
        this.status = status;
        this.description = description;
    }

    /**
     * @return the pdeptId
     */
    public int getPdeptId() {
        return pdeptId;
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
     * @return the assettypeId
     */
    public int getAssettypeId() {
        return assettypeId;
    }

    /**
     * @return the assettypeName
     */
    public String getAssettypeName() {
        return assettypeName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
