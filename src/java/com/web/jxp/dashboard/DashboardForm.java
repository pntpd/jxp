package com.web.jxp.dashboard;
import java.util.Collection;
import org.apache.struts.action.ActionForm;
public class DashboardForm extends ActionForm 
{
    private int ctp;
    private String doCR;
    private String doSearchCR;
    private String doSearchCRpp;
    private String searchPosition;
    private String searchPersonnel;
    private String doTraining;
    private String doTrainingSearch;
    private String doCrecruitment;
    private int ddlValue;
    private String ddlLabel;
    private String fromdate;
    private String todate;
    private int month;
    private int year;
    private String positioncb;
    private String crewrotationcb;
    private String exptype;    
    private int clientIdIndex;
    private int assetIdIndex;
    private Collection clients;
    private Collection assets;    
    private String doDashboard;
    private String doDashboardSearch;    
    private int ttype;
    private String doCrewEnr;
    private int clientId;
    private int clientassetId;
    private String doView;
    private String doCrecruitmentSearch;

    public String getDoCR() {
        return doCR;
    }

    public void setDoCR(String doCR) {
        this.doCR = doCR;
    }

    public String getSearchPosition() {
        return searchPosition;
    }

    public void setSearchPosition(String searchPosition) {
        this.searchPosition = searchPosition;
    }

    public String getSearchPersonnel() {
        return searchPersonnel;
    }

    public void setSearchPersonnel(String searchPersonnel) {
        this.searchPersonnel = searchPersonnel;
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

    public int getAssetIdIndex() {
        return assetIdIndex;
    }

    public void setAssetIdIndex(int assetIdIndex) {
        this.assetIdIndex = assetIdIndex;
    }

    public Collection getClients() {
        return clients;
    }

    public void setClients(Collection clients) {
        this.clients = clients;
    }

    public Collection getAssets() {
        return assets;
    }

    public void setAssets(Collection assets) {
        this.assets = assets;
    }

    public int getDdlValue() {
        return ddlValue;
    }

    public void setDdlValue(int ddlValue) {
        this.ddlValue = ddlValue;
    }

    public String getDdlLabel() {
        return ddlLabel;
    }

    public void setDdlLabel(String ddlLabel) {
        this.ddlLabel = ddlLabel;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDoSearchCR() {
        return doSearchCR;
    }

    public void setDoSearchCR(String doSearchCR) {
        this.doSearchCR = doSearchCR;
    }

    public String getPositioncb() {
        return positioncb;
    }

    public void setPositioncb(String positioncb) {
        this.positioncb = positioncb;
    }

    public String getCrewrotationcb() {
        return crewrotationcb;
    }

    public void setCrewrotationcb(String crewrotationcb) {
        this.crewrotationcb = crewrotationcb;
    }

    public String getDoSearchCRpp() {
        return doSearchCRpp;
    }

    public void setDoSearchCRpp(String doSearchCRpp) {
        this.doSearchCRpp = doSearchCRpp;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getExptype() {
        return exptype;
    }

    public void setExptype(String exptype) {
        this.exptype = exptype;
    }

    /**
     * @return the doDashboard
     */
    public String getDoDashboard() {
        return doDashboard;
    }

    /**
     * @param doDashboard the doDashboard to set
     */
    public void setDoDashboard(String doDashboard) {
        this.doDashboard = doDashboard;
    }

    /**
     * @return the doDashboardSearch
     */
    public String getDoDashboardSearch() {
        return doDashboardSearch;
    }

    /**
     * @param doDashboardSearch the doDashboardSearch to set
     */
    public void setDoDashboardSearch(String doDashboardSearch) {
        this.doDashboardSearch = doDashboardSearch;
    }

    public int getTtype() {
        return ttype;
    }

    public void setTtype(int ttype) {
        this.ttype = ttype;
    }

    /**
     * @return the doCrewEnr
     */
    public String getDoCrewEnr() {
        return doCrewEnr;
    }

    /**
     * @param doCrewEnr the doCrewEnr to set
     */
    public void setDoCrewEnr(String doCrewEnr) {
        this.doCrewEnr = doCrewEnr;
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

    public String getDoView() {
        return doView;
    }

    public void setDoView(String doView) {
        this.doView = doView;
    }

    /**
     * @return the doTraining
     */
    public String getDoTraining() {
        return doTraining;
    }

    /**
     * @param doTraining the doTraining to set
     */
    public void setDoTraining(String doTraining) {
        this.doTraining = doTraining;
    }

    /**
     * @return the doTrainingSearch
     */
    public String getDoTrainingSearch() {
        return doTrainingSearch;
    }

    /**
     * @param doTrainingSearch the doTrainingSearch to set
     */
    public void setDoTrainingSearch(String doTrainingSearch) {
        this.doTrainingSearch = doTrainingSearch;
    }

    /**
     * @return the doCrecruitment
     */
    public String getDoCrecruitment() {
        return doCrecruitment;
    }

    /**
     * @param doCrecruitment the doCrecruitment to set
     */
    public void setDoCrecruitment(String doCrecruitment) {
        this.doCrecruitment = doCrecruitment;
    }

    /**
     * @return the doCrecruitmentSearch
     */
    public String getDoCrecruitmentSearch() {
        return doCrecruitmentSearch;
    }

    /**
     * @param doCrecruitmentSearch the doCrecruitmentSearch to set
     */
    public void setDoCrecruitmentSearch(String doCrecruitmentSearch) {
        this.doCrecruitmentSearch = doCrecruitmentSearch;
    }

}
