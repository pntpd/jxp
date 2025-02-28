package com.web.jxp.crewdayrate;

public class CrewdayrateInfo
{
    private int clientId;
    private int assetId;
    private int positionId;
    private int crewrotationId;
    private String clientName;
    private String clientAssetName;
    private int pcount;
    private int ccount;
    private int scount;
    private int pendingcount;
    private int status;
    private int ddlValue;
    private double rate1, rate2, rate3;
    private String ddlLabel;
    private String name;
    private String currencyName;
    private String ratetype;
    private int candidateId;
    private int positionId2;
    private String position;
    private String position2;
    private double p2rate1;
    private double p2rate2;
    private double p2rate3;
    private int type;
    private int dayratehId;
    private String fromDate;
    private String toDate;
    
    //for ddl
    public CrewdayrateInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    //for p list
    public CrewdayrateInfo(int ddlValue, String ddlLabel, double rate1)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
        this.rate1 = rate1;
    }
    //For history
    public CrewdayrateInfo(int dayratehId, String fromDate, String toDate, double rate1, double rate2, double rate3)
    {
        this.dayratehId = dayratehId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        
    }
    //for index
    public CrewdayrateInfo(int assetId, String clientName, String clientAssetName, 
        int status, int pcount, int scount, int pendingcount)
    {
        this.assetId = assetId;
        this.clientName = clientName;
        this.clientAssetName = clientAssetName;
        this.status = status;
        this.pcount = pcount;
        this.scount = scount;
        this.pendingcount = pendingcount;
    }
    //For Crelist
    public CrewdayrateInfo(int candidateId, int positionId, String name, 
            double rate1, double rate2, double rate3, int type, String fromDate, String toDate)
    {        
        this.candidateId = candidateId;
        this.positionId = positionId;
        this.name = name;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.type = type;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    
    //for basic info
    public CrewdayrateInfo (String clientName, String clientAssetName, String currencyName, int clientId, String ratetype)
    {
        this.clientName = clientName;
        this.clientAssetName = clientAssetName;
        this.currencyName = currencyName;
        this.clientId = clientId;
        this.ratetype = ratetype;
    }
    
    public CrewdayrateInfo(double rate1, double rate2, double rate3, int positionId, int positionId2, double prate1,double prate2, double prate3)
    {
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.positionId = positionId;
        this.positionId2 = positionId2;
        this.p2rate1 = prate1;
        this.p2rate2 = prate2;
        this.p2rate3 = prate3;
    }

    /**
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @return the assetId
     */
    public int getAssetId() {
        return assetId;
    }

    /**
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @return the clientAssetName
     */
    public String getClientAssetName() {
        return clientAssetName;
    }

    /**
     * @return the pcount
     */
    public int getPcount() {
        return pcount;
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
     * @return the name
     */
    public String getName() {
        return name;
    }


    

    /**
     * @return the traingId
     */
  
    /**
     * @return the courseIdrel
    
    public int getCcount() {
        return ccount;
    }

    /**
     * @return the scount
     */
    public int getScount() {
        return scount;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return the ccount
     */
    public int getCcount() {
        return ccount;
    }

    /**
     * @return the pendingcount
     */
    public int getPendingcount() {
        return pendingcount;
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
    }

    /**
     * @return the rate1
     */
    public double getRate1() {
        return rate1;
    }

    /**
     * @return the rate2
     */
    public double getRate2() {
        return rate2;
    }

    /**
     * @return the rate3
     */
    public double getRate3() {
        return rate3;
    }

    /**
     * @return the crewrotationId
     */
    public int getCrewrotationId() {
        return crewrotationId;
    }

    /**
     * @return the currencyName
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * @return the ratetype
     */
    public String getRatetype() {
        return ratetype;
    }

    /**
     * @return the candidateId
     */
    public int getCandidateId() {
        return candidateId;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @return the position2
     */
    public String getPosition2() {
        return position2;
    }

    /**
     * @return the p2rate1
     */
    public double getP2rate1() {
        return p2rate1;
    }

    /**
     * @return the p2rate2
     */
    public double getP2rate2() {
        return p2rate2;
    }

    /**
     * @return the p2rate3
     */
    public double getP2rate3() {
        return p2rate3;
    }

    /**
     * @return the positionId2
     */
    public int getPositionId2() {
        return positionId2;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
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
     * @return the dayratehId
     */
    public int getDayratehId() {
        return dayratehId;
    }
}
