package com.web.jxp.managetraining;
import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ManagetrainingForm extends ActionForm
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
    private String doSearchAsset;
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
    private String completeby;
    private String alink;
    private int cbtodate;
    private int stype;
    private FormFile attachment;
    private String doSavepersonal;
    private int mcourseId;
    private int mpositionId;
    private int mcrewrotationId;
    private int typefrom;
    private String doSavepersonalassign;
    private String doSavecourseassign;
    private String mcrids;
    private int ctp;
    private String fromval;
    private String toval;
    private String ccval;
    private String bccval;
    private String subject;
    private String description;
    private String remarks;
    private String doMail;
    private String doIndexCourse;
    private int categoryId;
    private int subcategoryId;
    private int typefrom1;
    private int statusIndex;
    private int candidateId;
    private int coursenameId;
    private String exptype;
    
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

    /**
     * @return the doSearchAsset
     */
    public String getDoSearchAsset() {
        return doSearchAsset;
    }

    /**
     * @param doSearchAsset the doSearchAsset to set
     */
    public void setDoSearchAsset(String doSearchAsset) {
        this.doSearchAsset = doSearchAsset;
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

    public String getCompleteby() {
        return completeby;
    }

    public void setCompleteby(String completeby) {
        this.completeby = completeby;
    }

    public String getAlink() {
        return alink;
    }

    public void setAlink(String alink) {
        this.alink = alink;
    }

    public int getCbtodate() {
        return cbtodate;
    }

    public void setCbtodate(int cbtodate) {
        this.cbtodate = cbtodate;
    }

    public int getStype() {
        return stype;
    }

    public void setStype(int stype) {
        this.stype = stype;
    }

    public FormFile getAttachment() {
        return attachment;
    }

    public void setAttachment(FormFile attachment) {
        this.attachment = attachment;
    }

    public String getDoSavepersonal() {
        return doSavepersonal;
    }

    public void setDoSavepersonal(String doSavepersonal) {
        this.doSavepersonal = doSavepersonal;
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

    public String getDoSavepersonalassign() {
        return doSavepersonalassign;
    }

    public void setDoSavepersonalassign(String doSavepersonalassign) {
        this.doSavepersonalassign = doSavepersonalassign;
    }

    public String getDoSavecourseassign() {
        return doSavecourseassign;
    }

    public void setDoSavecourseassign(String doSavecourseassign) {
        this.doSavecourseassign = doSavecourseassign;
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

    public String getToval() {
        return toval;
    }

    public void setToval(String toval) {
        this.toval = toval;
    }

    public String getCcval() {
        return ccval;
    }

    public void setCcval(String ccval) {
        this.ccval = ccval;
    }

    public String getBccval() {
        return bccval;
    }

    public void setBccval(String bccval) {
        this.bccval = bccval;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoMail() {
        return doMail;
    }

    public void setDoMail(String doMail) {
        this.doMail = doMail;
    }

    public String getFromval() {
        return fromval;
    }

    public void setFromval(String fromval) {
        this.fromval = fromval;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDoIndexCourse() {
        return doIndexCourse;
    }

    public void setDoIndexCourse(String doIndexCourse) {
        this.doIndexCourse = doIndexCourse;
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

}