package com.web.jxp.formality;
import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

public class FormalityForm extends ActionForm
{
    private int formalityId;
    private int status;
    private String search;
    private String doCancel;
    private String doModify;
    private String name;
    private String doAdd;
    private String doSave;
    private String doView;    
    private int ctp;
    private int candidateId;
    private int candidateIdIndex;   
    private Collection candiateIdList;
    private String candidatename;
    private String description;
    private String deschidden;
    private int temptype;
    private int editor;
    private int attachment;
    private String formalityfilehidden;
    private FormFile formalityfile;
    private String expColumn[];
    private String eduColumn[];
    private String docColumn[];
    private String trainingColumn[];
    private String languageColumn[];
    private String vaccineColumn[];
    private String description2;
    private String description3;
    private FormFile formalityfile2;
    private String formalityfilehidden2;
    private String doDeleteFile;

    public String getFormalityfilehidden() {
        return formalityfilehidden;
    }

    public void setFormalityfilehidden(String formalityfilehidden) {
        this.formalityfilehidden = formalityfilehidden;
    }

    public FormFile getFormalityfile() {
        return formalityfile;
    }

    public void setFormalityfile(FormFile formalityfile) {
        this.formalityfile = formalityfile;
    }
    

    public int getEditor() {
        return editor;
    }

    public void setEditor(int editor) {
        this.editor = editor;
    }

    public int getAttachment() {
        return attachment;
    }

    public void setAttachment(int attachment) {
        this.attachment = attachment;
    }
    

    public int getTemptype() {
        return temptype;
    }

    public void setTemptype(int temptype) {
        this.temptype = temptype;
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

    public String getCandidatename() {
        return candidatename;
    }

    public void setCandidatename(String candidatename) {
        this.candidatename = candidatename;
    }

    
    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public Collection getCandiateIdList() {
        return candiateIdList;
    }

    public void setCandiateIdList(Collection candiateIdList) {
        this.candiateIdList = candiateIdList;
    }

    public int getCandidateIdIndex() {
        return candidateIdIndex;
    }

    public void setCandidateIdIndex(int candidateIdIndex) {
        this.candidateIdIndex = candidateIdIndex;
    }

    public ActionServlet getServlet() {
        return servlet;
    }

    public void setServlet(ActionServlet servlet) {
        this.servlet = servlet;
    }

    public MultipartRequestHandler getMultipartRequestHandler() {
        return multipartRequestHandler;
    }

    public void setMultipartRequestHandler(MultipartRequestHandler multipartRequestHandler) {
        this.multipartRequestHandler = multipartRequestHandler;
    }

    /**
     * @return the formalityId
     */
    public int getFormalityId() {
        return formalityId;
    }

    /**
     * @param formalityId the formalityId to set
     */
    public void setFormalityId(int formalityId) {
        this.formalityId = formalityId;
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
     * @return the expColumn
     */
    public String[] getExpColumn() {
        return expColumn;
    }

    /**
     * @param expColumn the expColumn to set
     */
    public void setExpColumn(String[] expColumn) {
        this.expColumn = expColumn;
    }

    /**
     * @return the eduColumn
     */
    public String[] getEduColumn() {
        return eduColumn;
    }

    /**
     * @param eduColumn the eduColumn to set
     */
    public void setEduColumn(String[] eduColumn) {
        this.eduColumn = eduColumn;
    }

    /**
     * @return the docColumn
     */
    public String[] getDocColumn() {
        return docColumn;
    }

    /**
     * @param docColumn the docColumn to set
     */
    public void setDocColumn(String[] docColumn) {
        this.docColumn = docColumn;
    }

    /**
     * @return the trainingColumn
     */
    public String[] getTrainingColumn() {
        return trainingColumn;
    }

    /**
     * @param trainingColumn the trainingColumn to set
     */
    public void setTrainingColumn(String[] trainingColumn) {
        this.trainingColumn = trainingColumn;
    }

    /**
     * @return the languageColumn
     */
    public String[] getLanguageColumn() {
        return languageColumn;
    }

    /**
     * @param languageColumn the languageColumn to set
     */
    public void setLanguageColumn(String[] languageColumn) {
        this.languageColumn = languageColumn;
    }

    /**
     * @return the vaccineColumn
     */
    public String[] getVaccineColumn() {
        return vaccineColumn;
    }

    /**
     * @param vaccineColumn the vaccineColumn to set
     */
    public void setVaccineColumn(String[] vaccineColumn) {
        this.vaccineColumn = vaccineColumn;
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
     * @return the formalityfile2
     */
    public FormFile getFormalityfile2() {
        return formalityfile2;
    }

    /**
     * @param formalityfile2 the formalityfile2 to set
     */
    public void setFormalityfile2(FormFile formalityfile2) {
        this.formalityfile2 = formalityfile2;
    }

    /**
     * @return the formalityfilehidden2
     */
    public String getFormalityfilehidden2() {
        return formalityfilehidden2;
    }

    /**
     * @param formalityfilehidden2 the formalityfilehidden2 to set
     */
    public void setFormalityfilehidden2(String formalityfilehidden2) {
        this.formalityfilehidden2 = formalityfilehidden2;
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
}