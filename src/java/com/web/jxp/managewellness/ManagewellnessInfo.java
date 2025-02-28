package com.web.jxp.managewellness;

public class ManagewellnessInfo
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
    
    private  int questCount;
    private String repeatvalue;
    private String fromdate;
    private String todate;
    private  int surveyId;
    private String question;
    private String answer;
    private String completedondate;
    private  int schedulecb;
    
    //for ddl
    public ManagewellnessInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    
    public ManagewellnessInfo(String name, String positionName, int candidateId)
    {
        this.name = name;
        this.positionName = positionName;
        this.candidateId = candidateId;
    }
    public ManagewellnessInfo(String categoryName, String subcategoryName, String  repeatvalue, int schedulecb)
    {
        this.categoryName = categoryName;
        this.subcategoryName = subcategoryName;
        this.repeatvalue = repeatvalue;
        this.schedulecb = schedulecb;
    }
    public ManagewellnessInfo(int surveyId, int status, String date, String subcategoryName , String categoryName, String completedondate)
    {
        this.surveyId = surveyId;
        this.status = status;
        this.date = date;
        this.subcategoryName = subcategoryName;
        this.categoryName = categoryName;
        this.completedondate = completedondate;
    }
    
    public ManagewellnessInfo( String subcategoryName, String categoryName, String assetName, String clientName, int subcategoryId, int categoryId)
    {
        this.subcategoryName = subcategoryName;
        this.categoryName = categoryName;
        this.assetName = assetName;
        this.clientName = clientName;
        this.subcategoryId = subcategoryId;
        this.categoryId = categoryId;
    }
    public ManagewellnessInfo(String question, String answer)
    {
        this.question = question;
        this.answer = answer;
    }
    public ManagewellnessInfo( int mcrewrotationId, int mcourseId, String alink, String completeby)
    {
        this.crewrotationId = mcrewrotationId;
        this.courseId = mcourseId;
        this.alink = alink;
        this.completeby = completeby;
    }
    public ManagewellnessInfo(int positionId,int courseId,String courseName,String categoryName,String subcategoryName,String coursetype, 
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
    public ManagewellnessInfo(int crewrotationId,String name, String positionName, int total, 
         int subcategoryId, String formdate,String todate, String categoryName, String subcategoryName, int pcount,int questCount, String repeatvalue, int schedulecb, int candidateId, int categoryId)
    {
        this.crewrotationId = crewrotationId;
        this.subcategoryId = subcategoryId;
        this.name = name;
        this.positionName = positionName;
        this.pcount = pcount;
        this.questCount = questCount;
        this.total = total;
        this.fromdate = formdate;
        this.todate = todate;
        this.categoryName = categoryName;
        this.subcategoryName = subcategoryName;
        this.repeatvalue = repeatvalue;
        this.schedulecb = schedulecb;
        this.candidateId = candidateId;
        this.categoryId = categoryId;
    }
    //for basic detail
    public ManagewellnessInfo (int crewrotationId, int clientId, int clientassetId, String name, String positionName, 
        String clientName, String assetName, int positionId, int candidateId)
    {
        this.crewrotationId = crewrotationId;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.name = name;
        this.positionName = positionName;
        this.clientName = clientName;
        this.assetName = assetName;
        this.positionId = positionId;
        this.candidateId = candidateId;
    }
    
    //for course detail
    public ManagewellnessInfo (String courseName, String categoryName, String subcategoryName, String clientName, String assetName, int categoryId, int subcategoryId)
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
    public ManagewellnessInfo(String categoryName, String subcategoryName, int questCount,String repeatvalue,String fromdate, String todate, int status, int subcategoryId, int schedulecb)
    {
        this.categoryName = categoryName;
        this.subcategoryName = subcategoryName;
        this.questCount = questCount;
        this.repeatvalue = repeatvalue;
        this.fromdate = fromdate;
        this.todate = todate;
        this.status = status;
        this.subcategoryId = subcategoryId;
        this.schedulecb = schedulecb;
    }
    //for assign 2
    public ManagewellnessInfo(int crewrotationId, String name, String positionName, int questCount, int candidateId, int positionId)
    {
        this.crewrotationId = crewrotationId;
        this.name = name;
        this.positionName = positionName;
        this.questCount = questCount;
        this.candidateId = candidateId;
        this.positionId = positionId;
    }
    public ManagewellnessInfo(int courseattachmentId, String filename, String date, String name)
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

    public int getQuestCount() {
        return questCount;
    }

    public String getRepeatvalue() {
        return repeatvalue;
    }

    public String getFromdate() {
        return fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getCompletedondate() {
        return completedondate;
    }

    public int getSchedulecb() {
        return schedulecb;
    }

}
