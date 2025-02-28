package com.web.jxp.client;

public class ClientInfo {

    private int clientId;
    private String name;
    private String headofficeaddress;
    private int countryId;
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
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;
    private int assetcount;
    private String countryName;
    private String userName;
    private int clientassetId;
    private String description;
    private String assettypeName;
    private int assettypeId;
    private int clientassetpicid;
    private String filename;
    private String ocsuserIds;
    private String ISDcode;

    private int portId;
    private String deliveryYear;
    private String assetFlag;
    private String lifeboat;
    private String berths;
    private String classification;
    private int positionCount;

    //clientassetposition
    private int clientassetpositionId;
    private int positionId;
    private String gradename;
    private String url;
    private String url_training;
    private String shortName;
    private String colorCode;    
    private int currencyId;
    private int deptcount;
    private int pdeptId;
    private String helpno;
    private String helpemail;
    private String ratetype;
    private int crewrota;
    private int startrota;
    private double allowance;
    private String cids;
    private String mids;
    private String rids;
    
    //for ddl
    public ClientInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    //for index
    public ClientInfo(int clientId, String name, String inchargename, String email,
            String contact, int assetcount, int status, String ISDCode) {
        this.clientId = clientId;
        this.name = name;
        this.inchargename = inchargename;
        this.email = email;
        this.contact = contact;
        this.assetcount = assetcount;
        this.status = status;
        this.ISDcode = ISDCode;
    }

    //for add
    public ClientInfo(int clientId, String name, String headofficeaddress, int countryId,
            String inchargename, String position, String email, String contact, String date,
            String link1, String link2, String link3, String link4, String link5, 
            String link6, String ocsuserIds, int status, int userId, String ISDcode, 
            String shortName, String colorCode, String mIds, String strRids) 
    {
        this.clientId = clientId;
        this.name = name;
        this.headofficeaddress = headofficeaddress;
        this.countryId = countryId;
        this.inchargename = inchargename;
        this.position = position;
        this.email = email;
        this.ISDcode = ISDcode;
        this.contact = contact;
        this.date = date;
        this.link1 = link1;
        this.link2 = link2;
        this.link3 = link3;
        this.link4 = link4;
        this.link5 = link5;
        this.link6 = link6;
        this.ocsuserIds = ocsuserIds;
        this.status = status;
        this.userId = userId;
        this.shortName = shortName;
        this.colorCode = colorCode;
        this.mids = mIds;
        this.rids = strRids;
    }

    //for view
    public ClientInfo(int clientId, String name, String headofficeaddress, String countryName,
            String inchargename, String position, String email, String contact, String date,
            String link1, String link2, String link3, String link4, String link5, String link6, String userName,
            int status, String ISDcode, String shortName, String colorCode, String ocsIds, String mids, String rids) 
    {
        this.clientId = clientId;
        this.name = name;
        this.headofficeaddress = headofficeaddress;
        this.countryName = countryName;
        this.inchargename = inchargename;
        this.position = position;
        this.email = email;
        this.contact = contact;
        this.date = date;
        this.link1 = link1;
        this.link2 = link2;
        this.link3 = link3;
        this.link4 = link4;
        this.link5 = link5;
        this.link6 = link6;
        this.userName = userName;
        this.status = status;
        this.ISDcode = ISDcode;
        this.shortName = shortName;
        this.colorCode = colorCode;
        this.ocsuserIds = ocsIds;
        this.mids = mids;
        this.rids = rids;
    }

    //modify client
    public ClientInfo(int clientId, String name, String headofficeaddress, int countryId, String inchargename,
            String position, String email, String contact, String date, String link1, String link2, 
            String link3, String link4, String link5, String link6, String ocsuserIds, int status, 
            String ISDcode, String shortName, String colorCode, String mids, String rids) {
        this.clientId = clientId;
        this.name = name;
        this.headofficeaddress = headofficeaddress;
        this.countryId = countryId;
        this.inchargename = inchargename;
        this.position = position;
        this.email = email;
        this.contact = contact;
        this.date = date;
        this.link1 = link1;
        this.link2 = link2;
        this.link3 = link3;
        this.link4 = link4;
        this.link5 = link5;
        this.link6 = link6;
        this.ocsuserIds = ocsuserIds;
        this.status = status;
        this.ISDcode = ISDcode;
        this.shortName = shortName;
        this.colorCode = colorCode;
        this.mids = mids;
        this.rids = rids;
    }

    //for asset list
    public ClientInfo(int clientassetId, String name, String description, String countryName, String assettypeName, int positionCount,
            int status, int countryId, int assettypeId, int deptcount) 
    {
        this.clientassetId = clientassetId;
        this.name = name;
        this.description = description;
        this.countryName = countryName;
        this.assettypeName = assettypeName;
        this.status = status;
        this.countryId = countryId;
        this.assettypeId = assettypeId;
        this.positionCount = positionCount;
        this.deptcount = deptcount;
    }

    //for Insert
    public ClientInfo(int clientassetId, String name, String description, int portId, String deliveryYear,
        String assetFlag,String classification, String berths, String lifeboat, int status, int countryId, 
        int assettypeId, String url, String url_training, int currencyId, String helpno, 
        String helpemail, String ratetype,int crewrota, int startrota, double allowance) 
    {
        this.clientassetId = clientassetId;
        this.name = name;
        this.description = description;
        this.portId = portId;
        this.deliveryYear = deliveryYear;
        this.assetFlag = assetFlag;
        this.classification = classification;
        this.berths = berths;
        this.lifeboat = lifeboat;
        this.status = status;
        this.countryId = countryId;
        this.assettypeId = assettypeId;
        this.url = url;
        this.url_training = url_training;
        this.currencyId = currencyId;
        this.helpno = helpno;
        this.helpemail = helpemail;
        this.ratetype = ratetype;
        this.crewrota = crewrota;
        this.startrota = startrota;
        this.allowance = allowance;
    }

    public ClientInfo(int clientassetpicid, String filename, String date, String name) {
        this.clientassetpicid = clientassetpicid;
        this.filename = filename;
        this.date = date;
        this.name = name;
    }

    public ClientInfo(int positionId, String positionname, String gradename, int clientassetpositionId, String assettypeName) {
        this.positionId = positionId;
        this.position = positionname;
        this.gradename = gradename;
        this.clientassetpositionId = clientassetpositionId;
        this.assettypeName = assettypeName;
    }
    //for dept list
    public ClientInfo(int pdeptId, String name, int positionCount)
    {
        this.pdeptId = pdeptId;
        this.name = name;
        this.positionCount = positionCount;
    }
    //for dept positionlist
    public ClientInfo(int clientassetpositionId, String position, String gradename, int pdeptId)
    {
        this.clientassetpositionId = clientassetpositionId;
        this.position = position;
        this.gradename = gradename;    
        this.pdeptId = pdeptId;
    }

    public String getISDcode() {
        return ISDcode;
    }

    public int getClientassetpositionId() {
        return clientassetpositionId;
    }

    public int getPositionId() {
        return positionId;
    }

    public String getGradename() {
        return gradename;
    }

    public int getPositionCount() {
        return positionCount;
    }

    public int getPortId() {
        return portId;
    }

    public String getDeliveryYear() {
        return deliveryYear;
    }

    public String getAssetFlag() {
        return assetFlag;
    }

    public String getLifeboat() {
        return lifeboat;
    }

    public String getBerths() {
        return berths;
    }

    public String getClassification() {
        return classification;
    }

    public String getOcsuserIds() {
        return ocsuserIds;
    }

    public int getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public String getHeadofficeaddress() {
        return headofficeaddress;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getInchargename() {
        return inchargename;
    }

    public String getPosition() {
        return position;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getDate() {
        return date;
    }

    public String getLink1() {
        return link1;
    }

    public String getLink2() {
        return link2;
    }

    public String getLink3() {
        return link3;
    }

    public String getLink4() {
        return link4;
    }

    public String getLink5() {
        return link5;
    }

    public String getLink6() {
        return link6;
    }

    public int getStatus() {
        return status;
    }

    public int getUserId() {
        return userId;
    }

    public int getDdlValue() {
        return ddlValue;
    }

    public String getDdlLabel() {
        return ddlLabel;
    }

    public int getAssetcount() {
        return assetcount;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getUserName() {
        return userName;
    }

    public int getClientassetId() {
        return clientassetId;
    }

    public String getDescription() {
        return description;
    }

    public String getAssettypeName() {
        return assettypeName;
    }

    public int getAssettypeId() {
        return assettypeId;
    }

    public int getClientassetpicid() {
        return clientassetpicid;
    }

    public String getFilename() {
        return filename;
    }

    public String getUrl() {
        return url;
    }

    /**
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @return the colorCode
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * @return the url_training
     */
    public String getUrl_training() {
        return url_training;
    }

    public int getCurrencyId() {
        return currencyId;
    }


    /**
     * @return the deptcount
     */
    public int getDeptcount() {
        return deptcount;
    }

    /**
     * @return the pdeptId
     */
    public int getPdeptId() {
        return pdeptId;
    }

    /**
     * @return the helpno
     */
    public String getHelpno() {
        return helpno;
    }

    /**
     * @return the helpemail
     */
    public String getHelpemail() {
        return helpemail;
    }

    /**
     * @return the ratetype
     */
    public String getRatetype() {
        return ratetype;
    }

    public int getCrewrota() {
        return crewrota;
    }

    public int getStartrota() {
        return startrota;
    }

    /**
     * @return the allowance
     */
    public double getAllowance() {
        return allowance;
    }

    /**
     * @return the cids
     */
    public String getCids() {
        return cids;
    }

    /**
     * @return the mids
     */
    public String getMids() {
        return mids;
    }

    /**
     * @return the rids
     */
    public String getRids() {
        return rids;
    }
}
