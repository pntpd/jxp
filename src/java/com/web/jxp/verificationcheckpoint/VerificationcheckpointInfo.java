package com.web.jxp.verificationcheckpoint;

public class VerificationcheckpointInfo {

    private int verificationcheckpointId;
    private String verificationcheckpointName;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;
    private String verificationcheckpointcode;
    private int tabid;
    private String displaynote;
    private int minverification;
    private String tabname;

    //for ddl
    public VerificationcheckpointInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public VerificationcheckpointInfo(int verificationcheckpointId, String name, int tabid, int minverification, String displaynote, int status, int userId) {
        this.verificationcheckpointId = verificationcheckpointId;
        this.verificationcheckpointName = name;
        this.tabid = tabid;
        this.minverification = minverification;
        this.displaynote = displaynote;
        this.status = status;
        this.userId = userId;
    }

    public VerificationcheckpointInfo(int verificationcheckpointId, String verificationcheckpointname, int Status) {
        this.verificationcheckpointId = verificationcheckpointId;
        this.verificationcheckpointName = verificationcheckpointname;
        this.status = Status;
    }

    public VerificationcheckpointInfo(int verificationcheckpointId, String name, String tabname, String displaynote, int minverification, int status, int userId) {
        this.verificationcheckpointId = verificationcheckpointId;
        this.verificationcheckpointName = name;
        this.tabname = tabname;
        this.displaynote = displaynote;
        this.minverification = minverification;
        this.status = status;
        this.userId = userId;

    }

    public VerificationcheckpointInfo(int verificationcheckpointId, String name, int tabid, String displaynote, int minverification, int status) {
        this.verificationcheckpointId = verificationcheckpointId;
        this.verificationcheckpointName = name;
        this.tabid = tabid;
        this.displaynote = displaynote;
        this.minverification = minverification;
        this.status = status;
    }

    public VerificationcheckpointInfo(String name, String tabname, String displaynotes, int minverification, int status) {
        this.verificationcheckpointName = name;
        this.tabname = tabname;
        this.displaynote = displaynotes;
        this.minverification = minverification;
        this.status = status;
    }

    public String getTabname() {
        return tabname;
    }

    /**
     * @return the verificationcheckpointId
     */
    public int getVerificationcheckpointId() {
        return verificationcheckpointId;
    }

    /**
     * @return the name
     */
    public String getVerificationcheckpointName() {
        return verificationcheckpointName;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
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

    public String getVerificationcheckpointcode() {
        return verificationcheckpointcode;
    }

    public int getTabid() {
        return tabid;
    }

    public String getDisplaynote() {
        return displaynote;
    }

    public int getMinverification() {
        return minverification;
    }

}
