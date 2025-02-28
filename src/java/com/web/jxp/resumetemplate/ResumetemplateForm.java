package com.web.jxp.resumetemplate;
import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

public class ResumetemplateForm extends ActionForm
{
    private int resumetemplateId;
    private int status;
    private String search;
    private String doCancel;
    private String doModify;
    private String name;
    private String doAdd;
    private String doSave;
    private String doView;    
    private int ctp;
    private int clientId;
    private int clientIdIndex;
    private Collection clients;
    private int temptype;
    private String description;
    private String deschidden;
    private String description2;
    private String description3;
    private String expColumn[];
    private String eduColumn[];
    private String docColumn[];
    private String trainingColumn[];
    private FormFile resumefile;
    private String resumefilehidden;
    private String doDeleteFile;
    private int exptype;    
    private int edutype;    
    private int doctype;    
    private int certtype;    
    private String experience;
    private String roles;
    private String label1;
    private String label2;
    private String label3;
    private String label4;
    private String label5;
    private String label6;
    private String label7;
    private String label8;
    private String label9;
    private String label10;
    private String label11;
    private String label12;
    private String label13;
    private String label14;
    private String label15;
    private String label16;
    private String label17;
    private String label18;
    private String label19;
    private String label20;
    private String label21;
    private String label22;
    private String label23;
    private String label24;
    private String label25;
    private String label26;

    public String[] getTrainingColumn() {
        return trainingColumn;
    }

    public void setTrainingColumn(String[] trainingColumn) {
        this.trainingColumn = trainingColumn;
    }

    public String[] getDocColumn() {
        return docColumn;
    }

    public void setDocColumn(String[] docColumn) {
        this.docColumn = docColumn;
    }

    public String[] getEduColumn() {
        return eduColumn;
    }

    public void setEduColumn(String[] eduColumn) {
        this.eduColumn = eduColumn;
    }

    public String[] getExpColumn() {
        return expColumn;
    }

    public void setExpColumn(String[] expColumn) {
        this.expColumn = expColumn;
    }

    public String getDeschidden() {
        return deschidden;
    }

    public void setDeschidden(String deschidden) {
        this.deschidden = deschidden;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the resumetemplateId
     */
    public int getResumetemplateId() {
        return resumetemplateId;
    }

    /**
     * @param resumetemplateId the resumetemplateId to set
     */
    public void setResumetemplateId(int resumetemplateId) {
        this.resumetemplateId = resumetemplateId;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
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

    /**
     * @return the doAdd
     */
    public String getDoAdd() {
        return doAdd;
    }

    /**
     * @param doAdd the doAdd to set
     */
    public void setDoAdd(String doAdd) {
        this.doAdd = doAdd;
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
     * @return the ctp
     */
    public int getCtp() {
        return ctp;
    }

    /**
     * @param ctp the ctp to set
     */
    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    /**
     * @return the temptype
     */
    public int getTemptype() {
        return temptype;
    }

    /**
     * @param temptype the temptype to set
     */
    public void setTemptype(int temptype) {
        this.temptype = temptype;
    }

    /**
     * @return the description2
     */
    public String getDescription2() {
        return description2;
    }

    /**
     * @param description2 the description2 to set
     */
    public void setDescription2(String description2) {
        this.description2 = description2;
    }
    /**
     * @return the description3
     */
    public String getDescription3() {
        return description3;
    }

    /**
     * @param description3 the description3 to set
     */
    public void setDescription3(String description3) {
        this.description3 = description3;
    }

    /**
     * @return the resumefile
     */
    public FormFile getResumefile() {
        return resumefile;
    }

    /**
     * @param resumefile the resumefile to set
     */
    public void setResumefile(FormFile resumefile) {
        this.resumefile = resumefile;
    }

    /**
     * @return the resumefilehidden
     */
    public String getResumefilehidden() {
        return resumefilehidden;
    }

    /**
     * @param resumefilehidden the resumefilehidden to set
     */
    public void setResumefilehidden(String resumefilehidden) {
        this.resumefilehidden = resumefilehidden;
    }

    /**
     * @return the doDeleteFile
     */
    public String getDoDeleteFile() {
        return doDeleteFile;
    }

    /**
     * @param doDeleteFile the doDeleteFile to set
     */
    public void setDoDeleteFile(String doDeleteFile) {
        this.doDeleteFile = doDeleteFile;
    }

    /**
     * @return the exptype
     */
    public int getExptype() {
        return exptype;
    }

    /**
     * @param exptype the exptype to set
     */
    public void setExptype(int exptype) {
        this.exptype = exptype;
    }

    /**
     * @return the roles
     */
    public String getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(String roles) {
        this.roles = roles;
    }

    /**
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * @return the clientIdIndex
     */
    public int getClientIdIndex() {
        return clientIdIndex;
    }

    /**
     * @param clientIdIndex the clientIdIndex to set
     */
    public void setClientIdIndex(int clientIdIndex) {
        this.clientIdIndex = clientIdIndex;
    }

    /**
     * @return the clients
     */
    public Collection getClients() {
        return clients;
    }

    /**
     * @param clients the clients to set
     */
    public void setClients(Collection clients) {
        this.clients = clients;
    }

    /**
     * @return the experience
     */
    public String getExperience() {
        return experience;
    }

    /**
     * @param experience the experience to set
     */
    public void setExperience(String experience) {
        this.experience = experience;
    }

    /**
     * @return the label1
     */
    public String getLabel1() {
        return label1;
    }

    /**
     * @param label1 the label1 to set
     */
    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    /**
     * @return the label2
     */
    public String getLabel2() {
        return label2;
    }

    /**
     * @param label2 the label2 to set
     */
    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    /**
     * @return the label3
     */
    public String getLabel3() {
        return label3;
    }

    /**
     * @param label3 the label3 to set
     */
    public void setLabel3(String label3) {
        this.label3 = label3;
    }

    /**
     * @return the label4
     */
    public String getLabel4() {
        return label4;
    }

    /**
     * @param label4 the label4 to set
     */
    public void setLabel4(String label4) {
        this.label4 = label4;
    }

    /**
     * @return the label5
     */
    public String getLabel5() {
        return label5;
    }

    /**
     * @param label5 the label5 to set
     */
    public void setLabel5(String label5) {
        this.label5 = label5;
    }

    /**
     * @return the label6
     */
    public String getLabel6() {
        return label6;
    }

    /**
     * @param label6 the label6 to set
     */
    public void setLabel6(String label6) {
        this.label6 = label6;
    }

    /**
     * @return the label7
     */
    public String getLabel7() {
        return label7;
    }

    /**
     * @param label7 the label7 to set
     */
    public void setLabel7(String label7) {
        this.label7 = label7;
    }

    /**
     * @return the label8
     */
    public String getLabel8() {
        return label8;
    }

    /**
     * @param label8 the label8 to set
     */
    public void setLabel8(String label8) {
        this.label8 = label8;
    }

    /**
     * @return the label9
     */
    public String getLabel9() {
        return label9;
    }

    /**
     * @param label9 the label9 to set
     */
    public void setLabel9(String label9) {
        this.label9 = label9;
    }

    /**
     * @return the label10
     */
    public String getLabel10() {
        return label10;
    }

    /**
     * @param label10 the label10 to set
     */
    public void setLabel10(String label10) {
        this.label10 = label10;
    }

    /**
     * @return the label11
     */
    public String getLabel11() {
        return label11;
    }

    /**
     * @param label11 the label11 to set
     */
    public void setLabel11(String label11) {
        this.label11 = label11;
    }

    /**
     * @return the label12
     */
    public String getLabel12() {
        return label12;
    }

    /**
     * @param label12 the label12 to set
     */
    public void setLabel12(String label12) {
        this.label12 = label12;
    }

    /**
     * @return the label13
     */
    public String getLabel13() {
        return label13;
    }

    /**
     * @param label13 the label13 to set
     */
    public void setLabel13(String label13) {
        this.label13 = label13;
    }

    /**
     * @return the label14
     */
    public String getLabel14() {
        return label14;
    }

    /**
     * @param label14 the label14 to set
     */
    public void setLabel14(String label14) {
        this.label14 = label14;
    }

    /**
     * @return the label15
     */
    public String getLabel15() {
        return label15;
    }

    /**
     * @param label15 the label15 to set
     */
    public void setLabel15(String label15) {
        this.label15 = label15;
    }

    /**
     * @return the label16
     */
    public String getLabel16() {
        return label16;
    }

    /**
     * @param label16 the label16 to set
     */
    public void setLabel16(String label16) {
        this.label16 = label16;
    }

    /**
     * @return the label17
     */
    public String getLabel17() {
        return label17;
    }

    /**
     * @param label17 the label17 to set
     */
    public void setLabel17(String label17) {
        this.label17 = label17;
    }

    /**
     * @return the label18
     */
    public String getLabel18() {
        return label18;
    }

    /**
     * @param label18 the label18 to set
     */
    public void setLabel18(String label18) {
        this.label18 = label18;
    }

    /**
     * @return the label19
     */
    public String getLabel19() {
        return label19;
    }

    /**
     * @param label19 the label19 to set
     */
    public void setLabel19(String label19) {
        this.label19 = label19;
    }

    /**
     * @return the label20
     */
    public String getLabel20() {
        return label20;
    }

    /**
     * @param label20 the label20 to set
     */
    public void setLabel20(String label20) {
        this.label20 = label20;
    }

    /**
     * @return the label21
     */
    public String getLabel21() {
        return label21;
    }

    /**
     * @param label21 the label21 to set
     */
    public void setLabel21(String label21) {
        this.label21 = label21;
    }

    /**
     * @return the label22
     */
    public String getLabel22() {
        return label22;
    }

    /**
     * @param label22 the label22 to set
     */
    public void setLabel22(String label22) {
        this.label22 = label22;
    }

    /**
     * @return the label23
     */
    public String getLabel23() {
        return label23;
    }

    /**
     * @param label23 the label23 to set
     */
    public void setLabel23(String label23) {
        this.label23 = label23;
    }

    /**
     * @return the label24
     */
    public String getLabel24() {
        return label24;
    }

    /**
     * @param label24 the label24 to set
     */
    public void setLabel24(String label24) {
        this.label24 = label24;
    }

    /**
     * @return the label25
     */
    public String getLabel25() {
        return label25;
    }

    /**
     * @param label25 the label25 to set
     */
    public void setLabel25(String label25) {
        this.label25 = label25;
    }

    /**
     * @return the label26
     */
    public String getLabel26() {
        return label26;
    }

    /**
     * @param label26 the label26 to set
     */
    public void setLabel26(String label26) {
        this.label26 = label26;
    }

    /**
     * @return the edutype
     */
    public int getEdutype() {
        return edutype;
    }

    /**
     * @param edutype the edutype to set
     */
    public void setEdutype(int edutype) {
        this.edutype = edutype;
    }

    /**
     * @return the doctype
     */
    public int getDoctype() {
        return doctype;
    }

    /**
     * @param doctype the doctype to set
     */
    public void setDoctype(int doctype) {
        this.doctype = doctype;
    }

    /**
     * @return the certtype
     */
    public int getCerttype() {
        return certtype;
    }

    /**
     * @param certtype the certtype to set
     */
    public void setCerttype(int certtype) {
        this.certtype = certtype;
    }
    
}