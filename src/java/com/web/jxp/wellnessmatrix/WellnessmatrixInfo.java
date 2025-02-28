package com.web.jxp.wellnessmatrix;

public class WellnessmatrixInfo
{
    private int wellnessmatrixId;
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
    private int wellnessmatrixpositionId;
    private int count1;
    private int count2;
    private int count3;
    private int subcategoryId; 
    private int courseId;
    private int wellnessmatrixdetailId;
    private int categoryId;
    private int positionId;
    
    private int clientId;
    private int assetId;
    private String clientName;
    private String clientAssetName;
    private int questionId;
    private int statusCount;
    private String category;
    private String subcategory;
    private String question;
    private String position;
    
    //for ddl
    public WellnessmatrixInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    //for index
    public WellnessmatrixInfo(int wellnessmatrixId, String name, String assettypeName, int pcount, int editcount, int status)
    {
        this.wellnessmatrixId = wellnessmatrixId;
        this.name = name;
        this.assettypeName = assettypeName;
        this.pcount = pcount;
        this.editcount = editcount;
        this.status = status;
    }
    //For excel type 3
    public WellnessmatrixInfo(String category, String subcategory, String question, String position)
    {
        this.category = category;
        this.subcategory = subcategory;
        this.question = question;
        this.position = position;
    }
    
    public WellnessmatrixInfo(int clientId, int assetId, String clientName, String clientAssetName, int pcount, int assettypeId)
    {
        this.clientId = clientId;
        this.assetId = assetId;
        this.clientName = clientName;
        this.clientAssetName = clientAssetName;
        this.pcount = pcount;
        this.assettypeId = assettypeId;
    }
    
    //for add
    public WellnessmatrixInfo(int wellnessmatrixId, String name, int assettypeId, String description, int status, int userId)
    {
        this.wellnessmatrixId = wellnessmatrixId;
        this.name = name;
        this.assettypeId = assettypeId;
        this.description = description;
        this.status = status;
        this.userId = userId;
    }
    //for view
    public WellnessmatrixInfo(int wellnessmatrixId, String name, String assettypeName, String description, int status, int assettypeId)
    {
        this.wellnessmatrixId = wellnessmatrixId;
        this.name = name;
        this.assettypeName = assettypeName;
        this.description = description;
        this.status = status;
        this.assettypeId = assettypeId;
    }
    //for position list page
    public WellnessmatrixInfo(int clientId, String name, int count1, int count2, int count3, int positionId, int assetId, int statusCount)
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
    public WellnessmatrixInfo(String name, int wellnessmatrixId, int categoryId, int subcategoryId, 
        int questionId)
    {
        this.name = name;
        this.wellnessmatrixId = wellnessmatrixId;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.questionId = questionId;
    }
    /**
     * @return the wellnessmatrixId
     */
    public int getWellnessmatrixId() {
        return wellnessmatrixId;
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
     * @return the wellnessmatrixpositionId
     */
    public int getWellnessmatrixpositionId() {
        return wellnessmatrixpositionId;
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
     * @return the wellnessmatrixdetailId
     */
    public int getWellnessmatrixdetailId() {
        return wellnessmatrixdetailId;
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

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the subcategory
     */
    public String getSubcategory() {
        return subcategory;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }
    
    

}
