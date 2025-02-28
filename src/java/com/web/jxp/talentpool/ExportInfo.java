package com.web.jxp.talentpool;

public class ExportInfo {
    
    private int experiencedetailId;
    private int status;
    private String lastdrawncurrrency;
    private int lastdrawncurrrencyvalue;
    private String position;
    private String companyname;
    private String assetName;
    private String department;
    private String workstartdate;
    private String workenddate;
    private String skills;
    private String rank;
    private String operator;
    private String ocsemployed;
    private String legalrights;
    private String experiencefilename;
    private String workfilename;
    private String compayIndname;
    private String assettypename;
    private String countryname;
    private String cityname;
    private String waterdepth;
    private String ownerpool;
    private String crewtypename;
    private String daterate;
    private int dayvalue;
    private String monthrate;
    private int monthvalue;
    private int currentworkingstatus;
    
    //for training certification 
    private int trainingandcertId;
    private String coursetype;
    private int coursetypeId;
    private String coursename;
    private String eduinstitute;
    private String locationofInstitute;
    private int locationofInstituteId;
    private String dateofissue;
    private String certificateno;
    private String expirydate;
    private String courseverification;
    private String approvedby;
    private int approvedbyid;
    private String certifilename;
    private String fieldofstudy;
    private String coursestart;
    private String passingyear;
    private String currency;
    private int filecount;
    
    //for bankdetails
    private String bankName;
    private String savingAccountNo;
    private String branch;
    private String IFSCCode;
    private int accountTypeId;
    private String accountTypename;
    private String bkFilename;
    private int bankdetid;
    private int primarybankId;
    private String accountno;
    
        //educational details
    private int educationdetailId;
    private String kindname;
    private int kindId;
    private String degree;
    private int degreeId;
    private String educationalfilename;
    private String backgroundofstudy;
    private int highestqualification;
    
    
    // candidate lang
    private String languageName;
    private String proficiency;
    private String empno;
    private int candlangid;
    private int candidateId;
    
    //Experience
    public ExportInfo(String position, String department, String companyname, String assetname, String startdate, String enddate, int status, String workfilename,
            String experiencefilename, int experiencedetailId,String compayIndname, String assettypename, String countryname, String cityname, String operator,
            String waterdepth,String lastdrawncurrrency,int lastdrawncurrrencyvalue, String skills, String rank, String ownerpool, String crewtypename,
                String ocsemployed, String legalrights, String daterate, int dayvalue, String monthrate, int monthvalue, int currentworkingstatus, String empno, int candidateId) {
        this.position = position;
        this.department = department;
        this.companyname = companyname;
        this.assetName = assetname;
        this.workstartdate = startdate;
        this.workenddate = enddate;
        this.status = status;
        this.workfilename = workfilename;
        this.experiencefilename = experiencefilename;
        this.experiencedetailId = experiencedetailId;
        this.compayIndname = compayIndname;
        this.assettypename = assettypename;
        this.countryname = countryname;
        this.cityname = cityname;
        this.operator = operator;
        this.waterdepth = waterdepth;
        this.lastdrawncurrrency = lastdrawncurrrency;
        this.lastdrawncurrrencyvalue = lastdrawncurrrencyvalue;
        this.skills = skills;
        this.rank = rank;
        this.ownerpool = ownerpool;
        this.crewtypename = crewtypename;
        this.ocsemployed = ocsemployed;
        this.legalrights = legalrights;
        this.daterate = daterate;
        this.dayvalue = dayvalue;
        this.monthrate = monthrate;
        this.monthvalue = monthvalue;
        this.currentworkingstatus  = currentworkingstatus;
        this.empno = empno;
        this.candidateId = candidateId;
    }
    
    public ExportInfo(String coursename, String coursetype, String educinst, String locationofInstitute, 
            String approvedby, String dateofissue,String expirydate, int status, int trainingandcertId, 
            String filename, String countryname, String coursestart, String passingyear, String certificateno, 
            String fieldofstudy, String empno, int candidateId) {
        this.coursename = coursename;
        this.coursetype = coursetype;
        this.eduinstitute = educinst;
        this.locationofInstitute = locationofInstitute;
        this.approvedby = approvedby;
        this.dateofissue = dateofissue;
        this.expirydate = expirydate;
        this.status = status;
        this.trainingandcertId = trainingandcertId;
        this.certifilename = filename;
        this.countryname = countryname;
        this.coursestart = coursestart;
        this.passingyear = passingyear;
        this.certificateno = certificateno;
        this.fieldofstudy = fieldofstudy;
        this.empno = empno;
        this.candidateId = candidateId;
    }
    
     public ExportInfo(int candidatebankId, String bankName, String bankAccounttype, String branch, 
             String ifsccode, String filename, int status, int primaryBankId, String accountno, String empno, int candidateId) {
        this.bankdetid = candidatebankId;
        this.bankName = bankName;
        this.accountTypename = bankAccounttype;
        this.branch = branch;
        this.IFSCCode = ifsccode;
        this.bkFilename = filename;
        this.status = status;
        this.primarybankId = primaryBankId;
        this.accountno = accountno;
        this.empno = empno;
        this.candidateId = candidateId;
    }
     
     //Education details
     public ExportInfo(String kindname, String degree, String educinst, String locationofinstit, String filedofstudy, String startdate,
             String enddate, int status, String filename, int educationdetailId, int highestqualification, String empno, int candidateId) {
        this.kindname = kindname;
        this.degree = degree;
        this.eduinstitute = educinst;
        this.locationofInstitute = locationofinstit;
        this.fieldofstudy = filedofstudy;
        this.coursestart = startdate;
        this.passingyear = enddate;
        this.status = status;
        this.educationalfilename = filename;
        this.educationdetailId = educationdetailId;
        this.highestqualification = highestqualification;
        this.empno = empno;
        this.candidateId = candidateId;
    }
     
    // candidate lang    
    public ExportInfo(int candidateId, int candidatelangId, String langname, String proficiency, String empno, int status) 
    {
        this.candidateId = candidateId;
        this.candlangid = candidatelangId;
        this.languageName = langname;
        this.proficiency = proficiency;
        this.empno = empno;
        this.status = status;
    }

    public int getEducationdetailId() {
        return educationdetailId;
    }

    public String getKindname() {
        return kindname;
    }

    public int getKindId() {
        return kindId;
    }

    public String getDegree() {
        return degree;
    }

    public int getDegreeId() {
        return degreeId;
    }

    public String getEducationalfilename() {
        return educationalfilename;
    }

    public String getBackgroundofstudy() {
        return backgroundofstudy;
    }

    public int getHighestqualification() {
        return highestqualification;
    }

    public String getBankName() {
        return bankName;
    }

    public String getSavingAccountNo() {
        return savingAccountNo;
    }

    public String getBranch() {
        return branch;
    }

    public String getIFSCCode() {
        return IFSCCode;
    }

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public String getAccountTypename() {
        return accountTypename;
    }

    public String getBkFilename() {
        return bkFilename;
    }

    public int getBankdetid() {
        return bankdetid;
    }

    public int getPrimarybankId() {
        return primarybankId;
    }

    public String getAccountno() {
        return accountno;
    }

    public int getTrainingandcertId() {
        return trainingandcertId;
    }

    public String getCoursetype() {
        return coursetype;
    }

    public int getCoursetypeId() {
        return coursetypeId;
    }

    public String getCoursename() {
        return coursename;
    }

    public String getEduinstitute() {
        return eduinstitute;
    }

    public String getLocationofInstitute() {
        return locationofInstitute;
    }

    public int getLocationofInstituteId() {
        return locationofInstituteId;
    }

    public String getDateofissue() {
        return dateofissue;
    }

    public String getCertificateno() {
        return certificateno;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public String getCourseverification() {
        return courseverification;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public int getApprovedbyid() {
        return approvedbyid;
    }

    public String getCertifilename() {
        return certifilename;
    }

    public String getFieldofstudy() {
        return fieldofstudy;
    }

    public String getCoursestart() {
        return coursestart;
    }

    public String getPassingyear() {
        return passingyear;
    }

    public String getCurrency() {
        return currency;
    }

    public int getFilecount() {
        return filecount;
    }

    public int getCurrentworkingstatus() {
        return currentworkingstatus;
    }
    
    public int getExperiencedetailId() {
        return experiencedetailId;
    }

    public int getStatus() {
        return status;
    }

    public String getLastdrawncurrrency() {
        return lastdrawncurrrency;
    }

    public int getLastdrawncurrrencyvalue() {
        return lastdrawncurrrencyvalue;
    }

    public String getPosition() {
        return position;
    }

    public String getCompanyname() {
        return companyname;
    }

    public String getAssetName() {
        return assetName;
    }

    public String getDepartment() {
        return department;
    }

    public String getWorkstartdate() {
        return workstartdate;
    }

    public String getWorkenddate() {
        return workenddate;
    }

    public String getSkills() {
        return skills;
    }

    public String getRank() {
        return rank;
    }

    public String getOperator() {
        return operator;
    }

    public String getOcsemployed() {
        return ocsemployed;
    }

    public String getLegalrights() {
        return legalrights;
    }

    public String getExperiencefilename() {
        return experiencefilename;
    }

    public String getWorkfilename() {
        return workfilename;
    }

    public String getCompayIndname() {
        return compayIndname;
    }

    public String getAssettypename() {
        return assettypename;
    }

    public String getCountryname() {
        return countryname;
    }

    public String getCityname() {
        return cityname;
    }

    public String getWaterdepth() {
        return waterdepth;
    }

    public String getOwnerpool() {
        return ownerpool;
    }

    public String getCrewtypename() {
        return crewtypename;
    }

    public String getDaterate() {
        return daterate;
    }

    public int getDayvalue() {
        return dayvalue;
    }

    public String getMonthrate() {
        return monthrate;
    }

    public int getMonthvalue() {
        return monthvalue;
    }

    /**
     * @return the languageName
     */
    public String getLanguageName() {
        return languageName;
    }

    /**
     * @return the proficiency
     */
    public String getProficiency() {
        return proficiency;
    }

    /**
     * @return the empno
     */
    public String getEmpno() {
        return empno;
    }

    /**
     * @return the candlangid
     */
    public int getCandlangid() {
        return candlangid;
    }

    /**
     * @return the candidateId
     */
    public int getCandidateId() {
        return candidateId;
    }
    
    
    
    
}
