package com.web.jxp.documentissuedby;

import java.util.Collection;

public class DocumentissuedbyInfo
{
    private int documentissuedbyId;
    private String documentissuedbyName;
    private int status;
    private int userId;  
    private int ddlValue;
    private String ddlLabel;
    
    private int countryId;
    private Collection countrys;
    private String countryName;
    
    private int documentId;
    private Collection documents;
    private String documentName;

    public int getDocumentId() {
        return documentId;
    }

    public Collection getDocuments() {
        return documents;
    }

    public String getDocumentName() {
        return documentName;
    }
    
    public int getCountryId() {
        return countryId;
    }

    public Collection getCountrys() {
        return countrys;
    }

    public String getCountryName() {
        return countryName;
    }
    
    //for ddl
    public DocumentissuedbyInfo(int ddlValue, String ddlLabel)
    {
        this.ddlValue = ddlValue;
        this.ddlLabel = ddlLabel;
        
    }

    public DocumentissuedbyInfo(int documentissuedbyId, String name, int status, int userId)
    {
        this.documentissuedbyId = documentissuedbyId;
        this.documentissuedbyName = name;
        this.status = status;
        this.userId = userId;
    }
    
    public DocumentissuedbyInfo(int documentissuedbyId, String documentissuedbyname, int Status , String countryname)
    {   
        
        this.documentissuedbyId = documentissuedbyId;
        this.documentissuedbyName = documentissuedbyname;
        this.status = Status;
        this.countryName = countryname;
        
    }
     public DocumentissuedbyInfo(int documentissuedbyId, String name, int status, String  countryName, int userId)
    {
        this.documentissuedbyId = documentissuedbyId;
        this.documentissuedbyName = name;
        this.status = status;
        this.countryName = countryName;
        this.userId = userId;
        
    }
     public DocumentissuedbyInfo(int documentissuedbyId, String name, int status, String  countryName, String documentName, int userId)
    {
        this.documentissuedbyId = documentissuedbyId;
        this.documentissuedbyName = name;
        this.status = status;
        this.countryName = countryName;
        this.documentName = documentName;
        this.userId = userId;
        
    }
    
      public DocumentissuedbyInfo(int documentissuedbyId, String countryName, String documentName, String  name, int status, int userId)
    {
        this.documentissuedbyId = documentissuedbyId;
        this.countryName = countryName;
        this.documentName = documentName;
        this.documentissuedbyName = name;
        this.status = status;
        this.userId = userId;
        
    }
      
    public DocumentissuedbyInfo(int documentissuedbyId, String countryName, String  documentName, String issuesAuthority, int status)
    {
        this.documentissuedbyId = documentissuedbyId;
        this.countryName = countryName;
        this.documentName = documentName;
        this.documentissuedbyName = issuesAuthority;
        this.status = status;
    }
    
     public DocumentissuedbyInfo(int documentissuedbyId, String name, int countryId, int status, int userId)
    {
        this.documentissuedbyId = documentissuedbyId;
        this.documentissuedbyName = name;
        this.countryId = countryId;
        this.status = status;
        this.userId = userId;
       
    }
     
    public DocumentissuedbyInfo(int documentissuedbyId, String name, int countryId, int documentId, int status, int userId)
    {
        this.documentissuedbyId = documentissuedbyId;
        this.documentissuedbyName = name;
        this.countryId = countryId;
        this.status = status;
        this.userId = userId;
        this.documentId = documentId;
       
    }

    /**
     * @return the documentissuedbyId
     */
    public int getDocumentissuedbyId() {
        return documentissuedbyId;
    }

    /**
     * @return the name
     */
    public String getDocumentissuedbyName() {
        return documentissuedbyName;
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
}
