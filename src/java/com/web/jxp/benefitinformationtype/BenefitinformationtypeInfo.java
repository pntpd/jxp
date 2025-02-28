package com.web.jxp.benefitinformationtype;

import java.util.Collection;

public class BenefitinformationtypeInfo
{
    private int benefitinformationtypeId;
    private String benefitinformationtypeName;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    private int benefittypeId;
    private Collection benefittypes;
    private String benefittypeName;

    
    //for ddl
    public BenefitinformationtypeInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public BenefitinformationtypeInfo(int benefitinformationtypeId, String name, int status, int userId)
    {
        this.benefitinformationtypeId = benefitinformationtypeId;
        this.benefitinformationtypeName = name;
        this.status = status;
        this.userId = userId;
    }
    
    public BenefitinformationtypeInfo(int benefitinformationtypeId, String benefitinformationtypename, int Status , String benefittypename)
    {   
        
        this.benefitinformationtypeId = benefitinformationtypeId;
        this.benefitinformationtypeName = benefitinformationtypename;
        this.status = Status;
        this.benefittypeName = benefittypename;
        
    }
     public BenefitinformationtypeInfo(int benefitinformationtypeId, String name, int status, String  benefittypeName, int userId)
    {
        this.benefitinformationtypeId = benefitinformationtypeId;
        this.benefitinformationtypeName = name;
        this.status = status;
        this.benefittypeName = benefittypeName;
        this.userId = userId;
        
    }
    
     public BenefitinformationtypeInfo(int benefitinformationtypeId, String name, int benefittypeId, int status, int userId)
    {
        this.benefitinformationtypeId = benefitinformationtypeId;
        this.benefitinformationtypeName = name;
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
     * @return the benefitinformationtypeId
     */
    public int getBenefitinformationtypeId() {
        return benefitinformationtypeId;
    }

    /**
     * @return the name
     */
    public String getBenefitinformationtypeName() {
        return benefitinformationtypeName;
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
