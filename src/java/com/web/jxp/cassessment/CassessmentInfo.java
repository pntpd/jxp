package com.web.jxp.cassessment;

public class CassessmentInfo {

    private int cassessmentId;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;

    private String Ids;
    private String name;
    private String position;
    private String vflagstatus;
    private String aflagstatus;
    private String scheduled;
    private String vdate;
    private String assessmentName;
    private String code;
    private String Scheduletype;
    private String deptName;
    private String rankName;

    private int pAssessmentId;
    private int assessorId;
    private int positionId;
    private int assessmentDetailId;
    private int assessmentId;
    private int minScore;
    private int passingFlag;
    private int candidateId;
    private int tz1;
    private int tz2;
    private int duration;
    private int coordinatorId;
    private int editflag;
    private int parameterId;
    private int attemptCount;
    private int expireflag;
    private double marks;

    private String remarks;
    private String datec;
    private String date;
    private String time;
    private String linkloc;
    private String timezone1;
    private String timezone2;

    private String assessmentquestion;
    private String assessmentanswertype;
    private String assparameter;
    private String parameter;
    private String mode;
    private String passing;

    private String assStatus;
    private String assessorName;
    private String dateTime;
    private String description;

    private String assessorDate;
    private String candidateDate;
    private String link;
    private String userName;
    private String candidateName;
    private String assessorCode;
    private String candidateCode;
    private String cEmail;
    private String uEmail;
    private String assettypeName;
    private int radio1;
    private int radio2;
    private int radio3;
    private int radio4;
    private int radio5;
    private int radio6;
    private int radio7;
    private int radio8;
    private int radio9;
    private int radio10;
    private int radio11;
    private int radio12;
    private int radio13;
    private int radio14;
    private int radio15;
    private int radio16;
    private int radio17;
    private int radio18;
    private String cdescription;

    public CassessmentInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public CassessmentInfo(int ddlValue, int cassessmentId, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.cassessmentId = cassessmentId;
        this.ddlLabel = ddlLabel;
    }

    public CassessmentInfo(int parameterid, String parameterName, String description) {
        this.parameterId = parameterid;
        this.name = parameterName;
        this.description = description;
    }

    public CassessmentInfo(int cassessmentId, String Ids, String name, String position, String vdate, 
        String vflagstatus, String aflagstatus, String scheduled, int userId, String assettypeName)
    {
        this.cassessmentId = cassessmentId;
        this.Ids = Ids;
        this.name = name;
        this.position = position;
        this.vdate = vdate;
        this.vflagstatus = vflagstatus;
        this.aflagstatus = aflagstatus;
        this.scheduled = scheduled;
        this.userId = userId;
        this.assettypeName = assettypeName;
    }

    public CassessmentInfo(int assessmentDetailId, String assessmentquestion, String assessmentanswertype, String assparameter) {
        this.assessmentDetailId = assessmentDetailId;
        this.assessmentquestion = assessmentquestion;
        this.assessmentanswertype = assessmentanswertype;
        this.assparameter = assparameter;
    }

    public CassessmentInfo(int assessmentDetailId, int assessmentId, String code, String assessmentName, int minScore, int passingFlag, String Scheduletype,
            int cassessmentId, int status, int expireflag) {
        this.assessmentDetailId = assessmentDetailId;
        this.assessmentId = assessmentId;
        this.code = code;
        this.assessmentName = assessmentName;
        this.minScore = minScore;
        this.passingFlag = passingFlag;
        this.Scheduletype = Scheduletype;
        this.cassessmentId = cassessmentId;
        this.status = status;
        this.expireflag = expireflag;
    }

    public CassessmentInfo(int candidateId, String name, String vflagstatus, String deptName, int positionId, String position, String rankName, int pass) {
        this.candidateId = candidateId;
        this.name = name;
        this.vflagstatus = vflagstatus;
        this.deptName = deptName;
        this.positionId = positionId;
        this.position = position;
        this.rankName = rankName;
        this.passingFlag = pass;
    }

    public CassessmentInfo(int assessmentId, int minScore, int passingflag, String parameter, String mode, String name, String passing, String assessmentName,
            String Scheduletype) {
        this.assessmentId = assessmentId;
        this.minScore = minScore;
        this.passingFlag = passingflag;
        this.parameter = parameter;
        this.mode = mode;
        this.name = name;
        this.passing = passing;
        this.assessmentName = assessmentName;
        this.Scheduletype = Scheduletype;
    }

    public CassessmentInfo(int cassessmentId, int candidateId, int passId, int assessorId, String linkloc, String mode, int tz1, int tz2, int duration,
            String date, String datec, int status, int uId, int attemptcount) {
        this.cassessmentId = cassessmentId;
        this.candidateId = candidateId;
        this.pAssessmentId = passId;
        this.assessorId = assessorId;
        this.linkloc = linkloc;
        this.mode = mode;
        this.tz1 = tz1;
        this.tz2 = tz2;
        this.duration = duration;
        this.date = date;
        this.datec = datec;
        this.status = status;
        this.userId = uId;
        this.attemptCount = attemptcount;
    }

    public CassessmentInfo(int cassessmentId, String linkloc, String mode, int duration, String datec, String assesor, String tz1, String tz2, String date,
            String time, int editflag, double marks, int status) {
        this.cassessmentId = cassessmentId;
        this.linkloc = linkloc;
        this.mode = mode;
        this.duration = duration;
        this.datec = datec;
        this.name = assesor;
        this.timezone1 = tz1;
        this.timezone2 = tz2;
        this.date = date;
        this.time = time;
        this.editflag = editflag;
        this.marks = marks;
        this.status = status;
    }

    public CassessmentInfo(int candidateId, int paId, int assessorId, String linkloc, String mode, int coordinatorId, int tz1, int tz2, int duration,
            String date, String time, String datec) {
        this.candidateId = candidateId;
        this.pAssessmentId = paId;
        this.assessorId = assessorId;
        this.linkloc = linkloc;
        this.mode = mode;
        this.coordinatorId = coordinatorId;
        this.tz1 = tz1;
        this.tz2 = tz2;
        this.duration = duration;
        this.date = date;
        this.time = time;
        this.datec = datec;
    }

    public CassessmentInfo(int cassessmentId, String name, String position, String statusValue, double marks, String assessorName, String date,
        String time, String dateTime, int minScore, int candidateId, String assessmentName, int status, 
        int paId, String assettypeName) {
        this.cassessmentId = cassessmentId;
        this.name = name;
        this.position = position;
        this.assStatus = statusValue;
        this.marks = marks;
        this.assessorName = assessorName;
        this.date = date;
        this.time = time;
        this.dateTime = dateTime;
        this.minScore = minScore;
        this.candidateId = candidateId;
        this.assessmentName = assessmentName;
        this.status = status;
        this.pAssessmentId = paId;
        this.assettypeName = assettypeName;
    }

    public CassessmentInfo(String mode, String linkloc, int duration, String date, String time, String assessmentName, String name, String remarks, double marks,
            String parameterid, int minScore, int passFlag, int assessmentId, int paid, int assessorId, int coordinateId, String Scheduletype) {
        this.mode = mode;
        this.linkloc = linkloc;
        this.duration = duration;
        this.date = date;
        this.time = time;
        this.assessmentName = assessmentName;
        this.name = name;
        this.remarks = remarks;
        this.marks = marks;
        this.parameter = parameterid;
        this.minScore = minScore;
        this.passingFlag = passFlag;
        this.assessmentId = assessmentId;
        this.pAssessmentId = paid;
        this.assessorId = assessorId;
        this.coordinatorId = coordinateId;
        this.Scheduletype = Scheduletype;
    }

    public CassessmentInfo(String assessorDate, String candidateDate, String mode, String linkstr, String userName, String candidateName,
            String assessorCode, String candidateCode, String assessmentName, String cEmail, String uEmail) {
        this.assessorDate = assessorDate;
        this.candidateDate = candidateDate;
        this.mode = mode;
        this.link = linkstr;
        this.userName = userName;
        this.candidateName = candidateName;
        this.assessorCode = assessorCode;
        this.candidateCode = candidateCode;
        this.assessmentName = assessmentName;
        this.cEmail = cEmail;
        this.uEmail = uEmail;
    }

    public CassessmentInfo(int parameterid, String parameterName, double marks) {
        this.parameterId = parameterid;
        this.name = parameterName;
        this.marks = marks;
    }
    
    public CassessmentInfo( int candidateId, int radio1, int radio2, int radio3, int  radio4, int  radio5, int radio6, int radio7, int radio8, int radio9, int radio10,
            int radio11, int radio12, int radio13, int radio14, int radio15, int radio16, int radio17, int radio18, String cdescription, int status, int positionId)
    {
        this.candidateId = candidateId;
        this.radio1 = radio1;
        this.radio2 = radio2;
        this.radio3 = radio3;
        this.radio4 = radio4;
        this.radio5 = radio5;
        this.radio6 = radio6;
        this.radio7 = radio7;
        this.radio8 = radio8;
        this.radio9 = radio9;
        this.radio10 = radio10;
        this.radio11 = radio11;
        this.radio12 = radio12;
        this.radio13 = radio13;
        this.radio14 = radio14;
        this.radio15 = radio15;
        this.radio16 = radio16;
        this.radio17 = radio17;
        this.radio18 = radio18;
        this.cdescription = cdescription;
        this.status = status;
        this.positionId = positionId;
            
    }

    public String getAssessorDate() {
        return assessorDate;
    }

    public String getCandidateDate() {
        return candidateDate;
    }

    public String getLink() {
        return link;
    }

    public String getUserName() {
        return userName;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public String getAssessorCode() {
        return assessorCode;
    }

    public String getCandidateCode() {
        return candidateCode;
    }

    public String getcEmail() {
        return cEmail;
    }

    public String getuEmail() {
        return uEmail;
    }

    public double getMarks() {
        return marks;
    }

    public String getAssStatus() {
        return assStatus;
    }

    public String getAssessorName() {
        return assessorName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getAssessmentDetailId() {
        return assessmentDetailId;
    }

    public String getAssessmentquestion() {
        return assessmentquestion;
    }

    public String getAssessmentanswertype() {
        return assessmentanswertype;
    }

    public String getAssparameter() {
        return assparameter;
    }

    public String getVdate() {
        return vdate;
    }

    public int getCassessmentId() {
        return cassessmentId;
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

    public String getIds() {
        return Ids;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public int getpAssessmentId() {
        return pAssessmentId;
    }

    public int getAssessorId() {
        return assessorId;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getVflagstatus() {
        return vflagstatus;
    }

    public String getAflagstatus() {
        return aflagstatus;
    }

    public String getScheduled() {
        return scheduled;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public String getCode() {
        return code;
    }

    public String getScheduletype() {
        return Scheduletype;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public int getMinScore() {
        return minScore;
    }

    public int getPassingFlag() {
        return passingFlag;
    }

    /**
     * @return the deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @return the candidateId
     */
    public int getCandidateId() {
        return candidateId;
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
    }

    /**
     * @return the rankName
     */
    public String getRankName() {
        return rankName;
    }

    /**
     * @return the parameter
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @return the passing
     */
    public String getPassing() {
        return passing;
    }

    /**
     * @return the linkloc
     */
    public String getLinkloc() {
        return linkloc;
    }

    public int getTz1() {
        return tz1;
    }

    public int getTz2() {
        return tz2;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    public String getTimezone1() {
        return timezone1;
    }

    public String getTimezone2() {
        return timezone2;
    }

    public String getDatec() {
        return datec;
    }

    public int getCoordinatorId() {
        return coordinatorId;
    }

    /**
     * @return the editflag
     */
    public int getEditflag() {
        return editflag;
    }

    public int getParameterId() {
        return parameterId;
    }

    public String getDescription() {
        return description;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public int getExpireflag() {
        return expireflag;
    }

    /**
     * @return the assettypeName
     */
    public String getAssettypeName() {
        return assettypeName;
    }

    public int getRadio1() {
        return radio1;
    }

    public int getRadio2() {
        return radio2;
    }

    public int getRadio3() {
        return radio3;
    }

    public int getRadio4() {
        return radio4;
    }

    public int getRadio5() {
        return radio5;
    }

    public int getRadio6() {
        return radio6;
    }

    public int getRadio7() {
        return radio7;
    }

    public int getRadio8() {
        return radio8;
    }

    public int getRadio9() {
        return radio9;
    }

    public int getRadio10() {
        return radio10;
    }

    public int getRadio11() {
        return radio11;
    }

    public int getRadio12() {
        return radio12;
    }

    public int getRadio13() {
        return radio13;
    }

    public int getRadio14() {
        return radio14;
    }

    public int getRadio15() {
        return radio15;
    }

    public int getRadio16() {
        return radio16;
    }

    public int getRadio17() {
        return radio17;
    }

    public int getRadio18() {
        return radio18;
    }

    public String getCdescription() {
        return cdescription;
    }

}
