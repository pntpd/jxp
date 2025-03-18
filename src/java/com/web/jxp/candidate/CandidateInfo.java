package com.web.jxp.candidate;

public class CandidateInfo {

    private int candidateId;
    private int ddlValue;
    private int status;
    private int languageId;
    private int proficiencyId;
    private int candlangid;
    private int countryId;
    private int cityId;
    private int positionId;
    private int departmentId;
    private int currencyId;
    private int nationalityId;
    private int maritalstatusId;
    private int relationId;
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
    private String employeeId;
    private String othercity;
    private String onlineStatus;
    private String religion;

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
    private String assettype;

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
    private int filecount;
    private int coursenameId;
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
    private int passflag;
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

    private int fileid;
    private int expectedsalary;
    private String filename;
    private String bloodpressure;
    private String city;
    
    //feedback
    private String companyindustry;
    private String country;
    private String lastdrawnsalarycurrency;
    private String skill;
    private String grade;
    private String crewtype;
    private String dayratecurrency;
    private String monthlysalarycurrency;
    private String waterdepth;
    private String waterdepthunit;
    
    private int applytype;
    private String applytypevalue;
    private String url;
    private int healthfileId;    
    private int positionId2;    
    
    
    //For Nominee
    private String nomineeName;
    private String nomineeRelation;
    private String nomineeContactno;
    private int nomineedetailId;
    private String address;
    private double percentage;
    
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
    private String role;
    
    //New changes
    private double height;
    private double weight;

    //info for bankdetails
    public CandidateInfo(String bankName, String savingAccountNo, String branch, String IFSCCode, int accountTypeId, String bkFilename) {
        this.bankName = bankName;
        this.savingAccountNo = savingAccountNo;
        this.branch = branch;
        this.IFSCCode = IFSCCode;
        this.accountTypeId = accountTypeId;
        this.bkFilename = bkFilename;
    }

    // for health 
    public CandidateInfo(String ssmf, String ogukmedicalftw, String ogukexp, String medifitcert, String medifitcertexp, String bloodgroup, int bloodpressure,
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
    public CandidateInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public CandidateInfo(String name, int status) {
        this.languageName = name;
        this.status = status;
    }
    
    //For header Search
    public CandidateInfo (int candidateId, String name, String positionName)
    {
        this.candidateId = candidateId;
        this.name = name;
        this.positionname = positionName;
    }

    //for index
    public CandidateInfo(int candidateId, String name,
            String countryName, int status, String date, String cityName, String positionName, String firstname, int pass,String onlineStatus) {
        this.candidateId = candidateId;
        this.name = name;
        this.countryName = countryName;
        this.status = status;
        this.date = date;
        this.city = cityName;
        this.position = positionName;
        this.firstname = firstname;
        this.passflag = pass;
        this.onlineStatus = onlineStatus;
    }

    // For excel 
    public CandidateInfo(int candidateId, String name,
            String countryName, int status, String date, String cityName, String positionName, String contactno1, String econactno, String email, String dob,
            String placeofbirth, String gender, String maritalstatus, String nationality, String prefferddept, int expectedsalary, String currency, String nextofkin,
            String relation, String address1line1, String address1line2, String address1line3, String address2line1, String address2line2, String address2line3,
            String gradeName, String seccontact, String assettype, String employeeId) {
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
        this.nextofkin = nextofkin;
        this.relation = relation;
        this.address1line1 = address1line1;
        this.address1line2 = address1line2;
        this.address1line3 = address1line3;
        this.address2line1 = address2line1;
        this.address2line2 = address2line2;
        this.address2line3 = address2line3;
        this.contactno3 = seccontact;
        this.assettype = assettype;
        this.employeeId = employeeId;
    }

    //for view
    public CandidateInfo(int candidateId, String name, String dob, String place, String email, String contact1_code, String contact1, String contact2_code, String contact2,
            String contact3_code, String contact3, String gender, String countryName, String nationality, String address1line1, String address1line2, String address2line1,
            String address2line2, String address2line3, String nextofkin, String relation, String econtact1_code, String econtact1, String econtact2_code, String econtact2,
            String maritialstatus, String photo, String position, String address1line3, String cityName, String experiencedept, int expectedsalary, String currency,
            int filecount, String firstname, int pass, String assettype, String employeeId, String onlineStatus, String applytypevalue,String middlename, String lastname,
            String religion, String position2, String state, String pincode, int age, String airport1, String airport2, String profile, 
            String skill1, String skill2) {
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
        this.passflag = pass;
        this.assettype = assettype;
        this.employeeId = employeeId;
        this.onlineStatus = onlineStatus;
        this.applytypevalue = applytypevalue;
        this.middlename = middlename;
        this.lastname = lastname;
        this.religion = religion;
        this.positionname =position2;
        this.stateName = state;
        this.pinCode = pincode;
        this.age = age;
        this.airport1 = airport1;
        this.airport2 = airport2;
        this.profile = profile;
        this.skill1 = skill1;
        this.skill2 = skill2;
    }

    //for language 
    public CandidateInfo(String languageName, String proficiencyName) {
        this.languageName = languageName;
        this.proficiencyName = proficiencyName;
    }
    //for HealthFileList
    public CandidateInfo(String filename, String date, int healthfileId) {
        this.filename = filename;
        this.date = date;
        this.healthfileId = healthfileId;
    }

    //create candidate
    public CandidateInfo(String firstname, String middlename, String lastname, String dob, String placeofbirth, String emailId, String code1,
            String code2, String code3, String contactno1, String contactno2, String contactno3, String gender, int countryId, int nationalityId, String address1line1,
            String address1line2, String address1line3, String address2line1, String address2line2, String address2line3, String nextofkin, int relationId, String ecode1,
            String ecode2, String econtactno1, String econtactno2, int maritalstatusId, String photofilename, int status, String resumefilename, int positionId,
            int departmentId, int currencyId, int expectedsalary, int cityId, int assettypeId, String employeeId, String othercity, int applytype, 
            String religion, int positionId2, int stateId, String pincode,  int age, String airport1, String airport2, String profile, 
            String skill1, String skill2) {
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
        this.othercity = othercity;
        this.applytype = applytype;
        this.religion = religion;
        this.positionId2 = positionId2;
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
    public CandidateInfo(int candidateId, String bankName, String savingAccountNo, String branch, String IFSCCode, int accountTypeId, String fileName1, int status,
            int primarybankId, String accountHolder) {
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

    //ManageBankdetail
    public CandidateInfo(int bankdetid, String bankname, String savingAccountNo, String branch,
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

    //by candidateid
    public CandidateInfo(String firstname, String middlename, String lastname, String dob, String placeofbirth, String email, String code1, String code2, String contactno1,
            String contactno2, String code3, String contactno3, String gender, int countryid, int nationalityid, String address1line1, String address1line2, String address2line1,
            String address2line2, String address2line3, String nextofkin, int relationid, String ecode1, String econtactno1, String ecode2, String econtactno2,
            int maritialstatusid, String photofilename, String address1line3, int departmentId, int positionId, int currencyId, int expectedsalary, int status, String cityName,
            int cityId, int filecount, int assettypeId, String employeeId, int applytype, String religion, int positionId2, int stateId, String pincode,
            int age, String airport1, String airport2, String profile, String skill1, String skill2) {

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
        this.applytype = applytype;
        this.religion = religion;
        this.positionId2 = positionId2;
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
    public CandidateInfo(int candidatelangId, String langname, String proficiencyname, String filename, int status, int pass) {
        this.candlangid = candidatelangId;
        this.languageName = langname;
        this.proficiencyName = proficiencyname;
        this.candlangfilename = filename;
        this.status = status;
        this.passflag = pass;
    }

    public CandidateInfo(int candidateLangId, int languageId, int proficiencyId, int status, String fileName1) {
        this.candlangid = candidateLangId;
        this.languageId = languageId;
        this.proficiencyId = proficiencyId;
        this.status = status;
        this.candlangfilename = fileName1;

    }

    public CandidateInfo(int languageId, int proficiencyId, String filename) {
        this.languageId = languageId;
        this.proficiencyId = proficiencyId;
        this.candlangfilename = filename;
    }

    //candidate health
    public CandidateInfo(int candidateId, String ssmf, String ogukmedicalftw, String ogukexp, String medifitcert, String medifitcertexp, String bloodgroup, int bloodpressure,
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
    public CandidateInfo(String ssmf, String ogukmedicalftw, String ogukexp, String medifitcert, String medifitcertexp, String bloodgroup, int bloodpressure, String hypertension,
            String diabetes, String smoking, String fileName1, int status, String cov192doses, double height, double weight) {
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

    //get by health id
    public CandidateInfo(int candidatehealthId, String ssmf, String ogukmedicalftw, String ogukexp, String medifitcert, String medifitcertexp, String bloodgroup,
            int bloodpressureId, String hypertension, String diabetes, String smoking, int userId, String mfFilename, int status, String covid192doses, 
            String bloodpressure, int pass, double height, double weight) {
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
        this.passflag = pass;
        this.height = height;
        this.weight = weight;
    }

    // for vaccination
    public CandidateInfo(int vaccinationNameId, int vaccinationTypeId, int placeofapplication, String dateofapplication, String dateofexpiry, int status, String filename,
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
    public CandidateInfo(int candidatevaccId, String vaccinename, String vaccinetypename, String placeofapplication, String filename, int status,
            String dateofapplication, String dateofexpiry, int pass) {
        this.candidatevaccineId = candidatevaccId;
        this.vaccinationName = vaccinename;
        this.vacinationType = vaccinetypename;
        this.placeofapplication = placeofapplication;
        this.vaccinecertfile = filename;
        this.status = status;
        this.dateofapplication = dateofapplication;
        this.dateofexpiry = dateofexpiry;
        this.passflag = pass;

    }

    public CandidateInfo(int candidatevaccineId, int vaccinationNameId, int vaccinationTypeId, int placeofapplicationId, String dateofapplication, String dateofexpiry, int status,
            String fileName1) {
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
    public CandidateInfo(int candidatebankId, String bankName, String bankAccounttype, String branch, String ifsccode, String filename, int status,
            int primaryBankId, int pass) {
        this.bankdetid = candidatebankId;
        this.bankName = bankName;
        this.accountTypename = bankAccounttype;
        this.branch = branch;
        this.IFSCCode = ifsccode;
        this.bkFilename = filename;
        this.status = status;
        this.primarybankId = primaryBankId;
        this.passflag = pass;
    }

    //govdoc
    public CandidateInfo(int govdocumentId, String documentname, String documentno, String placeofissue, String issuedby, int status,
            String dateofissue, String dateofexpiry, String countryname, int pass, int filecount) {
        this.govdocumentId = govdocumentId;
        this.documentname = documentname;
        this.documentno = documentno;
        this.placeofissue = placeofissue;
        this.issuedby = issuedby;
        this.status = status;
        this.dateofissue = dateofissue;
        this.dateofexpiry = dateofexpiry;
        this.countryName = countryname;
        this.passflag = pass;
        this.filecount = filecount;
    }

    //findby id
    public CandidateInfo(int documenttypeId, String documentno, int placeofissueId, int issuedbyId, String dateofissue, String dateofexpiry, int status, String cityName) {
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
    public CandidateInfo(int govdocumentId, int documentTypeId, String documentno, int DocumentIssuedbyId, int cityId, String dateofissue, String dateofexpiry, int status) {
        this.govdocumentId = govdocumentId;
        this.documenttypeId = documentTypeId;
        this.documentno = documentno;
        this.placeofissueId = cityId;
        this.dateofissue = dateofissue;
        this.dateofexpiry = dateofexpiry;
        this.issuedbyId = DocumentIssuedbyId;
        this.status = status;
    }

    public CandidateInfo(int positionId, String companyshipname, String region, double tonnage, int countryId, String signIn, String signoff, int status, int enginetypeId) {
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

    //for Training and certification
    public CandidateInfo(int coursetypeId, int coursenameId, String eduinstitute, int locationofInstituteId, String fieldofstudy,
            String coursestart, String passingyear, String dateofissue, String certificateno, String expirydate, String courseverification,
            int approvedbyId, String filename, int status, String cityName, String courseName) {
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
        this.coursename = courseName;

    }

    public CandidateInfo(int trainingandcertId, int coursetypeId, int courseNameId, String educationInstitute, int locationofInstitute, String fieldofstudy, String coursestarted,
            String passingyear, String dateofissue, String certificationno, String dateofexpiry, String courseverification, int approvedbyId, int status, String fileName1) {
        this.trainingandcertId = trainingandcertId;
        this.coursetypeId = coursetypeId;
        this.coursenameId = courseNameId;
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
    public CandidateInfo(int candidateId,  String coursename, String coursetype, String educinst, String approvedby, String dateofissue,
            String expirydate, int status, int trainingandcertId, String filename, int pass, int coursenameId, String url) 
    {
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
        this.passflag = pass;
        this.coursenameId = coursenameId;
        this.url = url;
    }   

    // for education detail
    public CandidateInfo(int kindId, int degreeId, String backgroundofstudy, String educinst, int locationofinstit, String fieldofstudy,
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
    public CandidateInfo(String kindname, String degree, String educinst, String locationofinstit, String filedofstudy, String startdate, String enddate,
            int status, String filename, int educationdetailId, int pass) {
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
        this.passflag = pass;

    }

    public CandidateInfo(int educationdetailId, int kindId, int degreeId, String backgroundofstudy, String educationInstitute,
            int locationofInstituteId, String fieldofstudy, String coursestarted, String passingdate, int highestqualification, int status, String fileName1) {
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

    public CandidateInfo(String companyName, int companyindustryId, int assettypeId, String assetName, int positionId, int departmentId, int countryId, int cityId,
            String clientpartyname, int waterdepthId, int lastdrawnsalarycurrencyId, int currentworkingstatus, int skillId, int gradeId, String ownerpool, int crewtypeId,
            String ocsemployed, String legalrights, int dayratecurrencyId, int dayrate, int monthlysalarycurrencyId, int monthlysalary, String workstartdate, String workenddate,
            String lastdrawnsalary, String filenamework, String filenameexp, String cityname, int status, String role) {
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
        this.role = role;
    }

    public CandidateInfo(String position, String department, String companyname, String assetname, String startdate, String enddate, int status, String workfilename,
            String experiencefilename, int experiencedetailId, int pass) {
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
        this.passflag = pass;
    }

    public CandidateInfo(int experiencedetailId, String companyName, int comnpanyindustryId, int assettypeId, String assetname, int positionId, int departmentId, int countryId,
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

    public CandidateInfo(int fileId, String filename, String date, String name, String localFile) {
        this.fileid = fileId;
        this.filename = filename;
        this.date = date;
        this.name = name;
        this.localFile = localFile;
    }
    
    // For excel 
    public CandidateInfo(int candidateId, String name,
            String countryName, int status, String date, String cityName, String positionName,
            String contactno1, String econactno, String email, String dob, String placeofbirth, String gender, String maritalstatus, String nationality, String prefferddept,
            int expectedsalary, String currency) {
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
    
    //feedback
    public CandidateInfo(String coursetype, String coursename, String eduinstitute, int locationofInstituteId, String fieldofstudy,
            String coursestart, String passingyear, String dateofissue, String certificateno, String expirydate, String courseverification,
            String approvedby, String filename, int status, String cityName, String url) {
        this.coursetype = coursetype;
        this.coursename = coursename;
        this.eduinstitute = eduinstitute;
        this.locationofInstituteId = locationofInstituteId;
        this.fieldofstudy = fieldofstudy;
        this.coursestart = coursestart;
        this.passingyear = passingyear;
        this.dateofissue = dateofissue;
        this.certificateno = certificateno;
        this.expirydate = expirydate;
        this.courseverification = courseverification;
        this.approvedby = approvedby;
        this.certifilename = filename;
        this.status = status;
        this.city = cityName;
        this.url = url;
    }
    
    public CandidateInfo(String companyName, String companyindustry, String assettype, String assetName, String position, String department, String country, int cityId,
            String clientpartyname, String waterdepth, String lastdrawnsalarycurrency, int currentworkingstatus, String skill, String grade, String ownerpool, String crewtype,
            String ocsemployed, String legalrights, String dayratecurrency, int dayrate, String monthlysalarycurrency, int monthlysalary, String workstartdate, String workenddate,
            String lastdrawnsalary, String filenamework, String filenameexp, String cityname, int status, String waterdepthunit) {
        this.waterdepthunit = waterdepthunit;
        this.companyname = companyName;
        this.companyindustry = companyindustry;
        this.assettype = assettype;
        this.assetName = assetName;
        this.position = position;
        this.department = department;
        this.country = country;
        this.cityId = cityId;
        this.clientpartyname = clientpartyname;
        this.waterdepth = waterdepth;
        this.lastdrawnsalarycurrency = lastdrawnsalarycurrency;
        this.currentworkingstatus = currentworkingstatus;
        this.skill = skill;
        this.grade = grade;
        this.ownerpool = ownerpool;
        this.crewtype = crewtype;
        this.ocsemployed = ocsemployed;
        this.legalrights = legalrights;
        this.dayratecurrency = dayratecurrency;
        this.dayrate = dayrate;
        this.monthlysalarycurrency = monthlysalarycurrency;
        this.monthlysalary = monthlysalary;
        this.workstartdate = workstartdate;
        this.workenddate = workenddate;
        this.lastdrawnsalary = lastdrawnsalary;
        this.workfilename = filenamework;
        this.experiencefilename = filenameexp;
        this.city = cityname;
        this.status = status;
    }
    
    public CandidateInfo(int nomineedetailId, String nomineeName, String nomineeContactno, 
            String nomineeRelation, int status, String code1Id, int pass) {
        this.nomineedetailId = nomineedetailId;
        this.nomineeName = nomineeName;
        this.nomineeContactno = nomineeContactno;
        this.nomineeRelation = nomineeRelation;
        this.status = status;
        this.code1Id = code1Id;
        this.passflag = pass;
    }
    
    //for add-modify
    public CandidateInfo(int nomineedetailId, String nomineename, String nomineecontactno, int relationId, 
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
    
    //Save nominee
    public CandidateInfo(int candidateId, int nomineedetailId, String nomineeName, String nomineeContactno, int relationId, 
            int status, String code1Id, String address, int age, double percentage) {
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

    public int getCandidateId() {
        return candidateId;
    }

    public int getDdlValue() {
        return ddlValue;
    }

    public int getStatus() {
        return status;
    }

    public int getLanguageId() {
        return languageId;
    }

    public int getProficiencyId() {
        return proficiencyId;
    }

    public int getCandlangid() {
        return candlangid;
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

    public int getMaritalstatusId() {
        return maritalstatusId;
    }

    public int getRelationId() {
        return relationId;
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

    public String getLanguageName() {
        return languageName;
    }

    public String getProficiencyName() {
        return proficiencyName;
    }

    public String getCandlangfilename() {
        return candlangfilename;
    }

    public String getDate() {
        return date;
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

    public String getNextofkin() {
        return nextofkin;
    }

    public String getPhotofilename() {
        return photofilename;
    }

    public String getAddress1line3() {
        return address1line3;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public int getBankdetid() {
        return bankdetid;
    }

    public int getPrimarybankId() {
        return primarybankId;
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

    public String getAccountTypename() {
        return accountTypename;
    }

    public String getBkFilename() {
        return bkFilename;
    }

    public String getAssettype() {
        return assettype;
    }

    public int getBloodpressureId() {
        return bloodpressureId;
    }

    public int getUserId() {
        return userId;
    }

    public int getCandidatehealthId() {
        return candidatehealthId;
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


    public int getEnginetypeId() {
        return enginetypeId;
    }

    public double getTonnage() {
        return tonnage;
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

    public int getDocumenttypeId() {
        return documenttypeId;
    }

    public int getPlaceofissueId() {
        return placeofissueId;
    }

    public int getIssuedbyId() {
        return issuedbyId;
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

    public int getFilecount() {
        return filecount;
    }

    public int getCoursenameId() {
        return coursenameId;
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

    public int getKindId() {
        return kindId;
    }

    public int getDegreeId() {
        return degreeId;
    }

    public int getHighestqualification() {
        return highestqualification;
    }

    public String getKindname() {
        return kindname;
    }

    public String getDegree() {
        return degree;
    }

    public String getEducationalfilename() {
        return educationalfilename;
    }

    public String getBackgroundofstudy() {
        return backgroundofstudy;
    }

    public int getExperiencedetailId() {
        return experiencedetailId;
    }

    public int getCompanyindustryId() {
        return companyindustryId;
    }

    public int getAssettypeId() {
        return assettypeId;
    }

    public int getWaterdepthId() {
        return waterdepthId;
    }

    public int getLastdrawnsalarycurrencyId() {
        return lastdrawnsalarycurrencyId;
    }

    public int getSkillsId() {
        return skillsId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public int getCrewtypeid() {
        return crewtypeid;
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

    public int getPassflag() {
        return passflag;
    }

    public String getCompanyname() {
        return companyname;
    }

    public String getDepartment() {
        return department;
    }

    public String getClientpartyname() {
        return clientpartyname;
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

    public String getOwnerpool() {
        return ownerpool;
    }

    public String getOcsemployed() {
        return ocsemployed;
    }

    public String getLegalrights() {
        return legalrights;
    }

    public String getExperiencefilename() {
        return experiencefilename;
    }

    public String getWorkfilename() {
        return workfilename;
    }

    public int getFileid() {
        return fileid;
    }

    public int getExpectedsalary() {
        return expectedsalary;
    }

    public String getFilename() {
        return filename;
    }

    public String getBloodpressure() {
        return bloodpressure;
    }

    public String getCity() {
        return city;
    }

    public String getOthercity() {
        return othercity;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public String getCompanyindustry() {
        return companyindustry;
    }

    public String getCountry() {
        return country;
    }

    public String getLastdrawnsalarycurrency() {
        return lastdrawnsalarycurrency;
    }

    public String getSkill() {
        return skill;
    }

    public String getGrade() {
        return grade;
    }

    public String getCrewtype() {
        return crewtype;
    }

    public String getDayratecurrency() {
        return dayratecurrency;
    }

    public String getMonthlysalarycurrency() {
        return monthlysalarycurrency;
    }

    public String getWaterdepth() {
        return waterdepth;
    }

    public String getWaterdepthunit() {
        return waterdepthunit;
    }

    public int getApplytype() {
        return applytype;
    }

    public String getApplytypevalue() {
        return applytypevalue;
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
     * @return the religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * @return the positionId2
     */
    public int getPositionId2() {
        return positionId2;
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
     * @return the nomineedetailId
     */
    public int getNomineedetailId() {
        return nomineedetailId;
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
     * @return the address
     */
    public String getAddress() {
        return address;
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

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }
    
}
