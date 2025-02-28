package com.web.jxp.verification;

public class VerificationInfo {

    private int verificationId;

    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;

    private int vstatusId;
    private int flagav;
    private int flagstatusId;

    private String Ids;
    private String enrollon;
    private String name;
    private String position;
    private String flagstatus;
    private String filename;
    private String date;
    private String authority;

    public VerificationInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public VerificationInfo(int ddlValue, int verificationId, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.verificationId = verificationId;
        this.ddlLabel = ddlLabel;
    }

    public VerificationInfo(int verificationId, String Ids, String enrollon, String name, String position, String flagstatus,
            int status, int userId, int flagav,int flagstatusId) {
        this.verificationId = verificationId;
        this.Ids = Ids;
        this.enrollon = enrollon;
        this.name = name;
        this.position = position;
        this.flagstatus = flagstatus;
        this.status = status;
        this.userId = userId;
        this.flagav = flagav;
        this.flagstatusId = flagstatusId;
    }

    public VerificationInfo(int vstatusId, String filename, String date, String authority, int status, String name) {
        this.vstatusId = vstatusId;
        this.filename = filename;
        this.date = date;
        this.authority = authority;
        this.status = status;
        this.name = name;
    }

    public int getVerificationId() {
        return verificationId;
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

    public String getIds() {
        return Ids;
    }

    public String getEnrollon() {
        return enrollon;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getFlagstatus() {
        return flagstatus;
    }

    public int getVstatusId() {
        return vstatusId;
    }

    public String getFilename() {
        return filename;
    }

    public String getDate() {
        return date;
    }

    public String getAuthority() {
        return authority;
    }

    /**
     * @return the flagav
     */
    public int getFlagav() {
        return flagav;
    }

    /**
     * @return the flagstatusId
     */
    public int getFlagstatusId() {
        return flagstatusId;
    }

}
