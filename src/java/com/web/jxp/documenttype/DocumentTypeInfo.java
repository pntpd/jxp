package com.web.jxp.documenttype;

public class DocumentTypeInfo {

    private int documentTypeId;
    private String name;
    private int status;
    private int userId;
    private int ddlValue;
    private String ddlLabel;
    private String docFormat;
    private String docModule;
    private String docsubModule;
    private int mflag;

    //for ddl
    public DocumentTypeInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public DocumentTypeInfo(int documentTypeId, String name, int status, int userId) {
        this.documentTypeId = documentTypeId;
        this.name = name;
        this.status = status;
        this.userId = userId;
    }

    //Index
    public DocumentTypeInfo(int documentTypeId, String name, String docformat, String moduletype, int status, int userId, String submodule) {
        this.documentTypeId = documentTypeId;
        this.name = name;
        this.docFormat = docformat;
        this.docModule = moduletype;
        this.status = status;
        this.userId = userId;
        this.docsubModule = submodule;
    }

    public DocumentTypeInfo(int documentTypeId, String name, String docformat, String moduletype, int status, int userId) {
        this.documentTypeId = documentTypeId;
        this.name = name;
        this.docFormat = docformat;
        this.docModule = moduletype;
        this.status = status;
        this.userId = userId;
        // this.docsubModule = submodule;
    }

    //for view
    public DocumentTypeInfo(int documentTypeId, String name, int status, 
            int userId, String docformat, String docmodule, String docsubmodule, int mflag)
    {
        this.documentTypeId = documentTypeId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.docFormat = docformat;
        this.docModule = docmodule;
        this.docsubModule = docsubmodule;
        this.mflag = mflag;
    }

    // For Edit
    public DocumentTypeInfo(int documentTypeId, String name, int status, String docformat, 
            String docmodule, String docsubmodule, int mflag) {
        this.documentTypeId = documentTypeId;
        this.name = name;
        this.status = status;
        this.docFormat = docformat;
        this.docModule = docmodule;
        this.docsubModule = docsubmodule;
        this.mflag = mflag;
    }

    // For Add
    public DocumentTypeInfo(int documentTypeId, String name, String format, String module, 
            String submodule, int status, int userId, int mflag) {
        this.documentTypeId = documentTypeId;
        this.name = name;
        this.docFormat = format;
        this.status = status;
        this.userId = userId;
        this.docModule = module;
        this.docsubModule = submodule;
        this.mflag = mflag;
    }

    public String getDocFormat() {
        return docFormat;
    }

    public String getDocModule() {
        return docModule;
    }

    public String getDocsubModule() {
        return docsubModule;
    }

    /**
     * @return the documentTypeId
     */
    public int getDocumentTypeId() {
        return documentTypeId;
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
     * @return the mflag
     */
    public int getMflag() {
        return mflag;
    }
}
