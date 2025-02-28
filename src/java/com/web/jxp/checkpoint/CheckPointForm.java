package com.web.jxp.checkpoint;

import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class CheckPointForm extends ActionForm {

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    private int checkpointId;
    private int ctp;
    private int status;

    private String search;
    private String doCancel;
    private String doModify;
    private String doAdd;
    private String doSave;
    private String doView;
    private String doAddMore;

    private Collection client;
    private Collection clientasset;
    private Collection position;
    private Collection grade;
  
    private int clientId;
    private int clientassetId;
    private int positionId;
    private int gradeId;
    private int checkFlag;
    
    private String positionname;
    private String displaynote;
    private String code;
    private String name;

    public int getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(int checkpointId) {
        this.checkpointId = checkpointId;
    }

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Collection getClient() {
        return client;
    }

    public void setClient(Collection client) {
        this.client = client;
    }

    public Collection getClientasset() {
        return clientasset;
    }

    public void setClientasset(Collection clientasset) {
        this.clientasset = clientasset;
    }

    public Collection getPosition() {
        return position;
    }

    public void setPosition(Collection position) {
        this.position = position;
    }

    public Collection getGrade() {
        return grade;
    }

    public void setGrade(Collection grade) {
        this.grade = grade;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientassetId() {
        return clientassetId;
    }

    public void setClientassetId(int clientassetId) {
        this.clientassetId = clientassetId;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    /**
     * @return the positionname
     */
    public String getPositionname() {
        return positionname;
    }

    /**
     * @param positionname the positionname to set
     */
    public void setPositionname(String positionname) {
        this.positionname = positionname;
    }

    /**
     * @return the displaynote
     */
    public String getDisplaynote() {
        return displaynote;
    }

    /**
     * @param displaynote the displaynote to set
     */
    public void setDisplaynote(String displaynote) {
        this.displaynote = displaynote;
    }

    /**
     * @return the checkFlag
     */
    public int getCheckFlag() {
        return checkFlag;
    }

    /**
     * @param checkFlag the checkFlag to set
     */
    public void setCheckFlag(int checkFlag) {
        this.checkFlag = checkFlag;
    }

    /**
     * @return the doAddMore
     */
    public String getDoAddMore() {
        return doAddMore;
    }

    /**
     * @param doAddMore the doAddMore to set
     */
    public void setDoAddMore(String doAddMore) {
        this.doAddMore = doAddMore;
    }
    
    
}
