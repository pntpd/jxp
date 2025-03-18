package com.web.jxp.talentpool;

public class TalentpoolInfo {

    private int candidateId;
    private int status;
    private int ddlValue;
    private int languageId;
    private int proficiencyId;
    private int candlangid;
    private int countryId;
    private int cityId;
    private int positionId;
    private int departmentId;
    private int currencyId;
    private int nationalityId;
    private int relationId;
    private int maritalstatusId;
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
    private String assettype;
    private String employeeId;
    private String clientCountry;
    private String ratetype;
    private double rate1;
    private double rate2;
    private double rate3;

    //for bankdetails
    private int accountTypeId;
    private int bankdetid;
    private int primarybankId;
    private String bankName;
    private String savingAccountNo;
    private String branch;
    private String IFSCCode;
    private String accountTypename;
    private String bkFilename;

    //for health Declaration
    private int bloodpressureId;
    private int userId;
    private int candidatehealthId;
    private String ssmf;
    private String ogukmedicalftw;
    private String ogukexp;
    private String medifitcert;
    private String medifitcertexp;
    private String bloodgroup;
    private String hypertension;
    private String diabetes;
    private String smoking;
    private String cov192doses;
    private String covid19vaccinestatus;
    private String vaccine1dose;
    private String vaccine1date;
    private String vaccine2dose;
    private String vaccine2date;
    private String vaccine3dose;
    private String vaccine3date;
    private String mfFilename;
    private double height;
    private double weight;

    //for hitch
    private int enginetypeId;
    private double tonnage;
    private String positionname;
    private String companyshipname;
    private String region;
    private String enginetype;
    private String signin;
    private String signoff;

    //for vaccination
    private int candidatevaccineId;
    private int vaccinationNameId;
    private int vaccinationTypeId;
    private int placeofapplicationId;
    private String vaccinecertfile;
    private String dateofapplication;
    private String dateofexpiry;
    private String vaccinationName;
    private String vacinationType;
    private String placeofapplication;

    //gov documents
    private int govdocumentId;
    private int documenttypeId;
    private int placeofissueId;
    private int issuedbyId;
    private String documentname;
    private String documentno;
    private String placeofissue;
    private String issuedby;
    private String govdocumentfile;

    //for training certification 
    private int trainingandcertId;
    private int coursetypeId;
    private int locationofInstituteId;
    private int approvedbyid;
    private int coursenameId;
    private int filecount;
    private String coursetype;
    private String coursename;
    private String eduinstitute;
    private String locationofInstitute;
    private String dateofissue;
    private String certificateno;
    private String expirydate;
    private String courseverification;
    private String approvedby;
    private String certifilename;
    private String fieldofstudy;
    private String coursestart;
    private String passingyear;
    private String currency;

    //educational details
    private int educationdetailId;
    private int kindId;
    private int degreeId;
    private int highestqualification;
    private String kindname;
    private String degree;
    private String educationalfilename;
    private String backgroundofstudy;

    // for work experience
    private int experiencedetailId;
    private int companyindustryId;
    private int assettypeId;
    private int waterdepthId;
    private int lastdrawnsalarycurrencyId;
    private int skillsId;
    private int gradeId;
    private int crewtypeid;
    private int dayratecurrencyid;
    private int dayrate;
    private int monthlysalarycurrencyId;
    private int monthlysalary;
    private int currentworkingstatus;
    private String companyname;
    private String department;
    private String clientpartyname;
    private String lastdrawnsalary;
    private String workstartdate;
    private String workenddate;
    private String ownerpool;
    private String ocsemployed;
    private String legalrights;
    private String experiencefilename;
    private String workfilename;

    private int progressId;
    private int alertCount;
    private int vflag;
    private int clientId;
    private String clientAsset;

    private int alertId;
    private int type;
    private int tabno;
    private String remarks;
    private String gradename;

    //verification details
    private int vStatusId;
    private String tabName;
    private String time;
    private String authority;

    //AssessmentList Details
    private int cassessmenthId;
    private int cassessmentId;
    private int paId;
    private int attempt;
    private int showflag;
    private double marks;
    private String coordinatorName;
    private String assessorName;
    private String assessment;
    private String statusValue;

    //verification tab
    private int cflag1;
    private int cflag2;
    private int cflag3;
    private int cflag4;
    private int cflag5;
    private int cflag6;
    private int cflag7;
    private int cflag8;
    private int cflag9;
    private int cflag10;

    //compliance tab
    private int jobpostId;
    private int shortlistId;
    private int shortlistStatus;
    private String userName;

    // Shortlisting history
    private String poston;
    private String shortliston;
    private String statusvalue;
    private String code;

    private int sflag;
    private int maillogId;
    private int usertype;
    private int oflag;
    private int onboardStatus;
    private int clientassetId;
    private int mobilizeStatus;
    private int rotationStatus;
    private int crewrotationId;
    private int toAssetId;
    private int activeStatus;
    private int fromClientId;

    private String arrivalDate;
    private String mobDate;
    private String signon;
    private String rotationStatusValue;
    private String transferDate;
    private String transferRemark;

    //Terminate Modal
    private int transferType;
    private int expectedsalary;
    private int fileid;

    private String terminateDate;
    private String terminateRemark;
    private String bloodpressure;
    private String city;
    private String filename;
    private String joiningDate;
    private String endDate;
    private String joiningDate1;
    private String endDate1;
    private String joiningDate2;
    private String endDate2;

    private String mailto;
    private String mailfrom;
    private String mailcc;
    private String mailbcc;
    private String subject;
    private String attachmentpath;
    private String sentby;
    private String mailby;
    private String maildate;

    private int toClientId;
    private int toPositionId;
    private int toCurrencyId;
    private String torate1;
    private String torate2;
    private String torate3;

    private String srby;
    private String srdate;
    private String usertypevalue;
    private String reason;
    private String omailby;
    private String omaildate;
    private String oadby;
    private String oaddate;
    private String oadreason;
    private String oadremarks;

    private int ct1;
    private int ct2;
    private int ct3;
    private int ct4;
    private int ct5;
    private int ct6;
    private int ct7;
    private int ct8;
    private int ct9;

    // Wellness Feedback
    private String feedback;
    private String categoryName;
    private String question;
    private String answer;
    private String senton;
    private String completedon;
    private int expiryflag;
    private String submissionDate;
    private String filleddate;
    private int surveyId;
    private int subcategorywfId;

    //Assenow
    private int cassessnowhId;
    private int cassessnowId;
    private String username;

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

    private String applytypevalue;
    private int applytype;
    private String url;

    //For Copmetency
    private int trackerId;
    private int healthfileId;
    private String role;
    private String priority;
    private String validtill;

    //For Nominee
    private String nomineeName;
    private String nomineeRelation;
    private String nomineeContactno;
    private String religion;
    private int nomineedetailId;
    private String address;
    private double percentage;

    //For Offshore Update
    private String offshorefile;
    private String typeName;
    private int offshoreId;
    private int remarktypeId;

    //For PPE
    private int ppedetailId;
    private int ppetypeId;
    private String ppetype;

    //For contract
    private int contractdetailId;
    private int contractId;
    private int refId;
    private int approval1;
    private int approval2;
    private String file1;
    private String file2;
    private String file3;
    private String fromDate;
    private String toDate;
    private String date1;
    private String date2;
    private String date3;
    private String ccaddress;
    private String username1;
    private String username2;
    private String username3;
    private String remarks1;
    private String remarks2;
    private String contractName;
    private String cval1;
    private String cval2;
    private String cval3;
    private String cval4;
    private String cval5;
    private String cval6;
    private String cval7;
    private String cval8;
    private String cval9;
    private String cval10;
    private int onboardingfileId;
    private int positionId2;
    private double p2rate1;
    private double p2rate2;
    private double p2rate3;
    private int travelDays;

    private int toPositionId2;
    private int toCurrencyId2;
    private String top2rate1;
    private String top2rate2;
    private String top2rate3;

    //For Appraisal
    private int appraisalId;
    private String position2;
    private double oldRate1;
    private double oldRate2;
    private double oldRate3;

    //For state
    private String stateName;
    private String pinCode;
    private int stateId;

    private int age;
    private String airport1;
    private String airport2;
    private String accountHolder;
    private String localFile;
    private String profile;
    private String skill1;
    private String skill2;

    //For dayrate tab
    private int dayrateId;

    //info for bankdetails
    public TalentpoolInfo(String bankName, String savingAccountNo, String branch, String IFSCCode, int accountTypeId, String bkFilename) {
        this.bankName = bankName;
        this.savingAccountNo = savingAccountNo;
        this.branch = branch;
        this.IFSCCode = IFSCCode;
        this.accountTypeId = accountTypeId;
        this.bkFilename = bkFilename;
    }

    //
    public TalentpoolInfo(String joiningdate, String enddate, String reason, String terminateremark, int uId, int candidateId, String fileName1) {
        this.joiningDate = joiningdate;
        this.endDate = enddate;
        this.reason = reason;
        this.terminateRemark = terminateremark;
        this.userId = uId;
        this.candidateId = candidateId;
        this.filename = fileName1;
    }

    public TalentpoolInfo(int candidateId, int positionid, int departmentid, String assetname,
            String startdate, String enddate, String companyname, int positionid2, String filename) {
        this.candidateId = candidateId;
        this.positionId = positionid;
        this.departmentId = departmentid;
        this.assetName = assetname;
        this.joiningDate = startdate;
        this.endDate = enddate;
        this.companyname = companyname;
        this.positionId2 = positionid2;
        this.filename = filename;
    }

    // for health 
    public TalentpoolInfo(String ssmf, String ogukmedicalftw, String ogukexp, String medifitcert, String medifitcertexp, String bloodgroup, int bloodpressure,
            String hypertension, String diabetes, String smoking, String cov192doses, String covid19vaccinestatus, String vaccine1dose, String vaccine1date,
            String vaccine2dose, String vaccine2date, String vaccine3dose, String vaccine3date, String mfFilename) {
        this.ssmf = ssmf;
        this.ogukmedicalftw = ogukmedicalftw;
        this.ogukexp = ogukexp;
        this.medifitcert = medifitcert;
        this.medifitcertexp = medifitcertexp;
        this.bloodgroup = bloodgroup;
        this.bloodpressureId = bloodpressure;
        this.hypertension = hypertension;
        this.diabetes = diabetes;
        this.smoking = smoking;
        this.cov192doses = cov192doses;
        this.covid19vaccinestatus = covid19vaccinestatus;
        this.vaccine1dose = vaccine1dose;
        this.vaccine1date = vaccine1date;
        this.vaccine2dose = vaccine2dose;
        this.vaccine2date = vaccine2date;
        this.vaccine3dose = vaccine3dose;
        this.vaccine3date = vaccine3date;
        this.mfFilename = mfFilename;
    }

    //for ddl
    public TalentpoolInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public TalentpoolInfo(String name, int status) {
        this.languageName = name;
        this.status = status;
    }

    public TalentpoolInfo(int assetId, int toasset, String joiningdate1, String remark, int userid, int candidateId, int type, int clientId,
            int toClientId, int toPositionId, int toCurrencyId, String torate1, String torate2, String torate3,
            int toPositionId2, int toCurrencyId2, String top2rate1, String top2rate2, String top2rate3, String endDate1, String joiningdate2, String endDate2) {
        this.clientassetId = assetId;
        this.toAssetId = toasset;
        this.joiningDate1 = joiningdate1;
        this.transferRemark = remark;
        this.userId = userid;
        this.candidateId = candidateId;
        this.transferType = type;
        this.clientId = clientId;
        this.toClientId = toClientId;
        this.toPositionId = toPositionId;
        this.toCurrencyId = toCurrencyId;
        this.torate1 = torate1;
        this.torate2 = torate2;
        this.torate3 = torate3;
        this.toPositionId2 = toPositionId2;
        this.toCurrencyId2 = toCurrencyId2;
        this.top2rate1 = top2rate1;
        this.top2rate2 = top2rate2;
        this.top2rate3 = top2rate3;
        this.endDate1 = endDate1;
        this.joiningDate2 = joiningdate2;
        this.endDate2 = endDate2;
    }

    //Contract Mail
    public TalentpoolInfo(String candidatename, int candidateId, String position,
            String email, String clientname, String assetname, String ccaddress) {
        this.name = candidatename;
        this.candidateId = candidateId;
        this.position = position;
        this.emailId = email;
        this.clientName = clientname;
        this.assetName = assetname;
        this.ccaddress = ccaddress;
    }

    //For contract modal details
    public TalentpoolInfo(int contractId, String file1, String file2, String file3, String date1,
            String date2, String date3, String username1, String username2, String username3, String contractName) {
        this.contractdetailId = contractId;
        this.file1 = file1;
        this.file2 = file2;
        this.file3 = file3;
        this.date1 = date1;
        this.date2 = date2;
        this.date3 = date3;
        this.username1 = username1;
        this.username2 = username2;
        this.username3 = username3;
        this.contractName = contractName;
    }

    //For contract listing
    public TalentpoolInfo(int contractdetailId, String clientName, String assetName,
            String positionName, String gradeName, int type,
            String fromDate, String toDate, String file1, String file2, String file3,
            int status, int approva1, int approva2, String stval, int userId, int contractId, String employeeId, int candidateId) {
        this.contractdetailId = contractdetailId;
        this.clientName = clientName;
        this.assetName = assetName;
        this.positionname = positionName;
        this.gradename = gradeName;
        this.type = type;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.file1 = file1;
        this.file2 = file2;
        this.file3 = file3;
        this.status = status;
        this.approval1 = approva1;
        this.approval2 = approva2;
        this.statusValue = stval;
        this.userId = userId;
        this.contractId = contractId;
        this.employeeId = employeeId;
        this.candidateId = candidateId;
    }

    //For contract modify
    public TalentpoolInfo(int contractId, String file1, String file2, String file3, String fromDate,
            String toDate, int type, int refId, int status, String remarks1, String remarks2,
            String val1, String val2, String val3, String val4, String val5, String val6,
            String val7, String val8, String val9, String val10) {
        this.contractId = contractId;
        this.file1 = file1;
        this.file2 = file2;
        this.file3 = file3;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.type = type;
        this.refId = refId;
        this.status = status;
        this.remarks1 = remarks1;
        this.remarks2 = remarks2;
        this.cval1 = val1;
        this.cval2 = val2;
        this.cval3 = val3;
        this.cval4 = val4;
        this.cval5 = val5;
        this.cval6 = val6;
        this.cval7 = val7;
        this.cval8 = val8;
        this.cval9 = val9;
        this.cval10 = val10;
    }

    //For contarct file modal
    public TalentpoolInfo(String file1, String file2, String file3, String date1, String date2, String date3, String username) {
        this.file1 = file1;
        this.file2 = file2;
        this.file3 = file3;
        this.date1 = date1;
        this.date2 = date2;
        this.date3 = date3;
        this.username = username;
    }

    //For approve1 upload
    public TalentpoolInfo(int contractdetailId, int candidateId, String remarks, String file2) {
        this.contractdetailId = contractdetailId;
        this.candidateId = candidateId;
        this.remarks = remarks;
        this.file2 = file2;
    }

    //for index
    public TalentpoolInfo(int candidateId, String name,
            String countryName, int status, String date, String cityName, String positionName, String firstname,
            String clientName, String clientAsset, int progressId, int alertCount, int vflag, int clientId,
            String gradeName, String position, String employeeId) {
        this.candidateId = candidateId;
        this.name = name;
        this.countryName = countryName;
        this.status = status;
        this.date = date;
        this.city = cityName;
        this.position = positionName;
        this.firstname = firstname;
        this.clientName = clientName;
        this.clientAsset = clientAsset;
        this.progressId = progressId;
        this.alertCount = alertCount;
        this.vflag = vflag;
        this.clientId = clientId;
        this.gradename = gradeName;
        this.positionname = position;
        this.employeeId = employeeId;
    }

    // For excel 
    public TalentpoolInfo(int candidateId, String name, String clientCountry, int status, String date, String positionName, String firstName, String clientName,
            String clientAsset, int progressId, int alertCount, int vflag, int clientId, String gradeName, String position, String city, String primarycontact,
            String econtact, String seccontact, String dob, String placeofbirth, String gender, String maritalstatus, String Nextofkin, String Relation, String address1line1,
            String address1line2, String address1line3, String address2line1, String address2line2, String address2line3, String email, String employeeId, String assettype,
            String countryName, String prefferdept, int ct1, int ct2, int ct3, int ct4, int ct5, int ct6, int ct8, int ct9, String nationality, String currency,
            double rate1, double rate2, int expectedsalary, String fromDate, String remarks, String reason, String endDate) {
        this.candidateId = candidateId;
        this.name = name;
        this.clientCountry = clientCountry;
        this.status = status;
        this.date = date;
        this.position = positionName;
        this.firstname = firstName;
        this.clientName = clientName;
        this.clientAsset = clientAsset;
        this.progressId = progressId;
        this.alertCount = alertCount;
        this.vflag = vflag;
        this.clientId = clientId;
        this.city = city;
        this.contactno1 = primarycontact;
        this.econtactno1 = econtact;
        this.contactno2 = seccontact;
        this.dob = dob;
        this.placeofbirth = placeofbirth;
        this.gender = gender;
        this.maritialstatus = maritalstatus;
        this.nextofkin = Nextofkin;
        this.relation = Relation;
        this.address1line1 = address1line1;
        this.address1line2 = address1line2;
        this.address1line3 = address1line3;
        this.address2line1 = address2line1;
        this.address2line2 = address2line2;
        this.address2line3 = address2line3;
        this.emailId = email;
        this.employeeId = employeeId;
        this.assettype = assettype;
        this.countryName = countryName;
        this.department = prefferdept;
        this.ct1 = ct1;
        this.ct2 = ct2;
        this.ct3 = ct3;
        this.ct4 = ct4;
        this.ct5 = ct5;
        this.ct6 = ct6;
        this.ct8 = ct8;
        this.ct9 = ct9;
        this.nationality = nationality;
        this.currency = currency;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.expectedsalary = expectedsalary;
        this.joiningDate = fromDate;
        this.remarks = remarks;
        this.reason = reason;
        this.endDate = endDate;
    }

    //for view
    public TalentpoolInfo(int candidateId, String name, String dob, String place, String email, String contact1_code, String contact1, String contact2_code, String contact2,
            String contact3_code, String contact3, String gender, String countryName, String nationality, String address1line1, String address1line2, String address2line1,
            String address2line2, String address2line3, String nextofkin, String relation, String econtact1_code, String econtact1, String econtact2_code, String econtact2,
            String maritialstatus, String photo, String position, String address1line3, String cityName, String experiencedept, int expectedsalary, String currency,
            int filecount, String firstname, int vflag, String assettype, int clientId, String assetName, int assetId, String clientName, String employeeId,
            int progressId, String applytypevalue, int positionId, String ratetype, double rate1, double rate2) {
        this.applytypevalue = applytypevalue;
        this.candidateId = candidateId;
        this.name = name;
        this.dob = dob;
        this.place = place;
        this.emailId = email;
        this.code1Id = contact1_code;
        this.contactno1 = contact1;
        this.code2Id = contact2_code;
        this.contactno2 = contact2;
        this.code3Id = contact3_code;
        this.contactno3 = contact3;
        this.gender = gender;
        this.countryName = countryName;
        this.nationality = nationality;
        this.address1line1 = address1line1;
        this.address1line2 = address1line2;
        this.address2line1 = address2line1;
        this.address2line2 = address2line2;
        this.address2line3 = address2line3;
        this.nextofkin = nextofkin;
        this.relation = relation;
        this.ecode1Id = econtact1_code;
        this.econtactno1 = econtact1;
        this.ecode2Id = econtact2_code;
        this.econtactno2 = econtact2;
        this.maritialstatus = maritialstatus;
        this.photo = photo;
        this.position = position;
        this.address1line3 = address1line3;
        this.city = cityName;
        this.department = experiencedept;
        this.expectedsalary = expectedsalary;
        this.currency = currency;
        this.filecount = filecount;
        this.firstname = firstname;
        this.vflag = vflag;
        this.assettype = assettype;
        this.clientId = clientId;
        this.assetName = assetName;
        this.toAssetId = assetId;
        this.clientName = clientName;
        this.employeeId = employeeId;
        this.progressId = progressId;
        this.positionId = positionId;
        this.ratetype = ratetype;
        this.rate1 = rate1;
        this.rate2 = rate2;
    }

    //for view
    public TalentpoolInfo(int candidateId, String name, String dob, String place, String email, String contact1_code, String contact1, String contact2_code, String contact2,
            String contact3_code, String contact3, String gender, String countryName, String nationality, String address1line1, String address1line2, String address2line1,
            String address2line2, String address2line3, String nextofkin, String relation, String econtact1_code, String econtact1, String econtact2_code, String econtact2,
            String maritialstatus, String photo, String position, String address1line3, String cityName, String experiencedept, int expectedsalary, String currency,
            int filecount, String firstname, int vflag, String assettype, int clientId, String assetName, int assetId, String clientName, String employeeId,
            int progressId, String applytypevalue, int positionId, String ratetype, double rate1, double rate2, int status, String religion,
            String position2, double p2rate1, double p2rate2, int positionId2, int travelDays, String state, String pincode, int age, String airport1,
            String airport2, int assetTypeId, String date2, String profile, String skill1, String skill2) {

        this.candidateId = candidateId;
        this.name = name;
        this.dob = dob;
        this.place = place;
        this.emailId = email;
        this.code1Id = contact1_code;
        this.contactno1 = contact1;
        this.code2Id = contact2_code;
        this.contactno2 = contact2;
        this.code3Id = contact3_code;
        this.contactno3 = contact3;
        this.gender = gender;
        this.countryName = countryName;
        this.nationality = nationality;
        this.address1line1 = address1line1;
        this.address1line2 = address1line2;
        this.address2line1 = address2line1;
        this.address2line2 = address2line2;
        this.address2line3 = address2line3;
        this.nextofkin = nextofkin;
        this.relation = relation;
        this.ecode1Id = econtact1_code;
        this.econtactno1 = econtact1;
        this.ecode2Id = econtact2_code;
        this.econtactno2 = econtact2;
        this.maritialstatus = maritialstatus;
        this.photo = photo;
        this.position = position;
        this.address1line3 = address1line3;
        this.city = cityName;
        this.department = experiencedept;
        this.expectedsalary = expectedsalary;
        this.currency = currency;
        this.filecount = filecount;
        this.firstname = firstname;
        this.vflag = vflag;
        this.assettype = assettype;
        this.clientId = clientId;
        this.assetName = assetName;
        this.toAssetId = assetId;
        this.clientName = clientName;
        this.employeeId = employeeId;
        this.progressId = progressId;
        this.applytypevalue = applytypevalue;
        this.positionId = positionId;
        this.ratetype = ratetype;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.status = status;
        this.religion = religion;
        this.positionname = position2;
        this.p2rate1 = p2rate1;
        this.p2rate2 = p2rate2;
        this.positionId2 = positionId2;
        this.travelDays = travelDays;
        this.stateName = state;
        this.pinCode = pincode;
        this.age = age;
        this.airport1 = airport1;
        this.airport2 = airport2;
        this.assettypeId = assetTypeId;
        this.date2 = date2;
        this.profile = profile;
        this.skill1 = skill1;
        this.skill2 = skill2;
    }

    //for language
    public TalentpoolInfo(String languageName, String proficiencyName) {
        this.languageName = languageName;
        this.proficiencyName = proficiencyName;
    }

    //For question Answer 
    public TalentpoolInfo(String question, String answer, int healthfileId) {
        this.question = question;
        this.answer = answer;
    }

    //Healthfilelist
    public TalentpoolInfo(int healthfileId, String filename, String date) {
        this.healthfileId = healthfileId;
        this.filename = filename;
        this.date = date;
    }

    //Nominee Listing
    public TalentpoolInfo(int nomineedetailId, String nomineeName, String nomineeContactno, String nomineeRelation,
            int status, String code1Id, String address, int age, double percentage, String employeeId, int candidateId) {
        this.nomineedetailId = nomineedetailId;
        this.nomineeName = nomineeName;
        this.nomineeContactno = nomineeContactno;
        this.nomineeRelation = nomineeRelation;
        this.status = status;
        this.code1Id = code1Id;
        this.address = address;
        this.age = age;
        this.percentage = percentage;
        this.employeeId = employeeId;
        this.candidateId = candidateId;
    }

    //for add-modify
    public TalentpoolInfo(int nomineedetailId, String nomineename, String nomineecontactno, int relationId,
            String code1Id, String address, int age, double percentage) {
        this.nomineedetailId = nomineedetailId;
        this.nomineeName = nomineename;
        this.nomineeContactno = nomineecontactno;
        this.relationId = relationId;
        this.code1Id = code1Id;
        this.address = address;
        this.age = age;
        this.percentage = percentage;
    }

    //save nominee
    public TalentpoolInfo(int candidateId, int nomineedetailId, String nomineeName, String nomineeContactno,
            int relationId, int status, String code1Id, String address, int age, double percentage) {
        this.candidateId = candidateId;
        this.nomineeName = nomineeName;
        this.nomineeContactno = nomineeContactno;
        this.relationId = relationId;
        this.status = status;
        this.code1Id = code1Id;
        this.address = address;
        this.age = age;
        this.percentage = percentage;
    }

    //For doctype excel
    public TalentpoolInfo(int candidateId, String documentname, int govId, String expirydate, String statusValue) {
        this.candidateId = candidateId;
        this.documentname = documentname;
        this.documenttypeId = govId;
        this.expirydate = expirydate;
        this.statusValue = statusValue;
    }

    //For doctype excel info
    public TalentpoolInfo(int candidateId, String name, String clientname, String assetname, String position) {
        this.candidateId = candidateId;
        this.name = name;
        this.clientName = clientname;
        this.assetName = assetname;
        this.position = position;
    }

    //create candidate
    public TalentpoolInfo(String firstname, String middlename, String lastname, String dob, String placeofbirth, String emailId, String code1,
            String code2, String code3, String contactno1, String contactno2, String contactno3, String gender, int countryId, int nationalityId, String address1line1,
            String address1line2, String address1line3, String address2line1, String address2line2, String address2line3, String nextofkin, int relationId, String ecode1,
            String ecode2, String econtactno1, String econtactno2, int maritalstatusId, String photofilename, int status, String resumefilename, int positionId,
            int departmentId, int currencyId, int expectedsalary, int cityId, int assettypeId, String employeeId, int applytype, double rate1, double rate2, String religion,
            int positionId2, double p2rate1, double p2rate2, int travelDays, int stateId, String pincode, int age, String airport1, String airport2, String profile,
            String skill1, String skill2) {
        this.applytype = applytype;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.dob = dob;
        this.placeofbirth = placeofbirth;
        this.emailId = emailId;
        this.code1Id = code1;
        this.code2Id = code2;
        this.code3Id = code3;
        this.contactno1 = contactno1;
        this.contactno2 = contactno2;
        this.contactno3 = contactno3;
        this.gender = gender;
        this.countryId = countryId;
        this.nationalityId = nationalityId;
        this.positionId = positionId;
        this.departmentId = departmentId;
        this.currencyId = currencyId;
        this.expectedsalary = expectedsalary;
        this.address1line3 = address1line3;
        this.address1line1 = address1line1;
        this.address1line2 = address1line2;
        this.address1line3 = address1line3;
        this.address2line1 = address2line1;
        this.address2line2 = address2line2;
        this.address2line3 = address2line3;
        this.nextofkin = nextofkin;
        this.relationId = relationId;
        this.ecode1Id = ecode1;
        this.ecode2Id = ecode2;
        this.econtactno1 = econtactno1;
        this.econtactno2 = econtactno2;
        this.maritalstatusId = maritalstatusId;
        this.photofilename = photofilename;
        this.resumefilename = resumefilename;
        this.status = status;
        this.cityId = cityId;
        this.assettypeId = assettypeId;
        this.employeeId = employeeId;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.religion = religion;
        this.positionId2 = positionId2;
        this.p2rate1 = p2rate1;
        this.p2rate2 = p2rate2;
        this.travelDays = travelDays;
        this.stateId = stateId;
        this.pinCode = pincode;
        this.age = age;
        this.airport1 = airport1;
        this.airport2 = airport2;
        this.profile = profile;
        this.skill1 = skill1;
        this.skill2 = skill2;
    }

    //create bankddetails
    public TalentpoolInfo(int candidateId, String bankName, String savingAccountNo,
            String branch, String IFSCCode, int accountTypeId, String fileName1,
            int status, int primarybankId, String accountHolder) {
        this.candidateId = candidateId;
        this.bankName = bankName;
        this.savingAccountNo = savingAccountNo;
        this.branch = branch;
        this.IFSCCode = IFSCCode;
        this.accountTypeId = accountTypeId;
        this.bkFilename = fileName1;
        this.status = status;
        this.primarybankId = primarybankId;
        this.accountHolder = accountHolder;
    }

    public TalentpoolInfo(int bankdetid, String bankname, String savingAccountNo, String branch,
            int accountTypeId, String ifsccode, String fileName, int primarybankid, String accountholder) {
        this.bankdetid = bankdetid;
        this.bankName = bankname;
        this.savingAccountNo = savingAccountNo;
        this.branch = branch;
        this.IFSCCode = ifsccode;
        this.accountTypeId = accountTypeId;
        this.bkFilename = fileName;
        this.primarybankId = primarybankid;
        this.accountHolder = accountholder;
    }

    //by candidateid for modify
    public TalentpoolInfo(String firstname, String middlename, String lastname, String dob, String placeofbirth, String email, String code1, String code2, String contactno1,
            String contactno2, String code3, String contactno3, String gender, int countryid, int nationalityid, String address1line1, String address1line2, String address2line1,
            String address2line2, String address2line3, String nextofkin, int relationid, String ecode1, String econtactno1, String ecode2, String econtactno2,
            int maritialstatusid, String photofilename, String address1line3, int departmentId, int positionId, int currencyId, int expectedsalary, int status, String cityName,
            int cityId, int filecount, int assettypeId, String employeeId, int applytype, String ratetype, String religion,
            int positionId2, int travelDays, int stateId, String pincode, int age, String airport1, String airport2, String profile,
            String skill1, String skill2) {

        this.applytype = applytype;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.dob = dob;
        this.placeofbirth = placeofbirth;
        this.emailId = email;
        this.code1Id = code1;
        this.code2Id = code2;
        this.contactno1 = contactno1;
        this.contactno2 = contactno2;
        this.code3Id = code3;
        this.contactno3 = contactno3;
        this.gender = gender;
        this.countryId = countryid;
        this.nationalityId = nationalityid;
        this.address1line1 = address1line1;
        this.address1line2 = address1line2;
        this.address1line3 = address1line3;
        this.address2line1 = address2line1;
        this.address2line2 = address2line2;
        this.address2line3 = address2line3;
        this.nextofkin = nextofkin;
        this.relationId = relationid;
        this.ecode1Id = ecode1;
        this.econtactno1 = econtactno1;
        this.ecode2Id = ecode2;
        this.econtactno2 = econtactno2;
        this.maritalstatusId = maritialstatusid;
        this.photofilename = photofilename;
        this.status = status;
        this.currencyId = currencyId;
        this.positionId = positionId;
        this.departmentId = departmentId;
        this.expectedsalary = expectedsalary;
        this.city = cityName;
        this.cityId = cityId;
        this.filecount = filecount;
        this.assettypeId = assettypeId;
        this.employeeId = employeeId;
        this.ratetype = ratetype;
        this.religion = religion;
        this.positionId2 = positionId2;
        this.travelDays = travelDays;
        this.stateId = stateId;
        this.pinCode = pincode;
        this.age = age;
        this.airport1 = airport1;
        this.airport2 = airport2;
        this.profile = profile;
        this.skill1 = skill1;
        this.skill2 = skill2;
    }

    // candidate lang
    public TalentpoolInfo(int candidatelangId, String langname, String proficiencyname, String filename, int status) {
        this.candlangid = candidatelangId;
        this.languageName = langname;
        this.proficiencyName = proficiencyname;
        this.candlangfilename = filename;
        this.status = status;
    }

    public TalentpoolInfo(int candidateLangId, int languageId, int proficiencyId, int status, String fileName1) {
        this.candlangid = candidateLangId;
        this.languageId = languageId;
        this.proficiencyId = proficiencyId;
        this.status = status;
        this.candlangfilename = fileName1;

    }

    public TalentpoolInfo(int languageId, int proficiencyId, String filename) {
        this.languageId = languageId;
        this.proficiencyId = proficiencyId;
        this.candlangfilename = filename;
    }

    //For offshore update    
    public TalentpoolInfo(int clientId, int clientassetId, int remarktypeId, String remarks, String filename, String date) {
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.remarktypeId = remarktypeId;
        this.remarks = remarks;
        this.offshorefile = filename;
        this.date = date;
    }

    //For offshore insert
    public TalentpoolInfo(int candidateId, int offshoreId, int clientId, int clientassetId, int remarktypeId,
            String remark, int status, String offshorefile, String date) {
        this.candidateId = candidateId;
        this.offshoreId = offshoreId;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.remarktypeId = remarktypeId;
        this.remarks = remark;
        this.status = status;
        this.offshorefile = offshorefile;
        this.date = date;
    }

    //For offshore listing
    public TalentpoolInfo(int offshoreId, String clinetName, String assetName, String remarktype,
            String remarks, int status, String offshorefile, String date, int candidateId, String employeeId) {
        this.offshoreId = offshoreId;
        this.clientName = clinetName;
        this.assetName = assetName;
        this.typeName = remarktype;
        this.remarks = remarks;
        this.status = status;
        this.offshorefile = offshorefile;
        this.date = date;
        this.candidateId = candidateId;
        this.employeeId = employeeId;
    }

    //candidate health
    public TalentpoolInfo(int candidateId, String ssmf, String ogukmedicalftw, String ogukexp, String medifitcert, String medifitcertexp, String bloodgroup, int bloodpressure,
            String hypertension, String diabetes, String smoking, String cov192doses, String covid19vaccinestatus, String vaccine1dose, String vaccine1date, String vaccine2dose,
            String vaccine2date, String vaccine3dose, String vaccine3date, String mfFilename, int status, int statu) {
        this.candidateId = candidateId;
        this.ssmf = ssmf;
        this.ogukmedicalftw = ogukmedicalftw;
        this.ogukexp = ogukexp;
        this.medifitcert = medifitcert;
        this.medifitcertexp = medifitcertexp;
        this.bloodgroup = bloodgroup;
        this.bloodpressureId = bloodpressure;
        this.hypertension = hypertension;
        this.diabetes = diabetes;
        this.smoking = smoking;
        this.cov192doses = cov192doses;
        this.covid19vaccinestatus = covid19vaccinestatus;
        this.vaccine1dose = vaccine1dose;
        this.vaccine1date = vaccine1date;
        this.vaccine2dose = vaccine2dose;
        this.vaccine2date = vaccine2date;
        this.vaccine3dose = vaccine3dose;
        this.vaccine3date = vaccine3date;
        this.mfFilename = mfFilename;
        this.status = status;

    }

    //candidatehealth
    public TalentpoolInfo(String ssmf, String ogukmedicalftw, String ogukexp, String medifitcert, String medifitcertexp, String bloodgroup, int bloodpressure,
            String hypertension, String diabetes, String smoking, String fileName1, int status, String cov192doses, double height, double weight) {
        this.ssmf = ssmf;
        this.ogukmedicalftw = ogukmedicalftw;
        this.ogukexp = ogukexp;
        this.medifitcert = medifitcert;
        this.medifitcertexp = medifitcertexp;
        this.bloodgroup = bloodgroup;
        this.bloodpressureId = bloodpressure;
        this.hypertension = hypertension;
        this.diabetes = diabetes;
        this.smoking = smoking;
        this.mfFilename = fileName1;
        this.status = status;
        this.cov192doses = cov192doses;
        this.height = height;
        this.weight = weight;
    }
    //Onboarding

    public TalentpoolInfo(String clientName, String clientassetName, String positionName, String gradeName, String arrivalDate, String mobdate, int onboardStatus,
            int candidateId, int clientId, int clientassetId, int shortlistid, String username) {
        this.clientName = clientName;
        this.position = positionName;
        this.arrivalDate = arrivalDate;
        this.mobDate = mobdate;
        this.onboardStatus = onboardStatus;
        this.candidateId = candidateId;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.shortlistId = shortlistid;
        this.username = username;
    }

    //get by health id
    public TalentpoolInfo(int candidatehealthId, String ssmf, String ogukmedicalftw, String ogukexp,
            String medifitcert, String medifitcertexp, String bloodgroup, int bloodpressureId,
            String hypertension, String diabetes, String smoking, int userId, String mfFilename,
            int status, String covid192doses, String bloodpressure, String employeeId, int candidateId, double height, double weight) {
        this.candidatehealthId = candidatehealthId;
        this.ssmf = ssmf;
        this.ogukmedicalftw = ogukmedicalftw;
        this.ogukexp = ogukexp;
        this.medifitcert = medifitcert;
        this.medifitcertexp = medifitcertexp;
        this.bloodgroup = bloodgroup;
        this.bloodpressureId = bloodpressureId;
        this.hypertension = hypertension;
        this.diabetes = diabetes;
        this.smoking = smoking;
        this.userId = userId;
        this.mfFilename = mfFilename;
        this.status = status;
        this.cov192doses = covid192doses;
        this.bloodpressure = bloodpressure;
        this.employeeId = employeeId;
        this.candidateId = candidateId;
        this.height = height;
        this.weight = weight;
    }

    // for vaccination
    public TalentpoolInfo(int vaccinationNameId, int vaccinationTypeId, int placeofapplication, String dateofapplication, String dateofexpiry, int status, String filename,
            String cityName) {
        this.vaccinationNameId = vaccinationNameId;
        this.vaccinationTypeId = vaccinationTypeId;
        this.placeofapplicationId = placeofapplication;
        this.dateofapplication = dateofapplication;
        this.dateofexpiry = dateofexpiry;
        this.status = status;
        this.vaccinecertfile = filename;
        this.placeofapplication = cityName;
    }

    //vaccination list
    public TalentpoolInfo(int candidatevaccId, String vaccinename, String vaccinetypename,
            String placeofapplication, String filename, int status, String dateofapplication,
            String dateofexpiry, String empId, int candidateId) {
        this.candidatevaccineId = candidatevaccId;
        this.vaccinationName = vaccinename;
        this.vacinationType = vaccinetypename;
        this.placeofapplication = placeofapplication;
        this.vaccinecertfile = filename;
        this.status = status;
        this.dateofapplication = dateofapplication;
        this.dateofexpiry = dateofexpiry;
        this.employeeId = empId;
        this.candidateId = candidateId;
    }

    public TalentpoolInfo(int candidatevaccineId, int vaccinationNameId, int vaccinationTypeId, int placeofapplicationId, String dateofapplication, String dateofexpiry,
            int status, String fileName1) {
        this.candidatevaccineId = candidatevaccineId;
        this.vaccinationNameId = vaccinationNameId;
        this.vaccinationTypeId = vaccinationTypeId;
        this.placeofapplicationId = placeofapplicationId;
        this.dateofapplication = dateofapplication;
        this.dateofexpiry = dateofexpiry;
        this.status = status;
        this.vaccinecertfile = fileName1;

    }

    //bank
    public TalentpoolInfo(int candidatebankId, String bankName, String bankAccounttype, String branch, String ifsccode, String filename, int status, int primaryBankId) {
        this.bankdetid = candidatebankId;
        this.bankName = bankName;
        this.accountTypename = bankAccounttype;
        this.branch = branch;
        this.IFSCCode = ifsccode;
        this.bkFilename = filename;
        this.status = status;
        this.primarybankId = primaryBankId;
    }

    //govdoc
    public TalentpoolInfo(int govdocumentId, String documentname, String documentno, String placeofissue, String issuedby, int status, String dateofissue,
            String dateofexpiry, String countryname, int filecount, String empId, int candidateId) {
        this.govdocumentId = govdocumentId;
        this.documentname = documentname;
        this.documentno = documentno;
        this.placeofissue = placeofissue;
        this.issuedby = issuedby;
        this.status = status;
        this.dateofissue = dateofissue;
        this.dateofexpiry = dateofexpiry;
        this.countryName = countryname;
        this.filecount = filecount;
        this.employeeId = empId;
        this.candidateId = candidateId;
    }

    //findby id
    public TalentpoolInfo(int documenttypeId, String documentno, int placeofissueId, int issuedbyId, String dateofissue, String dateofexpiry, int status, String cityName) {
        this.documenttypeId = documenttypeId;
        this.documentno = documentno;
        this.placeofissueId = placeofissueId;
        this.issuedbyId = issuedbyId;
        this.dateofissue = dateofissue;
        this.dateofexpiry = dateofexpiry;
        this.status = status;
        this.placeofissue = cityName;

    }

    //insert document
    public TalentpoolInfo(int govdocumentId, int documentTypeId, String documentno, int DocumentIssuedbyId, int cityId, String dateofissue, String dateofexpiry, int status) {
        this.govdocumentId = govdocumentId;
        this.documenttypeId = documentTypeId;
        this.documentno = documentno;
        this.placeofissueId = cityId;
        this.dateofissue = dateofissue;
        this.dateofexpiry = dateofexpiry;
        this.issuedbyId = DocumentIssuedbyId;
        this.status = status;
    }

    public TalentpoolInfo(int positionId, String companyshipname, String region, double tonnage, int countryId, String signIn, String signoff, int status, int enginetypeId) {
        this.positionId = positionId;
        this.companyshipname = companyshipname;
        this.region = region;
        this.tonnage = tonnage;
        this.countryId = countryId;
        this.signin = signIn;
        this.signoff = signoff;
        this.status = status;
        this.enginetypeId = enginetypeId;
    }

    //for verification tab
    public TalentpoolInfo(int candidateId, int cflag1, int cflag2, int cflag3, int cflag4, int cflag5, int cflag6, int cflag7, int cflag8, int cflag9, int cflag10) {
        this.candidateId = candidateId;
        this.cflag1 = cflag1;
        this.cflag2 = cflag2;
        this.cflag3 = cflag3;
        this.cflag4 = cflag4;
        this.cflag5 = cflag5;
        this.cflag6 = cflag6;
        this.cflag7 = cflag7;
        this.cflag8 = cflag8;
        this.cflag9 = cflag9;
        this.cflag10 = cflag10;
    }

    //for Training and certification
    public TalentpoolInfo(int coursetypeId, int coursenameId, String eduinstitute, int locationofInstituteId, String fieldofstudy,
            String coursestart, String passingyear, String dateofissue, String certificateno, String expirydate, String courseverification,
            int approvedbyId, String filename, int status, String cityName, String coursename) {
        this.coursetypeId = coursetypeId;
        this.coursenameId = coursenameId;
        this.eduinstitute = eduinstitute;
        this.locationofInstituteId = locationofInstituteId;
        this.fieldofstudy = fieldofstudy;
        this.coursestart = coursestart;
        this.passingyear = passingyear;
        this.dateofissue = dateofissue;
        this.certificateno = certificateno;
        this.expirydate = expirydate;
        this.courseverification = courseverification;
        this.approvedbyid = approvedbyId;
        this.certifilename = filename;
        this.status = status;
        this.city = cityName;
        this.coursename = coursename;
    }

    public TalentpoolInfo(int trainingandcertId, int coursetypeId, int coursenameId, String educationInstitute, int locationofInstitute, String fieldofstudy, String coursestarted,
            String passingyear, String dateofissue, String certificationno, String dateofexpiry, String courseverification, int approvedbyId, int status, String fileName1) {
        this.trainingandcertId = trainingandcertId;
        this.coursetypeId = coursetypeId;
        this.coursenameId = coursenameId;
        this.eduinstitute = educationInstitute;
        this.locationofInstituteId = locationofInstitute;
        this.fieldofstudy = fieldofstudy;
        this.coursestart = coursestarted;
        this.passingyear = passingyear;
        this.dateofissue = dateofissue;
        this.certificateno = certificationno;
        this.dateofexpiry = dateofexpiry;
        this.courseverification = courseverification;
        this.approvedbyid = approvedbyId;
        this.status = status;
        this.certifilename = fileName1;
    }

    //For Training-cert list
    public TalentpoolInfo(int candidateId, String coursename, String coursetype, String educinst, String approvedby, String dateofissue,
            String expirydate, int status, int trainingandcertId, String filename, int coursenameId, String url) {
        this.candidateId = candidateId;
        this.coursename = coursename;
        this.coursetype = coursetype;
        this.eduinstitute = educinst;
        this.approvedby = approvedby;
        this.dateofissue = dateofissue;
        this.expirydate = expirydate;
        this.status = status;
        this.trainingandcertId = trainingandcertId;
        this.certifilename = filename;
        this.coursenameId = coursenameId;
        this.url = url;
    }

    // for education detail
    public TalentpoolInfo(int kindId, int degreeId, String backgroundofstudy, String educinst, int locationofinstit, String fieldofstudy,
            String coursestart, String passingyear, int highestqualification, String filename, int status, String cityname) {
        this.kindId = kindId;
        this.degreeId = degreeId;
        this.backgroundofstudy = backgroundofstudy;
        this.eduinstitute = educinst;
        this.locationofInstituteId = locationofinstit;
        this.fieldofstudy = fieldofstudy;
        this.coursestart = coursestart;
        this.passingyear = passingyear;
        this.highestqualification = highestqualification;
        this.educationalfilename = filename;
        this.status = status;
        this.city = cityname;
    }
    //list

    public TalentpoolInfo(String kindname, String degree, String educinst, String locationofinstit, String filedofstudy, String startdate, String enddate, int status,
            String filename, int educationdetailId) {
        this.kindname = kindname;
        this.degree = degree;
        this.eduinstitute = educinst;
        this.locationofInstitute = locationofinstit;
        this.fieldofstudy = filedofstudy;
        this.coursestart = startdate;
        this.passingyear = enddate;
        this.status = status;
        this.educationalfilename = filename;
        this.educationdetailId = educationdetailId;
    }

    public TalentpoolInfo(int educationdetailId, int kindId, int degreeId, String backgroundofstudy, String educationInstitute, int locationofInstituteId, String fieldofstudy,
            String coursestarted, String passingdate, int highestqualification, int status, String fileName1) {
        this.educationdetailId = educationdetailId;
        this.kindId = kindId;
        this.degreeId = degreeId;
        this.backgroundofstudy = backgroundofstudy;
        this.eduinstitute = educationInstitute;
        this.locationofInstituteId = locationofInstituteId;
        this.fieldofstudy = fieldofstudy;
        this.coursestart = coursestarted;
        this.passingyear = passingdate;
        this.highestqualification = highestqualification;
        this.status = status;
        this.educationalfilename = fileName1;
    }

    public TalentpoolInfo(String companyName, int companyindustryId, int assettypeId, String assetName, int positionId, int departmentId, int countryId, int cityId,
            String clientpartyname, int waterdepthId, int lastdrawnsalarycurrencyId, int currentworkingstatus, int skillId, int gradeId, String ownerpool, int crewtypeId,
            String ocsemployed, String legalrights, int dayratecurrencyId, int dayrate, int monthlysalarycurrencyId, int monthlysalary, String workstartdate, String workenddate,
            String lastdrawnsalary, String filenamework, String filenameexp, String cityname, int status, String roles) {
        this.companyname = companyName;
        this.companyindustryId = companyindustryId;
        this.assettypeId = assettypeId;
        this.assetName = assetName;
        this.positionId = positionId;
        this.departmentId = departmentId;
        this.countryId = countryId;
        this.cityId = cityId;
        this.clientpartyname = clientpartyname;
        this.waterdepthId = waterdepthId;
        this.lastdrawnsalarycurrencyId = lastdrawnsalarycurrencyId;
        this.currentworkingstatus = currentworkingstatus;
        this.skillsId = skillId;
        this.gradeId = gradeId;
        this.ownerpool = ownerpool;
        this.crewtypeid = crewtypeId;
        this.ocsemployed = ocsemployed;
        this.legalrights = legalrights;
        this.dayratecurrencyid = dayratecurrencyId;
        this.dayrate = dayrate;
        this.monthlysalarycurrencyId = monthlysalarycurrencyId;
        this.monthlysalary = monthlysalary;
        this.workstartdate = workstartdate;
        this.workenddate = workenddate;
        this.lastdrawnsalary = lastdrawnsalary;
        this.workfilename = filenamework;
        this.experiencefilename = filenameexp;
        this.city = cityname;
        this.status = status;
        this.role = roles;
    }

    public TalentpoolInfo(String position, String department, String companyname, String assetname, String startdate, String enddate, int status, String workfilename,
            String experiencefilename, int experiencedetailId) {
        this.position = position;
        this.department = department;
        this.companyname = companyname;
        this.assetName = assetname;
        this.workstartdate = startdate;
        this.workenddate = enddate;
        this.status = status;
        this.workfilename = workfilename;
        this.experiencefilename = experiencefilename;
        this.experiencedetailId = experiencedetailId;
    }

    //Experience save
    public TalentpoolInfo(int experiencedetailId, String companyName, int comnpanyindustryId, int assettypeId, String assetname, int positionId, int departmentId, int countryId,
            int cityId, String clientpartyname, int waterdepthId, String lastdrawnsalary, String workstartdate, String workenddate, int currentworkstatus, int skillId, int gradeId,
            String ownerpool, int crewtypeId, String ocsemployed, String legalrights, int dayrate, int monthlysalary, int dayratecurrencyId, int monthlysalarycurrencyId,
            int lastdrawnsalarycurrencyId, int status, String fileName1, String fileName2, String role) {
        this.experiencedetailId = experiencedetailId;
        this.companyname = companyName;
        this.companyindustryId = comnpanyindustryId;
        this.assettypeId = assettypeId;
        this.assetName = assetname;
        this.positionId = positionId;
        this.departmentId = departmentId;
        this.countryId = countryId;
        this.cityId = cityId;
        this.clientpartyname = clientpartyname;
        this.waterdepthId = waterdepthId;
        this.lastdrawnsalary = lastdrawnsalary;
        this.workstartdate = workstartdate;
        this.workenddate = workenddate;
        this.currentworkingstatus = currentworkstatus;
        this.skillsId = skillId;
        this.gradeId = gradeId;
        this.ownerpool = ownerpool;
        this.crewtypeid = crewtypeId;
        this.ocsemployed = ocsemployed;
        this.legalrights = legalrights;
        this.dayrate = dayrate;
        this.monthlysalary = monthlysalary;
        this.dayratecurrencyid = dayratecurrencyId;
        this.monthlysalarycurrencyId = monthlysalarycurrencyId;
        this.lastdrawnsalarycurrencyId = lastdrawnsalarycurrencyId;
        this.status = status;
        this.experiencefilename = fileName1;
        this.workfilename = fileName2;
        this.role = role;
    }

    //Preview All resume modal
    public TalentpoolInfo(int fileId, String filename, String date, String name, String localFile, int a) {
        this.fileid = fileId;
        this.filename = filename;
        this.date = date;
        this.name = name;
        this.localFile = localFile;
    }

    //For Competency Feedback modal
    public TalentpoolInfo(String role, String date, String remarks, int trackerId) {
        this.role = role;
        this.date = date;
        this.remarks = remarks;
        this.trackerId = trackerId;
    }

    public TalentpoolInfo(int vstatusId, String date, String time, String username, String tabname, String authority, int status, String filename) {
        this.vStatusId = vstatusId;
        this.date = date;
        this.time = time;
        this.name = username;
        this.tabName = tabname;
        this.authority = authority;
        this.status = status;
        this.filename = filename;
    }

    public TalentpoolInfo(int cassessmenthId, int cassessmentId, int candidateId, String coordinatorName, String assessorName, String assessment, String statusValue,
            double marks, String date, String time, int status, int attempt, int showflag, int paId) {
        this.cassessmenthId = cassessmenthId;
        this.cassessmentId = cassessmentId;
        this.candidateId = candidateId;
        this.coordinatorName = coordinatorName;
        this.assessorName = assessorName;
        this.assessment = assessment;
        this.statusValue = statusValue;
        this.marks = marks;
        this.date = date;
        this.time = time;
        this.status = status;
        this.attempt = attempt;
        this.showflag = showflag;
        this.paId = paId;
    }

    //compliance tab
    public TalentpoolInfo(int jobpostId, String date, String checkpointName, String userName, String statusValue, int shortlistId, int shortlistStatus, int candidateId) {
        this.jobpostId = jobpostId;
        this.date = date;
        this.name = checkpointName;
        this.userName = userName;
        this.statusValue = statusValue;
        this.shortlistId = shortlistId;
        this.shortlistStatus = shortlistStatus;
        this.candidateId = candidateId;
    }

    //for Shortlisting history
    public TalentpoolInfo(int jobpostId, String poston, String client, String clientasset, String position, String grade, String shortliston,
            String shortlistBy, String statusvalue, String code) {
        this.jobpostId = jobpostId;
        this.poston = poston;
        this.clientName = client;
        this.clientAsset = clientasset;
        this.position = position;
        this.gradename = grade;
        this.shortliston = shortliston;
        this.name = shortlistBy;
        this.statusvalue = statusvalue;
        this.code = code;
    }

    //for client selection
    public TalentpoolInfo(int jobpostId, int candidateId, String date, String clientName, String positionName, String statusValue, int maillogId, int usertype, int shortlistId,
            int sflag, int oflag) {
        this.jobpostId = jobpostId;
        this.candidateId = candidateId;
        this.date = date;
        this.clientName = clientName;
        this.positionname = positionName;
        this.statusValue = statusValue;
        this.maillogId = maillogId;
        this.usertype = usertype;
        this.shortlistId = shortlistId;
        this.sflag = sflag;
        this.oflag = oflag;
    }

    //private int oflag;
    public TalentpoolInfo(String mailto, String mailfrom, String mailcc, String mailbcc, String subject, String filename, String attachmentpath, String sentby, String mailby,
            String maildate, String srby, String srdate, int shortlistId, int maillogId, String date, String usertypevalue, int sflag, String remarks, String reason, int oflag,
            String omailby, String omaildate, String oadby, String oaddate, String oadreason, String oadremarks) {
        this.mailto = mailto;
        this.mailfrom = mailfrom;
        this.mailcc = mailcc;
        this.mailbcc = mailbcc;
        this.subject = subject;
        this.filename = filename;
        this.attachmentpath = attachmentpath;
        this.sentby = sentby;
        this.mailby = mailby;
        this.maildate = maildate;
        this.srby = srby;
        this.srdate = srdate;
        this.shortlistId = shortlistId;
        this.maillogId = maillogId;
        this.date = date;
        this.usertypevalue = usertypevalue;
        this.sflag = sflag;
        this.remarks = remarks;
        this.reason = reason;
        this.oflag = oflag;
        this.omailby = omailby;
        this.omaildate = omaildate;
        this.oadby = oadby;
        this.oaddate = oaddate;
        this.oadreason = oadreason;
        this.oadremarks = oadremarks;
    }

    // For excel 
    public TalentpoolInfo(int candidateId, String name, String countryName, int status, String date, String cityName, String positionName, String contactno1, String econactno,
            String email, String dob, String placeofbirth, String gender, String maritalstatus, String nationality, String prefferddept, int expectedsalary, String currency) {
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

    //for alert modal
    public TalentpoolInfo(int alertId, int type, int tabno, String remarks, int userId, String date, String name) {

        this.alertId = alertId;
        this.type = type;
        this.tabno = tabno;
        this.remarks = remarks;
        this.userId = userId;
        this.date = date;
        this.name = name;
    }

    //for alert modal new
    public TalentpoolInfo(int alertId, int type, int tabno, String remarks, int userId, String date,
            String name, int documenttypeId, String documentname,
            int trainingandcertId, String coursename, int applytype) {

        this.alertId = alertId;
        this.type = type;
        this.tabno = tabno;
        this.remarks = remarks;
        this.userId = userId;
        this.date = date;
        this.name = name;
        this.documenttypeId = documenttypeId;
        this.documentname = documentname;
        this.trainingandcertId = trainingandcertId;
        this.coursename = coursename;
        this.applytype = applytype;
    }

    //
    public TalentpoolInfo(int alertId, int type, int tabno, String remarks, int userId, String date,
            String name, int documenttypeId, String documentname,
            String dateofexpiry, int trainingandcertId, String coursename, String expirydate) {

        this.alertId = alertId;
        this.type = type;
        this.tabno = tabno;
        this.remarks = remarks;
        this.userId = userId;
        this.date = date;
        this.name = name;
        this.documenttypeId = documenttypeId;
        this.documentname = documentname;
        this.dateofexpiry = dateofexpiry;
        this.trainingandcertId = trainingandcertId;
        this.coursename = coursename;
        this.expirydate = expirydate;
    }

    //For mobilization list 
    public TalentpoolInfo(int type, int crewrotationId, String mobdate, String clientName, String position, int candidateId) {
        this.type = type;
        this.crewrotationId = crewrotationId;
        this.mobDate = mobdate;
        this.clientName = clientName;
        this.position = position;
        this.candidateId = candidateId;
    }

    //rotation
    public TalentpoolInfo(String clientName, String clientassetName, String positionName, String gradeName, String signon, String signoff, int rotationStatus, int candidateId,
            String rotationStatusValue, int clientId, int clientassetId, int crewrotationId) {
        this.clientName = clientName;
        this.position = positionName;
        this.signon = signon;
        this.signoff = signoff;
        this.rotationStatus = rotationStatus;
        this.candidateId = candidateId;
        this.rotationStatusValue = rotationStatusValue;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.crewrotationId = crewrotationId;
    }

    //for notifications
    public TalentpoolInfo(String date, String documentname, String username, String filename, int candidateId) {
        this.date = date;
        this.documentname = documentname;
        this.userName = username;
        this.filename = filename;
        this.candidateId = candidateId;
    }

    //for Transfer/ Termination
    public TalentpoolInfo(int type, String date, String username, String remark, String assettname, String reason, String filename, String clientname,
            String position, String currency, String torate1, String torate2, String torate3) {
        this.type = type;
        this.date = date;
        this.userName = username;
        this.remarks = remark;
        this.assetName = assettname;
        this.reason = reason;
        this.filename = filename;
        this.clientName = clientname;
        this.position = position;
        this.currency = currency;
        this.torate1 = torate1;
        this.torate2 = torate2;
        this.torate3 = torate3;
    }

    //For Wellnes Feedback
    public TalentpoolInfo(int candidateId, String clientName, String clientAsset, String position, String gradename, String status, String feedback,
            String senton, String categoryName, int expiryflag, int surveyId) {
        this.clientName = clientName;
        this.clientAsset = clientAsset;
        this.position = position;
        this.gradename = gradename;
        this.statusValue = status;
        this.feedback = feedback;
        this.senton = senton;
        this.categoryName = categoryName;
        this.expiryflag = expiryflag;
        this.candidateId = candidateId;
        this.surveyId = surveyId;
    }

    //For Competency Listing
    public TalentpoolInfo(int candidateId, int trackerId, String role, String priority, String completedon, String validtill, int status,
            String assessorName, String clientname, String clientassetname, String position, String department, String feedbackdate, String statusValue) {
        this.candidateId = candidateId;
        this.trackerId = trackerId;
        this.role = role;
        this.priority = priority;
        this.completedon = completedon;
        this.validtill = validtill;
        this.status = status;
        this.assessorName = assessorName;
        this.clientName = clientname;
        this.clientAsset = clientassetname;
        this.position = position;
        this.department = department;
        this.statusValue = statusValue;
        this.date = feedbackdate;
    }

    public TalentpoolInfo(int surveyId, int clientassetId, int status, String submissionDate, String filleddate,
            String feedback, int subcategorywfId, String categoryName) {
        this.surveyId = surveyId;
        this.clientassetId = clientassetId;
        this.status = status;
        this.submissionDate = submissionDate;
        this.filleddate = filleddate;
        this.feedback = feedback;
        this.subcategorywfId = subcategorywfId;
        this.categoryName = categoryName;
    }

    //Assenow
    public TalentpoolInfo(int cassessnowhId, int cassessnowId, int candidateId,
            String positionName, String username, String statusValue, String date, String time, int status) {
        this.cassessnowhId = cassessnowhId;
        this.cassessnowId = cassessnowId;
        this.candidateId = candidateId;
        this.position = positionName;
        this.username = username;
        this.statusValue = statusValue;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public TalentpoolInfo(int candidateId, int radio1, int radio2, int radio3, int radio4, int radio5, int radio6, int radio7, int radio8, int radio9, int radio10,
            int radio11, int radio12, int radio13, int radio14, int radio15, int radio16, int radio17, int radio18, String cdescription, int status, int positionId) {
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
    //For Direct Onboard

    public TalentpoolInfo(int candidateId, String clientname, String positionName,
            String username, String date, String remarks) {
        this.candidateId = candidateId;
        this.clientName = clientname;
        this.positionname = positionName;
        this.username = username;
        this.date = date;
        this.remarks = remarks;
    }

    //for PPE listing
    public TalentpoolInfo(int ppedetailId, String remark, int status, String ppetype,
            String date, String username, String employeeId, int candidateId) {
        this.ppedetailId = ppedetailId;
        this.remarks = remark;
        this.status = status;
        this.ppetype = ppetype;
        this.date = date;
        this.username = username;
        this.employeeId = employeeId;
        this.candidateId = candidateId;
    }

    //For ppe modification
    public TalentpoolInfo(int ppetypeId, String remark, int status) {
        this.ppetypeId = ppetypeId;
        this.remarks = remark;
        this.status = status;
    }

    //For ppe adding
    public TalentpoolInfo(int candidateId, int ppetypeId, String remarks, int status) {
        this.candidateId = candidateId;
        this.ppetypeId = ppetypeId;
        this.remarks = remarks;
        this.status = status;
    }

    //For Appraisal Listing
    public TalentpoolInfo(int appraisalId, int positionId, String position, int positionId2,
            String position2, String remarks, String fromDate, int status, String filename,
            double newRate1, double newRate2, double newRate3, String toDate, String employeeId, int candidateId) {
        this.appraisalId = appraisalId;
        this.positionId = positionId;
        this.position = position;
        this.positionId2 = positionId2;
        this.position2 = position2;
        this.remarks = remarks;
        this.fromDate = fromDate;
        this.status = status;
        this.filename = filename;
        this.rate1 = newRate1;
        this.rate2 = newRate2;
        this.rate3 = newRate3;
        this.toDate = toDate;
        this.employeeId = employeeId;
        this.candidateId = candidateId;
    }

    //For hard position
    public TalentpoolInfo(int positionId, String position, int positionId2, String position2, int clientassetId) {
        this.positionId = positionId;
        this.position = position;
        this.positionId2 = positionId2;
        this.position2 = position2;
        this.clientassetId = clientassetId;
    }

    public TalentpoolInfo(double rate1, double rate2, double rate3) {
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.p2rate2 = rate3;
    }

    //For getDayRateForEdit
    public TalentpoolInfo(int dayratehId, double rate1, double rate2, double rate3, String fromDate, String toDate, int positionId) {
        this.dayrateId = dayratehId;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.positionId = positionId;
    }

    public TalentpoolInfo(int appraisalId, int positionId, int positionId2, double rate1, double rate2,
            double rate3, String fromDate, String toDate, int status, String fileName1, String remark, int checktype) {
        this.appraisalId = appraisalId;
        this.positionId = positionId;
        this.positionId2 = positionId2;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;
        this.filename = fileName1;
        this.remarks = remark;
        this.type = checktype;
    }

    public TalentpoolInfo(int positionId, int positionId2, String date, double rate1,
            double rate2, double rate3, String remarks, String filename, int type) {
        this.positionId = positionId;
        this.positionId2 = positionId2;
        this.date = date;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.remarks = remarks;
        this.filename = filename;
        this.type = type;
    }

    public TalentpoolInfo(double rate1, double rate2, double rate3, int positionId, int positionId2, double prate1, double prate2, double prate3) {
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.positionId = positionId;
        this.positionId2 = positionId2;
        this.p2rate1 = prate1;
        this.p2rate2 = prate2;
        this.p2rate3 = prate3;
    }

    //For Excel Data Report
    public TalentpoolInfo(int candidateId, String clientName, String clientAsset, String employeeId,
            String name, String positionName, String primarycontact, String econtact, String email, String dob,
            String gender, String maritalstatus, String countryName, String city, String address1line1,
            String address1line2, String address1line3, String nationality, String currency,
            double rate1, double rate2, int ct1, int ct2, int ct3, int ct4, int ct5, int ct6, int ct7,
            String contact2, String contact3, String placeofbirth, String nextofkin, String relation,
            String address2line1, String address2line2, String address2line3, String experiencedept, int expectedsalary) {
        this.candidateId = candidateId;
        this.clientName = clientName;
        this.clientAsset = clientAsset;
        this.employeeId = employeeId;
        this.name = name;
        this.position = positionName;
        this.contactno1 = primarycontact;
        this.econtactno1 = econtact;
        this.emailId = email;
        this.dob = dob;
        this.gender = gender;
        this.maritialstatus = maritalstatus;
        this.countryName = countryName;
        this.city = city;
        this.address1line1 = address1line1;
        this.address1line2 = address1line2;
        this.address1line3 = address1line3;
        this.nationality = nationality;
        this.currency = currency;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.ct1 = ct1;
        this.ct2 = ct2;
        this.ct3 = ct3;
        this.ct4 = ct4;
        this.ct5 = ct5;
        this.ct6 = ct6;
        this.ct7 = ct7;
        this.contactno2 = contact2;
        this.contactno3 = contact3;
        this.placeofbirth = placeofbirth;
        this.nextofkin = nextofkin;
        this.relation = relation;
        this.address2line1 = address2line1;
        this.address2line2 = address2line2;
        this.address2line3 = address2line3;
        this.department = experiencedept;
        this.expectedsalary = expectedsalary;
    }

    //For Dayrate Listing    
    TalentpoolInfo(int dayratehId, String position, String fromDate, String toDate, double rate1,
            double rate2, double rate3, int positionId, String employeeId, int candidateId) {
        this.dayrateId = dayratehId;
        this.position = position;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.positionId = positionId;
        this.employeeId = employeeId;
        this.candidateId = candidateId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
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

    public String getAddress1line2() {
        return address1line2;
    }

    public String getAddress2line1() {
        return address2line1;
    }

    public String getAddress2line2() {
        return address2line2;
    }

    public String getAddress2line3() {
        return address2line3;
    }

    public String getRelation() {
        return relation;
    }

    public String getMaritialstatus() {
        return maritialstatus;
    }

    public String getResumefilename() {
        return resumefilename;
    }

    public String getPhoto() {
        return photo;
    }

    public int getDdlValue() {
        return ddlValue;
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

    public String getAssetName() {
        return assetName;
    }

    public int getStatus() {
        return status;
    }

    public String getLanguageName() {
        return languageName;
    }

    public String getProficiencyName() {
        return proficiencyName;
    }

    public int getLanguageId() {
        return languageId;
    }

    public int getProficiencyId() {
        return proficiencyId;
    }

    public String getCandlangfilename() {
        return candlangfilename;
    }

    public String getDate() {
        return date;
    }

    public int getCandlangid() {
        return candlangid;
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

    public String getGender() {
        return gender;
    }

    public int getCountryId() {
        return countryId;
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

    public int getNationalityId() {
        return nationalityId;
    }

    public String getNextofkin() {
        return nextofkin;
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

    public String getAddress1line3() {
        return address1line3;
    }

    public String getAssettype() {
        return assettype;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getClientCountry() {
        return clientCountry;
    }

    public String getBankName() {
        return bankName;
    }

    public String getSavingAccountNo() {
        return savingAccountNo;
    }

    public String getBranch() {
        return branch;
    }

    public String getIFSCCode() {
        return IFSCCode;
    }

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public String getAccountTypename() {
        return accountTypename;
    }

    public String getBkFilename() {
        return bkFilename;
    }

    public int getBankdetid() {
        return bankdetid;
    }

    public int getPrimarybankId() {
        return primarybankId;
    }

    public String getSsmf() {
        return ssmf;
    }

    public String getOgukmedicalftw() {
        return ogukmedicalftw;
    }

    public String getOgukexp() {
        return ogukexp;
    }

    public String getMedifitcert() {
        return medifitcert;
    }

    public String getMedifitcertexp() {
        return medifitcertexp;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public int getBloodpressureId() {
        return bloodpressureId;
    }

    public String getHypertension() {
        return hypertension;
    }

    public String getDiabetes() {
        return diabetes;
    }

    public String getSmoking() {
        return smoking;
    }

    public String getCov192doses() {
        return cov192doses;
    }

    public String getCovid19vaccinestatus() {
        return covid19vaccinestatus;
    }

    public String getVaccine1dose() {
        return vaccine1dose;
    }

    public String getVaccine1date() {
        return vaccine1date;
    }

    public String getVaccine2dose() {
        return vaccine2dose;
    }

    public String getVaccine2date() {
        return vaccine2date;
    }

    public String getVaccine3dose() {
        return vaccine3dose;
    }

    public String getVaccine3date() {
        return vaccine3date;
    }

    public String getMfFilename() {
        return mfFilename;
    }

    public int getUserId() {
        return userId;
    }

    public int getCandidatehealthId() {
        return candidatehealthId;
    }

    public String getPositionname() {
        return positionname;
    }

    public String getCompanyshipname() {
        return companyshipname;
    }

    public String getRegion() {
        return region;
    }

    public String getEnginetype() {
        return enginetype;
    }

    public int getEnginetypeId() {
        return enginetypeId;
    }

    public double getTonnage() {
        return tonnage;
    }

    public String getSignin() {
        return signin;
    }

    public String getSignoff() {
        return signoff;
    }

    public int getCandidatevaccineId() {
        return candidatevaccineId;
    }

    public int getVaccinationNameId() {
        return vaccinationNameId;
    }

    public int getVaccinationTypeId() {
        return vaccinationTypeId;
    }

    public int getPlaceofapplicationId() {
        return placeofapplicationId;
    }

    public String getVaccinecertfile() {
        return vaccinecertfile;
    }

    public String getDateofapplication() {
        return dateofapplication;
    }

    public String getDateofexpiry() {
        return dateofexpiry;
    }

    public String getVaccinationName() {
        return vaccinationName;
    }

    public String getVacinationType() {
        return vacinationType;
    }

    public String getPlaceofapplication() {
        return placeofapplication;
    }

    public int getGovdocumentId() {
        return govdocumentId;
    }

    public String getDocumentname() {
        return documentname;
    }

    public String getDocumentno() {
        return documentno;
    }

    public String getPlaceofissue() {
        return placeofissue;
    }

    public String getIssuedby() {
        return issuedby;
    }

    public String getGovdocumentfile() {
        return govdocumentfile;
    }

    public int getDocumenttypeId() {
        return documenttypeId;
    }

    public int getPlaceofissueId() {
        return placeofissueId;
    }

    public int getIssuedbyId() {
        return issuedbyId;
    }

    public int getTrainingandcertId() {
        return trainingandcertId;
    }

    public int getCoursetypeId() {
        return coursetypeId;
    }

    public int getLocationofInstituteId() {
        return locationofInstituteId;
    }

    public int getApprovedbyid() {
        return approvedbyid;
    }

    public int getCoursenameId() {
        return coursenameId;
    }

    public int getFilecount() {
        return filecount;
    }

    public String getCoursetype() {
        return coursetype;
    }

    public String getCoursename() {
        return coursename;
    }

    public String getEduinstitute() {
        return eduinstitute;
    }

    public String getLocationofInstitute() {
        return locationofInstitute;
    }

    public String getDateofissue() {
        return dateofissue;
    }

    public String getCertificateno() {
        return certificateno;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public String getCourseverification() {
        return courseverification;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public String getCertifilename() {
        return certifilename;
    }

    public String getFieldofstudy() {
        return fieldofstudy;
    }

    public String getCoursestart() {
        return coursestart;
    }

    public String getPassingyear() {
        return passingyear;
    }

    public String getCurrency() {
        return currency;
    }

    public int getEducationdetailId() {
        return educationdetailId;
    }

    public String getKindname() {
        return kindname;
    }

    public int getKindId() {
        return kindId;
    }

    public String getDegree() {
        return degree;
    }

    public int getDegreeId() {
        return degreeId;
    }

    public String getEducationalfilename() {
        return educationalfilename;
    }

    public String getBackgroundofstudy() {
        return backgroundofstudy;
    }

    public int getHighestqualification() {
        return highestqualification;
    }

    public int getExperiencedetailId() {
        return experiencedetailId;
    }

    public String getCompanyname() {
        return companyname;
    }

    public int getCompanyindustryId() {
        return companyindustryId;
    }

    public int getAssettypeId() {
        return assettypeId;
    }

    public String getDepartment() {
        return department;
    }

    public String getClientpartyname() {
        return clientpartyname;
    }

    public int getWaterdepthId() {
        return waterdepthId;
    }

    public int getLastdrawnsalarycurrencyId() {
        return lastdrawnsalarycurrencyId;
    }

    public String getLastdrawnsalary() {
        return lastdrawnsalary;
    }

    public String getWorkstartdate() {
        return workstartdate;
    }

    public String getWorkenddate() {
        return workenddate;
    }

    public int getSkillsId() {
        return skillsId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public String getOwnerpool() {
        return ownerpool;
    }

    public int getCrewtypeid() {
        return crewtypeid;
    }

    public String getOcsemployed() {
        return ocsemployed;
    }

    public String getLegalrights() {
        return legalrights;
    }

    public int getDayratecurrencyid() {
        return dayratecurrencyid;
    }

    public int getDayrate() {
        return dayrate;
    }

    public int getMonthlysalarycurrencyId() {
        return monthlysalarycurrencyId;
    }

    public int getMonthlysalary() {
        return monthlysalary;
    }

    public int getCurrentworkingstatus() {
        return currentworkingstatus;
    }

    public String getExperiencefilename() {
        return experiencefilename;
    }

    public String getWorkfilename() {
        return workfilename;
    }

    public String getClientAsset() {
        return clientAsset;
    }

    public int getProgressId() {
        return progressId;
    }

    public int getAlertCount() {
        return alertCount;
    }

    public int getVflag() {
        return vflag;
    }

    public int getClientId() {
        return clientId;
    }

    public int getAlertId() {
        return alertId;
    }

    public int getType() {
        return type;
    }

    public int getTabno() {
        return tabno;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getGradename() {
        return gradename;
    }

    public int getvStatusId() {
        return vStatusId;
    }

    public String getTabName() {
        return tabName;
    }

    public String getTime() {
        return time;
    }

    public String getAuthority() {
        return authority;
    }

    public int getCassessmenthId() {
        return cassessmenthId;
    }

    public int getCassessmentId() {
        return cassessmentId;
    }

    public int getPaId() {
        return paId;
    }

    public int getAttempt() {
        return attempt;
    }

    public int getShowflag() {
        return showflag;
    }

    public double getMarks() {
        return marks;
    }

    public String getCoordinatorName() {
        return coordinatorName;
    }

    public String getAssessorName() {
        return assessorName;
    }

    public String getAssessment() {
        return assessment;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public int getCflag1() {
        return cflag1;
    }

    public int getCflag2() {
        return cflag2;
    }

    public int getCflag3() {
        return cflag3;
    }

    public int getCflag4() {
        return cflag4;
    }

    public int getCflag5() {
        return cflag5;
    }

    public int getCflag6() {
        return cflag6;
    }

    public int getCflag7() {
        return cflag7;
    }

    public int getCflag8() {
        return cflag8;
    }

    public int getCflag9() {
        return cflag9;
    }

    public int getCflag10() {
        return cflag10;
    }

    public int getJobpostId() {
        return jobpostId;
    }

    public String getUserName() {
        return userName;
    }

    public int getShortlistId() {
        return shortlistId;
    }

    public int getShortlistStatus() {
        return shortlistStatus;
    }

    public String getPoston() {
        return poston;
    }

    public String getShortliston() {
        return shortliston;
    }

    public String getStatusvalue() {
        return statusvalue;
    }

    public String getCode() {
        return code;
    }

    public int getSflag() {
        return sflag;
    }

    public int getMaillogId() {
        return maillogId;
    }

    public int getUsertype() {
        return usertype;
    }

    public int getOflag() {
        return oflag;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getMobDate() {
        return mobDate;
    }

    public int getOnboardStatus() {
        return onboardStatus;
    }

    public int getClientassetId() {
        return clientassetId;
    }

    public int getMobilizeStatus() {
        return mobilizeStatus;
    }

    public String getSignon() {
        return signon;
    }

    public String getRotationStatusValue() {
        return rotationStatusValue;
    }

    public int getRotationStatus() {
        return rotationStatus;
    }

    public int getCrewrotationId() {
        return crewrotationId;
    }

    public int getActiveStatus() {
        return activeStatus;
    }

    public int getFromClientId() {
        return fromClientId;
    }

    public int getToAssetId() {
        return toAssetId;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public String getTransferRemark() {
        return transferRemark;
    }

    public String getTerminateDate() {
        return terminateDate;
    }

    public String getTerminateRemark() {
        return terminateRemark;
    }

    public int getTransferType() {
        return transferType;
    }

    public int getExpectedsalary() {
        return expectedsalary;
    }

    public String getBloodpressure() {
        return bloodpressure;
    }

    public String getCity() {
        return city;
    }

    public int getFileid() {
        return fileid;
    }

    public String getFilename() {
        return filename;
    }

    public String getMailto() {
        return mailto;
    }

    public String getMailfrom() {
        return mailfrom;
    }

    public String getMailcc() {
        return mailcc;
    }

    public String getMailbcc() {
        return mailbcc;
    }

    public String getSubject() {
        return subject;
    }

    public String getAttachmentpath() {
        return attachmentpath;
    }

    public String getSentby() {
        return sentby;
    }

    public String getMailby() {
        return mailby;
    }

    public String getMaildate() {
        return maildate;
    }

    public String getSrby() {
        return srby;
    }

    public String getSrdate() {
        return srdate;
    }

    public String getUsertypevalue() {
        return usertypevalue;
    }

    public String getReason() {
        return reason;
    }

    public String getOmailby() {
        return omailby;
    }

    public String getOmaildate() {
        return omaildate;
    }

    public String getOadby() {
        return oadby;
    }

    public String getOaddate() {
        return oaddate;
    }

    public String getOadreason() {
        return oadreason;
    }

    public String getOadremarks() {
        return oadremarks;
    }

    /**
     * @return the ct1
     */
    public int getCt1() {
        return ct1;
    }

    /**
     * @return the ct2
     */
    public int getCt2() {
        return ct2;
    }

    /**
     * @return the ct3
     */
    public int getCt3() {
        return ct3;
    }

    /**
     * @return the ct4
     */
    public int getCt4() {
        return ct4;
    }

    /**
     * @return the ct5
     */
    public int getCt5() {
        return ct5;
    }

    /**
     * @return the ct6
     */
    public int getCt6() {
        return ct6;
    }

    /**
     * @return the ct7
     */
    public int getCt7() {
        return ct7;
    }

    /**
     * @return the ct8
     */
    public int getCt8() {
        return ct8;
    }

    /**
     * @return the ct9
     */
    public int getCt9() {
        return ct9;
    }

    /**
     * @return the joiningDate
     */
    public String getJoiningDate() {
        return joiningDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @return the joiningDate1
     */
    public String getJoiningDate1() {
        return joiningDate1;
    }

    /**
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
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
     * @return the senton
     */
    public String getSenton() {
        return senton;
    }

    /**
     * @return the completedon
     */
    public String getCompletedon() {
        return completedon;
    }

    /**
     * @return the expiryflag
     */
    public int getExpiryflag() {
        return expiryflag;
    }

    /**
     * @return the submissionDate
     */
    public String getSubmissionDate() {
        return submissionDate;
    }

    /**
     * @return the filleddate
     */
    public String getFilleddate() {
        return filleddate;
    }

    /**
     * @return the surveyId
     */
    public int getSurveyId() {
        return surveyId;
    }

    /**
     * @return the subcategorywfId
     */
    public int getSubcategorywfId() {
        return subcategorywfId;
    }

    /**
     * @return the cassessnowhId
     */
    public int getCassessnowhId() {
        return cassessnowhId;
    }

    /**
     * @return the cassessnowId
     */
    public int getCassessnowId() {
        return cassessnowId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
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

    public String getApplytypevalue() {
        return applytypevalue;
    }

    public int getApplytype() {
        return applytype;
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
     * @return the priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * @return the validtill
     */
    public String getValidtill() {
        return validtill;
    }

    /**
     * @return the ratetype
     */
    public String getRatetype() {
        return ratetype;
    }

    /**
     * @return the rate1
     */
    public double getRate1() {
        return rate1;
    }

    /**
     * @return the rate2
     */
    public double getRate2() {
        return rate2;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the healthfileId
     */
    public int getHealthfileId() {
        return healthfileId;
    }

    /**
     * @param healthfileId the healthfileId to set
     */
    public void setHealthfileId(int healthfileId) {
        this.healthfileId = healthfileId;
    }

    /**
     * @return the nomineeName
     */
    public String getNomineeName() {
        return nomineeName;
    }

    /**
     * @return the nomineeRelation
     */
    public String getNomineeRelation() {
        return nomineeRelation;
    }

    /**
     * @return the nomineeContactno
     */
    public String getNomineeContactno() {
        return nomineeContactno;
    }

    /**
     * @return the offshorefile
     */
    public String getOffshorefile() {
        return offshorefile;
    }

    /**
     * @return the typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @return the offshoreId
     */
    public int getOffshoreId() {
        return offshoreId;
    }

    /**
     * @return the remarktypeId
     */
    public int getRemarktypeId() {
        return remarktypeId;
    }

    /**
     * @return the nomineedetailId
     */
    public int getNomineedetailId() {
        return nomineedetailId;
    }

    /**
     * @return the ppedetailId
     */
    public int getPpedetailId() {
        return ppedetailId;
    }

    /**
     * @return the ppetypeId
     */
    public int getPpetypeId() {
        return ppetypeId;
    }

    /**
     * @return the ppetype
     */
    public String getPpetype() {
        return ppetype;
    }

    /**
     * @return the contractdetailId
     */
    public int getContractdetailId() {
        return contractdetailId;
    }

    /**
     * @return the file1
     */
    public String getFile1() {
        return file1;
    }

    /**
     * @return the file2
     */
    public String getFile2() {
        return file2;
    }

    /**
     * @return the file3
     */
    public String getFile3() {
        return file3;
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
     * @return the approval1
     */
    public int getApproval1() {
        return approval1;
    }

    /**
     * @return the approval2
     */
    public int getApproval2() {
        return approval2;
    }

    /**
     * @return the date1
     */
    public String getDate1() {
        return date1;
    }

    /**
     * @return the date2
     */
    public String getDate2() {
        return date2;
    }

    /**
     * @return the date3
     */
    public String getDate3() {
        return date3;
    }

    /**
     * @return the contractId
     */
    public int getContractId() {
        return contractId;
    }

    /**
     * @return the refId
     */
    public int getRefId() {
        return refId;
    }

    /**
     * @return the ccaddress
     */
    public String getCcaddress() {
        return ccaddress;
    }

    /**
     * @param ccaddress the ccaddress to set
     */
    public void setCcaddress(String ccaddress) {
        this.ccaddress = ccaddress;
    }

    /**
     * @return the username1
     */
    public String getUsername1() {
        return username1;
    }

    /**
     * @return the username2
     */
    public String getUsername2() {
        return username2;
    }

    /**
     * @return the username3
     */
    public String getUsername3() {
        return username3;
    }

    public int getToClientId() {
        return toClientId;
    }

    public int getToPositionId() {
        return toPositionId;
    }

    public int getToCurrencyId() {
        return toCurrencyId;
    }

    public String getTorate1() {
        return torate1;
    }

    public String getTorate2() {
        return torate2;
    }

    public String getTorate3() {
        return torate3;
    }

    /**
     * @return the religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * @return the remarks1
     */
    public String getRemarks1() {
        return remarks1;
    }

    /**
     * @return the remarks2
     */
    public String getRemarks2() {
        return remarks2;
    }

    /**
     * @return the contractName
     */
    public String getContractName() {
        return contractName;
    }

    /**
     * @return the cval1
     */
    public String getCval1() {
        return cval1;
    }

    /**
     * @return the cval2
     */
    public String getCval2() {
        return cval2;
    }

    /**
     * @return the cval3
     */
    public String getCval3() {
        return cval3;
    }

    /**
     * @return the cval4
     */
    public String getCval4() {
        return cval4;
    }

    /**
     * @return the cval5
     */
    public String getCval5() {
        return cval5;
    }

    /**
     * @return the cval6
     */
    public String getCval6() {
        return cval6;
    }

    /**
     * @return the cval7
     */
    public String getCval7() {
        return cval7;
    }

    /**
     * @return the cval8
     */
    public String getCval8() {
        return cval8;
    }

    /**
     * @return the cval9
     */
    public String getCval9() {
        return cval9;
    }

    /**
     * @return the cval10
     */
    public String getCval10() {
        return cval10;
    }

    /**
     * @return the onboardingfileId
     */
    public int getOnboardingfileId() {
        return onboardingfileId;
    }

    /**
     * @return the positionId2
     */
    public int getPositionId2() {
        return positionId2;
    }

    /**
     * @return the p2rate1
     */
    public double getP2rate1() {
        return p2rate1;
    }

    /**
     * @return the p2rate2
     */
    public double getP2rate2() {
        return p2rate2;
    }

    /**
     * @return the toPositionId2
     */
    public int getToPositionId2() {
        return toPositionId2;
    }

    /**
     * @return the toCurrencyId2
     */
    public int getToCurrencyId2() {
        return toCurrencyId2;
    }

    /**
     * @return the top2rate1
     */
    public String getTop2rate1() {
        return top2rate1;
    }

    /**
     * @return the top2rate2
     */
    public String getTop2rate2() {
        return top2rate2;
    }

    /**
     * @return the top2rate3
     */
    public String getTop2rate3() {
        return top2rate3;
    }

    /**
     * @return the appraisalId
     */
    public int getAppraisalId() {
        return appraisalId;
    }

    /**
     * @return the position2
     */
    public String getPosition2() {
        return position2;
    }

    /**
     * @return the rate3
     */
    public double getRate3() {
        return rate3;
    }

    /**
     * @return the travelDays
     */
    public int getTravelDays() {
        return travelDays;
    }

    /**
     * @return the p2rate3
     */
    public double getP2rate3() {
        return p2rate3;
    }

    /**
     * @return the stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @return the pinCode
     */
    public String getPinCode() {
        return pinCode;
    }

    /**
     * @return the stateId
     */
    public int getStateId() {
        return stateId;
    }

    /**
     * @return the oldRate1
     */
    public double getOldRate1() {
        return oldRate1;
    }

    /**
     * @return the oldRate2
     */
    public double getOldRate2() {
        return oldRate2;
    }

    /**
     * @return the oldRate3
     */
    public double getOldRate3() {
        return oldRate3;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @return the airport1
     */
    public String getAirport1() {
        return airport1;
    }

    /**
     * @return the airport2
     */
    public String getAirport2() {
        return airport2;
    }

    /**
     * @return the accountHolder
     */
    public String getAccountHolder() {
        return accountHolder;
    }

    /**
     * @return the localFile
     */
    public String getLocalFile() {
        return localFile;
    }

    /**
     * @return the dayrateId
     */
    public int getDayrateId() {
        return dayrateId;
    }

    /**
     * @return the endDate1
     */
    public String getEndDate1() {
        return endDate1;
    }

    /**
     * @return the joiningDate2
     */
    public String getJoiningDate2() {
        return joiningDate2;
    }

    /**
     * @return the endDate2
     */
    public String getEndDate2() {
        return endDate2;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the percentage
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * @return the profile
     */
    public String getProfile() {
        return profile;
    }

    /**
     * @return the skill1
     */
    public String getSkill1() {
        return skill1;
    }

    /**
     * @return the skill2
     */
    public String getSkill2() {
        return skill2;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

}
