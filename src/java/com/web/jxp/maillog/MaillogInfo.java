package com.web.jxp.maillog;

public class MaillogInfo
{
    private int accessId;
    private String name;
    private String ipaddress;
    private String desc;
    private String regDate;
    private String localip;
    private int ddlValue;
    private String ddlLable;

    private String email;
    private String subject;
    private int maillogId;
    private int type;
    private String filename;
    private String attachment1;
    private String attachment2;
    private String attachment3;
    private String attachment4;
    private String cc;
    
    public MaillogInfo ()
    {
    }

     public MaillogInfo(int ddlValue,String ddlLable)
    {
        this.ddlValue = ddlValue;
        this.ddlLable = ddlLable;
    }

    //for maillog
    public MaillogInfo(int maillogId, int type, String name, String email, String bcc,
        String subject, String filename, String regDate)
    {
        this.maillogId = maillogId;
        this.type = type;
        this.name = name;
        this.email = email;
        this.cc = bcc;
        this.subject = subject;
        this.filename = filename;
        this.regDate = regDate;

    }
    //maillogId, name, toaddress,ccaddress,subject, date,filename
    public MaillogInfo(int maillogId, String name, String toaddress,String ccaddress, 
            String subject, String date, String filename,String attachment1,String attachment2,String attachment3, String attachment4)
    {
        this.maillogId = maillogId;
        this.name = name;
        this.email = toaddress;
        this.cc = ccaddress;
        this.subject = subject;
        this.regDate = date;
        this.filename = filename;
        this.attachment1 = attachment1;
        this.attachment2 = attachment2;
        this.attachment3 = attachment3;
        this.attachment4 = attachment4;
    }

    public String getDdlLable() {
        return ddlLable;
    }

    public int getDdlValue() {
        return ddlValue;
    }
    public int getAccessId ()
    {
        return accessId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getIpaddress()
    {
        return ipaddress;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    public String getRegDate()
    {
        return regDate;
    }

    /**
     * @return the localip
     */
    public String getLocalip() {
        return localip;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return the maillogId
     */
    public int getMaillogId() {
        return maillogId;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @return the cc
     */
    public String getCc() {
        return cc;
    }

    /**
     * @return the attachment4
     */
    public String getAttachment4() {
        return attachment4;
    }

    /**
     * @return the attachment1
     */
    public String getAttachment1() {
        return attachment1;
    }

    /**
     * @return the attachment2
     */
    public String getAttachment2() {
        return attachment2;
    }

    /**
     * @return the attachment3
     */
    public String getAttachment3() {
        return attachment3;
    }
}
