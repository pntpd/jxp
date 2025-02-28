package com.web.jxp.contract;
import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

public class ContractForm extends ActionForm
{
    private int contractId;
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
    private Collection assets;
    private Collection contracts;
    private String clientname;
    private String description;
    private String deschidden;
    private int type;
    private int assetId;
    private int refId;
    private String expColumn[];
    private String eduColumn[];
    private String docColumn[];
    private String trainingColumn[];
    private String languageColumn[];
    private String vaccineColumn[];
    private String nomineeColumn[];
    private String description2;
    private String description3;
    private FormFile contractfile;
    private String contractfilehidden;
    private String doDeleteFile;
    
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

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public int getClientIdIndex() {
        return clientIdIndex;
    }

    public void setClientIdIndex(int clientIdIndex) {
        this.clientIdIndex = clientIdIndex;
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
     * @return the contractId
     */
    public int getContractId() {
        return contractId;
    }

    /**
     * @param contractId the contractId to set
     */
    public void setContractId(int contractId) {
        this.contractId = contractId;
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
     * @return the assets
     */
    public Collection getAssets() {
        return assets;
    }

    /**
     * @param assets the assets to set
     */
    public void setAssets(Collection assets) {
        this.assets = assets;
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

    /**
     * @return the assetId
     */
    public int getAssetId() {
        return assetId;
    }

    /**
     * @param assetId the assetId to set
     */
    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    /**
     * @return the contracts
     */
    public Collection getContracts() {
        return contracts;
    }

    /**
     * @param contracts the contracts to set
     */
    public void setContracts(Collection contracts) {
        this.contracts = contracts;
    }

    /**
     * @return the refId
     */
    public int getRefId() {
        return refId;
    }

    /**
     * @param refId the refId to set
     */
    public void setRefId(int refId) {
        this.refId = refId;
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
     * @return the contractfile
     */
    public FormFile getContractfile() {
        return contractfile;
    }

    /**
     * @param contractfile the contractfile to set
     */
    public void setContractfile(FormFile contractfile) {
        this.contractfile = contractfile;
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
     * @return the contractfilehidden
     */
    public String getContractfilehidden() {
        return contractfilehidden;
    }

    /**
     * @param contractfilehidden the contractfilehidden to set
     */
    public void setContractfilehidden(String contractfilehidden) {
        this.contractfilehidden = contractfilehidden;
    }

    /**
     * @return the nomineeColumn
     */
    public String[] getNomineeColumn() {
        return nomineeColumn;
    }

    /**
     * @param nomineeColumn the nomineeColumn to set
     */
    public void setNomineeColumn(String[] nomineeColumn) {
        this.nomineeColumn = nomineeColumn;
    }
}