package com.web.jxp.competencypriorities;

public class CompetencyprioritiesInfo
{
    private int competencyprioritiesId;
    private String name;
    private int status;
    private int userId;  
    private int departmentId;  
    private int ddlValue;
    private int schedule;
    private String ddlLabel;    
    private int assettypeId;
    private int clientId;
    private int assetId;
    private int degreeId;
    private String assetName;
    private String clientName;
    private String deptColumn;
    private String assettypeName;
    private String description;
    private String department;
    private String departmentName;    
    
    //for ddl
    public CompetencyprioritiesInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    //for list
    public CompetencyprioritiesInfo(int competencyprioritiesId, String name, String clientName, 
            String assetName, String department, int degreeId, String description, int status)
    {
        this.competencyprioritiesId = competencyprioritiesId;
        this.name = name;
        this.clientName = clientName;
        this.assetName = assetName;
        this.deptColumn = department;
        this.degreeId = degreeId;
        this.description = description;
        this.status = status;
    }
    //for add
    public CompetencyprioritiesInfo(int competencyprioritiesId, String name, int assetId, String description, 
            int status, int userId, String strdepartment, int clientId, int degreeId)
    { 
        this.competencyprioritiesId = competencyprioritiesId;
        this.name = name;
        this.assetId = assetId;
        this.description = description;
        this.status = status;
        this.userId = userId;     
        this.deptColumn = strdepartment;
        this.clientId = clientId;
        this.degreeId = degreeId;
    }
    //for view
    public CompetencyprioritiesInfo(int competencyprioritiesId, String name, String clientName, 
            String assetName, String deptIds, int degreeId, int status, String description)
    {
        this.competencyprioritiesId = competencyprioritiesId;
        this.name = name;
        this.clientName = clientName;
        this.assetName = assetName;
        this.deptColumn = deptIds;
        this.degreeId = degreeId;
        this.status = status;
        this.description = description; 
    }
    //For Edit
    public CompetencyprioritiesInfo(int competencyprioritiesId, String name, int clientId, int clientassetId, 
            String pdeptids, int degreeId, String description, int status )
    {
        this.competencyprioritiesId = competencyprioritiesId;
        this.name = name;
        this.clientId = clientId;
        this.assetId = clientassetId;
        this.deptColumn = pdeptids;
        this.degreeId = degreeId;
        this.description = description;
        this.status = status;
    }
    /**
     * @return the competencyprioritiesId
     */
    public int getCompetencyprioritiesId() {
        return competencyprioritiesId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the ddlValue
     */
    public int getDdlValue() {
        return ddlValue;
    }

    /**
     * @return the ddlLabel
     */
    public String getDdlLabel() {
        return ddlLabel;
    }

    /**
     * @return the assettypeId
     */
    public int getAssettypeId() {
        return assettypeId;
    }

    /**
     * @return the assettypeName
     */
    public String getAssettypeName() {
        return assettypeName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @return the departmentId
     */
    public int getDepartmentId() {
        return departmentId;
    }

    /**
     * @return the schedule
     */
    public int getSchedule() {
        return schedule;
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
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @return the deptColumn
     */
    public String getDeptColumn() {
        return deptColumn;
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
     * @return the assetName
     */
    public String getAssetName() {
        return assetName;
    }

    /**
     * @param assetName the assetName to set
     */
    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    /**
     * @return the degreeId
     */
    public int getDegreeId() {
        return degreeId;
    }
}
