package com.web.jxp.home;

public class HomeInfo
{
    private int homeId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    private int candidateId;
    private int onlineflag;
    private String emailId;
    
    
    //for ddl
    public HomeInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public HomeInfo(int candidateId, int onlineflag, String emailId)
    {
        this.candidateId = candidateId;
        this.onlineflag = onlineflag;
        this.emailId = emailId;
    }

    /**
     * @return the homeId
     */
    public int getCoursetypeId() {
        return homeId;
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

    public int getHomeId() {
        return homeId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public int getOnlineflag() {
        return onlineflag;
    }

    public String getEmailId() {
        return emailId;
    }
    
    
}
