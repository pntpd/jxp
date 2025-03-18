package com.web.jxp.crewdb;

public class CrewdbInfo {

    private int crewdbId;
    private String name;
    private String dob;
    private String place;
    private String email;
    private String contact1_code;
    private String contact1;
    private String contact2_code;
    private String contact2;
    private String contact3_code;
    private String contact3;
    private String gender;
    private String countryName;
    private String nationality;
    private String address1line1;
    private String address1line2;
    private String address1line3;
    private String address2line1;
    private String address2line2;
    private String address2line3;
    private String nextofkin;
    private String relation;
    private String econtact1_code;
    private String econtact1;
    private String econtact2_code;
    private String econtact2;
    private String maritialstatus;
    private String resumefilename;
    private String photo;
    private int ddlValue;
    private String ddlLabel;
    private String position;
    private String clientName;
    private String assetName;
    private int status;
    private String department;
    private String ownerPool;
    private int passId;

    private String languageName;
    private String proficiencyName;
    private String candlangfilename;
    private int filecount;
    private int childId;
    private int vstatus;

    //for bankdetails
    private String bankName;
    private String savingAccountNo;
    private String branch;
    private String IFSCCode;
    private String NREAccount;
    private String bkFilename;
    private int primarybankId;

    //for government documents
    private String documentName;
    private String documentNo;
    private String dateOfissue;
    private String dateOfexpiry;
    private String placeOfissue;
    private String docIssuedby;
    private String docFilename;

    //for health Declaration
    private String ssmf;
    private String ogukmedicalftw;
    private String ogukexp;
    private String medifitcert;
    private String medifitcertexp;
    private String bloodgroup;
    private String bloodpressure;
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

    // for work experience
    private String company;
    private String client;
    private String rigname;
    private String typeofrigvel;
    private String waterdepth;
    private String jobtitle;
    private String departmentfunction;
    private String countryname;
    private String city;
    private String workstartdate;
    private String workenddate;
    private String lastdrawnsalary;
    private String icurrworkinghere;
    private String verificationcheck;
    private String workexpfilename;

    //for working details
    private double totalworkexp;
    private String skills;
    private String compindustry;
    private String functionalarea;
    private String preferreddept;
    private int currentsalary;
    private int expectedsalary;
    private String rank;
    private String employedocs;
    private String legalrights;
    private String workfilename;
    private int dayrate;
    private int dayratesalary;
    private int monthlysalary;
    private String monthlysalarycurrency;
    private String currency;
    private String assettype;
    private String currentsalarystr;

    //for educdetail
    private String degree;
    private String backgroundofstudy;
    private String eduinstitute;
    private String locationofinstitute;
    private String fieldofstudy;
    private String coursestart;
    private String passingyear;
    private String highestqual;
    private String educfilename;

    //for training certification 
    private String coursetype;
    private String coursename;
    private String educinst;
    private String locationofinstit;
    private String dateofissue;
    private String certificateno;
    private String expirydate;
    private String courseverification;
    private String approvedby;
    private String certifilename;

    //for hitch
    //      private int position;
    private String companyshipname;
    private String region;
//        private String countryname;
    private String enginetype;
    private double tonnage;
    private String signin;
    private String signoff;

    //for vaccination
    private String vaccinename;
    private String vaccinetype;
    private String placeofapplication;
    private String dateofapplication;
    private String dateofexpiry;
    private String vacfilename;
    private String firstname;

    private int cflag3;
    private int cflag8;
    private int cflag9;
    private int cflag10;
    private int cflag11;

    // for cassessment history
    private int cassessmenthId;
    private int cassessmentId;
    private String coordinatorName;
    private String assessorName;
    private String assessment;
    private String statusValue;
    private int marks;
    private String date;
    private String time;
    private int alertId;
    private String applytypevalue;
    private String position2;

    //Nominee
    private String nomineeName;
    private String nomineeRelation;
    private String nomineeContactno;
    private int nomineedetailId;
    private String code1Id;
    private String stateName;
    private String pincode;
    private int age;
    private String airport1;
    private String airport2;

    //For Nominee
    private String ccval;

    public int getCflag8() {
        return cflag8;
    }

    public int getCflag9() {
        return cflag9;
    }

    public int getCflag10() {
        return cflag10;
    }

    public int getCassessmenthId() {
        return cassessmenthId;
    }

    public void setCassessmenthId(int cassessmenthId) {
        this.cassessmenthId = cassessmenthId;
    }

    public int getCassessmentId() {
        return cassessmentId;
    }

    public void setCassessmentId(int cassessmentId) {
        this.cassessmentId = cassessmentId;
    }

    public String getCoordinatorName() {
        return coordinatorName;
    }

    public void setCoordinatorName(String coordinatorName) {
        this.coordinatorName = coordinatorName;
    }

    public String getAssessorName() {
        return assessorName;
    }

    public void setAssessorName(String assessorName) {
        this.assessorName = assessorName;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getFilecount() {
        return filecount;
    }

    public void setFilecount(int filecount) {
        this.filecount = filecount;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAddress1line3() {
        return address1line3;
    }

    public void setAddress1line3(String address1line3) {
        this.address1line3 = address1line3;
    }

    public int getPrimarybankId() {
        return primarybankId;
    }

    public void setPrimarybankId(int primarybankId) {
        this.primarybankId = primarybankId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAssettype() {
        return assettype;
    }

    public void setAssettype(String assettype) {
        this.assettype = assettype;
    }

    public String getOwnerPool() {
        return ownerPool;
    }

    public void setOwnerPool(String ownerPool) {
        this.ownerPool = ownerPool;
    }

    public int getDayrate() {
        return dayrate;
    }

    public void setDayrate(int dayrate) {
        this.dayrate = dayrate;
    }

    public int getDayratesalary() {
        return dayratesalary;
    }

    public void setDayratesalary(int dayratesalary) {
        this.dayratesalary = dayratesalary;
    }

    public int getMonthlysalary() {
        return monthlysalary;
    }

    public void setMonthlysalary(int monthlysalary) {
        this.monthlysalary = monthlysalary;
    }

    public String getMonthlysalarycurrency() {
        return monthlysalarycurrency;
    }

    public void setMonthlysalarycurrency(String monthlysalarycurrency) {
        this.monthlysalarycurrency = monthlysalarycurrency;
    }

    public String getCandlangfilename() {
        return candlangfilename;
    }

    public void setCandlangfilename(String candlangfilename) {
        this.candlangfilename = candlangfilename;
    }

    public String getSsmf() {
        return ssmf;
    }

    public void setSsmf(String ssmf) {
        this.ssmf = ssmf;
    }

    public String getMedifitcert() {
        return medifitcert;
    }

    public void setMedifitcert(String medifitcert) {
        this.medifitcert = medifitcert;
    }

    public String getHypertension() {
        return hypertension;
    }

    public void setHypertension(String hypertension) {
        this.hypertension = hypertension;
    }

    public String getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(String diabetes) {
        this.diabetes = diabetes;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public int getCurrentsalary() {
        return currentsalary;
    }

    public void setCurrentsalary(int currentsalary) {
        this.currentsalary = currentsalary;
    }

    public String getVaccinename() {
        return vaccinename;
    }

    public void setVaccinename(String vaccinename) {
        this.vaccinename = vaccinename;
    }

    public String getVaccinetype() {
        return vaccinetype;
    }

    public void setVaccinetype(String vaccinetype) {
        this.vaccinetype = vaccinetype;
    }

    public String getPlaceofapplication() {
        return placeofapplication;
    }

    public void setPlaceofapplication(String placeofapplication) {
        this.placeofapplication = placeofapplication;
    }

    public String getDateofapplication() {
        return dateofapplication;
    }

    public void setDateofapplication(String dateofapplication) {
        this.dateofapplication = dateofapplication;
    }

    public String getDateofexpiry() {
        return dateofexpiry;
    }

    public void setDateofexpiry(String dateofexpiry) {
        this.dateofexpiry = dateofexpiry;
    }

    public String getVacfilename() {
        return vacfilename;
    }

    public void setVacfilename(String vacfilename) {
        this.vacfilename = vacfilename;
    }

    public String getWorkfilename() {
        return workfilename;
    }

    public void setWorkfilename(String workfilename) {
        this.workfilename = workfilename;
    }

    public String getWorkexpfilename() {
        return workexpfilename;
    }

    public void setWorkexpfilename(String workexpfilename) {
        this.workexpfilename = workexpfilename;
    }

    public String getCompanyshipname() {
        return companyshipname;
    }

    public void setCompanyshipname(String companyshipname) {
        this.companyshipname = companyshipname;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEnginetype() {
        return enginetype;
    }

    public void setEnginetype(String enginetype) {
        this.enginetype = enginetype;
    }

    public double getTonnage() {
        return tonnage;
    }

    public void setTonnage(double tonnage) {
        this.tonnage = tonnage;
    }

    public String getSignin() {
        return signin;
    }

    public void setSignin(String signin) {
        this.signin = signin;
    }

    public String getSignoff() {
        return signoff;
    }

    public void setSignoff(String signoff) {
        this.signoff = signoff;
    }

    public String getCoursetype() {
        return coursetype;
    }

    public void setCoursetype(String coursetype) {
        this.coursetype = coursetype;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getEducinst() {
        return educinst;
    }

    public void setEducinst(String educinst) {
        this.educinst = educinst;
    }

    public String getLocationofinstit() {
        return locationofinstit;
    }

    public void setLocationofinstit(String locationofinstit) {
        this.locationofinstit = locationofinstit;
    }

    public String getDateofissue() {
        return dateofissue;
    }

    public void setDateofissue(String dateofissue) {
        this.dateofissue = dateofissue;
    }

    public String getCertificateno() {
        return certificateno;
    }

    public void setCertificateno(String certificateno) {
        this.certificateno = certificateno;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getCourseverification() {
        return courseverification;
    }

    public void setCourseverification(String courseverification) {
        this.courseverification = courseverification;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public String getCertifilename() {
        return certifilename;
    }

    public void setCertifilename(String certifilename) {
        this.certifilename = certifilename;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getBackgroundofstudy() {
        return backgroundofstudy;
    }

    public void setBackgroundofstudy(String backgroundofstudy) {
        this.backgroundofstudy = backgroundofstudy;
    }

    public String getEduinstitute() {
        return eduinstitute;
    }

    public void setEduinstitute(String eduinstitute) {
        this.eduinstitute = eduinstitute;
    }

    public String getLocationofinstitute() {
        return locationofinstitute;
    }

    public void setLocationofinstitute(String locationofinstitute) {
        this.locationofinstitute = locationofinstitute;
    }

    public String getFieldofstudy() {
        return fieldofstudy;
    }

    public void setFieldofstudy(String fieldofstudy) {
        this.fieldofstudy = fieldofstudy;
    }

    public String getCoursestart() {
        return coursestart;
    }

    public void setCoursestart(String coursestart) {
        this.coursestart = coursestart;
    }

    public String getPassingyear() {
        return passingyear;
    }

    public void setPassingyear(String passingyear) {
        this.passingyear = passingyear;
    }

    public String getHighestqual() {
        return highestqual;
    }

    public void setHighestqual(String highestqual) {
        this.highestqual = highestqual;
    }

    public String getEducfilename() {
        return educfilename;
    }

    public void setEducfilename(String educfilename) {
        this.educfilename = educfilename;
    }

    public double getTotalworkexp() {
        return totalworkexp;
    }

    public void setTotalworkexp(double totalworkexp) {
        this.totalworkexp = totalworkexp;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getCompindustry() {
        return compindustry;
    }

    public void setCompindustry(String compindustry) {
        this.compindustry = compindustry;
    }

    public String getFunctionalarea() {
        return functionalarea;
    }

    public void setFunctionalarea(String functionalarea) {
        this.functionalarea = functionalarea;
    }

    public String getPreferreddept() {
        return preferreddept;
    }

    public void setPreferreddept(String preferreddept) {
        this.preferreddept = preferreddept;
    }

    public int getExpectedsalary() {
        return expectedsalary;
    }

    public void setExpectedsalary(int expectedsalary) {
        this.expectedsalary = expectedsalary;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getEmployedocs() {
        return employedocs;
    }

    public void setEmployedocs(String employedocs) {
        this.employedocs = employedocs;
    }

    public String getLegalrights() {
        return legalrights;
    }

    public void setLegalrights(String legalrights) {
        this.legalrights = legalrights;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getRigname() {
        return rigname;
    }

    public void setRigname(String rigname) {
        this.rigname = rigname;
    }

    public String getTypeofrigvel() {
        return typeofrigvel;
    }

    public void setTypeofrigvel(String typeofrigvel) {
        this.typeofrigvel = typeofrigvel;
    }

    public String getWaterdepth() {
        return waterdepth;
    }

    public void setWaterdepth(String waterdepth) {
        this.waterdepth = waterdepth;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getDepartmentfunction() {
        return departmentfunction;
    }

    public void setDepartmentfunction(String departmentfunction) {
        this.departmentfunction = departmentfunction;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWorkstartdate() {
        return workstartdate;
    }

    public void setWorkstartdate(String workstartdate) {
        this.workstartdate = workstartdate;
    }

    public String getWorkenddate() {
        return workenddate;
    }

    public void setWorkenddate(String workenddate) {
        this.workenddate = workenddate;
    }

    public String getLastdrawnsalary() {
        return lastdrawnsalary;
    }

    public void setLastdrawnsalary(String lastdrawnsalary) {
        this.lastdrawnsalary = lastdrawnsalary;
    }

    public String getIcurrworkinghere() {
        return icurrworkinghere;
    }

    public void setIcurrworkinghere(String icurrworkinghere) {
        this.icurrworkinghere = icurrworkinghere;
    }

    public String getVerificationcheck() {
        return verificationcheck;
    }

    public void setVerificationcheck(String verificationcheck) {
        this.verificationcheck = verificationcheck;
    }

    public String getOgukmedicalftw() {
        return ogukmedicalftw;
    }

    public void setOgukmedicalftw(String ogukmedicalftw) {
        this.ogukmedicalftw = ogukmedicalftw;
    }

    public String getOgukexp() {
        return ogukexp;
    }

    public void setOgukexp(String ogukexp) {
        this.ogukexp = ogukexp;
    }

    public String getMedifitcertexp() {
        return medifitcertexp;
    }

    public void setMedifitcertexp(String medifitcertexp) {
        this.medifitcertexp = medifitcertexp;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getBloodpressure() {
        return bloodpressure;
    }

    public void setBloodpressure(String bloodpressure) {
        this.bloodpressure = bloodpressure;
    }

    public String getCov192doses() {
        return cov192doses;
    }

    public void setCov192doses(String cov192doses) {
        this.cov192doses = cov192doses;
    }

    public String getCovid19vaccinestatus() {
        return covid19vaccinestatus;
    }

    public void setCovid19vaccinestatus(String covid19vaccinestatus) {
        this.covid19vaccinestatus = covid19vaccinestatus;
    }

    public String getVaccine1dose() {
        return vaccine1dose;
    }

    public void setVaccine1dose(String vaccine1dose) {
        this.vaccine1dose = vaccine1dose;
    }

    public String getVaccine1date() {
        return vaccine1date;
    }

    public void setVaccine1date(String vaccine1date) {
        this.vaccine1date = vaccine1date;
    }

    public String getVaccine2dose() {
        return vaccine2dose;
    }

    public void setVaccine2dose(String vaccine2dose) {
        this.vaccine2dose = vaccine2dose;
    }

    public String getVaccine2date() {
        return vaccine2date;
    }

    public void setVaccine2date(String vaccine2date) {
        this.vaccine2date = vaccine2date;
    }

    public String getVaccine3dose() {
        return vaccine3dose;
    }

    public void setVaccine3dose(String vaccine3dose) {
        this.vaccine3dose = vaccine3dose;
    }

    public String getVaccine3date() {
        return vaccine3date;
    }

    public void setVaccine3date(String vaccine3date) {
        this.vaccine3date = vaccine3date;
    }

    public String getMfFilename() {
        return mfFilename;
    }

    public void setMfFilename(String mfFilename) {
        this.mfFilename = mfFilename;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getDateOfissue() {
        return dateOfissue;
    }

    public void setDateOfissue(String dateOfissue) {
        this.dateOfissue = dateOfissue;
    }

    public String getDateOfexpiry() {
        return dateOfexpiry;
    }

    public void setDateOfexpiry(String dateOfexpiry) {
        this.dateOfexpiry = dateOfexpiry;
    }

    public String getPlaceOfissue() {
        return placeOfissue;
    }

    public void setPlaceOfissue(String placeOfissue) {
        this.placeOfissue = placeOfissue;
    }

    public String getDocIssuedby() {
        return docIssuedby;
    }

    public void setDocIssuedby(String docIssuedby) {
        this.docIssuedby = docIssuedby;
    }

    public String getDocFilename() {
        return docFilename;
    }

    public void setDocFilename(String docFilename) {
        this.docFilename = docFilename;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSavingAccountNo() {
        return savingAccountNo;
    }

    public void setSavingAccountNo(String savingAccountNo) {
        this.savingAccountNo = savingAccountNo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getIFSCCode() {
        return IFSCCode;
    }

    public void setIFSCCode(String IFSCCode) {
        this.IFSCCode = IFSCCode;
    }

    public String getNREAccount() {
        return NREAccount;
    }

    public void setNREAccount(String NREAccount) {
        this.NREAccount = NREAccount;
    }

    public String getBkFilename() {
        return bkFilename;
    }

    public void setBkFilename(String bkFilename) {
        this.bkFilename = bkFilename;
    }

    public CrewdbInfo(int passId) {
        this.passId = passId;
    }

    // for workexplist
    public CrewdbInfo(int childId, String jobtitle, String department, String companyname, String assettype, String rigname,
            String workstartdate, String workenddate, String workexpfilename, int vstatus) {
        this.childId = childId;
        this.jobtitle = jobtitle;
        this.departmentfunction = department;
        this.company = companyname;
        this.assettype = assettype;
        this.assetName = rigname;
        this.workstartdate = workstartdate;
        this.workenddate = workenddate;
        this.workexpfilename = workexpfilename;
        this.vstatus = vstatus;
    }

    public CrewdbInfo(String companyname, String companyindustry, String position, String department, String city, String workstartdate, String skills,
            String preferreddept, int lastdrawnsalary, int expectedsalary, String rank, String ownerpool, String ocsemployed,
            String legalrights, String clientpartyname, String waterdepth, String assettype, String assetname, String currency,
            int dayrate, String monthlysalarycurrency, int monthlysalary, int vstatus) {
        this.company = companyname;
        this.compindustry = companyindustry;
        this.position = position;
        this.departmentfunction = department;
        this.city = city;
        this.workstartdate = workstartdate;
        this.skills = skills;
        this.preferreddept = preferreddept;
        this.currentsalary = lastdrawnsalary;
        this.expectedsalary = expectedsalary;
        this.rank = rank;
        this.ownerPool = ownerpool;
        this.employedocs = ocsemployed;
        this.legalrights = legalrights;
        this.client = clientpartyname;
        this.waterdepth = waterdepth;
        this.assettype = assettype;
        this.assetName = assetname;
        this.monthlysalarycurrency = currency;
        this.dayrate = dayrate;
        this.monthlysalarycurrency = monthlysalarycurrency;
        this.monthlysalary = monthlysalary;
        this.vstatus = vstatus;
    }

    // for health declaration 
    public CrewdbInfo(String ssmf, String ogukmedicalftw, String ogukexp, String medifitcert, String medifitcertexp, String bloodgroup, String bloodpressure,
            String hypertension, String diabetes, String smoking, String covid192doses, int vstatus, double height, double weight) {
        this.ssmf = ssmf;
        this.ogukmedicalftw = ogukmedicalftw;
        this.ogukexp = ogukexp;
        this.medifitcert = medifitcert;
        this.medifitcertexp = medifitcertexp;
        this.bloodgroup = bloodgroup;
        this.bloodpressure = bloodpressure;
        this.hypertension = hypertension;
        this.diabetes = diabetes;
        this.smoking = smoking;
        this.cov192doses = covid192doses;
        this.vstatus = vstatus;
        this.height = height;
        this.weight = weight;
    }

    //for Hitch
    public CrewdbInfo(int childId, String position, String companyshipname, String region, String countryname, String enginetype, double tonnage,
            String signin, String signoff, int vstatus) {
        this.childId = childId;
        this.position = position;
        this.companyshipname = companyshipname;
        this.region = region;
        this.countryname = countryname;
        this.enginetype = enginetype;
        this.tonnage = tonnage;
        this.signin = signin;
        this.signoff = signoff;
        this.vstatus = vstatus;

    }

    //for training and certification details
    public CrewdbInfo(String coursetype, String coursename, String educinst, String locationofinstit, String fieldofstudy, String coursestart, String passingyear,
            String dateofissue, String certificateno, String expirydate, String courseverification, String approvedby, String certifilename) {
        this.coursetype = coursetype;
        this.coursename = coursename;
        this.educinst = educinst;
        this.locationofinstit = locationofinstit;
        this.fieldofstudy = fieldofstudy;
        this.coursestart = coursestart;
        this.passingyear = passingyear;
        this.dateOfissue = dateofissue;
        this.certificateno = certificateno;
        this.expirydate = expirydate;
        this.courseverification = courseverification;
        this.approvedby = approvedby;
        this.certifilename = certifilename;

    }

    public CrewdbInfo(int childId, String coursename, String coursetype, String educinst, String approvedby, String dateofissue, String expirydate,
            String certifilename, int vstatus) {
        this.childId = childId;
        this.coursename = coursename;
        this.coursetype = coursetype;
        this.educinst = educinst;
        //this.locationofinstit = locationofinstit;
        this.approvedby = approvedby;
        this.dateofissue = dateofissue;
        this.expirydate = expirydate;
        this.certifilename = certifilename;
        this.vstatus = vstatus;
        //this.locationofinstit = locationofinstit;
    }

    //for education details
    public CrewdbInfo(int childId, String degree, String backgroundofstudy, String eduinstitute, String fieldofstudy, String coursestart, String passingyear,
            String educfilename, int i, int vstatus, String locationofinstitute) {
        this.childId = childId;
        this.degree = degree;
        this.backgroundofstudy = backgroundofstudy;
        this.eduinstitute = eduinstitute;
        this.fieldofstudy = fieldofstudy;
        this.coursestart = coursestart;
        this.passingyear = passingyear;
        this.educfilename = educfilename;
        this.vstatus = vstatus;
        this.locationofinstitute = locationofinstitute;

    }

    // for working detail
    public CrewdbInfo(String workstartdate, double totalworkexp, String skills, String compindustry, String functionalarea,
            String position, String preferreddept, int currentsalary, int expectedsalary, String rank, String employedocs, String legalrights, String workfilename) {
        this.workstartdate = workstartdate;
        this.totalworkexp = totalworkexp;
        this.skills = skills;
        this.compindustry = compindustry;
        this.functionalarea = functionalarea;
        this.position = position;
        this.preferreddept = preferreddept;
        this.currentsalary = currentsalary;
        this.expectedsalary = expectedsalary;
        this.rank = rank;
        this.employedocs = employedocs;
        this.legalrights = legalrights;
        this.workfilename = workfilename;
    }

    // for government document
    public CrewdbInfo(int childId, String documentName, String documentNo, String dateOfissue, String dateOfexpiry, String placeOfissue, String docIssuedby,
            String docFilename, int i, int j, int vstatus, String countryname) {
        this.childId = childId;
        this.documentName = documentName;
        this.documentNo = documentNo;
        this.dateOfissue = dateOfissue;
        this.dateOfexpiry = dateOfexpiry;
        this.placeOfissue = placeOfissue;
        this.docIssuedby = docIssuedby;
        this.docFilename = docFilename;
        this.vstatus = vstatus;
        this.countryname = countryname;
    }

    //info for bankdetails
    public CrewdbInfo(int childId, String bankName, String savingAccountNo, String branch, String IFSCCode, int primarybankid, String bkFilename,
            int vstatus) {
        this.childId = childId;
        this.bankName = bankName;
        this.savingAccountNo = savingAccountNo;
        this.branch = branch;
        this.IFSCCode = IFSCCode;
        this.primarybankId = primarybankid;
        this.bkFilename = bkFilename;
        this.vstatus = vstatus;
    }

    //for vaccination
    public CrewdbInfo(int childId, String vaccinename, String vaccinetype, String placeofapplication, String dateofapplication, String dateofexpiry,
            String vacfilename, int i, int vstatus) {
        this.childId = childId;
        this.vaccinename = vaccinename;
        this.vaccinetype = vaccinetype;
        this.placeofapplication = placeofapplication;
        this.dateofapplication = dateofapplication;
        this.dateofexpiry = dateofexpiry;
        this.vacfilename = vacfilename;
        this.vstatus = vstatus;

    }

    //for ddl
    public CrewdbInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    //for index
    public CrewdbInfo(int crewdbId, String name, String position, String clientName,
            String countryName, String assetName, int status, String firstname) {
        this.crewdbId = crewdbId;
        this.name = name;
        this.position = position;
        this.clientName = clientName;
        this.countryName = countryName;
        this.assetName = assetName;
        this.status = status;
        this.firstname = firstname;
    }

    //for view
    public CrewdbInfo(int crewdbId, String name, String dob, String place, String email,
            String contact1_code, String contact1, String contact2_code, String contact2,
            String contact3_code, String contact3, String gender, String countryName, String city,
            String nationality, String address1line1, String address1line2, String address1line3, String address2line1,
            String address2line2, String address2line3, String preferredDept, int expectedsalary, String currency, String nextofkin, String relation,
            String econtact1_code, String econtact1, String econtact2_code, String econtact2,
            String maritialstatus, String resumefilename, String photo, String position, String firstname, int filecount,
            int vstatus, int cflag3, int cflag8, int cflag9, int cflag10, String ccval, String position2, String applytypevalue,
            int cflag11, String stateName, String pincode, int age, String airport1, String airport2) {
        this.crewdbId = crewdbId;
        this.name = name;
        this.dob = dob;
        this.place = place;
        this.email = email;
        this.contact1_code = contact1_code;
        this.contact1 = contact1;
        this.contact2_code = contact2_code;
        this.contact2 = contact2;
        this.contact3_code = contact3_code;
        this.contact3 = contact3;
        this.gender = gender;
        this.countryName = countryName;
        this.city = city;
        this.nationality = nationality;
        this.address1line1 = address1line1;
        this.address1line2 = address1line2;
        this.address1line3 = address1line3;
        this.address2line1 = address2line1;
        this.address2line2 = address2line2;
        this.address2line3 = address2line3;
        this.preferreddept = preferredDept;
        this.expectedsalary = expectedsalary;
        this.currency = currency;
        this.nextofkin = nextofkin;
        this.relation = relation;
        this.econtact1_code = econtact1_code;
        this.econtact1 = econtact1;
        this.econtact2_code = econtact2_code;
        this.econtact2 = econtact2;
        this.maritialstatus = maritialstatus;
        this.resumefilename = resumefilename;
        this.photo = photo;
        this.position = position;
        this.firstname = firstname;
        this.filecount = filecount;
        this.vstatus = vstatus;
        this.cflag3 = cflag3;
        this.cflag8 = cflag8;
        this.cflag9 = cflag9;
        this.cflag10 = cflag10;
        this.ccval = ccval;
        this.position2 = position2;
        this.applytypevalue = applytypevalue;
        this.cflag11 = cflag11;
        this.stateName = stateName;
        this.pincode = pincode;
        this.age = age;
        this.airport1 = airport1;
        this.airport2 = airport2;
    }

    //for language
    public CrewdbInfo(String languageName, String proficiencyName) {
        this.languageName = languageName;
        this.proficiencyName = proficiencyName;
    }

    public CrewdbInfo(int childId, String languageName, String proficiencyName, String candlangfilename, int vstatus) {
        this.childId = childId;
        this.languageName = languageName;
        this.proficiencyName = proficiencyName;
        this.candlangfilename = candlangfilename;
        this.vstatus = vstatus;
    }

    public CrewdbInfo(int cassessmenthId, int cassessmentId, String coordinatorName, String assessorName, String assessment,
            String statusValue, int marks, String date, String time, int status) {
        this.cassessmenthId = cassessmenthId;
        this.cassessmentId = cassessmentId;
        this.coordinatorName = coordinatorName;
        this.assessorName = assessorName;
        this.assessment = assessment;
        this.statusValue = statusValue;
        this.marks = marks;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    //Nomineelist
    public CrewdbInfo(int nomineedetailId, String nomineeName, String nomineeContactno,
            String nomineeRelation, int status, String code1Id, int vstatus) {
        this.nomineedetailId = nomineedetailId;
        this.nomineeName = nomineeName;
        this.nomineeContactno = nomineeContactno;
        this.nomineeRelation = nomineeRelation;
        this.status = status;
        this.code1Id = code1Id;
        this.vstatus = vstatus;
    }

    /**
     * @return the crewdbId
     */
    public int getCrewdbId() {
        return crewdbId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * @return the place
     */
    public String getPlace() {
        return place;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the contact1_code
     */
    public String getContact1_code() {
        return contact1_code;
    }

    /**
     * @return the contact1
     */
    public String getContact1() {
        return contact1;
    }

    /**
     * @return the contact2_code
     */
    public String getContact2_code() {
        return contact2_code;
    }

    /**
     * @return the contact2
     */
    public String getContact2() {
        return contact2;
    }

    /**
     * @return the contact3_code
     */
    public String getContact3_code() {
        return contact3_code;
    }

    /**
     * @return the contact3
     */
    public String getContact3() {
        return contact3;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @return the address1line1
     */
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

    /**
     * @return the econtact1_code
     */
    public String getEcontact1_code() {
        return econtact1_code;
    }

    /**
     * @return the econtact1
     */
    public String getEcontact1() {
        return econtact1;
    }

    /**
     * @return the econtact2_code
     */
    public String getEcontact2_code() {
        return econtact2_code;
    }

    /**
     * @return the econtact2
     */
    public String getEcontact2() {
        return econtact2;
    }

    /**
     * @return the maritialstatus
     */
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

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @return the vstatus
     */
    public int getVstatus() {
        return vstatus;
    }

    /**
     * @return the childId
     */
    public int getChildId() {
        return childId;
    }

    /**
     * @return the cflag3
     */
    public int getCflag3() {
        return cflag3;
    }

    /**
     * @param cflag3 the cflag3 to set
     */
    public void setCflag3(int cflag3) {
        this.cflag3 = cflag3;
    }

    public int getPassId() {
        return passId;
    }

    public String getCurrentsalarystr() {
        return currentsalarystr;
    }

    /**
     * @return the ccval
     */
    public String getCcval() {
        return ccval;
    }

    /**
     * @return the alertId
     */
    public int getAlertId() {
        return alertId;
    }

    /**
     * @return the position2
     */
    public String getPosition2() {
        return position2;
    }

    public String getApplytypevalue() {
        return applytypevalue;
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
     * @return the code1Id
     */
    public String getCode1Id() {
        return code1Id;
    }

    /**
     * @return the cflag11
     */
    public int getCflag11() {
        return cflag11;
    }

    /**
     * @return the stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @return the pincode
     */
    public String getPincode() {
        return pincode;
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

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

}
