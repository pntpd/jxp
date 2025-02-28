package com.web.jxp.managetraining;

public class ManagetrainingInfo
{    
    private int ddlValue;
    private String ddlLabel;
    private int crewrotationId;
    private int candidateId;
    private String name;
    private String positionName;
    private int pcount;
    private int total;
    private double percent;
    private int courseId;
    private String courseName;
    private String categoryName;
    private String subcategoryName;
    private int clientId;
    private int clientassetId;
    private String clientName;
    private String assetName;
    private int positionId;
    private String coursetype;
    private String level;
    private int status;
    private String statusval;
    private String date;
    private int clientmatrixdetailid;
    private String priority;
    private String courseNamerel;
    private String description;
    private String alink;
    private String completeby;
    private int courseattachmentId;
    private String filename;
    private int categoryId;
    private int subcategoryId;
    private int coursenameId;
    private String url;
    
    private int positionId2;
    private String position2;
    
    //for ddl
    public ManagetrainingInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    
    public ManagetrainingInfo(String name, String positionName, int candidateId)
    {
        this.name = name;
        this.positionName = positionName;
        this.candidateId = candidateId;
    }
    
    public ManagetrainingInfo(String name,int positionId, String positionName,int positionId2,String position2, int candidateId)
    {
        this.name = name;
        this.positionId = positionId;
        this.positionName = positionName;
        this.positionId2 = positionId2;
        this.position2 = position2;
        this.candidateId = candidateId;
    }
    public ManagetrainingInfo( int candidateId, int mcourseId, String alink, String completeby)
    {
        this.candidateId = candidateId;
        this.courseId = mcourseId;
        this.alink = alink;
        this.completeby = completeby;
    }
    public ManagetrainingInfo(int positionId,int courseId,String courseName,String categoryName,String subcategoryName,String coursetype, 
            String level,String priority,String courseNamerel, String description, int pcount, int subcategoryId, int categoryId, int coursenameId)
    {
        this.positionId = positionId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.categoryName = categoryName;
        this.subcategoryName = subcategoryName;
        this.coursetype = coursetype;
        this.level = level;
        this.priority = priority;
        this.courseNamerel = courseNamerel;
        this.description = description;
        this.pcount = pcount;
        this.subcategoryId = subcategoryId;
        this.categoryId = categoryId;
        this.coursenameId = coursenameId;
    }
    //for index 1
    public ManagetrainingInfo(int positionId, int candidateId, String name, String positionName, int pcount, int total, 
        double percent, int courseId, String courseName, String categoryName, String subcategoryName)
    {
        this.positionId = positionId;
        this.candidateId = candidateId;
        this.name = name;
        this.positionName = positionName;
        this.pcount = pcount;
        this.total = total;
        this.percent = percent;
        this.courseId = courseId;
        this.courseName = courseName;
        this.categoryName = categoryName;
        this.subcategoryName = subcategoryName;
    }
    //for basic detail
    public ManagetrainingInfo (int candidateId, int clientId, int clientassetId, String name, String positionName, 
        String clientName, String assetName, int positionId, int positionId2, String position2)
    {
        this.candidateId = candidateId;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.name = name;
        this.positionName = positionName;
        this.clientName = clientName;
        this.assetName = assetName;
        this.positionId = positionId;
        this.positionId2 = positionId2;
        this.position2 = position2;
    }
    
    //for course detail
    public ManagetrainingInfo (String courseName, String categoryName, String subcategoryName, String clientName, String assetName, int categoryId, int subcategoryId)
    {
        this.courseName = courseName;
        this.categoryName = categoryName;
        this.subcategoryName = subcategoryName;
        this.clientName = clientName;
        this.assetName = assetName;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
    }
    //for assign 1
    public ManagetrainingInfo(int courseId, String courseName, String categoryName, String subcategoryName, 
        String coursetype, String level, int status, String statusval, String date, int clientmatrixdetailid, String filename, String url)
    {
        this.courseId = courseId;
        this.courseName = courseName;
        this.categoryName = categoryName;
        this.subcategoryName = subcategoryName;
        this.coursetype = coursetype;
        this.level = level;
        this.status = status;
        this.statusval = statusval;
        this.date = date;
        this.clientmatrixdetailid = clientmatrixdetailid;
        this.filename = filename;
        this.url = url;
    }
    //for assign 2
    public ManagetrainingInfo(int candidateId, String name, String positionName, String coursetype, 
        String level, int status, String statusval, String date, int clientmatrixdetailid, String filename, String url, int positionId)
    {
        this.candidateId = candidateId;
        this.name = name;
        this.positionName = positionName;
        this.coursetype = coursetype;
        this.level = level;
        this.status = status;
        this.statusval = statusval;
        this.date = date;
        this.clientmatrixdetailid = clientmatrixdetailid;
        this.filename = filename;
        this.url = url;
        this.positionId = positionId;
    }
    
    public ManagetrainingInfo(int courseattachmentId, String filename, String date, String name)
    {
        this.courseattachmentId = courseattachmentId;
        this.filename = filename;
        this.date = date;
        this.name = name;
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
     * @return the crewrotationId
     */
    public int getCrewrotationId() {
        return crewrotationId;
    }

    /**
     * @return the candidateId
     */
    public int getCandidateId() {
        return candidateId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the positionName
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * @return the pcount
     */
    public int getPcount() {
        return pcount;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @return the percent
     */
    public double getPercent() {
        return percent;
    }

    /**
     * @return the courseId
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * @return the courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @return the subcategoryName
     */
    public String getSubcategoryName() {
        return subcategoryName;
    }

    /**
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @return the clientassetId
     */
    public int getClientassetId() {
        return clientassetId;
    }

    /**
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @return the assetName
     */
    public String getAssetName() {
        return assetName;
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
    }

    /**
     * @return the coursetype
     */
    public String getCoursetype() {
        return coursetype;
    }

    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return the statusval
     */
    public String getStatusval() {
        return statusval;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    public int getClientmatrixdetailid() {
        return clientmatrixdetailid;
    }

    public String getPriority() {
        return priority;
    }

    public String getCourseNamerel() {
        return courseNamerel;
    }

    public String getDescription() {
        return description;
    }

    public String getAlink() {
        return alink;
    }

    public void setAlink(String alink) {
        this.alink = alink;
    }

    public String getCompleteby() {
        return completeby;
    }

    public void setCompleteby(String completeby) {
        this.completeby = completeby;
    }

    public int getCourseattachmentId() {
        return courseattachmentId;
    }

    public void setCourseattachmentId(int courseattachmentId) {
        this.courseattachmentId = courseattachmentId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public int getCoursenameId() {
        return coursenameId;
    }

    public void setCoursenameId(int coursenameId) {
        this.coursenameId = coursenameId;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the positionId2
     */
    public int getPositionId2() {
        return positionId2;
    }

    /**
     * @return the position2
     */
    public String getPosition2() {
        return position2;
    }

}
