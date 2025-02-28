package com.web.jxp.shortlisting;

public class ShortlistingInfo {

    private int status;
    private int uId;
    private int userId;
    private int type;
    private String val1;
    private String val2;

    private int jobpostId;
    private int clientId;
    private int clientassetId;
    private int positionId;
    private int gradeId;
    private int countryId;
    private int cityId;
    private int qualificationId;
    private int noofopening;
    private int candidateId;
    private int showflag;
    private int assettypeId;

    private double minexp;
    private double maxexp;
    private double exp;

    private String name;
    private String client;
    private String clientasset;
    private String position;
    private String grade;
    private String country;
    private String city;
    private String nationality;
    private String language;
    private String gender;
    private String qualification;
    private String poston;
    private String code;
    private String degree;
    private String company;
    private String statusvalue;

    public ShortlistingInfo(int type, String val1, String val2, int showflag) {
        this.type = type;
        this.val1 = val1;
        this.val2 = val2;
        this.showflag = showflag;
    }

    public ShortlistingInfo(int clientId, String client, int clientassetId, String clientasset, int positionId, String position, int gradeId, String grade,
            int countryId, String country, int cityId, String city, double minexp, double maxexp, String nationality, String language, String gender,
            int qualificationId, String qualification, int noofopening, String poston, String code, String statusvalue,int assettypeId) {
        this.clientId = clientId;
        this.client = client;
        this.clientassetId = clientassetId;
        this.clientasset = clientasset;
        this.positionId = positionId;
        this.position = position;
        this.gradeId = gradeId;
        this.grade = grade;
        this.countryId = countryId;
        this.country = country;
        this.cityId = cityId;
        this.city = city;
        this.minexp = minexp;
        this.maxexp = maxexp;
        this.nationality = nationality;
        this.language = language;
        this.gender = gender;
        this.qualificationId = qualificationId;
        this.qualification = qualification;
        this.noofopening = noofopening;
        this.poston = poston;
        this.code = code;
        this.statusvalue = statusvalue;
        this.assettypeId = assettypeId;
    }

    public ShortlistingInfo(int candidateId, String name, String position, String grade, String degree, String qualification, String company, double exp) {
        this.candidateId = candidateId;
        this.name = name;
        this.position = position;
        this.grade = grade;
        this.degree = degree;
        this.qualification = qualification;
        this.company = company;
        this.exp = exp;
    }

    public ShortlistingInfo(int jobpostId, int clientId, int clientassetId, int status, int uId) {
        this.jobpostId = jobpostId;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.status = status;
        this.userId = uId;

    }

    public int getStatus() {
        return status;
    }

    public int getuId() {
        return uId;
    }

    public int getUserId() {
        return userId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getClientassetId() {
        return clientassetId;
    }

    public int getPositionId() {
        return positionId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public int getCountryId() {
        return countryId;
    }

    public int getCityId() {
        return cityId;
    }

    public int getQualificationId() {
        return qualificationId;
    }

    public int getNoofopening() {
        return noofopening;
    }

    public double getMinexp() {
        return minexp;
    }

    public double getMaxexp() {
        return maxexp;
    }

    public String getClient() {
        return client;
    }

    public String getClientasset() {
        return clientasset;
    }

    public String getPosition() {
        return position;
    }

    public String getGrade() {
        return grade;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getNationality() {
        return nationality;
    }

    public String getLanguage() {
        return language;
    }

    public String getGender() {
        return gender;
    }

    public String getQualification() {
        return qualification;
    }

    public String getPoston() {
        return poston;
    }

    public String getCode() {
        return code;
    }

    public int getType() {
        return type;
    }

    public String getVal1() {
        return val1;
    }

    public String getVal2() {
        return val2;
    }

    public String getName() {
        return name;
    }

    public double getExp() {
        return exp;
    }

    public String getDegree() {
        return degree;
    }

    public String getCompany() {
        return company;
    }

    public int getCandidateId() {
        return candidateId;
    }

    /**
     * @return the jobpostId
     */
    public int getJobpostId() {
        return jobpostId;
    }

    /**
     * @param showflag the showflag to set
     */
    public void setShowflag(int showflag) {
        this.showflag = showflag;
    }

    /**
     * @return the showflag
     */
    public int getShowflag() {
        return showflag;
    }

    /**
     * @return the statusvalue
     */
    public String getStatusvalue() {
        return statusvalue;
    }

    /**
     * @return the assettypeId
     */
    public int getAssettypeId() {
        return assettypeId;
    }

}
