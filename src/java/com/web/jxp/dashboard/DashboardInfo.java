
package com.web.jxp.dashboard;

import java.util.ArrayList;

public class DashboardInfo {
    private int ddlValue;
    private String ddlLabel;
    private int status1;
    private int normal;
    private int delayed;
    private int extended;
    private int crewrotationId;
    private String candidateName;
    private String position;
    private String grade;
    private int rotations;
    private int overstay;
    private int officework;
    private int training;
    private int available;
    private int absent;
    private int noavailable;
    private String fromdate;
    private String todate;
    private String expecteddate;
    private String currentdate;
    private String rdate1;
    private String rdate2;
    private int subtype;
    private ArrayList list1;
    private ArrayList list2;
    private ArrayList list3;
    private ArrayList list4;
    private int status;
    private int positionId;
    private int totalabsent;
    private int listflag;
    private String fromdate2;
    private String subtypevalue;
    private String remarks;
    private String name;
    private String shortName;
    private String colorCode;
    private ArrayList list5;
    private ArrayList list6;
    private ArrayList list7;
    private ArrayList list8;
    private int early;
    private int noofdays;
    private int candidateId;
    private ArrayList list9;
        //for ddl
    public DashboardInfo(int ddlValue, String ddlLabel) {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    public DashboardInfo(int ddlValue, String ddlLabel, String shortName, String colorCode) 
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
        this.shortName = shortName;
        this.colorCode = colorCode;
    }
    public DashboardInfo(String subtypevalue, String remarks, String name) {
        this.subtypevalue = subtypevalue;
        this.remarks = remarks;
        this.name = name;
    }
    public DashboardInfo(String subtypevalue, String remarks, String name, String position) {
        this.subtypevalue = subtypevalue;
        this.remarks = remarks;
        this.name = name;
        this.position = position;
    }
    
    DashboardInfo( int status1, int normal, int delayed, int extended)
    {
        this.status1 = status1;
        this.normal = normal;
        this.delayed = delayed;
        this.extended = extended;
    }
    
    DashboardInfo(int crewrotationId, String fromdate, String todate, String currentdate, String rdate1, 
        String rdate2, int listflag, String fromdate2,int candidateId, int positionId)
    {
        this.crewrotationId = crewrotationId;
        this.fromdate = fromdate;
        this.todate = todate;
        this.currentdate = currentdate;
        this.rdate1 = rdate1;
        this.rdate2 = rdate2;
        this.listflag = listflag;
        this.fromdate2 = fromdate2;
        this.candidateId = candidateId;
        this.positionId = positionId;
    }
    DashboardInfo(String position, String grade,int positionId)
    {
        this.positionId = positionId;
        this.position = position;
        this.grade = grade;
    }
    
    DashboardInfo(ArrayList list1, ArrayList list2, ArrayList list3, ArrayList list4, ArrayList list5, 
        ArrayList list6, ArrayList list7, ArrayList list8, ArrayList list9, int candidateId, int positionId)
    {
        this.list1 = list1;
        this.list2 = list2;
        this.list3 = list3;
        this.list4 = list4;
        this.list5 = list5;
        this.list6 = list6;
        this.list7 = list7;
        this.list8 = list8;
        this.list9 = list9;
        this.candidateId = candidateId;
        this.positionId = positionId;
    }
    
    DashboardInfo(int crewrotationId,String candidateName,String position,String grade,
            int rotations,int status, int candidateId, int positionId)
    {
        this.crewrotationId = crewrotationId;
        this.candidateName = candidateName;
        this.position = position;
        this.grade = grade;
        this.rotations = rotations;
        this.status = status;
        this.candidateId = candidateId;
        this.positionId = positionId;
    }
    
    DashboardInfo(int crewrotationId,String candidateName,String position,String grade,int rotations,int normal,int extended,int overstay,int officework,int training,int available, 
            int absent,int noavailable, int totalabsent,int early,int noofdays)
    {
        this.crewrotationId = crewrotationId;
        this.candidateName = candidateName;
        this.position = position;
        this.grade = grade;
        this.rotations = rotations;
        this.normal = normal;
        this.extended = extended;
        this.overstay = overstay;
        this.officework = officework;
        this.training = training;
        this.available = available;
        this.absent = absent;
        this.noavailable = noavailable;
        this.totalabsent = totalabsent;
        this.early = early;
        this.noofdays = noofdays;
    }
    
    public int getDdlValue() {
        return ddlValue;
    }

    public String getDdlLabel() {
        return ddlLabel;
    }

    public int getStatus1() {
        return status1;
    }

    public int getNormal() {
        return normal;
    }

    public int getDelayed() {
        return delayed;
    }

    public int getExtended() {
        return extended;
    }

    public int getCrewrotationId() {
        return crewrotationId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public String getPosition() {
        return position;
    }

    public String getGrade() {
        return grade;
    }

    public int getRotations() {
        return rotations;
    }

    public int getOverstay() {
        return overstay;
    }

    public int getOfficework() {
        return officework;
    }

    public int getTraining() {
        return training;
    }

    public int getAvailable() {
        return available;
    }

    public int getAbsent() {
        return absent;
    }

    public int getNoavailable() {
        return noavailable;
    }

    public String getFromdate() {
        return fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public String getExpecteddate() {
        return expecteddate;
    }

    public int getSubtype() {
        return subtype;
    }

    public ArrayList getList1() {
        return list1;
    }

    public ArrayList getList2() {
        return list2;
    }

    public ArrayList getList3() {
        return list3;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPositionId() {
        return positionId;
    }

    public int getTotalabsent() {
        return totalabsent;
    }

    public void setTotalabsent(int totalabsent) {
        this.totalabsent = totalabsent;
    }

    public ArrayList getList4() {
        return list4;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public String getRdate1() {
        return rdate1;
    }

    public String getRdate2() {
        return rdate2;
    }

    public int getListflag() {
        return listflag;
    }

    public void setListflag(int listflag) {
        this.listflag = listflag;
    }

    /**
     * @return the fromdate2
     */
    public String getFromdate2() {
        return fromdate2;
    } 

    /**
     * @return the subtypevalue
     */
    public String getSubtypevalue() {
        return subtypevalue;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the list5
     */
    public ArrayList getList5() {
        return list5;
    }

    public int getEarly() {
        return early;
    }
    
    public int getNoofdays() {
        return noofdays;
    }

    /**
     * @return the list6
     */
    public ArrayList getList6() {
        return list6;
    }

    /**
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @return the colorCode
     */
    public String getColorCode() {
        return colorCode;
    }

    public ArrayList getList7() {
        return list7;
    }

    public void setList7(ArrayList list7) {
        this.list7 = list7;
    }

    public ArrayList getList8() {
        return list8;
    }

    public void setList8(ArrayList list8) {
        this.list8 = list8;
    }

    /**
     * @return the list9
     */
    public ArrayList getList9() {
        return list9;
    }

    /**
     * @return the candidateId
     */
    public int getCandidateId() {
        return candidateId;
    }
    
    
}
