package com.web.jxp.pcode;
public class PcodeInfo
{
    private int pcodeId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;    
    private int assettypeId;
    private String assettypeName;
    private String deptName;
    private int pdeptId;
    private String description;
    private int pquestionId;
    private String code;
    private int pcodequestionId;
    private int pcategoryId;
    private int qcount;
    private String questionName;
    private String categoryName;
    
    //for ddl
    public PcodeInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    //for list
    public PcodeInfo(int pcodeId, String code, String name, String assettypeName, String deptName, int status, int qcount)
    {
        this.pcodeId = pcodeId;
        this.code = code;
        this.name = name;
        this.assettypeName = assettypeName;
        this.deptName = deptName;
        this.status = status;
        this.qcount = qcount;
    }
    //for add
    public PcodeInfo(int pcodeId, String code, String name, int assettypeId, int pdeptId, String description, int status, int userId)
    {   
        this.pcodeId = pcodeId;
        this.code = code;
        this.name = name;
        this.assettypeId = assettypeId;
        this.pdeptId = pdeptId;
        this.description = description;
        this.status = status;
        this.userId = userId;        
    }
    
    //for view
    public PcodeInfo(int pcodeId, String code, String name, String assettypeName, String deptName, 
        String description, int status, int assettypeId, int pdeptId)
    {   
        this.pcodeId = pcodeId;
        this.code = code;
        this.name = name;
        this.assettypeName = assettypeName;
        this.deptName = deptName;
        this.description = description;
        this.status = status;      
        this.assettypeId = assettypeId;
        this.pdeptId = pdeptId;
    }
    
    //for question list
    public PcodeInfo(int pquestionId, String name, int status)
    {   
        this.pquestionId = pquestionId;
        this.name = name;
        this.status = status;    
    }
    //for question list
    public PcodeInfo(String name, int pquestionId, int pcodequestionId, int pcategoryId)
    {
        this.name = name;
        this.pquestionId = pquestionId;
        this.pcodequestionId = pcodequestionId;
        this.pcategoryId = pcategoryId;
    }
    //For ExcelQuestion
    public PcodeInfo(String questionName, String categoryName, String code, String assettypeName, String deptName)
    {
        this.questionName = questionName;
        this.categoryName = categoryName;
        this.code = code;
        this.assettypeName = assettypeName;
        this.deptName = deptName;
    }

    /**
     * @return the pcodeId
     */
    public int getPcodeId() {
        return pcodeId;
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
     * @return the deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @return the pdeptId
     */
    public int getPdeptId() {
        return pdeptId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the pquestionId
     */
    public int getPquestionId() {
        return pquestionId;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the pcodequestionId
     */
    public int getPcodequestionId() {
        return pcodequestionId;
    }

    /**
     * @return the pcategoryId
     */
    public int getPcategoryId() {
        return pcategoryId;
    }

    /**
     * @return the qcount
     */
    public int getQcount() {
        return qcount;
    }

    /**
     * @return the questionName
     */
    public String getQuestionName() {
        return questionName;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }
}
