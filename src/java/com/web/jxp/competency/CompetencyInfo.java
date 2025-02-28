package com.web.jxp.competency;

public class CompetencyInfo
{
    private int competencyId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private int count1;
    private int count2;
    private int count3;
    private int count4;
    private int count5;
    private int count6;
    private int count7;
    private int count8;
    private String ddlLabel;
    private int trackerId;
    private String department; 
    private String position;
    private String grade;
    private String role;
    private String priority;
    private int dateDiff;
    private int crewrotationId;
    private int pcodeId;
    private int activeflag;
    private int positionId;
    
    //for ddl
    public CompetencyInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    
    //Position-personnel
    public CompetencyInfo(int crewrotationId, String name, String position, int positionId)
    {
        this.crewrotationId = crewrotationId;
        this.name = name;
        this.position = position;
        this.positionId = positionId;
    }
    
    public CompetencyInfo(int ddlValue, String ddlLabel, int count1, int count2, int count3)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
        this.count1 = count1;
        this.count2 = count2;
        this.count3 = count3;
    }
    //For getDeptTableCountList
    public CompetencyInfo(int ddlValue, String department, int count1, int count2, int count3, int count4, int count5, int count6, int count7, int count8)
    {
        this.ddlValue = ddlValue;
        this.department = department;
        this.count1 = count1;
        this.count2 = count2;
        this.count3 = count3;
        this.count4 = count4;
        this.count5 = count5;
        this.count6 = count6;
        this.count7 = count7;
        this.count8 = count8;
    }
    //For Trackerlist
    public CompetencyInfo(int crewrotationId, int trackerId, int status, int pcodeId, int activeflag)
    {
        this.crewrotationId = crewrotationId;
        this.trackerId = trackerId;
        this.status = status;
        this.pcodeId = pcodeId;
        this.activeflag = activeflag;
    }
    
    //For CompentencyListByName
    public CompetencyInfo(int trackerId, String department, String position, String name, String role, String priority, int dateDiff, int status)
    {
        this.trackerId = trackerId;
        this.department = department;
        this.position = position;
        this.name = name;
        this.role = role;
        this.priority = priority;
        this.dateDiff = dateDiff;
        this.status = status;
    }
    //for available role
    public CompetencyInfo (int positionId, int pcodeId)
    {
        this.positionId = positionId;
        this.pcodeId = pcodeId;
    }

    /**
     * @return the competencyId
     */
    public int getCompetencyId() {
        return competencyId;
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
     * @return the count1
     */
    public int getCount1() {
        return count1;
    }

    /**
     * @return the count2
     */
    public int getCount2() {
        return count2;
    }

    /**
     * @return the count3
     */
    public int getCount3() {
        return count3;
    }

    /**
     * @return the trackerId
     */
    public int getTrackerId() {
        return trackerId;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @return the grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @return the priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * @return the dateDiff
     */
    public int getDateDiff() {
        return dateDiff;
    }

    /**
     * @return the crewrotationId
     */
    public int getCrewrotationId() {
        return crewrotationId;
    }

    /**
     * @return the pcodeId
     */
    public int getPcodeId() {
        return pcodeId;
    }

    /**
     * @return the activeflag
     */
    public int getActiveflag() {
        return activeflag;
    }

    /**
     * @return the count4
     */
    public int getCount4() {
        return count4;
    }

    /**
     * @return the count5
     */
    public int getCount5() {
        return count5;
    }

    /**
     * @return the count6
     */
    public int getCount6() {
        return count6;
    }

    /**
     * @return the count7
     */
    public int getCount7() {
        return count7;
    }

    /**
     * @return the count8
     */
    public int getCount8() {
        return count8;
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
    }
}
