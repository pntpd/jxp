package com.web.jxp.rejectionreason;

public class RejectionreasonInfo {

    private int rejectionreasonId;
    private String name;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;
    private int rejectionType;

    //for ddl
    public RejectionreasonInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    // For add
    public RejectionreasonInfo(int rejectionreasonId, String name, int status, int userId, int rejectiontype) {
        this.rejectionreasonId = rejectionreasonId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.rejectionType = rejectiontype;
    }

    //For Index
    public RejectionreasonInfo(int rejectionreasonId, String name, int type, int status, int userId, int a, String s) {
        this.rejectionreasonId = rejectionreasonId;
        this.name = name;
        this.rejectionType = type;
        this.status = status;
        this.userId = userId;

    }

    //Excel
    public RejectionreasonInfo(int rejectionreasonId, String name, int status, int rejectiontype) {
        this.rejectionreasonId = rejectionreasonId;
        this.name = name;
        this.status = status;
        this.rejectionType = rejectiontype;
    }

    //For View
    //rejectionreasonId, name,rejectiontype, status, 0,0,0
    public RejectionreasonInfo(int rejectionreasonId, String name, int rejectiontype, int status, int userId, int b, int c) {
        this.rejectionreasonId = rejectionreasonId;
        this.name = name;
        this.rejectionType = rejectiontype;
        this.status = status;
        this.userId = userId;

    }

    //For Edit
    public RejectionreasonInfo(int rejectionreasonId, String name, int rejectiontype, int status, int userId, int d) {
        this.rejectionreasonId = rejectionreasonId;
        this.name = name;
        this.rejectionType = rejectiontype;
        this.status = status;
        this.userId = userId;

    }

    public int getRejectionType() {
        return rejectionType;
    }

    /**
     * @return the rejectionreasonId
     */
    public int getRejectionreasonId() {
        return rejectionreasonId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
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
}
