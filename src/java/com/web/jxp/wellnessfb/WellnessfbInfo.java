package com.web.jxp.wellnessfb;

public class WellnessfbInfo
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
    private int questioncount;
    private int subcategorycount;
    private int subcategoryId;
    private String subcategoryname;
    private String question;
    private int questionId;
    private String date;
    private int questionattachmentId;
    private int questiontypeId;
    private int questionnameId;
    private String categoryname;
    private String questionname;
    private int count;
    private int answertypeId;
    private String addvalues;
    private int recipientId;
    private String assettypeids;
    private String responderids;
    
    private String answertypevalue;
    private String recipientvalue;
    private String responderidsvalue;
    private int categorystatus;
    private int subcategorystatus;
    
    private int repeatdp;
    private String notification;
    private String schedulevalue;
    private int month;
    private int day;
    private int schedulecb;
    private String repeatvalue;
    
    //for ddl
    public WellnessfbInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
    }
    public WellnessfbInfo(int month, int day, String date)
    {
        this.month = month;
        this.day = day;
        this.date = date;
    }

    public WellnessfbInfo(int categoryId, String name, int status, int userId)
    {
        this.categoryId = categoryId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        
    }
    public WellnessfbInfo(int categoryId, String name, int status, String description, int questioncount, int subcategoryId, int categorystatus, String repeatvalue, String notification)
    {
        this.categoryId = categoryId;
        this.name = name;
        this.status = status;
        this.description = description;
        this.questioncount = questioncount;
        this.subcategoryId = subcategoryId;
        this.categorystatus = categorystatus;
        this.repeatvalue = repeatvalue;
        this.notification = notification;
    }
    public WellnessfbInfo(int categoryId, String name, int status, String description, int subcategoryId, String categoryname, int repeatdp,
            String notification, String schedulevalue, int schedulecb)
    {
        this.categoryId = categoryId;
        this.name = name;
        this.status = status;
        this.description = description;
        this.subcategoryId = subcategoryId;
        this.categoryname = categoryname;
        this.repeatdp = repeatdp;
        this.notification = notification;
        this.schedulevalue = schedulevalue;
        this.schedulecb = schedulecb;
    }
    public WellnessfbInfo(int categoryId, int subcategoryId, int questionId, String question, String description, int answertypeId, String addvalues, String assettypeids, String responderids, int recipientId, int status)
    {
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.question = question;
        this.description = description;
        this.answertypeId = answertypeId;
        this.addvalues = addvalues;
        this.assettypeids = assettypeids;
        this.responderids = responderids;
        this.recipientId = recipientId;
        this.status = status;
    }
    public WellnessfbInfo(int categoryId, int questionnameId, int status, String description, int questionId, int subcategoryId,int questiontypeId)
    {
        this.categoryId = categoryId;
        this.questionnameId = questionnameId;
        this.status = status;
        this.description = description;
        this.questionId = questionId;
        this.subcategoryId = subcategoryId;
        this.questiontypeId = questiontypeId;
    }
    public WellnessfbInfo(int categoryId, String name, int status, int subcategorycount, int questioncount)
    {
        this.categoryId = categoryId;
        this.name = name;
        this.status = status;
        this.subcategorycount = subcategorycount;
        this.questioncount = questioncount;
        
    }
    public WellnessfbInfo(int categoryId, String name, int status, int userId,String description)
    {
        this.categoryId = categoryId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.description = description;
    }
    public WellnessfbInfo(int categoryId,int subcategoryId, String name, int status, int userId,String description, int repeatdp, String notification, String schedulevalue, int schedulecb)
    {
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.description = description;
        this.repeatdp = repeatdp;
        this.notification = notification;
        this.schedulevalue = schedulevalue;
        this.schedulecb = schedulecb;
    }
    public WellnessfbInfo( int categoryId, int subcategoryId, String question, String addvalues, int answertypeId, String assettypeids,
            String responderids, int status, int uId, String description, int questionId, int recipientId)
    {
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.question = question;
        this.addvalues = addvalues;
        this.answertypeId = answertypeId;
        this.assettypeids = assettypeids;
        this.responderids = responderids;
        this.status = status;
        this.userId = uId;
        this.description = description;
        this.questionId = questionId;
        this.recipientId = recipientId;
    }
    
     public WellnessfbInfo(int categoryId, int subcategoryId, String question,String answertypevalue, String responderidsvalue, 
             String recipientvalue, int questionId, int status, int subcategorystatus, int categorystatus)
    {
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.question = question;
        this.answertypevalue = answertypevalue;
        this.responderidsvalue = responderidsvalue;
        this.recipientvalue = recipientvalue;
        this.questionId = questionId;
        this.status = status;
        this.subcategorystatus = subcategorystatus;
        this.categorystatus = categorystatus;
    }
     
     public WellnessfbInfo(int questionattachmentId, String filename, String date, String name)
    {
        this.questionattachmentId = questionattachmentId;
        this.filename = filename;
        this.date = date;
        this.name = name;
    }
     
     //excel
     public WellnessfbInfo(String categoryname,String subcategoryname,String question, String repeatvalue, String schedulevalue, String notification)
     {
         this.categoryname = categoryname;
         this.subcategoryname = subcategoryname;
         this.question = question;
         this.repeatvalue = repeatvalue;
         this.schedulevalue = schedulevalue;
         this.notification = notification;
     }

    public int getCount() {
        return count;
    }

    public int getQuestioncount() {
        return questioncount;
    }

    public void setQuestioncount(int questioncount) {
        this.questioncount = questioncount;
    }

    public String getQuestion() {
        return question;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionattachmentId() {
        return questionattachmentId;
    }

    public void setQuestionattachmentId(int questionattachmentId) {
        this.questionattachmentId = questionattachmentId;
    }

    public int getQuestiontypeId() {
        return questiontypeId;
    }

    public void setQuestiontypeId(int questiontypeId) {
        this.questiontypeId = questiontypeId;
    }

    public int getQuestionnameId() {
        return questionnameId;
    }

    public void setQuestionnameId(int questionnameId) {
        this.questionnameId = questionnameId;
    }

    public String getQuestionname() {
        return questionname;
    }

    public void setQuestionname(String questionname) {
        this.questionname = questionname;
    }
    
    public int getCoursenameId() {
        return questionnameId;
    }

    public void setCoursenameId(int questionnameId) {
        this.questionnameId = questionnameId;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public int getCoursetypeId() {
        return questiontypeId;
    }

    public String getDate() {
        return date;
    }
     
    public int getCourseattachmentId() {
        return questionattachmentId;
    }
     
     

    public int getCourseId() {
        return questionId;
    }

     
    public String getSubcategoryname() {
        return subcategoryname;
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

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilenamehidden() {
        return filenamehidden;
    }

    public void setFilenamehidden(String filenamehidden) {
        this.filenamehidden = filenamehidden;
    }

    public int getAnswertypeId() {
        return answertypeId;
    }

    public void setAnswertypeId(int answertypeId) {
        this.answertypeId = answertypeId;
    }

    public String getAddvalues() {
        return addvalues;
    }
    
    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public String getAssettypeids() {
        return assettypeids;
    }

    public String getResponderids() {
        return responderids;
    }

    public String getAnswertypevalue() {
        return answertypevalue;
    }

    public String getRecipientvalue() {
        return recipientvalue;
    }

    public String getResponderidsvalue() {
        return responderidsvalue;
    }

    public int getCategorystatus() {
        return categorystatus;
    }

    public int getSubcategorystatus() {
        return subcategorystatus;
    }

    public int getRepeatdp() {
        return repeatdp;
    }

    public String getNotification() {
        return notification;
    }
    

    public String getSchedulevalue() {
        return schedulevalue;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getSchedulecb() {
        return schedulecb;
    }

    public String getRepeatvalue() {
        return repeatvalue;
    }
    
    
    
}
