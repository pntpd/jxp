package com.web.jxp.resumetemplate;

import java.util.Collection;

public class ResumetemplateInfo {

    private int resumetemplateId;
    private String name;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;
    private int clientId;
    private int clientIdIndex;
    private String clientname;
    private String description;
    private String deschidden;
    private String description2;
    private String description3;
    private String expColumn;
    private String eduColumn;
    private String docColumn;
    private String trainingColumn;
    private String filename;
    private int temptype;
    private int exptype;
    private int edutype;    
    private int doctype;    
    private int certtype;  
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

    //for ddl
    public ResumetemplateInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public ResumetemplateInfo(int resumetemplateId, String name, int status, int userId) {
        this.resumetemplateId = resumetemplateId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    public ResumetemplateInfo(int resumetemplateId, String name, int status, String clientname) {
        this.resumetemplateId = resumetemplateId;
        this.name = name;
        this.status = status;
        this.clientname = clientname;
    }

    public ResumetemplateInfo(int resumetemplateId, String name, int status, int userId, String clientname, int a) {
        this.resumetemplateId = resumetemplateId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientname = clientname;

    }

    public ResumetemplateInfo(int resumetemplateId, String name, int status, int userId, String clientname) {
        this.resumetemplateId = resumetemplateId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientname = clientname;
    }

    public ResumetemplateInfo(int resumetemplateId, String name, int status, int userId, String clientname,
            String description, String workexp, String edudetail, String docdetail, String certdetail) {
        this.resumetemplateId = resumetemplateId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientname = clientname;
        this.description = description;
        this.expColumn = workexp;
        this.eduColumn = edudetail;
        this.docColumn = docdetail;
        this.trainingColumn = certdetail;//view

    }

    //View
    public ResumetemplateInfo(int resumetemplateId, String name, int status, int userId, String clientname,
            String description, String workexp, String edudetail, String docdetail, String certdetail,
            int templatetype, String description2, String description3, String filename,
            String label1, String label2, String label3, String label4, String label5, String label6, String label7, 
            String label8, String label9, String label10, String label11, String label12, String label13, String label14,
            String label15, String label16, String label17, String label18, String label19, String label20, String label21, 
            String label22, String label23, String label24, String label25, String label26, int edutype, int doctype, int certtype) {
        this.resumetemplateId = resumetemplateId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientname = clientname;
        this.description = description;
        this.expColumn = workexp;
        this.eduColumn = edudetail;
        this.docColumn = docdetail;
        this.trainingColumn = certdetail;
        this.temptype = templatetype;
        this.description2 = description2;
        this.description3 = description3;
        this.filename = filename;
        this.label1  = label1 ;
        this.label2  = label2 ;
        this.label3  = label3 ;
        this.label4  = label4 ;
        this.label5  = label5 ;
        this.label6  = label6 ;
        this.label7  = label7 ;
        this.label8  = label8 ;
        this.label9  = label9 ;
        this.label10 = label10;
        this.label11 = label11;
        this.label12 = label12;
        this.label13 = label13;
        this.label14 = label14;
        this.label15 = label15;
        this.label16 = label16;
        this.label17 = label17;
        this.label18 = label18;
        this.label19 = label19;
        this.label20 = label20;
        this.label21 = label21;
        this.label22 = label22;
        this.label23 = label23;
        this.label24 = label24;
        this.label25 = label25;
        this.label26 = label26;
        this.edutype = edutype;
        this.doctype = doctype;
        this.certtype = certtype;
    }

    public ResumetemplateInfo(int resumetemplateId, String name, int status, int userId, int clientId, String description, int b) {
        this.resumetemplateId = resumetemplateId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientId = clientId;
        this.description = description;
    }

    //Edit
    public ResumetemplateInfo(int resumetemplateId, String name, int clientid, String description, int status,
            String workexp, String edudetail, String docdetail, String certificate) {
        this.resumetemplateId = resumetemplateId;
        this.name = name;
        this.clientId = clientid;
        this.description = description;
        this.status = status;
        this.expColumn = workexp;
        this.eduColumn = edudetail;
        this.docColumn = docdetail;
        this.trainingColumn = certificate;
    }

    
    //Modify
    public ResumetemplateInfo(int resumetemplateId, String name, int clientid, String description, int status,
            String workexp, String edudetail, String docdetail, String certificate, int temptype, String description2, 
            String description3, String filename, int exptype, String label1, String label2, String label3, String label4,
    String label5, String label6, String label7, String label8, String label9, String label10, String label11, String label12, String label13,
    String label14, String label15, String label16, String label17, String label18, String label19, String label20, String label21, String label22,
    String label23, String label24, String label25, String label26, int edutype, int doctype, int certtype) {
        this.resumetemplateId = resumetemplateId;
        this.name = name;
        this.clientId = clientid;
        this.description = description;
        this.status = status;
        this.expColumn = workexp;
        this.eduColumn = edudetail;
        this.docColumn = docdetail;
        this.trainingColumn = certificate;
        this.temptype = temptype;
        this.description2 = description2;
        this.description3 = description3;
        this.filename = filename;
        this.exptype = exptype;
        this.label1  = label1 ;
        this.label2  = label2 ;
        this.label3  = label3 ;
        this.label4  = label4 ;
        this.label5  = label5 ;
        this.label6  = label6 ;
        this.label7  = label7 ;
        this.label8  = label8 ;
        this.label9  = label9 ;
        this.label10 = label10;
        this.label11 = label11;
        this.label12 = label12;
        this.label13 = label13;
        this.label14 = label14;
        this.label15 = label15;
        this.label16 = label16;
        this.label17 = label17;
        this.label18 = label18;
        this.label19 = label19;
        this.label20 = label20;
        this.label21 = label21;
        this.label22 = label22;
        this.label23 = label23;
        this.label24 = label24;
        this.label25 = label25;
        this.label26 = label26;
        this.edutype = edutype;
        this.doctype = doctype;
        this.certtype = certtype;
    }

    //wexpcolumn, candeducolumn, canddoccolumn, ctrainingcolumn
    public ResumetemplateInfo(int resumetemplateId, String name, int status, int userId, int clientId, String description,
            String expcolumn, String candeducolumn, String canddoccolumn, String ctrainingcolumn, int teplateType,
            String description2, String description3, String fileName, int exptype, String label1, String label2, String label3, String label4,
            String label5, String label6, String label7, String label8, String label9, String label10, String label11, String label12, String label13,
            String label14, String label15, String label16, String label17, String label18, String label19, String label20, String label21, String label22,
            String label23, String label24, String label25, String label26, int edutype, int doctype, int certtype) {
        this.resumetemplateId = resumetemplateId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientId = clientId;
        this.description = description;
        this.expColumn = expcolumn;
        this.eduColumn = candeducolumn;
        this.docColumn = canddoccolumn;
        this.trainingColumn = ctrainingcolumn;
        this.temptype = teplateType;
        this.description2 = description2;
        this.description3 = description3;
        this.filename = fileName;
        this.exptype = exptype;
        this.label1  = label1 ;
        this.label2  = label2 ;
        this.label3  = label3 ;
        this.label4  = label4 ;
        this.label5  = label5 ;
        this.label6  = label6 ;
        this.label7  = label7 ;
        this.label8  = label8 ;
        this.label9  = label9 ;
        this.label10 = label10;
        this.label11 = label11;
        this.label12 = label12;
        this.label13 = label13;
        this.label14 = label14;
        this.label15 = label15;
        this.label16 = label16;
        this.label17 = label17;
        this.label18 = label18;
        this.label19 = label19;
        this.label20 = label20;
        this.label21 = label21;
        this.label22 = label22;
        this.label23 = label23;
        this.label24 = label24;
        this.label25 = label25;
        this.label26 = label26;
        this.edutype = edutype;
        this.doctype = doctype;
        this.certtype = certtype;
    }

    public ResumetemplateInfo(int resumetemplateId, String name, int status, int userId, int clientId, String description,
            String expcolumn, String candeducolumn, String canddoccolumn, String ctrainingcolumn) {
        this.resumetemplateId = resumetemplateId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.clientId = clientId;
        this.description = description;
        this.expColumn = expcolumn;
        this.eduColumn = candeducolumn;
        this.docColumn = canddoccolumn;
        this.trainingColumn = ctrainingcolumn;
    }

    public int getTemptype() {
        return temptype;
    }

    public String getTrainingColumn() {
        return trainingColumn;
    }

    public String getDocColumn() {
        return docColumn;
    }

    public String getEduColumn() {
        return eduColumn;
    }

    public String getExpColumn() {
        return expColumn;
    }

    public String getDeschidden() {
        return deschidden;
    }

    public String getDescription() {
        return description;
    }

    public String getClientname() {
        return clientname;
    }

    public int getClientId() {
        return clientId;
    }
    
    public int getClientIdIndex() {
        return clientIdIndex;
    }

    /**
     * @return the resumetemplateId
     */
    public int getResumetemplateId() {
        return resumetemplateId;
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

    /**
     * @return the description2
     */
    public String getDescription2() {
        return description2;
    }

    /**
     * @return the description3
     */
    public String getDescription3() {
        return description3;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }
    /**
     * @return the exptype
     */
    public int getExptype() {
        return exptype;
    }
    /**
     * @return the label1
     */
    public String getLabel1() {
        return label1;
    }

    /**
     * @return the label2
     */
    public String getLabel2() {
        return label2;
    }

    /**
     * @return the label3
     */
    public String getLabel3() {
        return label3;
    }

    /**
     * @return the label4
     */
    public String getLabel4() {
        return label4;
    }

    /**
     * @return the label5
     */
    public String getLabel5() {
        return label5;
    }

    /**
     * @return the label6
     */
    public String getLabel6() {
        return label6;
    }

    /**
     * @return the label7
     */
    public String getLabel7() {
        return label7;
    }

    /**
     * @return the label8
     */
    public String getLabel8() {
        return label8;
    }

    /**
     * @return the label9
     */
    public String getLabel9() {
        return label9;
    }

    /**
     * @return the label10
     */
    public String getLabel10() {
        return label10;
    }

    /**
     * @return the label11
     */
    public String getLabel11() {
        return label11;
    }

    /**
     * @return the label12
     */
    public String getLabel12() {
        return label12;
    }

    /**
     * @return the label13
     */
    public String getLabel13() {
        return label13;
    }

    /**
     * @return the label14
     */
    public String getLabel14() {
        return label14;
    }

    /**
     * @return the label15
     */
    public String getLabel15() {
        return label15;
    }

    /**
     * @return the label16
     */
    public String getLabel16() {
        return label16;
    }

    /**
     * @return the label17
     */
    public String getLabel17() {
        return label17;
    }

    /**
     * @return the label18
     */
    public String getLabel18() {
        return label18;
    }

    /**
     * @return the label19
     */
    public String getLabel19() {
        return label19;
    }

    /**
     * @return the label20
     */
    public String getLabel20() {
        return label20;
    }

    /**
     * @return the label21
     */
    public String getLabel21() {
        return label21;
    }

    /**
     * @return the label22
     */
    public String getLabel22() {
        return label22;
    }

    /**
     * @return the label23
     */
    public String getLabel23() {
        return label23;
    }

    /**
     * @return the label24
     */
    public String getLabel24() {
        return label24;
    }

    /**
     * @return the label25
     */
    public String getLabel25() {
        return label25;
    }

    /**
     * @return the label26
     */
    public String getLabel26() {
        return label26;
    }

    /**
     * @return the edutype
     */
    public int getEdutype() {
        return edutype;
    }

    /**
     * @return the doctype
     */
    public int getDoctype() {
        return doctype;
    }

    /**
     * @return the certtype
     */
    public int getCerttype() {
        return certtype;
    }
}
