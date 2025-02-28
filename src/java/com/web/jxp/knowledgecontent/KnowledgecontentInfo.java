package com.web.jxp.knowledgecontent;

public class KnowledgecontentInfo {

    private int categoryId;
    private String name;
    private int status;
    private int substatus;
    private int userId;
    private int ddlValue;
    private String ddlLabel;

    private int coursecount;
    private int subcategorycount;
    private int subcategoryId;
    private int courseattachmentId;
    private int courseId;
    private int count;
    public int topicattachmentid;
    public int type;
    private String description;
    private String filename;
    private String filenamehidden;
    private String subcategoryname;
    private String coursetypename;
    private String date;
    private String categoryname;
    private String topicname;
    private String assettypeids;
    private String assettypeNames;

    //for ddl
    public KnowledgecontentInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public KnowledgecontentInfo(int categoryId, String name, int status, int userId) {
        this.categoryId = categoryId;
        this.name = name;
        this.status = status;
        this.userId = userId;

    }

    public KnowledgecontentInfo(int categoryId, String name, int status, String description, int coursecount, String assettypeids, int subcategoryId) {
        this.categoryId = categoryId;
        this.name = name;
        this.status = status;
        this.description = description;
        this.coursecount = coursecount;
        this.subcategoryId = subcategoryId;
        this.assettypeids = assettypeids;
    }

    public KnowledgecontentInfo(int categoryId, String name, int status, String description, int coursecount, int subcategoryId, String categoryname) {
        this.categoryId = categoryId;
        this.name = name;
        this.status = status;
        this.description = description;
        this.coursecount = coursecount;
        this.subcategoryId = subcategoryId;
        this.categoryname = categoryname;
    }

    public KnowledgecontentInfo(int categoryId, String name, int status, int subcategorycount, int coursecount) {
        this.categoryId = categoryId;
        this.name = name;
        this.status = status;
        this.subcategorycount = subcategorycount;
        this.coursecount = coursecount;

    }

    public KnowledgecontentInfo(int categoryId, String name, int status, int userId, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.description = description;
    }

    public KnowledgecontentInfo(int categoryId, int subcategoryId, String name, int status, int userId, String description) {
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.description = description;
    }

    public KnowledgecontentInfo(int categoryId, int subcategoryId, String topicname, int status, int userId, String description, int courseId, String assettypeids) {
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.status = status;
        this.userId = userId;
        this.description = description;
        this.courseId = courseId;
        this.topicname = topicname;
        this.assettypeids = assettypeids;
    }

    public KnowledgecontentInfo(int categoryId, String coursename, int status, String description,
            String subcategoryname, int subcategoryId, int courseId, int count, String assettypeNames, int substatus) {
        this.categoryId = categoryId;
        this.name = coursename;
        this.status = status;
        this.description = description;
        this.subcategoryname = subcategoryname;
        this.subcategoryId = subcategoryId;
        this.courseId = courseId;
        this.count = count;
        this.assettypeNames = assettypeNames;
        this.substatus = substatus;
    }

    public KnowledgecontentInfo(int courseattachmentId, String filename, String date, String name) {
        this.courseattachmentId = courseattachmentId;
        this.filename = filename;
        this.date = date;
        this.name = name;
    }

    //excel
    public KnowledgecontentInfo(String categoryname, String subcategoryname, String coursename) {
        this.categoryname = categoryname;
        this.subcategoryname = subcategoryname;
        this.topicname = coursename;
    }

    public KnowledgecontentInfo(int topicattachmentid, int type, String displayname, String filename) {
        this.topicattachmentid = topicattachmentid;
        this.type = type;
        this.name = displayname;
        this.filename = filename;
    }

    public int getCount() {
        return count;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getDate() {
        return date;
    }

    public int getCourseattachmentId() {
        return courseattachmentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getSubcategoryname() {
        return subcategoryname;
    }

    public String getCoursetypename() {
        return coursetypename;
    }

    public int getCoursecount() {
        return coursecount;
    }

    public int getSubcategorycount() {
        return subcategorycount;
    }

    public int getCategoryId() {
        return categoryId;
    }

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

    public String getDescription() {
        return description;
    }

    public String getFilename() {
        return filename;
    }

    public String getFilenamehidden() {
        return filenamehidden;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public String getTopicname() {
        return topicname;
    }

    public String getAssettypeids() {
        return assettypeids;
    }

    public int getTopicattachmentid() {
        return topicattachmentid;
    }

    public int getType() {
        return type;
    }

    public String getAssettypeNames() {
        return assettypeNames;
    }

    /**
     * @return the substatus
     */
    public int getSubstatus() {
        return substatus;
    }

}
