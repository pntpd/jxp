package com.web.jxp.crewrotation;

public class CrewrotationInfo {

    private int jobpostId;
    private int ddlValue;
    private int ddltype;
    private int status;

    private int positionId;
    private int gradeId;
    private int clientId;
    private int clientassetId;
    private int shortlistId;
    private int candidateId;

    private String date;
    private String name;
    private String ddlLabel;
    private String position;
    private String clientName;
    private String clientAsset;
    private String photo;
    private String gradeName;

    private int total1;
    private int total2;
    private int total3;
    private int onShoretotal;
    private int offShoretotal;

    private int status1;
    private int status2;
    private int normal;
    private int delayed;
    private int extended;
    private String signon;
    private String signoff;
    private int crewrotationId;
    private int positionCount;
    private int docflagId;
    private int noofdays;

    private String expecteddate;
    private String fromdate;
    private String todate;
    private String remarks;
    private int activityId;
    private String activitystatus;
    private String offshorestatus;

    private String Yesvalues;
    private String Novalues;
    private String NAvalues;
    private String values;

    private int cractivityId;
    private int datedifference;
    private String statusvalue;
    private String country;
    private int trainingCount;
    private int rotationCount;

    private int userId;
    private int signonId;
    private int signoffId;
    private int type;
    private int subtype;
    private int activeflag;
    private int overstaydays;
    private int crewrota;
    private int norota;
    
    private String typevalue;
    private int positionId2;
    private String position2;

    //for ddl
    public CrewrotationInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public CrewrotationInfo(int ddlValue, String ddlLabel, int ddltype) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
        this.ddltype = ddltype;
    }

    public CrewrotationInfo(int ddlValue, String ddlLabel, String values) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
        this.values = values;
    }

    // new index
    public CrewrotationInfo(String clientName, String clientAsset, String Country, int status1, int normal, int delayed, int extended, int clientId, int clientassetId,
            int positionCount, int crewrota) {

        this.clientName = clientName;
        this.clientAsset = clientAsset;
        this.country = Country;
        this.status1 = status1;
        this.normal = normal;
        this.delayed = delayed;
        this.extended = extended;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.positionCount = positionCount;
        this.crewrota = crewrota;
    }

    public CrewrotationInfo(int crewrotationId, int candidateId, int jobpostId, String name, String clientName, String position, String photo, String clientAsset,
            String country, String signon, String signoff, int docflagId, int noofdays, int status, String expecteddate, int activityId, String fromdate, String todate,
            String remarks, String activitystatus, int trainingCount, int rotationCount, int clientId, int clientassetId, int status1, int status2, String offshorestatus,
            int activeflag, int overstaydays, String position2, int positionId) {
        this.crewrotationId = crewrotationId;
        this.candidateId = candidateId;
        this.jobpostId = jobpostId;
        this.name = name;
        this.clientName = clientName;
        this.position = position;
        this.photo = photo;
        this.clientAsset = clientAsset;
        this.country = country;
        this.signon = signon;
        this.signoff = signoff;
        this.docflagId = docflagId;
        this.noofdays = noofdays;
        this.status = status;
        this.expecteddate = expecteddate;
        this.activityId = activityId;
        this.fromdate = fromdate;
        this.todate = todate;
        this.remarks = remarks;
        this.activitystatus = activitystatus;
        this.trainingCount = trainingCount;
        this.rotationCount = rotationCount;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.status1 = status1;
        this.status2 = status2;
        this.offshorestatus = offshorestatus;
        this.activeflag = activeflag;
        this.overstaydays = overstaydays;
        this.position2 = position2;
        this.positionId = positionId;
    }
    
    public CrewrotationInfo(int crewrotationId, int candidateId, int jobpostId, String name, String clientName, String position, String photo, String clientAsset,
            String country, String signon, String signoff, int docflagId, int noofdays, int status, String expecteddate, int activityId, String fromdate, String todate,
            String remarks, String activitystatus, int trainingCount, int rotationCount, int clientId, int clientassetId, int status1, int status2, String offshorestatus,
            int activeflag, int overstaydays, int positionId,int positionId2, String position2, int signonId, int signoffId) {
        this.crewrotationId = crewrotationId;
        this.candidateId = candidateId;
        this.jobpostId = jobpostId;
        this.name = name;
        this.clientName = clientName;
        this.position = position;
        this.photo = photo;
        this.clientAsset = clientAsset;
        this.country = country;
        this.signon = signon;
        this.signoff = signoff;
        this.docflagId = docflagId;
        this.noofdays = noofdays;
        this.status = status;
        this.expecteddate = expecteddate;
        this.activityId = activityId;
        this.fromdate = fromdate;
        this.todate = todate;
        this.remarks = remarks;
        this.activitystatus = activitystatus;
        this.trainingCount = trainingCount;
        this.rotationCount = rotationCount;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.status1 = status1;
        this.status2 = status2;
        this.offshorestatus = offshorestatus;
        this.activeflag = activeflag;        
        this.overstaydays = overstaydays;
        this.positionId = positionId;
        this.positionId2 = positionId2;
        this.position2 = position2;
        this.signonId = signonId;
        this.signoffId = signoffId;
    }

    public CrewrotationInfo(int crewrotationId, int cractivityId, String fromdate, String todate, int status, int activityId, int datedifference,
            String activitystatus, String statusvalue, int noofdays, int activeflag) {
        this.crewrotationId = crewrotationId;
        this.cractivityId = cractivityId;
        this.fromdate = fromdate;
        this.todate = todate;
        this.status = status;
        this.activityId = activityId;
        this.datedifference = datedifference;
        this.activitystatus = activitystatus;
        this.statusvalue = statusvalue;
        this.noofdays = noofdays;
        this.activeflag = activeflag;
    }

    public CrewrotationInfo(int crewrotationId, String Yesvalues, String Novalues, String NAvalues) {
        this.crewrotationId = crewrotationId;
        this.Yesvalues = Yesvalues;
        this.Novalues = Novalues;
        this.NAvalues = NAvalues;
    }
    
    public CrewrotationInfo(int activityId, int positionId, int nooffdays) 
    {
        this.activityId = activityId;
        this.positionId = positionId;
        this.noofdays = nooffdays;
    }
    
    public CrewrotationInfo(int activityId, int positionId, int signonId, int signoffId) 
    {
        this.activityId = activityId;
        this.positionId = positionId;
        this.signonId = signonId;
        this.signoffId = signoffId;
    }

    public CrewrotationInfo(int activityId, String fromdate, String todate, String remarks, 
            int status, int userId, int signonId, int signoffId, int crewrotationId, 
            int cractivityId, String activitystatus, int activeflag, int positionId, 
            int clientassetId, String position, int positionId2, String position2) {
        this.activityId = activityId;
        this.fromdate = fromdate;
        this.todate = todate;
        this.remarks = remarks;
        this.status = status;
        this.userId = userId;
        this.signonId = signonId;
        this.signoffId = signoffId;
        this.crewrotationId = crewrotationId;
        this.cractivityId = cractivityId;
        this.activitystatus = activitystatus;
        this.activeflag = activeflag;
        this.positionId = positionId;
        this.clientassetId = clientassetId;
        this.position = position;
        this.positionId2 = positionId2;
        this.position2 = position2;
    }
    public CrewrotationInfo(int crewrotationId, int cractivityId, String fromdate, String todate, int status, int activityId, int datedifference,
            String activitystatus, String statusvalue, int noofdays, int activeflag, String candidatename,
            String client,String asset,String position, int positionId) {
        this.crewrotationId = crewrotationId;
        this.cractivityId = cractivityId;
        this.fromdate = fromdate;
        this.todate = todate;
        this.status = status;
        this.activityId = activityId;
        this.datedifference = datedifference;
        this.activitystatus = activitystatus;
        this.statusvalue = statusvalue;
        this.noofdays = noofdays;
        this.activeflag = activeflag;
        this.name = candidatename;
        this.clientName = client;
        this.clientAsset = asset;
        this.position = position;
        this.positionId = positionId;
    }

    public CrewrotationInfo(int signonId, String fromdate, String todate, String remarks, 
            int type, int subtype, int userId, int crewrotationId, int norota, int positionId) {
        this.signonId = signonId;
        this.fromdate = fromdate;
        this.todate = todate;
        this.remarks = remarks;
        this.type = type;
        this.subtype = subtype;
        this.userId = userId;
        this.crewrotationId = crewrotationId;
        this.norota = norota;
        this.positionId = positionId;
    }
    
    public CrewrotationInfo(String name, String position, String fromdate, String todate, String typevalue, int dateDifference) {

        this.name = name;
        this.position = position;
        this.fromdate = fromdate;
        this.todate = todate;
        this.typevalue = typevalue;
        this.datedifference = dateDifference;
    }   
    
    public CrewrotationInfo(int positionId, String position1, int positionId2, String position2)
    {
        this.positionId = positionId;
        this.position = position1;
        this.positionId2 = positionId2;
        this.position2 = position2;
    }
    
    public int getActiveflag() {
        return activeflag;
    }

    public int getType() {
        return type;
    }

    public int getSubtype() {
        return subtype;
    }

    public int getStatus2() {
        return status2;
    }

    public String getOffshorestatus() {
        return offshorestatus;
    }

    public int getUserId() {
        return userId;
    }

    public int getSignonId() {
        return signonId;
    }

    public int getSignoffId() {
        return signoffId;
    }

    public int getTrainingCount() {
        return trainingCount;
    }

    public int getRotationCount() {
        return rotationCount;
    }

    public String getValues() {
        return values;
    }

    public String getActivitystatus() {
        return activitystatus;
    }

    public String getExpecteddate() {
        return expecteddate;
    }

    public String getFromdate() {
        return fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public String getRemarks() {
        return remarks;
    }

    public int getActivityId() {
        return activityId;
    }

    public int getPositionCount() {
        return positionCount;
    }

    public int getStatus1() {
        return status1;
    }

    public int getNormal() {
        return normal;
    }

    public int getDelayed() {
        return delayed;
    }

    public int getExtended() {
        return extended;
    }

    public int getOffShoretotal() {
        return offShoretotal;
    }

    public int getOnShoretotal() {
        return onShoretotal;
    }

    public int getTotal1() {
        return total1;
    }

    public int getTotal2() {
        return total2;
    }

    public int getTotal3() {
        return total3;
    }

    public int getCractivityId() {
        return cractivityId;
    }

    public int getDatedifference() {
        return datedifference;
    }

    public String getStatusvalue() {
        return statusvalue;
    }

    public String getYesvalues() {
        return Yesvalues;
    }

    public String getNovalues() {
        return Novalues;
    }

    public String getNAvalues() {
        return NAvalues;
    }

    public int getNoofdays() {
        return noofdays;
    }

    public int getDocflagId() {
        return docflagId;
    }

    public String getSignon() {
        return signon;
    }

    public String getSignoff() {
        return signoff;
    }

    public int getCrewrotationId() {
        return crewrotationId;
    }

    public int getJobpostId() {
        return jobpostId;
    }

    public int getDdlValue() {
        return ddlValue;
    }

    public int getStatus() {
        return status;
    }

    public int getPositionId() {
        return positionId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getClientassetId() {
        return clientassetId;
    }

    public String getDate() {
        return date;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDdlLabel() {
        return ddlLabel;
    }

    public String getPosition() {
        return position;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientAsset() {
        return clientAsset;
    }

    public String getGradeName() {
        return gradeName;
    }

    public String getName() {
        return name;
    }

    public int getShortlistId() {
        return shortlistId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public String getCountry() {
        return country;
    }

    public int getDdltype() {
        return ddltype;
    }

    public int getOverstaydays() {
        return overstaydays;
    }

    /**
     * @return the typevalue
     */
    public String getTypevalue() {
        return typevalue;
    }    

    public int getCrewrota() {
        return crewrota;
    }

    public int getNorota() {
        return norota;
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
