package com.web.jxp.experiencewaterdepth;

public class ExperienceWaterDepthInfo {

    private int experiencewaterdepthId;
    // private int depth;
    private String depth;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;
    private String ddlLabel1;
    private String unitMeasurement;

    //for ddl
    public ExperienceWaterDepthInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
//        this.ddlLabel1 = ddlLabel1;
    }

    public ExperienceWaterDepthInfo(int experiencewaterdepthId, String unitMeasurement, String depth, int status, int userId) {
        this.experiencewaterdepthId = experiencewaterdepthId;
        this.unitMeasurement = unitMeasurement;
        this.depth = depth;
        this.status = status;
        this.userId = userId;
    }

    public String getDepth() {
        return depth;
    }

    /**
     * @return the experiencewaterdepthId
     */
    public int getExperiencewaterdepthId() {
        return experiencewaterdepthId;
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

//    public int getDepth() {
//        return depth;
//    }   
    public String getDdlLabel1() {
        return ddlLabel1;
    }

    public String getUnitMeasurement() {
        return unitMeasurement;
    }
}
