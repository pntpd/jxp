package com.web.jxp.client;

import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class ClientForm extends ActionForm {

    private int clientId;
    private String search;
    private String doCancel;
    private String doModify;
    private String name;
    private String headofficeaddress;
    private int countryId;
    private Collection countries;
    private String inchargename;
    private String position;
    private String email;
    private String contact;
    private String date;
    private String link1;
    private String link2;
    private String link3;
    private String link4;
    private String link5;
    private String link6;
    private String doAdd;
    private String doSave;
    private String doView;
    private String doSaveAsset;
    private String doDeleteAsset;
    private int clientassetId;
    private int assettypeId;
    private Collection assettypes;
    private String description;
    private String doModifyAsset;
    private String doCancelAsset;
    private String tabno;
    private String userName;
    private String fname;
    private String[] ocsuserIds;
    private String[] coordinatorIds;
    private Collection users;
    private String ISDcode;
    private int countryIndexId;
    private int clientcode;
    private int clientassetcode;
    private Collection ports;
    private int portId;
    private String deliveryYear;
    private String assetFlag;
    private String lifeboat;
    private String berths;
    private String classification;
    private int status;
    private int assettypeIndexId;
    private int ctp;
    private String url;
    private String url_training;
    private String shortName;
    private String colorCode;
    private Collection currencies;
    private int currencyId;
    private String helpno;
    private String helpemail;
    private String ratetype;
    private int crewrota;
    private int startrota;
    private double allowance;
    private String[] mids;
    private String[] rids;

    public Collection getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Collection currencies) {
        this.currencies = currencies;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    public int getAssettypeIndexId() {
        return assettypeIndexId;
    }

    public void setAssettypeIndexId(int assettypeIndexId) {
        this.assettypeIndexId = assettypeIndexId;
    }

    public int getClientcode() {
        return clientcode;
    }

    public void setClientcode(int clientcode) {
        this.clientcode = clientcode;
    }

    public int getCountryIndexId() {
        return countryIndexId;
    }

    public void setCountryIndexId(int countryIndexId) {
        this.countryIndexId = countryIndexId;
    }

    public String getISDcode() {
        return ISDcode;
    }

    public void setISDcode(String ISDcode) {
        this.ISDcode = ISDcode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAssetFlag() {
        return assetFlag;
    }

    public void setAssetFlag(String assetFlag) {
        this.assetFlag = assetFlag;
    }

    public String getLifeboat() {
        return lifeboat;
    }

    public void setLifeboat(String lifeboat) {
        this.lifeboat = lifeboat;
    }

    public String getBerths() {
        return berths;
    }

    public void setBerths(String berths) {
        this.berths = berths;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getDeliveryYear() {
        return deliveryYear;
    }

    public void setDeliveryYear(String deliveryYear) {
        this.deliveryYear = deliveryYear;
    }

    public int getPortId() {
        return portId;
    }

    public void setPortId(int portId) {
        this.portId = portId;
    }

    public Collection getPorts() {
        return ports;
    }

    public void setPorts(Collection ports) {
        this.ports = ports;
    }

    public int getClientassetcode() {
        return clientassetcode;
    }

    public void setClientassetcode(int clientassetcode) {
        this.clientassetcode = clientassetcode;
    }

    public String[] getOcsuserIds() {
        return ocsuserIds;
    }

    public void setOcsuserIds(String[] ocsuserIds) {
        this.ocsuserIds = ocsuserIds;
    }

    public Collection getUsers() {
        return users;
    }

    public void setUsers(Collection users) {
        this.users = users;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadofficeaddress() {
        return headofficeaddress;
    }

    public void setHeadofficeaddress(String headofficeaddress) {
        this.headofficeaddress = headofficeaddress;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public Collection getCountries() {
        return countries;
    }

    public void setCountries(Collection countries) {
        this.countries = countries;
    }

    public String getInchargename() {
        return inchargename;
    }

    public void setInchargename(String inchargename) {
        this.inchargename = inchargename;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink1() {
        return link1;
    }

    public void setLink1(String link1) {
        this.link1 = link1;
    }

    public String getLink2() {
        return link2;
    }

    public void setLink2(String link2) {
        this.link2 = link2;
    }

    public String getLink3() {
        return link3;
    }

    public void setLink3(String link3) {
        this.link3 = link3;
    }

    public String getLink4() {
        return link4;
    }

    public void setLink4(String link4) {
        this.link4 = link4;
    }

    public String getLink5() {
        return link5;
    }

    public void setLink5(String link5) {
        this.link5 = link5;
    }

    public String getLink6() {
        return link6;
    }

    public void setLink6(String link6) {
        this.link6 = link6;
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

    public String getDoView() {
        return doView;
    }

    public void setDoView(String doView) {
        this.doView = doView;
    }

    public String getDoSaveAsset() {
        return doSaveAsset;
    }

    public void setDoSaveAsset(String doSaveAsset) {
        this.doSaveAsset = doSaveAsset;
    }

    public String getDoDeleteAsset() {
        return doDeleteAsset;
    }

    public void setDoDeleteAsset(String doDeleteAsset) {
        this.doDeleteAsset = doDeleteAsset;
    }

    public int getClientassetId() {
        return clientassetId;
    }

    public void setClientassetId(int clientassetId) {
        this.clientassetId = clientassetId;
    }

    public int getAssettypeId() {
        return assettypeId;
    }

    public void setAssettypeId(int assettypeId) {
        this.assettypeId = assettypeId;
    }

    public Collection getAssettypes() {
        return assettypes;
    }

    public void setAssettypes(Collection assettypes) {
        this.assettypes = assettypes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoModifyAsset() {
        return doModifyAsset;
    }

    public void setDoModifyAsset(String doModifyAsset) {
        this.doModifyAsset = doModifyAsset;
    }

    public String getDoCancelAsset() {
        return doCancelAsset;
    }

    public void setDoCancelAsset(String doCancelAsset) {
        this.doCancelAsset = doCancelAsset;
    }

    public String getTabno() {
        return tabno;
    }

    public void setTabno(String tabno) {
        this.tabno = tabno;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @param shortName the shortName to set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * @return the colorCode
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * @param colorCode the colorCode to set
     */
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * @return the url_training
     */
    public String getUrl_training() {
        return url_training;
    }

    /**
     * @param url_training the url_training to set
     */
    public void setUrl_training(String url_training) {
        this.url_training = url_training;
    }

    /**
     * @return the helpno
     */
    public String getHelpno() {
        return helpno;
    }

    /**
     * @param helpno the helpno to set
     */
    public void setHelpno(String helpno) {
        this.helpno = helpno;
    }

    /**
     * @return the helpemail
     */
    public String getHelpemail() {
        return helpemail;
    }

    /**
     * @param helpemail the helpemail to set
     */
    public void setHelpemail(String helpemail) {
        this.helpemail = helpemail;
    }

    /**
     * @return the ratetype
     */
    public String getRatetype() {
        return ratetype;
    }

    /**
     * @param ratetype the ratetype to set
     */
    public void setRatetype(String ratetype) {
        this.ratetype = ratetype;
    }

    public int getCrewrota() {
        return crewrota;
    }

    public void setCrewrota(int crewrota) {
        this.crewrota = crewrota;
    }

    public int getStartrota() {
        return startrota;
    }

    public void setStartrota(int startrota) {
        this.startrota = startrota;
    }

    /**
     * @return the allowance
     */
    public double getAllowance() {
        return allowance;
    }

    /**
     * @param allowance the allowance to set
     */
    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    /**
     * @return the coordinatorIds
     */
    public String[] getCoordinatorIds() {
        return coordinatorIds;
    }

    /**
     * @param coordinatorIds the coordinatorIds to set
     */
    public void setCoordinatorIds(String[] coordinatorIds) {
        this.coordinatorIds = coordinatorIds;
    }

    /**
     * @return the mids
     */
    public String[] getMids() {
        return mids;
    }

    /**
     * @param mids the mids to set
     */
    public void setMids(String[] mids) {
        this.mids = mids;
    }

    /**
     * @return the rids
     */
    public String[] getRids() {
        return rids;
    }

    /**
     * @param rids the rids to set
     */
    public void setRids(String[] rids) {
        this.rids = rids;
    }

}
