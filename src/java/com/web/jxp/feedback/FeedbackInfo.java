package com.web.jxp.feedback;

public class FeedbackInfo {

    private int feedbackId;
    private int crewrotationId;
    private int surveyId;
    private int status;
    private int ddlValue;
    private int questioncount;
    private int clientassetId;
    private int questionId;
    private int subcategorywfId;
    private int positionId;
    private int srno;
    private int type;
    private int expired;
    private String ddlLabel;
    private String submissionDate;
    private String feedback;
    private String doGetFeedbackList;
    private String filleddate;
    private String question;
    private String answer;

//    For Competency 
    private int trackerId;
    private int fcroleId;
    private int onlineflag;
    private int trackerdtlsId;
    private String role;
    private String category;
    private String completeByDate;
    private String onlineDate;
    private String desc;
    private String clientasset;
    private String helpno;
    private String helpemail;
    private String feedbackremarks;
    private String filename;
    private String displayname;
    private String clientName;
    private String assetName;
    private String name;
    private String coursename;
    private int trainingId;
    
    //for contract
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
    private String acceptanceDate;
    private String date3;
    private String ccaddress;
    private String username1;
    private String username2;
    private String username3;
    private String positionname;
    private String gradename;
    private String statusValue;
    private String updateValue;
    private String contract;
    private int userId;
    private int flag4;
    
    private int govdocumentId;
    private int documenttypeId;
    private int placeofissueId;
    private int issuedbyId;
    private String documentname;
    private String documentno;
    private String placeofissue;
    private String issuedby;
    private String govdocumentfile;
    private String dateofissue;
    private String dateofexpiry;
    private String countryName;
    private int passflag;
    private int filecount;
    
    //for interview tab
    private int interviewId;
    private int managerId;
    private int iflag;
    private int avflag;
    private String username;
    private String remarks;
    private String date;
    private String mode;
    private int candidateId;
    private String email;
    
    private int shortlistId;
    private int oflag;
    
    private String co_email;
    private String re_email;
    private int clientId;
    
    
    //for ddl
    public FeedbackInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public FeedbackInfo(String question, String answer, int type, int questionId) {
        this.question = question;
        this.answer = answer;
        this.type = type;
        this.questionId = questionId;
    }
    
    //For DocumentsList
    public FeedbackInfo(String name, String filename)
    {
        this.name = name;
        this.filename = filename;
    }
    
    //For Training cousre
    public FeedbackInfo( String coursename, String filename, int trainingId)
    {
        this.coursename = coursename;
        this.filename = filename;
        this.trainingId = trainingId;
    }
    
    //For topic files
    public FeedbackInfo(int type, String filename, String displayname)
    {
        this.type = type;
        this.filename = filename;
        this.displayname = displayname;
    }
    
    public FeedbackInfo(int interviewId, int managerId, String name, String email)
    {
        this.interviewId = interviewId;
        this.managerId = managerId;
        this.name = name;
        this.email = email;
    }

    //index page
    public FeedbackInfo(int surveyId, String feedback, String submissionDate, int status, int questionCount) {
        this.surveyId = surveyId;
        this.feedback = feedback;
        this.submissionDate = submissionDate;
        this.status = status;
        this.questioncount = questionCount;
    }

    //for detail
    public FeedbackInfo(int surveyId, int clientassetId, int status, String submissionDate, String filleddate,
            String feedback, int subcategorywfId, int expired) {
        this.surveyId = surveyId;
        this.clientassetId = clientassetId;
        this.status = status;
        this.submissionDate = submissionDate;
        this.filleddate = filleddate;
        this.feedback = feedback;
        this.subcategorywfId = subcategorywfId;
        this.expired = expired;
    }

    public FeedbackInfo(int surveyId, int clientassetId, int status, String submissionDate, String filleddate,
            int subcategorywfId) {
        this.surveyId = surveyId;
        this.clientassetId = clientassetId;
        this.status = status;
        this.submissionDate = submissionDate;
        this.filleddate = filleddate;
        this.subcategorywfId = subcategorywfId;
    }

    public FeedbackInfo(int trackerId, String role, String completeByDate, String onlineDate, int onlineflag, int status) {
        this.trackerId = trackerId;
        this.role = role;
        this.completeByDate = completeByDate;
        this.onlineDate = onlineDate;
        this.onlineflag = onlineflag;
        this.status = status;
    }

    public FeedbackInfo(int TrackId, int status, int onlineflag, String role, String desc, String clientasset, String helpno, String helpemail, String onlinedate,
            int fcroleId, String completeByDate, String feedbackremarks, String filename, String clientName) {
        this.trackerId = TrackId;
        this.status = status;
        this.onlineflag = onlineflag;
        this.role = role;
        this.desc = desc;
        this.clientasset = clientasset;
        this.helpno = helpno;
        this.helpemail = helpemail;
        this.onlineDate = onlinedate;
        this.fcroleId = fcroleId;
        this.completeByDate = completeByDate;
        this.feedbackremarks = feedbackremarks;
        this.filename = filename;
        this.clientName = clientName;
    }

    public FeedbackInfo(int trackerdtlsId, int questionId, String question, String category, String answer) {
        this.trackerdtlsId = trackerdtlsId;
        this.questionId = questionId;
        this.question = question;
        this.category = category;
        this.answer = answer;
    }
    
    //For contract listing
    public FeedbackInfo(int contractdetailId, String clientName, String assetName, 
            String positionName, String gradeName, int type,
            String fromDate, String toDate, String file1, String file2, String file3, 
            int status, int approva1, int approva2, String stval, int userId, int contractId, 
            String acceptanceDate, String contract)
    {
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
        this.acceptanceDate = acceptanceDate;
        this.contract = contract;
    }
    
    public FeedbackInfo(int contractId, String file1, String file2, String file3, String date1, 
            String date2, String date3, String username1, String username2, String username3, 
            String contract, int approval, int flag4, int approval2,String updateValue )
    {
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
        this.contract = contract;
        this.approval1 = approval;
        this.flag4 = flag4;
        this.approval2 = approval2;
        this.updateValue= updateValue;
    }
        
    public FeedbackInfo(int govdocumentId, String documentname, String documentno, String placeofissue, String issuedby, int status,
    String dateofissue, String dateofexpiry, String countryname, int pass, int filecount) 
    {
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

    //For interview list
    public FeedbackInfo(int interviewId, String username, String remarks, String mode, 
            String date, int iflag, int avflag, int type)
    {
        this.interviewId = interviewId;
        this.username = username;
        this.remarks = remarks;
        this.mode = mode;
        this.date = date;
        this.iflag = iflag;
        this.avflag = avflag;
        this.type = type;
    }    
    
    public FeedbackInfo(String name, int candidateId, String position, String email, String clientname, String assetname, String ccval)
    {
        this.name = name;
        this.candidateId = candidateId;
        this.positionname = position;
        this.email = email;
        this.clientName = clientname;
        this.assetName = assetname;
        this.ccaddress = ccval;
    }
    
    //For offerlist
    public FeedbackInfo(int candidateId, int shortlistId, String filename, String username, String date, int userId, int oflag, String position)
    {
        this.candidateId = candidateId;
        this.shortlistId = shortlistId;
        this.filename = filename;
        this.username = username;
        this.date = date;
        this.userId = userId;
        this.oflag = oflag;
        this.positionname = position;
    }
    
    //Mail modal
    public FeedbackInfo(int shortlistId, String username, String date, String remarks, String position, String co_email, String re_email, int clientId, int assetId)
    {
        this.shortlistId = shortlistId;
        this.username = username;
        this.date = date;
        this.remarks = remarks;
        this.positionname = position;
        this.co_email = co_email;
        this.re_email = re_email;
        this.clientId = clientId;
        this.clientassetId = assetId;
    }
    /**
     * @return the feedbackId
     */
    public int getFeedbackId() {
        return feedbackId;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
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
     * @return the surveyId
     */
    public int getSurveyId() {
        return surveyId;
    }

    /**
     * @return the submissionDate
     */
    public String getSubmissionDate() {
        return submissionDate;
    }

    /**
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * @return the doGetFeedbackList
     */
    public String getDoGetFeedbackList() {
        return doGetFeedbackList;
    }

    /**
     * @return the questioncount
     */
    public int getQuestioncount() {
        return questioncount;
    }

    /**
     * @return the clientassetId
     */
    public int getClientassetId() {
        return clientassetId;
    }

    /**
     * @return the filleddate
     */
    public String getFilleddate() {
        return filleddate;
    }

    /**
     * @return the subcategorywfId
     */
    public int getSubcategorywfId() {
        return subcategorywfId;
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
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
    }

    /**
     * @return the srno
     */
    public int getSrno() {
        return srno;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the crewrotationId
     */
    public int getCrewrotationId() {
        return crewrotationId;
    }

    /**
     * @return the questionId
     */
    public int getQuestionId() {
        return questionId;
    }

    /**
     * @return the expired
     */
    public int getExpired() {
        return expired;
    }

    public int getTrackerId() {
        return trackerId;
    }

    public int getOnlineflag() {
        return onlineflag;
    }

    public String getRole() {
        return role;
    }

    public String getCompleteByDate() {
        return completeByDate;
    }

    public String getOnlineDate() {
        return onlineDate;
    }

    public String getDesc() {
        return desc;
    }

    public String getClientasset() {
        return clientasset;
    }

    public String getHelpno() {
        return helpno;
    }

    public String getHelpemail() {
        return helpemail;
    }

    public int getFcroleId() {
        return fcroleId;
    }

    public String getCategory() {
        return category;
    }

    public int getTrackerdtlsId() {
        return trackerdtlsId;
    }

    public String getFeedbackremarks() {
        return feedbackremarks;
    }

    public String getFilename() {
        return filename;
    }

    /**
     * @return the displayname
     */
    public String getDisplayname() {
        return displayname;
    }

    /**
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the trainingId
     */
    public int getTrainingId() {
        return trainingId;
    }

    /**
     * @param trainingId the trainingId to set
     */
    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }

    /**
     * @return the coursename
     */
    public String getCoursename() {
        return coursename;
    }

    /**
     * @return the contractdetailId
     */
    public int getContractdetailId() {
        return contractdetailId;
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
     * @return the ccaddress
     */
    public String getCcaddress() {
        return ccaddress;
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

    /**
     * @return the positionname
     */
    public String getPositionname() {
        return positionname;
    }

    /**
     * @return the gradename
     */
    public String getGradename() {
        return gradename;
    }

    /**
     * @return the statusValue
     */
    public String getStatusValue() {
        return statusValue;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the assetName
     */
    public String getAssetName() {
        return assetName;
    }

    /**
     * @return the contract
     */
    public String getContract() {
        return contract;
    }

    /**
     * @return the acceptanceDate
     */
    public String getAcceptanceDate() {
        return acceptanceDate;
    }

    /**
     * @return the updateValue
     */
    public String getUpdateValue() {
        return updateValue;
    }

    /**
     * @param updateValue the updateValue to set
     */
    public void setUpdateValue(String updateValue) {
        this.updateValue = updateValue;
    }

    /**
     * @return the flag4
     */
    public int getFlag4() {
        return flag4;
    }

    /**
     * @return the govdocumentId
     */
    public int getGovdocumentId() {
        return govdocumentId;
    }

    /**
     * @return the documenttypeId
     */
    public int getDocumenttypeId() {
        return documenttypeId;
    }

    /**
     * @return the placeofissueId
     */
    public int getPlaceofissueId() {
        return placeofissueId;
    }

    /**
     * @return the issuedbyId
     */
    public int getIssuedbyId() {
        return issuedbyId;
    }

    /**
     * @return the documentname
     */
    public String getDocumentname() {
        return documentname;
    }

    /**
     * @return the documentno
     */
    public String getDocumentno() {
        return documentno;
    }

    /**
     * @return the placeofissue
     */
    public String getPlaceofissue() {
        return placeofissue;
    }

    /**
     * @return the issuedby
     */
    public String getIssuedby() {
        return issuedby;
    }

    /**
     * @return the govdocumentfile
     */
    public String getGovdocumentfile() {
        return govdocumentfile;
    }

    /**
     * @return the dateofissue
     */
    public String getDateofissue() {
        return dateofissue;
    }

    /**
     * @return the dateofexpiry
     */
    public String getDateofexpiry() {
        return dateofexpiry;
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @return the passflag
     */
    public int getPassflag() {
        return passflag;
    }

    /**
     * @return the filecount
     */
    public int getFilecount() {
        return filecount;
    }

    /**
     * @return the interviewId
     */
    public int getInterviewId() {
        return interviewId;
    }

    /**
     * @return the iflag
     */
    public int getIflag() {
        return iflag;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @return the candidateId
     */
    public int getCandidateId() {
        return candidateId;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the managerId
     */
    public int getManagerId() {
        return managerId;
    }

    /**
     * @return the avflag
     */
    public int getAvflag() {
        return avflag;
    }

    /**
     * @return the shortlistId
     */
    public int getShortlistId() {
        return shortlistId;
    }

    /**
     * @return the oflag
     */
    public int getOflag() {
        return oflag;
    }

    /**
     * @return the co_email
     */
    public String getCo_email() {
        return co_email;
    }

    /**
     * @return the re_email
     */
    public String getRe_email() {
        return re_email;
    }

    /**
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

}
