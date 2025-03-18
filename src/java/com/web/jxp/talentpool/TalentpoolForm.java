package com.web.jxp.talentpool;

import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class TalentpoolForm extends ActionForm {

    private int candidateId;
    private int ctp;
    private String search;
    private String doCancel;
    private String doModify;
    private String doView;
    private String doAdd;
    private String doSave;

    private String doViewVerificationlist;
    private String doViewAssessmentlist;
    private String doViewShortlistinglist;
    private String doGenerate;
    private String doGenerateOF;
    private int statusIndex;
    private int positionIndex;
    private Collection positions;
    private int type;
    private Collection clients;
    private Collection countries;
    private Collection assets;
    private int clientIdIndex;
    private int countryIdIndex;
    private int assetIdIndex;
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
    private int countryId;
    private int cityId;
    private int nationalityId;
    private int departmentId;
    private Collection departments;
    private int currencyId;
    private Collection currencies;
    private String nextofkin;
    private Collection relations;
    private int relationId;
    private Collection maritalstatuses;
    private int maritalstatusId;
    private String address1line1;
    private String address1line2;
    private String address1line3;
    private String address2line1;
    private String address2line2;
    private String address2line3;
    private FormFile photofile;
    private FormFile resumefile;
    private String photofilehidden;
    private String resumefilehidden;
    private String currentDate;
    private int expectedsalary;
    private String fname;
    private int positionIdhidden;
    private int resumetempId;
    private Collection resumetemplates;
    private Collection formalitytemplates;
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
    private int employementstatus;

    //for bankdetails
    private int bankdetailId;
    private String bankName;
    private String savingAccountNo;
    private String branch;
    private String IFSCCode;
    private int accountTypeId;
    private Collection bankAccountTypes;
    private String bankfilehidden;
    private String doViewBanklist;
    private String doManageBankdetail;
    private String doSaveBankdetail;
    private String doDeleteBankdetail;
    private FormFile bankfile;
    private int primarybankId;

    // for candidate Language
    private int candidateLangId;
    private int languageId;
    private int proficiencyId;
    private String langfilehidden;
    private String doaddlangdetail;
    private String doSavelangdetail;
    private String doModifylangdetail;
    private String doDeletelangdetail;
    private String doViewlangdetail;
    private Collection languages;
    private Collection proficiencies;
    private FormFile langfile;

    //candidate health
    private int candidatehealthId;
    private String ssmf;
    private String ogukmedicalftw;
    private String ogukexp;
    private String medifitcert;
    private String medifitcertexp;
    private String bloodgroup;
    private int bloodpressureid;
    private Collection bloodpressures;
    private String hypertension;
    private String diabetes;
    private String smoking;
    private String cov192doses;
    private String healthfilehidden;
    private String doaddhealthdetail;
    private String doSavehealthdetail;
    private String doModifyhealthdetail;
    private String doViewhealthdetail;
    private FormFile healthfile;
    private double height;
    private double weight;

    //vaccination detail
    private int candidatevaccineId;
    private int vaccinationNameId;
    private int vacinationTypeId;
    private Collection vacinationnames;
    private Collection vaccinationtypes;
    private int placeofapplicationId;
    private Collection cities;
    private FormFile vaccinedetailfile;
    private String vaccinedetailhiddenfile;
    private String dateofapplication;
    private String dateofexpiry;
    private String doaddvaccinationdetail;
    private String doSavevaccinationdetail;
    private String doViewvaccinationlist;
    private String doDeletevaccinationdetail;
    private String cityName;

    private int status;

    //for goverment document
    private int govdocumentId;
    private int documentTypeId;
    private Collection documentTypes;
    private String documentNo;
    private String dateofissue;
    private int documentissuedbyId;
    private Collection documentissuedbys;
    private String domodifygovdocumentdetail;
    private String doSavegovdocumentdetail;
    private String doViewgovdocumentlist;
    private String doDeletegovdocumentdetail;

    //for hitch detail
    private int positionId;
    private String companyshipName;
    private int countryworkedId;
    private int engineTypeId;
    private String region;
    private double tonnage;
    private String signIndate;
    private String signoffdate;
    private Collection engineTypes;

    //for training and certification
    private int trainingandcertId;
    private int coursetypeId;
    private int locationofInstituteId;
    private int approvedbyId;
    private int coursenameId;
    private Collection approvedbys;
    private Collection coursetypes;
    private Collection coursenames;
    private String educationInstitute;
    private String fieldofstudy;
    private String coursestarted;
    private String passingdate;
    private String certificationno;
    private String courseverification;
    private FormFile trainingcertfile;
    private String trainingcerthiddenfile;
    private String doaddtrainingcertdetail;
    private String doSavetrainingcertdetail;
    private String doViewtrainingcertlist;
    private String doDeletetrainingcertdetail;

    //educatinal details
    private int educationdetailId;
    private String kindname;
    private int kindId;
    private String degree;
    private int degreeId;
    private int highestqualification;
    private String backgroundofstudy;
    private FormFile educationfile;
    private Collection degrees;
    private Collection qualificationtypes;
    private String educationhiddenfile;
    private String doaddeducationdetail;
    private String doSaveeducationdetail;
    private String doVieweducationlist;
    private String doDeleteeducationdetail;

    // work experience
    private int experiencedetailId;
    private int companyindustryId;
    private int assettypeId;
    private int waterdepthId;
    private int lastdrawnsalarycurrencyId;
    private int skillsId;
    private int rankid;
    private int crewtypeid;
    private int dayratecurrencyid;
    private int dayrate;
    private int monthlysalarycurrencyId;
    private int monthlysalary;
    private int currentworkingstatus;
    private int gradeid;
    private FormFile experiencefile;
    private FormFile workingfile;
    private Collection companyindustries;
    private Collection assettypes;
    private Collection waterdepths;
    private Collection skills;
    private Collection ranks;
    private Collection crewtypes;
    private Collection grades;
    private String companyname;
    private String assetname;
    private String clientpartyname;
    private String lastdrawnsalary;
    private String workstartdate;
    private String workenddate;
    private String ownerpool;
    private String ocsemployed;
    private String legalrights;
    private String experiencehiddenfile;
    private String workinghiddenfile;
    private String doaddexperiencedetail;
    private String doSaveexperiencedetail;
    private String doViewexperiencelist;
    private String doDeleteexperiencedetail;
    private String ratetype;
    private double rate1;
    private double rate2;
    private double rate3;
    
    private double dayrate1;
    private double dayrate2;
    private double dayrate3;

    //talentpool
    private int positionIndexId;
    private int clientIndex;
    private int assetIndex;
    private int locationIndex;
    private Collection clientassets;
    private Collection locations;
    private Collection ipositions;
    private int verified;
    private int cassessmentId;
    private int pAssessmentId;

    //compliance tab
    private String doViewCompliancelist;
    private String doViewClientselectionlist;
    // onboard
    private String doViewOnboardinglist;
    private String doViewMobilizationlist;
    private String doViewRotationlist;
    private int clientId;
    private int clientassetId;
    private int crewrotationId;
    private String doSummary;

    //Transfer Modal
    private int fromClientId;
    private String transferDate;
    private String transferRemark;
    private String doSaveTransferModal;
    private int activeStatus;
    private int transferType;

    // Terminate Modal
    private String doSaveTerminateModal;
    private String terminateDate;
    private String terminateRemark;
    private String employeeid;
    private String EXTYPE;
    private String doViewNotificationlist;
    private String doViewTransferterminationlist;

    private String reason;
    private FormFile terminatefile;
    private String terminatefilehidden;
    private String joiningDate;
    private String endDate;
    private String joiningDate1;
    private String endDate1;
    private String joiningDate2;
    private String endDate2;

    private int toClientId;
    private int toAssetId;
    private int toPositionId;
    private int toCurrencyId;
    private String torate1;
    private String torate2;
    private String torate3;

    //Onboard modal
    private String doSaveOnboardModal;
    private String onboardDate;
    private String onboardRemark;
    private int clientIdModal;
    private int assetIdModal;
    private int positionIdModal;

    //Wellness Feedback
    private String doViewWellnessfeedbacklist;
    private int surveyId;
    private int applytype;

    // Competency List
    private String doViewCompetencylist;
    private Collection depts;
    private int clientIdcom;
    private int assetIdcom;
    private int positionIdcom;
    private int pdeptId;
    private int positionFilterId;
    private int assettypeIdIndex;
    private int courseIndex;
    private int candidateIdHeader;
    private String doViewHeader;
    private String doMobTravel;
    private int formalityId;
    private int generatedlistsize;
    private String doDeleteHealthFile;
    private int healthfileId;

    //For Nominee
    private String nomineeName;
    private String nomineeRelation;
    private String nomineeContactno;
    private String religion;
    private int nomineedetailId;
    private String doManageNomineedetail;
    private String doSaveNomineedetail;
    private String doDeleteNomineedetail;
    private String doViewNomineelist;
    private String address;
    private double percentage;

    //For Offshore update
    private int remarktypeId;
    private int offshoreId;
    private String offshorefilehidden;
    private String doaddoffshoredetail;
    private String doSaveoffshoredetail;
    private String doModifyoffshoredetail;
    private String doDeleteoffshoredetail;
    private String doViewoffshoredetail;
    private String remark;
    private String date;
    private Collection remarktypes;
    private FormFile offshorefile;

    //For PPE tab
    private int ppedetailId;
    private int ppetypeId;
    private String doManagePpedetail;
    private String doSavePpedetail;
    private String doDeletePpedetail;
    private String doViewPpelist;
    private String ppeRemark;
    private Collection ppetypes;

    //for Contract
    private String doViewContractlist;
    private String doManageContractdetail;
    private String doSaveContractdetail;
    private int contractId;
    private int contractIdhidden;
    private int refId;
    private int fileId;
    private int contractdetailId;
    private Collection contracts;
    private String doGenerateContarct;
    private String doGeneratedContract;
    private String repeatContract;
    private String contractApprove;
    private FormFile contractfile;
    private FormFile contractfile1;
    private String deleteContract;
    private String fromDate;
    private String toDate;
    private String deleteContractFile;
    private String cancelContract;
    private int clientIdContract;
    private int assetIdContract;
    private int contractStatus;
    private int typeId;
    private int onboardingId;
    private int onboardingfileId;
    private String cfilehidden;
    private String file1;
    private String file2;
    private String file3;
    private String hiddenFile;
    private String fromVal;
    private String toval;
    private String ccval;
    private String bccval;
    private String subject;
    private String description;
    
    //For secondary position
    private int positionId2;
    private double p2rate1;
    private double p2rate2;
    private int toPositionId2;
    private int toCurrencyId2;
    private String top2rate1;
    private String top2rate2;
    private String top2rate3;    
    private Collection positions2;
    
    //Appraisals
    private String currency;
    private String doViewAppraisallist;
    private String doAddAppraisaldetail;
    private String doSaveAppraisaldetail;
    private String appraisalfilehidden;
    private int appraisalId;
    private int checktype;
    private FormFile appraisalFile;
    private double oldRate1;
    private double oldRate2;
    private double oldRate3;
    
    private Collection states;
    private String pinCode;
    private int stateId;
    private int age;
    private String airport1;
    private String airport2;
    private String accountHolder;
    private String localFile;
    
    //For dayrate tab
    private String doAddDayratedetail;
    private String doViewDayratelist;
    private String doSaveDayratedetail;
    private String doSaveDayrate;
    private int dayrateId;
    private int dayrateId2;
    private int dayratehId;
    
    private int travelDays;
    private String role;
    private String profile;
    private String skill1;
    private String skill2;
    private String rolehiddenfile;

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getDoCancel() {
        return doCancel;
    }

    public void setDoCancel(String doCancel) {
        this.doCancel = doCancel;
    }

    public String getDoModify() {
        return doModify;
    }

    public void setDoModify(String doModify) {
        this.doModify = doModify;
    }

    public String getDoView() {
        return doView;
    }

    public void setDoView(String doView) {
        this.doView = doView;
    }

    public String getDoAdd() {
        return doAdd;
    }

    public void setDoAdd(String doAdd) {
        this.doAdd = doAdd;
    }

    public String getDoSave() {
        return doSave;
    }

    public void setDoSave(String doSave) {
        this.doSave = doSave;
    }

    public String getDoViewVerificationlist() {
        return doViewVerificationlist;
    }

    public void setDoViewVerificationlist(String doViewVerificationlist) {
        this.doViewVerificationlist = doViewVerificationlist;
    }

    public String getDoViewAssessmentlist() {
        return doViewAssessmentlist;
    }

    public void setDoViewAssessmentlist(String doViewAssessmentlist) {
        this.doViewAssessmentlist = doViewAssessmentlist;
    }

    public String getDoViewShortlistinglist() {
        return doViewShortlistinglist;
    }

    public void setDoViewShortlistinglist(String doViewShortlistinglist) {
        this.doViewShortlistinglist = doViewShortlistinglist;
    }

    public String getDoGenerate() {
        return doGenerate;
    }

    public void setDoGenerate(String doGenerate) {
        this.doGenerate = doGenerate;
    }

    public String getDoGenerateOF() {
        return doGenerateOF;
    }

    public void setDoGenerateOF(String doGenerateOF) {
        this.doGenerateOF = doGenerateOF;
    }

    public int getStatusIndex() {
        return statusIndex;
    }

    public void setStatusIndex(int statusIndex) {
        this.statusIndex = statusIndex;
    }

    public int getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(int positionIndex) {
        this.positionIndex = positionIndex;
    }

    public Collection getPositions() {
        return positions;
    }

    public void setPositions(Collection positions) {
        this.positions = positions;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Collection getClients() {
        return clients;
    }

    public void setClients(Collection clients) {
        this.clients = clients;
    }

    public Collection getCountries() {
        return countries;
    }

    public void setCountries(Collection countries) {
        this.countries = countries;
    }

    public Collection getAssets() {
        return assets;
    }

    public void setAssets(Collection assets) {
        this.assets = assets;
    }

    public int getClientIdIndex() {
        return clientIdIndex;
    }

    public void setClientIdIndex(int clientIdIndex) {
        this.clientIdIndex = clientIdIndex;
    }

    public int getCountryIdIndex() {
        return countryIdIndex;
    }

    public void setCountryIdIndex(int countryIdIndex) {
        this.countryIdIndex = countryIdIndex;
    }

    public int getAssetIdIndex() {
        return assetIdIndex;
    }

    public void setAssetIdIndex(int assetIdIndex) {
        this.assetIdIndex = assetIdIndex;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPlaceofbirth() {
        return placeofbirth;
    }

    public void setPlaceofbirth(String placeofbirth) {
        this.placeofbirth = placeofbirth;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getContactno1() {
        return contactno1;
    }

    public void setContactno1(String contactno1) {
        this.contactno1 = contactno1;
    }

    public String getContactno2() {
        return contactno2;
    }

    public void setContactno2(String contactno2) {
        this.contactno2 = contactno2;
    }

    public String getContactno3() {
        return contactno3;
    }

    public void setContactno3(String contactno3) {
        this.contactno3 = contactno3;
    }

    public String getEcontactno1() {
        return econtactno1;
    }

    public void setEcontactno1(String econtactno1) {
        this.econtactno1 = econtactno1;
    }

    public String getEcontactno2() {
        return econtactno2;
    }

    public void setEcontactno2(String econtactno2) {
        this.econtactno2 = econtactno2;
    }

    public String getCode1Id() {
        return code1Id;
    }

    public void setCode1Id(String code1Id) {
        this.code1Id = code1Id;
    }

    public String getCode2Id() {
        return code2Id;
    }

    public void setCode2Id(String code2Id) {
        this.code2Id = code2Id;
    }

    public String getCode3Id() {
        return code3Id;
    }

    public void setCode3Id(String code3Id) {
        this.code3Id = code3Id;
    }

    public String getEcode1Id() {
        return ecode1Id;
    }

    public void setEcode1Id(String ecode1Id) {
        this.ecode1Id = ecode1Id;
    }

    public String getEcode2Id() {
        return ecode2Id;
    }

    public void setEcode2Id(String ecode2Id) {
        this.ecode2Id = ecode2Id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(int nationalityId) {
        this.nationalityId = nationalityId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public Collection getDepartments() {
        return departments;
    }

    public void setDepartments(Collection departments) {
        this.departments = departments;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public Collection getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Collection currencies) {
        this.currencies = currencies;
    }

    public String getNextofkin() {
        return nextofkin;
    }

    public void setNextofkin(String nextofkin) {
        this.nextofkin = nextofkin;
    }

    public Collection getRelations() {
        return relations;
    }

    public void setRelations(Collection relations) {
        this.relations = relations;
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public Collection getMaritalstatuses() {
        return maritalstatuses;
    }

    public void setMaritalstatuses(Collection maritalstatuses) {
        this.maritalstatuses = maritalstatuses;
    }

    public int getMaritalstatusId() {
        return maritalstatusId;
    }

    public void setMaritalstatusId(int maritalstatusId) {
        this.maritalstatusId = maritalstatusId;
    }

    public String getAddress1line1() {
        return address1line1;
    }

    public void setAddress1line1(String address1line1) {
        this.address1line1 = address1line1;
    }

    public String getAddress1line2() {
        return address1line2;
    }

    public void setAddress1line2(String address1line2) {
        this.address1line2 = address1line2;
    }

    public String getAddress1line3() {
        return address1line3;
    }

    public void setAddress1line3(String address1line3) {
        this.address1line3 = address1line3;
    }

    public String getAddress2line1() {
        return address2line1;
    }

    public void setAddress2line1(String address2line1) {
        this.address2line1 = address2line1;
    }

    public String getAddress2line2() {
        return address2line2;
    }

    public void setAddress2line2(String address2line2) {
        this.address2line2 = address2line2;
    }

    public String getAddress2line3() {
        return address2line3;
    }

    public void setAddress2line3(String address2line3) {
        this.address2line3 = address2line3;
    }

    public FormFile getPhotofile() {
        return photofile;
    }

    public void setPhotofile(FormFile photofile) {
        this.photofile = photofile;
    }

    public FormFile getResumefile() {
        return resumefile;
    }

    public void setResumefile(FormFile resumefile) {
        this.resumefile = resumefile;
    }

    public String getPhotofilehidden() {
        return photofilehidden;
    }

    public void setPhotofilehidden(String photofilehidden) {
        this.photofilehidden = photofilehidden;
    }

    public String getResumefilehidden() {
        return resumefilehidden;
    }

    public void setResumefilehidden(String resumefilehidden) {
        this.resumefilehidden = resumefilehidden;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public int getExpectedsalary() {
        return expectedsalary;
    }

    public void setExpectedsalary(int expectedsalary) {
        this.expectedsalary = expectedsalary;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getPositionIdhidden() {
        return positionIdhidden;
    }

    public void setPositionIdhidden(int positionIdhidden) {
        this.positionIdhidden = positionIdhidden;
    }

    public int getResumetempId() {
        return resumetempId;
    }

    public void setResumetempId(int resumetempId) {
        this.resumetempId = resumetempId;
    }

    public Collection getResumetemplates() {
        return resumetemplates;
    }

    public void setResumetemplates(Collection resumetemplates) {
        this.resumetemplates = resumetemplates;
    }

    public Collection getFormalitytemplates() {
        return formalitytemplates;
    }

    public void setFormalitytemplates(Collection formalitytemplates) {
        this.formalitytemplates = formalitytemplates;
    }

    public String getCval1() {
        return cval1;
    }

    public void setCval1(String cval1) {
        this.cval1 = cval1;
    }

    public String getCval2() {
        return cval2;
    }

    public void setCval2(String cval2) {
        this.cval2 = cval2;
    }

    public String getCval3() {
        return cval3;
    }

    public void setCval3(String cval3) {
        this.cval3 = cval3;
    }

    public String getCval4() {
        return cval4;
    }

    public void setCval4(String cval4) {
        this.cval4 = cval4;
    }

    public String getCval5() {
        return cval5;
    }

    public void setCval5(String cval5) {
        this.cval5 = cval5;
    }

    public String getCval6() {
        return cval6;
    }

    public void setCval6(String cval6) {
        this.cval6 = cval6;
    }

    public String getCval7() {
        return cval7;
    }

    public void setCval7(String cval7) {
        this.cval7 = cval7;
    }

    public String getCval8() {
        return cval8;
    }

    public void setCval8(String cval8) {
        this.cval8 = cval8;
    }

    public String getCval9() {
        return cval9;
    }

    public void setCval9(String cval9) {
        this.cval9 = cval9;
    }

    public String getCval10() {
        return cval10;
    }

    public void setCval10(String cval10) {
        this.cval10 = cval10;
    }

    public int getEmployementstatus() {
        return employementstatus;
    }

    public void setEmployementstatus(int employementstatus) {
        this.employementstatus = employementstatus;
    }

    public int getBankdetailId() {
        return bankdetailId;
    }

    public void setBankdetailId(int bankdetailId) {
        this.bankdetailId = bankdetailId;
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

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public Collection getBankAccountTypes() {
        return bankAccountTypes;
    }

    public void setBankAccountTypes(Collection bankAccountTypes) {
        this.bankAccountTypes = bankAccountTypes;
    }

    public String getBankfilehidden() {
        return bankfilehidden;
    }

    public void setBankfilehidden(String bankfilehidden) {
        this.bankfilehidden = bankfilehidden;
    }

    public String getDoViewBanklist() {
        return doViewBanklist;
    }

    public void setDoViewBanklist(String doViewBanklist) {
        this.doViewBanklist = doViewBanklist;
    }

    public String getDoManageBankdetail() {
        return doManageBankdetail;
    }

    public void setDoManageBankdetail(String doManageBankdetail) {
        this.doManageBankdetail = doManageBankdetail;
    }

    public String getDoSaveBankdetail() {
        return doSaveBankdetail;
    }

    public void setDoSaveBankdetail(String doSaveBankdetail) {
        this.doSaveBankdetail = doSaveBankdetail;
    }

    public String getDoDeleteBankdetail() {
        return doDeleteBankdetail;
    }

    public void setDoDeleteBankdetail(String doDeleteBankdetail) {
        this.doDeleteBankdetail = doDeleteBankdetail;
    }

    public FormFile getBankfile() {
        return bankfile;
    }

    public void setBankfile(FormFile bankfile) {
        this.bankfile = bankfile;
    }

    public int getPrimarybankId() {
        return primarybankId;
    }

    public void setPrimarybankId(int primarybankId) {
        this.primarybankId = primarybankId;
    }

    public int getCandidateLangId() {
        return candidateLangId;
    }

    public void setCandidateLangId(int candidateLangId) {
        this.candidateLangId = candidateLangId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public int getProficiencyId() {
        return proficiencyId;
    }

    public void setProficiencyId(int proficiencyId) {
        this.proficiencyId = proficiencyId;
    }

    public String getLangfilehidden() {
        return langfilehidden;
    }

    public void setLangfilehidden(String langfilehidden) {
        this.langfilehidden = langfilehidden;
    }

    public String getDoaddlangdetail() {
        return doaddlangdetail;
    }

    public void setDoaddlangdetail(String doaddlangdetail) {
        this.doaddlangdetail = doaddlangdetail;
    }

    public String getDoSavelangdetail() {
        return doSavelangdetail;
    }

    public void setDoSavelangdetail(String doSavelangdetail) {
        this.doSavelangdetail = doSavelangdetail;
    }

    public String getDoModifylangdetail() {
        return doModifylangdetail;
    }

    public void setDoModifylangdetail(String doModifylangdetail) {
        this.doModifylangdetail = doModifylangdetail;
    }

    public String getDoDeletelangdetail() {
        return doDeletelangdetail;
    }

    public void setDoDeletelangdetail(String doDeletelangdetail) {
        this.doDeletelangdetail = doDeletelangdetail;
    }

    public String getDoViewlangdetail() {
        return doViewlangdetail;
    }

    public void setDoViewlangdetail(String doViewlangdetail) {
        this.doViewlangdetail = doViewlangdetail;
    }

    public Collection getLanguages() {
        return languages;
    }

    public void setLanguages(Collection languages) {
        this.languages = languages;
    }

    public Collection getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(Collection proficiencies) {
        this.proficiencies = proficiencies;
    }

    public FormFile getLangfile() {
        return langfile;
    }

    public void setLangfile(FormFile langfile) {
        this.langfile = langfile;
    }

    public int getCandidatehealthId() {
        return candidatehealthId;
    }

    public void setCandidatehealthId(int candidatehealthId) {
        this.candidatehealthId = candidatehealthId;
    }

    public String getSsmf() {
        return ssmf;
    }

    public void setSsmf(String ssmf) {
        this.ssmf = ssmf;
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

    public String getMedifitcert() {
        return medifitcert;
    }

    public void setMedifitcert(String medifitcert) {
        this.medifitcert = medifitcert;
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

    public int getBloodpressureid() {
        return bloodpressureid;
    }

    public void setBloodpressureid(int bloodpressureid) {
        this.bloodpressureid = bloodpressureid;
    }

    public Collection getBloodpressures() {
        return bloodpressures;
    }

    public void setBloodpressures(Collection bloodpressures) {
        this.bloodpressures = bloodpressures;
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

    public String getCov192doses() {
        return cov192doses;
    }

    public void setCov192doses(String cov192doses) {
        this.cov192doses = cov192doses;
    }

    public String getHealthfilehidden() {
        return healthfilehidden;
    }

    public void setHealthfilehidden(String healthfilehidden) {
        this.healthfilehidden = healthfilehidden;
    }

    public String getDoaddhealthdetail() {
        return doaddhealthdetail;
    }

    public void setDoaddhealthdetail(String doaddhealthdetail) {
        this.doaddhealthdetail = doaddhealthdetail;
    }

    public String getDoSavehealthdetail() {
        return doSavehealthdetail;
    }

    public void setDoSavehealthdetail(String doSavehealthdetail) {
        this.doSavehealthdetail = doSavehealthdetail;
    }

    public String getDoModifyhealthdetail() {
        return doModifyhealthdetail;
    }

    public void setDoModifyhealthdetail(String doModifyhealthdetail) {
        this.doModifyhealthdetail = doModifyhealthdetail;
    }

    public String getDoViewhealthdetail() {
        return doViewhealthdetail;
    }

    public void setDoViewhealthdetail(String doViewhealthdetail) {
        this.doViewhealthdetail = doViewhealthdetail;
    }

    public FormFile getHealthfile() {
        return healthfile;
    }

    public void setHealthfile(FormFile healthfile) {
        this.healthfile = healthfile;
    }

    public int getCandidatevaccineId() {
        return candidatevaccineId;
    }

    public void setCandidatevaccineId(int candidatevaccineId) {
        this.candidatevaccineId = candidatevaccineId;
    }

    public int getVaccinationNameId() {
        return vaccinationNameId;
    }

    public void setVaccinationNameId(int vaccinationNameId) {
        this.vaccinationNameId = vaccinationNameId;
    }

    public int getVacinationTypeId() {
        return vacinationTypeId;
    }

    public void setVacinationTypeId(int vacinationTypeId) {
        this.vacinationTypeId = vacinationTypeId;
    }

    public Collection getVacinationnames() {
        return vacinationnames;
    }

    public void setVacinationnames(Collection vacinationnames) {
        this.vacinationnames = vacinationnames;
    }

    public Collection getVaccinationtypes() {
        return vaccinationtypes;
    }

    public void setVaccinationtypes(Collection vaccinationtypes) {
        this.vaccinationtypes = vaccinationtypes;
    }

    public int getPlaceofapplicationId() {
        return placeofapplicationId;
    }

    public void setPlaceofapplicationId(int placeofapplicationId) {
        this.placeofapplicationId = placeofapplicationId;
    }

    public Collection getCities() {
        return cities;
    }

    public void setCities(Collection cities) {
        this.cities = cities;
    }

    public FormFile getVaccinedetailfile() {
        return vaccinedetailfile;
    }

    public void setVaccinedetailfile(FormFile vaccinedetailfile) {
        this.vaccinedetailfile = vaccinedetailfile;
    }

    public String getVaccinedetailhiddenfile() {
        return vaccinedetailhiddenfile;
    }

    public void setVaccinedetailhiddenfile(String vaccinedetailhiddenfile) {
        this.vaccinedetailhiddenfile = vaccinedetailhiddenfile;
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

    public String getDoaddvaccinationdetail() {
        return doaddvaccinationdetail;
    }

    public void setDoaddvaccinationdetail(String doaddvaccinationdetail) {
        this.doaddvaccinationdetail = doaddvaccinationdetail;
    }

    public String getDoSavevaccinationdetail() {
        return doSavevaccinationdetail;
    }

    public void setDoSavevaccinationdetail(String doSavevaccinationdetail) {
        this.doSavevaccinationdetail = doSavevaccinationdetail;
    }

    public String getDoViewvaccinationlist() {
        return doViewvaccinationlist;
    }

    public void setDoViewvaccinationlist(String doViewvaccinationlist) {
        this.doViewvaccinationlist = doViewvaccinationlist;
    }

    public String getDoDeletevaccinationdetail() {
        return doDeletevaccinationdetail;
    }

    public void setDoDeletevaccinationdetail(String doDeletevaccinationdetail) {
        this.doDeletevaccinationdetail = doDeletevaccinationdetail;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGovdocumentId() {
        return govdocumentId;
    }

    public void setGovdocumentId(int govdocumentId) {
        this.govdocumentId = govdocumentId;
    }

    public int getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(int documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public Collection getDocumentTypes() {
        return documentTypes;
    }

    public void setDocumentTypes(Collection documentTypes) {
        this.documentTypes = documentTypes;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getDateofissue() {
        return dateofissue;
    }

    public void setDateofissue(String dateofissue) {
        this.dateofissue = dateofissue;
    }

    public int getDocumentissuedbyId() {
        return documentissuedbyId;
    }

    public void setDocumentissuedbyId(int documentissuedbyId) {
        this.documentissuedbyId = documentissuedbyId;
    }

    public Collection getDocumentissuedbys() {
        return documentissuedbys;
    }

    public void setDocumentissuedbys(Collection documentissuedbys) {
        this.documentissuedbys = documentissuedbys;
    }

    public String getDomodifygovdocumentdetail() {
        return domodifygovdocumentdetail;
    }

    public void setDomodifygovdocumentdetail(String domodifygovdocumentdetail) {
        this.domodifygovdocumentdetail = domodifygovdocumentdetail;
    }

    public String getDoSavegovdocumentdetail() {
        return doSavegovdocumentdetail;
    }

    public void setDoSavegovdocumentdetail(String doSavegovdocumentdetail) {
        this.doSavegovdocumentdetail = doSavegovdocumentdetail;
    }

    public String getDoViewgovdocumentlist() {
        return doViewgovdocumentlist;
    }

    public void setDoViewgovdocumentlist(String doViewgovdocumentlist) {
        this.doViewgovdocumentlist = doViewgovdocumentlist;
    }

    public String getDoDeletegovdocumentdetail() {
        return doDeletegovdocumentdetail;
    }

    public void setDoDeletegovdocumentdetail(String doDeletegovdocumentdetail) {
        this.doDeletegovdocumentdetail = doDeletegovdocumentdetail;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getCompanyshipName() {
        return companyshipName;
    }

    public void setCompanyshipName(String companyshipName) {
        this.companyshipName = companyshipName;
    }

    public int getCountryworkedId() {
        return countryworkedId;
    }

    public void setCountryworkedId(int countryworkedId) {
        this.countryworkedId = countryworkedId;
    }

    public int getEngineTypeId() {
        return engineTypeId;
    }

    public void setEngineTypeId(int engineTypeId) {
        this.engineTypeId = engineTypeId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getTonnage() {
        return tonnage;
    }

    public void setTonnage(double tonnage) {
        this.tonnage = tonnage;
    }

    public String getSignIndate() {
        return signIndate;
    }

    public void setSignIndate(String signIndate) {
        this.signIndate = signIndate;
    }

    public String getSignoffdate() {
        return signoffdate;
    }

    public void setSignoffdate(String signoffdate) {
        this.signoffdate = signoffdate;
    }

    public Collection getEngineTypes() {
        return engineTypes;
    }

    public void setEngineTypes(Collection engineTypes) {
        this.engineTypes = engineTypes;
    }

    public int getTrainingandcertId() {
        return trainingandcertId;
    }

    public void setTrainingandcertId(int trainingandcertId) {
        this.trainingandcertId = trainingandcertId;
    }

    public int getCoursetypeId() {
        return coursetypeId;
    }

    public void setCoursetypeId(int coursetypeId) {
        this.coursetypeId = coursetypeId;
    }

    public int getLocationofInstituteId() {
        return locationofInstituteId;
    }

    public void setLocationofInstituteId(int locationofInstituteId) {
        this.locationofInstituteId = locationofInstituteId;
    }

    public int getApprovedbyId() {
        return approvedbyId;
    }

    public void setApprovedbyId(int approvedbyId) {
        this.approvedbyId = approvedbyId;
    }

    public int getCoursenameId() {
        return coursenameId;
    }

    public void setCoursenameId(int coursenameId) {
        this.coursenameId = coursenameId;
    }

    public Collection getApprovedbys() {
        return approvedbys;
    }

    public void setApprovedbys(Collection approvedbys) {
        this.approvedbys = approvedbys;
    }

    public Collection getCoursetypes() {
        return coursetypes;
    }

    public void setCoursetypes(Collection coursetypes) {
        this.coursetypes = coursetypes;
    }

    public Collection getCoursenames() {
        return coursenames;
    }

    public void setCoursenames(Collection coursenames) {
        this.coursenames = coursenames;
    }

    public String getEducationInstitute() {
        return educationInstitute;
    }

    public void setEducationInstitute(String educationInstitute) {
        this.educationInstitute = educationInstitute;
    }

    public String getFieldofstudy() {
        return fieldofstudy;
    }

    public void setFieldofstudy(String fieldofstudy) {
        this.fieldofstudy = fieldofstudy;
    }

    public String getCoursestarted() {
        return coursestarted;
    }

    public void setCoursestarted(String coursestarted) {
        this.coursestarted = coursestarted;
    }

    public String getPassingdate() {
        return passingdate;
    }

    public void setPassingdate(String passingdate) {
        this.passingdate = passingdate;
    }

    public String getCertificationno() {
        return certificationno;
    }

    public void setCertificationno(String certificationno) {
        this.certificationno = certificationno;
    }

    public String getCourseverification() {
        return courseverification;
    }

    public void setCourseverification(String courseverification) {
        this.courseverification = courseverification;
    }

    public FormFile getTrainingcertfile() {
        return trainingcertfile;
    }

    public void setTrainingcertfile(FormFile trainingcertfile) {
        this.trainingcertfile = trainingcertfile;
    }

    public String getTrainingcerthiddenfile() {
        return trainingcerthiddenfile;
    }

    public void setTrainingcerthiddenfile(String trainingcerthiddenfile) {
        this.trainingcerthiddenfile = trainingcerthiddenfile;
    }

    public String getDoaddtrainingcertdetail() {
        return doaddtrainingcertdetail;
    }

    public void setDoaddtrainingcertdetail(String doaddtrainingcertdetail) {
        this.doaddtrainingcertdetail = doaddtrainingcertdetail;
    }

    public String getDoSavetrainingcertdetail() {
        return doSavetrainingcertdetail;
    }

    public void setDoSavetrainingcertdetail(String doSavetrainingcertdetail) {
        this.doSavetrainingcertdetail = doSavetrainingcertdetail;
    }

    public String getDoViewtrainingcertlist() {
        return doViewtrainingcertlist;
    }

    public void setDoViewtrainingcertlist(String doViewtrainingcertlist) {
        this.doViewtrainingcertlist = doViewtrainingcertlist;
    }

    public String getDoDeletetrainingcertdetail() {
        return doDeletetrainingcertdetail;
    }

    public void setDoDeletetrainingcertdetail(String doDeletetrainingcertdetail) {
        this.doDeletetrainingcertdetail = doDeletetrainingcertdetail;
    }

    public int getEducationdetailId() {
        return educationdetailId;
    }

    public void setEducationdetailId(int educationdetailId) {
        this.educationdetailId = educationdetailId;
    }

    public String getKindname() {
        return kindname;
    }

    public void setKindname(String kindname) {
        this.kindname = kindname;
    }

    public int getKindId() {
        return kindId;
    }

    public void setKindId(int kindId) {
        this.kindId = kindId;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(int degreeId) {
        this.degreeId = degreeId;
    }

    public int getHighestqualification() {
        return highestqualification;
    }

    public void setHighestqualification(int highestqualification) {
        this.highestqualification = highestqualification;
    }

    public String getBackgroundofstudy() {
        return backgroundofstudy;
    }

    public void setBackgroundofstudy(String backgroundofstudy) {
        this.backgroundofstudy = backgroundofstudy;
    }

    public FormFile getEducationfile() {
        return educationfile;
    }

    public void setEducationfile(FormFile educationfile) {
        this.educationfile = educationfile;
    }

    public Collection getDegrees() {
        return degrees;
    }

    public void setDegrees(Collection degrees) {
        this.degrees = degrees;
    }

    public Collection getQualificationtypes() {
        return qualificationtypes;
    }

    public void setQualificationtypes(Collection qualificationtypes) {
        this.qualificationtypes = qualificationtypes;
    }

    public String getEducationhiddenfile() {
        return educationhiddenfile;
    }

    public void setEducationhiddenfile(String educationhiddenfile) {
        this.educationhiddenfile = educationhiddenfile;
    }

    public String getDoaddeducationdetail() {
        return doaddeducationdetail;
    }

    public void setDoaddeducationdetail(String doaddeducationdetail) {
        this.doaddeducationdetail = doaddeducationdetail;
    }

    public String getDoSaveeducationdetail() {
        return doSaveeducationdetail;
    }

    public void setDoSaveeducationdetail(String doSaveeducationdetail) {
        this.doSaveeducationdetail = doSaveeducationdetail;
    }

    public String getDoVieweducationlist() {
        return doVieweducationlist;
    }

    public void setDoVieweducationlist(String doVieweducationlist) {
        this.doVieweducationlist = doVieweducationlist;
    }

    public String getDoDeleteeducationdetail() {
        return doDeleteeducationdetail;
    }

    public void setDoDeleteeducationdetail(String doDeleteeducationdetail) {
        this.doDeleteeducationdetail = doDeleteeducationdetail;
    }

    public int getExperiencedetailId() {
        return experiencedetailId;
    }

    public void setExperiencedetailId(int experiencedetailId) {
        this.experiencedetailId = experiencedetailId;
    }

    public int getCompanyindustryId() {
        return companyindustryId;
    }

    public void setCompanyindustryId(int companyindustryId) {
        this.companyindustryId = companyindustryId;
    }

    public int getAssettypeId() {
        return assettypeId;
    }

    public void setAssettypeId(int assettypeId) {
        this.assettypeId = assettypeId;
    }

    public int getWaterdepthId() {
        return waterdepthId;
    }

    public void setWaterdepthId(int waterdepthId) {
        this.waterdepthId = waterdepthId;
    }

    public int getLastdrawnsalarycurrencyId() {
        return lastdrawnsalarycurrencyId;
    }

    public void setLastdrawnsalarycurrencyId(int lastdrawnsalarycurrencyId) {
        this.lastdrawnsalarycurrencyId = lastdrawnsalarycurrencyId;
    }

    public int getSkillsId() {
        return skillsId;
    }

    public void setSkillsId(int skillsId) {
        this.skillsId = skillsId;
    }

    public int getRankid() {
        return rankid;
    }

    public void setRankid(int rankid) {
        this.rankid = rankid;
    }

    public int getCrewtypeid() {
        return crewtypeid;
    }

    public void setCrewtypeid(int crewtypeid) {
        this.crewtypeid = crewtypeid;
    }

    public int getDayratecurrencyid() {
        return dayratecurrencyid;
    }

    public void setDayratecurrencyid(int dayratecurrencyid) {
        this.dayratecurrencyid = dayratecurrencyid;
    }

    public int getDayrate() {
        return dayrate;
    }

    public void setDayrate(int dayrate) {
        this.dayrate = dayrate;
    }

    public int getMonthlysalarycurrencyId() {
        return monthlysalarycurrencyId;
    }

    public void setMonthlysalarycurrencyId(int monthlysalarycurrencyId) {
        this.monthlysalarycurrencyId = monthlysalarycurrencyId;
    }

    public int getMonthlysalary() {
        return monthlysalary;
    }

    public void setMonthlysalary(int monthlysalary) {
        this.monthlysalary = monthlysalary;
    }

    public int getCurrentworkingstatus() {
        return currentworkingstatus;
    }

    public void setCurrentworkingstatus(int currentworkingstatus) {
        this.currentworkingstatus = currentworkingstatus;
    }

    public int getGradeid() {
        return gradeid;
    }

    public void setGradeid(int gradeid) {
        this.gradeid = gradeid;
    }

    public FormFile getExperiencefile() {
        return experiencefile;
    }

    public void setExperiencefile(FormFile experiencefile) {
        this.experiencefile = experiencefile;
    }

    public FormFile getWorkingfile() {
        return workingfile;
    }

    public void setWorkingfile(FormFile workingfile) {
        this.workingfile = workingfile;
    }

    public Collection getCompanyindustries() {
        return companyindustries;
    }

    public void setCompanyindustries(Collection companyindustries) {
        this.companyindustries = companyindustries;
    }

    public Collection getAssettypes() {
        return assettypes;
    }

    public void setAssettypes(Collection assettypes) {
        this.assettypes = assettypes;
    }

    public Collection getWaterdepths() {
        return waterdepths;
    }

    public void setWaterdepths(Collection waterdepths) {
        this.waterdepths = waterdepths;
    }

    public Collection getSkills() {
        return skills;
    }

    public void setSkills(Collection skills) {
        this.skills = skills;
    }

    public Collection getRanks() {
        return ranks;
    }

    public void setRanks(Collection ranks) {
        this.ranks = ranks;
    }

    public Collection getCrewtypes() {
        return crewtypes;
    }

    public void setCrewtypes(Collection crewtypes) {
        this.crewtypes = crewtypes;
    }

    public Collection getGrades() {
        return grades;
    }

    public void setGrades(Collection grades) {
        this.grades = grades;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getAssetname() {
        return assetname;
    }

    public void setAssetname(String assetname) {
        this.assetname = assetname;
    }

    public String getClientpartyname() {
        return clientpartyname;
    }

    public void setClientpartyname(String clientpartyname) {
        this.clientpartyname = clientpartyname;
    }

    public String getLastdrawnsalary() {
        return lastdrawnsalary;
    }

    public void setLastdrawnsalary(String lastdrawnsalary) {
        this.lastdrawnsalary = lastdrawnsalary;
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

    public String getOwnerpool() {
        return ownerpool;
    }

    public void setOwnerpool(String ownerpool) {
        this.ownerpool = ownerpool;
    }

    public String getOcsemployed() {
        return ocsemployed;
    }

    public void setOcsemployed(String ocsemployed) {
        this.ocsemployed = ocsemployed;
    }

    public String getLegalrights() {
        return legalrights;
    }

    public void setLegalrights(String legalrights) {
        this.legalrights = legalrights;
    }

    public String getExperiencehiddenfile() {
        return experiencehiddenfile;
    }

    public void setExperiencehiddenfile(String experiencehiddenfile) {
        this.experiencehiddenfile = experiencehiddenfile;
    }

    public String getWorkinghiddenfile() {
        return workinghiddenfile;
    }

    public void setWorkinghiddenfile(String workinghiddenfile) {
        this.workinghiddenfile = workinghiddenfile;
    }

    public String getDoaddexperiencedetail() {
        return doaddexperiencedetail;
    }

    public void setDoaddexperiencedetail(String doaddexperiencedetail) {
        this.doaddexperiencedetail = doaddexperiencedetail;
    }

    public String getDoSaveexperiencedetail() {
        return doSaveexperiencedetail;
    }

    public void setDoSaveexperiencedetail(String doSaveexperiencedetail) {
        this.doSaveexperiencedetail = doSaveexperiencedetail;
    }

    public String getDoViewexperiencelist() {
        return doViewexperiencelist;
    }

    public void setDoViewexperiencelist(String doViewexperiencelist) {
        this.doViewexperiencelist = doViewexperiencelist;
    }

    public String getDoDeleteexperiencedetail() {
        return doDeleteexperiencedetail;
    }

    public void setDoDeleteexperiencedetail(String doDeleteexperiencedetail) {
        this.doDeleteexperiencedetail = doDeleteexperiencedetail;
    }

    public String getRatetype() {
        return ratetype;
    }

    public void setRatetype(String ratetype) {
        this.ratetype = ratetype;
    }

    public double getRate1() {
        return rate1;
    }

    public void setRate1(double rate1) {
        this.rate1 = rate1;
    }

    public double getRate2() {
        return rate2;
    }

    public void setRate2(double rate2) {
        this.rate2 = rate2;
    }

    public int getPositionIndexId() {
        return positionIndexId;
    }

    public void setPositionIndexId(int positionIndexId) {
        this.positionIndexId = positionIndexId;
    }

    public int getClientIndex() {
        return clientIndex;
    }

    public void setClientIndex(int clientIndex) {
        this.clientIndex = clientIndex;
    }

    public int getAssetIndex() {
        return assetIndex;
    }

    public void setAssetIndex(int assetIndex) {
        this.assetIndex = assetIndex;
    }

    public int getLocationIndex() {
        return locationIndex;
    }

    public void setLocationIndex(int locationIndex) {
        this.locationIndex = locationIndex;
    }

    public Collection getClientassets() {
        return clientassets;
    }

    public void setClientassets(Collection clientassets) {
        this.clientassets = clientassets;
    }

    public Collection getLocations() {
        return locations;
    }

    public void setLocations(Collection locations) {
        this.locations = locations;
    }

    public Collection getIpositions() {
        return ipositions;
    }

    public void setIpositions(Collection ipositions) {
        this.ipositions = ipositions;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public int getCassessmentId() {
        return cassessmentId;
    }

    public void setCassessmentId(int cassessmentId) {
        this.cassessmentId = cassessmentId;
    }

    public int getpAssessmentId() {
        return pAssessmentId;
    }

    public void setpAssessmentId(int pAssessmentId) {
        this.pAssessmentId = pAssessmentId;
    }

    public String getDoViewCompliancelist() {
        return doViewCompliancelist;
    }

    public void setDoViewCompliancelist(String doViewCompliancelist) {
        this.doViewCompliancelist = doViewCompliancelist;
    }

    public String getDoViewClientselectionlist() {
        return doViewClientselectionlist;
    }

    public void setDoViewClientselectionlist(String doViewClientselectionlist) {
        this.doViewClientselectionlist = doViewClientselectionlist;
    }

    public String getDoViewOnboardinglist() {
        return doViewOnboardinglist;
    }

    public void setDoViewOnboardinglist(String doViewOnboardinglist) {
        this.doViewOnboardinglist = doViewOnboardinglist;
    }

    public String getDoViewMobilizationlist() {
        return doViewMobilizationlist;
    }

    public void setDoViewMobilizationlist(String doViewMobilizationlist) {
        this.doViewMobilizationlist = doViewMobilizationlist;
    }

    public String getDoViewRotationlist() {
        return doViewRotationlist;
    }

    public void setDoViewRotationlist(String doViewRotationlist) {
        this.doViewRotationlist = doViewRotationlist;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientassetId() {
        return clientassetId;
    }

    public void setClientassetId(int clientassetId) {
        this.clientassetId = clientassetId;
    }

    public int getCrewrotationId() {
        return crewrotationId;
    }

    public void setCrewrotationId(int crewrotationId) {
        this.crewrotationId = crewrotationId;
    }

    public String getDoSummary() {
        return doSummary;
    }

    public void setDoSummary(String doSummary) {
        this.doSummary = doSummary;
    }

    public int getFromClientId() {
        return fromClientId;
    }

    public void setFromClientId(int fromClientId) {
        this.fromClientId = fromClientId;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransferRemark() {
        return transferRemark;
    }

    public void setTransferRemark(String transferRemark) {
        this.transferRemark = transferRemark;
    }

    public String getDoSaveTransferModal() {
        return doSaveTransferModal;
    }

    public void setDoSaveTransferModal(String doSaveTransferModal) {
        this.doSaveTransferModal = doSaveTransferModal;
    }

    public int getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(int activeStatus) {
        this.activeStatus = activeStatus;
    }

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public String getDoSaveTerminateModal() {
        return doSaveTerminateModal;
    }

    public void setDoSaveTerminateModal(String doSaveTerminateModal) {
        this.doSaveTerminateModal = doSaveTerminateModal;
    }

    public String getTerminateDate() {
        return terminateDate;
    }

    public void setTerminateDate(String terminateDate) {
        this.terminateDate = terminateDate;
    }

    public String getTerminateRemark() {
        return terminateRemark;
    }

    public void setTerminateRemark(String terminateRemark) {
        this.terminateRemark = terminateRemark;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getEXTYPE() {
        return EXTYPE;
    }

    public void setEXTYPE(String EXTYPE) {
        this.EXTYPE = EXTYPE;
    }

    public String getDoViewNotificationlist() {
        return doViewNotificationlist;
    }

    public void setDoViewNotificationlist(String doViewNotificationlist) {
        this.doViewNotificationlist = doViewNotificationlist;
    }

    public String getDoViewTransferterminationlist() {
        return doViewTransferterminationlist;
    }

    public void setDoViewTransferterminationlist(String doViewTransferterminationlist) {
        this.doViewTransferterminationlist = doViewTransferterminationlist;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public FormFile getTerminatefile() {
        return terminatefile;
    }

    public void setTerminatefile(FormFile terminatefile) {
        this.terminatefile = terminatefile;
    }

    public String getTerminatefilehidden() {
        return terminatefilehidden;
    }

    public void setTerminatefilehidden(String terminatefilehidden) {
        this.terminatefilehidden = terminatefilehidden;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getJoiningDate1() {
        return joiningDate1;
    }

    public void setJoiningDate1(String joiningDate1) {
        this.joiningDate1 = joiningDate1;
    }

    public int getToClientId() {
        return toClientId;
    }

    public void setToClientId(int toClientId) {
        this.toClientId = toClientId;
    }

    public int getToAssetId() {
        return toAssetId;
    }

    public void setToAssetId(int toAssetId) {
        this.toAssetId = toAssetId;
    }

    public int getToPositionId() {
        return toPositionId;
    }

    public void setToPositionId(int toPositionId) {
        this.toPositionId = toPositionId;
    }

    public int getToCurrencyId() {
        return toCurrencyId;
    }

    public void setToCurrencyId(int toCurrencyId) {
        this.toCurrencyId = toCurrencyId;
    }

    public String getTorate1() {
        return torate1;
    }

    public void setTorate1(String torate1) {
        this.torate1 = torate1;
    }

    public String getTorate2() {
        return torate2;
    }

    public void setTorate2(String torate2) {
        this.torate2 = torate2;
    }

    public String getTorate3() {
        return torate3;
    }

    public void setTorate3(String torate3) {
        this.torate3 = torate3;
    }

    public String getDoSaveOnboardModal() {
        return doSaveOnboardModal;
    }

    public void setDoSaveOnboardModal(String doSaveOnboardModal) {
        this.doSaveOnboardModal = doSaveOnboardModal;
    }

    public String getOnboardDate() {
        return onboardDate;
    }

    public void setOnboardDate(String onboardDate) {
        this.onboardDate = onboardDate;
    }

    public String getOnboardRemark() {
        return onboardRemark;
    }

    public void setOnboardRemark(String onboardRemark) {
        this.onboardRemark = onboardRemark;
    }

    public int getClientIdModal() {
        return clientIdModal;
    }

    public void setClientIdModal(int clientIdModal) {
        this.clientIdModal = clientIdModal;
    }

    public int getAssetIdModal() {
        return assetIdModal;
    }

    public void setAssetIdModal(int assetIdModal) {
        this.assetIdModal = assetIdModal;
    }

    public int getPositionIdModal() {
        return positionIdModal;
    }

    public void setPositionIdModal(int positionIdModal) {
        this.positionIdModal = positionIdModal;
    }

    public String getDoViewWellnessfeedbacklist() {
        return doViewWellnessfeedbacklist;
    }

    public void setDoViewWellnessfeedbacklist(String doViewWellnessfeedbacklist) {
        this.doViewWellnessfeedbacklist = doViewWellnessfeedbacklist;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getApplytype() {
        return applytype;
    }

    public void setApplytype(int applytype) {
        this.applytype = applytype;
    }

    public String getDoViewCompetencylist() {
        return doViewCompetencylist;
    }

    public void setDoViewCompetencylist(String doViewCompetencylist) {
        this.doViewCompetencylist = doViewCompetencylist;
    }

    public Collection getDepts() {
        return depts;
    }

    public void setDepts(Collection depts) {
        this.depts = depts;
    }

    public int getClientIdcom() {
        return clientIdcom;
    }

    public void setClientIdcom(int clientIdcom) {
        this.clientIdcom = clientIdcom;
    }

    public int getAssetIdcom() {
        return assetIdcom;
    }

    public void setAssetIdcom(int assetIdcom) {
        this.assetIdcom = assetIdcom;
    }

    public int getPositionIdcom() {
        return positionIdcom;
    }

    public void setPositionIdcom(int positionIdcom) {
        this.positionIdcom = positionIdcom;
    }

    public int getPdeptId() {
        return pdeptId;
    }

    public void setPdeptId(int pdeptId) {
        this.pdeptId = pdeptId;
    }

    public int getPositionFilterId() {
        return positionFilterId;
    }

    public void setPositionFilterId(int positionFilterId) {
        this.positionFilterId = positionFilterId;
    }

    public int getAssettypeIdIndex() {
        return assettypeIdIndex;
    }

    public void setAssettypeIdIndex(int assettypeIdIndex) {
        this.assettypeIdIndex = assettypeIdIndex;
    }

    public int getCourseIndex() {
        return courseIndex;
    }

    public void setCourseIndex(int courseIndex) {
        this.courseIndex = courseIndex;
    }

    public int getCandidateIdHeader() {
        return candidateIdHeader;
    }

    public void setCandidateIdHeader(int candidateIdHeader) {
        this.candidateIdHeader = candidateIdHeader;
    }

    public String getDoViewHeader() {
        return doViewHeader;
    }

    public void setDoViewHeader(String doViewHeader) {
        this.doViewHeader = doViewHeader;
    }

    public String getDoMobTravel() {
        return doMobTravel;
    }

    public void setDoMobTravel(String doMobTravel) {
        this.doMobTravel = doMobTravel;
    }

    public int getFormalityId() {
        return formalityId;
    }

    public void setFormalityId(int formalityId) {
        this.formalityId = formalityId;
    }

    public int getGeneratedlistsize() {
        return generatedlistsize;
    }

    public void setGeneratedlistsize(int generatedlistsize) {
        this.generatedlistsize = generatedlistsize;
    }

    public String getDoDeleteHealthFile() {
        return doDeleteHealthFile;
    }

    public void setDoDeleteHealthFile(String doDeleteHealthFile) {
        this.doDeleteHealthFile = doDeleteHealthFile;
    }

    public int getHealthfileId() {
        return healthfileId;
    }

    public void setHealthfileId(int healthfileId) {
        this.healthfileId = healthfileId;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    public String getNomineeContactno() {
        return nomineeContactno;
    }

    public void setNomineeContactno(String nomineeContactno) {
        this.nomineeContactno = nomineeContactno;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public int getNomineedetailId() {
        return nomineedetailId;
    }

    public void setNomineedetailId(int nomineedetailId) {
        this.nomineedetailId = nomineedetailId;
    }

    public String getDoManageNomineedetail() {
        return doManageNomineedetail;
    }

    public void setDoManageNomineedetail(String doManageNomineedetail) {
        this.doManageNomineedetail = doManageNomineedetail;
    }

    public String getDoSaveNomineedetail() {
        return doSaveNomineedetail;
    }

    public void setDoSaveNomineedetail(String doSaveNomineedetail) {
        this.doSaveNomineedetail = doSaveNomineedetail;
    }

    public String getDoDeleteNomineedetail() {
        return doDeleteNomineedetail;
    }

    public void setDoDeleteNomineedetail(String doDeleteNomineedetail) {
        this.doDeleteNomineedetail = doDeleteNomineedetail;
    }

    public String getDoViewNomineelist() {
        return doViewNomineelist;
    }

    public void setDoViewNomineelist(String doViewNomineelist) {
        this.doViewNomineelist = doViewNomineelist;
    }

    public int getRemarktypeId() {
        return remarktypeId;
    }

    public void setRemarktypeId(int remarktypeId) {
        this.remarktypeId = remarktypeId;
    }

    public int getOffshoreId() {
        return offshoreId;
    }

    public void setOffshoreId(int offshoreId) {
        this.offshoreId = offshoreId;
    }

    public String getOffshorefilehidden() {
        return offshorefilehidden;
    }

    public void setOffshorefilehidden(String offshorefilehidden) {
        this.offshorefilehidden = offshorefilehidden;
    }

    public String getDoaddoffshoredetail() {
        return doaddoffshoredetail;
    }

    public void setDoaddoffshoredetail(String doaddoffshoredetail) {
        this.doaddoffshoredetail = doaddoffshoredetail;
    }

    public String getDoSaveoffshoredetail() {
        return doSaveoffshoredetail;
    }

    public void setDoSaveoffshoredetail(String doSaveoffshoredetail) {
        this.doSaveoffshoredetail = doSaveoffshoredetail;
    }

    public String getDoModifyoffshoredetail() {
        return doModifyoffshoredetail;
    }

    public void setDoModifyoffshoredetail(String doModifyoffshoredetail) {
        this.doModifyoffshoredetail = doModifyoffshoredetail;
    }

    public String getDoDeleteoffshoredetail() {
        return doDeleteoffshoredetail;
    }

    public void setDoDeleteoffshoredetail(String doDeleteoffshoredetail) {
        this.doDeleteoffshoredetail = doDeleteoffshoredetail;
    }

    public String getDoViewoffshoredetail() {
        return doViewoffshoredetail;
    }

    public void setDoViewoffshoredetail(String doViewoffshoredetail) {
        this.doViewoffshoredetail = doViewoffshoredetail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Collection getRemarktypes() {
        return remarktypes;
    }

    public void setRemarktypes(Collection remarktypes) {
        this.remarktypes = remarktypes;
    }

    public FormFile getOffshorefile() {
        return offshorefile;
    }

    public void setOffshorefile(FormFile offshorefile) {
        this.offshorefile = offshorefile;
    }

    public int getPpedetailId() {
        return ppedetailId;
    }

    public void setPpedetailId(int ppedetailId) {
        this.ppedetailId = ppedetailId;
    }

    public int getPpetypeId() {
        return ppetypeId;
    }

    public void setPpetypeId(int ppetypeId) {
        this.ppetypeId = ppetypeId;
    }

    public String getDoManagePpedetail() {
        return doManagePpedetail;
    }

    public void setDoManagePpedetail(String doManagePpedetail) {
        this.doManagePpedetail = doManagePpedetail;
    }

    public String getDoSavePpedetail() {
        return doSavePpedetail;
    }

    public void setDoSavePpedetail(String doSavePpedetail) {
        this.doSavePpedetail = doSavePpedetail;
    }

    public String getDoDeletePpedetail() {
        return doDeletePpedetail;
    }

    public void setDoDeletePpedetail(String doDeletePpedetail) {
        this.doDeletePpedetail = doDeletePpedetail;
    }

    public String getDoViewPpelist() {
        return doViewPpelist;
    }

    public void setDoViewPpelist(String doViewPpelist) {
        this.doViewPpelist = doViewPpelist;
    }

    public Collection getPpetypes() {
        return ppetypes;
    }

    public void setPpetypes(Collection ppetypes) {
        this.ppetypes = ppetypes;
    }

    public String getDoViewContractlist() {
        return doViewContractlist;
    }

    public void setDoViewContractlist(String doViewContractlist) {
        this.doViewContractlist = doViewContractlist;
    }

    public String getDoManageContractdetail() {
        return doManageContractdetail;
    }

    public void setDoManageContractdetail(String doManageContractdetail) {
        this.doManageContractdetail = doManageContractdetail;
    }

    public String getDoSaveContractdetail() {
        return doSaveContractdetail;
    }

    public void setDoSaveContractdetail(String doSaveContractdetail) {
        this.doSaveContractdetail = doSaveContractdetail;
    }
    
    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getContractdetailId() {
        return contractdetailId;
    }

    public void setContractdetailId(int contractdetailId) {
        this.contractdetailId = contractdetailId;
    }

    public Collection getContracts() {
        return contracts;
    }

    public void setContracts(Collection contracts) {
        this.contracts = contracts;
    }

    public String getDoGenerateContarct() {
        return doGenerateContarct;
    }

    public void setDoGenerateContarct(String doGenerateContarct) {
        this.doGenerateContarct = doGenerateContarct;
    }

    public String getDoGeneratedContract() {
        return doGeneratedContract;
    }

    public void setDoGeneratedContract(String doGeneratedContract) {
        this.doGeneratedContract = doGeneratedContract;
    }

    public String getRepeatContract() {
        return repeatContract;
    }

    public void setRepeatContract(String repeatContract) {
        this.repeatContract = repeatContract;
    }

    public String getContractApprove() {
        return contractApprove;
    }

    public void setContractApprove(String contractApprove) {
        this.contractApprove = contractApprove;
    }

    public FormFile getContractfile() {
        return contractfile;
    }

    public void setContractfile(FormFile contractfile) {
        this.contractfile = contractfile;
    }

    public String getDeleteContract() {
        return deleteContract;
    }

    public void setDeleteContract(String deleteContract) {
        this.deleteContract = deleteContract;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getDeleteContractFile() {
        return deleteContractFile;
    }

    public void setDeleteContractFile(String deleteContractFile) {
        this.deleteContractFile = deleteContractFile;
    }

    public int getClientIdContract() {
        return clientIdContract;
    }

    public void setClientIdContract(int clientIdContract) {
        this.clientIdContract = clientIdContract;
    }

    public int getAssetIdContract() {
        return assetIdContract;
    }

    public void setAssetIdContract(int assetIdContract) {
        this.assetIdContract = assetIdContract;
    }

    public int getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(int contractStatus) {
        this.contractStatus = contractStatus;
    }

    /**
     * @return the contractIdhidden
     */
    public int getContractIdhidden() {
        return contractIdhidden;
    }

    /**
     * @param contractIdhidden the contractIdhidden to set
     */
    public void setContractIdhidden(int contractIdhidden) {
        this.contractIdhidden = contractIdhidden;
    }

    /**
     * @return the typeId
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }    

    /**
     * @return the cfilehidden
     */
    public String getCfilehidden() {
        return cfilehidden;
    }

    /**
     * @param cfilehidden the cfilehidden to set
     */
    public void setCfilehidden(String cfilehidden) {
        this.cfilehidden = cfilehidden;
    }

    /**
     * @return the file1
     */
    public String getFile1() {
        return file1;
    }

    /**
     * @param file1 the file1 to set
     */
    public void setFile1(String file1) {
        this.file1 = file1;
    }

    /**
     * @return the file2
     */
    public String getFile2() {
        return file2;
    }

    /**
     * @param file2 the file2 to set
     */
    public void setFile2(String file2) {
        this.file2 = file2;
    }

    /**
     * @return the file3
     */
    public String getFile3() {
        return file3;
    }

    /**
     * @param file3 the file3 to set
     */
    public void setFile3(String file3) {
        this.file3 = file3;
    }

    /**
     * @return the cancelContract
     */
    public String getCancelContract() {
        return cancelContract;
    }

    /**
     * @param cancelContract the cancelContract to set
     */
    public void setCancelContract(String cancelContract) {
        this.cancelContract = cancelContract;
    }

    /**
     * @return the hiddenFile
     */
    public String getHiddenFile() {
        return hiddenFile;
    }

    /**
     * @param hiddenFile the hiddenFile to set
     */
    public void setHiddenFile(String hiddenFile) {
        this.hiddenFile = hiddenFile;
    }

    /**
     * @return the onboardingId
     */
    public int getOnboardingId() {
        return onboardingId;
    }

    /**
     * @param onboardingId the onboardingId to set
     */
    public void setOnboardingId(int onboardingId) {
        this.onboardingId = onboardingId;
    }

    /**
     * @return the onboardingfileId
     */
    public int getOnboardingfileId() {
        return onboardingfileId;
    }

    /**
     * @param onboardingfileId the onboardingfileId to set
     */
    public void setOnboardingfileId(int onboardingfileId) {
        this.onboardingfileId = onboardingfileId;
    }

    /**
     * @return the positionId2
     */
    public int getPositionId2() {
        return positionId2;
    }

    /**
     * @param positionId2 the positionId2 to set
     */
    public void setPositionId2(int positionId2) {
        this.positionId2 = positionId2;
    }

    /**
     * @return the p2rate1
     */
    public double getP2rate1() {
        return p2rate1;
    }

    /**
     * @param p2rate1 the p2rate1 to set
     */
    public void setP2rate1(double p2rate1) {
        this.p2rate1 = p2rate1;
    }

    /**
     * @return the p2rate2
     */
    public double getP2rate2() {
        return p2rate2;
    }

    /**
     * @param p2rate2 the p2rate2 to set
     */
    public void setP2rate2(double p2rate2) {
        this.p2rate2 = p2rate2;
    }

    /**
     * @return the toPositionId2
     */
    public int getToPositionId2() {
        return toPositionId2;
    }

    /**
     * @param toPositionId2 the toPositionId2 to set
     */
    public void setToPositionId2(int toPositionId2) {
        this.toPositionId2 = toPositionId2;
    }

    /**
     * @return the toCurrencyId2
     */
    public int getToCurrencyId2() {
        return toCurrencyId2;
    }

    /**
     * @param toCurrencyId2 the toCurrencyId2 to set
     */
    public void setToCurrencyId2(int toCurrencyId2) {
        this.toCurrencyId2 = toCurrencyId2;
    }

    /**
     * @return the top2rate1
     */
    public String getTop2rate1() {
        return top2rate1;
    }

    /**
     * @param top2rate1 the top2rate1 to set
     */
    public void setTop2rate1(String top2rate1) {
        this.top2rate1 = top2rate1;
    }

    /**
     * @return the top2rate2
     */
    public String getTop2rate2() {
        return top2rate2;
    }

    /**
     * @param top2rate2 the top2rate2 to set
     */
    public void setTop2rate2(String top2rate2) {
        this.top2rate2 = top2rate2;
    }

    /**
     * @return the top2rate3
     */
    public String getTop2rate3() {
        return top2rate3;
    }

    /**
     * @param top2rate3 the top2rate3 to set
     */
    public void setTop2rate3(String top2rate3) {
        this.top2rate3 = top2rate3;
    }

    /**
     * @return the positions2
     */
    public Collection getPositions2() {
        return positions2;
    }

    /**
     * @param positions2 the positions2 to set
     */
    public void setPositions2(Collection positions2) {
        this.positions2 = positions2;
    }

    /**
     * @return the ppeRemark
     */
    public String getPpeRemark() {
        return ppeRemark;
    }

    /**
     * @param ppeRemark the ppeRemark to set
     */
    public void setPpeRemark(String ppeRemark) {
        this.ppeRemark = ppeRemark;
    }

    /**
     * @return the contractfile1
     */
    public FormFile getContractfile1() {
        return contractfile1;
    }

    /**
     * @param contractfile1 the contractfile1 to set
     */
    public void setContractfile1(FormFile contractfile1) {
        this.contractfile1 = contractfile1;
    }

    /**
     * @return the fromVal
     */
    public String getFromVal() {
        return fromVal;
    }

    /**
     * @param fromVal the fromVal to set
     */
    public void setFromVal(String fromVal) {
        this.fromVal = fromVal;
    }

    /**
     * @return the ccval
     */
    public String getCcval() {
        return ccval;
    }

    /**
     * @param ccval the ccval to set
     */
    public void setCcval(String ccval) {
        this.ccval = ccval;
    }

    /**
     * @return the bccval
     */
    public String getBccval() {
        return bccval;
    }

    /**
     * @param bccval the bccval to set
     */
    public void setBccval(String bccval) {
        this.bccval = bccval;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the toval
     */
    public String getToval() {
        return toval;
    }

    /**
     * @param toval the toval to set
     */
    public void setToval(String toval) {
        this.toval = toval;
    }

    /**
     * @return the doViewAppraisallist
     */
    public String getDoViewAppraisallist() {
        return doViewAppraisallist;
    }

    /**
     * @param doViewAppraisallist the doViewAppraisallist to set
     */
    public void setDoViewAppraisallist(String doViewAppraisallist) {
        this.doViewAppraisallist = doViewAppraisallist;
    }

    /**
     * @return the doAddAppraisaldetail
     */
    public String getDoAddAppraisaldetail() {
        return doAddAppraisaldetail;
    }

    /**
     * @param doAddAppraisaldetail the doAddAppraisaldetail to set
     */
    public void setDoAddAppraisaldetail(String doAddAppraisaldetail) {
        this.doAddAppraisaldetail = doAddAppraisaldetail;
    }

    /**
     * @return the doSaveAppraisaldetail
     */
    public String getDoSaveAppraisaldetail() {
        return doSaveAppraisaldetail;
    }

    /**
     * @param doSaveAppraisaldetail the doSaveAppraisaldetail to set
     */
    public void setDoSaveAppraisaldetail(String doSaveAppraisaldetail) {
        this.doSaveAppraisaldetail = doSaveAppraisaldetail;
    }
    /**
     * @return the appraisalfilehidden
     */
    public String getAppraisalfilehidden() {
        return appraisalfilehidden;
    }

    /**
     * @param appraisalfilehidden the appraisalfilehidden to set
     */
    public void setAppraisalfilehidden(String appraisalfilehidden) {
        this.appraisalfilehidden = appraisalfilehidden;
    }

    /**
     * @return the appraisalId
     */
    public int getAppraisalId() {
        return appraisalId;
    }

    /**
     * @param appraisalId the appraisalId to set
     */
    public void setAppraisalId(int appraisalId) {
        this.appraisalId = appraisalId;
    }

    /**
     * @return the checktype
     */
    public int getChecktype() {
        return checktype;
    }

    /**
     * @param checktype the checktype to set
     */
    public void setChecktype(int checktype) {
        this.checktype = checktype;
    }

    /**
     * @return the appraisalFile
     */
    public FormFile getAppraisalFile() {
        return appraisalFile;
    }

    /**
     * @param appraisalFile the appraisalFile to set
     */
    public void setAppraisalFile(FormFile appraisalFile) {
        this.appraisalFile = appraisalFile;
    }

    /**
     * @return the rate3
     */
    public double getRate3() {
        return rate3;
    }

    /**
     * @param rate3 the rate3 to set
     */
    public void setRate3(double rate3) {
        this.rate3 = rate3;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the travelDays
     */
    public int getTravelDays() {
        return travelDays;
    }

    /**
     * @param travelDays the travelDays to set
     */
    public void setTravelDays(int travelDays) {
        this.travelDays = travelDays;
    }

    /**
     * @return the oldRate1
     */
    public double getOldRate1() {
        return oldRate1;
    }

    /**
     * @param oldRate1 the oldRate1 to set
     */
    public void setOldRate1(double oldRate1) {
        this.oldRate1 = oldRate1;
    }

    /**
     * @return the oldRate2
     */
    public double getOldRate2() {
        return oldRate2;
    }

    /**
     * @param oldRate2 the oldRate2 to set
     */
    public void setOldRate2(double oldRate2) {
        this.oldRate2 = oldRate2;
    }

    /**
     * @return the oldRate3
     */
    public double getOldRate3() {
        return oldRate3;
    }

    /**
     * @param oldRate3 the oldRate3 to set
     */
    public void setOldRate3(double oldRate3) {
        this.oldRate3 = oldRate3;
    }

    /**
     * @return the states
     */
    public Collection getStates() {
        return states;
    }

    /**
     * @param states the states to set
     */
    public void setStates(Collection states) {
        this.states = states;
    }

    /**
     * @return the pinCode
     */
    public String getPinCode() {
        return pinCode;
    }

    /**
     * @param pinCode the pinCode to set
     */
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
    /**
     * @return the stateId
     */
    public int getStateId() {
        return stateId;
    }

    /**
     * @param stateId the stateId to set
     */
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the airport1
     */
    public String getAirport1() {
        return airport1;
    }

    /**
     * @param airport1 the airport1 to set
     */
    public void setAirport1(String airport1) {
        this.airport1 = airport1;
    }

    /**
     * @return the airport2
     */
    public String getAirport2() {
        return airport2;
    }

    /**
     * @param airport2 the airport2 to set
     */
    public void setAirport2(String airport2) {
        this.airport2 = airport2;
    }

    /**
     * @return the accountHolder
     */
    public String getAccountHolder() {
        return accountHolder;
    }

    /**
     * @param accountHolder the accountHolder to set
     */
    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    /**
     * @return the localFile
     */
    public String getLocalFile() {
        return localFile;
    }

    /**
     * @param localFile the localFile to set
     */
    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }

    /**
     * @return the doViewDayratelist
     */
    public String getDoViewDayratelist() {
        return doViewDayratelist;
    }

    /**
     * @param doViewDayratelist the doViewDayratelist to set
     */
    public void setDoViewDayratelist(String doViewDayratelist) {
        this.doViewDayratelist = doViewDayratelist;
    }

    /**
     * @return the dayratrId
     */
    public int getDayrateId() {
        return dayrateId;
    }

    /**
     * @param dayratrId the dayratrId to set
     */
    public void setDayrateId(int dayratrId) {
        this.dayrateId = dayratrId;
    }

    /**
     * @return the doAddDayratedetail
     */
    public String getDoAddDayratedetail() {
        return doAddDayratedetail;
    }

    /**
     * @param doAddDayratedetail the doAddDayratedetail to set
     */
    public void setDoAddDayratedetail(String doAddDayratedetail) {
        this.doAddDayratedetail = doAddDayratedetail;
    }

    /**
     * @return the doSaveDayratedetail
     */
    public String getDoSaveDayratedetail() {
        return doSaveDayratedetail;
    }

    /**
     * @param doSaveDayratedetail the doSaveDayratedetail to set
     */
    public void setDoSaveDayratedetail(String doSaveDayratedetail) {
        this.doSaveDayratedetail = doSaveDayratedetail;
    }

    /**
     * @return the endDate2
     */
    public String getEndDate2() {
        return endDate2;
    }

    /**
     * @param endDate2 the endDate2 to set
     */
    public void setEndDate2(String endDate2) {
        this.endDate2 = endDate2;
    }

    /**
     * @return the endDate1
     */
    public String getEndDate1() {
        return endDate1;
    }

    /**
     * @param endDate1 the endDate1 to set
     */
    public void setEndDate1(String endDate1) {
        this.endDate1 = endDate1;
    }

    /**
     * @return the joiningDate2
     */
    public String getJoiningDate2() {
        return joiningDate2;
    }

    /**
     * @param joiningDate2 the joiningDate2 to set
     */
    public void setJoiningDate2(String joiningDate2) {
        this.joiningDate2 = joiningDate2;
    }

    /**
     * @return the dayratehId
     */
    public int getDayratehId() {
        return dayratehId;
    }

    /**
     * @param dayratehId the dayratehId to set
     */
    public void setDayratehId(int dayratehId) {
        this.dayratehId = dayratehId;
    }

    /**
     * @return the doSaveDayrate
     */
    public String getDoSaveDayrate() {
        return doSaveDayrate;
    }

    /**
     * @param doSaveDayrate the doSaveDayrate to set
     */
    public void setDoSaveDayrate(String doSaveDayrate) {
        this.doSaveDayrate = doSaveDayrate;
    }

    /**
     * @return the dayrateId2
     */
    public int getDayrateId2() {
        return dayrateId2;
    }

    /**
     * @param dayrateId2 the dayrateId2 to set
     */
    public void setDayrateId2(int dayrateId2) {
        this.dayrateId2 = dayrateId2;
    }

    /**
     * @return the dayrate1
     */
    public double getDayrate1() {
        return dayrate1;
    }

    /**
     * @param dayrate1 the dayrate1 to set
     */
    public void setDayrate1(double dayrate1) {
        this.dayrate1 = dayrate1;
    }

    /**
     * @return the dayrate2
     */
    public double getDayrate2() {
        return dayrate2;
    }

    /**
     * @param dayrate2 the dayrate2 to set
     */
    public void setDayrate2(double dayrate2) {
        this.dayrate2 = dayrate2;
    }

    /**
     * @return the dayrate3
     */
    public double getDayrate3() {
        return dayrate3;
    }

    /**
     * @param dayrate3 the dayrate3 to set
     */
    public void setDayrate3(double dayrate3) {
        this.dayrate3 = dayrate3;
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
     * @param percentage the percentage to set
     */
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the profile
     */
    public String getProfile() {
        return profile;
    }

    /**
     * @param profile the profile to set
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     * @return the skill1
     */
    public String getSkill1() {
        return skill1;
    }

    /**
     * @param skill1 the skill1 to set
     */
    public void setSkill1(String skill1) {
        this.skill1 = skill1;
    }

    /**
     * @return the skill2
     */
    public String getSkill2() {
        return skill2;
    }

    /**
     * @param skill2 the skill2 to set
     */
    public void setSkill2(String skill2) {
        this.skill2 = skill2;
    }

    /**
     * @return the rolehiddenfile
     */
    public String getRolehiddenfile() {
        return rolehiddenfile;
    }

    /**
     * @param rolehiddenfile the rolehiddenfile to set
     */
    public void setRolehiddenfile(String rolehiddenfile) {
        this.rolehiddenfile = rolehiddenfile;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    
}
