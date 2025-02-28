package com.web.jxp.formality;

import java.util.Collection;

public class FormalityInfo {

    private int formalityId;
    private String name;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;
    private int candidateId;
    private Collection candiateIdList;
    private int candidateIdIndex;
    private String candidatename;
    private String description;
    private String deschidden;
    private int editor;
    private int temptype;
    private int attachment;
    private String formalityfilename;
    private String expColumn;
    private String eduColumn;
    private String docColumn;
    private String trainingColumn;
    private String languageColumn;
    private String vaccineColumn;
    private String filename;
    private String description2;
    private String description3;

    //for ddl
    public FormalityInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public FormalityInfo(int formalityId, String name, int status, int userId) {
        this.formalityId = formalityId;
        this.name = name;
        this.status = status;
        this.userId = userId;

    }

    public FormalityInfo(int formalityId, String name, int status, String clientname) {
        this.formalityId = formalityId;
        this.name = name;
        this.status = status;
        this.candidatename = clientname;
    }

    public FormalityInfo(int formalityId, String name, int status, int userId, String clientname, int a) {
        this.formalityId = formalityId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.candidatename = clientname;

    }

    public FormalityInfo(int formalityId, String name, int status, int userId, String candidatename) {
        this.formalityId = formalityId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.candidatename = candidatename;
    }

    //For view
    public FormalityInfo(int formalityId, String name, int status, String candidatename, String description, 
            int templatetype, String filename, String workexp, String edudetail, String docdetail, String certdetail,
            String language, String vaccine, String description2, String description3, String filename2 ) 
    {
        this.formalityId = formalityId;
        this.name = name;
        this.status = status;
        this.candidatename = candidatename;
        this.description = description;
        this.temptype = templatetype;
        this.formalityfilename = filename;
        this.expColumn = workexp;
        this.eduColumn = edudetail;
        this.docColumn = docdetail;
        this.trainingColumn = certdetail;
        this.languageColumn = language;
        this.vaccineColumn = vaccine;
        this.description2 = description2;
        this.description3 = description3;
        this.filename = filename2;
    }

    public FormalityInfo(int formalityId, String name, int status, int userId, int candidateId, String description, int b) {
        this.formalityId = formalityId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.candidateId = candidateId;
        this.description = description;
    }

    //Edit
    public FormalityInfo(int formalityId, String name, int candidateid, String description, 
            int status, int temptype, String formalityfile, String workexp, String edudetail,
            String docdetail, String certificate, String langauge, String vaccine,String description2, 
            String description3, String filename) 
    {
        this.formalityId = formalityId;
        this.name = name;
        this.candidateId = candidateid;
        this.description = description;
        this.status = status;
        this.temptype = temptype;
        this.formalityfilename = formalityfile;
        this.expColumn = workexp;
        this.eduColumn = edudetail;
        this.docColumn = docdetail;
        this.trainingColumn = certificate;
        this.languageColumn = langauge;
        this.vaccineColumn = vaccine;
        this.description2 = description2;
        this.description3 = description3;
        this.filename = filename;
    }

    //Add page
    public FormalityInfo(int formalityId, String name, int status, int userId, int candidateId, String description) {
        this.formalityId = formalityId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.candidateId = candidateId;
        this.description = description;
    }

    //For add
    public FormalityInfo(int formalityId, String name, int status, int userId, int candidateId,
            String description, String fileName, int temptype, String expcolumn, String candeducolumn, 
            String canddoccolumn, String ctrainingcolumn, String clangcolumn, String cvaccinecolumn,
            String description2, String description3, String filename2) 
    {
        this.formalityId = formalityId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.candidateId = candidateId;
        this.description = description;
        this.formalityfilename = fileName;
        this.temptype = temptype;
        this.expColumn = expcolumn;
        this.eduColumn = candeducolumn;
        this.docColumn = canddoccolumn;
        this.trainingColumn = ctrainingcolumn;
        this.languageColumn = clangcolumn;
        this.vaccineColumn = cvaccinecolumn;
        this.description2 = description2;
        this.description3 = description3;
        this.filename = filename2;
    }

    public int getTemptype() {
        return temptype;
    }

    public String getFormalityfilename() {
        return formalityfilename;
    }

    public int getEditor() {
        return editor;
    }

    public int getAttachment() {
        return attachment;
    }

    public String getDeschidden() {
        return deschidden;
    }

    public String getDescription() {
        return description;
    }

    public String getCandidatename() {
        return candidatename;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public Collection getCandiateIdList() {
        return candiateIdList;
    }

    public int getCandidateIdIndex() {
        return candidateIdIndex;
    }

    /**
     * @return the formalityId
     */
    public int getFormalityId() {
        return formalityId;
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
     * @return the expColumn
     */
    public String getExpColumn() {
        return expColumn;
    }

    /**
     * @return the eduColumn
     */
    public String getEduColumn() {
        return eduColumn;
    }

    /**
     * @return the docColumn
     */
    public String getDocColumn() {
        return docColumn;
    }

    /**
     * @return the trainingColumn
     */
    public String getTrainingColumn() {
        return trainingColumn;
    }

    /**
     * @return the languageColumn
     */
    public String getLanguageColumn() {
        return languageColumn;
    }

    /**
     * @return the vaccineColumn
     */
    public String getVaccineColumn() {
        return vaccineColumn;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
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
}
