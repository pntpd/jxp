package com.web.jxp.cassessment;

import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class CassessmentForm extends ActionForm {

    private int cassessmentId;
    private String search;
    private String doCancel;
    private String doModify;
    private String doView;
    private String doSave;
    private String doAssessorView;
    private String doSaveScore;
    private String doAssessorSave;
    private String doCancelAssessor;
    private String doRetake;

    private String doChange;

    private Collection position;
    private Collection passessments;
    private Collection assessors;

    private Collection aPositions;

    private int vstatusIndex;
    private int astatusIndex;
    private int positionIndex;
    private int scheduleIndex;
    private int type;
    private int clientIdIndex;
    private int countryIdIndex;
    private int assetIdIndex;
    private int ctp;
    private int candidateId;
    private int pAssessmentId;
    private int assessorId;
    private int coordinatorId;

    private int selCandTimeZone;
    private int selAssessTimeZone;
    private int selAssessor;
    private int selDuration;
    private int aPositionIndex;

    private String txtstartdate;
    private String txttime;
    private String selMode;
    private String txtDate;
    private String txtLocLink;
    private String txtremarks;

    private int selectRole;
    private int passessmentIndex;
    private int assessorIndex;
    private double hdnmarks;
    private int hdnminScore;
    private int hdnpassFlag;
    private int attemptCount;
    private int hdnParameterId[];
    private double marks[];
    private Collection assettypes;
    private int assettypeIndex;
    
    private String doViewAssessNow;
    private String doSaveAssessNow;
    private int radio1;
    private int radio2;
    private int radio3;
    private int radio4;
    private int radio5;
    private int radio6;
    private int radio7;
    private int radio8;
    private int radio9;
    private int radio10;
    private int radio11;
    private int radio12;
    private int radio13;
    private int radio14;
    private int radio15;
    private int radio16;
    private int radio17;
    private int radio18;
    private String cdescription;
    private int positionId;

    public String getDoChange() {
        return doChange;
    }

    public void setDoChange(String doChange) {
        this.doChange = doChange;
    }

    public Collection getPassessments() {
        return passessments;
    }

    public void setPassessments(Collection passessments) {
        this.passessments = passessments;
    }

    public Collection getAssessors() {
        return assessors;
    }

    public void setAssessors(Collection assessors) {
        this.assessors = assessors;
    }

    public int getSelectRole() {
        return selectRole;
    }

    public void setSelectRole(int selectRole) {
        this.selectRole = selectRole;
    }

    public int getPassessmentIndex() {
        return passessmentIndex;
    }

    public void setPassessmentIndex(int passessmentIndex) {
        this.passessmentIndex = passessmentIndex;
    }

    public int getAssessorIndex() {
        return assessorIndex;
    }

    public void setAssessorIndex(int assessorIndex) {
        this.assessorIndex = assessorIndex;
    }

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    public int getClientIdIndex() {
        return clientIdIndex;
    }

    public void setClientIdIndex(int clientIdIndex) {
        this.clientIdIndex = clientIdIndex;
    }

    public int getCountryIdIndex() {
        return countryIdIndex;
    }

    public void setCountryIdIndex(int countryIdIndex) {
        this.countryIdIndex = countryIdIndex;
    }

    public int getAssetIdIndex() {
        return assetIdIndex;
    }

    public void setAssetIdIndex(int assetIdIndex) {
        this.assetIdIndex = assetIdIndex;
    }

    public int getCassessmentId() {
        return cassessmentId;
    }

    /**
     * @param cassessmentId the cassessmentId to set
     */
    public void setCassessmentId(int cassessmentId) {
        this.cassessmentId = cassessmentId;
    }

    /**
     * @return the search
     */
    public String getSearch() {
        return search;
    }

    /**
     * @param search the search to set
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * @return the doCancel
     */
    public String getDoCancel() {
        return doCancel;
    }

    /**
     * @param doCancel the doCancel to set
     */
    public void setDoCancel(String doCancel) {
        this.doCancel = doCancel;
    }

    /**
     * @return the doModify
     */
    public String getDoModify() {
        return doModify;
    }

    /**
     * @param doModify the doModify to set
     */
    public void setDoModify(String doModify) {
        this.doModify = doModify;
    }

    /**
     * @return the doView
     */
    public String getDoView() {
        return doView;
    }

    /**
     * @param doView the doView to set
     */
    public void setDoView(String doView) {
        this.doView = doView;
    }

    /**
     * @return the positionIndex
     */
    public int getPositionIndex() {
        return positionIndex;
    }

    /**
     * @param positionIndex the positionIndex to set
     */
    public void setPositionIndex(int positionIndex) {
        this.positionIndex = positionIndex;
    }

    public Collection getPosition() {
        return position;
    }

    public void setPosition(Collection position) {
        this.position = position;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    public int getVstatusIndex() {
        return vstatusIndex;
    }

    public void setVstatusIndex(int vstatusIndex) {
        this.vstatusIndex = vstatusIndex;
    }

    public int getAstatusIndex() {
        return astatusIndex;
    }

    public void setAstatusIndex(int astatusIndex) {
        this.astatusIndex = astatusIndex;
    }

    /**
     * @return the candidateId
     */
    public int getCandidateId() {
        return candidateId;
    }

    /**
     * @param candidateId the candidateId to set
     */
    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    /**
     * @return the scheduleIndex
     */
    public int getScheduleIndex() {
        return scheduleIndex;
    }

    /**
     * @param scheduleIndex the scheduleIndex to set
     */
    public void setScheduleIndex(int scheduleIndex) {
        this.scheduleIndex = scheduleIndex;
    }

    /**
     * @return the doSave
     */
    public String getDoSave() {
        return doSave;
    }

    /**
     * @param doSave the doSave to set
     */
    public void setDoSave(String doSave) {
        this.doSave = doSave;
    }

    /**
     * @return the pAssessmentId
     */
    public int getpAssessmentId() {
        return pAssessmentId;
    }

    /**
     * @param pAssessmentId the pAssessmentId to set
     */
    public void setpAssessmentId(int pAssessmentId) {
        this.pAssessmentId = pAssessmentId;
    }

    public String getTxtstartdate() {
        return txtstartdate;
    }

    public void setTxtstartdate(String txtstartdate) {
        this.txtstartdate = txtstartdate;
    }

    public String getTxttime() {
        return txttime;
    }

    public void setTxttime(String txttime) {
        this.txttime = txttime;
    }

    public String getSelMode() {
        return selMode;
    }

    public void setSelMode(String selMode) {
        this.selMode = selMode;
    }

    public String getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(String txtDate) {
        this.txtDate = txtDate;
    }

    public String getTxtLocLink() {
        return txtLocLink;
    }

    public void setTxtLocLink(String txtLocLink) {
        this.txtLocLink = txtLocLink;
    }

    public int getSelCandTimeZone() {
        return selCandTimeZone;
    }

    public void setSelCandTimeZone(int selCandTimeZone) {
        this.selCandTimeZone = selCandTimeZone;
    }

    public int getSelAssessTimeZone() {
        return selAssessTimeZone;
    }

    public void setSelAssessTimeZone(int selAssessTimeZone) {
        this.selAssessTimeZone = selAssessTimeZone;
    }

    public int getSelAssessor() {
        return selAssessor;
    }

    public void setSelAssessor(int selAssessor) {
        this.selAssessor = selAssessor;
    }

    public int getSelDuration() {
        return selDuration;
    }

    public void setSelDuration(int selDuration) {
        this.selDuration = selDuration;
    }

    public String getDoAssessorView() {
        return doAssessorView;
    }

    public void setDoAssessorView(String doAssessorView) {
        this.doAssessorView = doAssessorView;
    }

    public Collection getaPositions() {
        return aPositions;
    }

    public void setaPositions(Collection aPositions) {
        this.aPositions = aPositions;
    }

    public int getaPositionIndex() {
        return aPositionIndex;
    }

    public void setaPositionIndex(int aPositionIndex) {
        this.aPositionIndex = aPositionIndex;
    }

    public int[] getHdnParameterId() {
        return hdnParameterId;
    }

    public void setHdnParameterId(int[] hdnParameterId) {
        this.hdnParameterId = hdnParameterId;
    }

    public String getTxtremarks() {
        return txtremarks;
    }

    public void setTxtremarks(String txtremarks) {
        this.txtremarks = txtremarks;
    }

    public int getHdnminScore() {
        return hdnminScore;
    }

    public void setHdnminScore(int hdnminScore) {
        this.hdnminScore = hdnminScore;
    }

    public int getHdnpassFlag() {
        return hdnpassFlag;
    }

    public void setHdnpassFlag(int hdnpassFlag) {
        this.hdnpassFlag = hdnpassFlag;
    }

    public String getDoAssessorSave() {
        return doAssessorSave;
    }

    public void setDoAssessorSave(String doAssessorSave) {
        this.doAssessorSave = doAssessorSave;
    }

    public String getDoSaveScore() {
        return doSaveScore;
    }

    public void setDoSaveScore(String doSaveScore) {
        this.doSaveScore = doSaveScore;
    }

    public double getHdnmarks() {
        return hdnmarks;
    }

    public void setHdnmarks(double hdnmarks) {
        this.hdnmarks = hdnmarks;
    }

    public double[] getMarks() {
        return marks;
    }

    public void setMarks(double[] marks) {
        this.marks = marks;
    }

    /**
     * @return the doCancelAssessor
     */
    public String getDoCancelAssessor() {
        return doCancelAssessor;
    }

    /**
     * @param doCancelAssessor the doCancelAssessor to set
     */
    public void setDoCancelAssessor(String doCancelAssessor) {
        this.doCancelAssessor = doCancelAssessor;
    }

    public int getAssessorId() {
        return assessorId;
    }

    public void setAssessorId(int assessorId) {
        this.assessorId = assessorId;
    }

    public int getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(int coordinatorId) {
        this.coordinatorId = coordinatorId;
    }

    /**
     * @return the doRetake
     */
    public String getDoRetake() {
        return doRetake;
    }

    /**
     * @param doRetake the doRetake to set
     */
    public void setDoRetake(String doRetake) {
        this.doRetake = doRetake;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    /**
     * @return the assettypes
     */
    public Collection getAssettypes() {
        return assettypes;
    }

    /**
     * @param assettypes the assettypes to set
     */
    public void setAssettypes(Collection assettypes) {
        this.assettypes = assettypes;
    }

    /**
     * @return the assettypeIndex
     */
    public int getAssettypeIndex() {
        return assettypeIndex;
    }

    /**
     * @param assettypeIndex the assettypeIndex to set
     */
    public void setAssettypeIndex(int assettypeIndex) {
        this.assettypeIndex = assettypeIndex;
    }

    public String getDoViewAssessNow() {
        return doViewAssessNow;
    }

    public void setDoViewAssessNow(String doViewAssessNow) {
        this.doViewAssessNow = doViewAssessNow;
    }

    public String getDoSaveAssessNow() {
        return doSaveAssessNow;
    }

    public void setDoSaveAssessNow(String doSaveAssessNow) {
        this.doSaveAssessNow = doSaveAssessNow;
    }

    public int getRadio1() {
        return radio1;
    }

    public void setRadio1(int radio1) {
        this.radio1 = radio1;
    }

    public int getRadio2() {
        return radio2;
    }

    public void setRadio2(int radio2) {
        this.radio2 = radio2;
    }

    public int getRadio3() {
        return radio3;
    }

    public void setRadio3(int radio3) {
        this.radio3 = radio3;
    }

    public int getRadio4() {
        return radio4;
    }

    public void setRadio4(int radio4) {
        this.radio4 = radio4;
    }

    public int getRadio5() {
        return radio5;
    }

    public void setRadio5(int radio5) {
        this.radio5 = radio5;
    }

    public int getRadio6() {
        return radio6;
    }

    public void setRadio6(int radio6) {
        this.radio6 = radio6;
    }

    public int getRadio7() {
        return radio7;
    }

    public void setRadio7(int radio7) {
        this.radio7 = radio7;
    }

    public int getRadio8() {
        return radio8;
    }

    public void setRadio8(int radio8) {
        this.radio8 = radio8;
    }

    public int getRadio9() {
        return radio9;
    }

    public void setRadio9(int radio9) {
        this.radio9 = radio9;
    }

    public int getRadio10() {
        return radio10;
    }

    public void setRadio10(int radio10) {
        this.radio10 = radio10;
    }

    public int getRadio11() {
        return radio11;
    }

    public void setRadio11(int radio11) {
        this.radio11 = radio11;
    }

    public int getRadio12() {
        return radio12;
    }

    public void setRadio12(int radio12) {
        this.radio12 = radio12;
    }

    public int getRadio13() {
        return radio13;
    }

    public void setRadio13(int radio13) {
        this.radio13 = radio13;
    }

    public int getRadio14() {
        return radio14;
    }

    public void setRadio14(int radio14) {
        this.radio14 = radio14;
    }

    public int getRadio15() {
        return radio15;
    }

    public void setRadio15(int radio15) {
        this.radio15 = radio15;
    }

    public int getRadio16() {
        return radio16;
    }

    public void setRadio16(int radio16) {
        this.radio16 = radio16;
    }

    public int getRadio17() {
        return radio17;
    }

    public void setRadio17(int radio17) {
        this.radio17 = radio17;
    }

    public int getRadio18() {
        return radio18;
    }

    public void setRadio18(int radio18) {
        this.radio18 = radio18;
    }

    public String getCdescription() {
        return cdescription;
    }

    public void setCdescription(String cdescription) {
        this.cdescription = cdescription;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }
}
