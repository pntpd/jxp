package com.web.jxp.degree;

public class DegreeInfo
{
    private int degreeId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public DegreeInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public DegreeInfo(int degreeId, String name, int status, int userId)
    {
        this.degreeId = degreeId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the degreeId
     */
    public int getDegreeId() {
        return degreeId;
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
}
