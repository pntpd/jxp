package com.web.jxp.wellnessfb;
import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class WellnessfbForm extends ActionForm
{
    private int categoryId;
    private int status;
    private String search;
    private String doCancel;
    private String doModify;
    private String doModifysubcategory;
    private String doModifyquestion;
    private String name;
    private String doAdd;
    private String doSave;
    private Collection statuses;
    private String doView;
    private int statusIndex;
    private int ctp;
    
    private String categorycode;
    private String cdescription;
    private FormFile attachment;
    private String attachmenthidden;
    
    private String doIndexSubcategory;
    private String doAddSubcategory;
    private String doSaveSubcategory;
    private int subcategoryId;
    private String subcategorycode;
    private String doIndexQuestion;
    private String doAddQuestion;
    private String doSaveQuestion;
    private Collection subcategorys;
    private int subcategoryIdIndex;
    private String questioncode;
    private String fname;
    private int esubcategoryId;
    private int questionId;
    private int questionnameId;
    private Collection questionnames;
    private int questiontypeId;
    private Collection questiontypes;
    private String categoryname;
    private String subcategoryname;
    private String dodeleteQuestion;
    private String dodeletesubcategory;
    private int substatus;
    
    private String question;
    private int answertypeId;
    private int recipientId;
    private String addvalue;
    private String [] assettype;
    private String [] responder;
    private String assettypecb;
    private String respondercb;
    private int schedulecb;
    private int repeatdp;
    private String [] notificationdp;
    private String schedulevalue;
    private String monthdp;

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public String getRespondercb() {
        return respondercb;
    }

    public void setRespondercb(String respondercb) {
        this.respondercb = respondercb;
    }

    public String getAssettypecb() {
        return assettypecb;
    }

    public void setAssettypecb(String assettypecb) {
        this.assettypecb = assettypecb;
    }

    public String[] getAssettype() {
        return assettype;
    }

    public void setAssettype(String[] assettype) {
        this.assettype = assettype;
    }

    public String[] getResponder() {
        return responder;
    }

    public void setResponder(String[] responder) {
        this.responder = responder;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswertypeId() {
        return answertypeId;
    }

    public void setAnswertypeId(int answertypeId) {
        this.answertypeId = answertypeId;
    }

    public String getAddvalue() {
        return addvalue;
    }

    public void setAddvalue(String addvalue) {
        this.addvalue = addvalue;
    }

    public int getSubstatus() {
        return substatus;
    }

    public void setSubstatus(int substatus) {
        this.substatus = substatus;
    }

    public String getDodeleteQuestion() {
        return dodeleteQuestion;
    }

    public void setDodeleteQuestion(String dodeleteQuestion) {
        this.dodeleteQuestion = dodeleteQuestion;
    }

    public String getDodeletesubcategory() {
        return dodeletesubcategory;
    }

    public void setDodeletesubcategory(String dodeletesubcategory) {
        this.dodeletesubcategory = dodeletesubcategory;
    }

    public String getSubcategoryname() {
        return subcategoryname;
    }

    public void setSubcategoryname(String subcategoryname) {
        this.subcategoryname = subcategoryname;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getDoModifysubcategory() {
        return doModifysubcategory;
    }

    public void setDoModifysubcategory(String doModifysubcategory) {
        this.doModifysubcategory = doModifysubcategory;
    }

    public String getDoModifyquestion() {
        return doModifyquestion;
    }

    public void setDoModifyquestion(String doModifyquestion) {
        this.doModifyquestion = doModifyquestion;
    }
  

    public int getEsubcategoryId() {
        return esubcategoryId;
    }

    public void setEsubcategoryId(int esubcategoryId) {
        this.esubcategoryId = esubcategoryId;
    }
    
    

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Collection getSubcategorys() {
        return subcategorys;
    }

    public void setSubcategorys(Collection subcategorys) {
        this.subcategorys = subcategorys;
    }

    public int getSubcategoryIdIndex() {
        return subcategoryIdIndex;
    }

    public void setSubcategoryIdIndex(int subcategoryIdIndex) {
        this.subcategoryIdIndex = subcategoryIdIndex;
    }

    public String getDoIndexQuestion() {
        return doIndexQuestion;
    }

    public void setDoIndexQuestion(String doIndexQuestion) {
        this.doIndexQuestion = doIndexQuestion;
    }

    public String getQuestioncode() {
        return questioncode;
    }

    public void setQuestioncode(String questioncode) {
        this.questioncode = questioncode;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionnameId() {
        return questionnameId;
    }

    public void setQuestionnameId(int questionnameId) {
        this.questionnameId = questionnameId;
    }

    public Collection getQuestionnames() {
        return questionnames;
    }

    public void setQuestionnames(Collection questionnames) {
        this.questionnames = questionnames;
    }

    public int getQuestiontypeId() {
        return questiontypeId;
    }

    public void setQuestiontypeId(int questiontypeId) {
        this.questiontypeId = questiontypeId;
    }

    public Collection getQuestiontypes() {
        return questiontypes;
    }

    public void setQuestiontypes(Collection questiontypes) {
        this.questiontypes = questiontypes;
    }

    public String getDoAddQuestion() {
        return doAddQuestion;
    }

    public void setDoAddQuestion(String doAddQuestion) {
        this.doAddQuestion = doAddQuestion;
    }

    public String getDoSaveQuestion() {
        return doSaveQuestion;
    }

    public void setDoSaveQuestion(String doSaveQuestion) {
        this.doSaveQuestion = doSaveQuestion;
    }

    
    public String getSubcategorycode() {
        return subcategorycode;
    }

    public void setSubcategorycode(String subcategorycode) {
        this.subcategorycode = subcategorycode;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }
    
    

    public String getDoSaveSubcategory() {
        return doSaveSubcategory;
    }

    public void setDoSaveSubcategory(String doSaveSubcategory) {
        this.doSaveSubcategory = doSaveSubcategory;
    }

    public String getDoIndexSubcategory() {
        return doIndexSubcategory;
    }

    public void setDoIndexSubcategory(String doIndexSubcategory) {
        this.doIndexSubcategory = doIndexSubcategory;
    }

    public String getDoAddSubcategory() {
        return doAddSubcategory;
    }

    public void setDoAddSubcategory(String doAddSubcategory) {
        this.doAddSubcategory = doAddSubcategory;
    }
    
    

    public FormFile getAttachment() {
        return attachment;
    }

    public void setAttachment(FormFile attachment) {
        this.attachment = attachment;
    }

    public String getAttachmenthidden() {
        return attachmenthidden;
    }

    public void setAttachmenthidden(String attachmenthidden) {
        this.attachmenthidden = attachmenthidden;
    }

    public String getCdescription() {
        return cdescription;
    }

    public void setCdescription(String cdescription) {
        this.cdescription = cdescription;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategorycode() {
        return categorycode;
    }

    public void setCategorycode(String categorycode) {
        this.categorycode = categorycode;
    }

    public int getCtp() {
        return ctp;
    }

    public void setCtp(int ctp) {
        this.ctp = ctp;
    }

    
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
     * @return the doSave
     */
    public String getDoSave() {
        return doSave;
    }

    /**
     * @param doSave the doSave to set
     */
    public void setDoSave(String doSave) {
        this.doSave = doSave;
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
     * @return the doView
     */
    public String getDoView() {
        return doView;
    }

    /**
     * @param doView the doView to set
     */
    public void setDoView(String doView) {
        this.doView = doView;
    }

    /**
     * @return the statusIndex
     */
    public int getStatusIndex() {
        return statusIndex;
    }

    /**
     * @param statusIndex the statusIndex to set
     */
    public void setStatusIndex(int statusIndex) {
        this.statusIndex = statusIndex;
    }

    public int getSchedulecb() {
        return schedulecb;
    }

    public void setSchedulecb(int schedulecb) {
        this.schedulecb = schedulecb;
    }

    public int getRepeatdp() {
        return repeatdp;
    }

    public void setRepeatdp(int repeatdp) {
        this.repeatdp = repeatdp;
    }

    public String[] getNotificationdp() {
        return notificationdp;
    }

    public void setNotificationdp(String[] notificationdp) {
        this.notificationdp = notificationdp;
    }
    
    public String getSchedulevalue() {
        return schedulevalue;
    }

    public void setSchedulevalue(String schedulevalue) {
        this.schedulevalue = schedulevalue;
    }

    public String getMonthdp() {
        return monthdp;
    }

    public void setMonthdp(String monthdp) {
        this.monthdp = monthdp;
    }

    
}