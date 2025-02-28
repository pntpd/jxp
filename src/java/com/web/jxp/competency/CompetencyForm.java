package com.web.jxp.competency;
import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class CompetencyForm extends ActionForm
{
    private int competencyId;
    private int status;
    private String search;
    private String searchDetails;
    private String doSearch;
    private String searchGraph;
    private String searchName;
    private String searchPosition;
    private String doSearchPosition;
    private String searchDept;
    private String searchRole;
    private String name;
    private Collection statuses;
    private Collection clients;
    private Collection assets;
    private Collection positions;
    private int clientIdIndex;
    private int assetIdIndex;
    private int statusIndex;
    private int ctp;
    private int departmentId;
    private int crewrotationId;
    private int pcodeId;
    private int positionId;
    private int candidateId;
    private int roleId;
    private int type;
    private int expType;
    private String departmentcb;
    private String crewrotationcb;
    private String positioncb;
    private String comprolecb;
    private String doCancel;

    /**
     * @return the competencyId
     */
    public int getCompetencyId() {
        return competencyId;
    }

    /**
     * @param competencyId the competencyId to set
     */
    public void setCompetencyId(int competencyId) {
        this.competencyId = competencyId;
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
     * @return the statuses
     */
    public Collection getStatuses() {
        return statuses;
    }

    /**
     * @param statuses the statuses to set
     */
    public void setStatuses(Collection statuses) {
        this.statuses = statuses;
    }

    /**
     * @return the statusIndex
     */
    public int getStatusIndex() {
        return statusIndex;
    }

    /**
     * @param statusIndex the statusIndex to set
     */
    public void setStatusIndex(int statusIndex) {
        this.statusIndex = statusIndex;
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
     * @return the doSearch
     */
    public String getDoSearch() {
        return doSearch;
    }

    /**
     * @param doSearch the doSearch to set
     */
    public void setDoSearch(String doSearch) {
        this.doSearch = doSearch;
    }

    /**
     * @return the departmentId
     */
    public int getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(int positionId) {
        this.positionId = positionId;
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
     * @return the roleId
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * @return the departmentcb
     */
    public String getDepartmentcb() {
        return departmentcb;
    }

    /**
     * @param departmentcb the departmentcb to set
     */
    public void setDepartmentcb(String departmentcb) {
        this.departmentcb = departmentcb;
    }

    /**
     * @return the crewrotationcb
     */
    public String getCrewrotationcb() {
        return crewrotationcb;
    }

    /**
     * @param crewrotationcb the crewrotationcb to set
     */
    public void setCrewrotationcb(String crewrotationcb) {
        this.crewrotationcb = crewrotationcb;
    }

    /**
     * @return the positioncb
     */
    public String getPositioncb() {
        return positioncb;
    }

    /**
     * @param positioncb the positioncb to set
     */
    public void setPositioncb(String positioncb) {
        this.positioncb = positioncb;
    }
    /**
     * @return the searchName
     */
    public String getSearchName() {
        return searchName;
    }

    /**
     * @param searchName the searchName to set
     */
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    /**
     * @return the searchPosition
     */
    public String getSearchPosition() {
        return searchPosition;
    }

    /**
     * @param searchPosition the searchPosition to set
     */
    public void setSearchPosition(String searchPosition) {
        this.searchPosition = searchPosition;
    }

    /**
     * @return the searchDept
     */
    public String getSearchDept() {
        return searchDept;
    }

    /**
     * @param searchDept the searchDept to set
     */
    public void setSearchDept(String searchDept) {
        this.searchDept = searchDept;
    }

    /**
     * @return the searchRole
     */
    public String getSearchRole() {
        return searchRole;
    }

    /**
     * @param searchRole the searchRole to set
     */
    public void setSearchRole(String searchRole) {
        this.searchRole = searchRole;
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
     * @return the comprolecb
     */
    public String getComprolecb() {
        return comprolecb;
    }

    /**
     * @param comprolecb the comprolecb to set
     */
    public void setComprolecb(String comprolecb) {
        this.comprolecb = comprolecb;
    }

    /**
     * @return the crewrotationId
     */
    public int getCrewrotationId() {
        return crewrotationId;
    }

    /**
     * @param crewrotationId the crewrotationId to set
     */
    public void setCrewrotationId(int crewrotationId) {
        this.crewrotationId = crewrotationId;
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
     * @return the expType
     */
    public int getExpType() {
        return expType;
    }

    /**
     * @param expType the expType to set
     */
    public void setExpType(int expType) {
        this.expType = expType;
    }

    /**
     * @return the searchGraph
     */
    public String getSearchGraph() {
        return searchGraph;
    }

    /**
     * @param searchGraph the searchGraph to set
     */
    public void setSearchGraph(String searchGraph) {
        this.searchGraph = searchGraph;
    }

    /**
     * @return the positions
     */
    public Collection getPositions() {
        return positions;
    }

    /**
     * @param positions the positions to set
     */
    public void setPositions(Collection positions) {
        this.positions = positions;
    }

    /**
     * @return the doSearchPosition
     */
    public String getDoSearchPosition() {
        return doSearchPosition;
    }

    /**
     * @param doSearchPosition the doSearchPosition to set
     */
    public void setDoSearchPosition(String doSearchPosition) {
        this.doSearchPosition = doSearchPosition;
    }

    /**
     * @return the searchDetails
     */
    public String getSearchDetails() {
        return searchDetails;
    }

    /**
     * @param searchDetails the searchDetails to set
     */
    public void setSearchDetails(String searchDetails) {
        this.searchDetails = searchDetails;
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
}