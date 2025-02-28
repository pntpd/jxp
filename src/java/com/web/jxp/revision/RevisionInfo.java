package com.web.jxp.revision;

public class RevisionInfo
{
    private int revisionId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public RevisionInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public RevisionInfo(int revisionId, String name, int status, int userId)
    {
        this.revisionId = revisionId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the revisionId
     */
    public int getRevisionId() {
        return revisionId;
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
