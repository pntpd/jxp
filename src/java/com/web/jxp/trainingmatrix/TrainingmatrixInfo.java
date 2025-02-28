package com.web.jxp.trainingmatrix;

public class TrainingmatrixInfo
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
    private int clientmatrixdetailId;
    private int categoryId;
    private int subcategoryId;
    private int courseId; 
    private int tctypeId;
    private int traingId;
    private int levelId;
    private int courseIdrel;
    
    //for ddl
    public TrainingmatrixInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    //for index
    public TrainingmatrixInfo(int clientId, int assetId, String clientName, String clientAssetName, int pcount)
    {
        this.clientId = clientId;
        this.assetId = assetId;
        this.clientName = clientName;
        this.clientAssetName = clientAssetName;
        this.pcount = pcount;
    }
    //for basic info
    public TrainingmatrixInfo (int assetId, String clientName, String clientAssetName, String assettypeName)
    {
        this.assetId = assetId;
        this.clientName = clientName;
        this.clientAssetName = clientAssetName;
        this.assettypeName = assettypeName;
    }
    //for course
    public TrainingmatrixInfo (String name, int clientmatrixdetailId, int categoryId, int subcategoryId, int courseId, 
        int tctypeId, int traingId, int levelId, int courseIdrel)
    {
        this.name = name;
        this.clientmatrixdetailId = clientmatrixdetailId;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.courseId = courseId;
        this.tctypeId = tctypeId;
        this.traingId = traingId;
        this.levelId = levelId;
        this.courseIdrel = courseIdrel;
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
     * @return the clientmatrixdetailId
     */
    public int getClientmatrixdetailId() {
        return clientmatrixdetailId;
    }

    /**
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryId;
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
     * @return the tctypeId
     */
    public int getTctypeId() {
        return tctypeId;
    }

    /**
     * @return the traingId
     */
    public int getTraingId() {
        return traingId;
    }

    /**
     * @return the levelId
     */
    public int getLevelId() {
        return levelId;
    }

    /**
     * @return the courseIdrel
     */
    public int getCourseIdrel() {
        return courseIdrel;
    }
}
