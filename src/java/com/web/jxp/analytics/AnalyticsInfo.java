package com.web.jxp.analytics;
public class AnalyticsInfo 
{
    private int ddlValue;
    private String ddlLabel;   
    
        //for ddl
    public AnalyticsInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
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
