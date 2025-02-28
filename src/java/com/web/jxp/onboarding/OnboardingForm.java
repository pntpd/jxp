package com.web.jxp.onboarding;

import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class OnboardingForm extends ActionForm {

    private int jobpostId;
    private int ctp;
    private int statusIndex;
    private int ddlValue;

    private double variablepay;

    private String ddlLabel;
    private String search;
    private String searchon;
    private String doCancel;
    private String doView;
    private String doAdd;
    private String doSave;
    private String adate;
    private String doGenerate;
    private String doSaveTravel;
    private String doSaveAccomm;
    private String doSaveReqDoc;
    private String doSaveDocCheck;
    private String doMail;
    private String doUpload;
    private String doMailtravel;
    private String doDeleteExt;
    private String doSummary;
    private String doMobTravel;
    private String doMobAccomm;
    private String doViewCancel;

    private int onboarddocId;

    private Collection positions;
    private Collection clients;
    private Collection assets;
    private Collection formalitiestemplate;

    private int clientIdIndex;
    private int assetIdIndex;
    private int status;
    private int candidateId;
    private int clientId;
    private int shortlistId;
    private int clientassetId;
    private int onstatus;
    private int formalityId;
    private int assetcountryId;
    private int generatedlistsize;
    private int type;
    private int onboardingId;

    private String docListId[];
    private String hiddenfilename;
    private String txtsearch;

// Travel details
    private int depAirport;
    private int arrAirport;
    private int chkNotReqTravel;
    private String depFlightName;
    private String depFlightNumber;
    private String depSeatNumber;
    private String depTerminalNumber;
    private String depDate;
    private String depTime;
    private String arrFlightName;
    private String arrFlightNumber;
    private String arrSeatNumber;
    private String arrTerminalNumber;
    private String arrDate;
    private String arrTime;
    private FormFile upload1;

    // Accommodation details
    private int chkAccommodation;
    private String hotelname;
    private String hoteladdline1;
    private String hoteladdline2;
    private String hoteladdline3;
    private String contactnumber;
    private String roomnumber;
    private String checkindate;
    private String checkintime;
    private String checkoutdate;
    private String checkouttime;
    private String bookingnumber;
    private FormFile upload2;
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
    

    // Required Doc details
    private String chkreqdocListId[];

    // Doc checklist details
    private int reqcheckstatus;
    private String chkdoccheckListId[];

//    ---------------------------------------------------------------------
    private String[] onboardingkitIds;
    private String zipfilename;

    private String fromval;
    private String toval;
    private String ccval;
    private String bccval;
    private String subject;
    private String description;
    private double tafilesize;
    private FormFile attachfile;
    private String hdnExternalfile;
    
    private String date2;

    public int getGeneratedlistsize() {
        return generatedlistsize;
    }

    public void setGeneratedlistsize(int generatedlistsize) {
        this.generatedlistsize = generatedlistsize;
    }

    public String getSearchon() {
        return searchon;
    }

    public void setSearchon(String searchon) {
        this.searchon = searchon;
    }

    public Collection getFormalitiestemplate() {
        return formalitiestemplate;
    }

    public void setFormalitiestemplate(Collection formalitiestemplate) {
        this.formalitiestemplate = formalitiestemplate;
    }

    public int getFormalityId() {
        return formalityId;
    }

    public void setFormalityId(int formalityId) {
        this.formalityId = formalityId;
    }

    public String getDoGenerate() {
        return doGenerate;
    }

    public void setDoGenerate(String doGenerate) {
        this.doGenerate = doGenerate;
    }

    public String getAdate() {
        return adate;
    }

    public void setAdate(String adate) {
        this.adate = adate;
    }

    public int getClientassetId() {
        return clientassetId;
    }

    public void setClientassetId(int clientassetId) {
        this.clientassetId = clientassetId;
    }

    public double getVariablepay() {
        return variablepay;
    }

    public void setVariablepay(double variablepay) {
        this.variablepay = variablepay;
    }

    public int getOnstatus() {
        return onstatus;
    }

    public void setOnstatus(int onstatus) {
        this.onstatus = onstatus;
    }

    public int getJobpostId() {
        return jobpostId;
    }

    public void setJobpostId(int jobpostId) {
        this.jobpostId = jobpostId;
    }

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
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
     * @return the positions
     */
    public Collection getPositions() {
        return positions;
    }

    /**
     * @param positions the positions to set
     */
    public void setPositions(Collection positions) {
        this.positions = positions;
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

    public int getClientIdIndex() {
        return clientIdIndex;
    }

    /**
     * @param clientIdIndex the clientIdIndex to set
     */
    public void setClientIdIndex(int clientIdIndex) {
        this.clientIdIndex = clientIdIndex;
    }

    /**
     * @return the assetIdIndex
     */
    public int getAssetIdIndex() {
        return assetIdIndex;
    }

    /**
     * @param assetIdIndex the assetIdIndex to set
     */
    public void setAssetIdIndex(int assetIdIndex) {
        this.assetIdIndex = assetIdIndex;
    }

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
     * @return the statusIndex
     */
    public int getStatusIndex() {
        return statusIndex;
    }

    /**
     * @param statusIndex the statusIndex to set
     */
    public void setStatusIndex(int statusIndex) {
        this.statusIndex = statusIndex;
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

    public int getDdlValue() {
        return ddlValue;
    }

    public void setDdlValue(int ddlValue) {
        this.ddlValue = ddlValue;
    }

    public String getDdlLabel() {
        return ddlLabel;
    }

    public void setDdlLabel(String ddlLabel) {
        this.ddlLabel = ddlLabel;
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
     * @return the doAdd
     */
    public String getDoAdd() {
        return doAdd;
    }

    /**
     * @param doAdd the doAdd to set
     */
    public void setDoAdd(String doAdd) {
        this.doAdd = doAdd;
    }

    public String[] getDocListId() {
        return docListId;
    }

    public void setDocListId(String[] docListId) {
        this.docListId = docListId;
    }

    /**
     * @return the assetcountryId
     */
    public int getAssetcountryId() {
        return assetcountryId;
    }

    /**
     * @param assetcountryId the assetcountryId to set
     */
    public void setAssetcountryId(int assetcountryId) {
        this.assetcountryId = assetcountryId;
    }

    public String getDepFlightName() {
        return depFlightName;
    }

    public void setDepFlightName(String depFlightName) {
        this.depFlightName = depFlightName;
    }

    public String getDepFlightNumber() {
        return depFlightNumber;
    }

    public void setDepFlightNumber(String depFlightNumber) {
        this.depFlightNumber = depFlightNumber;
    }

    public String getDepSeatNumber() {
        return depSeatNumber;
    }

    public void setDepSeatNumber(String depSeatNumber) {
        this.depSeatNumber = depSeatNumber;
    }

    public String getDepTerminalNumber() {
        return depTerminalNumber;
    }

    public void setDepTerminalNumber(String depTerminalNumber) {
        this.depTerminalNumber = depTerminalNumber;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public String getArrFlightName() {
        return arrFlightName;
    }

    public void setArrFlightName(String arrFlightName) {
        this.arrFlightName = arrFlightName;
    }

    public String getArrFlightNumber() {
        return arrFlightNumber;
    }

    public void setArrFlightNumber(String arrFlightNumber) {
        this.arrFlightNumber = arrFlightNumber;
    }

    public String getArrSeatNumber() {
        return arrSeatNumber;
    }

    public void setArrSeatNumber(String arrSeatNumber) {
        this.arrSeatNumber = arrSeatNumber;
    }

    public String getArrTerminalNumber() {
        return arrTerminalNumber;
    }

    public void setArrTerminalNumber(String arrTerminalNumber) {
        this.arrTerminalNumber = arrTerminalNumber;
    }

    public String getArrDate() {
        return arrDate;
    }

    public void setArrDate(String arrDate) {
        this.arrDate = arrDate;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public FormFile getUpload1() {
        return upload1;
    }

    public void setUpload1(FormFile upload1) {
        this.upload1 = upload1;
    }

    /**
     * @return the doSaveTravel
     */
    public String getDoSaveTravel() {
        return doSaveTravel;
    }

    /**
     * @param doSaveTravel the doSaveTravel to set
     */
    public void setDoSaveTravel(String doSaveTravel) {
        this.doSaveTravel = doSaveTravel;
    }

    public int getDepAirport() {
        return depAirport;
    }

    public void setDepAirport(int depAirport) {
        this.depAirport = depAirport;
    }

    public int getArrAirport() {
        return arrAirport;
    }

    public void setArrAirport(int arrAirport) {
        this.arrAirport = arrAirport;
    }

    /**
     * @return the hiddenfilename
     */
    public String getHiddenfilename() {
        return hiddenfilename;
    }

    /**
     * @param hiddenfilename the hiddenfilename to set
     */
    public void setHiddenfilename(String hiddenfilename) {
        this.hiddenfilename = hiddenfilename;
    }

    public int getChkNotReqTravel() {
        return chkNotReqTravel;
    }

    public void setChkNotReqTravel(int chkNotReqTravel) {
        this.chkNotReqTravel = chkNotReqTravel;
    }

    public int getChkAccommodation() {
        return chkAccommodation;
    }

    public void setChkAccommodation(int chkAccommodation) {
        this.chkAccommodation = chkAccommodation;
    }

    /**
     * @return the doSaveAccomm
     */
    public String getDoSaveAccomm() {
        return doSaveAccomm;
    }

    /**
     * @param doSaveAccomm the doSaveAccomm to set
     */
    public void setDoSaveAccomm(String doSaveAccomm) {
        this.doSaveAccomm = doSaveAccomm;
    }

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public String getHoteladdline1() {
        return hoteladdline1;
    }

    public void setHoteladdline1(String hoteladdline1) {
        this.hoteladdline1 = hoteladdline1;
    }

    public String getHoteladdline2() {
        return hoteladdline2;
    }

    public void setHoteladdline2(String hoteladdline2) {
        this.hoteladdline2 = hoteladdline2;
    }

    public String getHoteladdline3() {
        return hoteladdline3;
    }

    public void setHoteladdline3(String hoteladdline3) {
        this.hoteladdline3 = hoteladdline3;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber;
    }

    public String getCheckindate() {
        return checkindate;
    }

    public void setCheckindate(String checkindate) {
        this.checkindate = checkindate;
    }

    public String getCheckintime() {
        return checkintime;
    }

    public void setCheckintime(String checkintime) {
        this.checkintime = checkintime;
    }

    public String getCheckoutdate() {
        return checkoutdate;
    }

    public void setCheckoutdate(String checkoutdate) {
        this.checkoutdate = checkoutdate;
    }

    public String getCheckouttime() {
        return checkouttime;
    }

    public void setCheckouttime(String checkouttime) {
        this.checkouttime = checkouttime;
    }

    public String getBookingnumber() {
        return bookingnumber;
    }

    public void setBookingnumber(String bookingnumber) {
        this.bookingnumber = bookingnumber;
    }

    public FormFile getUpload2() {
        return upload2;
    }

    public void setUpload2(FormFile upload2) {
        this.upload2 = upload2;
    }

    /**
     * @return the chkdoccheckListId
     */
    public String[] getChkdoccheckListId() {
        return chkdoccheckListId;
    }

    /**
     * @param chkdoccheckListId the chkdoccheckListId to set
     */
    public void setChkdoccheckListId(String[] chkdoccheckListId) {
        this.chkdoccheckListId = chkdoccheckListId;
    }

    public String[] getChkreqdocListId() {
        return chkreqdocListId;
    }

    public void setChkreqdocListId(String[] chkreqdocListId) {
        this.chkreqdocListId = chkreqdocListId;
    }

    public String getDoSaveReqDoc() {
        return doSaveReqDoc;
    }

    public void setDoSaveReqDoc(String doSaveReqDoc) {
        this.doSaveReqDoc = doSaveReqDoc;
    }

    public String getDoSaveDocCheck() {
        return doSaveDocCheck;
    }

    public void setDoSaveDocCheck(String doSaveDocCheck) {
        this.doSaveDocCheck = doSaveDocCheck;
    }

    public String getDoMail() {
        return doMail;
    }

    public void setDoMail(String doMail) {
        this.doMail = doMail;
    }

    public String getDoUpload() {
        return doUpload;
    }

    public void setDoUpload(String doUpload) {
        this.doUpload = doUpload;
    }

    public String getDoMailtravel() {
        return doMailtravel;
    }

    public void setDoMailtravel(String doMailtravel) {
        this.doMailtravel = doMailtravel;
    }

    public int getOnboarddocId() {
        return onboarddocId;
    }

    public void setOnboarddocId(int onboarddocId) {
        this.onboarddocId = onboarddocId;
    }

    public String[] getOnboardingkitIds() {
        return onboardingkitIds;
    }

    public void setOnboardingkitIds(String[] onboardingkitIds) {
        this.onboardingkitIds = onboardingkitIds;
    }

    public String getZipfilename() {
        return zipfilename;
    }

    public void setZipfilename(String zipfilename) {
        this.zipfilename = zipfilename;
    }

    public String getFromval() {
        return fromval;
    }

    public void setFromval(String fromval) {
        this.fromval = fromval;
    }

    public String getToval() {
        return toval;
    }

    public void setToval(String toval) {
        this.toval = toval;
    }

    public String getCcval() {
        return ccval;
    }

    public void setCcval(String ccval) {
        this.ccval = ccval;
    }

    public String getBccval() {
        return bccval;
    }

    public void setBccval(String bccval) {
        this.bccval = bccval;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FormFile getAttachfile() {
        return attachfile;
    }

    public void setAttachfile(FormFile attachfile) {
        this.attachfile = attachfile;
    }

    /**
     * @return the tafilesize
     */
    public double getTafilesize() {
        return tafilesize;
    }

    /**
     * @param tafilesize the tafilesize to set
     */
    public void setTafilesize(double tafilesize) {
        this.tafilesize = tafilesize;
    }

    public String getDoDeleteExt() {
        return doDeleteExt;
    }

    public void setDoDeleteExt(String doDeleteExt) {
        this.doDeleteExt = doDeleteExt;
    }

    public String getHdnExternalfile() {
        return hdnExternalfile;
    }

    public void setHdnExternalfile(String hdnExternalfile) {
        this.hdnExternalfile = hdnExternalfile;
    }

    /**
     * @return the reqcheckstatus
     */
    public int getReqcheckstatus() {
        return reqcheckstatus;
    }

    /**
     * @param reqcheckstatus the reqcheckstatus to set
     */
    public void setReqcheckstatus(int reqcheckstatus) {
        this.reqcheckstatus = reqcheckstatus;
    }

    /**
     * @return the doSummary
     */
    public String getDoSummary() {
        return doSummary;
    }

    /**
     * @param doSummary the doSummary to set
     */
    public void setDoSummary(String doSummary) {
        this.doSummary = doSummary;
    }

    public String getDoMobTravel() {
        return doMobTravel;
    }

    public void setDoMobTravel(String doMobTravel) {
        this.doMobTravel = doMobTravel;
    }

    public String getDoMobAccomm() {
        return doMobAccomm;
    }

    public void setDoMobAccomm(String doMobAccomm) {
        this.doMobAccomm = doMobAccomm;
    }

    public String getTxtsearch() {
        return txtsearch;
    }

    public void setTxtsearch(String txtsearch) {
        this.txtsearch = txtsearch;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDoViewCancel() {
        return doViewCancel;
    }

    public void setDoViewCancel(String doViewCancel) {
        this.doViewCancel = doViewCancel;
    }

    public int getOnboardingId() {
        return onboardingId;
    }

    public void setOnboardingId(int onboardingId) {
        this.onboardingId = onboardingId;
    }

    /**
     * @return the cval1
     */
    public String getCval1() {
        return cval1;
    }

    /**
     * @param cval1 the cval1 to set
     */
    public void setCval1(String cval1) {
        this.cval1 = cval1;
    }

    /**
     * @return the cval2
     */
    public String getCval2() {
        return cval2;
    }

    /**
     * @param cval2 the cval2 to set
     */
    public void setCval2(String cval2) {
        this.cval2 = cval2;
    }

    /**
     * @return the cval3
     */
    public String getCval3() {
        return cval3;
    }

    /**
     * @param cval3 the cval3 to set
     */
    public void setCval3(String cval3) {
        this.cval3 = cval3;
    }

    /**
     * @return the cval4
     */
    public String getCval4() {
        return cval4;
    }

    /**
     * @param cval4 the cval4 to set
     */
    public void setCval4(String cval4) {
        this.cval4 = cval4;
    }

    /**
     * @return the cval5
     */
    public String getCval5() {
        return cval5;
    }

    /**
     * @param cval5 the cval5 to set
     */
    public void setCval5(String cval5) {
        this.cval5 = cval5;
    }

    /**
     * @return the cval6
     */
    public String getCval6() {
        return cval6;
    }

    /**
     * @param cval6 the cval6 to set
     */
    public void setCval6(String cval6) {
        this.cval6 = cval6;
    }

    /**
     * @return the cval7
     */
    public String getCval7() {
        return cval7;
    }

    /**
     * @param cval7 the cval7 to set
     */
    public void setCval7(String cval7) {
        this.cval7 = cval7;
    }

    /**
     * @return the cval8
     */
    public String getCval8() {
        return cval8;
    }

    /**
     * @param cval8 the cval8 to set
     */
    public void setCval8(String cval8) {
        this.cval8 = cval8;
    }

    /**
     * @return the cval9
     */
    public String getCval9() {
        return cval9;
    }

    /**
     * @param cval9 the cval9 to set
     */
    public void setCval9(String cval9) {
        this.cval9 = cval9;
    }

    /**
     * @return the cval10
     */
    public String getCval10() {
        return cval10;
    }

    /**
     * @param cval10 the cval10 to set
     */
    public void setCval10(String cval10) {
        this.cval10 = cval10;
    }

    /**
     * @return the date2
     */
    public String getDate2() {
        return date2;
    }

    /**
     * @param date2 the date2 to set
     */
    public void setDate2(String date2) {
        this.date2 = date2;
    }

}
