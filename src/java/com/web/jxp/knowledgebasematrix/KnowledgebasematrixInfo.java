package com.web.jxp.knowledgebasematrix;

public class KnowledgebasematrixInfo {

    private int knowledgebasematrixId;
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
    private int count;

    private String position;
    private String grade;
    private String category;
    private String subcategory;
    private String topic;

    //for ddl
    public KnowledgebasematrixInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    //for index
    public KnowledgebasematrixInfo(int knowledgebasematrixId, String name, String assettypeName, int pcount, int editcount, int status) {
        this.knowledgebasematrixId = knowledgebasematrixId;
        this.name = name;
        this.assettypeName = assettypeName;
        this.pcount = pcount;
        this.editcount = editcount;
        this.status = status;
    }

    public KnowledgebasematrixInfo(int clientId, int assetId, String clientName, String clientAssetName, int pcount, int assettypeId) {
        this.clientId = clientId;
        this.assetId = assetId;
        this.clientName = clientName;
        this.clientAssetName = clientAssetName;
        this.pcount = pcount;
        this.assettypeId = assettypeId;
    }

    //for add
    public KnowledgebasematrixInfo(int knowledgebasematrixId, String name, int assettypeId, String description, int status, int userId) {
        this.knowledgebasematrixId = knowledgebasematrixId;
        this.name = name;
        this.assettypeId = assettypeId;
        this.description = description;
        this.status = status;
        this.userId = userId;
    }

    //for view
    public KnowledgebasematrixInfo(int knowledgebasematrixId, String name, String assettypeName, String description, int status, int assettypeId) {
        this.knowledgebasematrixId = knowledgebasematrixId;
        this.name = name;
        this.assettypeName = assettypeName;
        this.description = description;
        this.status = status;
        this.assettypeId = assettypeId;
    }

    //for position list page
    public KnowledgebasematrixInfo(int clientId, String name, int count1, int count2, int count3, int positionId, int assetId, int statusCount) {
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
    public KnowledgebasematrixInfo(String name, int knowledgebasematrixId, int categoryId, int subcategoryId, int questionId, int count) {
        this.name = name;
        this.knowledgebasematrixId = knowledgebasematrixId;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.questionId = questionId;
        this.count = count;
    }

    public KnowledgebasematrixInfo(String position, String grade, String category, String subcategory, String topic) {
        this.position = position;
        this.grade = grade;
        this.category = category;
        this.subcategory = subcategory;
        this.topic = topic;
    }

    public int getKnowledgebasematrixId() {
        return knowledgebasematrixId;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public int getUserId() {
        return userId;
    }

    public int getDdlValue() {
        return ddlValue;
    }

    public String getDdlLabel() {
        return ddlLabel;
    }

    public String getAssettypeName() {
        return assettypeName;
    }

    public int getAssettypeId() {
        return assettypeId;
    }

    public int getPcount() {
        return pcount;
    }

    public int getEditcount() {
        return editcount;
    }

    public String getDescription() {
        return description;
    }

    public int getWellnessmatrixpositionId() {
        return wellnessmatrixpositionId;
    }

    public int getCount1() {
        return count1;
    }

    public int getCount2() {
        return count2;
    }

    public int getCount3() {
        return count3;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getWellnessmatrixdetailId() {
        return wellnessmatrixdetailId;
    }

    public int getCategoryId() {
        return categoryId;
    }

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

    public int getCount() {
        return count;
    }

    public String getPosition() {
        return position;
    }

    public String getGrade() {
        return grade;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getTopic() {
        return topic;
    }

}
