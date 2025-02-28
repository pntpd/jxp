package com.web.jxp.coursename;

import com.web.jxp.coursename.*;

public class CoursenameInfo
{
    private int coursenameId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public CoursenameInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public CoursenameInfo(int coursenameId, String name, int status, int userId)
    {
        this.coursenameId = coursenameId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the coursenameId
     */
    public int getCoursenameId() {
        return coursenameId;
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
