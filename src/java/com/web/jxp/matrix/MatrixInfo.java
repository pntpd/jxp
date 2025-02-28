package com.web.jxp.matrix;

public class MatrixInfo
{
    private int matrixId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    private String assettypeName;
    private int assettypeId;
    private int pcount;
    private int editcount;
    private String description;
    private int matrixpositionId;
    private int count1;
    private int count2;
    private int count3;
    private int subcategoryId; 
    private int courseId;
    private int matrixdetailId;
    private int categoryId;
    private int positionId;
    
    //for ddl
    public MatrixInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    //for index
    public MatrixInfo(int matrixId, String name, String assettypeName, int pcount, int editcount, int status)
    {
        this.matrixId = matrixId;
        this.name = name;
        this.assettypeName = assettypeName;
        this.pcount = pcount;
        this.editcount = editcount;
        this.status = status;
    }
    
    //for add
    public MatrixInfo(int matrixId, String name, int assettypeId, String description, int status, int userId)
    {
        this.matrixId = matrixId;
        this.name = name;
        this.assettypeId = assettypeId;
        this.description = description;
        this.status = status;
        this.userId = userId;
    }
    //for view
    public MatrixInfo(int matrixId, String name, String assettypeName, String description, int status, int assettypeId)
    {
        this.matrixId = matrixId;
        this.name = name;
        this.assettypeName = assettypeName;
        this.description = description;
        this.status = status;
        this.assettypeId = assettypeId;
    }
    //for position list page
    public MatrixInfo(int matrixpositionId, String name, int count1, int count2, int count3, int positionId, int status)
    {
        this.matrixpositionId = matrixpositionId;
        this.name = name;
        this.count1 = count1;
        this.count2 = count2;
        this.count3 = count3;
        this.positionId = positionId;
        this.status = status;
    }
    //for course list
    public MatrixInfo(String name, int matrixdetailId, int categoryId, int subcategoryId, 
        int courseId)
    {
        this.name = name;
        this.matrixdetailId = matrixdetailId;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.courseId = courseId;
    }
    /**
     * @return the matrixId
     */
    public int getMatrixId() {
        return matrixId;
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
     * @return the assettypeName
     */
    public String getAssettypeName() {
        return assettypeName;
    }

    /**
     * @return the assettypeId
     */
    public int getAssettypeId() {
        return assettypeId;
    }

    /**
     * @return the pcount
     */
    public int getPcount() {
        return pcount;
    }

    /**
     * @return the editcount
     */
    public int getEditcount() {
        return editcount;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the matrixpositionId
     */
    public int getMatrixpositionId() {
        return matrixpositionId;
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
     * @return the subcategoryId
     */
    public int getSubcategoryId() {
        return subcategoryId;
    }

    /**
     * @return the courseId
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * @return the matrixdetailId
     */
    public int getMatrixdetailId() {
        return matrixdetailId;
    }

    /**
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
    }
}
