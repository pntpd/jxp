package com.web.jxp.competencyassessments;

public class CompetencyassessmentsInfo
{
    private int competencyassessmentsId;
    private String name;
    private int status;
    private int userId;  
    private int departmentId;  
    private int ddlValue;
    private int schedule;
    private String ddlLabel;    
    private int assettypeId;
    private String assettypeName;
    private String description;
    private String department;
    private String departmentName;    
    
    //for ddl
    public CompetencyassessmentsInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    //for list
    public CompetencyassessmentsInfo(int competencyassessmentsId, String name, String department, String assettypeName, 
            int status, int schedule, String description)
    {
        this.competencyassessmentsId = competencyassessmentsId;
        this.name = name;
        this.department = department;
        this.assettypeName = assettypeName;
        this.status = status;
        this.schedule = schedule;
        this.description = description;
    }
    //for add
    public CompetencyassessmentsInfo(int competencyassessmentsId, String name, int assettypeId, String description, 
            int status, int userId, int departmentId, int schedule)
    { 
        this.competencyassessmentsId = competencyassessmentsId;
        this.name = name;
        this.assettypeId = assettypeId;
        this.description = description;
        this.status = status;
        this.userId = userId;     
        this.departmentId = departmentId;
        this.schedule = schedule;
    }
    //for view
    public CompetencyassessmentsInfo(int competencyassessmentsId, String name, String assettypeName, int status,
            String description, String department, int schedule)
    {
        this.competencyassessmentsId = competencyassessmentsId;
        this.name = name;
        this.assettypeName = assettypeName;
        this.status = status;
        this.description = description;
        this.department = department;
        this.schedule = schedule;
    }

    /**
     * @return the competencyassessmentsId
     */
    public int getCompetencyassessmentsId() {
        return competencyassessmentsId;
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
}
