package com.web.jxp.compliancecheck;

public class CompliancecheckInfo {

    private int candidateId;

    private int ddlValue;
    private int status;
    private int languageId;
    private int proficiencyId;
    private int candlangid;
    private int jobpostId;
    private int openings;
    private int shortlistId;
    private int gradeId;
    private int clientId;
    private int clientassetId;
    private int shortlistccId;
    private int countryId;
    private int cityId;
    private int positionId;
    private int departmentId;
    private int currencyId;
    private int nationalityId;
    private int relationId;
    private int maritalstatusId;
    private int expectedsalary;
    private int checkpointId;
    private int nshortlistccId;
    private int fileid;

    private double expMin;
    private double expMax;
    private double experience;

    private String filename;
    private String name;
    private String place;
    private String countryName;
    private String nationality;
    private String address1line1;
    private String address1line2;
    private String address2line1;
    private String address2line2;
    private String address2line3;
    private String relation;
    private String maritialstatus;
    private String resumefilename;
    private String photo;
    private String ddlLabel;
    private String position;
    private String clientName;
    private String assetName;
    private String languageName;
    private String proficiencyName;
    private String candlangfilename;
    private String date;
    private String firstname;
    private String middlename;
    private String lastname;
    private String dob;
    private String placeofbirth;
    private String emailId;
    private String contactno1;
    private String contactno2;
    private String contactno3;
    private String econtactno1;
    private String econtactno2;
    private String code1Id;
    private String code2Id;
    private String code3Id;
    private String ecode1Id;
    private String ecode2Id;
    private String gender;
    private String nextofkin;
    private String photofilename;
    private String address1line3;
    private String city;
    private String department;
    private String currency;
    private String clientAsset;
    private String ccStatus;
    private String education;
    private String degree;
    private String qualificationType;
    private String gradeName;
    private String positionName;
    private String remarks;
    private String companyName;
    private String photoName;
    private String positionValue;
    private String checkpointName;
    private String userName;

    //for ddl
    public CompliancecheckInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public CompliancecheckInfo(String positionValue, String ddlLabel) {
        this.positionValue = positionValue;
        this.ddlLabel = ddlLabel;
    }

    public CompliancecheckInfo(String name, int status) {
        this.languageName = name;
        this.status = status;
    }

    //for index
    public CompliancecheckInfo(int jobpostId, String name,
            int status, String date, String positionName, String firstname, String clientAsset, String ccStatus, String clientName, int shortlistId, int candidateId) {
        this.jobpostId = jobpostId;
        this.name = name;
        this.status = status;
        this.date = date;
        this.position = positionName;
        this.firstname = firstname;
        this.clientAsset = clientAsset;
        this.ccStatus = ccStatus;
        this.clientName = clientName;
        this.shortlistId = shortlistId;
        this.candidateId = candidateId;
    }

    public CompliancecheckInfo(int candidateId, String name, String positionName, int expectedsalary,
            String currency, String degree, String qualificationType, String gradeName, String companyName, String photoName, double experience) {
        this.candidateId = candidateId;
        this.name = name;
        this.position = positionName;
        this.expectedsalary = expectedsalary;
        this.currency = currency;
        this.degree = degree;
        this.qualificationType = qualificationType;
        this.gradeName = gradeName;
        this.companyName = companyName;
        this.photoName = photoName;
        this.experience = experience;

    }

    public CompliancecheckInfo(int jobpostId, String date, String clientName, String positionName,
            String assetName, String qualificationType, double expMin, double expMax, int openings, int gradeId, int clientId, int clientassetId, String position) {
        this.jobpostId = jobpostId;
        this.date = date;
        this.clientName = clientName;
        this.position = positionName;
        this.assetName = assetName;
        this.qualificationType = qualificationType;
        this.expMin = expMin;
        this.expMax = expMax;
        this.openings = openings;
        this.gradeId = gradeId;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.positionName = position;

    }

    public CompliancecheckInfo(int shortlistId, int shortlistccId, int checkpointId, int status, String remarks, int a) {
        this.shortlistId = shortlistId;
        this.shortlistccId = shortlistccId;
        this.checkpointId = checkpointId;
        this.status = status;
        this.remarks = remarks;

    }

    public CompliancecheckInfo(String date, String remarks, String name) {
        this.date = date;
        this.remarks = remarks;
        this.name = name;
    }

    // For excel 
    public CompliancecheckInfo(int candidateId, String name, String countryName, int status, String date, String cityName, String positionName, String contactno1,
            String econactno, String email, String dob, String placeofbirth, String gender, String maritalstatus, String nationality, String prefferddept, int expectedsalary,
            String currency) {
        this.candidateId = candidateId;
        this.name = name;
        this.countryName = countryName;
        this.status = status;
        this.date = date;
        this.city = cityName;
        this.position = positionName;
        this.contactno1 = contactno1;
        this.econtactno1 = econactno;
        this.emailId = email;
        this.dob = dob;
        this.placeofbirth = placeofbirth;
        this.gender = gender;
        this.maritialstatus = maritalstatus;
        this.nationality = nationality;
        this.department = prefferddept;
        this.expectedsalary = expectedsalary;
        this.currency = currency;
    }

    public CompliancecheckInfo(int checkpointId, String checkpointName, int nshortlistccId, int ccstatus, String userName) {
        this.checkpointId = checkpointId;
        this.checkpointName = checkpointName;
        this.nshortlistccId = nshortlistccId;
        this.status = ccstatus;
        this.userName = userName;
    }

    public CompliancecheckInfo(int fileId, String filename, String date, String name) {
        this.fileid = fileId;
        this.filename = filename;
        this.date = date;
        this.name = name;
    }

    public CompliancecheckInfo(String date, String remarks, String name, int status) {
        this.date = date;
        this.remarks = remarks;
        this.name = name;
        this.status = status;
    }

    public int getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(int checkpointId) {
        this.checkpointId = checkpointId;
    }

    public int getNshortlistccId() {
        return nshortlistccId;
    }

    public String getCheckpointName() {
        return checkpointName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPositionValue() {
        return positionValue;
    }

    public int getShortlistccId() {
        return shortlistccId;
    }

    public double getExperience() {
        return experience;
    }

    public String getPhotoName() {
        return photoName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getPositionName() {
        return positionName;
    }

    public int getClientassetId() {
        return clientassetId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public int getShortlistId() {
        return shortlistId;
    }

    public int getJobpostId() {
        return jobpostId;
    }

    public String getQualificationType() {
        return qualificationType;
    }

    public double getExpMin() {
        return expMin;
    }

    public double getExpMax() {
        return expMax;
    }

    public int getOpenings() {
        return openings;
    }

    public String getEducation() {
        return education;
    }

    public String getDegree() {
        return degree;
    }

    public String getClientAsset() {
        return clientAsset;
    }

    public String getCcStatus() {
        return ccStatus;
    }

    public String getDepartment() {
        return department;
    }

    public String getCurrency() {
        return currency;
    }

    public int getExpectedsalary() {
        return expectedsalary;
    }

    public String getCity() {
        return city;
    }

    public int getCityId() {
        return cityId;
    }

    public int getPositionId() {
        return positionId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public String getAddress1line3() {
        return address1line3;
    }

    public String getCandlangfilename() {
        return candlangfilename;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPlaceofbirth() {
        return placeofbirth;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getContactno1() {
        return contactno1;
    }

    public String getContactno2() {
        return contactno2;
    }

    public String getContactno3() {
        return contactno3;
    }

    public String getEcontactno1() {
        return econtactno1;
    }

    public String getEcontactno2() {
        return econtactno2;
    }

    public String getCode1Id() {
        return code1Id;
    }

    public String getCode2Id() {
        return code2Id;
    }

    public String getCode3Id() {
        return code3Id;
    }

    public String getEcode1Id() {
        return ecode1Id;
    }

    public String getEcode2Id() {
        return ecode2Id;
    }

    public int getCountryId() {
        return countryId;
    }

    public int getNationalityId() {
        return nationalityId;
    }

    public int getRelationId() {
        return relationId;
    }

    public int getMaritalstatusId() {
        return maritalstatusId;
    }

    public String getPhotofilename() {
        return photofilename;
    }

    public int getCandlangid() {
        return candlangid;
    }

    public int getProficiencyId() {
        return proficiencyId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public String getDate() {
        return date;
    }

    public int getFileid() {
        return fileid;
    }

    public String getFilename() {
        return filename;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getPlace() {
        return place;
    }

    public String getGender() {
        return gender;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getNationality() {
        return nationality;
    }

    public String getAddress1line1() {
        return address1line1;
    }

    /**
     * @return the address1line2
     */
    public String getAddress1line2() {
        return address1line2;
    }

    /**
     * @return the address2line1
     */
    public String getAddress2line1() {
        return address2line1;
    }

    /**
     * @return the address2line2
     */
    public String getAddress2line2() {
        return address2line2;
    }

    /**
     * @return the address2line3
     */
    public String getAddress2line3() {
        return address2line3;
    }

    /**
     * @return the nextofkin
     */
    public String getNextofkin() {
        return nextofkin;
    }

    /**
     * @return the relation
     */
    public String getRelation() {
        return relation;
    }

    public String getMaritialstatus() {
        return maritialstatus;
    }

    /**
     * @return the resumefilename
     */
    public String getResumefilename() {
        return resumefilename;
    }

    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
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
     * @return the position
     */
    public String getPosition() {
        return position;
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
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return the languageName
     */
    public String getLanguageName() {
        return languageName;
    }

    /**
     * @return the proficiencyName
     */
    public String getProficiencyName() {
        return proficiencyName;
    }

}
