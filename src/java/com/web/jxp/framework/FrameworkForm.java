package com.web.jxp.framework;
import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class FrameworkForm extends ActionForm
{
    private String search;
    private String doCancel;
    private String doSave;
    private String doView;
    private Collection clients;
    private Collection assets;
    private int clientIdIndex;    
    private int assetIdIndex;
    private int ctp;
    private int pdeptId;
    private Collection pdepts;
    private String doDeptChange;
    private int clientId;
    private int assetId;
    private int positionIdHidden;
    private int pdeptIdHidden;
    private String doAssign;
    private Collection pcodes;
    private int pcodeId;
    private Collection assessmenttypes;
    private int passessmenttypeId;
    private Collection priorities;
    private int priorityId;
    private String doChangePcode;
    private int pcodeIdHidden;
    private String pcodeName;
    private int[] categoryId;
    private int[] questionId;
    private String doDeleteRole;
    private int status;
    private String[] positioncb;
    private String doAssignAll;
    private String pids;
    private int exceltype;
    private int monthId;
    
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
     * @return the assetIdIndex
     */
    public int getAssetIdIndex() {
        return assetIdIndex;
    }

    /**
     * @param assetIdIndex the assetIdIndex to set
     */
    public void setAssetIdIndex(int assetIdIndex) {
        this.assetIdIndex = assetIdIndex;
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
     * @return the pdeptId
     */
    public int getPdeptId() {
        return pdeptId;
    }

    /**
     * @param pdeptId the pdeptId to set
     */
    public void setPdeptId(int pdeptId) {
        this.pdeptId = pdeptId;
    }

    /**
     * @return the pdepts
     */
    public Collection getPdepts() {
        return pdepts;
    }

    /**
     * @param pdepts the pdepts to set
     */
    public void setPdepts(Collection pdepts) {
        this.pdepts = pdepts;
    }

    /**
     * @return the doDeptChange
     */
    public String getDoDeptChange() {
        return doDeptChange;
    }

    /**
     * @param doDeptChange the doDeptChange to set
     */
    public void setDoDeptChange(String doDeptChange) {
        this.doDeptChange = doDeptChange;
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
     * @return the positionIdHidden
     */
    public int getPositionIdHidden() {
        return positionIdHidden;
    }

    /**
     * @param positionIdHidden the positionIdHidden to set
     */
    public void setPositionIdHidden(int positionIdHidden) {
        this.positionIdHidden = positionIdHidden;
    }

    /**
     * @return the pdeptIdHidden
     */
    public int getPdeptIdHidden() {
        return pdeptIdHidden;
    }

    /**
     * @param pdeptIdHidden the pdeptIdHidden to set
     */
    public void setPdeptIdHidden(int pdeptIdHidden) {
        this.pdeptIdHidden = pdeptIdHidden;
    }

    /**
     * @return the doAssign
     */
    public String getDoAssign() {
        return doAssign;
    }

    /**
     * @param doAssign the doAssign to set
     */
    public void setDoAssign(String doAssign) {
        this.doAssign = doAssign;
    }

    /**
     * @return the pcodes
     */
    public Collection getPcodes() {
        return pcodes;
    }

    /**
     * @param pcodes the pcodes to set
     */
    public void setPcodes(Collection pcodes) {
        this.pcodes = pcodes;
    }

    /**
     * @return the pcodeId
     */
    public int getPcodeId() {
        return pcodeId;
    }

    /**
     * @param pcodeId the pcodeId to set
     */
    public void setPcodeId(int pcodeId) {
        this.pcodeId = pcodeId;
    }

    /**
     * @return the assessmenttypes
     */
    public Collection getAssessmenttypes() {
        return assessmenttypes;
    }

    /**
     * @param assessmenttypes the assessmenttypes to set
     */
    public void setAssessmenttypes(Collection assessmenttypes) {
        this.assessmenttypes = assessmenttypes;
    }

    /**
     * @return the passessmenttypeId
     */
    public int getPassessmenttypeId() {
        return passessmenttypeId;
    }

    /**
     * @param passessmenttypeId the passessmenttypeId to set
     */
    public void setPassessmenttypeId(int passessmenttypeId) {
        this.passessmenttypeId = passessmenttypeId;
    }

    /**
     * @return the priorities
     */
    public Collection getPriorities() {
        return priorities;
    }

    /**
     * @param priorities the priorities to set
     */
    public void setPriorities(Collection priorities) {
        this.priorities = priorities;
    }

    /**
     * @return the priorityId
     */
    public int getPriorityId() {
        return priorityId;
    }

    /**
     * @param priorityId the priorityId to set
     */
    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    /**
     * @return the doChangePcode
     */
    public String getDoChangePcode() {
        return doChangePcode;
    }

    /**
     * @param doChangePcode the doChangePcode to set
     */
    public void setDoChangePcode(String doChangePcode) {
        this.doChangePcode = doChangePcode;
    }

    /**
     * @return the pcodeIdHidden
     */
    public int getPcodeIdHidden() {
        return pcodeIdHidden;
    }

    /**
     * @param pcodeIdHidden the pcodeIdHidden to set
     */
    public void setPcodeIdHidden(int pcodeIdHidden) {
        this.pcodeIdHidden = pcodeIdHidden;
    }

    /**
     * @return the pcodeName
     */
    public String getPcodeName() {
        return pcodeName;
    }

    /**
     * @param pcodeName the pcodeName to set
     */
    public void setPcodeName(String pcodeName) {
        this.pcodeName = pcodeName;
    }

    /**
     * @return the categoryId
     */
    public int[] getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(int[] categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the questionId
     */
    public int[] getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId the questionId to set
     */
    public void setQuestionId(int[] questionId) {
        this.questionId = questionId;
    }

    /**
     * @return the doDeleteRole
     */
    public String getDoDeleteRole() {
        return doDeleteRole;
    }

    /**
     * @param doDeleteRole the doDeleteRole to set
     */
    public void setDoDeleteRole(String doDeleteRole) {
        this.doDeleteRole = doDeleteRole;
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
     * @return the positioncb
     */
    public String[] getPositioncb() {
        return positioncb;
    }

    /**
     * @param positioncb the positioncb to set
     */
    public void setPositioncb(String[] positioncb) {
        this.positioncb = positioncb;
    }

    /**
     * @return the doAssignAll
     */
    public String getDoAssignAll() {
        return doAssignAll;
    }

    /**
     * @param doAssignAll the doAssignAll to set
     */
    public void setDoAssignAll(String doAssignAll) {
        this.doAssignAll = doAssignAll;
    }

    /**
     * @return the pids
     */
    public String getPids() {
        return pids;
    }

    /**
     * @param pids the pids to set
     */
    public void setPids(String pids) {
        this.pids = pids;
    }

    /**
     * @return the exceltype
     */
    public int getExceltype() {
        return exceltype;
    }

    /**
     * @param exceltype the exceltype to set
     */
    public void setExceltype(int exceltype) {
        this.exceltype = exceltype;
    }

    /**
     * @return the monthId
     */
    public int getMonthId() {
        return monthId;
    }

    /**
     * @param monthId the monthId to set
     */
    public void setMonthId(int monthId) {
        this.monthId = monthId;
    }
}