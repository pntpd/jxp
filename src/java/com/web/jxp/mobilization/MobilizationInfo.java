package com.web.jxp.mobilization;

public class MobilizationInfo {

    private int jobpostId;
    private int ddlValue;
    private int ddltype;
    private int status;
    private String ddlLabel;

    private int clientId;
    private int clientassetId;
    private int opening;
    private int shortlistId;
    private int candidateId;
    private int crewrotationId;
    private int status1;
    private int positionCount;
    private int onboardCount;
    private int totalnoofopenings;
    private int totalnoofongoing;

    private int mobilizationId;
    private int userId;
    private int batchId;
    private int type;
    private int flag1;
    private int flag2;
    private int assettypeid;

    private String clientName;
    private String clientAsset;
    private String country;
    private String name;
    private String position;
    private String grade;
    private String photo;

    private String val1;
    private String val2;
    private String val3;
    private String val4;
    private String val5;
    private String val6;
    private String val7;
    private String val8;
    private String val9;
    private String vald9;
    private String valt9;
    private String val10;
    private String vald10;
    private String valt10;
    private String filename;
    private String regdate;
    private String moddate;

    private String candidatemail;
    private String clientemail;
    private String date;

    //for ddl
    public MobilizationInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public MobilizationInfo(int ddlValue, String ddlLabel, int ddltype) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
        this.ddltype = ddltype;
    }

    // new index
    public MobilizationInfo(int crewrotationId, String clientName, String clientAsset, String Country, int status1, int clientId, int clientassetId, int jobpostid) {

        this.crewrotationId = crewrotationId;
        this.clientName = clientName;
        this.clientAsset = clientAsset;
        this.country = Country;
        this.status1 = status1;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.jobpostId = jobpostid;
    }

    public MobilizationInfo(int clientId, int clientassetId, String clientName, String clientAsset, String country, int positionCount, int onboardCount, int totalnoofongoing,
            int assettypeid) {
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.clientName = clientName;
        this.clientAsset = clientAsset;
        this.country = country;
        this.positionCount = positionCount;
        this.onboardCount = onboardCount;
        this.totalnoofongoing = totalnoofongoing;
        this.assettypeid = assettypeid;
    }

    public MobilizationInfo(int crewrotationId, int candidateId, String name, String position, String grade, String photo, String clientName, String clientAsset, String country,
            int jobpostId, int flag1, int flag2) {
        this.crewrotationId = crewrotationId;
        this.candidateId = candidateId;
        this.name = name;
        this.position = position;
        this.grade = grade;
        this.photo = photo;
        this.clientName = clientName;
        this.clientAsset = clientAsset;
        this.country = country;
        this.jobpostId = jobpostId;
        this.flag1 = flag1;
        this.flag2 = flag2;
    }

    public MobilizationInfo(int batchId, int type, int mobilizationId, String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8, String val9, String val10,
            String filename, int status, int userId, String regdate, String moddate) {
        this.batchId = batchId;
        this.type = type;
        this.mobilizationId = mobilizationId;
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
        this.val5 = val5;
        this.val6 = val6;
        this.val7 = val7;
        this.val8 = val8;
        this.val9 = val9;
        this.val10 = val10;
        this.filename = filename;
        this.status = status;
        this.userId = userId;
        this.regdate = regdate;
        this.moddate = moddate;
    }

    public MobilizationInfo(int crewrotationid, int type, String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8, String val9,
            String val10, String filename, int status, int userId) {
        this.crewrotationId = crewrotationid;
        this.type = type;
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
        this.val5 = val5;
        this.val6 = val6;
        this.val7 = val7;
        this.val8 = val8;
        this.val9 = val9;
        this.val10 = val10;
        this.filename = filename;
        this.status = status;
        this.userId = userId;
    }

    public MobilizationInfo(int batchId, int type, int mobilizationId, String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8,
            String vald9, String valt9, String vald10, String valt10, String filename, int status) {
        this.batchId = batchId;
        this.type = type;
        this.mobilizationId = mobilizationId;
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
        this.val5 = val5;
        this.val6 = val6;
        this.val7 = val7;
        this.val8 = val8;
        this.vald9 = vald9;
        this.valt9 = valt9;
        this.vald10 = vald10;
        this.valt10 = valt10;
        this.filename = filename;
        this.status = status;
    }

    public MobilizationInfo(int candidateId, String name, String filename, int clientId, String clientname, int assetId, String assetname, String country, String position,
            String grade, int CrewId) {
        this.candidateId = candidateId;
        this.name = name;
        this.photo = filename;
        this.clientId = clientId;
        this.clientName = clientname;
        this.clientassetId = assetId;
        this.clientAsset = assetname;
        this.country = country;
        this.position = position;
        this.grade = grade;
        this.crewrotationId = CrewId;
    }

    public MobilizationInfo(int candidateId, String candidateName, String candidatemail, int clientId, String clientname, String clientemail, String clientAsset, String date) {
        this.candidateId = candidateId;
        this.name = candidateName;
        this.candidatemail = candidatemail;
        this.clientId = clientId;
        this.clientName = clientname;
        this.clientemail = clientemail;
        this.clientAsset = clientAsset;
        this.date = date;
    }

    public MobilizationInfo(int CrewId, int mobilizationId, String val1, String val5, String filename, int type) {
        this.crewrotationId = CrewId;
        this.mobilizationId = mobilizationId;
        this.val1 = val1;
        this.val5 = val5;
        this.filename = filename;
        this.type = type;
    }

    public MobilizationInfo(int mobilizationId, String val1, String val5, String filename,int type) {
        this.mobilizationId = mobilizationId;
        this.val1 = val1;
        this.val5 = val5;
        this.filename = filename;
        this.type = type;
    }

    public int getJobpostId() {
        return jobpostId;
    }

    public int getDdlValue() {
        return ddlValue;
    }

    public int getDdltype() {
        return ddltype;
    }

    public int getStatus() {
        return status;
    }

    public String getDdlLabel() {
        return ddlLabel;
    }

    public int getClientId() {
        return clientId;
    }

    public int getClientassetId() {
        return clientassetId;
    }

    public int getOpening() {
        return opening;
    }

    public int getShortlistId() {
        return shortlistId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public int getCrewrotationId() {
        return crewrotationId;
    }

    public int getStatus1() {
        return status1;
    }

    public int getPositionCount() {
        return positionCount;
    }

    public int getOnboardCount() {
        return onboardCount;
    }

    public int getTotalnoofopenings() {
        return totalnoofopenings;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientAsset() {
        return clientAsset;
    }

    public String getCountry() {
        return country;
    }

    public int getTotalnoofongoing() {
        return totalnoofongoing;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getGrade() {
        return grade;
    }

    public String getPhoto() {
        return photo;
    }

    public int getMobilizationId() {
        return mobilizationId;
    }

    public int getUserId() {
        return userId;
    }

    public String getVal1() {
        return val1;
    }

    public String getVal2() {
        return val2;
    }

    public String getVal3() {
        return val3;
    }

    public String getVal4() {
        return val4;
    }

    public String getVal5() {
        return val5;
    }

    public String getVal6() {
        return val6;
    }

    public String getVal7() {
        return val7;
    }

    public String getVal8() {
        return val8;
    }

    public String getVal9() {
        return val9;
    }

    public String getVal10() {
        return val10;
    }

    public String getFilename() {
        return filename;
    }

    public String getRegdate() {
        return regdate;
    }

    public String getModdate() {
        return moddate;
    }

    public int getType() {
        return type;
    }

    public int getBatchId() {
        return batchId;
    }

    public String getVald9() {
        return vald9;
    }

    public String getValt9() {
        return valt9;
    }

    public String getVald10() {
        return vald10;
    }

    public String getValt10() {
        return valt10;
    }

    public int getFlag1() {
        return flag1;
    }

    public int getFlag2() {
        return flag2;
    }

    public int getAssettypeid() {
        return assettypeid;
    }

    public String getCandidatemail() {
        return candidatemail;
    }

    public String getClientemail() {
        return clientemail;
    }

    public String getDate() {
        return date;
    }
}
