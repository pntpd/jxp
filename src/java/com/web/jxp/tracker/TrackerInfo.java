package com.web.jxp.tracker;

public class TrackerInfo
{    
    private int ddlValue;
    private String ddlLabel;
    private int crewrotationId;
    private int candidateId;
    private String name;
    private String positionName; 
    private int pcount;
    private int total;
    private int trackerId;
    private String role;
    private String passessmenttypeName;
    private String priorityName; 
    private int status;
    private String date;  
    private int clientId;
    private int clientassetId;
    private String assetName;
    private int positionId;
    private String clientName;
    private String statusval;
    private int pcodeId;
    private String code;
    private String description;
    private String completebydate1; 
    private String completebydate2; 
    private int userId;
    private String fileName;
    private String fileName2;
    private int fcroleId;
    private int passessmenttypeId;    
    private int priorityId;
    private int assettypeId;
    private int month;
    private String gradeName;
    private String userName;
    private String remarks;
    private String loginname1;
    private String loginname2;
    private String submitdate1;
    private String submitdate2;
    private int score;
    private int resultId;
    private String practicalremarks;
    private int prcaticalscore;
    private int resultId2;
    private int average;
    private String resultname1;
    private String resultname2;
    private String outcomeremarks;
    private String outcomeStr;
    private String courseName;
    private int outcomeId;    
    private int trainingId;  
    private int onlineflag;  
    private int noofdays;  
    private String appealremarks;
    private String appeal;
    private String appealreason;
    private String rejection;
    private String rejectionReason;
    private String category;
    private String question;
    private String answer;
    private String cvfile;
    private int positionId2;
    private String position2;
    
    //for ddl
    public TrackerInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    
    //for list 1
    public TrackerInfo(int positionId, int candidateId, String name, String positionName, 
        int pcount, int total, int pcodeId, String role, String passessmenttypeName, 
        String priorityName, int passessmenttypeId, int priorityId)
    {
        this.positionId = positionId;
        this.candidateId = candidateId;
        this.name = name;
        this.positionName = positionName;
        this.pcount = pcount;
        this.total = total;
        this.pcodeId = pcodeId;
        this.role = role;
        this.passessmenttypeName = passessmenttypeName;
        this.priorityName = priorityName;
        this.passessmenttypeId = passessmenttypeId;
        this.priorityId = priorityId;
    }
    
    //for assign 1
    public TrackerInfo(int trackerId, String role, String passessmenttypeName, String priorityName, 
        int status, String date, String statusval, int pcodeId, int fcroleId, int average)
    {
        this.trackerId = trackerId;
        this.role = role;
        this.passessmenttypeName = passessmenttypeName;
        this.priorityName = priorityName;
        this.status = status;
        this.date = date;
        this.statusval = statusval;
        this.pcodeId = pcodeId;
        this.fcroleId = fcroleId;
        this.average = average;
    }
    //Basic Details
    public TrackerInfo(int crewrotationId, int clientId, int clientassetId, String name, String positionName, 
        String clientName, String assetName, int positionId, int positionId2, int assettypeId, String position2)
    {
        this.crewrotationId = crewrotationId;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.name = name;
        this.positionName = positionName;
        this.clientName = clientName;
        this.assetName = assetName;
        this.positionId = positionId;
        this.positionId2 = positionId2;
        this.assettypeId = assettypeId;
        this.position2 = position2;
    }
    //for detail in modal
    public TrackerInfo(String code, String role, String description, String completebydate1, 
        int trackerId, int status)
    {
        this.code = code;
        this.role = role;
        this.description = description;
        this.completebydate1 = completebydate1;
        this.trackerId = trackerId;
        this.status = status;
    }
    //for assessor list
    public TrackerInfo(int userId, String name, String fileName)
    {
        this.userId = userId;
        this.name = name;
        this.fileName = fileName;
    }
    //for multiple role
    public TrackerInfo(String role, String passessmenttypeName, String priorityName) 
    {
        this.role = role;
        this.passessmenttypeName = passessmenttypeName;
        this.priorityName = priorityName;
        this.clientName = clientName;
        this.assetName = assetName;
        this.pcodeId = pcodeId;
    }
    //for basic detail 2
    public TrackerInfo(String role, String clientName, String assetName, int pcodeId, int total) 
    {
        this.role = role;
        this.clientName = clientName;
        this.assetName = assetName;
        this.pcodeId = pcodeId;
        this.total = total;
    }
    //for assign list 2
    public TrackerInfo(int candidateId, String name, String positionName, int status, 
        String statusval, String date, int trackerId, int average, int assessorId, int positionId)
    {
        this.candidateId = candidateId;
        this.name = name;
        this.positionName = positionName;
        this.status = status;
        this.statusval = statusval;
        this.date = date;
        this.trackerId = trackerId;
        this.average = average;
        this.positionId = positionId;
    }
    public TrackerInfo(String code, String role, int trackerId)
    {
        this.code = code;
        this.role = role;
        this.trackerId = trackerId;
    }
    //For UpdateTracker Modal
    public TrackerInfo(String name, String positionname, String role, String assessment, 
            String priority, int month, String username, int status, String completeddate, 
            String description, String file1, String file2, String loginname1, String loginname2,
            String submitdate1, String submitdate2, String remarks, int score, String remarks2, 
            int score2, int resultId1, int resultId2, int average, String completeddate2, String resultname1, 
            String resultname2, int clientassetId, int positionId, String courseName, int outcomeId, 
            String remarks3, String remarks4, String appeal, String rejectionReason, String rejection, 
            int onlineflag, String cvfile, int assettypeId, int noofdays,  String outcomeStr, int positionId2, String position2)
    {
        this.name = name;
        this.positionName = positionname;
        this.role = role;
        this.passessmenttypeName = assessment;
        this.priorityName = priority;
        this.month = month;
        this.userName  = username;
        this.status = status;
        this.completebydate1 = completeddate;
        this.description = description;
        this.fileName = file1;
        this.fileName2 = file2;
        this.loginname1 = loginname1;
        this.loginname2 = loginname2;
        this.submitdate1 = submitdate1;
        this.submitdate2 = submitdate2;     
        this.remarks = remarks;
        this.score = score;
        this.practicalremarks = remarks2;
        this.prcaticalscore = score2;
        this.resultId = resultId1;
        this.resultId2 = resultId2;
        this.average = average;
        this.completebydate2 = completeddate2;
        this.resultname1 = resultname1;
        this.resultname2 = resultname2;   
        this.clientassetId = clientassetId;
        this.positionId = positionId;
        this.courseName = courseName;
        this.outcomeId = outcomeId;
        this.outcomeremarks = remarks3;
        this.appealremarks = remarks4;
        this.appeal = appeal;
        this.rejectionReason = rejectionReason;
        this.rejection = rejection;
        this.onlineflag = onlineflag;
        this.cvfile = cvfile;
        this.assettypeId = assettypeId;
        this.noofdays = noofdays;
        this.outcomeStr = outcomeStr;
        this.positionId2 = positionId2;
        this.position2 = position2;
    }    
    
    public TrackerInfo(String name, String positionname, String role, String assessment, 
            String priority, int month, String username, int status, String completeddate, String description)
    {
        this.name = name;
        this.positionName = positionname;
        this.role = role;
        this.passessmenttypeName = assessment;
        this.priorityName = priority;
        this.month = month;
        this.userName  = username;
        this.status = status;
        this.completebydate1 = completeddate;
        this.description = description;
        
    }    
    
    public TrackerInfo(int trackerId, String remarks, int score, int resultId, String fileName1, int status,
            String fileName2, String practicalremarks, int prcaticalscore,  int resultId2, int average )
    {
        this.trackerId = trackerId;
        this.remarks = remarks;
        this.score = score;
        this.resultId = resultId;
        this.fileName = fileName1;
        this.status = status;
        this.fileName2 = fileName2;
        this.practicalremarks = practicalremarks;
        this.prcaticalscore = prcaticalscore;
        this.resultId2 = resultId2;
        this.average = average;
    }
    
    public TrackerInfo(int trackerId, String remarks, int score, int resultId, String fileName1, int status,
            String fileName2, String practicalremarks, int prcaticalscore,  int resultId2, int average, String loginname1, String loginname2 )
    {
        this.trackerId = trackerId;
        this.remarks = remarks;
        this.score = score;
        this.resultId = resultId;
        this.fileName = fileName1;
        this.status = status;
        this.fileName2 = fileName2;
        this.practicalremarks = practicalremarks;
        this.prcaticalscore = prcaticalscore;
        this.resultId2 = resultId2;
        this.average = average;
        this.loginname1 = loginname1;
        this.loginname2 = loginname2;
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
     * @return the trackerId
     */
    public int getTrackerId() {
        return trackerId;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
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
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
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
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }
    
    /**
     * @return the statusval
     */
    public String getStatusval() {
        return statusval;
    }

    /**
     * @return the pcodeId
     */
    public int getPcodeId() {
        return pcodeId;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the completebydate1
     */
    public String getCompletebydate1() {
        return completebydate1;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return the fcroleId
     */
    public int getFcroleId() {
        return fcroleId;
    }

    /**
     * @return the passessmenttypeId
     */
    public int getPassessmenttypeId() {
        return passessmenttypeId;
    }

    /**
     * @return the priorityId
     */
    public int getPriorityId() {
        return priorityId;
    }

    /**
     * @return the assettypeId
     */
    public int getAssettypeId() {
        return assettypeId;
    }

    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @return the gradeName
     */
    public String getGradeName() {
        return gradeName;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @return the resultId
     */
    public int getResultId() {
        return resultId;
    }

    /**
     * @return the fileName2
     */
    public String getFileName2() {
        return fileName2;
    }

    /**
     * @return the practicalremarks
     */
    public String getPracticalremarks() {
        return practicalremarks;
    }

    /**
     * @return the prcaticalscore
     */
    public int getPrcaticalscore() {
        return prcaticalscore;
    }

    /**
     * @return the resultId2
     */
    public int getResultId2() {
        return resultId2;
    }

    /**
     * @return the loginname1
     */
    public String getLoginname1() {
        return loginname1;
    }

    /**
     * @return the loginname2
     */
    public String getLoginname2() {
        return loginname2;
    }

    /**
     * @return the submitdate1
     */
    public String getSubmitdate1() {
        return submitdate1;
    }

    /**
     * @return the submitdate2
     */
    public String getSubmitdate2() {
        return submitdate2;
    }

    /**
     * @return the average
     */
    public int getAverage() {
        return average;
    }

    /**
     * @return the completebydate2
     */
    public String getCompletebydate2() {
        return completebydate2;
    }

    /**
     * @return the resultname1
     */
    public String getResultname1() {
        return resultname1;
    }

    /**
     * @return the resultname2
     */
    public String getResultname2() {
        return resultname2;
    }

    /**
     * @return the outcomeremarks
     */
    public String getOutcomeremarks() {
        return outcomeremarks;
    }

    /**
     * @return the outcomeId
     */
    public int getOutcomeId() {
        return outcomeId;
    }

    /**
     * @return the trainingId
     */
    public int getTrainingId() {
        return trainingId;
    }

    /**
     * @return the courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * @return the outcomeStr
     */
    public String getOutcomeStr() {
        return outcomeStr;
    }

    /**
     * @return the appealremarks
     */
    public String getAppealremarks() {
        return appealremarks;
    }

    /**
     * @return the appeal
     */
    public String getAppeal() {
        return appeal;
    }

    /**
     * @return the appealreason
     */
    public String getAppealreason() {
        return appealreason;
    }

    /**
     * @return the rejection
     */
    public String getRejection() {
        return rejection;
    }

    /**
     * @return the rejectionReason
     */
    public String getRejectionReason() {
        return rejectionReason;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @return the onlineflag
     */
    public int getOnlineflag() {
        return onlineflag;
    }

    /**
     * @return the cvfile
     */
    public String getCvfile() {
        return cvfile;
    }

    /**
     * @return the noofdays
     */
    public int getNoofdays() {
        return noofdays;
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
