package com.web.jxp.tctype;
import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.MultipartRequestHandler;

public class TctypeForm extends ActionForm
{
    private int tctypeId;
    private int status;
    private String search;
    private String doCancel;
    private String doModify;
    private String name;
    private String doAdd;
    private String doSave;
    private String doView;
    private int ctp;
    private int clientIndex;
    private int clientId;
    private Collection clients;
    private String client;
    
    private int assetIndex;
    private int assetId;
    private Collection assets;
    private String assetName;

    public int getClientIndex() {
        return clientIndex;
    }

    public void setClientIndex(int clientIndex) {
        this.clientIndex = clientIndex;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Collection getClients() {
        return clients;
    }

    public void setClients(Collection clients) {
        this.clients = clients;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getAssetIndex() {
        return assetIndex;
    }

    public void setAssetIndex(int assetIndex) {
        this.assetIndex = assetIndex;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public Collection getAssets() {
        return assets;
    }

    public void setAssets(Collection assets) {
        this.assets = assets;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
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
    

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    /**
     * @return the tctypeId
     */
    public int getTctypeId() {
        return tctypeId;
    }

    /**
     * @param tctypeId the tctypeId to set
     */
    public void setTctypeId(int tctypeId) {
        this.tctypeId = tctypeId;
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
}