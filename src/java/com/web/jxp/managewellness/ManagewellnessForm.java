package com.web.jxp.managewellness;
import java.util.Collection;
import org.apache.struts.action.ActionForm;

public class ManagewellnessForm extends ActionForm
{
    private String search;
    private String doCancel;
    private String doView;
    private Collection clients;
    private Collection assets;
    private Collection positions;
    private int clientIdIndex;    
    private int assetIdIndex;
    private int positionIdIndex;
    private int positionId2Index;
    private int mode;
    private String doSearch;
    private Collection categories;
    private Collection subcategories;
    private int categoryIdIndex;
    private int subcategoryIdIndex;
    private int crewrotationId;
    private String doAssign1;
    private String doAssign2;
    private int courseId;
    private String doAssignSave1;
    private int categoryIdDetail;
    private int subcategoryIdDetail;
    private String searchdetail;
    private int ftype;
    private int positionId;
    private String todate;
    private String fromdate;
    private int stype;
    private int mcourseId;
    private int mpositionId;
    private int mcrewrotationId;
    private int typefrom;
    private String mcrids;
    private int ctp;
    private int categoryId;
    private int subcategoryId;
    private int typefrom1;
    private int statusIndex;
    private int candidateId;
    private int coursenameId;
    private String exptype;
    private String enddate;
    private String startdate;
    private String doDeletedate;
    private String doSaveschedule;
    private String doIndexSubcategory;
    private String doSearchAsset;
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
     * @return the positionIdIndex
     */
    public int getPositionIdIndex() {
        return positionIdIndex;
    }

    /**
     * @param positionIdIndex the positionIdIndex to set
     */
    public void setPositionIdIndex(int positionIdIndex) {
        this.positionIdIndex = positionIdIndex;
    }

    /**
     * @return the mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(int mode) {
        this.mode = mode;
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
     * @return the categories
     */
    public Collection getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(Collection categories) {
        this.categories = categories;
    }

    /**
     * @return the subcategories
     */
    public Collection getSubcategories() {
        return subcategories;
    }

    /**
     * @param subcategories the subcategories to set
     */
    public void setSubcategories(Collection subcategories) {
        this.subcategories = subcategories;
    }

    /**
     * @return the categoryIdIndex
     */
    public int getCategoryIdIndex() {
        return categoryIdIndex;
    }

    /**
     * @param categoryIdIndex the categoryIdIndex to set
     */
    public void setCategoryIdIndex(int categoryIdIndex) {
        this.categoryIdIndex = categoryIdIndex;
    }

    /**
     * @return the subcategoryIdIndex
     */
    public int getSubcategoryIdIndex() {
        return subcategoryIdIndex;
    }

    /**
     * @param subcategoryIdIndex the subcategoryIdIndex to set
     */
    public void setSubcategoryIdIndex(int subcategoryIdIndex) {
        this.subcategoryIdIndex = subcategoryIdIndex;
    }

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
     * @return the doAssign1
     */
    public String getDoAssign1() {
        return doAssign1;
    }

    /**
     * @param doAssign1 the doAssign1 to set
     */
    public void setDoAssign1(String doAssign1) {
        this.doAssign1 = doAssign1;
    }

    /**
     * @return the doAssign2
     */
    public String getDoAssign2() {
        return doAssign2;
    }

    /**
     * @param doAssign2 the doAssign2 to set
     */
    public void setDoAssign2(String doAssign2) {
        this.doAssign2 = doAssign2;
    }

    /**
     * @return the courseId
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * @param courseId the courseId to set
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**
     * @return the doAssignSave1
     */
    public String getDoAssignSave1() {
        return doAssignSave1;
    }

    /**
     * @param doAssignSave1 the doAssignSave1 to set
     */
    public void setDoAssignSave1(String doAssignSave1) {
        this.doAssignSave1 = doAssignSave1;
    }

    /**
     * @return the categoryIdDetail
     */
    public int getCategoryIdDetail() {
        return categoryIdDetail;
    }

    /**
     * @param categoryIdDetail the categoryIdDetail to set
     */
    public void setCategoryIdDetail(int categoryIdDetail) {
        this.categoryIdDetail = categoryIdDetail;
    }

    /**
     * @return the subcategoryIdDetail
     */
    public int getSubcategoryIdDetail() {
        return subcategoryIdDetail;
    }

    /**
     * @param subcategoryIdDetail the subcategoryIdDetail to set
     */
    public void setSubcategoryIdDetail(int subcategoryIdDetail) {
        this.subcategoryIdDetail = subcategoryIdDetail;
    }

    /**
     * @return the searchdetail
     */
    public String getSearchdetail() {
        return searchdetail;
    }

    /**
     * @param searchdetail the searchdetail to set
     */
    public void setSearchdetail(String searchdetail) {
        this.searchdetail = searchdetail;
    }

    /**
     * @return the ftype
     */
    public int getFtype() {
        return ftype;
    }

    /**
     * @param ftype the ftype to set
     */
    public void setFtype(int ftype) {
        this.ftype = ftype;
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

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public int getStype() {
        return stype;
    }

    public void setStype(int stype) {
        this.stype = stype;
    }

    public int getMcourseId() {
        return mcourseId;
    }

    public void setMcourseId(int mcourseId) {
        this.mcourseId = mcourseId;
    }

    public int getMpositionId() {
        return mpositionId;
    }

    public void setMpositionId(int mpositionId) {
        this.mpositionId = mpositionId;
    }

    public int getTypefrom() {
        return typefrom;
    }

    public void setTypefrom(int typefrom) {
        this.typefrom = typefrom;
    }

    public int getMcrewrotationId() {
        return mcrewrotationId;
    }

    public void setMcrewrotationId(int mcrewrotationId) {
        this.mcrewrotationId = mcrewrotationId;
    }

    public String getMcrids() {
        return mcrids;
    }

    public void setMcrids(String mcrids) {
        this.mcrids = mcrids;
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
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public int getTypefrom1() {
        return typefrom1;
    }

    public void setTypefrom1(int typefrom1) {
        this.typefrom1 = typefrom1;
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

    public int getCoursenameId() {
        return coursenameId;
    }

    public void setCoursenameId(int coursenameId) {
        this.coursenameId = coursenameId;
    }

    public String getExptype() {
        return exptype;
    }

    public void setExptype(String exptype) {
        this.exptype = exptype;
    }

    public int getPositionId2Index() {
        return positionId2Index;
    }

    public void setPositionId2Index(int positionId2Index) {
        this.positionId2Index = positionId2Index;
    }

   

    public String getDoDeletedate() {
        return doDeletedate;
    }

    public void setDoDeletedate(String doDeletedate) {
        this.doDeletedate = doDeletedate;
    }

    public String getDoSaveschedule() {
        return doSaveschedule;
    }

    public void setDoSaveschedule(String doSaveschedule) {
        this.doSaveschedule = doSaveschedule;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getDoIndexSubcategory() {
        return doIndexSubcategory;
    }

    public void setDoIndexSubcategory(String doIndexSubcategory) {
        this.doIndexSubcategory = doIndexSubcategory;
    }

    public String getDoSearchAsset() {
        return doSearchAsset;
    }

    public void setDoSearchAsset(String doSearchAsset) {
        this.doSearchAsset = doSearchAsset;
    }
    
    
    
}