package com.web.jxp.checkpoint;

public class CheckPointInfo {

    private int checkpointId;

    private int status;
    private int uId;
    private int userId;
    private int ddlValue;
    private String ddlLabel;

    private int flag;
    private int clientId;
    private int clientassetId;
    private int positionId;
    private int gradeId;

    private String name;
    private String desc;
    private String clientname;
    private String clientassetname;
    private String positionname;
    private String grade;
    private String code;

    //for ddl
    public CheckPointInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public CheckPointInfo(int checkpointId, String name, String desc, String clientname, String clientassetname, String positionname, String grade, int flag,
            int status) {
        this.checkpointId = checkpointId;
        this.name = name;
        this.desc = desc;
        this.clientname = clientname;
        this.clientassetname = clientassetname;
        this.positionname = positionname;
        this.grade = grade;
        this.flag = flag;
        this.status = status;
    }

    public CheckPointInfo(int checkpointId, String name, String desc, int flag, int status, String clientname, String clientassetname, String positionname,
            String grade, int uId, String code) {
        this.checkpointId = checkpointId;
        this.name = name;
        this.desc = desc;
        this.flag = flag;
        this.status = status;
        this.clientname = clientname;
        this.clientassetname = clientassetname;
        this.positionname = positionname;
        this.grade = grade;
        this.uId = uId;
        this.code = code;
    }

    public CheckPointInfo(String name, String desc, int flag, int status, int clientId, int clientassetId, int positionId, int gradeId, int uId, String code,
            String positionname) {
        this.name = name;
        this.desc = desc;
        this.flag = flag;
        this.status = status;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.positionId = positionId;
        this.gradeId = gradeId;
        this.uId = uId;
        this.code = code;
        this.positionname = positionname;
    }

    public CheckPointInfo(int checkpointId, String name, int clientId, int clientassetId, int positionId, int gradeId, String description, int flag,
            int status, int uId) {
        this.checkpointId = checkpointId;
        this.name = name;
        this.clientId = clientId;
        this.clientassetId = clientassetId;
        this.positionId = positionId;
        this.gradeId = gradeId;
        this.desc = description;
        this.flag = flag;
        this.status = status;
        this.uId = uId;
    }

    public int getCheckpointId() {
        return checkpointId;
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

    public int getFlag() {
        return flag;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getClientname() {
        return clientname;
    }

    public String getClientassetname() {
        return clientassetname;
    }

    public String getPositionname() {
        return positionname;
    }

    public String getGrade() {
        return grade;
    }

    public int getuId() {
        return uId;
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

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
}
