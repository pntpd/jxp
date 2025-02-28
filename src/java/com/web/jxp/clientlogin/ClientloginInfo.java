package com.web.jxp.clientlogin;

public class ClientloginInfo 
{
    private int userId;
    private int ddlValue;
    private String ddlLabel;
    private int ddltype;
    private int clientId;
    private int clientassetId;
    private int status;
    private String clientasset;
    private String positionname;
    private int positionId;
    private String clientName;
    private String assetName;
    private String name;
    private String username;
    private String date;
    private String date2;
    private String stVal;
    private int jobpostId;
    private int opening;
    private int selcount;
    
    private String education;
    private String gradeName;
    private double expMin;
    private double expMax;
    private int shortlistcount;
    private int candidateId;
    private int assetId;
    private int interviewerId;
    private String degree;
    private String qualification;
    private String company;
    private double experience;
    private int shortlistId;
    private int sflag;
    private String mailby;
    private String mailon;
    private String interviewer;
    private String srby;
    private String sron;
    private String country;
    private String photo;
    private String pdffileName;
    private int substatus;
    private int ismanager;
    private String offerpdffile;
    private String email;
    private String int_email;
    private String r_email;
    private String subject;
    private String bccmailId;
    private String clientmailId;
    private String comailId;
    
    private String linkloc;
    private String mode;
    private int tz1;
    private int tz2;
    private int duration;    
    private int iflag;    
    private int avflag;    
    private int evflag;    
    private String datec;
    private int interviewId;
    private int type;
    private int type2;
    private String time;
    private String remarks;
    private String remarks2;
    
    private String ma_email;
    private String co_email;
    private String re_email;

    //for ddl
    public ClientloginInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    
    public ClientloginInfo(int ddlValue, String ddlLabel, int ddltype) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
        this.ddltype = ddltype;
    }
    
    //check login
    public ClientloginInfo(int userId, String name, String email, int ismanager)
    {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.ismanager = ismanager;
    }

    //for index
    public ClientloginInfo(int jobpostId, String date, String clientName, String positionName, String gradeName,
            int opening, int selcount, String stVal, int status, int clientId) {
        this.jobpostId = jobpostId;
        this.date = date;
        this.positionname = positionName;
        this.clientName = clientName;
        this.opening = opening;
        this.selcount = selcount;
        this.stVal = stVal;
        this.status = status;
        this.clientId = clientId;
    }    
    
    public ClientloginInfo(int jobpostId, String clientname, String assetname, String positionname, String grade, String education, String poston,
            double experiencemin, double experiencemax, int noofopening, int status, int selcount, int shortlistcount, int clientId) {
        this.jobpostId = jobpostId;
        this.clientName = clientname;
        this.assetName = assetname;
        this.positionname = positionname;
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
    
    //getShortlistedCandidateListByIDs
    public ClientloginInfo(int candidateId, String name, String position, String grade, String degree, String qualification, String company, double exp,
            int shortlistId, int status, int sflag, String photo, String interviewer, String int_date, String srby, String sron, String country, String pdffilename,
            int jobpostId, int substatus, String offerpdffile, int iflag, int interviewId, int type, int type2, int avflag, int evflag) {
        this.candidateId = candidateId;
        this.name = name;
        this.positionname = position;
        this.gradeName = grade;
        this.degree = degree;
        this.qualification = qualification;
        this.company = company;
        this.experience = exp;
        this.shortlistId = shortlistId;
        this.status = status;
        this.sflag = sflag;
        this.photo = photo;
        this.interviewer = interviewer;
        this.date = int_date;
        this.sron = sron;
        this.country = country;
        this.pdffileName = pdffilename;
        this.jobpostId = jobpostId;
        this.substatus = substatus;
        this.offerpdffile = offerpdffile;
        this.iflag = iflag;
        this.interviewId = interviewId;
        this.type = type;
        this.type2 = type2;
        this.avflag = avflag;
        this.evflag = evflag;
    }    
    
    public ClientloginInfo(int shortlistId, int candidateId, String clientname, String clientmailId, String comailId, 
            String pdffileName,String candidateName, int jobpostId, String position,  String date, String candidatemail, int clientId, int assetId,
            String m_mail, int positionId, String rmail) {
        this.shortlistId = shortlistId;
        this.candidateId = candidateId;
        this.clientName = clientname;
        this.comailId = comailId;
        this.pdffileName = pdffileName;
        this.name = candidateName;
        this.jobpostId = jobpostId;
        this.positionname = position; 
        this.date = date;
        this.email = candidatemail;//candidate
        this.clientId = clientId;
        this.assetId = assetId;
        this.int_email =  m_mail;//manager
        this.r_email =  rmail;//recuter
        this.positionId = positionId;
    }
    
    //Create Interview
    public ClientloginInfo(int jobpostId, int candidateId, int assetId, int interviewerId, String linkloc, 
            String mode, int tz1, int tz2, int duration, String date, String datec, int shortlistId, 
            int uId, String username, int positionId, int clientId)
    {
        this.jobpostId = jobpostId;
        this.candidateId = candidateId;
        this.assetId = assetId;
        this.interviewerId = interviewerId;
        this.linkloc = linkloc;
        this.mode = mode;
        this.tz1 = tz1;
        this.tz2 = tz2;
        this.duration = duration;
        this.date = date;
        this.datec = datec;
        this.shortlistId = shortlistId;
        this.userId = uId;
        this.username= username;
        this.positionId = positionId;
        this.clientId = clientId;
    }
    //edit interview
    public ClientloginInfo(int candidateId, int shortlistId, int managerId, String linkloc, String mode, int tz1,
            int tz2, int duration, String date, String time, String datec, int clientassetId, int positionId)
    {
        this.candidateId = candidateId;
        this.shortlistId = shortlistId;
        this.interviewerId = managerId;
        this.linkloc = linkloc;
        this.mode = mode;
        this.tz1 = tz1;
        this.tz2 = tz2;
        this.duration = duration;
        this.date = date;
        this.time = time;
        this.datec = datec;
        this.clientassetId = clientassetId;
        this.positionId = positionId;
    }
    
    //getInterviewDetailList for scheduled interview details
    public ClientloginInfo(int interviewId, String username, String link, String mode, String date,
            int candidateId, String remark1, String remark2, String date2)
    {
        this.interviewId = interviewId;
        this.username = username;
        this.linkloc = link;
        this.mode = mode;
        this.date = date;
        this.candidateId = candidateId;
        this.remarks = remark1;
        this.remarks2 = remark2;
        this.date2 = date2;
    }
    
    //Offer email detail
    public ClientloginInfo(int shortlistId, int candidateId, String clientname, String clientmailId, 
            String comailId, String offerpdffile, String candidateName, int jobpostId, String position,
            String date, String candidatemail)
    {
        this.shortlistId = shortlistId;
        this.candidateId = candidateId;
        this.clientName = clientname;
        this.clientmailId = clientmailId;
        this.comailId = comailId;
        this.name = candidateName;
        this.jobpostId = jobpostId;
        this.positionname = position;
        this.offerpdffile = offerpdffile;
        this.date = date;
        this.email = candidatemail;
    }
    
    //For interview invite mail details
    ClientloginInfo(int interviewId, String ma_email, String name, String candidatemail, String linkLoc, 
            String date, String time, String co_email, String re_email, int jobpostId, String position)
    {
        this.interviewId = interviewId;
        this.ma_email = ma_email;
        this.name = name;
        this.email =  candidatemail;
        this.linkloc = linkLoc;
        this.date = date;
        this.time = time;
        this.co_email = co_email;
        this.re_email = re_email;
        this.jobpostId = jobpostId;
        this.positionname = position;
    }
    
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
     * @return the clientassetId
     */
    public int getClientassetId() {
        return clientassetId;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return the clientasset
     */
    public String getClientasset() {
        return clientasset;
    }

    /**
     * @return the positionname
     */
    public String getPositionname() {
        return positionname;
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @return the jobpostId
     */
    public int getJobpostId() {
        return jobpostId;
    }

    /**
     * @return the opening
     */
    public int getOpening() {
        return opening;
    }

    /**
     * @return the selcount
     */
    public int getSelcount() {
        return selcount;
    }

    /**
     * @return the stVal
     */
    public String getStVal() {
        return stVal;
    }

    /**
     * @return the education
     */
    public String getEducation() {
        return education;
    }

    /**
     * @return the gradeName
     */
    public String getGradeName() {
        return gradeName;
    }

    /**
     * @return the expMin
     */
    public double getExpMin() {
        return expMin;
    }

    /**
     * @return the expMax
     */
    public double getExpMax() {
        return expMax;
    }

    /**
     * @return the shortlistcount
     */
    public int getShortlistcount() {
        return shortlistcount;
    }

    /**
     * @return the candidateId
     */
    public int getCandidateId() {
        return candidateId;
    }

    /**
     * @return the degree
     */
    public String getDegree() {
        return degree;
    }

    /**
     * @return the qualification
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @return the experience
     */
    public double getExperience() {
        return experience;
    }

    /**
     * @return the shortlistId
     */
    public int getShortlistId() {
        return shortlistId;
    }

    /**
     * @return the sflag
     */
    public int getSflag() {
        return sflag;
    }

    /**
     * @return the mailby
     */
    public String getMailby() {
        return mailby;
    }

    /**
     * @return the mailon
     */
    public String getMailon() {
        return mailon;
    }

    /**
     * @return the srby
     */
    public String getSrby() {
        return srby;
    }

    /**
     * @return the sron
     */
    public String getSron() {
        return sron;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @return the pdffileName
     */
    public String getPdffileName() {
        return pdffileName;
    }

    /**
     * @return the substatus
     */
    public int getSubstatus() {
        return substatus;
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
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the ismanager
     */
    public int getIsmanager() {
        return ismanager;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }
    

    /**
     * @return the linkloc
     */
    public String getLinkloc() {
        return linkloc;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @return the tz1
     */
    public int getTz1() {
        return tz1;
    }

    /**
     * @return the tz2
     */
    public int getTz2() {
        return tz2;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @return the datec
     */
    public String getDatec() {
        return datec;
    }

    /**
     * @return the assetId
     */
    public int getAssetId() {
        return assetId;
    }

    /**
     * @return the interviewerId
     */
    public int getInterviewerId() {
        return interviewerId;
    }

    /**
     * @return the iflag
     */
    public int getIflag() {
        return iflag;
    }

    /**
     * @return the interviewId
     */
    public int getInterviewId() {
        return interviewId;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the int_email
     */
    public String getInt_email() {
        return int_email;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @return the bccmailId
     */
    public String getBccmailId() {
        return bccmailId;
    }

    /**
     * @return the clientmailId
     */
    public String getClientmailId() {
        return clientmailId;
    }

    /**
     * @return the comailId
     */
    public String getComailId() {
        return comailId;
    }

    /**
     * @return the r_email
     */
    public String getR_email() {
        return r_email;
    }

    /**
     * @return the ma_email
     */
    public String getMa_email() {
        return ma_email;
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
     * @return the interviewer
     */
    public String getInterviewer() {
        return interviewer;
    }

    /**
     * @return the type2
     */
    public int getType2() {
        return type2;
    }

    /**
     * @return the remarks2
     */
    public String getRemarks2() {
        return remarks2;
    }

    /**
     * @return the date2
     */
    public String getDate2() {
        return date2;
    }

    /**
     * @return the avflag
     */
    public int getAvflag() {
        return avflag;
    }

    /**
     * @return the evflag
     */
    public int getEvflag() {
        return evflag;
    }
}
