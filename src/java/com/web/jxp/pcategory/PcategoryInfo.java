package com.web.jxp.pcategory;
public class PcategoryInfo
{
    private int pcategoryId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;    
    private int assettypeId;
    private String assettypeName;
    private String deptName;
    private int qcount;
    private int pdeptId;
    private String description;
    private int pquestionId;
    
    //for ddl
    public PcategoryInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    //for list
    public PcategoryInfo(int pcategoryId, String name, String assettypeName, String deptName, int qcount, int status)
    {
        this.pcategoryId = pcategoryId;
        this.name = name;
        this.assettypeName = assettypeName;
        this.deptName = deptName;
        this.qcount = qcount;
        this.status = status;
    }
    //for add
    public PcategoryInfo(int pcategoryId, String name, int assettypeId, int pdeptId, String description, int status, int userId)
    {   
        this.pcategoryId = pcategoryId;
        this.name = name;
        this.assettypeId = assettypeId;
        this.pdeptId = pdeptId;
        this.description = description;
        this.status = status;
        this.userId = userId;        
    }
    
    //for view
    public PcategoryInfo(int pcategoryId, String name, String assettypeName, String deptName, 
        String description, int status, int assettypeId, int qcount)
    {   
        this.pcategoryId = pcategoryId;
        this.name = name;
        this.assettypeName = assettypeName;
        this.deptName = deptName;
        this.description = description;
        this.status = status;      
        this.assettypeId = assettypeId;
        this.qcount = qcount;
    }
    
    //for question list
    public PcategoryInfo(int pquestionId, String name, int status)
    {   
        this.pquestionId = pquestionId;
        this.name = name;
        this.status = status;    
    }

    /**
     * @return the pcategoryId
     */
    public int getPcategoryId() {
        return pcategoryId;
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
     * @return the deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @return the qcount
     */
    public int getQcount() {
        return qcount;
    }

    /**
     * @return the pdeptId
     */
    public int getPdeptId() {
        return pdeptId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the pquestionId
     */
    public int getPquestionId() {
        return pquestionId;
    }
}
