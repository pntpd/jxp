package com.web.jxp.candidateregistration;

import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

public class CandidateRegistrationForm extends ActionForm {

    //Actions
    private int candidateId;
    private String doModify;
    private String doSave;
    private String doSaveDraft;
    private String fname;
    private String localFile;
    private String tempfname;
    private String templocalFile;
    private String currentDate;
    private int expCount;
    private int certCount;

    //Collections
    private Collection positions;
    private Collection clients;
    private Collection states;
    private Collection states2;
    private Collection countries;
    private Collection assets;
    private Collection assettypes;
    private Collection maritalstatuses;
    private Collection departments;
    private Collection currencies;
    private Collection relations;
    private Collection languages;
    private Collection qualificationtypes;
    private Collection degrees;
    private Collection coursenames;
    private Collection proficiencies;
    private Collection approvedbys;

    //Onshore
    private int onshore;

    //Resume
    private FormFile adhaarfile;
    private FormFile panfile;
    private String hdnadhaarfile;
    private String hdnpanfile;

    //Personal Information
    private String firstname;
    private String middlename;
    private String lastname;
    private String dob;
    private int age;

    private String placeofbirth;
    private int passport;
    private String adhaar;
    private String gender;
    private int maritalstatusId;

    private int nationalityId;
    private String religion;
    private String pancard;

    // Contact Information
    private String emailId;
    private String contactno1;
    private String code1Id;

    // Address
    private String address1line1;
    private String address1line2;
    private String address1line3;
    private String pinCode;
    private int countryId;
    private int stateId;
    private int cityId;
    private String cityName;

    private int sameAsPermanent;
    private String address2line1;
    private String address2line2;
    private String address2line3;
    private String pinCode2;
    private int countryId2;
    private int stateId2;
    private int cityId2;
    private String cityName2;

    //Position Applied For
    private int assettypeId;
    private int positionId;
    private int ecurrencyId;
    private int expectedsalary;
    private int lcurrencyId;
    private int lastdrawnsalary;

    //Education
    private int kindId;
    private int degreeId;

    //Work Experience
    private int isFresher;
    private int experiencedetailId;
    private int pastOCSemp;
    private String companyname;
    private String assetname;
    private int expassettypeId;
    private String workstartdate;
    private String workenddate;
    private int currentworkingstatus;
    private int exppositionId;

    //Language
    private int languageId1;
    private int languageId2;
    private int languageId3;
    private int languageId4;
    private int languageProf1[];
//    private int languageProf2[];
//    private int languageProf3[];
//    private int languageProf4[];

    //Offshore 
    private int coursenameId;
    private String dateofissue;
    private String dateofexpiry;
    private int isexpiry;
    private FormFile certificatefile;
    private int approvedbyId;

    //Login otp
    private String cap;

    public String getDoModify() {
        return doModify;
    }

    public void setDoModify(String doModify) {
        this.doModify = doModify;
    }

    public String getLocalFile() {
        return localFile;
    }

    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }

    public String getDoSave() {
        return doSave;
    }

    public void setDoSave(String doSave) {
        this.doSave = doSave;
    }

    public Collection getPositions() {
        return positions;
    }

    public void setPositions(Collection positions) {
        this.positions = positions;
    }

    public Collection getClients() {
        return clients;
    }

    public void setClients(Collection clients) {
        this.clients = clients;
    }

    public Collection getStates() {
        return states;
    }

    public void setStates(Collection states) {
        this.states = states;
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

    public Collection getAssettypes() {
        return assettypes;
    }

    public void setAssettypes(Collection assettypes) {
        this.assettypes = assettypes;
    }

    public Collection getMaritalstatuses() {
        return maritalstatuses;
    }

    public void setMaritalstatuses(Collection maritalstatuses) {
        this.maritalstatuses = maritalstatuses;
    }

    public Collection getDepartments() {
        return departments;
    }

    public void setDepartments(Collection departments) {
        this.departments = departments;
    }

    public Collection getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Collection currencies) {
        this.currencies = currencies;
    }

    public Collection getRelations() {
        return relations;
    }

    public void setRelations(Collection relations) {
        this.relations = relations;
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

    public int getOnshore() {
        return onshore;
    }

    public void setOnshore(int onshore) {
        this.onshore = onshore;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPlaceofbirth() {
        return placeofbirth;
    }

    public void setPlaceofbirth(String placeofbirth) {
        this.placeofbirth = placeofbirth;
    }

    public int getPassport() {
        return passport;
    }

    public void setPassport(int passport) {
        this.passport = passport;
    }

    public String getAdhaar() {
        return adhaar;
    }

    public void setAdhaar(String adhaar) {
        this.adhaar = adhaar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getMaritalstatusId() {
        return maritalstatusId;
    }

    public void setMaritalstatusId(int maritalstatusId) {
        this.maritalstatusId = maritalstatusId;
    }

    public int getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(int nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }
//
//    public int getYearofExp() {
//        return yearofExp;
//    }
//
//    public void setYearofExp(int yearofExp) {
//        this.yearofExp = yearofExp;
//    }
//
//    public int getYearofOffshoreExp() {
//        return yearofOffshoreExp;
//    }
//
//    public void setYearofOffshoreExp(int yearofOffshoreExp) {
//        this.yearofOffshoreExp = yearofOffshoreExp;
//    }
//
//    public int getYearofExpPosition() {
//        return yearofExpPosition;
//    }
//
//    public void setYearofExpPosition(int yearofExpPosition) {
//        this.yearofExpPosition = yearofExpPosition;
//    }

    public String getPancard() {
        return pancard;
    }

    public void setPancard(String pancard) {
        this.pancard = pancard;
    }

    public FormFile getPanfile() {
        return panfile;
    }

    public void setPanfile(FormFile panfile) {
        this.panfile = panfile;
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

    public String getCode1Id() {
        return code1Id;
    }

    public void setCode1Id(String code1Id) {
        this.code1Id = code1Id;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
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

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public ActionServlet getServlet() {
        return servlet;
    }

    public void setServlet(ActionServlet servlet) {
        this.servlet = servlet;
    }

    public MultipartRequestHandler getMultipartRequestHandler() {
        return multipartRequestHandler;
    }

    public void setMultipartRequestHandler(MultipartRequestHandler multipartRequestHandler) {
        this.multipartRequestHandler = multipartRequestHandler;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getAssettypeId() {
        return assettypeId;
    }

    public void setAssettypeId(int assettypeId) {
        this.assettypeId = assettypeId;
    }

    public int getEcurrencyId() {
        return ecurrencyId;
    }

    public void setEcurrencyId(int ecurrencyId) {
        this.ecurrencyId = ecurrencyId;
    }

    public int getExpectedsalary() {
        return expectedsalary;
    }

    public void setExpectedsalary(int expectedsalary) {
        this.expectedsalary = expectedsalary;
    }

    public int getLcurrencyId() {
        return lcurrencyId;
    }

    public void setLcurrencyId(int lcurrencyId) {
        this.lcurrencyId = lcurrencyId;
    }

    public int getLastdrawnsalary() {
        return lastdrawnsalary;
    }

    public void setLastdrawnsalary(int lastdrawnsalary) {
        this.lastdrawnsalary = lastdrawnsalary;
    }

    public int getKindId() {
        return kindId;
    }

    public void setKindId(int kindId) {
        this.kindId = kindId;
    }

    public int getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(int degreeId) {
        this.degreeId = degreeId;
    }

    public int getExperiencedetailId() {
        return experiencedetailId;
    }

    public void setExperiencedetailId(int experiencedetailId) {
        this.experiencedetailId = experiencedetailId;
    }

    public int getPastOCSemp() {
        return pastOCSemp;
    }

    public void setPastOCSemp(int pastOCSemp) {
        this.pastOCSemp = pastOCSemp;
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

    public int getExpassettypeId() {
        return expassettypeId;
    }

    public void setExpassettypeId(int expassettypeId) {
        this.expassettypeId = expassettypeId;
    }

    public int getExppositionId() {
        return exppositionId;
    }

    public void setExppositionId(int exppositionId) {
        this.exppositionId = exppositionId;
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

    public int getCurrentworkingstatus() {
        return currentworkingstatus;
    }

    public void setCurrentworkingstatus(int currentworkingstatus) {
        this.currentworkingstatus = currentworkingstatus;
    }

    public int getLanguageId1() {
        return languageId1;
    }

    public void setLanguageId1(int languageId1) {
        this.languageId1 = languageId1;
    }

    public int getLanguageId2() {
        return languageId2;
    }

    public void setLanguageId2(int languageId2) {
        this.languageId2 = languageId2;
    }

    public int getLanguageId3() {
        return languageId3;
    }

    public void setLanguageId3(int languageId3) {
        this.languageId3 = languageId3;
    }

    public int getLanguageId4() {
        return languageId4;
    }

    public void setLanguageId4(int languageId4) {
        this.languageId4 = languageId4;
    }

    public int[] getLanguageProf1() {
        return languageProf1;
    }

    public void setLanguageProf1(int[] languageProf1) {
        this.languageProf1 = languageProf1;
    }
//
//    public int[] getLanguageProf2() {
//        return languageProf2;
//    }
//
//    public void setLanguageProf2(int[] languageProf2) {
//        this.languageProf2 = languageProf2;
//    }
//
//    public int[] getLanguageProf3() {
//        return languageProf3;
//    }
//
//    public void setLanguageProf3(int[] languageProf3) {
//        this.languageProf3 = languageProf3;
//    }
//
//    public int[] getLanguageProf4() {
//        return languageProf4;
//    }
//
//    public void setLanguageProf4(int[] languageProf4) {
//        this.languageProf4 = languageProf4;
//    }

    public int getCoursenameId() {
        return coursenameId;
    }

    public void setCoursenameId(int coursenameId) {
        this.coursenameId = coursenameId;
    }

    public String getDateofissue() {
        return dateofissue;
    }

    public void setDateofissue(String dateofissue) {
        this.dateofissue = dateofissue;
    }

    public String getDateofexpiry() {
        return dateofexpiry;
    }

    public void setDateofexpiry(String dateofexpiry) {
        this.dateofexpiry = dateofexpiry;
    }

    public int getIsexpiry() {
        return isexpiry;
    }

    public void setIsexpiry(int isexpiry) {
        this.isexpiry = isexpiry;
    }

    public FormFile getCertificatefile() {
        return certificatefile;
    }

    public void setCertificatefile(FormFile certificatefile) {
        this.certificatefile = certificatefile;
    }

    public Collection getQualificationtypes() {
        return qualificationtypes;
    }

    public void setQualificationtypes(Collection qualificationtypes) {
        this.qualificationtypes = qualificationtypes;
    }

    public Collection getDegrees() {
        return degrees;
    }

    public void setDegrees(Collection degrees) {
        this.degrees = degrees;
    }

    public int getIsFresher() {
        return isFresher;
    }

    public void setIsFresher(int isFresher) {
        this.isFresher = isFresher;
    }

    public Collection getCoursenames() {
        return coursenames;
    }

    public void setCoursenames(Collection coursenames) {
        this.coursenames = coursenames;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public FormFile getAdhaarfile() {
        return adhaarfile;
    }

    public void setAdhaarfile(FormFile adhaarfile) {
        this.adhaarfile = adhaarfile;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public Collection getApprovedbys() {
        return approvedbys;
    }

    public void setApprovedbys(Collection approvedbys) {
        this.approvedbys = approvedbys;
    }

    public int getApprovedbyId() {
        return approvedbyId;
    }

    public void setApprovedbyId(int approvedbyId) {
        this.approvedbyId = approvedbyId;
    }

    public String getPinCode2() {
        return pinCode2;
    }

    public void setPinCode2(String pinCode2) {
        this.pinCode2 = pinCode2;
    }

    public int getCountryId2() {
        return countryId2;
    }

    public void setCountryId2(int countryId2) {
        this.countryId2 = countryId2;
    }

    public int getStateId2() {
        return stateId2;
    }

    public void setStateId2(int stateId2) {
        this.stateId2 = stateId2;
    }

    public int getCityId2() {
        return cityId2;
    }

    public void setCityId2(int cityId2) {
        this.cityId2 = cityId2;
    }

    public String getCityName2() {
        return cityName2;
    }

    public void setCityName2(String cityName2) {
        this.cityName2 = cityName2;
    }

    public String getTempfname() {
        return tempfname;
    }

    public void setTempfname(String tempfname) {
        this.tempfname = tempfname;
    }

    public String getTemplocalFile() {
        return templocalFile;
    }

    public void setTemplocalFile(String templocalFile) {
        this.templocalFile = templocalFile;
    }

    public int getSameAsPermanent() {
        return sameAsPermanent;
    }

    public void setSameAsPermanent(int sameAsPermanent) {
        this.sameAsPermanent = sameAsPermanent;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public Collection getStates2() {
        return states2;
    }

    public void setStates2(Collection states2) {
        this.states2 = states2;
    }

    public String getHdnadhaarfile() {
        return hdnadhaarfile;
    }

    public void setHdnadhaarfile(String hdnadhaarfile) {
        this.hdnadhaarfile = hdnadhaarfile;
    }

    public String getHdnpanfile() {
        return hdnpanfile;
    }

    public void setHdnpanfile(String hdnpanfile) {
        this.hdnpanfile = hdnpanfile;
    }

    public String getDoSaveDraft() {
        return doSaveDraft;
    }

    public void setDoSaveDraft(String doSaveDraft) {
        this.doSaveDraft = doSaveDraft;
    }

    public int getExpCount() {
        return expCount;
    }

    public void setExpCount(int expCount) {
        this.expCount = expCount;
    }

    public int getCertCount() {
        return certCount;
    }

    public void setCertCount(int certCount) {
        this.certCount = certCount;
    }

}
