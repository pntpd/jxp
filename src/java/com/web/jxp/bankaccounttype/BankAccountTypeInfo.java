package com.web.jxp.bankaccounttype;

public class BankAccountTypeInfo
{
    private int bankAccountTypeId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    //for ddl
    public BankAccountTypeInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public BankAccountTypeInfo(int bankAccountTypeId, String name, int status, int userId)
    {
        this.bankAccountTypeId = bankAccountTypeId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the bankAccountTypeId
     */
    public int getBankAccountTypeId() {
        return bankAccountTypeId;
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
