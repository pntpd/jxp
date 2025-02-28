package com.web.jxp.clientselection;

public class ClientselectionInfo {

    private int jobpostId;
    private int ddlValue;
    private int ddltype;
    private int status;

    private int positionId;
    private int gradeId;
    private int clientId;
    private int clientassetId;
    private int opening;
    private int selcount;
    private int shortlistcount;
    private int shortlistId;
    private int candidateId;
    private int sflag;
    private int substatus;
    private int maillogId;
    private double expMin;
    private double expMax;

    private String resumefilename;
    private String date;
    private String name;
    private String photo;
    private String ddlLabel;
    private String position;
    private String clientName;
    private String clientAsset;
    private String ccStatus;
    private String education;
    private String gradeName;
    private String photoName;
    private String positionValue;
    private String mobdate;
    private String filename;
    private String degree;
    private String qualification;

    private String userName;
    private String clientname;
    private String clientmailId;
    private String comailId;
    private String re_email;
    private String ma_email;
    private String pdffileName;
    private String candidateName;
    private String candidatemail;
    private String company;
    private String bccmailId;
    private String subject;
    private String mailfileName;

    private String mailby;
    private String mailon;
    private String srby;
    private String sron;
    private String country;
    private String sendby;

    private String srBy;
    private String srDate;
    private String reason;
    private String remark;
    private String offerpdffile;

    private double experience;
    private String fromDate;
    private String toDate;
    private String coordinator;
    private int progressId;

    //for ddl
    public ClientselectionInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public ClientselectionInfo(int ddlValue, String ddlLabel, int ddltype) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
        this.ddltype = ddltype;
    }

    public ClientselectionInfo(String positionValue, String ddlLabel) {
        this.positionValue = positionValue;
        this.ddlLabel = ddlLabel;
    }

    //for index
    public ClientselectionInfo(int jobpostId, String date, String clientName, String clientAsset, String positionName, String gradeName,
            String mobdate, int opening, int selcount, String ccStatus, int status, int clientId) {
        this.jobpostId = jobpostId;
        this.date = date;
        this.position = positionName;
        this.clientAsset = clientAsset;
        this.clientName = clientName;
        this.mobdate = mobdate;
        this.opening = opening;
        this.selcount = selcount;
        this.ccStatus = ccStatus;
        this.status = status;
        this.clientId = clientId;
    }

    public ClientselectionInfo(int jobpostId, String clientname, String assetname, String positionname, String grade, String education, String poston,
            double experiencemin, double experiencemax, int noofopening, int status, int selcount, int shortlistcount, int clientId) {
        this.jobpostId = jobpostId;
        this.clientName = clientname;
        this.clientAsset = assetname;
        this.position = positionname;
        this.gradeName = grade;
        this.education = education;
        this.date = poston;
        this.expMin = experiencemin;
        this.expMax = experiencemax;
        this.opening = noofopening;
        this.status = status;
        this.selcount = selcount;
        this.shortlistcount = shortlistcount;
        this.clientId = clientId;
    }

    public ClientselectionInfo(int shortlistId, int candidateId, String clientname, String clientmailId, String comailId, String pdffileName, String candidateName,
            int jobpostId, String position) {
        this.shortlistId = shortlistId;
        this.candidateId = candidateId;
        this.clientname = clientname;
        this.clientmailId = clientmailId;
        this.comailId = comailId;
        this.pdffileName = pdffileName;
        this.candidateName = candidateName;
        this.jobpostId = jobpostId;
        this.position = position;
    }

    public ClientselectionInfo(int candidateId, String name, String position, String grade, String degree, String qualification, String company, double exp,
            int shortlistId, int status, int sflag, String photo, String mailby, String mailon, String srby, String sron, String country, String pdffilename,
            int jobpostId, int substatus, String offerpdffile, int progressId) {
        this.candidateId = candidateId;
        this.name = name;
        this.position = position;
        this.gradeName = grade;
        this.degree = degree;
        this.qualification = qualification;
        this.company = company;
        this.experience = exp;
        this.shortlistId = shortlistId;
        this.status = status;
        this.sflag = sflag;
        this.photo = photo;
        this.mailby = mailby;
        this.mailon = mailon;
        this.srby = srby;
        this.sron = sron;
        this.country = country;
        this.pdffileName = pdffilename;
        this.jobpostId = jobpostId;
        this.substatus = substatus;
        this.offerpdffile = offerpdffile;
        this.progressId = progressId;
    }

    
    public ClientselectionInfo(int shortlistId, int candidateId, String clientname, String clientmailId,
            String comailId, String pdffileName, String candidateName, int jobpostId, String position, 
            String bccmailId, String subject, String mailfileName, String sendby, String date,
            String candidatemail, String fromDate, String toDate, int clientId, int clientassetId, String r_email, String ma_email) {
        this.shortlistId = shortlistId;
        this.candidateId = candidateId;
        this.clientname = clientname;
        this.clientmailId = clientmailId;
        this.comailId = comailId;
        this.pdffileName = pdffileName;
        this.candidateName = candidateName;
        this.jobpostId = jobpostId;
        this.position = position;
        this.bccmailId = bccmailId;
        this.subject = subject;
        this.mailfileName = mailfileName;
        this.sendby = sendby;
        this.date = date;
        this.candidatemail = candidatemail;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.re_email = r_email;
        this.ma_email = ma_email;
    }

    public ClientselectionInfo(String srBy, String srDate, String reason, String remark) {
        this.srBy = srBy;
        this.srDate = srDate;
        this.reason = reason;
        this.remark = remark;
    }

    public ClientselectionInfo(int maillogId, String mailon, String mailby, String pdffile) {
        this.maillogId = maillogId;
        this.mailon = mailon;
        this.mailby = mailby;
        this.pdffileName = pdffile;
    }

    public ClientselectionInfo(String candidatename, int candidateId, String pdffilename, String position) {
        this.candidateName = candidatename;
        this.candidateId = candidateId;
        this.pdffileName = pdffilename;
        this.position = position;
    }

    public int getJobpostId() {
        return jobpostId;
    }

    public int getDdlValue() {
        return ddlValue;
    }

    public int getStatus() {
        return status;
    }

    public int getPositionId() {
        return positionId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getClientassetId() {
        return clientassetId;
    }

    public int getOpening() {
        return opening;
    }

    public int getSelcount() {
        return selcount;
    }

    public double getExpMin() {
        return expMin;
    }

    public double getExpMax() {
        return expMax;
    }

    public String getResumefilename() {
        return resumefilename;
    }

    public String getDate() {
        return date;
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

    public String getClientAsset() {
        return clientAsset;
    }

    public String getCcStatus() {
        return ccStatus;
    }

    public String getEducation() {
        return education;
    }

    public String getGradeName() {
        return gradeName;
    }

    public String getPhotoName() {
        return photoName;
    }

    public String getPositionValue() {
        return positionValue;
    }

    public String getMobdate() {
        return mobdate;
    }

    public String getFilename() {
        return filename;
    }

    public String getUserName() {
        return userName;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the shortlistcount
     */
    public int getShortlistcount() {
        return shortlistcount;
    }

    public int getShortlistId() {
        return shortlistId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public String getClientname() {
        return clientname;
    }

    public String getClientmailId() {
        return clientmailId;
    }

    public String getComailId() {
        return comailId;
    }

    public String getPdffileName() {
        return pdffileName;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public String getDegree() {
        return degree;
    }

    public String getQualification() {
        return qualification;
    }

    public int getSflag() {
        return sflag;
    }

    public String getCompany() {
        return company;
    }

    public double getExperience() {
        return experience;
    }

    public String getMailby() {
        return mailby;
    }

    public String getMailon() {
        return mailon;
    }

    public String getSrby() {
        return srby;
    }

    public String getSron() {
        return sron;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    public String getBccmailId() {
        return bccmailId;
    }

    public String getSubject() {
        return subject;
    }

    public String getMailfileName() {
        return mailfileName;
    }

    /**
     * @return the sendby
     */
    public String getSendby() {
        return sendby;
    }

    /**
     * @return the srBy
     */
    public String getSrBy() {
        return srBy;
    }

    /**
     * @return the srDate
     */
    public String getSrDate() {
        return srDate;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @return the substatus
     */
    public int getSubstatus() {
        return substatus;
    }

    /**
     * @return the candidatemail
     */
    public String getCandidatemail() {
        return candidatemail;
    }

    /**
     * @return the maillogId
     */
    public int getMaillogId() {
        return maillogId;
    }

    /**
     * @return the offerpdffile
     */
    public String getOfferpdffile() {
        return offerpdffile;
    }

    /**
     * @return the ddltype
     */
    public int getDdltype() {
        return ddltype;
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
     * @return the coordinator
     */
    public String getCoordinator() {
        return coordinator;
    }

    /**
     * @return the re_email
     */
    public String getRe_email() {
        return re_email;
    }

    /**
     * @return the ma_email
     */
    public String getMa_email() {
        return ma_email;
    }

    /**
     * @return the progressId
     */
    public int getProgressId() {
        return progressId;
    }

}
