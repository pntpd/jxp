package com.web.jxp.candidate;

import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class CandidateForm extends ActionForm {

    private int candidateId;
    private int ctp;
    private String search;
    private String doCancel;
    private String doModify;
    private String doView;
    private String doAdd;
    private String doSave;

    private int statusIndex;
    private int positionIndex;
    private int type;
    private int clientIdIndex;
    private int countryIdIndex;
    private int assetIdIndex;
    private int assettypeId;

    private Collection positions;
    private Collection positions2;
    private Collection clients;
    private Collection countries;
    private Collection assets;
    private Collection assettypes;

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
    private Collection departments;
    private Collection currencies;
    private Collection relations;
    private Collection maritalstatuses;

    private int countryId;
    private int cityId;
    private int nationalityId;
    private int departmentId;
    private int currencyId;
    private int relationId;
    private int maritalstatusId;
    private int expectedsalary;
    private int positionIdhidden;

    private String nextofkin;
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
    private String fname;
    private String employeeid;
    private int onlineFlag;
    private String religion;

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
    private String companyname;
    private int companyindustryId;
    private Collection companyindustries;
    private String assetname;
    private String clientpartyname;
    private int waterdepthId;
    private int skillsId;
    private int rankid;
    private int crewtypeid;
    private int lastdrawnsalarycurrencyId;
    private int dayratecurrencyid;
    private int dayrate;
    private int monthlysalarycurrencyId;
    private int monthlysalary;
    private int currentworkingstatus;
    private int gradeid;
    private FormFile experiencefile;
    private FormFile workingfile;
    private Collection waterdepths;
    private Collection skills;
    private Collection ranks;
    private Collection crewtypes;
    private Collection grades;
    private String ownerpool;
    private String ocsemployed;
    private String legalrights;
    private String lastdrawnsalary;
    private String workstartdate;
    private String workenddate;
    private String experiencehiddenfile;
    private String workinghiddenfile;
    private String doaddexperiencedetail;
    private String doSaveexperiencedetail;
    private String doViewexperiencelist;
    private String doDeleteexperiencedetail;
    private int applytype;
    private int statustype;
    private int assettypeIdIndex;
    private int positionIdIndex;
    private int  courseIndex;
    private int candidateIdHeader;    
    private int healthfileId;    
    private String doViewHeader;
    private String doDeleteHealthFile;    
    private int positionId2;    
    
    private String nomineeName;
    private String nomineeRelation;
    private String nomineeContactno;
    private int nomineedetailId;
    private String doManageNomineedetail;
    private String doSaveNomineedetail;
    private String doDeleteNomineedetail;
    private String doViewNomineelist;
    private String address;
    private double percentage;
    
    private Collection states;
    private String pinCode;
    private String stateName;
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
    private String rolehiddenfile;

    public int getApplytype() {
        return applytype;
    }

    public void setApplytype(int applytype) {
        this.applytype = applytype;
    }
    
    

    public int getPositionIdhidden() {
        return positionIdhidden;
    }

    public void setPositionIdhidden(int positionIdhidden) {
        this.positionIdhidden = positionIdhidden;
    }

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    public Collection getGrades() {
        return grades;
    }

    public void setGrades(Collection grades) {
        this.grades = grades;
    }

    public int getGradeid() {
        return gradeid;
    }

    public void setGradeid(int gradeid) {
        this.gradeid = gradeid;
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

    public String getOwnerpool() {
        return ownerpool;
    }

    public void setOwnerpool(String ownerpool) {
        this.ownerpool = ownerpool;
    }

    public int getCrewtypeid() {
        return crewtypeid;
    }

    public void setCrewtypeid(int crewtypeid) {
        this.crewtypeid = crewtypeid;
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

    public int getDayratecurrencyid() {
        return dayratecurrencyid;
    }

    public void setDayratecurrencyid(int dayratecurrencyid) {
        this.dayratecurrencyid = dayratecurrencyid;
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

    public int getLastdrawnsalarycurrencyId() {
        return lastdrawnsalarycurrencyId;
    }

    public void setLastdrawnsalarycurrencyId(int lastdrawnsalarycurrencyId) {
        this.lastdrawnsalarycurrencyId = lastdrawnsalarycurrencyId;
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

    public String getExperiencehiddenfile() {
        return experiencehiddenfile;
    }

    public void setExperiencehiddenfile(String experiencehiddenfile) {
        this.experiencehiddenfile = experiencehiddenfile;
    }

    public FormFile getExperiencefile() {
        return experiencefile;
    }

    public void setExperiencefile(FormFile experiencefile) {
        this.experiencefile = experiencefile;
    }

    public String getWorkinghiddenfile() {
        return workinghiddenfile;
    }

    public void setWorkinghiddenfile(String workinghiddenfile) {
        this.workinghiddenfile = workinghiddenfile;
    }

    public FormFile getWorkingfile() {
        return workingfile;
    }

    public void setWorkingfile(FormFile workingfile) {
        this.workingfile = workingfile;
    }

    public int getExperiencedetailId() {
        return experiencedetailId;
    }

    public void setExperiencedetailId(int experiencedetailId) {
        this.experiencedetailId = experiencedetailId;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
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

    public int getWaterdepthId() {
        return waterdepthId;
    }

    public void setWaterdepthId(int waterdepthId) {
        this.waterdepthId = waterdepthId;
    }

    public String getLastdrawnsalary() {
        return lastdrawnsalary;
    }

    public void setLastdrawnsalary(String lastdrawnsalary) {
        this.lastdrawnsalary = lastdrawnsalary;
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

    public int getExpectedsalary() {
        return expectedsalary;
    }

    public void setExpectedsalary(int expectedsalary) {
        this.expectedsalary = expectedsalary;
    }

    public Collection getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Collection currencies) {
        this.currencies = currencies;
    }

    public Collection getDepartments() {
        return departments;
    }

    public void setDepartments(Collection departments) {
        this.departments = departments;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getAddress1line3() {
        return address1line3;
    }

    public void setAddress1line3(String address1line3) {
        this.address1line3 = address1line3;
    }

    public String getDoViewlangdetail() {
        return doViewlangdetail;
    }

    public void setDoViewlangdetail(String doViewlangdetail) {
        this.doViewlangdetail = doViewlangdetail;
    }

    public String getDoViewhealthdetail() {
        return doViewhealthdetail;
    }

    public void setDoViewhealthdetail(String doViewhealthdetail) {
        this.doViewhealthdetail = doViewhealthdetail;
    }

    public String getCov192doses() {
        return cov192doses;
    }

    public void setCov192doses(String cov192doses) {
        this.cov192doses = cov192doses;
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

    public int getCoursenameId() {
        return coursenameId;
    }

    public void setCoursenameId(int coursenameId) {
        this.coursenameId = coursenameId;
    }

    public String getEducationInstitute() {
        return educationInstitute;
    }

    public void setEducationInstitute(String educationInstitute) {
        this.educationInstitute = educationInstitute;
    }

    public int getLocationofInstituteId() {
        return locationofInstituteId;
    }

    public void setLocationofInstituteId(int locationofInstituteId) {
        this.locationofInstituteId = locationofInstituteId;
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

    public int getApprovedbyId() {
        return approvedbyId;
    }

    public void setApprovedbyId(int approvedbyId) {
        this.approvedbyId = approvedbyId;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public Collection getDocumentTypes() {
        return documentTypes;
    }

    public void setDocumentTypes(Collection documentTypes) {
        this.documentTypes = documentTypes;
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

    public String getDoDeleteBankdetail() {
        return doDeleteBankdetail;
    }

    public void setDoDeleteBankdetail(String doDeleteBankdetail) {
        this.doDeleteBankdetail = doDeleteBankdetail;
    }

    public int getPrimarybankId() {
        return primarybankId;
    }

    public void setPrimarybankId(int primarybankId) {
        this.primarybankId = primarybankId;
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

    public String getDoViewBanklist() {
        return doViewBanklist;
    }

    public void setDoViewBanklist(String doViewBanklist) {
        this.doViewBanklist = doViewBanklist;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCandidatevaccineId() {
        return candidatevaccineId;
    }

    public void setCandidatevaccineId(int candidatevaccineId) {
        this.candidatevaccineId = candidatevaccineId;
    }

    public String getVaccinedetailhiddenfile() {
        return vaccinedetailhiddenfile;
    }

    public void setVaccinedetailhiddenfile(String vaccinedetailhiddenfile) {
        this.vaccinedetailhiddenfile = vaccinedetailhiddenfile;
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

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public FormFile getHealthfile() {
        return healthfile;
    }

    public void setHealthfile(FormFile healthfile) {
        this.healthfile = healthfile;
    }

    public int getCandidatehealthId() {
        return candidatehealthId;
    }

    public void setCandidatehealthId(int candidatehealthId) {
        this.candidatehealthId = candidatehealthId;
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

    public String getDoDeletelangdetail() {
        return doDeletelangdetail;
    }

    public void setDoDeletelangdetail(String doDeletelangdetail) {
        this.doDeletelangdetail = doDeletelangdetail;
    }

    public String getDoModifylangdetail() {
        return doModifylangdetail;
    }

    public void setDoModifylangdetail(String doModifylangdetail) {
        this.doModifylangdetail = doModifylangdetail;
    }

    public FormFile getLangfile() {
        return langfile;
    }

    public void setLangfile(FormFile langfile) {
        this.langfile = langfile;
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

    public FormFile getBankfile() {
        return bankfile;
    }

    public void setBankfile(FormFile bankfile) {
        this.bankfile = bankfile;
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

    public String getBankfilehidden() {
        return bankfilehidden;
    }

    public void setBankfilehidden(String bankfilehidden) {
        this.bankfilehidden = bankfilehidden;
    }

    public int getBankdetailId() {
        return bankdetailId;
    }

    public void setBankdetailId(int bankdetailId) {
        this.bankdetailId = bankdetailId;
    }

    public String getDoSaveBankdetail() {
        return doSaveBankdetail;
    }

    public void setDoSaveBankdetail(String doSaveBankdetail) {
        this.doSaveBankdetail = doSaveBankdetail;
    }

    public String getDoManageBankdetail() {
        return doManageBankdetail;
    }

    public void setDoManageBankdetail(String doManageBankdetail) {
        this.doManageBankdetail = doManageBankdetail;
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

    public int getMaritalstatusId() {
        return maritalstatusId;
    }

    public void setMaritalstatusId(int maritalstatusId) {
        this.maritalstatusId = maritalstatusId;
    }

    public Collection getMaritalstatuses() {
        return maritalstatuses;
    }

    public void setMaritalstatuses(Collection maritalstatuses) {
        this.maritalstatuses = maritalstatuses;
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public Collection getRelations() {
        return relations;
    }

    public void setRelations(Collection relations) {
        this.relations = relations;
    }

    public String getNextofkin() {
        return nextofkin;
    }

    public void setNextofkin(String nextofkin) {
        this.nextofkin = nextofkin;
    }

    public int getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(int nationalityId) {
        this.nationalityId = nationalityId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getContactno2() {
        return contactno2;
    }

    public void setContactno2(String contactno2) {
        this.contactno2 = contactno2;
    }

    public String getContactno1() {
        return contactno1;
    }

    public void setContactno1(String contactno1) {
        this.contactno1 = contactno1;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPlaceofbirth() {
        return placeofbirth;
    }

    public void setPlaceofbirth(String placeofbirth) {
        this.placeofbirth = placeofbirth;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public Collection getCoursenames() {
        return coursenames;
    }

    public void setCoursenames(Collection coursenames) {
        this.coursenames = coursenames;
    }

    public int getOnlineFlag() {
        return onlineFlag;
    }

    public void setOnlineFlag(int onlineFlag) {
        this.onlineFlag = onlineFlag;
    }

    /**
     * @return the statustype
     */
    public int getStatustype() {
        return statustype;
    }

    /**
     * @param statustype the statustype to set
     */
    public void setStatustype(int statustype) {
        this.statustype = statustype;
    }

    /**
     * @return the assettypeIdIndex
     */
    public int getAssettypeIdIndex() {
        return assettypeIdIndex;
    }

    /**
     * @param assettypeIdIndex the assettypeIdIndex to set
     */
    public void setAssettypeIdIndex(int assettypeIdIndex) {
        this.assettypeIdIndex = assettypeIdIndex;
    }

    /**
     * @return the positionIdIndex
     */
    public int getPositionIdIndex() {
        return positionIdIndex;
    }

    /**
     * @param positionIdIndex the positionIdIndex to set
     */
    public void setPositionIdIndex(int positionIdIndex) {
        this.positionIdIndex = positionIdIndex;
    }

    /**
     * @return the courseIndex
     */
    public int getCourseIndex() {
        return courseIndex;
    }

    /**
     * @param courseIndex the courseIndex to set
     */
    public void setCourseIndex(int courseIndex) {
        this.courseIndex = courseIndex;
    }

    /**
     * @return the candidateIdHeader
     */
    public int getCandidateIdHeader() {
        return candidateIdHeader;
    }

    /**
     * @param candidateIdHeader the candidateIdHeader to set
     */
    public void setCandidateIdHeader(int candidateIdHeader) {
        this.candidateIdHeader = candidateIdHeader;
    }

    /**
     * @return the doViewHeader
     */
    public String getDoViewHeader() {
        return doViewHeader;
    }

    /**
     * @param doViewHeader the doViewHeader to set
     */
    public void setDoViewHeader(String doViewHeader) {
        this.doViewHeader = doViewHeader;
    }

    /**
     * @return the doDeleteHealthFile
     */
    public String getDoDeleteHealthFile() {
        return doDeleteHealthFile;
    }

    /**
     * @param doDeleteHealthFile the doDeleteHealthFile to set
     */
    public void setDoDeleteHealthFile(String doDeleteHealthFile) {
        this.doDeleteHealthFile = doDeleteHealthFile;
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
     * @return the religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * @param religion the religion to set
     */
    public void setReligion(String religion) {
        this.religion = religion;
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
     * @return the nomineeName
     */
    public String getNomineeName() {
        return nomineeName;
    }

    /**
     * @param nomineeName the nomineeName to set
     */
    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    /**
     * @return the nomineeRelation
     */
    public String getNomineeRelation() {
        return nomineeRelation;
    }

    /**
     * @param nomineeRelation the nomineeRelation to set
     */
    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    /**
     * @return the nomineeContactno
     */
    public String getNomineeContactno() {
        return nomineeContactno;
    }

    /**
     * @param nomineeContactno the nomineeContactno to set
     */
    public void setNomineeContactno(String nomineeContactno) {
        this.nomineeContactno = nomineeContactno;
    }

    /**
     * @return the nomineedetailId
     */
    public int getNomineedetailId() {
        return nomineedetailId;
    }

    /**
     * @param nomineedetailId the nomineedetailId to set
     */
    public void setNomineedetailId(int nomineedetailId) {
        this.nomineedetailId = nomineedetailId;
    }

    /**
     * @return the doManageNomineedetail
     */
    public String getDoManageNomineedetail() {
        return doManageNomineedetail;
    }

    /**
     * @param doManageNomineedetail the doManageNomineedetail to set
     */
    public void setDoManageNomineedetail(String doManageNomineedetail) {
        this.doManageNomineedetail = doManageNomineedetail;
    }

    /**
     * @return the doSaveNomineedetail
     */
    public String getDoSaveNomineedetail() {
        return doSaveNomineedetail;
    }

    /**
     * @param doSaveNomineedetail the doSaveNomineedetail to set
     */
    public void setDoSaveNomineedetail(String doSaveNomineedetail) {
        this.doSaveNomineedetail = doSaveNomineedetail;
    }

    /**
     * @return the doDeleteNomineedetail
     */
    public String getDoDeleteNomineedetail() {
        return doDeleteNomineedetail;
    }

    /**
     * @param doDeleteNomineedetail the doDeleteNomineedetail to set
     */
    public void setDoDeleteNomineedetail(String doDeleteNomineedetail) {
        this.doDeleteNomineedetail = doDeleteNomineedetail;
    }

    /**
     * @return the doViewNomineelist
     */
    public String getDoViewNomineelist() {
        return doViewNomineelist;
    }

    /**
     * @param doViewNomineelist the doViewNomineelist to set
     */
    public void setDoViewNomineelist(String doViewNomineelist) {
        this.doViewNomineelist = doViewNomineelist;
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
     * @return the stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @param stateName the stateName to set
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
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
}
