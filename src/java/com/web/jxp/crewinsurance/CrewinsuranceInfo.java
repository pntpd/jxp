package com.web.jxp.crewinsurance;

public class CrewinsuranceInfo {

    private int crewinsuranceId;
    private int crewrotationId;
    private String name;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;
    private int datedifference;
    private String position;
    private String grade;
    private String fromDate;
    private String toDate;
    private String filename;
    private String stval;
    private int dependent;

    private int positiondIndex;
    private int clientIdIndex;
    private int assetIdIndex;
    
    //For nominee
    private String nominee;
    private String relation;
    private String contactno;
    private int candidateId;
    private String code1Id;
    private String selectedNominee;
    private String selectedAll;
    private int nomineeId;
    

    //for ddl
    public CrewinsuranceInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public CrewinsuranceInfo(int crewinsuranceId, String fromDate, String toDate, int dependent, int status,
            int userId, String fileName1, int crewrotationId, String selectedNominee) {
        this.crewinsuranceId = crewinsuranceId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.dependent = dependent;
        this.status = status;
        this.userId = userId;
        this.filename = fileName1;
        this.crewrotationId = crewrotationId;
        this.selectedNominee = selectedNominee;
    }

    public CrewinsuranceInfo(int crewrotationId, String name, String position, String grade, int status,
            int datedifference, int userId, int clientIdIndex, int assetIdIndex, String stval,
            int crewinsuranceId, String fromdate, String todate, int dependent, String filename) {
        this.crewrotationId = crewrotationId;
        this.name = name;
        this.position = position;
        this.grade = grade;
        this.status = status;
        this.datedifference = datedifference;
        this.userId = userId;
        this.clientIdIndex = clientIdIndex;
        this.assetIdIndex = assetIdIndex;
        this.stval = stval;
        this.crewinsuranceId = crewinsuranceId;
        this.fromDate = fromdate;
        this.toDate = todate;
        this.dependent = dependent;
        this.filename = filename;
    }

    public CrewinsuranceInfo(int crewinsuranceId, String name, String position, String grade, int status,
            int datedifference, int userId) {
        this.crewinsuranceId = crewinsuranceId;
        this.name = name;
        this.position = position;
        this.grade = grade;
        this.status = status;
        this.datedifference = datedifference;
        this.userId = userId;
    }

    public CrewinsuranceInfo(int crewrotationid, int crewinsuranceId, int status, String fromdate, String todate,
            int dependent, String filename, String name, String position, String grade, int datedifference,
            String stval, int candidateId, String selectedNominee) {
        this.crewrotationId = crewrotationid;
        this.crewinsuranceId = crewinsuranceId;
        this.status = status;
        this.fromDate = fromdate;
        this.toDate = todate;
        this.dependent = dependent;
        this.filename = filename;
        this.name = name;
        this.position = position;
        this.grade = grade;
        this.datedifference = datedifference;
        this.stval = stval;
        this.candidateId = candidateId;
        this.selectedNominee = selectedNominee;
    }

    public CrewinsuranceInfo(int crewrotationid, int crewinsuranceId, int status, String fromdate, String todate,
            int dependent, String filename,String selectedNominee) {
        this.crewrotationId = crewrotationid;
        this.crewinsuranceId = crewinsuranceId;
        this.status = status;
        this.fromDate = fromdate;
        this.toDate = todate;
        this.dependent = dependent;
        this.filename = filename;
        this.selectedNominee = selectedNominee;
    }
    
    public CrewinsuranceInfo(int nomineeId, String nominee, String contactno, String relation, 
            int status, String code1Id) 
    {
        this.nomineeId = nomineeId;
        this.nominee = nominee;
        this.contactno = contactno;
        this.relation = relation;
        this.status = status;
        this.code1Id = code1Id;
    }
    
    /**
     * @return the crewinsuranceId
     */
    public int getCrewinsuranceId() {
        return crewinsuranceId;
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

    /**
     * @return the datedifference
     */
    public int getDatedifference() {
        return datedifference;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @return the grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * @return the crewrotationId
     */
    public int getCrewrotationId() {
        return crewrotationId;
    }

    /**
     * @return the positiondIndex
     */
    public int getPositiondIndex() {
        return positiondIndex;
    }

    /**
     * @return the clientIdIndex
     */
    public int getClientIdIndex() {
        return clientIdIndex;
    }

    /**
     * @return the assetIdIndex
     */
    public int getAssetIdIndex() {
        return assetIdIndex;
    }

    /**
     * @return the fromDate
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * @return the toDate
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @return the dependent
     */
    public int getDependent() {
        return dependent;
    }

    /**
     * @return the stval
     */
    public String getStval() {
        return stval;
    }

    /**
     * @return the candidateId
     */
    public int getCandidateId() {
        return candidateId;
    }

    /**
     * @return the nominee
     */
    public String getNominee() {
        return nominee;
    }

    /**
     * @return the relation
     */
    public String getRelation() {
        return relation;
    }

    /**
     * @return the contactno
     */
    public String getContactno() {
        return contactno;
    }

    /**
     * @return the code1Id
     */
    public String getCode1Id() {
        return code1Id;
    }
    /**
     * @return the nomineeId
     */
    public int getNomineeId() {
        return nomineeId;
    }

    /**
     * @return the selectedNominee
     */
    public String getSelectedNominee() {
        return selectedNominee;
    }

    /**
     * @return the selectedAll
     */
    public String getSelectedAll() {
        return selectedAll;
    }
}
