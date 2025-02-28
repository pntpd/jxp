package com.web.jxp.candidateregistration;

public class CandidateRegistrationInfo {

    private int homeId;
    private String name;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;
    private int candidateId;
    private int onlineflag;
    private String emailId;
    private int draftflag;
    private String city1_name;
    private String city2_name;

    private String resumeFile;
    private String proficiencytypes;

    private int onshore;
    private String firstname;
    private String middlename;
    private String lastname;
    private String dob;
    private String placeofbirth;
    private int passport;
    private String gender;
    private int maritalstatusId;
    private int nationalityId;
    private String religion;
//    private int yearofExp;
//    private int yearofOffshoreExp;
//    private int yearofExpPosition;
    private String adhaar;
    private String pancard;
    private String adhaarFile;
    private String panFile;
    private String code1;
    private String contactno1;
    private String addressline1_1;
    private String addressline1_2;
    private String addressline1_3;
    private String pincode;
    private int countryId;
    private int stateId;
    private int cityId;
    private int sameAsPermanent;
    private String addressline2_1;
    private String addressline2_2;
    private String addressline2_3;
    private String pincode2;
    private int countryId2;
    private int stateId2;
    private int cityId2;
    private int assettypeId;
    private int positionId;
    private int ecurrencyId;
    private int expectedsalary;
    private int lcurrencyId;
    private int lastdrawnsalary;
    private int kindId;
    private int degreeId;
    private int isFresher;
    private int languageId1;
    private int languageProf1[];
    private int languageId2;
    private int languageId3;
    private int languageId4;

    // Work Experience
    private String companyname;
    private String assetname;
    private String workstartdate;
    private int currentworkingstatus;
    private int pastOCSemp;
    private int expassettypeId;
    private String expassettype;
    private int exppositionId;
    private String expposition;
    private String workenddate;

    // Certificate
    private int coursenameId;
    private int approvedbyId;
    private int isexpiry;
    private String coursename;
    private String dateofissue;
    private String dateofexpiry;
    private String approvedby;
    private String tempfname;
    private String templocalFile;

    //for ddl
    public CandidateRegistrationInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public CandidateRegistrationInfo(int candidateId, int onlineflag, String emailId, int draftflag) {
        this.candidateId = candidateId;
        this.onlineflag = onlineflag;
        this.emailId = emailId;
        this.draftflag = draftflag;
    }

    public CandidateRegistrationInfo(String companyname, String assetname, String workstartdate, int currentworkingstatus,
            int pastOCSemp, int expassettypeId, String expassettype, int exppositionId, String expposition, String workenddate) {
        this.companyname = companyname;
        this.assetname = assetname;
        this.workstartdate = workstartdate;
        this.currentworkingstatus = currentworkingstatus;
        this.pastOCSemp = pastOCSemp;
        this.expassettypeId = expassettypeId;
        this.expassettype = expassettype;
        this.exppositionId = exppositionId;
        this.expposition = expposition;
        this.workenddate = workenddate;
    }

    public CandidateRegistrationInfo(int isexpiry, int coursenameId, String coursename, String dateofissue,
            String dateofexpiry, int approvedbyId, String approvedby, String tempfname, String templocalFile) {
        this.isexpiry = isexpiry;
        this.coursenameId = coursenameId;
        this.coursename = coursename;
        this.dateofissue = dateofissue;
        this.dateofexpiry = dateofexpiry;
        this.approvedbyId = approvedbyId;
        this.approvedby = approvedby;
        this.tempfname = tempfname;
        this.templocalFile = templocalFile;
    }

    public CandidateRegistrationInfo(int onshore, String firstname, String middlename, String lastname, String dob, String placeofbirth,
            int passport, String gender, int maritalstatusId, int nationalityId, String religion, String adhaar, String pancard,
            String code1, String contactno1, String emailId, String addressline1_1, String addressline1_2, String addressline1_3,
            String pincode, int countryId, int stateId, int cityId, int sameAsPermanent, String addressline2_1, String addressline2_2,
            String addressline2_3, String pincode2, int countryId2, int stateId2, int cityId2, int assettypeId, int positionId,
            int ecurrencyId, int expectedsalary, int lcurrencyId, int lastdrawnsalary, int kindId, int degreeId, int isFresher,
            int languageId1, int languageProf1[], int languageId2, int languageId3, int languageId4, int status, String adhaarFile,
            String panFile) {

        this.onshore = onshore;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.dob = dob;
        this.placeofbirth = placeofbirth;

        this.passport = passport;
        this.gender = gender;
        this.maritalstatusId = maritalstatusId;
        this.nationalityId = nationalityId;
        this.religion = religion;

        this.adhaar = adhaar;
        this.pancard = pancard;
        this.code1 = code1;
        this.contactno1 = contactno1;
        this.emailId = emailId;
        this.addressline1_1 = addressline1_1;

        this.addressline1_2 = addressline1_2;
        this.addressline1_3 = addressline1_3;
        this.pincode = pincode;
        this.countryId = countryId;
        this.stateId = stateId;
        this.cityId = cityId;
        this.sameAsPermanent = sameAsPermanent;

        this.addressline2_1 = addressline2_1;
        this.addressline2_2 = addressline2_3;
        this.addressline2_3 = addressline2_3;
        this.pincode2 = pincode2;
        this.countryId2 = countryId2;
        this.stateId2 = stateId2;
        this.cityId2 = cityId2;

        this.assettypeId = assettypeId;
        this.positionId = positionId;
        this.ecurrencyId = ecurrencyId;
        this.expectedsalary = expectedsalary;
        this.lcurrencyId = lcurrencyId;
        this.lastdrawnsalary = lastdrawnsalary;
        this.kindId = kindId;

        this.degreeId = degreeId;
        this.isFresher = isFresher;
        this.languageId1 = languageId1;
        this.languageProf1 = languageProf1;
        this.languageId2 = languageId2;
        this.languageId3 = languageId3;
        this.languageId4 = languageId4;

        this.status = status;

        this.adhaarFile = adhaarFile;
        this.panFile = panFile;
    }

    public CandidateRegistrationInfo(int onshore, String firstname, String middlename, String lastname, String dob, String placeofbirth,
            String gender, int maritalstatusid, int nationalityid, String religion, String code1, String contactno1, String email,
            String address1line1, String address1line2, String address1line3, int countryid, int stateid, int cityid, String city1_name,
            String pincode, int sameasprimaryadd, String address2line1, String address2line2, String address2line3, int countryid2,
            int stateid2, int cityid2, String city2_name, String pincode2, int assettypeid, int positionid2, int currencyid,
            int expectedsalary, int drawncurrencyid, int drawnsalary, int online, int fresher, int kindId, int degreeId, int passport,
            String adhaar, String adharFile, String pan, String panFile, String resumeFile, String proficiencytypes) {

        this.onshore = onshore;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.dob = dob;

        this.placeofbirth = placeofbirth;
        this.gender = gender;
        this.maritalstatusId = maritalstatusid;
        this.nationalityId = nationalityid;
        this.religion = religion;

        this.code1 = code1;
        this.contactno1 = contactno1;
        this.emailId = email;
        this.addressline1_1 = address1line1;
        this.addressline1_2 = address1line2;

        this.addressline1_3 = address1line3;
        this.countryId = countryid;
        this.stateId = stateid;
        this.cityId = cityid;
        this.city1_name = city1_name;

        this.pincode = pincode;
        this.sameAsPermanent = sameasprimaryadd;
        this.addressline2_1 = address2line1;
        this.addressline2_2 = address2line2;
        this.addressline2_3 = address2line3;

        this.countryId2 = countryid2;
        this.stateId2 = stateid2;
        this.cityId2 = cityid2;
        this.city2_name = city2_name;
        this.pincode2 = pincode2;

        this.assettypeId = assettypeid;
        this.positionId = positionid2;
        this.ecurrencyId = currencyid;
        this.expectedsalary = expectedsalary;
        this.lcurrencyId = drawncurrencyid;

        this.lastdrawnsalary = drawnsalary;
        this.onlineflag = online;
        this.isFresher = fresher;
        this.kindId = kindId;
        this.degreeId = degreeId;

        this.passport = passport;
        this.adhaar = adhaar;
        this.adhaarFile = adharFile;
        this.pancard = pan;
        this.panFile = panFile;
        this.resumeFile = resumeFile;
        
        this.proficiencytypes = proficiencytypes;
    }

//    public CandidateRegistrationInfo(String companyname, int assettypeid, String assetname, int positionid, String workstartdate,
//            String workenddate, int currentworkingstatus, int pastocsemp) {
//        this.companyname = companyname;
//        this.assettypeId = assettypeid;
//        this.assetname = assetname;
//        this.positionId = positionid;
//        this.workstartdate = workstartdate;
//        this.workenddate = workenddate;
//        this.currentworkingstatus = currentworkingstatus;
//        this.pastOCSemp = pastocsemp;
//    }
//    public CandidateRegistrationInfo(int coursenameid, String dateofissue, String expirydate, int approvedbyid, String filename,
//            int noexpiry) {
//        this.coursenameId = coursenameid;
//        this.dateofissue = dateofissue;
//        this.dateofexpiry = expirydate;
//        this.approvedbyId = approvedbyid;
//        this.tempfname = filename;
//        this.isexpiry = noexpiry;
//    }
    public String getCity1_name() {
        return city1_name;
    }

    public void setCity1_name(String city1_name) {
        this.city1_name = city1_name;
    }

    public String getCity2_name() {
        return city2_name;
    }

    public void setCity2_name(String city2_name) {
        this.city2_name = city2_name;
    }

    public int getHomeId() {
        return homeId;
    }

    public String getName() {
        return name;
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

    public int getCandidateId() {
        return candidateId;
    }

    public int getOnlineflag() {
        return onlineflag;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getCompanyname() {
        return companyname;
    }

    public String getAssetname() {
        return assetname;
    }

    public String getWorkstartdate() {
        return workstartdate;
    }

    public int getExpassettypeId() {
        return expassettypeId;
    }

    public String getExpassettype() {
        return expassettype;
    }

    public int getExppositionId() {
        return exppositionId;
    }

    public String getExpposition() {
        return expposition;
    }

    public String getWorkenddate() {
        return workenddate;
    }

    public int getCoursenameId() {
        return coursenameId;
    }

    public int getApprovedbyId() {
        return approvedbyId;
    }

    public int getCurrentworkingstatus() {
        return currentworkingstatus;
    }

    public int getPastOCSemp() {
        return pastOCSemp;
    }

    public int getIsexpiry() {
        return isexpiry;
    }

    public String getCoursename() {
        return coursename;
    }

    public String getDateofissue() {
        return dateofissue;
    }

    public String getDateofexpiry() {
        return dateofexpiry;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public String getTempfname() {
        return tempfname;
    }

    public String getTemplocalFile() {
        return templocalFile;
    }

    public int getOnshore() {
        return onshore;
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

    public String getDob() {
        return dob;
    }

    public String getPlaceofbirth() {
        return placeofbirth;
    }

    public int getPassport() {
        return passport;
    }

    public String getGender() {
        return gender;
    }

    public int getMaritalstatusId() {
        return maritalstatusId;
    }

    public int getNationalityId() {
        return nationalityId;
    }

    public String getReligion() {
        return religion;
    }

    public String getAdhaar() {
        return adhaar;
    }

    public String getPancard() {
        return pancard;
    }

    public String getCode1() {
        return code1;
    }

    public String getContactno1() {
        return contactno1;
    }

    public String getAddressline1_1() {
        return addressline1_1;
    }

    public String getAddressline1_2() {
        return addressline1_2;
    }

    public String getAddressline1_3() {
        return addressline1_3;
    }

    public String getPincode() {
        return pincode;
    }

    public int getCountryId() {
        return countryId;
    }

    public int getStateId() {
        return stateId;
    }

    public int getCityId() {
        return cityId;
    }

    public int getSameAsPermanent() {
        return sameAsPermanent;
    }

    public String getAddressline2_1() {
        return addressline2_1;
    }

    public String getAddressline2_2() {
        return addressline2_2;
    }

    public String getAddressline2_3() {
        return addressline2_3;
    }

    public String getPincode2() {
        return pincode2;
    }

    public int getCountryId2() {
        return countryId2;
    }

    public int getStateId2() {
        return stateId2;
    }

    public int getCityId2() {
        return cityId2;
    }

    public int getAssettypeId() {
        return assettypeId;
    }

    public int getPositionId() {
        return positionId;
    }

    public int getEcurrencyId() {
        return ecurrencyId;
    }

    public int getExpectedsalary() {
        return expectedsalary;
    }

    public int getLcurrencyId() {
        return lcurrencyId;
    }

    public int getLastdrawnsalary() {
        return lastdrawnsalary;
    }

    public int getKindId() {
        return kindId;
    }

    public int getDegreeId() {
        return degreeId;
    }

    public int getIsFresher() {
        return isFresher;
    }

    public int getLanguageId1() {
        return languageId1;
    }

    public int[] getLanguageProf1() {
        return languageProf1;
    }

    public int getLanguageId2() {
        return languageId2;
    }

    public int getLanguageId3() {
        return languageId3;
    }

    public int getLanguageId4() {
        return languageId4;
    }

    public int getDraftflag() {
        return draftflag;
    }

    public void setDraftflag(int draftflag) {
        this.draftflag = draftflag;
    }

    public String getAdhaarFile() {
        return adhaarFile;
    }

    public String getPanFile() {
        return panFile;
    }

    public String getResumeFile() {
        return resumeFile;
    }

    public String getProficiencytypes() {
        return proficiencytypes;
    }

}
