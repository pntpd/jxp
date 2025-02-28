package com.web.jxp.onboarding;

public class OnboardingInfo {

    private int jobpostId;
    private int ddlValue;
    private int ddltype;
    private int status;
    private int clientId;
    private int clientassetId;

    private int positionId;
    private int gradeId;
    private int shortlistId;
    private int candidateId;
    private int totalnoofopenings;
    private int positionCount;
    private int onboardCount;
    private int onboardflag;
    private int onflag;
    private int airportid;
    private int depairportid;
    private int arrairportid;
    private int countryId;
    private int chkNotReqTravel;
    private int chkAccommodation;
    private int maillogId;

    private String country;
    private String mobilizedate;
    private String arrivaldate;

    private String name;
    private String photo;
    private String ddlLabel;
    private String position;
    private String clientName;
    private String clientAsset;

    private String gradeName;
    private String photoName;
    private String positionValue;

    // Travel details
    private String depflightname;
    private String depflightno;
    private String depseatno;
    private String depterminalno;
    private String depdate;
    private String deptime;

    private String arrflightname;
    private String arrflightno;
    private String arrseatno;
    private String arrterminalno;
    private String arrdate;
    private String arrtime;
    private String filename;

    private String depairportname;
    private String arrairportname;

    // Accommodation details
    private int accomnotreq;
    private String hotelname;
    private String hoteladdline1;
    private String hoteladdline2;
    private String hoteladdline3;
    private String contactno;
    private String roomno;
    private String checkindate;
    private String checkintime;
    private String checkoutdate;
    private String checkouttime;
    private String bookingno;
    private String hoteldoc;

    // Required Doc details
    private String reqdocId;
    // Doc checklist details
    private String reqchecklistId;
    private String checklistBy;
    private String checklistdate;
    private String checklisttime;

//    -----------------------------------------------------------
    private int onboarddocId;
    private int formalityId;
    private int upflag;

    private String ticketfilename;
    private String hotdocname;
    private String reqdoconboarding;
    private String extfilename;
    private String pdffilename;
    private String formalityname;

    private String clientname;
    private String clientmailId;
    private String comailId;
    private String pdffileName;
    private String candidateName;
    private String bccmailId;
    private String subject;
    private String mailfileName;
    private String sendby;
    private String date;
    private String candidatemail;
    private String zipfilename;
    private String mailextfile;

    private int onboardingdocid;
    private String generateddoc;
    private String uploadeddoc;

//    --------------------------------------- TRAVEL & ACCOMMODATION NEW MODAL VAL -----------------------------------------------------
    private int type;
    private int onboardingdtlsid;
    private int uId;
    private String val1;
    private String val2;
    private String val3;
    private String val4;
    private String val5;
    private String val6;
    private String val7;
    private String val8;
    private String val9;
    private String vald9;
    private String valt9;
    private String val10;
    private String vald10;
    private String valt10;
    private String regdate;
    private String moddate;

    private String onboardingkits;
    private String ontime;
    private String extzipfile;
    private String mailto;
    private String mailfrom;
    private String mailcc;
    private String mailbcc;
    private String attachmentpath;
    private String sentby;
    private String attachmentpath1;
    private String attachmentpath2;
    private String attachmentpath3;
    private int onboardingfileId;
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

    //for ddl
    public OnboardingInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    public OnboardingInfo( String filename, int formalityId)
    {
        this.filename = filename;
        this.formalityId = formalityId;
    }
    public OnboardingInfo(int onboardingfileId, String filename, String name, int type) {
        this.onboardingfileId = onboardingfileId;
        this.filename = filename;
        this.name = name;
        this.type = type;
    }
    public OnboardingInfo(int onboardingfileId, String filename, int formalityId, int clientId, int assetId, String cval1, String cval2, String cval3, String cval4,
            String cval5, String cval6, String cval7, String cval8, String cval9, String cval10)
    {
        this.onboardingfileId = onboardingfileId;
        this.filename = filename;
        this.formalityId = formalityId;
        this.clientId = clientId;
        this.clientassetId = assetId;
        this.val1 = cval1;
        this.val2 = cval2;
        this.val3 = cval3;
        this.val4 = cval4;
        this.val5 = cval5;
        this.val6 = cval6;
        this.val7 = cval7;
        this.val8 = cval8;
        this.val9 = cval9;
        this.val10 = cval10;
    }
    public OnboardingInfo(int ddlValue, String ddlLabel, int ddltype) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
        this.ddltype = ddltype;
    }

    public OnboardingInfo(String positionValue, String ddlLabel) {
        this.positionValue = positionValue;
        this.ddlLabel = ddlLabel;
    }

    public OnboardingInfo(int clientId, String clientName, int clientassetId, String clientAsset, String country, int totalnoofopenings, int onboardCount,
            int countryId) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientassetId = clientassetId;
        this.clientAsset = clientAsset;
        this.country = country;
        this.totalnoofopenings = totalnoofopenings;
        this.onboardCount = onboardCount;
        this.countryId = countryId;
    }

    public OnboardingInfo(int clientId, int clientassetId, String clientName, int positionCount, String clientAsset, int onboardCount, int totalnoofopenings, String country) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientassetId = clientassetId;
        this.clientAsset = clientAsset;
        this.country = country;
        this.positionCount = positionCount;
        this.onboardCount = onboardCount;
        this.totalnoofopenings = totalnoofopenings;

    }

    public OnboardingInfo(int candidateId, String name, String position, String photo, String mobilizedate, int shortlistId,
            int onboardflag, int onflag, int countryId, int notereqtravel, int notereqtaccomm, int upflag, String extzipfile) {
        this.candidateId = candidateId;
        this.name = name;
        this.position = position;
        this.photo = photo;
        this.mobilizedate = mobilizedate;
        this.shortlistId = shortlistId;
        this.onboardflag = onboardflag;
        this.onflag = onflag;
        this.countryId = countryId;
        this.chkNotReqTravel = notereqtravel;
        this.chkAccommodation = notereqtaccomm;
        this.upflag = upflag;
        this.extzipfile = extzipfile;
    }

    public OnboardingInfo(int shortListId, int airportid, String depflightname, String depflightno, String depseatno, String depterminalno, String depdate,
            String deptime, String arrflightname, String arrflightno, String arrseatno, String arrterminalno, String arrdate, String arrtime,
            String ticketfilename) {
        this.shortlistId = shortListId;
        this.airportid = airportid;
        this.depflightname = depflightname;
        this.depflightno = depflightno;
        this.depseatno = depseatno;
        this.depterminalno = depterminalno;
        this.depdate = depdate;
        this.deptime = deptime;
        this.arrflightname = arrflightname;
        this.arrflightno = arrflightno;
        this.arrseatno = arrseatno;
        this.arrterminalno = arrterminalno;
        this.arrdate = arrdate;
        this.arrtime = arrtime;
        this.filename = ticketfilename;
    }

    public OnboardingInfo(int notreqtravel, int depairportId, String depflightname, String depflightno, String depseatno, String depterminalno, String depdate,
            String deptime, int arrairportid, String arrflightname, String arrflightno, String arrseatno, String arrterminalno, String arrdate, String arrtime,
            String ticketfilename, int notreqaccom, String hotelname, String hotaddline1, String hotaddline2, String hotaddline3, String hotcontactno, String hotroomno,
            String checkindate, String checkintime, String checkoutdate, String checkouttime, String hotdocname, String hotbookingno, String reqdocId,
            String reqchecklistId, String depairportname, String arrairportname, String checklistBy, String checklistdate, String checklisttime, int shortlistId) {
        this.chkNotReqTravel = notreqtravel;
        this.depairportid = depairportId;
        this.depflightname = depflightname;
        this.depflightno = depflightno;
        this.depseatno = depseatno;
        this.depterminalno = depterminalno;
        this.depdate = depdate;
        this.deptime = deptime;
        this.arrairportid = arrairportid;
        this.arrflightname = arrflightname;
        this.arrflightno = arrflightno;
        this.arrseatno = arrseatno;
        this.arrterminalno = arrterminalno;
        this.arrdate = arrdate;
        this.arrtime = arrtime;
        this.filename = ticketfilename;
        this.chkAccommodation = notreqaccom;
        this.hotelname = hotelname;
        this.hoteladdline1 = hotaddline1;
        this.hoteladdline2 = hotaddline2;
        this.hoteladdline3 = hotaddline3;
        this.contactno = hotcontactno;
        this.roomno = hotroomno;
        this.checkindate = checkindate;
        this.checkintime = checkintime;
        this.checkoutdate = checkoutdate;
        this.checkouttime = checkouttime;
        this.hoteldoc = hotdocname;
        this.bookingno = hotbookingno;
        this.reqdocId = reqdocId;
        this.reqchecklistId = reqchecklistId;
        this.depairportname = depairportname;
        this.arrairportname = arrairportname;
        this.checklistBy = checklistBy;
        this.checklistdate = checklistdate;
        this.checklisttime = checklisttime;
        this.shortlistId = shortlistId;
    }

    public OnboardingInfo(int shortlistId, int depairportId, String depflightname, String depflightnumber, String depseatnumber, String depterminalnumber,
            String depdate, String deptime, int arrairportId, String arrflightname, String arrflightnumber, String arrseatnumber, String arrterminalnumber,
            String arrdate, String arrtime, String fileName, int status, int travelnotreq) {
        this.shortlistId = shortlistId;
        this.depairportid = depairportId;
        this.depflightname = depflightname;
        this.depflightno = depflightnumber;
        this.depseatno = depseatnumber;
        this.depterminalno = depterminalnumber;
        this.depdate = depdate;
        this.deptime = deptime;
        this.arrairportid = arrairportId;
        this.arrflightname = arrflightname;
        this.arrflightno = arrflightnumber;
        this.arrseatno = arrseatnumber;
        this.arrterminalno = arrterminalnumber;
        this.arrdate = arrdate;
        this.arrtime = arrtime;
        this.filename = fileName;
        this.status = status;
        this.chkNotReqTravel = travelnotreq;
    }

    public OnboardingInfo(int shortlistId, String hotelname, String hoteladdline1, String hoteladdline2, String hoteladdline3, String contactno, String roomno,
            String checkindate, String checkintime, String checkoutdate, String checkouttime, String bookingno, String fileName, int status, int chkAccommodation) {
        this.shortlistId = shortlistId;
        this.hotelname = hotelname;
        this.hoteladdline1 = hoteladdline1;
        this.hoteladdline2 = hoteladdline2;
        this.hoteladdline3 = hoteladdline3;
        this.contactno = contactno;
        this.roomno = roomno;
        this.checkindate = checkindate;
        this.checkintime = checkintime;
        this.checkoutdate = checkoutdate;
        this.checkouttime = checkouttime;
        this.bookingno = bookingno;
        this.filename = fileName;
        this.status = status;
        this.chkAccommodation = chkAccommodation;
    }

    public OnboardingInfo(int shortlistId, String reqdoconboarding, String extfilename) {
        this.shortlistId = shortlistId;
        this.reqdoconboarding = reqdoconboarding;
        this.extfilename = extfilename;
    }

    public OnboardingInfo(int candidateId, String name, String position, String photo, String mobilizedate, String hotelname, int shortlistId,
            int onboardflag, String arrivaldate, int onflag, int upflag, String uploadexternal, String client, String country, int clientId, String date) {
        this.candidateId = candidateId;
        this.name = name;
        this.position = position;
        this.photo = photo;
        this.mobilizedate = mobilizedate;
        this.hotelname = hotelname;
        this.shortlistId = shortlistId;
        this.onboardflag = onboardflag;
        this.arrivaldate = arrivaldate;
        this.onflag = onflag;
        this.upflag = upflag;
        this.extfilename = uploadexternal;
        this.clientname = client;
        this.country = country;
        this.clientId = clientId;
        this.date = date;
    }

    public OnboardingInfo(int shortlistId, int onboarddocId, int formalityId, String pdffilename, String formalityname) {
        this.shortlistId = shortlistId;
        this.onboarddocId = onboarddocId;
        this.formalityId = formalityId;
        this.pdffilename = pdffilename;
        this.formalityname = formalityname;

    }

    public OnboardingInfo(int shortlistId, int candidateId, String clientname, String clientmailId, String comailId, String pdffileName,
            String candidateName, int jobpostId, String position, String bccmailId, String subject, String mailfileName, String sendby, String date,
            String candidatemail, String clientAsset) {
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
        this.clientAsset = clientAsset;
    }

    public OnboardingInfo(int shortlistId, String mailfileName, String sendby, String date, int maillogId) {
        this.shortlistId = shortlistId;
        this.mailfileName = mailfileName;
        this.sendby = sendby;
        this.date = date;
        this.maillogId = maillogId;
    }

    public OnboardingInfo(int notreqtravel, int notreqaccom, String reqdocId, String reqchecklistId, String checklistBy, String checklistdate, String checklisttime,
            int shortlistId, String mailextfile, String onboardingkits, String ondate, String ontime) {
        this.chkNotReqTravel = notreqtravel;
        this.chkAccommodation = notreqaccom;
        this.reqdocId = reqdocId;
        this.reqchecklistId = reqchecklistId;
        this.checklistBy = checklistBy;
        this.checklistdate = checklistdate;
        this.checklisttime = checklisttime;
        this.shortlistId = shortlistId;
        this.mailextfile = mailextfile;
        this.onboardingkits = onboardingkits;
        this.date = ondate;
        this.ontime = ontime;
    }

    public OnboardingInfo(int shortlistId, int onboardingdocid, String formalityname, String generateddoc, String uploadeddoc) {
        this.shortlistId = shortlistId;
        this.onboardingdocid = onboardingdocid;
        this.formalityname = formalityname;
        this.generateddoc = generateddoc;
        this.uploadeddoc = uploadeddoc;
    }

    public OnboardingInfo(int shortlist, int type, int onboardingdtlsid, String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8,
            String val9, String val10, String filename, int status, int userId, String regdate, String moddate, String vald9, String valt9, String vald10, String valt10) {
        this.shortlistId = shortlist;
        this.type = type;
        this.onboardingdtlsid = onboardingdtlsid;
        this.uId = userId;
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
        this.val5 = val5;
        this.val6 = val6;
        this.val7 = val7;
        this.val8 = val8;
        this.val9 = val9;
        this.vald9 = vald9;
        this.valt9 = valt9;
        this.val10 = val10;
        this.vald10 = vald10;
        this.valt10 = valt10;
        this.filename = filename;
        this.status = status;
        this.regdate = regdate;
        this.moddate = moddate;
    }

    public OnboardingInfo(int shortlistId, int onboardingId, String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8,
            String vald9, String valt9, String vald10, String valt10, String filename, int status) {
        this.shortlistId = shortlistId;
        this.onboardingdtlsid = onboardingId;
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
        this.val5 = val5;
        this.val6 = val6;
        this.val7 = val7;
        this.val8 = val8;
        this.vald9 = vald9;
        this.valt9 = valt9;
        this.vald10 = vald10;
        this.valt10 = valt10;
        this.filename = filename;
        this.status = status;
    }

    public OnboardingInfo(String mailto, String mailfrom, String mailcc, String mailbcc, String subject, String filename, String attachmentpath, String sentby, int maillogId,
            String date, String attachmentpath1, String attachmentpath2, String attachmentpath3) {

        this.mailto = mailto;
        this.mailfrom = mailfrom;
        this.mailcc = mailcc;
        this.mailbcc = mailbcc;
        this.subject = subject;
        this.filename = filename;
        this.attachmentpath = attachmentpath;
        this.sentby = sentby;
        this.maillogId = maillogId;
        this.date = date;
        this.attachmentpath1 = attachmentpath1;
        this.attachmentpath2 = attachmentpath2;
        this.attachmentpath3 = attachmentpath3;
    }

    public int getOnboardingdocid() {
        return onboardingdocid;
    }

    public String getGenerateddoc() {
        return generateddoc;
    }

    public String getUploadeddoc() {
        return uploadeddoc;
    }

    public String getMailextfile() {
        return mailextfile;
    }

    public int getOnflag() {
        return onflag;
    }

    public int getOnboardflag() {
        return onboardflag;
    }

    public String getMobilizedate() {
        return mobilizedate;
    }

    public String getHotelname() {
        return hotelname;
    }

    public String getArrivaldate() {
        return arrivaldate;
    }

    public int getOnboardCount() {
        return onboardCount;
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

    public String getGradeName() {
        return gradeName;
    }

    public String getPhotoName() {
        return photoName;
    }

    public String getPositionValue() {
        return positionValue;
    }

    public String getName() {
        return name;
    }

    public int getShortlistId() {
        return shortlistId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public String getCountry() {
        return country;
    }

    public int getDdltype() {
        return ddltype;
    }

    public int getTotalnoofopenings() {
        return totalnoofopenings;
    }

    public int getPositionCount() {
        return positionCount;
    }

    public int getAirportid() {
        return airportid;
    }

    public String getDepflightname() {
        return depflightname;
    }

    public String getDepflightno() {
        return depflightno;
    }

    public String getDepseatno() {
        return depseatno;
    }

    public String getDepterminalno() {
        return depterminalno;
    }

    public String getDepdate() {
        return depdate;
    }

    public String getDeptime() {
        return deptime;
    }

    public String getArrflightname() {
        return arrflightname;
    }

    public String getArrflightno() {
        return arrflightno;
    }

    public String getArrseatno() {
        return arrseatno;
    }

    public String getArrterminalno() {
        return arrterminalno;
    }

    public String getArrdate() {
        return arrdate;
    }

    public String getArrtime() {
        return arrtime;
    }

    public String getFilename() {
        return filename;
    }

    public String getReqdocId() {
        return reqdocId;
    }

    public String getReqchecklistId() {
        return reqchecklistId;
    }

    /**
     * @return the countryId
     */
    public int getCountryId() {
        return countryId;
    }

    public int getDepairportid() {
        return depairportid;
    }

    public int getArrairportid() {
        return arrairportid;
    }

    public int getChkNotReqTravel() {
        return chkNotReqTravel;
    }

    public int getChkAccommodation() {
        return chkAccommodation;
    }

    public int getAccomnotreq() {
        return accomnotreq;
    }

    public String getHoteladdline1() {
        return hoteladdline1;
    }

    public String getHoteladdline2() {
        return hoteladdline2;
    }

    public String getHoteladdline3() {
        return hoteladdline3;
    }

    public String getContactno() {
        return contactno;
    }

    public String getRoomno() {
        return roomno;
    }

    public String getCheckindate() {
        return checkindate;
    }

    public String getCheckintime() {
        return checkintime;
    }

    public String getCheckoutdate() {
        return checkoutdate;
    }

    public String getCheckouttime() {
        return checkouttime;
    }

    public String getBookingno() {
        return bookingno;
    }

    public String getTicketfilename() {
        return ticketfilename;
    }

    public String getHotdocname() {
        return hotdocname;
    }

    public String getReqdoconboarding() {
        return reqdoconboarding;
    }

    public String getExtfilename() {
        return extfilename;
    }

    public int getOnboarddocId() {
        return onboarddocId;
    }

    public int getFormalityId() {
        return formalityId;
    }

    public String getPdffilename() {
        return pdffilename;
    }

    public String getFormalityname() {
        return formalityname;
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

    public String getBccmailId() {
        return bccmailId;
    }

    public String getSubject() {
        return subject;
    }

    public String getMailfileName() {
        return mailfileName;
    }

    public String getSendby() {
        return sendby;
    }

    public String getDate() {
        return date;
    }

    public String getCandidatemail() {
        return candidatemail;
    }

    public String getZipfilename() {
        return zipfilename;
    }

    /**
     * @return the hoteldoc
     */
    public String getHoteldoc() {
        return hoteldoc;
    }

    /**
     * @return the upflag
     */
    public int getUpflag() {
        return upflag;
    }

    public String getDepairportname() {
        return depairportname;
    }

    public String getArrairportname() {
        return arrairportname;
    }

    public String getChecklistBy() {
        return checklistBy;
    }

    public String getChecklistdate() {
        return checklistdate;
    }

    public String getChecklisttime() {
        return checklisttime;
    }

    public int getMaillogId() {
        return maillogId;
    }

    public int getType() {
        return type;
    }

    public int getOnboardingdtlsid() {
        return onboardingdtlsid;
    }

    public int getuId() {
        return uId;
    }

    public String getVal1() {
        return val1;
    }

    public String getVal2() {
        return val2;
    }

    public String getVal3() {
        return val3;
    }

    public String getVal4() {
        return val4;
    }

    public String getVal5() {
        return val5;
    }

    public String getVal6() {
        return val6;
    }

    public String getVal7() {
        return val7;
    }

    public String getVal8() {
        return val8;
    }

    public String getVal9() {
        return val9;
    }

    public String getVal10() {
        return val10;
    }

    public String getRegdate() {
        return regdate;
    }

    public String getModdate() {
        return moddate;
    }

    public String getVald9() {
        return vald9;
    }

    public String getValt9() {
        return valt9;
    }

    public String getVald10() {
        return vald10;
    }

    public String getValt10() {
        return valt10;
    }

    public String getOnboardingkits() {
        return onboardingkits;
    }

    public String getOntime() {
        return ontime;
    }

    public String getExtzipfile() {
        return extzipfile;
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

    public String getAttachmentpath() {
        return attachmentpath;
    }

    public String getSentby() {
        return sentby;
    }

    public String getAttachmentpath1() {
        return attachmentpath1;
    }

    public String getAttachmentpath2() {
        return attachmentpath2;
    }

    public String getAttachmentpath3() {
        return attachmentpath3;
    }

    /**
     * @return the onboardingfileId
     */
    public int getOnboardingfileId() {
        return onboardingfileId;
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

}
