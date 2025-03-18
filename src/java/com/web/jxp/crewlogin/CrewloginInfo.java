package com.web.jxp.crewlogin;

public class CrewloginInfo {

    private int crewloginId;
    private String name;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;
    private int candidateId;
    private String emailId;
    private String submissionDate;
    private String feedback;
    private String doGetFeedbackList;
    private String clientname;
    private String assetname;
    private String position;
    private int crewrotationId;
    private int questioncount;
    private int positionId;
    private int clientId;
    private int clientassetId;

    // for BMI
    private double height;
    private double weight;

    //for wellness
    private int mobwellnessid;
    private int dailysteps;
    private String sleeptime;
    private String wakeuptime;
    private double sleephours;
    private double activityhours;
    private double standhours;
    private String date;

    //for ddl
    public CrewloginInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    //for BIM
    public CrewloginInfo(double height, double weight) {
        this.height = height;
        this.weight = weight;
    }

    public CrewloginInfo(int crewrotationId, int candidateId, int positionId, String name, String emailId) {
        this.crewrotationId = crewrotationId;
        this.candidateId = candidateId;
        this.positionId = positionId;
        this.name = name;
        this.emailId = emailId;
    }

    public CrewloginInfo(int crewrotationId, int candidateId, int positionId, String name, String emailId,
            int clientId, int clientassetId, String clientname, String assetname, String position) {
        this.crewrotationId = crewrotationId;
        this.candidateId = candidateId;
        this.positionId = positionId;
        this.name = name;
        this.emailId = emailId;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.clientname = clientname;
        this.assetname = assetname;
        this.position = position;
    }

    public CrewloginInfo(int crewloginId, String feedback, String submissionDate, int status, int questionCount) {
        this.crewloginId = crewloginId;
        this.feedback = feedback;
        this.submissionDate = submissionDate;
        this.status = status;
        this.questioncount = questionCount;
    }

    public CrewloginInfo(int mobwellnessid, int dailysteps, String sleeptime, String wakeuptime, double sleephours, double activityhours,
            double standhours, String date) {
        this.mobwellnessid = mobwellnessid;
        this.dailysteps = dailysteps;
        this.sleeptime = sleeptime;
        this.wakeuptime = wakeuptime;
        this.sleephours = sleephours;
        this.activityhours = activityhours;
        this.standhours = standhours;
        this.date = date;
    }

    /**
     * @return the crewloginId
     */
    public int getCoursetypeId() {
        return crewloginId;
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

    public int getCrewloginId() {
        return crewloginId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public String getEmailId() {
        return emailId;
    }

    /**
     * @return the crewrotationId
     */
    public int getCrewrotationId() {
        return crewrotationId;
    }

    /**
     * @return the submissionDate
     */
    public String getSubmissionDate() {
        return submissionDate;
    }

    /**
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * @return the questioncount
     */
    public int getQuestioncount() {
        return questioncount;
    }

    /**
     * @return the doGetFeedbackList
     */
    public String getDoGetFeedbackList() {
        return doGetFeedbackList;
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
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

    public String getClientname() {
        return clientname;
    }

    public String getAssetname() {
        return assetname;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public int getMobwellnessid() {
        return mobwellnessid;
    }

    public int getDailysteps() {
        return dailysteps;
    }

    public String getSleeptime() {
        return sleeptime;
    }

    public String getWakeuptime() {
        return wakeuptime;
    }

    public double getSleephours() {
        return sleephours;
    }

    public double getActivityhours() {
        return activityhours;
    }

    public double getStandhours() {
        return standhours;
    }

    public String getDate() {
        return date;
    }

}
