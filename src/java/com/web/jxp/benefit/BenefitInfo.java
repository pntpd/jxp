package com.web.jxp.benefit;

import java.util.Collection;

public class BenefitInfo {

    private int benefitId;
    private String benefitName;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;

    private int benefittypeId;
    private Collection benefittypes;
    private String benefittypeName;

    //for ddl
    public BenefitInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public BenefitInfo(int benefitId, String name, int status, int userId) {
        this.benefitId = benefitId;
        this.benefitName = name;
        this.status = status;
        this.userId = userId;
    }

    public BenefitInfo(int benefitId, String benefitname, int Status, String benefittypename) {

        this.benefitId = benefitId;
        this.benefitName = benefitname;
        this.status = Status;
        this.benefittypeName = benefittypename;

    }

    public BenefitInfo(int benefitId, String name, int status, String benefittypeName, int userId) {
        this.benefitId = benefitId;
        this.benefitName = name;
        this.status = status;
        this.benefittypeName = benefittypeName;
        this.userId = userId;

    }

    public BenefitInfo(int benefitId, String name, int benefittypeId, int status, int userId) {
        this.benefitId = benefitId;
        this.benefitName = name;
        this.benefittypeId = benefittypeId;
        this.status = status;
        this.userId = userId;

    }

    public int getBenefittypeId() {
        return benefittypeId;
    }

    public Collection getBenefittypes() {
        return benefittypes;
    }

    public String getBenefittypeName() {
        return benefittypeName;
    }

    /**
     * @return the benefitId
     */
    public int getBenefitId() {
        return benefitId;
    }

    /**
     * @return the name
     */
    public String getBenefitName() {
        return benefitName;
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
