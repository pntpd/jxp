package com.web.jxp.framework;

public class FrameworkInfo
{
    private int clientId;
    private int assetId; 
    private String clientName;
    private String clientAssetName;
    private int pcount;
    private int ddlValue;
    private String ddlLabel;
    private String assettypeName;
    private String name;
    private int pending;
    private int ccount;
    private int assettypeId;
    private int positionId;
    private String positionName;
    private String pdeptName;
    private int pdeptId;
    private int rolecount;
    private int count1;
    private int count2;
    private int count3;
    private int pquestionId;
    private int pcategoryId;
    private int fcroledetailId;
    private String gradeName;
    private String code;
    private String categoryName;
    private String questionName;
    private String passessmenttypeName;
    private String priorityName;
    private int month;
    
    //for ddl
    public FrameworkInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    //for index
    public FrameworkInfo(int clientId, int assetId, String clientName, String clientAssetName, int pcount, int pending, int ccount)
    {
        this.clientId = clientId;
        this.assetId = assetId;
        this.clientName = clientName;
        this.clientAssetName = clientAssetName;
        this.pcount = pcount;
        this.pending = pending;
        this.ccount = ccount;
    }
    //for basic info
    public FrameworkInfo (int assetId, String clientName, String clientAssetName, int pcount, int assettypeId)
    {
        this.assetId = assetId;
        this.clientName = clientName;
        this.clientAssetName = clientAssetName;
        this.pcount = pcount;
        this.assettypeId = assettypeId;
    }
    
    public FrameworkInfo (int positionId, String positionName, int pdeptId, String pdeptName, int rolecount)
    {
        this.positionId = positionId;
        this.positionName = positionName;
        this.pdeptId = pdeptId;
        this.pdeptName = pdeptName;        
        this.rolecount = rolecount;
    } 
    //for assign page
    public FrameworkInfo (String positionName, String pdeptName, int count1, int count2, int count3)
    {
        this.positionName = positionName;
        this.pdeptName = pdeptName;
        this.count1 = count1;
        this.count2 = count2;
        this.count3 = count3;
    }
    //for questionlist
    public FrameworkInfo (int pquestionId, int pcategoryId, String name, int fcroledetailId)
    {
        this.pquestionId = pquestionId;
        this.pcategoryId = pcategoryId;
        this.name = name;
        this.fcroledetailId = fcroledetailId;
    }
    //fpr position modal
    public FrameworkInfo(String positionName, String gradeName)
    {
        this.positionName = positionName;
        this.gradeName = gradeName;
    }
    //for question excel
    public FrameworkInfo (String positionName, String code, String categoryName, String questionName,
        String passessmenttypeName, String priorityName, int month)
    {
        this.positionName = positionName;
        this.code = code;
        this.categoryName = categoryName;
        this.questionName = questionName;
        this.passessmenttypeName = passessmenttypeName;
        this.priorityName = priorityName;
        this.month = month;
    }

    /**
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @return the assetId
     */
    public int getAssetId() {
        return assetId;
    }

    /**
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @return the clientAssetName
     */
    public String getClientAssetName() {
        return clientAssetName;
    }

    /**
     * @return the pcount
     */
    public int getPcount() {
        return pcount;
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
     * @return the assettypeName
     */
    public String getAssettypeName() {
        return assettypeName;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the pending
     */
    public int getPending() {
        return pending;
    }

    /**
     * @return the ccount
     */
    public int getCcount() {
        return ccount;
    }

    /**
     * @return the assettypeId
     */
    public int getAssettypeId() {
        return assettypeId;
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
    }

    /**
     * @return the positionName
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * @return the pdeptName
     */
    public String getPdeptName() {
        return pdeptName;
    }

    /**
     * @return the pdeptId
     */
    public int getPdeptId() {
        return pdeptId;
    }

    /**
     * @return the rolecount
     */
    public int getRolecount() {
        return rolecount;
    }

    /**
     * @return the count1
     */
    public int getCount1() {
        return count1;
    }

    /**
     * @return the count2
     */
    public int getCount2() {
        return count2;
    }

    /**
     * @return the count3
     */
    public int getCount3() {
        return count3;
    }

    /**
     * @return the pquestionId
     */
    public int getPquestionId() {
        return pquestionId;
    }

    /**
     * @return the pcategoryId
     */
    public int getPcategoryId() {
        return pcategoryId;
    }

    /**
     * @return the fcroledetailId
     */
    public int getFcroledetailId() {
        return fcroledetailId;
    }

    /**
     * @return the gradeName
     */
    public String getGradeName() {
        return gradeName;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @return the questionName
     */
    public String getQuestionName() {
        return questionName;
    }

    /**
     * @return the passessmenttypeName
     */
    public String getPassessmenttypeName() {
        return passessmenttypeName;
    }

    /**
     * @return the priorityName
     */
    public String getPriorityName() {
        return priorityName;
    }

    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }
}
