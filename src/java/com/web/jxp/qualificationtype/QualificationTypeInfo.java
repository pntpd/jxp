package com.web.jxp.qualificationtype;

public class QualificationTypeInfo
{
    private int qualificationtypeId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public QualificationTypeInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public QualificationTypeInfo(int qualificationtypeId, String name, int status, int userId)
    {
        this.qualificationtypeId = qualificationtypeId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the qualificationtypeId
     */
    public int getQualificationtypeId() {
        return qualificationtypeId;
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
