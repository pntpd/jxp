package com.web.jxp.timesheet;

public class TimesheetInfo
{
    private int clientId;
    private int assetId;
    private int userId;
    private int positionId;
    private int crewrotationId;
    private String clientName;
    private String clientAssetName;
    private int status;
    private int ddlValue;
    private String ddlLabel;
    private String name;
    private String currencyName;
    private String typevalue;
    private String statusValue;
    private String generatedOn;
    private String fromDate;
    private String toDate;
    private String position;
    private String grade;
    private String remarks;
    private String revision;
    private String filename;
    private String filename2;
    private String excelFile;
    private String email;
    private String approvalDate;
    private String ccaddress;
    private String bccaddress;
    private String subject;
    private int timesheetId;   
    private int tcount;
    private int currencyId;
    private int revisionId;
    private double rate;
    private double srate;
    private double amount;
    private double grandtotal;
    private int activityId;
    private int dateDiff;
    private int dateDiff2;
    private int type;
    private double percent;
    private int noofdays ;
    private double rate1;
    private double rate2;
    private double rate3;
    private double prate;
    private double allowance;
    private int totalcrew;
    private int flag;
    private int timesheetdetailId;
    private int candidateId;
    private int travelDays;
    private String employeeNo;
    private String date;
    private String date1;
    private int positionId2;
    private String position2;
    private int days1;
    private int days2;
    private int days3;
    private int days4;
    private int days5;
    private int repeatId;
    private int rflag;
    private int oldrate;
    
    //for appraisal
    public TimesheetInfo(int ddlValue, String ddlLabel, String date)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
        this.date = date;
    }
    
    //for ddl
    public TimesheetInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    
    //getRevisionById
    public TimesheetInfo(String revision, String remarks)
    {
        this.revision = revision;
        this.remarks = remarks;
    }
    
    //Senforapproval - getClientEmailById
    public TimesheetInfo(String email, String clientname, String fromDate, String toDate, String ccaddress, String excelfile)
    {
        this.email = email;
        this.clientName = clientname;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.ccaddress = ccaddress;
        this.excelFile = excelfile;
    }
    //For approve invoicing
    public TimesheetInfo(int timesheetId, String remarks, int uId, int status, String fileName1)
    {
        this.timesheetId = timesheetId;
        this.remarks = remarks;
        this.userId = uId;
        this.status = status;
        this.filename = fileName1;
    }
    //sent for Approval
    public TimesheetInfo(int timesheetId, String email, String ccaddress, String bccaddress, int uId, 
            int status, String fileName2, String approvalDate)
    {
        this.timesheetId = timesheetId;
        this.email = email;
        this.ccaddress = ccaddress;
        this.bccaddress = bccaddress;
        this.userId = uId;
        this.status = status;
        this.filename2 = fileName2;
        this.approvalDate = approvalDate;
    }
    //For save reason
    public TimesheetInfo(int timesheetId, int revisionId, String remarks, int uId, int status )
    {
        this.timesheetId = timesheetId;
        this.revisionId = revisionId;
        this.remarks = remarks;
        this.userId = uId;
        this.status = status;
    }
    //for index - getTimesheetByName , getExcel
    public TimesheetInfo(int assetId, String clientName, String clientAssetName, 
        String typevalue, int tcount)
    {
        this.assetId = assetId;
        this.clientName = clientName;
        this.clientAssetName = clientAssetName;
        this.typevalue = typevalue;
        this.tcount = tcount;
    }    
    
    //for basic info - getBasicDetail
    public TimesheetInfo (String clientName, String clientAssetName, String currencyName, int clientId, int currencyId, double allowance)
    {
        this.clientName = clientName;
        this.clientAssetName = clientAssetName;
        this.currencyName = currencyName;
        this.clientId = clientId;
        this.currencyId = currencyId;
        this.allowance = allowance;
    }
    //for view getTimesheetList
    public TimesheetInfo(int timesheetId, String statusValue, String generatedon, String fromDate , String toDate,
            String type, String currency, String approvalfile, int status, int repeatId)
    {
        this.timesheetId = timesheetId;
        this.statusValue = statusValue;
        this.generatedOn = generatedon;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.typevalue = type;
        this.currencyName = currency;
        this.filename2 = approvalfile;
        this.status = status;
        this.repeatId = repeatId;
    }
    
    //getBasicTimesheet
    public TimesheetInfo(int timesheetId, String fromDate, String toDate, int status, int tcount, double grandtotal)
    {
        this.timesheetId = timesheetId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;
        this.tcount = tcount;
        this.grandtotal = grandtotal;
    }
    
    // For Activitydeatils- getactivityDetails
    public TimesheetInfo(int crewrotationId, String fromDate, String toDate, int positionId, int activityId, int dateDiff, 
        String position, String statusValue, double rate, int candidateId,int travelDays, String name, String employeeNo, int oldrate)
    {
        this.crewrotationId = crewrotationId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.positionId = positionId;
        this.activityId = activityId;
        this.dateDiff = dateDiff;
        this.position = position;
        this.statusValue = statusValue;
        this.rate = rate;
        this.candidateId = candidateId;
        this.travelDays = travelDays;
        this.name = name;
        this.employeeNo = employeeNo;
        this.oldrate = oldrate;
    }
    
    //getStanbyPromotionDetails
    public TimesheetInfo(int crewrotationId, String fromDate, String toDate, int positionId, int activityId, int dateDiff, 
        String position, String statusValue, double rate, int candidateId)
    {
        this.crewrotationId = crewrotationId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.positionId = positionId;
        this.activityId = activityId;
        this.dateDiff = dateDiff;
        this.position = position;
        this.statusValue = statusValue;
        this.rate = rate;
        this.candidateId = candidateId;
    }
    
    //For Crewlist
    public TimesheetInfo(int candidateId, String name, String position, int positionId, 
            double rate1, double rate2, int crewrotationId, int travelDays, int assetId, 
            double rate3)
    {
        this.candidateId = candidateId;
        this.name = name;
        this.position = position;
        this.positionId = positionId;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.crewrotationId = crewrotationId;
        this.travelDays = travelDays;
        this.assetId = assetId;
        this.rate3 = rate3;
    }
    
    //getCrewList
    public TimesheetInfo(int candidateId, String name, String position, int positionId, 
            int crewrotationId, int travelDays, int assetId)
    {
        this.candidateId = candidateId;
        this.name = name;
        this.position = position;
        this.positionId = positionId;
        this.crewrotationId = crewrotationId;
        this.travelDays = travelDays;
        this.assetId = assetId;
    }
    
    //Crew rate List - getCrewRateList
    TimesheetInfo(int candidateId, int positionId, double rate1, double rate2, double rate3, String fromDate, String toDate)
    {
        this.candidateId = candidateId;
        this.positionId = positionId;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    //Rates
    TimesheetInfo(double rate1, double rate2, double rate3)
    {
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
    }
    
    //Allowance List- getAllowanceList
    public TimesheetInfo(int candidateId, String name, String position, int positionId, double rate1, int traveldays, double allowance)
    {
        this.candidateId = candidateId;
        this.name = name;
        this.position = position;
        this.positionId = positionId;
        this.rate1 = rate1;
        this.travelDays = traveldays;
        this.allowance = allowance;
     }
   
    //Pending List - getPendingList, getPandingData
    public TimesheetInfo(int timesheetdetailId, int crewrotationId, String name, String position, int type , 
            int noofdays, String fromDate, String toDate, double rate, double percent, double amount, 
            double grandtotal, int positionId, int totalCrew, String activityValue)
    {
        this.timesheetId = timesheetdetailId;
        this.crewrotationId = crewrotationId;
        this.name = name;
        this.position = position;
        this.type = type;
        this.noofdays = noofdays;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.rate = rate;
        this.percent = percent;
        this.amount = amount;
        this.grandtotal = grandtotal;
        this.positionId = positionId;
        this.totalcrew = totalCrew;
        this.statusValue = activityValue;
    }
    
    //For Modifylist - getModifyList , submittedList
    public TimesheetInfo(int timesheetdetailId, int crewrotationId, String name, String position, int type , 
            int noofdays, String fromDate, String toDate, double rate, double percent, double amount, 
            double grandtotal, double  prate, int flag, int positionId, String activityValue, int totalcrew, int assetId, int repeatId)
    {
        this.timesheetdetailId = timesheetdetailId;
        this.crewrotationId = crewrotationId;
        this.name = name;
        this.position = position;
        this.type = type;
        this.noofdays = noofdays;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.rate = rate;
        this.percent = percent;
        this.amount = amount;
        this.grandtotal = grandtotal;
        this.prate = prate;
        this.flag = flag;
        this.positionId = positionId;
        this.statusValue = activityValue;
        this.totalcrew = totalcrew;
        this.assetId = assetId;
        this.repeatId = repeatId;
    }
    
    //getExcelList
    public TimesheetInfo(int timesheetdetailId, int candidateId, String position, String employeeNo, int type,
            int days1, int days2, int days3, int days4, int days5, String name)
    {
        this.timesheetdetailId = timesheetdetailId;
        this.candidateId = candidateId;
        this.position = position;
        this.employeeNo = employeeNo;
        this.type = type;
        this.days1 = days1;
        this.days2 = days2;
        this.days3 = days3;
        this.days4 = days4;
        this.days5 = days5;
        this.name = name;
    }
    
    /*public TimesheetInfo(int candidateId, int positionId, String date, String position, 
            int positionId2, String position2, int dateDiff, int dateDiff2, String newdate,
            String fromDate, String toDate, double rate1, double rate2, double rate3, double oldrate1, 
            double oldrate2, double oldrate3)
    {
        this.candidateId = candidateId;
        this.positionId = positionId;
        this.date = date;
        this.position = position;
        this.positionId2 = positionId2;
        this.position2 = position2;
        this.dateDiff = dateDiff;
        this.dateDiff2 = dateDiff2;
        this.date1 = newdate;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.rate = rate1;
        this.rate1 = rate2;
        this.rate2 = rate3;
        this.oldrate1 = oldrate1;
        this.oldrate2 = oldrate2;
        this.oldrate3 = oldrate3;
    }*/
    
    //getAppriasalList2
    public TimesheetInfo(int candidateId, int positionId, String date, String position)
    {
        this.candidateId = candidateId;
        this.positionId = positionId;
        this.date = date;
        this.position = position;
    }
    
    /*public TimesheetInfo(int candidateId, int positionId, String position, 
            int positionId2, String position2, String fromDate, String date, String toDate, String date2,
            double rate1, double rate2, double rate3, double oldrate1, double oldrate2, double oldrate3)
    {
        this.candidateId = candidateId;
        this.positionId = positionId;
        this.position = position;
        this.positionId2 = positionId2;
        this.position2 = position2;
        this.fromDate = fromDate;
        this.date = date;
        this.toDate = toDate;
        this.date1 = date2;
        this.rate = rate1;
        this.rate1 = rate2;
        this.rate2 = rate3;
        this.oldrate1 = oldrate1;
        this.oldrate2 = oldrate2;
        this.oldrate3 = oldrate3;
    }*/
    
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

    public int getStatus() {
        return status;
    }


    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
    }

    /**
     * @return the rate1
 
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
     * @return the typevalue
     */
    
    /**
     * @return the tcount
     */
    public int getTcount() {
        return tcount;
    }

    /**
     * @return the typevalue
     */
    public String getTypevalue() {
        return typevalue;
    }

    /**
     * @return the generatedOn
     */
    public String getGeneratedOn() {
        return generatedOn;
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
     * @return the timesheetId
     */
    public int getTimesheetId() {
        return timesheetId;
    }

    /**
     * @return the statusValue
     */
    public String getStatusValue() {
        return statusValue;
    }
    /**
     * @return the rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

  
    /**
     * @return the grandtotal
     */
    public double getGrandtotal() {
        return grandtotal;
    }

    /**
     * @return the currencyId
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @return the grade
     */
    public String getGrade() {
        return grade;
    }

    
    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the revision
     */
    public String getRevision() {
        return revision;
    }

    /**
     * @return the revisionId
     */
    public int getRevisionId() {
        return revisionId;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the ccaddress
     */
    public String getCcaddress() {
        return ccaddress;
    }

    /**
     * @return the bccaddress
     */
    public String getBccaddress() {
        return bccaddress;
    }

    /**
     * @return the approvalDate
     */
    public String getApprovalDate() {
        return approvalDate;
    }

    /**
     * @return the filename2
     */
    public String getFilename2() {
        return filename2;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return the excelFile
     */
    public String getExcelFile() {
        return excelFile;
    }

    
    /**
     * @return the activityId
     */
    public int getActivityId() {
        return activityId;
    }

    /**
     * @return the dateDiff
     */
    public int getDateDiff() {
        return dateDiff;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the percent
     */
    public double getPercent() {
        return percent;
    }

    /**
     * @return the noofdays
     */
    public int getNoofdays() {
        return noofdays;
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
     * @return the prate
     */
    public double getPrate() {
        return prate;
    }

    /**
     * @return the srate
     */
    public double getSrate() {
        return srate;
    }

    /**
     * @return the totalcrew
     */
    public int getTotalcrew() {
        return totalcrew;
    }

    /**
     * @return the flag
     */
    public int getFlag() {
        return flag;
    }

    /**
     * @return the timesheetdetailId
     */
    public int getTimesheetdetailId() {
        return timesheetdetailId;
    }

    /**
     * @return the candidateId
     */
    public int getCandidateId() {
        return candidateId;
    }

    /**
     * @return the travelDays
     */
    public int getTravelDays() {
        return travelDays;
    }

    /**
     * @return the allowance
     */
    public double getAllowance() {
        return allowance;
    }

    /**
     * @return the employeeNo
     */
    public String getEmployeeNo() {
        return employeeNo;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @return the positionId2
     */
    public int getPositionId2() {
        return positionId2;
    }

    /**
     * @return the position2
     */
    public String getPosition2() {
        return position2;
    }

    /**
     * @return the dateDiff2
     */
    public int getDateDiff2() {
        return dateDiff2;
    }

    /**
     * @return the date1
     */
    public String getDate1() {
        return date1;
    }

    /**
     * @return the days1
     */
    public int getDays1() {
        return days1;
    }

    /**
     * @return the days2
     */
    public int getDays2() {
        return days2;
    }

    /**
     * @return the days3
     */
    public int getDays3() {
        return days3;
    }

    /**
     * @return the days4
     */
    public int getDays4() {
        return days4;
    }

    /**
     * @return the days5
     */
    public int getDays5() {
        return days5;
    }

    /**
     * @return the repeatId
     */
    public int getRepeatId() {
        return repeatId;
    }

    /**
     * @return the rflag
     */
    public int getRflag() {
        return rflag;
    }

    /**
     * @return the oldrate
     */
    public int getOldrate() {
        return oldrate;
    }

    /**
     * @return the rate3
     */
    public double getRate3() {
        return rate3;
    }
  
}
