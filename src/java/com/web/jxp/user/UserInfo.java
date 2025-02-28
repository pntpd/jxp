package com.web.jxp.user;

public class UserInfo {

    private int userId;
    private int status;
    private int moduleId;
    private int type;
    private int ddlValue;
    private int managerId;
    private int allclient;
    private int clientId;
    private int countryId;
    private int coordinator;
    private int assessor;
    private int cassessor;
    private int companytype;

    private String cvfile;
    private String positionRank;
    private String positionRankId;
    private String name;
    private String contact1;
    private String contact2;
    private String userName;
    private String password;
    private String email;
    private String module;
    private String permission;
    private String viewper;
    private String addper;
    private String editper;
    private String deleteper;
    private String mpermission;
    private String approveper;
    private String code;
    private String ddlLable;
    private String photo;
    private String managerName;
    private String address;
    private String clientName;
    private String countryName;
    private String assetids;
    private String assettext;
    private String cids;
    private int subtype;
    
    private int isManager;
    private int isRecruiter;

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

    public UserInfo(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public UserInfo(int value, String name, int tp) {
        this.ddlValue = value;
        this.ddlLable = name;
    }

    // USING FOR GETTING THE PASSWORD FOR CHANGE PASSWORD
    public UserInfo(int userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = password;
    }

    // for access
    public UserInfo(int userId, String name, String email, String userName, String permission,
            String contact1, String password, String photo, int coordinator, int assessor, int allclient, int cassessor, String cids, String assetids, int companytype) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.permission = permission;
        this.contact1 = contact1;
        this.password = password;
        this.photo = photo;
        this.coordinator = coordinator;
        this.assessor = assessor;
        this.allclient = allclient;
        this.cassessor = cassessor;
        this.cids = cids;
        this.assetids = assetids;
        this.companytype = companytype;
    }

    //for permission
    public UserInfo(int userId, int moduleId, String name, int type, String addper, String editper,
            String deleteper, String approveper, String viewper, int subtype) 
    {
        this.userId = userId;
        this.moduleId = moduleId;
        this.name = name;
        this.type = type;
        this.viewper = viewper;
        this.addper = addper;
        this.editper = editper;
        this.deleteper = deleteper;
        this.approveper = approveper;
        this.subtype = subtype;
    }

    //for forgot password
    public UserInfo(int userId, String email, String userName, String name, String password) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.name = name;
        this.password = password;
    }

    //for add and edit
    public UserInfo(int userId, String name, String email, String contact1, String userName, String password,
            String permission, int status, String contact2, String code, String photo, int managerId, int allclient,
            String address, int coordinator, int assessor, int cassessor, String cvfile, String postionstr, int ismanager, int isRecruiter) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contact1 = contact1;
        this.userName = userName;
        this.password = password;
        this.permission = permission;
        this.status = status;
        this.contact2 = contact2;
        this.code = code;
        this.photo = photo;
        this.managerId = managerId;
        this.allclient = allclient;
        this.address = address;
        this.coordinator = coordinator;
        this.assessor = assessor;
        this.cassessor = cassessor;
        this.cvfile = cvfile;
        this.positionRank = postionstr;
        this.isManager = ismanager;
        this.isRecruiter = isRecruiter;
    }
    
    public UserInfo(int userId, String name, String email, String contact1, String userName, String password,
            String permission, int status, String contact2, String code, String photo, int managerId, int allclient,
            String address, int coordinator, int assessor, int cassessor, String cvfile, int ismanager, int isrecruiter) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contact1 = contact1;
        this.userName = userName;
        this.password = password;
        this.permission = permission;
        this.status = status;
        this.contact2 = contact2;
        this.code = code;
        this.photo = photo;
        this.managerId = managerId;
        this.allclient = allclient;
        this.address = address;
        this.coordinator = coordinator;
        this.assessor = assessor;
        this.cassessor = cassessor;
        this.cvfile = cvfile;
        this.isManager = ismanager;
        this.isRecruiter = isrecruiter;
    }
    
    public UserInfo(int userId, String name, String email, String contact1, String userName, String password,
            String permission, int status, String contact2, String code, String photo, int managerId, int allclient,
            String address, int coordinator, int assessor, int cassessor) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contact1 = contact1;
        this.userName = userName;
        this.password = password;
        this.permission = permission;
        this.status = status;
        this.contact2 = contact2;
        this.code = code;
        this.photo = photo;
        this.managerId = managerId;
        this.allclient = allclient;
        this.address = address;
        this.coordinator = coordinator;
        this.assessor = assessor;
        this.cassessor = cassessor;
    }

    //for detail
    public UserInfo(int userId, String name, String email, String contact1, String userName,
            String password, int status, String permission, String contact2, String code,
            String photo, String managerName, int allclient, String address, int coordinator, 
            int assessor, int cassessor, String cvfile, int ismanager, int isrecruiter) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contact1 = contact1;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.permission = permission;
        this.contact2 = contact2;
        this.code = code;
        this.photo = photo;
        this.managerName = managerName;
        this.allclient = allclient;
        this.address = address;
        this.coordinator = coordinator;
        this.assessor = assessor;
        this.cassessor = cassessor;
        this.cvfile = cvfile;
        this.isManager = ismanager;
        this.isRecruiter = isrecruiter;
    }

    //for index
    public UserInfo(int userId, String name, String contact1, String email,
            int status, String code) {
        this.userId = userId;
        this.name = name;
        this.contact1 = contact1;
        this.email = email;
        this.status = status;
        this.code = code;

    }

    public UserInfo(int userId, String name, String contact1, String email,
            int status, String code, String admin) {
        this.userId = userId;
        this.name = name;
        this.contact1 = contact1;
        this.email = email;
        this.status = status;
        this.code = code;
        this.permission = admin;
    }

    //for forgot
    public UserInfo(String email, int userId, String name, String userName, String password) {
        this.email = email;
        this.userId = userId;
        this.name = name;
        this.userName = userName;
        this.password = password;
    }

    //for client
    public UserInfo(int clientId, int countryId, String clientName, String countryName, String assetids, String assettext, String positionids, String positiontext) {
        this.clientId = clientId;
        this.countryId = countryId;
        this.clientName = clientName;
        this.countryName = countryName;
        this.assetids = assetids;
        this.assettext = assettext;
        this.positionRank = positionids;
        this.positionRankId = positiontext;
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
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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
     * @return the module
     */
    public String getModule() {
        return module;
    }

    /**
     * @param module the module to set
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * @return the moduleId
     */
    public int getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId the moduleId to set
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
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
     * @return the viewper
     */
    public String getViewper() {
        return viewper;
    }

    /**
     * @param viewper the viewper to set
     */
    public void setViewper(String viewper) {
        this.viewper = viewper;
    }

    /**
     * @return the addper
     */
    public String getAddper() {
        return addper;
    }

    /**
     * @param addper the addper to set
     */
    public void setAddper(String addper) {
        this.addper = addper;
    }

    /**
     * @return the editper
     */
    public String getEditper() {
        return editper;
    }

    /**
     * @param editper the editper to set
     */
    public void setEditper(String editper) {
        this.editper = editper;
    }

    /**
     * @return the deleteper
     */
    public String getDeleteper() {
        return deleteper;
    }

    /**
     * @param deleteper the deleteper to set
     */
    public void setDeleteper(String deleteper) {
        this.deleteper = deleteper;
    }

    /**
     * @return the mpermission
     */
    public String getMpermission() {
        return mpermission;
    }

    /**
     * @param mpermission the mpermission to set
     */
    public void setMpermission(String mpermission) {
        this.mpermission = mpermission;
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
     * @return the approveper
     */
    public String getApproveper() {
        return approveper;
    }

    /**
     * @param approveper the approveper to set
     */
    public void setApproveper(String approveper) {
        this.approveper = approveper;
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
     * @return the ddlValue
     */
    public int getDdlValue() {
        return ddlValue;
    }

    /**
     * @param ddlValue the ddlValue to set
     */
    public void setDdlValue(int ddlValue) {
        this.ddlValue = ddlValue;
    }

    /**
     * @return the ddlLable
     */
    public String getDdlLable() {
        return ddlLable;
    }

    /**
     * @param ddlLable the ddlLable to set
     */
    public void setDdlLable(String ddlLable) {
        this.ddlLable = ddlLable;
    }

    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return the managerId
     */
    public int getManagerId() {
        return managerId;
    }

    /**
     * @return the managerName
     */
    public String getManagerName() {
        return managerName;
    }

    /**
     * @return the allclient
     */
    public int getAllclient() {
        return allclient;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @return the countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @return the assetids
     */
    public String getAssetids() {
        return assetids;
    }

    /**
     * @return the assettext
     */
    public String getAssettext() {
        return assettext;
    }

    public int getCompanytype() {
        return companytype;
    }

    public String getCids() {
        return cids;
    }

    /**
     * @return the subtype
     */
    public int getSubtype() {
        return subtype;
    }

    /**
     * @return the cassessor
     */
    public int getCassessor() {
        return cassessor;
    }

    /**
     * @return the cvfile
     */
    public String getCvfile() {
        return cvfile;
    }

    /**
     * @return the positionRank
     */
    public String getPositionRank() {
        return positionRank;
    }

    /**
     * @return the positionRankId
     */
    public String getPositionRankId() {
        return positionRankId;
    }

    /**
     * @return the isManager
     */
    public int getIsManager() {
        return isManager;
    }

    /**
     * @return the isRecruiter
     */
    public int getIsRecruiter() {
        return isRecruiter;
    }
}
