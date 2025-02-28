package com.web.jxp.billingcycle;

public class BillingcycleInfo
{
    private int billingcycleId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    private String assettypeName;
    private int assettypeId;
    private int pcount;
    private int editcount;
    private String description;
    private int billingcyclepositionId;
    private int count1;
    private int count2;
    private int count3;
    private int subcategoryId; 
    private int courseId;
    private int billingcycledetailId;
    private int categoryId;
    private int positionId;
    
    private int clientId;
    private int assetId;
    private String clientName;
    private String clientAssetName;
    private int questionId;
    private int statusCount;
    
    private String typevalue;
    private int billingstatus;
    private int type;
    
    //for ddl
    public BillingcycleInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    //for index
    public BillingcycleInfo(int billingcycleId, String name, String assettypeName, int pcount, int editcount, int status)
    {
        this.billingcycleId = billingcycleId;
        this.name = name;
        this.assettypeName = assettypeName;
        this.pcount = pcount;
        this.editcount = editcount;
        this.status = status;
    }
    
    public BillingcycleInfo(int clientId, int assetId, String clientName, String clientAssetName, int pcount,String typevalue,
        int billingstatus, int type)
    {
        this.clientId = clientId;
        this.assetId = assetId;
        this.clientName = clientName;
        this.clientAssetName = clientAssetName;
        this.pcount = pcount;
        this.typevalue = typevalue;
        this.billingstatus = billingstatus;
        this.type = type;
    }
  
    //for position list page
    public BillingcycleInfo(int clientId, String name, int count1, int count2, int count3, int positionId, int assetId, int statusCount)
    {
        this.clientId = clientId;
        this.name = name;
        this.count1 = count1;
        this.count2 = count2;
        this.count3 = count3;
        this.positionId = positionId;
        this.assetId = assetId;
        this.statusCount = statusCount;
    }
    //for course list
    public BillingcycleInfo(String name, int billingcycleId, int categoryId, int subcategoryId, 
        int questionId)
    {
        this.name = name;
        this.billingcycleId = billingcycleId;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.questionId = questionId;
    }
    /**
     * @return the billingcycleId
     */
    public int getBillingcycleId() {
        return billingcycleId;
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
     * @return the assettypeName
     */
    public String getAssettypeName() {
        return assettypeName;
    }

    /**
     * @return the assettypeId
     */
    public int getAssettypeId() {
        return assettypeId;
    }

    /**
     * @return the pcount
     */
    public int getPcount() {
        return pcount;
    }

    /**
     * @return the editcount
     */
    public int getEditcount() {
        return editcount;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the billingcyclepositionId
     */
    public int getBillingcyclepositionId() {
        return billingcyclepositionId;
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
     * @return the subcategoryId
     */
    public int getSubcategoryId() {
        return subcategoryId;
    }

    /**
     * @return the courseId
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * @return the billingcycledetailId
     */
    public int getBillingcycledetailId() {
        return billingcycledetailId;
    }

    /**
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getAssetId() {
        return assetId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientAssetName() {
        return clientAssetName;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(int statusCount) {
        this.statusCount = statusCount;
    }

    public String getTypevalue() {
        return typevalue;
    }

    public int getBillingstatus() {
        return billingstatus;
    }

    public int getType() {
        return type;
    }
    
}
