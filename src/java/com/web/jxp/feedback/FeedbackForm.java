package com.web.jxp.feedback;

import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class FeedbackForm extends ActionForm {

    private int surveyId;
    private int status;
    private int compstatus;
    private String search;
    private String search1;    
    private String compsearch;
    private String doCancel;
    private String name;
    private String doSave;
    private String doView;
    private String doViewProfile;
    private String doGetFeedbackList;
    private String fromDate;
    private String toDate;
    private String fromDate1;
    private String toDate1;
    private int crewrotationId;
    private int srno;
    private int type;
    private int questionId[];
    private int experiencedetailId;
    private int certificationdetaillId;
    private String answer[];
    private String doLogout;

    private String doViewlangdetail;
    private String doViewhealthdetail;
    private String doViewvaccinationlist;
    private String doViewtrainingcertlist;
    private String doVieweducationlist;
    private String doViewexperiencelist;
    private String doViewgovdocumentlist;
    private Collection bloodpressures;
    private String doViewexperience;
    private String doViewcertification;

//    For Comepetency
    private int trackerId;
    private int appealId;
    private int allwork;
    private int trackerDltsId[];
    private String appealremarks;
    private String feedbackremark;
    private String doCompetencyList;
    private String doSendAppeal;
    private String doOnlineAssessment;
    private String doSaveAssessment;
    private String doSubmitAssessment;
    private String doSaveFeedback;
    private String doCancelCompetency;
    private String doViewtopic;
    private String documentList;
    private String docSearch;
    private String tcsearch;
    private String trainingList;
    private String doAddGovDoc;
    private String domodifygovdocumentdetail;
    private int govdocumentId;
    private int candidateId;
    private Collection documentTypes;
    private String documentNo;
    private String dateofissue;
    private FormFile documentfile;
    private String documenthiddenfile;
    private int documentissuedbyId;
    private Collection documentissuedbys;
    private String currentDate;
    private Collection countries;
    private String fname;
    private int documentTypeId;
    private String cityName;
    private int placeofapplicationId;
    private int countryId;
    private String dateofexpiry;
    private String doSavegovdocumentdetail;
    
    //For adding traing file
    private String trainingcerthiddenfile;
    private String doaddtrainingcertdetail;
    private String doSavetrainingcertdetail;
    private Collection approvedbys;
    private Collection coursetypes;
    private Collection coursenames;
    private int trainingandcertId;
    private int coursetypeId;
    private int locationofInstituteId;
    private int coursenameId;
    private String educationInstitute;
    private String certificationno;
    private String courseverification;
    private FormFile trainingcertfile;
    
    //For contract listing
    private String viewContractList;
    private int contractdetailId;
    private int contractId;
    private int crewClientId;
    private int crewAssetId;
    private Collection clients;
    private Collection assets;
    private int refId;
    private int fileId;
    private String contractApprove;
    private String remark;
    private String searchContract;
    private FormFile contractfile;
    private FormFile offerFile;
    private int expiryId;
    private String search2;
    private String searchDoc;
    private String fromDateMob;
    private String toDateMob;
    
    private String doViewBanklist;
    private String viewInterviewList;
    private String doSaveAvailability;
    private String doAddAvailability;
    private int interviewId;
    
    private String viewClientOfferwList;
    private String doSaveOffer;
    private String doSendmail;
    private int shortlistId;
    private int assetId;
    private int clientId;
    
    private String fromval;
    private String toval;
    private String ccval;
    private String bccval;
    private String subject;
    private String description;
    /**
     * @return the surveyId
     */
    public int getSurveyId() {
        return surveyId;
    }

    /**
     * @param surveyId the surveyId to set
     */
    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the search
     */
    public String getSearch() {
        return search;
    }

    /**
     * @param search the search to set
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * @return the doCancel
     */
    public String getDoCancel() {
        return doCancel;
    }

    /**
     * @param doCancel the doCancel to set
     */
    public void setDoCancel(String doCancel) {
        this.doCancel = doCancel;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the doSave
     */
    public String getDoSave() {
        return doSave;
    }

    /**
     * @param doSave the doSave to set
     */
    public void setDoSave(String doSave) {
        this.doSave = doSave;
    }

    /**
     * @return the doView
     */
    public String getDoView() {
        return doView;
    }

    /**
     * @param doView the doView to set
     */
    public void setDoView(String doView) {
        this.doView = doView;
    }

    /**
     * @return the crewrotationId
     */
    public int getCrewrotationId() {
        return crewrotationId;
    }

    /**
     * @param crewrotationId the crewrotationId to set
     */
    public void setCrewrotationId(int crewrotationId) {
        this.crewrotationId = crewrotationId;
    }

    /**
     * @return the fromDate
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the doGetFeedbackList
     */
    public String getDoGetFeedbackList() {
        return doGetFeedbackList;
    }

    /**
     * @param doGetFeedbackList the doGetFeedbackList to set
     */
    public void setDoGetFeedbackList(String doGetFeedbackList) {
        this.doGetFeedbackList = doGetFeedbackList;
    }

    /**
     * @return the srno
     */
    public int getSrno() {
        return srno;
    }

    /**
     * @param srno the srno to set
     */
    public void setSrno(int srno) {
        this.srno = srno;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the questionId
     */
    /**
     * @return the answer
     */
    public String[] getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(String[] answer) {
        this.answer = answer;
    }

    /**
     * @return the questionId
     */
    public int[] getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId the questionId to set
     */
    public void setQuestionId(int[] questionId) {
        this.questionId = questionId;
    }

    /**
     * @return the doLogout
     */
    public String getDoLogout() {
        return doLogout;
    }

    /**
     * @param doLogout the doLogout to set
     */
    public void setDoLogout(String doLogout) {
        this.doLogout = doLogout;
    }

    /**
     * @return the doViewProfile
     */
    public String getDoViewProfile() {
        return doViewProfile;
    }

    /**
     * @param doViewProfile the doViewProfile to set
     */
    public void setDoViewProfile(String doViewProfile) {
        this.doViewProfile = doViewProfile;
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

    public String getDoViewvaccinationlist() {
        return doViewvaccinationlist;
    }

    public void setDoViewvaccinationlist(String doViewvaccinationlist) {
        this.doViewvaccinationlist = doViewvaccinationlist;
    }

    public String getDoViewtrainingcertlist() {
        return doViewtrainingcertlist;
    }

    public void setDoViewtrainingcertlist(String doViewtrainingcertlist) {
        this.doViewtrainingcertlist = doViewtrainingcertlist;
    }

    public String getDoVieweducationlist() {
        return doVieweducationlist;
    }

    public void setDoVieweducationlist(String doVieweducationlist) {
        this.doVieweducationlist = doVieweducationlist;
    }

    public String getDoViewexperiencelist() {
        return doViewexperiencelist;
    }

    public void setDoViewexperiencelist(String doViewexperiencelist) {
        this.doViewexperiencelist = doViewexperiencelist;
    }

    public String getDoViewBanklist() {
        return doViewBanklist;
    }

    public void setDoViewBanklist(String doViewBanklist) {
        this.doViewBanklist = doViewBanklist;
    }

    public String getDoViewgovdocumentlist() {
        return doViewgovdocumentlist;
    }

    public void setDoViewgovdocumentlist(String doViewgovdocumentlist) {
        this.doViewgovdocumentlist = doViewgovdocumentlist;
    }

    public Collection getBloodpressures() {
        return bloodpressures;
    }

    public void setBloodpressures(Collection bloodpressures) {
        this.bloodpressures = bloodpressures;
    }

    public String getDoViewexperience() {
        return doViewexperience;
    }

    public void setDoViewexperience(String doViewexperience) {
        this.doViewexperience = doViewexperience;
    }

    public String getDoViewcertification() {
        return doViewcertification;
    }

    public void setDoViewcertification(String doViewcertification) {
        this.doViewcertification = doViewcertification;
    }

    public int getExperiencedetailId() {
        return experiencedetailId;
    }

    public void setExperiencedetailId(int experiencedetailId) {
        this.experiencedetailId = experiencedetailId;
    }

    public int getCertificationdetaillId() {
        return certificationdetaillId;
    }

    public void setCertificationdetaillId(int certificationdetaillId) {
        this.certificationdetaillId = certificationdetaillId;
    }

    public String getDoCompetencyList() {
        return doCompetencyList;
    }

    public void setDoCompetencyList(String doCompetencyList) {
        this.doCompetencyList = doCompetencyList;
    }

    public int getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(int trackerId) {
        this.trackerId = trackerId;
    }

    public String getDoSendAppeal() {
        return doSendAppeal;
    }

    public void setDoSendAppeal(String doSendAppeal) {
        this.doSendAppeal = doSendAppeal;
    }

    public int getAppealId() {
        return appealId;
    }

    public void setAppealId(int appealId) {
        this.appealId = appealId;
    }

    public String getAppealremarks() {
        return appealremarks;
    }

    public void setAppealremarks(String appealremarks) {
        this.appealremarks = appealremarks;
    }

    public String getDoOnlineAssessment() {
        return doOnlineAssessment;
    }

    public void setDoOnlineAssessment(String doOnlineAssessment) {
        this.doOnlineAssessment = doOnlineAssessment;
    }

    public String getDoSaveAssessment() {
        return doSaveAssessment;
    }

    public void setDoSaveAssessment(String doSaveAssessment) {
        this.doSaveAssessment = doSaveAssessment;
    }

    public int[] getTrackerDltsId() {
        return trackerDltsId;
    }

    public void setTrackerDltsId(int[] trackerDltsId) {
        this.trackerDltsId = trackerDltsId;
    }

    public int getAllwork() {
        return allwork;
    }

    public void setAllwork(int allwork) {
        this.allwork = allwork;
    }

    public String getDoSubmitAssessment() {
        return doSubmitAssessment;
    }

    public void setDoSubmitAssessment(String doSubmitAssessment) {
        this.doSubmitAssessment = doSubmitAssessment;
    }

    public String getDoSaveFeedback() {
        return doSaveFeedback;
    }

    public void setDoSaveFeedback(String doSaveFeedback) {
        this.doSaveFeedback = doSaveFeedback;
    }

    public String getFeedbackremark() {
        return feedbackremark;
    }

    public void setFeedbackremark(String feedbackremark) {
        this.feedbackremark = feedbackremark;
    }

    public int getCompstatus() {
        return compstatus;
    }

    public void setCompstatus(int compstatus) {
        this.compstatus = compstatus;
    }

    public String getCompsearch() {
        return compsearch;
    }

    public void setCompsearch(String compsearch) {
        this.compsearch = compsearch;
    }

    public String getDoCancelCompetency() {
        return doCancelCompetency;
    }

    public void setDoCancelCompetency(String doCancelCompetency) {
        this.doCancelCompetency = doCancelCompetency;
    }

    public String getFromDate1() {
        return fromDate1;
    }

    public void setFromDate1(String fromDate1) {
        this.fromDate1 = fromDate1;
    }

    public String getToDate1() {
        return toDate1;
    }

    public void setToDate1(String toDate1) {
        this.toDate1 = toDate1;
    }

    /**
     * @return the doViewtopic
     */
    public String getDoViewtopic() {
        return doViewtopic;
    }

    /**
     * @param doViewtopic the doViewtopic to set
     */
    public void setDoViewtopic(String doViewtopic) {
        this.doViewtopic = doViewtopic;
    }

    /**
     * @return the documentList
     */
    public String getDocumentList() {
        return documentList;
    }

    /**
     * @param documentList the documentList to set
     */
    public void setDocumentList(String documentList) {
        this.documentList = documentList;
    }

    /**
     * @return the docSearch
     */
    public String getDocSearch() {
        return docSearch;
    }

    /**
     * @param docSearch the docSearch to set
     */
    public void setDocSearch(String docSearch) {
        this.docSearch = docSearch;
    }

    /**
     * @return the trainingList
     */
    public String getTrainingList() {
        return trainingList;
    }

    /**
     * @param trainingList the trainingList to set
     */
    public void setTrainingList(String trainingList) {
        this.trainingList = trainingList;
    }

    /**
     * @return the tcsearch
     */
    public String getTcsearch() {
        return tcsearch;
    }

    /**
     * @param tcsearch the tcsearch to set
     */
    public void setTcsearch(String tcsearch) {
        this.tcsearch = tcsearch;
    }

    /**
     * @return the doAddGovDoc
     */
    public String getDoAddGovDoc() {
        return doAddGovDoc;
    }

    /**
     * @param doAddGovDoc the doAddGovDoc to set
     */
    public void setDoAddGovDoc(String doAddGovDoc) {
        this.doAddGovDoc = doAddGovDoc;
    }

    /**
     * @return the govdocumentId
     */
    public int getGovdocumentId() {
        return govdocumentId;
    }

    /**
     * @param govdocumentId the govdocumentId to set
     */
    public void setGovdocumentId(int govdocumentId) {
        this.govdocumentId = govdocumentId;
    }

    /**
     * @return the candidateId
     */
    public int getCandidateId() {
        return candidateId;
    }

    /**
     * @param candidateId the candidateId to set
     */
    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    /**
     * @return the documentTypes
     */
    public Collection getDocumentTypes() {
        return documentTypes;
    }

    /**
     * @param documentTypes the documentTypes to set
     */
    public void setDocumentTypes(Collection documentTypes) {
        this.documentTypes = documentTypes;
    }

    /**
     * @return the documentNo
     */
    public String getDocumentNo() {
        return documentNo;
    }

    /**
     * @param documentNo the documentNo to set
     */
    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    /**
     * @return the dateofissue
     */
    public String getDateofissue() {
        return dateofissue;
    }

    /**
     * @param dateofissue the dateofissue to set
     */
    public void setDateofissue(String dateofissue) {
        this.dateofissue = dateofissue;
    }

    /**
     * @return the documentfile
     */
    public FormFile getDocumentfile() {
        return documentfile;
    }

    /**
     * @param documentfile the documentfile to set
     */
    public void setDocumentfile(FormFile documentfile) {
        this.documentfile = documentfile;
    }

    /**
     * @return the documenthiddenfile
     */
    public String getDocumenthiddenfile() {
        return documenthiddenfile;
    }

    /**
     * @param documenthiddenfile the documenthiddenfile to set
     */
    public void setDocumenthiddenfile(String documenthiddenfile) {
        this.documenthiddenfile = documenthiddenfile;
    }

    /**
     * @return the documentissuedbyId
     */
    public int getDocumentissuedbyId() {
        return documentissuedbyId;
    }

    /**
     * @param documentissuedbyId the documentissuedbyId to set
     */
    public void setDocumentissuedbyId(int documentissuedbyId) {
        this.documentissuedbyId = documentissuedbyId;
    }

    /**
     * @return the documentissuedbys
     */
    public Collection getDocumentissuedbys() {
        return documentissuedbys;
    }

    /**
     * @param documentissuedbys the documentissuedbys to set
     */
    public void setDocumentissuedbys(Collection documentissuedbys) {
        this.documentissuedbys = documentissuedbys;
    }

    /**
     * @return the domodifygovdocumentdetail
     */
    public String getDomodifygovdocumentdetail() {
        return domodifygovdocumentdetail;
    }

    /**
     * @param domodifygovdocumentdetail the domodifygovdocumentdetail to set
     */
    public void setDomodifygovdocumentdetail(String domodifygovdocumentdetail) {
        this.domodifygovdocumentdetail = domodifygovdocumentdetail;
    }

    /**
     * @return the currentDate
     */
    public String getCurrentDate() {
        return currentDate;
    }

    /**
     * @param currentDate the currentDate to set
     */
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    /**
     * @return the countries
     */
    public Collection getCountries() {
        return countries;
    }

    /**
     * @param countries the countries to set
     */
    public void setCountries(Collection countries) {
        this.countries = countries;
    }

    /**
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return the documentTypeId
     */
    public int getDocumentTypeId() {
        return documentTypeId;
    }

    /**
     * @param documentTypeId the documentTypeId to set
     */
    public void setDocumentTypeId(int documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    /**
     * @return the cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return the placeofapplicationId
     */
    public int getPlaceofapplicationId() {
        return placeofapplicationId;
    }

    /**
     * @param placeofapplicationId the placeofapplicationId to set
     */
    public void setPlaceofapplicationId(int placeofapplicationId) {
        this.placeofapplicationId = placeofapplicationId;
    }

    /**
     * @return the dateofexpiry
     */
    public String getDateofexpiry() {
        return dateofexpiry;
    }

    /**
     * @param dateofexpiry the dateofexpiry to set
     */
    public void setDateofexpiry(String dateofexpiry) {
        this.dateofexpiry = dateofexpiry;
    }

    /**
     * @return the countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the doSavegovdocumentdetail
     */
    public String getDoSavegovdocumentdetail() {
        return doSavegovdocumentdetail;
    }

    /**
     * @param doSavegovdocumentdetail the doSavegovdocumentdetail to set
     */
    public void setDoSavegovdocumentdetail(String doSavegovdocumentdetail) {
        this.doSavegovdocumentdetail = doSavegovdocumentdetail;
    }

    /**
     * @return the trainingcerthiddenfile
     */
    public String getTrainingcerthiddenfile() {
        return trainingcerthiddenfile;
    }

    /**
     * @param trainingcerthiddenfile the trainingcerthiddenfile to set
     */
    public void setTrainingcerthiddenfile(String trainingcerthiddenfile) {
        this.trainingcerthiddenfile = trainingcerthiddenfile;
    }

    /**
     * @return the doaddtrainingcertdetail
     */
    public String getDoaddtrainingcertdetail() {
        return doaddtrainingcertdetail;
    }

    /**
     * @param doaddtrainingcertdetail the doaddtrainingcertdetail to set
     */
    public void setDoaddtrainingcertdetail(String doaddtrainingcertdetail) {
        this.doaddtrainingcertdetail = doaddtrainingcertdetail;
    }

    /**
     * @return the doSavetrainingcertdetail
     */
    public String getDoSavetrainingcertdetail() {
        return doSavetrainingcertdetail;
    }

    /**
     * @param doSavetrainingcertdetail the doSavetrainingcertdetail to set
     */
    public void setDoSavetrainingcertdetail(String doSavetrainingcertdetail) {
        this.doSavetrainingcertdetail = doSavetrainingcertdetail;
    }

    /**
     * @return the approvedbys
     */
    public Collection getApprovedbys() {
        return approvedbys;
    }

    /**
     * @param approvedbys the approvedbys to set
     */
    public void setApprovedbys(Collection approvedbys) {
        this.approvedbys = approvedbys;
    }

    /**
     * @return the coursetypes
     */
    public Collection getCoursetypes() {
        return coursetypes;
    }

    /**
     * @param coursetypes the coursetypes to set
     */
    public void setCoursetypes(Collection coursetypes) {
        this.coursetypes = coursetypes;
    }

    /**
     * @return the coursenames
     */
    public Collection getCoursenames() {
        return coursenames;
    }

    /**
     * @param coursenames the coursenames to set
     */
    public void setCoursenames(Collection coursenames) {
        this.coursenames = coursenames;
    }

    /**
     * @return the trainingandcertId
     */
    public int getTrainingandcertId() {
        return trainingandcertId;
    }

    /**
     * @param trainingandcertId the trainingandcertId to set
     */
    public void setTrainingandcertId(int trainingandcertId) {
        this.trainingandcertId = trainingandcertId;
    }

    /**
     * @return the coursetypeId
     */
    public int getCoursetypeId() {
        return coursetypeId;
    }

    /**
     * @param coursetypeId the coursetypeId to set
     */
    public void setCoursetypeId(int coursetypeId) {
        this.coursetypeId = coursetypeId;
    }

    /**
     * @return the locationofInstituteId
     */
    public int getLocationofInstituteId() {
        return locationofInstituteId;
    }

    /**
     * @param locationofInstituteId the locationofInstituteId to set
     */
    public void setLocationofInstituteId(int locationofInstituteId) {
        this.locationofInstituteId = locationofInstituteId;
    }

    /**
     * @return the coursenameId
     */
    public int getCoursenameId() {
        return coursenameId;
    }

    /**
     * @param coursenameId the coursenameId to set
     */
    public void setCoursenameId(int coursenameId) {
        this.coursenameId = coursenameId;
    }

    /**
     * @return the educationInstitute
     */
    public String getEducationInstitute() {
        return educationInstitute;
    }

    /**
     * @param educationInstitute the educationInstitute to set
     */
    public void setEducationInstitute(String educationInstitute) {
        this.educationInstitute = educationInstitute;
    }

    /**
     * @return the certificationno
     */
    public String getCertificationno() {
        return certificationno;
    }

    /**
     * @param certificationno the certificationno to set
     */
    public void setCertificationno(String certificationno) {
        this.certificationno = certificationno;
    }

    /**
     * @return the courseverification
     */
    public String getCourseverification() {
        return courseverification;
    }

    /**
     * @param courseverification the courseverification to set
     */
    public void setCourseverification(String courseverification) {
        this.courseverification = courseverification;
    }

    /**
     * @return the trainingcertfile
     */
    public FormFile getTrainingcertfile() {
        return trainingcertfile;
    }

    /**
     * @param trainingcertfile the trainingcertfile to set
     */
    public void setTrainingcertfile(FormFile trainingcertfile) {
        this.trainingcertfile = trainingcertfile;
    }

    /**
     * @return the viewContractList
     */
    public String getViewContractList() {
        return viewContractList;
    }

    /**
     * @param viewContractList the viewContractList to set
     */
    public void setViewContractList(String viewContractList) {
        this.viewContractList = viewContractList;
    }

    /**
     * @return the contractdetailId
     */
    public int getContractdetailId() {
        return contractdetailId;
    }

    /**
     * @param contractdetailId the contractdetailId to set
     */
    public void setContractdetailId(int contractdetailId) {
        this.contractdetailId = contractdetailId;
    }

    /**
     * @return the contractId
     */
    public int getContractId() {
        return contractId;
    }

    /**
     * @param contractId the contractId to set
     */
    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    /**
     * @return the refId
     */
    public int getRefId() {
        return refId;
    }

    /**
     * @param refId the refId to set
     */
    public void setRefId(int refId) {
        this.refId = refId;
    }

    /**
     * @return the fileId
     */
    public int getFileId() {
        return fileId;
    }

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    /**
     * @return the contractApprove
     */
    public String getContractApprove() {
        return contractApprove;
    }

    /**
     * @param contractApprove the contractApprove to set
     */
    public void setContractApprove(String contractApprove) {
        this.contractApprove = contractApprove;
    }

    /**
     * @return the contractfile
     */
    public FormFile getContractfile() {
        return contractfile;
    }

    /**
     * @param contractfile the contractfile to set
     */
    public void setContractfile(FormFile contractfile) {
        this.contractfile = contractfile;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the crewClientId
     */
    public int getCrewClientId() {
        return crewClientId;
    }

    /**
     * @param crewClientId the crewClientId to set
     */
    public void setCrewClientId(int crewClientId) {
        this.crewClientId = crewClientId;
    }

    /**
     * @return the crewAssetId
     */
    public int getCrewAssetId() {
        return crewAssetId;
    }

    /**
     * @param crewAssetId the crewAssetId to set
     */
    public void setCrewAssetId(int crewAssetId) {
        this.crewAssetId = crewAssetId;
    }

    /**
     * @return the clients
     */
    public Collection getClients() {
        return clients;
    }

    /**
     * @param clients the clients to set
     */
    public void setClients(Collection clients) {
        this.clients = clients;
    }

    /**
     * @return the assets
     */
    public Collection getAssets() {
        return assets;
    }

    /**
     * @param assets the assets to set
     */
    public void setAssets(Collection assets) {
        this.assets = assets;
    }
    /**
     * @return the searchContract
     */
    public String getSearchContract() {
        return searchContract;
    }

    /**
     * @param searchContract the searchContract to set
     */
    public void setSearchContract(String searchContract) {
        this.searchContract = searchContract;
    }

    /**
     * @return the search1
     */
    public String getSearch1() {
        return search1;
    }

    /**
     * @param search1 the search1 to set
     */
    public void setSearch1(String search1) {
        this.search1 = search1;
    }

    /**
     * @return the expiryId
     */
    public int getExpiryId() {
        return expiryId;
    }

    /**
     * @param expiryId the expiryId to set
     */
    public void setExpiryId(int expiryId) {
        this.expiryId = expiryId;
    }

    /**
     * @return the search2
     */
    public String getSearch2() {
        return search2;
    }

    /**
     * @param search2 the search2 to set
     */
    public void setSearch2(String search2) {
        this.search2 = search2;
    }

    /**
     * @return the searchDoc
     */
    public String getSearchDoc() {
        return searchDoc;
    }

    /**
     * @param searchDoc the searchDoc to set
     */
    public void setSearchDoc(String searchDoc) {
        this.searchDoc = searchDoc;
    }

    /**
     * @return the fromDateMob
     */
    public String getFromDateMob() {
        return fromDateMob;
    }

    /**
     * @param fromDateMob the fromDateMob to set
     */
    public void setFromDateMob(String fromDateMob) {
        this.fromDateMob = fromDateMob;
    }

    /**
     * @return the toDateMob
     */
    public String getToDateMob() {
        return toDateMob;
    }

    /**
     * @param toDateMob the toDateMob to set
     */
    public void setToDateMob(String toDateMob) {
        this.toDateMob = toDateMob;
    }

    /**
     * @return the viewInterviewList
     */
    public String getViewInterviewList() {
        return viewInterviewList;
    }

    /**
     * @param viewInterviewList the viewInterviewList to set
     */
    public void setViewInterviewList(String viewInterviewList) {
        this.viewInterviewList = viewInterviewList;
    }

    /**
     * @return the doSaveAvailability
     */
    public String getDoSaveAvailability() {
        return doSaveAvailability;
    }

    /**
     * @param doSaveAvailability the doSaveAvailability to set
     */
    public void setDoSaveAvailability(String doSaveAvailability) {
        this.doSaveAvailability = doSaveAvailability;
    }

    /**
     * @return the interviewId
     */
    public int getInterviewId() {
        return interviewId;
    }

    /**
     * @param interviewId the interviewId to set
     */
    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    /**
     * @return the viewClientOfferwList
     */
    public String getViewClientOfferwList() {
        return viewClientOfferwList;
    }

    /**
     * @param viewClientOfferwList the viewClientOfferwList to set
     */
    public void setViewClientOfferwList(String viewClientOfferwList) {
        this.viewClientOfferwList = viewClientOfferwList;
    }

    /**
     * @return the doSaveOffer
     */
    public String getDoSaveOffer() {
        return doSaveOffer;
    }

    /**
     * @param doSaveOffer the doSaveOffer to set
     */
    public void setDoSaveOffer(String doSaveOffer) {
        this.doSaveOffer = doSaveOffer;
    }

    /**
     * @return the shortlistId
     */
    public int getShortlistId() {
        return shortlistId;
    }

    /**
     * @param shortlistId the shortlistId to set
     */
    public void setShortlistId(int shortlistId) {
        this.shortlistId = shortlistId;
    }
    
    /**
     * @return the offerFile
     */
    public FormFile getOfferFile() {
        return offerFile;
    }

    /**
     * @param offerFile the offerFile to set
     */
    public void setOfferFile(FormFile offerFile) {
        this.offerFile = offerFile;
    }

    /**
     * @return the doSendmail
     */
    public String getDoSendmail() {
        return doSendmail;
    }

    /**
     * @param doSendmail the doSendmail to set
     */
    public void setDoSendmail(String doSendmail) {
        this.doSendmail = doSendmail;
    }

    /**
     * @return the fromval
     */
    public String getFromval() {
        return fromval;
    }

    /**
     * @param fromval the fromval to set
     */
    public void setFromval(String fromval) {
        this.fromval = fromval;
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
     * @return the assetId
     */
    public int getAssetId() {
        return assetId;
    }

    /**
     * @param assetId the assetId to set
     */
    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    /**
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * @return the doAddAvailability
     */
    public String getDoAddAvailability() {
        return doAddAvailability;
    }

    /**
     * @param doAddAvailability the doAddAvailability to set
     */
    public void setDoAddAvailability(String doAddAvailability) {
        this.doAddAvailability = doAddAvailability;
    }
}
