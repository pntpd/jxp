package com.web.jxp.onboardingkit;

public class OnboardingkitInfo
{
    private int onboardingkitId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public OnboardingkitInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public OnboardingkitInfo(int onboardingkitId, String name, int status, int userId)
    {
        this.onboardingkitId = onboardingkitId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the onboardingkitId
     */
    public int getOnboardingkitId() {
        return onboardingkitId;
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
