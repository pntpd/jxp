package com.web.jxp.bank;

public class BankInfo
{
    private int bankId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    private String branch;
    private String accno;
    private String ifsc;
    
    //for ddl
    public BankInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public BankInfo(int bankId, String name, String branch, String accno, String ifsc, int status, int userId)
    {
        this.bankId = bankId;
        this.name = name;
        this.branch = branch;
        this.accno = accno;
        this.ifsc = ifsc;
        this.status = status;
        this.userId = userId;
    }

    /**
     * @return the bankId
     */
    public int getBankId() {
        return bankId;
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

    /**
     * @return the branch
     */
    public String getBranch() {
        return branch;
    }

    /**
     * @return the accno
     */
    public String getAccno() {
        return accno;
    }

    /**
     * @return the ifsc
     */
    public String getIfsc() {
        return ifsc;
    }
}
