package com.web.jxp.user;
import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

public class UserForm extends ActionForm
{
    private int userId;
    private String name;
    private String contact1;
    private String contact2;
    private String userName;
    private String userNameOld;
    private String email;
    private String code;
    private int status;
    private Collection statuses;
    private String address;
    private String search;
    private String doSearch;
    private String doAdd;
    private String doDetail;
    private String doModify;
    private String doSaveUser;
    private String doCancel;
    private String permission;
    private String doLogOut;
    private String moduleIdArr[];
    private String addRight[];
    private String editRight[];
    private String deleteRight[];
    private String viewRight[];
    private String approveRight[];
    private String positionRank[];
    private FormFile photo;
    private String photoHidden;
    private FormFile cvfile;
    private String cvHidden;
    private int managerId;
    private int allclient;
    private Collection managers;
    private Collection clients;
    private Collection countries;
    private Collection positions;
    private int clientId;
    private int countryId;
    private int[] assetId;
    private int coordinator;
    private int assessor;
    private int cassessor;
    private int isManager;
    private int isRecruiter;
    private int ctp;
    
    private int clientIndex;
    private String permissionIndex;

    public String getPermissionIndex() {
        return permissionIndex;
    }

    public void setPermissionIndex(String permissionIndex) {
        this.permissionIndex = permissionIndex;
    }
    
    public int getClientIndex() {
        return clientIndex;
    }

    public void setClientIndex(int clientIndex) {
        this.clientIndex = clientIndex;
    }
    
    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    public int getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(int coordinator) {
        this.coordinator = coordinator;
    }

    public int getAssessor() {
        return assessor;
    }

    public void setAssessor(int assessor) {
        this.assessor = assessor;
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
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
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
     * @return the contact1
     */
    public String getContact1() {
        return contact1;
    }

    /**
     * @param contact1 the contact1 to set
     */
    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    /**
     * @return the contact2
     */
    public String getContact2() {
        return contact2;
    }

    /**
     * @param contact2 the contact2 to set
     */
    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userNameOld
     */
    public String getUserNameOld() {
        return userNameOld;
    }

    /**
     * @param userNameOld the userNameOld to set
     */
    public void setUserNameOld(String userNameOld) {
        this.userNameOld = userNameOld;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
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
     * @return the doDetail
     */
    public String getDoDetail() {
        return doDetail;
    }

    /**
     * @param doDetail the doDetail to set
     */
    public void setDoDetail(String doDetail) {
        this.doDetail = doDetail;
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
     * @return the doSaveUser
     */
    public String getDoSaveUser() {
        return doSaveUser;
    }

    /**
     * @param doSaveUser the doSaveUser to set
     */
    public void setDoSaveUser(String doSaveUser) {
        this.doSaveUser = doSaveUser;
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
     * @return the permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * @param permission the permission to set
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * @return the doLogOut
     */
    public String getDoLogOut() {
        return doLogOut;
    }

    /**
     * @param doLogOut the doLogOut to set
     */
    public void setDoLogOut(String doLogOut) {
        this.doLogOut = doLogOut;
    }

    /**
     * @return the moduleIdArr
     */
    public String[] getModuleIdArr() {
        return moduleIdArr;
    }

    /**
     * @param moduleIdArr the moduleIdArr to set
     */
    public void setModuleIdArr(String[] moduleIdArr) {
        this.moduleIdArr = moduleIdArr;
    }

    /**
     * @return the addRight
     */
    public String[] getAddRight() {
        return addRight;
    }

    /**
     * @param addRight the addRight to set
     */
    public void setAddRight(String[] addRight) {
        this.addRight = addRight;
    }

    /**
     * @return the editRight
     */
    public String[] getEditRight() {
        return editRight;
    }

    /**
     * @param editRight the editRight to set
     */
    public void setEditRight(String[] editRight) {
        this.editRight = editRight;
    }

    /**
     * @return the deleteRight
     */
    public String[] getDeleteRight() {
        return deleteRight;
    }

    /**
     * @param deleteRight the deleteRight to set
     */
    public void setDeleteRight(String[] deleteRight) {
        this.deleteRight = deleteRight;
    }

    /**
     * @return the viewRight
     */
    public String[] getViewRight() {
        return viewRight;
    }

    /**
     * @param viewRight the viewRight to set
     */
    public void setViewRight(String[] viewRight) {
        this.viewRight = viewRight;
    }

    /**
     * @return the approveRight
     */
    public String[] getApproveRight() {
        return approveRight;
    }

    /**
     * @param approveRight the approveRight to set
     */
    public void setApproveRight(String[] approveRight) {
        this.approveRight = approveRight;
    }

    /**
     * @return the photo
     */
    public FormFile getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(FormFile photo) {
        this.photo = photo;
    }

    /**
     * @return the photoHidden
     */
    public String getPhotoHidden() {
        return photoHidden;
    }

    /**
     * @param photoHidden the photoHidden to set
     */
    public void setPhotoHidden(String photoHidden) {
        this.photoHidden = photoHidden;
    }

    /**
     * @return the managerId
     */
    public int getManagerId() {
        return managerId;
    }

    /**
     * @param managerId the managerId to set
     */
    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    /**
     * @return the allclient
     */
    public int getAllclient() {
        return allclient;
    }

    /**
     * @param allclient the allclient to set
     */
    public void setAllclient(int allclient) {
        this.allclient = allclient;
    }

    /**
     * @return the managers
     */
    public Collection getManagers() {
        return managers;
    }

    /**
     * @param managers the managers to set
     */
    public void setManagers(Collection managers) {
        this.managers = managers;
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
     * @return the countries
     */
    public Collection getCountries() {
        return countries;
    }

    /**
     * @param countries the countries to set
     */
    public void setCountries(Collection countries) {
        this.countries = countries;
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
     * @return the countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the assetId
     */
    public int[] getAssetId() {
        return assetId;
    }

    /**
     * @param assetId the assetId to set
     */
    public void setAssetId(int[] assetId) {
        this.assetId = assetId;
    }

    /**
     * @return the cassessor
     */
    public int getCassessor() {
        return cassessor;
    }

    /**
     * @param cassessor the cassessor to set
     */
    public void setCassessor(int cassessor) {
        this.cassessor = cassessor;
    }

    /**
     * @return the cvfile
     */
    public FormFile getCvfile() {
        return cvfile;
    }

    /**
     * @param cvfile the cvfile to set
     */
    public void setCvfile(FormFile cvfile) {
        this.cvfile = cvfile;
    }

    /**
     * @return the cvHidden
     */
    public String getCvHidden() {
        return cvHidden;
    }

    /**
     * @param cvHidden the cvHidden to set
     */
    public void setCvHidden(String cvHidden) {
        this.cvHidden = cvHidden;
    }

    /**
     * @return the positionRank
     */
    public String[] getPositionRank() {
        return positionRank;
    }

    /**
     * @param positionRank the positionRank to set
     */
    public void setPositionRank(String[] positionRank) {
        this.positionRank = positionRank;
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
     * @return the isManager
     */
    public int getIsManager() {
        return isManager;
    }

    /**
     * @param isManager the isManager to set
     */
    public void setIsManager(int isManager) {
        this.isManager = isManager;
    }

    /**
     * @return the isRecruiter
     */
    public int getIsRecruiter() {
        return isRecruiter;
    }

    /**
     * @param isRecruiter the isRecruiter to set
     */
    public void setIsRecruiter(int isRecruiter) {
        this.isRecruiter = isRecruiter;
    }
}
