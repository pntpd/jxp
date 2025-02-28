package com.web.jxp.createtraining;

public class CreatetrainingInfo
{
    private int categoryId;
    private String name;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    private String description;
    private String filename;
    private String filenamehidden;
    private int coursecount;
    private int subcategorycount;
    private int subcategoryId;
    private String subcategoryname;
    private String coursetypename;
    private int courseId;
    private String date;
    private int courseattachmentId;
    private int coursetypeId;
    private int coursenameId;
    private String categoryname;
    private String coursename;
    private int count;
    private String elearningfile;
    
    //for ddl
    public CreatetrainingInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }

    public CreatetrainingInfo(int categoryId, String name, int status, int userId)
    {
        this.categoryId = categoryId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        
    }
    //Sub-categorylist
    public CreatetrainingInfo(int categoryId, String name, int status, String description, int coursecount, int subcategoryId)
    {
        this.categoryId = categoryId;
        this.name = name;
        this.status = status;
        this.description = description;
        this.coursecount = coursecount;
        this.subcategoryId = subcategoryId;
    }
    public CreatetrainingInfo(int categoryId, String name, int status, String description, int coursecount, int subcategoryId, String categoryname)
    {
        this.categoryId = categoryId;
        this.name = name;
        this.status = status;
        this.description = description;
        this.coursecount = coursecount;
        this.subcategoryId = subcategoryId;
        this.categoryname = categoryname;
    }
    
    //Modify course
    public CreatetrainingInfo(int categoryId, int coursenameId, int status, String description, 
            int courseId, int subcategoryId,int coursetypeId, String elearningfile)
    {
        this.categoryId = categoryId;
        this.coursenameId = coursenameId;
        this.status = status;
        this.description = description;
        this.courseId = courseId;
        this.subcategoryId = subcategoryId;
        this.coursetypeId = coursetypeId;
        this.elearningfile = elearningfile;
    }
    //For Index
    public CreatetrainingInfo(int categoryId, String name, int status, int subcategorycount, int coursecount)
    {
        this.categoryId = categoryId;
        this.name = name;
        this.status = status;
        this.subcategorycount = subcategorycount;
        this.coursecount = coursecount;
        
    }
    public CreatetrainingInfo(int categoryId, String name, int status, int userId,String description)
    {
        this.categoryId = categoryId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.description = description;
    }
    public CreatetrainingInfo(int categoryId,int subcategoryId, String name, int status, int userId,String description)
    {
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.description = description;
    }
    public CreatetrainingInfo(int categoryId,int subcategoryId, int coursenameId, int status, int userId,
            String description,int courseId, int coursetypeId, String elearningfile)
    {
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.coursenameId = coursenameId;
        this.status = status;
        this.userId = userId;
        this.description = description;
        this.courseId = courseId;
        this.coursetypeId = coursetypeId;
        this.elearningfile = elearningfile;
    }
    
     public CreatetrainingInfo(int categoryId, String coursename, int status, String description,
             String subcategoryname,String coursetypename, int subcategoryId, int courseId, int count, String elearningfile)
    {
        this.categoryId = categoryId;
        this.name = coursename;
        this.status = status;
        this.description = description;
        this.subcategoryname = subcategoryname;
        this.coursetypename = coursetypename;
        this.subcategoryId = subcategoryId;
        this.courseId = courseId;
        this.count = count;
        this.elearningfile = elearningfile;
    }
     
     public CreatetrainingInfo(int courseattachmentId, String filename, String date, String name)
    {
        this.courseattachmentId = courseattachmentId;
        this.filename = filename;
        this.date = date;
        this.name = name;
    }
     
     //excel
     public CreatetrainingInfo(String categoryname,String subcategoryname,String coursename)
     {
         this.categoryname = categoryname;
         this.subcategoryname = subcategoryname;
         this.coursename = coursename;
     }

    public int getCount() {
        return count;
    }

    public int getCoursenameId() {
        return coursenameId;
    }

    public void setCoursenameId(int coursenameId) {
        this.coursenameId = coursenameId;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public int getCoursetypeId() {
        return coursetypeId;
    }

    public String getDate() {
        return date;
    }
     
    public int getCourseattachmentId() {
        return courseattachmentId;
    }
     
     

    public int getCourseId() {
        return courseId;
    }

     
    public String getSubcategoryname() {
        return subcategoryname;
    }

    public String getCoursetypename() {
        return coursetypename;
    }

    public int getCoursecount() {
        return coursecount;
    }

    public int getSubcategorycount() {
        return subcategorycount;
    }

    public int getCategoryId() {
        return categoryId;
    }

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

    public String getDescription() {
        return description;
    }

    public String getFilename() {
        return filename;
    }

    public String getFilenamehidden() {
        return filenamehidden;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public String getCoursename() {
        return coursename;
    }

    /**
     * @return the elearningfile
     */
    public String getElearningfile() {
        return elearningfile;
    }

}
